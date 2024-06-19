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
public class ChinaValidator implements IValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(ChinaValidator.class);
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.validators.Ivalidator#validateKYCRequest(com.currenciesdirect.gtg.compliance.domain.KYCProviderRequest)
	 */
	@Override
	public Boolean validateRequest(Contact request)
			{
		try{
			
			if(!request.getLastName().isEmpty()
			    && !request.getPostCode().isEmpty()
				){ 
				return Boolean.TRUE;
			}
			 
		} catch (Exception exception){
			LOGGER.warn("Failed China validation", exception);
			return Boolean.FALSE;
		}
		return Boolean.FALSE;
	}
}
