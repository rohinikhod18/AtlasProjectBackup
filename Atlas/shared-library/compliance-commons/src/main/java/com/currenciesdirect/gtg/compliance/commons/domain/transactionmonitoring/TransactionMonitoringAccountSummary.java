package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;


// TODO: Auto-generated Javadoc
/**
 * The Class TransactionMonitoringAccountSummary.
 */
public class TransactionMonitoringAccountSummary implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	private String status;
	
	/** The correlation id. */
	private String correlationId;

	/** The rule score. */
	private String ruleScore;

	/** The profie score. */
	private String profileScore;

	/** The rules triggered. */
	private List<String> rulesTriggered;

	/** The account id. */
	private Integer accountId;

	/** The http status. */
	private Integer httpStatus;
	
	/** The risk level. */
	private String riskLevel;
	
	private String ruleStatus;

	private String cardDecision;

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
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the rule score.
	 *
	 * @return the ruleScore
	 */
	public String getRuleScore() {
		return ruleScore;
	}

	/**
	 * Sets the rule score.
	 *
	 * @param ruleScore the ruleScore to set
	 */
	public void setRuleScore(String ruleScore) {
		this.ruleScore = ruleScore;
	}

	/**
	 * Gets the profile score.
	 *
	 * @return the profileScore
	 */
	public String getProfileScore() {
		return profileScore;
	}

	/**
	 * Sets the profile score.
	 *
	 * @param profileScore the profileScore to set
	 */
	public void setProfileScore(String profileScore) {
		this.profileScore = profileScore;
	}

	/**
	 * Gets the rules triggered.
	 *
	 * @return the rulesTriggered
	 */
	public List<String> getRulesTriggered() {
		return rulesTriggered;
	}

	/**
	 * Sets the rules triggered.
	 *
	 * @param rulesTriggered the rulesTriggered to set
	 */
	public void setRulesTriggered(List<String> rulesTriggered) {
		this.rulesTriggered = rulesTriggered;
	}

	/**
	 * Gets the account id.
	 *
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the httpStatus
	 */
	public Integer getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the risk level.
	 *
	 * @return the risk level
	 */
	public String getRiskLevel() {
		return riskLevel;
	}

	/**
	 * Sets the risk level.
	 *
	 * @param riskLevel the new risk level
	 */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	/**
	 * Gets the rule status.
	 *
	 * @return the rule status
	 */
	public String getRuleStatus() {
		return ruleStatus;
	}

	/**
	 * Sets the rule status.
	 *
	 * @param ruleStatus the new rule status
	 */
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public String getCardDecision() {
		return cardDecision;
	}

	public void setCardDecision(String cardDecision) {
		this.cardDecision = cardDecision;
	}
}
