package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.ArrayList;
import java.util.List;

public enum OnfidoStatusEnum {

	//CLEAR(1,"CLEAR"),
	
	REJECT(2,"REJECT"),
	
	CAUTION(3,"CAUTION"),
	
	SUSPECT(4,"SUSPECT");
	
	/** The service status as integer. */
	private Integer onfidoStatusAsInteger;
	
	/** The service status as string. */
	private String onfidoStatusAsString;
	
	/**
	 * Gets the onfido status values.
	 *
	 * @return the onfido status values
	 */
	public static List<String> getOnfidoStatusValues(){
		OnfidoStatusEnum[] values = OnfidoStatusEnum.values();
		List<String> ls = new ArrayList<>(values.length);
		
		for(OnfidoStatusEnum value : values){
			ls.add(value.getOnfidoStatusAsString());
		}
		return ls;
	}
	/**
	 * Instantiates a new service status.
	 *
	 * @param serviceStatusAsInteger the service status as integer
	 * @param serviceStatusAsString the service status as string
	 */
	OnfidoStatusEnum (Integer onfidoStatusAsInteger,String onfidoStatusAsString){
		this.onfidoStatusAsInteger = onfidoStatusAsInteger;
		this.onfidoStatusAsString = onfidoStatusAsString;
	}
	
	/**
	 * @return the onfidoStatusAsInteger
	 */
	public Integer getOnfidoStatusAsInteger() {
		return onfidoStatusAsInteger;
	}

	/**
	 * @return the onfidoStatusAsString
	 */
	public String getOnfidoStatusAsString() {
		return onfidoStatusAsString;
	}

	/**
	 * Gets the status as string.
	 *
	 * @param status the status
	 * @return the status as string
	 */
	public static String getStatusAsString(Integer status){
		for(OnfidoStatusEnum serviceStatus :OnfidoStatusEnum.values()){
			if(serviceStatus.getOnfidoStatusAsInteger().equals(status)){
				return serviceStatus.getOnfidoStatusAsString();
			}
		}
		return null;
	}
	
	/**
	 * Gets the status as integer.
	 *
	 * @param status the status
	 * @return the status as integer
	 */
	public static Integer getStatusAsInteger(String status){
		for(OnfidoStatusEnum serviceStatus :OnfidoStatusEnum.values()){
			if(serviceStatus.getOnfidoStatusAsString().equalsIgnoreCase(status)){
				return serviceStatus.getOnfidoStatusAsInteger();
			}
		}
		return null;
	}

}
