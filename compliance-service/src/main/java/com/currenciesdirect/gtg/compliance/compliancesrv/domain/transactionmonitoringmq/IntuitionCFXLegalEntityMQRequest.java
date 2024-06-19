package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionCFXLegalEntityMQRequest.
 */
//AT-4744
@Getter 
@Setter
public class IntuitionCFXLegalEntityMQRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	
	/** The company billing address. */
	@JsonProperty(value = "company_billing_address")
	private String companyBillingAddress;
	
	/** The company phone. */
	@JsonProperty(value = "company_phone")
	private String companyPhone;
	
	/** The shipping address. */
	@JsonProperty(value = "shipping_address")
	private String shippingAddress;
	
	/** The vat no. */
	@JsonProperty(value = "vat_no")
	private String vatNo;
	
	/** The country region. */
	@JsonProperty(value = "country_region")
	private String countryRegion;
	
	/** The tc signed date. */
	@JsonProperty(value = "tc_signed_date")
	private String tcSignedDate;
	
	/** The option. */
	@JsonProperty(value = "option")
	private String option;
	
	/** The type of financial account. */
	@JsonProperty(value = "type_of_financial_account")
	private String typeOfFinancialAccount;
	
	/** The est no transactions pcm. */
	@JsonProperty(value = "est_no_transactions_pcm")
	private String estNoTransactionsPcm;
	
	/** The sic. */
	@JsonProperty(value = "sic")
	private String sic;
	
	/** The former name. */
	@JsonProperty(value = "former_name")
	private String formerName;
	
	/** The match name. */
	@JsonProperty(value = "match_name")
	private String matchName;
	
	/** The legal form. */
	@JsonProperty(value = "legal_form")
	private String legalForm;
	
	/** The foreign owned company. */
	@JsonProperty(value = "foreign_owned_company")
	private String foreignOwnedCompany;
	
	/** The credit limit. */
	@JsonProperty(value = "credit_limit")
	private String creditLimit;
	
	/** The turnover. */
	@JsonProperty(value = "turnover")
	private Integer turnover;
	
	/** The net worth. */
	@JsonProperty(value = "net_worth")
	private String netWorth;
	
	/** The fixed assets. */
	@JsonProperty(value = "fixed_assets")
	private String fixedAssets;
	
	/** The total liabilities and equities. */
	@JsonProperty(value = "total_liabilities_and_equities")
	private String totalLiabilitiesAndEquities;
	
	/** The gross income. */
	@JsonProperty(value = "gross_income")
	private String grossIncome;
	
	/** The net income. */
	@JsonProperty(value = "net_income")
	private String netIncome;
	
	/** The financial strength. */
	@JsonProperty(value = "financial_strength")
	private String financialStrength;
	
	/** The annual sales. */
	@JsonProperty(value = "annual_sales")
	private String annualSales;
	
	/** The modelled annual sales. */
	@JsonProperty(value = "modelled_annual_sales")
	private String modelledAnnualSales;
	
	/** The net worth amount. */
	@JsonProperty(value = "net_worth_amount")
	private String netWorthAmount;
	
	/** The modelled net worth. */
	@JsonProperty(value = "modelled_net_worth")
	private String modelledNetWorth;
	
	/** The total share holders. */
	@JsonProperty(value = "total_share_holders")
	private String totalShareHolders;
	
	/** The total matched shareholders. */
	@JsonProperty(value = "total_matched_shareholders")
	private String totalMatchedShareholders;
	
	/** The registration date. */
	@JsonProperty(value = "registration_date")
	private String registrationDate;
	
	/** The iso country code 2 digit. */
	@JsonProperty(value = "iso_country_code_2_digit")
	private String isoCountryCode2Digit;
	
	/** The country risk indicator. */
	@JsonProperty(value = "country_risk_indicator")
	private String countryRiskIndicator;
	
	/** The risk trend. */
	@JsonProperty(value = "risk_trend")
	private String riskTrend;
	
	/** The risk direction. */
	@JsonProperty(value = "risk_direction")
	private String riskDirection;
	
	/** The updated risk. */
	@JsonProperty(value = "updated_risk")
	private String updatedRisk;
	
	/** The duns number. */
	@JsonProperty(value = "duns_number")
	private String dunsNumber;
	
	/** The group structure number of levels. */
	@JsonProperty(value = "group_structure_number_of_levels")
	private String groupStructureNumberOfLevels;
	
	/** The headquarter details. */
	@JsonProperty(value = "headquarter_details")
	private String headquarterDetails;
	
	/** The immediate parent details. */
	@JsonProperty(value = "immediate_parent_details")
	private String immediateParentDetails;
	
	/** The domestic ultimate parent details. */
	@JsonProperty(value = "domestic_ultimate_parent_details")
	private String domesticUltimateParentDetails;
	
	/** The global ultimate parent details. */
	@JsonProperty(value = "global_ultimate_parent_details")
	private String globalUltimateParentDetails;
	
	/** The risk rating. */
	@JsonProperty(value = "risk_rating")
	private String riskRating;
	
	/** The website. */
	@JsonProperty(value = "website")
	private String website;
	
	/** The country of establishment. */
	@JsonProperty(value = "country_of_establishment")
	private String countryOfEstablishment;
	
	/** The corporate type. */
	@JsonProperty(value = "corporate_type")
	private String corporateType;
	
	/** The registration no. */
	@JsonProperty(value = "registration_no")
	private String registrationNo;
	
	/** The incorporation date. */
	@JsonProperty(value = "incorporation_date")
	private String incorporationDate;
	
	/** The industry. */
	@JsonProperty(value = "industry")
	private String industry;
	
	/** The ongoing due diligence date. */
	@JsonProperty(value = "ongoing_due_diligence_date")
	private String ongoingDueDiligenceDate; //Add for AT-5393
}
