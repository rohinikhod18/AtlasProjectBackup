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
public class USAValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(USAValidator.class);
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request)
			 {
		Boolean isvalidate = Boolean.FALSE;
		try{
			Boolean nameAndDOBcheck = !request.getFirstName().isEmpty() && !request.getLastName().isEmpty() && !request.getDob().isEmpty();
			Boolean countryAndAddresscheck = !request.getCountry().isEmpty() && !request.getBuildingName().isEmpty() && 
					!request.getStreet().isEmpty() &&  request.getUnitNumber().isEmpty();
			isvalidate = validateRequestFields(request, nameAndDOBcheck, countryAndAddresscheck);
		} catch (Exception exception){
			LOGGER.warn("Failed USA validations", exception);
			isvalidate = Boolean.FALSE;
			return isvalidate;
		}
		
		return isvalidate;
	}
	
	/**
	 * Validate request fields.
	 *
	 * @param request the request
	 * @param nameAndDOBcheck the name and DO bcheck
	 * @param countryAndAddresscheck the country and addresscheck
	 * @return the boolean
	 */
	private Boolean validateRequestFields(Contact request, Boolean nameAndDOBcheck,
			Boolean countryAndAddresscheck) {
		Boolean valid = Boolean.FALSE;
		Boolean cityAndStatecheck = !request.getCity().isEmpty() && !request.getState().isEmpty()
			    && !request.getPostCode().isEmpty() && request.getNationalIdType().isEmpty();
		if( Boolean.TRUE.equals(nameAndDOBcheck) && Boolean.TRUE.equals(countryAndAddresscheck) 
				&& Boolean.TRUE.equals(cityAndStatecheck) ){ 
			valid = Boolean.TRUE;
		}
		return valid;
	}

}
