package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.docupload.DocumentDto;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class BaseDetailDto.
 */
public class BaseDetailDto{

	/** The account. */
	private AccountWrapper account;

	/** The contact. */
	private ContactWrapper currentContact;

	/** The documents. */
	private List<DocumentDto> documents;
	
	/** The search criteria. */
	private String searchCriteria;

	/** The current record. */
	private Integer currentRecord;

	/** The total records. */
	private Integer totalRecords;

	/** The locked. */
	private Boolean locked;

	/** The locked by. */
	private String lockedBy;

	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;

	/** The user. */
	private UserProfile user;
	
	/** The watchlist. */
	private Watchlist watchlist;
	
	/** The status reason. */
	private StatusReason statusReason;
	
	/** The status. */
	private Status status;
	
	/** The document list. */
	private List<UploadDocumentTypeDBDto> documentList;

	/** The activity logs. */
	private ActivityLogs activityLogs;

	/** The user resource id. */
	private Integer userResourceId;
	
	/** The pagination data. */
	private List<PaginationData> paginationData;
	
	/** The pagination details. */
	private PaginationDetails paginationDetails;
	
	private Boolean isPagenationRequired;
	
	private String source ;
	
	private String alertComplianceLog;
	
	private Boolean isOnQueue;
	
	private String dataAnonStatus;
	
	private String poiExists;
	
	private String intuitionRiskLevel = Constants.DASH_DETAILS_PAGE;
	
	private Integer accountVersion;
	
	private Integer accountTMFlag;
	
	public Boolean getIsPagenationRequired() {
		return isPagenationRequired;
	}

	public void setIsPagenationRequired(Boolean isPagenationRequired) {
		this.isPagenationRequired = isPagenationRequired;
	}

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public AccountWrapper getAccount() {
		return account;
	}

	/**
	 * Gets the documents.
	 *
	 * @return the documents
	 */
	public List<DocumentDto> getDocuments() {
		return documents;
	}

	/**
	 * Gets the locked.
	 *
	 * @return the locked
	 */
	public Boolean getLocked() {
		return locked;
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
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserProfile getUser() {
		return user;
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
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(AccountWrapper account) {
		this.account = account;
	}

	/**
	 * Sets the documents.
	 *
	 * @param documents the new documents
	 */
	public void setDocuments(List<DocumentDto> documents) {
		this.documents = documents;
	}

	/**
	 * Sets the locked.
	 *
	 * @param locked the new locked
	 */
	public void setLocked(Boolean locked) {
		this.locked = locked;
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
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Sets the user.
	 *
	 * @param user the new user
	 */
	public void setUser(UserProfile user) {
		this.user = user;
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
	 * Gets the current contact.
	 *
	 * @return the current contact
	 */
	public ContactWrapper getCurrentContact() {
		return currentContact;
	}

	/**
	 * Sets the current contact.
	 *
	 * @param currentContact the new current contact
	 */
	public void setCurrentContact(ContactWrapper currentContact) {
		this.currentContact = currentContact;
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
	 * Gets the search criteria.
	 *
	 * @return the search criteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * Gets the current record.
	 *
	 * @return the current record
	 */
	public Integer getCurrentRecord() {
		return currentRecord;
	}

	/**
	 * Gets the total records.
	 *
	 * @return the total records
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/**
	 * Sets the search criteria.
	 *
	 * @param searchCriteria the new search criteria
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * Sets the current record.
	 *
	 * @param currentRecord the new current record
	 */
	public void setCurrentRecord(Integer currentRecord) {
		this.currentRecord = currentRecord;
	}

	/**
	 * Sets the total records.
	 *
	 * @param totalRecords the new total records
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/**
	 * Gets the watchlist.
	 *
	 * @return the watchlist
	 */
	public Watchlist getWatchlist() {
		return watchlist;
	}

	/**
	 * Sets the watchlist.
	 *
	 * @param watchlist the new watchlist
	 */
	public void setWatchlist(Watchlist watchlist) {
		this.watchlist = watchlist;
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
	 * Gets the pagination data.
	 *
	 * @return the pagination data
	 */
	public List<PaginationData> getPaginationData() {
		return paginationData;
	}

	/**
	 * Sets the pagination data.
	 *
	 * @param paginationData the new pagination data
	 */
	public void setPaginationData(List<PaginationData> paginationData) {
		this.paginationData = paginationData;
	}

	/**
	 * Gets the pagination details.
	 *
	 * @return the pagination details
	 */
	public PaginationDetails getPaginationDetails() {
		return paginationDetails;
	}

	/**
	 * Sets the pagination details.
	 *
	 * @param paginationDetails the new pagination details
	 */
	public void setPaginationDetails(PaginationDetails paginationDetails) {
		this.paginationDetails = paginationDetails;
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
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	public String getAlertComplianceLog() {
		return alertComplianceLog;
	}

	public void setAlertComplianceLog(String alertComplianceLog) {
		this.alertComplianceLog = alertComplianceLog;
	}

	public Boolean getIsOnQueue() {
		return isOnQueue;
	}

	public void setIsOnQueue(Boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	/**
	 * @return the dataAnonStatus
	 */
	public String getDataAnonStatus() {
		return dataAnonStatus;
	}

	/**
	 * @param dataAnonStatus the dataAnonStatus to set
	 */
	public void setDataAnonStatus(String dataAnonStatus) {
		this.dataAnonStatus = dataAnonStatus;
	}

	/**
	 * @return the poiExists
	 */
	public String getPoiExists() {
		return poiExists;
	}

	/**
	 * @param poiExists the poiExists to set
	 */
	public void setPoiExists(String poiExists) {
		this.poiExists = poiExists;
	}

	public String getIntuitionRiskLevel() {
		return intuitionRiskLevel;
	}

	public void setIntuitionRiskLevel(String intuitionRiskLevel) {
		this.intuitionRiskLevel = intuitionRiskLevel;
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
	
}
