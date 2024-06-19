package com.currenciesdirect.gtg.compliance.kyc.core;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.dbport.KYCDBServiceImpl;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;

/**
 * The Class KYCConcreteDataBuilder.
 *
 * @author manish
 */
public class KYCConcreteDataBuilder extends KYCAbstractCacheDataBuilder {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(KYCConcreteDataBuilder.class);

	/** The k YC concrete data builder. */
	private static KYCConcreteDataBuilder kYCConcreteDataBuilder = null;

	/** The ikyc dao. */
	private IKYCDBService ikycDao = KYCDBServiceImpl.getInstance();

	/**
	 * Instantiates a new KYC concrete data builder.
	 */
	private KYCConcreteDataBuilder() {
		LOG.debug("KYCConcreteDataBuilder Contructor created");
	}

	/**
	 * Gets the single instance of KYCConcreteDataBuilder.
	 *
	 * @return single instance of KYCConcreteDataBuilder
	 */
	public static KYCConcreteDataBuilder getInstance() {
		if (kYCConcreteDataBuilder == null) {
			kYCConcreteDataBuilder = new KYCConcreteDataBuilder();
		}
		return kYCConcreteDataBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.kyc.core.KYCAbstractCacheDataBuilder#
	 * loadCache()
	 */
	@Override
	public void loadCache() {
		try {
			ConcurrentMap<String, List<String>> providerCountryMap;

			providerCountryMap = ikycDao.fetchKYCInitData();
			kYCCacheDataStructure.setCountryList(providerCountryMap);

			ConcurrentMap<String, KYCProviderProperty> configProperyMap;
			configProperyMap = ikycDao.getKYCProviderinitConfigProperties();
			kYCCacheDataStructure.setPRoviderinitConfigMap(configProperyMap);
		} catch (KYCException b2bException) {
			LOG.error("Error in class KYCConcreteDataBuilder loadCache() :", b2bException);
		} catch (Exception e) {
			LOG.error("Error in class KYCConcreteDataBuilder loadCache() :", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.kyc.core.KYCAbstractCacheDataBuilder#
	 * getProviderInitConfigProperty(java.lang.String)
	 */
	@Override
	public KYCProviderProperty getProviderInitConfigProperty(String providerName) throws KYCException {
		KYCProviderProperty property = new KYCProviderProperty();
		try {
			property = kYCCacheDataStructure.getProviderinitConfigMap(providerName);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return property;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.kyc.core.KYCAbstractCacheDataBuilder#
	 * getProviderForCountry(java.lang.String)
	 */
	@Override
	public String getProviderForCountry(String country) throws KYCException {
		ConcurrentHashMap<String, List<String>> providerCountryMap;
		String providerName = null;
		try {
			providerCountryMap = kYCCacheDataStructure.getPRoviderCountryMap();

			Set<String> keys = providerCountryMap.keySet();

			providerName = findProviderName(country, providerCountryMap, keys);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.COUNTRY_IS_NOT_SUPPORTED, e);
		}
		return providerName;

	}

	/**
	 * Find provider name.
	 *
	 * @param country
	 *            the country
	 * @param providerCountryMap
	 *            the provider country map
	 * @param keys
	 *            the keys
	 * @return the string
	 */
	private String findProviderName(String country, ConcurrentHashMap<String, List<String>> providerCountryMap,
			Set<String> keys) {
		String providerName = null;
		for (String key : keys) {
			List<String> lst = providerCountryMap.get(key);
			for (String Country : lst) {
				if ((Country.toUpperCase()).equals(country)) {
					providerName = key;
					break;
				}
			}
		}
		return providerName;
	}

}
