/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.IpServiceResponse;
import com.currenciesdirect.gtg.compliance.exception.IpException;

/**
 * @author manish
 *
 */
public interface IpService {

	
	public IpServiceResponse checkIpPostCodeDistance(IpServiceRequest request) throws IpException;
}
