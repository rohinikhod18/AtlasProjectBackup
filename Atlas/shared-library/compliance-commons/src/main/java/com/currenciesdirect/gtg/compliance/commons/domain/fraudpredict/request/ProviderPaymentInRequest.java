/*
 * Copyright 2012-2018 Currencies Direct Ltd, United Kingdom
 *
 * compliance-commons: ProviderPaymentInRequest.java
 * Last modified: 11/05/2018
*/
package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.request;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ProviderPaymentInRequest.
 */
public class ProviderPaymentInRequest {
  
  /** The transaction currency. */
  @JsonProperty("transaction_currency")
  private String transactionCurrency;
  
  /** The customer no. */
  @JsonProperty("customer_no")
  private String customerNo;
  
  /** The purpose of trade. */
  @JsonProperty("purpose_of_trade")
  private String purposeOfTrade;
  
  /** The risk score. */
  @JsonProperty("risk_score")
  private String riskScore;
  
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
  
  /** The payment method. */
  @JsonProperty("payment_method")
  private String paymentMethod;
  
  /** The bill ad line 1. */
  @JsonProperty("bill_ad_line1")
  private String billAdLine1;
  
  /** The third party payment. */
  @JsonProperty("third_party_payment")
  private String thirdPartyPayment;
  
  /** The bill ad zip. */
  @JsonProperty("bill_ad_zip")
  private String billAdZip;
  
  /** The cust type. */
  @JsonProperty("cust_type")
  private String custType;
  
  /** The country of fund. */
  @JsonProperty("country_of_fund")
  private String countryOfFund;
  
  /** The source application. */
  @JsonProperty("source_application")
  private String sourceApplication;
  
  /** The selling amount gbp value. */
  @JsonProperty("selling_amount_gbp_value")
  private String sellingAmountGbpValue;
  
  /** The turnover. */
  @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
  @JsonProperty("turnover")
  private String turnover;
  
  /** The contract number. */
  @JsonProperty("contract_number")
  private String contractNumber;
  
  /**
   * Gets the transaction currency.
   *
   * @return the transactionCurrency
   */
  public String getTransactionCurrency() {
    return transactionCurrency;
  }
  
  /**
   * Sets the transaction currency.
   *
   * @param transactionCurrency the transactionCurrency to set
   */
  public void setTransactionCurrency(String transactionCurrency) {
    this.transactionCurrency = transactionCurrency;
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
   * @param customerNo the customerNo to set
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
   * @param purposeOfTrade the purposeOfTrade to set
   */
  public void setPurposeOfTrade(String purposeOfTrade) {
    this.purposeOfTrade = purposeOfTrade;
  }
  
  /**
   * Gets the risk score.
   *
   * @return the riskScore
   */
  public String getRiskScore() {
    return riskScore;
  }
  
  /**
   * Sets the risk score.
   *
   * @param riskScore the riskScore to set
   */
  public void setRiskScore(String riskScore) {
    this.riskScore = riskScore;
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
   * @param orgCode the orgCode to set
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
   * @param screenResolution the screenResolution to set
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
   * @param browserType the browserType to set
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
   * @param browserVersion the browserVersion to set
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
   * @param deviceType the deviceType to set
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
   * @param deviceName the deviceName to set
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
   * @param deviceManufacturer the deviceManufacturer to set
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
   * @param deviceOsType the deviceOsType to set
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
   * @param browserLang the browserLang to set
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
   * @param browserOnline the browserOnline to set
   */
  public void setBrowserOnline(String browserOnline) {
    this.browserOnline = browserOnline;
  }
  
  /**
   * Gets the payment method.
   *
   * @return the paymentMethod
   */
  public String getPaymentMethod() {
    return paymentMethod;
  }
  
  /**
   * Sets the payment method.
   *
   * @param paymentMethod the paymentMethod to set
   */
  public void setPaymentMethod(String paymentMethod) {
    this.paymentMethod = paymentMethod;
  }
  
  /**
   * Gets the bill ad line 1.
   *
   * @return the billAdLine1
   */
  public String getBillAdLine1() {
    return billAdLine1;
  }
  
  /**
   * Sets the bill ad line 1.
   *
   * @param billAdLine1 the billAdLine1 to set
   */
  public void setBillAdLine1(String billAdLine1) {
    this.billAdLine1 = billAdLine1;
  }
  
  /**
   * Gets the third party payment.
   *
   * @return the thirdPartyPayment
   */
  public String getThirdPartyPayment() {
    return thirdPartyPayment;
  }
  
  /**
   * Sets the third party payment.
   *
   * @param thirdPartyPayment the thirdPartyPayment to set
   */
  public void setThirdPartyPayment(String thirdPartyPayment) {
    this.thirdPartyPayment = thirdPartyPayment;
  }
  
  /**
   * Gets the bill ad zip.
   *
   * @return the billAdZip
   */
  public String getBillAdZip() {
    return billAdZip;
  }
  
  /**
   * Sets the bill ad zip.
   *
   * @param billAdZip the billAdZip to set
   */
  public void setBillAdZip(String billAdZip) {
    this.billAdZip = billAdZip;
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
   * @param custType the custType to set
   */
  public void setCustType(String custType) {
    this.custType = custType;
  }
  
  /**
   * Gets the country of fund.
   *
   * @return the countryOfFund
   */
  public String getCountryOfFund() {
    return countryOfFund;
  }
  
  /**
   * Sets the country of fund.
   *
   * @param countryOfFund the countryOfFund to set
   */
  public void setCountryOfFund(String countryOfFund) {
    this.countryOfFund = countryOfFund;
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
   * @param sourceApplication the sourceApplication to set
   */
  public void setSourceApplication(String sourceApplication) {
    this.sourceApplication = sourceApplication;
  }
  
  /**
   * Gets the selling amount gbp value.
   *
   * @return the sellingAmountGbpValue
   */
  public String getSellingAmountGbpValue() {
    return sellingAmountGbpValue;
  }
  
  /**
   * Sets the selling amount gbp value.
   *
   * @param sellingAmountGbpValue the sellingAmountGbpValue to set
   */
  public void setSellingAmountGbpValue(String sellingAmountGbpValue) {
    this.sellingAmountGbpValue = sellingAmountGbpValue;
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
   * @param turnover the turnover to set
   */
  public void setTurnover(String turnover) {
    this.turnover = turnover;
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
   * @param contractNumber the contractNumber to set
   */
  public void setContractNumber(String contractNumber) {
    this.contractNumber = contractNumber;
  }
  
  
}
