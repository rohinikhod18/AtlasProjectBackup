package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class FundsOutBaseRequest {


	@JsonProperty("org_code")
	protected String orgCode;
	@JsonProperty("source_application")
	protected String sourceApplication;
	@JsonIgnore
	protected Integer orgId;
	@JsonIgnore
	protected Integer fundsOutId;
	
	/**
	 * 
	 * @return The orgCode
	 */
	@JsonProperty("org_code")
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 
	 * @param orgCode
	 *            The org_code
	 */
	@JsonProperty("org_code")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 
	 * @return The sourceApplication
	 */
	@JsonProperty("source_application")
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * 
	 * @param sourceApplication
	 *            The source_application
	 */
	@JsonProperty("source_application")
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}
	
	/**
	 * @return
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}
	public Integer getFundsOutId() {
		return fundsOutId;
	}

	public void setFundsOutId(Integer fundsOutId) {
		this.fundsOutId = fundsOutId;
	}
	
	public abstract Integer getPaymentFundsoutId();
}
