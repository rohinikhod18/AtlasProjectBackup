package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

public class IntuitionHistoricPaymentInRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Trade identification number. */
	private String TradeIdentificationNumber;

	private String TradeContractNumber;

	private String TradePaymentID;

	private String trade_account_number;

	private String timestamp;

	private String dealDate;

	private String custType;
	
	private String purposeOfTrade;

	private Double buyingAmount;
	
	private String buyCurrency;
	
	private Double sellingAmount;
	
	private String sellCurrency;
	
	private String transactionCurrency;
	
	private String customerLegalEntity;
	
	private String paymentMethod;
	
	private Double amount;
	
	private String firstName;
	
	private String lastName;
	
	private String country;
	
	private String email;
	
	private String phone;
	
	private String accountNumber;
	
	private String currencyCode;
	
	private String beneficiaryId;
	
	private String beneficiaryBankid;
	
	private String beneficaryBankName;
	
	private String beneficaryBankAddress;
	
	private String paymentReference;
	
	private String paymentRefMatchedKeyword;
	
	private String debtorName;
	
	private String debtorAccountNumber;
	
	private String billAddressLine;
	
	private String countryOfFund;
	
	private String watchlist;
	
	private String tradeContactId;
	
	private String trxType;
	
	private String contractNumber;
	
	private boolean thirdPartyPayment;
	
	private Double buyingAmountBaseValue;
	
	private Double sellingAmountBaseValue;
	
	private String channel;
	
	private String ccFirstName;
	
	private String billAdZip;
	
	private String isThreeds;
	
	private String avsResult;
	
	private String debitCardAddedDate;
	
	private String message;
	
	private String RGID;
	
	private Double tScore;
	
	private String tRisk;
	
	private String fraudsightRiskLevel;
	
	private String fraudsightReason;
	
	private String CVCCVVResult;
	
	private String brwsrType;
	
	private String brwsrVersion;
	
	private String brwsrLang;
	
	private String browserOnline;
	
	private String version;
	
	private String statusUpdateReason;
	
	private String deviceName;
	
	private String deviceVersion;
	
	private String deviceId;
	
	private String deviceManufacturer;
	
	private String deviceType;
	
	private String osType;
	
	private String screenResolution;
	
	private String updateStatus;
	
	private String blacklist;
	
	private String countryCheck;
	
	private String sanctionsContact;
	
	private String sanctionsBeneficiary;
	
	private String sanctionsBank;
	
	private String sanctions3rdParty;
	
	private String paymentReferenceCheck;
	
	private String fraudPredict;
	
	private String customCheck;
	
	private String atlasSTPCodes;
	
	private Integer accountId;

	/**
	 * @return the tradeIdentificationNumber
	 */
	public String getTradeIdentificationNumber() {
		return TradeIdentificationNumber;
	}

	/**
	 * @param tradeIdentificationNumber the tradeIdentificationNumber to set
	 */
	public void setTradeIdentificationNumber(String tradeIdentificationNumber) {
		TradeIdentificationNumber = tradeIdentificationNumber;
	}

	/**
	 * @return the tradeContractNumber
	 */
	public String getTradeContractNumber() {
		return TradeContractNumber;
	}

	/**
	 * @param tradeContractNumber the tradeContractNumber to set
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		TradeContractNumber = tradeContractNumber;
	}

	/**
	 * @return the tradePaymentID
	 */
	public String getTradePaymentID() {
		return TradePaymentID;
	}

	/**
	 * @param tradePaymentID the tradePaymentID to set
	 */
	public void setTradePaymentID(String tradePaymentID) {
		TradePaymentID = tradePaymentID;
	}

	/**
	 * @return the trade_account_number
	 */
	public String getTrade_account_number() {
		return trade_account_number;
	}

	/**
	 * @param trade_account_number the trade_account_number to set
	 */
	public void setTrade_account_number(String trade_account_number) {
		this.trade_account_number = trade_account_number;
	}

	/**
	 * @return the timestamp
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the dealDate
	 */
	public String getDealDate() {
		return dealDate;
	}

	/**
	 * @param dealDate the dealDate to set
	 */
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * @return the purposeOfTrade
	 */
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * @param purposeOfTrade the purposeOfTrade to set
	 */
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * @return the buyingAmount
	 */
	public Double getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * @param buyingAmount the buyingAmount to set
	 */
	public void setBuyingAmount(Double buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * @param buyCurrency the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * @return the sellingAmount
	 */
	public Double getSellingAmount() {
		return sellingAmount;
	}

	/**
	 * @param sellingAmount the sellingAmount to set
	 */
	public void setSellingAmount(Double sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	/**
	 * @return the sellCurrency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * @param sellCurrency the sellCurrency to set
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * @return the transactionCurrency
	 */
	public String getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * @param transactionCurrency the transactionCurrency to set
	 */
	public void setTransactionCurrency(String transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * @return the customerLegalEntity
	 */
	public String getCustomerLegalEntity() {
		return customerLegalEntity;
	}

	/**
	 * @param customerLegalEntity the customerLegalEntity to set
	 */
	public void setCustomerLegalEntity(String customerLegalEntity) {
		this.customerLegalEntity = customerLegalEntity;
	}

	/**
	 * @return the paymentMethod
	 */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

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
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the beneficiaryId
	 */
	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	/**
	 * @param beneficiaryId the beneficiaryId to set
	 */
	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	/**
	 * @return the beneficiaryBankid
	 */
	public String getBeneficiaryBankid() {
		return beneficiaryBankid;
	}

	/**
	 * @param beneficiaryBankid the beneficiaryBankid to set
	 */
	public void setBeneficiaryBankid(String beneficiaryBankid) {
		this.beneficiaryBankid = beneficiaryBankid;
	}

	/**
	 * @return the beneficaryBankName
	 */
	public String getBeneficaryBankName() {
		return beneficaryBankName;
	}

	/**
	 * @param beneficaryBankName the beneficaryBankName to set
	 */
	public void setBeneficaryBankName(String beneficaryBankName) {
		this.beneficaryBankName = beneficaryBankName;
	}

	/**
	 * @return the beneficaryBankAddress
	 */
	public String getBeneficaryBankAddress() {
		return beneficaryBankAddress;
	}

	/**
	 * @param beneficaryBankAddress the beneficaryBankAddress to set
	 */
	public void setBeneficaryBankAddress(String beneficaryBankAddress) {
		this.beneficaryBankAddress = beneficaryBankAddress;
	}

	/**
	 * @return the paymentReference
	 */
	public String getPaymentReference() {
		return paymentReference;
	}

	/**
	 * @param paymentReference the paymentReference to set
	 */
	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

	/**
	 * @return the paymentRefMatchedKeyword
	 */
	public String getPaymentRefMatchedKeyword() {
		return paymentRefMatchedKeyword;
	}

	/**
	 * @param paymentRefMatchedKeyword the paymentRefMatchedKeyword to set
	 */
	public void setPaymentRefMatchedKeyword(String paymentRefMatchedKeyword) {
		this.paymentRefMatchedKeyword = paymentRefMatchedKeyword;
	}

	/**
	 * @return the debtorName
	 */
	public String getDebtorName() {
		return debtorName;
	}

	/**
	 * @param debtorName the debtorName to set
	 */
	public void setDebtorName(String debtorName) {
		this.debtorName = debtorName;
	}

	/**
	 * @return the debtorAccountNumber
	 */
	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	/**
	 * @param debtorAccountNumber the debtorAccountNumber to set
	 */
	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	/**
	 * @return the billAddressLine
	 */
	public String getBillAddressLine() {
		return billAddressLine;
	}

	/**
	 * @param billAddressLine the billAddressLine to set
	 */
	public void setBillAddressLine(String billAddressLine) {
		this.billAddressLine = billAddressLine;
	}

	/**
	 * @return the countryOfFund
	 */
	public String getCountryOfFund() {
		return countryOfFund;
	}

	/**
	 * @param countryOfFund the countryOfFund to set
	 */
	public void setCountryOfFund(String countryOfFund) {
		this.countryOfFund = countryOfFund;
	}

	/**
	 * @return the watchlist
	 */
	public String getWatchlist() {
		return watchlist;
	}

	/**
	 * @param watchlist the watchlist to set
	 */
	public void setWatchlist(String watchlist) {
		this.watchlist = watchlist;
	}

	/**
	 * @return the tradeContactId
	 */
	public String getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * @param tradeContactId the tradeContactId to set
	 */
	public void setTradeContactId(String tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * @return the trxType
	 */
	public String getTrxType() {
		return trxType;
	}

	/**
	 * @param trxType the trxType to set
	 */
	public void setTrxType(String trxType) {
		this.trxType = trxType;
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

	/**
	 * @return the thirdPartyPayment
	 */
	public boolean isThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * @param thirdPartyPayment the thirdPartyPayment to set
	 */
	public void setThirdPartyPayment(boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * @return the buyingAmountBaseValue
	 */
	public Double getBuyingAmountBaseValue() {
		return buyingAmountBaseValue;
	}

	/**
	 * @param buyingAmountBaseValue the buyingAmountBaseValue to set
	 */
	public void setBuyingAmountBaseValue(Double buyingAmountBaseValue) {
		this.buyingAmountBaseValue = buyingAmountBaseValue;
	}

	/**
	 * @return the sellingAmountBaseValue
	 */
	public Double getSellingAmountBaseValue() {
		return sellingAmountBaseValue;
	}

	/**
	 * @param sellingAmountBaseValue the sellingAmountBaseValue to set
	 */
	public void setSellingAmountBaseValue(Double sellingAmountBaseValue) {
		this.sellingAmountBaseValue = sellingAmountBaseValue;
	}

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the ccFirstName
	 */
	public String getCcFirstName() {
		return ccFirstName;
	}

	/**
	 * @param ccFirstName the ccFirstName to set
	 */
	public void setCcFirstName(String ccFirstName) {
		this.ccFirstName = ccFirstName;
	}

	/**
	 * @return the billAdZip
	 */
	public String getBillAdZip() {
		return billAdZip;
	}

	/**
	 * @param billAdZip the billAdZip to set
	 */
	public void setBillAdZip(String billAdZip) {
		this.billAdZip = billAdZip;
	}

	/**
	 * @return the isThreeds
	 */
	public String getIsThreeds() {
		return isThreeds;
	}

	/**
	 * @param isThreeds the isThreeds to set
	 */
	public void setIsThreeds(String isThreeds) {
		this.isThreeds = isThreeds;
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
	 * @return the debitCardAddedDate
	 */
	public String getDebitCardAddedDate() {
		return debitCardAddedDate;
	}

	/**
	 * @param debitCardAddedDate the debitCardAddedDate to set
	 */
	public void setDebitCardAddedDate(String debitCardAddedDate) {
		this.debitCardAddedDate = debitCardAddedDate;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the rGID
	 */
	public String getRGID() {
		return RGID;
	}

	/**
	 * @param rGID the rGID to set
	 */
	public void setRGID(String rGID) {
		RGID = rGID;
	}

	/**
	 * @return the tScore
	 */
	public Double gettScore() {
		return tScore;
	}

	/**
	 * @param tScore the tScore to set
	 */
	public void settScore(Double tScore) {
		this.tScore = tScore;
	}

	/**
	 * @return the tRisk
	 */
	public String gettRisk() {
		return tRisk;
	}

	/**
	 * @param tRisk the tRisk to set
	 */
	public void settRisk(String tRisk) {
		this.tRisk = tRisk;
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
	 * @return the cVCCVVResult
	 */
	public String getCVCCVVResult() {
		return CVCCVVResult;
	}

	/**
	 * @param cVCCVVResult the cVCCVVResult to set
	 */
	public void setCVCCVVResult(String cVCCVVResult) {
		CVCCVVResult = cVCCVVResult;
	}

	/**
	 * @return the brwsrType
	 */
	public String getBrwsrType() {
		return brwsrType;
	}

	/**
	 * @param brwsrType the brwsrType to set
	 */
	public void setBrwsrType(String brwsrType) {
		this.brwsrType = brwsrType;
	}

	/**
	 * @return the brwsrVersion
	 */
	public String getBrwsrVersion() {
		return brwsrVersion;
	}

	/**
	 * @param brwsrVersion the brwsrVersion to set
	 */
	public void setBrwsrVersion(String brwsrVersion) {
		this.brwsrVersion = brwsrVersion;
	}

	/**
	 * @return the brwsrLang
	 */
	public String getBrwsrLang() {
		return brwsrLang;
	}

	/**
	 * @param brwsrLang the brwsrLang to set
	 */
	public void setBrwsrLang(String brwsrLang) {
		this.brwsrLang = brwsrLang;
	}

	/**
	 * @return the browserOnline
	 */
	public String getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * @param browserOnline the browserOnline to set
	 */
	public void setBrowserOnline(String browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the statusUpdateReason
	 */
	public String getStatusUpdateReason() {
		return statusUpdateReason;
	}

	/**
	 * @param statusUpdateReason the statusUpdateReason to set
	 */
	public void setStatusUpdateReason(String statusUpdateReason) {
		this.statusUpdateReason = statusUpdateReason;
	}

	/**
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * @param deviceName the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @return the deviceVersion
	 */
	public String getDeviceVersion() {
		return deviceVersion;
	}

	/**
	 * @param deviceVersion the deviceVersion to set
	 */
	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
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
	 * @return the deviceManufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * @param deviceManufacturer the deviceManufacturer to set
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @return the osType
	 */
	public String getOsType() {
		return osType;
	}

	/**
	 * @param osType the osType to set
	 */
	public void setOsType(String osType) {
		this.osType = osType;
	}

	/**
	 * @return the screenResolution
	 */
	public String getScreenResolution() {
		return screenResolution;
	}

	/**
	 * @param screenResolution the screenResolution to set
	 */
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
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
	 * @return the blacklist
	 */
	public String getBlacklist() {
		return blacklist;
	}

	/**
	 * @param blacklist the blacklist to set
	 */
	public void setBlacklist(String blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * @return the countryCheck
	 */
	public String getCountryCheck() {
		return countryCheck;
	}

	/**
	 * @param countryCheck the countryCheck to set
	 */
	public void setCountryCheck(String countryCheck) {
		this.countryCheck = countryCheck;
	}

	/**
	 * @return the sanctionsContact
	 */
	public String getSanctionsContact() {
		return sanctionsContact;
	}

	/**
	 * @param sanctionsContact the sanctionsContact to set
	 */
	public void setSanctionsContact(String sanctionsContact) {
		this.sanctionsContact = sanctionsContact;
	}

	/**
	 * @return the sanctionsBeneficiary
	 */
	public String getSanctionsBeneficiary() {
		return sanctionsBeneficiary;
	}

	/**
	 * @param sanctionsBeneficiary the sanctionsBeneficiary to set
	 */
	public void setSanctionsBeneficiary(String sanctionsBeneficiary) {
		this.sanctionsBeneficiary = sanctionsBeneficiary;
	}

	/**
	 * @return the sanctionsBank
	 */
	public String getSanctionsBank() {
		return sanctionsBank;
	}

	/**
	 * @param sanctionsBank the sanctionsBank to set
	 */
	public void setSanctionsBank(String sanctionsBank) {
		this.sanctionsBank = sanctionsBank;
	}

	/**
	 * @return the sanctions3rdParty
	 */
	public String getSanctions3rdParty() {
		return sanctions3rdParty;
	}

	/**
	 * @param sanctions3rdParty the sanctions3rdParty to set
	 */
	public void setSanctions3rdParty(String sanctions3rdParty) {
		this.sanctions3rdParty = sanctions3rdParty;
	}

	/**
	 * @return the paymentReferenceCheck
	 */
	public String getPaymentReferenceCheck() {
		return paymentReferenceCheck;
	}

	/**
	 * @param paymentReferenceCheck the paymentReferenceCheck to set
	 */
	public void setPaymentReferenceCheck(String paymentReferenceCheck) {
		this.paymentReferenceCheck = paymentReferenceCheck;
	}

	/**
	 * @return the fraudPredict
	 */
	public String getFraudPredict() {
		return fraudPredict;
	}

	/**
	 * @param fraudPredict the fraudPredict to set
	 */
	public void setFraudPredict(String fraudPredict) {
		this.fraudPredict = fraudPredict;
	}

	/**
	 * @return the customCheck
	 */
	public String getCustomCheck() {
		return customCheck;
	}

	/**
	 * @param customCheck the customCheck to set
	 */
	public void setCustomCheck(String customCheck) {
		this.customCheck = customCheck;
	}

	/**
	 * @return the atlasSTPCodes
	 */
	public String getAtlasSTPCodes() {
		return atlasSTPCodes;
	}

	/**
	 * @param atlasSTPCodes the atlasSTPCodes to set
	 */
	public void setAtlasSTPCodes(String atlasSTPCodes) {
		this.atlasSTPCodes = atlasSTPCodes;
	}

	/**
	 * @return the fundsInId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param fundsInId the fundsInId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

}
