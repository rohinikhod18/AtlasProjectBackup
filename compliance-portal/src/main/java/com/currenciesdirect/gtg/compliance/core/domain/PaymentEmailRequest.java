package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaymentEmailRequest.
 */
public class PaymentEmailRequest {

	/** The trade contact id. */
	private String tradeContractId;
	
	/** The currency. */
	private String currency;
	
	/** The organization. */
	private String orgCode;
	
	/** The cust type. */
	private String custType;
	
	/** The status. */
	private String status;
	
	/** The comment. */
	private String comment;
	
	/** The status reason. */
	private String statusReason;
	
	/** The user name. */
	private String userName;
	
	/** The client number. */
	private String clientNumber;


	/**
	 * @return the tradeContractId
	 */
	public String getTradeContractId() {
		return tradeContractId;
	}

	/**
	 * @param tradeContractId the tradeContractId to set
	 */
	public void setTradeContractId(String tradeContractId) {
		this.tradeContractId = tradeContractId;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the organization
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrgCode(String organization) {
		this.orgCode = organization;
	}

	/**
	 * @return the custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType the custType to set
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
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
}
