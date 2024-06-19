package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;

/**
 * The Class TransactionMonitoringMQServiceResponse.
 */
public class TransactionMonitoringMQServiceResponse extends ServiceMessageResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The transaction monitoring signup response. */
	private TransactionMonitoringSignupResponse transactionMonitoringSignupResponse;
	
	/** The transaction monitoring payment in response. */
	private TransactionMonitoringPaymentInResponse transactionMonitoringPaymentInResponse;
	
	/** The transaction monitoring payment out response. */
	private TransactionMonitoringPaymentOutResponse transactionMonitoringPaymentOutResponse;

	/**
	 * Gets the transaction monitoring signup response.
	 *
	 * @return the transaction monitoring signup response
	 */
	public TransactionMonitoringSignupResponse getTransactionMonitoringSignupResponse() {
		return transactionMonitoringSignupResponse;
	}

	/**
	 * Sets the transaction monitoring signup response.
	 *
	 * @param transactionMonitoringSignupResponse the new transaction monitoring signup response
	 */
	public void setTransactionMonitoringSignupResponse(
			TransactionMonitoringSignupResponse transactionMonitoringSignupResponse) {
		this.transactionMonitoringSignupResponse = transactionMonitoringSignupResponse;
	}

	/**
	 * Gets the transaction monitoring payment in response.
	 *
	 * @return the transaction monitoring payment in response
	 */
	public TransactionMonitoringPaymentInResponse getTransactionMonitoringPaymentInResponse() {
		return transactionMonitoringPaymentInResponse;
	}

	/**
	 * Sets the transaction monitoring payment in response.
	 *
	 * @param transactionMonitoringPaymentInResponse the new transaction monitoring payment in response
	 */
	public void setTransactionMonitoringPaymentInResponse(
			TransactionMonitoringPaymentInResponse transactionMonitoringPaymentInResponse) {
		this.transactionMonitoringPaymentInResponse = transactionMonitoringPaymentInResponse;
	}

	/**
	 * @return the transactionMonitoringPaymentOutResponse
	 */
	public TransactionMonitoringPaymentOutResponse getTransactionMonitoringPaymentOutResponse() {
		return transactionMonitoringPaymentOutResponse;
	}

	/**
	 * @param transactionMonitoringPaymentOutResponse the transactionMonitoringPaymentOutResponse to set
	 */
	public void setTransactionMonitoringPaymentOutResponse(
			TransactionMonitoringPaymentOutResponse transactionMonitoringPaymentOutResponse) {
		this.transactionMonitoringPaymentOutResponse = transactionMonitoringPaymentOutResponse;
	}
	
	

}
