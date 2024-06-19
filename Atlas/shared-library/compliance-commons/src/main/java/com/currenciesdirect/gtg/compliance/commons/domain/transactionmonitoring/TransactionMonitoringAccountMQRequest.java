package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class TransactionMonitoringAccountMQRequest extends ServiceMessage implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trade account number. */
	@ApiModelProperty(value = "Trade account number", required = true)
	@JsonProperty(value = "tradeAccountNumber")
	private String tradeAccountNumber;

	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}
}
