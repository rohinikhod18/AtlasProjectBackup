package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DocumentDataValidationBreakdown.
 */
public class DocumentDataValidationBreakdown implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The document expiry. */
	@ApiModelProperty(value = "The document expiry", required = true)
	@JsonProperty(value = "document_expiration")
	private BreakdownEntityDetails documentExpiry;
	
	/** The expiry date. */
	@ApiModelProperty(value = "The expiry date", required = true)
	@JsonProperty(value = "expiry_date")
	private BreakdownEntityDetails expiryDate;
	
	/** The gender. */
	@ApiModelProperty(value = "The gender of the client", example = "Male", required = true)
	@JsonProperty(value = "gender")
	private BreakdownEntityDetails gender;
	
	/** The birth date. */
	@ApiModelProperty(value = "The birth date", required = true)
	@JsonProperty(value = "date_of_birth")
	private BreakdownEntityDetails birthDate;
	
	/** The document numbers. */
	@ApiModelProperty(value = "The document numbers", required = true)
	@JsonProperty(value = "document_numbers")
	private BreakdownEntityDetails documentNumbers;
	
	/** The mrz. */
	@ApiModelProperty(value = "The machine readable zone", required = true)
	@JsonProperty(value = "mrz")
	private BreakdownEntityDetails mrz;

	/**
	 * @return the documentExpiry
	 */
	public BreakdownEntityDetails getDocumentExpiry() {
		return documentExpiry;
	}

	/**
	 * @param documentExpiry the documentExpiry to set
	 */
	public void setDocumentExpiry(BreakdownEntityDetails documentExpiry) {
		this.documentExpiry = documentExpiry;
	}

	/**
	 * @return the expiryDate
	 */
	public BreakdownEntityDetails getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(BreakdownEntityDetails expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the gender
	 */
	public BreakdownEntityDetails getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(BreakdownEntityDetails gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthDate
	 */
	public BreakdownEntityDetails getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate the birthDate to set
	 */
	public void setBirthDate(BreakdownEntityDetails birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the documentNumbers
	 */
	public BreakdownEntityDetails getDocumentNumbers() {
		return documentNumbers;
	}

	/**
	 * @param documentNumbers the documentNumbers to set
	 */
	public void setDocumentNumbers(BreakdownEntityDetails documentNumbers) {
		this.documentNumbers = documentNumbers;
	}

	/**
	 * @return the mrz
	 */
	public BreakdownEntityDetails getMrz() {
		return mrz;
	}

	/**
	 * @param mrz the mrz to set
	 */
	public void setMrz(BreakdownEntityDetails mrz) {
		this.mrz = mrz;
	}
	
	
}
