/**
 * 
 */
package com.currenciesdirect.gtg.compliance.ipport;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpDetailsRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpDetailsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.core.ip.IpProviderService;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;
import com.currenciesdirect.gtg.compliance.util.Constants;
import com.currenciesdirect.gtg.compliance.util.ExternalErrorCodeEnum;


/**
 * The Class IpPort.
 *
 * @author manish
 */
public class IpPort implements IpProviderService {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(IpPort.class);
	
	/** The ip provider service. */
	private static IpProviderService ipProviderService = null;
	
	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();
	
	/**
	 * Instantiates a new ip port.
	 */
	private IpPort(){
		
	}
	
	/**
	 * Gets the single instance of IpPort.
	 *
	 * @return single instance of IpPort
	 */
	public static IpProviderService getInstance(){
		if(ipProviderService==null){
			ipProviderService = new IpPort();
		}
		return ipProviderService;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ip.IpProviderService#checkIpPostCodeDistance(com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest, com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty)
	 */
	@Override
	public IpContactResponse checkIpPostCodeDistance(IpServiceRequest ipRequest, IpProviderProperty ipProviderProperty)
			throws IpException {
		Response response = null;
		ResteasyClient client = null;
		IpServiceResponse ipServiceResponse = new IpServiceResponse();
		IpContactResponse contactResponse = new IpContactResponse();
		try {
			client = resteasyClientHandler.getPostCodeRestEasyClient();
			
			ResteasyWebTarget target1 = client.target(ipProviderProperty.getEndPointUrl());		
			Builder clientrequest = target1.request();		
			
			response = clientrequest.post(Entity.entity(ipRequest, MediaType.APPLICATION_JSON));
			ipServiceResponse = response.readEntity(IpServiceResponse.class);		
			
			contactResponse = transformProviderResponse(ipServiceResponse, ipRequest);
		} catch(IpException e){
			throw e;
		}catch (Exception e) {
			throw new IpException(IpErrors.ERROR_WHILE_CALLING_IP_PROVIDER, e);
		} finally {
			if(response != null){
				response.close();
			}
		}
		return contactResponse;
	}
	
	/**
	 * Transform provider response.
	 *
	 * @param ipServiceResponse the ip service response
	 * @param ipServiceRequest the ip service request
	 * @return the ip contact response
	 * @throws IpException the ip exception
	 */
	private IpContactResponse transformProviderResponse(IpServiceResponse ipServiceResponse,
			IpServiceRequest ipServiceRequest) throws IpException {
		IpContactResponse contactResponse = new IpContactResponse();
		try {

			contactResponse.setErrorCode(ipServiceResponse.getErrorCode());
			contactResponse.setErrorDescription(ipServiceResponse.getErrorDescription());
			contactResponse.setGeoDifference(ipServiceResponse.getDistance());
			contactResponse.setUnit(ipServiceResponse.getUnits());
			contactResponse.setRegCountry(ipServiceRequest.getCountry());
			contactResponse.setIpCountry(ipServiceResponse.getIpCountry());
			contactResponse.setIpRountingType(ipServiceResponse.getIpRountingType());
			contactResponse.setIpLatitude(ipServiceResponse.getIpLatitude());
			contactResponse.setIpLongitude(ipServiceResponse.getIpLongitude());
			contactResponse.setPostCodeLatitude(ipServiceResponse.getPostCodeLatitude());
			contactResponse.setPostCodeLongitude(ipServiceResponse.getPostCodeLongitude());
			contactResponse.setPostcode(ipServiceRequest.getPostalCode());
			contactResponse.setIpAddress(ipServiceRequest.getIpAddress());
			contactResponse.setAnonymizerStatus(ipServiceResponse.getAnonymizerStatus());
		} catch (Exception e) {
			throw new IpException(IpErrors.ERROR_WHILE_TRANSFORMING_IP_PROVIDER_RESPONSE);
		}
		return contactResponse;
	}

	/**
	 * Transform provider response.
	 *
	 * @param ipServiceResponse the ip service response
	 * @param ipServiceRequest the ip service request
	 * @return the ip contact response
	 * @throws IpException the ip exception
	 */
	private IpContactResponse transformIpDetialsFromProviderResponse(IpDetailsResponse detailsResponse,
			IpServiceRequest ipServiceRequest) throws IpException {
		IpContactResponse contactResponse = new IpContactResponse();
		try {

			contactResponse.setErrorCode(detailsResponse.getErrorCode());
			contactResponse.setErrorDescription(detailsResponse.getErrorDescription());
			contactResponse.setIpLatitude(detailsResponse.getLatitiude());
			contactResponse.setIpLongitude(detailsResponse.getLongitude());
			contactResponse.setPostcode(ipServiceRequest.getPostalCode());
			contactResponse.setIpAddress(ipServiceRequest.getIpAddress());
			if (detailsResponse.getErrorCode() != null && ExternalErrorCodeEnum.IP_SERVICE_GENERIC_EXCEPTION.toString()
					.equals(detailsResponse.getErrorCode())) {
				contactResponse.setStatus(Constants.SERVICE_FAILURE);
			} else {
				contactResponse.setStatus(Constants.NOT_REQUIRED);
			}
			contactResponse.setAnonymizerStatus(detailsResponse.getAnonymizerStatus());
		} catch (Exception e) {
			contactResponse.setStatus(Constants.NOT_PERFORMED);
			LOG.error("Error in IpPort:checkIpPostCodeDistance()", e);
			throw new IpException(IpErrors.ERROR_WHILE_TRANSFORMING_IP_PROVIDER_RESPONSE);
		}
		return contactResponse;
	}
	@Override
	public IpContactResponse getIpDetails(IpServiceRequest ipRequest, IpProviderProperty ipProviderProperty)
			throws IpException {
		Response response = null;
		ResteasyClient client = null;
		IpDetailsRequest detailsRequest = new IpDetailsRequest();
		IpDetailsResponse detailsResponse = new IpDetailsResponse();
		IpContactResponse contactResponse = new IpContactResponse();
		try {
			detailsRequest.setIpAddress(ipRequest.getIpAddress());
			
			client = resteasyClientHandler.getIpRestEasyClient();
			ResteasyWebTarget target1 = client.target(ipProviderProperty.getEndPointUrl());
			
			Builder clientrequest = target1.request();
			response = clientrequest.post(Entity.entity(detailsRequest, MediaType.APPLICATION_JSON));
			
			detailsResponse = response.readEntity(IpDetailsResponse.class);		
			
			contactResponse = transformIpDetialsFromProviderResponse(detailsResponse, ipRequest);
		} catch(IpException e){
			throw e;
		}catch (Exception e) {
			contactResponse.setStatus(Constants.SERVICE_FAILURE);
			throw new IpException(IpErrors.ERROR_WHILE_CALLING_IP_PROVIDER, e);
		} finally {
			if(response != null){
				response.close();
			}
		}
		return contactResponse;
	}

}
