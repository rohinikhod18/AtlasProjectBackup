package com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv;

import java.io.Serializable;
import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PostCardTransactionRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The account number. */
	@ApiModelProperty(value = "The account number")
	@JsonProperty(value = "accountNumber")
	private String accountNumber;

	/** The instruction number. */
	@ApiModelProperty(value = "The instruction number")
	@JsonProperty(value = "instructionNumber")
	private String instructionNumber;

	/** The payment life id. */
	@ApiModelProperty(value = "The payment life id")
	@JsonProperty(value = "paymentLifeId")
	private String paymentLifeId;

	/** The gps datestamp. */
	@ApiModelProperty(value = "The gps datestamp")
	@JsonProperty(value = "gpsDatestamp")
	private Timestamp gpsDatestamp;

	/** The card token. */
	@ApiModelProperty(value = "The card token")
	@JsonProperty(value = "cardToken")
	private String cardToken;

	/** The card request type. */
	@ApiModelProperty(value = "The card request type")
	@JsonProperty(value = "cardRequestType")
	private String cardRequestType;

	/** The card Input Mode. */ //AT-5292
	@ApiModelProperty(value = "The card input mode")
	@JsonProperty(value = "cardInputMode")
	private String cardInputMode;

	
	/** The card Input Mode Desc. */ //AT-5292
	@ApiModelProperty(value = "The card Input Mode Desc")
	@JsonProperty(value = "cardInputModeDesc")
	private String cardInputModeDesc;

	/** The bill amount. */
	@ApiModelProperty(value = "The bill amount")
	@JsonProperty(value = "billAmount")
	private Double billAmount;

	/** The bill ccy. */
	@ApiModelProperty(value = "The bill ccy")
	@JsonProperty(value = "billCcy")
	private String billCcy;

	/** The txn amount. */
	@ApiModelProperty(value = "The txn amount")
	@JsonProperty(value = "txnAmount")
	private Double txnAmount;

	/** The txn currency. */
	@ApiModelProperty(value = "The txn currency")
	@JsonProperty(value = "txnCurrency")
	private String txnCurrency;

	/** The effective amount. */
	@ApiModelProperty(value = "The effective amount")
	@JsonProperty(value = "effectiveAmount")
	private Double effectiveAmount;
	
	/** The refundor disp amount. */
	@ApiModelProperty(value = "The refundor disp amount")
	@JsonProperty(value = "refundorDispAmount")
	private Double refundorDispAmount;

	/** The card present ind. */
	@ApiModelProperty(value = "The card present ind")
	@JsonProperty(value = "cardPresentInd")
	private String cardPresentInd;

	/** The card present desc. */
	@ApiModelProperty(value = "The card present desc")
	@JsonProperty(value = "cardPresentDesc")
	private String cardPresentDesc;

	/** The gps pos data. */
	@ApiModelProperty(value = "The gps pos data")
	@JsonProperty(value = "gpsPosData")
	private String gpsPosData;

	
	/** The mcc code. */
	@ApiModelProperty(value = "The mcc code")
	@JsonProperty(value = "mccCode")
	private String mccCode;

	/** The merchant name. */
	@ApiModelProperty(value = "The merchant name")
	@JsonProperty(value = "merchantName")
	private String merchantName;

	/** The merchant address. */
	@ApiModelProperty(value = "The merchant address")
	@JsonProperty(value = "merchantAddress")
	private String merchantAddress;

	/** The merchant country code. */
	@ApiModelProperty(value = "")
	@JsonProperty(value = "merchantCountryCode")
	private String merchantCountryCode;

	/** The merchant post code. */
	@ApiModelProperty(value = "The merchant post code")
	@JsonProperty(value = "merchantPostCode")
	private String merchantPostCode;

	/** The merchant region. */
	@ApiModelProperty(value = "The merchant region")
	@JsonProperty(value = "merchantRegion")
	private String merchantRegion;

	/** The merchant city. */
	@ApiModelProperty(value = "The merchant city")
	@JsonProperty(value = "merchantCity")
	private String merchantCity;

	/** The bin. */
	@ApiModelProperty(value = "The bin")
	@JsonProperty(value = "bin")
	private String bin;

	/** The product id. */
	@ApiModelProperty(value = "The product id")
	@JsonProperty(value = "productId")
	private String productId;

	/** The txn country. */
	@ApiModelProperty(value = "The txn country")
	@JsonProperty(value = "txnCountry")
	private String txnCountry;

	/** The pre auth seq. */
	@ApiModelProperty(value = "The pre auth seq")
	@JsonProperty(value = "preAuthSeq")
	private String preAuthSeq;

	/** The pre auth total parts. */
	@ApiModelProperty(value = "The pre auth total parts")
	@JsonProperty(value = "preAuthTotalParts")
	private String preAuthTotalParts;

	/** The pre auth multi part ind. */
	@ApiModelProperty(value = "The pre auth multi part ind")
	@JsonProperty(value = "preAuthMultiPartInd")
	private String preAuthMultiPartInd;

	/** The pre auth multi part final. */
	@ApiModelProperty(value = "The pre auth multi part final")
	@JsonProperty(value = "preAuthMultiPartFinal")
	private String preAuthMultiPartFinal;

	/** The pre auth matching reference. */
	@ApiModelProperty(value = "The pre auth matching reference")
	@JsonProperty(value = "preAuthMatchingReference")
	private String preAuthMatchingReference;

	/** The bill amount approve. */
	@ApiModelProperty(value = "The bill amount approve")
	@JsonProperty(value = "billAmountApprove")
	private Double billAmountApprove;

	/** The additional amount type. */
	@ApiModelProperty(value = "The additional amount type")
	@JsonProperty(value = "additionalAmountType")
	private String additionalAmountType;

	/** The additional amount ccy. */
	@ApiModelProperty(value = "The additional amount ccy")
	@JsonProperty(value = "additionalAmountCcy")
	private String additionalAmountCcy;
	
	/** The additional amount. */
	@ApiModelProperty(value = "The additional amount")
	@JsonProperty(value = "additionalAmount")
	private Double additionalAmount;

	/** The consolidated wallet balances. */
	@ApiModelProperty(value = "The consolidated wallet balances")
	@JsonProperty(value = "consolidatedWalletBalances")
	private Double consolidatedWalletBalances;

	/** The merchant ID. */
	@ApiModelProperty(value = "The merchant ID")
	@JsonProperty(value = "merchantID")
	private String merchantID;

	/** The merchant name other. */
	@ApiModelProperty(value = "The merchant name other")
	@JsonProperty(value = "merchantNameOther")
	private String merchantNameOther;

	/** The mcc code description. */
	@ApiModelProperty(value = "The mcc code description")
	@JsonProperty(value = "mccCodeDescription")
	private String mccCodeDescription;

	/** The merchant contact. */
	@ApiModelProperty(value = "The merchant contact")
	@JsonProperty(value = "merchantContact")
	private String merchantContact;

	/** The merchant second contact. */
	@ApiModelProperty(value = "The merchant second contact")
	@JsonProperty(value = "merchantSecondContact")
	private String merchantSecondContact;

	/** The merchant website. */
	@ApiModelProperty(value = "The merchant website")
	@JsonProperty(value = "merchantWebsite")
	private String merchantWebsite;

	/** The acquirer id. */
	@ApiModelProperty(value = "The acquirer id")
	@JsonProperty(value = "acquirerId")
	private String acquirerId;

	/** The acq datestamp. */
	@ApiModelProperty(value = "The acq datestamp")
	@JsonProperty(value = "acqDatestamp")
	private Timestamp acqDatestamp;

	/** The gps decision code. */
	@ApiModelProperty(value = "The gps decision code")
	@JsonProperty(value = "gpsDecisionCode")
	private String gpsDecisionCode;

	/** The gps decision code description. */
	@ApiModelProperty(value = "The gps decision code description")
	@JsonProperty(value = "gpsDecisionCodeDescription")
	private String gpsDecisionCodeDescription;

	/** The gps decline description. */
	@ApiModelProperty(value = "The gps decline description")
	@JsonProperty(value = "gpsDeclineDescription")
	private String gpsDeclineDescription;

	/** The titan decision. */
	@ApiModelProperty(value = "The titan decision")
	@JsonProperty(value = "titanDecision")
	private String titanDecision;

	/** The titan decision description. */
	@ApiModelProperty(value = "The titan decision description")
	@JsonProperty(value = "titanDecisionDescription")
	private String titanDecisionDescription;

	/** The card status code. */
	@ApiModelProperty(value = "The card status code")
	@JsonProperty(value = "cardStatusCode")
	private String cardStatusCode;

	/** The gps stip flag. */
	@ApiModelProperty(value = "The gps stip flag")
	@JsonProperty(value = "gpsStipFlag")
	private String gpsStipFlag;

	/** The avs result. */
	@ApiModelProperty(value = "The avs result")
	@JsonProperty(value = "avsResult")
	private String avsResult;

	/** The sca authentication exemption. */
	@ApiModelProperty(value = "The sca authentication exemption")
	@JsonProperty(value = "scaAuthenticationExemption")
	private String scaAuthenticationExemption;

	/** The authentication method. */
	@ApiModelProperty(value = "The authentication method")
	@JsonProperty(value = "authenticationMethod")
	private String authenticationMethod;

	/** The authentication method desc. */
	@ApiModelProperty(value = "The authentication method desc")
	@JsonProperty(value = "authenticationMethodDesc")
	private String authenticationMethodDesc;

	/** The cvv results. */
	@ApiModelProperty(value = "The cvv results")
	@JsonProperty(value = "cvvResults")
	private String cvvResults;

	/** The continent. */
	@ApiModelProperty(value = "The continent")
	@JsonProperty(value = "continent")
	private String continent;

	/** The longitude. */
	@ApiModelProperty(value = "The longitude")
	@JsonProperty(value = "longitude")
	private String longitude;

	/** The latitiude. */
	@ApiModelProperty(value = "The latitiude")
	@JsonProperty(value = "latitiude")
	private String latitiude;

	/** The region. */
	@ApiModelProperty(value = "The region")
	@JsonProperty(value = "region")
	private String region;

	/** The city. */
	@ApiModelProperty(value = "The city")
	@JsonProperty(value = "city")
	private String city;

	/** The timezone. */
	@ApiModelProperty(value = "The timezone")
	@JsonProperty(value = "timezone")
	private String timezone;

	/** The organization. */
	@ApiModelProperty(value = "The organization")
	@JsonProperty(value = "organization")
	private String organization;

	/** The carrier. */
	@ApiModelProperty(value = "The carrier")
	@JsonProperty(value = "carrier")
	private String carrier;

	/** The connection type. */
	@ApiModelProperty(value = "The connection type")
	@JsonProperty(value = "connectionType")
	private String connectionType;

	/** The line speed. */
	@ApiModelProperty(value = "The line speed")
	@JsonProperty(value = "lineSpeed")
	private String lineSpeed;

	/** The ip routing type. */
	@ApiModelProperty(value = "The ip routing type")
	@JsonProperty(value = "ipRoutingType")
	private String ipRoutingType;

	/** The country name. */
	@ApiModelProperty(value = "The country name")
	@JsonProperty(value = "countryName")
	private String countryName;

	/** The country code. */
	@ApiModelProperty(value = "The country code")
	@JsonProperty(value = "countryCode")
	private String countryCode;

	/** The state name. */
	@ApiModelProperty(value = "The state name")
	@JsonProperty(value = "stateName")
	private String stateName;

	/** The state code. */
	@ApiModelProperty(value = "The state code")
	@JsonProperty(value = "stateCode")
	private String stateCode;

	/** The postal code. */
	@ApiModelProperty(value = "The postal code")
	@JsonProperty(value = "postalCode")
	private String postalCode;

	/** The area code. */
	@ApiModelProperty(value = "The area code")
	@JsonProperty(value = "areaCode")
	private String areaCode;

	/** The anonymizer status. */
	@ApiModelProperty(value = "The anonymizer status")
	@JsonProperty(value = "anonymizerStatus")
	private String anonymizerStatus;

	/** The ip address. */
	@ApiModelProperty(value = "The ip address")
	@JsonProperty(value = "ipAddress")
	private String ipAddress;

	/** The device id. */
	@ApiModelProperty(value = "The device id")
	@JsonProperty(value = "deviceId")
	private String deviceId;

	/** The update status. */
	@ApiModelProperty(value = "The update status")
	@JsonProperty(value = "updateStatus")
	private String updateStatus;

	/** The date update. */
	@ApiModelProperty(value = "The date update")
	@JsonProperty(value = "dateUpdate")
	private Timestamp dateUpdate;

	/** The chargeback reason. */
	@ApiModelProperty(value = "The chargeback reason")
	@JsonProperty(value = "chargebackReason")
	private String chargebackReason;

	/** The dispute condition. */
	@ApiModelProperty(value = "The dispute condition")
	@JsonProperty(value = "disputeCondition")
	private String disputeCondition;

	/** The pos terminal. */
	@ApiModelProperty(value = "The pos terminal")
	@JsonProperty(value = "posTerminal")
	private String posTerminal;

	/** The pos date. */
	@ApiModelProperty(value = "The pos date")
	@JsonProperty(value = "posDate")
	private String posDate;

	/** The pos time. */
	@ApiModelProperty(value = "The pos time")
	@JsonProperty(value = "posTime")
	private String posTime;

	/** The gps pos capability. */
	@ApiModelProperty(value = "The gps pos capability")
	@JsonProperty(value = "gpsPosCapability")
	private String gpsPosCapability;
	
	/** The transaction description. */
	@ApiModelProperty(value = "The transaction description")
	@JsonProperty(value = "transactionDescription")
	private String transactionDescription;

	//AT-5371
	/** The pt wallet. */
	@ApiModelProperty(value = "The pt Wallet")
    @JsonProperty(value = "ptWallet")
	private String ptWallet;
	
	/** The pt device type. */
	@ApiModelProperty(value = "The pt Device Type")
    @JsonProperty(value = "ptDeviceType")
	private String ptDeviceType;
	
	/** The pt device IP. */
	@ApiModelProperty(value = "The pt Device IP")
	@JsonProperty(value = "ptDeviceIP")
	private String ptDeviceIP;
	
	/** The pt device ID. */
	@ApiModelProperty(value = "The pt Device ID")
	@JsonProperty(value = "ptDeviceID")
	private String ptDeviceID;
	
	/** The pt token. */
	@ApiModelProperty(value = "The pt Token")
	@JsonProperty(value = "ptToken")
	private String ptToken; //Add for AT-5474
	
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the instructionNumber
	 */
	public String getInstructionNumber() {
		return instructionNumber;
	}

	/**
	 * @param instructionNumber the instructionNumber to set
	 */
	public void setInstructionNumber(String instructionNumber) {
		this.instructionNumber = instructionNumber;
	}

	/**
	 * @return the paymentLifeId
	 */
	public String getPaymentLifeId() {
		return paymentLifeId;
	}

	/**
	 * @param paymentLifeId the paymentLifeId to set
	 */
	public void setPaymentLifeId(String paymentLifeId) {
		this.paymentLifeId = paymentLifeId;
	}

	/**
	 * @return the gpsDatestamp
	 */
	public Timestamp getGpsDatestamp() {
		return gpsDatestamp;
	}

	/**
	 * @param gpsDatestamp the gpsDatestamp to set
	 */
	public void setGpsDatestamp(Timestamp gpsDatestamp) {
		this.gpsDatestamp = gpsDatestamp;
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
	 * @return the cardRequestType
	 */
	public String getCardRequestType() {
		return cardRequestType;
	}

	/**
	 * @param cardRequestType the cardRequestType to set
	 */
	public void setCardRequestType(String cardRequestType) {
		this.cardRequestType = cardRequestType;
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
	 * @return the txnCurrency
	 */
	public String getTxnCurrency() {
		return txnCurrency;
	}

	/**
	 * @param txnCurrency the txnCurrency to set
	 */
	public void setTxnCurrency(String txnCurrency) {
		this.txnCurrency = txnCurrency;
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
	 * @return the refundorDispAmount
	 */
	public Double getRefundorDispAmount() {
		return refundorDispAmount;
	}

	/**
	 * @param refundorDispAmount the refundorDispAmount to set
	 */
	public void setRefundorDispAmount(Double refundorDispAmount) {
		this.refundorDispAmount = refundorDispAmount;
	}

	/**
	 * @return the cardPresentInd
	 */
	public String getCardPresentInd() {
		return cardPresentInd;
	}

	/**
	 * @param cardPresentInd the cardPresentInd to set
	 */
	public void setCardPresentInd(String cardPresentInd) {
		this.cardPresentInd = cardPresentInd;
	}

	/**
	 * @return the cardPresentDesc
	 */
	public String getCardPresentDesc() {
		return cardPresentDesc;
	}

	/**
	 * @param cardPresentDesc the cardPresentDesc to set
	 */
	public void setCardPresentDesc(String cardPresentDesc) {
		this.cardPresentDesc = cardPresentDesc;
	}

	/**
	 * @return the gpsPosData
	 */
	public String getGpsPosData() {
		return gpsPosData;
	}

	/**
	 * @param gpsPosData the gpsPosData to set
	 */
	public void setGpsPosData(String gpsPosData) {
		this.gpsPosData = gpsPosData;
	}

	/**
	 * @return the mccCode
	 */
	public String getMccCode() {
		return mccCode;
	}

	/**
	 * @param mccCode the mccCode to set
	 */
	public void setMccCode(String mccCode) {
		this.mccCode = mccCode;
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
	 * @return the merchantPostCode
	 */
	public String getMerchantPostCode() {
		return merchantPostCode;
	}

	/**
	 * @param merchantPostCode the merchantPostCode to set
	 */
	public void setMerchantPostCode(String merchantPostCode) {
		this.merchantPostCode = merchantPostCode;
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
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
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
	 * @return the preAuthSeq
	 */
	public String getPreAuthSeq() {
		return preAuthSeq;
	}

	/**
	 * @param preAuthSeq the preAuthSeq to set
	 */
	public void setPreAuthSeq(String preAuthSeq) {
		this.preAuthSeq = preAuthSeq;
	}

	/**
	 * @return the preAuthTotalParts
	 */
	public String getPreAuthTotalParts() {
		return preAuthTotalParts;
	}

	/**
	 * @param preAuthTotalParts the preAuthTotalParts to set
	 */
	public void setPreAuthTotalParts(String preAuthTotalParts) {
		this.preAuthTotalParts = preAuthTotalParts;
	}

	/**
	 * @return the preAuthMultiPartInd
	 */
	public String getPreAuthMultiPartInd() {
		return preAuthMultiPartInd;
	}

	/**
	 * @param preAuthMultiPartInd the preAuthMultiPartInd to set
	 */
	public void setPreAuthMultiPartInd(String preAuthMultiPartInd) {
		this.preAuthMultiPartInd = preAuthMultiPartInd;
	}

	/**
	 * @return the preAuthMultiPartFinal
	 */
	public String getPreAuthMultiPartFinal() {
		return preAuthMultiPartFinal;
	}

	/**
	 * @param preAuthMultiPartFinal the preAuthMultiPartFinal to set
	 */
	public void setPreAuthMultiPartFinal(String preAuthMultiPartFinal) {
		this.preAuthMultiPartFinal = preAuthMultiPartFinal;
	}

	/**
	 * @return the preAuthMatchingReference
	 */
	public String getPreAuthMatchingReference() {
		return preAuthMatchingReference;
	}

	/**
	 * @param preAuthMatchingReference the preAuthMatchingReference to set
	 */
	public void setPreAuthMatchingReference(String preAuthMatchingReference) {
		this.preAuthMatchingReference = preAuthMatchingReference;
	}

	/**
	 * @return the billAmountApprove
	 */
	public Double getBillAmountApprove() {
		return billAmountApprove;
	}

	/**
	 * @param billAmountApprove the billAmountApprove to set
	 */
	public void setBillAmountApprove(Double billAmountApprove) {
		this.billAmountApprove = billAmountApprove;
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
	 * @return the merchantID
	 */
	public String getMerchantID() {
		return merchantID;
	}

	/**
	 * @param merchantID the merchantID to set
	 */
	public void setMerchantID(String merchantID) {
		this.merchantID = merchantID;
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
	 * @return the mccCodeDescription
	 */
	public String getMccCodeDescription() {
		return mccCodeDescription;
	}

	/**
	 * @param mccCodeDescription the mccCodeDescription to set
	 */
	public void setMccCodeDescription(String mccCodeDescription) {
		this.mccCodeDescription = mccCodeDescription;
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
	 * @return the acquirerId
	 */
	public String getAcquirerId() {
		return acquirerId;
	}

	/**
	 * @param acquirerId the acquirerId to set
	 */
	public void setAcquirerId(String acquirerId) {
		this.acquirerId = acquirerId;
	}

	/**
	 * @return the acqDatestamp
	 */
	public Timestamp getAcqDatestamp() {
		return acqDatestamp;
	}

	/**
	 * @param acqDatestamp the acqDatestamp to set
	 */
	public void setAcqDatestamp(Timestamp acqDatestamp) {
		this.acqDatestamp = acqDatestamp;
	}

	/**
	 * @return the gpsDecisionCode
	 */
	public String getGpsDecisionCode() {
		return gpsDecisionCode;
	}

	/**
	 * @param gpsDecisionCode the gpsDecisionCode to set
	 */
	public void setGpsDecisionCode(String gpsDecisionCode) {
		this.gpsDecisionCode = gpsDecisionCode;
	}

	/**
	 * @return the gpsDecisionCodeDescription
	 */
	public String getGpsDecisionCodeDescription() {
		return gpsDecisionCodeDescription;
	}

	/**
	 * @param gpsDecisionCodeDescription the gpsDecisionCodeDescription to set
	 */
	public void setGpsDecisionCodeDescription(String gpsDecisionCodeDescription) {
		this.gpsDecisionCodeDescription = gpsDecisionCodeDescription;
	}

	/**
	 * @return the gpsDeclineDescription
	 */
	public String getGpsDeclineDescription() {
		return gpsDeclineDescription;
	}

	/**
	 * @param gpsDeclineDescription the gpsDeclineDescription to set
	 */
	public void setGpsDeclineDescription(String gpsDeclineDescription) {
		this.gpsDeclineDescription = gpsDeclineDescription;
	}

	/**
	 * @return the titanDecision
	 */
	public String getTitanDecision() {
		return titanDecision;
	}

	/**
	 * @param titanDecision the titanDecision to set
	 */
	public void setTitanDecision(String titanDecision) {
		this.titanDecision = titanDecision;
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
	 * @return the gpsStipFlag
	 */
	public String getGpsStipFlag() {
		return gpsStipFlag;
	}

	/**
	 * @param gpsStipFlag the gpsStipFlag to set
	 */
	public void setGpsStipFlag(String gpsStipFlag) {
		this.gpsStipFlag = gpsStipFlag;
	}

	/**
	 * @return the avsResult
	 */
	public String getAvsResult() {
		return avsResult;
	}

	/**
	 * @param avsResult the avsResult to set
	 */
	public void setAvsResult(String avsResult) {
		this.avsResult = avsResult;
	}

	/**
	 * @return the scaAuthenticationExemption
	 */
	public String getScaAuthenticationExemption() {
		return scaAuthenticationExemption;
	}

	/**
	 * @param scaAuthenticationExemption the scaAuthenticationExemption to set
	 */
	public void setScaAuthenticationExemption(String scaAuthenticationExemption) {
		this.scaAuthenticationExemption = scaAuthenticationExemption;
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
	 * @return the cvvResults
	 */
	public String getCvvResults() {
		return cvvResults;
	}

	/**
	 * @param cvvResults the cvvResults to set
	 */
	public void setCvvResults(String cvvResults) {
		this.cvvResults = cvvResults;
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
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
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
	 * @return the dateUpdate
	 */
	public Timestamp getDateUpdate() {
		return dateUpdate;
	}

	/**
	 * @param dateUpdate the dateUpdate to set
	 */
	public void setDateUpdate(Timestamp dateUpdate) {
		this.dateUpdate = dateUpdate;
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
	 * @return the disputeCondition
	 */
	public String getDisputeCondition() {
		return disputeCondition;
	}

	/**
	 * @param disputeCondition the disputeCondition to set
	 */
	public void setDisputeCondition(String disputeCondition) {
		this.disputeCondition = disputeCondition;
	}

	/**
	 * @return the posTerminal
	 */
	public String getPosTerminal() {
		return posTerminal;
	}

	/**
	 * @param posTerminal the posTerminal to set
	 */
	public void setPosTerminal(String posTerminal) {
		this.posTerminal = posTerminal;
	}

	/**
	 * @return the posDate
	 */
	public String getPosDate() {
		return posDate;
	}

	/**
	 * @param posDate the posDate to set
	 */
	public void setPosDate(String posDate) {
		this.posDate = posDate;
	}

	/**
	 * @return the posTime
	 */
	public String getPosTime() {
		return posTime;
	}

	/**
	 * @param posTime the posTime to set
	 */
	public void setPosTime(String posTime) {
		this.posTime = posTime;
	}

	/**
	 * @return the gpsPosCapability
	 */
	public String getGpsPosCapability() {
		return gpsPosCapability;
	}

	/**
	 * @param gpsPosCapability the gpsPosCapability to set
	 */
	public void setGpsPosCapability(String gpsPosCapability) {
		this.gpsPosCapability = gpsPosCapability;
	}

	/**
	 * @return the transactionDescription
	 */
	public String getTransactionDescription() {
		return transactionDescription;
	}

	/**
	 * @param transactionDescription the transactionDescription to set
	 */
	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
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
