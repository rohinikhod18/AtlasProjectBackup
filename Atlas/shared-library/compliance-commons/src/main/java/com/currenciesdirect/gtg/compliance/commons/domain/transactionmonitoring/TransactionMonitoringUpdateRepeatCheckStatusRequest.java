package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionMonitoringUpdateRepeatCheckStatusRequest.
 */
public class TransactionMonitoringUpdateRepeatCheckStatusRequest extends ServiceMessage implements Serializable {

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

	/** The cust type. */
	@ApiModelProperty(value = "cust Type", required = true)
	@JsonProperty(value = "custType")
	private String custType;

	/** The registration in date. */
	@ApiModelProperty(value = "registration In Date", required = true)
	@JsonProperty(value = "registrationInDate")
	private String registrationInDate;

	/** The user resource id. */
	@ApiModelProperty(value = "user Resource Id", required = true)
	@JsonProperty(value = "userResourceId")
	private Integer userResourceId;

	/** The created by. */
	@ApiModelProperty(value = "created By", required = true)
	private String createdBy;

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
	@JsonProperty(value = "crmAccountId")
	private String crmAccountId;

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
	 * Gets the account sf id.
	 *
	 * @return the account sf id
	 */
	public String getAccountSfId() {
		return accountSfId;
	}

	/**
	 * Sets the account sf id.
	 *
	 * @param accountSfId the new account sf id
	 */
	public void setAccountSfId(String accountSfId) {
		this.accountSfId = accountSfId;
	}

	/**
	 * Gets the contact sf id.
	 *
	 * @return the contact sf id
	 */
	public String getContactSfId() {
		return contactSfId;
	}

	/**
	 * Sets the contact sf id.
	 *
	 * @param contactSfId the new contact sf id
	 */
	public void setContactSfId(String contactSfId) {
		this.contactSfId = contactSfId;
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
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the registration in date.
	 *
	 * @return the registration in date
	 */
	public String getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 * Sets the registration in date.
	 *
	 * @param registrationInDate the new registration in date
	 */
	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
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
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return the trade contact id
	 */
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId the new trade contact id
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
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

	
	
}
