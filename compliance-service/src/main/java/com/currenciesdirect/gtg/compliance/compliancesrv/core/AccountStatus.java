package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.HashMap;
import java.util.Map;

public enum AccountStatus {
	
	ACTIVE("0000"), NOT_PERFORMED("0001"), IN_QUEUE("0002"), INACTIVE("0003"), REJECTED("0004");

	private String code;
	private static Map<String, AccountStatus> list = new HashMap<>();
	
	private AccountStatus(String code){
		this.code = code;
		add(this);
	}
	
	private synchronized void add(AccountStatus status){
		list.put(status.getCode(), status);
	}
	
	public String getCode() {
		return code;
	}
	
	public AccountStatus findAccountStatus(String code){
		return list.get(code);
	}
	
}
