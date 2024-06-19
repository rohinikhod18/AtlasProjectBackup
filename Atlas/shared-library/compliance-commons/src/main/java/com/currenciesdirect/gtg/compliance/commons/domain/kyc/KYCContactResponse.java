/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.kyc;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class KYCContactResponse.
 *
 * @author manish
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class KYCContactResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The provider name. */
	private String providerName;

	/** The provider method. */
	private String providerMethod;

	/** The provider response. */
	private String providerResponse;

	/** The provider unique ref no. */
	private String providerUniqueRefNo;

	/** The band text. */
	private String bandText;

	/** The status. */
	private String status;
	
	/** The overall score. */
	private String overallScore;

	/** The contac S ft id. */
	private String contacSFtId;

	/** The id. */
	private Integer id;

	/** The error code. */
	private String errorCode;

	/** The error description. */
	private String errorDescription;

	/**
	 * Gets the provider name.
	 *
	 * @return the provider name
	 */
	public String getProviderName() {
		return providerName;
	}

	/**
	 * Sets the provider name.
	 *
	 * @param providerName
	 *            the new provider name
	 */
	public void setProviderName(String providerName) {
		this.providerName = providerName;
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
	 * Gets the provider unique ref no.
	 *
	 * @return the provider unique ref no
	 */
	public String getProviderUniqueRefNo() {
		return providerUniqueRefNo;
	}

	/**
	 * Sets the provider unique ref no.
	 *
	 * @param providerUniqueRefNo
	 *            the new provider unique ref no
	 */
	public void setProviderUniqueRefNo(String providerUniqueRefNo) {
		this.providerUniqueRefNo = providerUniqueRefNo;
	}

	/**
	 * Gets the band text.
	 *
	 * @return the band text
	 */
	public String getBandText() {
		return bandText;
	}

	/**
	 * Sets the band text.
	 *
	 * @param bandText
	 *            the new band text
	 */
	public void setBandText(String bandText) {
		this.bandText = bandText;
	}

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
	 * Gets the overall score.
	 *
	 * @return the overall score
	 */
	public String getOverallScore() {
		return overallScore;
	}

	/**
	 * Sets the overall score.
	 *
	 * @param overallScore
	 *            the new overall score
	 */
	public void setOverallScore(String overallScore) {
		this.overallScore = overallScore;
	}

	/**
	 * Gets the contact SF id.
	 *
	 * @return the contact SF id
	 */
	public String getContactSFId() {
		return contacSFtId;
	}

	/**
	 * Sets the contact SF id.
	 *
	 * @param contacSFtId
	 *            the new contact SF id
	 */
	public void setContactSFId(String contacSFtId) {
		this.contacSFtId = contacSFtId;
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
	 * @param id
	 *            the new id
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * Gets the provider method.
	 *
	 * @return the provider method
	 */
	public String getProviderMethod() {
		return providerMethod;
	}

	/**
	 * Sets the provider method.
	 *
	 * @param providerMethod
	 *            the new provider method
	 */
	public void setProviderMethod(String providerMethod) {
		this.providerMethod = providerMethod;
	}

}
