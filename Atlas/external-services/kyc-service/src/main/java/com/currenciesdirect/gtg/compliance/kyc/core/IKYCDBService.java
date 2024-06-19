/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.core;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;


/**
 * The Interface IKYCDBService.
 *
 * @author manish
 */
public interface IKYCDBService {

	/**
	 * Fetch KYC init data.
	 *
	 * @return the concurrent map
	 * @throws KYCException the KYC exception
	 */
	public ConcurrentMap<String, List<String>> fetchKYCInitData()
			throws KYCException;
	
	 
	 /**
 	 * Gets the KYC providerinit config properties.
 	 *
 	 * @return the KYC providerinit config properties
 	 * @throws KYCException the KYC exception
 	 */
 	public ConcurrentMap<String, KYCProviderProperty> getKYCProviderinitConfigProperties() throws KYCException;


	/**
	 * Gets the country short code.
	 *
	 * @param country the country
	 * @return the country short code
	 * @throws KYCException the KYC exception
	 */
	public String getCountryShortCode(String country) throws KYCException;
}
