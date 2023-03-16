package io.mosip.capacitor.plugin;

import io.mosip.sbi.capacitor.plugin.model.DeviceDto;
import io.mosip.sbi.capacitor.plugin.model.ErrorDto;
import io.mosip.sbi.capacitor.plugin.model.DiscoverRequest;
import io.mosip.sbi.capacitor.plugin.model.InfoResponse;
import io.mosip.sbi.capacitor.plugin.model.CaptureRequest;
import io.mosip.sbi.capacitor.plugin.model.BiometricsDto;
import io.mosip.sbi.capacitor.plugin.model.CaptureResponse;
import io.mosip.sbi.capacitor.plugin.model.CaptureRespDetail;

import io.mosip.sbi.capacitor.plugin.model.CaptureDto;
import io.mosip.sbi.capacitor.plugin.model.DigitalId;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;

import androidx.activity.result.ActivityResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import android.util.Log;
import android.os.Bundle;
import android.content.Intent;
import android.content.ActivityNotFoundException;
import java.util.List;
import java.util.ArrayList;
import android.net.Uri;
import java.io.InputStream;
import java.util.stream.Collectors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Base64;

@CapacitorPlugin(name = "MosipSbiCapacitorPlugin")
public class MosipSbiCapacitorPlugin extends Plugin {

    public static final String LOG_TAG = "MosipSbiCapacitorPlugin";
    public static final String STATUS = "status";
    public static final String RESPONSE = "response";
    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String SBI_METHOD_DEVICE = "device";
    public static final String SBI_METHOD_DEVICE_INFO = "info";
    public static final String SBI_METHOD_CAPTURE = "capture";
    public static final String SBI_METHOD_RCAPTURE = "rcapture";

    @PluginMethod
    public void startActivity(PluginCall call) {
        ObjectMapper objectMapper = new ObjectMapper();

        JSObject data = call.getData();

        final String url = data.has("url") ? data.getString("url") : "";
        Log.d(LOG_TAG, "URL: " + url);

        final String methodType = data.has("methodType") ? data.getString("methodType") : null;
        Log.d(LOG_TAG, "Method Type: " + methodType);

        final String action = data.has("action") ? data.getString("action") : null;
        Log.d(LOG_TAG, "Action: " + action);

        if (action != null) {
            try {
                // discover
                if (methodType.equals(SBI_METHOD_DEVICE)) {
                    final String requestKey = data.has("requestKey") ? data.getString("requestKey") : "";
                    Log.d(LOG_TAG, "requestKey: " + requestKey);
                    String requestValue = data.has("requestValue") ? data.getString("requestValue") : "";
                    Log.d(LOG_TAG, "requestValue: " + requestValue);
                    final Intent intent = new Intent(action);
                    DiscoverRequest discoverRequest = new DiscoverRequest();
                    discoverRequest.setType(requestValue);
                    byte[] requestValueBytes = objectMapper.writeValueAsBytes(discoverRequest);
                    Log.d(LOG_TAG, "requestValue byte[]: " + requestValueBytes);
                    intent.putExtra(requestKey, requestValueBytes);
                    Log.d(LOG_TAG, "intent: " + intent);
                    startActivityForResult(call, intent, "onDiscoverResult");
                }
                // device info
                if (methodType.equals(SBI_METHOD_DEVICE_INFO)) {
                    final Intent intent = new Intent(action);
                    startActivityForResult(call, intent, "onDeviceInfoResult");
                }
                // rcapture
                if (methodType.equals(SBI_METHOD_RCAPTURE)) {
                    final String requestKey = data.has("requestKey") ? data.getString("requestKey") : "";
                    Log.d(LOG_TAG, "requestKey: " + requestKey);
                    String requestValue = data.has("requestValue") ? data.getString("requestValue") : "";
                    Log.d(LOG_TAG, "requestValue: " + requestValue);
                    final Intent intent = new Intent(action);
                    //CaptureRequest captureRequest = new CaptureRequest();
                    CaptureRequest captureRequest = objectMapper.readValue(requestValue, new TypeReference<CaptureRequest>() {
                    });
                    Log.d(LOG_TAG, "captureRequest: " + captureRequest);
                    byte[] requestValueBytes = objectMapper.writeValueAsBytes(captureRequest);
                    Log.d(LOG_TAG, "requestValue byte[]: " + requestValueBytes);
                    intent.putExtra(requestKey, requestValueBytes);
                    Log.d(LOG_TAG, "intent: " + intent);
                    startActivityForResult(call, intent, "onRCaptureResult");
                }
            } catch (JsonProcessingException jsonError) {
                call.error(jsonError.getMessage());
            } catch (ActivityNotFoundException activityError) {
                call.error(activityError.getMessage());
            } catch (Exception ioError) {
                call.error(ioError.getMessage());
            }
        } else {
            call.error("No action provided");
        }
    }

    @ActivityCallback
    public void onDiscoverResult(PluginCall call, ActivityResult result) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Log.d(LOG_TAG, "onDiscoverResult: " + result);
            Log.d(LOG_TAG, "onDiscoverResult data: " + result.getData());
            Bundle bundle = result.getData().getExtras();
            byte[] responseBytes = bundle.getByteArray("response");
            List<DeviceDto> list = objectMapper.readValue(responseBytes, new TypeReference<List<DeviceDto>>() {
            });
            if (list.isEmpty()) {
                String msg = "Discovery Failed! Invalid response";
                Log.d(LOG_TAG, "error: " + msg);
                JSObject ret = new JSObject();
                ret.put(STATUS, FAILURE);
                ret.put(RESPONSE, msg);
                call.resolve(ret);
            }
            DeviceDto deviceDto = list.get(0);
            Log.d(LOG_TAG, "deviceDto: " + deviceDto);
            if (deviceDto.getError() != null && !"0".equals(deviceDto.getError().getErrorCode())) {
                Log.d(LOG_TAG, "error code: " + deviceDto.getError().getErrorCode());
                Log.d(LOG_TAG, "error info: " + deviceDto.getError().getErrorInfo());
                JSObject ret = new JSObject();
                ret.put(STATUS, FAILURE);
                ret.put(RESPONSE, deviceDto.getError().getErrorCode() + deviceDto.getError().getErrorInfo());
                call.resolve(ret);
            }
            String callbackId = deviceDto.getCallbackId();
            Log.d(LOG_TAG, "callbackId: " + callbackId);
            JSObject ret = new JSObject();
            ret.put(STATUS, SUCCESS);
            ret.put(RESPONSE, objectMapper.writeValueAsString(deviceDto));
            call.success(ret);

        } catch (Exception e) {
            JSObject ret = new JSObject();
            Log.d(LOG_TAG, "Discover Exception: " + e.getMessage());
            ret.put(STATUS, FAILURE);
            ret.put(RESPONSE, e.getMessage());
            call.resolve(ret);
        }
    }

    @ActivityCallback
    public void onDeviceInfoResult(PluginCall call, ActivityResult result) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Log.d(LOG_TAG, "onDeviceInfoResult: " + result);
            Log.d(LOG_TAG, "onDeviceInfoResult data: " + result.getData());
            Bundle bundle = result.getData().getExtras();
            byte[] responseBytes = bundle.getByteArray("response");
            List<InfoResponse> list = objectMapper.readValue(responseBytes, new TypeReference<List<InfoResponse>>() {
            });
            if (list.isEmpty()) {
                String msg = "Device Info Failed! Invalid response";
                Log.d(LOG_TAG, "error: " + msg);
                JSObject ret = new JSObject();
                ret.put(STATUS, FAILURE);
                ret.put(RESPONSE, msg);
                call.resolve(ret);
            }
            //InfoResponse infoResponse = list.get(0);
            for (InfoResponse infoResponse : list) {
            Log.d(LOG_TAG, "infoResponse: " + infoResponse);
                if (infoResponse.getError() != null && !"0".equals(infoResponse.getError().getErrorCode())) {
                    Log.d(LOG_TAG, "error code: " + infoResponse.getError().getErrorCode());
                    Log.d(LOG_TAG, "error info: " + infoResponse.getError().getErrorInfo());
                    JSObject ret = new JSObject();
                    ret.put(STATUS, FAILURE);
                    ret.put(RESPONSE, infoResponse.getError().getErrorCode() + infoResponse.getError().getErrorInfo());
                    call.resolve(ret);
                }
            }
            JSObject ret = new JSObject();
            ret.put(STATUS, SUCCESS);
            ret.put(RESPONSE, objectMapper.writeValueAsString(list));
            call.success(ret);
        } catch (Exception e) {
            JSObject ret = new JSObject();
            Log.d(LOG_TAG, "Device Info Exception: " + e.getMessage());
            ret.put(STATUS, FAILURE);
            ret.put(RESPONSE, e.getMessage());
            call.resolve(ret);
        }
    }

    @ActivityCallback
    public void onRCaptureResult(PluginCall call, ActivityResult result) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Log.d(LOG_TAG, "onRCaptureResult: " + result);
            Bundle bundle = result.getData().getExtras();
            Uri uri = bundle.getParcelable(RESPONSE);
            InputStream inputStream = this.getActivity().getApplicationContext().getContentResolver().openInputStream(uri);
            CaptureResponse captureResponse = objectMapper.readValue(inputStream, new TypeReference<CaptureResponse>(){});
            for (CaptureRespDetail bio : captureResponse.getBiometrics()) {
                //On error, even for one attribute fail the RCapture
                if (bio.getError() != null && !"0".equals(bio.getError().getErrorCode())) {
                    Log.d(LOG_TAG, "error code: " + bio.getError().getErrorCode());
                    Log.d(LOG_TAG, "error info: " + bio.getError().getErrorInfo());
                    JSObject ret = new JSObject();
                    ret.put(STATUS, FAILURE);
                    ret.put(RESPONSE, bio.getError().getErrorCode() + bio.getError().getErrorInfo());
                    call.resolve(ret);
                }
                else if (bio.getData() == null || bio.getData().trim().isEmpty()) {
                    Log.d(LOG_TAG, "error code: " + bio.getError().getErrorCode());
                    Log.d(LOG_TAG, "error info: " + bio.getError().getErrorInfo());
                    JSObject ret = new JSObject();
                    ret.put(STATUS, FAILURE);
                    ret.put(RESPONSE, "RCapture failed. Data is null or empty");
                    call.resolve(ret);
                } else {
                    /* 
                    //below code is only to debug the response recvd 
                    Pattern pattern = Pattern.compile("(?<=\\.)(.*)(?=\\.)");
                    Matcher matcher = pattern.matcher(bio.getData());
                    String payload = "";
                    if (matcher.find()) {
                        payload = matcher.group(1);
                        Log.d(LOG_TAG, "payload: " + payload);
                    }
                    byte[] decodedPayload = Base64.getUrlDecoder().decode(payload);
                    CaptureDto captureDto = objectMapper.readValue(decodedPayload, new TypeReference<CaptureDto>(){});
                    Log.d(LOG_TAG, "CaptureDto: " + captureDto);
                    Matcher matcher1 = pattern.matcher(captureDto.getDigitalId());
                    String payload1 = "";
                    if (matcher1.find()) {
                        payload1 = matcher1.group(1);
                        Log.d(LOG_TAG, "payload1: " + payload1);
                    }
                    byte[] decodedPayload1 = Base64.getUrlDecoder().decode(payload1);
                    DigitalId digitalId = objectMapper.readValue(decodedPayload1, new TypeReference<DigitalId>(){});
                    Log.d(LOG_TAG, "DigitalId: " + digitalId);
                    */
                }
            }
            JSObject ret = new JSObject();
            ret.put(STATUS, SUCCESS);
            ret.put(RESPONSE, objectMapper.writeValueAsString(captureResponse));
            call.success(ret);
        } catch (Exception e) {
            JSObject ret = new JSObject();
            Log.d(LOG_TAG, "RCapture Exception: " + e.getMessage());
            ret.put(STATUS, FAILURE);
            ret.put(RESPONSE, e.getMessage());
            call.resolve(ret);
        }
    }

}
