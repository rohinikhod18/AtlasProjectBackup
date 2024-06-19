package com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FraudSightScoreResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The status. */
	private String status;

	/** The status. */
	private String fsScore;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the fsScore
	 */
	public String getFsScore() {
		return fsScore;
	}

	/**
	 * @param fsScore the fsScore to set
	 */
	public void setFsScore(String fsScore) {
		this.fsScore = fsScore;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
}
