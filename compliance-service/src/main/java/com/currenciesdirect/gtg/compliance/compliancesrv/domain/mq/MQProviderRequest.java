/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq;

import java.io.Serializable;

/**
 * @author manish
 *
 */
public class MQProviderRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer organizationID;
	private String entityType;
	private Integer accountCID;
	private Integer paymentInCID;
	private Integer paymentOutCID; 
	private Object statusJson; //NOSONAR
	private String timestamp;
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

	
	public Integer getAccountCID() {
		return accountCID;
	}
	public void setAccountCID(Integer accountCID) {
		this.accountCID = accountCID;
	}


	public Integer getPaymentInCID() {
		return paymentInCID;
	}
	public void setPaymentInCID(Integer paymentInCID) {
		this.paymentInCID = paymentInCID;
	}
	public Integer getPaymentOutCID() {
		return paymentOutCID;
	}
	public void setPaymentOutCID(Integer paymentOutCID) {
		this.paymentOutCID = paymentOutCID;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public Object getStatusJson() {
		return statusJson;
	}
	public void setStatusJson(Object statusJson) {
		this.statusJson = statusJson;
	}

}
