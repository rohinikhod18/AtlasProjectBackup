package com.currenciesdirect.gtg.compliance.customchecks.core;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Interface IPaymentInCustomCheckService.
 */
public interface IPaymentInCustomCheckService {

	/**
	 * Perform funds in velocity and whilte list checks.
	 *
	 * @param document the document
	 * @return the custom check response
	 * @throws CustomChecksException the custom checks exception
	 */
	public CustomCheckResponse performFundsInVelocityAndWhilteListChecks(CustomChecksRequest document)
			throws CustomChecksException;

}
