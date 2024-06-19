/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author manish
 *
 */
public class RegistrationCompany{

	@JsonProperty(value = "billing_address")
	private String billingAddress;

	@JsonProperty(value = "company_phone")
	private String companyPhone;

	@JsonProperty(value = "shipping_address")
	private String shippingAddress;

	@JsonProperty(value = "vat_no")
	private String vatNo;

	@JsonProperty(value = "country_region")
	private String countryRegion;

	@JsonProperty(value = "country_of_establishment")
	private String countryOfEstablishment;

	@JsonProperty(value = "corporate_type")
	private String corporateType;

	@JsonProperty(value = "registration_no")
	private String registrationNo;

	@JsonProperty(value = "incorporation_date")
	private String incorporationDate;

	@JsonProperty(value = "tc_signed_date")
	private String tAndcSignedDate;

	@JsonProperty(value = "industry")
	private String industry;

	@JsonProperty(value = "etailer")
	private String etailer;

	@JsonProperty(value = "option")
	private String option;

	@JsonProperty(value = "type_of_financial_account")
	private String typeOfFinancialAccount;

	@JsonProperty(value = "ccj")
	private String ccj;

	@JsonProperty(value = "ongoing_due_diligence_date")
	private String ongoingDueDiligenceDate;

	@JsonProperty(value = "est_no_transactions_pcm")
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
		RegistrationCompany other = (RegistrationCompany) obj;
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
		} else if (!etailer.equals(other.etailer)) {
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

	public boolean isKYCEligible() {
		return isKYCEligible;
	}

	public void setKYCEligible(boolean isKYCEligible) {
		this.isKYCEligible = isKYCEligible;
	}

	public boolean isFraugsterEligible() {
		return isFraugsterEligible;
	}

	public void setFraugsterEligible(boolean isFraugsterEligible) {
		this.isFraugsterEligible = isFraugsterEligible;
	}

	public boolean isSanctionEligible() {
		return isSanctionEligible;
	}

	public void setSanctionEligible(boolean isSanctionEligible) {
		this.isSanctionEligible = isSanctionEligible;
	}

	public boolean isSanctionPerformed() {
		return isSanctionPerformed;
	}

	public void setSanctionPerformed(boolean isSanctionPerformed) {
		this.isSanctionPerformed = isSanctionPerformed;
	}

}
