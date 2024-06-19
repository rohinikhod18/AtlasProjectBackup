package com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FundsOutBulkRecheckResponse.
 */
public class BulkRecheckResponse extends ServiceMessageResponse {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(value = "status")
	/** The status. */
	private String status;
	
	@JsonProperty(value = "tradePaymentId")
	/** The trade payment ID. */
	private Integer tradePaymentID;

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
	
}
