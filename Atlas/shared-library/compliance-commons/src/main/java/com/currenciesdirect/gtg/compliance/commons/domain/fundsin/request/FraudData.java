package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "fsScore", "fsId", "message", "fsReasonCode", "threedsVersion"})
public class FraudData implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The fs score. */
	@JsonProperty("fs_score")
	private String fsScore;
	
	/** The fs id. */
	@JsonProperty("fs_id")
	private String fsId;
	
	/** The fs message. */
	@JsonProperty("fs_message")
	private String fsMessage;
	
	/** The fs reason codes. */
	@JsonProperty("fs_reasoncodes")
	private String fsReasonCodes;
	
	/** The threeds version. */
	@JsonProperty(value = "threeds_version")
	private String threedsVersion;

	/**
	 * Gets the fs score.
	 *
	 * @return the fs score
	 */
	public String getFsScore() {
		return fsScore;
	}

	/**
	 * Sets the fs score.
	 *
	 * @param fsScore the new fs score
	 */
	public void setFsScore(String fsScore) {
		this.fsScore = fsScore;
	}

	/**
	 * @return the fsId
	 */
	public String getFsId() {
		return fsId;
	}

	/**
	 * @param fsId the fsId to set
	 */
	public void setFsId(String fsId) {
		this.fsId = fsId;
	}

	/**
	 * @return the fsMessage
	 */
	public String getFsMessage() {
		return fsMessage;
	}

	/**
	 * @param fsMessage the fsMessage to set
	 */
	public void setFsMessage(String fsMessage) {
		this.fsMessage = fsMessage;
	}

	/**
	 * @return the fsReasonCodes
	 */
	public String getFsReasonCodes() {
		return fsReasonCodes;
	}

	/**
	 * @param fsReasonCodes the fsReasonCodes to set
	 */
	public void setFsReasonCodes(String fsReasonCodes) {
		this.fsReasonCodes = fsReasonCodes;
	}

	/**
	 * Gets the threeds version.
	 *
	 * @return the threeds version
	 */
	public String getThreedsVersion() {
		return threedsVersion;
	}

	/**
	 * Sets the threeds version.
	 *
	 * @param threedsVersion the new threeds version
	 */
	public void setThreedsVersion(String threedsVersion) {
		this.threedsVersion = threedsVersion;
	}
	
}
