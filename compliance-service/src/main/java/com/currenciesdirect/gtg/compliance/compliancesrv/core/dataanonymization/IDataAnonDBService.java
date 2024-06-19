package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;

public interface IDataAnonDBService {
	
	/**
	 * Gets the account contact details.
	 *
	 * @param request the request
	 * @return the account contact details
	 * @throws ComplianceException the compliance exception
	 */
	public DataAnonRequestFromES getAccountContactDetails(DataAnonRequestFromES request) throws ComplianceException;
	
	/**
	 * Check data anon request is in process queue.
	 *
	 * @param request the request
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	public boolean checkDataAnonRequestIsInProcessQueue(DataAnonRequestFromES request) throws ComplianceException;
	
	/**
	 * Process data anonymization.
	 *
	 * @param request the request
	 * @param oldAccount 
	 * @return true, if successful
	 * @throws ComplianceException the compliance exception
	 */
	public boolean processDataAnonymization(DataAnonRequestFromES request, Account oldAccount) throws ComplianceException;

	/**
	 * Save data anon request.
	 *
	 * @param request the request
	 * @param userProfile the user profile
	 * @throws ComplianceException the compliance exception
	 */
	public void saveDataAnonRequest(DataAnonRequestFromES request, UserProfile userProfile) throws ComplianceException;

}
