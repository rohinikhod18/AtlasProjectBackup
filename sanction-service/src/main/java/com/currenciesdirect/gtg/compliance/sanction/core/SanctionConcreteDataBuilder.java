/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionConcreteDataBuilder.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.sanction.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Class SanctionConcreteDataBuilder.
 *
 * @author manish
 */
public class SanctionConcreteDataBuilder extends SanctionAbstractCacheDataBuilder {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SanctionConcreteDataBuilder.class);

	/** The sanction concrete data builder. */
	private static SanctionConcreteDataBuilder sanctionConcreteDataBuilder = null;

	/** The db service impl. */
	private IDBService dbServiceImpl = DBServiceImpl.getInstance();

	/**
	 * Instantiates a new sanction concrete data builder.
	 */
	private SanctionConcreteDataBuilder() {
		LOG.debug("Contructor created");
	}

	/**
	 * Gets the single instance of SanctionConcreteDataBuilder.
	 *
	 * @return single instance of SanctionConcreteDataBuilder
	 */
	public static SanctionConcreteDataBuilder getInstance() {
		if (sanctionConcreteDataBuilder == null) {
			sanctionConcreteDataBuilder = new SanctionConcreteDataBuilder();
		}
		return sanctionConcreteDataBuilder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.sanction.core.
	 * SanctionAbstractCacheDataBuilder#loadCache()
	 */
	@Override
	public void loadCache() {
		try {
			Map<String, ProviderProperty> hm = dbServiceImpl.getSanctionProviderInitConfigProperty();
			cacheDataStructure.setSanctionProviderinitConfigMap(hm);
		} catch (SanctionException sanctionException) {
			LOG.error("Error in class SanctionConcreteDataBuilder loadCache() :", sanctionException);
		} catch (Exception e) {
			LOG.error("Error in class SanctionConcreteDataBuilder loadCache() :", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.sanction.core.
	 * SanctionAbstractCacheDataBuilder#getProviderInitConfigProperty(java.lang.
	 * String)
	 */
	@Override
	public ProviderProperty getProviderInitConfigProperty(String providerName) throws SanctionException {
		ProviderProperty property = new ProviderProperty();
		try {
			property = cacheDataStructure.getProviderInitConfigMap(providerName);
		} catch (Exception e) {
			throw new SanctionException(SanctionErrors.ERROR_WHILE_FETCHING_FROM_CACHE, e);
		}
		return property;
	}

}
