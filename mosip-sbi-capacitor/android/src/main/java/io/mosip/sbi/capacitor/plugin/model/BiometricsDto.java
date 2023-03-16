package io.mosip.sbi.capacitor.plugin.model;

public class BiometricsDto {

    private String modality;
    private String bioSubType;
    private String bioValue;
    private String specVersion;
    private boolean isException;
    private String decodedBioResponse;
    private String signature;
    private boolean isForceCaptured;
    private int numOfRetries;
    private double sdkScore;
    private float qualityScore;

    public void setIsException(boolean exception) {
        isException = exception;
    }

    public void setIsForceCaptured(boolean forceCaptured) {
        isForceCaptured = forceCaptured;
    }

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getBioSubType() {
		return bioSubType;
	}

	public void setBioSubType(String bioSubType) {
		this.bioSubType = bioSubType;
	}

	public String getBioValue() {
		return bioValue;
	}

	public void setBioValue(String bioValue) {
		this.bioValue = bioValue;
	}

	public String getSpecVersion() {
		return specVersion;
	}

	public void setSpecVersion(String specVersion) {
		this.specVersion = specVersion;
	}

	public boolean isException() {
		return isException;
	}

	public void setException(boolean isException) {
		this.isException = isException;
	}

	public String getDecodedBioResponse() {
		return decodedBioResponse;
	}

	public void setDecodedBioResponse(String decodedBioResponse) {
		this.decodedBioResponse = decodedBioResponse;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public boolean isForceCaptured() {
		return isForceCaptured;
	}

	public void setForceCaptured(boolean isForceCaptured) {
		this.isForceCaptured = isForceCaptured;
	}

	public int getNumOfRetries() {
		return numOfRetries;
	}

	public void setNumOfRetries(int numOfRetries) {
		this.numOfRetries = numOfRetries;
	}

	public double getSdkScore() {
		return sdkScore;
	}

	public void setSdkScore(double sdkScore) {
		this.sdkScore = sdkScore;
	}

	public float getQualityScore() {
		return qualityScore;
	}

	public void setQualityScore(float qualityScore) {
		this.qualityScore = qualityScore;
	}
}
