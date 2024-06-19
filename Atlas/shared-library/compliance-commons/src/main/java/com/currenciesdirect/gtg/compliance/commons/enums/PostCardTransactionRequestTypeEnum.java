package com.currenciesdirect.gtg.compliance.commons.enums;

public enum PostCardTransactionRequestTypeEnum {

	/** The cx auth. */
	CX_AUTH("CX-Auth"),

	/** The cx reversal. */
	CX_REVERSAL("CX-Reversal"),

	/** The cx authpresentment. */
	CX_AUTHPRESENTMENT("CX-AuthPresentment"),

	/** The cx refund. */
	CX_REFUND("CX-Refund");
	
	/** The card request type as string. */
	private String cardRequestTypeAsString;

	/**
	 * Instantiates a new post card transaction request type enum.
	 *
	 * @param cardRequestTypeAsString the card request type as string
	 */
	PostCardTransactionRequestTypeEnum(String cardRequestTypeAsString) {
		this.cardRequestTypeAsString = cardRequestTypeAsString;
	}
	
	/**
	 * @return the cardRequestTypeAsString
	 */
	public String getCardRequestTypeAsString() {
		return cardRequestTypeAsString;
	}
}
