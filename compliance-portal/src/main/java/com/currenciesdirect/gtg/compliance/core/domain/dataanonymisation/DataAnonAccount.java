package com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonAccount {

	@ApiModelProperty(value = "The trade account number", required = true)
	@JsonProperty(value = "customer_number")
	private String tradeAccountNumber;

	@ApiModelProperty(value = "The Trade Account ID ", example = "301000006112367", required = true)
	@JsonProperty(value = "trade_id")
	private Integer tradeAccountID;
	
	@ApiModelProperty(value = "Account Salesforce ID", example = "0010O00001LUEp4FAH", required = true)
	@JsonProperty(value = "crm_id")
	private String accountSFID;

	@JsonProperty(value="contact")
	private List<DataAnonContact> contact = new ArrayList<>();

	/**
	 * @return the tradeAccountNumber
	 */
	public String getTradeAccountNumber() {
		return tradeAccountNumber;
	}

	/**
	 * @param tradeAccountNumber the tradeAccountNumber to set
	 */
	public void setTradeAccountNumber(String tradeAccountNumber) {
		this.tradeAccountNumber = tradeAccountNumber;
	}

	/**
	 * @return the tradeAccountID
	 */
	public Integer getTradeAccountID() {
		return tradeAccountID;
	}

	/**
	 * @param tradeAccountID the tradeAccountID to set
	 */
	public void setTradeAccountID(Integer tradeAccountID) {
		this.tradeAccountID = tradeAccountID;
	}

	/**
	 * @return the accountSFID
	 */
	public String getAccountSFID() {
		return accountSFID;
	}

	/**
	 * @param accountSFID the accountSFID to set
	 */
	public void setAccountSFID(String accountSFID) {
		this.accountSFID = accountSFID;
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
