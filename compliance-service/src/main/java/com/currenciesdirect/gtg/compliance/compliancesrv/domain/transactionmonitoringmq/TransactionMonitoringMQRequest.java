package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import java.io.Serializable;
import java.util.Date;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.IDomain;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class TransactionMonitoringMQRequest.
 */
public class TransactionMonitoringMQRequest extends ServiceMessage implements IDomain, Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@ApiModelProperty(value = "id", required = true)
	private Integer id;
	
	/** The org code. */
	@ApiModelProperty(value = "org code", required = true)
	private String orgCode;
	
	/** The request type. */
	@ApiModelProperty(value = "request type", required = true)
	private String requestType;
	
	/** The account ID. */
	@ApiModelProperty(value = "account id", required = true)
	private Integer accountID;
	
	/** The contact ID. */
	@ApiModelProperty(value = "contact id", required = true)
	private Integer contactID;
	
	/** The payment in ID. */
	@ApiModelProperty(value = "payment in id", required = true)
	private Integer paymentInID;
	
	/** The payment out ID. */
	@ApiModelProperty(value = "payment out id", required = true)
	private Integer paymentOutID;
	
	/** The request json. */
	@ApiModelProperty(value = "request json", required = true)
	private Object requestJson;
	
	/** The timestamp. */
	@ApiModelProperty(value = "timestamp", required = true)
	private Date timestamp;
	
	/** The is present. */
	@ApiModelProperty(value = "isPresent", required = true)
	private Integer isPresent;
	
	/** The created by. */
	@ApiModelProperty(value = "created by", required = true)
	private Integer createdBy;

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
	 * Gets the checks if is present.
	 *
	 * @return the checks if is present
	 */
	public Integer getIsPresent() {
		return isPresent;
	}

	/**
	 * Sets the checks if is present.
	 *
	 * @param isPresent the new checks if is present
	 */
	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

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
	 * Gets the account ID.
	 *
	 * @return the account ID
	 */
	public Integer getAccountID() {
		return accountID;
	}

	/**
	 * Sets the account ID.
	 *
	 * @param accountID the new account ID
	 */
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	/**
	 * Gets the contact ID.
	 *
	 * @return the contact ID
	 */
	public Integer getContactID() {
		return contactID;
	}

	/**
	 * Sets the contact ID.
	 *
	 * @param contactID the new contact ID
	 */
	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}

	/**
	 * Gets the payment in ID.
	 *
	 * @return the payment in ID
	 */
	public Integer getPaymentInID() {
		return paymentInID;
	}

	/**
	 * Sets the payment in ID.
	 *
	 * @param paymentInID the new payment in ID
	 */
	public void setPaymentInID(Integer paymentInID) {
		this.paymentInID = paymentInID;
	}

	/**
	 * Gets the payment out ID.
	 *
	 * @return the payment out ID
	 */
	public Integer getPaymentOutID() {
		return paymentOutID;
	}

	/**
	 * Sets the payment out ID.
	 *
	 * @param paymentOutID the new payment out ID
	 */
	public void setPaymentOutID(Integer paymentOutID) {
		this.paymentOutID = paymentOutID;
	}

	/**
	 * Gets the request json.
	 *
	 * @return the request json
	 */
	public Object getRequestJson() {
		return requestJson;
	}

	/**
	 * Sets the request json.
	 *
	 * @param requestJson the new request json
	 */
	public void setRequestJson(Object requestJson) {
		this.requestJson = requestJson;
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets the timestamp.
	 *
	 * @param timestamp the new timestamp
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
