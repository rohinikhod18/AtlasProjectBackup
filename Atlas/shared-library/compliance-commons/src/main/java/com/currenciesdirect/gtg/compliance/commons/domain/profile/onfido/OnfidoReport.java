package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class OnfidoReport.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoReport implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The created at. */
	@ApiModelProperty(value = "The created at datetimestamp", required = true)
	@JsonProperty(value = "created_at")
	private String createdAt;
	
	/** The href. */
	@ApiModelProperty(value = "The href API endpoint to retrieve the report", required = true)
	@JsonProperty(value = "href")
	private String href;
	
	/** The id. */
	@ApiModelProperty(value = "The identifier for the report", required = true)
	@JsonProperty(value = "id")
	private String id;
	
	/** The name. */
	@ApiModelProperty(value = "The name of the report type", required = true)
	@JsonProperty(value = "name")
	private String name;
	
	@ApiModelProperty(value = "The properties associated with the report", required = true)
	@JsonProperty(value = "properties")
	private OnfidoProperties properties;
	
	/** The result. */
	@ApiModelProperty(value = "The result of the verification, i.e. clear, consider, fail", required = true)
	@JsonProperty(value = "result")
	private String result;
	
	/** The status. */
	@ApiModelProperty(value = "The status of the report, e.g. Complete", required = true)
	@JsonProperty(value = "status")
	private String status;
	
	/** The sub result. */
	@ApiModelProperty(value = "The sub_result of the report. It gives a more detailed result for document reports only, and will be null otherwise", required = true)
	@JsonProperty(value = "sub_result")
	private String subResult;
	
	/** The variant. */
	@ApiModelProperty(value = "Variant is a version of a report, e.g. a Facial Similarity Report that can have a 'Standard' (Photo) or 'Video' variant", required = true)
	@JsonProperty(value = "variant")
	private String variant;
	
	/** The onfido breakdown. */
	@ApiModelProperty(value = "An OnFido breakdown object containing the details of the report", required = true)
	@JsonProperty(value = "breakdown")
	private OnfidoBreakdown onfidoBreakdown;

	/**
	 * Gets the created at.
	 *
	 * @return the created at
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * Sets the created at.
	 *
	 * @param createdAt the new created at
	 */
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the href.
	 *
	 * @return the href
	 */
	public String getHref() {
		return href;
	}

	/**
	 * Sets the href.
	 *
	 * @param href the new href
	 */
	public void setHref(String href) {
		this.href = href;
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
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
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
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result the new result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the sub result.
	 *
	 * @return the sub result
	 */
	public String getSubResult() {
		return subResult;
	}

	/**
	 * Sets the sub result.
	 *
	 * @param subResult the new sub result
	 */
	public void setSubResult(String subResult) {
		this.subResult = subResult;
	}

	/**
	 * Gets the variant.
	 *
	 * @return the variant
	 */
	public String getVariant() {
		return variant;
	}

	/**
	 * Sets the variant.
	 *
	 * @param variant the new variant
	 */
	public void setVariant(String variant) {
		this.variant = variant;
	}

	/**
	 * @return the properties
	 */
	public OnfidoProperties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(OnfidoProperties properties) {
		this.properties = properties;
	}

	/**
	 * @return the onfidoBreakdown
	 */
	public OnfidoBreakdown getOnfidoBreakdown() {
		return onfidoBreakdown;
	}

	/**
	 * @param onfidoBreakdown the onfidoBreakdown to set
	 */
	public void setOnfidoBreakdown(OnfidoBreakdown onfidoBreakdown) {
		this.onfidoBreakdown = onfidoBreakdown;
	}
	
}
