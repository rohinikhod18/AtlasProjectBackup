package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring.FraudRingNode;
import com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring.FraudRingRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface IFraudRingDBService {
	
	public FraudRingNode getClientData(FraudRingRequest fraudRingGraphRequest) throws CompliancePortalException;
}
