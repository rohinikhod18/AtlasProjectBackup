package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class EmailPayload.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailPayload {
	
	/** The email id. */
	@JsonProperty("email_id")
	private String emailId;

    /** The email ids. */
	@JsonProperty("email_ids")
    private String[] emailIds;

    /** The cc email id. */
	@JsonProperty("cc_email_id")
    private String ccEmailId;
	
    /** The cc email ids. */
	@JsonProperty("cc_email_ids")
    private String[] ccEmailIds;

	/** The mobile number. */
	@JsonProperty("mobile_number")
    private String mobileNumber;
	
    /** The mobile numbers. */
	@JsonProperty("mobile_numbers")
    private String[] mobileNumbers;

    /** The bcc email id. */
	@JsonProperty("bcc_email_id")
    private String bccEmailId;
	
	/** The bcc email ids. */
	@JsonProperty("bcc_email_ids")
    private String[] bccEmailIds;
	
	/** The subject. */
	@JsonProperty("subject")
    private String subject;

	/** The place holder. */
	@JsonProperty("place_holder")
	private Map<String, String> placeHolder;
	
	/** The attachments. */
	@JsonProperty("attachments")
    private EmailAttachments[] attachments;

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the emailIds
	 */
	public String[] getEmailIds() {
		return emailIds;
	}

	/**
	 * @param emailIds the emailIds to set
	 */
	public void setEmailIds(String[] emailIds) {
		this.emailIds = emailIds;
	}

	/**
	 * @return the attachments
	 */
	public EmailAttachments[] getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public void setAttachments(EmailAttachments[] attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the ccEmailId
	 */
	public String getCcEmailId() {
		return ccEmailId;
	}

	/**
	 * @param ccEmailId the ccEmailId to set
	 */
	public void setCcEmailId(String ccEmailId) {
		this.ccEmailId = ccEmailId;
	}

	/**
	 * @return the mobileNumbers
	 */
	public String[] getMobileNumbers() {
		return mobileNumbers;
	}

	/**
	 * @param mobileNumbers the mobileNumbers to set
	 */
	public void setMobileNumbers(String[] mobileNumbers) {
		this.mobileNumbers = mobileNumbers;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the bccEmailId
	 */
	public String getBccEmailId() {
		return bccEmailId;
	}

	/**
	 * @param bccEmailId the bccEmailId to set
	 */
	public void setBccEmailId(String bccEmailId) {
		this.bccEmailId = bccEmailId;
	}

	/**
	 * @return the placeHolder
	 */
	public Map<String, String> getPlaceHolder() {
		return placeHolder;
	}

	/**
	 * @param attributes the placeHolder to set
	 */
	public void setPlaceHolder(Map<String, String> attributes) {
		this.placeHolder = attributes;
	}

	/**
	 * @return the ccEmailIds
	 */
	public String[] getCcEmailIds() {
		return ccEmailIds;
	}

	/**
	 * @param ccEmailIds the ccEmailIds to set
	 */
	public void setCcEmailIds(String[] ccEmailIds) {
		this.ccEmailIds = ccEmailIds;
	}

	/**
	 * @return the mobileNumber
	 */
	public String getMobileNumber() {
		return mobileNumber;
	}

	/**
	 * @param mobileNumber the mobileNumber to set
	 */
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	/**
	 * @return the bccEmailIds
	 */
	public String[] getBccEmailIds() {
		return bccEmailIds;
	}

	/**
	 * @param bccEmailIds the bccEmailIds to set
	 */
	public void setBccEmailIds(String[] bccEmailIds) {
		this.bccEmailIds = bccEmailIds;
	}
    
 }