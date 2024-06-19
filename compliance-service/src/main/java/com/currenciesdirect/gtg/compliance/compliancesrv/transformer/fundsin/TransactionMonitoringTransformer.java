package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsin;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentMethodEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
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
 * The Class TransactionMonitoringTransformer.
 */
public class TransactionMonitoringTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringTransformer.class);
	
	/** The funds in DB service impl. */
	@Autowired
	protected FundsInDBServiceImpl fundsInDBServiceImpl;
	
	/** The i comm hub service impl. */
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;

	@Autowired
	protected NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;
	
	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		try {
			if(message.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.FUNDS_IN) {
				transformNewPaymentInRequest(message);
			} else if(message.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.DELETE_OPI) {
				transformDeleteOpiPaymentInRequest(message); // Add for AT-4699
			}else{
				transformRepeatCheckPaymentInRequest(message);
			}
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * Transform new payment in request.
	 *
	 * @param message the message
	 */
	private void transformNewPaymentInRequest(Message<MessageContext> message) {
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) messageExchange.getRequest();
			FundsInCreateResponse fundsInCreateResponse = (FundsInCreateResponse) messageExchange.getResponse(); //AT-4715
			Account account = (Account) fundsInCreateRequest.getAdditionalAttribute("account");
			Contact contact = (Contact) fundsInCreateRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest = new TransactionMonitoringPaymentsInRequest();
			
			MessageExchange blackListexchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			MessageExchange sanctionExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			MessageExchange fraugsterExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			MessageExchange customExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);

			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			
			Integer accountTMFlag = (Integer) fundsInCreateRequest.getAdditionalAttribute(Constants.ACCOUNT_TM_FLAG);
			
			if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4) {
				transactionMonitoringPaymentsInRequest = createServiceRequest(fundsInCreateRequest,
						messageExchange.getServiceInterface().name());
				transactionMonitoringPaymentsInRequest.setAccountId(account.getId());
				transactionMonitoringPaymentsInRequest.setContactId(contact.getId());
				transactionMonitoringPaymentsInRequest.setCreatedBy(token.getUserID());
				transformComplianceLog(account.getId(), transactionMonitoringPaymentsInRequest); //AT-5578
				
				String stpFlag = fundsInCreateRequest.getAdditionalAttribute("AtlasSTPFlag").toString();
				if(accountTMFlag == 4)
					stpFlag += ";DCP"; //AT-5293
				transactionMonitoringPaymentsInRequest.setAtlasSTPFlag(stpFlag);

				transformTransactionMonitoringRequestSetAllChecksStatus(account, contact,
						transactionMonitoringPaymentsInRequest, blackListexchange, sanctionExchange, fraugsterExchange,
						customExchange);

				transformTransactionMonitoringRequestSetWatchlist(contact.getId(), transactionMonitoringPaymentsInRequest);
			}
			
			transactionMonitoringPaymentsInRequest.setAccountTMFlag(accountTMFlag);
			transactionMonitoringPaymentsInRequest.setFundsInId(fundsInCreateRequest.getFundsInId());
			transactionMonitoringPaymentsInRequest.setRequestType("payment_in");
			transactionMonitoringPaymentsInRequest.addAttribute(Constants.ACCOUNT_TM_FLAG,accountTMFlag);
			
			transactionMonitoringPaymentsInRequest.setEtailer(
					(account.getCompany() != null) ? account.getCompany().getEtailer() : Boolean.toString(false));// AT-4815
			
			TransactionMonitoringPaymentInResponse transactionMonitoringPaymentInResponse = new TransactionMonitoringPaymentInResponse();
			transactionMonitoringPaymentInResponse.setId(fundsInCreateRequest.getFundsInId());
			transactionMonitoringPaymentInResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			
			TransactionMonitoringPaymentsSummary transactionMonitoringPaymentInSummary = new TransactionMonitoringPaymentsSummary();
			transactionMonitoringPaymentInSummary.setStatus(transactionMonitoringPaymentInResponse.getStatus());

			if(fundsInCreateResponse.getStatus() != null)//AT-4715
				transactionMonitoringPaymentsInRequest.setUpdateStatus(fundsInCreateResponse.getStatus());
			else
				transactionMonitoringPaymentsInRequest.setUpdateStatus(FundsInComplianceStatus.HOLD.name());
			
			MessageExchange ccExchange = createMessageExchange(transactionMonitoringPaymentsInRequest, transactionMonitoringPaymentInResponse,
					ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
			ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
					ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSIN, fundsInCreateRequest.getFundsInId(),
					account.getVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(), transactionMonitoringPaymentInResponse,
					transactionMonitoringPaymentInSummary));

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring New Payment In transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
	}

	/**
	 * Transform repeat check payment in request.
	 *
	 * @param message the message
	 */
	public void transformRepeatCheckPaymentInRequest(Message<MessageContext> message){
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fundsIn = (FundsInCreateRequest) messageExchange.getRequest();
			Integer tradePayId = fundsIn.getTrade().getPaymentFundsInId();
			transformCommonRequest(message, token, messageExchange, tradePayId);
			
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring Repeat Check transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
	}
	
	/**
	 * Transform delete opi payment in request.
	 *
	 * @param message the message
	 */
	public void transformDeleteOpiPaymentInRequest(Message<MessageContext> message){
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsInDeleteRequest fundsInDel = (FundsInDeleteRequest) messageExchange.getRequest();
			Integer tradePayId = fundsInDel.getPaymentFundsInId();
			transformCommonRequest(message, token, messageExchange, tradePayId);
			
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring Delete Opi Check transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
	}

	/**
	 * @param message
	 * @param token
	 * @param messageExchange
	 * @param fundsIn
	 * @throws ComplianceException
	 */
	private void transformCommonRequest(Message<MessageContext> message, UserProfile token,
			MessageExchange messageExchange, Integer tradePayId) throws ComplianceException {
		TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest = new TransactionMonitoringPaymentsInRequest();
		
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute(Constants.FIELD_EVENTID);
		
		FundsInCreateRequest fundsInCreateRequest = fundsInDBServiceImpl.getFundsInDetailsForIntuition(tradePayId);
		
		Integer accTMFlag = (Integer) fundsInCreateRequest.getAdditionalAttribute(Constants.ACCOUNT_TM_FLAG);
		
		if (accTMFlag == 1 || accTMFlag == 2 || accTMFlag == 4) {
			
			transactionMonitoringPaymentsInRequest = createServiceRequest(
					fundsInCreateRequest, messageExchange.getServiceInterface().name());
			transactionMonitoringPaymentsInRequest.setAccountId(fundsInCreateRequest.getAccId());
			transactionMonitoringPaymentsInRequest.setContactId(fundsInCreateRequest.getContactId());
			transactionMonitoringPaymentsInRequest.setCreatedBy(token.getUserID());
			transformAllCheckResponse(fundsInCreateRequest, transactionMonitoringPaymentsInRequest);
			String countryCheckStatus = fundsInDBServiceImpl.getCountryCheckStatus(fundsInCreateRequest);
			transactionMonitoringPaymentsInRequest.setCountryCheckStatus(countryCheckStatus);
			transformTransactionMonitoringRequestSetWatchlist(fundsInCreateRequest.getContactId(), transactionMonitoringPaymentsInRequest);		
			transactionMonitoringPaymentsInRequest.setUpdateStatus((String) fundsInCreateRequest.getAdditionalAttribute("status"));
			String statusUpdateReason = fundsInDBServiceImpl.getPaymentInStatusUpdateReason(fundsInCreateRequest);
			transactionMonitoringPaymentsInRequest.setStatusUpdateReason(statusUpdateReason);
			transactionMonitoringPaymentsInRequest.setAtlasSTPFlag((String) fundsInCreateRequest.getAdditionalAttribute("STPFlag"));
			
			if(message.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.RECHECK_FAILURES) {
				setUpdateCheckStatus(message, transactionMonitoringPaymentsInRequest, fundsInCreateRequest);
			}
		}
		
		transactionMonitoringPaymentsInRequest.setAccountTMFlag(accTMFlag);
		transactionMonitoringPaymentsInRequest.setFundsInId(fundsInCreateRequest.getFundsInId());
		transactionMonitoringPaymentsInRequest.setRequestType("payment_in_update");
		transactionMonitoringPaymentsInRequest
				.setEtailer(((String) fundsInCreateRequest.getAdditionalAttribute(Constants.ETAILER) != null)
						? (String) fundsInCreateRequest.getAdditionalAttribute(Constants.ETAILER)
						: Boolean.toString(false)); // Added for AT-5310

		TransactionMonitoringPaymentInResponse transactionMonitoringPaymentInResponse = new TransactionMonitoringPaymentInResponse();
		transactionMonitoringPaymentInResponse.setId(fundsInCreateRequest.getFundsInId());
		transactionMonitoringPaymentInResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
		
		TransactionMonitoringPaymentsSummary transactionMonitoringPaymentInSummary = new TransactionMonitoringPaymentsSummary();
		transactionMonitoringPaymentInSummary.setStatus(transactionMonitoringPaymentInResponse.getStatus());
		
		Integer accountVersion = (Integer) fundsInCreateRequest.getAdditionalAttribute("AccountVersion");

		MessageExchange ccExchange = createMessageExchange(transactionMonitoringPaymentsInRequest, transactionMonitoringPaymentInResponse,
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSIN, fundsInCreateRequest.getFundsInId(),
				accountVersion, EntityEnum.ACCOUNT.name(), token.getUserID(), transactionMonitoringPaymentInResponse,
				transactionMonitoringPaymentInSummary));
		
		transactionMonitoringPaymentsInRequest.addAttribute(Constants.ACCOUNT_TM_FLAG,accTMFlag);
		
		message.getPayload().appendMessageExchange(ccExchange);
	}
	
	/**
	 * @param message
	 * @param transactionMonitoringPaymentsInRequest
	 * @param fundsInCreateRequest
	 */
	private void setUpdateCheckStatus(Message<MessageContext> message,
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest,
			FundsInCreateRequest fundsInCreateRequest) {
		if (message.getPayload().isInternalRuleServiceEligible()) {
			MessageExchange blackListexchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			EventServiceLog blacklistLog = blackListexchange.getEventServiceLog(
					ServiceTypeEnum.BLACK_LIST_SERVICE, EntityEnum.CONTACT.name(),
					fundsInCreateRequest.getFundsInId());
			EventServiceLog countryCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
					EntityEnum.CONTACT.name(), fundsInCreateRequest.getFundsInId());

			transactionMonitoringPaymentsInRequest.setBlacklistStatus(blacklistLog.getStatus());
			transactionMonitoringPaymentsInRequest.setCountryCheckStatus(countryCheckLog.getStatus());
		}
		
		if (message.getPayload().isSanctionEligible()) {
			MessageExchange sanctionExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			EventServiceLog sanctionLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
					EntityEnum.DEBTOR.name(), fundsInCreateRequest.getFundsInId());
			transactionMonitoringPaymentsInRequest.setSanctionContactStatus(sanctionLog.getStatus());
			transactionMonitoringPaymentsInRequest.setSanction3rdPartyStatus(sanctionLog.getStatus());
		}
		
		if (message.getPayload().isFraugsterEligible()) {
			MessageExchange fraugsterExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			EventServiceLog fraugsterLog = fraugsterExchange.getEventServiceLog(
					ServiceTypeEnum.FRAUGSTER_SERVICE, EntityEnum.CONTACT.name(),
					fundsInCreateRequest.getContactId());
			transactionMonitoringPaymentsInRequest.setFraudPredictStatus(fraugsterLog.getStatus());
		}
		
		if (message.getPayload().isCustomCheckEligible()) {
			MessageExchange customExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			EventServiceLog customLog = customExchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
					EntityEnum.ACCOUNT.name(), fundsInCreateRequest.getAccId());
			transactionMonitoringPaymentsInRequest.setCustomCheckStatus(customLog.getStatus());
		}
	}
	
	/**
	 * Transform all check response.
	 *
	 * @param fundsInCreateRequest the funds in create request
	 * @param transactionMonitoringPaymentsInRequest the transaction monitoring payments in request
	 */
	private void transformAllCheckResponse(FundsInCreateRequest fundsInCreateRequest,
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest) {
		
		transactionMonitoringPaymentsInRequest
				.setBlacklistStatus((String) fundsInCreateRequest.getAdditionalAttribute("BLACKLIST_STATUS"));
		transactionMonitoringPaymentsInRequest
				.setSanctionContactStatus((String) fundsInCreateRequest.getAdditionalAttribute("SANCTION_STATUS"));
		transactionMonitoringPaymentsInRequest
				.setCustomCheckStatus((String) fundsInCreateRequest.getAdditionalAttribute("CUSTOM_CHECK_STATUS"));
		transactionMonitoringPaymentsInRequest
				.setFraudPredictStatus((String) fundsInCreateRequest.getAdditionalAttribute("FRAUGSTER_STATUS"));
		
	}

	/**
	 * Creates the service request.
	 *
	 * @param fundsInRequest the funds in request
	 * @param type the type
	 * @return the transaction monitoring payments in request
	 */
	private TransactionMonitoringPaymentsInRequest createServiceRequest(FundsInCreateRequest fundsInRequest, String type) {
		TransactionMonitoringPaymentsInRequest request = new TransactionMonitoringPaymentsInRequest();
		
		request.setAccountNumber(fundsInRequest.getTradeAccountNumber());
		request.setTransactionId(fundsInRequest.getPaymentFundsInId());
		request.setTransactionType(fundsInRequest.getTrade().getTransactionType());
		request.setTimeStamp(fundsInRequest.getTrade().getPaymentTime());
		request.setOrgCode(fundsInRequest.getOrgCode());
		request.setSourceApplication(fundsInRequest.getSourceApplication());
		request.setTradeAccountNumber(fundsInRequest.getTradeAccountNumber());
		request.setTradeContactId(fundsInRequest.getTrade().getTradeContactId());
		request.setCustType(fundsInRequest.getCustType());
		request.setPurposeOfTrade(fundsInRequest.getTrade().getPurposeOfTrade());
		request.setSellingAmount(fundsInRequest.getTrade().getSellingAmount()); 
		request.setSellingAmountBaseValue(fundsInRequest.getTrade().getSellingAmountBaseValue());
		request.setTransactionCurrency(fundsInRequest.getTrade().getTransactionCurrency());
		request.setContractNumber(fundsInRequest.getTrade().getContractNumber());
		request.setPaymentMethod(setPaymentInMethodForIntuition(fundsInRequest.getTrade().getPaymentMethod()));
		request.setCcFirstName(fundsInRequest.getTrade().getCcFirstName());
		request.setBillAddressLine(fundsInRequest.getTrade().getBillAddressLine());
		request.setAvsResult(fundsInRequest.getTrade().getAvsResult());
		request.setIsThreeds(fundsInRequest.getTrade().getIsThreeds());
		request.setDebitCardAddedDate(fundsInRequest.getTrade().getDebitCardAddedDate());
		request.setAvTradeValue(fundsInRequest.getTrade().getAvTradeValue());
		request.setAvTradeFrequency(fundsInRequest.getTrade().getAvTradeFrequency());
		request.setThirdPartyPayment(fundsInRequest.getTrade().getThirdPartyPayment());
		request.setTurnover(fundsInRequest.getTrade().getTurnover());
		request.setDebtorName(fundsInRequest.getTrade().getDebtorName());
		request.setDebatorAccountNumber(fundsInRequest.getTrade().getDebtorAccountNumber());
		request.setBillAdZip(fundsInRequest.getTrade().getBillAdZip());
		request.setCountryOfFund(fundsInRequest.getTrade().getCountryOfFund());
		request.setCustomerLegalEntity(fundsInRequest.getTrade().getCustLegalEntity());
		request.setCvcResult(fundsInRequest.getTrade().getCvcResult());
		//AT-5337
		request.setInitiatingParentContact(fundsInRequest.getTrade().getInitiatingParentContact());
		
		if(fundsInRequest.getDeviceInfo() != null) {
			request.setScreenResolution(fundsInRequest.getDeviceInfo().getScreenResolution());
			request.setBrowserType(fundsInRequest.getDeviceInfo().getBrowserName());
			request.setBrowserVersion(fundsInRequest.getDeviceInfo().getBrowserMajorVersion());
			request.setDeviceType(fundsInRequest.getDeviceInfo().getDeviceType());
			request.setDeviceName(fundsInRequest.getDeviceInfo().getDeviceName());
			request.setDeviceVersion(fundsInRequest.getDeviceInfo().getDeviceVersion());
			request.setDeviceId(fundsInRequest.getDeviceInfo().getDeviceId());
			request.setDeviceManufacturer(fundsInRequest.getDeviceInfo().getDeviceManufacturer());
			request.setOsType(fundsInRequest.getDeviceInfo().getOsName());
			request.setBrowserLanguage(fundsInRequest.getDeviceInfo().getBrowserLanguage());
			request.setBrowserOnline(fundsInRequest.getDeviceInfo().getBrowserOnline());
		}
		
		if(fundsInRequest.getRiskScore() != null) {
			request.setMessage(fundsInRequest.getRiskScore().getMessage());
			request.setRGID(fundsInRequest.getTrade().getRgTransId());
			if(fundsInRequest.getRiskScore().getTScore() != null) {
				request.settScore(fundsInRequest.getRiskScore().getTScore());
			}
			if(fundsInRequest.getRiskScore().getTRisk() != null) {
				request.settRisk(fundsInRequest.getRiskScore().getTRisk());
			}
		}
		request.setOsrId(fundsInRequest.getOsrId());
		request.setType(type);
		request.setFundsInId(fundsInRequest.getFundsInId());
		if(fundsInRequest.getFraudSight() != null) {
			request.setThreeDsOutcome(fundsInRequest.getFraudSight().getThreedsVersion());
			request.setFraudsightReason(fundsInRequest.getFraudSight().getFsReasonCodes());
			request.setFraudsightRiskLevel(fundsInRequest.getFraudSight().getFsMessage());
		}
		
		setCreditorBankDetailsForIn(request, fundsInRequest);
		
		return request;
	}
	
	
	/**
	 * Transform transaction monitoring request set all checks status.
	 *
	 * @param account the account
	 * @param contact the contact
	 * @param transactionMonitoringPaymentsInRequest the transaction monitoring payments in request
	 * @param blackListexchange the black listexchange
	 * @param sanctionExchange the sanction exchange
	 * @param fraugsterExchange the fraugster exchange
	 * @param customExchange the custom exchange
	 */
	private void transformTransactionMonitoringRequestSetAllChecksStatus(Account account, Contact contact,
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest, MessageExchange blackListexchange,
			MessageExchange sanctionExchange, MessageExchange fraugsterExchange, MessageExchange customExchange) {

		EventServiceLog blacklistLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				EntityEnum.CONTACT.name(), transactionMonitoringPaymentsInRequest.getFundsInId());
		EventServiceLog countryCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
				EntityEnum.CONTACT.name(), transactionMonitoringPaymentsInRequest.getFundsInId());
		EventServiceLog sanctionLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.DEBTOR.name(), transactionMonitoringPaymentsInRequest.getFundsInId());
		EventServiceLog fraugsterLog = fraugsterExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog customLog = customExchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
				EntityEnum.ACCOUNT.name(), account.getId());
		
		transactionMonitoringPaymentsInRequest.setBlacklistStatus(blacklistLog.getStatus());
		transactionMonitoringPaymentsInRequest.setCountryCheckStatus(countryCheckLog.getStatus());
		transactionMonitoringPaymentsInRequest.setSanctionContactStatus(sanctionLog.getStatus());
		transactionMonitoringPaymentsInRequest.setSanction3rdPartyStatus(sanctionLog.getStatus());
		transactionMonitoringPaymentsInRequest.setFraudPredictStatus(fraugsterLog.getStatus());
		transactionMonitoringPaymentsInRequest.setCustomCheckStatus(customLog.getStatus());

	}
	
	
	/**
	 * Transform transaction monitoring request set watchlist.
	 *
	 * @param contactID the contact ID
	 * @param transactionMonitoringPaymentsInRequest the transaction monitoring payments in request
	 * @throws ComplianceException the compliance exception
	 */
	private void transformTransactionMonitoringRequestSetWatchlist(Integer contactID,
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest) throws ComplianceException {

		List<String> watchList = fundsInDBServiceImpl.getContactWatchlist(contactID);
		transactionMonitoringPaymentsInRequest.setWatchlist(watchList);

	}
	

	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange exchange = null;
		try {
			exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
			TransactionMonitoringPaymentInResponse response = (TransactionMonitoringPaymentInResponse) exchange.getResponse();
			TransactionMonitoringPaymentsInRequest request = exchange.getRequest(TransactionMonitoringPaymentsInRequest.class);
			/*
			 * FundsInCreateResponse fundsInCreateResponse = (FundsInCreateResponse)
			 * message.getPayload() .getGatewayMessageExchange().getResponse();
			 */
   		     

			if (response != null && !response.getStatus().equalsIgnoreCase(Constants.SERVICE_FAILURE)
					&& !response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = response.getTransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsInId());
				summary.setStatus(tmProviderResponse.getStatus());
				summary.setFundsInId(request.getFundsInId());
				summary.setRuleScore(tmProviderResponse.getRuleScore());
				summary.setRuleRiskLevel(tmProviderResponse.getRuleRiskLevel());
				summary.setClientRiskLevel(tmProviderResponse.getClientRiskLevel());
				summary.setClientRiskScore(tmProviderResponse.getClientRiskScore());
				summary.setCorrelationId(response.getCorrelationId());
				summary.setUserId(tmProviderResponse.getUserId());
				summary.setAction(tmProviderResponse.getAction());
				eventServiceLog.setStatus(response.getStatus());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				tmProviderResponse.setCorrelationId(response.getCorrelationId());
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithNull(tmProviderResponse));
				response.addAttribute("ClientRiskLevel", tmProviderResponse.getClientRiskLevel());
			}else if(response != null && response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsInId());
				summary.setStatus(Constants.NOT_REQUIRED);
				summary.setFundsOutId(request.getFundsInId());
				tmProviderResponse.setStatus(Constants.NOT_REQUIRED);
				eventServiceLog.setStatus(response.getStatus());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
						
			}else if(response != null && response.getHttpStatus() == 400) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsInId());
				summary.setStatus(response.getStatus());
				summary.setFundsInId(request.getFundsInId());
				summary.setCorrelationId(response.getCorrelationId());
				eventServiceLog.setStatus(response.getStatus());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				tmProviderResponse.setCorrelationId(response.getCorrelationId());
				tmProviderResponse.setStatus(response.getStatus());
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithNull(tmProviderResponse));
				
				sendAlertEmailForInvalidRequest(request.getContractNumber(), "PaymentIN", response);
			}else {
				response = createDefaultFailureResponse(exchange, request);
			}
		
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring transformer class : transformResponse -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}


	/**
	 * Creates the default failure response.
	 *
	 * @param exchange the exchange
	 * @param request the request
	 * @return the transaction monitoring payment in response
	 */
	private TransactionMonitoringPaymentInResponse createDefaultFailureResponse(MessageExchange exchange,
			TransactionMonitoringPaymentsInRequest request) {
		TransactionMonitoringPaymentInResponse response = new TransactionMonitoringPaymentInResponse();
		TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
		TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
		
		EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE,
				EntityEnum.ACCOUNT.name(), request.getFundsInId());
		response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		summary.setFundsInId(response.getId());
		tmProviderResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
		
		exchange.setResponse(response);
		return response;
	}
	
	
	/**
	 * Send alert email for invalid request.
	 *
	 * @param contractNumber the contract number
	 * @param type the type
	 */
	private void sendAlertEmailForInvalidRequest(String contractNumber, String type, TransactionMonitoringPaymentInResponse response) {

		SendEmailRequestForTM sendTMEmailRequest = new SendEmailRequestForTM();
		EmailHeaderForTM header = new EmailHeaderForTM();
		EmailPayloadForTM payload = new EmailPayloadForTM();

		header.setSourceSystem("Atlas");
		header.setOrgCode("Currencies Direct");
		header.setLegalEntity("CDLGB");

		String email = System.getProperty("intuition.sendTo");
		List<String> list = Arrays.asList(email.split(","));

		payload.setEmailId(list);
		payload.setSubject("Intuition request failed");
		payload.setEmailContent(type +" request for Contract Number "+ contractNumber +" to Intuition failed, due to intuition error desc : "+
				response.getErrorDesc() +". <BR>"+response.getErrorDescription()+". <br> Please alert dev team.");
		payload.setFromEmilId(System.getProperty("intuition.sendFrom"));
		payload.setReplyToEmailId(System.getProperty("intuition.sendFrom"));

		sendTMEmailRequest.setTmHeader(header);
		sendTMEmailRequest.setTmPayload(payload);

		iCommHubServiceImpl.sendEmailForTMAlert(sendTMEmailRequest, true);

	}
	
	/**
	 * Sets the creditor bank details for in.
	 *
	 * @param transactionMonitoringPaymentsInRequest the transaction monitoring payments in request
	 * @param fundsInCreateRequest the funds in create request
	 */
	//AT-4627
	private void setCreditorBankDetailsForIn(
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest,
			FundsInCreateRequest fRequest) {
		if (!PaymentMethodEnum.FOA.getDatabasePaymentMethod().equalsIgnoreCase(fRequest.getTrade().getPaymentMethod())
				&& !PaymentMethodEnum.SWITCHDEBIT.getDatabasePaymentMethod()
						.equalsIgnoreCase(fRequest.getTrade().getPaymentMethod())
				&& !PaymentMethodEnum.DIRECTDEBIT.getDatabasePaymentMethod()
						.equalsIgnoreCase(fRequest.getTrade().getPaymentMethod())
				&& !PaymentMethodEnum.WALLET.getDatabasePaymentMethod()
						.equalsIgnoreCase(fRequest.getTrade().getPaymentMethod())
				&& !PaymentMethodEnum.RETURNOFFUNDS.getDatabasePaymentMethod()
						.equalsIgnoreCase(fRequest.getTrade().getPaymentMethod())) {
			transactionMonitoringPaymentsInRequest.setCreditorState(fRequest.getTrade().getDebtorState());
			transactionMonitoringPaymentsInRequest.setCreditorAccountName(fRequest.getTrade().getCreditorAccountName());
			transactionMonitoringPaymentsInRequest
					.setCreditorBankAccountNumber(fRequest.getTrade().getCreditorBankAccountNumber());
			transactionMonitoringPaymentsInRequest.setCreditorBankName(fRequest.getTrade().getCreditorBankName());
		}

	}
	
	/**
	 * Sets the payment in method for intuition.
	 *
	 * @param paymentMethod the payment method
	 * @return the string
	 */
	//AT-5138
	private String setPaymentInMethodForIntuition(String paymentMethod) {
		String formattedMethod = paymentMethod;
		if (!PaymentMethodEnum.FOA.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)
				&& !PaymentMethodEnum.SWITCHDEBIT.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)
				&& !PaymentMethodEnum.DIRECTDEBIT.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)
				&& !PaymentMethodEnum.WALLET.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)
				&& !PaymentMethodEnum.RETURNOFFUNDS.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)
				&& !PaymentMethodEnum.CHEQUEDRAFT.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)
				&& !PaymentMethodEnum.CHEQUE.getDatabasePaymentMethod().equalsIgnoreCase(paymentMethod)) {
			formattedMethod = "BXF-" + paymentMethod;
		} 
		
		return formattedMethod;
	}
	
	//AT-5578
	private void transformComplianceLog(Integer accountID,
			TransactionMonitoringPaymentsInRequest transactionMonitoringPaymentsInRequest) throws ComplianceException {
		String complianceLog = newRegistrationDBServiceImpl.getComplianceLogForIntuition(accountID);
		transactionMonitoringPaymentsInRequest.setComplianceLog(complianceLog);
	}
}
