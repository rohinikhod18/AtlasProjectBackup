package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CardPilotResponse.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPilotResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The response code. */
	@JsonProperty("response_code")
	private String responseCode;
	
	/** The response description. */
	@JsonProperty("response_description")
	private String responseDescription;
	
	/** The account number. */
	@JsonProperty("account_number")
	private String accountNumber;
	
	/** The org code. */
	@JsonProperty("org_code")
	private String orgCode;
	
	/** The card activity history details. */
	@JsonProperty("card_activity_history_details")
	private List<CardActivityHistoryDetails> cardActivityHistoryDetails = new ArrayList<>();
	
	/** The card pilot search criteria. */
	@JsonProperty("contract_note_search_criteria")
	private CardPilotSearchCriteria cardPilotSearchCriteria;
	
	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * @param responseDescription the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the cardActivityHistoryDetails
	 */
	public List<CardActivityHistoryDetails> getCardActivityHistoryDetails() {
		return cardActivityHistoryDetails;
	}

	/**
	 * @param cardActivityHistoryDetails the cardActivityHistoryDetails to set
	 */
	public void setCardActivityHistoryDetails(List<CardActivityHistoryDetails> cardActivityHistoryDetails) {
		this.cardActivityHistoryDetails = cardActivityHistoryDetails;
	}

	/**
	 * @return the cardPilotSearchCriteria
	 */
	public CardPilotSearchCriteria getCardPilotSearchCriteria() {
		return cardPilotSearchCriteria;
	}

	/**
	 * @param cardPilotSearchCriteria the cardPilotSearchCriteria to set
	 */
	public void setCardPilotSearchCriteria(CardPilotSearchCriteria cardPilotSearchCriteria) {
		this.cardPilotSearchCriteria = cardPilotSearchCriteria;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardPilotResponse [responseCode=");
		builder.append(responseCode);
		builder.append(", responseDescription=");
		builder.append(responseDescription);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", orgCode=");
		builder.append(orgCode);
		builder.append(", cardActivityHistoryDetails=");
		builder.append(cardActivityHistoryDetails);
		builder.append(", cardPilotSearchCriteria=");
		builder.append(cardPilotSearchCriteria);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", errorDescription=");
		builder.append(errorDescription);
		builder.append("]");
		return builder.toString();
	}
}
