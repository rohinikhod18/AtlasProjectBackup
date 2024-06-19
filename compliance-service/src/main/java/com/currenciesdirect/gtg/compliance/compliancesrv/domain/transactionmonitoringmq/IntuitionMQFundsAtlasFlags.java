package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IntuitionMQFundsAtlasFlags {

	@JsonProperty(value = "update_status")
	private String updateStatus;
	
	@JsonProperty(value = "blacklist")
	private String blacklist;
	
	@JsonProperty(value = "country_check")
	private String countryCheck;
	
	@JsonProperty(value = "sanctions_contact")
	private String sanctionsContact;
	
	@JsonProperty(value = "sanctions_beneficiary")
	private String sanctionsBeneficiary;
	
	@JsonProperty(value = "sanctions_bank")
	private String sanctionsBank;
	
	@JsonProperty(value = "sanctions_3rd_party")
	private String sanctions3rdParty;
	
	@JsonProperty(value = "payment_reference_check")
	private String paymentReferenceCheck;
	
	@JsonProperty(value = "fraud_predict")
	private String fraudPredict;
	
	@JsonProperty(value = "custom_check")
	private String customCheck;
	
	@JsonProperty(value = "atlas_STP_codes")
	private String stpCodes;
}
