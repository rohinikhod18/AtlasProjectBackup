package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * The Class TransactionMonitoringMQServiceRequest.
 */
public class TransactionMonitoringMQServiceRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The request type. */
	private String requestType;

	/** The org code. */
	private String orgCode;

	/** The created by. */
	private Integer createdBy;

	/** The transaction monitoring signup request. */
	private TransactionMonitoringSignupRequest transactionMonitoringSignupRequest;
	
	/** The transaction monitoring payments in request. */
	private TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest;
	
	/** The transaction monitoring payments out request. */
	private TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest;

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the transaction monitoring signup request.
	 *
	 * @return the transaction monitoring signup request
	 */
	public TransactionMonitoringSignupRequest getTransactionMonitoringSignupRequest() {
		return transactionMonitoringSignupRequest;
	}

	/**
	 * Sets the transaction monitoring signup request.
	 *
	 * @param transactionMonitoringSignupRequest the new transaction monitoring signup request
	 */
	public void setTransactionMonitoringSignupRequest(
			TransactionMonitoringSignupRequest transactionMonitoringSignupRequest) {
		this.transactionMonitoringSignupRequest = transactionMonitoringSignupRequest;
	}

	/**
	 * Gets the transaction monitoring payments in request.
	 *
	 * @return the transaction monitoring payments in request
	 */
	public TransactionMonitoringPaymentsInRequest getTransactionMonitoringPaymentsInRequest() {
		return transactionMonitoringPaymentsInRequest;
	}

	/**
	 * Sets the transaction monitoring payments in request.
	 *
	 * @param transactionMonitoringPaymentsInRequest the new transaction monitoring payments in request
	 */
	public void setTransactionMonitoringPaymentsInRequest(
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest) {
		this.transactionMonitoringPaymentsInRequest = transactionMonitoringPaymentsInRequest;
	}

	/**
	 * @return the transactionMonitoringPaymentsOutRequest
	 */
	public TransactionMonitoringPaymentsOutRequest getTransactionMonitoringPaymentsOutRequest() {
		return transactionMonitoringPaymentsOutRequest;
	}

	/**
	 * @param transactionMonitoringPaymentsOutRequest the transactionMonitoringPaymentsOutRequest to set
	 */
	public void setTransactionMonitoringPaymentsOutRequest(
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest) {
		this.transactionMonitoringPaymentsOutRequest = transactionMonitoringPaymentsOutRequest;
	}

	
	
}
