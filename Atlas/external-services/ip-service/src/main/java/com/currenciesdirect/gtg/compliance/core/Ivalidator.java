/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.IpServiceRequest;
import com.currenciesdirect.gtg.compliance.exception.IpException;

/**
 * @author manish
 *
 */
public interface Ivalidator {
	
	public Boolean validateIpRequest(IpServiceRequest ipRequest) throws IpException;

}
