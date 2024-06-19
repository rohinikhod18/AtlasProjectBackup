/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.io.Serializable;
import java.util.List;

/**
 * The Class KYCContactRequest.
 *
 * @author manish
 */
public class KYCContactRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The personal details. */
	private PersonalDetails personalDetails;

	/** The address. */
	private Address address;

	/** The phone. */
	private List<Phone> phone;

	/** The identification. */
	private List<Identification> identification;

	/** The request type. */
	private String requestType;

	/** The contact SF id. */
	private String contactSFId;

	/** The id. */
	private Integer id;

	/**
	 * Gets the personal details.
	 *
	 * @return the personal details
	 */
	public PersonalDetails getPersonalDetails() {
		return personalDetails;
	}

	/**
	 * Sets the personal details.
	 *
	 * @param personalDetails
	 *            the new personal details
	 */
	public void setPersonalDetails(PersonalDetails personalDetails) {
		this.personalDetails = personalDetails;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address
	 *            the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public List<Phone> getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone
	 *            the new phone
	 */
	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}

	/**
	 * Gets the identification.
	 *
	 * @return the identification
	 */
	public List<Identification> getIdentification() {
		return identification;
	}

	/**
	 * Sets the identification.
	 *
	 * @param identification
	 *            the new identification
	 */
	public void setIdentification(List<Identification> identification) {
		this.identification = identification;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType
	 *            the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the contact SF id.
	 *
	 * @return the contact SF id
	 */
	public String getContactSFId() {
		return contactSFId;
	}

	/**
	 * Sets the contact SF id.
	 *
	 * @param contactSFId
	 *            the new contact SF id
	 */
	public void setContactSFId(String contactSFId) {
		this.contactSFId = contactSFId;
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
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KYCContactRequest [personalDetails=" + personalDetails + ", address=" + address + ", phone=" + phone
				+ ", identification=" + identification + ", requestType=" + requestType + ", contactSFId=" + contactSFId
				+ ", id=" + id + "]";
	}

}
