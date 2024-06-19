/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FraudData;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.RiskScore;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInShortReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.BulkRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class FundsInRulesService.
 *
 * @author manish
 */
public class FundsInRulesService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FundsInRulesService.class);
	
	/** The Constant REASONS_OF_WATCHLIST. */
	private static final String REASONS_OF_WATCHLIST = "reasonsOfWatchlist";

	/**
	 * Process.
	 *
	 * @param msg
	 *            the msg
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> msg) {
		FundsInCreateResponse fundsInCreateResponse = null;
		try {
			MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = exchange.getServiceInterface();
			OperationEnum operation = exchange.getOperation();

			if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.DELETE_OPI) {
				deleteFundsInResponse(exchange);
				return msg;
			}

			fundsInCreateResponse = (FundsInCreateResponse) exchange.getResponse();
			if (fundsInCreateResponse == null) {
				fundsInCreateResponse = new FundsInCreateResponse();
			}

			if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN 
					&& (operation == OperationEnum.FUNDS_IN || operation == OperationEnum.RECHECK_FAILURES)) {
				processFundsInCreateResponse(msg, fundsInCreateResponse, operation);
			}

		} catch (Exception ex) {
			LOG.error("Error creating response:", ex);
			msg.getPayload().setFailed(true);
		}

		return msg;
	}
	
	
	/**
	 * @param fundsInCreateRequest
	 * @param responseReasons
	 */
	private void checkContactWatchList(FundsInCreateRequest fundsInCreateRequest, List<FundsInReasonCode> responseReasons) {
		Boolean isContactWatchList = fundsInCreateRequest.getAdditionalAttribute("watchlistid") != null;
		if (Boolean.TRUE.equals(isContactWatchList)) {
			responseReasons.add(FundsInReasonCode.CONTACT_ON_WATCHLIST);
		}
	}
	
	/**
	 * @param fundsInCreateRequest
	 * @param responseReasons
	 */
	private void checkDebtorNameOrAccNumMissing(FundsInCreateRequest fundsInCreateRequest, List<FundsInReasonCode> responseReasons) {
		Boolean isDebtorNameOrAccNumMissing = (Boolean) fundsInCreateRequest.getAdditionalAttribute(Constants.DEBETOR_FIELD_MISSING);
		if (isDebtorNameOrAccNumMissing != null && isDebtorNameOrAccNumMissing) {
			responseReasons.add(FundsInReasonCode.THIRDPARTY_ACCOUNT_NUM_OR_NAME_NOT_PRESENT);
		}
	}

	/**
	 * Process funds in create response.
	 *
	 * @param msg            the msg
	 * @param fundsInCreateResponse            the funds in create response
	 * @param operation the operation
	 */
	private void processFundsInCreateResponse(Message<MessageContext> msg, FundsInCreateResponse fundsInCreateResponse, OperationEnum operation) {
		FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) msg.getPayload().getGatewayMessageExchange().getRequest();
		TransactionMonitoringPaymentsInRequest tmRequest = (TransactionMonitoringPaymentsInRequest) msg
				.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getRequest(); //AT-4715
		createDefaultResponse(fundsInCreateRequest, fundsInCreateResponse);
		fundsInCreateResponse.setCorrelationID(fundsInCreateRequest.getCorrelationID());
		fundsInCreateResponse.setOrgCode(fundsInCreateRequest.getOrgCode());
		Boolean isRgDataExists = getIsRgDataExistsValue(fundsInCreateRequest);//AT-3830
		Boolean isFraudSightEligible = checkIsFsAllowed(fundsInCreateRequest);
		List<FundsInReasonCode> responseReasons = new ArrayList<>(10);
		List<FundsInShortReasonCode> shortResponseReasons = new ArrayList<>(10);//Add for AT-3470
		CustomCheckResponse ccResponse = (CustomCheckResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();
		Integer accountTMFlag = (Integer) tmRequest.getAdditionalAttribute("AccountTMFlag"); //AT-4594
		try {
			if (isChecksNotPerformed(fundsInCreateRequest)) {
				responseReasons.add(FundsInReasonCode.INACTIVE_CUSTOMER);
				shortResponseReasons.add(FundsInShortReasonCode.INACTIVE_CUSTOMER);//Add for AT-3470
				/**
				 * If Contact is Inactive then set status as NOT_REQUIRED to all
				 * services Changes made by -Saylee
				 */
				fundsInCreateResponse.setBlacklistCheckStatus(ServiceStatus.NOT_REQUIRED.name());
				fundsInCreateResponse.setCustomCheckStatus(ServiceStatus.NOT_REQUIRED.name());
				fundsInCreateResponse.setFraugsterCheckStatus(ServiceStatus.NOT_REQUIRED.name());
				fundsInCreateResponse.setSanctionCheckStatus(ServiceStatus.NOT_REQUIRED.name());
				fundsInCreateResponse.setDebitCardFraudCheckStatus(ServiceStatus.NOT_REQUIRED.name());
				fundsInCreateResponse.setIntuitionCheckStatus(ServiceStatus.NOT_REQUIRED.name());
			} else {

				checkContactWatchList(fundsInCreateRequest, responseReasons);
				
				checkDebtorNameOrAccNumMissing(fundsInCreateRequest, responseReasons);

				handleInternalResponse(msg, responseReasons, ccResponse, shortResponseReasons,isFraudSightEligible,isRgDataExists,fundsInCreateRequest);
				handleFraugsterResponse(msg, responseReasons, shortResponseReasons);
				ccResponse = handleCustomCheckResponse(msg, responseReasons, shortResponseReasons); 
				handleSanctionRespose(msg, fundsInCreateRequest, responseReasons, shortResponseReasons, accountTMFlag);
			    handleTransactionMonitoringRespose(msg, responseReasons, shortResponseReasons); // AT-4594
			    
			    //ccResponse = handleCustomCheckResponse(msg, responseReasons, shortResponseReasons);
				List<String> documentationRequiredCurrencies;
				if(null != ccResponse && null != ccResponse.getAccountWhiteList()){
					documentationRequiredCurrencies = ccResponse.getAccountWhiteList().getDocumentationRequiredWatchlistSellCurrency();
				} else {
					documentationRequiredCurrencies = new ArrayList<>();
				}
				
				// AT-4594
				if(accountTMFlag != 1 && accountTMFlag != 2 && accountTMFlag != 4) {
					//changes for AT-2986
					if(fundsInCreateRequest.getCustType().equalsIgnoreCase("PFX")){
						checkTransCurrAndWatchlistReasonsForPFX(responseReasons, documentationRequiredCurrencies, msg,fundsInCreateResponse);
					}else{
						checkTransCurrAndWatchlistReasons(responseReasons, documentationRequiredCurrencies, msg,fundsInCreateResponse);
					}
				}
				setAllCheckResponseStatus(msg, fundsInCreateResponse,isFraudSightEligible,isRgDataExists);
			}
			// if multiple checks have failed,
			// append each failure description in response
			checkResponseReasonSize(fundsInCreateResponse, responseReasons);
			addNewShortReasonCode(msg, fundsInCreateResponse, shortResponseReasons,isFraudSightEligible,isRgDataExists);//Add for AT-3470
			//setFundsInSTPFlag(fundsInCreateResponse);
			
			if (operation == OperationEnum.RECHECK_FAILURES) {
				getBulkRecheckServiceStatus(msg, responseReasons, ccResponse);
				setResponseForBulkRecheck(msg, operation, responseReasons, fundsInCreateResponse);
			}
			
			//setIntuitionResponse(fundsInCreateResponse, responseReasons); // AT-4594
			setFundsInSTPFlag(fundsInCreateResponse);
			tmRequest.setUpdateStatus(fundsInCreateResponse.getStatus());//AT-4715

		} catch (Exception ex) {
			LOG.debug("Error creating response:", ex);
			}
	}

	private Boolean checkIsFsAllowed(FundsInCreateRequest fundsInCreateRequest) {
		Boolean isFraudSightEligible;
		FraudData fraudSight =fundsInCreateRequest.getFraudSight();
		if (null != fraudSight && null != fraudSight.getFsMessage() &&  null != fraudSight.getFsScore()) {
			isFraudSightEligible=Boolean.TRUE;
		}else {
			isFraudSightEligible=Boolean.FALSE;
		}
		return isFraudSightEligible;
	}

	private Boolean getIsRgDataExistsValue(FundsInCreateRequest fundsInCreateRequest) {
		Boolean isRgDataExists;
		RiskScore rScore = fundsInCreateRequest.getRiskScore();
		if (null != rScore && null != rScore.getTRisk() && null != rScore.getTScore()) {
			isRgDataExists=Boolean.TRUE;
		}else {
			isRgDataExists=Boolean.FALSE;
		}
		return isRgDataExists;
	}

	// This will set STPFlag for FundsIn
	/**
	 * Sets the funds in STP flag.
	 *
	 * @param fundsInCreateResponse the new funds in STP flag
	 */
	private void setFundsInSTPFlag(FundsInCreateResponse fundsInCreateResponse) {
		boolean stpFlag = fundsInCreateResponse.getStatus().equals(FundsInComplianceStatus.CLEAR.name());
		fundsInCreateResponse.setSTPFlag(stpFlag);
	}

	/**
	 * Check response reason size.
	 *
	 * @param fundsInCreateResponse            the funds in create response
	 * @param responseReasons            the response reasons
	 */
	private void checkResponseReasonSize(FundsInCreateResponse fundsInCreateResponse,
			List<FundsInReasonCode> responseReasons) {
		if (responseReasons.size() > 1) {
			fundsInCreateResponse.setResponseReason(FundsInReasonCode.MULTIPLE_FAILURE);
			StringBuilder sb = new StringBuilder();
			for (FundsInReasonCode field : responseReasons) {
				if (!(sb.toString().contains(field.getReasonDescription()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(field.getReasonDescription());
				}
				fundsInCreateResponse.setResponseDescription(sb.toString());
				fundsInCreateResponse.setStatus(FundsInComplianceStatus.HOLD.name());
			}

		} else if (responseReasons.size() == 1) {
			fundsInCreateResponse.setResponseReason(responseReasons.get(0));
			fundsInCreateResponse.setStatus(FundsInComplianceStatus.HOLD.name());
		}
	}
	
	/**
	 * Adds the new short reason code.
	 *
	 * @param msg the msg
	 * @param fundsInCreateResponse the funds in create response
	 * @param shortResponseReasons the short response reasons
	 */
	private void addNewShortReasonCode(Message<MessageContext> msg, FundsInCreateResponse fundsInCreateResponse,
			List<FundsInShortReasonCode> shortResponseReasons, Boolean isFraudSightEligible,Boolean isRgDataExists) {
				
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		ContactResponse internalServiceContactResponse = internalServiceResponse.getContacts().get(0);
		
			StringBuilder sb = new StringBuilder();
			for (FundsInShortReasonCode field : shortResponseReasons) {
				if (!(sb.toString().contains(field.getReasonShort()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					if (field.getReasonShort().equalsIgnoreCase("RG-XXX.X")
							|| field.getReasonShort().equalsIgnoreCase("FS-XXX.XXX")) {
						if (Boolean.FALSE.equals(isFraudSightEligible) && Boolean.TRUE.equals(isRgDataExists)) {
							sb.append(field.getReasonShort().replaceAll("XXX.X",
									internalServiceContactResponse.getCardFraudCheck().gettScore().toString()));
						} else {
							sb.append(field.getReasonShort().replaceAll("XXX.XXX",
									internalServiceContactResponse.getFraudSightCheck().getFsScore()));// AT-3714
						}
					} else {
						sb.append(field.getReasonShort());
					}
				}
				fundsInCreateResponse.setShortResponseCode("NSTP;".concat(sb.toString()));
			}
	}

	/**
	 * Check trans curr and watchlist reasons.
	 *
	 * @param responseReasons the response reasons
	 * @param documentationRequiredCurrencies the documentation required currencies
	 * @param msg the msg
	 */
	// if VAT Reuquired and GBP, then HOLD
	// if Documentation Reuquired and currency NOT in Watchlist related whitelist, then HOLD
	// if any other watchlist HOLD
	
	@SuppressWarnings("unchecked")
	private void checkTransCurrAndWatchlistReasons(List<FundsInReasonCode> responseReasons,
			List<String> documentationRequiredCurrencies, Message<MessageContext> msg, FundsInCreateResponse fundsInCreateResponse/*, List<String> usClientListBDebtorAccountNumber*/) {
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		FundsInCreateRequest oldRequest = (FundsInCreateRequest) exchange.getRequest()
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		List<String> watchListDetails = (List<String>) oldRequest.getAdditionalAttribute(REASONS_OF_WATCHLIST);
		String transactionCurrency = oldRequest.getTrade().getTransactionCurrency();
		for (String reason : watchListDetails) {
			boolean vatAndGBP = WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(reason)
					&& "GBP".equals(oldRequest.getTrade().getTransactionCurrency());
			setVatRequiredWLFlag(reason, fundsInCreateResponse, vatAndGBP);
			boolean docReqAndCurr = WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(reason)
					&& !checkEtailerDocumentRequiredWatchList(reason, documentationRequiredCurrencies,
							transactionCurrency);
			setDocRequiredWLFlag(reason, fundsInCreateResponse, docReqAndCurr);
			boolean notVATandNotDocReq = null != reason
					&& !WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(reason)
					&& !WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(reason);

			if (vatAndGBP || docReqAndCurr || notVATandNotDocReq/* || isClientBList*/) {
				responseReasons.add(FundsInReasonCode.CONTACT_ON_WATCHLIST);
			}

		}
	}
	
	//ADD For AT-2986
	@SuppressWarnings("unchecked")
	private void checkTransCurrAndWatchlistReasonsForPFX(List<FundsInReasonCode> responseReasons,
			List<String> documentationRequiredCurrencies, Message<MessageContext> msg,FundsInCreateResponse fundsInCreateResponse/*, List<String> usClientListBDebtorAccountNumber*/) {
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		FundsInCreateRequest oldRequest = (FundsInCreateRequest) exchange.getRequest()
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		
		HashMap<Integer, List<String>> watchlisttradecon = (HashMap<Integer, List<String>>) oldRequest.getAdditionalAttribute("watchlist_with_trade&cont");
		String transactionCurrency = oldRequest.getTrade().getTransactionCurrency();
		
		for (Map.Entry<Integer, List<String>> e : watchlisttradecon.entrySet()) {	
			if(e.getKey().equals(oldRequest.getTrade().getTradeContactId())){
				List<String> watchlist = e.getValue();
				for( String value : watchlist){
					boolean vatAndGBP = WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(value)
							&& "GBP".equals(oldRequest.getTrade().getTransactionCurrency());
					setVatRequiredWLFlag(value, fundsInCreateResponse, vatAndGBP);
					boolean docReqAndCurr = WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(value)
							&& !checkEtailerDocumentRequiredWatchList(value, documentationRequiredCurrencies,
									transactionCurrency);
					setDocRequiredWLFlag(value, fundsInCreateResponse, docReqAndCurr);
					boolean notVATandNotDocReq = null != value
							&& !WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(value)
							&& !WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(value);
		
					if (vatAndGBP || docReqAndCurr || notVATandNotDocReq/* || isClientBList*/) {
						responseReasons.add(FundsInReasonCode.CONTACT_ON_WATCHLIST);
					}
				}
			}
		}
	}


	private void setVatRequiredWLFlag(String reason, FundsInCreateResponse fundsInCreateResponse, boolean vatAndGBP) {
		if (WatchList.E_TAILER_CLIENT_VAT_REQUIRED.getDescription().equals(reason)) {
			if (Boolean.TRUE.equals(vatAndGBP)) {
				fundsInCreateResponse.setVatRequiredWL(Boolean.TRUE);
			} else {
				fundsInCreateResponse.setVatRequiredWL(Boolean.FALSE);
			}
		}
	}
	
	private void setDocRequiredWLFlag(String reason, FundsInCreateResponse fundsInCreateResponse, boolean docReqAndCurr) {
		if (WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(reason)) {
			if (Boolean.TRUE.equals(docReqAndCurr)) {
				fundsInCreateResponse.setDocRequiredWL(Boolean.TRUE);
			} else {
				fundsInCreateResponse.setDocRequiredWL(Boolean.FALSE);
			}
		}
	}
		
	/**
	 * Check etailer document required watch list.
	 *
	 * @param reason the reason
	 * @param documentationRequiredCurrencies the documentation required currencies
	 * @param transactionCurrency the transaction currency
	 * @return true, if successful
	 */
	private boolean checkEtailerDocumentRequiredWatchList(String reason, List<String> documentationRequiredCurrencies,
			String transactionCurrency) {

		boolean result = false;
		if (WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription().equals(reason)) {
			for (String sellCurrency : documentationRequiredCurrencies) {
				if (transactionCurrency.equalsIgnoreCase(sellCurrency)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}
	

	/**
	 * Handle custom check response.
	 *
	 * @param msg            the msg
	 * @param responseReasons            the response reasons
	 * @return the custom check response
	 */
	private CustomCheckResponse handleCustomCheckResponse(Message<MessageContext> msg, List<FundsInReasonCode> responseReasons,
			List<FundsInShortReasonCode> shortResponseReasons) {
		CustomCheckResponse cResponse = msg.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE)
				.getResponse(CustomCheckResponse.class);
		if (cResponse != null && cResponse.getVelocityCheck() != null && cResponse.getWhiteListCheck() != null) {
			getCustomCheckStatus(cResponse, responseReasons, shortResponseReasons);
		}
		return cResponse;
	}

	/**
	 * Handle fraugster response.
	 *
	 * @param msg
	 *            the msg
	 * @param responseReasons
	 *            the response reasons
	 */
	private void handleFraugsterResponse(Message<MessageContext> msg, List<FundsInReasonCode> responseReasons,
			List<FundsInShortReasonCode> shortResponseReasons) {
		FraugsterPaymentsInResponse fResponse = (FraugsterPaymentsInResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();
		
	  if (fResponse != null && fResponse.getContactResponses() != null
				&& !fResponse.getContactResponses().isEmpty()) {
			   getFraugsterStatus(fResponse, responseReasons, shortResponseReasons);
		}
	}

	/**
	 * Handle sanction respose.
	 *
	 * @param msg
	 *            the msg
	 * @param fundsInCreateRequest
	 *            the funds in create request
	 * @param responseReasons
	 *            the response reasons
	 */
	private void handleSanctionRespose(Message<MessageContext> msg, FundsInCreateRequest fundsInCreateRequest,
			List<FundsInReasonCode> responseReasons, List<FundsInShortReasonCode> shortResponseReasons, Integer accountTMFlag) {
		MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		
		// AT-4594
		if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4) 
			responseReasons.clear();
				
		if (exchange != null) {
			SanctionResponse sanctionResponse = (SanctionResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse();
			if (sanctionResponse != null && sanctionResponse.getContactResponses() != null
					&& !sanctionResponse.getContactResponses().isEmpty() && fundsInCreateRequest.isSanctionEligible()) {
				getSanctionStatus(sanctionResponse, responseReasons, shortResponseReasons);
			}
		}
	}

	/**
	 * Handle internal response.
	 *
	 * @param msg
	 *            the msg
	 * @param responseReasons
	 *            the response reasons
	 * @param ccResponse 
	 */
	private void handleInternalResponse(Message<MessageContext> msg, List<FundsInReasonCode> responseReasons, CustomCheckResponse ccResponse,
			List<FundsInShortReasonCode> shortResponseReasons,Boolean isFraudSightEligible, Boolean isRgDataExists,FundsInCreateRequest fundsInCreateRequest) {
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		if (internalServiceResponse != null && internalServiceResponse.getContacts() != null
				&& !internalServiceResponse.getContacts().isEmpty()) {
			getInternalRulesStatuses(internalServiceResponse, responseReasons, ccResponse, shortResponseReasons,isFraudSightEligible,isRgDataExists,fundsInCreateRequest);
		}
	}

	/**
	 * Creates the default response.
	 *
	 * @param createRequest
	 *            the create request
	 * @param createResponse
	 *            the create response
	 */
	private void createDefaultResponse(FundsInCreateRequest createRequest, FundsInCreateResponse createResponse) {
		createResponse.setTradeContractNumber(createRequest.getTrade().getContractNumber());
		createResponse.setTradeAccountNumber(createRequest.getTradeAccountNumber());
		createResponse.setContactCs(ContactComplianceStatus.ACTIVE.name());
		createResponse.setStatus(FundsInComplianceStatus.CLEAR.name());
		createResponse.setResponseCode(FundsInReasonCode.PASS.getReasonCode());
		createResponse.setResponseDescription(FundsInReasonCode.PASS.getReasonDescription());
		createResponse.setShortResponseCode(FundsInShortReasonCode.PASS.getReasonShort());//Add for AT-3470
		createResponse.setResponseReason(FundsInReasonCode.PASS);
		createResponse.setBlacklistCheckStatus(ServiceStatus.NOT_REQUIRED.name());
		createResponse.setCustomCheckStatus(ServiceStatus.NOT_REQUIRED.name());
		createResponse.setFraugsterCheckStatus(ServiceStatus.NOT_REQUIRED.name());
		createResponse.setSanctionCheckStatus(ServiceStatus.NOT_REQUIRED.name());
		/**
		 * TradePaymentID added in response to broadcast Bulk Recheck response successfully towards Enterprise
		 * - Sneha Zagade 
		 * */
		createResponse.setTradePaymentID(createRequest.getTrade().getPaymentFundsInId());
	}

	/**
	 * Gets the blacklist status.
	 *
	 * @param serviceResponse
	 *            the service response
	 * @param responseReasons
	 *            the response reasons
	 * @param ccResponse 
	 * @return the blacklist status
	 */
	private void getInternalRulesStatuses(InternalServiceResponse serviceResponse,
			List<FundsInReasonCode> responseReasons, CustomCheckResponse ccResponse, 
			List<FundsInShortReasonCode> shortResponseReasons, Boolean isFraudSightEligible,Boolean isRgDataExists,FundsInCreateRequest fundsInCreateRequest) {
		ContactResponse c = serviceResponse.getContacts().get(0);
		/**
		 * If blacklist request is INVALID and status is 'Not Performed' then we
		 * will set that in response Change added by Vishal J
		 */
		getBlackListStatus(responseReasons, c.getBlacklist(), shortResponseReasons);
		getCountryCheckStatus(responseReasons, c.getCountryCheck(), ccResponse, shortResponseReasons, fundsInCreateRequest);

		/**
		 * Condition modified in if() to resolve AT-456 - Vishal J 1) Initially we were
		 * checking for 'Not Performed' but it should be for NOT_REQUIRED *
		 */
		if (Constants.PAYMENT_METHOD_DEBIT.equalsIgnoreCase(fundsInCreateRequest.getTrade().getPaymentMethod())) {
			getInternalRuleServiceStatusForSwitchDebitMethod(responseReasons, shortResponseReasons,
					isFraudSightEligible, isRgDataExists, c);
		} else {
			if (Boolean.FALSE.equals(isFraudSightEligible) && Boolean.TRUE.equals(isRgDataExists)) {
				if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getCardFraudCheck().getStatus())) {
					responseReasons.add(FundsInReasonCode.CARD_FRAUD_SERVICE_FAIL);
					shortResponseReasons.add(FundsInShortReasonCode.CARD_FRAUD_SERVICE_FAIL);// Add for AT-3470
				} else if (!ServiceStatus.PASS.name().equals(c.getCardFraudCheck().getStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(c.getCardFraudCheck().getStatus())) {
					responseReasons.add(FundsInReasonCode.CARD_SCORE);
					shortResponseReasons.add(FundsInShortReasonCode.CARD_SCORE);// Add for AT-3470
				}
			} else {
				if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getFraudSightCheck().getStatus())) {// AT-3714
					responseReasons.add(FundsInReasonCode.FRAUD_SIGHT_SERVICE_FAIL);
					shortResponseReasons.add(FundsInShortReasonCode.CARD_FRAUD_SERVICE_FAIL);
				} else if (!ServiceStatus.PASS.name().equals(c.getFraudSightCheck().getStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(c.getFraudSightCheck().getStatus())) {
					responseReasons.add(FundsInReasonCode.CARD_FRAUDSIGHT_SCORE);
					shortResponseReasons.add(FundsInShortReasonCode.CARD_FRAUDSIGHT_SCORE);
				}
			}
		}
	}

	private void getInternalRuleServiceStatusForSwitchDebitMethod(List<FundsInReasonCode> responseReasons,
			List<FundsInShortReasonCode> shortResponseReasons, Boolean isFraudSightEligible, Boolean isRgDataExists,
			ContactResponse c) {
		if(Boolean.FALSE.equals(isFraudSightEligible) && Boolean.TRUE.equals(isRgDataExists)) {
		if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getCardFraudCheck().getStatus())
				|| ServiceStatus.NOT_REQUIRED.name().equals(c.getCardFraudCheck().getStatus())) {
			responseReasons.add(FundsInReasonCode.CARD_FRAUD_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.CARD_FRAUD_SERVICE_FAIL);// Add for AT-3470
		} else if (!ServiceStatus.PASS.name().equals(c.getCardFraudCheck().getStatus())) {
			responseReasons.add(FundsInReasonCode.CARD_SCORE);
			shortResponseReasons.add(FundsInShortReasonCode.CARD_SCORE);// Add for AT-3470
		}
		}else {
		if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getFraudSightCheck().getStatus())
				|| ServiceStatus.NOT_REQUIRED.name().equals(c.getFraudSightCheck().getStatus())) {// AT-3714
			responseReasons.add(FundsInReasonCode.FRAUD_SIGHT_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.CARD_FRAUD_SERVICE_FAIL);
		} else if (!ServiceStatus.PASS.name().equals(c.getFraudSightCheck().getStatus())) {
			responseReasons.add(FundsInReasonCode.CARD_FRAUDSIGHT_SCORE);
			shortResponseReasons.add(FundsInShortReasonCode.CARD_FRAUDSIGHT_SCORE);
		}
		}
	}
	/**
	 * Gets the country check status.
	 *
	 * @param responseReasons the response reasons
	 * @param c the c
	 * @param ccResponse 
	 * @return the country check status
	 */
	private void getCountryCheckStatus(List<FundsInReasonCode> responseReasons, CountryCheckContactResponse c, CustomCheckResponse ccResponse,
			List<FundsInShortReasonCode> shortResponseReasons, FundsInCreateRequest fundsInCreateRequest) {
		if(ccResponse.isCountryWhitelistedForFundsIn() && countryCheckForRUSandUKR(fundsInCreateRequest)){  //Add for AT-4180
			c.setStatus(ServiceStatus.PASS.name());
		}
		else{
		if (ServiceStatus.WATCH_LIST.name().equals(c.getStatus())) {
			responseReasons.add(FundsInReasonCode.HIGHRISK_WATCHLIST);
		} else if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getStatus())) {
			responseReasons.add(FundsInReasonCode.COUNTRY_CHECK_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.COUNTRY_CHECK_SERVICE_FAIL);//Add for AT-3470
		} else if (!ServiceStatus.PASS.name().equals(c.getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(c.getStatus())) {
			responseReasons.add(FundsInReasonCode.SANCTIONED_COUNTRY);
			shortResponseReasons.add(FundsInShortReasonCode.COUNTRY_CHECK_FAIL);//Add for AT-3470
		}
		}
	}

	//Add for AT-4180
	private boolean countryCheckForRUSandUKR(FundsInCreateRequest fundsInCreateRequest) {
		return !fundsInCreateRequest.getTrade().getCountryOfFund().equalsIgnoreCase("RUS") && 
				!fundsInCreateRequest.getTrade().getCountryOfFund().equalsIgnoreCase("UKR");
	}

	/**
	 * Gets the black list status.
	 *
	 * @param responseReasons the response reasons
	 * @param c the c
	 * @return the black list status
	 */
	private void getBlackListStatus(List<FundsInReasonCode> responseReasons, BlacklistContactResponse blacklistContactResponse,
			List<FundsInShortReasonCode> shortResponseReasons) {
		if (ServiceStatus.NOT_PERFORMED.name().equals(blacklistContactResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.BLACKLIST_NOT_REQUIRED); // change end
		} else if (ServiceStatus.SERVICE_FAILURE.name().equals(blacklistContactResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.BLACKLIST_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.BLACKLIST_SERVICE_FAIL);//Add for AT-3470
		} else if (!ServiceStatus.PASS.name().equals(blacklistContactResponse.getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(blacklistContactResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.BLACKLISTED);
			addShortBlacklistReasonCode(shortResponseReasons, blacklistContactResponse);//Add for AT-3470
		}
	}
	
	/**
	 * Adds the short reason code.
	 *
	 * @param shortResponseReasons the short response reasons
	 * @param blacklistContactResponse the blacklist contact response
	 */
	private void addShortBlacklistReasonCode(List<FundsInShortReasonCode> shortResponseReasons, BlacklistContactResponse blacklistContactResponse) {
		
		for (BlacklistSTPData data : blacklistContactResponse.getData()) {
			if(Boolean.TRUE.equals(data.getFound())) {
				if(data.getRequestType().equalsIgnoreCase("CC NAME")) {
					shortResponseReasons.add(FundsInShortReasonCode.BLACKLISTED_NAME);
				}
				else if(data.getRequestType().equalsIgnoreCase("BANK NAME")) {
					shortResponseReasons.add(FundsInShortReasonCode.BLACKLISTED_BANK_NAME);
				}
				else if(data.getRequestType().equalsIgnoreCase("ACC_NUMBER")) {
					shortResponseReasons.add(FundsInShortReasonCode.BLACKLISTED_ACC_NUMBER);
				}
			}
		}
	}
	

	/**
	 * Gets the sanction status.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @param responseReasons
	 *            the response reasons
	 * @return the sanction status
	 */
	private void getSanctionStatus(SanctionResponse sanctionResponse, List<FundsInReasonCode> responseReasons,
			List<FundsInShortReasonCode> shortResponseReasons) {
		SanctionContactResponse scResponse = sanctionResponse.getContactResponses().get(0);
		
		if (!ServiceStatus.PASS.name().equals(scResponse.getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(scResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.SANCTIONED);
			shortResponseReasons.add(FundsInShortReasonCode.SANCTIONED);
			
		} else if (ServiceStatus.SERVICE_FAILURE.name().equals(scResponse.getStatus())) {

			responseReasons.add(FundsInReasonCode.SANCTION_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.SANCTION_SERVICE_FAIL);
		}
	}

	/**
	 * Gets the fraugster status.
	 *
	 * @param fResponse
	 *            the f response
	 * @param responseReasons
	 *            the response reasons
	 * @return the fraugster status
	 */
	private void getFraugsterStatus(FraugsterPaymentsInResponse fResponse, List<FundsInReasonCode> responseReasons,
			List<FundsInShortReasonCode> shortResponseReasons) {
		FraugsterPaymentsInContactResponse fraugsterPaymentsInContactResponse = fResponse.getContactResponses().get(0);
		
		if(!ServiceStatus.NOT_REQUIRED.name().equals(fResponse.getStatus())
			&& !ServiceStatus.SERVICE_FAILURE.name().equals(fResponse.getStatus())//Add for AT-3580
			&& !ServiceStatus.PASS.name().equals(fraugsterPaymentsInContactResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.FRAUGSTER_WATCHLIST);
			shortResponseReasons.add(FundsInShortReasonCode.FRAUGSTER_WATCHLIST);
		}
		//Add for AT-3580
		else if(ServiceStatus.SERVICE_FAILURE.name().equals(fResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.FRAUGSTER_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.FRAUGSTER_SERVICE_FAIL);
		}
	}

	/**
	 * Gets the custom check status.
	 *
	 * @param cResponse
	 *            the c response
	 * @param responseReasons
	 *            the response reasons
	 * @return the custom check status
	 */
	private void getCustomCheckStatus(CustomCheckResponse cResponse, List<FundsInReasonCode> responseReasons, 
			List<FundsInShortReasonCode> shortResponseReasons) {
		if (!ServiceStatus.SERVICE_FAILURE.name().equals(cResponse.getOverallStatus())) {

			setWhiteListCheckResponseReasons(cResponse, responseReasons, shortResponseReasons);
			
			if (!ServiceStatus.PASS.name().equals(cResponse.getFirstCreditCheck().getStatus())
					&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getFirstCreditCheck().getStatus())) {
				responseReasons.add(FundsInReasonCode.FIRSTCREDITCHECK);
				shortResponseReasons.add(FundsInShortReasonCode.FIRSTCREDITCHECK);
			}
			if (!ServiceStatus.PASS.name().equals(cResponse.getEuPoiCheck().getStatus())
					&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getEuPoiCheck().getStatus())) {
				responseReasons.add(FundsInReasonCode.EUPOICHECK);
				shortResponseReasons.add(FundsInShortReasonCode.EUPOICHECK);
			}
			if (!ServiceStatus.PASS.name().equals(cResponse.getCdincFirstCreditCheck().getStatus())
					&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getCdincFirstCreditCheck().getStatus())) {
				responseReasons.add(FundsInReasonCode.CDINC_FIRSTCREDITCHECK);
				shortResponseReasons.add(FundsInShortReasonCode.CDINC_FIRSTCREDITCHECK);
			}
		} else if (ServiceStatus.SERVICE_FAILURE.name().equals(cResponse.getOverallStatus())) {
			responseReasons.add(FundsInReasonCode.CUSTOM_CHECK_SERVICE_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.CUSTOM_CHECK_SERVICE_FAIL);
		}
	}


	private void setWhiteListCheckResponseReasons(CustomCheckResponse cResponse,
			List<FundsInReasonCode> responseReasons, List<FundsInShortReasonCode> shortResponseReasons) {
		if (!ServiceStatus.PASS.name().equals(cResponse.getWhiteListCheck().getAmoutRange()) &&
				!ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getWhiteListCheck().getAmoutRange())) {
			responseReasons.add(FundsInReasonCode.AMT_WHITELIST);
			shortResponseReasons.add(FundsInShortReasonCode.AMT_WHITELIST);
		}
		if (!ServiceStatus.PASS.name().equals(cResponse.getWhiteListCheck().getCurrency()) &&
				!ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getWhiteListCheck().getCurrency())) {
			responseReasons.add(FundsInReasonCode.CURRENCY_WHITELIST);
			shortResponseReasons.add(FundsInShortReasonCode.CURRENCY_WHITELIST);
		}
		if (!ServiceStatus.PASS.name().equals(cResponse.getWhiteListCheck().getThirdParty())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getWhiteListCheck().getThirdParty())) {
			responseReasons.add(FundsInReasonCode.THIRDPARTY_WHITELIST);
			shortResponseReasons.add(FundsInShortReasonCode.THIRDPARTY_WHITELIST);
		}
	}

	/**
	 * Sets the all check response status.
	 *
	 * @param message            the message
	 * @param fundsInCreateResponse            the funds in create response
	 */
	private void setAllCheckResponseStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse,
			boolean isFraudSightEligible, boolean isRgDataExists) {

		// Set SanctionCheck Response Status in fundsInCreateResponse to update
		// PaymentIn table
		setSanctionStatus(message, fundsInCreateResponse);

		// Set CustomCheck Response Status in fundsInCreateResponse to update
		// PaymentIn table in FundsInDBServiceImpl class.
		setCustomCheckStatus(message, fundsInCreateResponse);

		// Set FRAUGSTER Response Status in fundsInCreateResponse to update
		// PaymentIn table in FundsInDBServiceImpl class.
		setFraugsterStatus(message, fundsInCreateResponse);

		// Set INTERNAL RULE SERVICE Response Status in fundsInCreateResponse to
		// update PaymentIn table in FundsInDBServiceImpl class.
		setBlackListStatus(message, fundsInCreateResponse);
		
		setDebitCardFraudCheckStatus(message, fundsInCreateResponse,isFraudSightEligible,isRgDataExists);
		
		setTransactionMonitoringStatus(message, fundsInCreateResponse);
	}
	
	private void setTransactionMonitoringStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse) {
		MessageExchange tmExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		TransactionMonitoringPaymentsInRequest request = tmExchange.getRequest(TransactionMonitoringPaymentsInRequest.class);
		TransactionMonitoringPaymentInResponse tmPaymentsInResponse = (TransactionMonitoringPaymentInResponse) tmExchange
				.getResponse();
		try {
			if (tmPaymentsInResponse != null) {
				EventServiceLog eventServiceLog = tmExchange.getEventServiceLog(
						ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE, EntityEnum.ACCOUNT.name(),
						request.getFundsInId());
				fundsInCreateResponse.setIntuitionCheckStatus(eventServiceLog.getStatus());
			}
		} catch(Exception e) {
			LOG.error("Error in FundsInRuleService setTransactionMonitoringStatus() : ", e);
		}
	}
	
	private void setDebitCardFraudCheckStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse,
			boolean isFraudSightEligible, boolean isRgDataExists) {
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		try {
			if (internalServiceResponse != null && internalServiceResponse.getContacts() != null
					&& !internalServiceResponse.getContacts().isEmpty()) {
				ContactResponse contact = internalServiceResponse.getContacts().get(0);
				if (Boolean.FALSE.equals(isFraudSightEligible) && Boolean.TRUE.equals(isRgDataExists)) {
					fundsInCreateResponse.setDebitCardFraudCheckStatus(contact.getCardFraudCheck().getStatus());
				} else {
					fundsInCreateResponse.setDebitCardFraudCheckStatus(contact.getFraudSightCheck().getStatus());
				}
			}
		} catch(Exception e) {
			LOG.error("Error in FundsInRuleService setDebitCardFraudCheckStatus() : ", e);
		}
	}


	/**
	 * Sets the black list status.
	 *
	 * @param message
	 *            the message
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 */
	/**
	 * Purpose : In this method, we are setting blacklist status for Funds In
	 * and that status will get saved into database. But for this we are
	 * considering country check status also. Because if country is High
	 * Risk/Sanctioned then we are setting response to blacklisted customer so
	 * we need to consider its status also. If for any combination of blacklist
	 * status or country check status are NOT Required and/or PASS then we set
	 * blacklist status to PASS otherwise FAIL. And if both the statuses are NOT
	 * REQUIRED then we set blacklist status to NOT REQUIRED. - Vishal J
	 */
	private void setBlackListStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse) {
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		try {
			if (internalServiceResponse != null && internalServiceResponse.getContacts() != null
					&& !internalServiceResponse.getContacts().isEmpty()) {
				ContactResponse contact = internalServiceResponse.getContacts().get(0);
				fundsInCreateResponse.setBlacklistCheckStatus(contact.getBlacklist().getStatus());
			}
		} catch(Exception e) {
			LOG.error("Error in FundsInRuleService setBlackListStatus() : ", e);
		}
	}

	/**
	 * Sets the fraugster status.
	 *
	 * @param message
	 *            the message
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 */
	private void setFraugsterStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse) {
		MessageExchange fraugsterExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);

		FraugsterPaymentsInResponse fraugsterPaymentsInResponse = (FraugsterPaymentsInResponse) fraugsterExchange
				.getResponse();
		
		try {
			for (FraugsterPaymentsInContactResponse contact : fraugsterPaymentsInResponse.getContactResponses()) {
				EventServiceLog eventServiceLog = fraugsterExchange.getEventServiceLog(
						ServiceTypeEnum.FRAUGSTER_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
				fundsInCreateResponse.setFraugsterCheckStatus(eventServiceLog.getStatus());
			}
		} catch(Exception e) {
			LOG.error("Error in FundsInRuleService setFraugsterStatus() : ", e);
		}
	}

	/**
	 * Sets the custom check status.
	 *
	 * @param message
	 *            the message
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 */
	private void setCustomCheckStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse) {
		MessageExchange customCheckExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		FundsInCreateRequest fundsInCreateReqeust = (FundsInCreateRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		Account account = (Account) fundsInCreateReqeust.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		EventServiceLog customCheckLog = customCheckExchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
				EntityEnum.ACCOUNT.name(), account.getId());
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();

		try {
			CustomCheckResponse customCheckResponse = (CustomCheckResponse) customCheckExchange.getResponse();
			if (internalServiceResponse != null && internalServiceResponse.getContacts() != null
					&& !internalServiceResponse.getContacts().isEmpty()) {
				ContactResponse contact = internalServiceResponse.getContacts().get(0);
				String countryCheckStatus = contact.getCountryCheck().getStatus();
				String customCheckStatus = customCheckLog.getStatus();
				String firstCreditCheck = customCheckResponse.getFirstCreditCheck().getStatus();
				String euPoiCheck = customCheckResponse.getEuPoiCheck().getStatus();
				String cdincFirstCreditCheck = customCheckResponse.getCdincFirstCreditCheck().getStatus(); // Add for
																											// AT-3738
				fundsInCreateResponse.setCustomCheckStatus(determineCustomCheckColumnStatus(countryCheckStatus,
						customCheckStatus, firstCreditCheck, euPoiCheck, cdincFirstCreditCheck).name());
			} else {
				fundsInCreateResponse.setCustomCheckStatus(customCheckLog.getStatus());
			}
		} catch (Exception e) {
			LOG.error("Error in FundsInRuleService setCustomCheckStatus() : ", e);
		}
	}

	private ServiceStatus determineCustomCheckColumnStatus(
			String countryCheckStatus, String customCheckStatus, String firstCreditCheck,String euPoiCheck, 
			String cdincFirstCreditCheck) {
		ServiceStatus s;
		Boolean customPassCountryNotRequired = ServiceStatus.PASS.name().equals(customCheckStatus)
			      && ServiceStatus.NOT_REQUIRED.name().equals(countryCheckStatus);
		
		Boolean customNotRequiredCountryPass = ServiceStatus.NOT_REQUIRED.name().equals(customCheckStatus)
			      && ServiceStatus.PASS.name().equals(countryCheckStatus);
		
		Boolean customPassCountryPass = ServiceStatus.PASS.name().equals(customCheckStatus) 
				&& ServiceStatus.PASS.name().equals(countryCheckStatus);
		
		Boolean firstCreditCheckPass = ServiceStatus.PASS.name().equals(firstCreditCheck)
				|| ServiceStatus.NOT_REQUIRED.name().equals(firstCreditCheck);//AT-3346
		
		Boolean euPoiCheckPass = ServiceStatus.PASS.name().equals(euPoiCheck)
				|| ServiceStatus.NOT_REQUIRED.name().equals(euPoiCheck);//AT-3349
		
		Boolean cdincFirstCreditCheckPass = ServiceStatus.PASS.name().equals(cdincFirstCreditCheck)
				|| ServiceStatus.NOT_REQUIRED.name().equals(cdincFirstCreditCheck); //Add for AT-3738
		
		s = decideCustomCheckStatus(countryCheckStatus, customCheckStatus, customPassCountryNotRequired,
				customNotRequiredCountryPass, customPassCountryPass,firstCreditCheck,firstCreditCheckPass,euPoiCheck,euPoiCheckPass,
				cdincFirstCreditCheck, cdincFirstCreditCheckPass);
		
		return s;
	}

	private ServiceStatus decideCustomCheckStatus(String countryCheckStatus, String customCheckStatus,
			Boolean customPassCountryNotRequired, Boolean customNotRequiredCountryPass, Boolean customPassCountryPass,
			String firstCreditCheck,Boolean firstCreditCheckPass,String euPoiCheck,Boolean euPoiCheckPass,
			String cdincFirstCreditCheck, Boolean cdincFirstCreditCheckPass) {
		ServiceStatus s;
		if(ServiceStatus.SERVICE_FAILURE.name().equals(customCheckStatus)
		    || ServiceStatus.SERVICE_FAILURE.name().equals(countryCheckStatus)
		    || ServiceStatus.SERVICE_FAILURE.name().equals(firstCreditCheck)
		    || ServiceStatus.SERVICE_FAILURE.name().equals(euPoiCheck)
		    || ServiceStatus.SERVICE_FAILURE.name().equals(cdincFirstCreditCheck)){
		  s = ServiceStatus.SERVICE_FAILURE;
		}else if(ServiceStatus.NOT_REQUIRED.name().equals(customCheckStatus)
		    && ServiceStatus.NOT_REQUIRED.name().equals(countryCheckStatus)
		    && ServiceStatus.NOT_REQUIRED.name().equals(firstCreditCheck)
		    && ServiceStatus.NOT_REQUIRED.name().equals(euPoiCheck)
		    && ServiceStatus.NOT_REQUIRED.name().equals(cdincFirstCreditCheck)){
			s = ServiceStatus.NOT_REQUIRED;
		}else if( (Boolean.TRUE.equals(customPassCountryNotRequired) || Boolean.TRUE.equals(customNotRequiredCountryPass) 
				|| Boolean.TRUE.equals(customPassCountryPass)) 
				&& Boolean.TRUE.equals(firstCreditCheckPass)
				&& Boolean.TRUE.equals(euPoiCheckPass)
				&& Boolean.TRUE.equals(cdincFirstCreditCheckPass)) {
			s = ServiceStatus.PASS;
		} else{
			s = ServiceStatus.FAIL;
		}
		return s;
	}

	/**
	 * Sets the sanction status.
	 *
	 * @param message
	 *            the message
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 */
	private void setSanctionStatus(Message<MessageContext> message, FundsInCreateResponse fundsInCreateResponse) {
		MessageExchange sanctionExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		SanctionResponse sanctionRequest = (SanctionResponse) sanctionExchange.getResponse();
		try {
			if (sanctionRequest.getContactResponses() != null) {
				for (SanctionContactResponse contact : sanctionRequest.getContactResponses()) {
					EventServiceLog eventServiceLog = sanctionExchange.getEventServiceLog(
							ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.DEBTOR.name(), contact.getContactId());
					fundsInCreateResponse.setSanctionCheckStatus(eventServiceLog.getStatus());
				}
			}
		} catch(Exception e) {
			LOG.error("Error in FundsInRuleService setSanctionStatus() : ", e);
		}
	}

	/**
	 * Checks if is checks not performed.
	 *
	 * @param request
	 *            the request
	 * @return true, if is checks not performed
	 */
	/*clientPool.sendRequest(url, "POST",
				
	 * This check for sending response inactive customer if customer is
	 * inActive. To check this we enrich data in DataEnricher of account and
	 * contact.
	 */
	private static boolean isChecksNotPerformed(FundsInCreateRequest request) {
		Boolean isChecksPerformed = (Boolean) request.getAdditionalAttribute(Constants.PERFORM_CHECKS);
		return !(isChecksPerformed != null && isChecksPerformed);
	}

	/**
	 * Delete funds in response.
	 *
	 * @param exchange
	 *            the exchange
	 */
	private void deleteFundsInResponse(MessageExchange exchange) {

		FundsInDeleteResponse fundsInDeleteResponse = (FundsInDeleteResponse) exchange.getResponse();
		FundsInDeleteRequest fundsInDeleteRequest = exchange.getRequest(FundsInDeleteRequest.class);

		fundsInDeleteResponse.setOrgCode(fundsInDeleteRequest.getOrgCode());
		fundsInDeleteResponse.setPaymentFundsinId(fundsInDeleteRequest.getPaymentFundsInId());
		fundsInDeleteResponse.setResponseCode(FundsInReasonCode.PASS.getReasonCode());
		fundsInDeleteResponse.setResponseDescription(FundsInReasonCode.PASS.getReasonDescription());
		fundsInDeleteResponse.setStatus(FundsInComplianceStatus.REVERSED.name());
	}
	
	/**
	 * Sets the response for bulk recheck.
	 *
	 * @param msg the msg
	 * @param operation the operation
	 * @param responseReasons the response reasons
	 * @param fundsInResponse the funds in response
	 */
	private void setResponseForBulkRecheck(Message<MessageContext> msg, OperationEnum operation,
			List<FundsInReasonCode> responseReasons, FundsInCreateResponse fundsInResponse) {
		if(operation == OperationEnum.RECHECK_FAILURES) {
			BulkRecheckResponse recheckResponse = new BulkRecheckResponse();
			
			if(responseReasons.contains(FundsInReasonCode.RECHECK_PASS)) {
				recheckResponse.setStatus("PASS");
				recheckResponse.setTradePaymentID(fundsInResponse.getTradePaymentID());
			} else {
				recheckResponse.setStatus("FAIL");
				recheckResponse.setTradePaymentID(fundsInResponse.getTradePaymentID());
			}
			responseReasons.remove(FundsInReasonCode.RECHECK_PASS);
			MessageExchange recheckExchange = new MessageExchange();
			recheckExchange.setServiceTypeEnum(ServiceTypeEnum.FUNDSIN_BULK_RECHECK_SERVICE);
			recheckExchange.setResponse(recheckResponse);
			msg.getPayload().appendMessageExchange(recheckExchange);
		}
	}
	
	/**
	 * Gets the bulk recheck service status.
	 *
	 * @param msg the msg
	 * @param responseReasons the response reasons
	 * @param ccResponse the cc response
	 * @return the bulk recheck service status
	 */
	private void getBulkRecheckServiceStatus(Message<MessageContext> msg, List<FundsInReasonCode> responseReasons,
			CustomCheckResponse ccResponse) {
		SanctionResponse sanctionResponse;
		SanctionContactResponse scResponse = null;
		Boolean sanctionOverallStatus = Boolean.TRUE;
		
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();

		MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		if (exchange != null) {
			sanctionResponse = (SanctionResponse) msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE)
					.getResponse();
			scResponse = sanctionResponse.getContactResponses().get(0);
		}
		
		if(null != scResponse){
			 sanctionOverallStatus = !ServiceStatus.SERVICE_FAILURE.name().equals(scResponse.getStatus());
		}
		
		FraugsterPaymentsInResponse fResponse = (FraugsterPaymentsInResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();

		if (msg.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.RECHECK_FAILURES) {

			Boolean internalServiceOverallStatus = getOverallInternalRuleStatus(internalServiceResponse.getContacts().get(0));

			if (Boolean.FALSE.equals(ccResponse.getOverallStatus()
					.equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.getServiceStatusAsString()))
					&& Boolean.TRUE.equals(internalServiceOverallStatus) 
					&& Boolean.TRUE.equals(sanctionOverallStatus) 
					&& Boolean.FALSE.equals(fResponse.getStatus()
							.equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.getServiceStatusAsString()))) {
				responseReasons.add(FundsInReasonCode.RECHECK_PASS);
			}
		}

	}
	
	/**
	 * Gets the overall internal rule status.
	 *
	 * @param c the c
	 * @return the overall internal rule status
	 */
	private Boolean getOverallInternalRuleStatus(ContactResponse c) {
		boolean response = false;
		if (!ServiceStatus.SERVICE_FAILURE.name().equals(c.getBlacklist().getStatus()) 
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(c.getCountryCheck().getStatus())) {
			response = true;
		}
		return response;
	}
	
	/**
	 * Add for AT-4752
	 * 
	 * Creates the short response code.
	 *
	 * @param msg the msg
	 * @return the message
	 */
	public Message<MessageContext> createShortResponseCode(Message<MessageContext> msg) {
		FundsInCreateRequest fundsInCreateRequest = (FundsInCreateRequest) msg.getPayload().getGatewayMessageExchange()
				.getRequest();
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		
		FundsInCreateResponse fundsInCreateResponse = null;
		fundsInCreateResponse = (FundsInCreateResponse) exchange.getResponse();
		fundsInCreateResponse.setShortResponseCode(FundsInShortReasonCode.PASS.getReasonShort());
		Boolean isRgDataExists = getIsRgDataExistsValue(fundsInCreateRequest);
		Boolean isFraudSightEligible = checkIsFsAllowed(fundsInCreateRequest);
		List<FundsInReasonCode> responseReasons = new ArrayList<>(10);
		List<FundsInShortReasonCode> shortResponseReasons = new ArrayList<>(10);
		CustomCheckResponse ccResponse = (CustomCheckResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();
		
		Integer accountTMFlag = (Integer) fundsInCreateRequest.getAdditionalAttribute("AccountTMFlag");
		try {
			if (isChecksNotPerformed(fundsInCreateRequest)) {
				shortResponseReasons.add(FundsInShortReasonCode.INACTIVE_CUSTOMER);

			} else {

				handleInternalResponse(msg, responseReasons, ccResponse, shortResponseReasons, isFraudSightEligible,
						isRgDataExists, fundsInCreateRequest);
				handleSanctionRespose(msg, fundsInCreateRequest, responseReasons, shortResponseReasons, accountTMFlag);
				handleFraugsterResponse(msg, responseReasons, shortResponseReasons);

				ccResponse = handleCustomCheckResponse(msg, responseReasons, shortResponseReasons);
				List<String> documentationRequiredCurrencies;
				if(null != ccResponse && null != ccResponse.getAccountWhiteList()){
					documentationRequiredCurrencies = ccResponse.getAccountWhiteList().getDocumentationRequiredWatchlistSellCurrency();
				} else {
					documentationRequiredCurrencies = new ArrayList<>();
				}
				
				if(fundsInCreateRequest.getCustType().equalsIgnoreCase("PFX")){
					checkTransCurrAndWatchlistReasonsForPFX(responseReasons, documentationRequiredCurrencies, msg,fundsInCreateResponse);
				}else{
					checkTransCurrAndWatchlistReasons(responseReasons, documentationRequiredCurrencies, msg,fundsInCreateResponse);
				}

			}

			addNewShortReasonCode(msg, fundsInCreateResponse, shortResponseReasons, isFraudSightEligible,
					isRgDataExists);

		} catch (Exception ex) {
			LOG.debug("Error in createShortResponseCode:", ex);
			msg.getPayload().setFailed(true);
		}
		return msg;
	}
	
	/**
	 * @param msg
	 * @param responseReasons
	 * @param shortResponseReasons
	 */
	// AT-4594
	private void handleTransactionMonitoringRespose(Message<MessageContext> msg,
			List<FundsInReasonCode> responseReasons, List<FundsInShortReasonCode> shortResponseReasons) {
		MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		if (exchange != null) {
			TransactionMonitoringPaymentInResponse tmResponse = (TransactionMonitoringPaymentInResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getResponse();
			if (tmResponse != null && !ServiceStatus.NOT_REQUIRED.name().equals(tmResponse.getStatus())) {
				getTransactionMonitoringStatus(tmResponse, responseReasons, shortResponseReasons);
			}
		}
	}
	
	
	/**
	 * @param tmResponse
	 * @param responseReasons
	 * @param shortResponseReasons
	 */ 
	// AT-4594
	private void getTransactionMonitoringStatus(TransactionMonitoringPaymentInResponse tmResponse, List<FundsInReasonCode> responseReasons,
			List<FundsInShortReasonCode> shortResponseReasons) {

		//tmResponse.setStatus(ServiceStatus.FAIL.name()); //Testing - AT-4594 
		if (!ServiceStatus.PASS.name().equals(tmResponse.getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(tmResponse.getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(tmResponse.getStatus())
				&& !ServiceStatus.NOT_PERFORMED.name().equals(tmResponse.getStatus())) {
			responseReasons.add(FundsInReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
			shortResponseReasons.add(FundsInShortReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
		}
		else if (ServiceStatus.SERVICE_FAILURE.name().equals(tmResponse.getStatus()))
		{
		  responseReasons.add(FundsInReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL);
		  shortResponseReasons.add(FundsInShortReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL); 
		}	
	}

	
	/**
	 * @param msg
	 * @param fundsInCreateResponse
	 */
	// AT-4594
	private void setIntuitionResponse(FundsInCreateResponse fundsInCreateResponse,  List<FundsInReasonCode> responseReasons) {
		String sanctionCheckStatus = fundsInCreateResponse.getSanctionCheckStatus();
		String intuitionStatus = fundsInCreateResponse.getIntuitionCheckStatus();
		
		if (sanctionCheckStatus != null && intuitionStatus != null 
				&& !intuitionStatus.equalsIgnoreCase(ServiceStatus.NOT_REQUIRED.name())) {
			
			/* Testing - AT-4594 
			 * if(responseReasons.contains(FundsInReasonCode.RECHECK_PASS))
			 * responseReasons.remove(FundsInReasonCode.RECHECK_PASS);
			 */
			
			    if(sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.FAIL.name()) || 
						sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.name())) {
					fundsInCreateResponse.setStatus(FundsInComplianceStatus.HOLD.name());
				}
				else if(intuitionStatus.equalsIgnoreCase(ServiceStatus.FAIL.name()) || 
						intuitionStatus.equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.name())) {
					fundsInCreateResponse.setStatus(FundsInComplianceStatus.HOLD.name());
				}
				else if(sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.NOT_REQUIRED.name()) || 
						intuitionStatus.equalsIgnoreCase(ServiceStatus.PASS.name())) {
					fundsInCreateResponse.setStatus(FundsInComplianceStatus.CLEAR.name());
				}
				else if(sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.PASS.name()) &&
						intuitionStatus.equalsIgnoreCase(ServiceStatus.PASS.name())) {
					fundsInCreateResponse.setStatus(FundsInComplianceStatus.CLEAR.name());
				}
			    
		}
	}
	
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> processForManualUpdate(Message<MessageContext> message) {

		MessageExchange messageExchange;
		TransactionMonitoringPaymentsInRequest request;
		TransactionMonitoringPaymentInResponse response;
		List<FundsInReasonCode> responseReasons = new ArrayList<>(10);
		try {
			messageExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
			request = (TransactionMonitoringPaymentsInRequest) messageExchange.getRequest();
			response = (TransactionMonitoringPaymentInResponse) messageExchange.getResponse();

			if (request.getUpdateStatus().equalsIgnoreCase(FundsInComplianceStatus.HOLD.name())) {

				TransactionMonitoringFundsProviderResponse tmProviderResponse = response
						.getTransactionMonitoringFundsProviderResponse();

				response.setPaymentStatus(FundsInComplianceStatus.CLEAR.name());
				response.setResponseCode(FundsInReasonCode.PASS.getReasonCode());
				response.setResponseDescription(FundsInReasonCode.PASS.getReasonDescription());

				if (!ServiceStatus.PASS.name().equals(request.getSanctionContactStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionContactStatus())) {
					responseReasons.add(FundsInReasonCode.SANCTIONED);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())) {
					responseReasons.add(FundsInReasonCode.SANCTION_SERVICE_FAIL);
				}

				if (!ServiceStatus.PASS.name().equals(response.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(response.getStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(response.getStatus())
						&& !ServiceStatus.NOT_PERFORMED.name().equals(response.getStatus())) {
					responseReasons.add(FundsInReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equals(tmProviderResponse.getStatus())) {
					responseReasons.add(FundsInReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL);
				}

				checkResponseReasonSize(response, responseReasons);
			}

		} catch (Exception e) {
			LOG.error("Error while sting services response in PaymentInManualUpdateRuleService :: process() ::  ", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	private void checkResponseReasonSize(TransactionMonitoringPaymentInResponse response,
			List<FundsInReasonCode> responseReasons) {
		if (!responseReasons.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (FundsInReasonCode field : responseReasons) {
				if (!(sb.toString().contains(field.getReasonDescription()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(field.getReasonDescription());
				}
				response.setResponseDescription(sb.toString());
				response.setPaymentStatus(FundsInComplianceStatus.HOLD.name());
			}

		}
	}
}
