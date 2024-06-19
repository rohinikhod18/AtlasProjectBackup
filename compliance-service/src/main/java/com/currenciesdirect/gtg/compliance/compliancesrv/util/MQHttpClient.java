package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MQMessageContext;

/**
 * The Class MQHttpClient.
 */
public final class MQHttpClient {

	/** The Constant ERROR_CODE_URL_BASE. */
	private static final String ERROR_CODE_URL_BASE = "Error Code:{}, urlBase:{}";

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(MQHttpClient.class);

	/** The url base. */
	private String urlBase;

	/** The method. */
	private String method;

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	/**
	 * Gets the url base.
	 *
	 * @return the url base
	 */
	public String getUrlBase() {
		return urlBase;
	}

	/**
	 * Sets the url base.
	 *
	 * @param urlBase
	 *            the new url base
	 */
	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	/**
	 * Gets the method.
	 *
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * Sets the method.
	 *
	 * @param method
	 *            the new method
	 */
	public void setMethod(String method) {
		this.method = method;
	}

	/**
	 * The Enum Singleton.
	 */
	private enum Singleton {

		/** The client. */
		CLIENT;

		/** The thread safe client. */
		private final CloseableHttpClient threadSafeClient;

		/** The monitor. */
		private final IdleConnectionMonitorThread monitor;

		/** The client. */
		private ResteasyClient client = null;

		/**
		 * Instantiates a new singleton.
		 */
		private Singleton() {
			PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(200);
			cm.setDefaultMaxPerRoute(20);
			threadSafeClient = HttpClients.custom().setConnectionManager(cm).build();
			ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(threadSafeClient);
			client = new ResteasyClientBuilder().connectionPoolSize(10).maxPooledPerRoute(10).httpEngine(engine)
					.build();

			monitor = new IdleConnectionMonitorThread(cm);
			monitor.setDaemon(true);
			monitor.start();
		}

		/**
		 * Gets the.
		 *
		 * @return the resteasy client
		 */
		public ResteasyClient get() {
			return client;
		}

	}

	/**
	 * Gets the client.
	 *
	 * @return the client
	 */
	public static ResteasyClient getClient() {
		return Singleton.CLIENT.get();
	}

	/**
	 * The Class IdleConnectionMonitorThread.
	 */
	private static class IdleConnectionMonitorThread extends Thread {

		private static final String ERROR = "Error";

		/** The cm. */
		private final PoolingHttpClientConnectionManager cm;

		/** The stop signal. */
		private final BlockingQueue<Stop> stopSignal = new ArrayBlockingQueue<>(1);

		/**
		 * The Class Stop.
		 */
		private static class Stop {

			/** The stop queue. */
			private final BlockingQueue<Stop> stopQueue = new ArrayBlockingQueue<>(1);

			/**
			 * Stopped.
			 */
			public void stopped() {
				stopQueue.add(this);
			}

			/**
			 * Wait for stopped.
			 *
			 * @throws InterruptedException
			 *             the interrupted exception
			 */
			public void waitForStopped() throws InterruptedException {
				stopQueue.take();
			}

		}

		/**
		 * Instantiates a new idle connection monitor thread.
		 *
		 * @param cm
		 *            the cm
		 */
		IdleConnectionMonitorThread(PoolingHttpClientConnectionManager cm) {
			super();
			this.cm = cm;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run() {
			try {
				Stop stopRequest;
				while ((stopRequest = stopSignal.poll(600, TimeUnit.SECONDS)) == null) {
					cm.closeExpiredConnections();
					cm.closeIdleConnections(600, TimeUnit.SECONDS);
					LOG.trace("Stats: {}", cm.getTotalStats());
				}
				stopRequest.stopped();
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
				LOG.trace(ERROR, ex);
			}
		}

		/**
		 * Shutdown.
		 *
		 * @throws InterruptedException
		 *             the interrupted exception
		 */
		public void shutdown() throws InterruptedException {
			LOG.trace("Shutting down client pool");
			Stop stop = new Stop();
			stopSignal.add(stop);
			stop.waitForStopped();
			try {
				Singleton.CLIENT.threadSafeClient.close();
			} catch (IOException e) {
				LOG.trace("Client pool shut down", e);
			}
			cm.close();
			LOG.trace("Client pool shut down");
		}

	}

	/**
	 * Shutdown.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	public static void shutdown() throws InterruptedException {
		Singleton.CLIENT.monitor.shutdown();
	}

	/**
	 * Send request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MQMessageContext> sendRequest(Message<MQMessageContext> message) {
		if (message != null) {
			
			String request = JsonConverterUtil.convertToJsonWithNull(message.getPayload().getMqProviderRequest());
            LOG.info("------ MQ Provider request : {} ",request);
			
			MQProviderResponse response = sendRequest(urlBase, "POST", message.getPayload().getMqProviderRequest());
			if (response != null) {
				message.getPayload().setMqProviderResponse(response);
			}
		}

		return MessageBuilder.fromMessage(message).build();
	}

	/**
	 * Send request.
	 *
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param request
	 *            the request
	 * @return the MQ provider response
	 */
	public MQProviderResponse sendRequest(String url, String method, Object request) {
		Response response = null;
		MQProviderResponse responseObject = null;
		try {
			ResteasyClient resteasyClient = resteasyClientHandler.getRetEasyClient();
			ResteasyWebTarget target = resteasyClient.target(url);
			Builder clientrequest = target.request();
			switch (method) {
			case "POST":
				response = clientrequest.post(Entity.entity(request, MediaType.APPLICATION_JSON));
				break;
			case "PUT":
				response = clientrequest.put(Entity.entity(request, MediaType.APPLICATION_JSON));
				break;
			default:
				response = clientrequest.get();
			}
			if (response.getStatus() == 200 && response.hasEntity()) {
				responseObject = response.readEntity(MQProviderResponse.class);
			} else if (response.getStatus() == 404) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_UNAVAILABLE, urlBase);
				responseObject = new MQProviderResponse();
				responseObject.setStatus(Constants.NOT_FOUND);
			} else if (response.getStatus() == 500) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.INTERNAL_SERVER_ERROR, urlBase);
			} else {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_ERROR, urlBase);
			}
		} catch (Exception e) {
			responseObject = new MQProviderResponse();
			responseObject.setStatus(Constants.NOT_FOUND);
			LOG.error("Error {1}", e);
		} finally {
			try {
				if (response != null)
					response.close();
			} catch (Exception e) {
				LOG.error("Error while closing resource : ", e);
			}
		}

		return responseObject;
	}

	/**
	 * Send request to data lake.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MQMessageContext> sendRequestToDataLake(Message<MQMessageContext> message) {
		if (message != null) {

			Response response = sendRequestToDataLake(urlBase, "POST", message.getPayload().getMqProviderRequest());
			if (response != null) {
				message.getPayload().setDatalakeResponse(response);
			}
		}

		return MessageBuilder.fromMessage(message).build();
	}

	/**
	 * Send request to data lake.
	 *
	 * @param url
	 *            the url
	 * @param method
	 *            the method
	 * @param mqProviderRequest
	 *            the mq provider request
	 * @return the response
	 */
	private Response sendRequestToDataLake(String url, String method, MQProviderRequest mqProviderRequest) {
		Response response = null;
		try {
			ResteasyClient resteasyClient = resteasyClientHandler.getRetEasyClient();
			ResteasyWebTarget target = resteasyClient.target(url);
			Builder clientrequest = target.request();

			String jsonMQProviderRequest = JsonConverterUtil.convertToJsonWithoutNull(mqProviderRequest);
			LOG.warn("\n -------sendRequestToDataLake mqProvider Request Start -------- \n  {}", jsonMQProviderRequest);
			LOG.warn(" \n -----------sendRequestToDataLake mqProvider Request End ---------");

			switch (method) {
			case "POST":
				response = clientrequest.post(Entity.entity(mqProviderRequest, MediaType.APPLICATION_JSON));
				break;
			case "PUT":
				response = clientrequest.put(Entity.entity(mqProviderRequest, MediaType.APPLICATION_JSON));
				break;
			default:
				response = clientrequest.get();
			}
			
			String jsonMQProviderResponse = JsonConverterUtil.convertToJsonWithoutNull(response);
			LOG.warn("\n -------sendRequestToDataLake mqProvider Response Start -------- \n  {}", jsonMQProviderResponse);
			LOG.warn(" \n -----------sendRequestToDataLake mqProvider Response End ---------");
			
			if (response.getStatus() == 200) {
				LOG.warn("Successfully dumped into Data Lake for account ID {}",mqProviderRequest.getAccountCID());
			} else if (response.getStatus() == 404) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_UNAVAILABLE, urlBase);
			} else if (response.getStatus() == 500) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.INTERNAL_SERVER_ERROR, urlBase);
			} else {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_ERROR, urlBase);
			}
		} catch (Exception e) {
			LOG.error("Error {1}", e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				LOG.error("Error while closing resource : ", e);
			}
		}

		return response;
	}

}