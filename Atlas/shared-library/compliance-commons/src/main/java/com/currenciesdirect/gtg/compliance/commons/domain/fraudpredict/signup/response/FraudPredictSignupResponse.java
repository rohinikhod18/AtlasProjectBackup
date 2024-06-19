package com.currenciesdirect.gtg.compliance.commons.domain.fraudpredict.signup.response;

import java.io.Serializable;
import java.math.BigDecimal;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudPredictSignupResponse extends ServiceMessageResponse implements Serializable {


  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The score */
  @JsonProperty("score")
  private BigDecimal score;

  /** The fraudpredict_approved */
  @JsonProperty("fraudpredict_approved")
  private float fraudPredictApproved;

  @JsonProperty("percentage_from_threshold")
  private BigDecimal percentageFromThreshold;

  /** The decisionDrivers */
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
   * @return errors
   */
  public String[] getErrors() {
    return errors;
  }

  /**
   * @param errors
   */
  public void setErrors(String[] errors) {
    this.errors = errors;
  }

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
   * @return the score
   */
  public BigDecimal getScore() {
    return score;
  }

  /**
   * @return the fraudPredictApproved
   */
  public float getFraudpredictApproved() {
    return fraudPredictApproved;
  }

  /**
   * @return the decisionDrivers
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
   * @param fraudPredictApproved the fraudPredictApproved to set
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
