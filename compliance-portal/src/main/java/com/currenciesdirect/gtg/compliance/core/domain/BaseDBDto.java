package com.currenciesdirect.gtg.compliance.core.domain;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * The Class BaseDBDto.
 */
public class BaseDBDto {

	/** The account id. */
	private Integer accountId;
	
	/** The contact id. */
	private Integer contactId;
	
	/** The org code. */
	private String orgCode;
	
	/** The contract number. */
	private String contractNumber;
	
	/** The country. */
	private String country;
	
	/** The date of payment. */
	private String dateOfPayment;
	
	/** The contact name. */
	private String contactName;
	
	/** The reg in. */
	private Timestamp regIn;
	
	/** The reg comp. */
	private Timestamp regComp;
	
	/** The trade contact id. */
	private String tradeContactId;
	
	/** The contact attib. */
	private String contactAttib;
	
	/** The crm account id. */
	private String crmAccountId;
	
	/** The crm contact id. */
	private String crmContactId;
	
	/** The trade account num. */
	private String tradeAccountNum;
	
	/** The acc attrib. */
	private String accAttrib;
	
	/** The user resource id. */
	private Integer userResourceId;
	
	/** The locked by. */
	private String lockedBy;
	
	/** The acc compliance status. */
	private String accComplianceStatus;
	
	/** The con compliance status. */
	private String conComplianceStatus;
	
	/** The status reason. */
	private StatusReason statusReason;
	
	/** The event DB dtos. */
	private Map<String,List<EventDBDto>> eventDBDtos;
	
	/** The watch list. */
	private Watchlist watchList;
	
	/** The status. */
	private Status status;
	
	/** The activity logs. */
	private ActivityLogs activityLogs;
	
	/** The document list. */
	private List<UploadDocumentTypeDBDto> documentList;
	
	/** The pagination data. */
	private List<PaginationData> paginationData;
	
	/** The pagination details. */
	private PaginationDetails paginationDetails;
	
	/** The nationality full name. */
	private String nationalityFullName = Constants.DASH_UI;
	
	private String isDeleted;
	
	private String updatedOn;
	
	/** The intuition Risk Level. */
	private String intuitionRiskLevel = Constants.DASH_DETAILS_PAGE; // AT-4187
	
	/** The account TM flag. */
	private Integer accountTMFlag;
	
	/** The account version. */
	private Integer accountVersion;
	
	private Boolean isWatchlistUpdate;
	
	public List<PaginationData> getPaginationData() {
		return paginationData;
	}

	public void setPaginationData(List<PaginationData> paginationData) {
		this.paginationData = paginationData;
	}

	public PaginationDetails getPaginationDetails() {
		return paginationDetails;
	}

	public void setPaginationDetails(PaginationDetails paginationDetails) {
		this.paginationDetails = paginationDetails;
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
	 * @param accountId the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
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
	 * Gets the contract number.
	 *
	 * @return the contract number
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * Sets the contract number.
	 *
	 * @param contractNumber the new contract number
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the date of payment.
	 *
	 * @return the date of payment
	 */
	public String getDateOfPayment() {
		return dateOfPayment;
	}

	/**
	 * Sets the date of payment.
	 *
	 * @param dateOfPayment the new date of payment
	 */
	public void setDateOfPayment(String dateOfPayment) {
		this.dateOfPayment = dateOfPayment;
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
	 * @param contactName the new contact name
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
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
	 * Gets the contact attib.
	 *
	 * @return the contact attib
	 */
	public String getContactAttib() {
		return contactAttib;
	}

	/**
	 * Sets the contact attib.
	 *
	 * @param contactAttib the new contact attib
	 */
	public void setContactAttib(String contactAttib) {
		this.contactAttib = contactAttib;
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
	 * Sets the crm account id.
	 *
	 * @param crmAccountId the new crm account id
	 */
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
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
	 * @param crmContactId the new crm contact id
	 */
	public void setCrmContactId(String crmContactId) {
		this.crmContactId = crmContactId;
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
	 * Gets the acc attrib.
	 *
	 * @return the acc attrib
	 */
	public String getAccAttrib() {
		return accAttrib;
	}

	/**
	 * Sets the acc attrib.
	 *
	 * @param accAttrib the new acc attrib
	 */
	public void setAccAttrib(String accAttrib) {
		this.accAttrib = accAttrib;
	}

	/**
	 * Gets the user resource id.
	 *
	 * @return the user resource id
	 */
	public Integer getUserResourceId() {
		return userResourceId;
	}

	/**
	 * Sets the user resource id.
	 *
	 * @param userResourceId the new user resource id
	 */
	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	/**
	 * Gets the locked by.
	 *
	 * @return the locked by
	 */
	public String getLockedBy() {
		return lockedBy;
	}

	/**
	 * Sets the locked by.
	 *
	 * @param lockedBy the new locked by
	 */
	public void setLockedBy(String lockedBy) {
		this.lockedBy = lockedBy;
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
	 * Sets the acc compliance status.
	 *
	 * @param accComplianceStatus the new acc compliance status
	 */
	public void setAccComplianceStatus(String accComplianceStatus) {
		this.accComplianceStatus = accComplianceStatus;
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
	 * @param conComplianceStatus the new con compliance status
	 */
	public void setConComplianceStatus(String conComplianceStatus) {
		this.conComplianceStatus = conComplianceStatus;
	}

	/**
	 * Gets the status reason.
	 *
	 * @return the status reason
	 */
	public StatusReason getStatusReason() {
		return statusReason;
	}

	/**
	 * Sets the status reason.
	 *
	 * @param statusReason the new status reason
	 */
	public void setStatusReason(StatusReason statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * Gets the event DB dtos.
	 *
	 * @return the event DB dtos
	 */
	public Map<String, List<EventDBDto>> getEventDBDtos() {
		return eventDBDtos;
	}

	/**
	 * Sets the event DB dtos.
	 *
	 * @param eventDBDtos the event DB dtos
	 */
	public void setEventDBDtos(Map<String, List<EventDBDto>> eventDBDtos) {
		this.eventDBDtos = eventDBDtos;
	}

	/**
	 * Gets the watch list.
	 *
	 * @return the watch list
	 */
	public Watchlist getWatchList() {
		return watchList;
	}

	/**
	 * Sets the watch list.
	 *
	 * @param watchList the new watch list
	 */
	public void setWatchList(Watchlist watchList) {
		this.watchList = watchList;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Gets the activity logs.
	 *
	 * @return the activity logs
	 */
	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}

	/**
	 * Sets the activity logs.
	 *
	 * @param activityLogs the new activity logs
	 */
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

	/**
	 * Gets the document list.
	 *
	 * @return the document list
	 */
	public List<UploadDocumentTypeDBDto> getDocumentList() {
		return documentList;
	}

	/**
	 * Sets the document list.
	 *
	 * @param documentList the new document list
	 */
	public void setDocumentList(List<UploadDocumentTypeDBDto> documentList) {
		this.documentList = documentList;
	}

	/**
	 * Gets the nationality full name
	 *
	 * @return the nationality full name
	 */
	public String getNationalityFullName() {
		return nationalityFullName;
	}
	
	/**
	 * Sets the nationality full name.
	 *
	 * @param nationalityFullName the nationality full name
	 */

	public void setNationalityFullName(String nationalityFullName) {
		this.nationalityFullName = nationalityFullName;
	}

	/**
	 * Gets the isDeleted Status
	 *
	 * @return the isDeleted Status
	 */
	public String getIsDeleted() {
		return isDeleted;
	}
	
	/**
	 * Sets the isDeleted Status
	 *
	 * @param the isDeleted Status
	 */
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	/**
	 * Gets the updated on date
	 *
	 * @return the updated on date
	 */
	public String getUpdatedOn() {
		return updatedOn;
	}
	
	/**
	 * Sets the updated on date
	 *
	 * @param the updated on date
	 */
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * @return the intuitionRiskLevel
	 */
	public String getIntuitionRiskLevel() {
		return intuitionRiskLevel;
	}

	/**
	 * @param intuitionRiskLevel the intuitionRiskLevel to set
	 */
	public void setIntuitionRiskLevel(String intuitionRiskLevel) {
		this.intuitionRiskLevel = intuitionRiskLevel;
	}

	/**
	 * Gets the account TM flag.
	 *
	 * @return the account TM flag
	 */
	public Integer getAccountTMFlag() {
		return accountTMFlag;
	}

	/**
	 * Sets the account TM flag.
	 *
	 * @param accountTMFlag the new account TM flag
	 */
	public void setAccountTMFlag(Integer accountTMFlag) {
		this.accountTMFlag = accountTMFlag;
	}

	/**
	 * Gets the account version.
	 *
	 * @return the account version
	 */
	public Integer getAccountVersion() {
		return accountVersion;
	}

	/**
	 * Sets the account version.
	 *
	 * @param accountVersion the new account version
	 */
	public void setAccountVersion(Integer accountVersion) {
		this.accountVersion = accountVersion;
	}

	public Boolean getIsWatchlistUpdate() {
		return isWatchlistUpdate;
	}

	public void setIsWatchlistUpdate(Boolean isWatchlistUpdate) {
		this.isWatchlistUpdate = isWatchlistUpdate;
	}
	
}
