package com.currenciesdirect.gtg.compliance.compliancesrv.domain;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;

public class ProviderPropertyList {

	private List<ProviderProperty> list = new ArrayList<>();

	public List<ProviderProperty> getList() {
		return list;
	}

	public void setList(List<ProviderProperty> list) {
		this.list = list;
	}
	
}
