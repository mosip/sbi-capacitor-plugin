package io.mosip.capacitor.plugin;

import io.mosip.sbi.capacitor.plugin.model.DeviceDto;
import io.mosip.sbi.capacitor.plugin.model.ErrorDto;
import io.mosip.sbi.capacitor.plugin.model.DiscoverRequest;
import io.mosip.sbi.capacitor.plugin.model.InfoResponse;

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

@CapacitorPlugin(name = "MosipSbiCapacitor")
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

    ObjectMapper objectMapper = new ObjectMapper();

    @PluginMethod
    public void startActivity(PluginCall call) {
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
                    final String extraKey = data.has("extraKey") ? data.getString("extraKey") : "";
                    Log.d(LOG_TAG, "extraKey: " + extraKey);
                    String extraValue = data.has("extraValue") ? data.getString("extraValue") : "";
                    Log.d(LOG_TAG, "extraValue: " + extraValue);
                    final Intent intent = new Intent(action);
                    DiscoverRequest discoverRequest = new DiscoverRequest();
                    discoverRequest.setType(extraValue);
                    byte[] extraValueBytes = objectMapper.writeValueAsBytes(discoverRequest);
                    Log.d(LOG_TAG, "extraValue byte[]: " + extraValueBytes);
                    intent.putExtra(extraKey, extraValueBytes);
                    Log.d(LOG_TAG, "intent: " + intent);
                    startActivityForResult(call, intent, "onDiscoverResult");
                }
                // device info
                if (methodType.equals(SBI_METHOD_DEVICE_INFO)) {
                    final Intent intent = new Intent(action);
                    startActivityForResult(call, intent, "onDeviceInfoResult");
                }
            } catch (JsonProcessingException jsonError) {
                call.error(jsonError.getMessage());
            } catch (ActivityNotFoundException activityError) {
                call.error(activityError.getMessage());
            }
        } else {
            call.error("No action provided");
        }
    }

    @ActivityCallback
    public void onDiscoverResult(PluginCall call, ActivityResult result) {
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
            Log.d(LOG_TAG, "Exception: " + e.getMessage());
            ret.put(STATUS, FAILURE);
            ret.put(RESPONSE, e.getMessage());
            call.resolve(ret);
        }
    }

    @ActivityCallback
    public void onDeviceInfoResult(PluginCall call, ActivityResult result) {
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
            InfoResponse infoResponse = list.get(0);
            Log.d(LOG_TAG, "infoResponse: " + infoResponse);
            if (infoResponse.getError() != null && !"0".equals(infoResponse.getError().getErrorCode())) {
                Log.d(LOG_TAG, "error code: " + infoResponse.getError().getErrorCode());
                Log.d(LOG_TAG, "error info: " + infoResponse.getError().getErrorInfo());
                JSObject ret = new JSObject();
                ret.put(STATUS, FAILURE);
                ret.put(RESPONSE, infoResponse.getError().getErrorCode() + infoResponse.getError().getErrorInfo());
                call.resolve(ret);
            }
            JSObject ret = new JSObject();
            ret.put(STATUS, SUCCESS);
            ret.put(RESPONSE, objectMapper.writeValueAsString(infoResponse));
            call.success(ret);
        } catch (Exception e) {
            JSObject ret = new JSObject();
            Log.d(LOG_TAG, "Exception: " + e.getMessage());
            ret.put(STATUS, FAILURE);
            ret.put(RESPONSE, e.getMessage());
            call.resolve(ret);
        }
    }

}
