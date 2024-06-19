/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class FraugsterTransformer.
 *
 * @author manish
 */
public class FraugsterTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterTransformer.class);

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
			FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) messageExchange.getRequest();
			Contact contact = (Contact) fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);

			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			FraugsterPaymentsInRequest fraugsterPaymentsInRequest = createServiceRequest(fundsInCreateRequest,
					messageExchange.getServiceInterface().name());
			
			ServiceStatus defaultStatus;
			if (fundsInCreateRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)) {
				defaultStatus = ServiceStatus.NOT_PERFORMED;
			} else {
				defaultStatus = ServiceStatus.NOT_REQUIRED;
			}

			FraugsterPaymentsInResponse fraugsterPaymentsInResponse = createServiceDeafaultResponse(contact.getId(),
					defaultStatus);
			FraugsterSummary fraugsterSummary = new FraugsterSummary();
			fraugsterSummary.setStatus(fraugsterPaymentsInResponse.getStatus());
			// use this to update the FraugsterSchedularData table.
			fraugsterSummary.setCdTrasId(fraugsterPaymentsInRequest.getContactRequests().get(0).getTransactionID());

			MessageExchange ccExchange = createMessageExchange(fraugsterPaymentsInRequest, fraugsterPaymentsInResponse,
					ServiceTypeEnum.FRAUGSTER_SERVICE);
			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
					ServiceTypeEnum.FRAUGSTER_SERVICE, ServiceProviderEnum.FRAUGSTER_FUNDSIN_SERVICE, contact.getId(),
					contact.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(), fraugsterPaymentsInResponse,
					fraugsterSummary, defaultStatus));

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in Fraugster transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the service deafault response.
	 *
	 * @param contactId
	 *            the contact id
	 * @param defaultStatus
	 *            the default status
	 * @return the fraugster payments in response
	 */
	private FraugsterPaymentsInResponse createServiceDeafaultResponse(Integer contactId, ServiceStatus defaultStatus) {

		FraugsterPaymentsInResponse fPaymentInResponse = new FraugsterPaymentsInResponse();
		List<FraugsterPaymentsInContactResponse> fContactResponses = new ArrayList<>();
		FraugsterPaymentsInContactResponse response = new FraugsterPaymentsInContactResponse();
		response.setId(contactId);
		response.setStatus(defaultStatus.name());
		fContactResponses.add(response);
		fPaymentInResponse.setContactResponses(fContactResponses);
		fPaymentInResponse.setStatus(defaultStatus.name());
		return fPaymentInResponse;
	}

	/**
	 * Creates the service request.
	 *
	 * @param fundsInRequest
	 *            the funds in request
	 * @param type
	 *            the type
	 * @return the fraugster payments in request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FraugsterPaymentsInRequest createServiceRequest(FundsInCreateRequest fundsInRequest, String type) {
		FraugsterPaymentsInRequest request = new FraugsterPaymentsInRequest();
		List<FraugsterPaymentsInContactRequest> fContactRequests = new ArrayList<>();
		Account account = (Account) fundsInRequest.getAdditionalAttribute("account");
		FraugsterPaymentsInContactRequest fContactRequest;
		fContactRequest = transformPaymantInRequest(fundsInRequest);
		fContactRequests.add(fContactRequest);
		request.setContactRequests(fContactRequests);
		request.setOrgCode(fundsInRequest.getOrgCode());
		request.setCorrelationID(fundsInRequest.getCorrelationID());
		request.setSourceApplication(fundsInRequest.getSourceApplication());
		request.setRequestType(type);
		request.setCustType(CustomerTypeEnum.getCustumerTypeAsString(account.getCustomerType()));
		return request;
	}

	/**
	 * Transform paymant in request.
	 *
	 * @param fundsInRequest
	 *            the funds in request
	 * @return the fraugster payments in contact request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FraugsterPaymentsInContactRequest transformPaymantInRequest(FundsInCreateRequest fundsInRequest) {
		Account account = (Account) fundsInRequest.getAdditionalAttribute("account");
		Contact contact = (Contact) fundsInRequest.getAdditionalAttribute("contact");
		DeviceInfo deviceInfo = fundsInRequest.getDeviceInfo();
		FraugsterPaymentsInContactRequest requestData = new FraugsterPaymentsInContactRequest();
		FundsInTrade trade = fundsInRequest.getTrade();
		requestData.setEventType("payment_in");
		requestData.setId(contact.getId());
		requestData.setAccountIdentification(trade.getAccountIdentification());
		requestData.setAddressOnCard(trade.getBillAddressLine());
		requestData.setaVSResult(trade.getAvsResult());
		requestData.setaVTradeFrequency(trade.getAvTradeFrequency());
		requestData.setaVTradeValue(trade.getAvTradeValue());
		requestData.setChequeClearanceDate(trade.getChequeClearanceDate());
		requestData.setCurrency(trade.getTransactionCurrency().substring(0, 3));
		requestData.setCustomerAccountNumber(contact.getContactSFID());
		requestData.setCustomerAddressOrPostcode(trade.getBillAdZip());
		requestData.setCustomerFirstName(contact.getFirstName());
		requestData.setCustomerLastName(contact.getLastName());
		requestData.setDebitCardAddedDate(trade.getDebitCardAddedDate());
		requestData.setDateAndTime(trade.getPaymentTime());
		requestData.setNameOnCard(trade.getCcFirstName());
		requestData.setOrganizationCode(fundsInRequest.getOrgCode());
		requestData.setPaymentType(trade.getPaymentMethod());
		requestData.setReason(trade.getPurposeOfTrade());
		requestData.setRegistrationDateTime(account.getRegistrationDateTime());
		requestData.setThirdPartyPayment(String.valueOf(trade.getThirdPartyPayment()));
		requestData.setThreeDStatus(trade.getIsThreeds());
		requestData.setTransactionAmount((float) trade.getSellingAmount());
		//following fields are added for fraud predict
		requestData.setPurposeOfTrade(trade.getPurposeOfTrade());
		requestData.setCountryOfFund(trade.getCountryOfFund());
		requestData.setTurnover(trade.getTurnover());
		requestData.setSellingAmountGbpValue(Double.toString(trade.getSellingAmountBaseValue()));
		requestData.setContractNumber(trade.getContractNumber());
		if (null != fundsInRequest.getRiskScore() && null!= fundsInRequest.getRiskScore().getTScore()) {
		requestData.setRiskScore(Double.toString(fundsInRequest.getRiskScore().getTScore()));
		}
		
		
		/**
		 * Changes made for to set unique TransactionID by concatenate FundsInID
		 * and TradeAccountNumber- Saylee
		 */
		requestData.setTransactionID(String.valueOf(fundsInRequest.getFundsInId())
				+ (String.valueOf(fundsInRequest.getTrade().getTradeAccountNumber())));
		requestData.setTransactionReference(trade.getTransactionReference());

		requestData.setCustID(leftPadWithZero(account.getId()));

		if (null != deviceInfo) {
		    requestData.setUserAgent(deviceInfo.getUserAgent());
			requestData.setBrowserLanguage(deviceInfo.getBrowserLanguage());
			requestData.setBrowserMajorVersion(deviceInfo.getBrowserMajorVersion());
			requestData.setBrowserName(deviceInfo.getBrowserName());
			requestData.setBrowserOnline(deviceInfo.getBrowserOnline());
			// Added ScreenResolution instead of BrowserScreenResolution and
			// DeviceResolution
			requestData.setBrowserScreenResolution(deviceInfo.getScreenResolution());
			requestData.setDeviceID(deviceInfo.getDeviceId());
			requestData.setDeviceManufacturer(deviceInfo.getDeviceManufacturer());
			requestData.setDeviceName(deviceInfo.getDeviceName());
			requestData.setDeviceType(deviceInfo.getDeviceType());
			requestData.setDeviceVersion(deviceInfo.getDeviceVersion());
			//following 2 fields are added for fraud predict
			requestData.setBrowserMajorVersion(deviceInfo.getBrowserMajorVersion());
			requestData.setBrowserType(deviceInfo.getBrowserName());
			requestData.setOsName(deviceInfo.getOsName());	
		}
		
		/** set fraugster signup score **/
		String fraugsterSignupScore = (String) fundsInRequest.getAdditionalAttribute("FraugsterSignupScore");
		if (null != fraugsterSignupScore) {
			requestData.setCustSignupScore(Float.parseFloat(fraugsterSignupScore));
		}
		return requestData;
	}

	/**
	 * Left pad with zero.
	 *
	 * @param id
	 *            the id
	 * @return the string
	 */
	private String leftPadWithZero(Integer id) {
		String pad = "0000000000";
		return (pad + id).substring((pad + id).length() - 10);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange exchange = null;
		try {
			exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			transformPaymentInResponse(exchange);
		} catch (Exception e) {
			LOG.error("Error in Fraugster transformer class : transformResponse -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the default failure response.
	 *
	 * @param exchange
	 *            the exchange
	 */
	private FraugsterPaymentsInResponse createDefaultFailureResponse(MessageExchange exchange,
			FraugsterPaymentsInRequest request) {
		FraugsterPaymentsInResponse fResponse;
		fResponse = new FraugsterPaymentsInResponse();

		List<FraugsterPaymentsInContactResponse> conactResponseList = new ArrayList<>();
		FraugsterSummary fraugsterSummary = new FraugsterSummary();

		for (FraugsterPaymentsInContactRequest contact : request.getContactRequests()) {
			FraugsterPaymentsInContactResponse contactResponse = new FraugsterPaymentsInContactResponse();
			EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
					EntityEnum.CONTACT.name(), contact.getId());
			contactResponse.setId(contact.getId());
			contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			conactResponseList.add(contactResponse);
			fResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			fResponse.setContactResponses(null);
			fraugsterSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			fraugsterSummary.setFrgTransId(Constants.NOT_AVAILABLE);
			fraugsterSummary.setScore(Constants.NOT_AVAILABLE);
			eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(contactResponse));
		}
		fResponse.setContactResponses(conactResponseList);
		exchange.setResponse(fResponse);
		return fResponse;
	}

	/**
	 * Transform payment in response.
	 *
	 * @param exchange
	 *            the exchange
	 */
	private void transformPaymentInResponse(MessageExchange exchange) {
		
    	     FraugsterPaymentsInResponse fResponse = (FraugsterPaymentsInResponse) exchange.getResponse();
   		     FraugsterPaymentsInRequest request = exchange.getRequest(FraugsterPaymentsInRequest.class);
   		     
			if (fResponse == null|| fResponse.getContactResponses().get(0).getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
					fResponse = createDefaultFailureResponse(exchange, request);
				}
		
				if ("SUCCESS".equalsIgnoreCase(fResponse.getStatus())) {
					fResponse.setStatus(ServiceStatus.PASS.name());
				}
		
				List<FraugsterPaymentsInContactResponse> identityResponse = fResponse.getContactResponses();
				for (FraugsterPaymentsInContactResponse response : identityResponse) {
					if (identityResponse != null && !response.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
						if ("SUCCESS".equalsIgnoreCase(response.getStatus())) {
							response.setStatus(ServiceStatus.PASS.name());
						}
		
						EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
								EntityEnum.CONTACT.name(), response.getId());
						readResponse(eventServiceLog, response);
					}
				}
   		     }
   		     
	
	/**
	 * Transform fraugster resend request.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> transformFraugsterResendRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsInFraugsterResendRequest resendRequest = messageExchange
					.getRequest(FundsInFraugsterResendRequest.class);

			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) resendRequest
					.getAdditionalAttribute(Constants.OLD_REQUEST);

			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute("eventId");

			FraugsterPaymentsInRequest request = createServiceRequest(fundsInRequest, "FUNDS_IN");

			FraugsterPaymentsInContactResponse defaultResponse = new FraugsterPaymentsInContactResponse();
			FraugsterSummary summary = new FraugsterSummary();
			FraugsterPaymentsInResponse fPaymentInResponse;
			MessageExchange ccExchange;
			fPaymentInResponse = createServiceDeafaultResponse(ServiceStatus.NOT_PERFORMED, fundsInRequest);
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			// use this to update the FraugsterSchedularData table.
			summary.setCdTrasId(request.getContactRequests().get(0).getTransactionID());
			ccExchange = createMessageExchange(request, fPaymentInResponse, ServiceTypeEnum.FRAUGSTER_SERVICE);

			Contact contact = (Contact) fundsInRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);

			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.FRAUGSTER_SERVICE,
							ServiceProviderEnum.FRAUGSTER_FUNDSIN_SERVICE, contact.getId(), contact.getVersion(),
							EntityEnum.CONTACT.name(), 1, defaultResponse, summary, ServiceStatus.NOT_PERFORMED));

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception ex) {
			LOG.error("Error in Fraugster transformer class : transformRequest -", ex);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * @param status
	 * @param fundsInRequest
	 * @return payment in response
	 */
	private FraugsterPaymentsInResponse createServiceDeafaultResponse(ServiceStatus status,
			FundsInCreateRequest fundsInRequest) {

		Contact contact = (Contact) fundsInRequest.getAdditionalAttribute("contact");
		FraugsterPaymentsInResponse fPaymentInResponse = new FraugsterPaymentsInResponse();
		List<FraugsterPaymentsInContactResponse> fContactResponses = new ArrayList<>();
		fPaymentInResponse.setStatus(status.name());
		FraugsterPaymentsInContactResponse response = new FraugsterPaymentsInContactResponse();
		response.setId(contact.getId());
		response.setStatus(status.name());
		fContactResponses.add(response);
		fPaymentInResponse.setContactResponses(fContactResponses);
		return fPaymentInResponse;
	}

}
