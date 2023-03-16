package io.mosip.sbi.capacitor.plugin.model;

import java.util.Arrays;

public class CaptureBioDetail {

	private String type;
	private int count;
	private String[] bioSubType;
	private String[] exception;
	private int requestedScore;
	private String deviceId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String[] getBioSubType() {
		return bioSubType;
	}

	public void setBioSubType(String[] bioSubType) {
		this.bioSubType = bioSubType;
	}

	public String[] getException() {
		return exception;
	}

	public void setException(String[] exception) {
		this.exception = exception;
	}

	public int getRequestedScore() {
		return requestedScore;
	}

	public void setRequestedScore(int requestedScore) {
		this.requestedScore = requestedScore;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceSubId() {
		return deviceSubId;
	}

	public void setDeviceSubId(String deviceSubId) {
		this.deviceSubId = deviceSubId;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	private String deviceSubId;
	private String previousHash;

	@Override
	public String toString() {
		return "CaptureBioDetail [type=" + type + ", count=" + count + ", bioSubType=" + Arrays.toString(bioSubType)
				+ ", exception=" + Arrays.toString(exception) + ", requestedScore=" + requestedScore + ", deviceId="
				+ deviceId + ", deviceSubId=" + deviceSubId + ", previousHash=" + previousHash + "]";
	}
}
