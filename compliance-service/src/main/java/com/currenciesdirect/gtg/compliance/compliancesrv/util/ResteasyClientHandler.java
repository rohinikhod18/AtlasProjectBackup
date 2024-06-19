/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.util;


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
	private static final Logger LOG = LoggerFactory.getLogger(ResteasyClientHandler.class);	
		
	/** The resteasy client builder. */
	@SuppressWarnings("squid:S3077")
	private static volatile ResteasyClientHandler resteasyClientBuilder = null;

	/** The client. */
	private ResteasyClient client = null;

	/** The stale monitor. */
	private IdleConnectionMonitorThread staleMonitor = null;

	/**
	 * Instantiates a new resteasy client handler.
	 */
	private ResteasyClientHandler() {
		
		/** The httpclient. */
		CloseableHttpClient threadSafeClient= null;

		/** The engine. */
		ApacheHttpClient4Engine engine = null;
		
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		// Increase max total connection to 100
		cm.setMaxTotal(100);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		threadSafeClient = new DefaultHttpClient(cm);
		threadSafeClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, Boolean.TRUE);
		threadSafeClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, Integer.parseInt(System.getProperty("resteasy.connection.timeout")));
		threadSafeClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,Integer.parseInt(System.getProperty("resteasy.socket.timeout")));
		engine = new ApacheHttpClient4Engine(threadSafeClient,true);		
		client = new ResteasyClientBuilder().httpEngine(engine).build();
		
		staleMonitor	 = new IdleConnectionMonitorThread(cm);
	}

	/**
	 * Gets the single instance of ResteasyClientHandler.
	 *
	 * @return single instance of ResteasyClientHandler
	 */
	@SuppressWarnings("squid:S2142")
	public static ResteasyClientHandler getInstance() {
		synchronized (ResteasyClientHandler.class) {
			if (resteasyClientBuilder == null) {
				resteasyClientBuilder = new ResteasyClientHandler();
				resteasyClientBuilder.staleMonitor.start();
				try {
					resteasyClientBuilder.staleMonitor.join(1000);
				} catch (InterruptedException e) {
					LOG.error("ResteasyClientHandler",e);
				}
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
	
	public void getInMemoryClient(){
		
		// Method should not be empty..
		
	}

}
 class IdleConnectionMonitorThread extends Thread {
	private static final Logger LOG = LoggerFactory.getLogger(IdleConnectionMonitorThread.class);
    private final PoolingClientConnectionManager connMgr;
    private volatile boolean shutdown;
 
    public IdleConnectionMonitorThread( PoolingClientConnectionManager connMgr) {
        super();
        this.connMgr = connMgr;
        setDaemon(true);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Thread#run()
     */
    @Override
    @SuppressWarnings("squid:S2142")
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                    connMgr.closeExpiredConnections();
                    connMgr.closeIdleConnections(60, TimeUnit.SECONDS);
                    wait(5000);
                }
            }
        } catch (InterruptedException ex) {
        	LOG.debug("IdleConnectionMonitorThread",ex);
            shutdown();
        }
    }
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
