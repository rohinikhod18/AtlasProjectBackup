package com.currenciesdirect.gtg.compliance.dbport.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum PaymentMethodEnum.
 */
public enum PaymentMethodEnum {
	
	/** The unknown. */
	UNKNOWN(0,"UNKNOWN","UNKNOWN"),
	
	/** The foa. */
	FOA(1,"FOA","FOA"),
	
	/** The switchdebit. */
	SWITCHDEBIT(2,"SWITCH/DEBIT","SWITCH/DEBIT"),
	
	/** The directdebit. */
	DIRECTDEBIT(3,"DIRECTDEBIT","DIRECTDEBIT"),
	
	/** The chequedraft. */
	CHEQUEDRAFT(4,"CHEQUE/DRAFT","CHEQUE/DRAFT"),
	
	/** The bacschapstt. */
	BACSCHAPSTT(5,"BACS/CHAPS/TT","BACS/CHAPS/TT"),
	
	/** The Wallet.*/
	WALLET(6,"WALLET","WALLET"),
	
	/** The returnoffunds. */
	RETURNOFFUNDS(7,"RETURN_OF_FUNDS","RETURN_OF_FUNDS"),
	
	/** The achdebit. */
	ACHDEBIT(8,"ACH_DEBIT","ACH_DEBIT");//AT-5552
	
	/** The database status. */
	private Integer databaseStatus;
	
	/** The ui status. */
	private String uiStatus;
	
	/** The status. */
	private String status;
	
	/**
	 * Instantiates a new payment method enum.
	 *
	 * @param databaseStatus the database status
	 * @param uiStatus the ui status
	 * @param status the status
	 */
	PaymentMethodEnum (Integer databaseStatus,String uiStatus,String status){
		this.databaseStatus = databaseStatus;
		this.uiStatus = uiStatus;
		this.status = status;
	}
	
	/**
	 * Gets the ui status from database status.
	 *
	 * @param status the status
	 * @return the ui status from database status
	 */
	public static String getUiStatusFromDatabaseStatus(Integer status){
		for(ServiceStatusEnum serviceStatus :ServiceStatusEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getUiStatus();
			}
		}
		return null;
	}
	
	
	/**
	 * Gets the status from database status.
	 *
	 * @param status the status
	 * @return the status from database status
	 */
	public static String getStatusFromDatabaseStatus(Integer status){
		for(ServiceStatusEnum serviceStatus :ServiceStatusEnum.values()){
			if(serviceStatus.getDatabaseStatus().equals(status)){
				return serviceStatus.getStatus();
			}
		}
		return null;
	}
	
	/**
	 * Gets the ui status.
	 *
	 * @return the ui status
	 */
	public String getUiStatus() {
		return this.uiStatus;
	}			
	
	/**
	 * Gets the database status.
	 *
	 * @return the database status
	 */
	public Integer getDatabaseStatus() {
		return this.databaseStatus;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus(){
		return this.status;
	}
	
	/**
	 * Gets the payment method values.
	 *
	 * @return the payment method values
	 */
	public static List<String> getPaymentMethodValues(){
		List<String> ls = new ArrayList<>();
		PaymentMethodEnum[] values = PaymentMethodEnum.values();
		for(PaymentMethodEnum value : values){
			// UNKNOWN and FOA currently payment method not present 
			if(!value.getDatabaseStatus().equals(0) && !value.getDatabaseStatus().equals(1)){
				ls.add(value.uiStatus);
			}
			
		}
		return ls;
	}
	
}
