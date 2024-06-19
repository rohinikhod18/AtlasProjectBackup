package com.currenciesdirect.gtg.compliance.core.blacklist.payrefport;

public class ProviderBlacklistPayrefContactResponse {
	private static final long serialVersionUID = 1L;

	private String paymentReference;

	private String requestId;

	private String sanctionText;

	private int tokenSetRatio;

	private String status;

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSanctionText() {
		return sanctionText;
	}

	public void setSanctionText(String sanctionText) {
		this.sanctionText = sanctionText;
	}

	public int getTokenSetRatio() {
		return tokenSetRatio;
	}

	public void setTokenSetRatio(int tokenSetRatio) {
		this.tokenSetRatio = tokenSetRatio;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
