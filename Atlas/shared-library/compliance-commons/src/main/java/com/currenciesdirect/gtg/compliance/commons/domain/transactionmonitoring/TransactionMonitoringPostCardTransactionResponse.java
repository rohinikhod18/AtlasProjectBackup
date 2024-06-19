package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;

public class TransactionMonitoringPostCardTransactionResponse extends ServiceMessageResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The http status. */
	private Integer httpStatus;
	
	/** The Status. */
	private String status;

	/**
	 * @return the httpStatus
	 */
	public Integer getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

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
	
}
