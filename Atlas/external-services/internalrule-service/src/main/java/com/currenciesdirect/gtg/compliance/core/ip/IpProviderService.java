/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;

/**
 * The Interface IpProviderService.
 *
 * @author manish
 */
public interface IpProviderService {

	/**
	 * Check ip post code distance.
	 *
	 * @param ipRequest the ip request
	 * @param ipProviderProperty the ip provider property
	 * @return the ip contact response
	 * @throws IpException the ip exception
	 */
	public IpContactResponse checkIpPostCodeDistance(IpServiceRequest ipRequest, IpProviderProperty ipProviderProperty) throws IpException;
	
	
	public IpContactResponse getIpDetails(IpServiceRequest ipRequest, IpProviderProperty ipProviderProperty) throws IpException;
	
}
