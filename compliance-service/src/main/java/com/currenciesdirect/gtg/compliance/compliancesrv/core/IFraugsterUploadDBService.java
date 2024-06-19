package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.List;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;

public interface IFraugsterUploadDBService {

	public void updateFraugsterDataTable(List<Integer> fraugsetrId) throws ComplianceException;

	public String getFraugster(List<Integer> idlist) throws ComplianceException;
	
	

}
