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
public class JapanValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(JapanValidator.class);
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request)
			 {
		Boolean isValid;
		Boolean nameDOBGendercheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty()
			    && !request.getDob().isEmpty() && !request.getGender().isEmpty();
		Boolean addressCheck = !request.getCountry().isEmpty() && !request.getBuildingName().isEmpty()
			    && !request.getStreet().isEmpty() && !request.getCity().isEmpty(); //map with aza
		isValid = validateRequestFields(request, nameDOBGendercheck, addressCheck);
		
		return isValid;
	}
	
	/**
	 * Validate request fields.
	 *
	 * @param request the request
	 * @param nameDOBGendercheck the name DOB gendercheck
	 * @param addressCheck the address check
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean nameDOBGendercheck,
			Boolean addressCheck) {
		Boolean statePostcodeUnitCheck = !request.getState().isEmpty() && !request.getPostCode().isEmpty()
			    && !request.getUnitNumber().isEmpty();
		try{
			if(Boolean.TRUE.equals(nameDOBGendercheck) && Boolean.TRUE.equals(addressCheck) 
					&& Boolean.TRUE.equals(statePostcodeUnitCheck)) { 
				return Boolean.TRUE;
			}
		} catch (Exception exception){
			LOGGER.warn("Failed Japan validation",exception);
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
}
