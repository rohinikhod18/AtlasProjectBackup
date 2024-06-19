package com.currenciesdirect.gtg.compliance.core.domain;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;

/**
 * The Class IntuitionPaymentResponse.
 */
public class IntuitionPaymentResponse implements IDomain {
	
	/** The id. */
	private Integer id;
	
	/** The status. */
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
	
	/** The created on. */
	private String createdOn;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The intuition total records. */
	private Integer intuitionTotalRecords;
	
	/** The is required. */
	private Boolean isRequired;
	
	/** The status value. */
	private String statusValue;
	
	/** The pass count. */
	private Integer passCount;
	
	/** The fail count. */
	private Integer failCount;
	
	/** The entity id. */
	private String entityId;
	
	/** The entity type. */
	private String entityType;

	/** The current action. */
	private String currentAction;
	
	/** The decision. */
	private String decision;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Integer id) {
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
	 * Gets the client rule score.
	 *
	 * @return the client rule score
	 */
	public Integer getClientRuleScore() {
		return clientRiskScore;
	}

	/**
	 * Sets the client rule score.
	 *
	 * @param clientRiskScore the new client rule score
	 */
	public void setClientRuleScore(Integer clientRiskScore) {
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
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the intuition total records.
	 *
	 * @return the intuition total records
	 */
	public Integer getIntuitionTotalRecords() {
		return intuitionTotalRecords;
	}

	/**
	 * Sets the intuition total records.
	 *
	 * @param intuitionTotalRecords the new intuition total records
	 */
	public void setIntuitionTotalRecords(Integer intuitionTotalRecords) {
		this.intuitionTotalRecords = intuitionTotalRecords;
	}

	/**
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets the checks if is required.
	 *
	 * @param isRequired the new checks if is required
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	/**
	 * Gets the status value.
	 *
	 * @return the status value
	 */
	public String getStatusValue() {
		return statusValue;
	}

	/**
	 * Sets the status value.
	 *
	 * @param statusValue the new status value
	 */
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	/**
	 * Gets the pass count.
	 *
	 * @return the pass count
	 */
	public Integer getPassCount() {
		return passCount;
	}

	/**
	 * Sets the pass count.
	 *
	 * @param passCount the new pass count
	 */
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	/**
	 * Gets the fail count.
	 *
	 * @return the fail count
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * Sets the fail count.
	 *
	 * @param failCount the new fail count
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * Gets the entity id.
	 *
	 * @return the entity id
	 */
	public String getEntityId() {
		return entityId;
	}

	/**
	 * Sets the entity id.
	 *
	 * @param entityId the new entity id
	 */
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	/**
	 * Gets the entity type.
	 *
	 * @return the entity type
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * Sets the entity type.
	 *
	 * @param entityType the new entity type
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}
	
	/**
	 * Gets the current action.
	 *
	 * @return the current action
	 */
	public String getCurrentAction() {
		return currentAction;
	}

	/**
	 * Sets the current action.
	 *
	 * @param currentAction the new current action
	 */
	public void setCurrentAction(String currentAction) {
		this.currentAction = currentAction;
	}

	/**
	 * Gets the decision.
	 *
	 * @return the decision
	 */
	public String getDecision() {
		return decision;
	}

	/**
	 * Sets the decision.
	 *
	 * @param decision the new decision
	 */
	public void setDecision(String decision) {
		this.decision = decision;
	}
	
}
