package com.currenciesdirect.gtg.compliance.core.domain.sanction;

import java.util.List;

public class SanctionUpdateRequest {
	
	private Integer accountId;
	
	private String orgCode;
	
	private List<SanctionUpdateData> sanctions;
	
	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public List<SanctionUpdateData> getSanctions() {
		return sanctions;
	}

	public void setSanctions(List<SanctionUpdateData> sanctions) {
		this.sanctions = sanctions;
	}

}
