package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class SanctionResendRequest.
 */
public class SanctionResendRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	private Integer accountId;

	/** The event id. */
	@ApiModelProperty(value = "The event id", required = true)
	private Integer eventId;

	/** The event type. */
	@ApiModelProperty(value = "The event type", required = true)
	private String eventType;

	/** The entity id. */
	@ApiModelProperty(value = "The entity id", required = true)
	private Integer entityId;

	/** The org code. */
	@ApiModelProperty(value = "The org code", required = true)
	private String orgCode;

	/** The entity type. */
	@ApiModelProperty(value = "The entity type", required = true)
	private String entityType;

	/** The org id. */
	@JsonIgnore
	private Integer orgId;

	/**
	 * Gets the org id.
	 *
	 * @return the org id
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * Sets the org id.
	 *
	 * @param orgId
	 *            the new org id
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId
	 *            the new entity id
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the event id.
	 *
	 * @return the event id
	 */
	public Integer getEventId() {
		return eventId;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public String getEventType() {
		return eventType;
	}

	/**
	 * Sets the event id.
	 *
	 * @param eventId
	 *            the new event id
	 */
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	/**
	 * Sets the event type.
	 *
	 * @param eventType
	 *            the new event type
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

}
