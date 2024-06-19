package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailPayloadForTM {
	
	/** The email ids. */
	@JsonProperty("email_id")
    private List<String> emailId;
	
	/** The email id. */
	@JsonProperty("from_email_id")
	private String fromEmilId;
	
	/** The subject. */
	@JsonProperty("subject")
    private String subject;
	
	/** The email content. */
	@JsonProperty("email_content")
    private String emailContent;
	
	/** The reply to email id. */
	@JsonProperty("reply_to_email_id")
	private String replyToEmailId;

	/**
	 * @return the emailId
	 */
	public List<String> getEmailId() {
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(List<String> emailId) {
		this.emailId = emailId;
	}

	/**
	 * @return the fromEmilId
	 */
	public String getFromEmilId() {
		return fromEmilId;
	}

	/**
	 * @param fromEmilId the fromEmilId to set
	 */
	public void setFromEmilId(String fromEmilId) {
		this.fromEmilId = fromEmilId;
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
	 * @return the emailContent
	 */
	public String getEmailContent() {
		return emailContent;
	}

	/**
	 * @param emailContent the emailContent to set
	 */
	public void setEmailContent(String emailContent) {
		this.emailContent = emailContent;
	}

	/**
	 * @return the replyToEmailId
	 */
	public String getReplyToEmailId() {
		return replyToEmailId;
	}

	/**
	 * @param replyToEmailId the replyToEmailId to set
	 */
	public void setReplyToEmailId(String replyToEmailId) {
		this.replyToEmailId = replyToEmailId;
	}
	

}
