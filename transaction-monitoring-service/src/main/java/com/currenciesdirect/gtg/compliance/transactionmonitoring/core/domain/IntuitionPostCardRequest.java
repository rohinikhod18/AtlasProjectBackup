package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class IntuitionPostCardRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The trx ID. */
	@JsonProperty(value = "GPS_Txn_ID")
	public String trxID;
	
	/** The g PS datestamp. */
	@JsonProperty(value = "GPS_Date_Timestamp")
	public String gPSDatestamp;

	/** The titan account number. */
	@JsonProperty(value = "Titan_AC_number")
	public String titanAccountNumber;

	/** The card token. */
	@JsonProperty(value = "Card_Token")
	public String cardToken;

	/** The bin. */
	@JsonProperty(value = "Bin")
	public String bin;

	/** The product ID. */
	@JsonProperty(value = "Product_ID")
	public String productID;
	
	/** The acq date. */
	@JsonProperty(value = "Acq_Date_Timestamp")
	public String acqDate;
	
	/** The request type. */
	/*
	 * Transacion_Type change into trx_type - AT-5269
	 * due to intuition change the field name
	 */	
	@JsonProperty(value = "trx_type")
	public String requestType;
	
	/** The txn country. */
	@JsonProperty(value = "Txn_Country")
	public String txnCountry;
	
	/** The multi part ind. */
	@JsonProperty(value = "Pre_Auth_multi_part_Ind")
	public String multiPartInd;
	
	/** The multi part final. */
	@JsonProperty(value = "Pre_Auth_multi_part_Final")
	public String multiPartFinal;
	
	/** The multi part matching reference. */
	@JsonProperty(value = "Pre_Auth_matching_Reference")
	public String multiPartMatchingReference;
	
	/** The bill ccy. */
	@JsonProperty(value = "Bill_ccy")
	public String billCcy;
	
	/** The bill amount. */
	@JsonProperty(value = "Bill_Amount")
	public Double billAmount;
	
	/** The additional amount ccy. */
	@JsonProperty(value = "Addtnl_Amount_ccy")
	public String additionalAmountCcy;
	
	/** The additional amount. */
	@JsonProperty(value = "Addtnl_Amount")
	public Double additionalAmount;
	
	/** The merch ID. */
	@JsonProperty(value = "Merch_ID")
	public String merchID;
	
	/** The m CC code. */
	@JsonProperty(value = "MCC_code")
	public String mCCCode;
	
	/** The acquirer ID. */
	@JsonProperty(value = "Acquirer_ID")
	public String acquirerID;
	
	/** The titan decision code. */
	@JsonProperty(value = "Titan_Decision_Code")
	public String titanDecisionCode;
	
	/** The titan decision description. */
	@JsonProperty(value = "Titan_Decision_Description")
	public String titanDecisionDescription;
	
	/** The card status code. */
	@JsonProperty(value = "Card_Status_Code")
	public String cardStatusCode;
	
	/** The g PSSTIP flag. */
	@JsonProperty(value = "GPS_STIP_flag")
	public String gPSSTIPFlag;
	
	/** The a VS result. */
	@JsonProperty(value = "AVS_Result")
	public String aVSResult;
	
	/** The s CA authentication exemption code. */
	@JsonProperty(value = "SCA_Authentication_exemption_code")
	public String sCAAuthenticationExemptionCode;
	
	/** The s CA authentication exemption description. */
	@JsonProperty(value = "SCA_Authentication_exemption_description")
	public String sCAAuthenticationExemptionDescription;
	
	/** The authentication method. */
	@JsonProperty(value = "Authentication_method")
	public String authenticationMethod;
	
	/** The authentication method desc. */
	@JsonProperty(value = "Authentication_method_Desc")
	public String authenticationMethodDesc;
	
	/** The c VV results. */
	@JsonProperty(value = "CVV_Results")
	public String cVVResults;
	
	/** The p OS terminal. */
	@JsonProperty(value = "POS_Terminal")
	public String pOSTerminal;
	
	/** The p OS datestamp. */
	@JsonProperty(value = "POS_Datestamp")
	public String pOSDatestamp;
	
	/** The g PSPOS capability. */
	@JsonProperty(value = "GPS_POS_Capability")
	public String gPSPOSCapability;
	
	/** The g PSPOS data. */
	@JsonProperty(value = "GPS_POS_Data")
	public String gPSPOSData;
	
	@JsonProperty(value = "Transaction")
	private List<IntuitionPostCardTransactionRequest> transaction;
	
	@JsonProperty(value = "Account")
	private List<IntuitionPostCardAccountRequest> account;
	
	@JsonProperty(value = "Chargeback_Dispute")
	private List<IntuitionPostCardChargebackRequest> chargeback;
	
	@JsonProperty(value = "Device_IP")
	private List<IntuitionPostCardIPDeviceRequest> ipDevice;
}
