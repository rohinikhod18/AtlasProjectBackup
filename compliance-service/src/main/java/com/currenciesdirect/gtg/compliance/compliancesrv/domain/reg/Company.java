/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.FieldDisplayName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author manish
 *
 */
public class Company implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "The full billing address", example = "3 Stable Mews, London, WC1",  required = true)
	@JsonProperty(value = "billing_address")
	@FieldDisplayName(displayName = "Billing Address")
	private String billingAddress;

	/** The company phone. */
	@ApiModelProperty(value = "The company phone number", example = "+44-708881817", required = true)
	@JsonProperty(value = "company_phone")
	@FieldDisplayName(displayName = "Company Phone")
	private String companyPhone;

	/** The shipping address. */
	@ApiModelProperty(value = "The shipping address", example = "", required = true)
	@JsonProperty(value = "shipping_address")
	@FieldDisplayName(displayName = "Shipping Address")
	private String shippingAddress;

	/** The vat no. */
	@ApiModelProperty(value = "The VAT number", example = "GB123456789", required = true)
	@JsonProperty(value = "vat_no")
	@FieldDisplayName(displayName = "Vat No")
	private String vatNo;

	/** The country region. */
	@ApiModelProperty(value = "The country region", required = true)
	@JsonProperty(value = "country_region")
	@FieldDisplayName(displayName = "Country Region")
	private String countryRegion;

	/** The country of establishment. */
	@ApiModelProperty(value = "The country of establishment", required = true)
	@JsonProperty(value = "country_of_establishment")
	@FieldDisplayName(displayName = "Country Of Establishment")
	private String countryOfEstablishment;

	/** The corporate type. */
	@ApiModelProperty(value = "The corporate type", example = "Ltd", required = true)
	@JsonProperty(value = "corporate_type")
	@FieldDisplayName(displayName = "Corporate Type")
	private String corporateType;

	/** The registration no. */
	@ApiModelProperty(value = "The company registration number", example = "13243546", required = true)
	@JsonProperty(value = "registration_no")
	@FieldDisplayName(displayName = "Registration No")
	private String registrationNo;

	/** The incorporation date. */
	@ApiModelProperty(value = "The incorporation date", required = true)
	@JsonProperty(value = "incorporation_date")
	@FieldDisplayName(displayName = "Incorporation Date")
	private String incorporationDate;

	/** The t and c signed date. */
	@ApiModelProperty(value = "The terms and conditions signed date", required = true)
	@JsonProperty(value = "tc_signed_date")
	@FieldDisplayName(displayName = "TC Signed Date")
	private String tAndcSignedDate;

	/** The industry. */
	@ApiModelProperty(value = "The type of industry", example = "Retail - Misc", required = true)
	@JsonProperty(value = "industry")
	@FieldDisplayName(displayName = "Industry")
	private String industry;

	/** The etailer. */
	@ApiModelProperty(value = "Whether this account is a merchant that sells on the internet and makes use of our CD Collections Account service to collect Funds in a foreign currency", example = "false", required = true)
	@JsonProperty(value = "etailer")
	@FieldDisplayName(displayName = "Etailer")
	private String etailer;

	/** The option. */
	@ApiModelProperty(value = "Whether this account uses our CD Financial Management options service", required = true)
	@JsonProperty(value = "option")
	@FieldDisplayName(displayName = "Option")
	private String option;

	/** The type of financial account. */
	@ApiModelProperty(value = "The type of financial account", required = true)
	@JsonProperty(value = "type_of_financial_account")
	@FieldDisplayName(displayName = "Type Of Financial Account")
	private String typeOfFinancialAccount;

	/** The ccj. */
	@ApiModelProperty(value = "Whether account is subject to a County Court Judgment (True or False)", required = true)
	@JsonProperty(value = "ccj")
	@FieldDisplayName(displayName = "CCJ")
	private String ccj;

	/** The ongoing due diligence date. */
	@ApiModelProperty(value = "The next date for a due diligence review. All accounts are reviewed every 3-years for KYC purposes", required = true)
	@JsonProperty(value = "ongoing_due_diligence_date")
	@FieldDisplayName(displayName = "Ongoing Due Diligence Date")
	private String ongoingDueDiligenceDate;

	/** The est no transactions pcm. */
	@ApiModelProperty(value = "The estimated number of transactions per calendar month", example = "4", required = true)
	@JsonProperty(value = "est_no_transactions_pcm")
	@FieldDisplayName(displayName = "Est No Transactions PCM")
	private String estNoTransactionsPcm;

	// default value is set to true
	// update false as in when required
	@JsonIgnore
	private boolean isKYCEligible = false;
	@JsonIgnore
	private boolean isFraugsterEligible = true;
	@JsonIgnore
	private boolean isSanctionEligible = true;
	@JsonIgnore
	private boolean isSanctionPerformed = false;
	@JsonIgnore
	private boolean isFraugsterPerformed = false;

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

	@Override
	public String toString() {
		return "Company [billingAddress=" + billingAddress + ", companyPhone=" + companyPhone + ", shippingAddress="
				+ shippingAddress + ", vatNo=" + vatNo + ", countryRegion=" + countryRegion
				+ ", countryOfEstablishment=" + countryOfEstablishment + ", corporateType=" + corporateType
				+ ", registrationNo=" + registrationNo + ", incorporationDate=" + incorporationDate
				+ ", tAndcSignedDate=" + tAndcSignedDate + ", industry=" + industry + ", etailer=" + etailer
				+ ", option=" + option + ", typeOfFinancialAccount=" + typeOfFinancialAccount + ", ccj=" + ccj
				+ ", ongoingDueDiligenceDate=" + ongoingDueDiligenceDate + ", estNoTransactionsPcm="
				+ estNoTransactionsPcm + "]";
	}

	@SuppressWarnings("squid:S3776")
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((billingAddress == null) ? 0 : billingAddress.hashCode());
		result = prime * result + ((ccj == null) ? 0 : ccj.hashCode());
		result = prime * result + ((companyPhone == null) ? 0 : companyPhone.hashCode());
		result = prime * result + ((corporateType == null) ? 0 : corporateType.hashCode());
		result = prime * result + ((countryOfEstablishment == null) ? 0 : countryOfEstablishment.hashCode());
		result = prime * result + ((countryRegion == null) ? 0 : countryRegion.hashCode());
		result = prime * result + ((estNoTransactionsPcm == null) ? 0 : estNoTransactionsPcm.hashCode());
		result = prime * result + ((etailer == null) ? 0 : etailer.hashCode());
		result = prime * result + ((incorporationDate == null) ? 0 : incorporationDate.hashCode());
		result = prime * result + ((industry == null) ? 0 : industry.hashCode());
		result = prime * result + ((ongoingDueDiligenceDate == null) ? 0 : ongoingDueDiligenceDate.hashCode());
		result = prime * result + ((option == null) ? 0 : option.hashCode());
		result = prime * result + ((registrationNo == null) ? 0 : registrationNo.hashCode());
		result = prime * result + ((shippingAddress == null) ? 0 : shippingAddress.hashCode());
		result = prime * result + ((tAndcSignedDate == null) ? 0 : tAndcSignedDate.hashCode());
		result = prime * result + ((typeOfFinancialAccount == null) ? 0 : typeOfFinancialAccount.hashCode());
		result = prime * result + ((vatNo == null) ? 0 : vatNo.hashCode());
		return result;
	}

	@SuppressWarnings("squid:S3776")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (billingAddress == null) {
			if (other.billingAddress != null)
				return false;
		} else if (!billingAddress.equals(other.billingAddress)) {
			return false;
		  }	
		if (ccj == null) {
			if (other.ccj != null)
				return false;
		} else if (!ccj.equals(other.ccj)) {
			return false;
		  }	
		if (companyPhone == null) {
			if (other.companyPhone != null)
				return false;
		} else if (!companyPhone.equals(other.companyPhone)) {
			return false;
		  }	
		if (corporateType == null) {
			if (other.corporateType != null)
				return false;
		} else if (!corporateType.equals(other.corporateType)) {
			return false;
		  }	
		if (countryOfEstablishment == null) {
			if (other.countryOfEstablishment != null)
				return false;
		} else if (!countryOfEstablishment.equals(other.countryOfEstablishment)) {
			return false;
		  }	
		if (countryRegion == null) {
			if (other.countryRegion != null)
				return false;
		} else if (!countryRegion.equals(other.countryRegion)) {
			return false;
		  }	
		if (estNoTransactionsPcm == null) {
			if (other.estNoTransactionsPcm != null)
				return false;
		} else if (!estNoTransactionsPcm.equals(other.estNoTransactionsPcm)) {
			return false;
		  }	
		if (etailer == null) {
			if (other.etailer != null)
				return false;
		} else if (!etailer.equals(other.etailer))  {
			return false;
		  }
		if (incorporationDate == null) {
			if (other.incorporationDate != null)
				return false;
		} else if (!incorporationDate.equals(other.incorporationDate)) {
			return false;
		  }
		if (industry == null) {
			if (other.industry != null)
				return false;
		} else if (!industry.equals(other.industry)) {
			return false;
		  }
		if (ongoingDueDiligenceDate == null) {
			if (other.ongoingDueDiligenceDate != null)
				return false;
		} else if (!ongoingDueDiligenceDate.equals(other.ongoingDueDiligenceDate)) {
			return false;
		  }
		if (option == null) {
			if (other.option != null)
				return false;
		} else if (!option.equals(other.option)) {
			return false;
		  }
		if (registrationNo == null) {
			if (other.registrationNo != null)
				return false;
		} else if (!registrationNo.equals(other.registrationNo)) {
			return false;
		  }
		if (shippingAddress == null) {
			if (other.shippingAddress != null)
				return false;
		} else if (!shippingAddress.equals(other.shippingAddress)) {
			return false;
		  }
		if (tAndcSignedDate == null) {
			if (other.tAndcSignedDate != null)
				return false;
		} else if (!tAndcSignedDate.equals(other.tAndcSignedDate)) {
			return false;
		  }
		if (typeOfFinancialAccount == null) {
			if (other.typeOfFinancialAccount != null)
				return false;
		} else if (!typeOfFinancialAccount.equals(other.typeOfFinancialAccount)) {
			return false;
		  }
		if (vatNo == null) {
			if (other.vatNo != null)
				return false;
		} else if (!vatNo.equals(other.vatNo)) {
			return false;
		  }
		return true;
	}

	@JsonIgnore
	public boolean isKYCEligible() {
		return isKYCEligible;
	}

	public void setKYCEligible(boolean isKYCEligible) {
		this.isKYCEligible = isKYCEligible;
	}

	@JsonIgnore
	public boolean isFraugsterEligible() {
		return isFraugsterEligible;
	}

	public void setFraugsterEligible(boolean isFraugsterEligible) {
		this.isFraugsterEligible = isFraugsterEligible;
	}

	@JsonIgnore
	public boolean isSanctionEligible() {
		return isSanctionEligible;
	}

	public void setSanctionEligible(boolean isSanctionEligible) {
		this.isSanctionEligible = isSanctionEligible;
	}

	public boolean isSanctionPerformed() {
		return isSanctionPerformed;
	}

	@JsonIgnore
	public void setSanctionPerformed(boolean isSanctionPerformed) {
		this.isSanctionPerformed = isSanctionPerformed;
	}
	@JsonIgnore
	public void setFraugsterPerformed(boolean isFraugsterPerformed) {
		this.isFraugsterPerformed = isFraugsterPerformed;
	}

}
