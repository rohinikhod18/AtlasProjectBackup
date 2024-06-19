package com.currenciesdirect.gtg.compliance.fraugster.core;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.dbport.FraugsterDBServiceImpl;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

/**
 * The Class FraugsterConcreteDataBuilder.
 *
 * @author manish
 */
public class FraugsterConcreteDataBuilder extends FraugsterAbstractCacheDataBuilder {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterConcreteDataBuilder.class);

	/** The fraugster concrete data builder. */
	private static FraugsterConcreteDataBuilder fraugsterConcreteDataBuilder = null;

	/** The db service impl. */
	private IDBService dbServiceImpl = FraugsterDBServiceImpl.getInstance();

	/**
	 * Instantiates a new fraugster concrete data builder.
	 */
	private FraugsterConcreteDataBuilder() {
		LOG.debug("FraugsterConcreteDataBuilder Contructor created");
	}

	/**
	 * Gets the single instance of FraugsterConcreteDataBuilder.
	 *
	 * @return single instance of FraugsterConcreteDataBuilder
	 */
	public static FraugsterConcreteDataBuilder getInstance() {
		if (fraugsterConcreteDataBuilder == null) {
			fraugsterConcreteDataBuilder = new FraugsterConcreteDataBuilder();
		}
		return fraugsterConcreteDataBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.
	 * FraugsterAbstractCacheDataBuilder#loadCache()
	 */
	@Override
	public void loadCache() {
		try {
			ConcurrentMap<String, FraugsterProviderProperty> hm;
			hm = dbServiceImpl.getFraugsterProviderInitConfigProperty();
			cacheDataStructure.setFraugsterProviderinitConfigMap(hm);
		} catch (FraugsterException ipException) {
			LOG.error("Error in class FraugsterConcreteDataBuilder loadCache() :", ipException);
		} catch (Exception e) {
			LOG.error("Error in class FraugsterConcreteDataBuilder loadCache() :", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.
	 * FraugsterAbstractCacheDataBuilder#getProviderInitConfigProperty(java.lang
	 * .String)
	 */
	@Override
	public FraugsterProviderProperty getProviderInitConfigProperty(String methodName) throws FraugsterException {
		FraugsterProviderProperty property = new FraugsterProviderProperty();
		try {
			property = cacheDataStructure.getProviderinitConfigMap(methodName);
		} catch (Exception e) {
			throw new FraugsterException(FraugsterErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return property;
	}

}
