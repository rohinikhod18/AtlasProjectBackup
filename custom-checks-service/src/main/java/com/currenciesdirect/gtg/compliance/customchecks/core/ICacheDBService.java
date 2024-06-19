package com.currenciesdirect.gtg.compliance.customchecks.core;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckFraudPredictRules;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckVelocityRules;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Interface ICacheDBService.
 */
public interface ICacheDBService {

	/**
	 * Gets the velocity rules max frequency.
	 *
	 * @return the velocity rules max frequency
	 * @throws CustomChecksException
	 *             the custom checks exception
	 */
	public Map<String, CustomCheckVelocityRules> getVelocityRulesCacheData() throws CustomChecksException;

	/**
	 * Gets the account velocity rules.
	 *
	 * @return the account velocity rules
	 * @throws CustomChecksException
	 *             the custom checks exception
	 */
	public CustomCheckVelocityRules getAccountVelocityRules(Integer accId) throws CustomChecksException;

	/**
	 * Checks if is beneficiary account whitelisted.
	 *
	 * @param beneAccountToCheck
	 *            the bene account to check
	 * @return true, if is beneficiary account whitelisted
	 * @throws CustomChecksException
	 *             the custom checks exception
	 */
	public boolean isBeneficiaryAccountWhitelisted(String beneAccountToCheck) throws CustomChecksException;
	
	/**
	 * Gets the fraud predict provider init config property.
	 *
	 * @return the fraud predict provider init config property
	 * @throws CustomChecksException the custom checks exception
	 */
	public Map<String, CustomCheckFraudPredictRules> getFraudPredictProviderInitConfigProperty() throws CustomChecksException;
}
