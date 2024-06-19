/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq;

import java.io.Serializable;
import java.util.Date;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * @author manish
 *
 */
public class BroadCastQueueRequest extends ServiceMessage implements IDomain,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer organizationID;
	private String entityType;
	private Integer accountID;
	private Integer contactID;
	private Integer paymentInID;
	private Integer paymentOutID; 
	private Object statusJson; //NOSONAR
	private Date timestamp;
	
	public Object getStatusJson() {
		return statusJson;
	}
	public void setStatusJson(Object statusJson) {
		this.statusJson = statusJson;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOrganizationID() {
		return organizationID;
	}
	public void setOrganizationID(Integer organizationID) {
		this.organizationID = organizationID;
	}
	public String getEntityType() {
		return entityType;
	}
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public Integer getAccountID() {
		return accountID;
	}
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}
	public Integer getContactID() {
		return contactID;
	}
	public void setContactID(Integer contactID) {
		this.contactID = contactID;
	}
	public Integer getPaymentInID() {
		return paymentInID;
	}
	public void setPaymentInID(Integer paymentInID) {
		this.paymentInID = paymentInID;
	}
	public Integer getPaymentOutID() {
		return paymentOutID;
	}
	public void setPaymentOutID(Integer paymentOutID) {
		this.paymentOutID = paymentOutID;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}


}
