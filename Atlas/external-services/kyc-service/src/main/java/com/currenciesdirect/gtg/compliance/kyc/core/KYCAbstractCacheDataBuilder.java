/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;

/**
 * The Class KYCAbstractCacheDataBuilder.
 *
 * @author manish
 */
public abstract class KYCAbstractCacheDataBuilder {

	/** The k YC cache data structure. */
	protected KYCCacheDataStructure kYCCacheDataStructure = KYCCacheDataStructure.getInstance();

	/**
	 * Load cache.
	 *
	 * @throws KYCException
	 *             the KYC exception
	 */
	public abstract void loadCache() throws KYCException;

	/**
	 * Gets the provider init config property.
	 *
	 * @param providerName
	 *            the provider name
	 * @return the provider init config property
	 * @throws KYCException
	 *             the KYC exception
	 */
	public abstract KYCProviderProperty getProviderInitConfigProperty(String providerName) throws KYCException;

	/**
	 * Gets the provider for country.
	 *
	 * @param country
	 *            the country
	 * @return the provider for country
	 * @throws KYCException
	 *             the KYC exception
	 */
	public abstract String getProviderForCountry(String country) throws KYCException;

}
