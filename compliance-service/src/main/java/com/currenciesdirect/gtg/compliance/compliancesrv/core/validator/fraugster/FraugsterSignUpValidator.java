/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.fraugster;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

/**
 * @author manish
 *
 */
public class FraugsterSignUpValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(FraugsterSignUpValidator.class);
	private static IValidator validator = null;
	
	private  FraugsterSignUpValidator() {
		
	}
	
	public static IValidator getInstanse() {
		if(validator==null){
			validator = new FraugsterSignUpValidator();
		}
		return validator;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.fraugster.core.validator.IValidator#validateFraugsterRequest(com.currenciesdirect.gtg.compliance.fraugster.core.domain.IndentityFraudDetectionRequest)
	 */
	@Override
	public Boolean validateRequest(Contact contact) {

		Boolean isvalidate = Boolean.FALSE;

		try {
			Boolean titleFnameLnameDOB = !contact.getTitle().isEmpty() &&!contact.getFirstName().isEmpty()
					&& !contact.getLastName().isEmpty() && !String.valueOf(contact.getDob()).isEmpty();
			Boolean addressCheck = !contact.getAddressType().isEmpty() && !contact.getStreet().isEmpty()
			&& !contact.getCity().isEmpty() && !contact.getCountry().isEmpty();
			isvalidate = validateRequestFields(contact, titleFnameLnameDOB, addressCheck);
		} catch (Exception e) {
			LOGGER.debug("something went wrong",e);
			return Boolean.FALSE;
		}
		
		return isvalidate;
	
	}

	/**
	 * Validate request fields.
	 *
	 * @param contact the contact
	 * @param titleFnameLnameDOB the title fname lname DOB
	 * @param addressCheck the address check
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact contact, Boolean titleFnameLnameDOB,
			Boolean addressCheck) {
		Boolean commDetailsCheck = !contact.getPhoneMobile().isEmpty() && !contact.getEmail().isEmpty()
				&& !String.valueOf(contact.getPrimaryContact()).isEmpty() && !contact.getNationality().isEmpty();
		Boolean personDetailsCheck = titleFnameLnameDOB && !contact.getGender().isEmpty(); 
		return (personDetailsCheck && addressCheck && commDetailsCheck);
	}

}
