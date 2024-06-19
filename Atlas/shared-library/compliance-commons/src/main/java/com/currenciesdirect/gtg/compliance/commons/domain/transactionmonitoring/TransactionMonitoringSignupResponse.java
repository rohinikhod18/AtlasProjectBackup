package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;

/**
 * The Class TransactionMonitoringSignupResponse.
 */
public class TransactionMonitoringSignupResponse extends ServiceMessageResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The transaction monitoring account signup response. */
	private TransactionMonitoringAccountSignupResponse transactionMonitoringAccountSignupResponse;

	/** The transaction monitoring contact signup response. */
	private List<TransactionMonitoringContactSignupResponse> transactionMonitoringContactSignupResponse;


	/**
	 * Gets the transaction monitoring account signup response.
	 *
	 * @return the transaction monitoring account signup response
	 */
	public TransactionMonitoringAccountSignupResponse getTransactionMonitoringAccountSignupResponse() {
		return transactionMonitoringAccountSignupResponse;
	}

	/**
	 * Sets the transaction monitoring account signup response.
	 *
	 * @param transactionMonitoringAccountSignupResponse the new transaction monitoring account signup response
	 */
	public void setTransactionMonitoringAccountSignupResponse(
			TransactionMonitoringAccountSignupResponse transactionMonitoringAccountSignupResponse) {
		this.transactionMonitoringAccountSignupResponse = transactionMonitoringAccountSignupResponse;
	}

	/**
	 * Gets the transaction monitoring contact signup response.
	 *
	 * @return the transaction monitoring contact signup response
	 */
	public List<TransactionMonitoringContactSignupResponse> getTransactionMonitoringContactSignupResponse() {
		return transactionMonitoringContactSignupResponse;
	}

	/**
	 * Sets the transaction monitoring contact signup response.
	 *
	 * @param transactionMonitoringContactSignupResponse the new transaction monitoring contact signup response
	 */
	public void setTransactionMonitoringContactSignupResponse(
			List<TransactionMonitoringContactSignupResponse> transactionMonitoringContactSignupResponse) {
		this.transactionMonitoringContactSignupResponse = transactionMonitoringContactSignupResponse;
	}

}
