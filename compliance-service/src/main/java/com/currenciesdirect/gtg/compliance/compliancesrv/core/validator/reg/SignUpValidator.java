/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.reg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

/**
 * The Class SignUpValidator.
 *
 * @author manish
 */
public class SignUpValidator implements IValidator {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SignUpValidator.class);

	/** The validator. */
	private static IValidator validator = null;

	/**
	 * Instantiates a new sign up validator.
	 */
	private SignUpValidator() {

	}

	/**
	 * Gets the instanse.
	 *
	 * @return the instanse
	 */
	public static IValidator getInstanse() {
		if (validator == null) {
			validator = new SignUpValidator();
		}
		return validator;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator#
	 * validateRequest(com.currenciesdirect.gtg.compliance.compliancesrv.domain.
	 * reg.Contact)
	 */
	@Override
	public Boolean validateRequest(Contact contact) {
		Boolean isValid = Boolean.FALSE;
		try {
			Boolean fnameLnameDOBNationality = !contact.getFirstName().isEmpty() && !contact.getLastName().isEmpty()
					&& !contact.getDob().isEmpty() && !contact.getNationality().isEmpty();
			isValid = validateRequestFields(contact, fnameLnameDOBNationality);
		} catch (Exception e) {
			LOGGER.error("Error in validator: {1}", e);
			return Boolean.FALSE;
		}
		return isValid;
	}

	/**
	 * Validate request fields.
	 *
	 * @param contact
	 *            the contact
	 * @param fnameLnameDOBNationality
	 *            the fname lname DOB nationality
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact contact, Boolean fnameLnameDOBNationality) {
		Boolean address = !contact.getCountry().isEmpty() && !contact.getPostCode().isEmpty()
				&& !contact.getPhoneMobile().isEmpty();
		return (fnameLnameDOBNationality && address);
	}

}
