package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonAccountDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The customer number. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("customer_number")
	private String customerNumber;
	
	/** The number. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("number")
	private Long number;
	
	/** The name. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("name")
	private String name;
	
	/** The ip. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("ip")
	private String ip;
	
	/** The date. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("date")
	private String date;
	
	/** The url. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("url")
	private String url;
	
	/** The bank account. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("bank_account")
	private String bankAccount;
	
	/** The bank code. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("bank_code")
	private String bankCode;
	
	/** The cc security code. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("cc_security_code")
	private Integer ccSecurityCode;
	
	/** The cc date mmyy. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("cc_date_mmyy")
	private String ccDateMmyy;
	
	/** The address. */
	@ApiModelProperty(value = "The new Reference id", required = true)
	@JsonProperty("address")
	private DataAnonAccountDetailsAddress address;

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
	 * @return the number
	 */
	public Long getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(Long number) {
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the bankAccount
	 */
	public String getBankAccount() {
		return bankAccount;
	}

	/**
	 * @param bankAccount the bankAccount to set
	 */
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * @return the bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * @return the ccSecurityCode
	 */
	public Integer getCcSecurityCode() {
		return ccSecurityCode;
	}

	/**
	 * @param ccSecurityCode the ccSecurityCode to set
	 */
	public void setCcSecurityCode(Integer ccSecurityCode) {
		this.ccSecurityCode = ccSecurityCode;
	}

	/**
	 * @return the ccDateMmyy
	 */
	public String getCcDateMmyy() {
		return ccDateMmyy;
	}

	/**
	 * @param ccDateMmyy the ccDateMmyy to set
	 */
	public void setCcDateMmyy(String ccDateMmyy) {
		this.ccDateMmyy = ccDateMmyy;
	}

	/**
	 * @return the address
	 */
	public DataAnonAccountDetailsAddress getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(DataAnonAccountDetailsAddress address) {
		this.address = address;
	}
	
	
	
	
}
