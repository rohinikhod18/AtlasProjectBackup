/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.validator.sanction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.IValidator;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;

/**
 * @author manish
 *
 */
public class SanctionValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionValidator.class);
	private static IValidator instance = null;
	
	 private SanctionValidator() {
		
	}
	 
	 public static IValidator getInstance() {
		 if(instance==null){
			 instance = new SanctionValidator();
		 }
		 return instance;
	 }
	  
	
	@Override
	public Boolean validateRequest(Contact request) {
		Boolean isValidate = Boolean.FALSE;

		try {
					if (!request.getCountry().isEmpty()
							&& !request.getDob().isEmpty()
							&& !request.getFirstName().isEmpty()
							&& !request.getLastName().isEmpty()){
						isValidate = Boolean.TRUE;
					}
		} catch (Exception e) {
			LOGGER.warn("Failed South Sanction validations", e);
			return Boolean.FALSE;
		}
		return isValidate;

	}
}
