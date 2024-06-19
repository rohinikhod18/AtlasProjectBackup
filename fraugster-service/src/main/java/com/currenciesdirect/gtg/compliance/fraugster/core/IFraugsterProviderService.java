package com.currenciesdirect.gtg.compliance.fraugster.core;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterSessionToken;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

/**
 * The Interface IFraugsterProviderService.
 * @author abhijitg
 */
public interface IFraugsterProviderService {
	
	/**
	 * Do fraugster check.
	 *
	 * @param request the request
	 * @return the fraugster provider response
	 */
	public FraugsterSignupContactResponse doFraugsterSignupCheck(FraugsterSignupContactRequest request,FraugsterProviderProperty fraugsterProviderProperty,FraugsterSessionToken sessionToken) throws FraugsterException;
	
	public FraugsterOnUpdateContactResponse doFraugsterOnUpdateCheck(FraugsterOnUpdateContactRequest request,FraugsterProviderProperty fraugsterProviderProperty,FraugsterSessionToken sessionToken) throws FraugsterException;
	
	public FraugsterSessionToken doLogin(FraugsterProviderProperty fraugsterProviderProperty) throws FraugsterException;
	
	public Boolean doLogout(FraugsterProviderProperty fraugsterProviderProperty,FraugsterSessionToken sessionToken) throws FraugsterException;
	
	public FraugsterPaymentsOutContactResponse doFraugsterPaymentsOutCheck(FraugsterPaymentsOutContactRequest request,FraugsterProviderProperty fraugsterProviderProperty,FraugsterSessionToken sessionToken) throws FraugsterException;
	
	public FraugsterPaymentsInContactResponse doFraugsterPaymentsInCheck(FraugsterPaymentsInContactRequest request,FraugsterProviderProperty fraugsterProviderProperty,FraugsterSessionToken sessionToken) throws FraugsterException;


}
