package com.currenciesdirect.gtg.compliance.core;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;

/**
 * The Interface IServiceTransformer.
 */
public interface IServiceTransformer {

	/**
	 * Transform to blacklist STP request.
	 *
	 * @param requestData
	 *            the request data
	 * @param accID
	 *            the acc ID
	 * @return the blacklist STP request
	 */
	public BlacklistSTPRequest transformToBlacklistSTPRequest(InternalServiceRequestData requestData);

	/**
	 * Transform to rule request.
	 *
	 * @param requestData
	 *            the request data
	 * @param orgCode
	 *            the org code
	 * @return the state rule request
	 */
	public StateRuleRequest transformToRuleRequest(InternalServiceRequestData requestData, String orgCode);

	/**
	 * Transform tp ip request.
	 *
	 * @param requestData
	 *            the request data
	 * @param correlationId
	 *            the correlation id
	 * @return the ip service request
	 */
	public IpServiceRequest transformTpIpRequest(InternalServiceRequestData requestData);
	
	
	/**
	 * Transform blacklist STP response.
	 *
	 * @param requestData the request data
	 * @param blackListContactResponse the black list contact response
	 * @return the blacklist contact response
	 */
	public BlacklistContactResponse transformBlacklistSTPResponse(InternalServiceRequestData requestData,BlacklistContactResponse blackListContactResponse);

}
