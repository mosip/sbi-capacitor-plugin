package io.mosip.sbi.capacitor.plugin.model;

public class InfoResponse {

    private String deviceInfo;
    private ErrorDto error;

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public ErrorDto getError() {
        return error;
    }

    public void setError(ErrorDto error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "InfoResponse [deviceIfo=" + deviceInfo + ", error=" + error + "]";
    }
}
