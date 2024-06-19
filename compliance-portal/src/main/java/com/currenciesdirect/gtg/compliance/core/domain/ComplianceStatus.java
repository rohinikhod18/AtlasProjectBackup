package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.HashMap;
import java.util.Map;

public enum ComplianceStatus {
	ACTIVE("0000"), NOT_PERFORMED("0001"), IN_QUEUE("0002"), INACTIVE("0003"), REJECTED("0004");

	private String code;
	private static Map<String, ComplianceStatus> list = new HashMap<>();
	
	private ComplianceStatus(String code){
		this.code = code;
		add(this);
	}
	
	private synchronized void add(ComplianceStatus status){
		if(list == null) {
			list = new HashMap<>();
		}
		list.put(status.getCode(), status);
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	public ComplianceStatus findAccountStatus(String code){
		return list.get(code);
	}
	
}
