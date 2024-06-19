package com.currenciesdirect.gtg.compliance.compliancesrv.util;


import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

public final class HttpClientPool {
	private static final String ERROR_CODE_URL_BASE = "Error Code:{}, urlBase:{}";
	private static final Logger LOG = LoggerFactory.getLogger(HttpClientPool.class);
	private String urlBase;
	private String operation;
	private String method;
	private String responseType;
	private ServiceTypeEnum serviceType;
	private Class<ServiceMessage> requestClass;
	private Class<ServiceMessageResponse> responseClass;

	/** The resteasy client handler. */
	private ResteasyClientHandler resteasyClientHandler = ResteasyClientHandler.getInstance();

	public Class<ServiceMessage> getRequestClass() {
		return requestClass;
	}

	public void setRequestClass(Class<ServiceMessage> requestClass) {
		this.requestClass = requestClass;
	}

	public Class<ServiceMessageResponse> getResponseClass() {
		return responseClass;
	}

	public void setResponseClass(Class<ServiceMessageResponse> responseClass) {
		this.responseClass = responseClass;
	}

	public ServiceTypeEnum getServiceType() {
		return serviceType;
	}

	public void setServiceType(ServiceTypeEnum serviceType) {
		this.serviceType = serviceType;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getUrlBase() {
		return urlBase;
	}

	public void setUrlBase(String urlBase) {
		this.urlBase = urlBase;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Message<MessageContext> sendRequest(Message<MessageContext> message) {
		try {
			if (message != null) {
				MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
				headers.add("correlationId", message.getHeaders().get("correlationID").toString());
				ServiceMessageResponse response;
				LOG.debug("serviceType:{}, urlBase:{}", serviceType, urlBase);
				message.getPayload().setServiceTobeInvoked(serviceType);
				response = sendRequest(urlBase, "POST",
						message.getPayload().getMessageExchange(serviceType).getRequest(requestClass), responseClass,
						headers);
				if (serviceType != null && response != null) {
					message.getPayload().getMessageExchange(serviceType).setResponse(response);
				} else if (serviceType != null) {
					message.getPayload().getMessageExchange(serviceType).setResponse(null);
				}
			}
		} catch (Exception e) {
			LOG.error("Error :", e);
			message.getPayload().setFailed(true);
		}
		return MessageBuilder.fromMessage(message).build();
	}

	public <T> T sendRequest(String url, String method, Object request, Class<T> clazz,
			MultivaluedMap<String, Object> headers) {
		Response response = null;
		T responseObject = null;
		try {
			ResteasyClient resteasyClient = resteasyClientHandler.getRetEasyClient();
			ResteasyWebTarget target = resteasyClient.target(url);
			Builder clientrequest = target.request();
			switch (method) {
			case "POST":
				response = clientrequest.headers(headers).post(Entity.entity(request, MediaType.APPLICATION_JSON));
				break;
			case "PUT":
				response = clientrequest.put(Entity.entity(request, MediaType.APPLICATION_JSON));
				break;
			default:
				response = clientrequest.get();
			}
			if (response.getStatus() == 200 && response.hasEntity()) {
				responseObject = response.readEntity(clazz);
			} else if (response.getStatus() == 404) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_UNAVAILABLE, urlBase);
			} else if (response.getStatus() == 500) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.INTERNAL_SERVER_ERROR, urlBase);
			} else {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_ERROR, urlBase);
			}
		} catch (Exception e) {
			String logData = "Exception in sendRequest() for ";
			logData = logData.concat(url).concat(" ; ").concat(e.toString());
			LOG.error(logData);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				String logData = "Error while closing resource : ";
				logData = logData.concat(url).concat(" ; ").concat(e.toString());
				LOG.error(logData);
			}
		}

		return responseObject;
	}

	public Message<MessageContext> sendRequestToDataLake(Message<MessageContext> message) {
		try {
			if (message != null) {
				ServiceMessageResponse response;
				LOG.debug("serviceType:{}, urlBase:{}", serviceType, urlBase);
				message.getPayload().setServiceTobeInvoked(serviceType);

				String jsonMQProviderRequest = JsonConverterUtil.convertToJsonWithoutNull(message.getPayload()
								.getMessageExchange(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE).getRequest());
				LOG.warn("\n -------sendRequestToDataLake mqProvider Request Start -------- \n  {}",
						jsonMQProviderRequest);
				LOG.warn(" \n -----------sendRequestToDataLake mqProvider Request End ---------");
				response = sendRequestToDataLake(urlBase, "POST", jsonMQProviderRequest);
				if (serviceType != null) {
					message.getPayload().getMessageExchange(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE)
							.setResponse(response);

				}
				message.getPayload().clearAll();
			}
		} catch (Exception e) {
			LOG.error("Error :", e);
			message.getPayload().setFailed(true);
		}
		return MessageBuilder.fromMessage(message).build();
	}

	public <T> T sendRequestToDataLake(String url, String method, String request) {
		Response response = null;
		T responseObject = null;
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
			String jsonResponse = JsonConverterUtil.convertToJsonWithoutNull(response);
			LOG.warn("\n -------sendRequestToDataLake mqProvider Response Start -------- \n  {}", jsonResponse);
			LOG.warn(" \n -----------sendRequestToDataLake mqProvider Response End ---------");
			
			if (response.getStatus() == 200) {
				LOG.warn("Successfully dumped into Data Lake for account ID");
			} else if (response.getStatus() == 404) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_UNAVAILABLE, urlBase);
			} else if (response.getStatus() == 500) {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.INTERNAL_SERVER_ERROR, urlBase);
			} else {
				LOG.error(ERROR_CODE_URL_BASE, InternalProcessingCode.SERVICE_ERROR, urlBase);
			}
		} catch (Exception e) {
			String logData = "Exception in sendRequestToDataLake() for ";
			logData = logData.concat(url).concat(" ; ").concat(e.toString());
			LOG.error(logData);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				String logData = "Error while closing resource : ";
				logData = logData.concat(url).concat(" ; ").concat(e.toString());
				LOG.error(logData);
			}
		}

		return responseObject;
	}

}