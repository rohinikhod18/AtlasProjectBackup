package com.currenciesdirect.gtg.compliance.commons.domain.msg;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.currenciesdirect.gtg.compliance.commons.domain.IResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ServiceMessageResponse.
 */
public abstract class ServiceMessageResponse implements Serializable, IResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The correlation ID. */
	@JsonIgnore
	private UUID correlationID;

	/** The audit info. */
	@JsonIgnore
	protected AuditInfo auditInfo;

	/** The additional attributes. */
	@JsonIgnore
	protected HashMap<String, Object> additionalAttributes;

	/** The error code. */
	@ApiModelProperty(value = "The error code (when there is an error)", example = "", required = true)
	protected String errorCode;

	/** The error description. */
	@ApiModelProperty(value = "The error description (when there is an error)", example = "", required = true)
	protected String errorDescription;
	
	/** The osr ID. */
	@ApiModelProperty(value = "Originating Source ID, a unique global system transaction id which provides traceability of a request from source through the systems", example = "d8d0fde0-b207-dffb-eeb7-e2c76fea6b1b",  required = true)
	@JsonProperty(value="osr_id")
	protected String osrID;

	/**
	 * Instantiates a new service message response.
	 */
	public ServiceMessageResponse() {
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
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 */
	public void addAttribute(String key, Object value) {
		additionalAttributes.put(key, value);
	}

	/**
	 * Gets the additional attribute.
	 *
	 * @param key
	 *            the key
	 * @return the additional attribute
	 */
	public Object getAdditionalAttribute(String key) {
		return additionalAttributes.get(key);
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
	 * @param correlationID
	 *            the new correlation ID
	 */
	public void setCorrelationID(UUID correlationID) {
		this.correlationID = correlationID;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		if (additionalAttributes != null) {
			additionalAttributes.clear();
		}
		additionalAttributes = null;
	}

	/**
	 * @return the osrID
	 */
	public String getOsrID() {
		return osrID;
	}

	/**
	 * @param osrID the osrID to set
	 */
	@JsonProperty(value="osr_id")
	public void setOsrID(String osrID) {
		this.osrID = osrID;
	}
}