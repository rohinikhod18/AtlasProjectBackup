package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonRequestFromES extends ServiceMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The new reference id. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("new_reference_id")
	private Long newReferenceId;
	
	/** The org code. */
	@ApiModelProperty(value = "The organization code", required = true)
	@JsonProperty("org_code")
	private String orgCode;
	
	/** The account. */
	@ApiModelProperty(value = "The account", required = true)
	@JsonProperty("account")
	private DataAnonAccount account;

	/**
	 * @return the newReferenceId
	 */
	public Long getNewReferenceId() {
		return newReferenceId;
	}

	/**
	 * @param newReferenceId the newReferenceId to set
	 */
	public void setNewReferenceId(Long newReferenceId) {
		this.newReferenceId = newReferenceId;
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
	 * @return the account
	 */
	public DataAnonAccount getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(DataAnonAccount account) {
		this.account = account;
	}

}
