package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderConfigCache;
import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.ConfigDBService;

/**
 * The Class ProvideCacheLoader.
 */
public class ProvideCacheLoader {

	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(ProvideCacheLoader.class);
	
	/** The instance. */
	private static ProvideCacheLoader instance;

	/** The provider config. */
	private ProviderConfigCache providerConfig = new ProviderConfigCache();
	
	/** The country code config cache. */
	private ProviderConfigCache countryCodeConfigCache = new ProviderConfigCache();
	
	/** The user id config cache. */
	private ProviderConfigCache userIdConfigCache = new ProviderConfigCache();
	
	/** The country edd number. */
	private ProviderConfigCache countryEddNumber = new ProviderConfigCache();

	/** The country state cache. */
	private ProviderConfigCache countryStateCache = new ProviderConfigCache();//AT-3719
	
	/** The country short code cache. */
	private ProviderConfigCache countryShortCodeCache = new ProviderConfigCache();//AT-4666
	
	/**
	 * Instantiates a new provide cache loader.
	 */
	private ProvideCacheLoader() {
	}

	/**
	 * Gets the single instance of ProvideCacheLoader.
	 *
	 * @return single instance of ProvideCacheLoader
	 */
	public static ProvideCacheLoader getInstance() {
		if (instance == null) {
			instance = new ProvideCacheLoader();
			loadCache(instance);
		}
		return instance;
	}
	

	/**
	 * Gets the provider config.
	 *
	 * @return the provider config
	 */
	public ProviderConfigCache getProviderConfig() {
		return providerConfig;
	}
	
	/**
	 * Gets the country config.
	 *
	 * @return the country config
	 */
	public ProviderConfigCache getCountryConfig() {
		return countryCodeConfigCache;
	}
	
	/**
	 * added under AT-2248-unrelated to any jira
	 * Gets the user id config.
	 *
	 * @return the user id config
	 */
	public ProviderConfigCache getUserIdConfig() {
		return userIdConfigCache;
	}
	
	/**
	 * Gets the country edd number.
	 *
	 * @return the country edd number
	 */
	public ProviderConfigCache getCountryEddNumber() {
		return countryEddNumber;
	}

	/**
	 * Gets the country state cache.
	 *
	 * @return the country state cache
	 */
	public ProviderConfigCache getCountryStateCache() {
		return countryStateCache;
	}
	
	/**
	 * Gets the country short code cache.
	 *
	 * @return the country short code cache
	 */
	public ProviderConfigCache getCountryShortCodeCache() {
		return countryShortCodeCache;
	}


	/**
	 * Load cache.
	 *
	 * @param provideCacheLoader the provide cache loader
	 */
	private static void loadCache(ProvideCacheLoader provideCacheLoader) {
		ConfigDBService configDBService = new ConfigDBService();
		try {
			ConcurrentMap<String, ProviderProperty> hm;
			hm = configDBService.getProviderinitConfigProperties();
			provideCacheLoader.getProviderConfig().storeAll(hm);
			LOG.debug("=========  loded provider cache");
			ConcurrentMap<String, String> countryHm;
			countryHm = configDBService.getFullNameFromISOCountryCodes();
			provideCacheLoader.getCountryConfig().storeAll(countryHm);
			
			ConcurrentMap<String, Integer> userIdHm;//added under AT-2248-unrelated to any jira
			userIdHm = configDBService.getUserIdAndName();
			provideCacheLoader.getUserIdConfig().storeAll(userIdHm);
			
			ConcurrentMap<String,Integer> eddCountryHm;//AT-3346
			eddCountryHm = configDBService.getEddNumber();
			provideCacheLoader.getCountryEddNumber().storeAll(eddCountryHm);
			
			ConcurrentMap<String, String> countryState;//AT-3719
			countryState = configDBService.getCountryStateCode();
			provideCacheLoader.getCountryStateCache().storeAll(countryState);
			
			ConcurrentMap<String, String> countryShortHm;//AT-4666
			countryShortHm = configDBService.getCountryShortCode();
			provideCacheLoader.getCountryShortCodeCache().storeAll(countryShortHm);
			
		} catch (ComplianceException ex) {
			LOG.error("Error in class ProvideCacheLoader loadCache() :", ex);
		} catch (Exception e) {
			LOG.error("Error in class ProvideCacheLoader loadCache() :", e);
		}
	}

	/**
	 * Gets the provider property.
	 *
	 * @param providerName the provider name
	 * @return the provider property
	 * @throws ComplianceException the compliance exception
	 */
	public ProviderProperty getProviderProperty(String providerName) throws ComplianceException {
		try {
			return (ProviderProperty) instance.getProviderConfig().retrieve(providerName);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
	}

	/**
	 * Gets the end point url.
	 *
	 * @param providerName the provider name
	 * @return the end point url
	 * @throws ComplianceException the compliance exception
	 */
	public String getEndPointUrl(String providerName) throws ComplianceException {
		try {
			ProviderProperty property = (ProviderProperty) instance.getProviderConfig().retrieve(providerName);
			return property.getEndPointUrl();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}	
	}

	/**
	 * Gets the country full name.
	 *
	 * @param countryCode the country code
	 * @return the country full name
	 * @throws ComplianceException the compliance exception
	 */
	public String getCountryFullName(String countryCode)throws ComplianceException {
		try {
			if(countryCode == null){
				return null;
			}
			countryCode = countryCode.toUpperCase();	
			return (String)instance.getCountryConfig().retrieve(countryCode);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
	}
	
	/**
	 * added under AT-2248-unrelated to any jira
	 * 
	 * Gets the user id from cache.
	 *
	 * @param userName the user name
	 * @return the user id from cache
	 * @throws ComplianceException the compliance exception
	 */
	public Integer getUserIdFromCache(String userName) throws ComplianceException {
		try {
			if(userName == null){
				return null;
			}	
			return (Integer)instance.getUserIdConfig().retrieve(userName);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
	}
	
	/**AT-3346
	 * Gets the country edd number.
	 *
	 * @param countryCode the country code
	 * @return the country edd number
	 * @throws ComplianceException the compliance exception
	 */
	public Integer getCountryEddNumber(String countryCode) throws ComplianceException {
		try {
			if(countryCode == null){
				return null;
			}	
			return (Integer)instance.getCountryEddNumber().retrieve(countryCode);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
	}	
	
	/**
	 * Gets the country state code value.
	 *
	 * @param countryState the country state
	 * @return the country state code value
	 * @throws ComplianceException the compliance exception
	 */
	public String getCountryStateCodeValue(String countryState)throws ComplianceException {
		try {
			if(countryState == null){
				return null;
			}
			return (String)instance.getCountryStateCache().retrieve(countryState);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
	}
	
	//AT-4666
	/**
	 * Gets the country short code.
	 *
	 * @param countryCode the country code
	 * @return the country short code
	 * @throws ComplianceException the compliance exception
	 */
	public String getCountryShortCode(String countryCode)throws ComplianceException {
		try {
			if(countryCode == null){
				return null;
			}
			countryCode = countryCode.toUpperCase();	
			return (String)instance.getCountryShortCodeCache().retrieve(countryCode);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		}
	}
}