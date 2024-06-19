package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class TransactionMonitoringAccountProviderResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMonitoringAccountProviderResponse implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The id. */
	@JsonProperty("id")
	private String id;
	
	/** The status. */
	@JsonProperty("Status")
	private String status;
	
	/** The correlation id. */
	private String correlationId;

	/** The rule score. */
	@JsonProperty("RuleScore")
	private String ruleScore;

	/** The profie score. */
	@JsonProperty("profileScore")
	private String profileScore;

	/** The rules triggered. */
	@JsonProperty("RulesTriggered")
	private List<String> rulesTriggered;
	
	/** The risk level. */
	@JsonProperty("RiskLevel")
	private String riskLevel;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonProperty("cardDecision")
	private String cardDecision;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @param status the status to set
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

	public String getCardDecision() {
		return cardDecision;
	}

	public void setCardDecision(String cardDecision) {
		this.cardDecision = cardDecision;
	}
}
