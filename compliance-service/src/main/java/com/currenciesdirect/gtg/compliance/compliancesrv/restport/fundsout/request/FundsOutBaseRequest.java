package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class FundsOutBaseRequest extends ServiceMessage{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("org_code")
	protected String orgCode;
	@JsonProperty("source_application")
	protected String sourceApplication;
	@JsonProperty("cust_type")
	protected String custType;
	@JsonIgnore
	protected Integer orgId;
	@JsonIgnore
	protected Integer fundsOutId;
	private Integer version;
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
	 * @return the custType
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	@JsonProperty("cust_type")
	public void setCustType(String custType) {
		this.custType = custType;
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
	
	/**
	 * @return the fundsOutId
	 */
	public Integer getFundsOutId() {
		return fundsOutId;
	}

	/**
	 * @param fundsOutId the fundsOutId to set
	 */
	public void setFundsOutId(Integer fundsOutId) {
		this.fundsOutId = fundsOutId;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public abstract Integer getPaymentFundsoutId();
	public abstract String getTradeAccountNumber();
	
}
