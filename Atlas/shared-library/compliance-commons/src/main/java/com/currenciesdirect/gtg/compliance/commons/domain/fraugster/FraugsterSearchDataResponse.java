/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import java.io.Serializable;
import java.util.List;

/**
 * The Class FraugsterSearchDataResponse.
 *
 * @author manish
 */
public class FraugsterSearchDataResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The error msg. */
	private String errorMsg;

	/** The frg trans id. */
	private String frgTransId;

	/** The score. */
	private String score;

	/** The fraugster approved. */
	private String fraugsterApproved;

	/** The investigation points. */
	private List<String> investigationPoints;

	/** The id. */
	private Integer id;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/**
	 * Gets the error msg.
	 *
	 * @return the error msg
	 */
	public String getErrorMsg() {
		return errorMsg;
	}

	/**
	 * Gets the frg trans id.
	 *
	 * @return the frg trans id
	 */
	public String getFrgTransId() {
		return frgTransId;
	}

	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * Gets the investigation points.
	 *
	 * @return the investigation points
	 */
	public List<String> getInvestigationPoints() {
		return investigationPoints;
	}

	/**
	 * Sets the error msg.
	 *
	 * @param errorMsg
	 *            the new error msg
	 */
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	/**
	 * Sets the frg trans id.
	 *
	 * @param frgTransId
	 *            the new frg trans id
	 */
	public void setFrgTransId(String frgTransId) {
		this.frgTransId = frgTransId;
	}

	/**
	 * Sets the score.
	 *
	 * @param score
	 *            the new score
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * Sets the investigation points.
	 *
	 * @param investigationPoints
	 *            the new investigation points
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
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription
	 *            the new error description
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
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
	 * @param fraugsterApproved
	 *            the new fraugster approved
	 */
	public void setFraugsterApproved(String fraugsterApproved) {
		this.fraugsterApproved = fraugsterApproved;
	}

}
