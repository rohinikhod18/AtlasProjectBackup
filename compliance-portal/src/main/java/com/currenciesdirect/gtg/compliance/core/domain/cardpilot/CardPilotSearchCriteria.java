package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.BaseSearchCriteria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CardPilotSearchCriteria.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardPilotSearchCriteria extends BaseSearchCriteria {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The card pilot filter. */
	@JsonProperty(value = "filter")
	private CardPilotFilter cardPilotFilter;

	/**
	 * Gets the card pilot filter.
	 *
	 * @return the cardPilotFilter
	 */
	public CardPilotFilter getCardPilotFilter() {
		return cardPilotFilter;
	}

	/**
	 * Sets the card pilot filter.
	 *
	 * @param cardPilotFilter the cardPilotFilter to set
	 */
	public void setCardPilotFilter(CardPilotFilter cardPilotFilter) {
		this.cardPilotFilter = cardPilotFilter;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CardPilotSearchCriteria [cardPilotFilter=");
		builder.append(cardPilotFilter);
		builder.append("]");
		return builder.toString();
	}

	
}
