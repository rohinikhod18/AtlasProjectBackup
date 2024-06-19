/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.restport;


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

import com.currenciesdirect.gtg.compliance.kyc.core.IKYCService;
import com.currenciesdirect.gtg.compliance.kyc.core.KYCServiceImpl;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;


/**
 * @author manish
 *
 */
@Path("/")
public class RestPort {

	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(RestPort.class);
	
	/**
	 * 1)Rest Service "Check KYC Details"
	 * 2)This Service will request to third party provider for getting KYC details.
	 * 3)And will return response accordingly.
	 * @param headers
	 * @param request
	 * @return
	 */
	@POST
	@Path("/checkkyc")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkKYC(@Context HttpHeaders headers,KYCProviderRequest request) {
		LOG.debug("RestPort : getDetailsFromProviders method Start");
		KYCProviderResponse response = new KYCProviderResponse();
		IKYCService kycService = KYCServiceImpl.getInstance();
		long time = System.currentTimeMillis();
		try {
				response = kycService.checkKYC(request);
				response.setStatus("Success");
			
		}catch(KYCException exception) {
			logDebug(exception);
			response = createServiceFailureResponse(request);
			response.setErrorCode(exception.getkycErrors().getErrorCode());
			response.setErrorDescription(exception.getkycErrors().getErrorDescription());
		} catch (Exception e) {
			logDebug(e);
			response = createServiceFailureResponse(request);
			response.setErrorCode("0999");
			response.setErrorDescription("GenericExecption");
				
			}
		LOG.debug("ComplainceServiceIntegration:getDetailsFromProviders END");
		return Response.status(200).entity(response).header("requestReceived", time).build();
	
}
	private void logDebug(Throwable exception) {
		LOG.debug("Error in class GBGroup Port :", exception);
	}
	
	/**
	 * Creates the service failure response.
	 *
	 * @param request the request
	 * @return the KYC provider response
	 */
	private KYCProviderResponse createServiceFailureResponse(KYCProviderRequest request) {
		KYCProviderResponse response = new KYCProviderResponse();
		List<KYCContactResponse> contactResponseList = new ArrayList<>();
		for (KYCContactRequest contactRequest : request.getContact()) {
			KYCContactResponse contactResponse = new KYCContactResponse();
			contactResponse.setId(contactRequest.getId());
			contactResponse.setStatus(Constants.SERVICE_FAILURE);
			contactResponseList.add(contactResponse);
		}
		response.setStatus(Constants.SERVICE_FAILURE);
		response.setContactResponse(contactResponseList);
		return response;
	}
}