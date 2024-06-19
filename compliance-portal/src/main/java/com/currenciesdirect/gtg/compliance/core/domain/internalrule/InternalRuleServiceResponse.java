/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.internalrule;

/**
 * The Class InternalRuleServiceResponse.
 *
 * @author Rajesh
 */
public class InternalRuleServiceResponse {

	/** The aurora contact id. */
	private String auroraContactId;

	/** The id. */
	private Integer id;

	/** The name. */
	private String name;

	/** The email. */
	private String email;

	/** The contact status. */
	private String contactStatus;

	/** The global check. */
	private GlobalCheckResponse globalCheck;

	/** The blacklist. */
	private BlacklistResponse blacklist;

	/** The ip check. */
	private IpResponse ipCheck;

	/** The entity type. */
	private String entityType;

	/**
	 * Gets the aurora contact id.
	 *
	 * @return the aurora contact id
	 */
	public String getAuroraContactId() {
		return auroraContactId;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
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
	 * Gets the global check.
	 *
	 * @return the global check
	 */
	public GlobalCheckResponse getGlobalCheck() {
		return globalCheck;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public BlacklistResponse getBlacklist() {
		return blacklist;
	}

	/**
	 * Gets the ip check.
	 *
	 * @return the ip check
	 */
	public IpResponse getIpCheck() {
		return ipCheck;
	}

	/**
	 * Sets the aurora contact id.
	 *
	 * @param auroraContactId
	 *            the new aurora contact id
	 */
	public void setAuroraContactId(String auroraContactId) {
		this.auroraContactId = auroraContactId;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Sets the email.
	 *
	 * @param email
	 *            the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Sets the global check.
	 *
	 * @param globalCheck
	 *            the new global check
	 */
	public void setGlobalCheck(GlobalCheckResponse globalCheck) {
		this.globalCheck = globalCheck;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist
	 *            the new blacklist
	 */
	public void setBlacklist(BlacklistResponse blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Sets the ip check.
	 *
	 * @param ipCheck
	 *            the new ip check
	 */
	public void setIpCheck(IpResponse ipCheck) {
		this.ipCheck = ipCheck;
	}

	/**
	 * Gets the contact status.
	 *
	 * @return the contact status
	 */
	public String getContactStatus() {
		return contactStatus;
	}

	/**
	 * Sets the contact status.
	 *
	 * @param contactStatus
	 *            the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
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

	/**
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType
	 *            the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

}
