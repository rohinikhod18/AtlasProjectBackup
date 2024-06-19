package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class DataAnonymisationData.
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class DataAnonymisationDataRequest {

	/** The trade account num. */
	private String tradeAccountNum;
	
	/** The registered on. */
	private String registeredOn;
	
	/** The contact name. */
	private String contactName;
	
	/** The organisation. */
	private String organisation;
	
	/** The type. */
	private String type;
	
	/** The contact id. */
	@JsonProperty("contactId")
	private Integer contactId;
	
	/** The account id. */
	@JsonProperty("accountId")
	private Integer accountId;
	
	/** The user resource lock id. */
	private Integer userResourceLockId;
	
	/** The locked. */
	private Boolean locked;
	
	/** The locked by. */
	private String lockedBy;
	
	/** The total records. */
	private Integer totalRecords;
	
	/** The country of residence. */
	private String countryOfResidence;
	
	/** The new or updated. */
	private String newOrUpdated;
	
	/** The request date. */
	private String requestDate;
	
	/** The source. */
	private String source;
	
	/** The compliance status. */
	private String complianceStatus;
	
	/** The legal entity. */
	private String legalEntity;
	
	/** The Data anon status. */
	private String dataAnonStatus;
	
	/** The enter comment. */
	private String enterComment;
	
	private Integer tradeAccountID;
	
	private String crmAccountID;
	
	private Integer tradeContactID;
	
	private String crmContactID;
	
	@JsonProperty(value="org_code")
	private String orgCode;
	
	/** The initial approval comment. */
	private String[] initialApprovalComment;
		
		/** The initial approval by. */
	private String initialApprovalBy;
				
		/** The initial approval date. */
	private String initialApprovalDate;
		
	    /** The final approval comment. */
    private String finalApprovalComment;
	    		
	    /** The final approval by. */
    private  String finalApprovalBy;
	            
	    /** The final approval date. */
    private String finalApprovalDate;
                 
	    /** The data anonymization status. */
    private Integer dataAnonymizationStatus;

    /** The approved date. */
	private String approvedDate;
	
    /** The final approval comment. */
    private String rejectedComment;
	    		
	/** The final approval by. */
    private  String rejectedBy;
	            
	/** The final approval date. */
    private String rejectedDate;
	
    /** The activity. */
    private String activity;
    
    /** The comment. */
    private String comment;
    
    /** The activity by. */
    private String activityBy;
    
    /** The activity date. */
    private String activityDate;
  
    /** The id. */
    private Integer id;
    
		

		/**
		 * @return the tradeAccountNum
		 */
		public String getTradeAccountNum() {
			return tradeAccountNum;
		}

		/**
		 * @param tradeAccountNum the tradeAccountNum to set
		 */
		public void setTradeAccountNum(String tradeAccountNum) {
			this.tradeAccountNum = tradeAccountNum;
		}

		/**
		 * @return the registeredOn
		 */
		public String getRegisteredOn() {
			return registeredOn;
		}

		/**
		 * @param registeredOn the registeredOn to set
		 */
		public void setRegisteredOn(String registeredOn) {
			this.registeredOn = registeredOn;
		}

		/**
		 * @return the contactName
		 */
		public String getContactName() {
			return contactName;
		}

		/**
		 * @param contactName the contactName to set
		 */
		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		/**
		 * @return the organisation
		 */
		public String getOrganisation() {
			return organisation;
		}

		/**
		 * @param organisation the organisation to set
		 */
		public void setOrganisation(String organisation) {
			this.organisation = organisation;
		}

		/**
		 * @return the type
		 */
		public String getType() {
			return type;
		}

		/**
		 * @param type the type to set
		 */
		public void setType(String type) {
			this.type = type;
		}

		/**
		 * @return the contactId
		 */
		public Integer getContactId() {
			return contactId;
		}

		/**
		 * @param contactId the contactId to set
		 */
		public void setContactId(Integer contactId) {
			this.contactId = contactId;
		}

		/**
		 * @return the accountId
		 */
		public Integer getAccountId() {
			return accountId;
		}

		/**
		 * @param accountId the accountId to set
		 */
		public void setAccountId(Integer accountId) {
			this.accountId = accountId;
		}

		/**
		 * @return the userResourceLockId
		 */
		public Integer getUserResourceLockId() {
			return userResourceLockId;
		}

		/**
		 * @param userResourceLockId the userResourceLockId to set
		 */
		public void setUserResourceLockId(Integer userResourceLockId) {
			this.userResourceLockId = userResourceLockId;
		}

		/**
		 * @return the locked
		 */
		public Boolean getLocked() {
			return locked;
		}

		/**
		 * @param locked the locked to set
		 */
		public void setLocked(Boolean locked) {
			this.locked = locked;
		}

		/**
		 * @return the lockedBy
		 */
		public String getLockedBy() {
			return lockedBy;
		}

		/**
		 * @param lockedBy the lockedBy to set
		 */
		public void setLockedBy(String lockedBy) {
			this.lockedBy = lockedBy;
		}

		/**
		 * @return the totalRecords
		 */
		public Integer getTotalRecords() {
			return totalRecords;
		}

		/**
		 * @param totalRecords the totalRecords to set
		 */
		public void setTotalRecords(Integer totalRecords) {
			this.totalRecords = totalRecords;
		}

		/**
		 * @return the countryOfResidence
		 */
		public String getCountryOfResidence() {
			return countryOfResidence;
		}

		/**
		 * @param countryOfResidence the countryOfResidence to set
		 */
		public void setCountryOfResidence(String countryOfResidence) {
			this.countryOfResidence = countryOfResidence;
		}

		/**
		 * @return the newOrUpdated
		 */
		public String getNewOrUpdated() {
			return newOrUpdated;
		}

		/**
		 * @param newOrUpdated the newOrUpdated to set
		 */
		public void setNewOrUpdated(String newOrUpdated) {
			this.newOrUpdated = newOrUpdated;
		}

		/**
		 * @return the requestDate
		 */
		public String getRequestDate() {
			return requestDate;
		}

		/**
		 * @param requestDate the requestDate to set
		 */
		public void setRequestDate(String requestDate) {
			this.requestDate = requestDate;
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

		/**
		 * @return the complianceStatus
		 */
		public String getComplianceStatus() {
			return complianceStatus;
		}

		/**
		 * @param complianceStatus the complianceStatus to set
		 */
		public void setComplianceStatus(String complianceStatus) {
			this.complianceStatus = complianceStatus;
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
		 * @return the enterComment
		 */
		public String getEnterComment() {
			return enterComment;
		}

		/**
		 * @param enterComment the enterComment to set
		 */
		public void setEnterComment(String enterComment) {
			this.enterComment = enterComment;
		}

		/**
		 * @return the tradeAccountID
		 */
		public Integer getTradeAccountID() {
			return tradeAccountID;
		}

		/**
		 * @param tradeAccountID the tradeAccountID to set
		 */
		public void setTradeAccountID(Integer tradeAccountID) {
			this.tradeAccountID = tradeAccountID;
		}

		/**
		 * @return the crmAccountID
		 */
		public String getCrmAccountID() {
			return crmAccountID;
		}

		/**
		 * @param crmAccountID the crmAccountID to set
		 */
		public void setCrmAccountID(String crmAccountID) {
			this.crmAccountID = crmAccountID;
		}

		/**
		 * @return the tradeContactID
		 */
		public Integer getTradeContactID() {
			return tradeContactID;
		}

		/**
		 * @param tradeContactID the tradeContactID to set
		 */
		public void setTradeContactID(Integer tradeContactID) {
			this.tradeContactID = tradeContactID;
		}

		/**
		 * @return the crmContactID
		 */
		public String getCrmContactID() {
			return crmContactID;
		}

		/**
		 * @param crmContactID the crmContactID to set
		 */
		public void setCrmContactID(String crmContactID) {
			this.crmContactID = crmContactID;
		}

		/**
		 * @return the initialApprovalComment
		 */
		public String[] getInitialApprovalComment() {
			return initialApprovalComment;
		}

		/**
		 * @param initialApprovalComment the initialApprovalComment to set
		 */
		public void setInitialApprovalComment(String[] initialApprovalComment) {
			this.initialApprovalComment = initialApprovalComment;
		}

		/**
		 * @return the initialApprovalBy
		 */
		public String getInitialApprovalBy() {
			return initialApprovalBy;
		}

		/**
		 * @param initialApprovalBy the initialApprovalBy to set
		 */
		public void setInitialApprovalBy(String initialApprovalBy) {
			this.initialApprovalBy = initialApprovalBy;
		}

		/**
		 * @return the initialApprovalDate
		 */
		public String getInitialApprovalDate() {
			return initialApprovalDate;
		}

		/**
		 * @param initialApprovalDate the initialApprovalDate to set
		 */
		public void setInitialApprovalDate(String initialApprovalDate) {
			this.initialApprovalDate = initialApprovalDate;
		}

		/**
		 * @return the finalApprovalComment
		 */
		public String getFinalApprovalComment() {
			return finalApprovalComment;
		}

		/**
		 * @param finalApprovalComment the finalApprovalComment to set
		 */
		public void setFinalApprovalComment(String finalApprovalComment) {
			this.finalApprovalComment = finalApprovalComment;
		}

		/**
		 * @return the finalApprovalBy
		 */
		public String getFinalApprovalBy() {
			return finalApprovalBy;
		}

		/**
		 * @param finalApprovalBy the finalApprovalBy to set
		 */
		public void setFinalApprovalBy(String finalApprovalBy) {
			this.finalApprovalBy = finalApprovalBy;
		}

		/**
		 * @return the finalApprovalDate
		 */
		public String getFinalApprovalDate() {
			return finalApprovalDate;
		}

		/**
		 * @param finalApprovalDate the finalApprovalDate to set
		 */
		public void setFinalApprovalDate(String finalApprovalDate) {
			this.finalApprovalDate = finalApprovalDate;
		}

		/**
		 * @return the dataAnonymizationStatus
		 */
		public Integer getDataAnonymizationStatus() {
			return dataAnonymizationStatus;
		}

		/**
		 * @param dataAnonymizationStatus the dataAnonymizationStatus to set
		 */
		public void setDataAnonymizationStatus(Integer dataAnonymizationStatus) {
			this.dataAnonymizationStatus = dataAnonymizationStatus;
		}

		/**
		 * @return the orgCode
		 */
		public String getOrgCode() {
			return orgCode;
		}

		/**
		 * @param orgCode the orgCode to set
		 */
		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}

		/**
		 * @return the approvedDate
		 */
		public String getApprovedDate() {
			return approvedDate;
		}

		/**
		 * @param approvedDate the approvedDate to set
		 */
		public void setApprovedDate(String approvedDate) {
			this.approvedDate = approvedDate;
		}
		
		/**
		 * Gets the rejected comment.
		 *
		 * @return the rejected comment
		 */
		public String getRejectedComment() {
			return rejectedComment;
		}

		/**
		 * Sets the rejected comment.
		 *
		 * @param rejectedComment the new rejected comment
		 */
		public void setRejectedComment(String rejectedComment) {
			this.rejectedComment = rejectedComment;
		}

		/**
		 * Gets the rejected by.
		 *
		 * @return the rejected by
		 */
		public String getRejectedBy() {
			return rejectedBy;
		}

		/**
		 * Sets the rejected by.
		 *
		 * @param rejectedBy the new rejected by
		 */
		public void setRejectedBy(String rejectedBy) {
			this.rejectedBy = rejectedBy;
		}

		/**
		 * Gets the rejected date.
		 *
		 * @return the rejected date
		 */
		public String getRejectedDate() {
			return rejectedDate;
		}

		/**
		 * Sets the rejected date.
		 *
		 * @param rejectedDate the new rejected date
		 */
		public void setRejectedDate(String rejectedDate) {
			this.rejectedDate = rejectedDate;
		}

		/**
		 * @return the activity
		 */
		public String getActivity() {
			return activity;
		}

		/**
		 * @param activity the activity to set
		 */
		public void setActivity(String activity) {
			this.activity = activity;
		}

		/**
		 * @return the comment
		 */
		public String getComment() {
			return comment;
		}

		/**
		 * @param comment the comment to set
		 */
		public void setComment(String comment) {
			this.comment = comment;
		}

		/**
		 * @return the activityBy
		 */
		public String getActivityBy() {
			return activityBy;
		}

		/**
		 * @param activityBy the activityBy to set
		 */
		public void setActivityBy(String activityBy) {
			this.activityBy = activityBy;
		}

		/**
		 * @return the activityDate
		 */
		public String getActivityDate() {
			return activityDate;
		}

		/**
		 * @param activityDate the activityDate to set
		 */
		public void setActivityDate(String activityDate) {
			this.activityDate = activityDate;
		}

		/**
		 * @return the id
		 */
		public Integer getId() {
			return id;
		}

		/**
		 * @param id the id to set
		 */
		public void setId(Integer id) {
			this.id = id;
		}
			
}

