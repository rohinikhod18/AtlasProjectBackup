package com.currenciesdirect.gtg.compliance.customchecks.restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CDINCFirstCreditCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.EuPoiCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.customchecks.core.CustomChecksSearchImpl;
import com.currenciesdirect.gtg.compliance.customchecks.core.ICustomChecksService;
import com.currenciesdirect.gtg.compliance.customchecks.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.SearchWhiteListRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.response.SearchWhiteListResponse;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class CustomChecksRestPort.
 */
@Path("/")
public class CustomChecksRestPort {

	/** The Constant ERROR. */
	private static final String ERROR = "Error:";

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CustomChecksRestPort.class);
	/** The icustomer search service. */
	private static ICustomChecksService icustomerSearchService = CustomChecksSearchImpl.getInstance();

	/**
	 * performCheck.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/performCheck")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response performCheck(@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName, CustomChecksRequest request) {
		CustomCheckResponse customChecksResponse = null;
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			customChecksResponse = icustomerSearchService.performCheck(request);
			return Response.ok(customChecksResponse).build();
		} catch (CustomChecksException customerDataScanException) {
			LOG.error(ERROR, customerDataScanException);
			customChecksResponse = new CustomCheckResponse();
			createServiceFailResponse(customChecksResponse, ResponseStatus.SERVICE_FAILURE.getStatus());
			return Response.ok().entity(customChecksResponse).build();
		} catch (Exception exception) {
			LOG.error(ERROR, exception);
			customChecksResponse = new CustomCheckResponse();
			createServiceFailResponse(customChecksResponse, ResponseStatus.SERVICE_FAILURE.getStatus());
			return Response.ok().entity(customChecksResponse).build();
		}
	}

	/**
	 * repeatCheck.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/repeatCheck")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchData(@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName, CustomChecksRequest request) {
		CustomCheckResponse response = null;
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			response = icustomerSearchService.repeatCheck(request);
			return Response.ok(response).build();
		} catch (CustomChecksException customerDataScanException) {
			LOG.error(ERROR, customerDataScanException);
			response = new CustomCheckResponse();
			createServiceFailResponse(response, ResponseStatus.SERVICE_FAILURE.getStatus());
			return Response.ok().entity(response).build();
		} catch (Exception exception) {
			LOG.error(ERROR, exception);
			response = new CustomCheckResponse();
			createServiceFailResponse(response, ResponseStatus.SERVICE_FAILURE.getStatus());
			return Response.ok().entity(response).build();
		}
	}

	/**
	 * Update white list data.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/updateWhiteListData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateWhiteListData(@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName, AccountWhiteList request) {
		CustomCheckResponse response = null;
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			response = icustomerSearchService.updateWhiteListData(request);
			return Response.ok(response).build();
		} catch (CustomChecksException customerDataScanException) {
			LOG.error(ERROR, customerDataScanException);
			response = new CustomCheckResponse();
			response.setErrorCode(customerDataScanException.getCustomerDataScanErrors().getErrorCode());
			response.setErrorDescription(customerDataScanException.getCustomerDataScanErrors().getErrorDescription());
			return Response.ok().entity(response).build();
		} catch (Exception exception) {
			LOG.error(ERROR, exception);
			response = new CustomCheckResponse();
			response.setErrorCode(CustomChecksErrors.FAILED.getErrorCode());
			response.setErrorDescription(CustomChecksErrors.FAILED.getErrorDescription());
			return Response.ok().entity(response).build();
		}
	}

	/**
	 * Search white list data.
	 *
	 * @param correlationId
	 *            the correlation id
	 * @param userName
	 *            the user name
	 * @param request
	 *            the request
	 * @return the response
	 */
	@POST
	@Path("/searchWhiteListData")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchWhiteListData(@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName, SearchWhiteListRequest request) {
		SearchWhiteListResponse response = null;
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			response = icustomerSearchService.searchAccountWhilteList(request);
			return Response.ok(response).build();
		} catch (Exception exception) {
			LOG.error(ERROR, exception);
			response = new SearchWhiteListResponse();
			response.setErrorCode(CustomChecksErrors.FAILED.getErrorCode());
			response.setErrorDescription(CustomChecksErrors.FAILED.getErrorDescription());
			return Response.ok().entity(response).build();
		}
	}

	/**
	 * Creates the service fail response.
	 *
	 * @param customChecksResponse
	 *            the custom checks response
	 * @param status
	 *            the status
	 * @return the custom check response
	 */
	private CustomCheckResponse createServiceFailResponse(CustomCheckResponse customChecksResponse, String status) {
		customChecksResponse.setOverallStatus(status);
		VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
		WhiteListCheckResponse whiteListCheckResponse = new WhiteListCheckResponse();
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();//Add for AT-3349
		CDINCFirstCreditCheckResponse cdincFirstCreditCheckResponse = new CDINCFirstCreditCheckResponse();//Add for AT-3738
		
		velocityCheckResponse.setStatus(status);
		velocityCheckResponse.setBeneCheck(status);
		velocityCheckResponse.setNoOffundsoutTxn(status);
		velocityCheckResponse.setPermittedAmoutcheck(status);

		whiteListCheckResponse.setStatus(status);
		whiteListCheckResponse.setAmoutRange(status);
		whiteListCheckResponse.setCurrency(status);
		whiteListCheckResponse.setReasonOfTransfer(status);
		whiteListCheckResponse.setThirdParty(status);
		
		euPoiCheckResponse.setStatus(status);
		cdincFirstCreditCheckResponse.setStatus(status);

		customChecksResponse.setVelocityCheck(velocityCheckResponse);
		customChecksResponse.setWhiteListCheck(whiteListCheckResponse);
		customChecksResponse.setEuPoiCheck(euPoiCheckResponse);
		customChecksResponse.setFraudPredictStatus(status);//Add for AT-3161
		customChecksResponse.setCdincFirstCreditCheck(cdincFirstCreditCheckResponse);
		customChecksResponse.setOverallStatus(status);
		return customChecksResponse;
	}

	/**
	 * Gets the beneficiary details.
	 *
	 * @param beneBankAccNumber
	 *            the bene bank acc number
	 * @return the beneficiary details
	 * @throws CustomChecksException
	 *             the custom checks exception
	 */
	@POST
	@Path("/getBeneficiaryDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public PayeeESResponse getBeneficiaryDetails(String beneBankAccNumber) throws CustomChecksException {
		PayeeESResponse response = new PayeeESResponse();
		try {
			response = icustomerSearchService.getBeneficiaryDetails(beneBankAccNumber);

		} catch (CustomChecksException customerDataScanException) {
			LOG.error(ERROR, customerDataScanException);
			response = new PayeeESResponse();
			response.setErrorCode(customerDataScanException.getCustomerDataScanErrors().getErrorCode());
			response.setErrorDescription(customerDataScanException.getCustomerDataScanErrors().getErrorDescription());
		} catch (Exception exception) {
			LOG.error(ERROR, exception);
			response = new PayeeESResponse();
			response.setErrorCode(CustomChecksErrors.FAILED.getErrorCode());
			response.setErrorDescription(CustomChecksErrors.FAILED.getErrorDescription());
		}
		return response;
	}

}
