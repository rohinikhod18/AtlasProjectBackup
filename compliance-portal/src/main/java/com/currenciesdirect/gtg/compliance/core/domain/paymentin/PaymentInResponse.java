package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PaymentInResponse {

private PaymentComplianceStatus status;
	
	private Integer tradePaymentID;	
	
	private String tradeContractNumber;
	
	private String responseCode;
	
	private String responseDescription;
	
	private String orgCode;

	@JsonProperty(value="osr_id")
	private String osrID;

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public PaymentComplianceStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentComplianceStatus status) {
		this.status = status;
	}


	public Integer getTradePaymentID() {
		return tradePaymentID;
	}

	public void setTradePaymentID(Integer tradePaymentID) {
		this.tradePaymentID = tradePaymentID;
	}

	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}


	public String getOrgCode() {
		return orgCode;
	}

	
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the osrID
	 */
	public String getOsrID() {
		return osrID;
	}

	/**
	 * @param osrID the osrID to set
	 */
	@JsonProperty(value="osr_id")
	public void setOsrID(String osrID) {
		this.osrID = osrID;
	}

}
