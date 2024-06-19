package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

import io.swagger.annotations.ApiModelProperty;

public class OnfidoUpdateRequest extends ServiceMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	/** The account id. */
	@ApiModelProperty(value = "The account id", required = true)
	private Integer accountId;

	/** The resource id. */
	@ApiModelProperty(value = "The resource id", required = true)
	private Integer resourceId;

	/** The resource type. */
	@ApiModelProperty(value = "The resource type", required = true)
	private String resourceType;

	/** The org code. */
	@ApiModelProperty(value = "The org code", required = true)
	private String orgCode;
	
	/** The sanctions. */
	@ApiModelProperty(value = "An array of Sanction Update objects", required = true)
	private List<OnfidoUpdateData> onfido;

	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * @return the resourceId
	 */
	public Integer getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceType
	 */
	public String getResourceType() {
		return resourceType;
	}

	/**
	 * @param resourceType the resourceType to set
	 */
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

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
	 * @return the onfido
	 */
	public List<OnfidoUpdateData> getOnfido() {
		return onfido;
	}

	/**
	 * @param onfido the onfido to set
	 */
	public void setOnfido(List<OnfidoUpdateData> onfido) {
		this.onfido = onfido;
	}

}
