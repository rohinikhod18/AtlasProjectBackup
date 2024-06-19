package com.currenciesdirect.gtg.compliance.customchecks.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;

/**
 * The Class CustomCheckFraudPredictRules.
 */
public class CustomCheckFraudPredictRules extends ProviderProperty{
	
	/** The registration threeshold score. */
	private Double registrationThreesholdScore;
	
	/** The fraud predict custom check enabled. */
	private Boolean fraudPredictCustomCheckEnabled;

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
	 * @param registrationThreesholdScore the new registration threeshold score
	 */
	public void setRegistrationThreesholdScore(Double registrationThreesholdScore) {
		this.registrationThreesholdScore = registrationThreesholdScore;
	}

	/**
	 * Gets the fraud predict custom check enabled.
	 *
	 * @return the fraud predict custom check enabled
	 */
	public Boolean getFraudPredictCustomCheckEnabled() {
		return fraudPredictCustomCheckEnabled;
	}

	/**
	 * Sets the fraud predict custom check enabled.
	 *
	 * @param fraudPredictCustomCheckEnabled the new fraud predict custom check enabled
	 */
	public void setFraudPredictCustomCheckEnabled(Boolean fraudPredictCustomCheckEnabled) {
		this.fraudPredictCustomCheckEnabled = fraudPredictCustomCheckEnabled;
	}

}
