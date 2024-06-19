/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author manish
 *
 */
public class Company implements Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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
	@JsonProperty(value="tc_signed_date")
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
	@ApiModelProperty(value = "Whether this account uses our CD Financial Management options service", required = true)
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

	public String getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(String billingAddress) {
		this.billingAddress = billingAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getCountryRegion() {
		return countryRegion;
	}

	public void setCountryRegion(String countryRegion) {
		this.countryRegion = countryRegion;
	}

	public String getCountryOfEstablishment() {
		return countryOfEstablishment;
	}

	public void setCountryOfEstablishment(String countryOfEstablishment) {
		this.countryOfEstablishment = countryOfEstablishment;
	}

	public String getCorporateType() {
		return corporateType;
	}

	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}

	public String getRegistrationNo() {
		return registrationNo;
	}

	public void setRegistrationNo(String registrationNo) {
		this.registrationNo = registrationNo;
	}

	public String getIncorporationDate() {
		return incorporationDate;
	}

	public void setIncorporationDate(String incorporationDate) {
		this.incorporationDate = incorporationDate;
	}

	public String gettAndcSignedDate() {
		return tAndcSignedDate;
	}

	public void settAndcSignedDate(String tAndcSignedDate) {
		this.tAndcSignedDate = tAndcSignedDate;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getEtailer() {
		return etailer;
	}

	public void setEtailer(String etailer) {
		this.etailer = etailer;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getTypeOfFinancialAccount() {
		return typeOfFinancialAccount;
	}

	public void setTypeOfFinancialAccount(String typeOfFinancialAccount) {
		this.typeOfFinancialAccount = typeOfFinancialAccount;
	}

	public String getCcj() {
		return ccj;
	}

	public void setCcj(String ccj) {
		this.ccj = ccj;
	}

	public String getOngoingDueDiligenceDate() {
		return ongoingDueDiligenceDate;
	}

	public void setOngoingDueDiligenceDate(String ongoingDueDiligenceDate) {
		this.ongoingDueDiligenceDate = ongoingDueDiligenceDate;
	}

	public String getEstNoTransactionsPcm() {
		return estNoTransactionsPcm;
	}

	public void setEstNoTransactionsPcm(String estNoTransactionsPcm) {
		this.estNoTransactionsPcm = estNoTransactionsPcm;
	}

}
