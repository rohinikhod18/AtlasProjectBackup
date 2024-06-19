package com.currenciesdirect.gtg.compliance.compliancesrv.core.blacklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

/**
 * The Class BlacklistResendMessageValidator.
 */
public class BlacklistResendMessageValidator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(BlacklistResendMessageValidator.class);

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
		BlacklistResendRequest blacklistResendRequest = null;
		BlacklistResendResponse response = new BlacklistResendResponse();
		MessageExchange messageExchange = null;
		try {
			messageExchange = message.getPayload().getGatewayMessageExchange();

			blacklistResendRequest = (BlacklistResendRequest) messageExchange.getRequest();

			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.PROFILE && operation == OperationEnum.BLACKLIST_RESEND) {

				isAccountValidate = validateAccountForBlacklistResend(blacklistResendRequest.getAccountId());
				if (null != blacklistResendRequest.getEntityType()
						&& !("ACCOUNT").equalsIgnoreCase(blacklistResendRequest.getEntityType())) {
					isContactValidate = validateContactForBlacklistResend(blacklistResendRequest.getEntityId());
				} else {
					isContactValidate = validateTradeACcountIDForAccountBlacklistResend(
							blacklistResendRequest.getAccountId());
				}

				Integer orgId = newRegistrationDBServiceImpl.getOrganisationID(blacklistResendRequest.getOrgCode());
				blacklistResendRequest.setOrgId(orgId);
				setResponseStatus(isAccountValidate, isContactValidate, response);
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
			msgExchange.setServiceTypeEnum(ServiceTypeEnum.BLACK_LIST_SERVICE);
			msgExchange.setResponse(response);
			message.getPayload().appendMessageExchange(msgExchange);
		}
		if (messageExchange != null) {
			messageExchange.setResponse(response);
		}

		return message;
	}

	/**
	 * Sets the response status.
	 *
	 * @param isAccountValidate
	 *            the is account validate
	 * @param isContactValidate
	 *            the is contact validate
	 * @param response
	 *            the response
	 */
	private void setResponseStatus(Boolean isAccountValidate, Boolean isContactValidate,
			BlacklistResendResponse response) {
		if (isAccountValidate && isContactValidate) {
			response.setDecision(DECISION.SUCCESS);
		} else {
			response.setDecision(BaseResponse.DECISION.FAIL);
			response.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			LOGGER.error(" is not valid");
		}
	}

	/**
	 * Validate account for blacklist resend.
	 *
	 * @param accountId
	 *            the account id
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean validateAccountForBlacklistResend(Integer accountId) throws ComplianceException {
		return (accountId != null && newRegistrationDBServiceImpl.isAccountExistByDBId(accountId));
	}

	/**
	 * Validate contact for blacklist resend.
	 *
	 * @param contactId
	 *            the contact id
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean validateContactForBlacklistResend(Integer contactId) throws ComplianceException {
		return (contactId != null && newRegistrationDBServiceImpl.isContactExistByDBId(contactId));
	}

	/**
	 * Validate trade A ccount ID for account blacklist resend.
	 *
	 * @param accountID
	 *            the account ID
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean validateTradeACcountIDForAccountBlacklistResend(Integer accountID) throws ComplianceException {
		return (accountID != null && newRegistrationDBServiceImpl.isTradeAccountIdExist(accountID));
	}

}
