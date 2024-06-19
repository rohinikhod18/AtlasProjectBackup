/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SLLookUpMultiTask.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.finscanport;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.sanction.core.ISanctionService;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

/**
 * The Class SLLookUpMultiTask.
 *
 * @author manish
 */
public class SLLookUpMultiTask implements Callable<IDomain>{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SLLookUpMultiTask.class);

	/** The port. */
	private ISanctionService port = FinscanPort.getInstance(); 
	
	/** The sanction request. */
	private SanctionRequest sanctionRequest;
	
	/** The provider property. */
	private ProviderProperty providerProperty;
	
	/**
	 * Instantiates a new SL look up multi task.
	 *
	 * @param sanctionRequest the sanction request
	 * @param providerProperty the provider property
	 */
	public SLLookUpMultiTask(SanctionRequest sanctionRequest,ProviderProperty providerProperty){
		this.providerProperty = providerProperty;
		this.sanctionRequest = sanctionRequest;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public IDomain call() throws Exception {
		SanctionResponse response = new SanctionResponse();
		try{
			response = port.checkSanctionDetails(sanctionRequest, providerProperty);
		}catch(SanctionException e){
			logDebug(e);
			response.setErrorCode(e.getSanctionErrors().getErrorCode());
			response.setErrorDescription(e.getSanctionErrors().getErrorDescription());
		}catch(Exception e){
			LOG.error("Error in SLLookUpMultiTask call method", e);
			response.setErrorCode("0999");
			response.setErrorDescription("Generic Exception");
		}
		return response;
	}

	/**
	 * Log debug.
	 *
	 * @param exception the exception
	 */
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class SlGetStatusTask  :", exception);
	}


}
