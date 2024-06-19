package com.currenciesdirect.gtg.compliance.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.dbport.RegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.dbport.cfx.RegistrationCfxDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * A factory for creating RegistrationDetails objects.
 * @author abhijeetg
 */
@Component("registrationDetailsFactory")
public class RegistrationDetailsFactory {

	/** The registration cfx DB service impl. */
	@Autowired
	@Qualifier("regCfxDBServiceImpl")
	private RegistrationCfxDBServiceImpl registrationCfxDBServiceImpl;

	/** The registration DB service impl. */
	@Autowired
	@Qualifier("regDBServiceImpl")
	private RegistrationDBServiceImpl registrationDBServiceImpl;

	/**
	 * Gets the registration details factory.
	 *
	 * @param custType the cust type
	 * @return the registration details factory
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public IRegistrationDBService getRegistrationDetailsFactory(String custType) throws CompliancePortalException {

		try {

			if (("CFX").equalsIgnoreCase(custType.toUpperCase().trim()) || ("CFX (etailer)").equalsIgnoreCase(custType.toUpperCase().trim())) {
				return registrationCfxDBServiceImpl;
			} else if (("PFX").equalsIgnoreCase(custType.toUpperCase().trim())) {
				return registrationDBServiceImpl;
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST, e);
		}

		return null;
	}

}
