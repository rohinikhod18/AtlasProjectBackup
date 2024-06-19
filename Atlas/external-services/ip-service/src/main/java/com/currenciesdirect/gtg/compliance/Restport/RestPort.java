/**
 * 
 */
package com.currenciesdirect.gtg.compliance.Restport;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.currenciesdirect.gtg.compliance.core.IpService;
import com.currenciesdirect.gtg.compliance.core.IpServiceImpl;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceResponse;
import com.currenciesdirect.gtg.compliance.exception.IpException;


/**
 * @author manish
 *
 */
@Path("/")
public class RestPort {
	
	IpService ipService = IpServiceImpl.getInstance();

	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(RestPort.class);
	
	/**
	 * 1)Rest Service "Check ip Details"
	 * 2)This Service will request to third party provider for getting ip details.
	 * 3)And will return response accordingly.
	 * @param headers
	 * @param request
	 * @return
	 */
	@POST
	@Path("/findIpPostCodeDiff")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response findIpPostCodeDiff(@Context HttpHeaders headers,IpServiceRequest request) {
		// TODO Auto-generated method stub
		LOG.info("RestPort : findIpPostCodeDiff method Start");
		IpServiceResponse response = new IpServiceResponse();
		try {
		                
			response = ipService.checkIpPostCodeDistance(request);
		}catch(IpException exception) {
			response.setResponseStatus("Failure");
			response.setErrorCode(exception.getIpErrors().getErrorCode());
			response.setErrorDescription(exception.getIpErrors().getErrorDescription());
		} catch (Exception e) {
			response.setResponseStatus("Failure");
			response.setErrorCode("0999");
			response.setErrorDescription("GenericExecption");
			LOG.error("Error in RestPort : findIpPostCodeDiff method");
		}
		LOG.info("RestPort: findIpPostCodeDiff END");
		return Response.status(200).entity(response).build();
	
}

}