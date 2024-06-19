/*Copyright Currencies Direct Ltd 2015-2016. All rights reserved
worldwide. Currencies Direct Ltd PROPRIETARY/CONFIDENTIAL.*/
package com.currenciesdirect.gtg.compliance.kyc.core;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;


/**
 * @author Manish
 *
 */
@FunctionalInterface
public interface IKYCProviderService {
 
	/**
	 * @param request
	 * @return 
	 * @throws EnterpriseBaseException
	 */
	public KYCContactResponse checkKYCdetails(KYCContactRequest request,KYCProviderProperty property)throws KYCException;
}
