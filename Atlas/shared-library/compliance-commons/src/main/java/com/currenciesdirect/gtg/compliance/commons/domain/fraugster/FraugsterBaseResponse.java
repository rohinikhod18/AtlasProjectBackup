/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * The Class FraugsterBaseResponse.
 *
 * @author manish
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FraugsterBaseResponse extends ServiceMessageResponse implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The error msg. */
	private String errorMsg;
	
	/** The frg trans id. */
	private String frgTransId;
	
	/** The fraugster id. */
	protected String fraugsterId;
	
	/** The provider response. */
	protected String providerResponse;

	/** The score. */
	private String score;
	
	/** The fraugster approved. */
	private String fraugsterApproved;
	
	/** The investigation points. */
	private List<String> investigationPoints;
	
	/** The id. */
	private Integer id;
	
	/** The status. */
	private String status;
	
	/** The message. */
	private String message;
	
	/** The error. */
	private String[] errors;
	
	/** The percentage from threshold. */
	private String percentageFromThreshold;
	
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
	 * Gets the error msg.
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}
	
	/**
	 * Gets the frg trans id.
	 * @return the frg trans id
	 */
	public String getFrgTransId() {
		return frgTransId;
	}
	
	/**
	 * Gets the score.
	 * @return the score
	 */
	public String getScore() {
		return score;
	}
	
	/**
	 * Gets the investigation points.
	 * @return the investigation points
	 */
	public List<String> getInvestigationPoints() {
		return investigationPoints;
	}
	
	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	/**
	 * Sets the frg trans id.
	 *
	 * @param frgTransId the new frg trans id
	 */
	public void setFrgTransId(String frgTransId) {
		this.frgTransId = frgTransId;
	}
	
	/**
	 * Sets the score.
	 *
	 * @param score the new score
	 */
	public void setScore(String score) {
		this.score = score;
	}
	
	/**
	 * Sets the investigation points.
	 *
	 * @param investigationPoints the new investigation points
	 */
	public void setInvestigationPoints(List<String> investigationPoints) {
		this.investigationPoints = investigationPoints;
	}
	
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
	 * Gets the fraugster approved.
	 *
	 * @return the fraugster approved
	 */
	public String getFraugsterApproved() {
		return fraugsterApproved;
	}
	
	/**
	 * Sets the fraugster approved.
	 *
	 * @param fraugsterApproved the new fraugster approved
	 */
	public void setFraugsterApproved(String fraugsterApproved) {
		this.fraugsterApproved = fraugsterApproved;
	}
	
	/**
	 * Gets the fraugster id.
	 *
	 * @return the fraugster id
	 */
	public String getFraugsterId() {
		return fraugsterId;
	}

	/**
	 * Sets the fraugster id.
	 *
	 * @param fraugsterId the new fraugster id
	 */
	public void setFraugsterId(String fraugsterId) {
		this.fraugsterId = fraugsterId;
	}
	/**
	 * Gets the provider response.
	 *
	 * @return the provider response
	 */
	public String getProviderResponse() {
		return providerResponse;
	}

	/**
	 * Sets the provider response.
	 *
	 * @param providerResponse
	 *            the new provider response
	 */
	public void setProviderResponse(String providerResponse) {
		this.providerResponse = providerResponse;
	}

	/**
	 * @return the percentageFromThreshold
	 */
	public String getPercentageFromThreshold() {
		return percentageFromThreshold;
	}

	/**
	 * @param percentageFromThreshold the percentageFromThreshold to set
	 */
	public void setPercentageFromThreshold(String percentageFromThreshold) {
		this.percentageFromThreshold = percentageFromThreshold;
	}
}
