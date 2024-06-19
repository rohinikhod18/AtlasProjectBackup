package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class CardDetails.
 */
public class CardDetails implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The card ID. */
	@JsonProperty(value="cardID")
	private String cardID;
	
	/** The card status flag. */
	@JsonProperty(value="card_status_flag")
	private String cardStatusFlag;

	/** The date card dispatched. */
	@JsonProperty(value="date_card_dispatched")
	private String dateCardDispatched;

	/** The date card activated. */
	@JsonProperty(value="date_card_activated")
	private String dateCardActivated;

	/** The date card frozen. */
	@JsonProperty(value="date_card_frozen")
	private String dateCardFrozen;

	/** The freeze reason. */
	@JsonProperty(value="freeze_reason")
	private String freezeReason;

	/** The date card unfrozen. */
	@JsonProperty(value="date_card_unfrozen")
	private String dateCardUnfrozen;

	/** The date card blocked. */
	@JsonProperty(value="date_card_blocked")
	private String dateCardBlocked;

	/** The reason for block. */
	@JsonProperty(value="reason_for_block")
	private String reasonForBlock;

	/** The date card unblocked. */
	@JsonProperty(value="date_card_unblocked")
	private String dateCardUnblocked;

	/** The date last card pin view. */
	@JsonProperty(value="date_last_card_pin_view")
	private String dateLastCardPinView;

	/** The date last card pan view. */
	@JsonProperty(value="date_last_card_pan_view")
	private String dateLastCardPanView;

	/** The contact id. */
	@JsonProperty(value="contactId")
	private Integer contactId;
	
	/**
	 * Gets the card ID.
	 *
	 * @return the card ID
	 */
	public String getCardID() {
		return cardID;
	}

	/**
	 * Sets the card ID.
	 *
	 * @param cardID the new card ID
	 */
	public void setCardID(String cardID) {
		this.cardID = cardID;
	}

	/**
	 * Gets the card status flag.
	 *
	 * @return the card status flag
	 */
	public String getCardStatusFlag() {
		return cardStatusFlag;
	}

	/**
	 * Sets the card status flag.
	 *
	 * @param cardStatusFlag the new card status flag
	 */
	public void setCardStatusFlag(String cardStatusFlag) {
		this.cardStatusFlag = cardStatusFlag;
	}

	/**
	 * Gets the date card dispatched.
	 *
	 * @return the date card dispatched
	 */
	public String getDateCardDispatched() {
		return dateCardDispatched;
	}

	/**
	 * Sets the date card dispatched.
	 *
	 * @param dateCardDispatched the new date card dispatched
	 */
	public void setDateCardDispatched(String dateCardDispatched) {
		this.dateCardDispatched = dateCardDispatched;
	}

	/**
	 * Gets the date card activated.
	 *
	 * @return the date card activated
	 */
	public String getDateCardActivated() {
		return dateCardActivated;
	}

	/**
	 * Sets the date card activated.
	 *
	 * @param dateCardActivated the new date card activated
	 */
	public void setDateCardActivated(String dateCardActivated) {
		this.dateCardActivated = dateCardActivated;
	}

	/**
	 * Gets the date card frozen.
	 *
	 * @return the date card frozen
	 */
	public String getDateCardFrozen() {
		return dateCardFrozen;
	}

	/**
	 * Sets the date card frozen.
	 *
	 * @param dateCardFrozen the new date card frozen
	 */
	public void setDateCardFrozen(String dateCardFrozen) {
		this.dateCardFrozen = dateCardFrozen;
	}

	/**
	 * Gets the freeze reason.
	 *
	 * @return the freeze reason
	 */
	public String getFreezeReason() {
		return freezeReason;
	}

	/**
	 * Sets the freeze reason.
	 *
	 * @param freezeReason the new freeze reason
	 */
	public void setFreezeReason(String freezeReason) {
		this.freezeReason = freezeReason;
	}

	/**
	 * Gets the date card unfrozen.
	 *
	 * @return the date card unfrozen
	 */
	public String getDateCardUnfrozen() {
		return dateCardUnfrozen;
	}

	/**
	 * Sets the date card unfrozen.
	 *
	 * @param dateCardUnfrozen the new date card unfrozen
	 */
	public void setDateCardUnfrozen(String dateCardUnfrozen) {
		this.dateCardUnfrozen = dateCardUnfrozen;
	}

	/**
	 * Gets the date card blocked.
	 *
	 * @return the date card blocked
	 */
	public String getDateCardBlocked() {
		return dateCardBlocked;
	}

	/**
	 * Sets the date card blocked.
	 *
	 * @param dateCardBlocked the new date card blocked
	 */
	public void setDateCardBlocked(String dateCardBlocked) {
		this.dateCardBlocked = dateCardBlocked;
	}

	/**
	 * Gets the reason for block.
	 *
	 * @return the reason for block
	 */
	public String getReasonForBlock() {
		return reasonForBlock;
	}

	/**
	 * Sets the reason for block.
	 *
	 * @param reasonForBlock the new reason for block
	 */
	public void setReasonForBlock(String reasonForBlock) {
		this.reasonForBlock = reasonForBlock;
	}

	/**
	 * Gets the date card unblocked.
	 *
	 * @return the date card unblocked
	 */
	public String getDateCardUnblocked() {
		return dateCardUnblocked;
	}

	/**
	 * Sets the date card unblocked.
	 *
	 * @param dateCardUnblocked the new date card unblocked
	 */
	public void setDateCardUnblocked(String dateCardUnblocked) {
		this.dateCardUnblocked = dateCardUnblocked;
	}

	/**
	 * Gets the date last card pin view.
	 *
	 * @return the date last card pin view
	 */
	public String getDateLastCardPinView() {
		return dateLastCardPinView;
	}

	/**
	 * Sets the date last card pin view.
	 *
	 * @param dateLastCardPinView the new date last card pin view
	 */
	public void setDateLastCardPinView(String dateLastCardPinView) {
		this.dateLastCardPinView = dateLastCardPinView;
	}

	/**
	 * Gets the date last card pan view.
	 *
	 * @return the date last card pan view
	 */
	public String getDateLastCardPanView() {
		return dateLastCardPanView;
	}

	/**
	 * Sets the date last card pan view.
	 *
	 * @param dateLastCardPanView the new date last card pan view
	 */
	public void setDateLastCardPanView(String dateLastCardPanView) {
		this.dateLastCardPanView = dateLastCardPanView;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contact id
	 */
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the new contact id
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
