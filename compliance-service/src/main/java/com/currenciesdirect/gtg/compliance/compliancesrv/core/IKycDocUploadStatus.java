package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Interface IAccountStatusUpdate.
 * @author abhijitg
 */
public interface IKycDocUploadStatus {

	
	/**
	 * Update kyc doc upload status.
	 *
	 * @param message the message
	 * @return the message
	 * @throws Exception the exception
	 */
	Message<MessageContext> updateKycDocUploadStatus(Message<MessageContext> message) throws ComplianceException;
}
