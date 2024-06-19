/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Beneficiary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Trade;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
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
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;


/**
 * The Class FraugsterTransformer.
 *
 * @author manish
 */
public class FraugsterTransformer extends AbstractTransformer{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterTransformer.class);
	
	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
	
	/** The Constant CONTACT. */
	private static final String CONTACT  ="contact";
	
	/** The Constant ACCOUNT. */
	private static final String ACCOUNT ="account";
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(CONTACT);
			
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			FraugsterPaymentsOutRequest request = createServiceRequest(fundsOutRequest, messageExchange.getServiceInterface().name());
			FraugsterPaymentsOutContactResponse defaultResponse=new FraugsterPaymentsOutContactResponse();
			FraugsterSummary summary=new FraugsterSummary();	
			
			FraugsterPaymentsOutResponse fPaymentOutResponse;
			MessageExchange ccExchange;
			if(fundsOutRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)){
				fPaymentOutResponse = createServiceDeafaultResponse(ServiceStatus.NOT_PERFORMED,fundsOutRequest);
				defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
				summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
				// use this to update the FraugsterSchedularData table.
				summary.setCdTrasId(request.getContactRequests().get(0).getTransactionID());
				ccExchange = createMessageExchange(request, fPaymentOutResponse, ServiceTypeEnum.FRAUGSTER_SERVICE);
				
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
						ServiceTypeEnum.FRAUGSTER_SERVICE, ServiceProviderEnum.FRAUGSTER_FUNDSOUT_SERVICE,
						contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(),
						defaultResponse, summary, ServiceStatus.NOT_PERFORMED));
				
				
			}else{
				fPaymentOutResponse = createServiceDeafaultResponse(ServiceStatus.NOT_REQUIRED,fundsOutRequest);
				defaultResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
				summary.setStatus(ServiceStatus.NOT_REQUIRED.name());
				
				ccExchange = createMessageExchange(request, fPaymentOutResponse, ServiceTypeEnum.FRAUGSTER_SERVICE);
				
				ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
						ServiceTypeEnum.FRAUGSTER_SERVICE, ServiceProviderEnum.FRAUGSTER_FUNDSOUT_SERVICE,
						contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(), token.getUserID(),
						defaultResponse, summary, ServiceStatus.NOT_REQUIRED));
				
				
					
			}
			
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in Fraugster transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}


	/**
	 * Transform request for fraugster resend.
	 *
	 * @param msg the msg
	 * @return the message
	 */
	public Message<MessageContext> transformRequestForFraugsterResend(Message<MessageContext> msg) {
		try{
			MessageExchange msgExchange = msg.getPayload().getGatewayMessageExchange();
			FundsOutFruagsterResendRequest resendRequest = msgExchange.getRequest(FundsOutFruagsterResendRequest.class);
			FundsOutRequest fundsOutRequest = (FundsOutRequest) resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST);
			
			Integer eventId = (Integer) msg.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			FraugsterPaymentsOutRequest request = createServiceRequest(fundsOutRequest, "FUNDS_OUT");
			
			FraugsterPaymentsOutContactResponse defaultResponse=new FraugsterPaymentsOutContactResponse();
			FraugsterSummary summary=new FraugsterSummary();	
			FraugsterPaymentsOutResponse fPaymentOutResponse;
			MessageExchange ccExchange;
			fPaymentOutResponse = createServiceDeafaultResponse(ServiceStatus.NOT_PERFORMED,fundsOutRequest);
			defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
			// use this to update the FraugsterSchedularData table.
			summary.setCdTrasId(request.getContactRequests().get(0).getTransactionID());
			ccExchange = createMessageExchange(request, fPaymentOutResponse, ServiceTypeEnum.FRAUGSTER_SERVICE);
			
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			
			ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(eventId,
					ServiceTypeEnum.FRAUGSTER_SERVICE, ServiceProviderEnum.FRAUGSTER_FUNDSOUT_SERVICE,
					contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(), 1,
					defaultResponse, summary, ServiceStatus.NOT_PERFORMED));
			
			msg.getPayload().appendMessageExchange(ccExchange);
		}catch(Exception ex){
			LOG.error("Error in Fraugster transformer class : transformRequest -", ex);
			msg.getPayload().setFailed(true);
		}
		return msg;
	}
	
	
	/**
	 * Creates the service request.
	 *
	 * @param fundsOutRequest the funds out request
	 * @param type the type
	 * @return the fraugster payments out request
	 * @throws ComplianceException the compliance exception
	 */
	private FraugsterPaymentsOutRequest createServiceRequest(FundsOutRequest fundsOutRequest, String type) throws ComplianceException {
		FraugsterPaymentsOutRequest request = new FraugsterPaymentsOutRequest();
		Account account = (Account) fundsOutRequest.getAdditionalAttribute(ACCOUNT);
		List<FraugsterPaymentsOutContactRequest> fContactRequests = new ArrayList<>();
		FraugsterPaymentsOutContactRequest fContactRequest;
		fContactRequest = transformPaymantOutRequest(fundsOutRequest);
		fContactRequests.add(fContactRequest);
		request.setContactRequests(fContactRequests);
		request.setOrgCode(fundsOutRequest.getOrgCode());
		request.setCorrelationID(fundsOutRequest.getCorrelationID());
		request.setSourceApplication(fundsOutRequest.getSourceApplication());
		request.setRequestType(type);
		request.setCustType(CustomerTypeEnum.getCustumerTypeAsString(account.getCustomerType()));
		return request;
	}


	/**
	 * Creates the service deafault response.
	 *
	 * @param status the status
	 * @param fundsOutRequest the funds out request
	 * @return the fraugster payments out response
	 */
	private FraugsterPaymentsOutResponse createServiceDeafaultResponse(ServiceStatus status,FundsOutRequest fundsOutRequest) {
		Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(CONTACT);
		FraugsterPaymentsOutResponse fPaymentOutResponse = new FraugsterPaymentsOutResponse();
		List<FraugsterPaymentsOutContactResponse> fContactResponses = new ArrayList<>();
		fPaymentOutResponse.setStatus(status.name());
		FraugsterPaymentsOutContactResponse response = new FraugsterPaymentsOutContactResponse();
		response.setId(contact.getId());
		response.setStatus(status.name());
		fContactResponses.add(response);
		fPaymentOutResponse.setContactResponses(fContactResponses);
		return fPaymentOutResponse;
	}

	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		try {
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			transformPaymentOutResponse(exchange);
		}catch(Exception e){
			LOG.error("Exception in transformResponse() of Fraugster transformer", e);
			message.getPayload().setFailed(true);
		}
		
		return message;
	}

	/**
	 * Left pad with zero.
	 *
	 * @param id the id
	 * @return the string
	 */
	private String leftPadWithZero(Integer id) {
		String pad = "0000000000";
		return (pad + id).substring((pad + id).length() - 10);
	}

	/**
	 * Transform paymant out request.
	 *
	 * @param fundsOutRequest the funds out request
	 * @return the fraugster payments out contact request
	 * @throws ComplianceException the compliance exception
	 */
	private FraugsterPaymentsOutContactRequest transformPaymantOutRequest(FundsOutRequest fundsOutRequest)
			throws ComplianceException {
		Account account = (Account) fundsOutRequest.getAdditionalAttribute(ACCOUNT);
		Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(CONTACT);
		DeviceInfo deviceInfo = fundsOutRequest.getDeviceInfo();
		Beneficiary bene = fundsOutRequest.getBeneficiary();
		Trade trade = fundsOutRequest.getTrade();
		FraugsterPaymentsOutContactRequest requestData = new FraugsterPaymentsOutContactRequest();

		requestData.setEventType("payment_out");
		requestData.setBuyingAmount(Double.toString(trade.getBuyingAmount()));
		requestData.setCustomerNo(contact.getContactSFID());
		requestData.setContracrNumber(trade.getContractNumber());
		requestData.setPurposeOfTrade(trade.getPurposeOfTrade());
		requestData.setSellingCurrency(trade.getSellCurrency());
		
		requestData.setBeneficiaryType(bene.getBeneficiaryType());
		requestData.setBeneficiaryCurrencyCode(bene.getCurrencyCode());
		requestData.setBeneficiaryAccountNumber(bene.getAccountNumber());
		requestData.setBeneficiaryBankAddress(bene.getBeneficaryBankAddress());
		requestData.setBeneficiaryBankName(bene.getBeneficaryBankName());
		if (null != bene.getCountry()) {
			requestData.setBeneficiaryCountry(null != countryCache.getCountryFullName(bene.getCountry())
					? countryCache.getCountryFullName(bene.getCountry())
					: "");
			requestData.setBeneficiaryCountryCode(bene.getCountry());
		}
		requestData.setBeneficiaryCurrency(bene.getCurrencyCode());
		requestData.setBeneficiaryEmail(bene.getEmail());
		requestData.setBeneficiaryFirstName(bene.getFirstName());
		requestData.setBeneficiaryLastName(bene.getLastName());
		requestData.setBeneficiarySwift(bene.getBeneficiarySwift());
		requestData.setTransactionDateTime(bene.getTransactionDateTime());

		requestData.setCustID(leftPadWithZero(account.getId()));
		/**
		 * Changes made for to set unique TransactionID by concatenate PaymentOutID and
		 * TradeAccountNumber- Saylee
		 */
		requestData.setTransactionID(String.valueOf(fundsOutRequest.getFundsOutId())
				+ (String.valueOf(fundsOutRequest.getTrade().getTradeAccountNumber())));
		// ??
		requestData.setCurrency(fundsOutRequest.getTrade().getBuyCurrency());
		// ??
		requestData.setCustomerAccountNumber(fundsOutRequest.getTrade().getTradeAccountNumber());
		requestData.setCustomerFirstName(contact.getFirstName());
		requestData.setCustomerLastName(contact.getLastName());
		requestData.setValueDate(fundsOutRequest.getTrade().getValueDate());
		requestData.setoPICreatedDate(fundsOutRequest.getBeneficiary().getOpiCreatedDate());
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
			requestData.setOsDateAndTime(deviceInfo.getOsDateAndTime());
			requestData.setOsName(deviceInfo.getOsName());
			requestData.setBrowserType(deviceInfo.getBrowserName());
		}
		requestData.setDealId(fundsOutRequest.getTrade().getContractNumber());

		requestData.setId(contact.getId());

		/** set fraugster signup score **/
		String fraugsterSignupScore = (String) fundsOutRequest.getAdditionalAttribute("FraugsterSignupScore");
		if (null != fraugsterSignupScore) {
			requestData.setCustSignupScore(Float.parseFloat(fraugsterSignupScore));
		}

		return requestData;
	}
	
	/**
	 * Transform payment out response.
	 *
	 * @param exchange the exchange
	 */
	private void transformPaymentOutResponse(MessageExchange exchange) {
		FraugsterPaymentsOutResponse fResponse = (FraugsterPaymentsOutResponse) exchange.getResponse();
		FraugsterPaymentsOutRequest request = exchange.getRequest(FraugsterPaymentsOutRequest.class);

		if (fResponse == null
				|| fResponse.getContactResponses().get(0).getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
			fResponse = crateServiceDownResponse(exchange, request);
		}
		if (Constants.SUCCESS.equalsIgnoreCase(fResponse.getStatus()))
			fResponse.setStatus(ServiceStatus.PASS.name());
	
		List<FraugsterPaymentsOutContactResponse> identityResponse = fResponse.getContactResponses();
		
			for (FraugsterPaymentsOutContactResponse response : identityResponse) {
				if (identityResponse != null && !response.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
					if (Constants.SUCCESS.equalsIgnoreCase(response.getStatus())) {
						response.setStatus(ServiceStatus.PASS.name());
					}
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
						EntityEnum.CONTACT.name(), response.getId());
				readResponse(eventServiceLog, response);
			}
		}
	}
	
	/**
	 * Crate service down response.
	 *
	 * @param exchange the exchange
	 * @param request the request
	 * @return the fraugster payments out response
	 */
	private FraugsterPaymentsOutResponse crateServiceDownResponse(MessageExchange exchange,
			FraugsterPaymentsOutRequest request) {
		FraugsterPaymentsOutResponse fResponse;
		fResponse = new FraugsterPaymentsOutResponse();
		List<FraugsterPaymentsOutContactResponse> conactResponseList = new ArrayList<>();
		FraugsterSummary fraugsterSummary = new FraugsterSummary();
		
		for (FraugsterPaymentsOutContactRequest contact : request.getContactRequests()) {

			FraugsterPaymentsOutContactResponse conactResponse = new FraugsterPaymentsOutContactResponse();
			EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
					EntityEnum.CONTACT.name(), contact.getId());

			conactResponse.setId(contact.getId());
			conactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			conactResponseList.add(conactResponse);
			fResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			fResponse.setContactResponses(null);
			fraugsterSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			fraugsterSummary.setFrgTransId(Constants.NOT_AVAILABLE);
			fraugsterSummary.setScore(Constants.NOT_AVAILABLE);
			eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(conactResponse));
		}
		fResponse.setContactResponses(conactResponseList);
		exchange.setResponse(fResponse);
		return fResponse;
	}
}
