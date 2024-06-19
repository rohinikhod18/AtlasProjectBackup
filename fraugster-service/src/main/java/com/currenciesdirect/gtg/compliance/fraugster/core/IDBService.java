/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core;

import java.util.concurrent.ConcurrentMap;

import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;


/**
 * @author manish
 *
 */
public interface IDBService {
	
	
	public ConcurrentMap<String, FraugsterProviderProperty> getFraugsterProviderInitConfigProperty() throws FraugsterException;
	

}
