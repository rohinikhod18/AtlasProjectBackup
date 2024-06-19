package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ComplianceContact.
 *
 * @author bnt
 */
public class ComplianceContact extends ProfileResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The id. */
	@JsonIgnore
	private Integer id;
	
	/** The skip rest checks. */
	@JsonIgnore
	private Boolean skipRestChecks = Boolean.FALSE;
	
	/** The version. */
	@JsonIgnore
	private Integer version;
	
	/** The contact SFID. */
	@ApiModelProperty(value = "The contact SFID", dataType = "java.lang.String", required = true)
	private String contactSFID;
	
	/** The ccs. */
	@ApiModelProperty(value = "The Contact Compliance Status", required = true)
	private ContactComplianceStatus ccs;
	
	/** The trade contact ID. */
	@ApiModelProperty(value = "The trade contact ID", required = true)
	private Integer tradeContactID;
	
	/** The crc. */
	@JsonIgnore
	private ComplianceReasonCode crc;
	
	/** The kyc status. */
	@JsonIgnore
	private String kycStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The sanction status. */
	@JsonIgnore
	private String sanctionStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The fraugster status. */
	@JsonIgnore
	private String fraugsterStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The blacklist status. */
	@JsonIgnore
	private String blacklistStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The custom check status. */
	@JsonIgnore
	private String customCheckStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The paymentin watchlist status. */
	@JsonIgnore
	private String paymentinWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();//Added for AT-2986
	
	/** The paymentout watchlist status. */
	@JsonIgnore
	private String paymentoutWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();//Added for AT-2986
	
	/** The contact STP status. */
	@JsonIgnore
	private Boolean sTPFlag;
	
	/** The transaction monitoring status. */
	@JsonIgnore
	private String transactionMonitoringStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/**
	 * Gets the STP flag.
	 *
	 * @return the STP flag
	 */
	@JsonIgnore
	public Boolean getSTPFlag() {
		return sTPFlag;
	}

	/**
	 * Sets the STP flag.
	 *
	 * @param sTPFlag the new STP flag
	 */
	public void setSTPFlag(Boolean sTPFlag) {
		this.sTPFlag = sTPFlag;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse#getErrorCode()
	 */
	@JsonIgnore
	@Override
	public String getErrorCode() {
		return errorCode;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse#getErrorDescription()
	 */
	@JsonIgnore
	@Override
	public String getErrorDescription() {
		return errorDescription;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
	 * Gets the skip rest checks.
	 *
	 * @return the skip rest checks
	 */
	public Boolean getSkipRestChecks() {
		return skipRestChecks;
	}

	/**
	 * Sets the skip rest checks.
	 *
	 * @param skipRestChecks the new skip rest checks
	 */
	public void setSkipRestChecks(Boolean skipRestChecks) {
		this.skipRestChecks = skipRestChecks;
	}

	/**
	 * Gets the contact SFID.
	 *
	 * @return the contact SFID
	 */
	public String getContactSFID() {
		return contactSFID;
	}

	/**
	 * Sets the contact SFID.
	 *
	 * @param contactSFID the new contact SFID
	 */
	public void setContactSFID(String contactSFID) {
		this.contactSFID = contactSFID;
	}

	/**
	 * Gets the ccs.
	 *
	 * @return the ccs
	 */
	public ContactComplianceStatus getCcs() {
		return ccs;
	}
	
	/**
	 * Sets the ccs.
	 *
	 * @param ccs the new ccs
	 */
	public void setCcs(ContactComplianceStatus ccs) {
		this.ccs = ccs;
	}
	
	
	/**
	 * Gets the crc.
	 *
	 * @return the crc
	 */
	public ComplianceReasonCode getCrc() {
		return crc;
	}

	/**
	 * Sets the crc.
	 *
	 * @param crc the new crc
	 */
	public void setCrc(ComplianceReasonCode crc) {
		this.crc = crc;
		this.setResponseCode(crc.getReasonCode());
		this.setResponseDescription(crc.getReasonDescription());
		this.setReasonForInactive(crc.getReasonShort());
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse#toString()
	 */
	@Override
	public String toString() {
		return "Contact [id=" + id + ", ccs=" + ccs + ", getResponseCode()=" + getResponseCode()
				+ ", getResponseDescription()=" + getResponseDescription() + "]";
	}

	/**
	 * Gets the trade contact ID.
	 *
	 * @return the trade contact ID
	 */
	public Integer getTradeContactID() {
		return tradeContactID;
	}

	/**
	 * Sets the trade contact ID.
	 *
	 * @param tradeContactID the new trade contact ID
	 */
	public void setTradeContactID(Integer tradeContactID) {
		this.tradeContactID = tradeContactID;
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
	 * Gets the fraugster status.
	 *
	 * @return the fraugster status
	 */
	public String getFraugsterStatus() {
		return fraugsterStatus;
	}

	/**
	 * Sets the fraugster status.
	 *
	 * @param fraugsterStatus the new fraugster status
	 */
	public void setFraugsterStatus(String fraugsterStatus) {
		this.fraugsterStatus = fraugsterStatus;
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
	 * Gets the paymentin watchlist status.
	 *
	 * @return the paymentin watchlist status
	 */
	public String getPaymentinWatchlistStatus() {
		return paymentinWatchlistStatus;
	}

	/**
	 * Sets the paymentin watchlist status.
	 *
	 * @param paymentinWatchlistStatus the new paymentin watchlist status
	 */
	public void setPaymentinWatchlistStatus(String paymentinWatchlistStatus) {
		this.paymentinWatchlistStatus = paymentinWatchlistStatus;
	}

	/**
	 * Gets the paymentout watchlist status.
	 *
	 * @return the paymentout watchlist status
	 */
	public String getPaymentoutWatchlistStatus() {
		return paymentoutWatchlistStatus;
	}

	/**
	 * Sets the paymentout watchlist status.
	 *
	 * @param paymentoutWatchlistStatus the new paymentout watchlist status
	 */
	public void setPaymentoutWatchlistStatus(String paymentoutWatchlistStatus) {
		this.paymentoutWatchlistStatus = paymentoutWatchlistStatus;
	}

	/**
	 * @return the transactionMonitoringStatus
	 */
	public String getTransactionMonitoringStatus() {
		return transactionMonitoringStatus;
	}

	/**
	 * @param transactionMonitoringStatus the transactionMonitoringStatus to set
	 */
	public void setTransactionMonitoringStatus(String transactionMonitoringStatus) {
		this.transactionMonitoringStatus = transactionMonitoringStatus;
	}
	
}
