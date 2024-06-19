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
public class SpainValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SpainValidator.class);
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request)
			 {
		Boolean isvalidate = Boolean.FALSE;
		try{
			boolean isNameValid = !request.getFirstName().isEmpty()
						    && !request.getLastName().isEmpty()
						    && !request.getSecondSurname().isEmpty();
			if(isNameValid
			    && !request.getDob().isEmpty()
			    && !request.getNationalIdType().isEmpty()
				){ 
				isvalidate = Boolean.TRUE;
			}
		} catch (Exception exception){
			LOGGER.warn("Failed Spain validations", exception);
			return Boolean.FALSE;
		}
		
		return 	isvalidate;
	}

}
