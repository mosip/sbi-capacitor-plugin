package io.mosip.sbi.capacitor.plugin.model;

import java.util.List;

public class RCaptureResponse {

	private List<RCaptureRespDetail> biometrics;

	public List<RCaptureRespDetail> getBiometrics() {
		return biometrics;
	}

	public void setBiometrics(List<RCaptureRespDetail> biometrics) {
		this.biometrics = biometrics;
	}

	@Override
	public String toString() {
		return "RCaptureResponse [biometrics=" + biometrics + "]";
	}

}
