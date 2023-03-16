package io.mosip.sbi.capacitor.plugin.model;

import java.util.List;

public class CaptureRequest {

    private String env;
    private String purpose;
    private String specVersion;
    private int timeout;
    private String captureTime;
    private String transactionId;
    private List<CaptureBioDetail> bio;

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
	public String getSpecVersion() {
		return specVersion;
	}
	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getCaptureTime() {
		return captureTime;
	}
	public void setCaptureTime(String captureTime) {
		this.captureTime = captureTime;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public List<CaptureBioDetail> getBio() {
		return bio;
	}
	public void setBio(List<CaptureBioDetail> bio) {
		this.bio = bio;
	}
	@Override
	public String toString() {
		return "CaptureRequest [env=" + env + ", purpose=" + purpose + ", specVersion=" + specVersion + ", timeout="
				+ timeout + ", captureTime=" + captureTime + ", transactionId=" + transactionId + ", bio=" + bio + "]";
	}
}
