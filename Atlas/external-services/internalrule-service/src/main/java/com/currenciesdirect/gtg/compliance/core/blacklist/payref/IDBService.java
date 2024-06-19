/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;


/**
 * @author Abhijeet.k
 *
 */
public interface IDBService {
	
	
	public ConcurrentMap<String, BlacklistPayrefProviderProperty> getBlacklistPayrefProviderInitConfigProperty() throws BlacklistException, InternalRuleException;
	

}
