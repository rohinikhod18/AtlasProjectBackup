package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.fundsin.response;

import java.io.Serializable;
import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudPredictFundsInResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Score. */
	@JsonProperty("score")
	private BigDecimal score;

	/** The Fraud Predict Approved. */
	@JsonProperty("fraudpredict_approved")
	private float fraudPredictApproved;

	@JsonProperty("percentage_from_threshold")
	private BigDecimal percentageFromThreshold;
	
	/** The Decision Drivers. */
	@JsonProperty("decision_drivers")
	private DecisionDrivers decisionDrivers;
	
	/** The frg trans id. */
	@JsonProperty("trans_id")
	private String frgTransId;
	
	/** The message. */
	@JsonProperty("message")
	private String message;
	
	/** The error. */
	@JsonProperty("errors")
	private String[] errors;
	
	/** The status. */
	@JsonProperty("status")
	private String status;


	
	/**
	 * @return status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return frgTransId
	 */
	public String getFrgTransId() {
		return frgTransId;
	}

	/**
	 * @param frgTransId
	 */
	public void setFrgTransId(String frgTransId) {
		this.frgTransId = frgTransId;
	}

	/**
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return error
	 */
	public String[] getError() {
		return errors;
	}

	/**
	 * @param error
	 */
	public void setError(String[] errors) {
		this.errors = errors;
	}

	/**
	 * @return score
	 */
	public BigDecimal getScore() {
		return score;
	}

	/**
	 * @return fraudPredictApproved
	 */
	public float getFraudPredictApproved() {
		return fraudPredictApproved;
	}

	/**
	 * @return decisionDrivers
	 */
	public DecisionDrivers getDecisionDrivers() {
		return decisionDrivers;
	}

	/**
	 * @param score
	 */
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	/**
	 * @param fraudPredictApproved
	 */
	public void setFraudPredictApproved(float fraudPredictApproved) {
		this.fraudPredictApproved = fraudPredictApproved;
	}

	/**
	 * @param decisionDrivers
	 */
	public void setDecisionDrivers(DecisionDrivers decisionDrivers) {
		this.decisionDrivers = decisionDrivers;
	}

  /**
   * @return the percentageFromThreshold
   */
  public BigDecimal getPercentageFromThreshold() {
    return percentageFromThreshold;
  }

  /**
   * @param percentageFromThreshold the percentageFromThreshold to set
   */
  public void setPercentageFromThreshold(BigDecimal percentageFromThreshold) {
    this.percentageFromThreshold = percentageFromThreshold;
  }
}
