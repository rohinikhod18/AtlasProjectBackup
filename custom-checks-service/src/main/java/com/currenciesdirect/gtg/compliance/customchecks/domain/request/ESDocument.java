package com.currenciesdirect.gtg.compliance.customchecks.domain.request;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.currenciesdirect.gtg.compliance.customchecks.util.DocumentTypeResolver;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeResolver;

/**
 * @author bnt
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@JsonTypeResolver(DocumentTypeResolver.class)
public abstract class ESDocument implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("type")
	protected String type;
	@JsonProperty("isDeleted")
	protected Boolean isDeleted = Boolean.FALSE;
	@JsonProperty("org_code")
	protected String orgCode;
	@JsonProperty("source_application")
	protected String sourceApplication;
	@JsonProperty("accId")
	protected Integer accId;
	@JsonProperty("createdOn")
	protected Date createdOn = new Date();
	@JsonIgnore
	public abstract String getCurrencyCode();
	@JsonIgnore
	public abstract Double getBaseAmount();
	
	/** The additional attributes. */
	@JsonIgnore
	protected Map<String, Object> additionalAttributes;
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the isDeleted
	 */
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted==null?Boolean.FALSE:isDeleted;
	}
	
	/**
	 * 
	 * @return The orgCode
	 */
	@JsonProperty("org_code")
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 
	 * @param orgCode
	 *            The org_code
	 */
	@JsonProperty("org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 
	 * @return The sourceApplication
	 */
	@JsonProperty("source_application")
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * 
	 * @param sourceApplication
	 *            The source_application
	 */
	@JsonProperty("source_application")
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}
	/**
	 * @return the accId
	 */
	public Integer getAccId() {
		return accId;
	}
	/**
	 * @param accId the accId to set
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	
	/**
	 * @return
	 */
	public String getCreatedOn() {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(createdOn);
	}
	/**
	 * @param createdOn
	 */
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	/**
	 * @return the additionalAttributes
	 */
	public Map<String, Object> getAdditionalAttributes() {
		return additionalAttributes;
	}
	/**
	 * @param additionalAttributes the additionalAttributes to set
	 */
	public void setAdditionalAttributes(Map<String, Object> additionalAttributes) {
		this.additionalAttributes = additionalAttributes;
	}
}
