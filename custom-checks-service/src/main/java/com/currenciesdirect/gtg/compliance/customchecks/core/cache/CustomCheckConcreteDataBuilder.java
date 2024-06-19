package com.currenciesdirect.gtg.compliance.customchecks.core.cache;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customchecks.core.ICacheDBService;
import com.currenciesdirect.gtg.compliance.customchecks.dbport.CacheDBServiceImpl;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckFraudPredictRules;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckVelocityRules;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class CustomCheckConcreteDataBuilder.
 *
 * @author abhijeetg
 */
public class CustomCheckConcreteDataBuilder extends CustomCheckAbstractCacheDataBuilder {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CustomCheckConcreteDataBuilder.class);

	/** The custom check concrete data builder. */
	private static CustomCheckConcreteDataBuilder customCheckConcreteDataBuilder = null;

	/** The db service impl. */
	private ICacheDBService dbServiceImpl = CacheDBServiceImpl.getInstance();

	/**
	 * Instantiates a new custom check concrete data builder.
	 */
	private CustomCheckConcreteDataBuilder() {
		LOG.debug("Contructor created");
	}

	/**
	 * Gets the single instance of CustomCheckConcreteDataBuilder.
	 *
	 * @return single instance of CustomCheckConcreteDataBuilder
	 */
	public static CustomCheckConcreteDataBuilder getInstance() {
		if (customCheckConcreteDataBuilder == null) {
			customCheckConcreteDataBuilder = new CustomCheckConcreteDataBuilder();
		}
		return customCheckConcreteDataBuilder;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.CustomCheckAbstractCacheDataBuilder#loadCache()
	 */
	@Override
	public void loadCache() {
		try {
			Map<String, CustomCheckVelocityRules> hm  = dbServiceImpl.getVelocityRulesCacheData();
			cacheDataStructure.setVelocityRulesMap(hm);
			//ADD for AT-3161
			Map<String, CustomCheckFraudPredictRules> hmfp  = dbServiceImpl.getFraudPredictProviderInitConfigProperty();
			cacheDataStructure.setFraudPredictRulesMap(hmfp);
		} catch (CustomChecksException customChecksException) {
			LOG.error("Error in class CustomCheckConcreteDataBuilder loadCache() :", customChecksException);
		} catch (Exception e) {
			LOG.error("Error in class CustomCheckConcreteDataBuilder loadCache() :", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.CustomCheckAbstractCacheDataBuilder#getVelocityRulesMap(java.lang.String)
	 */
	@Override
	public CustomCheckVelocityRules getVelocityRulesMap(String key) throws CustomChecksException {
		CustomCheckVelocityRules customCheckVelocityRules;
		try {
			customCheckVelocityRules = cacheDataStructure.getVelocityRulesMap(key);
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return customCheckVelocityRules;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.CustomCheckAbstractCacheDataBuilder#getVelocityRulesMap(java.lang.String)
	 */
	public CustomCheckVelocityRules getVelocityRule(String organizationCode, String custType, String eventType) throws CustomChecksException {
		String key = organizationCode + "-" + custType + "-" + eventType;
		CustomCheckVelocityRules customCheckVelocityRules;
		try {
			customCheckVelocityRules = cacheDataStructure.getVelocityRulesMap(key.toUpperCase());
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return customCheckVelocityRules;
	}
	
	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName the method name
	 * @return the provider init config property
	 * @throws CustomChecksException the custom checks exception
	 */
	//ADD for AT-3161
	public CustomCheckFraudPredictRules getProviderInitConfigProperty(String methodName) throws CustomChecksException {
		CustomCheckFraudPredictRules property = new CustomCheckFraudPredictRules();
		try {
			property = cacheDataStructure.getFraudPredictRulesMap(methodName);
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return property;
	}

}
