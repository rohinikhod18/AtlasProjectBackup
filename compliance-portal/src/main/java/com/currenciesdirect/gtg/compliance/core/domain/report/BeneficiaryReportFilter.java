/*
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.report;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class BeneficiaryReportFilter.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BeneficiaryReportFilter extends BaseFilter implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The country. */
	private String[] country;

	/** The currency. */
	@JsonProperty("currency_code_list")
	private String[] currency;

	/** The third party. */
	@JsonProperty("is_third_party")
	private String thirdParty;

	/** The date from for payee. */
	@JsonProperty("date_from")
	private String dateFromForPayee;

	/** The date to for payee. */
	@JsonProperty("date_to")
	private String dateToForPayee;

	/** The client type. */
	@JsonProperty("client_type")
	private String clientType;

	/**
	 * Gets the client type.
	 *
	 * @return the client type
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * Sets the client type.
	 *
	 * @param clientType
	 *            the new client type
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String[] getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String[] country) {
		this.country = country;
	}

	/**
	 * Gets the currency.
	 *
	 * @return the currency
	 */
	public String[] getCurrency() {
		return currency;
	}

	/**
	 * Sets the currency.
	 *
	 * @param currency
	 *            the currency to set
	 */
	public void setCurrency(String[] currency) {
		this.currency = currency;
	}

	/**
	 * Gets the third party.
	 *
	 * @return the thirdParty
	 */
	public String getThirdParty() {
		return thirdParty;
	}

	/**
	 * Sets the third party.
	 *
	 * @param thirdParty
	 *            the thirdParty to set
	 */
	public void setThirdParty(String thirdParty) {
		this.thirdParty = thirdParty;
	}

	/**
	 * Gets the date from for payee.
	 *
	 * @return the date from for payee
	 */
	public String getDateFromForPayee() {
		return dateFromForPayee;
	}

	/**
	 * Sets the date from for payee.
	 *
	 * @param dateFromForPayee
	 *            the new date from for payee
	 */
	public void setDateFromForPayee(String dateFromForPayee) {
		this.dateFromForPayee = dateFromForPayee;
	}

	/**
	 * Gets the date to for payee.
	 *
	 * @return the date to for payee
	 */
	public String getDateToForPayee() {
		return dateToForPayee;
	}

	/**
	 * Sets the date to for payee.
	 *
	 * @param dateToForPayee
	 *            the new date to for payee
	 */
	public void setDateToForPayee(String dateToForPayee) {
		this.dateToForPayee = dateToForPayee;
	}
}
