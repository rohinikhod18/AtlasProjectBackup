package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonAccount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The customer number. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("customer_number")
	private String customerNumber;
	
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
	private DataAnonAccountDetails anonymizationDetails;
	
	/** The contact. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("contact")
	private List<DataAnonContact> contact;

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

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
	public DataAnonAccountDetails getAnonymizationDetails() {
		return anonymizationDetails;
	}

	/**
	 * @param anonymizationDetails the anonymizationDetails to set
	 */
	public void setAnonymizationDetails(DataAnonAccountDetails anonymizationDetails) {
		this.anonymizationDetails = anonymizationDetails;
	}

	/**
	 * @return the contact
	 */
	public List<DataAnonContact> getContact() {
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(List<DataAnonContact> contact) {
		this.contact = contact;
	}

	
}
