package com.currenciesdirect.gtg.compliance.commons.domain.whitelist;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class AccountWhiteList.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "orgCode", "accountId", "createdOn", "updatedOn", "approvedReasonOfTransList",
		"approvedBuyCurrencyAmountRangeList", "approvedSellCurrencyAmountRangeList", "approvedThirdpartyAccountList",
		"approvedHighRiskCountryList", "documentationRequiredWatchlistSellCurrency", "usClientListBBeneAccNumber" })
public class AccountWhiteList extends ServiceMessage {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The organisation code", required = true)
	@JsonProperty("orgCode")
	private String orgCode;

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	@JsonProperty("accountId")
	private Integer accountId;

	/** The created on. */
	@ApiModelProperty(value = "The created on date", required = true)
	@JsonProperty("createdOn")
	private String createdOn;

	/** The updated on. */
	@ApiModelProperty(value = "The updated on date", required = true)
	@JsonProperty("updatedOn")
	private String updatedOn;

	/** The approved reason of trans list. */
	@ApiModelProperty(value = "The approved reason of transactions list", required = true)
	@JsonProperty("approvedReasonOfTransList")
	private List<String> approvedReasonOfTransList = new ArrayList<>();

	/** The approved buy currency amount range list. */
	@ApiModelProperty(value = "The approved buy currency amount range list", required = true)
	@JsonProperty("approvedBuyCurrencyAmountRangeList")
	private List<ApprovedCurrencyAmountRangePair> approvedBuyCurrencyAmountRangeList = new ArrayList<>();

	/** The approved sell currency amount range list. */
	@ApiModelProperty(value = "The approved sell currency amount range list", required = true)
	@JsonProperty("approvedSellCurrencyAmountRangeList")
	private List<ApprovedCurrencyAmountRangePair> approvedSellCurrencyAmountRangeList = new ArrayList<>();

	/** The approved thirdparty account list. */
	@ApiModelProperty(value = "The approved thirdparty account list", required = true)
	@JsonProperty("approvedThirdpartyAccountList")
	private List<String> approvedThirdpartyAccountList = new ArrayList<>();

	/** The approved high risk country list. */
	@ApiModelProperty(value = "The approved high risk country list", required = true)
	@JsonProperty("approvedHighRiskCountryList")
	private List<HighRiskCountry> approvedHighRiskCountryList = new ArrayList<>();

	/** The approved OPI account number. */
	@ApiModelProperty(value = "The approved OPI account number", required = true)
	@JsonProperty("approvedOPIAccountNumber")
	private List<String> approvedOPIAccountNumber = new ArrayList<>();

	/** The documentation required watchlist sell currency. */
	@ApiModelProperty(value = "The documentation required watchlist sell currency", required = true)
	@JsonProperty("documentationRequiredWatchlistSellCurrency")
	private List<String> documentationRequiredWatchlistSellCurrency = new ArrayList<>();

	/** The us client list B bene acc number. */
	@ApiModelProperty(value = "The US Client List B Beneficiary Account Number", required = true)
	@JsonProperty("usClientListBBeneAccNumber")
	private List<String> usClientListBBeneAccNumber = new ArrayList<>();


	/** The approved high risk country list for funds in. */
	@JsonProperty("approvedHighRiskCountryListForFundsIn")
	private List<HighRiskCountry> approvedHighRiskCountryListForFundsIn = new ArrayList<>();

	/**
	 * Gets the approved high risk country list for funds in.
	 *
	 * @return the approved high risk country list for funds in
	 */
	public List<HighRiskCountry> getApprovedHighRiskCountryListForFundsIn() {
		return approvedHighRiskCountryListForFundsIn;
	}

	/**
	 * Sets the approved high risk country list for funds in.
	 *
	 * @param approvedHighRiskCountryListForFundsIn
	 *            the new approved high risk country list for funds in
	 */
	public void setApprovedHighRiskCountryListForFundsIn(List<HighRiskCountry> approvedHighRiskCountryListForFundsIn) {
		this.approvedHighRiskCountryListForFundsIn = approvedHighRiskCountryListForFundsIn;
	}

	/**
	 * Gets the us client list B bene acc number.
	 *
	 * @return the us client list B bene acc number
	 */
	public List<String> getUsClientListBBeneAccNumber() {
		return usClientListBBeneAccNumber;
	}

	/**
	 * Sets the us client list B bene acc number.
	 *
	 * @param usClientListBClientAccountNumber
	 *            the new us client list B bene acc number
	 */
	public void setUsClientListBBeneAccNumber(List<String> usClientListBClientAccountNumber) {
		this.usClientListBBeneAccNumber = usClientListBClientAccountNumber;
	}

	/**
	 * Gets the documentation required watchlist sell currency.
	 *
	 * @return the documentation required watchlist sell currency
	 */
	public List<String> getDocumentationRequiredWatchlistSellCurrency() {
		return documentationRequiredWatchlistSellCurrency;
	}

	/**
	 * Sets the documentation required watchlist sell currency.
	 *
	 * @param documentationRequiredWatchlistSellCurrency
	 *            the new documentation required watchlist sell currency
	 */
	public void setDocumentationRequiredWatchlistSellCurrency(List<String> documentationRequiredWatchlistSellCurrency) {
		this.documentationRequiredWatchlistSellCurrency = documentationRequiredWatchlistSellCurrency;
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
	 * Gets the account id.
	 *
	 * @return The accountId
	 */
	@JsonProperty("accountId")
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            The accountId
	 */
	@JsonProperty("accountId")
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the created on.
	 *
	 * @return The createdOn
	 */
	@JsonProperty("createdOn")
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn
	 *            The createdOn
	 */
	@JsonProperty("createdOn")
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return The updatedOn
	 */
	@JsonProperty("updatedOn")
	public String getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn
	 *            The updatedOn
	 */
	@JsonProperty("updatedOn")
	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the approved reason of trans list.
	 *
	 * @return The approvedReasonOfTransList
	 */
	@JsonProperty("approvedReasonOfTransList")
	public List<String> getApprovedReasonOfTransList() {
		return approvedReasonOfTransList;
	}

	/**
	 * Sets the approved reason of trans list.
	 *
	 * @param approvedReasonOfTransList
	 *            The approvedReasonOfTransList
	 */
	@JsonProperty("approvedReasonOfTransList")
	public void setApprovedReasonOfTransList(List<String> approvedReasonOfTransList) {
		this.approvedReasonOfTransList = approvedReasonOfTransList;
	}

	/**
	 * Gets the approved buy currency amount range list.
	 *
	 * @return The approvedBuyCurrencyAmountRangeList
	 */
	@JsonProperty("approvedBuyCurrencyAmountRangeList")
	public List<ApprovedCurrencyAmountRangePair> getApprovedBuyCurrencyAmountRangeList() {
		return approvedBuyCurrencyAmountRangeList;
	}

	/**
	 * Sets the approved buy currency amount range list.
	 *
	 * @param approvedBuyCurrencyAmountRangeList
	 *            The approvedBuyCurrencyAmountRangeList
	 */
	@JsonProperty("approvedBuyCurrencyAmountRangeList")
	public void setApprovedBuyCurrencyAmountRangeList(
			List<ApprovedCurrencyAmountRangePair> approvedBuyCurrencyAmountRangeList) {
		this.approvedBuyCurrencyAmountRangeList = approvedBuyCurrencyAmountRangeList;
	}

	/**
	 * Gets the approved sell currency amount range list.
	 *
	 * @return The approvedSellCurrencyAmountRangeList
	 */
	@JsonProperty("approvedSellCurrencyAmountRangeList")
	public List<ApprovedCurrencyAmountRangePair> getApprovedSellCurrencyAmountRangeList() {
		return approvedSellCurrencyAmountRangeList;
	}

	/**
	 * Sets the approved sell currency amount range list.
	 *
	 * @param approvedSellCurrencyAmountRangeList
	 *            The approvedSellCurrencyAmountRangeList
	 */
	@JsonProperty("approvedSellCurrencyAmountRangeList")
	public void setApprovedSellCurrencyAmountRangeList(
			List<ApprovedCurrencyAmountRangePair> approvedSellCurrencyAmountRangeList) {
		this.approvedSellCurrencyAmountRangeList = approvedSellCurrencyAmountRangeList;
	}

	/**
	 * Gets the approved thirdparty account list.
	 *
	 * @return The approvedThirdpartyAccountList
	 */
	@JsonProperty("approvedThirdpartyAccountList")
	public List<String> getApprovedThirdpartyAccountList() {
		return approvedThirdpartyAccountList;
	}

	/**
	 * Sets the approved thirdparty account list.
	 *
	 * @param approvedThirdpartyAccountList
	 *            The approvedThirdpartyAccountList
	 */
	@JsonProperty("approvedThirdpartyAccountList")
	public void setApprovedThirdpartyAccountList(List<String> approvedThirdpartyAccountList) {
		this.approvedThirdpartyAccountList = approvedThirdpartyAccountList;
	}

	/**
	 * Gets the approved high risk country list.
	 *
	 * @return the approvedHighRiskCountryList
	 */
	public List<HighRiskCountry> getApprovedHighRiskCountryList() {
		return approvedHighRiskCountryList;
	}

	/**
	 * Sets the approved high risk country list.
	 *
	 * @param approvedHighRiskCountryList
	 *            the approvedHighRiskCountryList to set
	 */
	public void setApprovedHighRiskCountryList(List<HighRiskCountry> approvedHighRiskCountryList) {
		this.approvedHighRiskCountryList = approvedHighRiskCountryList;
	}

	/**
	 * Gets the approved OPI account number.
	 *
	 * @return the approvedOPIAccountNumber
	 */
	public List<String> getApprovedOPIAccountNumber() {
		return approvedOPIAccountNumber;
	}

	/**
	 * Sets the approved OPI account number.
	 *
	 * @param approvedOPIAccountNumber
	 *            the approvedOPIAccountNumber to set
	 */
	public void setApprovedOPIAccountNumber(List<String> approvedOPIAccountNumber) {
		this.approvedOPIAccountNumber = approvedOPIAccountNumber;
	}

}
