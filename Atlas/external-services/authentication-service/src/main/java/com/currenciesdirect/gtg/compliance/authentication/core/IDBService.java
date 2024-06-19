/**
 * 
 */
package com.currenciesdirect.gtg.compliance.authentication.core;

import com.currenciesdirect.gtg.compliance.authentication.core.domain.AuthRequest;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.User;
import com.currenciesdirect.gtg.compliance.authentication.exception.AuthenticationException;

/**
 * @author manish
 *
 */
public interface IDBService {
	
	
	public User getUserDetails(AuthRequest authRequest) throws AuthenticationException;


}
