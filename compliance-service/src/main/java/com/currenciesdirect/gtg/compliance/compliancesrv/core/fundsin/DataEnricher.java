package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.FundsInCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CardFraudScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FraudSightScoreResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IEventDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.WatchList;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsInDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.WatchListRules;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class DataEnricher.
 */
public class DataEnricher extends AbstractDataEnricher {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEnricher.class);

	private static final String WATCHLISTID = "watchlistid";
	private static final String FUNDS_IN_REPEAT = "FUNDS_IN_REPEAT";

	@Autowired
	private IEventDBService eventDBServiceImpl;
	@Autowired
	private FundsInDBServiceImpl fundsInDBService;

	private static final String ERROR = "Exception in enrichData()";

	/**
	 * Enrich data.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 */
	public Message<MessageContext> enrichData(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			getUserTableId(message);
			FundsInBaseRequest fundsInBaseRequest = (FundsInBaseRequest) messageExchange.getRequest();
			FundsInCreateRequest oldReuqest = fundsInDBService.getFundsInDetails(fundsInBaseRequest);
			Account account = (Account) oldReuqest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			String custType;
			if (null != account && null != account.getCustType()) {
				custType = account.getCustType().trim();
				/* old request cust type **/ /* current request cust type */
				if (!((custType != null && fundsInBaseRequest.getCustType() != null)
						&& (custType.equalsIgnoreCase(fundsInBaseRequest.getCustType().trim())))) {
					/*
					 * If old and current request doesnot match then set current
					 * CustType into old request and pass it to data validation
					 * layer for error response code
					 */
					oldReuqest.setCustType(fundsInBaseRequest.getCustType());
					enrichCommonData(message, oldReuqest);
				} else  {
					enrichPFXCFXData(message, oldReuqest);
				}
			} else {// No Account exists so forwarding to next validation level for correct error Message
				enrichCommonData(message, oldReuqest);
			}
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}
		return message;
	}
	
	
	/**
	 * Enrich data for delet opi.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> enrichDataForDeletOpi(Message<MessageContext> message) {
		
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			getUserTableId(message);
			FundsInDeleteRequest fundsInDeleteRequest = (FundsInDeleteRequest) messageExchange.getRequest();
			fundsInDBService.getFundsInContactAccountDetailsForDeleteOpi(fundsInDeleteRequest);
			
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}
		return message;
		
	}

	/**
	 * Enrich data.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 */
	public Message<MessageContext> enrichDataForBlacklistRecheck(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			getUserTableId(message);
			FundsInBaseRequest fundsInBaseRequest = (FundsInBaseRequest) messageExchange.getRequest();
			FundsInCreateRequest oldReuqest = fundsInDBService.getFundsInDetailsForBlacklistRecheck(fundsInBaseRequest);
			enrichCommonData(message, oldReuqest);
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}
		return message;
	}

	/**
	 * Enrich PFX Data
	 * 
	 * @param message
	 * @return
	 * @throws ComplianceException
	 */
	private Message<MessageContext> enrichPFXCFXData(Message<MessageContext> message, FundsInCreateRequest oldReuqest)
			throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			if (serviceInterfaceType != ServiceInterfaceType.FUNDSIN)
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
						new Exception(" Invalid Service Interface Type"));
			enrichCommonData(message, oldReuqest);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		}
		return message;
	}

	/**
	 * Enrich Common Data
	 * 
	 * @param message
	 * @return
	 * @throws ComplianceException
	 */
	private Message<MessageContext> enrichCommonData(Message<MessageContext> message, FundsInCreateRequest oldReuqest)
			throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();
			if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.FUNDS_IN) {
				handleFundsInCreate(messageExchange, oldReuqest);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.CUSTOMCHECK_RESEND) {
				handleRepeatCustomCheck(message, messageExchange, eventDBServiceImpl,oldReuqest);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.SANCTION_RESEND) {
				handleRepeatSanctionCheck(message, messageExchange, eventDBServiceImpl);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.FRAUGSTER_RESEND) {
				handleRepeatFraugsterCheck(messageExchange, oldReuqest);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSIN && operation == OperationEnum.BLACKLIST_RESEND) {
				handleRepeatBlacklistCheck(messageExchange, oldReuqest);
			}
		} catch (Exception e) {
			LOGGER.error(ERROR, e);
		}
		return message;
	}

	/**
	 * This method will enrich the data for blacklist repeat check for funds in
	 * 
	 * @param message
	 * @return message
	 */
	private void handleRepeatBlacklistCheck(MessageExchange messageExchange, FundsInCreateRequest oldReuqest)
			throws ComplianceException {

		FundsInBlacklistResendRequest resendRequest = messageExchange.getRequest(FundsInBlacklistResendRequest.class);

		if (null != oldReuqest.getAccId()) {
			updateAddtionalAttributesforBlacklist(resendRequest, oldReuqest);
			Contact contact = (Contact) resendRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) resendRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			if (null == account || null == contact) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
			}
			oldReuqest.addAttribute(Constants.FIELD_CONTACT, contact);
			oldReuqest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
			messageExchange.getRequest().addAttribute(Constants.OLD_REQUEST, oldReuqest);
		}
	}

	/**
	 * Update Addtional Attributes
	 * 
	 * @param request
	 * @param details
	 */
	private void updateAddtionalAttributesforBlacklist(FundsInBaseRequest request, FundsInCreateRequest details) {
		request.setOrgId(details.getOrgId());
		request.addAttribute(Constants.FIELD_ACC_ACCOUNT, details.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT));
		Integer noOfContacts = (Integer) details.getAdditionalAttribute(Constants.FIELD_NO_OF_CONTACT);
		request.addAttribute(Constants.FIELD_NO_OF_CONTACT, noOfContacts);
		Integer tradeContactId = null;
		if (null != details.getTrade())
			tradeContactId = details.getTrade().getTradeContactId();
		List<Contact> contactList = (List<Contact>) details.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);

		if (null != noOfContacts) {
			for (Contact contact : contactList) {
				if (contact.getTradeContactID().equals(tradeContactId)) {
					request.addAttribute(Constants.FIELD_CONTACT, contact);
				}
			}
		}
	}

	/**
	 * This method will enrich the data for fraugster repeat check for funds in
	 * 
	 * @param message
	 * @return message
	 */
	private void handleRepeatFraugsterCheck(MessageExchange messageExchange, FundsInCreateRequest oldReuqest)
			throws ComplianceException {

		FundsInFraugsterResendRequest resendRequest = messageExchange.getRequest(FundsInFraugsterResendRequest.class);

		if (null != oldReuqest.getAccId()) {
			updateAddtionalAttributesforFraugster(resendRequest, oldReuqest);
			Contact contact = (Contact) resendRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) resendRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			if (null == account || null == contact) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
			}
			oldReuqest.addAttribute(Constants.FIELD_CONTACT, contact);
			oldReuqest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);

			/**
			 * fraugster signup score is needed for fraugster payment in
			 * request
			 **/
			FraugsterSummary fraugsterSummary = newRegistrationDBServiceImpl
					.getFirstFraugsterSignupSummary(contact.getId(), EntityEnum.CONTACT.toString());
			if (null != fraugsterSummary) {
				resendRequest.addAttribute("FraugsterSignupScore", fraugsterSummary.getScore());
			}
			resendRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);

			messageExchange.getRequest().addAttribute(Constants.OLD_REQUEST, oldReuqest);
		}
	}

	/**
	 * Update Addtional Attributes
	 * 
	 * @param request
	 * @param details
	 */
	private void updateAddtionalAttributesforFraugster(FundsInBaseRequest request, FundsInCreateRequest details) {
		request.setOrgId(details.getOrgId());
		request.addAttribute(Constants.FIELD_ACC_ACCOUNT, details.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT));
		Integer noOfContacts = (Integer) details.getAdditionalAttribute(Constants.FIELD_NO_OF_CONTACT);
		request.addAttribute(Constants.FIELD_NO_OF_CONTACT, noOfContacts);
		Integer tradeContactId = null;
		if (null != details.getTrade())
			tradeContactId = details.getTrade().getTradeContactId();
		List<Contact> contactList = (List<Contact>) details.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		int count = 1;
		if (null != noOfContacts) {
			for (Contact contact : contactList) {
				if (contact.getTradeContactID().equals(tradeContactId)) {
					request.addAttribute(Constants.FIELD_CONTACT, contact);
				}
				request.addAttribute(WATCHLISTID + count, details.getAdditionalAttribute(WATCHLISTID + count));
				request.addAttribute("watchlistreasonname" + count,
						details.getAdditionalAttribute("watchlistreasonname" + count));
				count++;
			}

		}
	}

	private void handleRepeatSanctionCheck(Message<MessageContext> message, MessageExchange messageExchange,
			IEventDBService ieventDBService) throws ComplianceException {
		FundsInSanctionResendRequest request = messageExchange.getRequest(FundsInSanctionResendRequest.class);
		FundsInCreateRequest fundsInRequest = fundsInDBService.getFundsInDetails(request);
		fundsInRequest.setFundsInId(request.getPaymentInId());
		Account account = fundsInDBService.getAccountByTradeAccountNumber(
				fundsInRequest.getTrade().getTradeAccountNumber(), request.getOrgCode());
		Contact contact = fundsInDBService.getContactByTradeContactID(fundsInRequest.getTrade().getTradeContactId(),
				request.getOrgCode());
		message.getPayload().getGatewayMessageExchange().setRequest(fundsInRequest);
		EntityDetails entityDetails = ieventDBService.getPreviousSanctionDetails(request.getEntityId(),
				EntityEnum.DEBTOR.name());
		if (fundsInRequest.getTrade().getCustType().equals(Constants.PFX))
			entityDetails.setContactName(contact.getFullName());
		else
			entityDetails.setContactName(account.getAccountName());
		fundsInRequest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
		fundsInRequest.addAttribute(Constants.FIELD_CONTACT, contact);
		fundsInRequest.addAttribute(FUNDS_IN_REPEAT, request);
		fundsInRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS, entityDetails);
		fundsInRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		fundsInRequest.setSanctionEligible(isThirdPartyPayment(fundsInRequest));
		messageExchange.setRequest(fundsInRequest);
	}

	private void handleRepeatCustomCheck(Message<MessageContext> message, MessageExchange messageExchange,
			IEventDBService ieventDBService,FundsInCreateRequest oldReuqest) throws ComplianceException {
		FundsInCustomCheckResendRequest customCheckResend = messageExchange.getRequest(FundsInCustomCheckResendRequest.class);
		FundsInCreateRequest fundsInRequest = fundsInDBService.getFundsInRequestById(customCheckResend.getPaymentInId());
		boolean isFirstFundsIn = fundsInDBService.checkFirstFundsIn(fundsInRequest);//AT-3346
		Integer eddCountryNumber = getCountryEddNumber(fundsInRequest.getTrade().getCountryOfFund());		//AT-3346
		Timestamp leUpdatedDate = (Timestamp) oldReuqest.getAdditionalAttribute(Constants.LE_UPDATE_DATE);
		boolean isAnyFundsCleared = fundsInDBService.checkAnyFundsClearForContact(fundsInRequest,leUpdatedDate);//AT-3349
		EntityDetails entityDetails = ieventDBService.isServicePerformedForFundsInCustomCheck(customCheckResend.getPaymentInId(), "VELOCITYCHECK");

		boolean isCDINCFirstFundsIn = fundsInDBService.checkCDINCFirstFundsIn(fundsInRequest); //Add for AT-3738
		
		fundsInRequest.setFundsInId(customCheckResend.getPaymentInId());
		Account account = fundsInDBService.getAccountByTradeAccountNumber(
				fundsInRequest.getTrade().getTradeAccountNumber(), customCheckResend.getOrgCode());
		Contact contact = fundsInDBService.getContactByTradeContactID(fundsInRequest.getTrade().getTradeContactId(),
				customCheckResend.getOrgCode());
		entityDetails.setAccountName(account.getAccountName());
		message.getPayload().getGatewayMessageExchange().setRequest(fundsInRequest);
		fundsInRequest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
		fundsInRequest.addAttribute(Constants.FIELD_CONTACT, contact);
		fundsInRequest.addAttribute(FUNDS_IN_REPEAT, customCheckResend);
		fundsInRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS, entityDetails);
		fundsInRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		fundsInRequest.addAttribute(Constants.EDD_COUNTRY_NUMBER, eddCountryNumber); //AT-3346
		fundsInRequest.addAttribute(Constants.ZERO_CLEAR_FUNDSIN, isFirstFundsIn);//AT-3346
		fundsInRequest.addAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE, isAnyFundsCleared);//AT-3349
		fundsInRequest.addAttribute(Constants.CONTACT_POI_EXISTS, getPOIExists(oldReuqest,fundsInRequest));//AT-3349
		fundsInRequest.addAttribute(Constants.CDINC_ZERO_CLEAR_FUNDSIN, isCDINCFirstFundsIn); //Add for AT-3738

		messageExchange.setRequest(fundsInRequest);
	}

	private void handleFundsInCreate(MessageExchange messageExchange, FundsInCreateRequest oldReuqest)
			throws ComplianceException {
		FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) messageExchange.getRequest();
		boolean isFirstFundsIn = fundsInDBService.checkFirstFundsIn(fundsInRequest);						//AT-3346
		Integer eddCountryNumber = getCountryEddNumber(fundsInRequest.getTrade().getCountryOfFund());		//AT-3346
		Timestamp leUpdatedDate = (Timestamp) oldReuqest.getAdditionalAttribute(Constants.LE_UPDATE_DATE);
		boolean isAnyFundsCleared = fundsInDBService.checkAnyFundsClearForContact(fundsInRequest,leUpdatedDate);//AT-3349
		
		boolean isCDINCFirstFundsIn = fundsInDBService.checkCDINCFirstFundsIn(fundsInRequest); //Add for AT-3738

		if (StringUtils.isNullOrEmpty(fundsInRequest.getTrade().getContractNumber())) {
			fundsInRequest.getTrade().setContractNumber(fundsInRequest.getPaymentFundsInId().toString());
		}

		fundsInRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		// to get tradecontactId in below method call
		if (null != oldReuqest.getAccId()) {
			oldReuqest.setTrade(fundsInRequest.getTrade());
			updateAddtionalAttributes(fundsInRequest, oldReuqest);
			Contact contact = (Contact) fundsInRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) fundsInRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			/**
			 * fraugster signup score is needed for fraugster paymentin request
			 **/
			if (null != contact) {
				FraugsterSummary fraugsterSummary = newRegistrationDBServiceImpl
						.getFirstFraugsterSignupSummary(contact.getId(), EntityEnum.CONTACT.toString());
				if (null != fraugsterSummary) {
					fundsInRequest.addAttribute("FraugsterSignupScore", fraugsterSummary.getScore());
				}
				/* check Account Contact status in AbstractDataEnricher class */
				// When account and contact status is inactive set
				// Do_Perform_Other_Checks to false where
				// on single check will be performed and status for all check is
				// set
				// to NOT_REQUIRED:
				checkAccountContactStatus(fundsInRequest, account, contact);
			}

		}
		fundsInRequest.setSanctionEligible(isThirdPartyPayment(fundsInRequest));
		fundsInRequest.addAttribute(Constants.ZERO_CLEAR_FUNDSIN, isFirstFundsIn);//AT-3346
		fundsInRequest.addAttribute(Constants.EDD_COUNTRY_NUMBER, eddCountryNumber);//AT-3346
		fundsInRequest.addAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE, isAnyFundsCleared);//AT-3349
		fundsInRequest.addAttribute(Constants.CONTACT_POI_EXISTS, getPOIExists(oldReuqest,fundsInRequest));//AT-3349
		fundsInRequest.addAttribute(Constants.CDINC_ZERO_CLEAR_FUNDSIN, isCDINCFirstFundsIn); //Add for AT-3738
		fundsInRequest.addAttribute("AccountTMFlag", oldReuqest.getAdditionalAttribute("AccountTMFlag"));
		fundsInRequest.addAttribute("DormantFlag", oldReuqest.getAdditionalAttribute("DormantFlag"));
		
		messageExchange.getRequest().addAttribute(Constants.OLD_REQUEST, oldReuqest);
		LOGGER.debug(Constants.OLD_REQUEST+"{}", oldReuqest);
	}

	@SuppressWarnings("unchecked")
	private void updateAddtionalAttributes(FundsInCreateRequest request, FundsInCreateRequest details) {
		request.setOrgId(details.getOrgId());
		request.addAttribute(Constants.FIELD_ACC_ACCOUNT, details.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT));
		Integer noOfContacts = (Integer) details.getAdditionalAttribute("noOfContacts");
		request.addAttribute("noOfContacts", noOfContacts);
		Integer tradeContactId = details.getTrade().getTradeContactId();
		List<Contact> contacts = (List<Contact>) details.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		if (null != contacts) {
			for (Contact contact : contacts) {
				if (contact.getTradeContactID().equals(tradeContactId)) {
					request.addAttribute(Constants.FIELD_CONTACT, contact);
					break;
				}
			}

		}
		String watchListIds = (String) details.getAdditionalAttribute(WATCHLISTID);
		watchListIds = watchListIds.replace("0,", "");
		if (watchListIds.length() > 2)
			request.addAttribute(WATCHLISTID, watchListIds);
	}

	/**
	 * Enrich white list data.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> enrichWhiteListData(Message<MessageContext> message) throws ComplianceException {
		UdateWhiteListRequest request = (UdateWhiteListRequest) message.getPayload().getGatewayMessageExchange()
				.getRequest();
		FundsInCreateRequest fundsInRequest = null;
		String displayName = null;
		if (null != request.getPaymentFundsInId()) {
			fundsInRequest = fundsInDBService.getFundsInRequestById(request.getPaymentFundsInId());
			displayName = newRegistrationDBServiceImpl
					.getCountryDisplayName(fundsInRequest.getTrade().getCountryOfFund());
		}
		if (null != fundsInRequest) {
			fundsInRequest.setFundsInId(request.getPaymentFundsInId());
			request.addAttribute(Constants.OLD_REQUEST, fundsInRequest);
			request.addAttribute(Constants.COUNTRY_DISPLAY_NAME, displayName);
			checkWatchlist(request, fundsInRequest.getAccId());

		}
		return message;
	}

	private void checkWatchlist(UdateWhiteListRequest request, Integer accId) throws ComplianceException {
		WatchListRules watchlistsR = new WatchListRules();
		List<String> watchlists = watchlistsR.getWatchlist(accId);
		if (null != watchlists) {
			for (Object ckeckWatchlist : watchlists) {
				if (ckeckWatchlist.equals(WatchList.E_TAILER_CLIENT_DOCUMENTATIONREQUIRED.getDescription()))
					request.setDocumentationRequiredWatchlist(true);
			}
		}

	}

	/**
	 * Checks if is account name null.
	 *
	 * @param request
	 *            the request
	 * @return true, if is account name null
	 */
	public boolean isAccountNameNull(FundsInCreateRequest request) {
		return StringUtils.isNullOrTrimEmpty(request.getTrade().getDebtorName());
	}

	/**
	 * Checks if is third party payment.
	 *
	 * @param request
	 *            the request
	 * @return true, if is third party payment
	 */
	public boolean isThirdPartyPayment(FundsInCreateRequest request) {
		return request.getTrade().getThirdPartyPayment() != null && request.getTrade().getThirdPartyPayment()
				&& request.getTrade().getDebtorName() != null && !request.getTrade().getDebtorName().trim().isEmpty();

	}

	/**
	 * Checks if is account number null.
	 *
	 * @param fundsInRequest
	 *            the funds in request
	 * @return true, if is account number null
	 */
	public boolean isAccountNumberNull(FundsInCreateRequest fundsInRequest) {
		return StringUtils.isNullOrTrimEmpty(fundsInRequest.getTrade().getDebtorAccountNumber());
	}

	/**
	 * Enrich data for failed funds in.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> enrichDataForFailedFundsIn(Message<MessageContext> message)
			throws ComplianceException {
		ReprocessFailedDetails request = message.getPayload().getGatewayMessageExchange()
				.getRequest(ReprocessFailedDetails.class);
		FundsInCreateRequest fundsInDetails;
		FundsInCreateResponse fundsInResponse = new FundsInCreateResponse();
		fundsInDetails = fundsInDBService.getFailedFundsInDetails(request.getTransId());
		message.getPayload().setOrgCode(fundsInDetails.getOrgCode());
		List<EventServiceLog> eslList = fundsInDBService.getFailedFundsInESLDetails(fundsInDetails);
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Integer userID = fundsInDBService.getUserIDFromSSOUserID(token.getPreferredUserName());
		token.setUserID(userID);
		
		boolean isFirstFundsIn = fundsInDBService.checkFirstFundsIn(fundsInDetails);						//AT-3346
		Integer eddCountryNumber = getCountryEddNumber(fundsInDetails.getTrade().getCountryOfFund());		//AT-3346
		Timestamp leUpdatedDate = (Timestamp) fundsInDetails.getAdditionalAttribute(Constants.LE_UPDATE_DATE);
		boolean isAnyFundsCleared = fundsInDBService.checkAnyFundsClearForContact(fundsInDetails,leUpdatedDate);//AT-3349
		boolean isCDINCFirstFundsIn = fundsInDBService.checkCDINCFirstFundsIn(fundsInDetails); //Add for AT-3738

		SanctionResponse sanctionResponse = new SanctionResponse();
		List<EventServiceLog> sanctionEntityLogList = new ArrayList<>();
		ContactResponse contactResponse = new ContactResponse();
		for (EventServiceLog esl : eslList) {
			if (esl.getServiceType() == 6) {
				buildFraugsterExchange(message, esl, token);
			}
			if (esl.getServiceType() == 7) {
				buildSanctionExchange(message, esl, sanctionResponse, token, sanctionEntityLogList);
			}
			if (esl.getServiceType() == 3 || esl.getServiceType() == 8 || esl.getServiceType() == 11
					|| esl.getServiceType() == 14) {
				buildInternalRuleServiceExchange(message, esl, contactResponse, fundsInDetails);
			}
			if (esl.getServiceType() == 9) {
				buildCustomCheckExchange(message, esl, token, fundsInDetails);
			}
		}
		if (Boolean.TRUE.equals(setServiceChecksEligiblity(fundsInDetails))) {
			fundsInDetails.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		}
		fundsInDetails.addAttribute("FailedFundsinDetails", request);
		fundsInDetails.addAttribute(Constants.OLD_REQUEST, fundsInDetails);
		fundsInDetails.addAttribute(Constants.ZERO_CLEAR_FUNDSIN, isFirstFundsIn);//AT-3346
		fundsInDetails.addAttribute(Constants.EDD_COUNTRY_NUMBER, eddCountryNumber);//AT-3346
		fundsInDetails.addAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE, isAnyFundsCleared);//AT-3349
		fundsInDetails.addAttribute(Constants.CONTACT_POI_EXISTS, getPOIExists(fundsInDetails,fundsInDetails));//AT-3349
		fundsInDetails.addAttribute(Constants.CDINC_ZERO_CLEAR_FUNDSIN, isCDINCFirstFundsIn); //Add for AT-3738
		message.getPayload().getGatewayMessageExchange().setRequest(fundsInDetails);
		message.getPayload().getGatewayMessageExchange().setResponse(fundsInResponse);
		return message;
	}

	/**
	 * Builds the fraugster exchange.
	 *
	 * @param message
	 *            the message
	 * @param esl
	 *            the esl
	 * @param token
	 *            the token
	 */
	private void buildFraugsterExchange(Message<MessageContext> message, EventServiceLog esl, UserProfile token) {
		FraugsterPaymentsInResponse fraugsterPayInResponse = new FraugsterPaymentsInResponse();
		FraugsterPaymentsInContactResponse fraugsterContactResponse = JsonConverterUtil
				.convertToObject(FraugsterPaymentsInContactResponse.class, esl.getProviderResponse());
		List<FraugsterPaymentsInContactResponse> fraugsterContactResponseList = new ArrayList<>();
		fraugsterContactResponseList.add(fraugsterContactResponse);
		fraugsterPayInResponse.setContactResponses(fraugsterContactResponseList);
		fraugsterPayInResponse.setStatus(fraugsterContactResponse.getStatus());
		FraugsterSummary summary = JsonConverterUtil.convertToObject(FraugsterSummary.class, esl.getSummary());
		MessageExchange ccExchange = new MessageExchange();
		ccExchange.setResponse(fraugsterPayInResponse);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);

		ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(esl.getEventId(),
				ServiceTypeEnum.FRAUGSTER_SERVICE, ServiceProviderEnum.FRAUGSTER_FUNDSIN_SERVICE, esl.getEntityId(),
				esl.getEntityVersion(), EntityEnum.CONTACT.name(), token.getUserID(), fraugsterContactResponse, summary,
				esl.getStatus()));

		message.getPayload().appendMessageExchange(ccExchange);
	}

	/**
	 * Creates the event service log entry with status.
	 *
	 * @param eventId
	 *            the event id
	 * @param servceType
	 *            the servce type
	 * @param providerEnum
	 *            the provider enum
	 * @param entityID
	 *            the entity ID
	 * @param entityVersion
	 *            the entity version
	 * @param entityType
	 *            the entity type
	 * @param user
	 *            the user
	 * @param providerResponse
	 *            the provider response
	 * @param summary
	 *            the summary
	 * @param serviceStatus
	 *            the service status
	 * @return the event service log
	 */
	@SuppressWarnings("squid:S00107")
	protected EventServiceLog createEventServiceLogEntryWithStatus(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary, String serviceStatus) {

		EventServiceLog eventServiceLog = new EventServiceLog();
		eventServiceLog.setServiceName(servceType.getShortName());
		eventServiceLog.setServiceProviderName(providerEnum.getProvidername());
		eventServiceLog.setEntityId(entityID);
		eventServiceLog.setEventId(eventId);
		eventServiceLog.setEntityVersion(entityVersion);
		eventServiceLog.setEntityType(entityType);
		eventServiceLog.setStatus(serviceStatus);
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setCratedBy(user);
		eventServiceLog.setUpdatedBy(user);
		eventServiceLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		return eventServiceLog;
	}

	/**
	 * Builds the sanction exchange.
	 *
	 * @param message
	 *            the message
	 * @param esl
	 *            the esl
	 * @param sanctionResponse
	 *            the sanction response
	 * @param token
	 *            the token
	 * @param sanctionEntityLogList
	 *            the sanction entity log list
	 */
	private void buildSanctionExchange(Message<MessageContext> message, EventServiceLog esl,
			SanctionResponse sanctionResponse, UserProfile token, List<EventServiceLog> sanctionEntityLogList) {

		EventServiceLog contactLog = getSanctionContactResponse(esl, sanctionResponse, token);

		sanctionEntityLogList.add(contactLog);

		createPreviousSanctionExchange(message, sanctionResponse, sanctionEntityLogList);
	}

	/**
	 * Gets the sanction contact response.
	 *
	 * @param esl
	 *            the esl
	 * @param sanctionResponse
	 *            the sanction response
	 * @param token
	 *            the token
	 * @return the sanction contact response
	 */
	private EventServiceLog getSanctionContactResponse(EventServiceLog esl, SanctionResponse sanctionResponse,
			UserProfile token) {
		if ("DEBTOR".equalsIgnoreCase(esl.getEntityType())) {
			List<SanctionContactResponse> sanctionContactResponseList = new ArrayList<>();
			SanctionContactResponse sanctionContactResponse = JsonConverterUtil
					.convertToObject(SanctionContactResponse.class, esl.getProviderResponse());
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
					esl.getSummary());
			sanctionContactResponseList.add(sanctionContactResponse);
			sanctionResponse.setContactResponses(sanctionContactResponseList);
			return createEventServiceLogEntryWithStatus(esl.getEventId(), ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE, esl.getEntityId(), esl.getEntityVersion(),
					EntityEnum.DEBTOR.name(), token.getUserID(), sanctionContactResponse, sanctionSummary,
					esl.getStatus());
		}
		return null;
	}

	/**
	 * Creates the previous sanction exchange.
	 *
	 * @param message
	 *            the message
	 * @param sanctionResponse
	 *            the sanction response
	 * @param sanctionEntityLogList
	 *            the sanction entity log list
	 */
	private void createPreviousSanctionExchange(Message<MessageContext> message, SanctionResponse sanctionResponse,
			List<EventServiceLog> sanctionEntityLogList) {
		MessageExchange ccExchange = new MessageExchange();
		ccExchange.setResponse(sanctionResponse);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
		for (EventServiceLog sanctionLog : sanctionEntityLogList) {
			ccExchange.addEventServiceLog(sanctionLog);
			message.getPayload().appendMessageExchange(ccExchange);
		}
	}

	/**
	 * Builds the internal rule service exchange.
	 *
	 * @param message
	 *            the message
	 * @param esl
	 *            the esl
	 * @param contactResponse
	 *            the contact response
	 */
	private void buildInternalRuleServiceExchange(Message<MessageContext> message, EventServiceLog esl,
			ContactResponse contactResponse, FundsInCreateRequest fundsInDetails) {
		if (esl.getServiceType() == 3) {
			BlacklistContactResponse blacklistContactResponse = JsonConverterUtil
					.convertToObject(BlacklistContactResponse.class, esl.getProviderResponse());
			contactResponse.setBlacklist(blacklistContactResponse);
		}
		if (esl.getServiceType() == 8) {
			CountryCheckContactResponse countryCheckContactResponse = JsonConverterUtil
					.convertToObject(CountryCheckContactResponse.class, esl.getProviderResponse());
			contactResponse.setCountryCheck(countryCheckContactResponse);
			fundsInDetails.addAttribute("CountryCheckStatus", esl.getStatus());
		}
		if (esl.getServiceType() == 11) {
			CardFraudScoreResponse cardFraudScoreResponse = JsonConverterUtil
					.convertToObject(CardFraudScoreResponse.class, esl.getProviderResponse());
			contactResponse.setCardFraudCheck(cardFraudScoreResponse);
		}
		if (esl.getServiceType() == 14) {
			FraudSightScoreResponse fraudSightScoreResponse = JsonConverterUtil
					.convertToObject(FraudSightScoreResponse.class, esl.getProviderResponse());
			contactResponse.setFraudSightCheck(fraudSightScoreResponse);
		}
		if (null != contactResponse.getBlacklist() && null != contactResponse.getCountryCheck()
				&& (null != contactResponse.getCardFraudCheck() || null != contactResponse.getFraudSightCheck())) {
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			InternalServiceResponse response = new InternalServiceResponse();
			List<ContactResponse> responseList = new ArrayList<>();
			responseList.add(contactResponse);
			response.setContacts(responseList);
			ccExchange.setResponse(response);
			message.getPayload().appendMessageExchange(ccExchange);
		}
	}

	/**
	 * Builds the custom check exchange.
	 *
	 * @param message
	 *            the message
	 * @param esl
	 *            the esl
	 * @param token
	 *            the token
	 * @param fundsInDetails
	 *            the funds in details
	 */
	private void buildCustomCheckExchange(Message<MessageContext> message, EventServiceLog esl, UserProfile token,
			FundsInCreateRequest fundsInDetails) {
		MessageExchange ccExchange = new MessageExchange();
		CustomCheckResponse serviceResponse = JsonConverterUtil.convertToObject(CustomCheckResponse.class,
				esl.getProviderResponse());
		CustomChecksRequest serviceRequest = new CustomChecksRequest();

		Account account = (Account) fundsInDetails.getAdditionalAttribute("account");

		serviceRequest.setOrgCode(fundsInDetails.getOrgCode());
		serviceRequest.setPaymentTransId(fundsInDetails.getFundsInId());
		serviceRequest.setAccId(account.getId());
		// ?? is below correct?
		serviceRequest.setBuyAmountOnAccount(account.getMaxTransactionAmount());
		serviceRequest.setBuyCurrencyOnAccount(account.getBuyingCurrency());
		serviceRequest.setReasonsOfTransferOnAccount(account.getPurposeOfTran());
		serviceRequest.setSellAmountOnAccount(account.getMaxTransactionAmount());
		serviceRequest.setSellCurrencyOnAccount(account.getSellingCurrency());
		fundsInDetails.setType(FUNDS_IN_REPEAT);
		serviceRequest.setESDocument(fundsInDetails);
		ccExchange.setRequest(serviceRequest);
		ccExchange.setResponse(serviceResponse);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(esl.getEventId(),
				ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, ServiceProviderEnum.CUSTOM_CHECKS_SERVICE, esl.getEntityId(),
				esl.getEntityVersion(), EntityEnum.ACCOUNT.name(), token.getUserID(), serviceResponse, serviceResponse,
				esl.getStatus()));
		fundsInDetails.addAttribute("customCheckESLStatus", esl.getStatus());
		message.getPayload().appendMessageExchange(ccExchange);
	}

	/**
	 * Sets the service checks eligiblity.
	 *
	 * @param fundsInDetails
	 *            the funds in details
	 * @return the boolean
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Boolean setServiceChecksEligiblity(FundsInCreateRequest fundsInDetails) throws ComplianceException {
		Boolean result = Boolean.FALSE;
		if (fundsInDetails.getAdditionalAttribute(Constants.FRAUGSTER_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsInDetails.addAttribute(Constants.RECHECK_FRAUGSTER_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsInDetails.addAttribute(Constants.RECHECK_FRAUGSTER_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsInDetails.getAdditionalAttribute(Constants.SANCTION_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsInDetails.addAttribute(Constants.RECHECK_SANCTION_ELIGIBLE, Boolean.TRUE);
			fundsInDetails.setSanctionEligible(true);
			Contact contact = (Contact) fundsInDetails.getAdditionalAttribute("contact");

			try {
				SanctionSummary sanctionSummary = newRegistrationDBServiceImpl.getFirstSanctionSummary(contact.getId(),
						EntityEnum.CONTACT.name());

				fundsInDetails.addAttribute("firstSanctionSummary" + contact.getId(), sanctionSummary);

				EntityDetails entityDetails = eventDBServiceImpl.isSanctionPerformed(contact.getId(),
						EntityEnum.CONTACT.name());
				contact.setSanctionPerformed(entityDetails.getIsExist());

			} catch (ComplianceException complianceException) {
				LOGGER.error("Exception in setServiceChecksEligiblity()", complianceException);
			}
			result = Boolean.TRUE;
		} else {
			fundsInDetails.addAttribute(Constants.RECHECK_SANCTION_ELIGIBLE, Boolean.FALSE);
			fundsInDetails.setSanctionEligible(false);
		}
		if (fundsInDetails.getAdditionalAttribute(Constants.BLACKLIST_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsInDetails.addAttribute(Constants.RECHECK_BLACKLIST_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsInDetails.addAttribute(Constants.RECHECK_BLACKLIST_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsInDetails.getAdditionalAttribute("CountryCheckStatus").equals(Constants.SERVICE_FAILURE)) {
			fundsInDetails.addAttribute(Constants.RECHECK_COUNTRY_CHECK_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsInDetails.addAttribute(Constants.RECHECK_COUNTRY_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsInDetails.getAdditionalAttribute("customCheckESLStatus").equals(Constants.SERVICE_FAILURE)) {
			fundsInDetails.addAttribute(Constants.RECHECK_CUSTOM_CHECK_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsInDetails.addAttribute(Constants.RECHECK_CUSTOM_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		return result;
	}
	
	/**
	 * AT-3349
	 * Gets the POI exists.
	 *
	 * @param oldReuqest the old reuqest
	 * @param fundsInRequest the funds in request
	 * @return the POI exists
	 */
	@SuppressWarnings("unchecked")
	private Integer getPOIExists(FundsInCreateRequest oldReuqest, FundsInCreateRequest fundsInRequest) {
		Integer poiExists = null;
		List<Contact> contacts = (List<Contact>) oldReuqest.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		for(Contact contact : contacts ) {
			if(fundsInRequest.getTrade().getTradeContactId().equals(contact.getTradeContactID())) {
				poiExists = contact.getPoiExists();
				break;
			}
		}
		return poiExists;
	}
}