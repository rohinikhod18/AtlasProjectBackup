package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsout.response;

import java.io.Serializable;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response.Feature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DecisionDrivers implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Device Type. */
	@JsonProperty("device_type")
	private Feature deviceType;

	/** The Device Name. */
	@ApiModelProperty(value = "Device Model Name", example = "Kindle Fire 72.2.7", required = true)
	@JsonProperty("device_name")
	private Feature deviceName;

	/** The Browser Online. */
	@JsonProperty("browser_online")
	private Feature browserOnline;

	/** The Device Manufacturer. */
	@ApiModelProperty(value = "Manufacturer of Device", example = "Amazon", required = true)
	@JsonProperty("device_manufacturer")
	private Feature deviceManufacturer;

	/** The Organization Code. */
	@JsonProperty("org_code")
	private Feature orgCode;

	/** The Screen Resolution. */
	@JsonProperty("screen_resolution")
	private Feature screenResolution;

	/** The Browser Language. */
	@JsonProperty("browser_lang")
	private Feature browserLang;

	/** The Device OS Type. */
	@JsonProperty("device_os_type")
	private Feature deviceOsType;

	/** The Source Application. */
	@JsonProperty("source_application")
	private Feature sourceApplication;

	/** The Browser Type. */
	@JsonProperty("browser_type")
	private Feature browserType;

	/** The Browser Version. */
	@JsonProperty("browser_version")
	private Feature browserVersion;

	/** The Sell Currency. */
	@JsonProperty("sell_currency")
	private Feature sellCurrency;

	/** The Beneficiary Type. */
	@JsonProperty("beneficiary_type")
	private Feature beneficiaryType;

	/** The Purpose of Trade. */
	@JsonProperty("purpose_of_trade")
	private Feature purposeOfTrade;

	/** The Buy Currency. */
	@JsonProperty("buy_currency")
	private Feature buyCurrency;

	/** The Buy Amount. */
	@JsonProperty("buying_amount")
	private Feature buyingAmount;

	/** The Beneficiary Currency Code. */
	@JsonProperty("beneficiary_currency_code")
	private Feature beneficiaryCurrencyCode;

	/** The Customer Type. */
	@JsonProperty("cust_type")
	private Feature customerType;

	/** The Beneficiary Email Domain. */
	@JsonProperty("beneficiary_email_domain")
	private Feature beneficiaryEmailDomain;

	/** The Beneficiary Bank Name. */
	@JsonProperty("beneficiary_bank_name")
	private Feature beneficiaryBankName;

	/** The Beneficiary Country. */
	@JsonProperty("beneficiary_country")
	private Feature beneficiaryCountry;

	/**
	 * @return deviceType
	 */
	public Feature getDeviceType() {
		return deviceType;
	}

	/**
	 * @return deviceName
	 */
	public Feature getDeviceName() {
		return deviceName;
	}

	/**
	 * @return browserOnline
	 */
	public Feature getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * @return deviceManufacturer
	 */
	public Feature getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * @return orgCode
	 */
	public Feature getOrgCode() {
		return orgCode;
	}

	/**
	 * @return screenResolution
	 */
	public Feature getScreenResolution() {
		return screenResolution;
	}

	/**
	 * @return browserLang
	 */
	public Feature getBrowserLang() {
		return browserLang;
	}

	/**
	 * @return deviceOsType
	 */
	public Feature getDeviceOsType() {
		return deviceOsType;
	}

	/**
	 * @return sourceApplication
	 */
	public Feature getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * @return browserType
	 */
	public Feature getBrowserType() {
		return browserType;
	}

	/**
	 * @return browserVersion
	 */
	public Feature getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * @return sellCurrency
	 */
	public Feature getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * @return beneficiaryType
	 */
	public Feature getBeneficiaryType() {
		return beneficiaryType;
	}

	/**
	 * @return purposeOfTrade
	 */
	public Feature getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * @return buyCurrency
	 */
	public Feature getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * @return buyingAmount
	 */
	public Feature getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * @return beneficiaryCurrencyCode
	 */
	public Feature getBeneficiaryCurrencyCode() {
		return beneficiaryCurrencyCode;
	}

	/**
	 * @return customerType
	 */
	public Feature getCustomerType() {
		return customerType;
	}

	/**
	 * @return beneficiaryEmailDomain
	 */
	public Feature getBeneficiaryEmailDomain() {
		return beneficiaryEmailDomain;
	}

	/**
	 * @return beneficiaryBankName
	 */
	public Feature getBeneficiaryBankName() {
		return beneficiaryBankName;
	}

	/**
	 * @return beneficiaryCountry
	 */
	public Feature getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	/**
	 * @param deviceType
	 */
	public void setDeviceType(Feature deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * @param deviceName
	 */
	public void setDeviceName(Feature deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * @param browserOnline
	 */
	public void setBrowserOnline(Feature browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * @param deviceManufacturer
	 */
	public void setDeviceManufacturer(Feature deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * @param orgCode
	 */
	public void setOrgCode(Feature orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @param screenResolution
	 */
	public void setScreenResolution(Feature screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * @param browserLang
	 */
	public void setBrowserLang(Feature browserLang) {
		this.browserLang = browserLang;
	}

	/**
	 * @param deviceOsType
	 */
	public void setDeviceOsType(Feature deviceOsType) {
		this.deviceOsType = deviceOsType;
	}

	/**
	 * @param sourceApplication
	 */
	public void setSourceApplication(Feature sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * @param browserType
	 */
	public void setBrowserType(Feature browserType) {
		this.browserType = browserType;
	}

	/**
	 * @param browserVersion
	 */
	public void setBrowserVersion(Feature browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * @param sellCurrency
	 */
	public void setSellCurrency(Feature sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	/**
	 * @param beneficiaryType
	 */
	public void setBeneficiaryType(Feature beneficiaryType) {
		this.beneficiaryType = beneficiaryType;
	}

	/**
	 * @param purposeOfTrade
	 */
	public void setPurposeOfTrade(Feature purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * @param buyCurrency
	 */
	public void setBuyCurrency(Feature buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * @param buyingAmount
	 */
	public void setBuyingAmount(Feature buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * @param beneficiaryCurrencyCode
	 */
	public void setBeneficiaryCurrencyCode(Feature beneficiaryCurrencyCode) {
		this.beneficiaryCurrencyCode = beneficiaryCurrencyCode;
	}

	/**
	 * @param customerType
	 */
	public void setCustomerType(Feature customerType) {
		this.customerType = customerType;
	}

	/**
	 * @param beneficiaryEmailDomain
	 */
	public void setBeneficiaryEmailDomain(Feature beneficiaryEmailDomain) {
		this.beneficiaryEmailDomain = beneficiaryEmailDomain;
	}

	/**
	 * @param beneficiaryBankName
	 */
	public void setBeneficiaryBankName(Feature beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}

	/**
	 * @param beneficiaryCountry
	 */
	public void setBeneficiaryCountry(Feature beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}
}
