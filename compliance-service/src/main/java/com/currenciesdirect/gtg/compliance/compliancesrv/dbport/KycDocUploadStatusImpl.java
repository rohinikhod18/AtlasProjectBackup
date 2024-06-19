package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IKycDocUploadStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.docupload.DocumentUploadStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Class KycDocUploadStatusImpl.
 * @author abhijitg
 */
public class KycDocUploadStatusImpl extends AbstractDao implements IKycDocUploadStatus {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(KycDocUploadStatusImpl.class);

	/*
	 * 1. get request parameters from  DocumentUPloadStatusRequest.
	 * 2. Update "AccountStatus" field in "Account" table
	 * (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.IAccountStatusUpdate#updateKycAccountStatus(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> updateKycDocUploadStatus(Message<MessageContext> message) throws ComplianceException {
		
		DocumentUploadStatusRequest request = (DocumentUploadStatusRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();

		LOG.debug("updateKycAccountStatus() --> STARTED  {}", request);
		
		return message;
	}
}
