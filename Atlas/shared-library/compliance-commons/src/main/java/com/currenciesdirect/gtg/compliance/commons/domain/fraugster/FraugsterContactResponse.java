package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

/**
 * The Class FraugsterContactResponse.
 */
public class FraugsterContactResponse extends FraugsterBaseResponse{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The provider name. */
	private String providerName;
	
	/** The provider method. */
	private String providerMethod;
	
	/** The contact id. */
	private Integer contactId;
	
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
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * Sets the provider name.
	 *
	 * @param providerName the new provider name
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	/**
	 * Gets the provider method.
	 *
	 * @return the provider method
	 */
	public String getProviderMethod() {
		return providerMethod;
	}

	/**
	 * Sets the provider method.
	 *
	 * @param providerMethod the new provider method
	 */
	public void setProviderMethod(String providerMethod) {
		this.providerMethod = providerMethod;
	}	

}

