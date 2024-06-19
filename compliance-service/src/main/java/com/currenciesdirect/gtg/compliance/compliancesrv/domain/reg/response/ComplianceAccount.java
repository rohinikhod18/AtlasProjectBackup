package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ComplianceAccount.
 */

@JsonIgnoreProperties(ignoreUnknown=true)
public class ComplianceAccount extends ProfileResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@JsonIgnore
	private Integer id;
	
	/** The version. */
	@JsonIgnore
	private Integer version;
	
	/** The account SFID. */
	@ApiModelProperty(value = "The account SalesForce ID", dataType = "java.lang.String", required = true)
	private String accountSFID;
	
	/** The acs. */
	@ApiModelProperty(value = "The Account Compliance Status", required = true)
	private ContactComplianceStatus acs;
	
	/** The contacts. */
	@ApiModelProperty(value = "Array of contacts", required = true)
	private List<ComplianceContact> contacts;
	
	/** The trade account ID. */
	@ApiModelProperty(value = "The trade account ID", required = true)
	private Integer tradeAccountID;
	
	/** The arc. */
	@JsonIgnore
	private ComplianceReasonCode arc;

	/** The registration in date. */
	@ApiModelProperty(value = "The registration in date", required = true)
	private Timestamp registrationInDate;
	
	/** The registered date. */
	@ApiModelProperty(value = "The registered date", required = true)
	private Timestamp registeredDate;
	
	/** The expiry date. */
	@JsonIgnore
	private Timestamp expiryDate;
	
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
	
	/** The paymentin watchlist status. */
	@JsonIgnore
	private String paymentinWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The paymentout watchlist status. */
	@JsonIgnore
	private String paymentoutWatchlistStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The account STP status. */
	@JsonIgnore
	private Boolean sTPFlag;
	
	@JsonIgnore
	private ComplianceContact specificContact;
	
	@JsonIgnore
	private ComplianceContact otherContacts;
	
	@JsonProperty("custType")
	private String custType;
	
	@JsonIgnore
	private String eventType;
	
	/** The transaction monitoring status. */
	@JsonIgnore
	private String transactionMonitoringStatus = ServiceStatus.NOT_REQUIRED.toString();
	
	/** The intuition risk level. */
	private String intuitionRiskLevel;

	@JsonProperty("cardDecision")
	private String cardDecision;

	/**
	 * TODO
	 */
	@JsonProperty("cardStatus")
	private String cardStatus;
	
	@JsonIgnore
	public Boolean getSTPFlag() {
		return sTPFlag;
	}

	public void setSTPFlag(Boolean sTPFlag) {
		this.sTPFlag = sTPFlag;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceMessageResponse#getErrorCode()
	 */
	@JsonIgnore
	@Override
	public String getErrorCode() {
		return errorCode;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceMessageResponse#getErrorDescription()
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
	 * Gets the account SFID.
	 *
	 * @return the account SFID
	 */
	public String getAccountSFID() {
		return accountSFID;
	}

	/**
	 * Sets the account SFID.
	 *
	 * @param accountSFID the new account SFID
	 */
	public void setAccountSFID(String accountSFID) {
		this.accountSFID = accountSFID;
	}

	/**
	 * Gets the acs.
	 *
	 * @return the acs
	 */
	public ContactComplianceStatus getAcs() {
		return acs;
	}
	
	/**
	 * Sets the acs.
	 *
	 * @param acs the new acs
	 */
	public void setAcs(ContactComplianceStatus acs) {
		this.acs = acs;
	}
	
	/**
	 * Gets the contacts.
	 *
	 * @return the contacts
	 */
	public List<ComplianceContact> getContacts() {
		return contacts;
	}
	
	/**
	 * Sets the contacts.
	 *
	 * @param contacts the new contacts
	 */
	public void setContacts(List<ComplianceContact> contacts) {
		this.contacts = contacts;
	}
	
	/**
	 * Adds the contact.
	 *
	 * @param contact the contact
	 */
	public void addContact(ComplianceContact contact) {
		if(this.contacts==null)
			this.contacts = new ArrayList<>();
		this.contacts.add(contact);
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
	
	/**
	 * Gets the arc.
	 *
	 * @return the arc
	 */
	public ComplianceReasonCode getArc() {
		return arc;
	}

	/**
	 * Sets the arc.
	 *
	 * @param arc the new arc
	 */
	public void setArc(ComplianceReasonCode arc) {
		this.arc = arc;
		this.setResponseCode(arc.getReasonCode());
		this.setResponseDescription(arc.getReasonDescription());
		this.setReasonForInactive(arc.getReasonShort());
	}


	/**
	 * Gets the registration in date.
	 *
	 * @return the registrationInDate
	 */
	@JsonIgnore
	public Timestamp getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 * Serializeregistration in date as string.
	 *
	 * @return the string
	 */
	@JsonProperty("registrationInDate")
	public String serializeregistrationInDateAsString() {
		if(null !=registrationInDate){
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sfd.format(registrationInDate);
		}else{
			return null;
		}
			
	}
	
	/**
	 * Sets the registration in date.
	 *
	 * @param registrationInDate the registrationInDate to set
	 */
	public void setRegistrationInDate(Timestamp registrationInDate) {
		this.registrationInDate = registrationInDate;
	}

	/**
	 * Gets the registered date.
	 *
	 * @return the registeredDate
	 */
	@JsonIgnore
	public Timestamp getRegisteredDate() {
		return registeredDate;
	}

	/**
	 * Serialize registered date as string.
	 *
	 * @return the registeredDate
	 */
	@JsonProperty("registeredDate")
	public String serializeRegisteredDateAsString() {
		if(null != registeredDate){
			SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sfd.format(registeredDate);
		}else{
			return null;
		}
		
	}
	
	/**
	 * Sets the registered date.
	 *
	 * @param registeredDate the registeredDate to set
	 */
	public void setRegisteredDate(Timestamp registeredDate) {
		this.registeredDate = registeredDate;
	}
	
	

	/**
	 * Gets the expiry date.
	 *
	 * @return the expiryDate
	 */
	public Timestamp getExpiryDate() {
		return expiryDate;
	}

	/**
	 * Sets the expiry date.
	 *
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(Timestamp expiryDate) {
		this.expiryDate = expiryDate;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseResponse#toString()
	 */
	@Override
	public String toString() {
		return "Account [id=" + id + ", acs=" + acs + ", contacts=" + contacts + ", getResponseCode()="
				+ getResponseCode() + ", getResponseDescription()=" + getResponseDescription() + "]";
	}

	/**
	 * Gets the trade account ID.
	 *
	 * @return the trade account ID
	 */
	public Integer getTradeAccountID() {
		return tradeAccountID;
	}

	/**
	 * Sets the trade account ID.
	 *
	 * @param tradeAccountID the new trade account ID
	 */
	public void setTradeAccountID(Integer tradeAccountID) {
		this.tradeAccountID = tradeAccountID;
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
	 * Gets the contact by id.
	 *
	 * @param contactId the contact id
	 * @return the contact by id
	 */
	public ComplianceContact getContactById(Integer contactId){
		for (ComplianceContact c: contacts){
			if(c.getId().equals(contactId))
				return c;
		}
		return null;
	}

	/**
	 * @return the specificContact
	 */
	@JsonIgnore
	public ComplianceContact getSpecificContact() {
		return specificContact;
	}

	/**
	 * @param specificContact the specificContact to set
	 */
	@JsonIgnore
	public void setSpecificContact(ComplianceContact specificContact) {
		this.specificContact = specificContact;
	}

	/**
	 * @return the otherContacts
	 */
	@JsonIgnore
	public ComplianceContact getOtherContacts() {
		return otherContacts;
	}

	/**
	 * @param otherContacts the otherContacts to set
	 */
	@JsonIgnore
	public void setOtherContacts(ComplianceContact otherContacts) {
		this.otherContacts = otherContacts;
	}

	/**
	 * @return the custType
	 */
	@JsonIgnore
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	@JsonIgnore
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * @return the eventType
	 */
	@JsonIgnore
	public String getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	@JsonIgnore
	public void setEventType(String eventType) {
		this.eventType = eventType;
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

	/**
	 * Gets the intuition risk level.
	 *
	 * @return the intuition risk level
	 */
	public String getIntuitionRiskLevel() {
		return intuitionRiskLevel;
	}

	public void setIntuitionRiskLevel(String intuitionRiskLevel) {
		this.intuitionRiskLevel = intuitionRiskLevel;
	}

	public String getCardDecision() {
		return cardDecision;
	}

	public void setCardDecision(String cardDecision) {
		this.cardDecision = cardDecision;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
}

