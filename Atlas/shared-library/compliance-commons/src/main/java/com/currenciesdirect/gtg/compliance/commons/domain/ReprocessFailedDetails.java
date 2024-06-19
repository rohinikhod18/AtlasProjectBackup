package com.currenciesdirect.gtg.compliance.commons.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ReprocessFailedDetails.
 */
public class ReprocessFailedDetails extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@ApiModelProperty(value = "repeat check of funds in id", required = true)
	private int id;

	/** The trans type. */
	@ApiModelProperty(value = "type of transaction", required = true)
	private String transType;

	/** The batch id. */
	@ApiModelProperty(value = "transaction batch id", required = true)
	private int batchId;

	/** The trans id. */
	@ApiModelProperty(value = "transaction id", required = true)
	private int transId;

	/** The status. */
	@ApiModelProperty(value = "repeat check of funds in status", required = true)
	private String status;

	/** The retry count. */
	@ApiModelProperty(value = "repeat check retry count", required = true)
	private int retryCount;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the trans type.
	 *
	 * @return the transType
	 */
	public String getTransType() {
		return transType;
	}

	/**
	 * Gets the batch id.
	 *
	 * @return the batchId
	 */
	public int getBatchId() {
		return batchId;
	}

	/**
	 * Gets the trans id.
	 *
	 * @return the transId
	 */
	public int getTransId() {
		return transId;
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
	 * Gets the retry count.
	 *
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Sets the trans type.
	 *
	 * @param transType
	 *            the transType to set
	 */
	public void setTransType(String transType) {
		this.transType = transType;
	}

	/**
	 * Sets the batch id.
	 *
	 * @param batchId
	 *            the batchId to set
	 */
	public void setBatchId(int batchId) {
		this.batchId = batchId;
	}

	/**
	 * Sets the trans id.
	 *
	 * @param transId
	 *            the transId to set
	 */
	public void setTransId(int transId) {
		this.transId = transId;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Sets the retry count.
	 *
	 * @param retryCount
	 *            the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

}
