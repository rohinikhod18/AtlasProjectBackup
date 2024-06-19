package com.currenciesdirect.gtg.compliance.core.domain;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class BaseFilter.
 */
public class BaseFilter implements IFilter, Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The sort. */
	private Sort sort;

	/** The status. */
	private String[] status;

	/** The keyword. */
	private String keyword;

	/** The user profile. */
	private UserProfile userProfile;

	/** The exclude cust type. */
	private String[] excludeCustType;

	/** The cust type. */
	private String[] custType;

	/** The kyc status. */
	private String[] kycStatus;

	/** The blacklist status. */
	private String[] blacklistStatus;

	/** The sanction status. */
	private String[] sanctionStatus;

	/** The fraugster status. */
	private String[] fraugsterStatus;

	/** The custom check status. */
	private String[] customCheckStatus;

	/** The watch list status. */
	private String[] watchListStatus;

	/** The organisation. */
	private String[] organization;
	
	/** The legal entity. */
	private String[] legalEntity;

	/** The buy currency. */
	private String[] buyCurrency;

	/** The sell currency. */
	private String[] sellCurrency;

	/** The source. */
	private String[] source;

	/** The trans value. */
	private String[] transValue;

	/** The date from. */
	private String dateFrom;

	/** The date to. */
	private String dateTo;

	/** The new or updated record. */
	private String[] newOrUpdatedRecord;

	/** The owner. */
	protected String[] owner;

	/** The watch list reason. */
	private String[] watchListReason;

	/** The date filter type. */
	private String[] dateFilterType;

	/** device id. */
	private String deviceId;

	private String[] onfidoStatus;
	
	/* 1646 changes 
	 * Riskstatus 
	 */
	private String[] riskStatus;

	private String[] fraudSightStatus;

	//Added for AT-4614
	private String[] intuitionStatus;
	
	//Added for AT-4655,AT-4656
	private String[] transactionMonitoringRequest;
	
	/**
	 * Gets the watch list reason.
	 *
	 * @return the watch list reason
	 */
	public String[] getWatchListReason() {
		return watchListReason;
	}

	/**
	 * Sets the watch list reason.
	 *
	 * @param watchListReason
	 *            the new watch list reason
	 */
	public void setWatchListReason(String[] watchListReason) {
		this.watchListReason = watchListReason;
	}

	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public String[] getOwner() {
		return owner;
	}

	/**
	 * Sets the owner.
	 *
	 * @param owner
	 *            the new owner
	 */
	public void setOwner(String[] owner) {
		this.owner = owner;
	}

	/**
	 * Gets the sort.
	 *
	 * @return the sort
	 */
	public Sort getSort() {
		return sort;
	}

	/**
	 * Sets the sort.
	 *
	 * @param sort
	 *            the new sort
	 */
	public void setSort(Sort sort) {
		this.sort = sort;
	}

	/**
	 * Gets the user profile.
	 *
	 * @return the user profile
	 */
	public UserProfile getUserProfile() {
		return userProfile;
	}

	/**
	 * Sets the user profile.
	 *
	 * @param userProfile
	 *            the new user profile
	 */
	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * Gets the keyword.
	 *
	 * @return the keyword
	 */
	public String getKeyword() {
		return keyword;
	}

	/**
	 * Sets the keyword.
	 *
	 * @param keyword
	 *            the new keyword
	 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/**
	 * Gets the exclude cust type.
	 *
	 * @return the excludeCustType
	 */
	public String[] getExcludeCustType() {
		return excludeCustType;
	}

	/**
	 * Sets the exclude cust type.
	 *
	 * @param excludeCustType
	 *            the excludeCustType to set
	 */
	public void setExcludeCustType(String[] excludeCustType) {
		this.excludeCustType = excludeCustType;
	}

	/**
	 * Gets the kyc status.
	 *
	 * @return the kycStatus
	 */
	public String[] getKycStatus() {
		return kycStatus;
	}

	/**
	 * Sets the kyc status.
	 *
	 * @param kycStatus
	 *            the kycStatus to set
	 */
	public void setKycStatus(String[] kycStatus) {
		this.kycStatus = kycStatus;
	}

	/**
	 * Gets the blacklist status.
	 *
	 * @return the blacklistStatus
	 */
	public String[] getBlacklistStatus() {
		return blacklistStatus;
	}

	/**
	 * Sets the blacklist status.
	 *
	 * @param blacklistStatus
	 *            the blacklistStatus to set
	 */
	public void setBlacklistStatus(String[] blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}

	/**
	 * Gets the sanction status.
	 *
	 * @return the sanctionStatus
	 */
	public String[] getSanctionStatus() {
		return sanctionStatus;
	}

	/**
	 * Sets the sanction status.
	 *
	 * @param sanctionStatus
	 *            the sanctionStatus to set
	 */
	public void setSanctionStatus(String[] sanctionStatus) {
		this.sanctionStatus = sanctionStatus;
	}

	/**
	 * Gets the fraugster status.
	 *
	 * @return the fraugsterStatus
	 */
	public String[] getFraugsterStatus() {
		return fraugsterStatus;
	}

	/**
	 * Sets the fraugster status.
	 *
	 * @param fraugsterStatus
	 *            the fraugsterStatus to set
	 */
	public void setFraugsterStatus(String[] fraugsterStatus) {
		this.fraugsterStatus = fraugsterStatus;
	}

	/**
	 * Gets the custom check status.
	 *
	 * @return the customCheckStatus
	 */
	public String[] getCustomCheckStatus() {
		return customCheckStatus;
	}

	/**
	 * Sets the custom check status.
	 *
	 * @param customCheckStatus
	 *            the customCheckStatus to set
	 */
	public void setCustomCheckStatus(String[] customCheckStatus) {
		this.customCheckStatus = customCheckStatus;
	}

	/**
	 * Gets the watch list status.
	 *
	 * @return the watchListStatus
	 */
	public String[] getWatchListStatus() {
		return watchListStatus;
	}

	/**
	 * Sets the watch list status.
	 *
	 * @param watchListStatus
	 *            the watchListStatus to set
	 */
	public void setWatchListStatus(String[] watchListStatus) {
		this.watchListStatus = watchListStatus;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the custType
	 */
	public String[] getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType
	 *            the custType to set
	 */
	public void setCustType(String[] custType) {
		this.custType = custType;
	}

	/**
	 * Gets the date from.
	 *
	 * @return the date from
	 */
	public String getDateFrom() {
		return dateFrom;
	}

	/**
	 * Sets the date from.
	 *
	 * @param dateFrom
	 *            the new date from
	 */
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * Gets the date to.
	 *
	 * @return the date to
	 */
	public String getDateTo() {
		return dateTo;
	}

	/**
	 * Sets the date to.
	 *
	 * @param dateTo
	 *            the new date to
	 */
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * Gets the organisation.
	 *
	 * @return the organisation
	 */
	public String[] getOrganization() {
		return organization;
	}

	/**
	 * Sets the organization.
	 *
	 * @param organization
	 *            the new organization
	 */
	public void setOrganization(String[] organization) {
		this.organization = organization;
	}

	/**
	 * Gets the buy currency.
	 *
	 * @return the buy currency
	 */
	public String[] getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * Sets the buy currency.
	 *
	 * @param buyCurrency
	 *            the new buy currency
	 */
	public void setBuyCurrency(String[] buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * Gets the sell currency.
	 *
	 * @return the sell currency
	 */
	public String[] getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * Sets the sell currency.
	 *
	 * @param sellCurrency
	 *            the new sell currency
	 */
	public void setSellCurrency(String[] sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String[] getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source
	 *            the new source
	 */
	public void setSource(String[] source) {
		this.source = source;
	}

	/**
	 * Gets the trans value.
	 *
	 * @return the trans value
	 */
	public String[] getTransValue() {
		return transValue;
	}

	/**
	 * Sets the trans value.
	 *
	 * @param transValue
	 *            the new trans value
	 */
	public void setTransValue(String[] transValue) {
		this.transValue = transValue;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String[] getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(String[] status) {
		this.status = status;
	}

	/**
	 * Gets the new or updated record.
	 *
	 * @return the new or updated record
	 */
	public String[] getNewOrUpdatedRecord() {
		return newOrUpdatedRecord;
	}

	/**
	 * Sets the new or updated record.
	 *
	 * @param newOrUpdatedRecord
	 *            the new new or updated record
	 */
	public void setNewOrUpdatedRecord(String[] newOrUpdatedRecord) {
		this.newOrUpdatedRecord = newOrUpdatedRecord;
	}

	/**
	 * Gets the date filter type.
	 *
	 * @return the date filter type
	 */
	public String[] getDateFilterType() {
		return dateFilterType;
	}

	/**
	 * Sets the date filter type.
	 *
	 * @param dateFilterType
	 *            the new date filter type
	 */
	public void setDateFilterType(String[] dateFilterType) {
		this.dateFilterType = dateFilterType;
	}

	/**
	 * Gets the legal entity.
	 *
	 * @return the legal entity
	 */
	public String[] getLegalEntity() {
		return legalEntity;
	}

	/**
	 * Sets the legal entity.
	 *
	 * @param legalEntity the new legal entity
	 */
	public void setLegalEntity(String[] legalEntity) {
		this.legalEntity = legalEntity;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId
	 *            the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	

	public String[] getOnfidoStatus() {
		return onfidoStatus;
	}

	public void setOnfidoStatus(String[] onfidoStatus) {
		this.onfidoStatus = onfidoStatus;
	}

	public String[] getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String[] riskStatus) {
		this.riskStatus = riskStatus;
	}

	/**
	 * @return the fraudSightStatus
	 */
	public String[] getFraudSightStatus() {
		return fraudSightStatus;
	}

	/**
	 * @param fraudSightStatus the fraudSightStatus to set
	 */
	public void setFraudSightStatus(String[] fraudSightStatus) {
		this.fraudSightStatus = fraudSightStatus;
	}

	//Added for AT-4614
	public String[] getIntuitionStatus() {
		return intuitionStatus;
	}

	public void setIntuitionStatus(String[] intuitionStatus) {
		this.intuitionStatus = intuitionStatus;
	}

    //Added for AT-4655 and AT-4656
	public String[] getTransactionMonitoringRequest() {
		return transactionMonitoringRequest;
	}

	public void setTransactionMonitoringRequest(String[] transactionMonitoringRequest) {
		this.transactionMonitoringRequest = transactionMonitoringRequest;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	

}
