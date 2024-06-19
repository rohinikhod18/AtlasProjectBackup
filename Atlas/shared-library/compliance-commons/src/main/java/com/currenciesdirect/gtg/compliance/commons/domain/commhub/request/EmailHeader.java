package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class EmailHeader.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailHeader {

	/** The customer type. */
	@JsonProperty("customer_type")
	private String customerType;

    /** The notification type. */
	@JsonProperty("notification_type")
    private String[] notificationType;

    /** The retry timeout. */
	@JsonProperty("retry_timeout")
    private String retryTimeout;

    /** The partner. */
	@JsonProperty("partner")
    private String partner;

    /** The cust number. */
	@JsonProperty("cust_number")
    private String custNumber;

    /** The source system. */
	@JsonProperty("source_system")
    private String sourceSystem;

    /** The legal entity. */
	@JsonProperty("legal_entity")
    private String legalEntity;

    /** The state. */
	@JsonProperty("state")
    private String state;

    /** The event. */
	@JsonProperty("event")
    private String event;

    /** The locale. */
	@JsonProperty("locale")
    private String locale;

    /** The org code. */
	@JsonProperty("org_code")
    private String orgCode;

    /** The osr id. */
	@JsonProperty("osr_id")
    private String osrId;

	/**
	 * @return the customerType
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * @param customerType the customerType to set
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the notificationType
	 */
	public String[] getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String[] notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the retryTimeout
	 */
	public String getRetryTimeout() {
		return retryTimeout;
	}

	/**
	 * @param retryTimeout the retryTimeout to set
	 */
	public void setRetryTimeout(String retryTimeout) {
		this.retryTimeout = retryTimeout;
	}

	/**
	 * @return the partner
	 */
	public String getPartner() {
		return partner;
	}

	/**
	 * @param partner the partner to set
	 */
	public void setPartner(String partner) {
		this.partner = partner;
	}

	/**
	 * @return the custNumber
	 */
	public String getCustNumber() {
		return custNumber;
	}

	/**
	 * @param custNumber the custNumber to set
	 */
	public void setCustNumber(String custNumber) {
		this.custNumber = custNumber;
	}

	/**
	 * @return the sourceSystem
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * @return the legalEntity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}

	/**
	 * @param legalEntity the legalEntity to set
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale(String locale) {
		this.locale = locale;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
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
