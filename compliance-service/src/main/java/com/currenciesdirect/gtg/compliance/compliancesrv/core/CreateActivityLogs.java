package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.ConversionPrediction;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact.DeleteContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Company;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.CopyObjectUtil;

/**
 * The Class CreateActivityLogs.
 */
public class CreateActivityLogs {

	public static final String RECORD_UPDATED_SUCCESSFULLY = "Record updated successfully";
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CreateActivityLogs.class);

	/**
	 * Adds the upate logs.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> addUpateLogs(Message<MessageContext> message) {

		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest fRequest = (RegistrationServiceRequest) exchange.getRequest();
		RegistrationResponse registrationResponse = (RegistrationResponse) message.getPayload()
				.getGatewayMessageExchange().getResponse();

		try {
			Account account = fRequest.getAccount();
			Account oldAccount = (Account) fRequest.getAdditionalAttribute("oldAccount");

			Boolean isAccountModified = (Boolean) fRequest.getAdditionalAttribute("isAccountModified");
			Boolean isConactModified = (Boolean) fRequest.getAdditionalAttribute("isContactModified");
			Boolean isCompanyModified = (Boolean) fRequest.getAdditionalAttribute("isCompanyModified");
			Boolean isConversionPredictionModified = (Boolean) fRequest
					.getAdditionalAttribute("isConversionPredictionModified");
			Boolean isCorporateComplianceModified = (Boolean) fRequest
					.getAdditionalAttribute("isCorporateComplianceModified");

			String activityLogDetails = null;
			String accountName = account.getAccountName();

			List<String> activityLog = new ArrayList<>();

			if (Boolean.TRUE.equals(isAccountModified) 
					|| Boolean.TRUE.equals(isCompanyModified) 
					|| Boolean.TRUE.equals(isConversionPredictionModified)
					|| Boolean.TRUE.equals(isCorporateComplianceModified)) {
				String fieldsModified;

				if (Boolean.TRUE.equals(isAccountModified)) {
					fieldsModified = CopyObjectUtil.compareFieldsForContentCheckingActivityLog(oldAccount, account);
					activityLogDetails = addActivityLog(registrationResponse, accountName, activityLog, fieldsModified);
				}

				if (Boolean.TRUE.equals(isCompanyModified)) {
					Company oldCompany = oldAccount.getCompany();
					Company company = account.getCompany();
					fieldsModified = CopyObjectUtil.compareFieldsForContentCheckingActivityLog(oldCompany, company);
					activityLogDetails = addActivityLog(registrationResponse, accountName, activityLog, fieldsModified);
				}

				if (Boolean.TRUE.equals(isConversionPredictionModified)) {
					ConversionPrediction oldConversionPrediction = oldAccount.getConversionPrediction();
					ConversionPrediction conversionPrediction = account.getConversionPrediction();

					fieldsModified = CopyObjectUtil.compareFieldsForContentCheckingActivityLog(oldConversionPrediction,
							conversionPrediction);
					activityLogDetails = addActivityLog(registrationResponse, accountName, activityLog, fieldsModified);
				}

				if (Boolean.TRUE.equals(isCorporateComplianceModified)) {
					CorperateCompliance oldConversionPrediction = oldAccount.getCorperateCompliance();
					CorperateCompliance conversionPrediction = account.getCorperateCompliance();
					fieldsModified = CopyObjectUtil.compareFieldsForContentCheckingActivityLog(oldConversionPrediction,
							conversionPrediction);
					activityLogDetails = addActivityLog(registrationResponse, accountName, activityLog, fieldsModified);
				}

			}

			if (Boolean.TRUE.equals(isConactModified)) {

				@SuppressWarnings("unchecked")
				List<Contact> oldContactlist = (List<Contact>) fRequest
						.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
				List<ComplianceContact> complianceContact = registrationResponse.getAccount().getContacts();
				String fieldsModified;

				for (Contact newContacts : fRequest.getAccount().getContacts()) {

					Contact oldContacts = getContactBySFId(newContacts.getContactSFID(), oldContactlist);

					fieldsModified = getModifiedFields(newContacts, oldContacts);

					activityLogDetails = addResponseDescriptionToLog(registrationResponse, complianceContact,
							fieldsModified, newContacts);
				}
				activityLog.add(activityLogDetails);
			}

			fRequest.addAttribute("activityLog", activityLog);
			LOG.warn("\n -------Activity Log details in create activity log -------- \n  {}", activityLog);
		} catch (Exception e) {
			LOG.error("Exception in addUpateLogs()", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	private String addActivityLog(RegistrationResponse registrationResponse, String accountName,
			List<String> activityLog, String fieldsModified) {
		String stpResponseDescription;
		String activityLogDetails;
		if (null == registrationResponse.getAccount().getResponseDescription()) {
			stpResponseDescription = RECORD_UPDATED_SUCCESSFULLY;
		} else {
			stpResponseDescription = registrationResponse.getAccount().getResponseDescription();
		}
		if (RECORD_UPDATED_SUCCESSFULLY.equalsIgnoreCase(stpResponseDescription)) {
			activityLogDetails = "For " + accountName + ":, " + fieldsModified;
		} else {
			activityLogDetails = "For " + accountName + ":, " + fieldsModified + ", " + stpResponseDescription;
		}

		String formatedFields = formatFieldsModified(activityLogDetails);

		activityLog.add(formatedFields);
		return activityLogDetails;
	}

	private String getModifiedFields(Contact newContacts, Contact oldContacts) {
		String modifiedFields = null;

		if (null != oldContacts) {
			modifiedFields = CopyObjectUtil.compareFieldsForContentCheckingActivityLog(oldContacts, newContacts);
		}
		return modifiedFields;
	}

	private String addResponseDescriptionToLog(RegistrationResponse registrationResponse,
			List<ComplianceContact> complianceContact, String fieldsModified, Contact newContacts) {
		String formatedFields;
		String stpResponseDescription;
		String activityLogDetails;
		String contactName = newContacts.getFirstAndLastName();
		if (null == complianceContact.get(0).getResponseDescription()
				&& null == registrationResponse.getAccount().getResponseDescription()) {
			stpResponseDescription = RECORD_UPDATED_SUCCESSFULLY;
		} else if (null != registrationResponse.getAccount().getResponseDescription()) {
			stpResponseDescription = registrationResponse.getAccount().getResponseDescription();
		} else {
			stpResponseDescription = complianceContact.get(0).getResponseDescription();
		}

		if (RECORD_UPDATED_SUCCESSFULLY.equalsIgnoreCase(stpResponseDescription)) {
			activityLogDetails = "For " + contactName + ":, " + fieldsModified;
		} else {
			activityLogDetails = "For " + contactName + ":, " + fieldsModified + ", " + stpResponseDescription;
		}

		formatedFields = formatFieldsModified(activityLogDetails);
		return formatedFields;
	}

	/**
	 * Format fields modified.
	 *
	 * @param fieldsModified
	 *            the fields modified
	 * @return the string
	 */
	@SuppressWarnings("squid:S5361")
	private String formatFieldsModified(String fieldsModified) {
		String replaceComma = fieldsModified.replaceAll(", ", "<br><b>-</b> ");
		String replacebraces = replaceComma.replace("[", "");
		return replacebraces.replace("]", "");
	}

	/**
	 * Gets the contact by SF id.
	 *
	 * @param sfId
	 *            the sf id
	 * @param contacts
	 *            the contacts
	 * @return the contact by SF id
	 */
	private Contact getContactBySFId(String sfId, List<Contact> contacts) {
		for (Contact c : contacts) {
			if (c.getContactSFID().equalsIgnoreCase(sfId))
				return c;
		}
		return null;
	}
	
	/**
	 * Adds the delete contact logs.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> addContactDeleteLogs(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		DeleteContactRequest deleteContactRequest = (DeleteContactRequest) exchange.getRequest();
		RegistrationServiceRequest regServiceRequest = (RegistrationServiceRequest) deleteContactRequest.getAdditionalAttribute("regRequest");
		Account acc = regServiceRequest.getAccount();
		
		if(!CustomerTypeEnum.PFX.name().equalsIgnoreCase(acc.getCustType())) {
			List<Contact> contacts = acc.getContacts();
			Contact contact = contacts.get(0);
			List<String> activityLog = new ArrayList<>();
			String activityLogDetails;
			String contactName = contact.getFirstName()+" "+contact.getMiddleName()+" "+contact.getLastName();
			activityLogDetails = "Contact "+contactName+" has been deleted";
			activityLog.add(activityLogDetails);
			deleteContactRequest.addAttribute("activityLog", activityLog);
		}
		return message;
	}
}
