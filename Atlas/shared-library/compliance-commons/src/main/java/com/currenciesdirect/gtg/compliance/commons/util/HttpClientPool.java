package com.currenciesdirect.gtg.compliance.commons.util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.exception.ComplianceCommonsError;
import com.currenciesdirect.gtg.compliance.commons.exception.ComplianceCommonsException;

/**
 * The Class HttpClientPool.
 */
public final class HttpClientPool {

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	/** The Constant httpClientPool. */
	private static HttpClientPool httpClientPool = null;
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientPool.class);
	
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
			MultivaluedMap<String, Object> headers, MediaType mediaType) throws ComplianceCommonsException {
		Response response;
		T responseObject = null;
		ResteasyClient resteasyClient = resteasyClientHandler.getRetEasyClient();
		ResteasyWebTarget target = resteasyClient.target(url);
		Builder clientrequest = target.request();
		try {
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
				//Added for AT-4014
				case "PATCH":
					response = clientrequest.method("PATCH", Entity.entity(request, mediaType));
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
					throw new ComplianceCommonsException(ComplianceCommonsError.UNAUTHORISED_USER);
				case 403:
				case 500:
				default:	
					throw new ComplianceCommonsException(ComplianceCommonsError.FAILED);
			}
			response.close();
		}catch (Exception e) {
			String logData = "Exception in HttpClientPool Commons from sendRequest() for ";
			logData = logData.concat(url).concat(" Exception:").concat(e.toString());
			LOG.error(logData);
		}
		return responseObject;
	}
}