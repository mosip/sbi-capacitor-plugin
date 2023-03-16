package io.mosip.sbi.capacitor.plugin.model;

import java.util.List;

public class CaptureResponse {

	private List<CaptureRespDetail> biometrics;

	public List<CaptureRespDetail> getBiometrics() {
		return biometrics;
	}

	public void setBiometrics(List<CaptureRespDetail> biometrics) {
		this.biometrics = biometrics;
	}

	@Override
	public String toString() {
		return "CaptureResponse [biometrics=" + biometrics + "]";
	}

}
