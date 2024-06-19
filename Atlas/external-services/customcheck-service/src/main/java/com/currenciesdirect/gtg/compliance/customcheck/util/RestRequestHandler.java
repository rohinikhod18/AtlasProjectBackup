package com.currenciesdirect.gtg.compliance.customcheck.util;

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

public class RestRequestHandler {

	private static volatile RestRequestHandler restEasyClient = null;
	private ResteasyClient client = null;
	private HttpClient httpClient = null;
	private ClientConnectionManager cm = null;

	private RestRequestHandler() {
		ClientConnectionManager cm = new PoolingClientConnectionManager();

		httpClient = new DefaultHttpClient(cm);
		httpClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, true);
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 6000);
		ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);

		client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(10).httpEngine(engine).build();
	}

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

	public ResteasyClient getRetEasyClient() {
		return client;
	}

	public void finalize() {
		cm.closeExpiredConnections();
		httpClient.getConnectionManager().shutdown();

	}

	public HttpClient getHttpClientByRoute(String route) {
		return httpClient;
	}

	public void intialize() {

	}

	public <T> T sendRequest(String url, String method, Object request, Class<T> clazz) {
		Response response = null;
		T responseObject = null;
		ResteasyClient resteasyClient = getRetEasyClient();
		ResteasyWebTarget target = resteasyClient.target(url);
		Builder clientrequest = target.request();
		switch (method) {
		case Constants.METHOD_POST:
			response = clientrequest.post(Entity.entity(request, MediaType.APPLICATION_JSON));
			break;
		case Constants.METHOD_PUT:
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