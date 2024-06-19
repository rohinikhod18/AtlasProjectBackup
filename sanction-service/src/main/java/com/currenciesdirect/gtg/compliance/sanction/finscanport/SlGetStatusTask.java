/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SlGetStatusTask.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.finscanport;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.sanction.core.ISanctionService;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusRequest;
import com.currenciesdirect.gtg.compliance.sanction.core.domain.SanctionGetStatusResponse;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;

/**
 * The Class SlGetStatusTask.
 *
 * @author manish
 */
public class SlGetStatusTask implements Callable<IDomain>{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(SlGetStatusTask.class);
	
	/** The port. */
	private ISanctionService port = FinscanPort.getInstance(); 
	
	/** The funds out request. */
	private SanctionGetStatusRequest fundsOutRequest;
	
	/** The provider property. */
	private ProviderProperty providerProperty;
	
	/**
	 * Instantiates a new sl get status task.
	 *
	 * @param fundsOutRequest the funds out request
	 * @param providerProperty the provider property
	 */
	public SlGetStatusTask(SanctionGetStatusRequest fundsOutRequest,ProviderProperty providerProperty){
		this.providerProperty = providerProperty;
		this.fundsOutRequest = fundsOutRequest;
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 */
	@Override
	public IDomain call() throws Exception {
		SanctionGetStatusResponse response = new SanctionGetStatusResponse();
		try{
			response = port.getSanctionStatus(fundsOutRequest, providerProperty);
		}catch(SanctionException e){
			logDebug(e);
			response.setStatus(Constants.FAIL);
			response.setErrorCode(e.getSanctionErrors().getErrorCode());
			response.setErrorDescription(e.getSanctionErrors().getErrorDescription());
		}catch(Exception e){
			LOG.error("Error in SlGetStatusTask call method", e);
			response.setStatus(Constants.FAIL);
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
