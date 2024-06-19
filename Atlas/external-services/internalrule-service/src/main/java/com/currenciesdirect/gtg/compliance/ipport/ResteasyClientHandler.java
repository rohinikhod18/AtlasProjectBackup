/**
 * 
 */
package com.currenciesdirect.gtg.compliance.ipport;

 


import java.util.ArrayList;
import java.util.List;
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

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.ip.IpConcreteDataBuilder;
import com.currenciesdirect.gtg.compliance.util.Constants;


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
	
	/** The httpclient. */
	private CloseableHttpClient postCodeThreadSafeClient= null;
	
	/** The ip thread safe client. */
	private CloseableHttpClient ipThreadSafeClient= null;

	/** The client. */
	private ResteasyClient postCodeclient = null;  
	
	/** The ipclient. */
	private ResteasyClient ipclient = null;
	
	/** The stale monitor. */
	private IdleConnectionMonitorThread staleMonitor = null;

	/**
	 * Instantiates a new resteasy client handler.
	 */
	private ResteasyClientHandler() {

		/** The engine. */
		ApacheHttpClient4Engine postEngine = null;
		
		/** The ip engine. */
		ApacheHttpClient4Engine ipEngine = null;
		
		/** The builder. */
		IpConcreteDataBuilder postCodebuilder = IpConcreteDataBuilder.getInstance();
		
		/** The ip builder. */
		IpConcreteDataBuilder ipBuilder = IpConcreteDataBuilder.getInstance();
		
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		// Increase max total connection to 20
		cm.setMaxTotal(20);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		PoolingClientConnectionManager ipcm = new PoolingClientConnectionManager();
		// Increase max total connection to 20
		ipcm.setMaxTotal(20);
		// Increase default max connection per route to 20
		ipcm.setDefaultMaxPerRoute(20);
		IpProviderProperty ipPostCodeProviderProperty = new IpProviderProperty();
		IpProviderProperty ipProviderProperty = new IpProviderProperty();
		postCodeThreadSafeClient = new DefaultHttpClient(cm);
		ipThreadSafeClient = new DefaultHttpClient(ipcm);
		List<PoolingClientConnectionManager> connMgrList=new ArrayList<>();
		try{
			ipPostCodeProviderProperty = postCodebuilder.getProviderInitConfigProperty(Constants.CHECK_IP_POSTCODE);
			postCodeThreadSafeClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, Boolean.TRUE);
			postCodeThreadSafeClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, ipPostCodeProviderProperty.getConnectionTimeoutMillis());
			postCodeThreadSafeClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,ipPostCodeProviderProperty.getSocketTimeoutMillis());
			postEngine = new ApacheHttpClient4Engine(postCodeThreadSafeClient,true);		
			postCodeclient = new ResteasyClientBuilder().httpEngine(postEngine).build();
			connMgrList.add(cm);

			ipProviderProperty = ipBuilder.getProviderInitConfigProperty(Constants.GET_IP_DETAILS);
			ipThreadSafeClient.getParams().setParameter(CoreConnectionPNames.STALE_CONNECTION_CHECK, Boolean.TRUE);
			ipThreadSafeClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, ipProviderProperty.getConnectionTimeoutMillis());
			ipThreadSafeClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,ipProviderProperty.getSocketTimeoutMillis());
			ipEngine = new ApacheHttpClient4Engine(ipThreadSafeClient,true);		
			ipclient = new ResteasyClientBuilder().httpEngine(ipEngine).build();
			connMgrList.add(ipcm);
			staleMonitor	 = new IdleConnectionMonitorThread(connMgrList);
			
		}catch(Exception e){
			LOG.error("Error in  ResteasyClientHandler ",e);
		}
		
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
	public ResteasyClient getPostCodeRestEasyClient() {
		return postCodeclient;
	}
	
	public ResteasyClient getIpRestEasyClient() {
		return ipclient;
	}

}
 class IdleConnectionMonitorThread extends Thread {
	private static final Logger LOG = LoggerFactory.getLogger(IdleConnectionMonitorThread.class);
    private final List<PoolingClientConnectionManager> connMgr;
    private volatile boolean shutdown;
 
    public IdleConnectionMonitorThread(List<PoolingClientConnectionManager> connMgr) {
        super();
        this.connMgr = connMgr;
    }
    @Override
    @SuppressWarnings("squid:S2142")
    public void run() {
        try {
            while (!shutdown) {
                synchronized (this) {
                	for(PoolingClientConnectionManager conn : connMgr){
                		 wait(1000);
                     	conn.closeExpiredConnections();
                     	conn.closeIdleConnections(30, TimeUnit.SECONDS);
                	}
                }
            }
        } catch (InterruptedException ex) {
        	LOG.error("IdleConnectionMonitorThread",ex);
            shutdown();
        }
    }
    
    /**
     * Shutdown.
     */
    public void shutdown() {
        shutdown = true;
        synchronized (this) {
            notifyAll();
        }
    }
}
