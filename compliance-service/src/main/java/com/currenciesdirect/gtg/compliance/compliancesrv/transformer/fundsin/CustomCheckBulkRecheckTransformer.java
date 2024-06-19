package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractCustomCheckTransformer;

/**
 * The Class CustomCheckBulkRecheckTransformer.
 */
public class CustomCheckBulkRecheckTransformer extends AbstractCustomCheckTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CustomCheckBulkRecheckTransformer.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {

		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fundsInCreateReqeust = (FundsInCreateRequest) messageExchange.getRequest();

			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			Account account = (Account) fundsInCreateReqeust.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);

			CustomChecksRequest serviceRequest = new CustomChecksRequest();
			serviceRequest.setOrgCode(fundsInCreateReqeust.getOrgCode());
			serviceRequest.setPaymentTransId(fundsInCreateReqeust.getFundsInId());
			serviceRequest.setAccId(account.getId());
			serviceRequest.setBuyAmountOnAccount(account.getMaxTransactionAmount());
			serviceRequest.setBuyCurrencyOnAccount(account.getBuyingCurrency());
			serviceRequest.setReasonsOfTransferOnAccount(account.getPurposeOfTran());
			serviceRequest.setSellAmountOnAccount(account.getMaxTransactionAmount());
			serviceRequest.setSellCurrencyOnAccount(account.getSellingCurrency());
			serviceRequest.setEddCountryFlag((Integer) fundsInCreateReqeust.getAdditionalAttribute(Constants.EDD_COUNTRY_NUMBER));
			serviceRequest.setZeroFundsInClear((boolean) fundsInCreateReqeust.getAdditionalAttribute(Constants.ZERO_CLEAR_FUNDSIN));
			serviceRequest.setZeroFundsClearAfterLEDate((boolean) fundsInCreateReqeust.getAdditionalAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE));
			serviceRequest.setPoiExistsFlag((Integer) fundsInCreateReqeust.getAdditionalAttribute(Constants.CONTACT_POI_EXISTS));
			serviceRequest.setZeroFundsInClearForCDINC((boolean) fundsInCreateReqeust.getAdditionalAttribute(Constants.CDINC_ZERO_CLEAR_FUNDSIN));//Add for AT-3738
	
			ServiceProviderEnum provider = ServiceProviderEnum.CUSTOM_CHECKS_SERVICE;
			fundsInCreateReqeust.setType("FUNDS_IN_ADD");
			serviceRequest.setESDocument(fundsInCreateReqeust);
			ServiceStatus defaultStatus;
			if (fundsInCreateReqeust.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)) {
				defaultStatus = ServiceStatus.NOT_PERFORMED;
			} else {
				defaultStatus = ServiceStatus.NOT_REQUIRED;
			}
			CustomCheckResponse serviceResponse = createDefaultResponse(defaultStatus);
			MessageExchange ccExchange = createMessageExchange(serviceRequest, serviceResponse,
					ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
					ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, provider, account.getId(), account.getVersion(),
					EntityEnum.ACCOUNT.name(), token.getUserID(), serviceResponse, serviceResponse, defaultStatus));

			message.getPayload().removeMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in CustomChecks transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		Account account = null;
		MessageExchange exchange = null;
		EventServiceLog log = null;
		try {
			FundsInCreateRequest fundsInCreateReqeust = (FundsInCreateRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			account = (Account) fundsInCreateReqeust.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);

			exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			CustomCheckResponse serviceResponse = (CustomCheckResponse) exchange.getResponse();
			log = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, EntityEnum.ACCOUNT.name(),
					account.getId());
			if (null != serviceResponse) {
				updateEventServiceLogEntry(log, serviceResponse, serviceResponse, serviceResponse.getOverallStatus());
				serviceResponse.setCheckedOn(log.getUpdatedOn().toString());
			} else {
				serviceResponse = createDefaultResponse(ServiceStatus.SERVICE_FAILURE);
				updateEventServiceLogEntry(exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
						EntityEnum.ACCOUNT.name(), account.getId()), serviceResponse, serviceResponse,
						serviceResponse.getOverallStatus());
				exchange.setResponse(serviceResponse);
			}
		} catch (Exception e) {
			LOG.error("Error in CustomChecks transformer class : transformResponse -", e);
			try {
				CustomCheckResponse serviceResponse = createDefaultResponse(ServiceStatus.SERVICE_FAILURE);
				if (log != null)
					updateEventServiceLogEntry(
							exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
									EntityEnum.ACCOUNT.name(), account.getId()),
							serviceResponse, serviceResponse, serviceResponse.getOverallStatus());
				if (exchange != null)
					exchange.setResponse(serviceResponse);
			} catch (Exception cp) {
				LOG.error("Error in CustomChecks transformer class : transformResponse -", cp);
				message.getPayload().setFailed(true);
			}
		}
		return message;
	}

}
