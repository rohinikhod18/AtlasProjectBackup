package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ComplianceAccount.
 */
public class ComplianceAccount extends ProfileResponse{

	private String accountSFID;
	private ComplianceStatus acs;
	private List<ComplianceContact> contacts;
	private Integer tradeAccountID;
	private String registeredDate;
	private String registrationInDate;
	private String custType;
	public String getAccountSFID() {
		return accountSFID;
	}

	public void setAccountSFID(String accountSFID) {
		this.accountSFID = accountSFID;
	}

	public ComplianceStatus getAcs() {
		return acs;
	}

	public void setAcs(ComplianceStatus acs) {
		this.acs = acs;
	}

	public List<ComplianceContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<ComplianceContact> contacts) {
		this.contacts = contacts;
	}

	/**
	 * Adds the contact.
	 *
	 * @param contact
	 *            the contact
	 */
	public void addContact(ComplianceContact contact) {
		if (this.contacts == null)
			this.contacts = new ArrayList<>();
		this.contacts.add(contact);
	}

	public Integer getTradeAccountID() {
		return tradeAccountID;
	}

	public void setTradeAccountID(Integer tradeAccountID) {
		this.tradeAccountID = tradeAccountID;
	}

	public String getRegistrationInDate() {
		return registrationInDate;
	}

	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
	}

	public String getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}

	public String getCustType() {
		return custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}
}
