package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface IFraudRingService {
	
	public FraudRingResponse getFraudRingGraphData(FraudRingRequest fraudRingGraphRequest) throws CompliancePortalException;

	public Boolean checkIsFraudRingPresent(FraudRingRequest fraudRingGraphRequest) throws CompliancePortalException;
	
	public FraudRingNode getNodeDetails(FraudRingRequest fraudRingGraphRequest) throws CompliancePortalException;
}
