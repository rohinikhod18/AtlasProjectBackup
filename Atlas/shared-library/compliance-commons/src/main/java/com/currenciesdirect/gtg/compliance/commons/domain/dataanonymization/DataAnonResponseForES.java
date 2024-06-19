package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonResponseForES extends BaseResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The code. */
	@ApiModelProperty(value = "The code", required = true)
	@JsonProperty("code")
	private String code;
	
	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	@JsonProperty("status")
	private String status;
	
	/** The system. */
	@ApiModelProperty(value = "The system", required = true)
	@JsonProperty("system")
	private String system;

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}

	/**
	 * @param system the system to set
	 */
	public void setSystem(String system) {
		this.system = system;
	}
	
	

}
