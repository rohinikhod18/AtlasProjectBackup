package com.currenciesdirect.gtg.compliance.customchecks.domain.request;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class SearchWhiteList.
 */
public class SearchWhiteListRequest extends IRequest implements IDomain{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The org code. */
	@JsonProperty("orgCode")
	private String orgCode;
	
	/** The acc id. */
	@JsonProperty("accId")
	private Integer accId;

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
	 * @return the accId
	 */
	public Integer getAccId() {
		return accId;
	}

	/**
	 * @param accId the accId to set
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	
	
}
