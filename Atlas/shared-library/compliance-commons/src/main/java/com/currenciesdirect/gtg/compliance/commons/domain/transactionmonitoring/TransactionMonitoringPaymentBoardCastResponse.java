package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionMonitoringPaymentBoardCastResponse {

	private String status;
	
	private Integer tradePaymentID;	
	
	private String tradeContractNumber;
	
	private String responseCode;
	
	private String responseDescription;
	
	private String orgCode;

	@JsonProperty(value="osr_id")
	private String osrID;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the tradePaymentID
	 */
	public Integer getTradePaymentID() {
		return tradePaymentID;
	}

	/**
	 * @param tradePaymentID the tradePaymentID to set
	 */
	public void setTradePaymentID(Integer tradePaymentID) {
		this.tradePaymentID = tradePaymentID;
	}

	/**
	 * @return the tradeContractNumber
	 */
	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	/**
	 * @param tradeContractNumber the tradeContractNumber to set
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * @param responseDescription the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
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
	public void setOsrID(String osrID) {
		this.osrID = osrID;
	}
	
	
	
}
