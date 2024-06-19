package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionMonitoringPostCardTransactionRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trx ID. */
	@JsonProperty(value = "TrxID")
	public String trxID;

	/** The titan account number. */
	@JsonProperty(value = "titanAccountNumber")
	public String titanAccountNumber;

	/** The card token. */
	@JsonProperty(value = "cardToken")
	public String cardToken;

	/** The txn CCY. */
	@JsonProperty(value = "TxnCCY")
	public String txnCCY;

	/** The txn amount. */
	@JsonProperty(value = "TxnAmount")
	public Double txnAmount;
	
	/** The g PS datestamp. */
	@JsonProperty(value = "GPSDatestamp")
	public String gPSDatestamp;
	
	/** The request type. */
	@JsonProperty(value = "requestType")
	public String requestType;
	
	/** The card input mode. */
	@JsonProperty(value = "cardInputMode")
	public String cardInputMode;
	
	/** The card input mode desc. */
	@JsonProperty(value = "cardInputModeDesc")
	public String cardInputModeDesc;
	
	/** The merch ID. */
	@JsonProperty(value = "merchID")
	public String merchID;
	
	/** The merchant name. */
	@JsonProperty(value = "merchantName")
	public String merchantName;
	
	/** The merchant name other. */
	@JsonProperty(value = "merchantNameOther")
	public String merchantNameOther;
	
	/** The m CC code. */
	@JsonProperty(value = "MCCCode")
	public String mCCCode;
	
	/** The m CC code description. */
	@JsonProperty(value = "MCCCodeDescription")
	public String mCCCodeDescription;
	
	/** The merchant country code. */
	@JsonProperty(value = "merchantCountryCode")
	public String merchantCountryCode;
	
	/** The merchant postcode. */
	@JsonProperty(value = "merchantPostcode")
	public String merchantPostcode;
	
	/** The merchant address. */
	@JsonProperty(value = "merchantAddress")
	public String merchantAddress;
	
	/** The p OS terminal. */
	@JsonProperty(value = "POSTerminal")
	public String pOSTerminal;
	
	/** The bin. */
	@JsonProperty(value = "Bin")
	public String bin;
	
	/** The product ID. */
	@JsonProperty(value = "productID")
	public String productID;
	
	/** The acq date. */
	@JsonProperty(value = "acqDate")
	public String acqDate;
	
	/** The card present indicator. */
	@JsonProperty(value = "Card_Present_Indicator")
	public String cardPresentIndicator;
	
	/** The card present description. */
	@JsonProperty(value = "Card_present_description")
	public String cardPresentDescription;
	
	/** The txn country. */
	@JsonProperty(value = "Txn_Country")
	public String txnCountry;
	
	/** The multi part ind. */
	@JsonProperty(value = "multi_part_Ind")
	public String multiPartInd;
	
	/** The multi part final. */
	@JsonProperty(value = "multi_part_final")
	public String multiPartFinal;
	
	/** The multi part matching reference. */
	@JsonProperty(value = "multi_part_matching_reference")
	public String multiPartMatchingReference;
	
	/** The bill ccy. */
	@JsonProperty(value = "Bill_ccy")
	public String billCcy;
	
	/** The bill amount. */
	@JsonProperty(value = "Bill_Amount")
	public Double billAmount;
	
	/** The effective amount. */
	@JsonProperty(value = "Effective_Amount")
	public Double effectiveAmount;
	
	/** The additional amount type. */
	@JsonProperty(value = "Additional_Amount_Type")
	public String additionalAmountType;
	
	/** The additional amount ccy. */
	@JsonProperty(value = "Additional_Amount_ccy")
	public String additionalAmountCcy;
	
	/** The additional amount. */
	@JsonProperty(value = "Additional_Amount")
	public Double additionalAmount;
	
	/** The consolidated wallet balances. */
	@JsonProperty(value = "Consolidated_Wallet_balances")
	public Double consolidatedWalletBalances;
	
	/** The merchant region. */
	@JsonProperty(value = "Merchant_Region")
	public String merchantRegion;
	
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
	
	/** The acquirer ID. */
	@JsonProperty(value = "Acquirer_ID")
	public String acquirerID;
	
	/** The g PS decision code. */
	@JsonProperty(value = "GPS_Decision_Code")
	public String gPSDecisionCode;
	
	/** The g PS decision code description. */
	@JsonProperty(value = "GPS_Decision_Code_Description")
	public String gPSDecisionCodeDescription;
	
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
	
	/** The device ID. */
	@JsonProperty(value = "device_ID")
	public String deviceID;
	
	/** The continent. */
	@JsonProperty(value = "continent")
	public String continent;
	
	/** The longitude. */
	@JsonProperty(value = "longitude")
	public String longitude;
	
	/** The latitiude. */
	@JsonProperty(value = "latitiude")
	public String latitiude;
	
	/** The region. */
	@JsonProperty(value = "region")
	public String region;
	
	/** The city. */
	@JsonProperty(value = "city")
	public String city;
	
	/** The timezone. */
	@JsonProperty(value = "timezone")
	public String timezone;
	
	/** The organization. */
	@JsonProperty(value = "organization")
	public String organization;
	
	/** The carrier. */
	@JsonProperty(value = "carrier")
	public String carrier;
	
	/** The connection type. */
	@JsonProperty(value = "connection_type")
	public String connectionType;
	
	/** The line speed. */
	@JsonProperty(value = "line_speed")
	public String lineSpeed;
	
	/** The ip routing type. */
	@JsonProperty(value = "ip_routing_type")
	public String ipRoutingType;
	
	/** The country name. */
	@JsonProperty(value = "country_name")
	public String countryName;
	
	/** The country code. */
	@JsonProperty(value = "country_code")
	public String countryCode;
	
	/** The state name. */
	@JsonProperty(value = "state_name")
	public String stateName;
	
	/** The state code. */
	@JsonProperty(value = "state_code")
	public String stateCode;
	
	/** The postal code. */
	@JsonProperty(value = "postal_code")
	public String postalCode;
	
	/** The area code. */
	@JsonProperty(value = "area_code")
	public String areaCode;
	
	/** The anonymizer status. */
	@JsonProperty(value = "anonymizer_status")
	public String anonymizerStatus;
	
	/** The ip address. */
	@JsonProperty(value = "ip_address")
	public String ipAddress;
	
	/** The update status. */
	@JsonProperty(value = "Update_Status")
	public String updateStatus;
	
	/** The update date. */
	@JsonProperty(value = "Update_date")
	public String updateDate;
	
	/** The update amount. */
	@JsonProperty(value = "Update_Amount")
	public Double updateAmount;
	
	/** The chargeback reason. */
	@JsonProperty(value = "Chargeback_reason")
	public String chargebackReason;
	
	/** The p OS datestamp. */
	@JsonProperty(value = "POS_Datestamp")
	public String pOSDatestamp;
	
	/** The g PSPOS capability. */
	@JsonProperty(value = "GPS_POS_Capability")
	public String gPSPOSCapability;
	
	/** The g PSPOS data. */
	@JsonProperty(value = "GPS_POS_Data")
	public String gPSPOSData;

	/** The is present. */
	private Integer isPresent = 0;
	
	//AT-5371
	/** The pt wallet. */
    @JsonProperty(value = "ptWallet")
	private String ptWallet;
   
	/** The pt device type. */
	@JsonProperty(value = "ptDeviceType")
	private String ptDeviceType;

    /** The pt device IP. */
    @JsonProperty(value = "ptDeviceIP")
    private String ptDeviceIP;

    /** The pt device ID. */
    @JsonProperty(value = "ptDeviceID")
    private String ptDeviceID;
    
    /** The pt token. */
    @JsonProperty(value = "ptToken")
	private String ptToken; //Add for AT-5474
	
	/**
	 * @return the trxID
	 */
	public String getTrxID() {
		return trxID;
	}

	/**
	 * @param trxID the trxID to set
	 */
	public void setTrxID(String trxID) {
		this.trxID = trxID;
	}

	/**
	 * @return the titanAccountNumber
	 */
	public String getTitanAccountNumber() {
		return titanAccountNumber;
	}

	/**
	 * @param titanAccountNumber the titanAccountNumber to set
	 */
	public void setTitanAccountNumber(String titanAccountNumber) {
		this.titanAccountNumber = titanAccountNumber;
	}

	/**
	 * @return the cardToken
	 */
	public String getCardToken() {
		return cardToken;
	}

	/**
	 * @param cardToken the cardToken to set
	 */
	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	/**
	 * @return the txnCCY
	 */
	public String getTxnCCY() {
		return txnCCY;
	}

	/**
	 * @param txnCCY the txnCCY to set
	 */
	public void setTxnCCY(String txnCCY) {
		this.txnCCY = txnCCY;
	}

	/**
	 * @return the txnAmount
	 */
	public Double getTxnAmount() {
		return txnAmount;
	}

	/**
	 * @param txnAmount the txnAmount to set
	 */
	public void setTxnAmount(Double txnAmount) {
		this.txnAmount = txnAmount;
	}

	/**
	 * @return the gPSDatestamp
	 */
	public String getgPSDatestamp() {
		return gPSDatestamp;
	}

	/**
	 * @param gPSDatestamp the gPSDatestamp to set
	 */
	public void setgPSDatestamp(String gPSDatestamp) {
		this.gPSDatestamp = gPSDatestamp;
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
	 * @return the cardInputMode
	 */
	public String getCardInputMode() {
		return cardInputMode;
	}

	/**
	 * @param cardInputMode the cardInputMode to set
	 */
	public void setCardInputMode(String cardInputMode) {
		this.cardInputMode = cardInputMode;
	}

	/**
	 * @return the cardInputModeDesc
	 */
	public String getCardInputModeDesc() {
		return cardInputModeDesc;
	}

	/**
	 * @param cardInputModeDesc the cardInputModeDesc to set
	 */
	public void setCardInputModeDesc(String cardInputModeDesc) {
		this.cardInputModeDesc = cardInputModeDesc;
	}

	/**
	 * @return the merchID
	 */
	public String getMerchID() {
		return merchID;
	}

	/**
	 * @param merchID the merchID to set
	 */
	public void setMerchID(String merchID) {
		this.merchID = merchID;
	}

	/**
	 * @return the merchantName
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * @param merchantName the merchantName to set
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	/**
	 * @return the merchantNameOther
	 */
	public String getMerchantNameOther() {
		return merchantNameOther;
	}

	/**
	 * @param merchantNameOther the merchantNameOther to set
	 */
	public void setMerchantNameOther(String merchantNameOther) {
		this.merchantNameOther = merchantNameOther;
	}

	/**
	 * @return the mCCCode
	 */
	public String getmCCCode() {
		return mCCCode;
	}

	/**
	 * @param mCCCode the mCCCode to set
	 */
	public void setmCCCode(String mCCCode) {
		this.mCCCode = mCCCode;
	}

	/**
	 * @return the mCCCodeDescription
	 */
	public String getmCCCodeDescription() {
		return mCCCodeDescription;
	}

	/**
	 * @param mCCCodeDescription the mCCCodeDescription to set
	 */
	public void setmCCCodeDescription(String mCCCodeDescription) {
		this.mCCCodeDescription = mCCCodeDescription;
	}

	/**
	 * @return the merchantCountryCode
	 */
	public String getMerchantCountryCode() {
		return merchantCountryCode;
	}

	/**
	 * @param merchantCountryCode the merchantCountryCode to set
	 */
	public void setMerchantCountryCode(String merchantCountryCode) {
		this.merchantCountryCode = merchantCountryCode;
	}

	/**
	 * @return the merchantPostcode
	 */
	public String getMerchantPostcode() {
		return merchantPostcode;
	}

	/**
	 * @param merchantPostcode the merchantPostcode to set
	 */
	public void setMerchantPostcode(String merchantPostcode) {
		this.merchantPostcode = merchantPostcode;
	}

	/**
	 * @return the merchantAddress
	 */
	public String getMerchantAddress() {
		return merchantAddress;
	}

	/**
	 * @param merchantAddress the merchantAddress to set
	 */
	public void setMerchantAddress(String merchantAddress) {
		this.merchantAddress = merchantAddress;
	}

	/**
	 * @return the pOSTerminal
	 */
	public String getpOSTerminal() {
		return pOSTerminal;
	}

	/**
	 * @param pOSTerminal the pOSTerminal to set
	 */
	public void setpOSTerminal(String pOSTerminal) {
		this.pOSTerminal = pOSTerminal;
	}

	/**
	 * @return the bin
	 */
	public String getBin() {
		return bin;
	}

	/**
	 * @param bin the bin to set
	 */
	public void setBin(String bin) {
		this.bin = bin;
	}

	/**
	 * @return the productID
	 */
	public String getProductID() {
		return productID;
	}

	/**
	 * @param productID the productID to set
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}

	/**
	 * @return the acqDate
	 */
	public String getAcqDate() {
		return acqDate;
	}

	/**
	 * @param acqDate the acqDate to set
	 */
	public void setAcqDate(String acqDate) {
		this.acqDate = acqDate;
	}

	/**
	 * @return the cardPresentIndicator
	 */
	public String getCardPresentIndicator() {
		return cardPresentIndicator;
	}

	/**
	 * @param cardPresentIndicator the cardPresentIndicator to set
	 */
	public void setCardPresentIndicator(String cardPresentIndicator) {
		this.cardPresentIndicator = cardPresentIndicator;
	}

	/**
	 * @return the cardPresentDescription
	 */
	public String getCardPresentDescription() {
		return cardPresentDescription;
	}

	/**
	 * @param cardPresentDescription the cardPresentDescription to set
	 */
	public void setCardPresentDescription(String cardPresentDescription) {
		this.cardPresentDescription = cardPresentDescription;
	}

	/**
	 * @return the txnCountry
	 */
	public String getTxnCountry() {
		return txnCountry;
	}

	/**
	 * @param txnCountry the txnCountry to set
	 */
	public void setTxnCountry(String txnCountry) {
		this.txnCountry = txnCountry;
	}

	/**
	 * @return the multiPartInd
	 */
	public String getMultiPartInd() {
		return multiPartInd;
	}

	/**
	 * @param multiPartInd the multiPartInd to set
	 */
	public void setMultiPartInd(String multiPartInd) {
		this.multiPartInd = multiPartInd;
	}

	/**
	 * @return the multiPartFinal
	 */
	public String getMultiPartFinal() {
		return multiPartFinal;
	}

	/**
	 * @param multiPartFinal the multiPartFinal to set
	 */
	public void setMultiPartFinal(String multiPartFinal) {
		this.multiPartFinal = multiPartFinal;
	}

	/**
	 * @return the multiPartMatchingReference
	 */
	public String getMultiPartMatchingReference() {
		return multiPartMatchingReference;
	}

	/**
	 * @param multiPartMatchingReference the multiPartMatchingReference to set
	 */
	public void setMultiPartMatchingReference(String multiPartMatchingReference) {
		this.multiPartMatchingReference = multiPartMatchingReference;
	}

	/**
	 * @return the billCcy
	 */
	public String getBillCcy() {
		return billCcy;
	}

	/**
	 * @param billCcy the billCcy to set
	 */
	public void setBillCcy(String billCcy) {
		this.billCcy = billCcy;
	}

	/**
	 * @return the billAmount
	 */
	public Double getBillAmount() {
		return billAmount;
	}

	/**
	 * @param billAmount the billAmount to set
	 */
	public void setBillAmount(Double billAmount) {
		this.billAmount = billAmount;
	}

	/**
	 * @return the effectiveAmount
	 */
	public Double getEffectiveAmount() {
		return effectiveAmount;
	}

	/**
	 * @param effectiveAmount the effectiveAmount to set
	 */
	public void setEffectiveAmount(Double effectiveAmount) {
		this.effectiveAmount = effectiveAmount;
	}

	/**
	 * @return the additionalAmountType
	 */
	public String getAdditionalAmountType() {
		return additionalAmountType;
	}

	/**
	 * @param additionalAmountType the additionalAmountType to set
	 */
	public void setAdditionalAmountType(String additionalAmountType) {
		this.additionalAmountType = additionalAmountType;
	}

	/**
	 * @return the additionalAmountCcy
	 */
	public String getAdditionalAmountCcy() {
		return additionalAmountCcy;
	}

	/**
	 * @param additionalAmountCcy the additionalAmountCcy to set
	 */
	public void setAdditionalAmountCcy(String additionalAmountCcy) {
		this.additionalAmountCcy = additionalAmountCcy;
	}

	/**
	 * @return the additionalAmount
	 */
	public Double getAdditionalAmount() {
		return additionalAmount;
	}

	/**
	 * @param additionalAmount the additionalAmount to set
	 */
	public void setAdditionalAmount(Double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	/**
	 * @return the consolidatedWalletBalances
	 */
	public Double getConsolidatedWalletBalances() {
		return consolidatedWalletBalances;
	}

	/**
	 * @param consolidatedWalletBalances the consolidatedWalletBalances to set
	 */
	public void setConsolidatedWalletBalances(Double consolidatedWalletBalances) {
		this.consolidatedWalletBalances = consolidatedWalletBalances;
	}

	/**
	 * @return the merchantRegion
	 */
	public String getMerchantRegion() {
		return merchantRegion;
	}

	/**
	 * @param merchantRegion the merchantRegion to set
	 */
	public void setMerchantRegion(String merchantRegion) {
		this.merchantRegion = merchantRegion;
	}

	/**
	 * @return the merchantCity
	 */
	public String getMerchantCity() {
		return merchantCity;
	}

	/**
	 * @param merchantCity the merchantCity to set
	 */
	public void setMerchantCity(String merchantCity) {
		this.merchantCity = merchantCity;
	}

	/**
	 * @return the merchantContact
	 */
	public String getMerchantContact() {
		return merchantContact;
	}

	/**
	 * @param merchantContact the merchantContact to set
	 */
	public void setMerchantContact(String merchantContact) {
		this.merchantContact = merchantContact;
	}

	/**
	 * @return the merchantSecondContact
	 */
	public String getMerchantSecondContact() {
		return merchantSecondContact;
	}

	/**
	 * @param merchantSecondContact the merchantSecondContact to set
	 */
	public void setMerchantSecondContact(String merchantSecondContact) {
		this.merchantSecondContact = merchantSecondContact;
	}

	/**
	 * @return the merchantWebsite
	 */
	public String getMerchantWebsite() {
		return merchantWebsite;
	}

	/**
	 * @param merchantWebsite the merchantWebsite to set
	 */
	public void setMerchantWebsite(String merchantWebsite) {
		this.merchantWebsite = merchantWebsite;
	}

	/**
	 * @return the acquirerID
	 */
	public String getAcquirerID() {
		return acquirerID;
	}

	/**
	 * @param acquirerID the acquirerID to set
	 */
	public void setAcquirerID(String acquirerID) {
		this.acquirerID = acquirerID;
	}

	/**
	 * @return the gPSDecisionCode
	 */
	public String getgPSDecisionCode() {
		return gPSDecisionCode;
	}

	/**
	 * @param gPSDecisionCode the gPSDecisionCode to set
	 */
	public void setgPSDecisionCode(String gPSDecisionCode) {
		this.gPSDecisionCode = gPSDecisionCode;
	}

	/**
	 * @return the gPSDecisionCodeDescription
	 */
	public String getgPSDecisionCodeDescription() {
		return gPSDecisionCodeDescription;
	}

	/**
	 * @param gPSDecisionCodeDescription the gPSDecisionCodeDescription to set
	 */
	public void setgPSDecisionCodeDescription(String gPSDecisionCodeDescription) {
		this.gPSDecisionCodeDescription = gPSDecisionCodeDescription;
	}

	/**
	 * @return the titanDecisionCode
	 */
	public String getTitanDecisionCode() {
		return titanDecisionCode;
	}

	/**
	 * @param titanDecisionCode the titanDecisionCode to set
	 */
	public void setTitanDecisionCode(String titanDecisionCode) {
		this.titanDecisionCode = titanDecisionCode;
	}

	/**
	 * @return the titanDecisionDescription
	 */
	public String getTitanDecisionDescription() {
		return titanDecisionDescription;
	}

	/**
	 * @param titanDecisionDescription the titanDecisionDescription to set
	 */
	public void setTitanDecisionDescription(String titanDecisionDescription) {
		this.titanDecisionDescription = titanDecisionDescription;
	}

	/**
	 * @return the cardStatusCode
	 */
	public String getCardStatusCode() {
		return cardStatusCode;
	}

	/**
	 * @param cardStatusCode the cardStatusCode to set
	 */
	public void setCardStatusCode(String cardStatusCode) {
		this.cardStatusCode = cardStatusCode;
	}

	/**
	 * @return the gPSSTIPFlag
	 */
	public String getgPSSTIPFlag() {
		return gPSSTIPFlag;
	}

	/**
	 * @param gPSSTIPFlag the gPSSTIPFlag to set
	 */
	public void setgPSSTIPFlag(String gPSSTIPFlag) {
		this.gPSSTIPFlag = gPSSTIPFlag;
	}

	/**
	 * @return the aVSResult
	 */
	public String getaVSResult() {
		return aVSResult;
	}

	/**
	 * @param aVSResult the aVSResult to set
	 */
	public void setaVSResult(String aVSResult) {
		this.aVSResult = aVSResult;
	}

	/**
	 * @return the sCAAuthenticationExemptionCode
	 */
	public String getsCAAuthenticationExemptionCode() {
		return sCAAuthenticationExemptionCode;
	}

	/**
	 * @param sCAAuthenticationExemptionCode the sCAAuthenticationExemptionCode to set
	 */
	public void setsCAAuthenticationExemptionCode(String sCAAuthenticationExemptionCode) {
		this.sCAAuthenticationExemptionCode = sCAAuthenticationExemptionCode;
	}

	/**
	 * @return the sCAAuthenticationExemptionDescription
	 */
	public String getsCAAuthenticationExemptionDescription() {
		return sCAAuthenticationExemptionDescription;
	}

	/**
	 * @param sCAAuthenticationExemptionDescription the sCAAuthenticationExemptionDescription to set
	 */
	public void setsCAAuthenticationExemptionDescription(String sCAAuthenticationExemptionDescription) {
		this.sCAAuthenticationExemptionDescription = sCAAuthenticationExemptionDescription;
	}

	/**
	 * @return the authenticationMethod
	 */
	public String getAuthenticationMethod() {
		return authenticationMethod;
	}

	/**
	 * @param authenticationMethod the authenticationMethod to set
	 */
	public void setAuthenticationMethod(String authenticationMethod) {
		this.authenticationMethod = authenticationMethod;
	}

	/**
	 * @return the authenticationMethodDesc
	 */
	public String getAuthenticationMethodDesc() {
		return authenticationMethodDesc;
	}

	/**
	 * @param authenticationMethodDesc the authenticationMethodDesc to set
	 */
	public void setAuthenticationMethodDesc(String authenticationMethodDesc) {
		this.authenticationMethodDesc = authenticationMethodDesc;
	}

	/**
	 * @return the cVVResults
	 */
	public String getcVVResults() {
		return cVVResults;
	}

	/**
	 * @param cVVResults the cVVResults to set
	 */
	public void setcVVResults(String cVVResults) {
		this.cVVResults = cVVResults;
	}

	/**
	 * @return the deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}

	/**
	 * @param deviceID the deviceID to set
	 */
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	/**
	 * @return the continent
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * @param continent the continent to set
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the latitiude
	 */
	public String getLatitiude() {
		return latitiude;
	}

	/**
	 * @param latitiude the latitiude to set
	 */
	public void setLatitiude(String latitiude) {
		this.latitiude = latitiude;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the timezone
	 */
	public String getTimezone() {
		return timezone;
	}

	/**
	 * @param timezone the timezone to set
	 */
	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the carrier
	 */
	public String getCarrier() {
		return carrier;
	}

	/**
	 * @param carrier the carrier to set
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	/**
	 * @return the connectionType
	 */
	public String getConnectionType() {
		return connectionType;
	}

	/**
	 * @param connectionType the connectionType to set
	 */
	public void setConnectionType(String connectionType) {
		this.connectionType = connectionType;
	}

	/**
	 * @return the lineSpeed
	 */
	public String getLineSpeed() {
		return lineSpeed;
	}

	/**
	 * @param lineSpeed the lineSpeed to set
	 */
	public void setLineSpeed(String lineSpeed) {
		this.lineSpeed = lineSpeed;
	}

	/**
	 * @return the ipRoutingType
	 */
	public String getIpRoutingType() {
		return ipRoutingType;
	}

	/**
	 * @param ipRoutingType the ipRoutingType to set
	 */
	public void setIpRoutingType(String ipRoutingType) {
		this.ipRoutingType = ipRoutingType;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the stateName
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * @param stateName the stateName to set
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * @return the stateCode
	 */
	public String getStateCode() {
		return stateCode;
	}

	/**
	 * @param stateCode the stateCode to set
	 */
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the areaCode
	 */
	public String getAreaCode() {
		return areaCode;
	}

	/**
	 * @param areaCode the areaCode to set
	 */
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	/**
	 * @return the anonymizerStatus
	 */
	public String getAnonymizerStatus() {
		return anonymizerStatus;
	}

	/**
	 * @param anonymizerStatus the anonymizerStatus to set
	 */
	public void setAnonymizerStatus(String anonymizerStatus) {
		this.anonymizerStatus = anonymizerStatus;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the updateStatus
	 */
	public String getUpdateStatus() {
		return updateStatus;
	}

	/**
	 * @param updateStatus the updateStatus to set
	 */
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	/**
	 * @return the updateDate
	 */
	public String getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateAmount
	 */
	public Double getUpdateAmount() {
		return updateAmount;
	}

	/**
	 * @param updateAmount the updateAmount to set
	 */
	public void setUpdateAmount(Double updateAmount) {
		this.updateAmount = updateAmount;
	}

	/**
	 * @return the chargebackReason
	 */
	public String getChargebackReason() {
		return chargebackReason;
	}

	/**
	 * @param chargebackReason the chargebackReason to set
	 */
	public void setChargebackReason(String chargebackReason) {
		this.chargebackReason = chargebackReason;
	}

	/**
	 * @return the pOSDatestamp
	 */
	public String getpOSDatestamp() {
		return pOSDatestamp;
	}

	/**
	 * @param pOSDatestamp the pOSDatestamp to set
	 */
	public void setpOSDatestamp(String pOSDatestamp) {
		this.pOSDatestamp = pOSDatestamp;
	}

	/**
	 * @return the gPSPOSCapability
	 */
	public String getgPSPOSCapability() {
		return gPSPOSCapability;
	}

	/**
	 * @param gPSPOSCapability the gPSPOSCapability to set
	 */
	public void setgPSPOSCapability(String gPSPOSCapability) {
		this.gPSPOSCapability = gPSPOSCapability;
	}

	/**
	 * @return the gPSPOSData
	 */
	public String getgPSPOSData() {
		return gPSPOSData;
	}

	/**
	 * @param gPSPOSData the gPSPOSData to set
	 */
	public void setgPSPOSData(String gPSPOSData) {
		this.gPSPOSData = gPSPOSData;
	}

	/**
	 * @return the isPresent
	 */
	public Integer getIsPresent() {
		return isPresent;
	}

	/**
	 * @param isPresent the isPresent to set
	 */
	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

	/**
	 * @return the ptWallet
	 */
	public String getPtWallet() {
		return ptWallet;
	}

	/**
	 * @param ptWallet the ptWallet to set
	 */
	public void setPtWallet(String ptWallet) {
		this.ptWallet = ptWallet;
	}

	/**
	 * @return the ptDeviceType
	 */
	public String getPtDeviceType() {
		return ptDeviceType;
	}

	/**
	 * @param ptDeviceType the ptDeviceType to set
	 */
	public void setPtDeviceType(String ptDeviceType) {
		this.ptDeviceType = ptDeviceType;
	}

	/**
	 * @return the ptDeviceIP
	 */
	public String getPtDeviceIP() {
		return ptDeviceIP;
	}

	/**
	 * @param ptDeviceIP the ptDeviceIP to set
	 */
	public void setPtDeviceIP(String ptDeviceIP) {
		this.ptDeviceIP = ptDeviceIP;
	}

	/**
	 * @return the ptDeviceID
	 */
	public String getPtDeviceID() {
		return ptDeviceID;
	}

	/**
	 * @param ptDeviceID the ptDeviceID to set
	 */
	public void setPtDeviceID(String ptDeviceID) {
		this.ptDeviceID = ptDeviceID;
	}

	/**
	 * @return the ptToken
	 */
	public String getPtToken() {
		return ptToken;
	}

	/**
	 * @param ptToken the ptToken to set
	 */
	public void setPtToken(String ptToken) {
		this.ptToken = ptToken;
	}
	
	
}
