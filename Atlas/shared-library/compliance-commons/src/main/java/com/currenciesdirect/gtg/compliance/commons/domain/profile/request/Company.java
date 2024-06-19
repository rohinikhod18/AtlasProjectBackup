/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.profile.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class Company.
 *
 * @author manish
 */
public class Company implements Serializable {

	@ApiModelProperty(value = "The full billing address", example = "3 Stable Mews, London, WC1",  required = true)
	@JsonProperty(value = "billing_address")
	private String billingAddress;

	/** The company phone. */
	@ApiModelProperty(value = "The company phone number", example = "+44-708881817", required = true)
	@JsonProperty(value = "company_phone")
	private String companyPhone;

	/** The shipping address. */
	@ApiModelProperty(value = "The shipping address", example = "", required = true)
	@JsonProperty(value = "shipping_address")
	private String shippingAddress;

	/** The vat no. */
	@ApiModelProperty(value = "The VAT number", example = "GB123456789", required = true)
	@JsonProperty(value = "vat_no")
	private String vatNo;

	/** The country region. */
	@ApiModelProperty(value = "The country region", required = true)
	@JsonProperty(value = "country_region")
	private String countryRegion;

	/** The country of establishment. */
	@ApiModelProperty(value = "The country of establishment", required = true)
	@JsonProperty(value = "country_of_establishment")
	private String countryOfEstablishment;

	/** The corporate type. */
	@ApiModelProperty(value = "The corporate type", example = "Ltd", required = true)
	@JsonProperty(value = "corporate_type")
	private String corporateType;

	/** The registration no. */
	@ApiModelProperty(value = "The company registration number", example = "13243546", required = true)
	@JsonProperty(value = "registration_no")
	private String registrationNo;

	/** The incorporation date. */
	@ApiModelProperty(value = "The incorporation date", required = true)
	@JsonProperty(value = "incorporation_date")
	private String incorporationDate;

	/** The t and c signed date. */
	@ApiModelProperty(value = "The terms and conditions signed date", required = true)
	@JsonProperty(value = "tc_signed_date")
	private String  tAndcSignedDate;

	/** The industry. */
	@ApiModelProperty(value = "The type of industry", example = "Retail - Misc", required = true)
	@JsonProperty(value = "industry")
	private String industry;

	/** The etailer. */
	@ApiModelProperty(value = "Whether this account is a merchant that sells on the internet and makes use of our CD Collections Account service to collect Funds in a foreign currency", example = "false", required = true)
	@JsonProperty(value = "etailer")
	private String etailer;

	/** The option. */
	@ApiModelProperty(value = "The option", required = true)
	@JsonProperty(value = "option")
	private String option;

	/** The type of financial account. */
	@ApiModelProperty(value = "The type of financial account", required = true)
	@JsonProperty(value = "type_of_financial_account")
	private String typeOfFinancialAccount;

	/** The ccj. */
	@ApiModelProperty(value = "Whether account is subject to a County Court Judgment (True or False)", required = true)
	@JsonProperty(value = "ccj")
	private String ccj;

	/** The ongoing due diligence date. */
	@ApiModelProperty(value = "The next date for a due diligence review. All accounts are reviewed every 3-years for KYC purposes", required = true)
	@JsonProperty(value = "ongoing_due_diligence_date")
	private String ongoingDueDiligenceDate;

	/** The est no transactions pcm. */
	@ApiModelProperty(value = "The estimated number of transactions per calendar month", example = "4", required = true)
	@JsonProperty(value = "est_no_transactions_pcm")
	private String estNoTransactionsPcm;

	/**
	 * Gets the billing address.
	 *
	 * @return the billing address
	 */
	public String getBillingAddress() {
		return billingAddress;
	}

	/**
	 * Sets the billing address.
	 *
	 * @param billingAddress the new billing address
	 */
	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	/**
	 * Gets the company phone.
	 *
	 * @return the company phone
	 */
	public String getCompanyPhone() {
		return companyPhone;
	}

	/**
	 * Sets the company phone.
	 *
	 * @param companyPhone the new company phone
	 */
	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	/**
	 * Gets the shipping address.
	 *
	 * @return the shipping address
	 */
	public String getShippingAddress() {
		return shippingAddress;
	}

	/**
	 * Sets the shipping address.
	 *
	 * @param shippingAddress the new shipping address
	 */
	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	/**
	 * Gets the vat no.
	 *
	 * @return the vat no
	 */
	public String getVatNo() {
		return vatNo;
	}

	/**
	 * Sets the vat no.
	 *
	 * @param vatNo the new vat no
	 */
	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	/**
	 * Gets the country region.
	 *
	 * @return the country region
	 */
	public String getCountryRegion() {
		return countryRegion;
	}

	/**
	 * Sets the country region.
	 *
	 * @param countryRegion the new country region
	 */
	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	/**
	 * Gets the country of establishment.
	 *
	 * @return the country of establishment
	 */
	public String getCountryOfEstablishment() {
		return countryOfEstablishment;
	}

	/**
	 * Sets the country of establishment.
	 *
	 * @param countryOfEstablishment the new country of establishment
	 */
	public void setCountryOfEstablishment(String countryOfEstablishment) {
		this.countryOfEstablishment = countryOfEstablishment;
	}

	/**
	 * Gets the corporate type.
	 *
	 * @return the corporate type
	 */
	public String getCorporateType() {
		return corporateType;
	}

	/**
	 * Sets the corporate type.
	 *
	 * @param corporateType the new corporate type
	 */
	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}

	/**
	 * Gets the registration no.
	 *
	 * @return the registration no
	 */
	public String getRegistrationNo() {
		return registrationNo;
	}

	/**
	 * Sets the registration no.
	 *
	 * @param registrationNo the new registration no
	 */
	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	/**
	 * Gets the incorporation date.
	 *
	 * @return the incorporation date
	 */
	public String getIncorporationDate() {
		return incorporationDate;
	}

	/**
	 * Sets the incorporation date.
	 *
	 * @param incorporationDate the new incorporation date
	 */
	public void setIncorporationDate(String incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	/**
	 * Gets the t andc signed date.
	 *
	 * @return the t andc signed date
	 */
	public String gettAndcSignedDate() {
		return tAndcSignedDate;
	}

	/**
	 * Sets the t andc signed date.
	 *
	 * @param tAndcSignedDate the new t andc signed date
	 */
	public void settAndcSignedDate(String tAndcSignedDate) {
		this.tAndcSignedDate = tAndcSignedDate;
	}

	/**
	 * Gets the industry.
	 *
	 * @return the industry
	 */
	public String getIndustry() {
		return industry;
	}

	/**
	 * Sets the industry.
	 *
	 * @param industry the new industry
	 */
	public void setIndustry(String industry) {
		this.industry = industry;
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
	 * Gets the option.
	 *
	 * @return the option
	 */
	public String getOption() {
		return option;
	}

	/**
	 * Sets the option.
	 *
	 * @param option the new option
	 */
	public void setOption(String option) {
		this.option = option;
	}

	/**
	 * Gets the type of financial account.
	 *
	 * @return the type of financial account
	 */
	public String getTypeOfFinancialAccount() {
		return typeOfFinancialAccount;
	}

	/**
	 * Sets the type of financial account.
	 *
	 * @param typeOfFinancialAccount the new type of financial account
	 */
	public void setTypeOfFinancialAccount(String typeOfFinancialAccount) {
		this.typeOfFinancialAccount = typeOfFinancialAccount;
	}

	/**
	 * Gets the ccj.
	 *
	 * @return the ccj
	 */
	public String getCcj() {
		return ccj;
	}

	/**
	 * Sets the ccj.
	 *
	 * @param ccj the new ccj
	 */
	public void setCcj(String ccj) {
		this.ccj = ccj;
	}

	/**
	 * Gets the ongoing due diligence date.
	 *
	 * @return the ongoing due diligence date
	 */
	public String getOngoingDueDiligenceDate() {
		return ongoingDueDiligenceDate;
	}

	/**
	 * Sets the ongoing due diligence date.
	 *
	 * @param ongoingDueDiligenceDate the new ongoing due diligence date
	 */
	public void setOngoingDueDiligenceDate(String ongoingDueDiligenceDate) {
		this.ongoingDueDiligenceDate = ongoingDueDiligenceDate;
	}

	/**
	 * Gets the est no transactions pcm.
	 *
	 * @return the est no transactions pcm
	 */
	public String getEstNoTransactionsPcm() {
		return estNoTransactionsPcm;
	}

	/**
	 * Sets the est no transactions pcm.
	 *
	 * @param estNoTransactionsPcm the new est no transactions pcm
	 */
	public void setEstNoTransactionsPcm(String estNoTransactionsPcm) {
		this.estNoTransactionsPcm = estNoTransactionsPcm;
	}

}
