/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;

/**
 * The Class FraugsterProviderProperty.
 *
 * @author manish
 */
public class FraugsterProviderProperty extends ProviderProperty {

	/** The fraugster inactivity refresh time. */
	private Integer fraugsterInactivityRefreshTime;

	/** Added new threesholdScore -Saylee. */
	private Double registrationThreesholdScore;

	/** The payment in threeshold score. */
	private Double paymentInThreesholdScore;

	/** The payment out threeshold score. */
	private Double paymentOutThreesholdScore;

	/**
	 * Gets the fraugster inactivity refresh time.
	 *
	 * @return the fraugster inactivity refresh time
	 */
	public Integer getFraugsterInactivityRefreshTime() {
		return fraugsterInactivityRefreshTime;
	}

	/**
	 * Sets the fraugster inactivity refresh time.
	 *
	 * @param fraugsterInactivityRefreshTime
	 *            the new fraugster inactivity refresh time
	 */
	public void setFraugsterInactivityRefreshTime(Integer fraugsterInactivityRefreshTime) {
		this.fraugsterInactivityRefreshTime = fraugsterInactivityRefreshTime;
	}

	/**
	 * Gets the registration threeshold score.
	 *
	 * @return the registration threeshold score
	 */
	public Double getRegistrationThreesholdScore() {
		return registrationThreesholdScore;
	}

	/**
	 * Sets the registration threeshold score.
	 *
	 * @param registrationThreesholdScore
	 *            the new registration threeshold score
	 */
	public void setRegistrationThreesholdScore(Double registrationThreesholdScore) {
		this.registrationThreesholdScore = registrationThreesholdScore;
	}

	/**
	 * Gets the payment in threeshold score.
	 *
	 * @return the payment in threeshold score
	 */
	public Double getPaymentInThreesholdScore() {
		return paymentInThreesholdScore;
	}

	/**
	 * Sets the payment in threeshold score.
	 *
	 * @param paymentInThreesholdScore
	 *            the new payment in threeshold score
	 */
	public void setPaymentInThreesholdScore(Double paymentInThreesholdScore) {
		this.paymentInThreesholdScore = paymentInThreesholdScore;
	}

	/**
	 * Gets the payment out threeshold score.
	 *
	 * @return the payment out threeshold score
	 */
	public Double getPaymentOutThreesholdScore() {
		return paymentOutThreesholdScore;
	}

	/**
	 * Sets the payment out threeshold score.
	 *
	 * @param paymentOutThreesholdScore
	 *            the new payment out threeshold score
	 */
	public void setPaymentOutThreesholdScore(Double paymentOutThreesholdScore) {
		this.paymentOutThreesholdScore = paymentOutThreesholdScore;
	}

}
