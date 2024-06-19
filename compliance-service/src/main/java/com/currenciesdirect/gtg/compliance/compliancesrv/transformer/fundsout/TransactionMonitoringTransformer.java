package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.fundsout;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeaderForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayloadForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
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
public class TransactionMonitoringTransformer extends AbstractTransformer{

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringTransformer.class);
	
	/** The funds in DB service impl. */
	@Autowired
	protected FundsOutDBServiceImpl fundsOutDBServiceImpl;

	/** The i comm hub service impl. */
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;
	
	@Autowired
	protected NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;
	
	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();
	
	/** The Constant ACCOUNT_TM_FLAG. */
	private static final String ACCOUNT_TM_FLAG = "AccountTMFlag";
	
	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		try {
			
			if(message.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.FUNDS_OUT) {
				tranformNewPaymentOutRequest(message);
			} else if(message.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.DELETE_OPI) {
				transformDeleteOpiPaymentOutRequest(message); // Add for AT-4699
			} else{
				tranformRepeatCheckPaymentOutRequest(message);
			}
			
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	

	private void tranformNewPaymentOutRequest(Message<MessageContext> message) throws ComplianceException {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
		FundsOutResponse fundsOutResponse = (FundsOutResponse) messageExchange.getResponse(); //AT-4715
		Account account = (Account) fundsOutRequest.getAdditionalAttribute("account");
		Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
		TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest = new TransactionMonitoringPaymentsOutRequest();
		
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute(Constants.FIELD_EVENTID);
		
		Integer accountTMFlag = (Integer) fundsOutRequest.getAdditionalAttribute(ACCOUNT_TM_FLAG);
		
		if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4) {
			transactionMonitoringPaymentsOutRequest = createServiceRequest(
					fundsOutRequest, messageExchange.getServiceInterface().name());
			transactionMonitoringPaymentsOutRequest.setAccountId(account.getId());
			transactionMonitoringPaymentsOutRequest.setContactId(contact.getId());
			transactionMonitoringPaymentsOutRequest.setCreatedBy(token.getUserID());
			transactionMonitoringPaymentsOutRequest.setCreditFrom(getCreditFromValue(fundsOutRequest,
					account.getAffiliateName()));// Add For AT-5454
			transformComplianceLog(account.getId(), transactionMonitoringPaymentsOutRequest); //AT-5578
			String stpFlag = fundsOutRequest.getAdditionalAttribute("AtlasSTPFlag").toString();
			if(accountTMFlag == 4)
				stpFlag += ";DCP"; //AT-5294
			transactionMonitoringPaymentsOutRequest.setAtlasSTPFlag(stpFlag);

			transformTransactionMonitoringRequestSetAllChecksStatus(message, contact,
					transactionMonitoringPaymentsOutRequest, fundsOutRequest);

			transformTransactionMonitoringRequestSetWatchlist(contact.getId(), transactionMonitoringPaymentsOutRequest);
		}
		
		transactionMonitoringPaymentsOutRequest.setAccountTMFlag(accountTMFlag);
		transactionMonitoringPaymentsOutRequest.setFundsOutId(fundsOutRequest.getFundsOutId());
		transactionMonitoringPaymentsOutRequest.setRequestType("payment_out");
		transactionMonitoringPaymentsOutRequest.addAttribute(ACCOUNT_TM_FLAG, accountTMFlag);
		transactionMonitoringPaymentsOutRequest.setEtailer(
				(account.getCompany() != null) ? account.getCompany().getEtailer() : Boolean.toString(false)); // AT-4815

		TransactionMonitoringPaymentOutResponse transactionMonitoringPaymentOutResponse = new TransactionMonitoringPaymentOutResponse();
		transactionMonitoringPaymentOutResponse.setId(fundsOutRequest.getFundsOutId());
		transactionMonitoringPaymentOutResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
		
		TransactionMonitoringPaymentsSummary transactionMonitoringPaymentOutSummary = new TransactionMonitoringPaymentsSummary();
		transactionMonitoringPaymentOutSummary.setStatus(transactionMonitoringPaymentOutResponse.getStatus());

		if(fundsOutResponse.getStatus() != null)//AT-4716
			transactionMonitoringPaymentsOutRequest.setUpdateStatus(fundsOutResponse.getStatus());
		else
			transactionMonitoringPaymentsOutRequest.setUpdateStatus(FundsOutComplianceStatus.HOLD.name());
		
		MessageExchange ccExchange = createMessageExchange(transactionMonitoringPaymentsOutRequest, transactionMonitoringPaymentOutResponse,
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSOUT, fundsOutRequest.getFundsOutId(),
				account.getVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(), transactionMonitoringPaymentOutResponse,
				transactionMonitoringPaymentOutSummary));

		message.getPayload().appendMessageExchange(ccExchange);
	}

	//AT-4451
	private void tranformRepeatCheckPaymentOutRequest(Message<MessageContext> message) throws ComplianceException {
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutRequest fundsOut = (FundsOutRequest) messageExchange.getRequest();
			Integer tradePayId = fundsOut.getBeneficiary().getPaymentFundsoutId();
			transformCommonRequest(message, token, messageExchange, tradePayId);
			
		} catch (Exception e) {
			LOG.error("Error in Transaction Monitoring Repeat Check transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		
	}

	private void transformDeleteOpiPaymentOutRequest(Message<MessageContext> message) throws ComplianceException {
		try {
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutDeleteRequest fundsOutDeleteRequest = (FundsOutDeleteRequest) messageExchange.getRequest();
			Integer tradePayId = fundsOutDeleteRequest.getPaymentFundsoutId();
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
	 * @param fundsOut
	 * @throws ComplianceException
	 */
	private void transformCommonRequest(Message<MessageContext> message, UserProfile token,
			MessageExchange messageExchange, Integer tradePayId) throws ComplianceException {
		TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest = new TransactionMonitoringPaymentsOutRequest();
		
		FundsOutRequest fundsOutRequest = fundsOutDBServiceImpl.getFundsOutDetailsForIntuition(tradePayId);
		
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute(Constants.FIELD_EVENTID);
		
		Integer accTMFlag = (Integer) fundsOutRequest.getAdditionalAttribute(ACCOUNT_TM_FLAG);
		
		if (accTMFlag == 1 || accTMFlag == 2 || accTMFlag == 4) {
			transactionMonitoringPaymentsOutRequest = createServiceRequest(fundsOutRequest,
					messageExchange.getServiceInterface().name());
			transactionMonitoringPaymentsOutRequest.setAccountId(fundsOutRequest.getAccId());
			transactionMonitoringPaymentsOutRequest.setContactId(fundsOutRequest.getContactId());
			transactionMonitoringPaymentsOutRequest.setCreatedBy(token.getUserID());
			transactionMonitoringPaymentsOutRequest.setCreditFrom(getCreditFromValue(fundsOutRequest,
					(String) fundsOutRequest.getAdditionalAttribute("AffiliateName")));// Add For AT-5454
			transformAllCheckResponseForRepeatCheck(fundsOutRequest, transactionMonitoringPaymentsOutRequest);
			if (fundsOutRequest.getBeneficiary().getPaymentReference() != null
					&& fundsOutRequest.getBeneficiary().getPaymentReference().length() > 2) {
				fundsOutDBServiceImpl.getPaymentReferenceMatchKeywordForIntuition(
						fundsOutRequest.getBeneficiary().getBeneficiaryId(), transactionMonitoringPaymentsOutRequest);
			}
			transformTransactionMonitoringRequestSetWatchlist(fundsOutRequest.getContactId(),
					transactionMonitoringPaymentsOutRequest);
			transactionMonitoringPaymentsOutRequest.setUpdateStatus((String) fundsOutRequest.getAdditionalAttribute("status"));
			String statusUpdateReason = fundsOutDBServiceImpl.getStatusUpdateReasonForIntuition(fundsOutRequest);
			transactionMonitoringPaymentsOutRequest.setStatusUpdateReason(statusUpdateReason);
			transactionMonitoringPaymentsOutRequest.setAtlasSTPFlag((String) fundsOutRequest.getAdditionalAttribute("STPFlag"));
			
			if(message.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.RECHECK_FAILURES) {
				
				setUpdateCheckStatus(message, transactionMonitoringPaymentsOutRequest, fundsOutRequest);
			}
		}
		
		transactionMonitoringPaymentsOutRequest.setAccountTMFlag(accTMFlag);
		transactionMonitoringPaymentsOutRequest.setFundsOutId(fundsOutRequest.getFundsOutId());
		transactionMonitoringPaymentsOutRequest.setRequestType("payment_out_update");
		transactionMonitoringPaymentsOutRequest
				.setEtailer(((String) fundsOutRequest.getAdditionalAttribute(Constants.ETAILER) != null)
						? (String) fundsOutRequest.getAdditionalAttribute(Constants.ETAILER)
						: Boolean.toString(false)); //Added for AT-5310

		TransactionMonitoringPaymentOutResponse transactionMonitoringPaymentOutResponse = new TransactionMonitoringPaymentOutResponse();
		transactionMonitoringPaymentOutResponse.setId(fundsOutRequest.getFundsOutId());
		transactionMonitoringPaymentOutResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
		
		TransactionMonitoringPaymentsSummary transactionMonitoringPaymentOutSummary = new TransactionMonitoringPaymentsSummary();
		transactionMonitoringPaymentOutSummary.setStatus(transactionMonitoringPaymentOutResponse.getStatus());
		
		Integer accountVersion = (Integer) fundsOutRequest.getAdditionalAttribute("AccountVersion");

		MessageExchange ccExchange = createMessageExchange(transactionMonitoringPaymentsOutRequest, transactionMonitoringPaymentOutResponse,
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId,
				ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, ServiceProviderEnum.TRANSACTION_MONITORING_FUNDSOUT, fundsOutRequest.getFundsOutId(),
				accountVersion, EntityEnum.ACCOUNT.name(), token.getUserID(), transactionMonitoringPaymentOutResponse,
				transactionMonitoringPaymentOutSummary));
		
		transactionMonitoringPaymentsOutRequest.addAttribute(ACCOUNT_TM_FLAG, accTMFlag);
		
		message.getPayload().appendMessageExchange(ccExchange);
	}

	/**
	 * @param message
	 * @param transactionMonitoringPaymentsOutRequest
	 * @param fundsOutRequest
	 */
	private void setUpdateCheckStatus(Message<MessageContext> message,
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest,
			FundsOutRequest fundsOutRequest) {
		if (message.getPayload().isInternalRuleServiceEligible()) {
			MessageExchange blackListexchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			EventServiceLog blacklistLog = blackListexchange.getEventServiceLog(
					ServiceTypeEnum.BLACK_LIST_SERVICE, EntityEnum.BENEFICIARY.name(),
					fundsOutRequest.getBeneficiary().getBeneficiaryId());
			EventServiceLog countryCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
					EntityEnum.BENEFICIARY.name(), fundsOutRequest.getBeneficiary().getBeneficiaryId());
			EventServiceLog paymentReference = blackListexchange.getEventServiceLog(
					ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE, EntityEnum.BENEFICIARY.name(),
					fundsOutRequest.getBeneficiary().getBeneficiaryId());
			transactionMonitoringPaymentsOutRequest.setBlacklistStatus(blacklistLog.getStatus());
			transactionMonitoringPaymentsOutRequest.setCountryCheckStatus(countryCheckLog.getStatus());
			transactionMonitoringPaymentsOutRequest.setPaymentReferenceCheck(paymentReference.getStatus());
		}
		
		if (message.getPayload().isSanctionEligible()) {
			MessageExchange sanctionExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			EventServiceLog sanctionConLog = sanctionExchange.getEventServiceLog(
					ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(),
					fundsOutRequest.getContactId());
			EventServiceLog sanctionBenLog = sanctionExchange.getEventServiceLog(
					ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BENEFICIARY.name(),
					fundsOutRequest.getBeneficiary().getBeneficiaryId());
			EventServiceLog sanctionBankLog = sanctionExchange.getEventServiceLog(
					ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BANK.name(),
					fundsOutRequest.getBeneficiary().getBeneficiaryBankid());
			transactionMonitoringPaymentsOutRequest.setSanctionContactStatus(sanctionConLog.getStatus());
			transactionMonitoringPaymentsOutRequest.setSanctionBeneficiaryStatus(sanctionBenLog.getStatus());
			transactionMonitoringPaymentsOutRequest.setSanctionBankStatus(sanctionBankLog.getStatus());
		}
		
		if (message.getPayload().isFraugsterEligible()) {
			MessageExchange fraugsterExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			EventServiceLog fraugsterLog = fraugsterExchange.getEventServiceLog(
					ServiceTypeEnum.FRAUGSTER_SERVICE, EntityEnum.CONTACT.name(),
					fundsOutRequest.getContactId());
			transactionMonitoringPaymentsOutRequest.setFraudPredictStatus(fraugsterLog.getStatus());
		}
		
		if (message.getPayload().isCustomCheckEligible()) {
			MessageExchange customExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
			EventServiceLog customLog = customExchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
					EntityEnum.BENEFICIARY.name(), fundsOutRequest.getBeneficiary().getBeneficiaryId());
			transactionMonitoringPaymentsOutRequest.setCustomCheckStatus(customLog.getStatus());
		}
	}
	
	//AT-4451
	private void transformAllCheckResponseForRepeatCheck(FundsOutRequest fundsOutRequest,
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest) throws ComplianceException {
		String countryCheckStatus = fundsOutDBServiceImpl.getCountryCheckStatusForIntuition(fundsOutRequest);
		transactionMonitoringPaymentsOutRequest.setCountryCheckStatus(countryCheckStatus);
		fundsOutDBServiceImpl.getSanctionStatusForIntuition(fundsOutRequest, transactionMonitoringPaymentsOutRequest);
		transactionMonitoringPaymentsOutRequest
				.setBlacklistStatus((String) fundsOutRequest.getAdditionalAttribute("BLACKLIST_STATUS"));
		transactionMonitoringPaymentsOutRequest
				.setCustomCheckStatus((String) fundsOutRequest.getAdditionalAttribute("CUSTOM_CHECK_STATUS"));
		transactionMonitoringPaymentsOutRequest
				.setFraudPredictStatus((String) fundsOutRequest.getAdditionalAttribute("FRAUGSTER_STATUS"));
		transactionMonitoringPaymentsOutRequest
				.setPaymentReferenceCheck((String) fundsOutRequest.getAdditionalAttribute("PAYMENT_REFERENCE_STATUS"));
	}


	private TransactionMonitoringPaymentsOutRequest createServiceRequest(FundsOutRequest fundsOutRequest, String type) throws ComplianceException {
		TransactionMonitoringPaymentsOutRequest request = new TransactionMonitoringPaymentsOutRequest();
		
		request.setAccountNumber(fundsOutRequest.getTradeAccountNumber());
		request.setTransactionId(fundsOutRequest.getBeneficiary().getPaymentFundsoutId());
		request.setTimeStamp(fundsOutRequest.getBeneficiary().getTransactionDateTime());
		request.setOrgCode(fundsOutRequest.getOrgCode());
		request.setSourceApplication(fundsOutRequest.getSourceApplication());
		request.setTradeAccountNumber(fundsOutRequest.getTradeAccountNumber());
		request.setTradeContactId(fundsOutRequest.getTrade().getTradeContactId());
		request.setCustType(fundsOutRequest.getTrade().getCustType());
		request.setPurposeOfTrade(fundsOutRequest.getTrade().getPurposeOfTrade());
		request.setSellingAmount(fundsOutRequest.getTrade().getSellingAmount());
		request.setBuyingAmount(fundsOutRequest.getTrade().getBuyingAmount());
		request.setSellingAmountBaseValue(null);
		request.setTransactionCurrency(null);
		request.setContractNumber(fundsOutRequest.getTrade().getContractNumber());
		request.setThirdPartyPayment(fundsOutRequest.getTrade().getThirdPartyPayment());
		request.setCustomerLegalEntity(fundsOutRequest.getTrade().getCustLegalEntity());
		request.setMaturityDate(fundsOutRequest.getTrade().getMaturityDate());
		request.setDealDate(fundsOutRequest.getTrade().getDealDate());
		request.setBuyingAmount(fundsOutRequest.getTrade().getBuyingAmount());
		request.setMaturityDate(fundsOutRequest.getTrade().getMaturityDate());
		request.setValueDate(fundsOutRequest.getTrade().getValueDate());
		request.setBuyingAmountBaseValue(fundsOutRequest.getTrade().getBuyingAmountBaseValue());
		request.setBuyCurrency(fundsOutRequest.getTrade().getBuyCurrency());
		request.setSellCurrency(fundsOutRequest.getTrade().getSellCurrency());
		//AT-5337
		request.setInitiatingParentContact(fundsOutRequest.getTrade().getInitiatingParentContact());
		
		if(fundsOutRequest.getDeviceInfo() != null) {
			request.setScreenResolution(fundsOutRequest.getDeviceInfo().getScreenResolution());
			request.setBrowserType(fundsOutRequest.getDeviceInfo().getBrowserName());
			request.setBrowserVersion(fundsOutRequest.getDeviceInfo().getBrowserMajorVersion());
			request.setDeviceType(fundsOutRequest.getDeviceInfo().getDeviceType());
			request.setDeviceName(fundsOutRequest.getDeviceInfo().getDeviceName());
			request.setDeviceVersion(fundsOutRequest.getDeviceInfo().getDeviceVersion());
			request.setDeviceId(fundsOutRequest.getDeviceInfo().getDeviceId());
			request.setDeviceManufacturer(fundsOutRequest.getDeviceInfo().getDeviceManufacturer());
			request.setOsType(fundsOutRequest.getDeviceInfo().getOsName());
			request.setBrowserLanguage(fundsOutRequest.getDeviceInfo().getBrowserLanguage());
		}
	
		request.setPhone(fundsOutRequest.getBeneficiary().getPhone());
		request.setFirstName(fundsOutRequest.getBeneficiary().getFirstName());
		request.setLastName(fundsOutRequest.getBeneficiary().getLastName());
		request.setEmail(fundsOutRequest.getBeneficiary().getEmail());
		request.setCountry(countryCache.getCountryShortCode(fundsOutRequest.getBeneficiary().getCountry()));
		request.setAccountNumber(fundsOutRequest.getBeneficiary().getAccountNumber());
		request.setCurrencyCode(fundsOutRequest.getBeneficiary().getCurrencyCode());
		request.setPaymentReference(fundsOutRequest.getBeneficiary().getPaymentReference());
		request.setOpiCreatedDate(fundsOutRequest.getBeneficiary().getOpiCreatedDate());
		request.setAmount(fundsOutRequest.getBeneficiary().getAmount());
		request.setBeneficaryType(fundsOutRequest.getBeneficiary().getBeneficiaryType());
		request.setBeneficiaryBankAddress(fundsOutRequest.getBeneficiary().getBeneficaryBankAddress());
		request.setBeneficiaryBankId(fundsOutRequest.getBeneficiary().getBeneficiaryBankid().toString());
		request.setBeneficiaryBankName(fundsOutRequest.getBeneficiary().getBeneficaryBankName());
		request.setBeneficiaryId(fundsOutRequest.getBeneficiary().getBeneficiaryBankid().toString());
		
		request.setVersion(fundsOutRequest.getVersion());
		request.setOsrId(fundsOutRequest.getOsrId());
		request.setType(type);
		request.setFundsOutId(fundsOutRequest.getFundsOutId());
		
		return request;
	}
	
	
	private void transformTransactionMonitoringRequestSetAllChecksStatus(Message<MessageContext> message, Contact contact,
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest, FundsOutRequest fundsOutRequest) {
		
		MessageExchange blackListexchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		MessageExchange sanctionExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange fraugsterExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		MessageExchange customExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);


		EventServiceLog blacklistLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				EntityEnum.BENEFICIARY.name(), fundsOutRequest.getBeneficiary().getBeneficiaryId());
		EventServiceLog countryCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
				EntityEnum.BENEFICIARY.name(), fundsOutRequest.getBeneficiary().getBeneficiaryId());
		EventServiceLog sanctionConLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog sanctionBenLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.BENEFICIARY.name(), fundsOutRequest.getBeneficiary().getBeneficiaryId());
		EventServiceLog sanctionBankLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.BANK.name(), fundsOutRequest.getBeneficiary().getBeneficiaryBankid());
		EventServiceLog fraugsterLog = fraugsterExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog customLog = customExchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
				EntityEnum.BENEFICIARY.name(), fundsOutRequest.getBeneficiary().getBeneficiaryId());
		EventServiceLog paymentReference = blackListexchange.getEventServiceLog(
				ServiceTypeEnum.BLACKLIST_PAY_REF_SERVICE, EntityEnum.BENEFICIARY.name(),
				fundsOutRequest.getBeneficiary().getBeneficiaryId());
		
		transactionMonitoringPaymentsOutRequest.setBlacklistStatus(blacklistLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setCountryCheckStatus(countryCheckLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setSanctionContactStatus(sanctionConLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setSanctionBeneficiaryStatus(sanctionBenLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setSanctionBankStatus(sanctionBankLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setFraudPredictStatus(fraugsterLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setCustomCheckStatus(customLog.getStatus());
		transactionMonitoringPaymentsOutRequest.setPaymentReferenceCheck(paymentReference.getStatus());
		
		BlacklistPayRefSummary blacklistPayRefSummary = JsonConverterUtil.convertToObject(BlacklistPayRefSummary.class,
				paymentReference.getSummary());
		if (blacklistPayRefSummary.getPaymentReference() != null
				&& blacklistPayRefSummary.getPaymentReference().length() > 2) {
			transactionMonitoringPaymentsOutRequest
					.setPaymentRefMatchedKeyword(blacklistPayRefSummary.getSanctionText());
		}

	}
	
	
	private void transformTransactionMonitoringRequestSetWatchlist(Integer contactID,
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest) throws ComplianceException {

		List<String> watchList = fundsOutDBServiceImpl.getContactWatchlist(contactID);
		transactionMonitoringPaymentsOutRequest.setWatchlist(watchList);

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
			TransactionMonitoringPaymentOutResponse response = (TransactionMonitoringPaymentOutResponse) exchange
					.getResponse();
			TransactionMonitoringPaymentsOutRequest request = (TransactionMonitoringPaymentsOutRequest) exchange
					.getRequest();
			FundsOutResponse fundsOutResponse = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
					.getResponse();

		
			if (response != null && !response.getStatus().equalsIgnoreCase(Constants.SERVICE_FAILURE)
					&& !response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = response
						.getTransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsOutId());
				summary.setStatus(tmProviderResponse.getStatus());
				summary.setFundsOutId(request.getFundsOutId());
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
				fundsOutResponse.addAttribute("ClientRiskLevel", tmProviderResponse.getClientRiskLevel());
			}else if(response != null && response.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED)) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsOutId());
				summary.setStatus(Constants.NOT_REQUIRED);
				summary.setFundsOutId(request.getFundsOutId());
				tmProviderResponse.setStatus(Constants.NOT_REQUIRED);
				eventServiceLog.setStatus(response.getStatus());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(tmProviderResponse));
			}else if(response != null && response.getHttpStatus() == 400) {
				TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
				TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsOutId());
				summary.setStatus(response.getStatus());
				summary.setFundsOutId(request.getFundsOutId());
				summary.setCorrelationId(response.getCorrelationId());
				eventServiceLog.setStatus(response.getStatus());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
				tmProviderResponse.setCorrelationId(response.getCorrelationId());
				tmProviderResponse.setStatus(response.getStatus());
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithNull(tmProviderResponse));
				
				sendAlertEmailForInvalidRequest(request.getContractNumber(), "PaymentOUT", response);
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
	 * @param contact the contact
	 * @return the transaction monitoring payment in response
	 */
	private TransactionMonitoringPaymentOutResponse createDefaultFailureResponse(MessageExchange exchange, TransactionMonitoringPaymentsOutRequest request) {
		TransactionMonitoringPaymentOutResponse response = new TransactionMonitoringPaymentOutResponse();
		TransactionMonitoringPaymentsSummary summary = new TransactionMonitoringPaymentsSummary();
		TransactionMonitoringFundsProviderResponse tmProviderResponse = new TransactionMonitoringFundsProviderResponse();
		
		EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE,
				EntityEnum.ACCOUNT.name(), request.getFundsOutId());
		response.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		summary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
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
	private void sendAlertEmailForInvalidRequest(String contractNumber, String type, TransactionMonitoringPaymentOutResponse response) {

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
	 * Gets the credit from value.
	 *
	 * @param fundsOutRequest the funds out request
	 * @param affilateName the affilate name
	 * @return the credit from value
	 * 
	 * Add for AT-5454
	 */
	private String getCreditFromValue(FundsOutRequest fundsOutRequest, String affilateName) {

		String creditFrom = "";

		if (fundsOutRequest.getTrade().getCustType().equalsIgnoreCase("CFX")
				&& fundsOutRequest.getOrgCode().equalsIgnoreCase("TorFxOz")
				&& (affilateName != null && affilateName.equalsIgnoreCase("MoneyTech"))) {
			if (fundsOutRequest.getTrade().getCreditFrom() != null
					&& fundsOutRequest.getTrade().getCreditFrom().equalsIgnoreCase("MoneyTech")) {
				return fundsOutRequest.getTrade().getCreditFrom();
			} else {
				creditFrom = "Client-MoneyTech";
			}

		}

		return creditFrom;
	}
	
	//AT-5578
	private void transformComplianceLog(Integer accountID,
			TransactionMonitoringPaymentsOutRequest transactionMonitoringPaymentsOutRequest) throws ComplianceException {
		String complianceLog = newRegistrationDBServiceImpl.getComplianceLogForIntuition(accountID);
		transactionMonitoringPaymentsOutRequest.setComplianceLog(complianceLog);
	}
}
