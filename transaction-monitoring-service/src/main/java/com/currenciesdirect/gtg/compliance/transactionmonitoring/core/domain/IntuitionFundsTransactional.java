package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;


import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionFundsTransactional implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "trade_account_number")
	private String tradeAccountNumber;
	
	@JsonProperty(value = "trade_contact_id")
	private String tradeContactId;
	
	@JsonProperty(value = "trx_type")
	private String trxType;
	
	@JsonProperty(value = "purpose_of_trade")
	private String purposeOfTrade;
	
	@JsonProperty(value = "contract_number")
	private String contractNumber;
	
	@JsonProperty(value = "deal_date")
	private String dealDate;
	
	@JsonProperty(value = "third_party_payment")
	private String thirdPartyPayment;
	
	@JsonProperty(value = "buying_amount_base_value")
	private Double buyingAmountBaseValue;

	@JsonProperty(value = "selling_amount_base_value")
	private Double sellingAmountBaseValue;
	
	@JsonProperty(value = "value_date")
	private String valueDate;
	
	@JsonProperty(value = "opi_created_date")
	private String opiCreatedDate;
	
	@JsonProperty(value = "maturity_date")
	private String maturity_date;
	
	@JsonProperty(value = "channel")
	private String channel;
	
	//AT-5337
	@JsonProperty(value = "InitiatingParentContact")
	private String initiatingParentContact;
	
}
