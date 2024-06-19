package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class SanctionUpdateData.
 */
public class SanctionUpdateData implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The entity id. */
	@ApiModelProperty(value = "The entity id", required = true)
	private Integer entityId;
	
	/** The event service log id. */
	@ApiModelProperty(value = "The event service log id", required = true)
	private Integer eventServiceLogId;
	
	/** The entity type. */
	@ApiModelProperty(value = "The entity type", required = true)
	private String entityType;
	
	/** The field. */
	@ApiModelProperty(value = "The field", required = true)
	private String field;
	
	/** The value. */
	@ApiModelProperty(value = "The value", required = true)
	private String value;
	
	/** The contact status. */
	@ApiModelProperty(value = "The contact status", required = true)
	private String contactStatus;
	
	/** The bank status. */
	@ApiModelProperty(value = "The bank status", required = true)
	private String bankStatus;
	
	/** The beneficiary status. */
	@ApiModelProperty(value = "The beneficiary status", required = true)
	private String beneficiaryStatus;
	
	/**
	 * Gets the field.
	 *
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * Sets the field.
	 *
	 * @param field the new field
	 */
	public void setField(String field) {
		this.field = field;
	}

	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * @param entityId the new entity id
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the event service log id.
	 *
	 * @return the event service log id
	 */
	public Integer getEventServiceLogId() {
		return eventServiceLogId;
	}

	/**
	 * Sets the event service log id.
	 *
	 * @param eventServiceLogId the new event service log id
	 */
	public void setEventServiceLogId(Integer eventServiceLogId) {
		this.eventServiceLogId = eventServiceLogId;
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
	 * @param entityType the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
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
	 * @param contactStatus the new contact status
	 */
	public void setContactStatus(String contactStatus) {
		this.contactStatus = contactStatus;
	}

	/**
	 * Gets the bank status.
	 *
	 * @return the bank status
	 */
	public String getBankStatus() {
		return bankStatus;
	}

	/**
	 * Sets the bank status.
	 *
	 * @param bankStatus the new bank status
	 */
	public void setBankStatus(String bankStatus) {
		this.bankStatus = bankStatus;
	}

	/**
	 * Gets the beneficiary status.
	 *
	 * @return the beneficiary status
	 */
	public String getBeneficiaryStatus() {
		return beneficiaryStatus;
	}

	/**
	 * Sets the beneficiary status.
	 *
	 * @param beneficiaryStatus the new beneficiary status
	 */
	public void setBeneficiaryStatus(String beneficiaryStatus) {
		this.beneficiaryStatus = beneficiaryStatus;
	}

}
