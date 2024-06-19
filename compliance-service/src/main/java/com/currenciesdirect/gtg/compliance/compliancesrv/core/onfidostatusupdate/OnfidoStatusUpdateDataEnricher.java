package com.currenciesdirect.gtg.compliance.compliancesrv.core.onfidostatusupdate;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class OnfidoStatusUpdateDataEnricher extends AbstractDataEnricher {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OnfidoStatusUpdateDataEnricher.class);
	
	/** The Constant ENRICH_DATA_ERROR. */
	private static final String ENRICH_DATA_ERROR = "Error while EnrichData";
	
	@Autowired
	private IEventDBService eventDBService;
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		OnfidoUpdateRequest onfidoUpdateRequest=null;
		MessageExchange messageExchange = null;
		try{
			getUserTableId(message);
			messageExchange = message.getPayload().getGatewayMessageExchange();

			onfidoUpdateRequest = messageExchange.getRequest(OnfidoUpdateRequest.class);
			
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.ONFIDO_STATUS_UPDATE){

				
				for (OnfidoUpdateData data : onfidoUpdateRequest.getOnfido()) {

					checkOnfidoStatusUpdateRequest(message, onfidoUpdateRequest, data);
				}
			}
			
		}catch (Exception e) {
			LOGGER.error(ENRICH_DATA_ERROR, e);
		}
		return message;
	}
	
	/**
	 * Check onfido status update request.
	 */
	private void checkOnfidoStatusUpdateRequest(Message<MessageContext> message,
			OnfidoUpdateRequest sanctionUpdateRequest, OnfidoUpdateData data) throws ComplianceException {
		if (sanctionUpdateRequest.getResourceType().equalsIgnoreCase(ServiceInterfaceType.PROFILE.name())) {
			getEventForOnfidoStatusUpdate(data.getEventServiceLogId(), Constants.ONFIDO,message);
		}
	}
	
	/**
	 * Gets the event for onfido status update.
	 *
	 * @param eventServiceLogId the event service log id
	 * @param serviceName the service name
	 * @param message the message
	 * @return the event for onfido status update
	 * @throws ComplianceException the compliance exception
	 */
	private void getEventForOnfidoStatusUpdate(Integer eventServiceLogId,String serviceName,Message<MessageContext> message) throws ComplianceException{
		if(eventServiceLogId != null){
			eventDBService.getEventSerivceLogByIdAndService(eventServiceLogId,serviceName,message);
		}
	}

	public IEventDBService getEventDBService() {
		return eventDBService;
	}

	public void setEventDBService(IEventDBService eventDBService) {
		this.eventDBService = eventDBService;
	}
}
