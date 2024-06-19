package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 * The Class TransactionMonitoringAccountSignupResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMonitoringAccountSignupResponse extends ServiceMessageResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer accountId;
	
	/** The http status. */
	private Integer httpStatus;
	
	/** The Status. */
	private String status;
	
	/** The error desc. */
	private String errorDesc;
	
	/** The transaction monitoring account provider response. */
	private TransactionMonitoringAccountProviderResponse transactionMonitoringAccountProviderResponse;

	/**
	 * Gets the account id.
	 *
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the httpStatus
	 */
	public Integer getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
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

	/**
	 * @return the errorDesc
	 */
	public String getErrorDesc() {
		return errorDesc;
	}

	/**
	 * @param errorDesc the errorDesc to set
	 */
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}

	/**
	 * Gets the transaction monitoring account provider response.
	 *
	 * @return the transactionMonitoringAccountProviderResponse
	 */
	public TransactionMonitoringAccountProviderResponse getTransactionMonitoringAccountProviderResponse() {
		return transactionMonitoringAccountProviderResponse;
	}

	/**
	 * Sets the transaction monitoring account provider response.
	 *
	 * @param transactionMonitoringAccountProviderResponse the transactionMonitoringAccountProviderResponse to set
	 */
	public void setTransactionMonitoringAccountProviderResponse(
			TransactionMonitoringAccountProviderResponse transactionMonitoringAccountProviderResponse) {
		this.transactionMonitoringAccountProviderResponse = transactionMonitoringAccountProviderResponse;
	}
	
}
