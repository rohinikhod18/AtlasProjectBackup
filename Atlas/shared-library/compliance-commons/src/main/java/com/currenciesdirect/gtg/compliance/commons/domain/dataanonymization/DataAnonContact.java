package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonContact implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The trade id. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("trade_id")
	private Long tradeId;
	
	/** The crm id. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("crm_id")
	private String crmId;
	
	/** The anonymization details. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("anonymization_details")
	private DataAnonContactDetails anonymizationDetails;

	/**
	 * @return the tradeId
	 */
	public Long getTradeId() {
		return tradeId;
	}

	/**
	 * @param tradeId the tradeId to set
	 */
	public void setTradeId(Long tradeId) {
		this.tradeId = tradeId;
	}

	/**
	 * @return the crmId
	 */
	public String getCrmId() {
		return crmId;
	}

	/**
	 * @param crmId the crmId to set
	 */
	public void setCrmId(String crmId) {
		this.crmId = crmId;
	}

	/**
	 * @return the anonymizationDetails
	 */
	public DataAnonContactDetails getAnonymizationDetails() {
		return anonymizationDetails;
	}

	/**
	 * @param anonymizationDetails the anonymizationDetails to set
	 */
	public void setAnonymizationDetails(DataAnonContactDetails anonymizationDetails) {
		this.anonymizationDetails = anonymizationDetails;
	}
	
	
}
