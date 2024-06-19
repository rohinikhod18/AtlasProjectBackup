package com.currenciesdirect.gtg.compliance.customerdatascan.restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.currenciesdirect.gtg.compliance.customerdatascan.core.CustomerDataModifierImpl;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.CustomerDataSearchImpl;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.ICustomerModifierService;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.ICustomerSearchService;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanDeleteRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanInsertResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanSearchResponse;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.CustomerDataScanUpdateRequest;
import com.currenciesdirect.gtg.compliance.customerdatascan.core.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanErrors;
import com.currenciesdirect.gtg.compliance.customerdatascan.exception.CustomerDataScanException;


/**
 * The Class CustomerDataScanRestPort.
 */
@Path("/customer")
public class CustomerDataScanRestPort {

	/** The icustomer service. */
	private static ICustomerModifierService icustomerService = CustomerDataModifierImpl
			.getInstance();
	
	/** The icustomer search service. */
	private static ICustomerSearchService icustomerSearchService = CustomerDataSearchImpl
			.getInstance();

	/**
	 * Save data.
	 *
	 * @param correlationId the correlation id
	 * @param userName the user name
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveData(
			@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName,
			CustomerDataScanInsertRequest request) {
		CustomerDataScanInsertResponse customerDataScanResponse = null;
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			request.setUserName(userName);
			request.setCorrelationId(correlationId);
			customerDataScanResponse = icustomerService.saveDocument(request);
			return Response.ok(customerDataScanResponse).build();
		} catch (CustomerDataScanException customerDataScanException) {
			customerDataScanResponse = new CustomerDataScanInsertResponse();
			customerDataScanResponse.setErrorCode(customerDataScanException
					.getCustomerDataScanErrors().getErrorCode());
			customerDataScanResponse.setErrorDescription(customerDataScanException
					.getCustomerDataScanErrors().getErrorDescription());
			customerDataScanResponse
					.setStatus(ResponseStatus.FAILED
							.getStatus());
			return Response.serverError().entity(customerDataScanResponse)
					.build();
		} catch (Exception exception) {
			customerDataScanResponse = new CustomerDataScanInsertResponse();
			customerDataScanResponse
					.setStatus(ResponseStatus.FAILED
							.getStatus());
			return Response.serverError().entity(customerDataScanResponse)
					.build();
		}
	}

	/**
	 * Search data.
	 *
	 * @param correlationId the correlation id
	 * @param userName the user name
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchData(
			@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName,
			CustomerDataScanSearchRequest request) {
		CustomerDataScanSearchResponse response = null;
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			request.setUserName(userName);
			request.setCorrelationId(correlationId);
			response = icustomerSearchService.searchDocument(request);
			return Response.ok(response).build();
		} catch (CustomerDataScanException customerDataScanException) {
			response = new CustomerDataScanSearchResponse();
			response.setErrorCode(customerDataScanException
					.getCustomerDataScanErrors().getErrorCode());
			response.setErrorDescription(customerDataScanException
					.getCustomerDataScanErrors().getErrorDescription());
			return Response.serverError().entity(response).build();
		} catch (Exception exception) {
			response = new CustomerDataScanSearchResponse();
			response.setErrorCode(CustomerDataScanErrors.FAILED.getErrorCode());
			response.setErrorDescription(CustomerDataScanErrors.FAILED
					.getErrorDescription());
			return Response.serverError().entity(response).build();
		}
	}

	/**
	 * Update data.
	 *
	 * @param correlationId the correlation id
	 * @param userName the user name
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateData(
			@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName,
			CustomerDataScanUpdateRequest request) {
		CustomerDataScanResponse customerDataScanResponse = new CustomerDataScanResponse();
		try {
			if (request == null) {
				return Response.status(Status.BAD_REQUEST).build();
			}
			request.setUserName(userName);
			request.setCorrelationId(correlationId);
			customerDataScanResponse = icustomerService.updateDocument(request);
			customerDataScanResponse
					.setStatus(ResponseStatus.SUCCESS
							.getStatus());
			return Response.ok(customerDataScanResponse).build();
		} catch (CustomerDataScanException customerDataScanException) {
			customerDataScanResponse = new CustomerDataScanResponse();
			customerDataScanResponse.setErrorCode(customerDataScanException
					.getCustomerDataScanErrors().getErrorCode());
			customerDataScanResponse
					.setErrorDescription(customerDataScanException
							.getCustomerDataScanErrors().getErrorDescription());
			customerDataScanResponse
					.setStatus(ResponseStatus.FAILED
							.getStatus());
			return Response.serverError().entity(customerDataScanResponse)
					.build();
		} catch (Exception exception) {
			customerDataScanResponse
					.setStatus(ResponseStatus.FAILED
							.getStatus());
			return Response.serverError().entity(customerDataScanResponse)
					.build();
		}
	}

	/**
	 * Delete data.
	 *
	 * @param correlationId the correlation id
	 * @param userName the user name
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteData(
			@HeaderParam("correlationId") String correlationId,
			@HeaderParam("username") String userName,
			CustomerDataScanDeleteRequest request) {
		CustomerDataScanResponse customerDataScanResponse = new CustomerDataScanResponse();
		try {
			request.setUserName(userName);
			request.setCorrelationId(correlationId);
			customerDataScanResponse = icustomerService.deleteDocument(request);
			customerDataScanResponse
					.setStatus(ResponseStatus.SUCCESS
							.getStatus());
			return Response.ok(customerDataScanResponse).build();
		} catch (CustomerDataScanException customerDataScanException) {
			customerDataScanResponse = new CustomerDataScanResponse();
			customerDataScanResponse.setErrorCode(customerDataScanException
					.getCustomerDataScanErrors().getErrorCode());
			customerDataScanResponse
					.setErrorDescription(customerDataScanException
							.getCustomerDataScanErrors().getErrorDescription());
			customerDataScanResponse
					.setStatus(ResponseStatus.FAILED
							.getStatus());
			return Response.serverError().entity(customerDataScanResponse)
					.build();
		} catch (Exception exception) {
			customerDataScanResponse
					.setStatus(ResponseStatus.FAILED
							.getStatus());
			return Response.serverError().entity(customerDataScanResponse)
					.build();
		}
	}
}
