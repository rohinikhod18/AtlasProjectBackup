package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonResponseForES;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class DataAnonDataEnricher extends AbstractDataEnricher{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataAnonDataEnricher.class);
	
	/** The data anon DB service impl. */
	@Autowired
	private IDataAnonDBService iDataAnonDBServiceImpl;
	
	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> dataEnricher(Message<MessageContext> message, @Header UUID correlationID) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		LOGGER.debug("Gateway message headers=[{}], payload=[{}] validated!!", message.getHeaders(),
				message.getPayload());
		DataAnonResponseForES response = messageExchange.getResponse(DataAnonResponseForES.class);
		DataAnonRequestFromES request = messageExchange.getRequest(DataAnonRequestFromES.class);
		try {
			getUserTableId(message);
			request = iDataAnonDBServiceImpl.getAccountContactDetails(request);
		}catch(Exception e) {
			LOGGER.error(Constants.INVALID_REQUEST, e);
			response.setDecision(BaseResponse.DECISION.FAIL);
			
			response.setCode(DataAnonConstants.RESPONSECODE1);
			response.setStatus(DataAnonConstants.RECEIVED);
			response.setSystem(DataAnonConstants.SYSTEM);
		}
		messageExchange.setResponse(response);
		messageExchange.setRequest(request);
		return message;
	}
}
