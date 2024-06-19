/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.globalcheck;

/**
 * The Class StateRuleRequest.
 *
 * @author manish
 */
public class StateRuleRequest {

	/** The id. */
	private String id;

	/** The contact id. */
	private String contactId;

	/** The org code. */
	private String orgCode;

	/** The iso alpha 3 country code. */
	private String isoAlpha3CountryCode;
	
	/** The org legal entity. */
	private String orgLegalEntity;

	/** The state. */
	private String state;

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Gets the iso alpha 3 country code.
	 *
	 * @return the iso alpha 3 country code
	 */
	public String getIsoAlpha3CountryCode() {
		return isoAlpha3CountryCode;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Sets the iso alpha 3 country code.
	 *
	 * @param isoAlpha3CountryCode
	 *            the new iso alpha 3 country code
	 */
	public void setIsoAlpha3CountryCode(String isoAlpha3CountryCode) {
		this.isoAlpha3CountryCode = isoAlpha3CountryCode;
	}
	
	/**
	 * Gets the org legal entity.
	 *
	 * @return the org legal entity
	 */
	public String getOrgLegalEntity() {
		return orgLegalEntity;
	}

	/**
	 * Sets the org legal entity.
	 *
	 * @param orgLegalEntity
	 *            the new org legal entity
	 */
	public void setOrgLegalEntity(String orgLegalEntity) {
		this.orgLegalEntity = orgLegalEntity;
	}

	/**
	 * Sets the state.
	 *
	 * @param state
	 *            the new state
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public String getContactId() {
		return contactId;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId
	 *            the new contact id
	 */
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
}
