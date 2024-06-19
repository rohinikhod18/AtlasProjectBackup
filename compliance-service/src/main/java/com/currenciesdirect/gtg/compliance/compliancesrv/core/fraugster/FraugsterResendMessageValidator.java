package com.currenciesdirect.gtg.compliance.compliancesrv.core.fraugster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

/**
 * The Class FraugsterResendMessageValidator.
 */
public class FraugsterResendMessageValidator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FraugsterResendMessageValidator.class);

	/** The new registration DB service impl. */
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
		FraugsterResendRequest fraugsterResendRequest = null;
		FraugsterResendResponse response = new FraugsterResendResponse();
		MessageExchange messageExchange = null;
		try {
			messageExchange = message.getPayload().getGatewayMessageExchange();

			fraugsterResendRequest = (FraugsterResendRequest) messageExchange.getRequest();

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.FRAUGSTER_RESEND) {

				isAccountValidate = validateAccountForFraugsterResend(fraugsterResendRequest.getAccountId());
				if (null != fraugsterResendRequest.getEntityType()
						&& !("ACCOUNT").equalsIgnoreCase(fraugsterResendRequest.getEntityType())) {
					isContactValidate = validateContactForFraugsterResend(fraugsterResendRequest.getEntityId());
				} else {
					isContactValidate = validateTradeACcountIDForAccountFraugsterResend(
							fraugsterResendRequest.getAccountId());
				}

				Integer orgId = newRegistrationDBServiceImpl.getOrganisationID(fraugsterResendRequest.getOrgCode());
				fraugsterResendRequest.setOrgId(orgId);
				if (isAccountValidate && isContactValidate) {
					response.setDecision(DECISION.SUCCESS);
				} else {
					response.setDecision(BaseResponse.DECISION.FAIL);
					response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
					LOGGER.error(" is not valid");
				}
			} else {
				response.setDecision(BaseResponse.DECISION.FAIL);
				response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
				LOGGER.error("Request is not valid");
			}

		} catch (Exception e) {
			LOGGER.error("Request is not valid", e);
			response.setDecision(DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
		}
		if (Boolean.FALSE.equals(isAccountValidate) || Boolean.FALSE.equals(isContactValidate)) {
			MessageExchange msgExchange = new MessageExchange();
			msgExchange.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);
			msgExchange.setResponse(response);
			message.getPayload().appendMessageExchange(msgExchange);
		}
		if (messageExchange != null) {
			messageExchange.setResponse(response);
		}

		return message;
	}

	/**
	 * Validate account for fraugster resend.
	 *
	 * @param accountId
	 *            the account id
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean validateAccountForFraugsterResend(Integer accountId) throws ComplianceException {
		return (accountId != null && newRegistrationDBServiceImpl.isAccountExistByDBId(accountId));
	}

	/**
	 * Validate contact for fraugster resend.
	 *
	 * @param contactId
	 *            the contact id
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean validateContactForFraugsterResend(Integer contactId) throws ComplianceException {
		return (contactId != null && newRegistrationDBServiceImpl.isContactExistByDBId(contactId));
	}

	/**
	 * Validate trade A ccount ID for account fraugster resend.
	 *
	 * @param accountID
	 *            the account ID
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean validateTradeACcountIDForAccountFraugsterResend(Integer accountID) throws ComplianceException {
		return (accountID != null && newRegistrationDBServiceImpl.isTradeAccountIdExist(accountID));

	}
}