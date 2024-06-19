package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

// TODO: Auto-generated Javadoc
/**
 * The Class TransactionMonitoringContactSummary.
 */
public class TransactionMonitoringContactSummary implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	@ApiModelProperty(value = "The status", required = true)
	private String status;
	
	/** The trade account id. */
	private String tradeAccountId;
	
	/** The correlation id. */
	private String correlationId;
	
	/** The rule score. */
	private String ruleScore;

	/** The profile score. */
	private String profileScore;

	/** The rules triggered. */
	private List<String> rulesTriggered;
	
	/** The http status. */
	private Integer httpStatus;

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
	 * @return the tradeAccountId
	 */
	public String getTradeAccountId() {
		return tradeAccountId;
	}

	/**
	 * @param tradeAccountId the tradeAccountId to set
	 */
	public void setTradeAccountId(String tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}

	/**
	 * @return the correlationId
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * @param correlationId the correlationId to set
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * @return the ruleScore
	 */
	public String getRuleScore() {
		return ruleScore;
	}

	/**
	 * @param ruleScore the ruleScore to set
	 */
	public void setRuleScore(String ruleScore) {
		this.ruleScore = ruleScore;
	}

	/**
	 * @return the profileScore
	 */
	public String getProfileScore() {
		return profileScore;
	}

	/**
	 * @param profileScore the profileScore to set
	 */
	public void setProfileScore(String profileScore) {
		this.profileScore = profileScore;
	}

	/**
	 * @return the rulesTriggered
	 */
	public List<String> getRulesTriggered() {
		return rulesTriggered;
	}

	/**
	 * @param rulesTriggered the rulesTriggered to set
	 */
	public void setRulesTriggered(List<String> rulesTriggered) {
		this.rulesTriggered = rulesTriggered;
	}

	/**
	 * @return the httpStatus
	 */
	public Integer getHttpStatus() {
		return httpStatus;
	}

	/**
	 * @param httpStatus the httpStatus to set
	 */
	public void setHttpStatus(Integer httpStatus) {
		this.httpStatus = httpStatus;
	}

	
	
}
