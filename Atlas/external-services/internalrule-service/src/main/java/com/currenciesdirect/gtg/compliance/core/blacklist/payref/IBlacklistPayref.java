package com.currenciesdirect.gtg.compliance.core.blacklist.payref;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.core.blacklist.payrefport.BlacklistPayrefProviderProperty;
import com.currenciesdirect.gtg.compliance.exception.BlacklistPayrefException;


/**
 * The Interface IBlacklistPayref.
 */
public interface IBlacklistPayref
{
	
	/**
	 * Do pay ref payments out check.
	 *
	 * @param request the request
	 * @param blacklistPayrefProviderProperty the blacklist payref provider property
	 * @return the blacklist payref contact response
	 * @throws Exception the exception
	 */
	public BlacklistPayrefContactResponse doPayRefPaymentsOutCheck(InternalServiceRequestData request, BlacklistPayrefProviderProperty blacklistPayrefProviderProperty)throws BlacklistPayrefException;
}
