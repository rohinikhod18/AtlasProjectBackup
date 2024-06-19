package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LongAndIntegerUtils {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(LongAndIntegerUtils.class);
	
	/**
	 * Instantiates a new long and integer utils.
	 */
	private LongAndIntegerUtils() {}
	
	/**
	 * AT - 3118
	 * Gets the entity service hash.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @param serviceType the service type
	 * @return the entity service hash
	 */
	public static Long getEntityServiceHash(Integer entityId,Integer entityType,Integer serviceType) {
		Long entityServiceHash = null;
		try {
			entityServiceHash = ((long)entityId * 10000) + (entityType * 100) + serviceType;
		}catch(Exception e) {
			LOG.error("error in getEntityServiceHash() : {1}",e);
		}
		return entityServiceHash;
	}

}
