package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FraudData;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.RiskScore;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CardFraudScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FraudSightScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalRuleSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractInternalRulesTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class InternalRuleBulkRecheckTransformer.
 */
public class InternalRuleBulkRecheckTransformer extends AbstractInternalRulesTransformer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InternalRuleBulkRecheckTransformer.class);
	
	/** The Constant NOT_AVAILABLE. */
	private static final String NOT_AVAILABLE = "Not Available";

	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);

			Account account = (Account) fundsInRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			Contact contact = (Contact) fundsInRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			InternalServiceRequest internalServiceRequest = new InternalServiceRequest();

			internalServiceRequest.setCorrelationID(fundsInRequest.getCorrelationID());
			internalServiceRequest.setOrgCode(fundsInRequest.getOrgCode());
			internalServiceRequest.setAccountSFId(account.getAccSFID());
			List<InternalServiceRequestData> datas = new ArrayList<>();

			InternalServiceRequestData fundsInData = new InternalServiceRequestData();
			if (null != fundsInRequest.getTrade().getCountryOfFund()) {
				fundsInData.setCountry(
						null != countryCache.getCountryFullName(fundsInRequest.getTrade().getCountryOfFund())
								? countryCache.getCountryFullName(fundsInRequest.getTrade().getCountryOfFund()) : "");
			}
			
			if ("SWITCH/DEBIT".equalsIgnoreCase(fundsInRequest.getTrade().getPaymentMethod())) {
				fundsInData.setCcName(fundsInRequest.getTrade().getCcFirstName());
				fundsInData.setAccountNumber(null);
			} else {
				fundsInData.setName(fundsInRequest.getTrade().getDebtorName());
				fundsInData.setAccountNumber(fundsInRequest.getTrade().getDebtorAccountNumber());
			}

			fundsInData.setEntityType(EntityEnum.CONTACT.name());
			fundsInData.setId(fundsInRequest.getFundsInId());
			fundsInData.setThirdPartyPayment(fundsInRequest.getTrade().getThirdPartyPayment());
			fundsInData.setBankName(fundsInRequest.getTrade().getDebtorBankName());
			RiskScore rScore = fundsInRequest.getRiskScore();
			if (null != rScore && null != rScore.getTRisk() && null != rScore.getTScore()) {
				internalServiceRequest.setIsRgDataExists(Boolean.TRUE);
				fundsInData.setCardFraudScore(rScore.getTScore());
				fundsInData.setCardFraudScoreThreshold(rScore.getTRisk());
			}else {
				internalServiceRequest.setIsRgDataExists(Boolean.FALSE);
			}

			//AT-3830
			checkIsFraudSightEligible(fundsInRequest, internalServiceRequest, fundsInData);

			fundsInData.setPaymentMethod(fundsInRequest.getTrade().getPaymentMethod());
			datas.add(fundsInData);

			ServiceStatus defaultStatus;
			if (fundsInRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)) {
				defaultStatus = ServiceStatus.NOT_PERFORMED;
			} else {
				defaultStatus = ServiceStatus.NOT_REQUIRED;
			}
			BlacklistContactResponse defaultResponse = new BlacklistContactResponse();
			BlacklistSummary summary = new BlacklistSummary();
			defaultResponse.setStatus(defaultStatus.name());
			summary.setStatus(defaultStatus.name());
			List<Contact> contacts = new ArrayList<>(1);
			contacts.add(contact);
			MessageExchange ccExchange = createMessageExchange(internalServiceRequest,
					createInternalRuleServiceDefaultResponse(contacts), ServiceTypeEnum.INTERNAL_RULE_SERVICE);

			ccExchange.addEventServiceLog(
					createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.BLACK_LIST_SERVICE,
							ServiceProviderEnum.BLACKLIST, fundsInRequest.getFundsInId(), fundsInRequest.getVersion(),
							EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary, defaultStatus));

			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.HRC_SERVICE,
					ServiceProviderEnum.COUNTRYCHECK, fundsInRequest.getFundsInId(), fundsInRequest.getVersion(),
					EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary, defaultStatus));

			//AT-3830
			if(Boolean.FALSE.equals(internalServiceRequest.getIsFraudSightEligible()) && Boolean.TRUE.equals(internalServiceRequest.getIsRgDataExists())){
					ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
					ServiceTypeEnum.CARD_FRAUD_SCORE_SERVICE, ServiceProviderEnum.CARD_FRAUD_SCORE_SERVICE,
					fundsInRequest.getFundsInId(), fundsInRequest.getVersion(), EntityEnum.CONTACT.name(),
					token.getUserID(), defaultResponse, summary, defaultStatus));
			}else{
					ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
					ServiceTypeEnum.FRAUD_SIGHT_SCORE_SERVICE, ServiceProviderEnum.FRAUD_SIGHT_SCORE_SERVICE,
					fundsInRequest.getFundsInId(), fundsInRequest.getVersion(), EntityEnum.CONTACT.name(),
					token.getUserID(), defaultResponse, summary, defaultStatus));
			}
			
			internalServiceRequest.setSearchdata(datas);
			internalServiceRequest.setRequestType(Constants.PAYMENT_IN);

			message.getPayload().removeMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.error("Exception in FundsIn InternalRuleBulkRecheckTransformer : transformRequest()", e);
			message.getPayload().setFailed(true);
		}
		return message;

	}

	/**
	 * @param fundsInRequest
	 * @param internalServiceRequest
	 * @param fundsInData
	 */
	private void checkIsFraudSightEligible(FundsInCreateRequest fundsInRequest,
			InternalServiceRequest internalServiceRequest, InternalServiceRequestData fundsInData) {
		FraudData fraudSight = fundsInRequest.getFraudSight();
		if (null != fraudSight && null != fraudSight.getFsMessage()&&  null != fraudSight.getFsScore()){
			internalServiceRequest.setIsFraudSightEligible(Boolean.TRUE);
			fundsInData.setFraudSightScore(fraudSight.getFsScore());
			fundsInData.setFraudSightMessage(fraudSight.getFsMessage());
		}else {
			internalServiceRequest.setIsFraudSightEligible(Boolean.FALSE);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		InternalServiceRequest internalServiceRequest = null;
		MessageExchange exchange = null;
		try {
			exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();
			internalServiceRequest = exchange.getRequest(InternalServiceRequest.class);
			InternalRuleSummary internalRuleSummary = new InternalRuleSummary();

			if (internalServiceResponse == null) {
				transformResponseForServiceFailure(internalServiceRequest, exchange);
			} else {

				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();

				for (ContactResponse response : contactResponses) {
					EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
							EntityEnum.CONTACT.name(), response.getId());
					EventServiceLog hrclistLog = exchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
							EntityEnum.CONTACT.name(), response.getId());
					EventServiceLog fraudScoreLog = exchange.getEventServiceLog(
							ServiceTypeEnum.CARD_FRAUD_SCORE_SERVICE, EntityEnum.CONTACT.name(), response.getId());
					//AT-3714
					EventServiceLog fraudSightScoreLog = exchange.getEventServiceLog(
							ServiceTypeEnum.FRAUD_SIGHT_SCORE_SERVICE, EntityEnum.CONTACT.name(), response.getId());
					updateESLForInternalruleServices(internalServiceResponse, internalRuleSummary, response,
							blacklistLog, hrclistLog, fraudScoreLog,fraudSightScoreLog,internalServiceRequest);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error in InternalRuleServiceTransformer class : transformResponse -", e);
			message.getPayload().setFailed(true);
			try {
				transformResponseForServiceFailure(internalServiceRequest, exchange);
			} catch (Exception cp) {
				LOGGER.error(
						"Error in InternalRuleServiceTransformer transformer class : transformResponseForServiceFailure -",
						cp);
				message.getPayload().setFailed(true);
			}

		}
		return message;
	}

	/**
	 * Transform response for service failure.
	 *
	 * @param internalServiceRequest
	 *            the internal service request
	 * @param exchange
	 *            the exchange
	 */
	private void transformResponseForServiceFailure(InternalServiceRequest internalServiceRequest,
			MessageExchange exchange) {
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
		List<ContactResponse> contactsResponseList = new ArrayList<>();

		try {
			for (InternalServiceRequestData contact : internalServiceRequest.getSearchdata()) {

				ContactResponse response = new ContactResponse();
				BlacklistContactResponse blacklist = new BlacklistContactResponse();
				BlacklistSummary blacklistSummary = new BlacklistSummary();

				blacklist.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklist.setData(null);
				blacklistSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				blacklistSummary.setAccountNumber(NOT_AVAILABLE);
				blacklistSummary.setBankName(NOT_AVAILABLE);
				blacklistSummary.setName(NOT_AVAILABLE);
				response.setBlacklist(blacklist);
				response.setId(contact.getId());
				response.setContactStatus(ServiceStatus.SERVICE_FAILURE.name());

				CountryCheckContactResponse countryCheckResponse = new CountryCheckContactResponse();
				countryCheckResponse.setCountry(contact.getCountry());
				countryCheckResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				response.setCountryCheck(countryCheckResponse);

				//AT-3714/AT-3830
				if (Boolean.FALSE.equals(internalServiceRequest.getIsFraudSightEligible())
						&& Boolean.TRUE.equals(internalServiceRequest.getIsRgDataExists())) {
					CardFraudScoreResponse cardFraudScoreResponse = new CardFraudScoreResponse();
					cardFraudScoreResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					response.setCardFraudCheck(cardFraudScoreResponse);
				} else {
					FraudSightScoreResponse fraudSightScoreResponse = new FraudSightScoreResponse();
					fraudSightScoreResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					response.setFraudSightCheck(fraudSightScoreResponse);
				}
				
				EventServiceLog blacklistLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.CONTACT.name(), response.getId());

				updateEventServiceLogEntry(blacklistLog, blacklistSummary, response.getBlacklist(),
						ServiceStatus.SERVICE_FAILURE.name());
				contactsResponseList.add(response);

			}
			internalServiceResponse.setContacts(contactsResponseList);
			exchange.setResponse(internalServiceResponse);

		} catch (Exception e) {
			LOGGER.error("Error in reposne mapping", e);
		}

	}

	/**
	 * Update ESL for internalrule services.
	 *
	 * @param internalServiceResponse
	 *            the internal service response
	 * @param internalRuleSummary
	 *            the internal rule summary
	 * @param response
	 *            the response
	 * @param blacklistLog
	 *            the blacklist log
	 * @param hrclistLog
	 *            the hrclist log
	 * @param fraudScoreLog
	 *            the fraud score log
	 */
	private void updateESLForInternalruleServices(InternalServiceResponse internalServiceResponse,
			InternalRuleSummary internalRuleSummary, ContactResponse response, EventServiceLog blacklistLog,
			EventServiceLog hrclistLog, EventServiceLog fraudScoreLog,EventServiceLog fraudSightScoreLog,InternalServiceRequest internalServiceRequest) {
		try {
			if (ServiceStatus.FAIL.name().equalsIgnoreCase(response.getBlacklist().getStatus())) {
				internalServiceResponse.setStatus(ServiceStatus.FAIL.name());
			}

			BlacklistSummary summary = createBlacklistSummary(response);
			if (StringUtils.isNullOrEmpty(summary.getAccountNumber())) {
				summary.setAccountNumber(ServiceStatus.NOT_REQUIRED.name());
			}
			if (StringUtils.isNullOrTrimEmpty(summary.getName())) {
				summary.setName(ServiceStatus.NOT_REQUIRED.name());
			}
			// change end
			updateEventServiceLogEntry(blacklistLog, summary, response.getBlacklist(),
					response.getBlacklist().getStatus());
			internalRuleSummary.setBlacklistSummary(summary);

			updateEventServiceLogEntry(hrclistLog, response.getCountryCheck(), response.getCountryCheck(),
					response.getCountryCheck().getStatus());
			internalRuleSummary.setHrcSummary(response.getCountryCheck());

			if (Boolean.FALSE.equals(internalServiceRequest.getIsFraudSightEligible())
					&& Boolean.TRUE.equals(internalServiceRequest.getIsRgDataExists())) {
				updateEventServiceLogEntry(fraudScoreLog, response.getCardFraudCheck(), response.getCardFraudCheck(),
						response.getCardFraudCheck().getStatus());
				internalRuleSummary.setCardFraudScoreSummary(response.getCardFraudCheck());
			} else {// AT-3830
				updateEventServiceLogEntry(fraudSightScoreLog, response.getFraudSightCheck(),
						response.getFraudSightCheck(), response.getFraudSightCheck().getStatus());
				internalRuleSummary.setFraudSightScoreSummary(response.getFraudSightCheck());
			}
		} catch (Exception e) {
			LOGGER.error("Error in reposne mapping", e);
			updateEventServiceLogEntry(blacklistLog, null, null, response.getBlacklist().getStatus());

		}
	}

	/**
	 * Creates the internal rule service default response.
	 *
	 * @param contacts
	 *            the contacts
	 * @return the internal service response
	 */
	protected InternalServiceResponse createInternalRuleServiceDefaultResponse(List<Contact> contacts) {
		InternalServiceResponse response = new InternalServiceResponse();
		List<ContactResponse> responseList = new ArrayList<>();
		for (Contact contact : contacts) {
			ContactResponse contactResponse = new ContactResponse();
			GlobalCheckContactResponse globalCheckContactResponse = new GlobalCheckContactResponse();
			BlacklistContactResponse blacklistContactResponse = new BlacklistContactResponse();
			IpContactResponse ipContactResponse = new IpContactResponse();
			CountryCheckContactResponse countryCheckContactResponse = new CountryCheckContactResponse();
			CardFraudScoreResponse cardFraudScoreResponse = new CardFraudScoreResponse();
			FraudSightScoreResponse fraudSightScoreResponse = new FraudSightScoreResponse();//AT-3714
			contactResponse.setId(contact.getId());
			globalCheckContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			blacklistContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			ipContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			countryCheckContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			cardFraudScoreResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			fraudSightScoreResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setContactStatus(ServiceStatus.NOT_REQUIRED.name());
			contactResponse.setCardFraudCheck(cardFraudScoreResponse);
			contactResponse.setFraudSightCheck(fraudSightScoreResponse);
			contactResponse.setIpCheck(ipContactResponse);
			contactResponse.setCountryCheck(countryCheckContactResponse);
			contactResponse.setGlobalCheck(globalCheckContactResponse);
			contactResponse.setBlacklist(blacklistContactResponse);
			responseList.add(contactResponse);
		}
		response.setContacts(responseList);
		return response;
	}

}
