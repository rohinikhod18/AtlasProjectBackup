/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;

/**
 * @author manish
 *
 */
public class PaymentResponse {

	
	private PaymentComplianceStatus status;
	
	private String tradePaymentID;	
	
	private String tradeContractNumber;
	
	private String responseCode;
	
	private String responseDescription;


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


	public String getTradePaymentID() {
		return tradePaymentID;
	}

	public void setTradePaymentID(String tradePaymentID) {
		this.tradePaymentID = tradePaymentID;
	}

	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

}
