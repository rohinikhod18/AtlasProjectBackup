package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response;

import java.io.Serializable;

public class CDINCFirstCreditCheckResponse implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
