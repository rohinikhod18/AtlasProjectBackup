package com.currenciesdirect.gtg.compliance.kyc.core;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;

/**
 * The Class KYCCacheDataStructure.
 *
 * @author manish
 */
public class KYCCacheDataStructure {

	/** The k YC cache data structure. */
	private static KYCCacheDataStructure kYCCacheDataStructure = new KYCCacheDataStructure();
	/** The provider init config map. */
	private KYCCache providerInitConfigMap = new KYCCache();

	/** The Provider country map. */
	private KYCCache providerCountryMap = new KYCCache();

	/**
	 * Instantiates a new KYC cache data structure.
	 */
	private KYCCacheDataStructure() {

	}

	/**
	 * Gets the single instance of KYCCacheDataStructure.
	 *
	 * @return single instance of KYCCacheDataStructure
	 */
	public static KYCCacheDataStructure getInstance() {
		return kYCCacheDataStructure;
	}

	/**
	 * Sets the P roviderinit config map.
	 *
	 * @param properties
	 *            the properties
	 */
	public void setPRoviderinitConfigMap(ConcurrentMap<String, KYCProviderProperty> properties) {
		this.providerInitConfigMap.kycStoreAll(properties);
	}

	/**
	 * Gets the P roviderinit config map.
	 *
	 * @param <T>
	 *            the generic type
	 * @param providerName
	 *            the provider name
	 * @return the t
	 */
	@SuppressWarnings("unchecked")
	public <T> T getProviderinitConfigMap(String providerName) {
		return (T) this.providerInitConfigMap.kycRetrieve(providerName);
	}

	/**
	 * Gets the p rovider country map.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the p rovider country map
	 */
	@SuppressWarnings("unchecked")
	public <T> T getPRoviderCountryMap() {
		return (T) this.providerCountryMap.kycGetAll();
	}

	/**
	 * Gets the listof country for provider.
	 *
	 * @param <T>
	 *            the generic type
	 * @param providerName
	 *            the provider name
	 * @return the listof country for provider
	 */
	@SuppressWarnings("unchecked")
	public <T> T getListofCountryForProvider(String providerName) {
		return (T) this.providerCountryMap.kycRetrieve(providerName);
	}

	/**
	 * Contains provider inp rovider country map.
	 *
	 * @param providerName
	 *            the provider name
	 * @return true, if successful
	 */
	public boolean containsProviderInpRoviderCountryMap(String providerName) {
		return this.providerCountryMap.kycContains(providerName);

	}

	/**
	 * Sets the country list.
	 *
	 * @param countryList
	 *            the country list
	 */
	public void setCountryList(ConcurrentMap<String, List<String>> countryList) {
		this.providerCountryMap.kycStoreAll(countryList);
	}

}
