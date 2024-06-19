package com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class IntuitionPostCardTransactionMQRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The card input mode. */
	@JsonProperty(value = "Card_Input_mode")
	public String cardInputMode;
	
	/** The card input mode desc. */
	@JsonProperty(value = "Card_Input_mode_Desc")
	public String cardInputModeDesc;
	
	/** The card present indicator. */
	@JsonProperty(value = "Card_Present_Ind")
	public String cardPresentIndicator;
	
	/** The card present description. */
	@JsonProperty(value = "Card_present_description")
	public String cardPresentDescription;
	
	/** The txn CCY. */
	@JsonProperty(value = "Txn_ccy")
	public String txnCCY;

	/** The txn amount. */
	@JsonProperty(value = "Txn_Amount")
	public Double txnAmount;
	
	/** The effective amount. */
	@JsonProperty(value = "Effective_Amount")
	public Double effectiveAmount;
	
	/** The additional amount type. */
	@JsonProperty(value = "Addtnl_Amount_Type")
	public String additionalAmountType;
	
	/** The merchant name. */
	@JsonProperty(value = "Merchant_name")
	public String merchantName;
	
	/** The merchant name other. */
	@JsonProperty(value = "Merchant_name_other")
	public String merchantNameOther;
	
	/** The m CC code description. */
	@JsonProperty(value = "MCC_code_description")
	public String mCCCodeDescription;
	
	/** The merchant country code. */
	@JsonProperty(value = "Merchant_Country_Code")
	public String merchantCountryCode;
	
	/** The merchant postcode. */
	@JsonProperty(value = "Merchant_Postcode")
	public String merchantPostcode;
	
	/** The merchant region. */
	@JsonProperty(value = "Merchant_Region")
	public String merchantRegion;
	
	/** The merchant address. */
	@JsonProperty(value = "Merchant_Address")
	public String merchantAddress;

	/** The merchant city. */
	@JsonProperty(value = "Merchant_City")
	public String merchantCity;

	/** The merchant contact. */
	@JsonProperty(value = "Merchant_contact")
	public String merchantContact;

	/** The merchant second contact. */
	@JsonProperty(value = "Merchant_second_contact")
	public String merchantSecondContact;

	/** The merchant website. */
	@JsonProperty(value = "Merchant_Website")
	public String merchantWebsite;
	
	/** The g PS decision code. */
	@JsonProperty(value = "GPS_Decision_Code")
	public String gPSDecisionCode;
	
	/** The g PS decision code description. */
	@JsonProperty(value = "GPS_Decision_Code_Description")
	public String gPSDecisionCodeDescription;
	
	//AT-5371
	/** The pt wallet. */
	@JsonProperty(value = "PT_Wallet")
	public String ptWallet;
	
	/** The pt device type. */
	@JsonProperty(value = "PT_DeviceType")
	public String ptDeviceType;
	
	/** The pt device IP. */
	@JsonProperty(value = "PT_DeviceIP")
	public String ptDeviceIP;
	
	/** The pt device ID. */
	@JsonProperty(value = "PT_DeviceID")
	public String ptDeviceID;

}
