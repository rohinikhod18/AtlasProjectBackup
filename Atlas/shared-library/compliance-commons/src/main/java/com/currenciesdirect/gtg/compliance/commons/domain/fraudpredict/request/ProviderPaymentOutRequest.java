/*
 * Copyright 2012-2018 Currencies Direct Ltd, United Kingdom
 * compliance-commons: ProviderPaymentInRequest.java
 * Last modified: 11/05/2018
*/
package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ProviderPaymentOutRequest.
 */
public class ProviderPaymentOutRequest {

	/** The buy currency. */
	@JsonProperty("buy_currency")
	private String buyCurrency;

	/** The buying amount. */
	@JsonProperty("buying_amount")
	private String buyingAmount;

	/** The customer no. */
	@JsonProperty("customer_no")
	private String customerNo;

	/** The purpose of trade. */
	@JsonProperty("purpose_of_trade")
	private String purposeOfTrade;

	/** The org code. */
	@JsonProperty("org_code")
	private String orgCode;

	/** The screen resolution. */
	@JsonProperty("screen_resolution")
	private String screenResolution;

	/** The browser type. */
	@JsonProperty("browser_type")
	private String browserType;

	/** The browser version. */
	@JsonProperty("browser_version")
	private String browserVersion;

	/** The device type. */
	@JsonProperty("device_type")
	private String deviceType;

	/** The device name. */
	@ApiModelProperty(value = "Device Model Name", example = "Kindle Fire 72.2.7", required = true)
	@JsonProperty("device_name")
	private String deviceName;

	/** The device manufacturer. */
	@ApiModelProperty(value = "Manufacturer of Device", example = "Amazon", required = true)
	@JsonProperty("device_manufacturer")
	private String deviceManufacturer;

	/** The device os type. */
	@JsonProperty("device_os_type")
	private String deviceOsType;

	/** The browser lang. */
	@JsonProperty("browser_lang")
	private String browserLang;

	/** The browser online. */
	@JsonProperty("browser_online")
	private String browserOnline;

	/** The beneficiary email. */
	@JsonProperty("beneficiary_email")
	private String beneficiaryEmail;

	/** The beneficiary phone. */
	@JsonProperty("beneficiary_phone")
	private String beneficiaryPhone;

	/** The beneficiary country. */
	@JsonProperty("beneficiary_country")
	private String beneficiaryCountry;

	/** The beneficiary currency code. */
	@JsonProperty("beneficiary_currency_code")
	private String beneficiaryCurrencyCode;

	/** The beneficiary bank name. */
	@JsonProperty("beneficiary_bank_name")
	private String beneficiaryBankName;

	/** The beneficiary bank address. */
	@JsonProperty("beneficiary_bank_address")
	private String beneficiaryBankAddress;

	/** The cust type. */
	@JsonProperty("cust_type")
	private String custType;

	/** The source application. */
	@JsonProperty("source_application")
	private String sourceApplication;

	/** The contract number. */
	@JsonProperty("contract_number")
	private String contractNumber;

	/** The beneficiary type. */
	@JsonProperty("beneficiary_type")
	private String beneficiaryType;

	/** The selling Currency */
	@JsonProperty("sell_currency")
	private String sellingCurrency;

	/**
	 * @return sellingCurrency
	 */
	public String getSellingCurrency() {
		return sellingCurrency;
	}

	/**
	 * @param sellingCurrency
	 */
	public void setSellingCurrency(String sellingCurrency) {
		this.sellingCurrency = sellingCurrency;
	}

	/**
	 * Gets the buy currency.
	 *
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * Sets the buy currency.
	 *
	 * @param buyCurrency
	 *            the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * Gets the buying amount.
	 *
	 * @return the buyingAmount
	 */
	public String getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * Sets the buying amount.
	 *
	 * @param buyingAmount
	 *            the buyingAmount to set
	 */
	public void setBuyingAmount(String buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * Gets the customer no.
	 *
	 * @return the customerNo
	 */
	public String getCustomerNo() {
		return customerNo;
	}

	/**
	 * Sets the customer no.
	 *
	 * @param customerNo
	 *            the customerNo to set
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	/**
	 * Gets the purpose of trade.
	 *
	 * @return the purposeOfTrade
	 */
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * Sets the purpose of trade.
	 *
	 * @param purposeOfTrade
	 *            the purposeOfTrade to set
	 */
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the screen resolution.
	 *
	 * @return the screenResolution
	 */
	public String getScreenResolution() {
		return screenResolution;
	}

	/**
	 * Sets the screen resolution.
	 *
	 * @param screenResolution
	 *            the screenResolution to set
	 */
	public void setScreenResolution(String screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * Gets the browser type.
	 *
	 * @return the browserType
	 */
	public String getBrowserType() {
		return browserType;
	}

	/**
	 * Sets the browser type.
	 *
	 * @param browserType
	 *            the browserType to set
	 */
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}

	/**
	 * Gets the browser version.
	 *
	 * @return the browserVersion
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Sets the browser version.
	 *
	 * @param browserVersion
	 *            the browserVersion to set
	 */
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * Gets the device type.
	 *
	 * @return the deviceType
	 */
	public String getDeviceType() {
		return deviceType;
	}

	/**
	 * Sets the device type.
	 *
	 * @param deviceType
	 *            the deviceType to set
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * Gets the device name.
	 *
	 * @return the deviceName
	 */
	public String getDeviceName() {
		return deviceName;
	}

	/**
	 * Sets the device name.
	 *
	 * @param deviceName
	 *            the deviceName to set
	 */
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Gets the device manufacturer.
	 *
	 * @return the deviceManufacturer
	 */
	public String getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * Sets the device manufacturer.
	 *
	 * @param deviceManufacturer
	 *            the deviceManufacturer to set
	 */
	public void setDeviceManufacturer(String deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * Gets the device os type.
	 *
	 * @return the deviceOsType
	 */
	public String getDeviceOsType() {
		return deviceOsType;
	}

	/**
	 * Sets the device os type.
	 *
	 * @param deviceOsType
	 *            the deviceOsType to set
	 */
	public void setDeviceOsType(String deviceOsType) {
		this.deviceOsType = deviceOsType;
	}

	/**
	 * Gets the browser lang.
	 *
	 * @return the browserLang
	 */
	public String getBrowserLang() {
		return browserLang;
	}

	/**
	 * Sets the browser lang.
	 *
	 * @param browserLang
	 *            the browserLang to set
	 */
	public void setBrowserLang(String browserLang) {
		this.browserLang = browserLang;
	}

	/**
	 * Gets the browser online.
	 *
	 * @return the browserOnline
	 */
	public String getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * Sets the browser online.
	 *
	 * @param browserOnline
	 *            the browserOnline to set
	 */
	public void setBrowserOnline(String browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * Gets the beneficiary email.
	 *
	 * @return the beneficiaryEmail
	 */
	public String getBeneficiaryEmail() {
		return beneficiaryEmail;
	}

	/**
	 * Sets the beneficiary email.
	 *
	 * @param beneficiaryEmail
	 *            the beneficiaryEmail to set
	 */
	public void setBeneficiaryEmail(String beneficiaryEmail) {
		this.beneficiaryEmail = beneficiaryEmail;
	}

	/**
	 * Gets the beneficiary phone.
	 *
	 * @return the beneficiaryPhone
	 */
	public String getBeneficiaryPhone() {
		return beneficiaryPhone;
	}

	/**
	 * Sets the beneficiary phone.
	 *
	 * @param beneficiaryPhone
	 *            the beneficiaryPhone to set
	 */
	public void setBeneficiaryPhone(String beneficiaryPhone) {
		this.beneficiaryPhone = beneficiaryPhone;
	}

	/**
	 * Gets the beneficiary country.
	 *
	 * @return the beneficiaryCountry
	 */
	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	/**
	 * Sets the beneficiary country.
	 *
	 * @param beneficiaryCountry
	 *            the beneficiaryCountry to set
	 */
	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}

	/**
	 * Gets the beneficiary currency code.
	 *
	 * @return the beneficiaryCurrencyCode
	 */
	public String getBeneficiaryCurrencyCode() {
		return beneficiaryCurrencyCode;
	}

	/**
	 * Sets the beneficiary currency code.
	 *
	 * @param beneficiaryCurrencyCode
	 *            the beneficiaryCurrencyCode to set
	 */
	public void setBeneficiaryCurrencyCode(String beneficiaryCurrencyCode) {
		this.beneficiaryCurrencyCode = beneficiaryCurrencyCode;
	}

	/**
	 * Gets the beneficiary bank name.
	 *
	 * @return the beneficiaryBankName
	 */
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	/**
	 * Sets the beneficiary bank name.
	 *
	 * @param beneficiaryBankName
	 *            the beneficiaryBankName to set
	 */
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	/**
	 * Gets the beneficiary bank address.
	 *
	 * @return the beneficiaryBankAddress
	 */
	public String getBeneficiaryBankAddress() {
		return beneficiaryBankAddress;
	}

	/**
	 * Sets the beneficiary bank address.
	 *
	 * @param beneficiaryBankAddress
	 *            the beneficiaryBankAddress to set
	 */
	public void setBeneficiaryBankAddress(String beneficiaryBankAddress) {
		this.beneficiaryBankAddress = beneficiaryBankAddress;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType
	 *            the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the source application.
	 *
	 * @return the sourceApplication
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the sourceApplication to set
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the contract number.
	 *
	 * @return the contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * Sets the contract number.
	 *
	 * @param contractNumber
	 *            the contractNumber to set
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	/**
	 * Gets the beneficiary type.
	 *
	 * @return the beneficiaryType
	 */
	public String getBeneficiaryType() {
		return beneficiaryType;
	}

	/**
	 * Sets the beneficiary type.
	 *
	 * @param beneficiaryType
	 *            the beneficiaryType to set
	 */
	public void setBeneficiaryType(String beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

}
