package com.currenciesdirect.gtg.compliance.blacklist.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSubScriptionDataStructure;
import com.currenciesdirect.gtg.compliance.blacklist.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;

/**
 * The Class BalcklistDataBuilder.
 */
public class BalcklistDataBuilder {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BalcklistDataBuilder.class);

	/** The blacklist sub scription data structure. */
	private static BlacklistSubScriptionDataStructure blacklistSubScriptionDataStructure = BlacklistSubScriptionDataStructure
			.getInstance();

	/** The blacklist cache data builder. */
	@SuppressWarnings("squid:S3077")
	private static volatile BalcklistDataBuilder blacklistCacheDataBuilder = null;

	/** The i blacklist service dao. */
	private static IDBService iBlacklistServiceDao = DBServiceImpl.getInstance();

	/**
	 * Instantiates a new balcklist data builder.
	 */
	private BalcklistDataBuilder() {
	}

	/**
	 * Gets the single instance of BalcklistDataBuilder.
	 *
	 * @return single instance of BalcklistDataBuilder
	 */
	public static BalcklistDataBuilder getInstance() {
		if (blacklistCacheDataBuilder == null) {
			synchronized (BalcklistDataBuilder.class) {
				blacklistCacheDataBuilder = new BalcklistDataBuilder();
			}
		}
		return blacklistCacheDataBuilder;
	}

	static {
		try {
			loadCache();
		} catch (BlacklistException blacklistException) {
			try {
				throw blacklistException;
			} catch (BlacklistException e) {
				LOG.error("Error Load Cache: ", e);
			}

		}
	}

	/**
	 * Load cache.
	 *
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public static void loadCache() throws BlacklistException {
		blacklistSubScriptionDataStructure.setBlaklistTypes(iBlacklistServiceDao.getAllBlacklistTypes());
	}

	/**
	 * Checks if is blacklist type present.
	 *
	 * @param type
	 *            the type
	 * @return true, if is type present
	 * @throws BlacklistException
	 *             the blacklist exception
	 */
	public boolean isTypePresent(String type) throws BlacklistException {
		try {
			return blacklistSubScriptionDataStructure.isTypePresent(type);
		} catch (Exception exception) {
			throw new BlacklistException(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA_FROM_CACHE, exception);
		}
	}
}
