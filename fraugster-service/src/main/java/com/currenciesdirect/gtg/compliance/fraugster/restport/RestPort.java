/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.restport;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.FraugsterServiceImpl;
import com.currenciesdirect.gtg.compliance.fraugster.core.IFraugsterService;
import com.currenciesdirect.gtg.compliance.fraugster.util.Constants;

/**
 * The Class RestPort.
 *
 * @author manish
 */
@Path("/")
public class RestPort implements IFraugsterServicePort {

	private static final String NOT_REQUIRED = "NOT_REQUIRED";

  /** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RestPort.class);

	/** The i fraugster service. */
	private IFraugsterService iFraugsterService = FraugsterServiceImpl.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.restport.
	 * IFraugsterServicePort#doSignUpFraugsterCheck(javax.ws.rs.core.
	 * HttpHeaders, com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterSignupRequest)
	 */
	@POST
	@Path("/fraudDetection_signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doSignUpFraugsterCheck(@Context HttpHeaders headers, FraugsterSignupRequest request) {
		FraugsterSignupResponse response = new FraugsterSignupResponse();
		try {
			response = iFraugsterService.doFraugsterCheckForNewSignUp(request);

		} catch (Exception e) {
			LOG.debug("Error in RestPort : doSignUpFraugsterCheck method", e);
			response = createSignupFailureResponse(request.getContactRequests());
		}
		return Response.status(200).entity(response).build();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.restport.
	 * IFraugsterServicePort#doOnUpdateFraugsterCheck(javax.ws.rs.core.
	 * HttpHeaders, com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterOnUpdateRequest)
	 */
	@Override
	@POST
	@Path("/fraudDetection_onUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doOnUpdateFraugsterCheck(@Context HttpHeaders headers, FraugsterOnUpdateRequest request) {
		LOG.debug("RestPort : doOnUpdateFraugsterCheck method Start");
		FraugsterOnUpdateResponse response = new FraugsterOnUpdateResponse();
		try {
			response = iFraugsterService.doFraugsterCheckForOnUpdate(request);

		} catch (Exception e) {
			LOG.error("Error in RestPort : doOnUpdateFraugsterCheck method", e);
			response = createUpdateServiceFailureResponse(request);
		}
		LOG.debug("RestPort: doOnUpdateFraugsterCheck END");
		return Response.status(200).entity(response).build();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.restport.
	 * IFraugsterServicePort#doPaymentsOutFraugsterCheck(javax.ws.rs.core.
	 * HttpHeaders, com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterPaymentsOutRequest)
	 */
	@Override
	@POST
	@Path("/fraudDetection_paymentsOut")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPaymentsOutFraugsterCheck(@Context HttpHeaders headers, FraugsterPaymentsOutRequest request) {
		FraugsterPaymentsOutResponse response = new FraugsterPaymentsOutResponse();
		try {
			response = iFraugsterService.doFraugsterCheckForPaymentsOut(request);

		} catch (Exception e) {
			LOG.error("Error in RestPort : doPaymentsOutFraugsterCheck method", e);
			response = createPaymentOutServiceFailureResponse(request);
		}
		return Response.status(200).entity(response).build();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.fraugster.restport.
	 * IFraugsterServicePort#doPaymentsInFraugsterCheck(javax.ws.rs.core.
	 * HttpHeaders, com.currenciesdirect.gtg.compliance.fraugster.core.domain.
	 * FraugsterPaymentsInRequest)
	 */
	@Override
	@POST
	@Path("/fraudDetection_paymentsIn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response doPaymentsInFraugsterCheck(@Context HttpHeaders headers, FraugsterPaymentsInRequest request) {
		FraugsterPaymentsInResponse response = new FraugsterPaymentsInResponse();
		try {
			response = iFraugsterService.doFraugsterCheckForPaymentsIn(request);

		} catch (Exception e) {
			LOG.error("Error in RestPort : doPaymentsInFraugsterCheck method", e);
			response = createPaymentInServiceFailureResponse(request);
		}
		return Response.status(200).entity(response).build();

	}

	/**
	 * Creates the signup failure response.
	 *
	 * @param contactRequestList
	 *            the contact request list
	 * @return the fraugster signup response
	 */
	private FraugsterSignupResponse createSignupFailureResponse(
			List<FraugsterSignupContactRequest> contactRequestList) {
		FraugsterSignupResponse response = new FraugsterSignupResponse();
		List<FraugsterSignupContactResponse> contactResponses = new ArrayList<>();
		FraugsterSignupContactResponse contactResponse = new FraugsterSignupContactResponse();
		for (FraugsterSignupContactRequest contact : contactRequestList) {
			contactResponse.setId(contact.getId());
			contactResponse.setStatus(Constants.SERVICE_FAILURE);
			contactResponses.add(contactResponse);
		}
		response.setContactResponses(contactResponses);
		response.setStatus(Constants.SERVICE_FAILURE);
		return response;
	}

	/**
	 * Creates the update service failure response.
	 *
	 * @param request
	 *            the request
	 * @return the fraugster on update response
	 */
	private FraugsterOnUpdateResponse createUpdateServiceFailureResponse(FraugsterOnUpdateRequest request) {

		FraugsterOnUpdateResponse response = new FraugsterOnUpdateResponse();
		List<FraugsterOnUpdateContactResponse> contactResponses = new ArrayList<>();

		for (FraugsterOnUpdateContactRequest contactRequest : request.getContactRequests()) {
			FraugsterOnUpdateContactResponse contactResponse = new FraugsterOnUpdateContactResponse();
			contactResponse.setId(contactRequest.getId());
			contactResponse.setStatus(Constants.SERVICE_FAILURE);
			contactResponses.add(contactResponse);
		}
		response.setStatus(Constants.SERVICE_FAILURE);
		response.setContactResponses(contactResponses);
		return response;
	}

	/**
	 * Creates the payment out service failure response.
	 *
	 * @param request
	 *            the request
	 * @return the fraugster payments out response
	 */
	private FraugsterPaymentsOutResponse createPaymentOutServiceFailureResponse(FraugsterPaymentsOutRequest request) {

		FraugsterPaymentsOutResponse response = new FraugsterPaymentsOutResponse();
		List<FraugsterPaymentsOutContactResponse> contactResponses = new ArrayList<>();
		if (System.getProperty("custType.toPerform.fraugster").contains(request.getCustType())) {
			for (FraugsterPaymentsOutContactRequest contactRequest : request.getContactRequests()) {
				FraugsterPaymentsOutContactResponse contactResponse = new FraugsterPaymentsOutContactResponse();
				contactResponse.setId(contactRequest.getId());
				contactResponse.setStatus(NOT_REQUIRED);
				contactResponses.add(contactResponse);
			}
			response.setStatus(NOT_REQUIRED);
		} else {

			for (FraugsterPaymentsOutContactRequest contactRequest : request.getContactRequests()) {
				FraugsterPaymentsOutContactResponse contactResponse = new FraugsterPaymentsOutContactResponse();
				contactResponse.setId(contactRequest.getId());
				contactResponse.setStatus(Constants.SERVICE_FAILURE);
				contactResponses.add(contactResponse);
			}
			response.setStatus(Constants.SERVICE_FAILURE);
		}
		response.setContactResponses(contactResponses);
		return response;

	}

	/**
	 * Creates the payment in service failure response.
	 *
	 * @param request
	 *            the request
	 * @return the fraugster payments in response
	 */
	private FraugsterPaymentsInResponse createPaymentInServiceFailureResponse(FraugsterPaymentsInRequest request) {

		FraugsterPaymentsInResponse response = new FraugsterPaymentsInResponse();
		List<FraugsterPaymentsInContactResponse> contactResponses = new ArrayList<>();

		if (System.getProperty("custType.toPerform.fraugster").contains(request.getCustType())) {
			for (FraugsterPaymentsInContactRequest contactRequest : request.getContactRequests()) {
				FraugsterPaymentsInContactResponse contactResponse = new FraugsterPaymentsInContactResponse();
				contactResponse.setId(contactRequest.getId());
				contactResponse.setStatus(NOT_REQUIRED);
				contactResponses.add(contactResponse);
			}
			response.setStatus(NOT_REQUIRED);
		} else {
			for (FraugsterPaymentsInContactRequest contactRequest : request.getContactRequests()) {
				FraugsterPaymentsInContactResponse contactResponse = new FraugsterPaymentsInContactResponse();
				contactResponse.setId(contactRequest.getId());
				contactResponse.setStatus(Constants.SERVICE_FAILURE);
				contactResponses.add(contactResponse);
			}
			response.setStatus(Constants.SERVICE_FAILURE);
		}
		response.setContactResponses(contactResponses);

		return response;

	}

}