/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.core;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

/**
 * The Interface IFraugsterService.
 *
 * @author manish
 */
public interface IFraugsterService {
	
	/**
	 * Do fraugster check for new sign up.
	 *
	 * @param request the request
	 * @return the fraugster signup response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterSignupResponse doFraugsterCheckForNewSignUp(FraugsterSignupRequest request)throws FraugsterException;
	
	/**
	 * Do fraugster check for on update.
	 *
	 * @param request the request
	 * @return the fraugster on update response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterOnUpdateResponse doFraugsterCheckForOnUpdate(FraugsterOnUpdateRequest request)throws FraugsterException;
	
	/**
	 * Do fraugster check for payments out.
	 *
	 * @param request the request
	 * @return the fraugster payments out response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterPaymentsOutResponse doFraugsterCheckForPaymentsOut(FraugsterPaymentsOutRequest request)throws FraugsterException;
	
	/**
	 * Do fraugster check for payments in.
	 *
	 * @param request the request
	 * @return the fraugster payments in response
	 * @throws FraugsterException the fraugster exception
	 */
	public FraugsterPaymentsInResponse doFraugsterCheckForPaymentsIn(FraugsterPaymentsInRequest request) throws FraugsterException;

}
