/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.externalservice.enterpriseport;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;

/**
 * The Interface EmailAPI.
 *
 * @author vishalj
 */
public interface IEmailClient {
	
	/**
	 * Send email on payment rejection.
	 *
	 * @param PaymentUpdateRequest the payment update request
	 */
	public void sendEmail(Address oldAddress, Address newAddress, RegistrationServiceRequest request,ICommHubServiceImpl iCommHubServiceImpl);
}
