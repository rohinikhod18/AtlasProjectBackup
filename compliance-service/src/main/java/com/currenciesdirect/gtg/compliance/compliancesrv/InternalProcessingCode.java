package com.currenciesdirect.gtg.compliance.compliancesrv;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Umesh
 *
 */

public enum InternalProcessingCode {
	APPROVED("000"), 
	REJECTED("101"),
	NOT_CLEARED("102"),
	INV_REQUEST("901"),
	DUPLICATE_REQUEST("902"),
	GEN_DECLINE("999"),
	SERVICE_UNAVAILABLE("404"), 
	INTERNAL_SERVER_ERROR("500"), 
	SERVICE_ERROR("110");

	private String code;
	
	private static Map<String, InternalProcessingCode> ipcs;

	private InternalProcessingCode(String code) {
		this.code = code;
		add(this);
	}

	public String getCode() {
		return code;
	}

	private static synchronized void add(InternalProcessingCode ipc) {
		if (ipcs == null) {
			ipcs = new HashMap<>();
		}
		ipcs.put(ipc.getCode(), ipc);
	}

	public static InternalProcessingCode findByCode(String code) {
		return ipcs.get(code);
	}
}
