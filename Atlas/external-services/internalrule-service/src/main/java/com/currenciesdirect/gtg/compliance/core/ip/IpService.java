/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;

/**
 * The Interface IpService.
 *
 * @author manish
 */
@FunctionalInterface
public interface IpService {

	/**
	 * Check ip post code distance.
	 *
	 * @param request
	 *            the request
	 * @return the ip contact response
	 * @throws IpException
	 *             the ip exception
	 */
	public IpContactResponse checkIpPostCodeDistance(IpServiceRequest request) throws IpException;
}
