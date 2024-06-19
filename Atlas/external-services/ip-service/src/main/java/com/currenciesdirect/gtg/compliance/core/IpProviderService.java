/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.core.domain.CustomerLocation;
import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.IpRequestData;
import com.currenciesdirect.gtg.compliance.exception.IpException;

/**
 * @author manish
 *
 */
public interface IpProviderService {

	public CustomerLocation getCustomerLocationFromIp(IpRequestData ipRequest, IpProviderProperty ipProviderProperty) throws IpException;
	
}
