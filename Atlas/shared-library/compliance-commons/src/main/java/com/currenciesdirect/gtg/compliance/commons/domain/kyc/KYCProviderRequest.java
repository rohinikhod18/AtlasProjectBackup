/*Copyright Currencies Direct Ltd 2015-2016. All rights reserved
worldwide. Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.*/
package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * The Class KYCProviderRequest.
 *
 * @author Manish
 */
public class KYCProviderRequest extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
    
    /** The org code. */
    private String orgCode;
    
    /** The contact. */
    private List<KYCContactRequest> contact;
    
    /** The request type. */
    private String requestType;
    
    /** The source application. */
    private String sourceApplication;
    
    /** The account SF id. */
    private String accountSFId;
    
    /** The legal entity. */
    private String legalEntity;
    
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
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the account SF id.
	 *
	 * @return the account SF id
	 */
	public String getAccountSFId() {
		return accountSFId;
	}

	/**
	 * Sets the account SF id.
	 *
	 * @param accountSFId the new account SF id
	 */
	public void setAccountSFId(String accountSFId) {
		this.accountSFId = accountSFId;
	}

	/**
	 * Gets the contact.
	 *
	 * @return the contact
	 */
	public List<KYCContactRequest> getContact() {
		return contact;
	}

	/**
	 * Sets the contact.
	 *
	 * @param contact the new contact
	 */
	public void setContact(List<KYCContactRequest> contact) {
		this.contact = contact;
	}

	/**
	 * Gets the contact request by ID.
	 *
	 * @param id the id
	 * @return the contact request by ID
	 */
	public KYCContactRequest getContactRequestByID(Integer id){
		if(contact != null){
			for(KYCContactRequest c:contact){
				if(c.getId().equals(id))
						return c;
			}
		}
		return null;
	}
	
	/**
	 * @return the legalEntity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KYCProviderRequest [orgCode=" + orgCode + ", contact=" + contact + ", requestType=" + requestType
				+ ", sourceApplication=" + sourceApplication + ", accountSFId=" + accountSFId + "]";
	}

}
