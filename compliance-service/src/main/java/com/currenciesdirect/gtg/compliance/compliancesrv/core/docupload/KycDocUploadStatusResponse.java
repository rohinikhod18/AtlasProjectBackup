package com.currenciesdirect.gtg.compliance.compliancesrv.core.docupload;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.docupload.DocumentUploadStatusResponse;	
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;

/**
 * The Class KycDocUploadStatusRespons.
 * @author abhijitg
 */
public class KycDocUploadStatusResponse {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KycDocUploadStatusResponse.class);

	/**
	 * Process.
	 *
	 * @param message the message
	 * @return the message
	 */
	@SuppressWarnings("unchecked")
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		if (message.getHeaders().get(MessageContextHeaders.GATEWAY_OPERATION).equals( OperationEnum.CONTACT_STATUS.name())) {

			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			DocumentUploadStatusResponse uploadResponse=new DocumentUploadStatusResponse();
			uploadResponse.setFileUploadStatus("Success");
			messageExchange.setResponse(uploadResponse);
			
			
			DeferredResult<ResponseEntity<?>> deferredResult = (DeferredResult<ResponseEntity<?>>) message.getHeaders()
					.get(MessageContextHeaders.GATEWAY_RESPONSE_OBJECT);

			DocumentUploadStatusResponse response = (DocumentUploadStatusResponse) messageExchange.getResponse();
			ResponseEntity<ServiceMessageResponse> responseEntity = new ResponseEntity<>(response, HttpStatus.OK);

			deferredResult.setResult(responseEntity);

			return message;
		} else {
			LOGGER.warn("Document Upload Response : NO RESPONSE ");
		}
		return message;
	}
}
