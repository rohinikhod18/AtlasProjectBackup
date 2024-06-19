package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.socialdata.SocialDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.socialdata.SocialDataResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface ISocialDataService {

	public SocialDataResponse getSocialData(SocialDataRequest socialDataRequest) throws CompliancePortalException;
}
