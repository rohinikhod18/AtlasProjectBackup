package com.currenciesdirect.gtg.compliance.customchecks.core.cache;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderConfigCache;
import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.customchecks.dbport.ConfigDBService;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class CustomChecksCacheLoader.
 */
public class CustomChecksCacheLoader {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CustomChecksCacheLoader.class);

	/** The provider config. */
	private ProviderConfigCache providerConfig = new ProviderConfigCache();

	/** The instance. */
	private static CustomChecksCacheLoader instance;

	/**
	 * Instantiates a new custom checks cache loader.
	 */
	private CustomChecksCacheLoader() {
	}

	/**
	 * Gets the single instance of CustomChecksCacheLoader.
	 *
	 * @return single instance of CustomChecksCacheLoader
	 */
	public static CustomChecksCacheLoader getInstance() {
		if (instance == null) {
			instance = new CustomChecksCacheLoader();
			loadCache(instance);
		}
		return instance;
	}

	/**
	 * Load cache.
	 *
	 * @param ccCacheLoader the provide cache loader
	 */
	private static void loadCache(CustomChecksCacheLoader ccCacheLoader) {
		ConfigDBService configDBService = new ConfigDBService();
		try {
			ConcurrentMap<String, ProviderProperty> ccCacheMap;
			ccCacheMap = configDBService.getProviderinitConfigProperties();
			ccCacheLoader.getProviderConfig().storeAll(ccCacheMap);
			LOG.debug("=========  loded provider cache");
		} catch (CustomChecksException ex) {
			LOG.error("Error in class CustomChecksCacheLoader loadCache() :", ex);
		} catch (Exception e) {
			LOG.error("Error in class CustomChecksCacheLoader loadCache() :", e);
		}

	}

	/**
	 * Gets the provider property.
	 *
	 * @param providerName the provider name
	 * @return the provider property
	 * @throws CustomChecksException the custom checks exception
	 */
	public ProviderProperty getProviderProperty(String providerName) throws CustomChecksException {
		try {
			return (ProviderProperty) instance.getProviderConfig().retrieve(providerName);
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_CONNECTION, e);
		}
	}

	/**
	 * Gets the end point url.
	 *
	 * @param providerName the provider name
	 * @return the end point url
	 * @throws CustomChecksException the custom checks exception
	 */
	public String getEndPointUrl(String providerName) throws CustomChecksException {
		try {
			ProviderProperty property = (ProviderProperty) instance.getProviderConfig().retrieve(providerName);
			return property.getEndPointUrl();
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_DATABASE_CONNECTION, e);
		}
	}

	/**
	 * Gets the provider config.
	 *
	 * @return the provider config
	 */
	public ProviderConfigCache getProviderConfig() {
		return providerConfig;
	}

}
