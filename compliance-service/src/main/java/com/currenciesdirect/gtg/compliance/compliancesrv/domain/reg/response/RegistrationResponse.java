package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationResponse extends BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	

	/** The org code. */
	@ApiModelProperty(value = "The organisation code", dataType = "java.lang.String", required = true)
	private String orgCode;

	/** The account. */
	@ApiModelProperty(value = "A Compliance Account", required = true)
	private ComplianceAccount account;
	
	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public ComplianceAccount getAccount() {
		return account;
	}
	public void setAccount(ComplianceAccount account) {
		this.account = account;
	}
	@Override
	public String toString() {
		return "RegistrationResponse [orgCode=" + orgCode + ", account=" + account + "]";
	}
	

}