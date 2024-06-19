package com.currenciesdirect.gtg.compliance.fraugster.restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupRequest;

@Path("/")
public interface IFraugsterServicePort {

	/**
	 * Do sign up fraugster check.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/fraudDetection_signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doSignUpFraugsterCheck(HttpHeaders headers, FraugsterSignupRequest request);

	/**
	 * Do on update fraugster check.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/fraudDetection_onUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doOnUpdateFraugsterCheck(HttpHeaders headers, FraugsterOnUpdateRequest request);

	/**
	 * Do payments out fraugster check.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/fraudDetection_paymentsOut")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doPaymentsOutFraugsterCheck(HttpHeaders headers, FraugsterPaymentsOutRequest request);

	/**
	 * Do payments in fraugster check.
	 *
	 * @param headers the headers
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/fraudDetection_paymentsIn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response doPaymentsInFraugsterCheck(HttpHeaders headers, FraugsterPaymentsInRequest request);

}