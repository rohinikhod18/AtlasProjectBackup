package com.currenciesdirect.gtg.compliance.commons.domain.titan.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class TitanGetPaymentStatusTradeRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trade account number. */
	@ApiModelProperty(value = "The trade account number", required = true, example = "0201085005048284")
	@JsonProperty("trade_account_number")
	private String tradeAccountNumber;

	/** The contract number. */
	@ApiModelProperty(value = "The Trade Identification number", required = true, example = "0201085005048284-000000001")
	@JsonProperty("contract_number")
	private String contractNumber;

	/**
	 * @return the tradeAccountNumber
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

}
