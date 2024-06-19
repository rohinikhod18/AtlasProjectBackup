package com.currenciesdirect.gtg.compliance.compliancesrv.msg;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;

/**
 * The Class MessageExchange.
 */
@SuppressWarnings("unchecked")
public class MessageExchange implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The service type enum. */
	private ServiceTypeEnum serviceTypeEnum;

	/** The service interface. */
	private ServiceInterfaceType serviceInterface;
	
	/** The operation. */
	private OperationEnum operation;

	/** The request. */
	private ServiceMessage request;

	/** The response. */
	private ServiceMessageResponse response;
	
	/** The event service log. */
	private EventServiceLogContainer eventServiceLog = new EventServiceLogContainer();

	/** The service name. */
	private String serviceName;
	
	/** The service response. */
	private String serviceResponse;
	
	/** The internal processing code. */
	private InternalProcessingCode internalProcessingCode = InternalProcessingCode.NOT_CLEARED;

	/**
	 * Gets the service interface.
	 *
	 * @return the service interface
	 */
	public ServiceInterfaceType getServiceInterface() {
		return serviceInterface;
	}

	/**
	 * Sets the service interface.
	 *
	 * @param serviceInterface the new service interface
	 */
	public void setServiceInterface(ServiceInterfaceType serviceInterface) {
		this.serviceInterface = serviceInterface;
	}

	/**
	 * Gets the operation.
	 *
	 * @return the operation
	 */
	public OperationEnum getOperation() {
		return operation;
	}

	/**
	 * Sets the operation.
	 *
	 * @param operation the new operation
	 */
	public void setOperation(OperationEnum operation) {
		this.operation = operation;
	}

	/**
	 * Gets the service type enum.
	 *
	 * @return the service type enum
	 */
	public ServiceTypeEnum getServiceTypeEnum() {
		return serviceTypeEnum;
	}

	/**
	 * Sets the service type enum.
	 *
	 * @param serviceTypeEnum the new service type enum
	 */
	public void setServiceTypeEnum(ServiceTypeEnum serviceTypeEnum) {
		this.serviceTypeEnum = serviceTypeEnum;
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public ServiceMessage getRequest() {
		return request;
	}
	
	/**
	 * Gets the request.
	 *
	 * @param <T> the generic type
	 * @param requestClass the request class
	 * @return the request
	 */
	@SuppressWarnings("squid:S1172")
	public <T extends ServiceMessage> T getRequest(Class<T> requestClass) {
		return (T)request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request the new request
	 */
	public void setRequest(ServiceMessage request) {
		this.request = request;
	}

	/**
	 * Gets the response.
	 *
	 * @return the response
	 */
	public ServiceMessageResponse getResponse() {
		return response;
	}

	/**
	 * Gets the response.
	 *
	 * @param <T> the generic type
	 * @param t the t
	 * @return the response
	 */
	@SuppressWarnings("squid:S1172")
	public <T extends ServiceMessageResponse> T getResponse(Class<T> t) {
		return (T)response;
	}
	
	/**
	 * Sets the response.
	 *
	 * @param response the new response
	 */
	public void setResponse(ServiceMessageResponse response) {
		this.response = response;
	}

	/**
	 * Gets the internal processing code.
	 *
	 * @return the internal processing code
	 */
	public InternalProcessingCode getInternalProcessingCode() {
		return internalProcessingCode;
	}

	/**
	 * Sets the internal processing code.
	 *
	 * @param internalProcessingCode the new internal processing code
	 */
	public void setInternalProcessingCode(InternalProcessingCode internalProcessingCode) {
		this.internalProcessingCode = internalProcessingCode;
	}

	/**
	 * Clear.
	 */
	public void clear() {
		if(eventServiceLog!=null){
		this.eventServiceLog.cleanup();
		}
		
		if(request!= null && request.getAdditionalAttributes()!=null){
			this.request.clear();
			}
		
		if(response!=null&&response.getAdditionalAttributes()!=null){
			this.response.clear();
			}
		eventServiceLog = null;
		serviceTypeEnum = null;
		request = null;
		response = null;
		internalProcessingCode = null;
		serviceName = null;
		serviceResponse = null;
		operation = null;
	}

	/**
	 * Gets the service name.
	 *
	 * @return the service name
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the service name.
	 *
	 * @param serviceName the new service name
	 */
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * Gets the service response.
	 *
	 * @return the service response
	 */
	public String getServiceResponse() {
		return serviceResponse;
	}

	/**
	 * Sets the service response.
	 *
	 * @param serviceResponse the new service response
	 */
	public void setServiceResponse(String serviceResponse) {
		this.serviceResponse = serviceResponse;
	}

	/**
	 * Adds the event service log.
	 *
	 * @param log the log
	 */
	public void addEventServiceLog(EventServiceLog log){
		eventServiceLog.addEventServiceLog(log);
	}
	
	/**
	 * Gets the event service log.
	 *
	 * @param serviceTypeEnum the service type enum
	 * @param entityType the entity type
	 * @param entityId the entity id
	 * @return the event service log
	 */
	public EventServiceLog getEventServiceLog(ServiceTypeEnum serviceTypeEnum, String entityType, Integer entityId){
		return eventServiceLog.getEventServiceLog(serviceTypeEnum.getShortName()+entityType+entityId);
	}
	
	/**
	 * Gets the event service log as list.
	 *
	 * @return the event service log as list
	 */
	public Collection<EventServiceLog> getEventServiceLogAsList(){
		return eventServiceLog.getEventServiceLogAsList();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MessageExchange [serviceTypeEnum=" + serviceTypeEnum + ", request=" + request + ", response=" + response
				+ "]";
	}
	
	
	
}
class EventServiceLogContainer implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4082013938614082385L;
	private Map<String, EventServiceLog> eventServiceLogs = new ConcurrentHashMap<>(7);
	
	public void addEventServiceLog(EventServiceLog log){
		eventServiceLogs.put(log.getServiceName()+log.getEntityType()+log.getEntityId(), log);
	}
	
	public EventServiceLog getEventServiceLog(String key){
		return eventServiceLogs.get(key);
	}
	
	public Collection<EventServiceLog> getEventServiceLogAsList(){
		return eventServiceLogs.values();
	}
	
	public void cleanup(){
		eventServiceLogs.clear();
		eventServiceLogs = null;
	}
	
	
}