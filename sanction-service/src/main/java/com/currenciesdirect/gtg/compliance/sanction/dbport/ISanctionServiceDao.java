/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: ISanctionServiceDao.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.dbport;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Interface ISanctionServiceDao.
 */
public interface ISanctionServiceDao {
	
	/**
	 * Log sanction request into DB.
	 *
	 * @param sanctionRequest the sanction request
	 * @param reuest the reuest
	 * @return the boolean
	 * @throws SanctionException the sanction exception
	 */
	public Boolean logSanctionRequest(SanctionRequest sanctionRequest,String reuest) throws SanctionException;
	
	/**
	 * Log sanction response into DB.
	 *
	 * @param sanctionRequest the sanction request
	 * @param response the response
	 * @return the boolean
	 * @throws SanctionException the sanction exception
	 */
	public Boolean logSanctionResponse(SanctionRequest sanctionRequest, String response) throws SanctionException;

}
