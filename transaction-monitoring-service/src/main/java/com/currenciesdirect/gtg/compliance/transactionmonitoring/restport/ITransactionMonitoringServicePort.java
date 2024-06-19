package com.currenciesdirect.gtg.compliance.transactionmonitoring.restport;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;


// TODO: Auto-generated Javadoc
/**
 * The Interface ITransactionMonitoringServicePort.
 */
@Path("/")
public interface ITransactionMonitoringServicePort {

	/**
	 * Do transaction monitoring check for new sign up.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/transactionMonitoring_signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doTransactionMonitoringCheckForNewSignUp(HttpHeaders headers, TransactionMonitoringSignupRequest request);
	
	/**
	 * Do transaction monitoring check for update.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/transactionMonitoring_update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doTransactionMonitoringCheckForUpdate(HttpHeaders headers, TransactionMonitoringSignupRequest request);
	
	/**
	 * Do transaction monitoring check for funds in.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/transactionMonitoring_fundsIn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doTransactionMonitoringCheckForFundsIn(HttpHeaders headers, TransactionMonitoringPaymentsInRequest request);

}