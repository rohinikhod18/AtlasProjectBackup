/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class InternalServiceRequest.
 *
 * @author manish
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class InternalServiceRequest extends ServiceMessage implements Serializable,IRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The searchdata. */
	private List<InternalServiceRequestData> searchdata;

	/** The account SF id. */
	private String accountSFId;

	/** The org code. */
	private String orgCode;

	/** The source application. */
	private String sourceApplication;

	/** The is all check perform. */
	private Boolean isAllCheckPerform;

	/** The is onlyBlacklistCheckPerform. */
	private Boolean onlyBlacklistCheckPerform = Boolean.FALSE;

	/** The is ip eligible. */
	private Boolean isIpEligible = Boolean.TRUE;

	/** The request type. */
	private String requestType;

	/* The blacklist data type. */
	private String blacklistDataType;

	/* The blacklist request.*/
	private BlacklistRequest blacklistRequest;

	//AT-3830
	/** The is fraud sight eligible. */
	private Boolean isFraudSightEligible;
	
	private Boolean isRgDataExists;
	/**
	* Gets the blacklist data type.
	*
	* @return the blacklist data type
	*/
	public String getBlacklistDataType() {
	return blacklistDataType;
	}

	/**
	* Sets the blacklist data type.
	*
	* @param blacklistDataType
	* the new blacklist data type
	*/
	public void setBlacklistDataType(String blacklistDataType) {
	this.blacklistDataType = blacklistDataType;
	}

	/**
	* Gets the blacklist request.
	*
	* @return the blacklist request
	*/
	public BlacklistRequest getBlacklistRequest() {
	return blacklistRequest;
	}

	/**
	* Sets the blacklist request.
	*
	* @param blacklistRequest
	* the new blacklist request
	*/
	public void setBlacklistRequest(BlacklistRequest blacklistRequest) {
	this.blacklistRequest = blacklistRequest;
	}





	/**
	 * Gets the only blacklist check perform.
	 *
	 * @return the only blacklist check perform
	 */
	public Boolean getOnlyBlacklistCheckPerform() {
		return onlyBlacklistCheckPerform;
	}

	/**
	 * Sets the only blacklist check perform.
	 *
	 * @param onlyBlacklistCheckPerform
	 *            the new only blacklist check perform
	 */
	public void setOnlyBlacklistCheckPerform(Boolean onlyBlacklistCheckPerform) {
		this.onlyBlacklistCheckPerform = onlyBlacklistCheckPerform;
	}

	/**
	 * Gets the searchdata.
	 *
	 * @return the searchdata
	 */
	public List<InternalServiceRequestData> getSearchdata() {
		return searchdata;
	}

	/**
	 * Sets the searchdata.
	 *
	 * @param searchdata
	 *            the new searchdata
	 */
	public void setSearchdata(List<InternalServiceRequestData> searchdata) {
		this.searchdata = searchdata;
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
	 * @param accountSFId
	 *            the new account SF id
	 */
	public void setAccountSFId(String accountSFId) {
		this.accountSFId = accountSFId;
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
	 * Gets the checks if is all check perform.
	 *
	 * @return the checks if is all check perform
	 */
	public Boolean getIsAllCheckPerform() {
		return isAllCheckPerform;
	}

	/**
	 * Sets the checks if is all check perform.
	 *
	 * @param isAllCheckPerform
	 *            the new checks if is all check perform
	 */
	public void setIsAllCheckPerform(Boolean isAllCheckPerform) {
		this.isAllCheckPerform = isAllCheckPerform;
	}

	/**
	 * Gets the checks if is ip eligible.
	 *
	 * @return the checks if is ip eligible
	 */
	public Boolean getIsIpEligible() {
		return isIpEligible;
	}

	/**
	 * Sets the checks if is ip eligible.
	 *
	 * @param isIpEligible
	 *            the new checks if is ip eligible
	 */
	public void setIsIpEligible(Boolean isIpEligible) {
		this.isIpEligible = isIpEligible;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType
	 *            the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public Boolean getIsFraudSightEligible() {
		return isFraudSightEligible;
	}

	public void setIsFraudSightEligible(Boolean isFraudSightEligible) {
		this.isFraudSightEligible = isFraudSightEligible;
	}

	public Boolean getIsRgDataExists() {
		return isRgDataExists;
	}

	public void setIsRgDataExists(Boolean isRgDataExists) {
		this.isRgDataExists = isRgDataExists;
	}
	
}
