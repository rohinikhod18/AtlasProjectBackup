
package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CardPilotRequest.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPilotRequest {

	/** The source application. */
	@JsonProperty(value = "source_application")
	private String sourceApplication;

	/** The org code. */
	@JsonProperty(value = "org_code")
	private String orgCode;

	/** The account number. */
	@JsonProperty(value = "account_number")
	private String accountNumber;

	/** The card pilot search criteria. */
	@JsonProperty(value = "search_criteria")
	private CardPilotSearchCriteria cardPilotSearchCriteria;

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
	 * Gets the account number.
	 *
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * Sets the account number.
	 *
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * Gets the card pilot search criteria.
	 *
	 * @return the cardPilotSearchCriteria
	 */
	public CardPilotSearchCriteria getCardPilotSearchCriteria() {
		return cardPilotSearchCriteria;
	}

	/**
	 * Sets the card pilot search criteria.
	 *
	 * @param cardPilotSearchCriteria the cardPilotSearchCriteria to set
	 */
	public void setCardPilotSearchCriteria(CardPilotSearchCriteria cardPilotSearchCriteria) {
		this.cardPilotSearchCriteria = cardPilotSearchCriteria;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardPilotRequest [sourceApplication=");
		builder.append(sourceApplication);
		builder.append(", orgCode=");
		builder.append(orgCode);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", cardPilotSearchCriteria=");
		builder.append(cardPilotSearchCriteria);
		builder.append("]");
		return builder.toString();
	}

}
