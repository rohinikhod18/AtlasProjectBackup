package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnquiryResponse extends BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	private String registrationStatus;

	public String getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(String registrationStatus) {
		this.registrationStatus = registrationStatus;
	}
}
