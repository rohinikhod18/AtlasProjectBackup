/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: IDBService.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Interface IDBService.
 */
@FunctionalInterface
public interface IDBService {
	
	
	
	/**
	 * Gets the sanction provider init config property.
	 *
	 * @return the sanction provider init config property
	 * @throws SanctionException the sanction exception
	 */
	public Map<String, ProviderProperty> getSanctionProviderInitConfigProperty()
			throws SanctionException;

}
