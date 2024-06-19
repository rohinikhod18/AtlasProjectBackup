package com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author prashant.verma
 */
public class KafkaFailedTxRequestAudit implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	private Long auditId;

	private String messageId;

	private String message;

	private String topic;

	private String status;

	private Timestamp createdOn;

	private Timestamp updatedOn;

	private String errorMessage;

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long id) {
		this.auditId = id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "KafkaFailedAuditRequest [id=" + auditId + ", messageId=" + messageId + ", message=" + message + ", topic="
				+ topic + ", status=" + status + ", createdOn=" + createdOn + ", updatedOn=" + updatedOn
				+ ", errorMessage=" + errorMessage + "]";
	}
}