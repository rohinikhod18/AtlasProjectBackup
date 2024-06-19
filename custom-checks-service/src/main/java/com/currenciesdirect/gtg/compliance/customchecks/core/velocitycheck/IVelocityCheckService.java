package com.currenciesdirect.gtg.compliance.customchecks.core.velocitycheck;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

public interface IVelocityCheckService {
	/**
	 * @param request
	 * @return
	 * @throws CustomChecksException
	 */
	public WhiteListCheckResponse performCheck(CustomChecksRequest request)
			throws CustomChecksException;
}
