/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core;

import java.util.concurrent.ConcurrentHashMap;

import com.currenciesdirect.gtg.compliance.core.domain.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.PostCodeLocation;
import com.currenciesdirect.gtg.compliance.exception.IpException;


/**
 * @author manish
 *
 */
public interface IDBService {
	
	
	public ConcurrentHashMap<String, IpProviderProperty> getIPProviderInitConfigProperty() throws IpException;
	
	public PostCodeLocation getLocationFromPostCode(String postCode) throws IpException;

}
