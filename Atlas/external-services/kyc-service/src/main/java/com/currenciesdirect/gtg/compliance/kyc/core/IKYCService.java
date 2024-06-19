/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;


/**
 * The Interface IKYCService.
 *
 * @author manish
 */
@FunctionalInterface
public interface IKYCService {
	
	/**
	 * Check KYC.
	 *
	 * @param request the request
	 * @return the KYC provider response
	 * @throws KYCException the KYC exception
	 */
	public KYCProviderResponse checkKYC(KYCProviderRequest request) throws KYCException;

}
