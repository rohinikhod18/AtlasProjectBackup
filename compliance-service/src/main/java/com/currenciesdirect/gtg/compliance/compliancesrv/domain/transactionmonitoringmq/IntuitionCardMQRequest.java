package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IntuitionCardMQRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6760036199941502165L;
	
	@JsonProperty(value="cardID")
	private String cardID;

	@JsonProperty(value = "card_status_flag")
	private String cardStatusFlag;
	
	@JsonProperty(value = "date_card_dispatched")
	private String dateCardDispatched;
	
	@JsonProperty(value = "date_card_activated")
	private String dateCardActivated;
	
	@JsonProperty(value = "date_card_frozen")
	private String dateCardFrozen;
	
	@JsonProperty(value = "freeze_reason")
	private String freezeReason;
	
	@JsonProperty(value = "date_card_unfrozen")
	private String dateCardUnfrozen;
	
	@JsonProperty(value = "date_card_blocked")
	private String dateCardBlocked;
	
	@JsonProperty(value = "reason_for_block")
	private String reasonForBlock;
	
	@JsonProperty(value = "date_card_unblocked")
	private String dateCardUnblocked;
	
	@JsonProperty(value = "date_last_card_pin_view")
	private String dateLastCardPinView;

	@JsonProperty(value = "date_last_card_pan_view")
	private String dateLastCardPanView;
	
}
