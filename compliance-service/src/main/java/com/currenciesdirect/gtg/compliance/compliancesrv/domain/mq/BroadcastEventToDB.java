/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq;

import java.io.Serializable;
import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * @author Rajesh
 *
 */
public class BroadcastEventToDB extends ServiceMessage implements IDomain,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orgCode;
	
	private MQEntityTypeEnum entityType;

	private Integer accountId;

    private Integer contactId;
    
    private Integer paymentInId;
    
    private Integer paymentOutId;
    
    private Object statusJson;

    private String deliveryStatus;

    private Timestamp   deliverOn;

    private Timestamp   deliveredOn;

    private Integer createdBy;

    private Timestamp createdOn;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Object getStatusJson() {
		return statusJson;
	}

	public void setStatusJson(Object statusJson) {
		this.statusJson = statusJson;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
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

	public MQEntityTypeEnum getEntityType() {
		return entityType;
	}

	public void setEntityType(MQEntityTypeEnum entityType) {
		this.entityType = entityType;
	}

	
}
