package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class StatusUpdateResponse extends BaseResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;
	
	/** The account. */
	@ApiModelProperty(value = "A Compliance Account", required = true)
	private ComplianceAccount account;
	
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the account
	 */
	public ComplianceAccount getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(ComplianceAccount account) {
		this.account = account;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StatusUpdateResponse [orgCode=" + orgCode +"]";
	}
}