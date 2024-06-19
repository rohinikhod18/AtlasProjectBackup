/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.kyc;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

/**
 * @author manish
 *
 */
public class SouthAfricaValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SouthAfricaValidator.class);
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request)
			{
		Boolean isValidate;
		Boolean nameAndDOBcheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
			    && !request.getDob().isEmpty();
		Boolean addressCheck = !request.getCivicNumber().isEmpty() && !request.getCountry().isEmpty()
			    && !request.getStreet().isEmpty() && !request.getCity().isEmpty();
		isValidate = validateRequestFields(request, nameAndDOBcheck, addressCheck);
		
		return 	isValidate;
	}
	
	/**
	 * Validate request fields.
	 *
	 * @param request the request
	 * @param isValidate the is validate
	 * @param nameAndDOBcheck the name and DO bcheck
	 * @param addressCheck the address check
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean nameAndDOBcheck,
			Boolean addressCheck) {
		Boolean valid = Boolean.FALSE;
		Boolean postcodeNatIDHomePhCheck = !request.getState().isEmpty() && !request.getPostCode().isEmpty() 
				&& request.getNationalIdNumber().isEmpty() && request.getPhoneHome().isEmpty();
		try{
			if(Boolean.TRUE.equals(nameAndDOBcheck) && Boolean.TRUE.equals(addressCheck) 
					&& Boolean.TRUE.equals(postcodeNatIDHomePhCheck)){ 
				valid = Boolean.TRUE;
			}
		} catch (Exception exception){
			LOGGER.warn("Failed South Africa validations", exception);
			return Boolean.FALSE;
		}
		return valid;
	}

}
