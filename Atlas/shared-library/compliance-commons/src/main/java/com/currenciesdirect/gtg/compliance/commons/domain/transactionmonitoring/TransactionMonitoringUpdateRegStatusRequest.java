/*
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionMonitoringUpdateRegStatusRequest.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMonitoringUpdateRegStatusRequest extends ServiceMessage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account number", required = true)
	@JsonProperty(value = "accountId")
	private Integer accountId;

	/** The contact id. */
	@ApiModelProperty(value = "The contact number", required = true)
	@JsonProperty(value = "contactId")
	private Integer contactId;

	/** The account sf id. */
	@ApiModelProperty(value = "The accountSfId", required = true)
	@JsonProperty(value = "accountSfId")
	private String accountSfId;

	/** The contact sf id. */
	@ApiModelProperty(value = "The contactSfId", required = true)
	@JsonProperty(value = "contactSfId")
	private String contactSfId;

	/** The org code. */
	@ApiModelProperty(value = "The accountSfId", required = true)
	@JsonProperty(value = "orgCode")
	private String orgCode;
	
	// private StatusReasonUpdateRequest[] contactStatusReasons;
	// private WatchlistUpdateRequest[] watchlist;

	/** The overall watchlist status. */
	@ApiModelProperty(value = "overall Watchlist Status", required = true)
	@JsonProperty(value = "overallWatchlistStatus")
	private Boolean overallWatchlistStatus;
	
	/** The pre account status. */
	@ApiModelProperty(value = "pre Contact Status", required = true)
	@JsonProperty(value = "preContactStatus")
	private String preContactStatus;
	
	/** The updated contact status. */
	@ApiModelProperty(value = "updated Contact Status", required = true)
	@JsonProperty(value = "updatedContactStatus")
	private String updatedContactStatus;

	/** The cust type. */
	@ApiModelProperty(value = "cust Type", required = true)
	@JsonProperty(value = "custType")
	private String custType;

	/** The compliance done on. */
	@ApiModelProperty(value = "The accountSfId", required = true)
	@JsonProperty(value = "complianceDoneOn")
	private String complianceDoneOn;

	/** The registration in date. */
	@ApiModelProperty(value = "registration In Date", required = true)
	@JsonProperty(value = "registrationInDate")
	private String registrationInDate;
	
	/** The compliance expiry. */
	@ApiModelProperty(value = "compliance Expiry", required = true)
	@JsonProperty(value = "complianceExpiry")
	private String complianceExpiry;
	
	/** The comment. */
	@ApiModelProperty(value = "comment", required = true)
	@JsonProperty(value = "comment")
	private String comment;
	
	/** The compliance log. */
	@ApiModelProperty(value = "compliance Log", required = true)
	@JsonProperty(value = "complianceLog")
	private String complianceLog;
	
	/** The is on queue. */
	@ApiModelProperty(value = "The accountSfId", required = true)
	@JsonProperty(value = "isOnQueue")
	private Boolean isOnQueue;

	/** The user resource id. */
	@ApiModelProperty(value = "user Resource Id", required = true)
	@JsonProperty(value = "userResourceId")
	private Integer userResourceId;

	/** The created by. */
	@ApiModelProperty(value = "created By", required = true)
	private String createdBy;
	
	/** The updated account status. */
	@ApiModelProperty(value = "updated Account Status", required = true)
	private String updatedAccountStatus;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty(value = "tradeAccountNumber")
	private String tradeAccountNumber;
	
	/** The trade contact Id. */
	@ApiModelProperty(value = "The trade contact Id", example = "751843", required = true)
	@JsonProperty(value = "tradeContactId")
	private Integer tradeContactId;
	
	/** The account TM flag. */
	@ApiModelProperty(value = "The account TM Flag", required = true)
	@JsonProperty(value = "accountTMFlag")
	private Integer accountTMFlag;
	
	/** The crm account id. */
	@ApiModelProperty(value = "The crm account id", required = true)
	@JsonProperty(value="crmAccountId")
	private String crmAccountId;
	// private UserProfile user;
	
	/** The sanction status. */
	@ApiModelProperty(value = "The sanction status", required = true)
	@JsonProperty(value = "sanctionStatus")
	private String sanctionStatus = null;

	/** The blacklist status. */
	@ApiModelProperty(value = "The blacklist status", required = true)
	@JsonProperty(value = "blacklistStatus")
	private String blacklistStatus = null;

	/** The kyc status. */
	@ApiModelProperty(value = "The kyc status", required = true)
	@JsonProperty(value = "kycStatus")
	private String kycStatus = null;

	/** The fraudpredict status. */
	@ApiModelProperty(value = "The fraudpredict date", required = true)
	@JsonProperty(value = "fraugsterDate")
	private String fraudpredictDate = null;
	
	@ApiModelProperty(value = "The fraudpredict score", required = true)
	@JsonProperty(value = "fraugsterScore")
	private String fraudpredictScore = null;
	
	@ApiModelProperty(value = "The account blacklist status", required = true)
	@JsonProperty(value = "accountBlacklist")
	private String accountBlacklistStatus = null;
	
	@ApiModelProperty(value = "The account sanction status", required = true)
	@JsonProperty(value = "accountSanction")
	private String accountSanctionStatus = null;
	
	@ApiModelProperty(value = "The account version", required = true)
	@JsonProperty(value = "accountVersion")
	private Integer accountVersion = null;
	
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
	 * @return the accountSfId
	 */
	public String getAccountSfId() {
		return accountSfId;
	}

	/**
	 * @param accountSfId the accountSfId to set
	 */
	public void setAccountSfId(String accountSfId) {
		this.accountSfId = accountSfId;
	}

	/**
	 * @return the contactSfId
	 */
	public String getContactSfId() {
		return contactSfId;
	}

	/**
	 * @param contactSfId the contactSfId to set
	 */
	public void setContactSfId(String contactSfId) {
		this.contactSfId = contactSfId;
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
	 * @return the overallWatchlistStatus
	 */
	public Boolean getOverallWatchlistStatus() {
		return overallWatchlistStatus;
	}

	/**
	 * @param overallWatchlistStatus the overallWatchlistStatus to set
	 */
	public void setOverallWatchlistStatus(Boolean overallWatchlistStatus) {
		this.overallWatchlistStatus = overallWatchlistStatus;
	}

	/**
	 * @return the preContactStatus
	 */
	public String getPreContactStatus() {
		return preContactStatus;
	}

	/**
	 * @param preContactStatus the preContactStatus to set
	 */
	public void setPreContactStatus(String preContactStatus) {
		this.preContactStatus = preContactStatus;
	}

	/**
	 * @return the updatedContactStatus
	 */
	public String getUpdatedContactStatus() {
		return updatedContactStatus;
	}

	/**
	 * @param updatedContactStatus the updatedContactStatus to set
	 */
	public void setUpdatedContactStatus(String updatedContactStatus) {
		this.updatedContactStatus = updatedContactStatus;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
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
	 * @return the registrationInDate
	 */
	public String getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 * @param registrationInDate the registrationInDate to set
	 */
	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
	}

	/**
	 * @return the complianceExpiry
	 */
	public String getComplianceExpiry() {
		return complianceExpiry;
	}

	/**
	 * @param complianceExpiry the complianceExpiry to set
	 */
	public void setComplianceExpiry(String complianceExpiry) {
		this.complianceExpiry = complianceExpiry;
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
	 * @return the complianceLog
	 */
	public String getComplianceLog() {
		return complianceLog;
	}

	/**
	 * @param complianceLog the complianceLog to set
	 */
	public void setComplianceLog(String complianceLog) {
		this.complianceLog = complianceLog;
	}

	/**
	 * @return the isOnQueue
	 */
	public Boolean getIsOnQueue() {
		return isOnQueue;
	}

	/**
	 * @param isOnQueue the isOnQueue to set
	 */
	public void setIsOnQueue(Boolean isOnQueue) {
		this.isOnQueue = isOnQueue;
	}

	/**
	 * @return the userResourceId
	 */
	public Integer getUserResourceId() {
		return userResourceId;
	}

	/**
	 * @param userResourceId the userResourceId to set
	 */
	public void setUserResourceId(Integer userResourceId) {
		this.userResourceId = userResourceId;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the updatedAccountStatus
	 */
	public String getUpdatedAccountStatus() {
		return updatedAccountStatus;
	}

	/**
	 * @param updatedAccountStatus the updatedAccountStatus to set
	 */
	public void setUpdatedAccountStatus(String updatedAccountStatus) {
		this.updatedAccountStatus = updatedAccountStatus;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	/**
	 * @return the tradeAccountNumber
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * @return the tradeContactId
	 */
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * @param tradeContactId the tradeContactId to set
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
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

	/**
	 * @return the crmAccountId
	 */
	public String getCrmAccountId() {
		return crmAccountId;
	}

	/**
	 * @param crmAccountId the crmAccountId to set
	 */
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	/**
	 * Gets the sanction status.
	 *
	 * @return the sanction status
	 */
	public String getSanctionStatus() {
		return sanctionStatus;
	}

	/**
	 * Sets the sanction status.
	 *
	 * @param sanctionStatus the new sanction status
	 */
	public void setSanctionStatus(String sanctionStatus) {
		this.sanctionStatus = sanctionStatus;
	}

	/**
	 * Gets the blacklist status.
	 *
	 * @return the blacklist status
	 */
	public String getBlacklistStatus() {
		return blacklistStatus;
	}

	/**
	 * Sets the blacklist status.
	 *
	 * @param blacklistStatus the new blacklist status
	 */
	public void setBlacklistStatus(String blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}

	/**
	 * Gets the kyc status.
	 *
	 * @return the kyc status
	 */
	public String getKycStatus() {
		return kycStatus;
	}

	/**
	 * Sets the kyc status.
	 *
	 * @param kycStatus the new kyc status
	 */
	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	/**
	 * Gets the fraudpredict date.
	 *
	 * @return the fraudpredict date
	 */
	public String getFraudpredictDate() {
		return fraudpredictDate;
	}

	/**
	 * Sets the fraudpredict date.
	 *
	 * @param fraudpredictStatus the new fraudpredict date
	 */
	public void setFraudpredictDate(String fraudpredictDate) {
		this.fraudpredictDate = fraudpredictDate;
	}

	/**
	 * Gets the fraudpredict score.
	 *
	 * @return the fraudpredict score
	 */
	public String getFraudpredictScore() {
		return fraudpredictScore;
	}

	/**
	 * Sets the fraudpredict score.
	 *
	 * @param fraudpredictScore the new fraudpredict score
	 */
	public void setFraudpredictScore(String fraudpredictScore) {
		this.fraudpredictScore = fraudpredictScore;
	}

	/**
	 * Gets the account blacklist status.
	 *
	 * @return the account blacklist status
	 */
	public String getAccountBlacklistStatus() {
		return accountBlacklistStatus;
	}

	/**
	 * Sets the account blacklist status.
	 *
	 * @param accountBlacklistStatus the new account blacklist status
	 */
	public void setAccountBlacklistStatus(String accountBlacklistStatus) {
		this.accountBlacklistStatus = accountBlacklistStatus;
	}

	/**
	 * Gets the account sanction status.
	 *
	 * @return the account sanction status
	 */
	public String getAccountSanctionStatus() {
		return accountSanctionStatus;
	}

	/**
	 * Sets the account sanction status.
	 *
	 * @param accountSanctionStatus the new account sanction status
	 */
	public void setAccountSanctionStatus(String accountSanctionStatus) {
		this.accountSanctionStatus = accountSanctionStatus;
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

	
}
