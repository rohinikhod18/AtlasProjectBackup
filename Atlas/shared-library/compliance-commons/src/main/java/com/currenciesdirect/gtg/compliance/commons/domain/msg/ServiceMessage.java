package com.currenciesdirect.gtg.compliance.commons.domain.msg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ServiceMessage.
 */
public abstract class ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The correlation ID. */
	@JsonIgnore
	private  UUID correlationID;
	
	/** The audit info. */
	@JsonIgnore
	protected AuditInfo auditInfo;
	
	/** The additional attributes. */
	@JsonIgnore
	protected HashMap<String, Object> additionalAttributes;
	
	@ApiModelProperty(value = "Originating Source ID, a unique global system transaction id which provides traceability of a request from source through the systems", example = "d8d0fde0-b207-dffb-eeb7-e2c76fea6b1b",  required = true)
	@JsonProperty(value="osr_id")
	protected String osrId;

	/**
	 * Instantiates a new service message.
	 */
	public ServiceMessage() {
		auditInfo = new AuditInfo();
		additionalAttributes = new HashMap<>();
	}
	
	/**
	 * Gets the audit info.
	 *
	 * @return the audit info
	 */
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	
	/**
	 * Gets the additional attributes.
	 *
	 * @return the additional attributes
	 */
	public Map<String, Object> getAdditionalAttributes() {
		return additionalAttributes;
	}

	/**
	 * Adds the attribute.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addAttribute(String key, Object value) {
		additionalAttributes.put(key, value);
	}

	/**
	 * Gets the additional attribute.
	 *
	 * @param key the key
	 * @return the additional attribute
	 */
	public Object getAdditionalAttribute(String key) {
		return additionalAttributes.get(key);
	}
	
	/**
	 * Gets the additional attribute.
	 *
	 * @param <T> the generic type
	 * @param key the key
	 * @param clazz the clazz
	 * @return the additional attribute
	 */
	@SuppressWarnings("squid:S1172")
	public <T> T getAdditionalAttribute(String key,Class<T> clazz) {
		return (T) additionalAttributes.get(key);
	}

	/**
	 * Gets the correlation ID.
	 *
	 * @return the correlation ID
	 */
	public UUID getCorrelationID() {
		return correlationID;
	}

	/**
	 * Sets the correlation ID.
	 *
	 * @param correlationID the new correlation ID
	 */
	public void setCorrelationID(UUID correlationID) {
		this.correlationID = correlationID;
	}
	
	/**
	 * Clear.
	 */
	public void clear() {
		if(additionalAttributes!=null){
			additionalAttributes.clear();
		}
		additionalAttributes = null;
	}

	/**
	 * @return the osrId
	 */
	public String getOsrId() {
		return osrId;
	}

	/**
	 * @param osrId the osrId to set
	 */
	public void setOsrId(String osrId) {
		this.osrId = osrId;
	}
	
}