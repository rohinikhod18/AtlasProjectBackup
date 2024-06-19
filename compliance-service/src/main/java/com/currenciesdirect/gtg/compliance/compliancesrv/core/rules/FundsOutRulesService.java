package com.currenciesdirect.gtg.compliance.compliancesrv.core.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringFundsProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutShortReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.BulkRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutContactResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class FundsOutRulesService.
 */
public class FundsOutRulesService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FundsOutRulesService.class);
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

		try {

			MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = exchange.getServiceInterface();
			OperationEnum operation = exchange.getOperation();
			
			TransactionMonitoringPaymentsOutRequest tmRequest = new TransactionMonitoringPaymentsOutRequest();
			
			List<FundsOutReasonCode> responseReasons = new ArrayList<>();
			List<FundsOutShortReasonCode> shortResponseReasons = new ArrayList<>();
			FundsOutResponse fundsOutResponse = (FundsOutResponse) exchange.getResponse();
			if (fundsOutResponse == null) {
				fundsOutResponse = new FundsOutResponse();
			}

			if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && (operation == OperationEnum.FUNDS_OUT 
					|| operation == OperationEnum.RECHECK_FAILURES)) {
				 tmRequest = (TransactionMonitoringPaymentsOutRequest) msg
						.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getRequest(); //AT-4716
				responseReasons = processFundsOutCreateResponse(msg, fundsOutResponse, shortResponseReasons);
				setResponseForBulkRecheck(msg, operation, responseReasons, fundsOutResponse);				

			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && operation == OperationEnum.UPDATE_OPI) {
				processFundsOutUpdateResponse(msg, exchange, responseReasons, fundsOutResponse, shortResponseReasons);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && operation == OperationEnum.DELETE_OPI) {
				processFundsOutDeleteResponse(exchange, fundsOutResponse);
			}
			/*
			 * set all services response status , this status response are store
			 * in db : Abhijit G
			 */
			setAllCheckResponseStatus(msg, fundsOutResponse, operation);

			if (!responseReasons.isEmpty()) {
				fundsOutResponse.setResponseReason(FundsOutReasonCode.MULTIPLE_FAILURE);
				StringBuilder sb = gatherAllReasons(responseReasons);
				fundsOutResponse.setResponseDescription(sb.toString());
				fundsOutResponse.setStatus(FundsInComplianceStatus.HOLD.name());
				//Add for AT-3470
				/*
				 * if(operation == OperationEnum.FUNDS_OUT) {
				 * addNewShortReasonCode(fundsOutResponse, shortResponseReasons); }
				 */
			}
			//Add for AT-3470
			if(operation == OperationEnum.FUNDS_OUT && !shortResponseReasons.isEmpty()) {
				addNewShortReasonCode(fundsOutResponse, shortResponseReasons);
			}
			//setIntuitionResponse(fundsOutResponse); // AT-4594
			setFundsOutSTPFlagStatus(fundsOutResponse);
			if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && (operation == OperationEnum.FUNDS_OUT 
					|| operation == OperationEnum.RECHECK_FAILURES)) {
				tmRequest.setUpdateStatus(fundsOutResponse.getStatus()); //AT-4716
			}
		} catch (Exception e) {
			LOG.error("Error while sting services response in FundsOutRulesService :: process() ::  ", e);
			msg.getPayload().setFailed(true);
		}

		return msg;
	}
	
	/**
	 * Adds the new short reason code.
	 *
	 * @param fundsOutResponse the funds out response
	 * @param shortResponseReasons the short response reasons
	 */
	private void addNewShortReasonCode(FundsOutResponse fundsOutResponse,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		
			StringBuilder sb = new StringBuilder();
			for (FundsOutShortReasonCode field : shortResponseReasons) {
				if (!(sb.toString().contains(field.getFundsOutReasonShort()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(field.getFundsOutReasonShort());
				}
				fundsOutResponse.setShortResponseCode("NSTP;".concat(sb.toString()));
			}
	}

	/**
	 * Sets the response for bulk recheck.
	 *
	 * @param msg the msg
	 * @param operation the operation
	 * @param responseReasons the response reasons
	 * @param fundsOutResponse 
	 */
	private void setResponseForBulkRecheck(Message<MessageContext> msg, OperationEnum operation,
			List<FundsOutReasonCode> responseReasons, FundsOutResponse fundsOutResponse) {
		if(operation == OperationEnum.RECHECK_FAILURES) {
			BulkRecheckResponse recheckResponse = new BulkRecheckResponse();
			
			if(responseReasons.contains(FundsOutReasonCode.RECHECK_PASS)) {
				recheckResponse.setStatus("PASS");
				recheckResponse.setTradePaymentID(fundsOutResponse.getTradePaymentID());
			} else {
				recheckResponse.setStatus("FAIL");
				recheckResponse.setTradePaymentID(fundsOutResponse.getTradePaymentID());
			}
			responseReasons.remove(FundsOutReasonCode.RECHECK_PASS);
			MessageExchange recheckExchange = new MessageExchange();
			recheckExchange.setServiceTypeEnum(ServiceTypeEnum.FUNDSOUT_BULK_RECHECK_SERVICE);
			recheckExchange.setResponse(recheckResponse);
			msg.getPayload().appendMessageExchange(recheckExchange);
		}
	}

	// This will set STPFLag for FundsOut
	/**
	 * @param fundsOutResponse
	 */
	private void setFundsOutSTPFlagStatus(FundsOutResponse fundsOutResponse) {
		fundsOutResponse.setSTPFlag(Boolean.FALSE);
		if (fundsOutResponse.getStatus().equals(FundsOutComplianceStatus.CLEAR.name()))
			fundsOutResponse.setSTPFlag(Boolean.TRUE);
	}

	private StringBuilder gatherAllReasons(List<FundsOutReasonCode> responseReasons) {
		StringBuilder sb = new StringBuilder();
		for (FundsOutReasonCode field : responseReasons) {
			if (!(sb.toString().contains(field.getFundsOutReasonDescription()))) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(field.getFundsOutReasonDescription());
		}
		}
		return sb;
	}

	private void processFundsOutDeleteResponse(MessageExchange exchange, FundsOutResponse fundsOutResponse) {
		FundsOutDeleteRequest fRequest = (FundsOutDeleteRequest) exchange.getRequest();
		FundsOutRequest oldRequest = (FundsOutRequest) fRequest.getAdditionalAttribute("oldRequest");
		fundsOutResponse.setResponseReason(FundsOutReasonCode.PASS);
		fundsOutResponse.setCorrelationID(fRequest.getCorrelationID());
		fundsOutResponse.setOrgCode(fRequest.getOrgCode());
		fundsOutResponse.setOsrID(fRequest.getOsrId());
		fundsOutResponse.setStatus(FundsOutComplianceStatus.REVERSED.name());
		fundsOutResponse.setTradeContractNumber(oldRequest.getTrade().getContractNumber());
		fundsOutResponse.setTradePaymentID(oldRequest.getBeneficiary().getPaymentFundsoutId());
	}

	private void processFundsOutUpdateResponse(Message<MessageContext> msg, MessageExchange exchange,
			List<FundsOutReasonCode> responseReasons, FundsOutResponse fundsOutResponse, List<FundsOutShortReasonCode> shortResponseReasons) {
		FundsOutUpdateRequest fRequest = (FundsOutUpdateRequest) exchange.getRequest();
		FundsOutRequest oldRequest = (FundsOutRequest) fRequest.getAdditionalAttribute("oldRequest");
		fundsOutResponse.setTradePaymentID(fRequest.getPaymentFundsoutId());
		fundsOutResponse.setTradeAccountNumber(fundsOutResponse.getTradeAccountNumber());
		fundsOutResponse.setTradeContractNumber(oldRequest.getTrade().getContractNumber());
		fundsOutResponse.setCorrelationID(fRequest.getCorrelationID());
		fundsOutResponse.setOrgCode(fRequest.getOrgCode());
		fundsOutResponse.setOsrID(fRequest.getOsrId());
		fundsOutResponse.setStatus(FundsOutComplianceStatus.CLEAR.name());

		if (isCustomerInactive(fRequest)) {
			responseReasons.add(FundsOutReasonCode.INACTIVE_CUSTOMER);
		} else {
			CustomCheckResponse cResponse = (CustomCheckResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();
			responseReasons.addAll(getCustomChecksStatus(cResponse, oldRequest, shortResponseReasons));//Add for AT-3161
		}
	}

	/**
	 * Process funds out create response.
	 *
	 * @param msg
	 *            the msg
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return the list
	 */
	private List<FundsOutReasonCode> processFundsOutCreateResponse(Message<MessageContext> msg,
			FundsOutResponse fundsOutResponse, List<FundsOutShortReasonCode> shortResponseReasons) {
		FundsOutRequest fundsOutRequest = (FundsOutRequest) msg.getPayload().getGatewayMessageExchange().getRequest();
		createDefaultResponse(fundsOutRequest, fundsOutResponse);
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		Integer accountTMFlag = (Integer) fundsOutRequest.getAdditionalAttribute("AccountTMFlag"); //AT-4594
		if (isCustomerInactive(fundsOutRequest)) {
			responseReasons.add(FundsOutReasonCode.INACTIVE_CUSTOMER);
			shortResponseReasons.add(FundsOutShortReasonCode.INACTIVE_CUSTOMER);//AT-3470
			return responseReasons;
		}
		try {
			CustomCheckResponse cResponse = (CustomCheckResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();

			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();

			responseReasons.addAll(getBlacklistStatus(internalServiceResponse, shortResponseReasons));
			responseReasons.addAll(getCountryCheckStatus(internalServiceResponse, cResponse, shortResponseReasons, fundsOutRequest));
			responseReasons.addAll(getBlacklistPayRefStatus(internalServiceResponse, shortResponseReasons));//Add for AT-3649
            
            FraugsterPaymentsOutResponse fResponse = (FraugsterPaymentsOutResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();
            if(!"NOT_REQUIRED".equals(fResponse.getStatus()) 
            		&& CustomerTypeEnum.PFX.name().equals(fundsOutRequest.getTrade().getCustType()) ) {
				responseReasons.addAll(getFraugsterStatus(fResponse, shortResponseReasons));
			}
            
            responseReasons.addAll(getCustomChecksStatus(cResponse, fundsOutRequest, shortResponseReasons));//Add for AT-3161

         // AT-4594
    		if(accountTMFlag == 1 || accountTMFlag == 2 || accountTMFlag == 4) 
    			responseReasons.clear();// AT-4594
            
            SanctionResponse sanctionResponse = (SanctionResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse();
			responseReasons.addAll(getSanctionStatus(sanctionResponse, shortResponseReasons));
			
			//AT-4594
			TransactionMonitoringPaymentOutResponse tmResponse = (TransactionMonitoringPaymentOutResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE).getResponse();
			responseReasons.addAll(getTransactionMonitoringStatus(tmResponse, shortResponseReasons));
            
			List<String> usClientListBBeneAccNumber;
			if (null != cResponse.getAccountWhiteList()){
				usClientListBBeneAccNumber = cResponse.getAccountWhiteList()
						.getUsClientListBBeneAccNumber();
			} else {
				usClientListBBeneAccNumber = new ArrayList<>();
			}
			
			// AT-4594
			if(accountTMFlag != 1 && accountTMFlag != 2 && accountTMFlag != 4) {
				
				if (fundsOutRequest.getAdditionalAttribute(Constants.PERFORM_CHECKS).equals(Boolean.TRUE)) {
					responseReasons.addAll(checksContactsWatchListStatus(fundsOutRequest));
				}
				
				//changes for AT-2986
				if(fundsOutRequest.getTrade().getCustType().equalsIgnoreCase("PFX")){
					checkAccNumberAndWatchlistReasonsForPFX(responseReasons, msg, usClientListBBeneAccNumber,fundsOutResponse);
				}else{
					checkAccNumberAndWatchlistReasons(responseReasons, msg, usClientListBBeneAccNumber,fundsOutResponse);
				}
			}
			
			getBulkRecheckServiceStatus(msg, responseReasons, cResponse, internalServiceResponse, sanctionResponse,
					fResponse);

		} catch (Exception ex) {
			LOG.error("{1}",ex);
		}
		return responseReasons;
	}

	/**
	 * Gets the bulk recheck service status.
	 *
	 * @param msg the msg
	 * @param responseReasons the response reasons
	 * @param cResponse the c response
	 * @param internalServiceResponse the internal service response
	 * @param sanctionResponse the sanction response
	 * @param fResponse the f response
	 * @return the bulk recheck service status
	 */
	private void getBulkRecheckServiceStatus(Message<MessageContext> msg, List<FundsOutReasonCode> responseReasons,
			CustomCheckResponse cResponse, InternalServiceResponse internalServiceResponse,
			SanctionResponse sanctionResponse, FraugsterPaymentsOutResponse fResponse) {
		if(msg.getPayload().getGatewayMessageExchange().getOperation() == OperationEnum.RECHECK_FAILURES) {
			
			SanctionContactResponse scResponse = sanctionResponse.getContactResponses().get(0);
			SanctionBankResponse sBankResponse = sanctionResponse.getBankResponses().get(0);
			SanctionBeneficiaryResponse sBeneResponse = sanctionResponse.getBeneficiaryResponses().get(0);
			
			Boolean sanctionOverallStatus = !ServiceStatus.SERVICE_FAILURE.name().equals(scResponse.getStatus()) &&
					!ServiceStatus.SERVICE_FAILURE.name().equals(sBankResponse.getStatus()) &&
					!ServiceStatus.SERVICE_FAILURE.name().equals(sBeneResponse.getStatus());
			
			Boolean internalServiceOverallStatus = getOverallInternalRuleStatus(internalServiceResponse.getContacts().get(0));
			
			if(!cResponse.getOverallStatus().equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.getServiceStatusAsString()) &&
					Boolean.TRUE.equals(internalServiceOverallStatus) && Boolean.TRUE.equals(sanctionOverallStatus) 
					&& !fResponse.getStatus().equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.getServiceStatusAsString())){
				responseReasons.add(FundsOutReasonCode.RECHECK_PASS);
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
		return (!ServiceStatus.SERVICE_FAILURE.name().equals(c.getBlacklist().getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(c.getCountryCheck().getStatus()));
	}

	@SuppressWarnings("unchecked")
	private void checkAccNumberAndWatchlistReasons(List<FundsOutReasonCode> responseReasons,
			Message<MessageContext> msg, List<String> usClientListBBeneAccNumber,FundsOutResponse fundsOutResponse) {
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		FundsOutRequest oldRequest = (FundsOutRequest) exchange.getRequest()
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		List<String> watchListDetails = (List<String>) oldRequest.getAdditionalAttribute(REASONS_OF_WATCHLIST);
		String beneficiaryAccountNumber = oldRequest.getBeneficiary().getAccountNumber();
		for (String reason : watchListDetails) {
			boolean notIsClientBList = null != reason && !WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(reason);
			boolean isClientBList = WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(reason)
					&& !checkUSClientBWatchListedAccountNumber(reason, usClientListBBeneAccNumber,
							beneficiaryAccountNumber);
			setUsClientListBClientWLFlag(reason, fundsOutResponse, isClientBList);
			if (isClientBList || notIsClientBList) {
				responseReasons.add(FundsOutReasonCode.CONTACT_WATCHLIST);
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	private void checkAccNumberAndWatchlistReasonsForPFX(List<FundsOutReasonCode> responseReasons,
			Message<MessageContext> msg, List<String> usClientListBBeneAccNumber,FundsOutResponse fundsOutResponse) {
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		FundsOutRequest oldRequest = (FundsOutRequest) exchange.getRequest()
				.getAdditionalAttribute(Constants.OLD_REQUEST);
		
		HashMap<Integer, List<String>> watchlisttradecon = (HashMap<Integer, List<String>>) oldRequest.getAdditionalAttribute("watchlist_with_trade&cont");
		String beneficiaryAccountNumber = oldRequest.getBeneficiary().getAccountNumber();
		
		for (Map.Entry<Integer, List<String>> e : watchlisttradecon.entrySet()) {	
			if(e.getKey().equals(oldRequest.getTrade().getTradeContactId())){
				List<String> watchlist = e.getValue();
				for( String value : watchlist){
					boolean notIsClientBList = null != value && !WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(value);
					boolean isClientBList = WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(value)
							&& !checkUSClientBWatchListedAccountNumber(value, usClientListBBeneAccNumber,
								beneficiaryAccountNumber);
					setUsClientListBClientWLFlag(value, fundsOutResponse, isClientBList);
					if (isClientBList || notIsClientBList) {
						responseReasons.add(FundsOutReasonCode.CONTACT_WATCHLIST);
					}
				}
			}
		}
	}

	private void setUsClientListBClientWLFlag(String reason, FundsOutResponse fundsOutResponse, boolean isClientBList) {
		if (WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(reason)) {  // Added changes for AT-5174
			if (Boolean.TRUE.equals(isClientBList)) {
				fundsOutResponse.setUsClientListBClientWLFlag(Boolean.TRUE);
			} else {
				fundsOutResponse.setUsClientListBClientWLFlag(Boolean.FALSE);
			}
		}
	}

	private boolean checkUSClientBWatchListedAccountNumber(String reason,
			List<String> usClientBlistFundsOutAccountNumbers, String beneficiaryAccountNumber) {
		boolean result = false;
		if (WatchList.US_CLIENT_LIST_B_CLIENT.getDescription().equals(reason)) {
			for (String accountNumber : usClientBlistFundsOutAccountNumbers) {
				if (beneficiaryAccountNumber.equalsIgnoreCase(accountNumber)) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	/**
	 * Checks contacts watch list status.
	 * 
	 * @param fundsOutRequest
	 *            the funds out request
	 * @return the list
	 */
	private List<FundsOutReasonCode> checksContactsWatchListStatus(FundsOutRequest fundsOutRequest) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		Integer noOfContacts = (Integer) fundsOutRequest.getAdditionalAttribute("noOfContacts");

		if (null != noOfContacts && noOfContacts > 0) {
			processWatchListReasons(fundsOutRequest, responseReasons, noOfContacts);
		}
		return responseReasons;
	}

	private void processWatchListReasons(FundsOutRequest fundsOutRequest, List<FundsOutReasonCode> responseReasons,
			Integer noOfContacts) {
		String reason;
		for (int i = 1; i <= noOfContacts; i++) {
			reason = (String) fundsOutRequest.getAdditionalAttribute("watchlistreasonname" + i);
			if (shouldSkipWatchListReason(reason) && isContactAndBeneficiarySame(fundsOutRequest)) {
				return;
			}
			// if condition is added for AT-1178
			if (null != reason) {
				responseReasons.add(FundsOutReasonCode.CONTACT_WATCHLIST);
			}
		}
	}

	private boolean shouldSkipWatchListReason(String reason) {
		return (!StringUtils.isNullOrEmpty(reason) && (WatchList.USGLOBALCHECK.getDescription().equals(reason)
				|| WatchList.INTER_COMPANY.getDescription().equals(reason)));
	}

	/**
	 * Checks if is contact and beneficiary same.
	 *
	 * @param fundsOutRequest
	 *            the funds out request
	 * @return true, if is contact and beneficiary same
	 */
	// Added Code for US Client List B LOGIC (JIRA AT-990)
	private boolean isContactAndBeneficiarySame(FundsOutRequest fundsOutRequest) {

		String nameToCompare;
		if (Constants.CFX.equals(fundsOutRequest.getTrade().getCustType())) {
			Account reqAccount = fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT, Account.class);
			nameToCompare = reqAccount.getAccountName();
		} else {
			Contact reqContact = fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT, Contact.class);
			nameToCompare = reqContact.getFullName();
		}
		String beneficiaryName = fundsOutRequest.getBeneficiary().getFullName();
		return beneficiaryName != null && nameToCompare != null && beneficiaryName.equalsIgnoreCase(nameToCompare);
	}

	/**
	 * Creates the default response.
	 *
	 * @param fundsOutRequest
	 *            the funds out request
	 * @param fundsOutResponse
	 *            the funds out response
	 */
	private void createDefaultResponse(FundsOutRequest fundsOutRequest, FundsOutResponse fundsOutResponse) {
		FundsOutContactResponse contact = new FundsOutContactResponse();
		contact.setTradeContactId(fundsOutRequest.getTrade().getTradeContactId());
		contact.setContactCs(ContactComplianceStatus.ACTIVE.name());
		fundsOutResponse.setTradeAccountNumber(fundsOutRequest.getTradeAccountNumber());
		fundsOutResponse.setTradeContractNumber(fundsOutRequest.getTrade().getContractNumber());
		fundsOutResponse.setCorrelationID(fundsOutRequest.getCorrelationID());
		fundsOutResponse.setOrgCode(fundsOutRequest.getOrgCode());
		fundsOutResponse.setOsrID(fundsOutRequest.getOsrId());
		fundsOutResponse.setStatus(FundsOutComplianceStatus.CLEAR.name());
		fundsOutResponse.setResponseReason(FundsOutReasonCode.PASS);
		fundsOutResponse.setShortResponseCode(FundsOutShortReasonCode.PASS.getFundsOutReasonShort());// Add for AT-3470
		/**
		 * TradePaymentID added in response to broadcast Bulk Recheck response successfully towards Enterprise
		 * - Vishal J 
		 * */
		fundsOutResponse.setTradePaymentID(fundsOutRequest.getBeneficiary().getPaymentFundsoutId());
	}

	/**
	 * Gets the blacklist status.
	 *
	 * @param serviceResponse
	 *            the service response
	 * @return the blacklist status
	 */
	private List<FundsOutReasonCode> getBlacklistStatus(InternalServiceResponse serviceResponse, 
			List<FundsOutShortReasonCode> shortResponseReasons) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		for (ContactResponse c : serviceResponse.getContacts()) {
			if (!ServiceStatus.PASS.name().equals(c.getBlacklist().getStatus())
					&& !ServiceStatus.NOT_REQUIRED.name().equals(c.getBlacklist().getStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(c.getBlacklist().getStatus())) {
				responseReasons.add(FundsOutReasonCode.BLACKLISTED_BENE);
				addShortBlacklistReasonCode(shortResponseReasons, c.getBlacklist());//Add for AT-3470
			} else if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getBlacklist().getStatus())) {
				responseReasons.add(FundsOutReasonCode.SYSTEM_FAILURE);
				shortResponseReasons.add(FundsOutShortReasonCode.BLACKLIST_SERVICE_FAIL);//Add for AT-3470
			}
		}
		return responseReasons;
	}
	
	/**
	 * Adds the short blacklist reason code.
	 *
	 * @param shortResponseReasons the short response reasons
	 * @param blacklistContactResponse the blacklist contact response
	 */
	private void addShortBlacklistReasonCode(List<FundsOutShortReasonCode> shortResponseReasons, BlacklistContactResponse blacklistContactResponse) {
		
		for (BlacklistSTPData data : blacklistContactResponse.getData()) {
			if(Boolean.TRUE.equals(data.getFound())) {
				if(data.getRequestType().equalsIgnoreCase("CONTACT NAME")) {
					shortResponseReasons.add(FundsOutShortReasonCode.BLACKLISTED_NAME);
				}
				else if(data.getRequestType().equalsIgnoreCase("BANK NAME")) {
					shortResponseReasons.add(FundsOutShortReasonCode.BLACKLISTED_BANK_NAME);
				}
				else if(data.getRequestType().equalsIgnoreCase("ACC_NUMBER")) {
					shortResponseReasons.add(FundsOutShortReasonCode.BLACKLISTED_ACC_NUMBER);
				}
			}
		}
	}

	/**
	 * Gets the country check status.
	 *
	 * @param serviceResponse
	 *            the service response
	 * @param cResponse
	 *            the c response
	 * @return the country check status
	 */
	private List<FundsOutReasonCode> getCountryCheckStatus(InternalServiceResponse serviceResponse,
			CustomCheckResponse cResponse, List<FundsOutShortReasonCode> shortResponseReasons, FundsOutRequest fundsOutRequest) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		for (ContactResponse c : serviceResponse.getContacts()) {
			if (cResponse.isCountryWhitelisted() && countryCheckForRUSandUKR(fundsOutRequest)) { //Add for AT-4180
				c.getCountryCheck().setStatus(ServiceStatus.PASS.name());
			} else if (!ServiceStatus.PASS.name().equals(c.getCountryCheck().getStatus())
					&& !ServiceStatus.NOT_REQUIRED.name().equals(c.getBlacklist().getStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(c.getCountryCheck().getStatus())) {
				responseReasons.add(FundsOutReasonCode.BLACKLISTED_COUNTRY);
				shortResponseReasons.add(FundsOutShortReasonCode.BLACKLISTED_COUNTRY);//Add for AT-3470
			} else if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getCountryCheck().getStatus())) {
				responseReasons.add(FundsOutReasonCode.SYSTEM_FAILURE);
				shortResponseReasons.add(FundsOutShortReasonCode.COUNTRY_CHECK_SERVICE_FAIL);//Add for AT-3470
			}
		}
		return responseReasons;
	}
	
	//Add for AT-4180
	private boolean countryCheckForRUSandUKR(FundsOutRequest fundsOutRequest) {
		return !fundsOutRequest.getBeneficiary().getCountry().equalsIgnoreCase("RUS") && 
				!fundsOutRequest.getBeneficiary().getCountry().equalsIgnoreCase("UKR");
	}
	
	/**
	 * Gets the blacklist pay ref status.
	 *
	 * @param serviceResponse the service response
	 * @param shortResponseReasons the short response reasons
	 * @return the blacklist pay ref status
	 */
	//Add for AT-3649
	private List<FundsOutReasonCode> getBlacklistPayRefStatus(InternalServiceResponse serviceResponse, 
			List<FundsOutShortReasonCode> shortResponseReasons) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		for (ContactResponse c : serviceResponse.getContacts()) {
			if (!ServiceStatus.PASS.name().equals(c.getBlacklistPayref().getStatus())
					&& !ServiceStatus.NOT_PERFORMED.name().equals(c.getBlacklistPayref().getStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(c.getBlacklistPayref().getStatus())) {
				responseReasons.add(FundsOutReasonCode.BLACKLISTED_PAY_REF);
				shortResponseReasons.add(FundsOutShortReasonCode.BLACKLISTED_PAY_REF);
			} else if (ServiceStatus.SERVICE_FAILURE.name().equals(c.getBlacklistPayref().getStatus())) {
				responseReasons.add(FundsOutReasonCode.BLACKLISTED_PAY_REF_SERVICE_FAIL);
				shortResponseReasons.add(FundsOutShortReasonCode.BLACKLISTED_PAY_REF_SERVICE_FAIL);
			}
		}
		return responseReasons;
	}

	/**
	 * Gets the sanction status.
	 *
	 * @param sanctionResponse
	 *            the sanction response
	 * @return the sanction status
	 */
	private List<FundsOutReasonCode> getSanctionStatus(SanctionResponse sanctionResponse,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		SanctionContactResponse scResponse = sanctionResponse.getContactResponses().get(0);
		SanctionBankResponse sBankResponse = sanctionResponse.getBankResponses().get(0);
		SanctionBeneficiaryResponse sBeneResponse = sanctionResponse.getBeneficiaryResponses().get(0);
		
		if (!ServiceStatus.PASS.name().equals(scResponse.getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(scResponse.getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(scResponse.getStatus())) {
			responseReasons.add(FundsOutReasonCode.CONTACT_SANCTIONED);
			shortResponseReasons.add(FundsOutShortReasonCode.CONTACT_SANCTIONED);
		} else if (ServiceStatus.NOT_REQUIRED.name().equals(scResponse.getStatus())) {
			responseReasons.add(FundsOutReasonCode.INACTIVE_CUSTOMER);
		}

		if (!ServiceStatus.NOT_REQUIRED.name().equals(scResponse.getStatus())) {
			if (!ServiceStatus.PASS.name().equals(sBankResponse.getStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(sBankResponse.getStatus())) {
				responseReasons.add(FundsOutReasonCode.BANK_SANCTIONED);
				shortResponseReasons.add(FundsOutShortReasonCode.BANK_SANCTIONED);
			}

			if (!ServiceStatus.PASS.name().equals(sBeneResponse.getStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equals(sBeneResponse.getStatus())) {
				responseReasons.add(FundsOutReasonCode.BENEFICIARY_SANCTIONED);
				shortResponseReasons.add(FundsOutShortReasonCode.BENEFICIARY_SANCTIONED);
			}
		}
		//Add for AT-3580
		if(ServiceStatus.SERVICE_FAILURE.name().equals(scResponse.getStatus())
				|| ServiceStatus.SERVICE_FAILURE.name().equals(sBankResponse.getStatus())
				|| ServiceStatus.SERVICE_FAILURE.name().equals(sBeneResponse.getStatus())) {
			responseReasons.add(FundsOutReasonCode.SANCTION_SERVICE_FAIL);
			shortResponseReasons.add(FundsOutShortReasonCode.SANCTION_SERVICE_FAIL);
		}
		
		return responseReasons;
	}

	/**
	 * Gets the fraugster status.
	 *
	 * @param fResponse
	 *            the f response
	 * @return the fraugster status
	 */
	private List<FundsOutReasonCode> getFraugsterStatus(FraugsterPaymentsOutResponse fResponse,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		FraugsterPaymentsOutContactResponse response = fResponse.getContactResponses().get(0);
		List<FundsOutReasonCode> responseReasons = new ArrayList<>(1);
		if (!ServiceStatus.PASS.name().equals(response.getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(fResponse.getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(fResponse.getStatus())) {
			responseReasons.add(FundsOutReasonCode.FRAUGSTER_WATCHLIST);
			shortResponseReasons.add(FundsOutShortReasonCode.FRAUGSTER_WATCHLIST);
		}
		//Add for AT-3580
		else if(ServiceStatus.SERVICE_FAILURE.name().equals(fResponse.getStatus())) {
			responseReasons.add(FundsOutReasonCode.FRAUGSTER_SERVICE_FAIL);
			shortResponseReasons.add(FundsOutShortReasonCode.FRAUGSTER_SERVICE_FAIL);
		}
		return responseReasons;
	}

	/**
	 * Gets the custom checks status.
	 *
	 * @param cResponse
	 *            the c response
	 * @return the custom checks status
	 */
	private List<FundsOutReasonCode> getCustomChecksStatus(CustomCheckResponse cResponse, FundsOutRequest fundsOutRequest,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>(10);
		if (!ServiceStatus.SERVICE_FAILURE.name().equals(cResponse.getOverallStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getOverallStatus())) {
			addReasonCodeForVelocityAndBeneficiary(cResponse, responseReasons, shortResponseReasons);
			addReasonCodeForMismatch(cResponse, responseReasons, shortResponseReasons);
			addReasonCodeForEuPoiCheck(cResponse, responseReasons, shortResponseReasons);//Add for AT-3349
			//Add for AT-3161
			if(CustomerTypeEnum.PFX.name().equals(fundsOutRequest.getTrade().getCustType())) {
				addReasonCodeForCustomRuleFraudPredict(cResponse, responseReasons, shortResponseReasons);
			}
		}
		checkForServiceFailure(cResponse, responseReasons,shortResponseReasons);
		return responseReasons;
	}

	private void addReasonCodeForMismatch(CustomCheckResponse cResponse, List<FundsOutReasonCode> responseReasons,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		if (!ServiceStatus.PASS.name().equals(cResponse.getWhiteListCheck().getReasonOfTransfer())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getWhiteListCheck().getReasonOfTransfer())) {
			responseReasons.add(FundsOutReasonCode.REASON_MISMATCH);
			shortResponseReasons.add(FundsOutShortReasonCode.REASON_MISMATCH);
		}
		if (!ServiceStatus.PASS.name().equals(cResponse.getWhiteListCheck().getCurrency()) 
				&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getWhiteListCheck().getReasonOfTransfer())) {
			responseReasons.add(FundsOutReasonCode.CURRENCY_MISMATCH);
			shortResponseReasons.add(FundsOutShortReasonCode.CURRENCY_MISMATCH);
		}
		if (!ServiceStatus.PASS.name().equals(cResponse.getWhiteListCheck().getAmoutRange())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getWhiteListCheck().getReasonOfTransfer())) {
			responseReasons.add(FundsOutReasonCode.AMT_MISMATCH);
			shortResponseReasons.add(FundsOutShortReasonCode.AMT_MISMATCH);
		}
	}

	private void addReasonCodeForVelocityAndBeneficiary(CustomCheckResponse cResponse,
			List<FundsOutReasonCode> responseReasons, List<FundsOutShortReasonCode> shortResponseReasons) {
		if (!ServiceStatus.PASS.name().equals(cResponse.getVelocityCheck().getNoOffundsoutTxn())) {
			responseReasons.add(FundsOutReasonCode.FRQ_VELOCITY);
			shortResponseReasons.add(FundsOutShortReasonCode.FRQ_VELOCITY);
		}
		if (!ServiceStatus.PASS.name().equals(cResponse.getVelocityCheck().getPermittedAmoutcheck())) {
			responseReasons.add(FundsOutReasonCode.AMT_VELOCITY);
			shortResponseReasons.add(FundsOutShortReasonCode.AMT_VELOCITY);
		}

		if (!ServiceStatus.PASS.name().equals(cResponse.getVelocityCheck().getBeneCheck())) {
			responseReasons.add(FundsOutReasonCode.DUPLICATE_BENE);
			shortResponseReasons.add(FundsOutShortReasonCode.DUPLICATE_BENE);
		}
	}
	
	private void addReasonCodeForCustomRuleFraudPredict(CustomCheckResponse cResponse,
			List<FundsOutReasonCode> responseReasons, List<FundsOutShortReasonCode> shortResponseReasons) {
		if (!ServiceStatus.PASS.name().equals(cResponse.getFraudPredictStatus())) {
			responseReasons.add(FundsOutReasonCode.CUSTOM_RULE_FRAUDPREDICT_CHECK);
			shortResponseReasons.add(FundsOutShortReasonCode.CUSTOM_RULE_FRAUDPREDICT_CHECK);
		}
	}

	private void addReasonCodeForEuPoiCheck(CustomCheckResponse cResponse, List<FundsOutReasonCode> responseReasons,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		if (!ServiceStatus.PASS.name().equals(cResponse.getEuPoiCheck().getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(cResponse.getEuPoiCheck().getStatus())) {
			responseReasons.add(FundsOutReasonCode.CUSTOM_RULE_EU_POI_CHECK_FAIL);
			shortResponseReasons.add(FundsOutShortReasonCode.CUSTOM_RULE_EU_POI_CHECK_FAIL);
		}
	}
	
	/**
	 * Check for service failure.
	 *
	 * @param cResponse
	 *            the c response
	 * @param responseReasons
	 *            the response reasons
	 */
	private void checkForServiceFailure(CustomCheckResponse cResponse, List<FundsOutReasonCode> responseReasons,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		if (ServiceStatus.SERVICE_FAILURE.name().equals(cResponse.getOverallStatus())) {
			responseReasons.add(FundsOutReasonCode.CUSTOMCHECK);
			shortResponseReasons.add(FundsOutShortReasonCode.CUSTOMCHECK);
		}
	}

	/**
	 * If operation is FUNDS_OUT then get Blacklist, Sanction, Fraugster,
	 * Customcheck status and if operation is UPDATE_OPI or DELETE_OPI then only
	 * get Customcheck status .
	 *
	 * @param msg
	 *            the msg
	 * @param fundsOutResponse
	 *            the funds out response
	 * @param operation
	 *            the operation
	 */
	private void setAllCheckResponseStatus(Message<MessageContext> msg, FundsOutResponse fundsOutResponse,
			OperationEnum operation) {
			if (operation == OperationEnum.FUNDS_OUT || operation == OperationEnum.RECHECK_FAILURES) {
				/*
				 * set blacklist status. iterate EventServiceLog to get
				 * INTERNAL_RULE_SERVICE blackList response
				 */
				getInternalRuleServiceStatus(msg, fundsOutResponse);

				/*
				 * set sanction service status. iterate EventServiceLog to get
				 * SANCTION_SERVICE blackList response
				 */
				getSanctionStatus(msg, fundsOutResponse);

				/*
				 * set fraugster service status. iterate EventServiceLog to get
				 * SANCTION_SERVICE blackList response
				 */
				getFraugsterStatus(msg, fundsOutResponse);

				/*
				 * set custom check service status. iterate EventServiceLog to
				 * get CUSTOM_CHECKS_SERVICE blackList response
				 */
				getCustomCheckStatus(msg, fundsOutResponse);
				
				getTransactionMonitoringStatus(msg, fundsOutResponse);

			} else {
				getCustomCheckStatusForFundsoutUpdateAndtDelete(msg, fundsOutResponse);
			}

	}

	private void getCustomCheckStatusForFundsoutUpdateAndtDelete(Message<MessageContext> msg,
			FundsOutResponse fundsOutResponse) {

		MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		CustomChecksRequest serviceRequest = exchange.getRequest(CustomChecksRequest.class);
		FundsOutRequest fReuqest = (FundsOutRequest) serviceRequest.getESDocument();
		EventServiceLog customCheckEventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
				EntityEnum.BENEFICIARY.name(), fReuqest.getBeneficiary().getBeneficiaryId());

		if (customCheckEventServiceLog.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED))
			fundsOutResponse.setCustomCheckStatus(Constants.NOT_REQUIRED);

		else if (customCheckEventServiceLog.getStatus().equalsIgnoreCase(Constants.PASS))
			fundsOutResponse.setCustomCheckStatus(Constants.PASS);

		else if ((customCheckEventServiceLog.getStatus().equalsIgnoreCase(Constants.FAIL)))
			fundsOutResponse.setCustomCheckStatus(Constants.FAIL);

		else
			fundsOutResponse.setCustomCheckStatus(customCheckEventServiceLog.getStatus());
	}

	/**
	 * Purpose : In this method, we are setting blacklist status for Fundsout
	 * and that status will get saved into database. But for this we are
	 * considering country check status also. Because if country is High
	 * Risk/Sanctioned then we are setting response to blacklisted customer so
	 * we need to consider its status also. If for any combination of blacklist
	 * status or country check status are NOT Required and/or PASS then we set
	 * blacklist status to PASS otherwise FAIL. And if both the statuses are NOT
	 * REQUIRED then we set blacklist status to NOT REQUIRED. - Vishal J
	 *
	 * @param msg
	 *            the msg
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return the internal rule service status
	 */
	private void getInternalRuleServiceStatus(Message<MessageContext> msg, FundsOutResponse fundsOutResponse) {
		MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) exchange.getResponse();
		if (internalServiceResponse != null && internalServiceResponse.getContacts() != null
				&& !internalServiceResponse.getContacts().isEmpty()) {
			ContactResponse contact = internalServiceResponse.getContacts().get(0);
			fundsOutResponse.setBlacklistCheckStatus(contact.getBlacklist().getStatus());
			fundsOutResponse.setPaymentReferenceCheckStatus(contact.getBlacklistPayref().getStatus());
		}
	}

	/**
	 * Gets the sanction status.
	 *
	 * @param msg
	 *            the msg
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return the sanction status
	 */
	private void getSanctionStatus(Message<MessageContext> msg, FundsOutResponse fundsOutResponse) {
		MessageExchange sanctionExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		SanctionResponse sanctionResponse = (SanctionResponse) sanctionExchange.getResponse();
		List<SanctionContactResponse> contactList = sanctionResponse.getContactResponses();
		SanctionContactResponse contactResponse = contactList.get(0);
		SanctionBankResponse bankResponse = sanctionResponse.getBankResponses().get(0);
		SanctionBeneficiaryResponse beneResponse = sanctionResponse.getBeneficiaryResponses().get(0);
		EventServiceLog contactServiceLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.CONTACT.name(), contactResponse.getContactId());
		EventServiceLog bankServiceLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.BANK.name(), bankResponse.getBankID());
		EventServiceLog beneServiceLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.BENEFICIARY.name(), beneResponse.getBeneficiaryId());

		if (ServiceStatus.PASS.name().equalsIgnoreCase(contactServiceLog.getStatus())
				&& ServiceStatus.PASS.name().equalsIgnoreCase(bankServiceLog.getStatus())
				&& ServiceStatus.PASS.name().equalsIgnoreCase(beneServiceLog.getStatus())) {
			fundsOutResponse.setSanctionCheckStatus(ServiceStatus.PASS.name());

		} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contactServiceLog.getStatus())
				&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(bankServiceLog.getStatus())
				&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(beneServiceLog.getStatus())) {
			fundsOutResponse.setSanctionCheckStatus(ServiceStatus.NOT_REQUIRED.name());
		} else if (ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(contactServiceLog.getStatus())
            || ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(bankServiceLog.getStatus())
            || ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(beneServiceLog.getStatus())) {
            fundsOutResponse.setSanctionCheckStatus(ServiceStatus.SERVICE_FAILURE.name());
        } else {
			fundsOutResponse.setSanctionCheckStatus(ServiceStatus.FAIL.name());
		}
	}

	/**
	 * Gets the fraugster status.
	 *
	 * @param msg
	 *            the msg
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return the fraugster status
	 */
	private void getFraugsterStatus(Message<MessageContext> msg, FundsOutResponse fundsOutResponse) {
		MessageExchange fraugsterExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		FraugsterPaymentsOutResponse fraugsterResponse = (FraugsterPaymentsOutResponse) fraugsterExchange.getResponse();
		for (FraugsterPaymentsOutContactResponse response : fraugsterResponse.getContactResponses()) {
			EventServiceLog eventServiceLog = fraugsterExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
					EntityEnum.CONTACT.name(), response.getId());
			fundsOutResponse.setFraugsterCheckStatus(eventServiceLog.getStatus());
		}
	}

	/**
	 * Gets the custom check status.
	 *
	 * @param msg
	 *            the msg
	 * @param fundsOutResponse
	 *            the funds out response
	 * @return the custom check status
	 */
	private void getCustomCheckStatus(Message<MessageContext> msg, FundsOutResponse fundsOutResponse) {
		MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		CustomChecksRequest serviceRequest = exchange.getRequest(CustomChecksRequest.class);
		FundsOutRequest fReuqest = (FundsOutRequest) serviceRequest.getESDocument();
		EventServiceLog customCheckEventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
				EntityEnum.BENEFICIARY.name(), fReuqest.getBeneficiary().getBeneficiaryId());
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		if (internalServiceResponse != null && internalServiceResponse.getContacts() != null
				&& !internalServiceResponse.getContacts().isEmpty()) {
			ContactResponse contact = internalServiceResponse.getContacts().get(0);
			String countryCheckStatus = contact.getCountryCheck().getStatus();
			String customCheckStatus = customCheckEventServiceLog.getStatus();
			
			// If either Custom or Country chec SERVICE failed, mark it SERVICE_FAILURE
			// if both NOT_REQUIRED, then NOT_REQUIRED
			// if one is PASS and other is NOT_REQUIRED, PASS
			// else FAIL
			fundsOutResponse.setCustomCheckStatus(
					determineCustomCheckColumnStatus(countryCheckStatus, customCheckStatus).name());
		} else {
			fundsOutResponse.setCustomCheckStatus(customCheckEventServiceLog.getStatus());
		}
	}
	
	private ServiceStatus determineCustomCheckColumnStatus(
			String poCountryCheck, String poCustomCheck) {
		ServiceStatus result;
		if(ServiceStatus.SERVICE_FAILURE.name().equals(poCustomCheck)
		    || ServiceStatus.SERVICE_FAILURE.name().equals(poCountryCheck) ){
		  result = ServiceStatus.SERVICE_FAILURE;
		}else if(ServiceStatus.NOT_REQUIRED.name().equals(poCustomCheck)
		    && ServiceStatus.NOT_REQUIRED.name().equals(poCountryCheck) ){
			result = ServiceStatus.NOT_REQUIRED;
		}else if( (ServiceStatus.PASS.name().equals(poCustomCheck)
		      && ServiceStatus.NOT_REQUIRED.name().equals(poCountryCheck))
		    ||(ServiceStatus.NOT_REQUIRED.name().equals(poCustomCheck)
		      && ServiceStatus.PASS.name().equals(poCountryCheck))
		    ){
			result = ServiceStatus.PASS;
		}else if(ServiceStatus.PASS.name().equals(poCustomCheck) 
				&& ServiceStatus.PASS.name().equals(poCountryCheck)){
			result = ServiceStatus.PASS;
		}
		else{
			result = ServiceStatus.FAIL;
		}
		
		return result;
	}
	
	private void getTransactionMonitoringStatus(Message<MessageContext> message, FundsOutResponse fundsOutResponse) {
		MessageExchange tmExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		TransactionMonitoringPaymentsOutRequest request = tmExchange.getRequest(TransactionMonitoringPaymentsOutRequest.class);
		TransactionMonitoringPaymentOutResponse tmPaymentsOutResponse = (TransactionMonitoringPaymentOutResponse) tmExchange
				.getResponse();
		if (tmPaymentsOutResponse != null) {
			EventServiceLog eventServiceLog = tmExchange.getEventServiceLog(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE,
					EntityEnum.ACCOUNT.name(), request.getFundsOutId());
			fundsOutResponse.setIntuitionCheckStatus(eventServiceLog.getStatus());
		}
	}

	/**
	 * Checks if is customer inactive.
	 *
	 * @param request
	 *            the request
	 * @return true, if is customer inactive
	 */
	/*
	 * This check for sending response inactive customer if customer is
	 * inActive. To check this we enrich data in DataEnricher of account and
	 * contact.
	 */
	private boolean isCustomerInactive(ServiceMessage request) {
		Boolean isActive = (Boolean) request.getAdditionalAttribute(Constants.PERFORM_CHECKS);
		return !(isActive != null && isActive);
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
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		FundsOutResponse fundsOutResponse = (FundsOutResponse) exchange.getResponse();
		if (fundsOutResponse == null) {
			fundsOutResponse = new FundsOutResponse();
		}
		FundsOutRequest fundsOutRequest = (FundsOutRequest) msg.getPayload().getGatewayMessageExchange().getRequest();
		fundsOutResponse.setShortResponseCode(FundsOutShortReasonCode.PASS.getFundsOutReasonShort());
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		List<FundsOutShortReasonCode> shortResponseReasons = new ArrayList<>();
		
		
		if (isCustomerInactive(fundsOutRequest)) {
			shortResponseReasons.add(FundsOutShortReasonCode.INACTIVE_CUSTOMER);
		}
		try {
			CustomCheckResponse cResponse = (CustomCheckResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();

			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();

			responseReasons.addAll(getBlacklistStatus(internalServiceResponse, shortResponseReasons));
			responseReasons.addAll(getCountryCheckStatus(internalServiceResponse, cResponse, shortResponseReasons, fundsOutRequest));
			responseReasons.addAll(getBlacklistPayRefStatus(internalServiceResponse, shortResponseReasons));

			SanctionResponse sanctionResponse = (SanctionResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse();
			responseReasons.addAll(getSanctionStatus(sanctionResponse, shortResponseReasons));
            
            FraugsterPaymentsOutResponse fResponse = (FraugsterPaymentsOutResponse) msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();
            if(!"NOT_REQUIRED".equals(fResponse.getStatus()) 
            		&& CustomerTypeEnum.PFX.name().equals(fundsOutRequest.getTrade().getCustType()) ) {
				responseReasons.addAll(getFraugsterStatus(fResponse, shortResponseReasons));
			}
            
			responseReasons.addAll(getCustomChecksStatus(cResponse, fundsOutRequest, shortResponseReasons));//Add for AT-3161

			List<String> usClientListBBeneAccNumber;
			if (null != cResponse.getAccountWhiteList()){
				usClientListBBeneAccNumber = cResponse.getAccountWhiteList()
						.getUsClientListBBeneAccNumber();
			} else {
				usClientListBBeneAccNumber = new ArrayList<>();
			}
			if(fundsOutRequest.getTrade().getCustType().equalsIgnoreCase("PFX")){
				checkAccNumberAndWatchlistReasonsForPFX(responseReasons, msg, usClientListBBeneAccNumber,fundsOutResponse);
			}else{
				checkAccNumberAndWatchlistReasons(responseReasons, msg, usClientListBBeneAccNumber,fundsOutResponse);
			}
			
			addNewShortReasonCode(fundsOutResponse, shortResponseReasons);

		} catch (Exception ex) {
			LOG.error("Error while sting services response in FundsOutRulesService :: createShortResponseCode() ::  ", ex);
			msg.getPayload().setFailed(true);
		}
		return msg;
	}

	/**
	 * @param tmResponse
	 * @param shortResponseReasons
	 * @return
	 */
	//AT-4594
	private List<FundsOutReasonCode> getTransactionMonitoringStatus(TransactionMonitoringPaymentOutResponse tmResponse,
			List<FundsOutShortReasonCode> shortResponseReasons) {
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();
		
		if (!ServiceStatus.PASS.name().equals(tmResponse.getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(tmResponse.getStatus())
				&& !ServiceStatus.NOT_REQUIRED.name().equals(tmResponse.getStatus())
				&& !ServiceStatus.NOT_PERFORMED.name().equals(tmResponse.getStatus())) {
			responseReasons.add(FundsOutReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
			shortResponseReasons.add(FundsOutShortReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
		}
		else if (ServiceStatus.SERVICE_FAILURE.name().equals(tmResponse.getStatus()))
		{
			responseReasons.add(FundsOutReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL);
			shortResponseReasons.add(FundsOutShortReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL); 
		}	
		
		return responseReasons;
	}
	
	
	/**
	 * @param msg
	 * @param fundsOutResponse
	 */
	// AT-4594
	private void setIntuitionResponse(FundsOutResponse fundsOutResponse) {
		
		String sanctionCheckStatus = fundsOutResponse.getSanctionCheckStatus();
		String intuitionStatus = fundsOutResponse.getIntuitionCheckStatus();
		
		if (sanctionCheckStatus != null && intuitionStatus != null 
				&& !intuitionStatus.equalsIgnoreCase(ServiceStatus.NOT_REQUIRED.name())) {
			
			    if(sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.FAIL.name()) || 
						sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.name())) {
			    	fundsOutResponse.setStatus(FundsOutComplianceStatus.HOLD.name());
				}
				else if(intuitionStatus.equalsIgnoreCase(ServiceStatus.FAIL.name()) || 
						intuitionStatus.equalsIgnoreCase(ServiceStatus.SERVICE_FAILURE.name())) {
					fundsOutResponse.setStatus(FundsOutComplianceStatus.HOLD.name());
				}
				else if(sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.NOT_REQUIRED.name()) || 
						intuitionStatus.equalsIgnoreCase(ServiceStatus.PASS.name())) {
					fundsOutResponse.setStatus(FundsOutComplianceStatus.CLEAR.name());
				}
				else if(sanctionCheckStatus.equalsIgnoreCase(ServiceStatus.PASS.name()) &&
						intuitionStatus.equalsIgnoreCase(ServiceStatus.PASS.name())) {
					fundsOutResponse.setStatus(FundsOutComplianceStatus.CLEAR.name());
				}
		}
	}
	
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> processForManualUpdate(Message<MessageContext> message) {
		MessageExchange messageExchange;
		TransactionMonitoringPaymentsOutRequest request;
		TransactionMonitoringPaymentOutResponse response;
		List<FundsOutReasonCode> responseReasons = new ArrayList<>();

		try {
			messageExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
			request = (TransactionMonitoringPaymentsOutRequest) messageExchange.getRequest();
			response = (TransactionMonitoringPaymentOutResponse) messageExchange.getResponse();
			
			if (request.getUpdateStatus().equalsIgnoreCase(FundsOutComplianceStatus.HOLD.name())) {

				TransactionMonitoringFundsProviderResponse tmProviderResponse = response
						.getTransactionMonitoringFundsProviderResponse();

				response.setPaymentStatus(FundsInComplianceStatus.CLEAR.name());
				response.setResponseCode(FundsInReasonCode.PASS.getReasonCode());
				response.setResponseDescription(FundsInReasonCode.PASS.getReasonDescription());

				if (!ServiceStatus.PASS.name().equals(request.getSanctionContactStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionContactStatus())) {
					responseReasons.add(FundsOutReasonCode.CONTACT_SANCTIONED);
				} else if (ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionContactStatus())) {
					responseReasons.add(FundsOutReasonCode.INACTIVE_CUSTOMER);
				}

				if (!ServiceStatus.NOT_REQUIRED.name().equals(request.getSanctionBankStatus())) {
					if (!ServiceStatus.PASS.name().equals(request.getSanctionBankStatus())
							&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBankStatus())) {
						responseReasons.add(FundsOutReasonCode.BANK_SANCTIONED);
					}

					if (!ServiceStatus.PASS.name().equals(request.getSanctionBeneficiaryStatus())
							&& !ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBeneficiaryStatus())) {
						responseReasons.add(FundsOutReasonCode.BENEFICIARY_SANCTIONED);
					}
				}

				if (ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionContactStatus())
						|| ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBankStatus())
						|| ServiceStatus.SERVICE_FAILURE.name().equals(request.getSanctionBeneficiaryStatus())) {
					responseReasons.add(FundsOutReasonCode.SANCTION_SERVICE_FAIL);
				}

				if (!ServiceStatus.PASS.name().equals(tmProviderResponse.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equals(tmProviderResponse.getStatus())
						&& !ServiceStatus.NOT_REQUIRED.name().equals(tmProviderResponse.getStatus())
						&& !ServiceStatus.NOT_PERFORMED.name().equals(tmProviderResponse.getStatus())) {
					responseReasons.add(FundsOutReasonCode.TRANSACTION_MONITORING_CHECK_FAIL);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equals(tmProviderResponse.getStatus())) {
					responseReasons.add(FundsOutReasonCode.TRANSACTION_MONITORING_SERVICE_FAIL);
				}

				checkResponseReasonSize(response, responseReasons);
			}

		} catch (Exception e) {
			LOG.error(
					"Error while sting services response in PaymentOutManualUpdateRuleService :: paymentOutCreateResponse() ::  ",
					e);
		}

		return message;
	}

	private void checkResponseReasonSize(TransactionMonitoringPaymentOutResponse response,
			List<FundsOutReasonCode> responseReasons) {
		if (!responseReasons.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for (FundsOutReasonCode field : responseReasons) {
				if (!(sb.toString().contains(field.getFundsOutReasonDescription()))) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append(field.getFundsOutReasonDescription());
				}
				response.setResponseDescription(sb.toString());
				response.setPaymentStatus(FundsInComplianceStatus.HOLD.name());
			}

		}
	}
}
