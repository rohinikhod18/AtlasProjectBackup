package com.currenciesdirect.gtg.compliance.compliancesrv.core.rules;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse.DECISION;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.PropertyHandler;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class RegistrationRulesService.
 *
 * @author bnt
 */
public class RegistrationRulesService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationRulesService.class);

	/** The Constant ACCOUNT. */
	private static final String ACCOUNT = "oldAccount";

	/*
	 * create rule object from message fire the rule update the results in event
	 * and eventServiceLog summary & status in future drools rule engine will be
	 * implemented Purpose of method - Business: Get MessageContext from
	 * transformer and validate each service check status and accordingly set
	 * response reasoneCode and reasonDescription Implementation : 1.Get
	 * messageContext from transformer and separate messageContext object
	 * according to each service 2.First Priority goes to Internal_Rule_Service
	 * to check information is blacklisted or not. 3.If customer information is
	 * blacklisted then no further check is performed. 4.If customer information
	 * is not blacklisted then Fraugster,Sanction,KYC checks are performed. 5.If
	 * any check is fail except Fraugster then account status is set to Inactive
	 * 6.If any contact status of that account is active then account status is
	 * active if and only if contact in that account is not blacklisted
	 */
	/**
	 * Process.
	 *
	 * @param signUpMsg
	 *            the sign up msg
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> signUpMsg) {
		RegistrationResponse signUpResponse = new RegistrationResponse();
		Boolean kycHookStatus = Boolean.parseBoolean(System.getProperty("kycHookApplied"));//AT-5472
		try {
			RegistrationServiceRequest signUpRequest = (RegistrationServiceRequest) signUpMsg.getPayload()
					.getGatewayMessageExchange().getRequest();
			signUpResponse = (RegistrationResponse) signUpMsg.getPayload().getGatewayMessageExchange().getResponse();
			if (signUpResponse == null) {
				signUpResponse = new RegistrationResponse();
				signUpMsg.getPayload().getGatewayMessageExchange().setResponse(signUpResponse);
			}
			signUpResponse.setOsrID(signUpRequest.getOsrId());
			signUpResponse.setOrgCode(signUpRequest.getOrgCode());
			if (Constants.CFX.equalsIgnoreCase(signUpRequest.getAccount().getCustType())) {
				performCfxRules(signUpMsg);
			} else {
				performPFXRules(signUpMsg, signUpResponse, signUpRequest);
				//AT-5472
				if (Boolean.TRUE.equals(kycHookStatus)) {
					setContactstatus(signUpResponse, signUpRequest);
				}
			}
			updateRegistrationDates(signUpResponse.getAccount());
		} catch (Exception e) {
			if (signUpResponse == null) {
				signUpResponse = new RegistrationResponse();
			}
			signUpResponse.setOsrID(signUpMsg.getHeaders().get(MessageContextHeaders.OSR_ID).toString());
			signUpResponse.setErrorCode(ComplianceReasonCode.SYSTEM_FAILURE.getReasonCode());
			signUpResponse.setErrorDescription(ComplianceReasonCode.SYSTEM_FAILURE.getReasonDescription());
			signUpResponse.setDecision(DECISION.FAIL);
			LOG.error("Error in Registration Rule Service", e);
		}
		return signUpMsg;
	}

	/**
	 * Perform PFX rules.
	 *
	 * @param msg
	 *            the msg
	 * @param response
	 *            the response
	 * @param request
	 *            the request
	 */
	private void performPFXRules(Message<MessageContext> msg, RegistrationResponse response,
			RegistrationServiceRequest request) {
		if (OperationEnum.ADD_CONTACT==msg.getPayload().getGatewayMessageExchange().getOperation()) {
			performPFXAddContactRules(msg, response, request);
		} else {
			performSignUpPFXRules(msg, response, request);
		}
		setPFXContactSTPFlagStatus(response);
	}

	// set contact STPFlag status
	/**
	 * Sets the PFX contact STP flag status.
	 *
	 * @param response
	 *            the new PFX contact STP flag status
	 */
	private void setPFXContactSTPFlagStatus(RegistrationResponse response) {
		ComplianceAccount acc = response.getAccount();
		List<ComplianceContact> signUpContact = acc.getContacts();
		setContactStpFlag(signUpContact);
		setPFXAccountSTPFlagStatus(response);
	}

	// This will set Account STPFlag. if all contacts are active then Account
	// STPFlag is set true
	/**
	 * Sets the PFX account STP flag status.
	 *
	 * @param response
	 *            the new PFX account STP flag status
	 */
	// AT-1679 - sneha zagade
	private void setPFXAccountSTPFlagStatus(RegistrationResponse response) {
		ComplianceAccount acc = response.getAccount();
		Boolean accountSTPStatus= Boolean.FALSE;
		if (acc.getAcs().name().equals(ContactComplianceStatus.ACTIVE.name())) {
			accountSTPStatus = Boolean.TRUE;
		}
		acc.setSTPFlag(accountSTPStatus);
	}

	/**
	 * Perform sign up PFX rules.
	 *
	 * @param msg
	 *            the msg
	 * @param response
	 *            the response
	 * @param request
	 *            the request
	 */
	private void performSignUpPFXRules(Message<MessageContext> msg, RegistrationResponse response,
			RegistrationServiceRequest request) {
		ComplianceAccount acc = new ComplianceAccount();
		performCommonPFXRules(msg, response, request, acc);
		acc.setPaymentinWatchlistStatus((String) request.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS));
		acc.setPaymentoutWatchlistStatus(
				(String) request.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS));
		setPFXSignUpServiceStatuses(acc, msg);
		response.setAccount(acc);
	}

	/**
	 * Perform PFX add contact rules.
	 *
	 * @param msg
	 *            the msg
	 * @param response
	 *            the response
	 * @param request
	 *            the request
	 */
	private void performPFXAddContactRules(Message<MessageContext> msg, RegistrationResponse response,
			RegistrationServiceRequest request) {
		ComplianceAccount acc = new ComplianceAccount();

		Boolean blackListedcontact = performCommonPFXRules(msg, response, request, acc);
		checkIsOtherContactBlacklistedForAddCOntact(request, acc, blackListedcontact);
		setPFXAddContactServiceStatuses(acc, msg);
		response.setAccount(acc);
		// Condition For AddContact
		updateAccoutStatusForAddContact(request, response, blackListedcontact);

		// set STPFlag for Addcontact
		ComplianceAccount account = response.getAccount();
		List<ComplianceContact> signUpContact = account.getContacts();
		setContactStpFlag(signUpContact);
	}

	/**
	 * Check is other contact blacklisted for add C ontact.
	 *
	 * @param request
	 *            the request
	 * @param acc
	 *            the acc
	 * @param blackListedcontact
	 *            the black listedcontact
	 * @return the boolean
	 */
	private Boolean checkIsOtherContactBlacklistedForAddCOntact(RegistrationServiceRequest request,
			ComplianceAccount acc, Boolean blackListedcontact) {
		String blackListedContacts = request.getAdditionalAttribute("listOfBlackListedContact").toString();
		if (Boolean.FALSE.equals(blackListedcontact)) {
			getAcountStatusForBlacklist(acc, blackListedContacts);
		}
		return blackListedcontact;
	}

	/**
	 * Perform common PFX rules.
	 *
	 * @param msg
	 *            the msg
	 * @param response
	 *            the response
	 * @param request
	 *            the request
	 * @param acc
	 *            the acc
	 * @return true, if successful
	 */
	private boolean performCommonPFXRules(Message<MessageContext> msg, RegistrationResponse response,
			RegistrationServiceRequest request, ComplianceAccount acc) {
		boolean blackListedcontact;
		Boolean isContactModifiedFlag = Boolean.FALSE;

		Map<Integer, ComplianceContact> responseContacts = createDefaultResponse(request);
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		MessageExchange fraugsterExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		MessageExchange sanctionExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange kycExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);

		FraugsterSignupResponse fResponse = null;
		if (fraugsterExchange != null)
			fResponse = (FraugsterSignupResponse) fraugsterExchange.getResponse();

		SanctionResponse sanctionResponse = null;
		if (sanctionExchange != null)
			sanctionResponse = (SanctionResponse) sanctionExchange.getResponse();

		KYCProviderResponse kycProviderResponse = null;
		if (kycExchange != null)
			kycProviderResponse = (KYCProviderResponse) kycExchange.getResponse();

		processServiceResponses(responseContacts, internalServiceResponse, fResponse, sanctionResponse,
				kycProviderResponse,request.getAccount().getCustLegalEntity());

		acc.setVersion(request.getAccount().getVersion());
		acc.setId(request.getAccount().getId());
		acc.setAccountSFID(request.getAccount().getAccSFID());
		acc.setTradeAccountID(request.getAccount().getTradeAccountID());
		acc.setAcs(ContactComplianceStatus.INACTIVE);
		blackListedcontact = processContactsStatus(acc, responseContacts);
		Boolean isContactModified = (Boolean) request.getAdditionalAttribute("isContactModified");
		isContactModified = isContactModified == null ? isContactModifiedFlag : isContactModified;
		if (Boolean.TRUE.equals(isContactModified) && ContactComplianceStatus.INACTIVE==acc.getAcs()) {
			acc.setArc(acc.getContacts().get(0).getCrc());
		}
		if (blackListedcontact) {
			acc.setAcs(ContactComplianceStatus.INACTIVE);
			acc.setArc(ComplianceReasonCode.BLACKLISTED);
		}
		response.setAccount(acc);
		return blackListedcontact;
	}

	// Make sure that response code and description are not NULL for any
	// contact.
	// When only fraugster fails or contact is on any watchlist && contact is
	// active,
	/**
	 * Process contacts status.
	 *
	 * @param acc
	 *            the acc
	 * @param responseContacts
	 *            the response contacts
	 * @return true, if successful
	 */
	// but shuld not change response code and description
	private boolean processContactsStatus(ComplianceAccount acc, Map<Integer, ComplianceContact> responseContacts) {
		boolean blackListedcontact = false;
		for (ComplianceContact cResponse : responseContacts.values()) {
			if (null == cResponse.getResponseCode() && ContactComplianceStatus.ACTIVE == cResponse.getCcs()) {
				cResponse.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
				cResponse.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());
			}

			if (ContactComplianceStatus.ACTIVE == cResponse.getCcs()) {
				acc.setAcs(ContactComplianceStatus.ACTIVE);
				acc.setArc(ComplianceReasonCode.PASS);
			}
			/**
			 * AT-336 : Added BLACKLISTCOUNTRY check to change Account Status to
			 * Inactive when blacklisted country. : Abhijit G
			 */
			if (ComplianceReasonCode.GLOBACLCHECK.getReasonCode().equals(cResponse.getResponseCode())
					|| ComplianceReasonCode.BLACKLISTED.getReasonCode().equals(cResponse.getResponseCode())
					|| ComplianceReasonCode.BLACKLISTCOUNTRY.getReasonCode().equals(cResponse.getResponseCode()))
				blackListedcontact = true;
			acc.addContact(cResponse);
		}
		return blackListedcontact;
	}

	/**
	 * Process service responses.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @param fResponse
	 *            the f response
	 * @param sanctionResponse
	 *            the sanction response
	 * @param kycProviderResponse
	 *            the kyc provider response
	 */
	private void processServiceResponses(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse, FraugsterSignupResponse fResponse,
			SanctionResponse sanctionResponse, KYCProviderResponse kycProviderResponse, String legalEntity) {
		if(LegalEntityEnum.isEULegalEntity(legalEntity) 
				&& Boolean.TRUE.equals(getPFXBlackListStatus(responseContacts, internalServiceResponse))) {
			createResponseForEULegalEntityBlacklistedCustomers(responseContacts);
		}//AT-3398
		else if (Boolean.FALSE.equals(getPFXBlackListStatus(responseContacts, internalServiceResponse))
				&& (Boolean.FALSE.equals(getPFXGlobalCheckStatus(responseContacts, internalServiceResponse)))
				&& (Boolean.FALSE.equals(getPFXHighRiskCountryStatus(responseContacts, internalServiceResponse)))) {
			// When all above three checks are passed this block gets executed
			// if Contact is added to Fraugster watchlist
			// update contact reason codes accordingly
			if (fResponse != null)
				updatePFXFraugsterSignUpStatus(responseContacts, fResponse);
			if (sanctionResponse != null)
				populatePFXSanctionStatus(responseContacts, sanctionResponse);
			if (kycProviderResponse != null)
				getKYCStatus(responseContacts, kycProviderResponse);
		}
	}

	/**
	 * Gets the acount status for blacklist.
	 *
	 * @param acc
	 *            the acc
	 * @param blackListedContacts
	 *            the black listed contacts
	 * @return the acount status for blacklist
	 */
	private boolean getAcountStatusForBlacklist(ComplianceAccount acc, String blackListedContacts) {
		boolean blackListedcontact;
		if (blackListedContacts.length() > 0) {
			acc.setAcs(ContactComplianceStatus.INACTIVE);
			acc.setArc(ComplianceReasonCode.BLACKLISTED);
			blackListedcontact = true;
		} else {
			blackListedcontact = false;
		}
		return blackListedcontact;
	}

	/**
	 * Perform cfx rules.
	 *
	 * @param msg
	 *            the msg
	 */
	@SuppressWarnings("unchecked")
	private void performCfxRules(Message<MessageContext> msg) {
		RegistrationServiceRequest request = (RegistrationServiceRequest) msg.getPayload().getGatewayMessageExchange()
				.getRequest();
		RegistrationResponse response = (RegistrationResponse) msg.getPayload().getGatewayMessageExchange()
				.getResponse();
		if (response == null) {
			response = new RegistrationResponse();
			msg.getPayload().getGatewayMessageExchange().setResponse(response);
		}
		ComplianceAccount acc = new ComplianceAccount();
		performCommonCFXRules(msg, response, request, acc);
		if (OperationEnum.ADD_CONTACT==msg.getPayload().getGatewayMessageExchange().getOperation()) {
			setCFXAddContactServiceStatuses(acc, msg);

			// set STP Flag for add contact
			ComplianceAccount account = response.getAccount();
			List<ComplianceContact> signUpContact = account.getContacts();
			setContactStpFlag(signUpContact);
		}
		else {
			setCFXSignUpServiceStatuses(acc, msg);
			setCFXSTPFlagStatus(response);
		}
		/**
		 * Explicitly setting all contact statuses to INACTIVE if Account gets
		 * INACTIVE on add contact (AT-1576) -Vishal J
		 */
		List<Contact> oldContacts = (List<Contact>) request.getAdditionalAttribute("oldContacts");
		for (Contact contact : oldContacts) {
			ComplianceContact complianceContact = new ComplianceContact();
			complianceContact.setId(contact.getId());
			complianceContact.setContactSFID(contact.getContactSFID());
			complianceContact.setTradeContactID(contact.getTradeContactID());
			complianceContact.setCcs(response.getAccount().getAcs());
			complianceContact.setKycStatus(contact.getPreviousKycStatus());
			complianceContact.setFraugsterStatus(contact.getPreviousFraugsterStatus());
			complianceContact.setSanctionStatus(contact.getPreviousSanctionStatus());
			complianceContact.setBlacklistStatus(contact.getPreviousBlacklistStatus());
			complianceContact.setCustomCheckStatus(contact.getPreviousCountryGlobalCheckStatus());
			boolean stpFlag = complianceContact.getCcs().name().equals(ContactComplianceStatus.ACTIVE.name());
			complianceContact.setSTPFlag(stpFlag);
			complianceContact.setVersion(contact.getVersion());
			acc.getContacts().add(complianceContact);
		}
		response.setAccount(acc);
	}

	// set STPFlag for CFX customer
	/**
	 * Sets the CFXSTP flag status.
	 *
	 * @param response
	 *            the new CFXSTP flag status
	 */
	private void setCFXSTPFlagStatus(RegistrationResponse response) {
		ComplianceAccount acc = response.getAccount();
		List<ComplianceContact> signUpContact = acc.getContacts();
		setContactStpFlag(signUpContact);

		// set STPFlag for account
		boolean stpFlag = acc.getAcs().name().equals(ContactComplianceStatus.ACTIVE.name());
		acc.setSTPFlag(stpFlag);
	}

	/**
	 * Perform common CFX rules.
	 *
	 * @param msg
	 *            the msg
	 * @param response
	 *            the response
	 * @param request
	 *            the request
	 * @param acc
	 *            the acc
	 * @return true, if successful
	 */
	@SuppressWarnings("squid:S2589") // until getCFXGlobalCheckStatus and getCFXHighRiskCountryStatus implemented
	private boolean performCommonCFXRules(Message<MessageContext> msg, RegistrationResponse response,
			RegistrationServiceRequest request, ComplianceAccount acc) {
		boolean blackListedcontact = false;

		response.setOrgCode(request.getOrgCode());
		createDefaultCFXResponse(request, acc);
		InternalServiceResponse internalServiceResponseCfx = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		Map<Integer, ComplianceContact> responseContactCfx = new HashMap<>();
		for (ComplianceContact contact : acc.getContacts()) {
			responseContactCfx.put(contact.getId(), contact);
		}
		if (Boolean.FALSE.equals(getCFXBlackListStatus(responseContactCfx, internalServiceResponseCfx))
				&& (Boolean.FALSE.equals(getCFXGlobalCheckStatus(responseContactCfx, internalServiceResponseCfx)))
				&& (Boolean.FALSE.equals(getCFXHighRiskCountryStatus(responseContactCfx, internalServiceResponseCfx)))) {
			acc.setAcs(ContactComplianceStatus.INACTIVE);
			acc.setArc(ComplianceReasonCode.COMPLIANCE_PENDING);
			for (ComplianceContact contact : acc.getContacts()) {
				contact.setCcs(ContactComplianceStatus.INACTIVE);
				contact.setCrc(ComplianceReasonCode.COMPLIANCE_PENDING);
			}
			/**
			 * NEED TO IMPLEMENT WHEN IT WILL HAVE ONLINE REGISTRATION. NO NEED
			 * TO CALL FOLLOWING METHODS AS CURRENTLY ITS MANUAL REGITRATION AND
			 * ACCOUNT STATUS IS ALWAYS INACTIVE AND NOT DEPEND on SANCTION OR
			 * KYC OR FRAUGSTER getKYCStatus(responseContactCfx,
			 * kycProviderResponse);
			 * updateCFXFraugsterSignUpStatus(responseContactCfx, fResponse);
			 * populateCFXSanctionStatus(responseContactCfx, fResponse);
			 */
		} else {
			for (ComplianceContact contact : acc.getContacts()) {
				if (contact.getCrc() == ComplianceReasonCode.BLACKLISTED
						|| contact.getCrc() == ComplianceReasonCode.BLACKLISTED_COMPANY) {
					acc.setArc(contact.getCrc());
					blackListedcontact = true;
					break;
				}
			}
			acc.setAcs(ContactComplianceStatus.INACTIVE);
		}
		response.setAccount(acc);
		return blackListedcontact;
	}

	/**
	 * Check getCFXBlackListStatus for each contact and set reasonCode
	 * accordingly.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the CFX black list status
	 */
	private Boolean getCFXBlackListStatus(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse blacklistContactResponse : internalServiceResponse.getContacts()) {
			if ((EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(blacklistContactResponse.getEntityType())) {
				boolean val = isAccountBlacklisted(responseContacts, blacklistContactResponse);
				if (val) {
					return val;
				}
			} else {
				isAnyContactBlacklisted = setContactBlackListReasonCode(responseContacts, isAnyContactBlacklisted,
						blacklistContactResponse);
			}
		}
		// if any contact is blacklisted,
		// mark other contacts as 914
		return fillBlacklistResponse(responseContacts, isAnyContactBlacklisted);
	}

	/**
	 * Checks if is account blacklisted.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param blacklistContactResponse
	 *            the blacklist contact response
	 * @return true, if is account blacklisted
	 */
	private boolean isAccountBlacklisted(Map<Integer, ComplianceContact> responseContacts,
			ContactResponse blacklistContactResponse) {
		if (!blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.PASS.name())) {
			// when blacklistContactResponse status is pass then its not
			// blacklisted
			// So when its blacklisted this block will get executed
			for (ComplianceContact cResponse : responseContacts.values()) {
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				if (blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
					cResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
				} else {
					cResponse.setCrc(ComplianceReasonCode.BLACKLISTED_COMPANY);
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Gets the PFX black list status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the PFX black list status
	 */
	private Boolean getPFXBlackListStatus(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse blacklistContactResponse : internalServiceResponse.getContacts()) {
			isAnyContactBlacklisted = setContactBlackListReasonCode(responseContacts, isAnyContactBlacklisted,
					blacklistContactResponse);

		}
		// if any contact is blacklisted, mark other contacts as 914
		return fillBlacklistResponse(responseContacts, isAnyContactBlacklisted);
	}

	/**
	 * Sets the contact black list reason code.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param isAnyContactBlacklisted
	 *            the is any contact blacklisted
	 * @param blacklistContactResponse
	 *            the blacklist contact response
	 * @return true, if successful
	 */
	private boolean setContactBlackListReasonCode(Map<Integer, ComplianceContact> responseContacts,
			boolean isAnyContactBlacklisted, ContactResponse blacklistContactResponse) {
		boolean isBlack = isAnyContactBlacklisted;

		ComplianceContact cResponse = responseContacts.get(blacklistContactResponse.getId());
		// When one of mutiple contacts is blacklisted
		// Blacklist is not checked for rest
		// so contat blackList response is NULL
		if (null == blacklistContactResponse.getBlacklist().getStatus()) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setCrc(ComplianceReasonCode.BLACKLISTED_OTHER);
			return isBlack;
		}
		if (!ServiceStatus.PASS.name().equals(blacklistContactResponse.getBlacklist().getStatus())
				&& !blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())
				&& !blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.NOT_PERFORMED.name())
				&& !blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.NOT_REQUIRED.name())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			cResponse.setCrc(ComplianceReasonCode.BLACKLISTED);
			isBlack = true;
		} else if (blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			return true;
		} // changes made for AT-268
			// Added status as Inactive if 1st contact is blacklisted
			// changes made by-Saylee
		else if ((ServiceStatus.NOT_PERFORMED.name()
				.equalsIgnoreCase(blacklistContactResponse.getBlacklist().getStatus())
				|| ServiceStatus.NOT_REQUIRED.name()
						.equalsIgnoreCase(blacklistContactResponse.getBlacklist().getStatus()))
				&& isAnyContactBlacklisted) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setCrc(ComplianceReasonCode.BLACKLISTED_OTHER);
		} else {
			cResponse.setCcs(ContactComplianceStatus.ACTIVE);
		}
		return isBlack;
	}

	/**
	 * If any one contact is blacklisted then status of other contact is set to
	 * inactive and other contact status set to blacklisted other.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param isAnyContactBlacklisted
	 *            the is any contact blacklisted
	 * @return true, if successful
	 */
	private boolean fillBlacklistResponse(Map<Integer, ComplianceContact> responseContacts,
			boolean isAnyContactBlacklisted) {
		boolean areAllContactsInactive = false;
		if (isAnyContactBlacklisted) {
			areAllContactsInactive = true;
			for (ComplianceContact cResponse : responseContacts.values()) {
				if (!(ComplianceReasonCode.BLACKLISTED==cResponse.getCrc()
						|| ComplianceReasonCode.GLOBACLCHECK==cResponse.getCrc()
						|| ComplianceReasonCode.BLACKLISTCOUNTRY==cResponse.getCrc()
						|| ComplianceReasonCode.SYSTEM_FAILURE==cResponse.getCrc())) {
					cResponse.setCcs(ContactComplianceStatus.INACTIVE);
					cResponse.setCrc(ComplianceReasonCode.BLACKLISTED_OTHER);
				}
			}
		}
		return areAllContactsInactive;
	}

	/**
	 * getCFXGlobalCheckStatus() METHOD IS NOT IMPLEMENTED AS CURRENTLY CFX HAS
	 * MANUAL REGITRATION. NEED TO IMPLEMENT WHEN IT WILL HAVE ONLINE
	 * REGISTRATION.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the CFX global check status
	 */
	private Boolean getCFXGlobalCheckStatus(Map<Integer, ComplianceContact> responseContacts, // NOSONAR
			InternalServiceResponse internalServiceResponse) { // NOSONAR
		return Boolean.FALSE;
	}

	/**
	 * getCFXHighRiskCountryStatus() METHOD IS NOT IMPLEMENTED AS CURRENTLY CFX
	 * HAS MANUAL REGITRATION. NEED TO IMPLEMENT WHEN IT WILL HAVE ONLINE
	 * REGISTRATION.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the CFX high risk country status
	 */
	private Boolean getCFXHighRiskCountryStatus(Map<Integer, ComplianceContact> responseContacts, // NOSONAR
			InternalServiceResponse internalServiceResponse) { // NOSONAR
		return Boolean.FALSE;
	}

	/**
	 * If blacklist check is Passed and if country is USA then GlobalCheck is
	 * performed If country is USA then GlobalCheck status is Fail and set
	 * status as Inactive.
	 *
	 * @param signUpResponseContacts
	 *            the sign up response contacts
	 * @param signUpInternalServiceResponse
	 *            the sign up internal service response
	 * @return the PFX global check status
	 */
	private Boolean getPFXGlobalCheckStatus(Map<Integer, ComplianceContact> signUpResponseContacts,
			InternalServiceResponse signUpInternalServiceResponse) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse contactServiceResponse : signUpInternalServiceResponse.getContacts()) {
			ComplianceContact signUpCResponse = signUpResponseContacts.get(contactServiceResponse.getId());
			if (!(EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(contactServiceResponse.getEntityType())
					&& Boolean.FALSE.equals(signUpCResponse.getSkipRestChecks())) {
				if (ServiceStatus.FAIL.name().equals(contactServiceResponse.getGlobalCheck().getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setSkipRestChecks(Boolean.TRUE);
					signUpCResponse.setCrc(ComplianceReasonCode.GLOBACLCHECK);
					isAnyContactBlacklisted = true;
				} else if (ServiceStatus.WATCH_LIST.name()
						.equalsIgnoreCase(contactServiceResponse.getGlobalCheck().getStatus())) {
					signUpCResponse.setCrc(ComplianceReasonCode.USGLOBAL_WATCHLIST);
				} else if (ServiceStatus.SERVICE_FAILURE.name()
						.equalsIgnoreCase(contactServiceResponse.getGlobalCheck().getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setSkipRestChecks(Boolean.TRUE);
					signUpCResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
					isAnyContactBlacklisted = true;
				}
				/**
				 * Changes made for AT-31 Added status as INACTIVE if country is
				 * USA with list C state -Saylee
				 */
				else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(
						contactServiceResponse.getGlobalCheck().getStatus()) && isAnyContactBlacklisted) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setCrc(ComplianceReasonCode.BLACKLISTED_OTHER);
				} else if (ServiceStatus.NOT_REQUIRED.name()
						.equalsIgnoreCase(contactServiceResponse.getGlobalCheck().getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.ACTIVE);
				}

			}
		}
		return fillBlacklistResponse(signUpResponseContacts, isAnyContactBlacklisted);
	}

	/**
	 * If RiskLevel of Country is high then HighRiskCountryStatus is Fail and
	 * contact status set to Inactive.
	 *
	 * @param signUpResponseContacts
	 *            the sign up response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the PFX high risk country status
	 */
	private Boolean getPFXHighRiskCountryStatus(Map<Integer, ComplianceContact> signUpResponseContacts,
			InternalServiceResponse internalServiceResponse) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse contactServiceResponse : internalServiceResponse.getContacts()) {
			ComplianceContact signUpCResponse = signUpResponseContacts.get(contactServiceResponse.getId());
			if (!(EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(contactServiceResponse.getEntityType())
					&& Boolean.FALSE.equals(signUpCResponse.getSkipRestChecks())) {
				if (contactServiceResponse.getCountryCheck().getStatus().equals(ServiceStatus.FAIL.name())) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setSkipRestChecks(Boolean.TRUE);
					signUpCResponse.setCrc(ComplianceReasonCode.BLACKLISTCOUNTRY);
					isAnyContactBlacklisted = true;
				} else if (ServiceStatus.WATCH_LIST.name()
						.equalsIgnoreCase(contactServiceResponse.getCountryCheck().getStatus())) {
					signUpCResponse.setCrc(ComplianceReasonCode.HIGHRISK_WATCHLIST);
				} else if (ServiceStatus.SERVICE_FAILURE.name()
						.equalsIgnoreCase(contactServiceResponse.getCountryCheck().getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
					signUpCResponse.setSkipRestChecks(Boolean.TRUE);
					isAnyContactBlacklisted = true;
				} // changes made for AT-268
					// Added status Inactive if 1st contact is contain
					// Sanction country
					// changes made by-Saylee
				else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(
						contactServiceResponse.getCountryCheck().getStatus()) && isAnyContactBlacklisted) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setCrc(ComplianceReasonCode.BLACKLISTED_OTHER);
				} else if (!ServiceStatus.NOT_REQUIRED.name()
						.equalsIgnoreCase(contactServiceResponse.getCountryCheck().getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.ACTIVE);
				}

			}
		}
		return fillBlacklistResponse(signUpResponseContacts, isAnyContactBlacklisted);
	}

	/**
	 * If response from transformer is fail then reasonCode set to Sanctioned
	 * and contact status is Inactive.
	 *
	 * @param signUpResponseContacts
	 *            the sign up response contacts
	 * @param sanctionResponse
	 *            the sanction response
	 * @return the boolean
	 */
	private Boolean populatePFXSanctionStatus(Map<Integer, ComplianceContact> signUpResponseContacts,
			SanctionResponse sanctionResponse) {
		boolean areAllContactsInactive = true;
		if (sanctionResponse.getContactResponses() != null) {
			for (SanctionContactResponse sResponse : sanctionResponse.getContactResponses()) {
				ComplianceContact signUpCResponse = signUpResponseContacts.get(sResponse.getContactId());
				if (!ServiceStatus.PASS.name().equalsIgnoreCase(sResponse.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(sResponse.getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setCrc(ComplianceReasonCode.SANCTIONED);
				} else if (ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(sResponse.getStatus())) {
					signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
					signUpCResponse.setCrc(ComplianceReasonCode.SANCTIONED);
					areAllContactsInactive = true;
				} else {
					areAllContactsInactive = false;
				}
			}

		}
		return areAllContactsInactive;
	}

	/**
	 * If response from transformer is fail then reasonCode set to KYC and
	 * contact status is Inactive If Sanction Status is Fail and KYC status also
	 * fail then reasonCode set to KYC_AND_SANCTIONED If Country not supported
	 * in KYC then reasonCode set to KYC_NA i.e country_not_supported
	 *
	 * @param signUpResponseContacts
	 *            the sign up response contacts
	 * @param kycProviderResponse
	 *            the kyc provider response
	 * @return the KYC status
	 */
	private Boolean getKYCStatus(Map<Integer, ComplianceContact> signUpResponseContacts,
			KYCProviderResponse kycProviderResponse) {
		boolean areAllContactsInactive = true;
		if (ServiceStatus.FAIL.name().equalsIgnoreCase(kycProviderResponse.getStatus())) {
			for (ComplianceContact signUpCResponse : signUpResponseContacts.values()) {
				signUpCResponse.setCcs(ContactComplianceStatus.INACTIVE);
				signUpCResponse.setCrc(ComplianceReasonCode.KYC_NA);
			}
		} else {
			for (KYCContactResponse kycResponse : kycProviderResponse.getContactResponse()) {
				areAllContactsInactive = setComplianceReasonCodeForKYC(signUpResponseContacts, areAllContactsInactive,
						kycResponse);
			}
		}
		return areAllContactsInactive;
	}

	/**
	 * 1. If contact is sanctioned, always make contact Inactive 2. If BandText
	 * is "POA NEEDED" or "POI NEEDED", and contact not sanctioned, don't change
	 * contact status. And set response code accrodingly 3. In other cases mark
	 * contact KYC failed. if sanction is also failed, mark KYC and sanction
	 * failed
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param areAllContactsInactive
	 *            the are all contacts inactive
	 * @param kycResponse
	 *            the kyc response
	 * @return true, if successful
	 */
	private boolean setComplianceReasonCodeForKYC(Map<Integer, ComplianceContact> responseContacts,
			boolean areAllContactsInactive, KYCContactResponse kycResponse) {
		boolean contactsInactive = areAllContactsInactive;
		ComplianceContact cResponse = responseContacts.get(kycResponse.getId());
		boolean isContactSanctioned = cResponse.getCrc()==ComplianceReasonCode.SANCTIONED;
		if (!ServiceStatus.PASS.name().equalsIgnoreCase(kycResponse.getStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(kycResponse.getStatus())) {
			if (!isPOIOrPOANeeded(isContactSanctioned, kycResponse.getBandText(), cResponse)) {
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				if (isContactSanctioned) {
					cResponse.setCrc(ComplianceReasonCode.KYC_AND_SANCTIONED);
				} else if (Constants.COUNTRY_NOT_SUPPORTED.equals(kycResponse.getErrorCode())) {
					cResponse.setCrc(ComplianceReasonCode.KYC_NA);
				} else if (Constants.EU_LE_POI_NEEDED.equals(kycResponse.getBandText())) {//AT-3327
					cResponse.setCrc(ComplianceReasonCode.KYC_POI);
				} else {
					cResponse.setCrc(ComplianceReasonCode.KYC);
				}
			}
		} else if (ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(kycResponse.getStatus())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			if (isContactSanctioned) {
				cResponse.setCrc(ComplianceReasonCode.KYC_AND_SANCTIONED);
				contactsInactive = true;
			} else {
				cResponse.setCrc(ComplianceReasonCode.KYC);
				contactsInactive = true;
			}
		} else {
			contactsInactive = false;
		}
		return contactsInactive;
	}

	/**
	 * Checks if is POI or POA needed.
	 *
	 * @param isContactSanctioned
	 *            the is contact sanctioned
	 * @param bandText
	 *            the band text
	 * @param cResponse
	 *            the c response
	 * @return true, if is POI or POA needed
	 */
	private boolean isPOIOrPOANeeded(boolean isContactSanctioned, String bandText, ComplianceContact cResponse) {
		boolean result = false;
		if (!isContactSanctioned && Constants.POA_NEEDED.equalsIgnoreCase(bandText)) {
			cResponse.setCrc(ComplianceReasonCode.KYC_POA);
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			result = true;
		} else if (!isContactSanctioned && Constants.POI_NEEDED.equalsIgnoreCase(bandText)) {
			cResponse.setCrc(ComplianceReasonCode.KYC_POI);
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			result = true;
		}
		return result;
	}

	/**
	 * Update common fraugster sign up status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param fResponse
	 *            the f response
	 */
	private void updateCommonFraugsterSignUpStatus(Map<Integer, ComplianceContact> responseContacts,
			FraugsterSignupResponse fResponse) {

		for (FraugsterSignupContactResponse idResponse : fResponse.getContactResponses()) {
			ComplianceContact cResponse = responseContacts.get(idResponse.getId());
			if (ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(idResponse.getStatus())
					|| ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(idResponse.getStatus())) {
				cResponse.setResponseCode(ComplianceReasonCode.FRAUGSTER_WATCHLIST.getReasonCode());
				cResponse.setResponseDescription(ComplianceReasonCode.FRAUGSTER_WATCHLIST.getReasonDescription());
			}
		}
	}

	/**
	 * Update PFX fraugster sign up status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param fResponse
	 *            the f response
	 */
	private void updatePFXFraugsterSignUpStatus(Map<Integer, ComplianceContact> responseContacts,
			FraugsterSignupResponse fResponse) {

		updateCommonFraugsterSignUpStatus(responseContacts, fResponse);
	}

	/**
	 * Creates the default response.
	 *
	 * @param request
	 *            the request
	 * @return the map
	 */
	private Map<Integer, ComplianceContact> createDefaultResponse(RegistrationServiceRequest request) {
		Map<Integer, ComplianceContact> responseContacts = new HashMap<>(request.getAccount().getContacts().size());
		for (com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact requestContact : request.getAccount()
				.getContacts()) {
			ComplianceContact cResponse = new ComplianceContact();
			cResponse.setVersion(requestContact.getVersion());
			cResponse.setId(requestContact.getId());
			cResponse.setContactSFID(requestContact.getContactSFID());
			cResponse.setCcs(ContactComplianceStatus.ACTIVE);
			cResponse.setCrc(ComplianceReasonCode.PASS);
			cResponse.setTradeContactID(requestContact.getTradeContactID());
			responseContacts.put(requestContact.getId(), cResponse);
		}
		return responseContacts;
	}

	/**
	 * Creates the default CFX response.
	 *
	 * @param request
	 *            the request
	 * @param complianceAccount
	 *            the compliance account
	 * @return the compliance account
	 */
	private ComplianceAccount createDefaultCFXResponse(RegistrationServiceRequest request,
			ComplianceAccount complianceAccount) {
		List<ComplianceContact> responseContacts = new ArrayList<>();
		complianceAccount.setAcs(ContactComplianceStatus.INACTIVE);
		complianceAccount.setId(request.getAccount().getId());
		complianceAccount.setAccountSFID(request.getAccount().getAccSFID());
		complianceAccount.setArc(ComplianceReasonCode.COMPLIANCE_PENDING);
		complianceAccount.setTradeAccountID(request.getAccount().getTradeAccountID());
		complianceAccount.setVersion(request.getAccount().getVersion());

		for (com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact requestContact : request.getAccount()
				.getContacts()) {
			ComplianceContact cResponse = new ComplianceContact();
			cResponse.setVersion(requestContact.getVersion());
			cResponse.setId(requestContact.getId());
			cResponse.setContactSFID(requestContact.getContactSFID());
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setCrc(ComplianceReasonCode.COMPLIANCE_PENDING);
			cResponse.setTradeContactID(requestContact.getTradeContactID());
			responseContacts.add(cResponse);
		}
		complianceAccount.setContacts(responseContacts);
		return complianceAccount;
	}

	/**
	 * Update accout status for add contact.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @param isBlacklisted
	 *            the is blacklisted
	 */
	private void updateAccoutStatusForAddContact(RegistrationServiceRequest request, RegistrationResponse response,
			Boolean isBlacklisted) {

		Account oldAccount = (Account) request.getAdditionalAttribute(ACCOUNT);

		// AT-355.If oldaccount & status not null & no contact is blacklisted
		// then old account status checked
		// if true set new status as active and remove arc description
		// else if old status and new status is same then remove arc description
		// --Tejas I
		if (oldAccount != null && oldAccount.getAccountStatus() != null && Boolean.FALSE.equals(isBlacklisted)) {
			if (ContactComplianceStatus.ACTIVE.name().equals(oldAccount.getAccountStatus())) {
				response.getAccount().setAcs(ContactComplianceStatus.ACTIVE);
				response.getAccount().setArc(ComplianceReasonCode.ACCOUNT_STATUS_UNCHANGED);
			} else if (response.getAccount().getAcs().name().equals(oldAccount.getAccountStatus())) {
				response.getAccount().setArc(ComplianceReasonCode.ACCOUNT_STATUS_UNCHANGED);
			}
		}
	}

	/**
	 * This method is used for setting different micro service statuses
	 * performed on account and contact these statuses are updated in account
	 * and contact tables
	 * 
	 * If any service status is not_required in update and add contact then set
	 * previous status.
	 *
	 * @param signUpAccount
	 *            the sign up account
	 * @param msg
	 *            the msg
	 * @return the compliance account
	 */
	private ComplianceAccount setCFXSignUpServiceStatuses(ComplianceAccount signUpAccount,
			Message<MessageContext> msg) {
		RegistrationServiceRequest registrationServiceRequest = (RegistrationServiceRequest) msg.getPayload()
				.getGatewayMessageExchange().getRequest();
		Account regAccount = registrationServiceRequest.getAccount();

		MessageExchange blackListexchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		MessageExchange sanctionExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange fraugsterExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		MessageExchange kycExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
		// For CFX Sign up update account table for status
		EventServiceLog accountBlackListlog = blackListexchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				EntityEnum.ACCOUNT.name(), regAccount.getId());
		signUpAccount.setBlacklistStatus(accountBlackListlog.getStatus());
		signUpAccount.setPaymentinWatchlistStatus(
				(String) registrationServiceRequest.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS));
		signUpAccount.setPaymentoutWatchlistStatus(
				(String) registrationServiceRequest.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS));
		EventServiceLog accountSanctionLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.ACCOUNT.name(), regAccount.getId());
		signUpAccount.setSanctionStatus(accountSanctionLog.getStatus());
		// End For CFX Sign up update account table for status
		for (ComplianceContact signUpContact : signUpAccount.getContacts()) {
			updateContactStatus(blackListexchange, sanctionExchange, fraugsterExchange, kycExchange, signUpContact,
					signUpAccount.getBlacklistStatus());
		}

		return signUpAccount;
	}

	/**
	 * Sets the PFX sign up service statuses.
	 *
	 * @param signUpAccResponse
	 *            the sign up acc response
	 * @param signUpMsg
	 *            the sign up msg
	 * @return the compliance account
	 */
	private ComplianceAccount setPFXSignUpServiceStatuses(ComplianceAccount signUpAccResponse,
			Message<MessageContext> signUpMsg) {
		MessageExchange blackListexchange = signUpMsg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		MessageExchange sanctionExchange = signUpMsg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange fraugsterExchange = signUpMsg.getPayload()
				.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		MessageExchange kycExchange = signUpMsg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
		for (ComplianceContact contact : signUpAccResponse.getContacts()) {
			updateContactStatus(blackListexchange, sanctionExchange, fraugsterExchange, kycExchange, contact,
					signUpAccResponse.getBlacklistStatus());
		}
		return signUpAccResponse;
	}

	/**
	 * Sets the CFX add contact service statuses.
	 *
	 * @param account
	 *            the account
	 * @param msg
	 *            the msg
	 * @return the compliance account
	 */
	private ComplianceAccount setCFXAddContactServiceStatuses(ComplianceAccount account, Message<MessageContext> msg) {
		return setCommonAddContactServiceStatuses(account, msg);
	}

	/**
	 * Sets the PFX add contact service statuses.
	 *
	 * @param account
	 *            the account
	 * @param msg
	 *            the msg
	 * @return the compliance account
	 */
	private ComplianceAccount setPFXAddContactServiceStatuses(ComplianceAccount account, Message<MessageContext> msg) {
		return setCommonAddContactServiceStatuses(account, msg);
	}

	/**
	 * Sets the common add contact service statuses.
	 *
	 * @param addContactAccount
	 *            the add contact account
	 * @param addContactMsg
	 *            the add contact msg
	 * @return the compliance account
	 */
	private ComplianceAccount setCommonAddContactServiceStatuses(ComplianceAccount addContactAccount,
			Message<MessageContext> addContactMsg) {

		RegistrationServiceRequest registrationServiceRequest = (RegistrationServiceRequest) addContactMsg.getPayload()
				.getGatewayMessageExchange().getRequest();

		Account regAccount = registrationServiceRequest.getAccount();

		MessageExchange blackListexchange = addContactMsg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		MessageExchange sanctionExchange = addContactMsg.getPayload()
				.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange fraugsterExchange = addContactMsg.getPayload()
				.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		MessageExchange kycExchange = addContactMsg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);

		setPreviousStatus(addContactAccount, regAccount);
		setPreviousWatchlistStatus(addContactAccount, regAccount, registrationServiceRequest);
		for (ComplianceContact contactAdded : addContactAccount.getContacts()) {

			updateContactStatus(blackListexchange, sanctionExchange, fraugsterExchange, kycExchange, contactAdded,
					addContactAccount.getBlacklistStatus());
		}

		return addContactAccount;
	}

	/**
	 * Sets the previous status.
	 *
	 * @param account
	 *            the account
	 * @param regAccount
	 *            the reg account
	 */
	private void setPreviousStatus(ComplianceAccount account, Account regAccount) {

		if (ServiceStatus.NOT_REQUIRED.toString().equals(account.getBlacklistStatus())) {
			account.setBlacklistStatus(regAccount.getPreviousBlacklistStatus());
		}

		if (ServiceStatus.NOT_REQUIRED.toString().equals(account.getSanctionStatus())) {
			account.setSanctionStatus(regAccount.getPreviousSanctionStatus());
		}
		/**
		 * KYC and Fraugster status will be always not_required fro account.
		 * need to check whether these below to check can be skipped?
		 **/
		if (ServiceStatus.NOT_REQUIRED.toString().equals(account.getKycStatus())) {
			account.setKycStatus(regAccount.getPreviousKycStatus());
		}

		if (ServiceStatus.NOT_REQUIRED.toString().equals(account.getFraugsterStatus())) {
			account.setFraugsterStatus(regAccount.getPreviousFraugsterStatus());
		}
	}

	/**
	 * Sets the previous watchlist status.
	 *
	 * @param account
	 *            the account
	 * @param regAccount
	 *            the reg account
	 * @param registrationServiceRequest
	 *            the registration service request
	 */
	private void setPreviousWatchlistStatus(ComplianceAccount account, Account regAccount,
			RegistrationServiceRequest registrationServiceRequest) {
		if (!StringUtils.isNullOrEmpty(regAccount.getPreviousPaymentinWatchlistStatus())) {
			if (ServiceStatus.FAIL.toString().equals(regAccount.getPreviousPaymentinWatchlistStatus())) {
				account.setPaymentinWatchlistStatus(ServiceStatus.FAIL.name());
			} else {
				account.setPaymentinWatchlistStatus((String) registrationServiceRequest
						.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS));
			}
		} else {
			account.setPaymentinWatchlistStatus(
					(String) registrationServiceRequest.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS));
		}
		if (!StringUtils.isNullOrEmpty(regAccount.getPreviousPaymentoutWatchlistStatus())) {
			if (ServiceStatus.FAIL.toString().equals(regAccount.getPreviousPaymentoutWatchlistStatus())) {
				account.setPaymentoutWatchlistStatus(ServiceStatus.FAIL.name());
			} else {
				account.setPaymentoutWatchlistStatus((String) registrationServiceRequest
						.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS));
			}
		} else {
			account.setPaymentoutWatchlistStatus(
					(String) registrationServiceRequest.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS));
		}
	}

	/**
	 * Update contact status.
	 *
	 * @param blackListexchange
	 *            the black listexchange
	 * @param sanctionExchange
	 *            the sanction exchange
	 * @param fraugsterExchange
	 *            the fraugster exchange
	 * @param kycExchange
	 *            the kyc exchange
	 * @param contact
	 *            the contact
	 * @param accountBlackListStatus
	 *            the account black list status
	 */
	private void updateContactStatus(MessageExchange blackListexchange, MessageExchange sanctionExchange,
			MessageExchange fraugsterExchange, MessageExchange kycExchange, ComplianceContact contact,
			String accountBlackListStatus) {
		EventServiceLog blacklistLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog countryCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog globalCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog sanctionLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog fraugsterLog = fraugsterExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog kycLog = kycExchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE, EntityEnum.CONTACT.name(),
				contact.getId());

		contact.setCustomCheckStatus(getOverallCustomCheckStatus(globalCheckLog, countryCheckLog));

		// this is signup process and there would be no previuos status hence
		// removing below if conditions
		if (null == blacklistLog.getStatus()) {
			contact.setBlacklistStatus(accountBlackListStatus);
		} else {
			contact.setBlacklistStatus(blacklistLog.getStatus());
		}
		contact.setSanctionStatus(sanctionLog.getStatus());
		contact.setFraugsterStatus(fraugsterLog.getStatus());
		contact.setKycStatus(kycLog.getStatus());

	}

	/**
	 * Gets the overall custom check status.
	 *
	 * @param firstGlobalCheckLog
	 *            the first global check log
	 * @param firstCountryCheckLog
	 *            the first country check log
	 * @return the overall custom check status
	 */
	private String getOverallCustomCheckStatus(EventServiceLog firstGlobalCheckLog,
			EventServiceLog firstCountryCheckLog) {
		if (isCustomCheckStatusServiceFailure(firstGlobalCheckLog, firstCountryCheckLog)) {
			return Constants.SERVICE_FAILURE;
		} else if (isCustomCheckStatusFailed(firstGlobalCheckLog, firstCountryCheckLog)) {
			return Constants.FAIL;
		} else if (isCustomCheckNotRequired(firstGlobalCheckLog, firstCountryCheckLog)) {
			return Constants.NOT_REQUIRED;
		} else if (isCustomCheckNotPerformed(firstGlobalCheckLog, firstCountryCheckLog)) {
			return Constants.NOT_PERFORMED;
		} else {
			return Constants.PASS;
		}
	}

	/**
	 * Checks if is custom check not performed.
	 *
	 * @param firstGlobalCheckLog
	 *            the first global check log
	 * @param firstCountryCheckLog
	 *            the first country check log
	 * @return true, if is custom check not performed
	 */
	private boolean isCustomCheckNotPerformed(EventServiceLog firstGlobalCheckLog,
			EventServiceLog firstCountryCheckLog) {
		return Constants.NOT_PERFORMED.equals(firstCountryCheckLog.getStatus())
				|| Constants.NOT_PERFORMED.equals(firstGlobalCheckLog.getStatus());
	}

	/**
	 * Checks if is custom check not required.
	 *
	 * @param firstGlobalCheckLog
	 *            the first global check log
	 * @param firstCountryCheckLog
	 *            the first country check log
	 * @return true, if is custom check not required
	 */
	private boolean isCustomCheckNotRequired(EventServiceLog firstGlobalCheckLog,
			EventServiceLog firstCountryCheckLog) {
		return Constants.NOT_REQUIRED.equals(firstCountryCheckLog.getStatus())
				&& Constants.NOT_REQUIRED.equals(firstGlobalCheckLog.getStatus());
	}

	/**
	 * Checks if is custom check status service failure.
	 *
	 * @param firstGlobalCheckLog
	 *            the first global check log
	 * @param firstCountryCheckLog
	 *            the first country check log
	 * @return true, if is custom check status service failure
	 */
	private boolean isCustomCheckStatusServiceFailure(EventServiceLog firstGlobalCheckLog,
			EventServiceLog firstCountryCheckLog) {
		return Constants.SERVICE_FAILURE.equals(firstCountryCheckLog.getStatus())
				|| Constants.SERVICE_FAILURE.equals(firstGlobalCheckLog.getStatus());
	}

	/**
	 * Checks if is custom check status failed.
	 *
	 * @param firstGlobalCheckLog
	 *            the first global check log
	 * @param firstCountryCheckLog
	 *            the first country check log
	 * @return true, if is custom check status failed
	 */
	private boolean isCustomCheckStatusFailed(EventServiceLog firstGlobalCheckLog,
			EventServiceLog firstCountryCheckLog) {
		return Constants.FAIL.equals(firstCountryCheckLog.getStatus())
				|| Constants.FAIL.equals(firstGlobalCheckLog.getStatus());
	}

	/**
	 * Update registration dates.
	 *
	 * @param account
	 *            the account
	 */
	private void updateRegistrationDates(ComplianceAccount account) {
		Timestamp time = new Timestamp(System.currentTimeMillis());
		account.setRegistrationInDate(time);
		if (ContactComplianceStatus.ACTIVE==account.getAcs()) {
			account.setRegisteredDate(time);
			Calendar c = Calendar.getInstance();
			c.setTime(time);
			c.add(Calendar.YEAR, PropertyHandler.getComplianceExpiryYears());
			account.setExpiryDate(new Timestamp(c.getTimeInMillis()));
		}
	}
	
	/**
	 * Sets the contact stp flag.
	 *
	 * @param signUpContact the new contact stp flag
	 */
	private void setContactStpFlag(List<ComplianceContact> signUpContact) {
		for (ComplianceContact contact : signUpContact) {
			boolean flag = contact.getCcs().name().equals(ContactComplianceStatus.ACTIVE.name());
			contact.setSTPFlag(flag);
		}
	}
	
	/**
	 * AT-3398
	 * Creates the response for EU legal entity customers.
	 *
	 * @param responseContacts the response contacts
	 * @param kycProviderResponse the kyc provider response
	 */
	private void createResponseForEULegalEntityBlacklistedCustomers(Map<Integer, ComplianceContact> responseContacts) {
		
		for(ComplianceContact complianceContact : responseContacts.values()) {
			if(complianceContact.getCrc()==ComplianceReasonCode.BLACKLISTED)
				complianceContact.setCrc(ComplianceReasonCode.KYC_POI);
		}
	}
	
	/**
	 * Sets the contactstatus.
	 *
	 * @param signUpResponse the sign up response
	 * @param signUpRequest  the sign up request
	 */
	private void setContactstatus(RegistrationResponse signUpResponse, RegistrationServiceRequest signUpRequest) {
		
		//AT-5550
		ComplianceAccount aResponse = signUpResponse.getAccount();
	
		for (Contact contact : signUpRequest.getAccount().getContacts()) {
			if (contact.getOccupation() != null && aResponse!=null) {
				for (ComplianceContact cResponse : aResponse.getContacts()) {
					if (contact.getOccupation().equalsIgnoreCase("pfxreg000")) {
						cResponse.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());	
						aResponse.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());						
					}
					if (contact.getOccupation().equalsIgnoreCase("pfxreg902")) {
						cResponse.setResponseCode(ComplianceReasonCode.BLACKLISTED.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.BLACKLISTED.getReasonDescription());
						aResponse.setResponseCode(ComplianceReasonCode.BLACKLISTED.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.BLACKLISTED.getReasonDescription());
					}
					if (contact.getOccupation().equalsIgnoreCase("pfxreg908")) {
						cResponse.setResponseCode(ComplianceReasonCode.KYC.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.KYC.getReasonDescription());
						aResponse.setResponseCode(ComplianceReasonCode.KYC.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.KYC.getReasonDescription());
					}
					if (contact.getOccupation().equalsIgnoreCase("pfxreg909")) {
						cResponse.setResponseCode(ComplianceReasonCode.KYC_NA.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.KYC_NA.getReasonDescription());
						aResponse.setResponseCode(ComplianceReasonCode.KYC_NA.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.KYC_NA.getReasonDescription());
					}
					if (contact.getOccupation().equalsIgnoreCase("pfxreg910")) {
						cResponse.setResponseCode(ComplianceReasonCode.KYC_AND_SANCTIONED.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.KYC_AND_SANCTIONED.getReasonDescription());
						aResponse.setResponseCode(ComplianceReasonCode.KYC_AND_SANCTIONED.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.KYC_AND_SANCTIONED.getReasonDescription());
					}
					if (contact.getOccupation().equalsIgnoreCase("pfxreg918")) {
						cResponse.setResponseCode(ComplianceReasonCode.KYC_POA.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.KYC_POA.getReasonDescription());
						aResponse.setResponseCode(ComplianceReasonCode.KYC_POA.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.KYC_POA.getReasonDescription());
					}
					if (contact.getOccupation().equalsIgnoreCase("pfxreg919")) {
						cResponse.setResponseCode(ComplianceReasonCode.KYC_POI.getReasonCode());
						cResponse.setResponseDescription(ComplianceReasonCode.KYC_POI.getReasonDescription());						
						aResponse.setResponseCode(ComplianceReasonCode.KYC_POI.getReasonCode());
						aResponse.setResponseDescription(ComplianceReasonCode.KYC_POI.getReasonDescription());
					}
				}	
			}
		}
	}
}
