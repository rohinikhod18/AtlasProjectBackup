/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.util;


import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;

/**
 * The Class ResteasyClientHandler.
 *
 * @author manish
 */
public class ResteasyClientHandler {
	
		
	/** The resteasy client builder. */
	@SuppressWarnings("squid:S3077")
	private static volatile ResteasyClientHandler resteasyClientBuilder = null;

	/** The client. */
	private ResteasyClient client = null;

	/**
	 * Instantiates a new resteasy client handler.
	 */
	private ResteasyClientHandler() {
		
		/** The httpclient. */
		HttpClient httpclient= null;

		/** The engine. */
		ApacheHttpClient4Engine engine = null;
		
		ClientConnectionManager cm = new PoolingClientConnectionManager();
		httpclient = new DefaultHttpClient(cm);
		httpclient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, Boolean.TRUE);
		httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.parseInt(System.getProperty("resteasy.connection.timeout")));
		httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,Integer.parseInt(System.getProperty("resteasy.socket.timeout")));
		engine = new ApacheHttpClient4Engine(httpclient,true);
		
		client = new ResteasyClientBuilder().httpEngine(engine).build();
		
	}

	/**
	 * Gets the single instance of ResteasyClientHandler.
	 *
	 * @return single instance of ResteasyClientHandler
	 */
	public static ResteasyClientHandler getInstance() {
		synchronized (ResteasyClientHandler.class) {
			if (resteasyClientBuilder == null) {
				resteasyClientBuilder = new ResteasyClientHandler();
			}
		}
		return resteasyClientBuilder;
	}

	/**
	 * Gets the ret easy client.
	 *
	 * @return the ret easy client
	 */
	public ResteasyClient getRetEasyClient() {
		return client;
	}

}
