/*
 * Copyright 2012-2017 Currencies Direct Ltd, United Kingdom
 *
 * titan-wrapper-service: Payee.java Last modified: 26-Jul-2017
 */
package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Payee.
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class WrapperPayee implements IDomain,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@JsonProperty("payee_id")
	private Integer id;

	/** The legacy payee ID. */
	@JsonProperty("legacy_payee_id")
	private Integer legacyPayeeID;

	/** The type. */
	@JsonProperty("payee_type")
	private String type;

	/** The company name. */
	@JsonProperty("business_name")
	private String companyName;

	/** The first name. */
	@JsonProperty("first_name")
	private String firstName;

	/** The middle name. */
	@JsonProperty("middle_name")
	private String middleName;

	/** The last name. */
	@JsonProperty("last_name")
	private String lastName;

	/** The nick name. */
	@JsonProperty("nick_name")
	private String nickName;

	/** The email. */
	@JsonProperty("email_id")
	private String email;

	/** The work phone number. */
	@JsonProperty("phone_number")
	private String workPhoneNumber;

	/** The mobile phone number. */
	@JsonProperty("mobile_number")
	private String mobilePhoneNumber;

	/** The mailing street. */
	@JsonProperty("payee_street")
	private String mailingStreet;

	/** The mailing city. */
	@JsonProperty("mailing_city")
	private String mailingCity;

	/** The mailing state. */
	@JsonProperty("payee_state")
	private String mailingState;

	/** The mailing post code. */
	@JsonProperty("payee_post_code")
	private String mailingPostCode;

	/** The customer note. */
	@JsonProperty("note_to_payee")
	private String customerNote;

	/** The reference. */
	@JsonProperty("reference")
	private String reference;

	/** The customer can receive payment. */
	@JsonProperty("is_receivable")
	private Boolean customerCanReceivePayment;

	/** The Source Application. */
	@JsonProperty("source_application")
	private String sourceApplication;

	/** The self. */
	@JsonProperty("self")
	private Boolean self;

	/** The default bank charge type. */
	@JsonProperty("default_bank_charge_type")
	private String defaultBankChargeType;

	/** The priority payment. */
	@JsonProperty("priority_payment")
	private Boolean priorityPayment;

	/** The active. */
	@JsonProperty("active")
	private Boolean active;

	/** The deleted. */
	@JsonProperty("deleted")
	private Boolean deleted;

	/**
	 * The org code.
	 * 
	 * request contain org code which have to convert into organization
	 * id.import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
	 */
	@JsonProperty("org_code")
	private String organizationCode;

	/**
	 * The country Iso code.
	 * 
	 * request contain payee country code which have to convert into country id
	 */
	@JsonProperty("payee_country")
	private String countryCode;

	/**
	 * The notification type lookup value.
	 * 
	 * request contain string notificationlookupvaluecode
	 */
	@JsonProperty("notification_type")
	private String notificationTypeLookupValueCode;

	/** The purpose of payment. */
	@JsonProperty("purpose_of_payment")
	private String purposeOfPayment;

	/** The account number. */
	@JsonProperty("account_number")
	private String accountNumber;

	/** The is used in transaction. */
	@JsonProperty("is_used_in_transaction")
	private Boolean isUsedInTransaction;

	/** The payee payment method list. */
	@JsonProperty("payee_payment_method_list")
	private List<WrapperPayeePaymentMethod> payeePaymentMethodList;

	/** The organization name. */
	@JsonProperty("organization_name")
	private String organizationName;

	/** The country name. */
	@JsonProperty("country_name")
	private String countryName;

	/** The online. */
	@JsonProperty("online")
	private Boolean online;

	/**
	 * Gets the online.
	 *
	 * @return the online
	 */
	public Boolean getOnline() {
		return online;
	}

	/**
	 * Sets the online.
	 *
	 * @param online
	 *            the online to set
	 */
	public void setOnline(Boolean online) {
		this.online = online;
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
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		if (null != id) {
			this.id = id;
		}
	}

	/**
	 * Gets the legacy payee ID.
	 *
	 * @return the legacyPayeeID
	 */
	public Integer getLegacyPayeeID() {
		return legacyPayeeID;
	}

	/**
	 * Sets the legacy payee ID.
	 *
	 * @param legacyPayeeID
	 *            the legacyPayeeID to set
	 */
	public void setLegacyPayeeID(Integer legacyPayeeID) {
		if (null != legacyPayeeID) {
			this.legacyPayeeID = legacyPayeeID;
		}
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		if (null != type && !type.isEmpty()) {
			this.type = type;
		}
	}

	/**
	 * Gets the company name.
	 *
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}

	/**
	 * Sets the company name.
	 *
	 * @param companyName
	 *            the companyName to set
	 */
	public void setCompanyName(String companyName) {
		if (companyName != null && !companyName.isEmpty()) {
			this.companyName = companyName;
		}
	}

	/**
	 * Gets the first name.
	 *
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		if (firstName != null && !firstName.isEmpty()) {
			this.firstName = firstName;
		}
	}

	/**
	 * Gets the middle name.
	 *
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name.
	 *
	 * @param middleName
	 *            the middleName to set
	 */
	public void setMiddleName(String middleName) {
		if (middleName != null) {
			this.middleName = middleName;
		}
	}

	/**
	 * Gets the last name.
	 *
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		if (lastName != null && !lastName.isEmpty()) {
			this.lastName = lastName;
		}
	}

	/**
	 * Gets the nick name.
	 *
	 * @return the nickName
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * Sets the nick name.
	 *
	 * @param nickName
	 *            the nickName to set
	 */
	public void setNickName(String nickName) {
		if (nickName != null && !nickName.isEmpty()) {
			this.nickName = nickName;
		}
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		if (email != null) {
			this.email = email;
		}
	}

	/**
	 * Gets the work phone number.
	 *
	 * @return the workPhoneNumber
	 */
	public String getWorkPhoneNumber() {
		return workPhoneNumber;
	}

	/**
	 * Sets the work phone number.
	 *
	 * @param workPhoneNumber
	 *            the workPhoneNumber to set
	 */
	public void setWorkPhoneNumber(String workPhoneNumber) {
		if (workPhoneNumber != null) {
			this.workPhoneNumber = workPhoneNumber;
		}
	}

	/**
	 * Gets the mobile phone number.
	 *
	 * @return the mobilePhoneNumber
	 */
	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	/**
	 * Sets the mobile phone number.
	 *
	 * @param mobilePhoneNumber
	 *            the mobilePhoneNumber to set
	 */
	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		if (mobilePhoneNumber != null) {
			this.mobilePhoneNumber = mobilePhoneNumber;
		}
	}

	/**
	 * Gets the mailing street.
	 *
	 * @return the mailingStreet
	 */
	public String getMailingStreet() {
		return mailingStreet;
	}

	/**
	 * Sets the mailing street.
	 *
	 * @param mailingStreet
	 *            the mailingStreet to set
	 */
	public void setMailingStreet(String mailingStreet) {
		if (mailingStreet != null) {
			this.mailingStreet = mailingStreet;
		}
	}

	/**
	 * Gets the mailing city.
	 *
	 * @return the mailingCity
	 */
	public String getMailingCity() {
		return mailingCity;
	}

	/**
	 * Sets the mailing city.
	 *
	 * @param mailingCity
	 *            the mailingCity to set
	 */
	public void setMailingCity(String mailingCity) {
		if (mailingCity != null) {
			this.mailingCity = mailingCity;
		}
	}

	/**
	 * Gets the mailing state.
	 *
	 * @return the mailingState
	 */
	public String getMailingState() {
		return mailingState;
	}

	/**
	 * Sets the mailing state.
	 *
	 * @param mailingState
	 *            the mailingState to set
	 */
	public void setMailingState(String mailingState) {
		if (null != mailingState) {
			this.mailingState = mailingState;
		}
	}

	/**
	 * Gets the mailing post code.
	 *
	 * @return the mailingPostCode
	 */
	public String getMailingPostCode() {
		return mailingPostCode;
	}

	/**
	 * Sets the mailing post code.
	 *
	 * @param mailingPostCode
	 *            the mailingPostCode to set
	 */
	public void setMailingPostCode(String mailingPostCode) {
		if (null != mailingPostCode) {
			this.mailingPostCode = mailingPostCode;
		}
	}

	/**
	 * Gets the customer note.
	 *
	 * @return the customerNote
	 */
	public String getCustomerNote() {
		return customerNote;
	}

	/**
	 * Sets the customer note.
	 *
	 * @param customerNote
	 *            the customerNote to set
	 */
	public void setCustomerNote(String customerNote) {
		if (null != customerNote) {
			this.customerNote = customerNote;
		}
	}

	/**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * Sets the reference.
	 *
	 * @param reference
	 *            the reference to set
	 */
	public void setReference(String reference) {
		if (null != reference) {
			this.reference = reference;
		}
	}

	/**
	 * Gets the customer can receive payment.
	 *
	 * @return the customerCanReceivePayment
	 */
	public Boolean getCustomerCanReceivePayment() {
		return customerCanReceivePayment;
	}

	/**
	 * Sets the customer can receive payment.
	 *
	 * @param customerCanReceivePayment
	 *            the customerCanReceivePayment to set
	 */
	public void setCustomerCanReceivePayment(Boolean customerCanReceivePayment) {
		if (null != customerCanReceivePayment) {
			this.customerCanReceivePayment = customerCanReceivePayment;
		}
	}

	/**
	 * Gets the source application.
	 *
	 * @return the sourceApplication
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the sourceApplication to set
	 */
	public void setSourceApplication(String sourceApplication) {
		if (null != sourceApplication && !sourceApplication.isEmpty()) {
			this.sourceApplication = sourceApplication;
		}
	}

	/**
	 * Gets the active.
	 *
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * Sets the active.
	 *
	 * @param active
	 *            the active to set
	 */
	public void setActive(Boolean active) {
		if (null != active) {
			this.active = active;
		}
	}

	/**
	 * Gets the deleted.
	 *
	 * @return the deleted
	 */
	public Boolean getDeleted() {
		return deleted;
	}

	/**
	 * Sets the deleted.
	 *
	 * @param deleted
	 *            the deleted to set
	 */
	public void setDeleted(Boolean deleted) {
		if (null != deleted) {
			this.deleted = deleted;
		}
	}

	/**
	 * Gets the organization code.
	 *
	 * @return the organizationCode
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * Sets the organization code.
	 *
	 * @param organizationCode
	 *            the organizationCode to set
	 */
	public void setOrganizationCode(String organizationCode) {
		if (null != organizationCode && !organizationCode.isEmpty()) {
			this.organizationCode = organizationCode;
		}
	}

	/**
	 * Gets the country code.
	 *
	 * @return the country code
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * Sets the country code.
	 *
	 * @param countryCode
	 *            the new country code
	 */
	public void setCountryCode(String countryCode) {
		if (null != countryCode && !countryCode.isEmpty()) {
			this.countryCode = countryCode;
		}
	}

	/**
	 * Gets the notification type lookup value code.
	 *
	 * @return the notificationTypeLookupValueCode
	 */
	public String getNotificationTypeLookupValueCode() {
		return notificationTypeLookupValueCode;
	}

	/**
	 * Sets the notification type lookup value code.
	 *
	 * @param notificationTypeLookupValueCode
	 *            the notificationTypeLookupValueCode to set
	 */
	public void setNotificationTypeLookupValueCode(String notificationTypeLookupValueCode) {
		if (null != notificationTypeLookupValueCode && !notificationTypeLookupValueCode.isEmpty()) {
			this.notificationTypeLookupValueCode = notificationTypeLookupValueCode;
		}
	}

	/**
	 * Gets the purpose of payment.
	 *
	 * @return the purposeOfPayment
	 */
	public String getPurposeOfPayment() {
		return purposeOfPayment;
	}

	/**
	 * Sets the purpose of payment.
	 *
	 * @param purposeOfPayment
	 *            the purposeOfPayment to set
	 */
	public void setPurposeOfPayment(String purposeOfPayment) {
		if (null != purposeOfPayment && !purposeOfPayment.isEmpty()) {
			this.purposeOfPayment = purposeOfPayment;
		}
	}

	/**
	 * Gets the payee payment method list.
	 *
	 * @return the payeePaymentMethodList
	 */
	public List<WrapperPayeePaymentMethod> getPayeePaymentMethodList() {
		return payeePaymentMethodList;
	}

	/**
	 * Sets the payee payment method list.
	 *
	 * @param payeePaymentMethodList
	 *            the payeePaymentMethodList to set
	 */
	public void setPayeePaymentMethodList(List<WrapperPayeePaymentMethod> payeePaymentMethodList) {
		this.payeePaymentMethodList = payeePaymentMethodList;
	}

	/**
	 * Gets the account number.
	 *
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets the checks if is used in transaction.
	 *
	 * @return the isUsedInTransaction
	 */
	public Boolean getIsUsedInTransaction() {
		return isUsedInTransaction;
	}

	/**
	 * Sets the checks if is used in transaction.
	 *
	 * @param isUsedInTransaction
	 *            the isUsedInTransaction to set
	 */
	public void setIsUsedInTransaction(Boolean isUsedInTransaction) {
		this.isUsedInTransaction = isUsedInTransaction;
	}

	/**
	 * Gets the organization name.
	 *
	 * @return the organizationName
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * Sets the organization name.
	 *
	 * @param organizationName
	 *            the organizationName to set
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * Gets the country name.
	 *
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * Sets the country name.
	 *
	 * @param countryName
	 *            the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Gets the self.
	 *
	 * @return the self
	 */
	public Boolean getSelf() {
		return self;
	}

	/**
	 * Sets the self.
	 *
	 * @param self
	 *            the self to set
	 */
	public void setSelf(Boolean self) {
		if (null != self) {
			this.self = self;
		}
	}

	/**
	 * Gets the default bank charge type.
	 *
	 * @return the defaultBankChargeType
	 */
	public String getDefaultBankChargeType() {
		return defaultBankChargeType;
	}

	/**
	 * Sets the default bank charge type.
	 *
	 * @param defaultBankChargeType
	 *            the defaultBankChargeType to set
	 */
	public void setDefaultBankChargeType(String defaultBankChargeType) {
		if (null != defaultBankChargeType) {
			this.defaultBankChargeType = defaultBankChargeType;
		}
	}

	/**
	 * Gets the priority payment.
	 *
	 * @return the priorityPayment
	 */
	public Boolean getPriorityPayment() {
		return priorityPayment;
	}

	/**
	 * Sets the priority payment.
	 *
	 * @param priorityPayment
	 *            the priorityPayment to set
	 */
	public void setPriorityPayment(Boolean priorityPayment) {
		if (null != priorityPayment) {
			this.priorityPayment = priorityPayment;
		}
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str
	 *            the str
	 * @return true, if is null or empty
	 */
	public static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;

		return result;
	}

	/**
	 * Gets the full name.
	 *
	 * @return the full name
	 */
	public String getFullName() {
		StringBuilder name = new StringBuilder();
		if ("Corporate".equals(getType())) {
			name.append(getCompanyName());
		}
		else {
			if (!isNullOrEmpty(getFirstName()))
				name.append(getFirstName());
			if (!isNullOrEmpty(getMiddleName())) {
				if (name.length() > 0) {
					name.append(' ');
				}
				name.append(getMiddleName());
			}
			if (!isNullOrEmpty(getLastName())) {
				if (name.length() > 0) {
					name.append(' ');
				}
				name.append(getLastName());
			}
		}
		return name.toString();
	}

	/**
	 * Gets the atlas bene acc number.
	 *
	 * @return the atlas bene acc number
	 */
	public String getAtlasBeneAccNumber() {
		String atlasBeneAccNumber = "";
		List<WrapperPayeePaymentMethod> payeePaymentMethod = getPayeePaymentMethodList();
		if (null != payeePaymentMethod) {
			for (WrapperPayeePaymentMethod payeePayment : payeePaymentMethod) {
				if (null != payeePayment && null != payeePayment.getPayeeBank()) {
					WrapperPayeeBank payeeBank =  payeePayment.getPayeeBank();
					atlasBeneAccNumber = createAtlasBeneAccNumber(payeeBank);
				}
			}
		}
		return atlasBeneAccNumber;
	}
	
	private String createAtlasBeneAccNumber(WrapperPayeeBank payeeBank) {
		StringBuilder atlasBeneAccNumber = new StringBuilder();
		
		//swiftCode
		if (null != payeeBank.getBankBIC()) {
			atlasBeneAccNumber.append(payeeBank.getBankBIC());
		}
		
		//beneAccNumber
		if (null != payeeBank.getAccountNumber()) {
			atlasBeneAccNumber.append(payeeBank.getAccountNumber());
		}
		else if (null != payeeBank.getIban()) {
			atlasBeneAccNumber.append(payeeBank.getIban());
		}
		
		//intraCountryCode
		if (null != payeeBank.getBankIntraCountryCode()) {
			atlasBeneAccNumber.append(payeeBank.getBankIntraCountryCode());
		}

		return atlasBeneAccNumber.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Payee [id=");
		builder.append(id);
		builder.append(", legacyPayeeID=");
		builder.append(legacyPayeeID);
		builder.append(", type=");
		builder.append(type);
		builder.append(", companyName=");
		builder.append(companyName);
		builder.append(", firstName=");
		builder.append(firstName);
		builder.append(", middleName=");
		builder.append(middleName);
		builder.append(", lastName=");
		builder.append(lastName);
		builder.append(", nickName=");
		builder.append(nickName);
		builder.append(", email=");
		builder.append(email);
		builder.append(", workPhoneNumber=");
		builder.append(workPhoneNumber);
		builder.append(", mobilePhoneNumber=");
		builder.append(mobilePhoneNumber);
		builder.append(", mailingStreet=");
		builder.append(mailingStreet);
		builder.append(", mailingCity=");
		builder.append(mailingCity);
		builder.append(", mailingState=");
		builder.append(mailingState);
		builder.append(", mailingPostCode=");
		builder.append(mailingPostCode);
		builder.append(", customerNote=");
		builder.append(customerNote);
		builder.append(", reference=");
		builder.append(reference);
		builder.append(", customerCanReceivePayment=");
		builder.append(customerCanReceivePayment);
		builder.append(", self=");
		builder.append(self);
		builder.append(", sourceApplication=");
		builder.append(sourceApplication);
		builder.append(", defaultBankChargeType=");
		builder.append(defaultBankChargeType);
		builder.append(", priorityPayment=");
		builder.append(priorityPayment);
		builder.append(", active=");
		builder.append(active);
		builder.append(", deleted=");
		builder.append(deleted);
		builder.append(", organizationCode=");
		builder.append(organizationCode);
		builder.append(", countryCode=");
		builder.append(countryCode);
		builder.append(", notificationTypeLookupValueCode=");
		builder.append(notificationTypeLookupValueCode);
		builder.append(", purposeOfPayment=");
		builder.append(purposeOfPayment);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", isUsedInTransaction=");
		builder.append(isUsedInTransaction);
		builder.append(", payeePaymentMethodList=");
		builder.append(payeePaymentMethodList);
		builder.append(", organizationName=");
		builder.append(organizationName);
		builder.append(", countryName=");
		builder.append(countryName);
		builder.append(']');
		return builder.toString();
	}

}
