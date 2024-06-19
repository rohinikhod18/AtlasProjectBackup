package com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
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
import com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncaching.ContactSanctionResponseCaching;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.FundsOutDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.WatchListRules;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.request.FundsOutCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
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

	/** The funds out DB service. */
	@Autowired
	private FundsOutDBServiceImpl fundsOutDBService;

	/** The event DB service impl. */
	@Autowired
	private IEventDBService eventDBServiceImpl;
	
	@Autowired
	private ContactSanctionResponseCaching contactSanctionStatusCaching;
	
	/** The Constant ERROR_LOG. */
	private static final String ERROR_LOG = "Exception in enrichData()";

	/**
	 * Enrich data.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> enrichData(Message<MessageContext> message) {

		try {
			getUserTableId(message);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutBaseRequest fundsOutBaseRequest = (FundsOutBaseRequest) messageExchange.getRequest();

			FundsOutRequest oldReuqest = fundsOutDBService.getFundsOutDetails(fundsOutBaseRequest);
			Account account = (Account) oldReuqest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);

			String custType;
			if (null != account && null != account.getCustType()) {
				custType = account.getCustType().trim();
				/* old request ka cust type **/ /*
												 * current request ka cust type
												 */
				if (!((null != custType && null != fundsOutBaseRequest.getCustType())
						&& (custType.equalsIgnoreCase(fundsOutBaseRequest.getCustType().trim())))) {
					/*
					 * If old and current request does not match then set
					 * current CustType into old request and pass it to data
					 * validation layer for error response code
					 */
					oldReuqest.setCustType(fundsOutBaseRequest.getCustType());
					enrichCommonData(message, oldReuqest);
				} else  {
					
					enrichPFXCFXData(message, oldReuqest);
				}
			} else {// No Account exists so forwarding to next validation level
					// for correct error Message
				enrichCommonData(message, oldReuqest);
			}
		} catch (Exception e) {
			LOGGER.error(ERROR_LOG, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Enrich PFX Data.
	 *
	 * @param message
	 *            the message
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> enrichPFXCFXData(Message<MessageContext> message, FundsOutRequest oldReuqest)
			throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			if (serviceInterfaceType != ServiceInterfaceType.FUNDSOUT)
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
						new Exception(" Invalid Service Interface Type"));
			enrichCommonData(message, oldReuqest);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		}
		return message;
	}

	

	/**
	 * Enrich Common Data.
	 *
	 * @param message
	 *            the message
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> enrichCommonData(Message<MessageContext> message, FundsOutRequest oldReuqest)
			throws ComplianceException {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			ServiceInterfaceType serviceInterfaceType = messageExchange.getServiceInterface();
			OperationEnum operation = messageExchange.getOperation();

			if (operation == OperationEnum.SANCTION_RESEND) {
				sanctionResend(message, messageExchange, eventDBServiceImpl, fundsOutDBService);
			} else if (operation == OperationEnum.CUSTOMCHECK_RESEND) {
				customCheckResend(message, messageExchange, eventDBServiceImpl, fundsOutDBService);
			} else if (operation == OperationEnum.FUNDS_OUT) {
				createFundsOut(messageExchange, eventDBServiceImpl, oldReuqest);
			} else if (operation == OperationEnum.FRAUGSTER_RESEND) {
				fraugsterResend(messageExchange, oldReuqest);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && operation == OperationEnum.UPDATE_OPI) {
				updateFundsOut(messageExchange, oldReuqest);
			} else if (serviceInterfaceType == ServiceInterfaceType.FUNDSOUT && operation == OperationEnum.DELETE_OPI) {
				deleteFundsOut(messageExchange, oldReuqest);
			} else if (operation == OperationEnum.BLACKLIST_RESEND) {
				blacklistResend(messageExchange, oldReuqest);
			} else if (operation == OperationEnum.PAYMENT_REFERENCE_RESEND) {
				paymentReferenceResend(messageExchange, oldReuqest); // added for AT-3658
			}
			messageExchange.getRequest().addAttribute(Constants.OLD_REQUEST, oldReuqest);
			LOGGER.debug(Constants.OLD_REQUEST+"{}", oldReuqest);
		} catch (Exception e) {
		     throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR, e);
		}
		return message;

	}

	/**
	 * This method is created for blacklist repeat check. 1.It gets all the old
	 * details of account and contact. 2.Also gets payment out and payment out
	 * attribute details.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> enrichDataForBlacklistRecheck(Message<MessageContext> message) {

		try {
			getUserTableId(message);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutBaseRequest fundsOutBaseRequest = (FundsOutBaseRequest) messageExchange.getRequest();
			FundsOutRequest oldReuqest = fundsOutDBService
					.getFundsOutDetailsForBlacklistRepeatCheck(fundsOutBaseRequest);
			oldReuqest.setCustType(fundsOutBaseRequest.getCustType());
			enrichCommonData(message, oldReuqest);
		} catch (Exception e) {
			LOGGER.error(ERROR_LOG, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Delete Funds Out.
	 *
	 * @param messageExchange
	 *            the message exchange
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the funds out request
	 */
	private FundsOutRequest deleteFundsOut(MessageExchange messageExchange, FundsOutRequest oldReuqest) {
		FundsOutDeleteRequest fRequest = (FundsOutDeleteRequest) messageExchange.getRequest();
		fRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		fRequest.addAttribute(Constants.ZERO_EU_FUNDS_CLEARED_AFTER_LE_DATE,Boolean.TRUE);
		if (null != oldReuqest && null != oldReuqest.getFundsOutId())
			updateAddtionalAttributes(fRequest, oldReuqest);
		return oldReuqest;
	}

	/**
	 * UpdateFundsOut.
	 *
	 * @param messageExchange
	 *            the message exchange
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the funds out request
	 */
	private FundsOutRequest updateFundsOut(MessageExchange messageExchange, FundsOutRequest oldReuqest) {
		FundsOutUpdateRequest fRequest = (FundsOutUpdateRequest) messageExchange.getRequest();
		fRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		if (null != oldReuqest && null != oldReuqest.getFundsOutId()) {
			oldReuqest.getBeneficiary().setAmount(fRequest.getAmount());
			if (fRequest.getBuyingAmountBaseValue() != null) {
				oldReuqest.getTrade().setBuyingAmountBaseValue(fRequest.getBuyingAmountBaseValue());
			}
			if (!StringUtils.isNullOrTrimEmpty(fRequest.getValueDate())) {
				oldReuqest.getTrade().setValueDate(fRequest.getValueDate());
			}
			updateAddtionalAttributes(fRequest, oldReuqest);
		}
		return oldReuqest;
	}

	/**
	 * Create Funds Out.
	 *
	 * @param messageExchange
	 *            the message exchange
	 * @param ieventDBService
	 *            the ievent DB service
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the funds out request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FundsOutRequest createFundsOut(MessageExchange messageExchange, IEventDBService ieventDBService,
			FundsOutRequest oldReuqest) throws ComplianceException {
		FundsOutRequest fundsOutRequest = (FundsOutRequest) messageExchange.getRequest();
		// to get tradecontactId in below method call
		if (null != oldReuqest.getAccId()) {
			oldReuqest.setTrade(fundsOutRequest.getTrade());
			oldReuqest.setBeneficiary(fundsOutRequest.getBeneficiary());
			updateAddtionalAttributes(fundsOutRequest, oldReuqest);
			Contact contact = (Contact) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) fundsOutRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			
			//Add for AT-3349 new Custome rule for EU Legal Entity
			String date = (String) oldReuqest.getAdditionalAttribute(Constants.LE_UPDATE_DATE);
			fundsOutDBService.checkAnyFundsClearForContact(fundsOutRequest, date);

			if (StringUtils.isNullOrEmpty(fundsOutRequest.getTrade().getContractNumber())) {
				fundsOutRequest.getTrade()
						.setContractNumber(fundsOutRequest.getBeneficiary().getPaymentFundsoutId().toString());
			}

			if (null != contact && null != account) {
				/**
				 * fraugster signup score is needed for fraugster paymentout
				 * request
				 **/
				FraugsterSummary fraugsterSummary = newRegistrationDBServiceImpl
						.getFirstFraugsterSignupSummary(contact.getId(), EntityEnum.CONTACT.toString());
				if (null != fraugsterSummary) {
					fundsOutRequest.addAttribute("FraugsterSignupScore", fraugsterSummary.getScore());
				}
				
				//ADD for AT-3161 Custom rule FP check
				if(account.getCustType().equals("PFX")){
					newRegistrationDBServiceImpl.getFraugsterSignupDetails(contact.getId(), EntityEnum.CONTACT.toString(),fundsOutRequest);//Changes for AT-3243
				}

				EntityDetails entityDetails = ieventDBService.isSanctionPerformed(contact.getId(),
						EntityEnum.CONTACT.name());
				EntityDetails beneficiaryEntityDetails = ieventDBService.isSanctionPerformed(
						fundsOutRequest.getBeneficiary().getBeneficiaryId(), EntityEnum.BENEFICIARY.name());
				EntityDetails bankEntityDetails = ieventDBService.isSanctionPerformed(
						fundsOutRequest.getBeneficiary().getBeneficiaryBankid(), EntityEnum.BANK.name());
				
				//AT-2248 Removing firstSanctionSummary and using previous one that has been performed
				SanctionSummary sanctionSummary = new SanctionSummary();
			setContactSanctionSummary(bankEntityDetails, fundsOutRequest, contact, sanctionSummary);
			setBeneSanctionSummary(beneficiaryEntityDetails, fundsOutRequest,sanctionSummary);	
			setBankSanctionSummary(bankEntityDetails, fundsOutRequest,sanctionSummary);	
				//AT-3102 - Sanction Contact check caching for Payment-Out
				checkContactSanctionCache(fundsOutRequest, contact);

				contact.setSanctionPerformed(entityDetails.getIsExist());
				fundsOutRequest.setBankSanctionPerformed(bankEntityDetails.getIsExist());
				fundsOutRequest.setBeneficiarySanctionPerformed(beneficiaryEntityDetails.getIsExist());
				// When account and contact status is inactive set
				// Do_Perform_Other_Checks to false where
				// on single check will be performed and status for all check is
				// set to NOT_REQUIRED: Laxmi B
				fundsOutRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
				/*
				 * check Account Contact status in AbstractDataEnricher class :
				 * Abhijit G
				 */
				checkAccountContactStatus(fundsOutRequest, account, contact);
				fundsOutRequest.addAttribute("AccountTMFlag", oldReuqest.getAdditionalAttribute("AccountTMFlag"));
				fundsOutRequest.addAttribute("DormantFlag", oldReuqest.getAdditionalAttribute("DormantFlag"));
			}
		}
		return oldReuqest;
	}

	
	private void setContactSanctionSummary (EntityDetails entityDetails,FundsOutRequest fundsOutRequest,Contact contact,SanctionSummary sanctionSummary)
	{
		if(null != entityDetails.getSummary()){
			fundsOutRequest.addAttribute(Constants.PREVIOUS_CONTACT_SANCTION_SUMMARY + contact.getId(),
					JsonConverterUtil.convertToObject(SanctionSummary.class, entityDetails.getSummary()));
		}else{
			fundsOutRequest.addAttribute(Constants.PREVIOUS_CONTACT_SANCTION_SUMMARY + contact.getId(),
					sanctionSummary);
		}
	}

	private void setBeneSanctionSummary (EntityDetails beneficiaryEntityDetails,FundsOutRequest fundsOutRequest,SanctionSummary sanctionSummary)
	{
		if(null != beneficiaryEntityDetails.getSummary())
			fundsOutRequest.addAttribute(Constants.PREVIOUS_BENEFICARY_SANCTION_SUMMARY + fundsOutRequest.getBeneficiary().getBeneficiaryId(),
				JsonConverterUtil.convertToObject(SanctionSummary.class, beneficiaryEntityDetails.getSummary()));
		else
			fundsOutRequest.addAttribute(Constants.PREVIOUS_BENEFICARY_SANCTION_SUMMARY + fundsOutRequest.getBeneficiary().getBeneficiaryId(),
					sanctionSummary);
	}

	private void setBankSanctionSummary (EntityDetails bankEntityDetails,FundsOutRequest fundsOutRequest,SanctionSummary sanctionSummary)
	{
		if(null != bankEntityDetails.getSummary())
			fundsOutRequest.addAttribute(Constants.PREVIOUS_BANK_SANCTION_SUMMARY + fundsOutRequest.getBeneficiary().getBeneficiaryBankid(),
				JsonConverterUtil.convertToObject(SanctionSummary.class, bankEntityDetails.getSummary()));
		else
			fundsOutRequest.addAttribute(Constants.PREVIOUS_BANK_SANCTION_SUMMARY + fundsOutRequest.getBeneficiary().getBeneficiaryBankid(),
					sanctionSummary);
		
	}
	/**
	 * fraugsterResend.
	 *
	 * @param msgExchange
	 *            the msg exchange
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the funds out request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FundsOutRequest fraugsterResend(MessageExchange msgExchange, FundsOutRequest oldReuqest)
			throws ComplianceException {
		FundsOutFruagsterResendRequest resendRequest = msgExchange.getRequest(FundsOutFruagsterResendRequest.class);
		// to get tradecontactId in below method call
		if (null != oldReuqest.getAccId()) {
			updateAddtionalAttributes(resendRequest, oldReuqest);
			Contact contact = (Contact) resendRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) resendRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			if (null == account || null == contact) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
			}
			oldReuqest.addAttribute(Constants.FIELD_CONTACT, contact);
			oldReuqest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);

			/**
			 * fraugster signup score is needed for fraugster paymentout
			 * request
			 **/
			FraugsterSummary fraugsterSummary = newRegistrationDBServiceImpl
					.getFirstFraugsterSignupSummary(contact.getId(), EntityEnum.CONTACT.toString());
			if (null != fraugsterSummary) {
				resendRequest.addAttribute("FraugsterSignupScore", fraugsterSummary.getScore());
			}
			/**
			 * Take firstSanctionSummary for particular Contact when we call
			 * getStatus() to show values of SanctionID, WorldCheck,
			 * OfacList on UI -Saylee
			 */
			resendRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		}
		return oldReuqest;
	}

	/**
	 * Blacklist resend.
	 *
	 * @param msgExchange
	 *            the msg exchange
	 * @param oldReuqest
	 *            the old reuqest
	 * @return the funds out request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FundsOutRequest blacklistResend(MessageExchange msgExchange, FundsOutRequest oldReuqest)
			throws ComplianceException {
		FundsOutBlacklistResendRequest resendRequest = msgExchange.getRequest(FundsOutBlacklistResendRequest.class);
		// to get tradecontactId in below method call
		if (null != oldReuqest.getAccId()) {
			updateAddtionalAttributes(resendRequest, oldReuqest);
			Contact contact = (Contact) resendRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) resendRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			if (null == account || null == contact) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
			}
			oldReuqest.addAttribute(Constants.FIELD_CONTACT, contact);
			oldReuqest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
			msgExchange.getRequest().addAttribute(Constants.OLD_REQUEST, oldReuqest);
		}
		return oldReuqest;
	}
	
	//added for AT-3658
	/**
	 * Payment reference resend.
	 *
	 * @param msgExchange the msg exchange
	 * @param oldReuqest the old reuqest
	 * @return the funds out request
	 * @throws ComplianceException the compliance exception
	 */
	private FundsOutRequest paymentReferenceResend(MessageExchange msgExchange, FundsOutRequest oldReuqest)
			throws ComplianceException {
		FundsOutPaymentReferenceResendRequest resendRequest = msgExchange.getRequest(FundsOutPaymentReferenceResendRequest.class);
		// to get tradecontactId in below method call
		if (null != oldReuqest.getAccId()) {
			updateAddtionalAttributes(resendRequest, oldReuqest);
			Contact contact = (Contact) resendRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
			Account account = (Account) resendRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			if (null == account || null == contact) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
			}
			oldReuqest.addAttribute(Constants.FIELD_CONTACT, contact);
			oldReuqest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
			msgExchange.getRequest().addAttribute(Constants.OLD_REQUEST, oldReuqest);
		}
		return oldReuqest;
	}

	/**
	 * Custom Check Resend.
	 *
	 * @param message
	 *            the message
	 * @param messageExchange
	 *            the message exchange
	 * @param ieventDBService
	 *            the ievent DB service
	 * @param dbService
	 *            the db service
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void customCheckResend(Message<MessageContext> message, MessageExchange messageExchange,
			IEventDBService ieventDBService, FundsOutDBServiceImpl dbService) throws ComplianceException {
		FundsOutCustomCheckResendRequest customCheckResend = messageExchange
				.getRequest(FundsOutCustomCheckResendRequest.class);
		FundsOutRequest fundsOutRequest = dbService.getFundsOutRequestById(customCheckResend.getPaymentOutId());
		EntityDetails entityDetails = ieventDBService
				.isServicePerformedForFundsOutCustomCheck(customCheckResend.getPaymentOutId(), "VELOCITYCHECK");
		fundsOutRequest.setFundsOutId(customCheckResend.getPaymentOutId());
		Account account = dbService.getAccountByTradeAccountNumber(fundsOutRequest.getTrade().getTradeAccountNumber(),
				customCheckResend.getOrgCode());
		Contact contact = dbService.getContactByTradeContactID(fundsOutRequest.getTrade().getTradeContactId(),
				customCheckResend.getOrgCode());
		//ADD for AT-3161 Custom rule FP check
		if(account.getCustType().equals("PFX")){
			newRegistrationDBServiceImpl.getFraugsterSignupDetails(contact.getId(), EntityEnum.CONTACT.toString(),fundsOutRequest);//changes for AT-3243
		}
		
		//Add for AT-3349 new Custome rule for EU Legal Entity
			fundsOutDBService.checkAnyFundsClearForContact(fundsOutRequest, account.getLeUpdateDate());
		
		
		entityDetails.setBeneficiaryName(fundsOutRequest.getBeneficiary().getFullName());
		message.getPayload().getGatewayMessageExchange().setRequest(fundsOutRequest);
		fundsOutRequest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
		fundsOutRequest.addAttribute(Constants.FIELD_CONTACT, contact);
		fundsOutRequest.addAttribute("FUNDS_OUT_REPEAT", customCheckResend);
		fundsOutRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS, entityDetails);
		fundsOutRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		messageExchange.setRequest(fundsOutRequest);
	}

	/**
	 * Sanction Resend.
	 *
	 * @param message
	 *            the message
	 * @param messageExchange
	 *            the message exchange
	 * @param ieventDBService
	 *            the ievent DB service
	 * @param dbService
	 *            the db service
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void sanctionResend(Message<MessageContext> message, MessageExchange messageExchange,
			IEventDBService ieventDBService, FundsOutDBServiceImpl dbService) throws ComplianceException {
		FundsOutSanctionResendRequest sanctionResend = messageExchange.getRequest(FundsOutSanctionResendRequest.class);
		FundsOutRequest fundsOutRequest = dbService.getFundsOutRequestById(sanctionResend.getPaymentOutId());
		fundsOutRequest.setFundsOutId(sanctionResend.getPaymentOutId());

		Account account = dbService.getAccountByTradeAccountNumber(fundsOutRequest.getTrade().getTradeAccountNumber(),
				sanctionResend.getOrgCode());
		Contact contact = dbService.getContactByTradeContactID(fundsOutRequest.getTrade().getTradeContactId(),
				sanctionResend.getOrgCode());
		message.getPayload().getGatewayMessageExchange().setRequest(fundsOutRequest);
		fundsOutRequest.addAttribute(Constants.FIELD_ACC_ACCOUNT, account);
		fundsOutRequest.addAttribute(Constants.FIELD_CONTACT, contact);
		Integer oldOrgId = newRegistrationDBServiceImpl.getOldOrganisationID(account.getId());
		if(null == oldOrgId || 0 == oldOrgId) {
			fundsOutRequest.setOrgId(newRegistrationDBServiceImpl.getOrganisationID(fundsOutRequest.getOrgCode()));
		} else {
			fundsOutRequest.setOrgId(oldOrgId);
		}
		fundsOutRequest.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		if (EntityEnum.CONTACT.name().equalsIgnoreCase(sanctionResend.getEntityType())) {
			SanctionSummary sanctionSummary = newRegistrationDBServiceImpl
					.getFirstSanctionSummary(sanctionResend.getEntityId(), sanctionResend.getEntityType());
			fundsOutRequest.addAttribute(Constants.FIELD_FIRST_SANCTION_SUMMARY + sanctionResend.getEntityId(),
					sanctionSummary);
			fundsOutRequest.addAttribute(Constants.FIELD_FIRST_SANCTION_SUMMARY, sanctionSummary);
			EntityDetails entityDetails = ieventDBService.getPreviousSanctionDetails(sanctionResend.getEntityId(),
					EntityEnum.CONTACT.name());
			contact.setSanctionPerformed(entityDetails.getIsExist());
			fundsOutRequest.setIsContactEligible(Boolean.TRUE);
			fundsOutRequest.setIsBeneficiaryEligible(Boolean.FALSE);
			fundsOutRequest.setIsBankEligible(Boolean.FALSE);
			entityDetails.setContactName(contact.getFullName());
			fundsOutRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS, entityDetails);
		} else if (EntityEnum.BENEFICIARY.name().equalsIgnoreCase(sanctionResend.getEntityType())) {
			SanctionSummary sanctionBeneficiarySummary = newRegistrationDBServiceImpl
					.getFirstSanctionSummary(sanctionResend.getEntityId(), sanctionResend.getEntityType());
			EntityDetails entityDetails = ieventDBService.getPreviousSanctionDetails(sanctionResend.getEntityId(),
					EntityEnum.BENEFICIARY.name());
			fundsOutRequest.setIsContactEligible(Boolean.FALSE);
			fundsOutRequest.setIsBankEligible(Boolean.FALSE);
			fundsOutRequest.setIsBeneficiaryEligible(Boolean.TRUE);
			fundsOutRequest.setBeneficiarySanctionPerformed(entityDetails.getIsExist());
			entityDetails.setBeneficiaryName(fundsOutRequest.getBeneficiary().getFullName());
			fundsOutRequest.addAttribute(
					Constants.FIELD_FIRST_BENEFICARY_SANCTION_SUMMARY + sanctionResend.getEntityId(),
					sanctionBeneficiarySummary);
			fundsOutRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS, entityDetails);
		} else {
			SanctionSummary sanctionBankSummary = newRegistrationDBServiceImpl
					.getFirstSanctionSummary(sanctionResend.getEntityId(), sanctionResend.getEntityType());
			EntityDetails entityDetails = ieventDBService.getPreviousSanctionDetails(sanctionResend.getEntityId(),
					EntityEnum.BANK.name());
			fundsOutRequest.setIsContactEligible(Boolean.FALSE);
			fundsOutRequest.setIsBankEligible(Boolean.TRUE);
			fundsOutRequest.setIsBeneficiaryEligible(Boolean.FALSE);
			fundsOutRequest.setBankSanctionPerformed(entityDetails.getIsExist());
			entityDetails.setBankName(fundsOutRequest.getBeneficiary().getBeneficaryBankName());
			fundsOutRequest.addAttribute(
					Constants.FIELD_FIRST_BENEFICARY_SANCTION_SUMMARY + sanctionResend.getEntityId(),
					sanctionBankSummary);
			fundsOutRequest.addAttribute(Constants.FIELD_ENTITY_DETAILS, entityDetails);
		}

		fundsOutRequest.addAttribute("SANCTION_RESEND_REQUEST", sanctionResend);
		messageExchange.setRequest(fundsOutRequest);
	}

	/**
	 * Update Addtional Attributes.
	 *
	 * @param request
	 *            the request
	 * @param details
	 *            the details
	 */
	private void updateAddtionalAttributes(FundsOutBaseRequest request, FundsOutRequest details) {
		request.setOrgId(details.getOrgId());
		request.addAttribute(Constants.FIELD_ACC_ACCOUNT, details.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT));
		Integer noOfContacts = (Integer) details.getAdditionalAttribute("noOfContacts");
		request.addAttribute("noOfContacts", noOfContacts);
		Integer tradeContactId = null;
		if (null != details.getTrade())
			tradeContactId = details.getTrade().getTradeContactId();
		if (null != noOfContacts) {
			Contact contact;
			for (int i = 1; i <= noOfContacts; i++) {
				contact = (Contact) details.getAdditionalAttribute("contact" + i);
				if (contact.getTradeContactID().equals(tradeContactId)) {
					request.addAttribute(Constants.FIELD_CONTACT, contact);
				}

				request.addAttribute("watchlistid" + i, details.getAdditionalAttribute("watchlistid" + i));
				request.addAttribute("watchlistreasonname" + i,
						details.getAdditionalAttribute("watchlistreasonname" + i));
			}
		}

	}

	/**
	 * Enrich WhiteList Data.
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
		FundsOutRequest fundsOutRequest = null;
		String displayName = null;
		if (null != request.getPaymentFundsOutId()) {
			fundsOutRequest = fundsOutDBService.getFundsOutRequestById(request.getPaymentFundsOutId());
			displayName = newRegistrationDBServiceImpl
					.getCountryDisplayName(fundsOutRequest.getBeneficiary().getCountry());
		}
		if (null != fundsOutRequest) {
			fundsOutRequest.setFundsOutId(request.getPaymentFundsOutId());
			request.addAttribute(Constants.OLD_REQUEST, fundsOutRequest);
			request.addAttribute(Constants.COUNTRY_DISPLAY_NAME, displayName);
			checkWatchlist(request, fundsOutRequest.getAccId());
		}
		return message;
	}

	private void checkWatchlist(UdateWhiteListRequest request, Integer accId) throws ComplianceException {
		WatchListRules watchlistsR = new WatchListRules();
		List<String> watchlists = watchlistsR.getWatchlist(accId);
		if (null != watchlists) {
			for (Object ckeckWatchlist : watchlists) {
				if (ckeckWatchlist.equals(WatchList.US_CLIENT_LIST_B_CLIENT.getDescription()))
					request.setUSClientListBBeneAccNumber(true);
			}
		}

	}

	/**
	 * Enrich data for failed funds out.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws Exception
	 */
	public Message<MessageContext> enrichDataForFailedFundsOut(Message<MessageContext> message)
			throws ComplianceException {
		ReprocessFailedDetails request = message.getPayload().getGatewayMessageExchange()
				.getRequest(ReprocessFailedDetails.class);
		FundsOutRequest fundsOutDetails;
		FundsOutResponse fundsOutResponse = new FundsOutResponse();
		Integer count = 0;
		fundsOutDetails = fundsOutDBService.getFailedFundsOutDetails(request.getTransId());
		List<EventServiceLog> eslList = fundsOutDBService.getFailedFundsOutESLDetails(fundsOutDetails);
		message.getPayload().setOrgCode(fundsOutDetails.getOrgCode());
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Integer userID = fundsOutDBService.getUserIDFromSSOUserID(token.getPreferredUserName());
		token.setUserID(userID);
		
		Contact contact = (Contact) fundsOutDetails.getAdditionalAttribute(Constants.FIELD_CONTACT);
		Account account = (Account) fundsOutDetails.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		//Add for AT-3349 new Custome rule for EU Legal Entity
		String date = (String) fundsOutDetails.getAdditionalAttribute(Constants.LE_UPDATE_DATE);
		fundsOutDBService.checkAnyFundsClearForContact(fundsOutDetails, date);
		
		//ADD for AT-3161 Custom rule FP check
		if(account.getCustType().equals("PFX")){
			newRegistrationDBServiceImpl.getFraugsterSignupDetails(contact.getId(), EntityEnum.CONTACT.toString(),fundsOutDetails);//Changes for AT-3243
		}
		
		SanctionResponse sanctionResponse = new SanctionResponse();
		List<EventServiceLog> sanctionEntityLogList = new ArrayList<>();
		ContactResponse contactResponse = new ContactResponse();
		for (EventServiceLog esl : eslList) {
			if (esl.getServiceType() == 6) {
				buildFraugsterExchange(message, esl, token);
			}
			if (esl.getServiceType() == 7) {
				count++;
				buildSanctionExchange(message, esl, count, sanctionResponse, token, sanctionEntityLogList);
			}
			if (esl.getServiceType() == 3 || esl.getServiceType() == 8 || esl.getServiceType() == 15) {
				buildInternalRuleServiceExchange(message, esl, contactResponse, fundsOutDetails);

			}
			if (esl.getServiceType() == 9) {
				buildCustomCheckExchange(message, esl, token, fundsOutDetails);
			}
		}
		if (Boolean.TRUE.equals(setServiceChecksEligiblity(fundsOutDetails))) {
			fundsOutDetails.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
		}
		fundsOutDetails.addAttribute("FailedFundsoutDetails", request);
		fundsOutDetails.addAttribute(Constants.OLD_REQUEST, fundsOutDetails);
		message.getPayload().getGatewayMessageExchange().setRequest(fundsOutDetails);
		message.getPayload().getGatewayMessageExchange().setResponse(fundsOutResponse);
		return message;
	}

	private Boolean setServiceChecksEligiblity(FundsOutRequest fundsOutDetails) throws ComplianceException {
		Boolean result = Boolean.FALSE;
		if (fundsOutDetails.getAdditionalAttribute(Constants.FRAUGSTER_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsOutDetails.addAttribute(Constants.RECHECK_FRAUGSTER_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsOutDetails.addAttribute(Constants.RECHECK_FRAUGSTER_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsOutDetails.getAdditionalAttribute(Constants.SANCTION_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsOutDetails.addAttribute(Constants.RECHECK_SANCTION_ELIGIBLE, Boolean.TRUE);
			fundsOutDetails.setIsContactEligible(Boolean.TRUE);
			fundsOutDetails.setIsBeneficiaryEligible(Boolean.TRUE);
			fundsOutDetails.setIsBankEligible(Boolean.TRUE);
			Contact contact = (Contact) fundsOutDetails.getAdditionalAttribute("contact");

			try {
				SanctionSummary sanctionSummary = newRegistrationDBServiceImpl.getFirstSanctionSummary(contact.getId(),
						EntityEnum.CONTACT.name());
				SanctionSummary sanctionBeneficiarySummary = newRegistrationDBServiceImpl.getFirstSanctionSummary(
						fundsOutDetails.getBeneficiary().getBeneficiaryId(), EntityEnum.BENEFICIARY.name());
				SanctionSummary sanctionBankSummary = newRegistrationDBServiceImpl.getFirstSanctionSummary(
						fundsOutDetails.getBeneficiary().getBeneficiaryBankid(), EntityEnum.BANK.name());

				fundsOutDetails.addAttribute(Constants.FIELD_FIRST_SANCTION_SUMMARY + contact.getId(), sanctionSummary);
				fundsOutDetails.addAttribute(
						"firstBeneficiarySanctionSummary" + fundsOutDetails.getBeneficiary().getBeneficiaryId(),
						sanctionBeneficiarySummary);
				fundsOutDetails.addAttribute(
						"firstBankSanctionSummary" + fundsOutDetails.getBeneficiary().getBeneficiaryBankid(),
						sanctionBankSummary);

				EntityDetails entityDetails = eventDBServiceImpl.isSanctionPerformed(contact.getId(),
						EntityEnum.CONTACT.name());
				EntityDetails beneficiaryEntityDetails = eventDBServiceImpl.isSanctionPerformed(
						fundsOutDetails.getBeneficiary().getBeneficiaryId(), EntityEnum.BENEFICIARY.name());
				EntityDetails bankEntityDetails = eventDBServiceImpl.isSanctionPerformed(
						fundsOutDetails.getBeneficiary().getBeneficiaryBankid(), EntityEnum.BANK.name());

				contact.setSanctionPerformed(entityDetails.getIsExist());
				fundsOutDetails.setBankSanctionPerformed(bankEntityDetails.getIsExist());
				fundsOutDetails.setBeneficiarySanctionPerformed(beneficiaryEntityDetails.getIsExist());

			} catch (ComplianceException complianceException) {
				LOGGER.error("Exception in setServiceChecksEligiblity()", complianceException);
			}
			result = Boolean.TRUE;
		} else {
			fundsOutDetails.addAttribute(Constants.RECHECK_SANCTION_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsOutDetails.getAdditionalAttribute(Constants.BLACKLIST_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsOutDetails.addAttribute(Constants.RECHECK_BLACKLIST_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsOutDetails.addAttribute(Constants.RECHECK_BLACKLIST_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsOutDetails.getAdditionalAttribute("CountryCheckStatus").equals(Constants.SERVICE_FAILURE)) {
			fundsOutDetails.addAttribute(Constants.RECHECK_COUNTRY_CHECK_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsOutDetails.addAttribute(Constants.RECHECK_COUNTRY_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsOutDetails.getAdditionalAttribute("customCheckESLStatus").equals(Constants.SERVICE_FAILURE)) {
			fundsOutDetails.addAttribute(Constants.RECHECK_CUSTOM_CHECK_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsOutDetails.addAttribute(Constants.RECHECK_CUSTOM_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		if (fundsOutDetails.getAdditionalAttribute(Constants.PAYMENT_REFERENCE_STATUS).equals(Constants.SERVICE_FAILURE)) {
			fundsOutDetails.addAttribute(Constants.RECHECK_BLACKLIST_PAY_REF_CHECK_ELIGIBLE, Boolean.TRUE);
			result = Boolean.TRUE;
		} else {
			fundsOutDetails.addAttribute(Constants.RECHECK_BLACKLIST_PAY_REF_CHECK_ELIGIBLE, Boolean.FALSE);
		}
		return result;
	}

	private void buildCustomCheckExchange(Message<MessageContext> message, EventServiceLog esl, UserProfile token,
			FundsOutRequest fundsOutRequest) {
		MessageExchange ccExchange = new MessageExchange();
		CustomCheckResponse serviceResponse = JsonConverterUtil.convertToObject(CustomCheckResponse.class,
				esl.getProviderResponse());
		CustomChecksRequest serviceRequest = new CustomChecksRequest();

		Account account = (Account) fundsOutRequest.getAdditionalAttribute("account");

		serviceRequest.setOrgCode(fundsOutRequest.getOrgCode());
		serviceRequest.setPaymentTransId(fundsOutRequest.getFundsOutId());
		serviceRequest.setAccId(account.getId());
		// ?? is below correct?
		serviceRequest.setBuyAmountOnAccount(account.getMaxTransactionAmount());
		serviceRequest.setBuyCurrencyOnAccount(account.getBuyingCurrency());
		serviceRequest.setReasonsOfTransferOnAccount(account.getPurposeOfTran());
		serviceRequest.setSellAmountOnAccount(account.getMaxTransactionAmount());
		serviceRequest.setSellCurrencyOnAccount(account.getSellingCurrency());
		fundsOutRequest.setType("FUNDS_OUT_REPEAT");
		serviceRequest.setESDocument(fundsOutRequest);
		ccExchange.setRequest(serviceRequest);
		ccExchange.setResponse(serviceResponse);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(esl, ServiceTypeEnum.CUSTOM_CHECKS_SERVICE,
				ServiceProviderEnum.CUSTOM_CHECKS_SERVICE, EntityEnum.BENEFICIARY.name(), token.getUserID(),
				serviceResponse, serviceResponse));
		fundsOutRequest.addAttribute("customCheckESLStatus", esl.getStatus());
		message.getPayload().appendMessageExchange(ccExchange);
	}

	private void buildInternalRuleServiceExchange(Message<MessageContext> message, EventServiceLog esl,
			ContactResponse contactResponse, FundsOutRequest fundsOutDetails) {
		if (esl.getServiceType() == 3) {
			BlacklistContactResponse blacklistContactResponse = JsonConverterUtil
					.convertToObject(BlacklistContactResponse.class, esl.getProviderResponse());
			contactResponse.setBlacklist(blacklistContactResponse);
		}
		if (esl.getServiceType() == 8) {
			CountryCheckContactResponse countryCheckContactResponse = JsonConverterUtil
					.convertToObject(CountryCheckContactResponse.class, esl.getProviderResponse());
			contactResponse.setCountryCheck(countryCheckContactResponse);
			fundsOutDetails.addAttribute("CountryCheckStatus", esl.getStatus());
		}
		if (esl.getServiceType() == 15) {
			BlacklistPayrefContactResponse blacklistPayrefContactResponse = JsonConverterUtil
					.convertToObject(BlacklistPayrefContactResponse.class, esl.getProviderResponse());
			contactResponse.setBlacklistPayref(blacklistPayrefContactResponse);
			fundsOutDetails.addAttribute("BlacklistPayrefStatus", esl.getStatus());
		}
		if (null != contactResponse.getBlacklist() && null != contactResponse.getCountryCheck() && 
				null != contactResponse.getBlacklistPayref()) {
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

	private void buildSanctionExchange(Message<MessageContext> message, EventServiceLog esl, Integer count,
			SanctionResponse sanctionResponse, UserProfile token, List<EventServiceLog> sanctionEntityLogList) {

		getSanctionContactResponse(esl, sanctionResponse, token);

		getSanctionBeneResponse(esl, sanctionResponse, token);

		getSanctionBankResponse(esl, sanctionResponse, token);

		if (count == 3) {
			sanctionEntityLogList.add((EventServiceLog) sanctionResponse.getAdditionalAttribute("contactLog"));
			sanctionEntityLogList.add((EventServiceLog) sanctionResponse.getAdditionalAttribute("bankLog"));
			sanctionEntityLogList.add((EventServiceLog) sanctionResponse.getAdditionalAttribute("beneLog"));
		}

		createPreviousSanctionExchange(message, sanctionResponse, count, sanctionEntityLogList);
	}

	private void buildFraugsterExchange(Message<MessageContext> message, EventServiceLog esl, UserProfile token) {
		FraugsterPaymentsOutResponse fraugsterPayOutResponse = new FraugsterPaymentsOutResponse();
		FraugsterPaymentsOutContactResponse fraugsterContactResponse = JsonConverterUtil
				.convertToObject(FraugsterPaymentsOutContactResponse.class, esl.getProviderResponse());
		List<FraugsterPaymentsOutContactResponse> fraugsterContactResponseList = new ArrayList<>();
		fraugsterContactResponseList.add(fraugsterContactResponse);
		fraugsterPayOutResponse.setContactResponses(fraugsterContactResponseList);
		fraugsterPayOutResponse.setStatus(fraugsterContactResponse.getStatus());
		FraugsterSummary summary = JsonConverterUtil.convertToObject(FraugsterSummary.class, esl.getSummary());
		MessageExchange ccExchange = new MessageExchange();
		ccExchange.setResponse(fraugsterPayOutResponse);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);

		ccExchange.addEventServiceLog(createEventServiceLogEntryWithStatus(esl, ServiceTypeEnum.FRAUGSTER_SERVICE,
				ServiceProviderEnum.FRAUGSTER_FUNDSOUT_SERVICE, EntityEnum.CONTACT.name(), token.getUserID(),
				fraugsterContactResponse, summary));

		message.getPayload().appendMessageExchange(ccExchange);
	}

	private void createPreviousSanctionExchange(Message<MessageContext> message, SanctionResponse sanctionResponse,
			Integer count, List<EventServiceLog> sanctionEntityLogList) {
		if (count == 3) {
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setResponse(sanctionResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.SANCTION_SERVICE);
			for (EventServiceLog sanctionLog : sanctionEntityLogList) {
				ccExchange.addEventServiceLog(sanctionLog);
			}
			message.getPayload().appendMessageExchange(ccExchange);
		}
	}

	private EventServiceLog getSanctionBankResponse(EventServiceLog esl, SanctionResponse sanctionResponse,
			UserProfile token) {
		if ("BANK".equalsIgnoreCase(esl.getEntityType())) {
			List<SanctionBankResponse> sanctionBankResponseList = new ArrayList<>();
			SanctionBankResponse sanctionBankResponse = JsonConverterUtil.convertToObject(SanctionBankResponse.class,
					esl.getProviderResponse());
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
					esl.getSummary());
			sanctionBankResponseList.add(sanctionBankResponse);
			sanctionResponse.setBankResponses(sanctionBankResponseList);
			EventServiceLog bankLog = createEventServiceLogEntryWithStatus(esl, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE, EntityEnum.BANK.name(), token.getUserID(),
					sanctionBankResponse, sanctionSummary);
			sanctionResponse.addAttribute("bankLog", bankLog);
			return bankLog;
		}
		return null;
	}

	private EventServiceLog getSanctionBeneResponse(EventServiceLog esl, SanctionResponse sanctionResponse,
			UserProfile token) {
		if ("BENEFICIARY".equalsIgnoreCase(esl.getEntityType())) {
			List<SanctionBeneficiaryResponse> sanctionBeneficiaryResponseList = new ArrayList<>();
			SanctionBeneficiaryResponse sanctionBeneResponse = JsonConverterUtil
					.convertToObject(SanctionBeneficiaryResponse.class, esl.getProviderResponse());
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
					esl.getSummary());
			sanctionBeneficiaryResponseList.add(sanctionBeneResponse);
			sanctionResponse.setBeneficiaryResponses(sanctionBeneficiaryResponseList);
			EventServiceLog beneLog = createEventServiceLogEntryWithStatus(esl, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE, EntityEnum.BENEFICIARY.name(), token.getUserID(),
					sanctionBeneResponse, sanctionSummary);
			sanctionResponse.addAttribute("beneLog", beneLog);
			return beneLog;
		}
		return null;
	}

	private EventServiceLog getSanctionContactResponse(EventServiceLog esl, SanctionResponse sanctionResponse,
			UserProfile token) {
		if ("CONTACT".equalsIgnoreCase(esl.getEntityType())) {
			List<SanctionContactResponse> sanctionContactResponseList = new ArrayList<>();
			SanctionContactResponse sanctionContactResponse = JsonConverterUtil
					.convertToObject(SanctionContactResponse.class, esl.getProviderResponse());
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
					esl.getSummary());
			sanctionContactResponseList.add(sanctionContactResponse);
			sanctionResponse.setContactResponses(sanctionContactResponseList);
			EventServiceLog contactLog = createEventServiceLogEntryWithStatus(esl, ServiceTypeEnum.SANCTION_SERVICE,
					ServiceProviderEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(), token.getUserID(),
					sanctionContactResponse, sanctionSummary);
			sanctionResponse.addAttribute("contactLog", contactLog);
			return contactLog;
		}
		return null;
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
	protected EventServiceLog createEventServiceLogEntryWithStatus(EventServiceLog esl, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, String entityType, Integer user, Object providerResponse,
			Object summary) {

		EventServiceLog eventServiceLog = new EventServiceLog();
		eventServiceLog.setServiceName(servceType.getShortName());
		eventServiceLog.setServiceProviderName(providerEnum.getProvidername());
		eventServiceLog.setEntityId(esl.getEntityId());
		eventServiceLog.setEventId(esl.getEventId());
		eventServiceLog.setEntityVersion(esl.getEntityVersion());
		eventServiceLog.setEntityType(entityType);
		eventServiceLog.setStatus(esl.getStatus());
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setCratedBy(user);
		eventServiceLog.setUpdatedBy(user);
		eventServiceLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		return eventServiceLog;
	}

	/**
	 * Check contact sanction cache.
	 *
	 * @param fundsOutRequest the funds out request
	 * @param contact the contact
	 */
	private void checkContactSanctionCache(FundsOutRequest fundsOutRequest, Contact contact) {
		String sanctionContactCacheResponse = contactSanctionStatusCaching.getContactSanctionCacheResponse(contact.getId());
		
		if(null != sanctionContactCacheResponse) {
			SanctionContactResponse sanctionContactResponse = JsonConverterUtil.convertToObject(SanctionContactResponse.class,
					sanctionContactCacheResponse);
			LOGGER.info("\n---------------Sanction Cache Response------------");
			LOGGER.info("{} : {}",sanctionContactResponse.getSanctionId(),sanctionContactCacheResponse);
			if(Boolean.TRUE.equals(sanctionContactResponse.getIsCachedResponse()))
				fundsOutRequest.setIsContactEligible(Boolean.FALSE);
		}
		fundsOutRequest.addAttribute("contactSanctionCachedProviderResponse", sanctionContactCacheResponse);
	}
	
	//Added for AT-3658
	/**
	 * Enrich data for payment reference recheck.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> enrichDataForPaymentReferenceRecheck(Message<MessageContext> message) { 
		try {
			getUserTableId(message);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			FundsOutBaseRequest fundsOutBaseRequest = (FundsOutBaseRequest) messageExchange.getRequest();
			FundsOutRequest oldReuqest = fundsOutDBService
					.getFundsOutDetailsForPaymentReferenceRepeatCheck(fundsOutBaseRequest);
			oldReuqest.setCustType(fundsOutBaseRequest.getCustType());
			enrichCommonData(message, oldReuqest);
		} catch (Exception e) {
			LOGGER.error(ERROR_LOG, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
}