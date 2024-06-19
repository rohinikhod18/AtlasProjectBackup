package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionFundsDebitCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty(value = "debtor_name")
	private String debtorName;
	
	@JsonProperty(value = "debtor_account_number")
	private String debtorAccountNumber;
	
	@JsonProperty(value = "cc_first_name")
	private String ccFirstName;
	
	@JsonProperty(value = "bill_ad_zip")
	private String billAdZip;
	
	@JsonProperty(value = "country_of_fund")
	private String countryOfFund;
	
	@JsonProperty(value = "is_threeds")
	private String isThreeds;
	
	@JsonProperty(value = "avs_result")
	private String avsResult;
	
	@JsonProperty(value = "debit_card_added_date")
	private String debitCardAddedDate;
	
	@JsonProperty(value = "message")
	private String message;
	
	@JsonProperty(value = "RGID")
	private String RGID;
	
	@JsonProperty(value = "tScore")
	private Double tScore;
	
	@JsonProperty(value = "tRisk")
	private String tRisk;
	
	@JsonProperty(value = "ds_outcome")
	private String dsOutcome;
	
	@JsonProperty(value = "fraudsight_risk_level")
	private String fraudsightRiskLevel;
	
	@JsonProperty(value = "fraudsight_reason")
	private String fraudsightReason;
	
	@JsonProperty(value = "CVC_CVV_Result")
	private String CVVCVCResult;
}
