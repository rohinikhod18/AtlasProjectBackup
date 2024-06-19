package com.currenciesdirect.gtg.compliance.core.domain.registration;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;

public class RegistrationQueueFilter extends BaseFilter {
	
	private String[] onfidoStatus;

	@Override
	public String[] getOnfidoStatus() {
		return onfidoStatus;
	}

	@Override
	public void setOnfidoStatus(String[] onfidoStatus) {
		this.onfidoStatus = onfidoStatus;
	} 

}
