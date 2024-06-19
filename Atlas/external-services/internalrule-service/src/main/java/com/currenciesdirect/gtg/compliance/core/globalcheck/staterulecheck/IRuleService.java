package com.currenciesdirect.gtg.compliance.core.globalcheck.staterulecheck;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.GlobalCheckContactResponse;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleRequest;
import com.currenciesdirect.gtg.compliance.exception.globalcheck.GlobalCheckException;

/**
 * The Interface IRuleService.
 */
@FunctionalInterface
public interface IRuleService {

	/**
	 * Apply rule.
	 *
	 * @param requests
	 *            the requests
	 * @return the global check contact response
	 * @throws GlobalCheckException
	 *             the global check exception
	 */
	public GlobalCheckContactResponse applyRule(StateRuleRequest requests) throws GlobalCheckException;

}
