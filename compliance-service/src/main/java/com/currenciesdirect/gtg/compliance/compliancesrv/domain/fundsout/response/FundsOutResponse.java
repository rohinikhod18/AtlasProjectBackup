package com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class FundsOutResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundsOutResponse extends BaseResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/** The org code. */
	private String orgCode;
	
	/** The status. */
	private String status;
	
	/** The trade payment ID. */
	private Integer tradePaymentID;	

	/** The trade contract number. */
	private String tradeContractNumber;
	
	/** The trade account number. */
	@JsonProperty(value = "trade_acc_number")
	private String tradeAccountNumber;
	
	/** The response reason. */
	@JsonIgnore
	private FundsOutReasonCode responseReason;
	
	/* Added new fields to set service status*/
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
    private	String customCheckStatus;


	/** The account STP status. */
	@JsonIgnore
	private Boolean sTPFlag;
	
	/** The account STP status. */
	@JsonIgnore
	private Boolean usClientListBClientWLFlag;
	
	/** The payment reference check status. */
	@JsonIgnore
	private String paymentReferenceCheckStatus;//Add for AT-3649
	
	@JsonIgnore
    private	String intuitionCheckStatus;
	
	@JsonIgnore
	public Boolean getSTPFlag() {
		return sTPFlag;
	}

	public void setSTPFlag(Boolean sTPFlag) {
		this.sTPFlag = sTPFlag;
	}

	/**
	 * Gets the org code.
	 *
	 * @return The orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode            The orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}


	/**
	 * Gets the response reason.
	 *
	 * @return the response reason
	 */
	public FundsOutReasonCode getResponseReason() {
		return responseReason;
	}

	
	/**
	 * Sets the response reason.
	 *
	 * @param responseReason the new response reason
	 */
	public void setResponseReason(FundsOutReasonCode responseReason) {
		this.responseReason = responseReason;
		this.responseCode = responseReason.getFundsOutReasonCode();
		this.responseDescription = responseReason.getFundsOutReasonDescription();
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
	 * @return the usClientListBClientWLFlag
	 */
	public Boolean getUsClientListBClientWLFlag() {
		return usClientListBClientWLFlag;
	}

	/**
	 * @param usClientListBClientWLFlag the usClientListBClientWLFlag to set
	 */
	public void setUsClientListBClientWLFlag(Boolean usClientListBClientWLFlag) {
		this.usClientListBClientWLFlag = usClientListBClientWLFlag;
	}

	/**
	 * Gets the payment reference check status.
	 *
	 * @return the payment reference check status
	 */
	public String getPaymentReferenceCheckStatus() {
		return paymentReferenceCheckStatus;
	}

	/**
	 * Sets the payment reference check status.
	 *
	 * @param paymentReferenceCheckStatus the new payment reference check status
	 */
	public void setPaymentReferenceCheckStatus(String paymentReferenceCheckStatus) {
		this.paymentReferenceCheckStatus = paymentReferenceCheckStatus;
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

}
