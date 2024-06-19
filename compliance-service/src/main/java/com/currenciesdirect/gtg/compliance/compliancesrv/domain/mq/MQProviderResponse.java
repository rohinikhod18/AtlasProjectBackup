/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author manish
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class MQProviderResponse implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String 	id;
	private String  errorCode; 
    private String  errorDesc; 
    private String  status; 
    private String  accountCID; 
    private String  contactID; 
    private String  paymentOutCID; 
    private String  paymentInCID;
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getContactID() {
		return contactID;
	}
	public void setContactID(String contactID) {
		this.contactID = contactID;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAccountCID() {
		return accountCID;
	}
	public void setAccountCID(String accountCID) {
		this.accountCID = accountCID;
	}
	public String getPaymentOutCID() {
		return paymentOutCID;
	}
	public void setPaymentOutCID(String paymentOutCID) {
		this.paymentOutCID = paymentOutCID;
	}
	public String getPaymentInCID() {
		return paymentInCID;
	}
	public void setPaymentInCID(String paymentInCID) {
		this.paymentInCID = paymentInCID;
	}
}
