package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.message.AdviceMessage;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;


/**
 * The Class FraugsterUploadSuccessHandler.
 */
public class FraugsterUploadSuccessHandler  {

	/** The ifraugster upload DB service. */
	@Autowired
	@Qualifier("fraugsterUploadDBServiceImpl")
	private IFraugsterUploadDBService ifraugsterUploadDBService;
	
	/**
	 * On success.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<String> onSuccess(AdviceMessage<String> message) throws ComplianceException {
		
		@SuppressWarnings("unchecked")
		List<Integer> fraugsetrId =  (List<Integer>) message.getInputMessage().getHeaders().get("ids");
		
		ifraugsterUploadDBService.updateFraugsterDataTable(fraugsetrId);
		return message;
	}
}
