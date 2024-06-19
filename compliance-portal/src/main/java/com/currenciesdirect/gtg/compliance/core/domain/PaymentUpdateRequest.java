package com.currenciesdirect.gtg.compliance.core.domain;

public class PaymentUpdateRequest extends BaseUpdateRequest {

	private String tradeContactId;
	
	private String tradeContractNumber;
	
	private String tradePaymentId;
	
	private String custType;
	
	private String buyCurrency;
	
	private String sellCurrency;
	
	/** The contact name. */
	private String contactName;
	
	/** The status reason. */
	private String statusReason;
	
	/** The user name. */
	private String userName;
	
	/** The client number. */
	private String clientNumber;
	
	/** The country risk level. */
	private String countryRiskLevel;
	
	/** The country. */
	private String country;
	
	private String email;
	
	/** The legal entity. */
	private String legalEntity;
	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTradeContactId() {
		return tradeContactId;
	}

	public void setTradeContactId(String tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}

	public String getTradePaymentId() {
		return tradePaymentId;
	}

	public void setTradePaymentId(String tradePaymentId) {
		this.tradePaymentId = tradePaymentId;
	}

	/**
	 * Gets the cust type.
	 *
	 * @return the cust type
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * Sets the cust type.
	 *
	 * @param custType the new cust type
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * @return the buyCurrency
	 */
	public String getBuyCurrency() {
		return buyCurrency;
	}

	/**
	 * @param buyCurrency the buyCurrency to set
	 */
	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	/**
	 * @return the sellCurrency
	 */
	public String getSellCurrency() {
		return sellCurrency;
	}

	/**
	 * @param sellCurrency the sellCurrency to set
	 */
	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}
	
	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	
	/**
	 * @return the statusReason
	 */
	public String getStatusReason() {
		return statusReason;
	}

	/**
	 * @param statusReason the statusReason to set
	 */
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the clientNumber
	 */
	public String getClientNumber() {
		return clientNumber;
	}

	/**
	 * @param clientNumber the clientNumber to set
	 */
	public void setClientNumber(String clientNumber) {
		this.clientNumber = clientNumber;
	}

	public String getCountryRiskLevel() {
		return countryRiskLevel;
	}

	public void setCountryRiskLevel(String countryRiskLevel) {
		this.countryRiskLevel = countryRiskLevel;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the legal entity.
	 *
	 * @return the legal entity
	 */
	public String getLegalEntity() {
		return legalEntity;
	}
	
	/**
	 * Sets the legal entity.
	 *
	 * @param legalEntity the new legal entity
	 */
	public void setLegalEntity(String legalEntity) {
		this.legalEntity = legalEntity;
	}
}
