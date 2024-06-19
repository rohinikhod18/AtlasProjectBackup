package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionMonitoringFundsProviderResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private String id;
	
	@JsonProperty("RulesTriggered")
	private List<String> rulesTriggered;
	
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("RuleScore")
	private Integer ruleScore;
	
	@JsonProperty("RuleRiskLevel")
	private String ruleRiskLevel;
	
	@JsonProperty("ClientRiskScore")
	private Integer clientRiskScore;
	
	@JsonProperty("ClientRiskLevel")
	private String clientRiskLevel;
	
	@JsonProperty("UserId")
	private String userId;
	
	//Add for AT-4880
	@JsonProperty("Action")
	private String action;
	
	private String correlationId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getRulesTriggered() {
		return rulesTriggered;
	}

	public void setRulesTriggered(List<String> rulesTriggered) {
		this.rulesTriggered = rulesTriggered;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getRuleScore() {
		return ruleScore;
	}

	public void setRuleScore(Integer ruleScore) {
		this.ruleScore = ruleScore;
	}

	public String getRuleRiskLevel() {
		return ruleRiskLevel;
	}

	public void setRuleRiskLevel(String ruleRiskLevel) {
		this.ruleRiskLevel = ruleRiskLevel;
	}

	public Integer getClientRiskScore() {
		return clientRiskScore;
	}

	public void setClientRiskScore(Integer clientRiskScore) {
		this.clientRiskScore = clientRiskScore;
	}

	public String getClientRiskLevel() {
		return clientRiskLevel;
	}

	public void setClientRiskLevel(String clientRiskLevel) {
		this.clientRiskLevel = clientRiskLevel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
