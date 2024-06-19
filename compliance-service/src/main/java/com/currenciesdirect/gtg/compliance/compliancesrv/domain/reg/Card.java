package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Card implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "The titan account number", required = true)
	@JsonProperty(value="titanAccountNumber")
	private String tradeAccountNumber;
	
	@ApiModelProperty(value = "The card id", required = true)
	@JsonProperty(value="cardID")
	private String cardID;
	
	@ApiModelProperty(value = "The cardStatus")
	@JsonProperty(value="cardStatusFlag")
	private String cardStatusFlag;

	@ApiModelProperty(value = "The Date card dispatched")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateCardDispatched")
	private String dateCardDispatched;

	@ApiModelProperty(value = "The Date card activated")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateCardActivated")
	private String dateCardActivated;

	@ApiModelProperty(value = "The Date card frozen")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateCardFrozen")
	private String dateCardFrozen;

	@ApiModelProperty(value = "The Freeze reason")
	@JsonProperty(value="freezeReason")
	private String freezeReason;

	@ApiModelProperty(value = "The Date card unfrozen")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateCardUnfrozen")
	private String dateCardUnfrozen;

	@ApiModelProperty(value = "The Date card blocked")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateCardBlocked")
	private String dateCardBlocked;

	@ApiModelProperty(value = "The Reason for block")
	@JsonProperty(value="reasonForBlock")
	private String reasonForBlock;

	@ApiModelProperty(value = "The Date card unblocked")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateCardUnblocked")
	private String dateCardUnblocked;

	@ApiModelProperty(value = "The Date last card PIN view")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateLastCardPinView")
	private String dateLastCardPinView;

	@ApiModelProperty(value = "The Date last card PAN view")
	@JsonFormat(pattern = "yyyy-MM-dd")
	@JsonProperty(value="dateLastCardPanView")
	private String dateLastCardPanView;

	@JsonProperty(value="eventType")
	private String eventType;
	
	@JsonProperty(value="isCardActive")
	private Boolean isCardActive;

	@JsonProperty(value="contactId")
	private Integer contactId;

}
