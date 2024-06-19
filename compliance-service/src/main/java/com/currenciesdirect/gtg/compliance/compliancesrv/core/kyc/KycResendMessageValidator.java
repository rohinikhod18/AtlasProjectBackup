package com.currenciesdirect.gtg.compliance.compliancesrv.core.kyc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KycResendRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

/**
 * The Class KycResendMessageValidator.
 */
public class KycResendMessageValidator {

	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	private static final Logger LOGGER = LoggerFactory.getLogger(KycResendMessageValidator.class);

	@Autowired
	private NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;

	/**
	 * Process.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message) {
		Boolean isAccountValidate = Boolean.FALSE;
		Boolean isContactValidate = Boolean.FALSE;
		KycResendRequest kycResendRequest = null;
		MessageExchange messageExchange = null;
		KYCSummary response = new KYCSummary();
		try {
			messageExchange = message.getPayload().getGatewayMessageExchange();

			kycResendRequest = (KycResendRequest) messageExchange.getRequest();

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.KYC_RESEND) {

				isAccountValidate = validateAccountForKycResend(kycResendRequest.getAccountId());
				isContactValidate = validateContactForKycResend(kycResendRequest.getContactId());
				Integer orgId = newRegistrationDBServiceImpl.getOrganisationID(kycResendRequest.getOrgCode());
				kycResendRequest.setOrgId(orgId);
				if (isAccountValidate && isContactValidate) {
					response.setDecision(DECISION.SUCCESS);
				} else {
					response.setDecision(DECISION.FAIL);
					response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
					LOGGER.error(REQUEST_IS_NOT_VALID);
				}
			} else {
				response.setDecision(BaseResponse.DECISION.FAIL);
				response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
				LOGGER.error(REQUEST_IS_NOT_VALID);
			}

		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			response.setDecision(DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
		}
		if (Boolean.FALSE.equals(isAccountValidate) || Boolean.FALSE.equals(isContactValidate)) {
			MessageExchange msgExchange = new MessageExchange();
			msgExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
			msgExchange.setResponse(response);
			message.getPayload().appendMessageExchange(msgExchange);
		}
		if (messageExchange != null)
			messageExchange.setResponse(response);

		return message;
	}

	private Boolean validateAccountForKycResend(Integer accountId) throws ComplianceException {
		return (accountId != null && newRegistrationDBServiceImpl.isAccountExistByDBId(accountId));
	}

	private Boolean validateContactForKycResend(Integer contactId) throws ComplianceException {
		return (contactId != null && newRegistrationDBServiceImpl.isContactExistByDBId(contactId));
	}

}
