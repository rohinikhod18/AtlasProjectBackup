package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Account;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;

/**
 * The Class Account.
 */
public class AccountWrapper extends Account{

	private static final long serialVersionUID = 1L;

	/** The estim trans value. */
	private String estimTransValue = Constants.DASH_DETAILS_PAGE;

	/** The org code. */
	private String orgCode;

	 /** The browser type. */
	private String browserType;

	/** The cookie info. */
	private String cookieInfo;

	/** The refferal text. */
	private String refferalText;

	/** The date of reg. */
	private Timestamp dateOfReg;

	/** The compliance status. */
	private String complianceStatus;
	
	/** The name. */
	private String name;
	
    /** The annual FX requirement. */
	private String annualFXRequirement = Constants.DASH_DETAILS_PAGE;
	
	/** The source lookup. */
	private String sourceLookup;
	
	/** The avg transaction value. */
	private String avgTransactionValue;
	
	
	/** The reg mode. */
	private String regMode;
	
	/** The legal entity. */
	private String legalEntity;

	/** The reg comp. */
	private String regCompleteAccount;
	
	/** The registration in date. */
	private String registrationInDate;
	
	/** The compliance expiry. */
	private String complianceExpiry;
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;
	
	/** The add campaign. */
	private String addCampaign;

	private String referralId;
	
	private String complianceDoneOn;

	/** The account TM flag. */
	private Integer accountTMFlag;
	/**
	 * Gets the estim trans value.
	 *
	 * @return the estim trans value
	 */
	public String getEstimTransValue() {
		return estimTransValue;
	}

	/**
	 * Sets the estim trans value.
	 *
	 * @param estimTransValue
	 *            the new estim trans value
	 */
	public void setEstimTransValue(String estimTransValue) {
		if(!StringUtils.isNullOrEmpty(estimTransValue)) {
			this.estimTransValue = estimTransValue;
		}
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
	 * Gets the browser type.
	 *
	 * @return the browser type
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * Sets the browser type.
	 *
	 * @param browserType
	 *            the new browser type
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * Gets the cookie info.
	 *
	 * @return the cookie info
	 */
	public String getCookieInfo() {
		return cookieInfo;
	}

	/**
	 * Sets the cookie info.
	 *
	 * @param cookieInfo
	 *            the new cookie info
	 */
	public void setCookieInfo(String cookieInfo) {
		this.cookieInfo = cookieInfo;
	}

	/**
	 * Gets the refferal text.
	 *
	 * @return the refferal text
	 */
	public String getRefferalText() {
		return refferalText;
	}

	/**
	 * Sets the refferal text.
	 *
	 * @param refferalText
	 *            the new refferal text
	 */
	public void setRefferalText(String refferalText) {
		this.refferalText = refferalText;
	}

	/**
	 * Gets the date of reg.
	 *
	 * @return the date of reg
	 */
	public Timestamp getDateOfReg() {
		return dateOfReg;
	}

	/**
	 * Sets the date of reg.
	 *
	 * @param dateOfReg
	 *            the new date of reg
	 */
	public void setDateOfReg(Timestamp dateOfReg) {
		this.dateOfReg = dateOfReg;
	}

	/**
	 * Gets the compliance status.
	 *
	 * @return the compliance status
	 */
	public String getComplianceStatus() {
		return complianceStatus;
	}

	/**
	 * Sets the compliance status.
	 *
	 * @param complianceStatus
	 *            the new compliance status
	 */
	public void setComplianceStatus(String complianceStatus) {
		this.complianceStatus = complianceStatus;
	}

	/**
	 * Gets the reg mode.
	 *
	 * @return the reg mode
	 */
	public String getRegMode() {
		return regMode;
	}

	/**
	 * Sets the reg mode.
	 *
	 * @param regMode the new reg mode
	 */
	public void setRegMode(String regMode) {
		this.regMode = regMode;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the annual FX requirement.
	 *
	 * @return the annual FX requirement
	 */
	public String getAnnualFXRequirement() {
		return annualFXRequirement;
	}

	/**
	 * Sets the annual FX requirement.
	 *
	 * @param annualFXRequirement the new annual FX requirement
	 */
	public void setAnnualFXRequirement(String annualFXRequirement) {
		this.annualFXRequirement = annualFXRequirement;
	}

	/**
	 * Gets the source lookup.
	 *
	 * @return the source lookup
	 */
	public String getSourceLookup() {
		return sourceLookup;
	}

	/**
	 * Sets the source lookup.
	 *
	 * @param sourceLookup the new source lookup
	 */
	public void setSourceLookup(String sourceLookup) {
		this.sourceLookup = sourceLookup;
	}

	/**
	 * Gets the avg transaction value.
	 *
	 * @return the avg transaction value
	 */
	public String getAvgTransactionValue() {
		return avgTransactionValue;
	}

	/**
	 * Sets the avg transaction value.
	 *
	 * @param avgTransactionValue the new avg transaction value
	 */
	public void setAvgTransactionValue(String avgTransactionValue) {
		this.avgTransactionValue = avgTransactionValue;
	}

	/**
	 * Gets the legal entity.
	 *
	 * @return the legal entity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * Sets the legal entity.
	 *
	 * @param legalEntity the new legal entity
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	/**
	 * @return the regCompleteAccount
	 */
	public String getRegCompleteAccount() {
		return regCompleteAccount;
	}

	/**
	 * @param regCompleteAccount the regCompleteAccount to set
	 */
	public void setRegCompleteAccount(String regCompleteAccount) {
		this.regCompleteAccount = regCompleteAccount;
	}
	
	/**
	 * Gets registration in date
	 * 
	 * @return the registrationInDate
	 */
	public String getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 *  Sets registration in date
	 * 
	 * @param registrationInDate
	 */
	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
	}
	
	/**
	 * Gets compliance expiry
	 * 
	 * @return the complianceExpiry
	 */
	public String getComplianceExpiry() {
		return complianceExpiry;
	}

	/**
	 * Sets compliance expiry
	 * 
	 * @param complianceExpiry
	 */
	public void setComplianceExpiry(String complianceExpiry) {
		this.complianceExpiry = complianceExpiry;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the addCampaign
	 */
	public String getAddCampaign() {
		return addCampaign;
	}

	/**
	 * @param addCampaign the addCampaign to set
	 */
	public void setAddCampaign(String addCampaign) {
		this.addCampaign = addCampaign;
	}

	/**
	 * @return the referralId
	 */
	public String getReferralId() {
		return referralId;
	}

	/**
	 * @param referralId the referralId to set
	 */
	public void setReferralId(String referralId) {
		this.referralId = referralId;
	}

	/**
	 * @return the complianceDoneOn
	 */
	public String getComplianceDoneOn() {
		return complianceDoneOn;
	}

	/**
	 * @param complianceDoneOn the complianceDoneOn to set
	 */
	public void setComplianceDoneOn(String complianceDoneOn) {
		this.complianceDoneOn = complianceDoneOn;
	}

	/**
	 * @return the accountTMFlag
	 */
	public Integer getAccountTMFlag() {
		return accountTMFlag;
	}

	/**
	 * @param accountTMFlag the accountTMFlag to set
	 */
	public void setAccountTMFlag(Integer accountTMFlag) {
		this.accountTMFlag = accountTMFlag;
	}
	
	
	
}
