package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class TransactionMonitoringPaymentsInRequest.
 */
public class TransactionMonitoringPaymentsInRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@JsonProperty(value= "org_code")
	private String orgCode;

	/** The request type. */
	private String requestType;

	/** The source application. */
	@JsonProperty(value= "source_application")
	private String sourceApplication;
	
	/** The cust Type. */
	@JsonProperty(value= "cust_type")
	private String custType;

	/** The time stamp. */
	@JsonProperty(value= "timestamp")
	private String timeStamp;
	
	/** The transaction id. */
	@JsonProperty(value= "trxID")
	private Integer transactionId;
	
	/** The transaction type. */
	@JsonProperty(value= "trx_type")
	private String transactionType;
	
	/** The trade account number. */
	@JsonProperty(value= "trade_account_number")
	private String tradeAccountNumber;
	
	/** The trade contact id. */
	@JsonProperty(value= "trade_contact_id")
	private Integer tradeContactId;
	
	/** The purpose of trade. */
	@JsonProperty(value= "purpose_of_trade")
	private String purposeOfTrade;
	
	/** The selling amount. */
	@JsonProperty(value= "selling_amount")
	private double sellingAmount;
	
	/** The selling amount base value. */
	@JsonProperty(value= "selling_amount_base_value")
	private double sellingAmountBaseValue;
	
	/** The transaction currency. */
	@JsonProperty(value= "transaction_currency")
	private String transactionCurrency;
	
	/** The contract number. */
	@JsonProperty(value= "contract_number")
	private String contractNumber;
	
	/** The payment method. */
	@JsonProperty(value= "payment_method")
	private String paymentMethod;
	
	/** The cc first name. */
	@JsonProperty(value= "cc_first_name")
	private String ccFirstName;
	
	/** The bill address line. */
	@JsonProperty(value= "bill_address_line")
	private String billAddressLine;
	
	/** The avs result. */
	@JsonProperty(value= "avs_result")
	private String avsResult;
	
	/** The is threeds. */
	@JsonProperty(value= "is_threeds")
	private String isThreeds;
	
	/** The debit card added date. */
	@JsonProperty(value= "debit_card_added_date")
	private String debitCardAddedDate;
	
	/** The av trade value. */
	@JsonProperty(value= "av_trade_value")
	private String avTradeValue;
	
	/** The av trade frequency. */
	@JsonProperty(value= "av_trade_frequency")
	private String avTradeFrequency;
	
	/** The third party payment. */
	@JsonProperty(value= "third_party_payment")
	private boolean thirdPartyPayment;
	
	/** The turnover. */
	@JsonProperty(value= "turnover")
	private String turnover;
	
	/** The debtor name. */
	@JsonProperty(value= "debtor_name")
	private String debtorName;
	
	/** The debtor account number. */
	@JsonProperty(value= "debtor_account_number")
	private String debtorAccountNumber;
	
	/** The bill ad zip. */
	@JsonProperty(value= "bill_ad_zip")
	private String billAdZip;
	
	/** The country of fund. */
	@JsonProperty(value= "country_of_fund")
	private String countryOfFund;
	
	/** The customer legal entity. */
	@JsonProperty(value= "customer_legal_entity")
	private String customerLegalEntity;
	
	/** The browser user agent. */
	@JsonProperty(value= "browser_user_agent")
	private String browserUserAgent;
	
	/** The screen resolution. */
	@JsonProperty(value= "screen_resolution")
	private String screenResolution;
	
	/** The browser type. */
	@JsonProperty(value= "brwsr_type")
	private String browserType;
	
	/** The browser version. */
	@JsonProperty(value= "brwsr_version")
	private String browserVersion;
	
	/** The device type. */
	@JsonProperty(value= "device_type")
	private String deviceType;
	
	/** The device name. */
	@JsonProperty(value= "device_name")
	private String deviceName;
	
	/** The device version. */
	@JsonProperty(value= "device_version")
	private String deviceVersion;
	
	/** The device id. */
	@JsonProperty(value= "device_id")
	private String deviceId;
	
	/** The device manufacturer. */
	@JsonProperty(value= "device_manufacturer")
	private String deviceManufacturer;
	
	/** The os type. */
	@JsonProperty(value= "os_type")
	private String osType;
	
	/** The browser language. */
	@JsonProperty(value= "brwsr_lang")
	private String browserLanguage;
	
	/** The browser online. */
	@JsonProperty(value= "browser_online")
	private String browserOnline;
	
	/** The message. */
	@JsonProperty(value= "message")
	private String message;
	
	/** The rgid. */
	@JsonProperty(value= "RGID")
	private String RGID;
	
	/** The t score. */
	@JsonProperty(value= "tScore")
	private double tScore;
	
	/** The t risk. */
	@JsonProperty(value= "tRisk")
	private double tRisk;
	
	/** The debator account number. */
	@JsonProperty(value= "debtorAccountNumber")
	private String debatorAccountNumber;
	
	/** The type. */
	@JsonProperty(value= "type")
	private String type;
	
	/** The deal date. */
	@JsonProperty(value= "deal_date")
	private String dealDate;
	
	/** The buying amount. */
	@JsonProperty(value= "buying_amount")
	private String buyingAmount;
	
	/** The maturity date. */
	@JsonProperty(value= "maturity_date")
	private String maturityDate;
	
	/** The buying amount base value. */
	@JsonProperty(value= "buying_amount_base_value")
	private String buyingAmountBaseValue;
	
	/** The buy currency. */
	@JsonProperty(value= "buy_currency")
	private String buyCurrency;
	
	/** The sell currency. */
	@JsonProperty(value= "sell_currency")
	private String sellCurrency;
	
	/** The phone. */
	@JsonProperty(value= "phone")
	private String phone;
	
	/** The first name. */
	@JsonProperty(value= "first_name")
	private String firstName;
	
	/** The last name. */
	@JsonProperty(value= "last_name")
	private String lastName;
	
	/** The email. */
	@JsonProperty(value= "email")
	private String email;
	
	/** The country. */
	@JsonProperty(value= "country")
	private String country;
	
	/** The account number. */
	@JsonProperty(value= "account_number")
	private String accountNumber;
	
	/** The currency code. */
	@JsonProperty(value= "currency_code")
	private String currencyCode;
	
	/** The beneficiary id. */
	@JsonProperty(value= "beneficiary_id")
	private String beneficiaryId;
	
	/** The beneficiary bank id. */
	@JsonProperty(value= "beneficiary_bank_id")
	private String beneficiaryBankId;
	
	/** The beneficiary bank name. */
	@JsonProperty(value= "beneficiary_bank_name")
	private String beneficiaryBankName;
	
	/** The beneficiary bank address. */
	@JsonProperty(value= "beneficiary_bank_address")
	private String beneficiaryBankAddress;
	
	/** The payment reference. */
	@JsonProperty(value= "payment_reference")
	private String paymentReference;
	
	/** The opi created date. */
	@JsonProperty(value= "opi_created_date")
	private String opiCreatedDate;
	
	/** The beneficary type. */
	@JsonProperty(value= "beneficiary_type")
	private String beneficaryType;
	
	/** The amount. */
	@JsonProperty(value= "amount")
	private String amount;
	
	/** The version. */
	@JsonProperty(value= "version")
	private Integer version;
	
	/** The update status. */
	@JsonProperty(value= "update_status")
	private String updateStatus;
	
	/** The sanction update status. */
	@JsonProperty(value= "status_update_reason")
	private String statusUpdateReason;
	
	/** The watchlist. */
	@JsonProperty(value= "watchlist")
	private List<String> watchlist;
	
	/** The blacklist. */
	@JsonProperty(value= "blacklist")
	private String blacklistStatus;
	
	/** The country check. */
	@JsonProperty(value= "country_check")
	private String countryCheckStatus;
	
	/** The sanction 3 rd party. */
	@JsonProperty(value= "sanction_3rd_party")
	private String sanction3rdPartyStatus;
	
	/** The sanction contact. */
	@JsonProperty(value= "sanction_contact")
	private String sanctionContactStatus;
	
	/** The sanction beneficiary. */
	@JsonProperty(value= "sanction_beneficiary")
	private String sanctionBeneficiaryStatus;
	
	/** The sanction bank. */
	@JsonProperty(value= "sanction_bank")
	private String sanctionBankStatus;
	
	/** The fraud predict. */
	@JsonProperty(value= "fraud_predict")
	private String fraudPredictStatus;
	
	/** The custom check. */
	@JsonProperty(value= "custom_check")
	private String customCheckStatus;
	
	/** The funds in id. */
	private Integer fundsInId;
	
	/** The value date. */
	@JsonProperty(value= "value_date")
	private String valueDate;
	
	/** The cvc result. */
	@JsonProperty(value = "cvc_result")
	private String cvcResult;
	
	/** The three ds outcome. */
	@JsonProperty(value = "ds_outcome")
	private String threeDsOutcome;
	
	@JsonProperty(value = "atlas_STP_codes")
	private String atlasSTPFlag;
	
	@JsonProperty(value = "fraudsight_risk_level")
	private String fraudsightRiskLevel;
	
	@JsonProperty(value = "fraudsight_reason")
	private String fraudsightReason;
	
	/** The creditor state. */
	@JsonProperty(value = "creditor_state")
	private String creditorState;
	
	/** The creditor bank name. */
	@JsonProperty(value = "creditor_bank_name")
	private String creditorBankName;
	
	/** The creditor bank account number. */
	@JsonProperty(value = "creditor_bank_AccountNumber")
	private String creditorBankAccountNumber;
	
	/** The creditor account name. */
	@JsonProperty(value = "creditor_account_name")
	private String creditorAccountName;
	
	/** The account id. */
	private Integer accountId;
		
	/** The created by. */
	private Integer createdBy;

	/** The is present. */
	private Integer isPresent = 0;
	
	private Integer accountTMFlag;
	
	private Integer contactId;
	
	private String etailer; //AT-4815
	
	//AT-5337
	@JsonProperty(value = "initiating_parent_contact")
	private String initiatingParentContact;
	
	//AT-5578
	@JsonProperty(value = "compliance_log")
	private String complianceLog;

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the checks if is present.
	 *
	 * @return the checks if is present
	 */
	public Integer getIsPresent() {
		return isPresent;
	}

	/**
	 * Sets the checks if is present.
	 *
	 * @param isPresent the new checks if is present
	 */
	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the new account id
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public String getTimeStamp() {
		return timeStamp;
	}

	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp the new time stamp
	 */
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	/**
	 * Gets the transaction id.
	 *
	 * @return the transaction id
	 */
	public Integer getTransactionId() {
		return transactionId;
	}

	/**
	 * Sets the transaction id.
	 *
	 * @param transactionId the new transaction id
	 */
	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * Gets the transaction type.
	 *
	 * @return the transaction type
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * Sets the transaction type.
	 *
	 * @param transactionType the new transaction type
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * Gets the trade account number.
	 *
	 * @return the trade account number
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * Sets the trade account number.
	 *
	 * @param tradeAccountNumber the new trade account number
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * Gets the purpose of trade.
	 *
	 * @return the purpose of trade
	 */
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * Sets the purpose of trade.
	 *
	 * @param purposeOfTrade the new purpose of trade
	 */
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * Gets the selling amount.
	 *
	 * @return the selling amount
	 */
	public double getSellingAmount() {
		return sellingAmount;
	}

	/**
	 * Sets the selling amount.
	 *
	 * @param sellingAmount the new selling amount
	 */
	public void setSellingAmount(double sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	/**
	 * Gets the selling amount base value.
	 *
	 * @return the selling amount base value
	 */
	public double getSellingAmountBaseValue() {
		return sellingAmountBaseValue;
	}

	/**
	 * Sets the selling amount base value.
	 *
	 * @param sellingAmountBaseValue the new selling amount base value
	 */
	public void setSellingAmountBaseValue(double sellingAmountBaseValue) {
		this.sellingAmountBaseValue = sellingAmountBaseValue;
	}

	/**
	 * Gets the transaction currency.
	 *
	 * @return the transaction currency
	 */
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * Sets the transaction currency.
	 *
	 * @param transactionCurrency the new transaction currency
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * Gets the contract number.
	 *
	 * @return the contract number
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * Sets the contract number.
	 *
	 * @param contractNumber the new contract number
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return the payment method
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod the new payment method
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Gets the cc first name.
	 *
	 * @return the cc first name
	 */
	public String getCcFirstName() {
		return ccFirstName;
	}

	/**
	 * Sets the cc first name.
	 *
	 * @param ccFirstName the new cc first name
	 */
	public void setCcFirstName(String ccFirstName) {
		this.ccFirstName = ccFirstName;
	}

	/**
	 * Gets the bill address line.
	 *
	 * @return the bill address line
	 */
	public String getBillAddressLine() {
		return billAddressLine;
	}

	/**
	 * Sets the bill address line.
	 *
	 * @param billAddressLine the new bill address line
	 */
	public void setBillAddressLine(String billAddressLine) {
		this.billAddressLine = billAddressLine;
	}

	/**
	 * Gets the avs result.
	 *
	 * @return the avs result
	 */
	public String getAvsResult() {
		return avsResult;
	}

	/**
	 * Sets the avs result.
	 *
	 * @param avsResult the new avs result
	 */
	public void setAvsResult(String avsResult) {
		this.avsResult = avsResult;
	}

	/**
	 * Gets the checks if is threeds.
	 *
	 * @return the checks if is threeds
	 */
	public String getIsThreeds() {
		return isThreeds;
	}

	/**
	 * Sets the checks if is threeds.
	 *
	 * @param isThreeds the new checks if is threeds
	 */
	public void setIsThreeds(String isThreeds) {
		this.isThreeds = isThreeds;
	}

	/**
	 * Gets the debit card added date.
	 *
	 * @return the debit card added date
	 */
	public String getDebitCardAddedDate() {
		return debitCardAddedDate;
	}

	/**
	 * Sets the debit card added date.
	 *
	 * @param debitCardAddedDate the new debit card added date
	 */
	public void setDebitCardAddedDate(String debitCardAddedDate) {
		this.debitCardAddedDate = debitCardAddedDate;
	}

	/**
	 * Gets the av trade value.
	 *
	 * @return the av trade value
	 */
	public String getAvTradeValue() {
		return avTradeValue;
	}

	/**
	 * Sets the av trade value.
	 *
	 * @param avTradeValue the new av trade value
	 */
	public void setAvTradeValue(String avTradeValue) {
		this.avTradeValue = avTradeValue;
	}

	/**
	 * Gets the av trade frequency.
	 *
	 * @return the av trade frequency
	 */
	public String getAvTradeFrequency() {
		return avTradeFrequency;
	}

	/**
	 * Sets the av trade frequency.
	 *
	 * @param avTradeFrequency the new av trade frequency
	 */
	public void setAvTradeFrequency(String avTradeFrequency) {
		this.avTradeFrequency = avTradeFrequency;
	}

	/**
	 * Checks if is third party payment.
	 *
	 * @return true, if is third party payment
	 */
	public boolean isThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment the new third party payment
	 */
	public void setThirdPartyPayment(boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Gets the turnover.
	 *
	 * @return the turnover
	 */
	public String getTurnover() {
		return turnover;
	}

	/**
	 * Sets the turnover.
	 *
	 * @param turnover the new turnover
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/**
	 * Gets the debtor name.
	 *
	 * @return the debtor name
	 */
	public String getDebtorName() {
		return debtorName;
	}

	/**
	 * Sets the debtor name.
	 *
	 * @param debtorName the new debtor name
	 */
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	/**
	 * Gets the debtor account number.
	 *
	 * @return the debtor account number
	 */
	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	/**
	 * Sets the debtor account number.
	 *
	 * @param debtorAccountNumber the new debtor account number
	 */
	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	/**
	 * Gets the bill ad zip.
	 *
	 * @return the bill ad zip
	 */
	public String getBillAdZip() {
		return billAdZip;
	}

	/**
	 * Sets the bill ad zip.
	 *
	 * @param billAdZip the new bill ad zip
	 */
	public void setBillAdZip(String billAdZip) {
		this.billAdZip = billAdZip;
	}

	/**
	 * Gets the country of fund.
	 *
	 * @return the country of fund
	 */
	public String getCountryOfFund() {
		return countryOfFund;
	}

	/**
	 * Sets the country of fund.
	 *
	 * @param countryOfFund the new country of fund
	 */
	public void setCountryOfFund(String countryOfFund) {
		this.countryOfFund = countryOfFund;
	}

	/**
	 * Gets the customer legal entity.
	 *
	 * @return the customer legal entity
	 */
	public String getCustomerLegalEntity() {
		return customerLegalEntity;
	}

	/**
	 * Sets the customer legal entity.
	 *
	 * @param customerLegalEntity the new customer legal entity
	 */
	public void setCustomerLegalEntity(String customerLegalEntity) {
		this.customerLegalEntity = customerLegalEntity;
	}

	/**
	 * Gets the browser user agent.
	 *
	 * @return the browser user agent
	 */
	public String getBrowserUserAgent() {
		return browserUserAgent;
	}

	/**
	 * Sets the browser user agent.
	 *
	 * @param browserUserAgent the new browser user agent
	 */
	public void setBrowserUserAgent(String browserUserAgent) {
		this.browserUserAgent = browserUserAgent;
	}

	/**
	 * Gets the screen resolution.
	 *
	 * @return the screen resolution
	 */
	public String getScreenResolution() {
		return screenResolution;
	}

	/**
	 * Sets the screen resolution.
	 *
	 * @param screenResolution the new screen resolution
	 */
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * Gets the browser type.
	 *
	 * @return the browser type
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * Sets the browser type.
	 *
	 * @param browserType the new browser type
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * Gets the browser version.
	 *
	 * @return the browser version
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Sets the browser version.
	 *
	 * @param browserVersion the new browser version
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * Gets the device type.
	 *
	 * @return the device type
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * Sets the device type.
	 *
	 * @param deviceType the new device type
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * Gets the device name.
	 *
	 * @return the device name
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Sets the device name.
	 *
	 * @param deviceName the new device name
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Gets the device version.
	 *
	 * @return the device version
	 */
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * Sets the device version.
	 *
	 * @param deviceVersion the new device version
	 */
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}

	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * Gets the device manufacturer.
	 *
	 * @return the device manufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * Sets the device manufacturer.
	 *
	 * @param deviceManufacturer the new device manufacturer
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * Gets the os type.
	 *
	 * @return the os type
	 */
	public String getOsType() {
		return osType;
	}

	/**
	 * Sets the os type.
	 *
	 * @param osType the new os type
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}

	/**
	 * Gets the browser language.
	 *
	 * @return the browser language
	 */
	public String getBrowserLanguage() {
		return browserLanguage;
	}

	/**
	 * Sets the browser language.
	 *
	 * @param browserLanguage the new browser language
	 */
	public void setBrowserLanguage(String browserLanguage) {
		this.browserLanguage = browserLanguage;
	}

	/**
	 * Gets the browser online.
	 *
	 * @return the browser online
	 */
	public String getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * Sets the browser online.
	 *
	 * @param browserOnline the new browser online
	 */
	public void setBrowserOnline(String browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the rgid.
	 *
	 * @return the rgid
	 */
	public String getRGID() {
		return RGID;
	}

	/**
	 * Sets the rgid.
	 *
	 * @param rGID the new rgid
	 */
	public void setRGID(String rGID) {
		RGID = rGID;
	}

	/**
	 * Gets the t risk.
	 *
	 * @return the t risk
	 */
	public double gettRisk() {
		return tRisk;
	}

	/**
	 * Sets the t risk.
	 *
	 * @param tRisk the new t risk
	 */
	public void settRisk(double tRisk) {
		this.tRisk = tRisk;
	}

	/**
	 * Gets the debator account number.
	 *
	 * @return the debator account number
	 */
	public String getDebatorAccountNumber() {
		return debatorAccountNumber;
	}

	/**
	 * Sets the debator account number.
	 *
	 * @param debatorAccountNumber the new debator account number
	 */
	public void setDebatorAccountNumber(String debatorAccountNumber) {
		this.debatorAccountNumber = debatorAccountNumber;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the deal date.
	 *
	 * @return the deal date
	 */
	public String getDealDate() {
		return dealDate;
	}

	/**
	 * Sets the deal date.
	 *
	 * @param dealDate the new deal date
	 */
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	/**
	 * Gets the buying amount.
	 *
	 * @return the buying amount
	 */
	public String getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * Sets the buying amount.
	 *
	 * @param buyingAmount the new buying amount
	 */
	public void setBuyingAmount(String buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * Gets the maturity date.
	 *
	 * @return the maturity date
	 */
	public String getMaturityDate() {
		return maturityDate;
	}

	/**
	 * Sets the maturity date.
	 *
	 * @param maturityDate the new maturity date
	 */
	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	/**
	 * Gets the buying amount base value.
	 *
	 * @return the buying amount base value
	 */
	public String getBuyingAmountBaseValue() {
		return buyingAmountBaseValue;
	}

	/**
	 * Sets the buying amount base value.
	 *
	 * @param buyingAmountBaseValue the new buying amount base value
	 */
	public void setBuyingAmountBaseValue(String buyingAmountBaseValue) {
		this.buyingAmountBaseValue = buyingAmountBaseValue;
	}

	/**
	 * Gets the buy currency.
	 *
	 * @return the buy currency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * Sets the buy currency.
	 *
	 * @param buyCurrency the new buy currency
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * Gets the sell currency.
	 *
	 * @return the sell currency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * Sets the sell currency.
	 *
	 * @param sellCurrency the new sell currency
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * Gets the phone.
	 *
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Sets the phone.
	 *
	 * @param phone the new phone
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the new first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the new last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Sets the email.
	 *
	 * @param email the new email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * Gets the account number.
	 *
	 * @return the account number
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber the new account number
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets the currency code.
	 *
	 * @return the currency code
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * Sets the currency code.
	 *
	 * @param currencyCode the new currency code
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * Gets the beneficiary id.
	 *
	 * @return the beneficiary id
	 */
	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	/**
	 * Sets the beneficiary id.
	 *
	 * @param beneficiaryId the new beneficiary id
	 */
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	/**
	 * Gets the beneficiary bank id.
	 *
	 * @return the beneficiary bank id
	 */
	public String getBeneficiaryBankId() {
		return beneficiaryBankId;
	}

	/**
	 * Sets the beneficiary bank id.
	 *
	 * @param beneficiaryBankId the new beneficiary bank id
	 */
	public void setBeneficiaryBankId(String beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	/**
	 * Gets the beneficiary bank name.
	 *
	 * @return the beneficiary bank name
	 */
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	/**
	 * Sets the beneficiary bank name.
	 *
	 * @param beneficiaryBankName the new beneficiary bank name
	 */
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	/**
	 * Gets the beneficiary bank address.
	 *
	 * @return the beneficiary bank address
	 */
	public String getBeneficiaryBankAddress() {
		return beneficiaryBankAddress;
	}

	/**
	 * Sets the beneficiary bank address.
	 *
	 * @param beneficiaryBankAddress the new beneficiary bank address
	 */
	public void setBeneficiaryBankAddress(String beneficiaryBankAddress) {
		this.beneficiaryBankAddress = beneficiaryBankAddress;
	}

	/**
	 * Gets the payment reference.
	 *
	 * @return the payment reference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * Sets the payment reference.
	 *
	 * @param paymentReference the new payment reference
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * Gets the opi created date.
	 *
	 * @return the opi created date
	 */
	public String getOpiCreatedDate() {
		return opiCreatedDate;
	}

	/**
	 * Sets the opi created date.
	 *
	 * @param opiCreatedDate the new opi created date
	 */
	public void setOpiCreatedDate(String opiCreatedDate) {
		this.opiCreatedDate = opiCreatedDate;
	}

	/**
	 * Gets the beneficary type.
	 *
	 * @return the beneficary type
	 */
	public String getBeneficaryType() {
		return beneficaryType;
	}

	/**
	 * Sets the beneficary type.
	 *
	 * @param beneficaryType the new beneficary type
	 */
	public void setBeneficaryType(String beneficaryType) {
		this.beneficaryType = beneficaryType;
	}

	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public String getAmount() {
		return amount;
	}

	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * Gets the update status.
	 *
	 * @return the update status
	 */
	public String getUpdateStatus() {
		return updateStatus;
	}

	/**
	 * Sets the update status.
	 *
	 * @param updateStatus the new update status
	 */
	public void setUpdateStatus(String updateStatus) {
		this.updateStatus = updateStatus;
	}

	/**
	 * Gets the sanction update reason.
	 *
	 * @return the sanction update reason
	 */
	public String getStatusUpdateReason() {
		return statusUpdateReason;
	}

	/**
	 * Sets the sanction update reason.
	 *
	 * @param sanctionUpdateReason the new sanction update reason
	 */
	public void setStatusUpdateReason(String statusUpdateReason) {
		this.statusUpdateReason = statusUpdateReason;
	}

	/**
	 * Gets the watchlist.
	 *
	 * @return the watchlist
	 */
	public List<String> getWatchlist() {
		return watchlist;
	}

	/**
	 * Sets the watchlist.
	 *
	 * @param watchlist the new watchlist
	 */
	public void setWatchlist(List<String> watchlist) {
		this.watchlist = watchlist;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public String getBlacklistStatus() {
		return blacklistStatus;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklistStatus the new blacklist status
	 */
	public void setBlacklistStatus(String blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}

	/**
	 * Gets the country check.
	 *
	 * @return the country check
	 */
	public String getCountryCheckStatus() {
		return countryCheckStatus;
	}

	/**
	 * Sets the country check.
	 *
	 * @param countryCheckStatus the new country check status
	 */
	public void setCountryCheckStatus(String countryCheckStatus) {
		this.countryCheckStatus = countryCheckStatus;
	}

	/**
	 * Gets the sanction 3 rd party.
	 *
	 * @return the sanction 3 rd party
	 */
	public String getSanction3rdPartyStatus() {
		return sanction3rdPartyStatus;
	}

	/**
	 * Sets the sanction 3 rd party.
	 *
	 * @param sanction3rdPartyStatus the new sanction 3 rd party status
	 */
	public void setSanction3rdPartyStatus(String sanction3rdPartyStatus) {
		this.sanction3rdPartyStatus = sanction3rdPartyStatus;
	}

	/**
	 * Gets the sanction contact.
	 *
	 * @return the sanction contact
	 */
	public String getSanctionContactStatus() {
		return sanctionContactStatus;
	}

	/**
	 * Sets the sanction contact.
	 *
	 * @param sanctionContactStatus the new sanction contact status
	 */
	public void setSanctionContactStatus(String sanctionContactStatus) {
		this.sanctionContactStatus = sanctionContactStatus;
	}

	/**
	 * Gets the sanction beneficiary.
	 *
	 * @return the sanction beneficiary
	 */
	public String getSanctionBeneficiaryStatus() {
		return sanctionBeneficiaryStatus;
	}

	/**
	 * Sets the sanction beneficiary.
	 *
	 * @param sanctionBeneficiaryStatus the new sanction beneficiary
	 */
	public void setSanctionBeneficiary(String sanctionBeneficiaryStatus) {
		this.sanctionBeneficiaryStatus = sanctionBeneficiaryStatus;
	}

	/**
	 * Gets the sanction bank.
	 *
	 * @return the sanction bank
	 */
	public String getSanctionBankStatus() {
		return sanctionBankStatus;
	}

	/**
	 * Sets the sanction bank.
	 *
	 * @param sanctionBankStatus the new sanction bank status
	 */
	public void setSanctionBankStatus(String sanctionBankStatus) {
		this.sanctionBankStatus = sanctionBankStatus;
	}

	/**
	 * Gets the fraud predict.
	 *
	 * @return the fraud predict
	 */
	public String getFraudPredictStatus() {
		return fraudPredictStatus;
	}

	/**
	 * Sets the fraud predict.
	 *
	 * @param fraudPredictStatus the new fraud predict status
	 */
	public void setFraudPredictStatus(String fraudPredictStatus) {
		this.fraudPredictStatus = fraudPredictStatus;
	}

	/**
	 * Gets the custom check.
	 *
	 * @return the custom check
	 */
	public String getCustomCheckStatus() {
		return customCheckStatus;
	}

	/**
	 * Sets the custom check.
	 *
	 * @param customCheckStatus the new custom check status
	 */
	public void setCustomCheckStatus(String customCheckStatus) {
		this.customCheckStatus = customCheckStatus;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Gets the trade contact id.
	 *
	 * @return the trade contact id
	 */
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * Sets the trade contact id.
	 *
	 * @param tradeContactId the new trade contact id
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * Gets the t score.
	 *
	 * @return the t score
	 */
	public double gettScore() {
		return tScore;
	}

	/**
	 * Sets the t score.
	 *
	 * @param tScore the new t score
	 */
	public void settScore(double tScore) {
		this.tScore = tScore;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getFundsInId() {
		return fundsInId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param fundsInId the new funds in id
	 */
	public void setFundsInId(Integer fundsInId) {
		this.fundsInId = fundsInId;
	}
	
	/**
	 * Gets the value date.
	 *
	 * @return the value date
	 */
	public String getValueDate() {
		return valueDate;
	}

	/**
	 * Sets the value date.
	 *
	 * @param valueDate the new value date
	 */
	public void setValueDate(String valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * Gets the account TM flag.
	 *
	 * @return the account TM flag
	 */
	public Integer getAccountTMFlag() {
		return accountTMFlag;
	}

	/**
	 * Sets the account TM flag.
	 *
	 * @param accountTMFlag the new account TM flag
	 */
	public void setAccountTMFlag(Integer accountTMFlag) {
		this.accountTMFlag = accountTMFlag;
	}

	/**
	 * Gets the cvc result.
	 *
	 * @return the cvc result
	 */
	public String getCvcResult() {
		return cvcResult;
	}

	/**
	 * Sets the cvc result.
	 *
	 * @param cvcResult the new cvc result
	 */
	public void setCvcResult(String cvcResult) {
		this.cvcResult = cvcResult;
	}

	/**
	 * Gets the three ds outcome.
	 *
	 * @return the three ds outcome
	 */
	public String getThreeDsOutcome() {
		return threeDsOutcome;
	}

	/**
	 * Sets the three ds outcome.
	 *
	 * @param threeDsOutcome the new three ds outcome
	 */
	public void setThreeDsOutcome(String threeDsOutcome) {
		this.threeDsOutcome = threeDsOutcome;
	}

	public String getAtlasSTPFlag() {
		return atlasSTPFlag;
	}

	public void setAtlasSTPFlag(String atlasSTPFlag) {
		this.atlasSTPFlag = atlasSTPFlag;
	}

	/**
	 * @return the fraudsightRiskLevel
	 */
	public String getFraudsightRiskLevel() {
		return fraudsightRiskLevel;
	}

	/**
	 * @param fraudsightRiskLevel the fraudsightRiskLevel to set
	 */
	public void setFraudsightRiskLevel(String fraudsightRiskLevel) {
		this.fraudsightRiskLevel = fraudsightRiskLevel;
	}

	/**
	 * @return the fraudsightReason
	 */
	public String getFraudsightReason() {
		return fraudsightReason;
	}

	/**
	 * @param fraudsightReason the fraudsightReason to set
	 */
	public void setFraudsightReason(String fraudsightReason) {
		this.fraudsightReason = fraudsightReason;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the etailer.
	 *
	 * @return the etailer
	 */
	public String getEtailer() {
		return etailer;
	}

	/**
	 * Sets the etailer.
	 *
	 * @param etailer the new etailer
	 */
	public void setEtailer(String etailer) {
		this.etailer = etailer;
	}

	/**
	 * @return the creditorState
	 */
	public String getCreditorState() {
		return creditorState;
	}

	/**
	 * @param creditorState the creditorState to set
	 */
	public void setCreditorState(String creditorState) {
		this.creditorState = creditorState;
	}

	/**
	 * @return the creditorBankName
	 */
	public String getCreditorBankName() {
		return creditorBankName;
	}

	/**
	 * @param creditorBankName the creditorBankName to set
	 */
	public void setCreditorBankName(String creditorBankName) {
		this.creditorBankName = creditorBankName;
	}

	/**
	 * @return the creditorBankAccountNumber
	 */
	public String getCreditorBankAccountNumber() {
		return creditorBankAccountNumber;
	}

	/**
	 * @param creditorBankAccountNumber the creditorBankAccountNumber to set
	 */
	public void setCreditorBankAccountNumber(String creditorBankAccountNumber) {
		this.creditorBankAccountNumber = creditorBankAccountNumber;
	}

	/**
	 * @return the creditorAccountName
	 */
	public String getCreditorAccountName() {
		return creditorAccountName;
	}

	/**
	 * @param creditorAccountName the creditorAccountName to set
	 */
	public void setCreditorAccountName(String creditorAccountName) {
		this.creditorAccountName = creditorAccountName;
	}

	/**
	 * @return the initiatingParentContact
	 */
	public String getInitiatingParentContact() {
		return initiatingParentContact;
	}

	/**
	 * @param initiatingParentContact the initiatingParentContact to set
	 */
	public void setInitiatingParentContact(String initiatingParentContact) {
		this.initiatingParentContact = initiatingParentContact;
	}

	/**
	 * @return the complianceLog
	 */
	public String getComplianceLog() {
		return complianceLog;
	}

	/**
	 * @param complianceLog the complianceLog to set
	 */
	public void setComplianceLog(String complianceLog) {
		this.complianceLog = complianceLog;
	}
}
