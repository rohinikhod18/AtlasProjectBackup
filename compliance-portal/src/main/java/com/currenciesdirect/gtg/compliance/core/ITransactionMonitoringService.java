package com.currenciesdirect.gtg.compliance.core;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

public interface ITransactionMonitoringService {
	
	/**
	 * Sync registration record with intuition.
	 *
	 * @param user the user
	 * @param request the request
	 * @return the base repeat check response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public BaseResponse syncRegistrationRecordWithIntuition(UserProfile user, List<String> request)
			throws CompliancePortalException;

}
