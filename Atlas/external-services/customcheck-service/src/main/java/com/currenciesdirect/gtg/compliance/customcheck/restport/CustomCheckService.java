package com.currenciesdirect.gtg.compliance.customcheck.restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customcheck.core.CustomCheckCacheDataBuilder;
import com.currenciesdirect.gtg.compliance.customcheck.core.CustomCheckServiceImpl;
import com.currenciesdirect.gtg.compliance.customcheck.core.ICustomCheckService;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteResponse;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertResponse;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckSearchResponse;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateResponse;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckException;



/**
 * The Class CustomCheckService
 * 
 * @author abhijitg
 */

@Path("/")
public class CustomCheckService {

	static final Logger LOG = LoggerFactory.getLogger(CustomCheckService.class);
	private ICustomCheckService customCheckInterface = CustomCheckServiceImpl.getInstance();
	 CustomCheckCacheDataBuilder customCheckCacheDataBuilder=CustomCheckCacheDataBuilder.getInstance();
	
	
	/**
	 * Save custom check data.
	 *
	 * @param correlationId the correlation id
	 * @param request the request
	 * @return the response
	 * @throws InterruptedException the interrupted exception
	 */
	@POST
	@Path("/insert")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response saveCustomCheckData(@HeaderParam("correlationId") String correlationId,CustomCheckInsertRequest request) throws InterruptedException {
		LOG.info("CustomCheckService start: saveCustomCheckData()");
		CustomCheckInsertResponse customCheckInsertResponse = new CustomCheckInsertResponse();
		try {
			request.setCorrelationId(correlationId);
			request.setCreatedOn(new java.sql.Timestamp(System.currentTimeMillis()));
			request.setUpdatedOn(new java.sql.Timestamp(System.currentTimeMillis()));
			customCheckInterface.saveCustomCheckDetails(request);
			customCheckInsertResponse.setStatus(CustomCheckException.SUCCESSMESSAGE);
		} catch (CustomCheckException customCheckException) {
			customCheckInsertResponse.setErrorDescription(customCheckException.getDescription());
			customCheckInsertResponse.setErrorCode("0999");
			customCheckInsertResponse.setStatus(CustomCheckException.ERRORMESSAGE);
			LOG.error("Error while Inserting Data ",customCheckException);
			return Response.serverError().entity(customCheckInsertResponse).build();
		} 
		return Response.status(200).entity(customCheckInsertResponse).build();
	}

	/**
	 * Update custom check data.
	 *
	 * @param correlationId the correlation id
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCustomCheckData(@HeaderParam("correlationId") String correlationId ,CustomCheckUpdateRequest request) {
		LOG.info("CustomCheckService start: updateCustomCheckData()");
		CustomCheckUpdateResponse customCheckUpdateResponse=new CustomCheckUpdateResponse();
		try {
			request.setCorrelationId(correlationId);
			request.setCreatedOn(new java.sql.Timestamp(System.currentTimeMillis()));
			request.setUpdatedOn(new java.sql.Timestamp(System.currentTimeMillis()));
			customCheckUpdateResponse = customCheckInterface.updateCustomCheckDetails(request);
			customCheckUpdateResponse.setStatus(CustomCheckException.SUCCESSMESSAGE);
		} catch (CustomCheckException customCheckException) {
			customCheckUpdateResponse.setErrorDescription(customCheckException.getDescription());
			customCheckUpdateResponse.setErrorCode("0999");
			customCheckUpdateResponse.setStatus(CustomCheckException.ERRORMESSAGE);
			LOG.error("Error while Updating Data ",customCheckException);
			return Response.serverError().entity(customCheckUpdateResponse).build();
		}
		return Response.status(200).entity(customCheckUpdateResponse).build();
	}

	/**
	 * Delete custom check data.
	 *
	 * @param correlationId the correlation id
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/delete")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCustomCheckData(@HeaderParam("correlationId") String correlationId, CustomCheckDeleteRequest request) {
		LOG.info("CustomCheckService start: deleteCustomCheckData()");
		CustomCheckDeleteResponse customCheckDeleteResponse = new CustomCheckDeleteResponse();
		try {
			request.setCorrelationId(correlationId);
			request.setCreatedOn(new java.sql.Timestamp(System.currentTimeMillis()));
			request.setUpdatedOn(new java.sql.Timestamp(System.currentTimeMillis()));
			customCheckInterface.deleteCustomCheckDetails(request);
			customCheckDeleteResponse.setStatus(CustomCheckException.SUCCESSMESSAGE);
		} catch (CustomCheckException customCheckException) {
			customCheckDeleteResponse.setErrorDescription(customCheckException.getDescription());
			customCheckDeleteResponse.setErrorCode("0999");
			customCheckDeleteResponse.setStatus(CustomCheckException.ERRORMESSAGE);
			LOG.error("Error while Deleting Data ",customCheckException);
			return Response.serverError().entity(customCheckDeleteResponse).build();
		} 
		return Response.status(200).entity(customCheckDeleteResponse).build();
	}

	/**
	 * Search custom check data.
	 *
	 * @param correlationId the correlation id
	 * @param request the request
	 * @return the response
	 */
	@POST
	@Path("/search")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response searchCustomCheckData(@HeaderParam ("correlationId") String correlationId,CustomCheckSearchRequest request) {
		CustomCheckSearchResponse searchResponse = new CustomCheckSearchResponse();
		
		LOG.info("CustomCheckService start: searchCustomCheckData()");
		try {
			request.setCorrelationId(correlationId);
			searchResponse = customCheckInterface.searchCustomCheckDetails(request);
			searchResponse.setStatus(CustomCheckException.SUCCESSMESSAGE);
		} catch (CustomCheckException customCheckException) {
			searchResponse.setErrorDescription(customCheckException.getDescription());
			searchResponse.setStatus(CustomCheckException.ERRORMESSAGE);
			searchResponse.setErrorCode("0999");
			LOG.error("Error while searching Data ",customCheckException);
			return Response.serverError().entity(searchResponse).build();
		}
		return Response.status(200).entity(searchResponse).build();
	}

}
