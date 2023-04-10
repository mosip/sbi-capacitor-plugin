package io.mosip.sbi.capacitor.plugin.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeviceDto {

    private String deviceId;
    private String deviceStatus;
    private String firmware;
    private String certification;
    private String serviceVersion;
    private String[] deviceSubId;
    private String callbackId;
    private String digitalId;
    private String deviceCode;
    private String[] specVersion;
    private String env;
    private String purpose;
    private ErrorDto error;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getFirmware() {
        return firmware;
    }

    public void setFirmware(String firmware) {
        this.firmware = firmware;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getServiceVersion() {
        return serviceVersion;
    }

    public void setServiceVersion(String serviceVersion) {
        this.serviceVersion = serviceVersion;
    }

    public String[] getDeviceSubId() {
        return deviceSubId;
    }

    public void setDeviceSubId(String[] deviceSubId) {
        this.deviceSubId = deviceSubId;
    }

    public String getCallbackId() {
        return callbackId;
    }

    public void setCallbackId(String callbackId) {
        this.callbackId = callbackId;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getDeviceCode() {
        return deviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        this.deviceCode = deviceCode;
    }

    public String[] getSpecVersion() {
        return specVersion;
    }

    public void setSpecVersion(String[] specVersion) {
        this.specVersion = specVersion;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }

    @Override
	public String toString() {
		return "DeviceDto [deviceId=" + deviceId + ", deviceStatus=" + deviceStatus + ", firmware=" + firmware
				+ ", certification=" + certification + ", serviceVersion=" + serviceVersion + ", deviceSubId="
				+ Arrays.toString(deviceSubId) + ", callbackId=" + callbackId + ", digitalId=" + digitalId
				+ ", deviceCode=" + deviceCode + ", specVersion=" + Arrays.toString(specVersion) + ", env=" + env
				+ ", purpose=" + purpose + ", error=" + error + "]";
	}
}
