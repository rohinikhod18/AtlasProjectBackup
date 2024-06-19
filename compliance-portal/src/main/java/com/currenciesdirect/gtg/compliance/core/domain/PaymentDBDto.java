package com.currenciesdirect.gtg.compliance.core.domain;

public class PaymentDBDto extends BaseDBDto {

	
	private String tradePaymentId;
	
	private FurtherPaymentDetails furtherpaymentDetails;	
	
	private String alertComplianceLog;
	
	private Boolean isOnQueue;
	
	private String legalEntity;

	public String getTradePaymentId() {
		return tradePaymentId;
	}

	public void setTradePaymentId(String tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	
	public FurtherPaymentDetails getFurtherpaymentDetails() {
		return furtherpaymentDetails;
	}

	public void setFurtherpaymentDetails(FurtherPaymentDetails furtherpaymentDetails) {
		this.furtherpaymentDetails = furtherpaymentDetails;
	}

	public String getAlertComplianceLog() {
		return alertComplianceLog;
	}

	public void setAlertComplianceLog(String alertComplianceLog) {
		this.alertComplianceLog = alertComplianceLog;
	}

	public Boolean getIsOnQueue() {
		return isOnQueue;
	}

	public void setIsOnQueue(Boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	public String getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
	
}
