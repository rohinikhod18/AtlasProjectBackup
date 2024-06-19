package com.currenciesdirect.gtg.compliance.kyc.util;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.springframework.stereotype.Component;

/**
 * The Class RestRequestHandler.
 *
 * @author bnt
 */
@Component
public class RestRequestHandler {

	/** The rest easy client. */
	@SuppressWarnings("squid:S3077")
	private static volatile RestRequestHandler restEasyClient = null;

	/** The client. */
	private ResteasyClient client = null;

	/** The http client. */
	private HttpClient httpClient = null;

	/** The cm. */
	private ClientConnectionManager cm = null;

	/**
	 * Instantiates a new rest request handler.
	 */
	private RestRequestHandler() {
		cm = new PoolingClientConnectionManager();

		httpClient = new DefaultHttpClient(cm);
		httpClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, Boolean.TRUE);
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);

		client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(10).httpEngine(engine).build();
	}

	/**
	 * Gets the single instance of RestRequestHandler.
	 *
	 * @return single instance of RestRequestHandler
	 */
	public static RestRequestHandler getInstance() {
		if (restEasyClient == null) {
			synchronized (RestRequestHandler.class) {
				if (restEasyClient == null) {
					restEasyClient = new RestRequestHandler();
				}
			}
		}
		return restEasyClient;
	}

	/**
	 * Gets the ret easy client.
	 *
	 * @return the ret easy client
	 */
	public ResteasyClient getRetEasyClient() {
		return client;
	}

	/**
	 * Clean up.
	 */
	public void cleanUp() {
		cm.closeExpiredConnections();
		httpClient.getConnectionManager().shutdown();

	}

	/**
	 * Gets the http client by route.
	 *
	 * @return the http client by route
	 */
	public HttpClient getHttpClientByRoute() {
		return httpClient;
	}

	/**
	 * Intialize.
	 */
	public void intialize() {
		// default implementation ignored
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
	 * @return the t
	 */
	public <T> T sendRequest(String url, String method, Object request, Class<T> clazz) {
		Response response;
		T responseObject;
		ResteasyClient resteasyClient = getRetEasyClient();
		ResteasyWebTarget target = resteasyClient.target(url);
		Builder clientrequest = target.request();
		switch (method) {
		case Constants.KYC_METHOD_POST:
			response = clientrequest.post(Entity.entity(request, MediaType.APPLICATION_JSON));
			break;
		case Constants.KYC_METHOD_PUT:
			response = clientrequest.put(Entity.entity(request, MediaType.APPLICATION_JSON));
			break;
		default:
			response = clientrequest.get();
		}
		responseObject = response.readEntity(clazz);
		response.close();

		return responseObject;
	}
}