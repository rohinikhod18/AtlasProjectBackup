package com.currenciesdirect.gtg.compliance.compliancesrv.flowcontrol.filter;

import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

public class ValidationFilter {

	/**
	 * Filter.
	 *
	 * @param message the message
	 * @return true, if successful
	 */
	@Filter
	public boolean filter(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationResponse response = (RegistrationResponse) messageExchange.getResponse();
		return response.getDecision() != BaseResponse.DECISION.FAIL;
	}
}