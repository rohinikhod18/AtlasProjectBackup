package com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake;

import java.io.Serializable;

/**
 * @author prashant.verma
 */
public class DataLakeTxResponse implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer responseCode;
	
	private String responseDescription;
	
	private Boolean status;

	public Integer getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(Integer responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseDescription() {
		return responseDescription;
	}

	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public DataLakeTxResponse(Boolean status, Integer responseCode, String responseDescription) {
		this.responseCode = responseCode;
		this.responseDescription = responseDescription;
		this.status = status;
	}
}