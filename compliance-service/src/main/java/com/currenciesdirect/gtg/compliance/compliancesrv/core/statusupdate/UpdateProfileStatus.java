package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoReport;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ContactComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OnfidoStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.PropertyHandler;

/**
 * @author basits
 *
 */
public class UpdateProfileStatus {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(UpdateProfileStatus.class);
	
	/**
	 * @param message
	 * @param correlationID
	 * @return
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
		StatusUpdateResponse response = new StatusUpdateResponse();
		try {
			Account oldAccount = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
			statusUpdateRequest.addAttribute(Constants.FIELD_BROADCAST_REQUIRED, Boolean.FALSE);
			statusUpdateRequest.addAttribute("Details_Not_Match", Boolean.FALSE);
			String serviceStatus = "";
			String serviceSubStatus = "";
			
			//To check for all the reports in the onfido response json for clear
			getOnfidoResult(statusUpdateRequest);
			serviceStatus = (String) statusUpdateRequest.getAdditionalAttribute("onfidoResult");
			serviceSubStatus = (String) statusUpdateRequest.getAdditionalAttribute("onfidoSubResult");
	
			Contact oldContact = null;
			for (Contact contact : oldAccount.getContacts()) {
				if (contact.getContactSFID().equals(statusUpdateRequest.getCrmContactId())) {
					oldContact = contact;
					break;
				}
			}
			if (CustomerTypeEnum.PFX.name().equals(oldAccount.getCustType()) && checkProfileStatus(statusUpdateRequest, oldAccount, oldContact,serviceStatus,serviceSubStatus)) {
				statusUpdateDecision(statusUpdateRequest, oldAccount,oldContact,response,serviceStatus,serviceSubStatus);
			}
			else {
				RegistrationResponse updateResponse;
				updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.INACTIVE,ComplianceReasonCode.MISSINGINFO,response);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceStatus);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.FAIL.name());
				response.getAccount().setReasonForInactive("Contact details does not match from documents or contact is on blacklist");
				response.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
				response.setResponseDescription(ComplianceReasonCode.MISSINGINFO.getReasonDescription());
				statusUpdateRequest.addAttribute(Constants.FIELD_BROADCAST_RESPONSE, updateResponse);
				statusUpdateRequest.addAttribute("Details_Not_Match", Boolean.TRUE);
			}
			response.setOsrID(statusUpdateRequest.getOsrId());
			messageExchange.setResponse(response);
		} catch (Exception e) {
			LOGGER.error("Error in UpdateProfileStatus: {1}", e);
		}
		return message;
	}

	/**
	 * @param statusUpdateRequest
	 * @param oldAccount
	 */
	private boolean checkProfileStatus(StatusUpdateRequest statusUpdateRequest, Account oldAccount, Contact oldContact,String serviceStatus,String serviceSubStatus) {
		String firstName = "";
		String lastName = "";
		String birthDate = "";
		try {
			for (OnfidoReport report : statusUpdateRequest.getReports()) {
				if ("document".equalsIgnoreCase(report.getName())) {
					firstName = report.getProperties().getFirstName();
					lastName = report.getProperties().getLastName();
					birthDate = report.getProperties().getDateOfBirth();
				}
			}
			if(OnfidoStatusEnum.CONSIDER.getOnfidoStatusAsString().equalsIgnoreCase(serviceStatus)
					&& OnfidoStatusEnum.REJECT.getOnfidoStatusAsString().equalsIgnoreCase(serviceSubStatus)
					&& oldContact.isPoiNeeded() && !isAnyContactBlacklisted(oldAccount, oldContact)) {
				return true;
			}
			if ((null != firstName && null != lastName && null != birthDate) && doesContactMatch(oldContact, firstName, lastName, birthDate) && oldContact.isPoiNeeded()
					&& !isAnyContactBlacklisted(oldAccount, oldContact)) {
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("Exception....", e);
		}
		return false;
	}

	/**
	 * @param statusUpdateRequest
	 * @return
	 */
	private RegistrationResponse setAccountContactStatusAndReason(StatusUpdateRequest statusUpdateRequest, Contact oldContact, Account oldAccount, ContactComplianceStatus status, ComplianceReasonCode reason,StatusUpdateResponse response) {
		List<ComplianceContact> contactsList = new ArrayList<>();
		ComplianceContact responseContact = new ComplianceContact();
		responseContact.setContactSFID(statusUpdateRequest.getCrmContactId());
		responseContact.setId(oldContact.getId());
		responseContact.setCcs(status);
		responseContact.setCrc(reason);
		responseContact.setTradeContactID(oldContact.getTradeContactID());
		contactsList.add(responseContact);

		ComplianceAccount acc = new ComplianceAccount();
		acc.setAccountSFID(statusUpdateRequest.getCrmAccountId());
		acc.setAcs(status);
		acc.setArc(reason);
		acc.setTradeAccountID(oldAccount.getTradeAccountID());
		acc.setRegistrationInDate((Timestamp) statusUpdateRequest.getAdditionalAttribute("AccountRegisteredDate"));
		
		setComplianceDoneOnAndExpiryDate(statusUpdateRequest, acc);
		acc.setContacts(contactsList);

		RegistrationResponse updateResponse = new RegistrationResponse();
		updateResponse.setAccount(acc);
		updateResponse.setOrgCode(statusUpdateRequest.getOrgCode());
		updateResponse.setOsrID(statusUpdateRequest.getOsrId());
		response.setAccount(acc);
		response.setOrgCode(statusUpdateRequest.getOrgCode());
		response.setOsrID(statusUpdateRequest.getOsrId());
		return updateResponse;
	}

	/**
	 * @param statusUpdateRequest
	 * @param acc
	 */
	private void setComplianceDoneOnAndExpiryDate(StatusUpdateRequest statusUpdateRequest, ComplianceAccount acc) {
		Timestamp complianceDoneOn = (Timestamp) statusUpdateRequest.getAdditionalAttribute("AccountRegisteredDate");
		if(null != complianceDoneOn){
			acc.setRegisteredDate(complianceDoneOn);
		} else {
			acc.setRegisteredDate(new Timestamp(System.currentTimeMillis()));
		}
		
		Timestamp complianceExpiryDate = (Timestamp) statusUpdateRequest.getAdditionalAttribute("AccountExpiryDate");
		if(null != complianceExpiryDate){
			acc.setExpiryDate(complianceExpiryDate);
		} else{
			Timestamp time = new Timestamp(System.currentTimeMillis());
			Calendar c = Calendar.getInstance();
			c.setTime(time);
			c.add(Calendar.YEAR, PropertyHandler.getComplianceExpiryYears());
			acc.setExpiryDate(new Timestamp(c.getTimeInMillis()));
		}
	}

	/**
	 * @param oldContact
	 * @param firstName
	 * @param lastName
	 * @param inBirthDate
	 * @return
	 * @throws ParseException
	 */
	private boolean doesContactMatch(Contact oldContact, String firstName, String lastName, String inBirthDate)
			throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date birthDate;
		Date oldBirthDate;
		boolean result = false;
		birthDate = dateFormat.parse(inBirthDate);
		oldBirthDate = dateFormat.parse(oldContact.getDob());

		if (firstName.equalsIgnoreCase(oldContact.getFirstName()) 
				&& lastName.equalsIgnoreCase(oldContact.getLastName())
				&& birthDate.equals(oldBirthDate)) {
			result = true;
		}
		return result;
	}

	/**
	 * @param oldAccount
	 * @param oldContact
	 * @return
	 */
	private boolean isAnyContactBlacklisted(Account oldAccount, Contact oldContact) {
		boolean result = false;
		for (Contact contact : oldAccount.getContacts()) {
			if (!contact.getContactSFID().equals(oldContact.getContactSFID())
					&& ServiceStatus.FAIL.name().equals(contact.getPreviousBlacklistStatus())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * Status update decision.
	 *
	 * @param statusUpdateRequest the status update request
	 * @param oldAccount the old account
	 */
	private void statusUpdateDecision(StatusUpdateRequest statusUpdateRequest, Account oldAccount,Contact oldContact, StatusUpdateResponse response, String serviceStatus, String serviceSubStatus) {
		if(null != oldContact) {
			actualStatusDecision(statusUpdateRequest, oldAccount, response, serviceStatus, oldContact,serviceSubStatus);
		}
	}

	/**
	 * @param statusUpdateRequest
	 * @param oldAccount
	 * @param response
	 * @param serviceStatus
	 * @param oldContact
	 */
	private void actualStatusDecision(StatusUpdateRequest statusUpdateRequest, Account oldAccount,
			StatusUpdateResponse response, String serviceStatus, Contact oldContact, String serviceSubStatus) {
		RegistrationResponse updateResponse;
		if(OnfidoStatusEnum.CLEAR.getOnfidoStatusAsString().equalsIgnoreCase(serviceStatus) && otherServicesStatusChecks(oldContact)) {
			updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.ACTIVE,ComplianceReasonCode.PASS,response);
			statusUpdateRequest.addAttribute(Constants.FIELD_BROADCAST_REQUIRED, Boolean.TRUE);
			statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceStatus);
			statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.PASS.name());
			response.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
			response.setResponseDescription(ComplianceReasonCode.PASS.getReasonDescription());
		}
		else if(OnfidoStatusEnum.CONSIDER.getOnfidoStatusAsString().equalsIgnoreCase(serviceStatus)  && otherServicesStatusChecks(oldContact)) {
			if(OnfidoStatusEnum.CAUTION.getOnfidoStatusAsString().equalsIgnoreCase(serviceSubStatus)) {
				updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.INACTIVE,ComplianceReasonCode.ONFIDO_CAUTION,response);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceSubStatus);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.FAIL.name());
				statusUpdateRequest.addAttribute(Constants.STATUS_UPDATE_REASON, Boolean.TRUE);
				response.setResponseCode(ComplianceReasonCode.ONFIDO_CAUTION.getReasonCode());
				response.setResponseDescription(ComplianceReasonCode.ONFIDO_CAUTION.getReasonDescription());
			}
			else if(OnfidoStatusEnum.REJECT.getOnfidoStatusAsString().equalsIgnoreCase(serviceSubStatus)) {
				updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.INACTIVE,ComplianceReasonCode.ONFIDO_REJECT,response);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceSubStatus);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.FAIL.name());
				statusUpdateRequest.addAttribute(Constants.STATUS_UPDATE_REASON, Boolean.TRUE);
				response.setResponseCode(ComplianceReasonCode.ONFIDO_REJECT.getReasonCode());
				response.setResponseDescription(ComplianceReasonCode.ONFIDO_REJECT.getReasonDescription());
			}
			else if(OnfidoStatusEnum.SUSPECT.getOnfidoStatusAsString().equalsIgnoreCase(serviceSubStatus)) {
				updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.INACTIVE,ComplianceReasonCode.ONFIDO_SUSPECT,response);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceSubStatus);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.WATCH_LIST.name());
				statusUpdateRequest.addAttribute(Constants.STATUS_UPDATE_REASON, Boolean.TRUE);
				response.setResponseCode(ComplianceReasonCode.ONFIDO_SUSPECT.getReasonCode());
				response.setResponseDescription(ComplianceReasonCode.ONFIDO_SUSPECT.getReasonDescription());
			}
			else {
				updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.INACTIVE,ComplianceReasonCode.MISSINGINFO,response);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceStatus);
				statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.FAIL.name());
				response.getAccount().setReasonForInactive("Status update failed Onfido Sub-result not found");
				response.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
				response.setResponseDescription(ComplianceReasonCode.MISSINGINFO.getReasonDescription());
			}
		}
		else {
			updateResponse = setAccountContactStatusAndReason(statusUpdateRequest, oldContact, oldAccount,ContactComplianceStatus.INACTIVE,ComplianceReasonCode.MISSINGINFO,response);
			statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT_REASON,serviceStatus);
			statusUpdateRequest.addAttribute(Constants.ONFIDO_RESULT,ServiceStatus.FAIL.name());
			response.getAccount().setReasonForInactive("Other service checks are failed for account");
			response.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
			response.setResponseDescription(ComplianceReasonCode.MISSINGINFO.getReasonDescription());
		}
		statusUpdateRequest.addAttribute(Constants.FIELD_BROADCAST_RESPONSE, updateResponse);
		// remove all contacts and keep only modified one
		oldAccount.getContacts().clear();
		oldAccount.getContacts().add(oldContact);
	}
	
	/**
	 * Other services status checks.
	 *
	 * @param oldContact the old contact
	 * @return true, if successful
	 */
	private boolean otherServicesStatusChecks(Contact oldContact) {
		return (ServiceStatus.PASS.name().equals(oldContact.getPreviousSanctionStatus())
				&& ServiceStatus.PASS.name().equals(oldContact.getPreviousBlacklistStatus())
				&& ServiceStatus.PASS.name().equals(oldContact.getPreviousCountryGlobalCheckStatus())
				&& ServiceStatus.PASS.name().equals(oldContact.getPreviousFraugsterStatus()));
	}
	
	/**
	 * Gets the onfido result.
	 *
	 * @param statusUpdateRequest the status update request
	 * @param onfidoResult the onfido result
	 * @param onfidoSubResult the onfido sub result
	 * @return the onfido result
	 */
	private void getOnfidoResult(StatusUpdateRequest statusUpdateRequest) {
		String onfidoResult = OnfidoStatusEnum.CLEAR.getOnfidoStatusAsString();
		String onfidoSubResult = ServiceStatus.NOT_REQUIRED.name();
		for(OnfidoReport report: statusUpdateRequest.getReports()){
			if(!OnfidoStatusEnum.CLEAR.getOnfidoStatusAsString().equalsIgnoreCase(report.getResult())) {
				onfidoResult = report.getResult();
				onfidoSubResult = report.getSubResult();
				break;
			}
		}
		if(null == onfidoSubResult) {
			onfidoSubResult = ServiceStatus.NOT_REQUIRED.name();
		}
		statusUpdateRequest.addAttribute("onfidoResult", onfidoResult);
		statusUpdateRequest.addAttribute("onfidoSubResult", onfidoSubResult);
	}
}