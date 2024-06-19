package com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class OrderCardStatusUpdateRequest extends ServiceMessage implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "The titan account number", required = true)
	@JsonProperty(value="cdCustomerRef")
	private String tradeAccountNumber;
	
	@ApiModelProperty(value = "The card id", required = true)
	@JsonProperty(value="publicToken")
	private String cardId;

	@ApiModelProperty(value = "The contact id", required = true)
	@JsonProperty(value="contactId")
	private String contactId;

	@ApiModelProperty(value = "The eventType")
	@JsonProperty(value="eventType")
	private String eventType;

	@ApiModelProperty(value = "The event date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="eventDate")
	private String eventDate;

	@ApiModelProperty(value = "The event status")
	@JsonProperty(value="eventStatus")
	private String eventStatus;

	@ApiModelProperty(value = "The event reason")
	@JsonProperty(value="eventReason")
	private String eventReason;

	@ApiModelProperty(value = "Is Card Active")
	@JsonProperty(value="isCardActive")
	private Boolean isCardActive;

	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventDate() {
		return eventDate;
	}

	public void setEventDate(String eventDate) {
		this.eventDate = eventDate;
	}

	public String getEventStatus() {
		return eventStatus;
	}

	public void setEventStatus(String eventStatus) {
		this.eventStatus = eventStatus;
	}

	public String getEventReason() {
		return eventReason;
	}

	public void setEventReason(String eventReason) {
		this.eventReason = eventReason;
	}

	public Boolean getIsCardActive() {
		return isCardActive;
	}

	public void setIsCardActive(Boolean isCardActive) {
		this.isCardActive = isCardActive;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
