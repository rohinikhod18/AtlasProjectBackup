package com.currenciesdirect.gtg.compliance.authentication.restport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.currenciesdirect.gtg.compliance.authentication.core.AuthenticationServiceImpl;
import com.currenciesdirect.gtg.compliance.authentication.core.IAuthenticationService;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.AuthRequest;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.User;
import com.currenciesdirect.gtg.compliance.authentication.exception.AuthenticationException;


/**
 * @author manish
 *
 */
@Stateless
@Path("/authenticationService")
public class RestPort {
	
	/**
	 * The Constant Logger
	 */
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(RestPort.class);
	
	IAuthenticationService authenticationService = AuthenticationServiceImpl.getInstance();
	
	
	

/*	*//**
	 * @param headers
	 * @param authRequest
	 * @return
	 *//*
	@POST
	@Path("/validateuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response validateuser(@Context HttpHeaders headers,@QueryParam("userName") String userName, 
											@QueryParam("password") String password ) {
		// TODO Auto-generated method stub
		LOG.info("RestPort method Start");
		//AuthResponse response = new AuthResponse();
		List<String> list = new ArrayList<String>();
		try {
			if (headers != null) {
				for (String header : headers.getRequestHeaders().keySet()) 
				{
					LOG.info("Header:" + header + "Value:"
							+ headers.getRequestHeader(header));
					list.add(header);
				}
			}
			LOG.info(""+list);
			//	AuthRequest authRequest = new AuthRequest();
			   //user = userValidationIntegrationImpl.getUserDetails(userName,password);
					
						
					
			
			} catch (Exception e) {
				LOG.info("Error in RestPort : validateuser class", e);
			}finally{
				list=null;
			}
		LOG.info("UserValidationIntegration:UserValidationIntegration method END");
		return Response.status(200).entity("").build();
	}
*/
	
	@POST
	@Path("/authenticateUser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response authenticateUser(@Context HttpHeaders headers,AuthRequest authRequest) {
		// TODO Auto-generated method stub
		LOG.info("RestPort : authenticateUser() method  Start");
		//AuthResponse response = new AuthResponse();
		List<String> list = new ArrayList<String>();
		User user = new User();
		long time = System.currentTimeMillis();
		try {
			if (headers != null) {
				for (String header : headers.getRequestHeaders().keySet()) 
				{
					LOG.info("Header:" + header + "Value:"
							+ headers.getRequestHeader(header));
					list.add(header);
				}
			}
			LOG.info(""+list);
			//	AuthRequest authRequest = new AuthRequest();
			   user = authenticationService.getUser(authRequest);
					
						
					
			
			}catch (AuthenticationException e) {
				LOG.info("Error in RestPort: authenticateUser()", e);
				user.setErrorCode(e.getErrorname().getErrorCode());
				user.setErrorDescription(e.getErrorname().getErrorDescription());
			} catch (Exception e) {
				LOG.info("Error in RestPort : authenticateUser()", e);
				user.setErrorCode("0999");
				user.setErrorDescription("Generic Exception");
				
			}finally{
				list=null;
			}
		LOG.info(" RestPort : authenticateUser() method END");
		return Response.status(200).entity(user).header("requestReceived", time).build();
	}
	
}
