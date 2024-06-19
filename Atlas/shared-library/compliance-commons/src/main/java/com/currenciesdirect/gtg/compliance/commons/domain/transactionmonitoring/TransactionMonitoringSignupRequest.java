package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The Class TransactionMonitoringSignupRequest.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMonitoringSignupRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The transaction monitoring account request. */
	private TransactionMonitoringAccountRequest transactionMonitoringAccountRequest;

	/** The transaction monitoring contact requests. */
	private List<TransactionMonitoringContactRequest> transactionMonitoringContactRequests;

	/** The request type. */
	private String requestType;
	
	private String orgCode;
	
	private Integer createdBy;
	
	private Integer isPresent = 0;
	
	/** The account TM flag. */
	private Integer accountTMFlag; //Add for AT-4169

	@JsonProperty(value = "isCardApply")
	private String isCardApply;
	
	@JsonProperty(value = "Registration_load_criteria") //AT-5127
	private String registrationLoadCriteria; 
	
	/**
	 * @return the accountTMFlag
	 */
	public Integer getAccountTMFlag() {
		return accountTMFlag;
	}

	/**
	 * @param accountTMFlag the accountTMFlag to set
	 */
	public void setAccountTMFlag(Integer accountTMFlag) {
		this.accountTMFlag = accountTMFlag;
	}

	public Integer getIsPresent() {
		return isPresent;
	}

	public void setIsPresent(Integer isPresent) {
		this.isPresent = isPresent;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getIsCardApply() {
		return isCardApply;
	}

	public void setIsCardApply(String cardApply) {
		this.isCardApply = cardApply;
	}

	/**
	 * Gets the transaction monitoring account request.
	 *
	 * @return the transaction monitoring account request
	 */
	public TransactionMonitoringAccountRequest getTransactionMonitoringAccountRequest() {
		return transactionMonitoringAccountRequest;
	}

	/**
	 * Sets the transaction monitoring account request.
	 *
	 * @param transactionMonitoringAccountRequest the new transaction monitoring account request
	 */
	public void setTransactionMonitoringAccountRequest(
			TransactionMonitoringAccountRequest transactionMonitoringAccountRequest) {
		this.transactionMonitoringAccountRequest = transactionMonitoringAccountRequest;
	}

	/**
	 * Gets the transaction monitoring contact requests.
	 *
	 * @return the transaction monitoring contact requests
	 */
	public List<TransactionMonitoringContactRequest> getTransactionMonitoringContactRequests() {
		return transactionMonitoringContactRequests;
	}

	/**
	 * Sets the transaction monitoring contact requests.
	 *
	 * @param transactionMonitoringContactRequests the new transaction monitoring contact requests
	 */
	public void setTransactionMonitoringContactRequests(
			List<TransactionMonitoringContactRequest> transactionMonitoringContactRequests) {
		this.transactionMonitoringContactRequests = transactionMonitoringContactRequests;
	}

	/**
	 * Gets the request type.
	 *
	 * @return the request type
	 */
	public String getRequestType() {
		return requestType;
	}

	/**
	 * Sets the request type.
	 *
	 * @param requestType the new request type
	 */
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	/**
	 * @return the registrationLoadCriteria
	 */
	public String getRegistrationLoadCriteria() {
		return registrationLoadCriteria;
	}

	/**
	 * @param registrationLoadCriteria the registrationLoadCriteria to set
	 */
	public void setRegistrationLoadCriteria(String registrationLoadCriteria) {
		this.registrationLoadCriteria = registrationLoadCriteria;
	}

}
