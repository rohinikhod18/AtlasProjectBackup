package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

public class SanctionCsvRequest extends ServiceMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sourceCode;
	
	private String clientId;
	
	private String commentID;
	
	private String statusIndicator;
	
	private String fullName;
	
	private String country;
	
	private String dob;
	
	private String recordeType;

	/**
	 * @return the sourceCode
	 */
	public String getSourceCode() {
		return sourceCode;
	}

	/**
	 * @param sourceCode the sourceCode to set
	 */
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	/**
	 * @return the clientId
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * @param clientId the clientId to set
	 */
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * @return the commentID
	 */
	public String getCommentID() {
		return commentID;
	}

	/**
	 * @param commentID the commentID to set
	 */
	public void setCommentID(String commentID) {
		this.commentID = commentID;
	}

	/**
	 * @return the statusIndicator
	 */
	public String getStatusIndicator() {
		return statusIndicator;
	}

	/**
	 * @param statusIndicator the statusIndicator to set
	 */
	public void setStatusIndicator(String statusIndicator) {
		this.statusIndicator = statusIndicator;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the dob
	 */
	public String getDob() {
		return dob;
	}

	/**
	 * @param dob the dob to set
	 */
	public void setDob(String dob) {
		this.dob = dob;
	}

	/**
	 * @return the recordeType
	 */
	public String getRecordeType() {
		return recordeType;
	}

	/**
	 * @param recordeType the recordeType to set
	 */
	public void setRecordeType(String recordeType) {
		this.recordeType = recordeType;
	}
	
	
	
}
