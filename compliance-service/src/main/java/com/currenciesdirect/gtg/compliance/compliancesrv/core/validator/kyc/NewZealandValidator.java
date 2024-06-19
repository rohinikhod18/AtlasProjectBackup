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
public class NewZealandValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(NewZealandValidator.class);
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request)
			 {
		Boolean valid;
		Boolean nameDOBGendercheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
			    && !request.getDob().isEmpty() && !request.getGender().isEmpty();
		Boolean addressCheck = !request.getCountry().isEmpty() && !request.getStreet().isEmpty() 
				&& !request.getCity().isEmpty() && !request.getState().isEmpty();
		Boolean postcodeAndDLVersionCheck = !request.getPostCode().isEmpty() && request.getDlVersionNumber().isEmpty() 
				&& request.getDlLicenseNumber().isEmpty();
		valid = validateRequestFields(nameDOBGendercheck, addressCheck, postcodeAndDLVersionCheck);
		
		return valid;
	}
	
	/**
	 * Validate request fields.
	 *
	 * @param nameDOBGendercheck the name DOB gendercheck
	 * @param addressCheck the address check
	 * @param postcodeAndDLVersionCheck the postcode and DL version check
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Boolean nameDOBGendercheck, Boolean addressCheck,
			Boolean postcodeAndDLVersionCheck) {
		
		try{			 
			if(Boolean.TRUE.equals(nameDOBGendercheck) && Boolean.TRUE.equals(addressCheck) 
					&& Boolean.TRUE.equals(postcodeAndDLVersionCheck)){ 
				return  Boolean.TRUE;
			}
		} catch (Exception exception){
			LOGGER.warn("Failed NewZealand Validations", exception);
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
}
