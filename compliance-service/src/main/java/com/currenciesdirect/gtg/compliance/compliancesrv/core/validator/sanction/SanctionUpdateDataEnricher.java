package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.sanction;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class SanctionUpdateDataEnricher.
 */
public class SanctionUpdateDataEnricher  extends AbstractDataEnricher{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionUpdateDataEnricher.class);

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
		SanctionUpdateRequest sanctionUpdateRequest=null;
		MessageExchange messageExchange = null;
		try{
			getUserTableId(message);
			messageExchange = message.getPayload().getGatewayMessageExchange();

			sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);
			
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			
			if(serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.SANCTION_UPDATE){

				
				for (SanctionUpdateData data : sanctionUpdateRequest.getSanctions()) {

					checkSanctionUpdateRequest(message, sanctionUpdateRequest, data);
				}
			}
			
		}catch (Exception e) {
			LOGGER.error(ENRICH_DATA_ERROR, e);
		}
		return message;
	}



	/**
	 * @param message
	 * @param sanctionUpdateRequest
	 * @param data
	 * @throws ComplianceException
	 */
	private void checkSanctionUpdateRequest(Message<MessageContext> message,
			SanctionUpdateRequest sanctionUpdateRequest, SanctionUpdateData data) throws ComplianceException {
		if (sanctionUpdateRequest.getResourceType().equalsIgnoreCase(ServiceInterfaceType.PROFILE.name())) {
			getEventForSanctionUpdate(data.getEventServiceLogId(), Constants.SANCTION,
					message);
		} else if (sanctionUpdateRequest.getResourceType()
				.equalsIgnoreCase(ServiceInterfaceType.FUNDSOUT.name())) {
			getEventForFundsOutSanctionUpdate(data.getEventServiceLogId(),
					Constants.SANCTION, message);
		} else if (sanctionUpdateRequest.getResourceType()
				.equalsIgnoreCase(ServiceInterfaceType.FUNDSIN.name())) {
			getEventForFundsInSanctionUpdate(data.getEventServiceLogId(),
					Constants.SANCTION, message);
		}
	}
	
	
	
	private void getEventForSanctionUpdate(Integer eventServiceLogId,String serviceName,Message<MessageContext> message) throws ComplianceException{
		if(eventServiceLogId != null){
			eventDBService.getEventSerivceLogByIdAndService(eventServiceLogId,serviceName,message);
		}
	}
	
	private void getEventForFundsOutSanctionUpdate(Integer eventServiceLogId,String serviceName,Message<MessageContext> message) throws ComplianceException{
		if(eventServiceLogId != null){
			eventDBService.getFundsOutEventSerivceLogByIdAndService(eventServiceLogId,serviceName,message);
		}
	}
	
	private  void getEventForFundsInSanctionUpdate(Integer eventServiceLogId,String serviceName,Message<MessageContext> message) throws ComplianceException{
		if(eventServiceLogId != null){
			eventDBService.getFundsInEventSerivceLogByIdAndService(eventServiceLogId,serviceName,message);
		}
	}

	public IEventDBService getEventDBService() {
		return eventDBService;
	}

	public void setEventDBService(IEventDBService eventDBService) {
		this.eventDBService = eventDBService;
	}
	
}
