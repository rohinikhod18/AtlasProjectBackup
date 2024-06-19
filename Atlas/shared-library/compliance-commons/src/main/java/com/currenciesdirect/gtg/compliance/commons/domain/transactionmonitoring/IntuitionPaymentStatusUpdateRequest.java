package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.util.List;
import java.util.Base64;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class IntuitionPaymentStatusUpdateRequest.
 */
public class IntuitionPaymentStatusUpdateRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant USERNAME. */
	private static final String USERNAME = "atlasUser";
	
	/** The Constant PASSWORD. */
	private static final String PASSWORD = "atlas123";
	
	/** The id. */
	@JsonProperty("id")
	private String id;
	
	/** The rules triggered. */
	@JsonProperty("RulesTriggered")
	private List<String> rulesTriggered;
	
	/** The status. */
	@JsonProperty("Status")
	private String status;
	
	/** The rule score. */
	@JsonProperty("RuleScore")
	private Integer ruleScore;
	
	/** The rule risk level. */
	@JsonProperty("RuleRiskLevel")
	private String ruleRiskLevel;
	
	/** The client risk score. */
	@JsonProperty("ClientRiskScore")
	private Integer clientRiskScore;
	
	/** The client risk level. */
	@JsonProperty("ClientRiskLevel")
	private String clientRiskLevel;
	
	/** The user id. */
	@JsonProperty("UserId")
	private String userId;
	
	/** The trade contract number. */
	@JsonProperty(value = "TradeContractNumber")
	private String tradeContractNumber;
	
	/** The trade payment ID. */
	@JsonProperty(value = "TradePaymentID")
	private String tradePaymentID;
	
	/** The trx type. */
	@JsonProperty(value = "trx_type")
	private String trxType;
	
	//Add for AT-4880
	/** The action. */
	@JsonProperty(value = "Action")
	private String action;

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
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the rules triggered.
	 *
	 * @return the rules triggered
	 */
	public List<String> getRulesTriggered() {
		return rulesTriggered;
	}

	/**
	 * Sets the rules triggered.
	 *
	 * @param rulesTriggered the new rules triggered
	 */
	public void setRulesTriggered(List<String> rulesTriggered) {
		this.rulesTriggered = rulesTriggered;
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
	 * Gets the trade contract number.
	 *
	 * @return the trade contract number
	 */
	public String getTradeContractNumber() {
		return tradeContractNumber;
	}

	/**
	 * Sets the trade contract number.
	 *
	 * @param tradeContractNumber the new trade contract number
	 */
	public void setTradeContractNumber(String tradeContractNumber) {
		this.tradeContractNumber = tradeContractNumber;
	}
	
	/**
	 * Gets the trade payment ID.
	 *
	 * @return the trade payment ID
	 */
	public String getTradePaymentID() {
		return tradePaymentID;
	}

	/**
	 * Sets the trade payment ID.
	 *
	 * @param tradePaymentID the new trade payment ID
	 */
	public void setTradePaymentID(String tradePaymentID) {
		this.tradePaymentID = tradePaymentID;
	}

	/**
	 * Gets the trx type.
	 *
	 * @return the trx type
	 */
	public String getTrxType() {
		return trxType;
	}

	/**
	 * Sets the trx type.
	 *
	 * @param trxType the new trx type
	 */
	public void setTrxType(String trxType) {
		this.trxType = trxType;
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
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * Checks if is aunthenticated.
	 *
	 * @param auth the auth
	 * @return true, if is aunthenticated
	 */
	public boolean isAunthenticated(String auth) {

		String decodedAuth = "";
		String authString = auth.replace("Basic ", "");

		String[] authParts = authString.split("\\s+");
		String authInfo = authParts[0];
		byte[] bytes = null;
		bytes = Base64.getDecoder().decode(authInfo);
		decodedAuth = new String(bytes);
		String[] credentials = decodedAuth.split(":");
		if(null != credentials && credentials[0].equalsIgnoreCase(USERNAME)
				&& credentials[1].equalsIgnoreCase(PASSWORD)) {
			return true;
		} else {
			return false;
		}
	}

}
