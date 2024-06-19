/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;

/**
 * @author Rajesh
 *
 */
public class BroadcastEventToDB {

	private String orgCode;
	
	private String entityType;

	private Integer accountId;

    private Integer contactId;
    
    private Integer paymentInId;
    
    private Integer paymentOutId;
    
    private String statusJson;

    private String deliveryStatus;

    private Timestamp   deliverOn;

    private Timestamp   deliveredOn;

    private String createdBy;

    private Timestamp createdOn;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getStatusJson() {
		return statusJson;
	}

	public void setStatusJson(String statusJson) {
		this.statusJson = statusJson;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getDeliverOn() {
		return deliverOn;
	}

	public void setDeliverOn(Timestamp deliverOn) {
		this.deliverOn = deliverOn;
	}

	public Timestamp getDeliveredOn() {
		return deliveredOn;
	}

	public void setDeliveredOn(Timestamp deliveredOn) {
		this.deliveredOn = deliveredOn;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public String getEntityType() {
		return entityType;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public Integer getContactId() {
		return contactId;
	}

	public Integer getPaymentInId() {
		return paymentInId;
	}

	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	public void setPaymentInId(Integer paymentInId) {
		this.paymentInId = paymentInId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	
}
