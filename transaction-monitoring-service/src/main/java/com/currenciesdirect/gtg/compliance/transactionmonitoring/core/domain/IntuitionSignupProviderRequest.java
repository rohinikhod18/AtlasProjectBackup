package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionSignupProviderRequest.
 */
@Getter 
@Setter
public class IntuitionSignupProviderRequest implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;	
	
	/** The trade acc number. */
	@JsonProperty(value = "trade_acc_number")
	private String tradeAccNumber;
	
	/** The reg date time. */
	@JsonProperty(value = "reg_date_time")
	private String regDateTime;
	
	/** The channel. */
	@JsonProperty(value = "channel")
	private String channel;
	
	/** The act name. */
	@JsonProperty(value = "act_name")
	private String actName;
	
	/** The country interest. */
	@JsonProperty(value = "country_interest")
	private String countryInterest;
	
	/** The trade name. */
	@JsonProperty(value = "trade_name")
	private String tradeName;
	
	/** The trasaction purpose. */
	@JsonProperty(value = "trasaction_purpose")
	private String trasactionPurpose;
	
	/** The op country. */
	@JsonProperty(value = "op_country")
	private String opCountry;
	
	/** The service req. */
	@JsonProperty(value = "service_req")
	private String serviceReq;
	
	/** The txn value. */
	@JsonProperty(value = "txn_value")
	private String txnValue;
	
	/** The source of fund. */
	@JsonProperty(value = "source_of_fund")
	private String sourceOfFund;
	
	/** The source. */
	@JsonProperty(value = "source")
	private String source;
	
	/** The sub source. */
	@JsonProperty(value = "sub_source")
	private String subSource;
	
	/** The referral. */
	@JsonProperty(value = "referral")
	private String referral;
	
	/** The referral text. */
	@JsonProperty(value = "referral_text")
	private String referralText;
	
	/** The extended referral. */
	@JsonProperty(value = "extended_referral")
	private String extendedReferral;
	
	/** The ad campaign. */
	@JsonProperty(value = "ad_campaign")
	private String adCampaign;
	
	/** The keywords. */
	@JsonProperty(value = "keywords")
	private String keywords;
	
	/** The search engine. */
	@JsonProperty(value = "search_engine")
	private String searchEngine;
	
	/** The affiliate name. */
	@JsonProperty(value = "affiliate_name")
	private String affiliateName;
	
	/** The reg mode. */
	@JsonProperty(value = "reg_mode")
	private String regMode;
	
	/** The organization legal entity. */
	@JsonProperty(value = "organization_legal_entity")
	private String organizationLegalEntity;
	
	/** The etailer. */
	@JsonProperty(value = "etailer")
	private String etailer;
	
	/** The etv band. */
	@JsonProperty(value = "ETVBand")
	private String etvBand;
	
	/** The conversion flag. */
	@JsonProperty(value = "conversionFlag")
	private String conversionFlag;
	
	/** The conversion probability. */
	@JsonProperty(value = "conversionProbability")
	private String conversionProbability;
	
	/** The conversion accountStatus. */
	@JsonProperty(value = "Account_Status")
	private String accountStatus;
	
	/** The compliance log. */
	@JsonProperty(value = "compliance_log")
	private String complianceLog;
	
	/** The cust type. */
	@JsonProperty(value = "cust_type")
	private String custType;
	
	/** The buying currency. */
	@JsonProperty(value = "buying_currency")
	private String buyingCurrency;
	
	/** The selling currency. */
	@JsonProperty(value = "selling_currency")
	private String sellingCurrency;

	@JsonProperty(value = "isCardApply")
	private String isCardApply;

	@JsonProperty(value = "date_last_contact_added")
	private String lastContactAddDate;
	
	//AT-5127
	@JsonProperty(value = "Registration_load_criteria")
	private String registrationLoadCriteria;

	/** The account. */
	@JsonProperty(value = "Account")
	private List<IntuitionSignupAccountProviderRequest> account;
	
	/** The cfxLegalEntity. */
	@JsonProperty(value = "CFX_Legal_Entity")
	private List<IntuitionSignupCFXLegalEntityProviderRequest> cfxLegalEntity;
	
	/** The contacts. */
	@JsonProperty(value = "Contact")
	private List<IntuitionSignupContactProviderRequest> contacts;
	
	/** The IPDevice. */
	@JsonProperty(value = "IP_Device")
	private List<IntuitionSignupIPDeviceProviderRequest> ipDevice;
	
	/** The IPDevice. */
	@JsonProperty(value = "Cards_Data")
	private List<IntuitionSignupCardProviderRequest> card;
}
