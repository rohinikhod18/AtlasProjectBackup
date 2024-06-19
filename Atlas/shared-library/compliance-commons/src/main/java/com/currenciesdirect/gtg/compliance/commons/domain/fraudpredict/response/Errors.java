package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Errors implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The customer Id  */
	@JsonProperty("customer_id")
	private int customerId;

	
	/**
	 * @return customerId
	 */
	public int getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId
	 */
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
}
