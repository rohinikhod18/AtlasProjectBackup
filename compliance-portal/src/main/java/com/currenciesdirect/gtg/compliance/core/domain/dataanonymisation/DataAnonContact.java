package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataAnonContact {

	@JsonProperty(value = "trade_id")
	private Integer tradeContactId;
	
	@JsonProperty(value = "crm_id")
	private String contactSfId;

	/**
	 * @return the tradeContactId
	 */
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * @param tradeContactId the tradeContactId to set
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * @return the contactSfId
	 */
	public String getContactSfId() {
		return contactSfId;
	}

	/**
	 * @param contactSfId the contactSfId to set
	 */
	public void setContactSfId(String contactSfId) {
		this.contactSfId = contactSfId;
	}
	


}
