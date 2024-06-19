package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsin.response;

import java.io.Serializable;
import com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response.Feature;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DecisionDrivers.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DecisionDrivers implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Customer Type. */
	@JsonProperty("cust_type")
	private Feature customerType;

	/** The Transaction Currency. */
	@JsonProperty("transaction_currency")
	private Feature transactionCurrency;

	/** The Payment Method. */
	@JsonProperty("payment_method")
	private Feature paymentMethod;

	/** The Browser Online. */
	@JsonProperty("browser_online")
	private Feature browserOnline;

	/** The Device Type. */
	@ApiModelProperty(value = "Type of Device", example = "Desktop, Tablet, Mobile", required = true)
	@JsonProperty("device_type")
	private Feature deviceType;

	/** The Device Manufacturer. */
	@ApiModelProperty(value = "Manufacturer of Device", required = true)
	@JsonProperty("device_manufacturer")
	private Feature deviceManufacturer;

	/** The Third Party Payment. */
	@JsonProperty("third_party_payment")
	private Feature thirdPartyPayment;

	/** The Device Name. */
	@ApiModelProperty(value = "Device Model Name", required = true, example = "Galaxy Note 6")
	@JsonProperty("device_name")
	private Feature deviceName;

	/** The Device OS Type. */
	@JsonProperty("device_os_type")
	private Feature deviceOsType;

	/** The Risk Score. */
	@JsonProperty("risk_score")
	private Feature riskScore;

	/** The Country Of Fund. */
	@JsonProperty("country_of_fund")
	private Feature countryOfFund;

	/** The Browser Type. */
	@JsonProperty("browser_type")
	private Feature browserType;

	/** The Screen Resolution. */
	@JsonProperty("screen_resolution")
	private Feature screenResolution;

	/** The Browser Version. */
	@JsonProperty("browser_version")
	private Feature browserVersion;

	/** The Browser Language. */
	@JsonProperty("browser_lang")
	private Feature browserLang;

	/** The Organization Code. */
	@JsonProperty("org_code")
	private Feature orgCode;

	/** The Purpose Of Trade. */
	@JsonProperty("purpose_of_trade")
	private Feature purposeOfTrade;

	/** The Source Application. */
	@JsonProperty("source_application")
	private Feature sourceApplication;

	
	/** The selling amount gbp value. */
	@JsonProperty("selling_amount_gbp_value")
	private Feature sellingAmountGbpValue;
	
	/**
	 * Gets the customer type.
	 *
	 * @return customerType
	 */
	public Feature getCustomerType() {
		return customerType;
	}

	/**
	 * Gets the transaction currency.
	 *
	 * @return transactionCurrency
	 */
	public Feature getTransactionCurrency() {
		return transactionCurrency;
	}

	/**
	 * Gets the payment method.
	 *
	 * @return paymentMethod
	 */
	public Feature getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * Gets the browser online.
	 *
	 * @return browserOnline
	 */
	public Feature getBrowserOnline() {
		return browserOnline;
	}

	/**
	 * Gets the device type.
	 *
	 * @return deviceType
	 */
	public Feature getDeviceType() {
		return deviceType;
	}

	/**
	 * Gets the device manufacturer.
	 *
	 * @return deviceManufacturer
	 */
	public Feature getDeviceManufacturer() {
		return deviceManufacturer;
	}

	/**
	 * Gets the third party payment.
	 *
	 * @return thirdPartyPayment
	 */
	public Feature getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Gets the device name.
	 *
	 * @return deviceName
	 */
	public Feature getDeviceName() {
		return deviceName;
	}

	/**
	 * Gets the device os type.
	 *
	 * @return deviceOsType
	 */
	public Feature getDeviceOsType() {
		return deviceOsType;
	}

	/**
	 * Gets the risk score.
	 *
	 * @return riskScore
	 */
	public Feature getRiskScore() {
		return riskScore;
	}

	/**
	 * Gets the country of fund.
	 *
	 * @return countryOfFund
	 */
	public Feature getCountryOfFund() {
		return countryOfFund;
	}

	/**
	 * Gets the browser type.
	 *
	 * @return browserType
	 */
	public Feature getBrowserType() {
		return browserType;
	}

	/**
	 * Gets the screen resolution.
	 *
	 * @return screenResolution
	 */
	public Feature getScreenResolution() {
		return screenResolution;
	}

	/**
	 * Gets the browser version.
	 *
	 * @return browserVersion
	 */
	public Feature getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Gets the browser lang.
	 *
	 * @return browserLang
	 */
	public Feature getBrowserLang() {
		return browserLang;
	}

	/**
	 * Gets the org code.
	 *
	 * @return orgCode
	 */
	public Feature getOrgCode() {
		return orgCode;
	}

	/**
	 * Gets the purpose of trade.
	 *
	 * @return purposeOfTrade
	 */
	public Feature getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * Gets the source application.
	 *
	 * @return sourceApplication
	 */
	public Feature getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType the new customer type
	 */
	public void setCustomerType(Feature customerType) {
		this.customerType = customerType;
	}

	/**
	 * Sets the transaction currency.
	 *
	 * @param transactionCurrency the new transaction currency
	 */
	public void setTransactionCurrency(Feature transactionCurrency) {
		this.transactionCurrency = transactionCurrency;
	}

	/**
	 * Sets the payment method.
	 *
	 * @param paymentMethod the new payment method
	 */
	public void setPaymentMethod(Feature paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Sets the browser online.
	 *
	 * @param browserOnline the new browser online
	 */
	public void setBrowserOnline(Feature browserOnline) {
		this.browserOnline = browserOnline;
	}

	/**
	 * Sets the device type.
	 *
	 * @param deviceType the new device type
	 */
	public void setDeviceType(Feature deviceType) {
		this.deviceType = deviceType;
	}

	/**
	 * Sets the device manufacturer.
	 *
	 * @param deviceManufacturer the new device manufacturer
	 */
	public void setDeviceManufacturer(Feature deviceManufacturer) {
		this.deviceManufacturer = deviceManufacturer;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment the new third party payment
	 */
	public void setThirdPartyPayment(Feature thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Sets the device name.
	 *
	 * @param deviceName the new device name
	 */
	public void setDeviceName(Feature deviceName) {
		this.deviceName = deviceName;
	}

	/**
	 * Sets the device os type.
	 *
	 * @param deviceOsType the new device os type
	 */
	public void setDeviceOsType(Feature deviceOsType) {
		this.deviceOsType = deviceOsType;
	}

	/**
	 * Sets the risk score.
	 *
	 * @param riskScore the new risk score
	 */
	public void setRiskScore(Feature riskScore) {
		this.riskScore = riskScore;
	}

	/**
	 * Sets the country of fund.
	 *
	 * @param countryOfFund the new country of fund
	 */
	public void setCountryOfFund(Feature countryOfFund) {
		this.countryOfFund = countryOfFund;
	}

	/**
	 * Sets the browser type.
	 *
	 * @param browserType the new browser type
	 */
	public void setBrowserType(Feature browserType) {
		this.browserType = browserType;
	}

	/**
	 * Sets the screen resolution.
	 *
	 * @param screenResolution the new screen resolution
	 */
	public void setScreenResolution(Feature screenResolution) {
		this.screenResolution = screenResolution;
	}

	/**
	 * Sets the browser version.
	 *
	 * @param browserVersion the new browser version
	 */
	public void setBrowserVersion(Feature browserVersion) {
		this.browserVersion = browserVersion;
	}

	/**
	 * Sets the browser lang.
	 *
	 * @param browserLang the new browser lang
	 */
	public void setBrowserLang(Feature browserLang) {
		this.browserLang = browserLang;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(Feature orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Sets the purpose of trade.
	 *
	 * @param purposeOfTrade the new purpose of trade
	 */
	public void setPurposeOfTrade(Feature purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication the new source application
	 */
	public void setSourceApplication(Feature sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the selling amount gbp value.
	 *
	 * @return the selling amount gbp value
	 */
	public Feature getSellingAmountGbpValue() {
		return sellingAmountGbpValue;
	}

	/**
	 * Sets the selling amount gbp value.
	 *
	 * @param sellingAmountGbpValue the new selling amount gbp value
	 */
	public void setSellingAmountGbpValue(Feature sellingAmountGbpValue) {
		this.sellingAmountGbpValue = sellingAmountGbpValue;
	}
}