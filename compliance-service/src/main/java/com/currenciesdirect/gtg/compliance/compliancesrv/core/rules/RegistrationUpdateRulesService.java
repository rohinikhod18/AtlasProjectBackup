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
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.BulkRegRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.PropertyHandler;

/**
 * The Class RegistrationUpdateRulesService.
 *
 * @author bnt
 */
public class RegistrationUpdateRulesService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RegistrationUpdateRulesService.class);

	/** The Constant ACCOUNT. */
	private static final String ACCOUNT = "oldAccount";
	

	/*
	 * create rule object from mesg fire the rule update the results in event
	 * and eventServiceLog summary & status
	 * 
	 * in future drools rule engine will be implemented
	 */
	/**
	 * Process.
	 *
	 * @param msg
	 *            the msg
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> msg) {
		RegistrationResponse response = new RegistrationResponse();
		Boolean kycHookStatus = Boolean.parseBoolean(System.getProperty("kycHookApplied"));//AT-5472
		try {
			MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
			OperationEnum operation = exchange.getOperation();
			RegistrationServiceRequest request = (RegistrationServiceRequest) msg.getPayload()
					.getGatewayMessageExchange().getRequest();

			response = (RegistrationResponse) msg.getPayload().getGatewayMessageExchange().getResponse();
			if (response == null) {
				response = new RegistrationResponse();
				msg.getPayload().getGatewayMessageExchange().setResponse(response);
			}
			response.setOsrID(request.getOsrId());
			response.setOrgCode(request.getOrgCode());
			if (Constants.CFX.equalsIgnoreCase(request.getAccount().getCustType())) {
				performCfxRules(msg);
			} else {
				performPFXRules(msg, response, request);
				//AT-5472
				if (Boolean.TRUE.equals(kycHookStatus)) {
					setContactstatus(response, request);
				}
			}	
			updateRegistrationDates(request, response.getAccount());
			if (operation == OperationEnum.RECHECK_FAILURES)
				setResponseForBulkRecheck(msg, response);
		} catch (Exception e) {
			if (null == response)
				response = new RegistrationResponse();
			response.setErrorCode(ComplianceReasonCode.SYSTEM_FAILURE.getReasonCode());
			response.setErrorDescription(ComplianceReasonCode.SYSTEM_FAILURE.getReasonDescription());
			response.setDecision(DECISION.FAIL);
			LOG.error("error in rule service", e);
		}
		return msg;
	}

	/**
	 * Sets the response for bulk recheck.
	 *
	 * @param msg
	 *            the msg
	 * @param response
	 *            the response
	 */
	private void setResponseForBulkRecheck(Message<MessageContext> msg, RegistrationResponse response) {
		BulkRegRecheckResponse recheckResponse = new BulkRegRecheckResponse();

		Boolean recheckStatus = (Boolean) response.getAdditionalAttribute(Constants.RECHECK_STATUS);
		if (Boolean.TRUE.equals(recheckStatus)) {
			recheckResponse.setStatus("PASS");
			recheckResponse.setAccountID(response.getAccount().getId());
		} else {
			recheckResponse.setStatus("FAIL");
			recheckResponse.setAccountID(response.getAccount().getId());
		}
		MessageExchange recheckExchange = new MessageExchange();
		recheckExchange.setServiceTypeEnum(ServiceTypeEnum.REGISTRATION_BULK_RECHECK_SERVICE);
		recheckExchange.setResponse(recheckResponse);
		msg.getPayload().appendMessageExchange(recheckExchange);
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
		Map<Integer, ComplianceContact> responseContacts = createDefaultResponse(request);
		@SuppressWarnings("unchecked")
		List<Contact> oldContacts = (List<Contact>) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);

		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		try {
			
			processCheckResponse(msg, request, responseContacts, oldContacts, internalServiceResponse);

		} catch (Exception ex) {
			msg.getPayload().setFailed(true);
			LOG.error("error evaluating result", ex);
		}

		ComplianceAccount acc = new ComplianceAccount();
		acc.setVersion(request.getAccount().getVersion());
		acc.setId(request.getAccount().getId());
		acc.setAccountSFID(request.getAccount().getAccSFID());
		acc.setTradeAccountID(request.getAccount().getTradeAccountID());
		acc.setAcs(ContactComplianceStatus.valueOf(request.getAccount().getAccountStatus()));
		for (ComplianceContact complianceContact : responseContacts.values()) {
			if (complianceContact.getCcs()==ContactComplianceStatus.ACTIVE)
				acc.setAcs(ContactComplianceStatus.valueOf(ContactComplianceStatus.getComplianceAsString(1)));
			else
				acc.setAcs(ContactComplianceStatus.valueOf(request.getAccount().getAccountStatus()));
		}

		// Account would be Inactive of AnyContact is blacklisted
		// if all contacts are Inactive
		boolean blackListedcontact = checkIfAnyContactBlacklisted(request, responseContacts, acc);
		setServiceStatuses(acc, msg);
		response.setAccount(acc);
		if (!blackListedcontact) {
			compareoldnewAccountStatus(request, response);
		}
		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		OperationEnum operation = exchange.getOperation();
		if (operation == OperationEnum.RECHECK_FAILURES) {
			for (ComplianceContact con : acc.getContacts()) {
				Integer contactId = (Integer) request.getAdditionalAttribute(Constants.CONTACT_ID);
				if (null != contactId && contactId.equals(con.getId())) {
					getBulkRecheckServiceStatusForContact(con, response);
					processContactsStatusForPFX(acc, con, blackListedcontact);
				}
			}
		}

	}

	private void processCheckResponse(Message<MessageContext> msg, RegistrationServiceRequest request,
			Map<Integer, ComplianceContact> responseContacts, List<Contact> oldContacts,
			InternalServiceResponse internalServiceResponse) {
		String legalEntity = request.getAccount().getCustLegalEntity();
		if (Boolean.FALSE.equals(processPFXBlackListResponse(responseContacts, internalServiceResponse, request))
				&& Boolean.FALSE.equals(getGlobalCheckStatus(responseContacts, internalServiceResponse, request))
				&& Boolean.FALSE.equals(getHighRiskCountryStatus(responseContacts, internalServiceResponse, request))) {
			// if Contact is added to Fraugster watchlist, update contact
			// reason codes accordingly
			MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			processFraugsterResponse(responseContacts, exchange);

			exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
			processSanctionResponse(request, responseContacts, oldContacts, exchange);
			exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			processKYCResponse(request, responseContacts, oldContacts, exchange);
		}
		//Add for AT-3398
		else if(LegalEntityEnum.isEULegalEntity(legalEntity)
				&& (Boolean.TRUE.equals(processPFXBlackListResponse(responseContacts, internalServiceResponse, request)))) {
			MessageExchange exchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			processKYCResponse(request, responseContacts, oldContacts, exchange);
		}
	}

	/**
	 * Process contacts status for PFX.
	 *
	 * @param acc
	 *            the acc
	 * @param con
	 *            the con
	 * @param blackListedcontact
	 *            the black listedcontact
	 */
	private void processContactsStatusForPFX(ComplianceAccount acc, ComplianceContact con, boolean blackListedcontact) {
		if (null == con.getResponseCode() && !blackListedcontact) {
			con.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
			con.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());
			con.setCcs(ContactComplianceStatus.ACTIVE);
			if (ContactComplianceStatus.ACTIVE == con.getCcs()) {
				acc.setAcs(ContactComplianceStatus.ACTIVE);
				acc.setArc(ComplianceReasonCode.PASS);
			}
		} else if (null == con.getResponseCode()) {
			con.setResponseCode(ComplianceReasonCode.BLACKLISTED.getReasonCode());
			con.setResponseDescription(ComplianceReasonCode.BLACKLISTED.getReasonDescription());
			con.setCcs(ContactComplianceStatus.INACTIVE);
			acc.setAcs(ContactComplianceStatus.INACTIVE);
			acc.setArc(ComplianceReasonCode.BLACKLISTED);
		}
	}

	/**
	 * Gets the bulk recheck service status for contact.
	 *
	 * @param complianceContact
	 *            the compliance contact
	 * @param response
	 *            the response
	 * @return the bulk recheck service status for contact
	 */
	private void getBulkRecheckServiceStatusForContact(ComplianceContact complianceContact,
			RegistrationResponse response) {
		Boolean recheckStatus = Boolean.FALSE;
		if (!ServiceStatus.SERVICE_FAILURE.name().equals(complianceContact.getKycStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(complianceContact.getFraugsterStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(complianceContact.getBlacklistStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(complianceContact.getSanctionStatus())) {
			recheckStatus = Boolean.TRUE;
		}
		response.addAttribute(Constants.RECHECK_STATUS, recheckStatus);
	}

	/**
	 * Check if any contact blacklisted.
	 *
	 * @param request
	 *            the request
	 * @param responseContacts
	 *            the response contacts
	 * @param acc
	 *            the acc
	 * @return true, if successful
	 */
	private boolean checkIfAnyContactBlacklisted(RegistrationServiceRequest request,
			Map<Integer, ComplianceContact> responseContacts, ComplianceAccount acc) {
		String blackListedContacts = request.getAdditionalAttribute("listOfBlackListedContact").toString();
		boolean blackListedcontact;
		boolean areAllContactsInactive = true;

		if (checkIfOtherContactsAreBlacklisted(blackListedContacts, responseContacts)) {
			acc.setAcs(ContactComplianceStatus.INACTIVE);
			acc.setArc(ComplianceReasonCode.BLACKLISTED);
			blackListedcontact = true;
		} else {
			blackListedcontact = false;
		}
		for (ComplianceContact cResponse : responseContacts.values()) {
			if (ContactComplianceStatus.ACTIVE==cResponse.getCcs())
				areAllContactsInactive = false;
			acc.addContact(cResponse);
		}
		// if there is no contact blacklisted and all contacts are INACTIVE
		// update account status to INACTIVE as well.
		if ((areAllContactsInactive || !blackListedcontact) && null != acc.getContacts()) {
			updateAccountResonCode(acc);
			if (areAllContactsInactive) {
				acc.setAcs(ContactComplianceStatus.INACTIVE);
			}
		}
		return blackListedcontact;
	}

	// blackListedContacts gives list of ContactId's where Previous Blacklist
	// Status is true
	// so if current contact had previous status as blacklisted and current
	// status is NOT blacklisted, remove those from list
	// And if the list still has any contacts left, that means there is still
	// blacklisted contact on Account
	// So mark account inactive.
	// If any of updated Contact is found to be blacklisted, add to list and at
	// the end check list size to make sure if any is blacklisted

	/**
	 * Check if other contacts are blacklisted.
	 *
	 * @param blackListedContacts
	 *            the black listed contacts
	 * @param responseContacts
	 *            the response contacts
	 * @return true, if successful
	 */
	private boolean checkIfOtherContactsAreBlacklisted(String blackListedContacts,
			Map<Integer, ComplianceContact> responseContacts) {
		StringBuilder tmpBuilder = new StringBuilder();
		String tmp = blackListedContacts;
		for (ComplianceContact cResponse : responseContacts.values()) {
			if (ComplianceReasonCode.BLACKLISTED==cResponse.getCrc()
					|| ComplianceReasonCode.BLACKLISTCOUNTRY==cResponse.getCrc()
					|| ComplianceReasonCode.GLOBACLCHECK==cResponse.getCrc()) {
				tmpBuilder.append(tmp).append(cResponse.getId()).append(",");
				tmp = tmpBuilder.toString();
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			} else {
				tmp = tmp.replace(cResponse.getId() + ",", "");
			}

		}

		return (tmp.length() > 0);
	}

	/**
	 * Update account reson code.
	 *
	 * @param acc
	 *            the acc
	 */
	private void updateAccountResonCode(ComplianceAccount acc) {
		for (ComplianceContact complianceContact : acc.getContacts()) {
			if (null != complianceContact.getCrc()) {
				acc.setArc(complianceContact.getCrc());
			}
		}
	}

	/**
	 * Process KYC response.
	 *
	 * @param request
	 *            the request
	 * @param responseContacts
	 *            the response contacts
	 * @param oldContacts
	 *            the old contacts
	 * @param exchange
	 *            the exchange
	 */
	private void processKYCResponse(RegistrationServiceRequest request,
			Map<Integer, ComplianceContact> responseContacts, List<Contact> oldContacts, MessageExchange exchange) {
		KYCProviderResponse kycProviderResponse;

		// AT-415.It checks whether update request is kyceligible or
		// sanctioneligible & if eligible then its
		// respective methods are called.-Tejas I
		Account account = request.getAccount();
		List<Contact> contacts = account.getContacts();
		Boolean isKycEligible = Boolean.FALSE;
		for (Contact contact : contacts) {
			if (contact.isKYCEligible()) {
				isKycEligible = Boolean.TRUE;
			}
		}
		// AT-1176. It will check if the update request contains any contact by
		// (account.getContacts().size() > 0) this condation ,
		// to avoid null-pointer exception
		if (exchange != null && !account.getContacts().isEmpty()) {
			kycProviderResponse = (KYCProviderResponse) exchange.getResponse();
			if (kycProviderResponse != null && isKycEligible)
				getKYCStatus(responseContacts, kycProviderResponse, request, oldContacts);
			else if (kycProviderResponse != null) {
				getDefaultKycResponse(responseContacts, kycProviderResponse, oldContacts, request);
			}
		}
	}

	/**
	 * Process sanction response.
	 *
	 * @param request
	 *            the request
	 * @param responseContacts
	 *            the response contacts
	 * @param oldContacts
	 *            the old contacts
	 * @param exchange
	 *            the exchange
	 */
	private void processSanctionResponse(RegistrationServiceRequest request,
			Map<Integer, ComplianceContact> responseContacts, List<Contact> oldContacts, MessageExchange exchange) {
		SanctionResponse sanctionResponse;
		// AT-415.It checks whether update request is kyceligible or
		// sanctioneligible & if eligible then its
		// respective methods are called.-Tejas I
		Account account = request.getAccount();
		List<Contact> contacts = account.getContacts();
		Boolean isSanctionEligible = Boolean.FALSE;
		for (Contact contact : contacts) {
			if (contact.isSanctionEligible()) {
				isSanctionEligible = Boolean.TRUE;
			}
		}
		// AT-1176. It will check if the update request contains any contact by
		// (account.getContacts().size() > 0) this condation ,
		// to avoid null-pointer exception
		if (exchange != null && !account.getContacts().isEmpty()) {
			sanctionResponse = (SanctionResponse) exchange.getResponse();
			if (sanctionResponse != null && isSanctionEligible)
				processPFXSanctionResponse(responseContacts, sanctionResponse, request);
			else if (sanctionResponse != null)
				getDefaultSanctionResponse(responseContacts, sanctionResponse, oldContacts);
		}
	}

	/**
	 * Process fraugster response.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param exchange
	 *            the exchange
	 */
	private void processFraugsterResponse(Map<Integer, ComplianceContact> responseContacts, MessageExchange exchange) {
		FraugsterOnUpdateResponse fResponse;
		if (exchange != null) {
			fResponse = (FraugsterOnUpdateResponse) exchange.getResponse();
			if (fResponse != null)
				updateFraugsterOnUpdateStatus(responseContacts, fResponse);
		}
	}

	/**
	 * Purpose : This method is called when if update request is not kyc
	 * eligible. Implementation:-The Method set old contact status so that an
	 * INACTIVE contact due to kyc failed doesn't change to ACTIVE since rest of
	 * flow may set ACTIVE status.And Vice versa for ACTIVE to INACTIVE.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param kycProviderResponse
	 *            the kyc provider response
	 * @param oldContacts
	 *            the old contacts
	 * @param request
	 *            the request
	 * @return the default kyc response
	 */
	private void getDefaultKycResponse(Map<Integer, ComplianceContact> responseContacts,
			KYCProviderResponse kycProviderResponse, List<Contact> oldContacts, RegistrationServiceRequest request) {
		// For loop added for At-1182.
		for (KYCContactResponse kycResponse : kycProviderResponse.getContactResponse()) {
			ComplianceContact cResponse = responseContacts.get(kycResponse.getId());
			for (Contact requestContact : oldContacts) {
				boolean otherServiceStatus = !isContactBlacklisted(cResponse) && !isContactSanctioned(cResponse)
						&& !isContactPreviouKYCPass(requestContact);
				if (null != cResponse && requestContact.getId().equals(kycResponse.getId()) && otherServiceStatus) {

					cResponse.setCcs(ContactComplianceStatus.valueOf(requestContact.getContactStatus()));
					setPreviousInactiveStatus(request, cResponse);
				}
			}
		}
	}

	/**
	 * Sets the previous inactive status.
	 *
	 * @param request
	 *            the request
	 * @param cResponse
	 *            the c response
	 */
	private void setPreviousInactiveStatus(RegistrationServiceRequest request, ComplianceContact cResponse) {
		if ("INACTIVE".equals(cResponse.getCcs().getComplianceStatusAsString())) {
			cResponse.setReasonForInactive(request.getAdditionalAttribute("previousInactiveReason").toString());
		}
	}

	/**
	 * Checks if is contact previou KYC pass.
	 *
	 * @param requestContact
	 *            the request contact
	 * @return true, if is contact previou KYC pass
	 */
	private boolean isContactPreviouKYCPass(Contact requestContact) {
		return null != requestContact.getPreviousKycStatus()
				&& ServiceStatus.PASS.getServiceStatusAsString().equals(requestContact.getPreviousKycStatus());
	}

	/**
	 * Purpose : This method is called when if update request is not sanction
	 * eligible. Implementation:-The Method set old contact status so that an
	 * INACTIVE contact due to sanction failed doesn't change to ACTIVE since
	 * rest of flow may set ACTIVE status.And Vice versa for ACTIVE to INACTIVE.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param sanctionResponse
	 *            the sanction response
	 * @param oldContacts
	 *            the old contacts
	 * @return the default sanction response
	 */
	private void getDefaultSanctionResponse(Map<Integer, ComplianceContact> responseContacts,
			SanctionResponse sanctionResponse, List<Contact> oldContacts) {
		// For loop added for At-1182.
		for (SanctionContactResponse sResponse : sanctionResponse.getContactResponses()) {
			ComplianceContact cResponse = responseContacts.get(sResponse.getContactId());
			for (Contact requestContact : oldContacts) {
				if (requestContact.getId().equals(sResponse.getContactId()) && null != cResponse
						&& !isContactBlacklisted(cResponse) && !isContactPreviousSanctionPass(requestContact)) {

					cResponse.setCcs(ContactComplianceStatus.valueOf(requestContact.getContactStatus()));
				}
			}
		}
	}

	/**
	 * Checks if is contact previous sanction pass.
	 *
	 * @param requestContact
	 *            the request contact
	 * @return true, if is contact previous sanction pass
	 */
	private boolean isContactPreviousSanctionPass(Contact requestContact) {
		return null != requestContact.getPreviousSanctionStatus()
				&& ServiceStatus.PASS.getServiceStatusAsString().equals(requestContact.getPreviousSanctionStatus());
	}

	/**
	 * Purpose : This method is called to compare old status of account and new
	 * status of account after update request. Implementation:-If account is not
	 * blacklisted then it checks account status. If old and new account status
	 * is same then account status remain same and no reason code & description
	 * is added. If status are different then status of each contact in that
	 * account is checked except for the one which is in present update request
	 * and if any one contact is active then account status is set to ACTIVE
	 * with no reason code & description
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	private void compareoldnewAccountStatus(RegistrationServiceRequest request, RegistrationResponse response) {
		Account oldAccount = (Account) request.getAdditionalAttribute(ACCOUNT);
		if (oldAccount != null && null != oldAccount.getAccountStatus()
				&& response.getAccount().getAcs().name().equals(oldAccount.getAccountStatus())) {
			response.getAccount().setArc(ComplianceReasonCode.ACCOUNT_STATUS_UNCHANGED);
		} else {
			if (!request.getAccount().getContacts().isEmpty())
				updateStatusFromProvious(response, oldAccount);

		}
	}

	/**
	 * Update status from provious.
	 *
	 * @param response
	 *            the response
	 * @param oldAccount
	 *            the old account
	 */
	private void updateStatusFromProvious(RegistrationResponse response, Account oldAccount) {
		List<ComplianceContact> requestContacts = response.getAccount().getContacts();
		List<Contact> oldContacts = oldAccount.getContacts();
		for (int i = 0; i < oldContacts.size(); i++) {
			Contact contact = oldContacts.get(i);
			for (ComplianceContact complianceContact : requestContacts) {
				comparecontactAndUpdateAccountStatus(response, contact, complianceContact);
			}
		}

	}

	/**
	 * Comparecontact and update account status.
	 *
	 * @param response
	 *            the response
	 * @param contact
	 *            the contact
	 * @param complianceContact
	 *            the compliance contact
	 */
	private void comparecontactAndUpdateAccountStatus(RegistrationResponse response, Contact contact,
			ComplianceContact complianceContact) {
		if (!complianceContact.getId().equals(contact.getId())
				&& ContactComplianceStatus.ACTIVE.name().equals(contact.getContactStatus())) {
			response.getAccount().setAcs(ContactComplianceStatus.ACTIVE);
			response.getAccount().setArc(ComplianceReasonCode.ACCOUNT_STATUS_UNCHANGED);
		}
	}

	/**
	 * Perform cfx rules.
	 *
	 * @param msg
	 *            the msg
	 */
	private void performCfxRules(Message<MessageContext> msg) {
		RegistrationServiceRequest request = (RegistrationServiceRequest) msg.getPayload().getGatewayMessageExchange()
				.getRequest();
		RegistrationResponse response = (RegistrationResponse) msg.getPayload().getGatewayMessageExchange()
				.getResponse();
		if (response == null) {
			response = new RegistrationResponse();
			msg.getPayload().getGatewayMessageExchange().setResponse(response);
		}
		response.setOrgCode(request.getOrgCode());
		ComplianceAccount complianceAccount = createDefaultCFXResponse(request);
		InternalServiceResponse internalServiceResponseCfx = (InternalServiceResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
		Map<Integer, ComplianceContact> responseContactCfx = new HashMap<>();
		for (ComplianceContact contact : complianceAccount.getContacts()) {
			responseContactCfx.put(contact.getId(), contact);
		}
		Boolean isAnyContactBlacklisted = processCFXBlackListResponse(responseContactCfx, internalServiceResponseCfx);
		if (Boolean.TRUE.equals(isAnyContactBlacklisted)) {
			updateCFXAccountForBlacklisted(complianceAccount);
		} else {
			updateCFXAccountIfAnyContactBlacklisted(complianceAccount);
		}

		// verify sanction status
		if (request.getAccount().getCompany().isSanctionEligible()) {
			updateCFXAccountSanctionStatus(msg, complianceAccount);
		}

		setServiceStatuses(complianceAccount, msg);

		// code commented for jira AT-875
		response.setAccount(complianceAccount);

		MessageExchange exchange = msg.getPayload().getGatewayMessageExchange();
		OperationEnum operation = exchange.getOperation();
		if (operation == OperationEnum.RECHECK_FAILURES) {
			getBulkRecheckServiceStatusForAccount(complianceAccount, response);
			for (ComplianceContact con : complianceAccount.getContacts()) {
				Integer contactId = (Integer) request.getAdditionalAttribute(Constants.CONTACT_ID);
				if (null != contactId && contactId.equals(con.getId())) {
					getBulkRecheckServiceStatusForContact(con, response);
					processContactsStatusForCFX(complianceAccount, con, isAnyContactBlacklisted);
				}
			}
		}
		/**
		 * Code added to set contact status as per Account status
		 * If Account status: ACTIVE then all contact statuses should be ACTIVE &
		 * If Account status: INACTIVE then all contact statuses should be INACTIVE
		 * */
		updateContactStatusAsPerAccount(complianceAccount);
	}

	/**
	 * Update contact status as per account.
	 *
	 * @param complianceAccount the compliance account
	 */
	private void updateContactStatusAsPerAccount(ComplianceAccount complianceAccount) {
		if(ContactComplianceStatus.INACTIVE.getComplianceStatusAsString().equals(complianceAccount.getAcs().getComplianceStatusAsString())) {
			for(ComplianceContact complianceContact: complianceAccount.getContacts()){
				complianceContact.setCcs(complianceAccount.getAcs());
			}
		}
	}

	/**
	 * Process contacts status for CFX.
	 *
	 * @param complianceAccount
	 *            the compliance account
	 * @param con
	 *            the con
	 * @param isAnyContactBlacklisted
	 *            the is any contact blacklisted
	 */
	private void processContactsStatusForCFX(ComplianceAccount complianceAccount, ComplianceContact con,
			Boolean isAnyContactBlacklisted) {
		if (null == con.getResponseCode() && !isAnyContactBlacklisted) {
			con.setResponseCode(ComplianceReasonCode.COMPLIANCE_PENDING.getReasonCode());
			con.setResponseDescription(ComplianceReasonCode.COMPLIANCE_PENDING.getReasonDescription());
			con.setCcs(ContactComplianceStatus.ACTIVE);
		} else if (null == con.getResponseCode()) {
			con.setResponseCode(ComplianceReasonCode.BLACKLISTED.getReasonCode());
			con.setResponseDescription(ComplianceReasonCode.BLACKLISTED.getReasonDescription());
			con.setCcs(ContactComplianceStatus.INACTIVE);
			complianceAccount.setAcs(ContactComplianceStatus.INACTIVE);
			complianceAccount.setArc(ComplianceReasonCode.BLACKLISTED);
		}
	}

	/**
	 * Gets the bulk recheck service status for account.
	 *
	 * @param complianceAccount
	 *            the compliance account
	 * @param response
	 *            the response
	 * @return the bulk recheck service status for account
	 */
	private void getBulkRecheckServiceStatusForAccount(ComplianceAccount complianceAccount,
			RegistrationResponse response) {
		Boolean recheckStatus = Boolean.FALSE;
		if (!ServiceStatus.SERVICE_FAILURE.name().equals(complianceAccount.getKycStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(complianceAccount.getFraugsterStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(complianceAccount.getBlacklistStatus())
				&& !ServiceStatus.SERVICE_FAILURE.name().equals(complianceAccount.getSanctionStatus())) {
			recheckStatus = Boolean.TRUE;
		}
		response.addAttribute(Constants.RECHECK_STATUS, recheckStatus);
	}

	/**
	 * Update CFX account sanction status.
	 *
	 * @param msg
	 *            the msg
	 * @param complianceAccount
	 *            the compliance account
	 */
	private void updateCFXAccountSanctionStatus(Message<MessageContext> msg, ComplianceAccount complianceAccount) {
		SanctionResponse sResponse = (SanctionResponse) msg.getPayload()
				.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse();
		for (SanctionContactResponse cResponse : sResponse.getContactResponses()) {
			if (cResponse.getSanctionId().contains("-A-")
					&& (!Constants.PASS.equalsIgnoreCase(cResponse.getStatus()))) {
				complianceAccount.setArc(ComplianceReasonCode.SANCTIONED_COMPANY);
				complianceAccount.setAcs(ContactComplianceStatus.INACTIVE);
			}
		}
	}

	/**
	 * Update CFX account if any contact blacklisted.
	 *
	 * @param complianceAccount
	 *            the compliance account
	 */
	private void updateCFXAccountIfAnyContactBlacklisted(ComplianceAccount complianceAccount) {
		for (ComplianceContact contact : complianceAccount.getContacts()) {
			if (contact.getCrc() == ComplianceReasonCode.BLACKLISTED
					|| contact.getCrc() == ComplianceReasonCode.BLACKLISTED_COMPANY) {
				complianceAccount.setArc(contact.getCrc());
				complianceAccount.setAcs(ContactComplianceStatus.INACTIVE);
				break;
			}
		}
	}

	/**
	 * Update CFX account for blacklisted.
	 *
	 * @param complianceAccount
	 *            the compliance account
	 */
	private void updateCFXAccountForBlacklisted(ComplianceAccount complianceAccount) {
		complianceAccount.setAcs(ContactComplianceStatus.INACTIVE);
		complianceAccount.setArc(ComplianceReasonCode.COMPLIANCE_PENDING);
		for (ComplianceContact contact : complianceAccount.getContacts()) {
			contact.setCcs(ContactComplianceStatus.INACTIVE);
			contact.setCrc(ComplianceReasonCode.COMPLIANCE_PENDING);
		}
	}

	/**
	 * Process CFX black list response.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return true, if successful
	 */
	private boolean processCFXBlackListResponse(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse) {
		boolean isAnyContactBlacklisted = false;
		boolean isAccountUpdated = true;
		for (ContactResponse blacklistContactResponse : internalServiceResponse.getContacts()) {
			if (null != blacklistContactResponse.getBlacklist().getStatus()) {
				if ((EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(blacklistContactResponse.getEntityType())
						&& !blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.PASS.name())) {
					isAnyCFXContactBlacklisted(responseContacts, blacklistContactResponse, isAccountUpdated);
					isAnyContactBlacklisted = true;
				} else if (!blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.PASS.name())) {
					isAccountUpdated = false;
					isAnyCFXContactBlacklisted(responseContacts, blacklistContactResponse, isAccountUpdated);
				}
			}
		}
		return isAnyContactBlacklisted;
	}

	/**
	 * Checks if is any CFX contact blacklisted.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param blacklistContactResponse
	 *            the blacklist contact response
	 * @param isAccountUpdated
	 *            the is account updated
	 */
	private void isAnyCFXContactBlacklisted(Map<Integer, ComplianceContact> responseContacts,
			ContactResponse blacklistContactResponse, boolean isAccountUpdated) {
		ComplianceReasonCode reasonCode;

		if (isAccountUpdated) {

			reasonCode = ComplianceReasonCode.BLACKLISTED_COMPANY;
		} else {
			reasonCode = ComplianceReasonCode.BLACKLISTED;
		}

		for (ComplianceContact cResponse : responseContacts.values()) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			if (blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
				cResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
			} else {
				cResponse.setCrc(reasonCode);
			}
		}
	}

	/**
	 * Process PFX black list response.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return true, if successful
	 */
	private boolean processPFXBlackListResponse(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse, RegistrationServiceRequest request) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse blacklistContactResponse : internalServiceResponse.getContacts()) {
			ComplianceContact cResponse = responseContacts.get(blacklistContactResponse.getId());
			if (blacklistContactResponse.getBlacklist().getStatus().equals(ServiceStatus.FAIL.name())) {
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				cResponse.setSkipRestChecks(Boolean.TRUE);
				cResponse.setCrc(ComplianceReasonCode.BLACKLISTED);
				isAnyContactBlacklisted = true;
				request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
			} else if (blacklistContactResponse.getBlacklist().getStatus()
					.equals(ServiceStatus.SERVICE_FAILURE.name())) {
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				cResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
				cResponse.setSkipRestChecks(Boolean.TRUE);
				isAnyContactBlacklisted = false;
				request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
			} else {
				cResponse.setCcs(ContactComplianceStatus.ACTIVE);
			}
		}
		return isAnyContactBlacklisted;
	}

	/**
	 * Gets the global check status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the global check status
	 */
	private Boolean getGlobalCheckStatus(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse, RegistrationServiceRequest request) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse contactResponse : internalServiceResponse.getContacts()) {
			if (!(EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(contactResponse.getEntityType())) {
				ComplianceContact cResponse = responseContacts.get(contactResponse.getId());
				if (Boolean.FALSE.equals(cResponse.getSkipRestChecks())) {
					isAnyContactBlacklisted = updateContactGlobalCheckStatus(contactResponse, cResponse, request);
				}
			}
		}
		return isAnyContactBlacklisted;
	}

	/**
	 * Update contact global check status.
	 *
	 * @param contactResponse
	 *            the contact response
	 * @param cResponse
	 *            the c response
	 * @return true, if successful
	 */
	private boolean updateContactGlobalCheckStatus(ContactResponse contactResponse, ComplianceContact cResponse, RegistrationServiceRequest request) {
		boolean isAnyContactBlacklisted = false;
		if (ServiceStatus.FAIL.name().equals(contactResponse.getGlobalCheck().getStatus())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			cResponse.setCrc(ComplianceReasonCode.GLOBACLCHECK);
			isAnyContactBlacklisted = true;
			request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);

		} else if (ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(contactResponse.getGlobalCheck().getStatus())) {
			cResponse.setCrc(ComplianceReasonCode.USGLOBAL_WATCHLIST);

		} else if (ServiceStatus.SERVICE_FAILURE.name()
				.equalsIgnoreCase(contactResponse.getGlobalCheck().getStatus())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			cResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
			request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);

		} else if (!ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contactResponse.getGlobalCheck().getStatus())) {
			cResponse.setCcs(ContactComplianceStatus.ACTIVE);
		}
		return isAnyContactBlacklisted;
	}

	/**
	 * Gets the high risk country status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param internalServiceResponse
	 *            the internal service response
	 * @return the high risk country status
	 */
	private Boolean getHighRiskCountryStatus(Map<Integer, ComplianceContact> responseContacts,
			InternalServiceResponse internalServiceResponse, RegistrationServiceRequest request) {
		boolean isAnyContactBlacklisted = false;
		for (ContactResponse contactResponse : internalServiceResponse.getContacts()) {
			if (!(EntityEnum.ACCOUNT.toString()).equalsIgnoreCase(contactResponse.getEntityType())) {
				ComplianceContact cResponse = responseContacts.get(contactResponse.getId());
				if (Boolean.FALSE.equals(cResponse.getSkipRestChecks())) {
					isAnyContactBlacklisted = updateContactHighRiskCountryStatus(contactResponse, cResponse, request);
				}
			}
		}
		return isAnyContactBlacklisted;
	}

	/**
	 * Update contact high risk country status.
	 *
	 * @param contactResponse
	 *            the contact response
	 * @param cResponse
	 *            the c response
	 * @return true, if successful
	 */
	private boolean updateContactHighRiskCountryStatus(ContactResponse contactResponse, ComplianceContact cResponse, RegistrationServiceRequest request) {
		boolean isAnyContactBlacklisted = false;
		if (contactResponse.getCountryCheck().getStatus().equals(ServiceStatus.FAIL.name())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			cResponse.setCrc(ComplianceReasonCode.BLACKLISTCOUNTRY);
			isAnyContactBlacklisted = true;
			request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);

		} else if (ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(contactResponse.getCountryCheck().getStatus())) {
			cResponse.setCrc(ComplianceReasonCode.HIGHRISK_WATCHLIST);
		} else if (ServiceStatus.SERVICE_FAILURE.name()
				.equalsIgnoreCase(contactResponse.getCountryCheck().getStatus())) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setCrc(ComplianceReasonCode.SYSTEM_FAILURE);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
		} else if (!ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(contactResponse.getCountryCheck().getStatus())) {
			cResponse.setCcs(ContactComplianceStatus.ACTIVE);
		}
		return isAnyContactBlacklisted;
	}

	/**
	 * Process PFX sanction response.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param sanctionResponse
	 *            the sanction response
	 * @return the boolean
	 */
	private Boolean processPFXSanctionResponse(Map<Integer, ComplianceContact> responseContacts,
			SanctionResponse sanctionResponse, RegistrationServiceRequest request) {
		boolean areAllContactsInactive = true;
		for (SanctionContactResponse sResponse : sanctionResponse.getContactResponses()) {
			ComplianceContact cResponse = responseContacts.get(sResponse.getContactId());
			if (!ServiceStatus.PASS.name().equalsIgnoreCase(sResponse.getStatus())
					&& !ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(sResponse.getStatus())) {// &&
																										// !ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sResponse.getStatus())
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				cResponse.setSkipRestChecks(Boolean.TRUE);
				cResponse.setCrc(ComplianceReasonCode.SANCTIONED);
				request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
			} else if (ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(sResponse.getStatus())) {
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				cResponse.setSkipRestChecks(Boolean.TRUE);
				cResponse.setCrc(ComplianceReasonCode.SANCTIONED);
				areAllContactsInactive = true;
				request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
			} else {
				areAllContactsInactive = false;
			}
		}
		return areAllContactsInactive;
	}

	/**
	 * Gets the KYC status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param kycProviderResponse
	 *            the kyc provider response
	 * @param request
	 *            the request
	 * @return the KYC status
	 */
	private Boolean getKYCStatus(Map<Integer, ComplianceContact> responseContacts,
			KYCProviderResponse kycProviderResponse, RegistrationServiceRequest request, List<Contact> oldContacts) {
		boolean areAllContactsInactive = true;
		boolean isEligibleForEmail = (boolean) request.getAdditionalAttribute("sendEmailOnAddressChange");

		if (ServiceStatus.FAIL.name().equalsIgnoreCase(kycProviderResponse.getStatus())) {
			for (ComplianceContact cResponse : responseContacts.values()) {

				/*
				 * if statement added to send email after address change instead
				 * of INACTIVATE contact(AT-1503)
				 */
				if (Boolean.TRUE.equals(isEligibleForEmail) && Boolean.FALSE.equals(cResponse.getSkipRestChecks())) {
					Contact oldContact = getContactBySFId(cResponse.getContactSFID(), oldContacts);
					if(null != oldContact) {
						cResponse.setCcs(ContactComplianceStatus.valueOf(oldContact.getContactStatus()));
					}
				}else{
					cResponse.setCcs(ContactComplianceStatus.INACTIVE);
				}
				cResponse.setCrc(ComplianceReasonCode.KYC_NA);
			}
		} else {
			areAllContactsInactive = processKYCResponse(responseContacts, kycProviderResponse, isEligibleForEmail, oldContacts, request);
		}
		return areAllContactsInactive;
	}

	/**
	 * Process KYC response.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param kycProviderResponse
	 *            the kyc provider response
	 * @param isEligibleForEmail
	 *            the is eligible for email
	 * @return true, if successful
	 */
	private boolean processKYCResponse(Map<Integer, ComplianceContact> responseContacts,
			KYCProviderResponse kycProviderResponse, boolean isEligibleForEmail, 
			List<Contact> oldContacts, RegistrationServiceRequest request) {
		
		boolean areAllContactsInactive = false;
		boolean isContactSanctioned = false;
		
		for (KYCContactResponse kycResponse : kycProviderResponse.getContactResponse()) {
			
			ComplianceContact cResponse = responseContacts.get(kycResponse.getId());
			if(null != cResponse.getCrc()) {
				isContactSanctioned = (cResponse.getCrc()==ComplianceReasonCode.SANCTIONED);
			}
			Contact oldContact = getContactBySFId(cResponse.getContactSFID(), oldContacts);
			String kycResponseStatus = kycResponse.getStatus();
			
			if (!ServiceStatus.PASS.name().equalsIgnoreCase(kycResponseStatus)
					&& !ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(kycResponseStatus)) {// &&
				// !ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(kycResponse.getBandText())
				/*
				 * if statement added to send email after address change instead
				 * of INACTIVATE contact(AT-1503)
				 */
				checkContactStatusOnAddressChange(cResponse, isEligibleForEmail, oldContact,
						kycResponse, isContactSanctioned);
			} else if (ServiceStatus.SERVICE_FAILURE.name().equalsIgnoreCase(kycResponseStatus)) {
				/*
				 * if statement added to send email after address change instead
				 * of INACTIVATE contact(AT-1503)
				 */
				if (updateContactComplianceStatus(cResponse, isEligibleForEmail, oldContact)) {
					areAllContactsInactive = true;
				}
			} else {
				areAllContactsInactive = false;
				request.addAttribute(Constants.SEND_EMAIL_ON_ADDRESS_CHANGE, Boolean.FALSE);
			}
		}
		
		return areAllContactsInactive;
	}
	
	private void checkContactStatusOnAddressChange(ComplianceContact cResponse,
			boolean isEligibleForEmail, Contact oldContact, 
			KYCContactResponse kycResponse, boolean isContactSanctioned) {
		
		decideContactStatusOnAddressChange(isEligibleForEmail, cResponse, oldContact);
		if (!isPOIOrPOANeeded(kycResponse.getBandText(), cResponse, isEligibleForEmail, isContactSanctioned)) {					
			processKYCFailResponse(kycResponse, cResponse);
		}
	}
	
	private boolean updateContactComplianceStatus(ComplianceContact cResponse,
			boolean isEligibleForEmail, Contact oldContact) {
		
		boolean allContactsInactive = false;
		if (null != oldContact && Boolean.TRUE.equals(isEligibleForEmail) && Boolean.FALSE.equals(cResponse.getSkipRestChecks())) {
			cResponse.setCcs(ContactComplianceStatus.valueOf(oldContact.getContactStatus()));
		} else {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			allContactsInactive = true;
		}
		cResponse.setSkipRestChecks(Boolean.TRUE);
		cResponse.setCrc(ComplianceReasonCode.KYC);
		
		return allContactsInactive;
	}

	private void decideContactStatusOnAddressChange(boolean isEligibleForEmail, ComplianceContact cResponse,
			Contact oldContact) {
		if (null != oldContact && Boolean.TRUE.equals(isEligibleForEmail) && Boolean.FALSE.equals(cResponse.getSkipRestChecks())) {
			cResponse.setCcs(ContactComplianceStatus.valueOf(oldContact.getContactStatus()));
		}else{
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
		}
	}
	
	private boolean isPOIOrPOANeeded(String bandText, ComplianceContact cResponse, boolean isEligibleForEmail,
			boolean isContactSanctioned) {
		boolean result = false;
		if (!isContactSanctioned && Constants.POA_NEEDED.equalsIgnoreCase(bandText)) {
			cResponse.setCrc(ComplianceReasonCode.KYC_POA);
			if(!isEligibleForEmail) {
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			}
			cResponse.setSkipRestChecks(Boolean.TRUE);
			result = true;
		} else if (!isContactSanctioned && Constants.POI_NEEDED.equalsIgnoreCase(bandText)) {
			result = setPOIResponse(cResponse, isEligibleForEmail);
		} 
		// ADD for AT-3398
		/*
		 * else if (!isContactSanctioned &&
		 * Constants.EU_LE_POI_NEEDED.equalsIgnoreCase(bandText)) { result =
		 * setPOIResponse(cResponse, isEligibleForEmail); }
		 */else if(isContactSanctioned) {
			cResponse.setCrc(ComplianceReasonCode.KYC_AND_SANCTIONED);
			cResponse.setSkipRestChecks(Boolean.TRUE);
			result = true;
		}
		return result;
	}

	private boolean setPOIResponse(ComplianceContact cResponse, boolean isEligibleForEmail) {
		boolean result;
		cResponse.setCrc(ComplianceReasonCode.KYC_POI);
		if(!isEligibleForEmail) {
			cResponse.setCcs(ContactComplianceStatus.INACTIVE);
		}
		cResponse.setSkipRestChecks(Boolean.TRUE);
		result = true;
		
		return result;
	}

	/**
	 * Process KYC fail response.
	 *
	 * @param kycResponse
	 *            the kyc response
	 * @param cResponse
	 *            the c response
	 */
	private void processKYCFailResponse(KYCContactResponse kycResponse, ComplianceContact cResponse) {
		if (Boolean.TRUE.equals(cResponse.getSkipRestChecks())) {
			cResponse.setCrc(ComplianceReasonCode.KYC_AND_SANCTIONED);
		} else if (Constants.COUNTRY_NOT_SUPPORTED.equals(kycResponse.getErrorCode())) {
			cResponse.setSkipRestChecks(Boolean.TRUE);
			cResponse.setCrc(ComplianceReasonCode.KYC_NA);
		} else {
			cResponse.setSkipRestChecks(Boolean.TRUE);
			cResponse.setCrc(ComplianceReasonCode.KYC);
		}
	}

	/**
	 * Update fraugster on update status.
	 *
	 * @param responseContacts
	 *            the response contacts
	 * @param fResponse
	 *            the f response
	 */
	private void updateFraugsterOnUpdateStatus(Map<Integer, ComplianceContact> responseContacts,
			FraugsterOnUpdateResponse fResponse) {

		for (FraugsterOnUpdateContactResponse idResponse : fResponse.getContactResponses()) {
			ComplianceContact cResponse = responseContacts.get(idResponse.getId());
			if (ServiceStatus.WATCH_LIST.name().equalsIgnoreCase(idResponse.getStatus())) {
				cResponse.setResponseCode(ComplianceReasonCode.FRAUGSTER_WATCHLIST.getReasonCode());
				cResponse.setResponseDescription(ComplianceReasonCode.FRAUGSTER_WATCHLIST.getReasonDescription());
			}
		}
	}

	/**
	 * Creates the default response.
	 *
	 * @param request
	 *            the request
	 * @return In Response, all contact status should be updated, specially when
	 *         any contact is blacklisted Hence get the lost of contacts from
	 *         Attributes
	 */

	@SuppressWarnings("unchecked")
	private static Map<Integer, ComplianceContact> createDefaultResponse(RegistrationServiceRequest request) {
		List<Contact> contacts;
		if(!request.getAccount().getContacts().isEmpty()) {
			contacts = request.getAccount().getContacts();
		} else {
			contacts = (List<Contact>) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		}
		Map<Integer, ComplianceContact> responseContacts = new HashMap<>(contacts.size());
		for (Contact requestContact : contacts) {
			ComplianceContact cResponse = new ComplianceContact();
			cResponse.setVersion(requestContact.getVersion());
			cResponse.setId(requestContact.getId());
			cResponse.setContactSFID(requestContact.getContactSFID());
			cResponse.setCcs(ContactComplianceStatus.valueOf(requestContact.getContactStatus()));
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
	 * @return the compliance account
	 */
	private static ComplianceAccount createDefaultCFXResponse(RegistrationServiceRequest request) {
		Account oldAccount = (Account) request.getAdditionalAttribute(ACCOUNT);
		List<ComplianceContact> responseContacts = new ArrayList<>();
		ComplianceAccount complianceAccount = new ComplianceAccount();
		complianceAccount.setId(request.getAccount().getId());
		complianceAccount.setAccountSFID(request.getAccount().getAccSFID());
		complianceAccount.setTradeAccountID(request.getAccount().getTradeAccountID());
		complianceAccount.setVersion(request.getAccount().getVersion());

		if (oldAccount != null && oldAccount.getAccountStatus() != null && null == complianceAccount.getArc()) {
			complianceAccount.setAcs(ContactComplianceStatus.valueOf(oldAccount.getAccountStatus()));
			complianceAccount.setArc(ComplianceReasonCode.RECORD_UPDATED_SUCCESSFULLY);
		}

		if(null != oldAccount) {
			for (Contact requestContact : oldAccount.getContacts()) {
				ComplianceContact cResponse = new ComplianceContact();
				cResponse.setVersion(requestContact.getVersion());
				cResponse.setId(requestContact.getId());
				cResponse.setContactSFID(requestContact.getContactSFID());
				cResponse.setTradeContactID(requestContact.getTradeContactID());
				responseContacts.add(cResponse);
				Contact t = oldAccount.getContactByID(requestContact.getId());
				cResponse.setCcs(ContactComplianceStatus.valueOf(t.getContactStatus()));
			}
		}
		complianceAccount.setContacts(responseContacts);
		return complianceAccount;
	}

	/**
	 * This method is used for setting different micro service statuses
	 * performed on account and contact these statuses are updated in account
	 * and contact tables
	 * 
	 * If any service status is not_required in update and addcontact then set
	 * previous status.
	 *
	 * @param account
	 *            the account
	 * @param msg
	 *            the msg
	 * @return the compliance account
	 */
	private ComplianceAccount setServiceStatuses(ComplianceAccount account, Message<MessageContext> msg) {
		try {
			OperationEnum operationEnum = msg.getPayload().getGatewayMessageExchange().getOperation();

			MessageExchange blackListexchange = msg.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			MessageExchange sanctionExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);

			RegistrationServiceRequest request = (RegistrationServiceRequest) msg.getPayload()
					.getGatewayMessageExchange().getRequest();
			Integer contactId = (Integer) request.getAdditionalAttribute(Constants.CONTACT_ID);
			Account regAccount = request.getAccount();
			if (Constants.CFX.equalsIgnoreCase(regAccount.getCustType())) {
				EventServiceLog accountBlackListlog = blackListexchange.getEventServiceLog(
						ServiceTypeEnum.BLACK_LIST_SERVICE, EntityEnum.ACCOUNT.name(), account.getId());
				account.setBlacklistStatus(accountBlackListlog.getStatus());
				EventServiceLog accountSanctionLog = sanctionExchange.getEventServiceLog(
						ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.ACCOUNT.name(), account.getId());
				account.setSanctionStatus(accountSanctionLog.getStatus());

			}

			updateAccountPreviousServiceStatus(account, operationEnum, regAccount);

			account.setPaymentinWatchlistStatus(
					(String) request.getAdditionalAttribute(Constants.PAYMENTIN_WATCHLIST_STATUS));
			account.setPaymentoutWatchlistStatus(
					(String) request.getAdditionalAttribute(Constants.PAYMENTOUT_WATCHLIST_STATUS));
			if (null != account.getContacts()) {
				updateAllContactServiceStatuses(account, operationEnum, msg, regAccount, contactId);
			} else {
				// there are no contacts modified and no checks performed for
				// account
				// account status stays same for PFX
				if (Constants.PFX.equalsIgnoreCase(request.getAccount().getCustType())) {
					Account oldAcc = (Account) request.getAdditionalAttribute(ACCOUNT);
					regAccount.setAccountStatus(oldAcc.getAccountStatus());
				}
			}
		} catch (Exception e) {
			LOG.error("error in setting microservice statuses in account and contact", e);
		}
		return account;

	}

	/**
	 * Update account previous service status.
	 *
	 * @param account
	 *            the account
	 * @param operationEnum
	 *            the operation enum
	 * @param regAccount
	 *            the reg account
	 */
	private void updateAccountPreviousServiceStatus(ComplianceAccount account, OperationEnum operationEnum,
			Account regAccount) {
		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(account.getBlacklistStatus())) {
			account.setBlacklistStatus(regAccount.getPreviousBlacklistStatus());
		}

		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(account.getSanctionStatus())) {
			account.setSanctionStatus(regAccount.getPreviousSanctionStatus());
		}

		/**
		 * KYC and fraugster status will be always not_required fro account.
		 * need to check whether these below to check can be skipped?
		 **/
		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(account.getKycStatus())) {
			account.setKycStatus(regAccount.getPreviousKycStatus());
		}

		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(account.getFraugsterStatus())) {
			account.setFraugsterStatus(regAccount.getPreviousFraugsterStatus());
		}
	}

	/**
	 * Update all contact service statuses.
	 *
	 * @param account
	 *            the account
	 * @param operationEnum
	 *            the operation enum
	 * @param blackListexchange
	 *            the black listexchange
	 * @param sanctionExchange
	 *            the sanction exchange
	 * @param kycExchange
	 *            the kyc exchange
	 * @param fraugsterExchange
	 *            the fraugster exchange
	 * @param regAccount
	 *            the reg account
	 * @param contactId
	 *            the contact id
	 */
	private void updateAllContactServiceStatuses(ComplianceAccount account, OperationEnum operationEnum,
			Message<MessageContext> msg, Account regAccount, Integer contactId) {
		
		MessageExchange blackListexchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		MessageExchange sanctionExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		MessageExchange kycExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
		MessageExchange fraugsterExchange = msg.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		
		for (ComplianceContact contact : account.getContacts()) {

			Contact regContact = regAccount.getContactByID(contact.getId());
			// Compliance Account has all contacts populated, even if not in
			// Update Request
			// hence add null check here to avoid NPE, and DO NOT change service
			// status columns
			if (null != regContact) {
				if (OperationEnum.RECHECK_FAILURES==operationEnum) {
					if (null != contactId && contactId.equals(contact.getId()))
						updateSingleContactServiceStatus(operationEnum, blackListexchange, sanctionExchange,
								kycExchange, fraugsterExchange, contact, regContact);
				} else {
					updateSingleContactServiceStatus(operationEnum, blackListexchange, sanctionExchange, kycExchange,
							fraugsterExchange, contact, regContact);
				}
			}
		}
	}

	/**
	 * Update single contact service status.
	 *
	 * @param operationEnum
	 *            the operation enum
	 * @param blackListexchange
	 *            the black listexchange
	 * @param sanctionExchange
	 *            the sanction exchange
	 * @param kycExchange
	 *            the kyc exchange
	 * @param fraugsterExchange
	 *            the fraugster exchange
	 * @param contact
	 *            the contact
	 * @param regContact
	 *            the reg contact
	 */
	private void updateSingleContactServiceStatus(OperationEnum operationEnum, MessageExchange blackListexchange,
			MessageExchange sanctionExchange, MessageExchange kycExchange, MessageExchange fraugsterExchange,
			ComplianceContact contact, Contact regContact) {
		EventServiceLog contactBlackListlog = blackListexchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());

		EventServiceLog globalCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.GLOBAL_CHECK_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		EventServiceLog countryCheckLog = blackListexchange.getEventServiceLog(ServiceTypeEnum.HRC_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());

		contact.setCustomCheckStatus(getOverallCustomCheckStatus(globalCheckLog, countryCheckLog));
		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(contact.getCustomCheckStatus())) {
			contact.setBlacklistStatus(regContact.getPreviousCountryGlobalCheckStatus());
		}

		contact.setBlacklistStatus(contactBlackListlog.getStatus());
		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& (ServiceStatus.NOT_REQUIRED.toString().equals(contact.getBlacklistStatus())
						|| null == contact.getBlacklistStatus())) {
			contact.setBlacklistStatus(regContact.getPreviousBlacklistStatus());
		}

		EventServiceLog contactSanctionLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.CONTACT.name(), contact.getId());
		contact.setSanctionStatus(contactSanctionLog.getStatus());
		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(contact.getSanctionStatus())) {
			contact.setSanctionStatus(regContact.getPreviousSanctionStatus());
		}

		/**
		 * If we update some details for Account only then since contact was not
		 * provided in request fraugster exchange was getting null below. So
		 * null check is added there.
		 */
		if(null != fraugsterExchange) {
			EventServiceLog fraugsterLog = fraugsterExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
					EntityEnum.CONTACT.name(), contact.getId());
			contact.setFraugsterStatus(fraugsterLog.getStatus());
			if (OperationEnum.UPDATE_ACCOUNT == operationEnum
					&& ServiceStatus.NOT_REQUIRED.toString().equals(contact.getFraugsterStatus())) {
				contact.setFraugsterStatus(regContact.getPreviousFraugsterStatus());
			}
		}

		EventServiceLog kycLog = kycExchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE, EntityEnum.CONTACT.name(),
				contact.getId());
		contact.setKycStatus(kycLog.getStatus());
		setKycStatusOnUpdate(operationEnum, contact, regContact);
	}

	/**
	 * Sets the kyc status on update.
	 *
	 * @param operationEnum
	 *            the operation enum
	 * @param contact
	 *            the contact
	 * @param regContact
	 *            the reg contact
	 */
	private void setKycStatusOnUpdate(OperationEnum operationEnum, ComplianceContact contact, Contact regContact) {
		if (OperationEnum.UPDATE_ACCOUNT == operationEnum
				&& ServiceStatus.NOT_REQUIRED.toString().equals(contact.getKycStatus())) {
			contact.setKycStatus(regContact.getPreviousKycStatus());
		}
	}

	/**
	 * Gets the overall custom check status.
	 *
	 * @param globalCheckLog
	 *            the global check log
	 * @param countryCheckLog
	 *            the country check log
	 * @return the overall custom check status
	 */
	private String getOverallCustomCheckStatus(EventServiceLog globalCheckLog, EventServiceLog countryCheckLog) {
		if (isCustomCheckStatusServiceFailure(globalCheckLog, countryCheckLog)) {
			return Constants.SERVICE_FAILURE;
		} else if (isCountryGlobalCheckStatusFailed(globalCheckLog, countryCheckLog)) {
			return Constants.FAIL;
		} else if (isCustomCheckNotRequired(globalCheckLog, countryCheckLog)) {
			return Constants.NOT_REQUIRED;
		} else if (isCustomCheckNotPerformed(globalCheckLog, countryCheckLog)) {
			return Constants.NOT_PERFORMED;
		} else {
			return Constants.PASS;
		}
	}

	/**
	 * Checks if is custom check not performed.
	 *
	 * @param globalCheckLog
	 *            the global check log
	 * @param countryCheckLog
	 *            the country check log
	 * @return true, if is custom check not performed
	 */
	private boolean isCustomCheckNotPerformed(EventServiceLog globalCheckLog, EventServiceLog countryCheckLog) {

		return Constants.NOT_PERFORMED.equals(countryCheckLog.getStatus())
				|| Constants.NOT_PERFORMED.equals(globalCheckLog.getStatus());
	}

	/**
	 * Checks if is custom check not required.
	 *
	 * @param globalCheckLog
	 *            the global check log
	 * @param countryCheckLog
	 *            the country check log
	 * @return true, if is custom check not required
	 */
	private boolean isCustomCheckNotRequired(EventServiceLog globalCheckLog, EventServiceLog countryCheckLog) {

		return Constants.NOT_REQUIRED.equals(countryCheckLog.getStatus())
				&& Constants.NOT_REQUIRED.equals(globalCheckLog.getStatus());
	}

	/**
	 * Checks if is custom check status service failure.
	 *
	 * @param globalCheckLog
	 *            the global check log
	 * @param countryCheckLog
	 *            the country check log
	 * @return true, if is custom check status service failure
	 */
	private boolean isCustomCheckStatusServiceFailure(EventServiceLog globalCheckLog, EventServiceLog countryCheckLog) {

		return Constants.SERVICE_FAILURE.equals(countryCheckLog.getStatus())
				|| Constants.SERVICE_FAILURE.equals(globalCheckLog.getStatus());
	}

	/**
	 * Checks if is country global check status failed.
	 *
	 * @param globalCheckLog
	 *            the global check log
	 * @param countryCheckLog
	 *            the country check log
	 * @return true, if is country global check status failed
	 */
	private boolean isCountryGlobalCheckStatusFailed(EventServiceLog globalCheckLog, EventServiceLog countryCheckLog) {

		return Constants.FAIL.equals(countryCheckLog.getStatus()) || Constants.FAIL.equals(globalCheckLog.getStatus());
	}

	/**
	 * Default response for un modified field.
	 *
	 * @param msg
	 *            the msg
	 * @return the message
	 */
	public Message<MessageContext> defaultResponseForUnModifiedField(Message<MessageContext> msg) {

		try {
			MessageContext context = msg.getPayload();
			MessageExchange exchange = context.getGatewayMessageExchange();
			RegistrationServiceRequest request = (RegistrationServiceRequest) exchange.getRequest();
			RegistrationResponse response = new RegistrationResponse();
			Account account = request.getAccount();
			response.setOrgCode(request.getOrgCode());
			Account oldAccount = (Account) request.getAdditionalAttribute(ACCOUNT);
			@SuppressWarnings("unchecked")
			List<Contact> oldContacts = (List<Contact>) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
			ComplianceAccount accountResponse = new ComplianceAccount();
			accountResponse.setAccountSFID(account.getAccSFID());
			if (oldAccount != null && oldAccount.getAccountStatus() != null) {
				accountResponse.setAcs(ContactComplianceStatus.valueOf(oldAccount.getAccountStatus()));
			}
			accountResponse.setResponseCode(ComplianceReasonCode.RECORD_UPDATED_SUCCESSFULLY.getReasonCode());
			accountResponse
					.setResponseDescription(ComplianceReasonCode.RECORD_UPDATED_SUCCESSFULLY.getReasonDescription());
			accountResponse.setTradeAccountID(account.getTradeAccountID());
			accountResponse.setId(account.getId());
			accountResponse.setContacts(getDefaultContactResponseList(request.getAccount().getContacts(), oldContacts));
			response.setAccount(accountResponse);
			context.getGatewayMessageExchange().setResponse(response);

		} catch (Exception e) {
			LOG.error("Exception in defaultResponseForUnModifiedField()", e);
		}
		return msg;
	}

	/**
	 * Gets the default contact response list.
	 *
	 * @param contacts
	 *            the contacts
	 * @param oldContacts
	 *            the old contacts
	 * @return the default contact response list
	 */
	private List<ComplianceContact> getDefaultContactResponseList(List<Contact> contacts, List<Contact> oldContacts) {
		List<ComplianceContact> contactResponseList = new ArrayList<>();
		for (Contact contact : contacts) {
			Contact oldContact = getContactById(oldContacts, contact.getId());
			ComplianceContact cResponse = new ComplianceContact();
			cResponse.setContactSFID(contact.getContactSFID());
			cResponse.setId(contact.getId());
			if (null != oldContact)
				cResponse.setCcs(ContactComplianceStatus.valueOf(oldContact.getContactStatus()));
			else
				cResponse.setCcs(ContactComplianceStatus.INACTIVE);
			cResponse.setResponseCode(ComplianceReasonCode.RECORD_UPDATED_SUCCESSFULLY.getReasonCode());
			cResponse.setResponseDescription(ComplianceReasonCode.RECORD_UPDATED_SUCCESSFULLY.getReasonDescription());
			cResponse.setTradeContactID(contact.getTradeContactID());
			contactResponseList.add(cResponse);
		}
		return contactResponseList;
	}

	/**
	 * Gets the contact by id.
	 *
	 * @param contactList
	 *            the contact list
	 * @param id
	 *            the id
	 * @return the contact by id
	 */
	private Contact getContactById(List<Contact> contactList, Integer id) {
		for (Contact contact : contactList) {
			if (id.equals(contact.getId())) {
				return contact;
			}
		}
		return null;
	}

	/**
	 * Update registration dates.
	 *
	 * @param request
	 *            the request
	 * @param account
	 *            the account
	 */
	private void updateRegistrationDates(RegistrationServiceRequest request, ComplianceAccount account) {
		Timestamp registered = (Timestamp) request.getAdditionalAttribute(Constants.FIELD_REGISTERED_TIME);
		Timestamp registrationIn = (Timestamp) request.getAdditionalAttribute(Constants.FIELD_REGISTRATION_IN_TIME);
		Timestamp expiry = (Timestamp) request.getAdditionalAttribute(Constants.FIELD_EXPIRY_TIME);

		account.setRegistrationInDate(registrationIn);

		if (null != registered) {
			account.setRegisteredDate(registered);
			account.setExpiryDate(expiry);
		} else if (ContactComplianceStatus.ACTIVE==account.getAcs()) {
			account.setRegisteredDate(new Timestamp(System.currentTimeMillis()));
			Calendar c = Calendar.getInstance();
			c.setTime(new Timestamp(System.currentTimeMillis()));
			c.add(Calendar.YEAR, PropertyHandler.getComplianceExpiryYears());
			account.setExpiryDate(new Timestamp(c.getTimeInMillis()));
		}

	}

	/**
	 * Checks if is contact blacklisted.
	 *
	 * @param contact
	 *            the contact
	 * @return true, if is contact blacklisted
	 */
	private boolean isContactBlacklisted(ComplianceContact contact) {

		if (contact != null) {
			if (contact.getCrc() == null) {
				return false;
			}
			if (ComplianceReasonCode.BLACKLISTED==contact.getCrc()
					|| ComplianceReasonCode.BLACKLISTCOUNTRY==contact.getCrc()
					|| ComplianceReasonCode.GLOBACLCHECK==contact.getCrc()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if is contact sanctioned.
	 *
	 * @param contact
	 *            the contact
	 * @return true, if is contact sanctioned
	 */
	private boolean isContactSanctioned(ComplianceContact contact) {

		if (contact != null) {
			if (contact.getCrc() == null) {
				return false;
			}
			if (ComplianceReasonCode.SANCTIONED==contact.getCrc()) {
				return true;
			}
		}

		return false;
	}
	
	private Contact getContactBySFId(String sfId, List<Contact> contacts) {
		for (Contact c : contacts) {
			if (c.getContactSFID().equalsIgnoreCase(sfId))
				return c;
		}
		return null;
	}
	
	/**
	 * Sets the contactstatus.
	 *
	 * @param signUpResponse the sign up response
	 * @param signUpRequest the sign up request
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
