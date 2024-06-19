package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class TransactionMonitoringPaymentsSummary.
 */
public class TransactionMonitoringPaymentsSummary implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The funds in id. */
	private Integer fundsInId;
	
	/** The funds out id. */
	private Integer fundsOutId;
	
	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	private String status;
	
	/** The correlation id. */
	private String correlationId;
	
	/** The rule score. */
	private Integer ruleScore;
	
	/** The rule risk level. */
	private String ruleRiskLevel;
	
	/** The client risk score. */
	private Integer clientRiskScore;
	
	/** The client risk level. */
	private String clientRiskLevel;
	
	/** The user id. */
	private String userId;
	
	//Add for AT-4880
	/** The action. */
	private String action;

	/**
	 * Gets the funds in id.
	 *
	 * @return the funds in id
	 */
	public Integer getFundsInId() {
		return fundsInId;
	}

	/**
	 * Sets the funds in id.
	 *
	 * @param fundsInId the new funds in id
	 */
	public void setFundsInId(Integer fundsInId) {
		this.fundsInId = fundsInId;
	}

	/**
	 * Gets the funds out id.
	 *
	 * @return the funds out id
	 */
	public Integer getFundsOutId() {
		return fundsOutId;
	}

	/**
	 * Sets the funds out id.
	 *
	 * @param fundsOutId the new funds out id
	 */
	public void setFundsOutId(Integer fundsOutId) {
		this.fundsOutId = fundsOutId;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the rule score.
	 *
	 * @return the rule score
	 */
	public Integer getRuleScore() {
		return ruleScore;
	}

	/**
	 * Sets the rule score.
	 *
	 * @param ruleScore the new rule score
	 */
	public void setRuleScore(Integer ruleScore) {
		this.ruleScore = ruleScore;
	}

	/**
	 * Gets the rule risk level.
	 *
	 * @return the rule risk level
	 */
	public String getRuleRiskLevel() {
		return ruleRiskLevel;
	}

	/**
	 * Sets the rule risk level.
	 *
	 * @param ruleRiskLevel the new rule risk level
	 */
	public void setRuleRiskLevel(String ruleRiskLevel) {
		this.ruleRiskLevel = ruleRiskLevel;
	}

	/**
	 * Gets the client risk score.
	 *
	 * @return the client risk score
	 */
	public Integer getClientRiskScore() {
		return clientRiskScore;
	}

	/**
	 * Sets the client risk score.
	 *
	 * @param clientRiskScore the new client risk score
	 */
	public void setClientRiskScore(Integer clientRiskScore) {
		this.clientRiskScore = clientRiskScore;
	}

	/**
	 * Gets the client risk level.
	 *
	 * @return the client risk level
	 */
	public String getClientRiskLevel() {
		return clientRiskLevel;
	}

	/**
	 * Sets the client risk level.
	 *
	 * @param clientRiskLevel the new client risk level
	 */
	public void setClientRiskLevel(String clientRiskLevel) {
		this.clientRiskLevel = clientRiskLevel;
	}

	/**
	 * Gets the user id.
	 *
	 * @return the user id
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * Sets the user id.
	 *
	 * @param userId the new user id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(String action) {
		this.action = action;
	}
	
}
