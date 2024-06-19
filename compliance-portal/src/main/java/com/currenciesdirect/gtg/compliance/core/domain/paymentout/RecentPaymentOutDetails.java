package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.io.Serializable;

/**
 *  The class Recent Payment Out Details
 */
public class RecentPaymentOutDetails implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * The Payment id
	 */
	private Integer paymentId;
	
	/**
	 * The customer Type
	 */
	private String custType;
	
	/**
	 * @return payment id
	 */
	public Integer getPaymentId() {
		return paymentId;
	}
	
	/**
	 * Sets the sanction.
	 * @param paymentid        
	 */
	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
	/**
	 * @return customer type 
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custtype       
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
}
