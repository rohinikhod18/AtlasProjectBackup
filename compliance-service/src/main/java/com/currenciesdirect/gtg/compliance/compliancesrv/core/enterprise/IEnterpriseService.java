package com.currenciesdirect.gtg.compliance.compliancesrv.core.enterprise;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.enterprise.EnterpriseIPAddressDetails;

//AT-4745
public interface IEnterpriseService {
	public EnterpriseIPAddressDetails getIPAddressDetails(String iPAddress)
			throws ComplianceException;
}
