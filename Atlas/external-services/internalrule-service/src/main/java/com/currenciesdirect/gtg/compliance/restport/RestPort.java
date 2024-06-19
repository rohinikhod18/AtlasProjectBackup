/**
 * 
 */
package com.currenciesdirect.gtg.compliance.restport;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.currenciesdirect.gtg.compliance.core.InternalService;
import com.currenciesdirect.gtg.compliance.core.InternalServicesFacade;
import com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.IWhitelistBeneficiaryModifier;
import com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.WhitelistBeneficiaryModifierImpl;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.exception.Errors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.util.Constants;
import com.currenciesdirect.gtg.compliance.util.RequestTypes;

/**
 * @author manish
 *
 */
@Path("/")
public class RestPort {

	private InternalService internalService = InternalServicesFacade.getInstance();

	private IWhitelistBeneficiaryModifier whitelistBeneficiaryModifier = WhitelistBeneficiaryModifierImpl.getInstance();
	
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(RestPort.class);

	/**
	 * 
	 * @param headers
	 * @param request
	 * @return
	 */
	@POST
	@Path("/checkRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkRegistration(InternalServiceRequest request) {
		LOG.debug("RestPort : checkRegistration method Start");
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			response = internalService.performCheck(request);
		} catch (InternalRuleException exception) {
			LOG.error(Constants.ERROR_IN_RESTPORT_MESSAGE, exception);
			response = createServiceFailureResponse(request, exception.getErrors());
		} catch (Exception e) {
			LOG.error("Error in RestPort : checkRegistration method", e);
			response = createServiceFailureResponse(request, InternalRuleErrors.FAILED);
		}
		LOG.debug("RestPort: checkRegistration END");
		return Response.status(200).entity(response).build();

	}

	/**
	 * Show blacklist data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/showBlacklistData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response showBlacklistData(InternalServiceRequest request) {
		LOG.debug("RestPort : showBlacklistData method Start");
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			request.setRequestType(RequestTypes.SHOW_BLACKLIST_DATA + "");
			response = internalService.performCheck(request);
		} catch (InternalRuleException exception) {
			LOG.error(Constants.ERROR_IN_RESTPORT_MESSAGE, exception);
			response = createServiceFailureResponse(request, exception.getErrors());
		} catch (Exception e) {
			LOG.error("Error in RestPort : showBlacklistData method", e);
			response = createServiceFailureResponse(request, InternalRuleErrors.FAILED);
		}
		LOG.debug("RestPort : showBlacklistData method End");
		return Response.status(200).entity(response).build();
	}

	/**
	 * Save into blacklist data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/saveIntoBlacklistData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveIntoBlacklistData(InternalServiceRequest request) {
		LOG.debug("RestPort : saveIntoBlacklistData method Start");
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			request.setRequestType(RequestTypes.ADD_BLACKLIST_DATA + "");
			response = internalService.performCheck(request);
		} catch (InternalRuleException exception) {
			LOG.error(Constants.ERROR_IN_RESTPORT_MESSAGE, exception);
			response = createServiceFailureResponse(request, exception.getErrors());
		} catch (Exception e) {
			LOG.error("Error in RestPort : saveIntoBlacklistData method", e);
			response = createServiceFailureResponse(request, InternalRuleErrors.FAILED);

		}
		LOG.debug("RestPort : saveIntoBlacklistData method End");
		return Response.status(200).entity(response).build();
	}

	/**
	 * Delete from blacklist data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/deleteFromBlacklist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFromBlacklist(InternalServiceRequest request) {
		LOG.debug("RestPort : deleteFromBlacklist method Start");
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			request.setRequestType(RequestTypes.DELETE_BLACKLIST_DATA + "");
			response = internalService.performCheck(request);
		} catch (InternalRuleException exception) {
			LOG.error(Constants.ERROR_IN_RESTPORT_MESSAGE, exception);
			response = createServiceFailureResponse(request, exception.getErrors());
		} catch (Exception e) {
			LOG.error("Error in RestPort : deleteFromBlacklist method", e);
			response = createServiceFailureResponse(request, InternalRuleErrors.FAILED);

		}
		LOG.debug("RestPort : deleteFromBlacklist method End");
		return Response.status(200).entity(response).build();
	}

	/**
	 * Search into blacklist data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/searchBlacklistData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchBlacklistData(InternalServiceRequest request) {
		LOG.debug("RestPort : searchBlacklistData method Start");
		InternalServiceResponse response = new InternalServiceResponse();
		try {
			request.setRequestType(RequestTypes.SEARCH_BLACKLIST_DATA + "");
			response = internalService.performCheck(request);
		} catch (InternalRuleException exception) {
			LOG.error(Constants.ERROR_IN_RESTPORT_MESSAGE, exception);
			response = createServiceFailureResponse(request, exception.getErrors());
		} catch (Exception e) {
			LOG.error("Error in RestPort : searchBlacklistData method", e);
			response = createServiceFailureResponse(request, InternalRuleErrors.FAILED);

		}
		LOG.debug("RestPort : searchBlacklistData method End");
		return Response.status(200).entity(response).build();
	}

	/**
	 * Creates the service failure response.
	 *
	 * @param request
	 *            the request
	 * @return the internal service response
	 */
	private InternalServiceResponse createServiceFailureResponse(InternalServiceRequest request, Errors ex) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> contactResponseList = new ArrayList<>();
		for (InternalServiceRequestData contactRequest : request.getSearchdata()) {
			ContactResponse contactResponse = new ContactResponse();
			contactResponse.setId(contactRequest.getId());
			contactResponse.setEntityType(contactRequest.getEntityType());
			contactResponseList.add(contactResponse);
		}
		response.setStatus(Constants.SERVICE_FAILURE);
		response.setContacts(contactResponseList);
		response.setErrorCode(ex.getErrorCode());
		response.setErrorDescription(ex.getErrorDescription());
		return response;
	}
	
	
	/**
	 * Show WhitelistBeneficiary data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/showWhiteListBeneficiaryData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response showWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request) {
		LOG.debug("RestPort : showWhitelistBeneficiaryData method Start");
		List<WhitelistBeneficiaryResponse> response = null;
		try {
			response = whitelistBeneficiaryModifier.getWhitelistBeneficiaryData(request);
		} catch (Exception e) {
			LOG.error("Error in RestPort : showWhitelistBeneficiaryData method", e);
		}
		LOG.debug("RestPort : showWhitelistBeneficiaryData method End");
		return Response.status(200).entity(response).build();
	}
	
	
	/**
	 * Save into WhitelistBeneficiary data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/saveIntoWhitelistBeneficiaryData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveIntoWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request) {
		LOG.debug("RestPort : saveIntoWhiteBeneficiarylistData method Start");
		WhitelistBeneficiaryResponse response = new WhitelistBeneficiaryResponse();
		try {
			response = whitelistBeneficiaryModifier.saveIntoWhitelistBeneficiaryData(request);
		} catch (InternalRuleException exception) {
			LOG.error(Constants.ERROR_IN_RESTPORT_MESSAGE, exception);
			Errors ex = exception.getErrors();
			response.setStatus(Constants.SERVICE_FAILURE);
			response.setErrorCode(ex.getErrorCode());
			response.setErrorDescription(ex.getErrorDescription());
		} catch (Exception e) {
			LOG.error("Error in RestPort : saveIntoWhiteBeneficiarylistData method", e);
			response.setStatus(Constants.SERVICE_FAILURE);
		}
		LOG.debug("RestPort : saveIntoWhiteBeneficiarylistData method End");
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Delete from WhiteListBeneficiary data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/deleteFromWhiteListBeneficiary")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFromWhiteListBeneficiary(WhitelistBeneficiaryRequest request) {
		LOG.debug("RestPort : deleteFromWhiteListBeneficiary method Start");
		WhitelistBeneficiaryResponse response = new WhitelistBeneficiaryResponse();
		try {
			response = whitelistBeneficiaryModifier.deleteFromWhiteListBeneficiaryData(request);
		} catch (Exception e) {
			LOG.error("Error in RestPort : deleteFromWhiteListBeneficiary method", e);
			response.setStatus(Constants.SERVICE_FAILURE);
		}
		LOG.debug("RestPort : deleteFromWhiteListBeneficiary method End");
		return Response.status(200).entity(response).build();
	}
	
	/**
	 * Search into blacklist data.
	 *
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/searchWhiteListBeneficiaryData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchWhiteListBeneficiaryData(WhitelistBeneficiaryRequest request) {
		LOG.debug("RestPort : searchBlacklistData method Start");
		List<WhitelistBeneficiaryResponse> response = null;
		try {
			response = whitelistBeneficiaryModifier.searchWhiteListBeneficiaryData(request);
		} catch (Exception e) {
			LOG.error("Error in RestPort : searchBlacklistData method", e);
		}
		LOG.debug("RestPort : searchBlacklistData method End");
		return Response.status(200).entity(response).build();
	}


}