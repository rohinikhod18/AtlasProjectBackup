package com.currenciesdirect.gtg.compliance.authentication.core;


import com.currenciesdirect.gtg.compliance.authentication.core.domain.AuthRequest;
import com.currenciesdirect.gtg.compliance.authentication.core.domain.User;
import com.currenciesdirect.gtg.compliance.authentication.dbport.DBServiceImpl;
import com.currenciesdirect.gtg.compliance.authentication.exception.AuthenticationException;
import com.currenciesdirect.gtg.compliance.authentication.exception.Errorname;

/**
 * @author manish
 *
 */
public class AuthenticationServiceImpl implements IAuthenticationService{
	
	static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(AuthenticationServiceImpl.class);
	
	IDBService idbService = DBServiceImpl.getInsance();
	
	private static IAuthenticationService authenticationService = null;
	
	private AuthenticationServiceImpl() {
		
	}
	
	public static IAuthenticationService getInstance() {
		if(authenticationService == null) {
			authenticationService = new AuthenticationServiceImpl();
		}
		return authenticationService;
	}
	/**
	 * @param authRequest
	 * @return
	 * @throws AuthenticationException
	 */
	public User getUser(AuthRequest authRequest)
			throws AuthenticationException {
		LOG.info("+++++++++++++++ AuthenticationServiceImpl :getUser method start++++++++++++++");
 
	  User user = new User();
		try {
		
			
			user = idbService.getUserDetails(authRequest);
			
			LOG.info("++++++++++++AuthenticationServiceImpl :getUser method end+++++++++++++++");
			
		} catch (AuthenticationException e) {
			LOG.error("	Error in UserValidation:getUser", e.getException());
			throw e;
		} catch (Exception e) {
			LOG.error("	Error in UserValidation:getUser", e);
			throw new AuthenticationException(Errorname.AUTHENTICATION_GENERIC_EXCEPTION,e);
		} 
		return user;
	}
	

}
