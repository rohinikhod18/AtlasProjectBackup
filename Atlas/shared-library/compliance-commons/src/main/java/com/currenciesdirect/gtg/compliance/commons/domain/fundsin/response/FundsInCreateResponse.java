/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FundsInCreateResponse.
 *
 * @author manish
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundsInCreateResponse extends BaseResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;

	/** The trade payment ID. */
	private Integer tradePaymentID;	

	/** The trade contract number. */
	private String tradeContractNumber;

	/** The contact cs. */
	private String contactCs;

	/** The status. */
	private String status;
	
	/** The trade account number. */
	@JsonProperty(value = "trade_acc_number")
	private String tradeAccountNumber;
	
	/** The blacklist check status. */
	@JsonIgnore
	private String blacklistCheckStatus;
	
	/** The sanction check status. */
	@JsonIgnore
	private String sanctionCheckStatus;
	
	/** The fraugster check status. */
	@JsonIgnore
	private String fraugsterCheckStatus;
	
	/** The custom check status. */
	@JsonIgnore
	private String customCheckStatus;
	
	/** The is black listed. */
	@JsonIgnore
	private Boolean isBlackListed = Boolean.FALSE;
	
	/** The is sanctioned. */
	@JsonIgnore
	private Boolean isSanctioned = Boolean.FALSE;


	/**
	 * Gets the contact cs.
	 *
	 * @return the contact cs
	 */
	public String getContactCs() {
		return contactCs;
	}

	/**
	 * Sets the contact cs.
	 *
	 * @param contactCs the new contact cs
	 */
	public void setContactCs(String contactCs) {
		this.contactCs = contactCs;
	}

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
	 * @param status the new status
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
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the checks if is black listed.
	 *
	 * @return the checks if is black listed
	 */
	public Boolean getIsBlackListed() {
		return isBlackListed;
	}

	/**
	 * Sets the checks if is black listed.
	 *
	 * @param isBlackListed the new checks if is black listed
	 */
	public void setIsBlackListed(Boolean isBlackListed) {
		this.isBlackListed = isBlackListed;
	}

	/**
	 * Gets the checks if is sanctioned.
	 *
	 * @return the checks if is sanctioned
	 */
	public Boolean getIsSanctioned() {
		return isSanctioned;
	}

	/**
	 * Sets the checks if is sanctioned.
	 *
	 * @param isSanctioned the new checks if is sanctioned
	 */
	public void setIsSanctioned(Boolean isSanctioned) {
		this.isSanctioned = isSanctioned;
	}

	/**
	 * Gets the trade payment ID.
	 *
	 * @return the trade payment ID
	 */
	public Integer getTradePaymentID() {
		return tradePaymentID;
	}

	/**
	 * Sets the trade payment ID.
	 *
	 * @param tradePaymentID the new trade payment ID
	 */
	public void setTradePaymentID(Integer tradePaymentID) {
		this.tradePaymentID = tradePaymentID;
	}

	/**
	 * Gets the trade contract number.
	 *
	 * @return the trade contract number
	 */
	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	/**
	 * Sets the trade contract number.
	 *
	 * @param tradeContractNumber the new trade contract number
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	/**
	 * Gets the blacklist check status.
	 *
	 * @return the blacklist check status
	 */
	public String getBlacklistCheckStatus() {
		return blacklistCheckStatus;
	}

	/**
	 * Sets the blacklist check status.
	 *
	 * @param blacklistCheckStatus the new blacklist check status
	 */
	public void setBlacklistCheckStatus(String blacklistCheckStatus) {
		this.blacklistCheckStatus = blacklistCheckStatus;
	}

	/**
	 * Gets the sanction check status.
	 *
	 * @return the sanction check status
	 */
	public String getSanctionCheckStatus() {
		return sanctionCheckStatus;
	}

	/**
	 * Sets the sanction check status.
	 *
	 * @param sanctionCheckStatus the new sanction check status
	 */
	public void setSanctionCheckStatus(String sanctionCheckStatus) {
		this.sanctionCheckStatus = sanctionCheckStatus;
	}

	/**
	 * Gets the fraugster check status.
	 *
	 * @return the fraugster check status
	 */
	public String getFraugsterCheckStatus() {
		return fraugsterCheckStatus;
	}

	/**
	 * Sets the fraugster check status.
	 *
	 * @param fraugsterCheckStatus the new fraugster check status
	 */
	public void setFraugsterCheckStatus(String fraugsterCheckStatus) {
		this.fraugsterCheckStatus = fraugsterCheckStatus;
	}

	/**
	 * Gets the custom check status.
	 *
	 * @return the custom check status
	 */
	public String getCustomCheckStatus() {
		return customCheckStatus;
	}

	/**
	 * Sets the custom check status.
	 *
	 * @param customCheckStatus the new custom check status
	 */
	public void setCustomCheckStatus(String customCheckStatus) {
		this.customCheckStatus = customCheckStatus;
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

}