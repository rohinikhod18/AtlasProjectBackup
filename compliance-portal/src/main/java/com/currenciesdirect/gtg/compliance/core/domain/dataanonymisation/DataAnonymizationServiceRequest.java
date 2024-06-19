package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonymizationServiceRequest {
	
	@ApiModelProperty(value = "The CD organisation code where the call originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty(value="org_code")
	private String orgCode;
	
	@JsonProperty(value="account")
	private DataAnonAccount account;
	
	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public DataAnonAccount getAccount() {
		return account;
	}

	public void setAccount(DataAnonAccount account) {
		this.account = account;
	}


}
