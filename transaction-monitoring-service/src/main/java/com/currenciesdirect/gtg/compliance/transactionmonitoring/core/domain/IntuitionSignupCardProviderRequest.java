package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class IntuitionSignupCardProviderRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
