package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import java.util.concurrent.ConcurrentMap;

import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;
import com.currenciesdirect.gtg.compliance.dbport.BlacklistPayrefDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;

public class BlacklistPayrefConcreteDataBuilder extends BlacklistPayrefAbstractcacheDatabuilder {
	private static final org.slf4j.Logger LOG =
	 LoggerFactory.getLogger(BlacklistPayrefConcreteDataBuilder.class);

	/** The BlacklistPayref concrete data builder. */
	private static BlacklistPayrefConcreteDataBuilder blacklistpayrefConcreteDataBuilder = null;

	private IDBService dbServiceImpl = BlacklistPayrefDBServiceImpl.getInstance();

	public BlacklistPayrefConcreteDataBuilder() {
		LOG.debug("BlacklistPayrefConcreteDataBuilder Contructor created");
	}

	public static BlacklistPayrefConcreteDataBuilder getInstance() {
		if (blacklistpayrefConcreteDataBuilder == null) {
			blacklistpayrefConcreteDataBuilder = new BlacklistPayrefConcreteDataBuilder();
		}
		return blacklistpayrefConcreteDataBuilder;
	}

	@Override
	public void loadCache() {
		try {

			ConcurrentMap<String, BlacklistPayrefProviderProperty> hm;
			hm = dbServiceImpl.getBlacklistPayrefProviderInitConfigProperty();
			cacheDataStructure.setBlacklistPayrefCache(hm);
		} catch (BlacklistException ipException) {
			LOG.error("Error in class BlacklistPayrefConcreteDataBuilder loadCache() :",
			 ipException);
		} catch (Exception e) {
			 LOG.error("Error in class BlacklistPayrefConcreteDataBuilder loadCache() :",
			 e);
		}

	}

	// @Override

	// public

	@Override
	public BlacklistPayrefProviderProperty getProviderInitConfigProperty(String methodName) throws BlacklistException {
		BlacklistPayrefProviderProperty property = new BlacklistPayrefProviderProperty();
		try {
			property = cacheDataStructure.getProviderinitConfigMap(methodName);
		} catch (Exception e) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_FETCHING_CACHE_DATA, e);
		}
		return property;
	}

}
