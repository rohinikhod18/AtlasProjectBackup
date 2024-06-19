package com.currenciesdirect.gtg.compliance.core.loadcache;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class FraudSightConcreteDataBuilder.
 */
public class FraudSightConcreteDataBuilder extends FraudSightAbstractCacheDataBuilder {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraudSightConcreteDataBuilder.class);

	/** The fraud sight concrete data builder. */
	private static FraudSightConcreteDataBuilder fraudSightConcreteDataBuilder = null;

	/** The db service impl. */
	private IDBService dbServiceImpl = FraudSightDBServiceImpl.getInstance();

	/**
	 * Instantiates a new fraud sight concrete data builder.
	 */
	private FraudSightConcreteDataBuilder() {
		LOG.debug("FraudSightConcreteDataBuilder Contructor created");
	}

	/**
	 * Gets the single instance of FraudSightConcreteDataBuilder.
	 *
	 * @return single instance of FraudSightConcreteDataBuilder
	 */
	public static FraudSightConcreteDataBuilder getInstance() {
		if (fraudSightConcreteDataBuilder == null) {
			fraudSightConcreteDataBuilder = new FraudSightConcreteDataBuilder();
		}
		return fraudSightConcreteDataBuilder;
	}

	/**
	 * Load cache.
	 */
	@Override
	public void loadCache() {
		try {
			ConcurrentMap<String, FraudSightProviderProperty> hm;
			hm = dbServiceImpl.getFraudSightProviderInitConfigProperty();
			cacheDataStructure.setFraudSightProviderinitConfigMap(hm);
		} catch (Exception e) {
			LOG.error("Error in class FraudSightConcreteDataBuilder loadCache() :", e);
		}
	}

	/**
	 * Gets the provider init config property.
	 *
	 * @param methodName the method name
	 * @return the provider init config property
	 */
	@Override
	public FraudSightProviderProperty getProviderInitConfigProperty(String methodName) {
		FraudSightProviderProperty property = new FraudSightProviderProperty();
		try {
			property = cacheDataStructure.getProviderinitConfigMap(methodName);
		} catch (Exception e) {
			LOG.error("Error in class FraudSightConcreteDataBuilder getProviderInitConfigProperty() :", e);
		}
		return property;
	}

}
