package com.currenciesdirect.gtg.compliance.compliancesrv.util;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.AddContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.SignUpRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.UpdateAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.UpdateContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;

/**
 * The Class ParseSSNNumber.
 */
public class ParseSSNNumber {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ParseSSNNumber.class);
	
	private static final String ERROR_WHILE_PARSING_SSN_FIELDS = "Error while parsing ssn fields";
		
	/** The parse ssn number. */
	private static ParseSSNNumber parseSsnNumber = null;
	
	/**
	 * Instantiates a new parses the SSN number.
	 */
	private ParseSSNNumber() {}

	/**
	 * Gets the single instance of ParseSSNNumber.
	 *
	 * @return single instance of ParseSSNNumber
	 */
	public static ParseSSNNumber getInstance() {
		if(null == parseSsnNumber) {
			parseSsnNumber = new ParseSSNNumber();
		}
		return parseSsnNumber;
	}
	
	/**
	 * Parses the ssn number.
	 *
	 * @param ssnNumber the ssn number
	 * @return the parses the SSN number
	 */
	public ParseSSNNumber parseSsnNumber(RegistrationServiceRequest serviceRequest) {
		ParseSSNNumber parseSSNNumber = new ParseSSNNumber();
		try {
			Account account = serviceRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			if (null != contacts && !contacts.isEmpty()) {
				for (Contact contact : contacts) {
					getLastFourDigitOfSsn(contact);
				}
			}
		} catch (Exception e) {
			LOGGER.debug(ERROR_WHILE_PARSING_SSN_FIELDS, e);
		}
		return parseSSNNumber;
	}
	
	/**
	 * Parses the ssn number.
	 *
	 * @param ssnNumber the ssn number
	 * @return the parses the SSN number
	 */
	public ParseSSNNumber parseSsnNumber(SignUpRequest serviceRequest) {
		ParseSSNNumber parseSSNNumber = new ParseSSNNumber();
		try {
			com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Account account = serviceRequest.getAccount();
			List<com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact> contacts = account.getContacts();
			if (null != contacts && !contacts.isEmpty()) {
				for (com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact contact : contacts) {
					contact.setSsn(getFormatedSSNNumber(contact.getSsn()));
				
				}
			}
		} catch (Exception e) {
			LOGGER.debug(ERROR_WHILE_PARSING_SSN_FIELDS, e);
		}
		return parseSSNNumber;
	}
	
	/**
	 * Parses the ssn number.
	 *
	 * @param ssnNumber the ssn number
	 * @return the parses the SSN number
	 */
	public ParseSSNNumber parseSsnNumberForAddContact(AddContactRequest serviceRequest) {
		ParseSSNNumber parseSSNNumber = new ParseSSNNumber();
		try {
			List<com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact> contacts = serviceRequest.getContacts();
			if (null != contacts && !contacts.isEmpty()) {
				for (com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact contact : contacts) {
					contact.setSsn(getFormatedSSNNumber(contact.getSsn()));
				}
			}
		} catch (Exception e) {
			LOGGER.debug(ERROR_WHILE_PARSING_SSN_FIELDS, e);
		}
		return parseSSNNumber;
	}
	
	/**
	 * Parses the ssn number.
	 *
	 * @param ssnNumber the ssn number
	 * @return the parses the SSN number
	 */
	public ParseSSNNumber parseSsnNumberForUpdate(UpdateAccount serviceRequest) {
		ParseSSNNumber parseSSNNumber = new ParseSSNNumber();
		try {
			List<UpdateContact> contacts = serviceRequest.getContacts();
			if (null != contacts && !contacts.isEmpty()) {
				for (UpdateContact contact : contacts) {
					contact.setSsn(getFormatedSSNNumber(contact.getSsn()));
				}
			}
		} catch (Exception e) {
			LOGGER.debug(ERROR_WHILE_PARSING_SSN_FIELDS, e);
		}
		return parseSSNNumber;
	}
	
	/**
	 * Gets the last four digit of ssn.
	 *
	 * @param contact the contact
	 * @return the last four digit of ssn
	 */
	private void getLastFourDigitOfSsn(Contact contact) {
		String ssn = contact.getSsn();
		String ssnLastFourNumbers = "";
		if(null != ssn && !StringUtils.isNullOrTrimEmpty(ssn) && ssn.length() > 4) {
			ssnLastFourNumbers = ssn.substring(ssn.length() - 4);
			contact.setSsn(ssnLastFourNumbers);
		}
	}
	
	
	private String getFormatedSSNNumber(String originalSSN) {
		String formatedSSNNumber = originalSSN;
		if (null != originalSSN && !StringUtils.isNullOrTrimEmpty(originalSSN) && originalSSN.length() == 9) {
			formatedSSNNumber = originalSSN.substring(0, 3) + '-' + originalSSN.substring(3, 5) + '-'
					+ originalSSN.substring(5, 9);
		}

		return formatedSSNNumber;
	}
}
		

