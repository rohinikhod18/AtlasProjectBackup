/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.transactionmonitoringport;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.CoreConnectionPNames;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Class ResteasyClientHandler.
 *
 * @author manish
 */
@SuppressWarnings("deprecation")
public class ResteasyClientHandler {

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ResteasyClientHandler.class);

	/** The resteasy client builder. */
	@SuppressWarnings("squid:S3077")
	private static volatile ResteasyClientHandler fClientBuilder = null;

	/** The client. */
	private ResteasyClient fraugsterClient = null;

	/** The stale monitor. */
	private static TMIdleConnectionMonitorThread fStaleMonitor = null;

	/**
	 * Instantiates a new resteasy client handler.
	 */
	private ResteasyClientHandler() {
		
		/** The httpclient. */
		CloseableHttpClient threadSafeFraugsterClient = null;

		/** The engine. */
		ApacheHttpClient4Engine engine = null;
		
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		// Increase max total connection to 10
		cm.setMaxTotal(10);
		// Increase default max connection per route to 5
		cm.setDefaultMaxPerRoute(5);
		threadSafeFraugsterClient = new DefaultHttpClient(cm);
		threadSafeFraugsterClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, Boolean.TRUE);
		threadSafeFraugsterClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,
				Integer.parseInt(System.getProperty("resteasy.connection.timeout")));
		threadSafeFraugsterClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				Integer.parseInt(System.getProperty("resteasy.socket.timeout")));
		engine = new ApacheHttpClient4Engine(threadSafeFraugsterClient, true);
		fraugsterClient = new ResteasyClientBuilder().httpEngine(engine).build();

		fStaleMonitor = new TMIdleConnectionMonitorThread(cm);

	}

	/**
	 * Gets the single instance of ResteasyClientHandler.
	 *
	 * @return single instance of ResteasyClientHandler
	 */
	@SuppressWarnings("squid:S2142")
	public static ResteasyClientHandler getInstance() {
		synchronized (ResteasyClientHandler.class) {
			if (fClientBuilder == null) {
				fClientBuilder = new ResteasyClientHandler();
				fStaleMonitor.start();
				try {
					fStaleMonitor.join(1000);
				} catch (InterruptedException e) {
					LOGGER.error("ResteasyClientHandler", e);
				}
			}
		}
		return fClientBuilder;
	}

	/**
	 * Gets the ret easy client.
	 *
	 * @return the ret easy client
	 */
	public ResteasyClient getRetEasyClient() {
		return fraugsterClient;
	}

}

class TMIdleConnectionMonitorThread extends Thread {
	private static final Logger LOG = LoggerFactory.getLogger(TMIdleConnectionMonitorThread.class);
	private final PoolingClientConnectionManager fConnMgr;
	private volatile boolean fraugsterShutdown;

	public TMIdleConnectionMonitorThread(PoolingClientConnectionManager connMgr) {
		super();
		this.fConnMgr = connMgr;
	}

	@Override
	@SuppressWarnings("squid:S2142")
	public void run() {
		try {
			while (!fraugsterShutdown) {
				synchronized (this) {
					wait(1000);
					fConnMgr.closeExpiredConnections();
					fConnMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException ex) {
			LOG.debug("IdleConnectionMonitorThread ", ex);
			shutdown();
		}
	}

	public void shutdown() {
		fraugsterShutdown = true;
		synchronized (this) {
			notifyAll();
		}
	}
}
