package com.currenciesdirect.gtg.compliance.util;



import java.util.concurrent.Future;

import javax.ws.rs.client.AsyncInvoker;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.RecheckServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;


/**
 * The Class HttpClientPool.
 */
public final class HttpClientPool {

	private Logger log = LoggerFactory.getLogger(HttpClientPool.class);
	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	/** The Constant httpClientPool. */
	private static HttpClientPool httpClientPool = null;
	
	/**
	 * Gets the single instance of ResteasyClientHandler.
	 *
	 * @return single instance of ResteasyClientHandler
	 */
	public static HttpClientPool getInstance() {
		synchronized (HttpClientPool.class) {
			if (httpClientPool == null) {
				httpClientPool = new HttpClientPool();
			}
		}
		return httpClientPool;
	}
	/**
	 * Send request.
	 *
	 * @param <T>
	 *            the generic type
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param request
	 *            the request
	 * @param clazz
	 *            the clazz
	 * @param headers
	 *            the headers
	 * @param mediaType
	 *            the media type
	 * @return the t
	 * @throws CompliancePortalException 
	 */
	public <T> T sendRequest(String url, String method, Object request, Class<T> clazz,
			MultivaluedMap<String, Object> headers, MediaType mediaType) throws CompliancePortalException {
		Response response;
		T responseObject = null;
		ResteasyClient resteasyClient = resteasyClientHandler.getRetEasyClient();
		ResteasyWebTarget target = resteasyClient.target(url);
		Builder clientrequest = target.request();
		if (headers != null && !headers.isEmpty()) {
			clientrequest.headers(headers);
		}
		switch (method) {
		case "POST":
			response = clientrequest.post(Entity.entity(request, mediaType));
			break;
		case "PUT":
			response = clientrequest.put(Entity.entity(request, mediaType));
			break;
		default:
			response = clientrequest.get();
		}

		switch(response.getStatus()){
		case 200:
			if(response.hasEntity()) {
				responseObject = response.readEntity(clazz);
			}
			break;
		case 401:
			throw new CompliancePortalException(CompliancePortalErrors.UNAUTHORISED_USER);
			
		case 403:
		case 500:
		default:	
			throw new CompliancePortalException(CompliancePortalErrors.FAILED);
			
		}
		
		
		response.close();

		return responseObject;
	}
	/**
	 * Send Async request.
	 *
	 * @param <T>
	 *            the generic type
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param request
	 *            the request
	 * @param clazz
	 *            the clazz
	 * @param headers
	 *            the headers
	 * @param mediaType
	 *            the media type
	 * @return the t
	 * @throws CompliancePortalException 
	 */
	public  Future<Response> sendAsyncRequest(String url, String method, Object request, 
			MultivaluedMap<String, Object> headers, MediaType mediaType) throws CompliancePortalException {
		Future<Response> response;
		
		try {
			ResteasyClient resteasyClient = resteasyClientHandler.getRetEasyClient();
			ResteasyWebTarget target = resteasyClient.target(url);
			Builder clientrequest = target.request();
			if (headers != null && !headers.isEmpty()) {
				clientrequest.headers(headers);
			}
			switch (method) {
			case "POST":
				response = clientrequest.async().post(Entity.entity(request, mediaType));
				break;
			case "PUT":
				response = clientrequest.async().put(Entity.entity(request, mediaType));
				break;
			default:
				response = clientrequest.async().get();
			}
			return response;
		}catch(Exception e) {
			log.error("Async Request Failed with "+e.getMessage());
			throw new CompliancePortalException(CompliancePortalErrors.FAILED);
		}

	}

}