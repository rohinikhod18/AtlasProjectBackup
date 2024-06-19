/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.restport;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupResponse;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.ITransactionMonitoringService;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.TransactionMonitoringServiceImpl;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.util.Constants;

/**
 * The Class RestPort.
 */
@Path("/")
public class RestPort implements ITransactionMonitoringServicePort {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RestPort.class);

	/** The i transaction monitoring service. */
	private ITransactionMonitoringService iTransactionMonitoringService = TransactionMonitoringServiceImpl
			.getInstance();

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
	public Response doTransactionMonitoringCheckForNewSignUp(@Context HttpHeaders headers,
			TransactionMonitoringSignupRequest request) {
		TransactionMonitoringSignupResponse response = new TransactionMonitoringSignupResponse();
		try {
			response = iTransactionMonitoringService.doTransactionMonitoringCheckForNewSignUp(request);

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doTransactionMonitoringCheckForNewSignUp method", e);
			response = createSignupFailureResponse();
		}
		return Response.status(200).entity(response).build();

	}

	/**
	 * Creates the signup failure response.
	 *
	 * @param contactRequestList the contact request list
	 * @return the transaction monitoring signup response
	 */
	private TransactionMonitoringSignupResponse createSignupFailureResponse() {

		TransactionMonitoringSignupResponse response = new TransactionMonitoringSignupResponse();
		TransactionMonitoringAccountSignupResponse accResponse = new TransactionMonitoringAccountSignupResponse();
		accResponse.setStatus(Constants.SERVICE_FAILURE);

		response.setTransactionMonitoringAccountSignupResponse(accResponse);
		return response;
	}
	
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
	public Response doTransactionMonitoringCheckForUpdate(@Context HttpHeaders headers,
			TransactionMonitoringSignupRequest request) {
		TransactionMonitoringSignupResponse response = new TransactionMonitoringSignupResponse();
		try {
			response = iTransactionMonitoringService.doTransactionMonitoringCheckForUpdate(request);

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doTransactionMonitoringCheckForUpdate method", e);
			response = createSignupFailureResponse();
		}
		return Response.status(200).entity(response).build();

	}
	
	
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
	public Response doTransactionMonitoringCheckForFundsIn(@Context HttpHeaders headers,
			TransactionMonitoringPaymentsInRequest request) {
		TransactionMonitoringPaymentInResponse response = new TransactionMonitoringPaymentInResponse();
		try {
			response = iTransactionMonitoringService.doTransactionMonitoringCheckForFundsIn(request);

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doTransactionMonitoringCheckForFundsIn method", e);
			response = createFundsInFailureResponseForFundsIn();
		}
		return Response.status(200).entity(response).build();

	}
	
	@POST
	@Path("/transactionMonitoring_fundsOut")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doTransactionMonitoringCheckForFundsOut(@Context HttpHeaders headers,
			TransactionMonitoringPaymentsOutRequest request) {
		TransactionMonitoringPaymentOutResponse response = new TransactionMonitoringPaymentOutResponse();
		try {
			response = iTransactionMonitoringService.doTransactionMonitoringCheckForFundsOut(request);

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doTransactionMonitoringCheckForFundsOut method", e);
			response = createFundsOutFailureResponseForFundsOut();
		}
		return Response.status(200).entity(response).build();

	}
	
	/**
	 * Creates the funds in failure response for funds in.
	 *
	 * @param contactRequestList the contact request list
	 * @return the transaction monitoring payment in response
	 */
	private TransactionMonitoringPaymentInResponse createFundsInFailureResponseForFundsIn() {

		TransactionMonitoringPaymentInResponse response = new TransactionMonitoringPaymentInResponse();
		response.setStatus(Constants.SERVICE_FAILURE);
		return response;
	}
	
	/**
	 * Creates the funds out failure response for funds out.
	 *
	 * @return the transaction monitoring payment out response
	 */
	private TransactionMonitoringPaymentOutResponse createFundsOutFailureResponseForFundsOut() {

		TransactionMonitoringPaymentOutResponse response = new TransactionMonitoringPaymentOutResponse();
		response.setStatus(Constants.SERVICE_FAILURE);
		return response;
	}
	
	/**
	 * Do transaction monitoring check re perform.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/transactionMonitoring_mq_resend")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doTransactionMonitoringCheckRePerform(@Context HttpHeaders headers,
			TransactionMonitoringMQServiceRequest request) {
		TransactionMonitoringMQServiceResponse response = new TransactionMonitoringMQServiceResponse();
		try {
			if(request.getRequestType().equalsIgnoreCase("signup") || request.getRequestType().equalsIgnoreCase("add_contact") || 
					request.getRequestType().equalsIgnoreCase("update")) {
				TransactionMonitoringSignupRequest signupReq = request.getTransactionMonitoringSignupRequest();
				TransactionMonitoringSignupResponse signupResp = iTransactionMonitoringService.doTransactionMonitoringCheckForNewSignUp(signupReq);
				response.setTransactionMonitoringSignupResponse(signupResp);
			}else if(request.getRequestType().equalsIgnoreCase("payment_in") || request.getRequestType().equalsIgnoreCase("payment_in_update")) {
				TransactionMonitoringPaymentsInRequest payInReq = request.getTransactionMonitoringPaymentsInRequest();
				TransactionMonitoringPaymentInResponse payInResp = iTransactionMonitoringService.doTransactionMonitoringCheckForFundsIn(payInReq);
				response.setTransactionMonitoringPaymentInResponse(payInResp);
			}else if(request.getRequestType().equalsIgnoreCase("payment_out") || request.getRequestType().equalsIgnoreCase("payment_out_update")) {
				TransactionMonitoringPaymentsOutRequest payOutReq = request.getTransactionMonitoringPaymentsOutRequest();
				TransactionMonitoringPaymentOutResponse payOutResp = iTransactionMonitoringService.doTransactionMonitoringCheckForFundsOut(payOutReq);
				response.setTransactionMonitoringPaymentOutResponse(payOutResp);
			}

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doTransactionMonitoringCheckRePerform method", e);
		}
		return Response.status(200).entity(response).build();

	}
	
	//AT-4896
	/**
	 * Do transaction monitoring check for post transaction.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/transactionMonitoring_postCardTransaction")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doTransactionMonitoringCheckForPostTransaction(@Context HttpHeaders headers,
			TransactionMonitoringPostCardTransactionRequest request) {
		TransactionMonitoringPostCardTransactionResponse response = new TransactionMonitoringPostCardTransactionResponse();
		try {

			response = iTransactionMonitoringService.doTransactionMonitoringCheckForPostCardTransaction(request);

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doTransactionMonitoringCheckForPostTransaction method", e);

		}
		return Response.status(200).entity(response).build();

	}

}