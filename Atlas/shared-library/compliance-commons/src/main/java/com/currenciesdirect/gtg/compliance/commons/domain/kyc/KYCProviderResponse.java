/*Copyright Currencies Direct Ltd 2015-2016. All rights reserved
worldwide. Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.*/
package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class KYCProviderResponse.
 *
 * @author Manish
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KYCProviderResponse extends ServiceMessageResponse {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;

	/** The org code. */
	private String orgCode;

	/** The source application. */
	private String sourceApplication;

	/** The account SF id. */
	private String accountSFId;

	/** The contact response. */
	private List<KYCContactResponse> contactResponse = new ArrayList<>();

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
	 * @param status
	 *            the new status
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @param orgCode
	 *            the new org code
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
	 * @param sourceApplication
	 *            the new source application
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
	 * Sets the account id.
	 *
	 * @param accountSFId
	 *            the new account id
	 */
	public void setAccountId(String accountSFId) {
		this.accountSFId = accountSFId;
	}

	/**
	 * Gets the contact response.
	 *
	 * @return the contact response
	 */
	public List<KYCContactResponse> getContactResponse() {
		return contactResponse;
	}

	/**
	 * Sets the contact response.
	 *
	 * @param contactResponse
	 *            the new contact response
	 */
	public void setContactResponse(List<KYCContactResponse> contactResponse) {
		this.contactResponse = contactResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KYCProviderResponse [status=" + status + ", orgCode=" + orgCode + ", sourceApplication="
				+ sourceApplication + ", accountSFId=" + accountSFId + ", errorCode=" + errorCode
				+ ", errorDescription=" + errorDescription + ", contactResponse=" + contactResponse + "]";
	}

	/**
	 * Gets the contact response by id.
	 *
	 * @param contactID
	 *            the contact ID
	 * @return the contact response by id
	 */
	public KYCContactResponse getContactResponseById(Integer contactID) {
		for (KYCContactResponse kycResponse : contactResponse) {
			if (kycResponse.getId().equals(contactID))
				return kycResponse;
		}
		return null;
	}

}
