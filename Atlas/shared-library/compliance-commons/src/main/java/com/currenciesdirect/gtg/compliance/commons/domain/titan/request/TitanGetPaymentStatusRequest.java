package com.currenciesdirect.gtg.compliance.commons.domain.titan.request;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class TitanGetPaymentStatusRequest extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the request originated", required = true)
	@JsonProperty("org_code")
	private String orgCode;

	/** The source application. */
	@ApiModelProperty(value = "The source application where the request originated", required = true)
	@JsonProperty("source_application")
	private String sourceApplication;

	/** The request type. */
	@ApiModelProperty(value = "The request type (FundsIn/FundsOut)", required = true)
	@JsonProperty("request_type")
	private String requestType;

	@ApiModelProperty(value = "Object containing details of the Funds-In trade", required = true)
	@JsonProperty("trade")
	private TitanGetPaymentStatusTradeRequest trade;

	/** The trade payment id. */
	private Integer tradePaymentId;

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
	 * @return the sourceApplication
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * @param sourceApplication the sourceApplication to set
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * @return the requestType
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * @param requestType the requestType to set
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the trade
	 */
	public TitanGetPaymentStatusTradeRequest getTrade() {
		return trade;
	}

	/**
	 * @param trade the trade to set
	 */
	public void setTrade(TitanGetPaymentStatusTradeRequest trade) {
		this.trade = trade;
	}

	/**
	 * @return the tradePaymentId
	 */
	public Integer getTradePaymentId() {
		return tradePaymentId;
	}

	/**
	 * @param tradePaymentId the tradePaymentId to set
	 */
	public void setTradePaymentId(Integer tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

}
