/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.ip;

import java.util.Map;

import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.core.domain.ip.PostCodeLocation;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.ip.IpException;



/**
 * The Interface IDBService.
 *
 * @author manish
 */
public interface IDBService {
	
	
	/**
	 * Gets the IP provider init config property.
	 *
	 * @return the IP provider init config property
	 * @throws IpException the ip exception
	 * @throws InternalRuleException 
	 */
	public Map<String, IpProviderProperty> getIPProviderInitConfigProperty() throws InternalRuleException;
	
	/**
	 * Gets the location from post code.
	 *
	 * @param postCode the post code
	 * @return the location from post code
	 * @throws IpException the ip exception
	 */
	public PostCodeLocation getLocationFromPostCode(String postCode) throws InternalRuleException;

}
