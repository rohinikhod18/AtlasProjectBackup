/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
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
 	@ApiModelProperty(value = "The serial version unique ID", required = true)
 	private static final long serialVersionUID = 1L;
 
 	/** The org code. */
 	@ApiModelProperty(value = "The org code", required = true, example ="1")
 	private String orgCode;
 
 	/** The trade payment ID. */
 	@ApiModelProperty(value = "The trade payment ID", required = true)
 	private Integer tradePaymentID;	
 
 	/** The trade contract number. */
 	@ApiModelProperty(value = "The trade contract number", required = true)
 	private String tradeContractNumber;
 
 	/** The contact cs. */
 	@ApiModelProperty(value = "The contact cs", required = true)
 	private String contactCs;
 
 	/** The status. */
 	@ApiModelProperty(value = "The status", required = true)
 	private String status;
 	
 	/** The trade account number. */
 	@ApiModelProperty(value = "The trade account number", required = true)
 	@JsonProperty(value = "trade_acc_number")
 	private String tradeAccountNumber;
 	
 	/** The blacklist check status. */
 	@ApiModelProperty(value = "The blacklist check status", required = true)
 	@JsonIgnore
 	private String blacklistCheckStatus;
 	
 	/** The sanction check status. */
 	@ApiModelProperty(value = "The sanction check status", required = true)
 	@JsonIgnore
 	private String sanctionCheckStatus;
 	
	/** The fraugster check status. */
 	@ApiModelProperty(value = "The fraudster check status", required = true)
 	@JsonIgnore
 	private String fraugsterCheckStatus;
 	
 	/** The custom check status. */
 	@ApiModelProperty(value = "The custom check status", required = true)
 	@JsonIgnore
 	private String customCheckStatus;
 	
 	/** The response reason. */
 	@ApiModelProperty(value = "The response reason", required = true)
 	@JsonIgnore
 	private FundsInReasonCode responseReason;
 	
 	/** The is black listed. */
 	@ApiModelProperty(value = "The black listed", required = true)
 	@JsonIgnore
 	private Boolean isBlackListed = Boolean.FALSE;
 	
 	/** The is sanctioned. */
 	@ApiModelProperty(value = "Whether client is sanctioned", required = true)
 	@JsonIgnore
 	private Boolean isSanctioned = Boolean.FALSE;
 
 	/** The account STP status. */
 	@ApiModelProperty(value = "The account STP status", required = true)
 	@JsonIgnore
 	private Boolean sTPFlag;	
	
	/** The WL status status. */
 	@JsonIgnore
 	private Boolean docRequiredWL;
 	
 	/** The WL status status. */
 	@JsonIgnore
 	private Boolean vatRequiredWL;
 	
 	@ApiModelProperty(value = "The card Fraud check status", required = true)
 	@JsonIgnore
 	private String debitCardFraudCheckStatus;
 	
 	@ApiModelProperty(value = "The card Fraud check status", required = true)
 	@JsonIgnore
 	private String intuitionCheckStatus;
 	
	@JsonIgnore
	public Boolean getSTPFlag() {
		return sTPFlag;
	}

	public void setSTPFlag(Boolean sTPFlag) {
		this.sTPFlag = sTPFlag;
	}
	
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
	 * Gets the response reason.
	 *
	 * @return the response reason
	 */
	public FundsInReasonCode getResponseReason() {
		return responseReason;
	}

	/**
	 * Sets the response reason.
	 *
	 * @param responseReason the new response reason
	 */
	public void setResponseReason(FundsInReasonCode responseReason) {
		this.responseReason = responseReason;
		this.responseCode = responseReason.getReasonCode();
		this.responseDescription = responseReason.getReasonDescription();
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

	/**
	 * @return the docRequiredWL
	 */
	public Boolean getDocRequiredWL() {
		return docRequiredWL;
	}

	/**
	 * @param docRequiredWL the docRequiredWL to set
	 */
	public void setDocRequiredWL(Boolean docRequiredWL) {
		this.docRequiredWL = docRequiredWL;
	}
	
	/**
	 * @return the vatRequiredWL
	 */
	public Boolean getVatRequiredWL() {
		return vatRequiredWL;
	}

	/**
	 * @param vatRequiredWL the vatRequiredWL to set
	 */
	public void setVatRequiredWL(Boolean vatRequiredWL) {
		this.vatRequiredWL = vatRequiredWL;
	}

	public String getDebitCardFraudCheckStatus() {
		return debitCardFraudCheckStatus;
	}

	public void setDebitCardFraudCheckStatus(String debitCardFraudCheckStatus) {
		this.debitCardFraudCheckStatus = debitCardFraudCheckStatus;
	}

	/**
	 * @return the intuitionCheckStatus
	 */
	public String getIntuitionCheckStatus() {
		return intuitionCheckStatus;
	}

	/**
	 * @param intuitionCheckStatus the intuitionCheckStatus to set
	 */
	public void setIntuitionCheckStatus(String intuitionCheckStatus) {
		this.intuitionCheckStatus = intuitionCheckStatus;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FundsInCreateResponse [responseCode=" + responseCode + ", responseDescription=" + responseDescription
				+ ", orgCode=" + orgCode + ", tradePaymentID=" + tradePaymentID + ", tradeContractNumber="
				+ tradeContractNumber + ", contactCs=" + contactCs + ", status=" + status + "]";
	}

	
}