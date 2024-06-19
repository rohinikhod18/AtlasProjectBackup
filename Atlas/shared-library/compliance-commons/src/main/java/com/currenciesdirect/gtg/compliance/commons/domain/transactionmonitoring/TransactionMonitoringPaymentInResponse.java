package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

/**
 * The Class TransactionMonitoringPaymentInResponse.
 */
public class TransactionMonitoringPaymentInResponse extends BaseResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	private Integer id;

	/** The status. */
	private String status;

	/** The correlation id. */
	private String correlationId;
	
	/** The transaction monitoring funds provider response. */
	private TransactionMonitoringFundsProviderResponse transactionMonitoringFundsProviderResponse;
	
	/** The http status. */
	private Integer httpStatus;
	
	/** The payment status. */
	private String paymentStatus;
	
	/** The error desc. */
	private String errorDesc;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the transaction monitoring funds provider response.
	 *
	 * @return the transaction monitoring funds provider response
	 */
	public TransactionMonitoringFundsProviderResponse getTransactionMonitoringFundsProviderResponse() {
		return transactionMonitoringFundsProviderResponse;
	}

	/**
	 * Sets the transaction monitoring funds provider response.
	 *
	 * @param transactionMonitoringFundsProviderResponse the new transaction monitoring funds provider response
	 */
	public void setTransactionMonitoringFundsProviderResponse(TransactionMonitoringFundsProviderResponse transactionMonitoringFundsProviderResponse) {
		this.transactionMonitoringFundsProviderResponse = transactionMonitoringFundsProviderResponse;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public Integer getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus the new http status
	 */
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
}
