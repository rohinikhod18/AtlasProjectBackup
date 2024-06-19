package com.currenciesdirect.gtg.compliance.core.domain.registration;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.BaseDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;

/**
 * The Class RegistrationDetailsDBDto.
 */

public class RegistrationDetailsDBDto extends BaseDetailDto {

	/** The contact id. */
	private Integer contactId;
	
	/** The current contact id. */
	private Integer currentContactId;

	/** The crm contact id. */
	private String crmContactId;

	/** The crm account id. */
	private String crmAccountId;
	
	/** The cc crm contact id. */
	private String ccCrmContactId;
	
	/** The trade contact id. */
	private String tradeContactId;

	/** The trade account num. */
	private String tradeAccountNum;
	
	/** The trade account id. */
	private String tradeAccountId;
	
	/** The contact name. */
	private String contactName;

	/** The contact attrib. */
	private String contactAttrib;

	/** The account attrib. */
	private String accountAttrib;

	/** The con compliance status. */
	private String conComplianceStatus;

	/** The acc compliance status. */
	private String accComplianceStatus;

	/** The account id. */
	private Integer accountId;

	/** The orgnization. */
	private String orgnization;

	/** The reg in. */
	private Timestamp regIn;

	/** The reg comp. */
	private Timestamp regComp;
	
	/** The reg comp. */
	private Timestamp regCompAccount;
	
	/** The registration in date. */
	private Timestamp registrationInDate;
	
	/** The compliance expiry. */
	private Timestamp complianceExpiry;
	 
	/** The event DB dto. */
	private Map<String, List<EventDBDto>> eventDBDto;

	/** The other contacts. */
	private List<ContactWrapper> otherContacts;

	/** The watach list. */
	private Watchlist watachList;

	/** The activity logs. */
	private ActivityLogs activityLogs;

	/** The contact status reason. */
	private StatusReason contactStatusReason;
	
	/** The user resource id. */
	private Integer userResourceId;
	
	/** The locked by. */
	private String lockedBy;
	
	/** The is country supported. */
	private List<String> kycSupportedCountryList;
	
	private String legalEntity;
	
	/**New field created to set country of residence(Eg. United Kingdom) for contact */
	private String countryOfResidenceFullName;
	
	private String alertComplianceLog;
	
	private Boolean isOnQueue;
	
	private String customCheckStatus;

	private String dataAnonStatus;
	
	private String poiExists;
	
	private Integer accountTMFlag;
	
	public String getCustomCheckStatus() {
		return customCheckStatus;
	}

	public void setCustomCheckStatus(String customCheckStatus) {
		this.customCheckStatus = customCheckStatus;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the contact name.
	 *
	 * @return the contact name
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * Sets the contact name.
	 *
	 * @param contactName
	 *            the new contact name
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * Gets the con compliance status.
	 *
	 * @return the con compliance status
	 */
	public String getConComplianceStatus() {
		return conComplianceStatus;
	}

	/**
	 * Sets the con compliance status.
	 *
	 * @param conComplianceStatus
	 *            the new con compliance status
	 */
	public void setConComplianceStatus(String conComplianceStatus) {
		this.conComplianceStatus = conComplianceStatus;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the orgnization.
	 *
	 * @return the orgnization
	 */
	public String getOrgnization() {
		return orgnization;
	}

	/**
	 * Sets the orgnization.
	 *
	 * @param orgnization
	 *            the new orgnization
	 */
	public void setOrgnization(String orgnization) {
		this.orgnization = orgnization;
	}

	/**
	 * Gets the contact attrib.
	 *
	 * @return the contact attrib
	 */
	public String getContactAttrib() {
		return contactAttrib;
	}

	/**
	 * Sets the contact attrib.
	 *
	 * @param contactAttrib
	 *            the new contact attrib
	 */
	public void setContactAttrib(String contactAttrib) {
		this.contactAttrib = contactAttrib;
	}

	/**
	 * Gets the account attrib.
	 *
	 * @return the account attrib
	 */
	public String getAccountAttrib() {
		return accountAttrib;
	}

	/**
	 * Gets the trade account num.
	 *
	 * @return the trade account num
	 */
	public String getTradeAccountNum() {
		return tradeAccountNum;
	}

	/**
	 * Sets the trade account num.
	 *
	 * @param tradeAccountNum the new trade account num
	 */
	public void setTradeAccountNum(String tradeAccountNum) {
		this.tradeAccountNum = tradeAccountNum;
	}

	/**
	 * Sets the account attrib.
	 *
	 * @param accountAttrib
	 *            the new account attrib
	 */
	public void setAccountAttrib(String accountAttrib) {
		this.accountAttrib = accountAttrib;
	}

	/**
	 * Gets the crm contact id.
	 *
	 * @return the crm contact id
	 */
	public String getCrmContactId() {
		return crmContactId;
	}

	/**
	 * Sets the crm contact id.
	 *
	 * @param crmContactId
	 *            the new crm contact id
	 */
	public void setCrmContactId(String crmContactId) {
		this.crmContactId = crmContactId;
	}

	/**
	 * Gets the crm account id.
	 *
	 * @return the crm account id
	 */
	public String getCrmAccountId() {
		return crmAccountId;
	}

	/**
	 * Gets the activity logs.
	 *
	 * @return the activity logs
	 */
	@Override
	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}

	/**
	 * Sets the activity logs.
	 *
	 * @param activityLogs the new activity logs
	 */
	@Override
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

	/**
	 * Sets the crm account id.
	 *
	 * @param crmAccountId
	 *            the new crm account id
	 */
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	/**
	 * Gets the other contacts.
	 *
	 * @return the other contacts
	 */
	public List<ContactWrapper> getOtherContacts() {
		return otherContacts;
	}

	/**
	 * Sets the other contacts.
	 *
	 * @param otherContacts
	 *            the new other contacts
	 */
	public void setOtherContacts(List<ContactWrapper> otherContacts) {
		this.otherContacts = otherContacts;
	}

	/**
	 * Gets the acc compliance status.
	 *
	 * @return the acc compliance status
	 */
	public String getAccComplianceStatus() {
		return accComplianceStatus;
	}

	/**
	 * Gets the watach list.
	 *
	 * @return the watach list
	 */
	public Watchlist getWatachList() {
		return watachList;
	}

	/**
	 * Sets the watach list.
	 *
	 * @param watachList the new watach list
	 */
	public void setWatachList(Watchlist watachList) {
		this.watachList = watachList;
	}

	/**
	 * Sets the acc compliance status.
	 *
	 * @param accComplianceStatus
	 *            the new acc compliance status
	 */
	public void setAccComplianceStatus(String accComplianceStatus) {
		this.accComplianceStatus = accComplianceStatus;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return the trade contact id
	 */
	public String getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId the new trade contact id
	 */
	public void setTradeContactId(String tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Gets the event DB dto.
	 *
	 * @return the event DB dto
	 */
	public Map<String, List<EventDBDto>> getEventDBDto() {
		return eventDBDto;
	}

	/**
	 * Sets the event DB dto.
	 *
	 * @param eventDBDto the event DB dto
	 */
	public void setEventDBDto(Map<String, List<EventDBDto>> eventDBDto) {
		this.eventDBDto = eventDBDto;
	}

	/**
	 * Gets the reg in.
	 *
	 * @return the reg in
	 */
	public Timestamp getRegIn() {
		return regIn;
	}

	/**
	 * Sets the reg in.
	 *
	 * @param regIn the new reg in
	 */
	public void setRegIn(Timestamp regIn) {
		this.regIn = regIn;
	}

	/**
	 * Gets the reg comp.
	 *
	 * @return the reg comp
	 */
	public Timestamp getRegComp() {
		return regComp;
	}

	/**
	 * Sets the reg comp.
	 *
	 * @param regComp the new reg comp
	 */
	public void setRegComp(Timestamp regComp) {
		this.regComp = regComp;
	}

	/**
	 * Gets the contact status reason.
	 *
	 * @return the contact status reason
	 */
	public StatusReason getContactStatusReason() {
		return contactStatusReason;
	}

	/**
	 * Sets the contact status reason.
	 *
	 * @param contactStatusReason the new contact status reason
	 */
	public void setContactStatusReason(StatusReason contactStatusReason) {
		this.contactStatusReason = contactStatusReason;
	}

	/**
	 * Gets the user resource id.
	 *
	 * @return the user resource id
	 */
	@Override
	public Integer getUserResourceId() {
		return userResourceId;
	}

	/**
	 * Sets the user resource id.
	 *
	 * @param userResourceId the new user resource id
	 */
	@Override
	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	/**
	 * Gets the locked by.
	 *
	 * @return the locked by
	 */
	@Override
	public String getLockedBy() {
		return lockedBy;
	}

	/**
	 * Sets the locked by.
	 *
	 * @param lockedBy the new locked by
	 */
	@Override
	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
	}

	/**
	 * Gets the trade account id.
	 *
	 * @return the trade account id
	 */
	public String getTradeAccountId() {
		return tradeAccountId;
	}

	/**
	 * Sets the trade account id.
	 *
	 * @param tradeAccountId the new trade account id
	 */
	public void setTradeAccountId(String tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}

	/**
	 * Gets the current contact id.
	 *
	 * @return the current contact id
	 */
	public Integer getCurrentContactId() {
		return currentContactId;
	}

	/**
	 * Sets the current contact id.
	 *
	 * @param currentContactId the new current contact id
	 */
	public void setCurrentContactId(Integer currentContactId) {
		this.currentContactId = currentContactId;
	}

	/**
	 * Gets the cc crm contact id.
	 *
	 * @return the cc crm contact id
	 */
	public String getCcCrmContactId() {
		return ccCrmContactId;
	}

	/**
	 * Sets the cc crm contact id.
	 *
	 * @param ccCrmContactId the new cc crm contact id
	 */
	public void setCcCrmContactId(String ccCrmContactId) {
		this.ccCrmContactId = ccCrmContactId;
	}

	/**
	 * Gets the kyc supported country list.
	 *
	 * @return the kyc supported country list
	 */
	public List<String> getKycSupportedCountryList() {
		return kycSupportedCountryList;
	}

	/**
	 * Sets the kyc supported country list.
	 *
	 * @param kycSupportedCountryList the new kyc supported country list
	 */
	public void setKycSupportedCountryList(List<String> kycSupportedCountryList) {
		this.kycSupportedCountryList = kycSupportedCountryList;
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
	 * @return the regCompAccount
	 */
	public Timestamp getRegCompAccount() {
		return regCompAccount;
	}

	/**
	 * @param regCompAccount the regCompAccount to set
	 */
	public void setRegCompAccount(Timestamp regCompAccount) {
		this.regCompAccount = regCompAccount;
	}

	/**
	 * @return the countryOfResidenceFullName
	 */
	public String getCountryOfResidenceFullName() {
		return countryOfResidenceFullName;
	}

	/**
	 * @param countryOfResidenceFullName the countryOfResidenceFullName to set
	 */
	public void setCountryOfResidenceFullName(String countryOfResidenceFullName) {
		this.countryOfResidenceFullName = countryOfResidenceFullName;
	}

	/**
	 * Gets registration in date
	 * 
	 * @return the registrationInDate
	 */
	public Timestamp getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 *  Sets registration in date
	 * 
	 * @param registrationInDate
	 */
	public void setRegistrationInDate(Timestamp registrationInDate) {
		this.registrationInDate = registrationInDate;
	}

	/**
	 * Gets compliance expiry
	 * 
	 * @return the complianceExpiry
	 */
	public Timestamp getComplianceExpiry() {
		return complianceExpiry;
	}

	/**
	 * Sets compliance expiry
	 * 
	 * @param complianceExpiry
	 */
	public void setComplianceExpiry(Timestamp complianceExpiry) {
		this.complianceExpiry = complianceExpiry;
	}

	@Override
	public String getAlertComplianceLog() {
		return alertComplianceLog;
	}
	
	@Override
	public void setAlertComplianceLog(String alertComplianceLog) {
		this.alertComplianceLog = alertComplianceLog;
	}
	
	@Override
	public Boolean getIsOnQueue() {
		return isOnQueue;
	}
    
	@Override
	public void setIsOnQueue(Boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	/**
	 * @return the dataAnonStatus
	 */
	@Override
	public String getDataAnonStatus() {
		return dataAnonStatus;
	}

	/**
	 * @param dataAnonStatus the dataAnonStatus to set
	 */
	@Override
	public void setDataAnonStatus(String dataAnonStatus) {
		this.dataAnonStatus = dataAnonStatus;
	}

	@Override
	public String getPoiExists() {
		return poiExists;
	}

	@Override
	public void setPoiExists(String poiExists) {
		this.poiExists = poiExists;
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
