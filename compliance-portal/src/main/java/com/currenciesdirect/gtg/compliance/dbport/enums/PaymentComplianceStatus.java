package com.currenciesdirect.gtg.compliance.dbport.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * The Enum PaymentComplianceStatus.
 */
public enum PaymentComplianceStatus {

	/** The clear. */
	CLEAR(1,"0000"), /** The reject. */
	REJECT(2,"0001"), /** The seize. */
    SEIZE(3,"0002"), /** The hold. */
    HOLD(4,"0003"), /** The watched. */
    WATCHED(5,"0004"),
    REVERSED(6,"0005");

	/** The id. */
	private Integer id;
	
	/** The code. */
	private String code;
	
	/** The list. */
	private static Map<String, PaymentComplianceStatus> list = new HashMap<>();
	
	/**
	 * Instantiates a new payment compliance status.
	 *
	 * @param id the id
	 * @param code the code
	 */
	private PaymentComplianceStatus(Integer id,String code){
		this.id = id;
		this.code = code;
		add(this);
	}
	
	/**
	 * Adds the.
	 *
	 * @param status the status
	 */
	private synchronized void add(PaymentComplianceStatus status){
		if(list == null) {
			list = new HashMap<>();
		}
		list.put(status.getCode(), status);
	}
	
	/**
	 * Gets the id by compliance status.
	 *
	 * @param status the status
	 * @return the id by compliance status
	 */
	public static Integer getIdByComplianceStatus(String status){
	        for (PaymentComplianceStatus cmpStatus : PaymentComplianceStatus.values()) {
				if(cmpStatus.name().equalsIgnoreCase(status)){
					return cmpStatus.getId();
				}
			}
	        return -1;
	}
	
	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * Find account status.
	 *
	 * @param code the code
	 * @return the payment compliance status
	 */
	public PaymentComplianceStatus findAccountStatus(String code){
		return list.get(code);
	}
	
	public static String getStatusFromDatabaseStatus(Integer status){
		for(PaymentComplianceStatus serviceStatus :PaymentComplianceStatus.values()){
			if(serviceStatus.getId().equals(status)){
				return serviceStatus.name();
			}
		}
		return HOLD.name();
	}
}
