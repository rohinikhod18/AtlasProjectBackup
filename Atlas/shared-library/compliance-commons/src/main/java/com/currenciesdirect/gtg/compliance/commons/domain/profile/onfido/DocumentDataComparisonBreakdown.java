package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DocumentDataComparisonBreakdown.
 */
public class DocumentDataComparisonBreakdown implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The expiry date. */
	@ApiModelProperty(value = "The expiry date", required = true)
	@JsonProperty(value = "date_of_expiry")
	private BreakdownEntityDetails expiryDate;
	
	/** The issuing country. */
	@ApiModelProperty(value = "The issuing country", required = true)
	@JsonProperty(value = "issuing_country")
	private BreakdownEntityDetails issuingCountry;
	
	/** The document type. */
	@ApiModelProperty(value = "The document type", required = true)
	@JsonProperty(value = "document_type")
	private BreakdownEntityDetails documentType;
	
	/** The document numbers. */
	@ApiModelProperty(value = "The document numbers", required = true)
	@JsonProperty(value = "document_numbers")
	private BreakdownEntityDetails documentNumbers;
	
	/** The gender. */
	@ApiModelProperty(value = "The gender of the client", example = "Male", required = true)
	@JsonProperty(value = "gender")
	private BreakdownEntityDetails gender;
	
	/** The birth date. */
	@ApiModelProperty(value = "The birth date", required = true)
	@JsonProperty(value = "date_of_birth")
	private BreakdownEntityDetails birthDate;
	
	/** The last name. */
	@ApiModelProperty(value = "The last name", required = true)
	@JsonProperty(value = "last_name")
	private BreakdownEntityDetails lastName;
	
	/** The first name. */
	@ApiModelProperty(value = "The client's first name", example = "Robert", required = true)
	@JsonProperty(value = "first_name")
	private BreakdownEntityDetails firstName;

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
	 * @return the issuingCountry
	 */
	public BreakdownEntityDetails getIssuingCountry() {
		return issuingCountry;
	}

	/**
	 * @param issuingCountry the issuingCountry to set
	 */
	public void setIssuingCountry(BreakdownEntityDetails issuingCountry) {
		this.issuingCountry = issuingCountry;
	}

	/**
	 * @return the documentType
	 */
	public BreakdownEntityDetails getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(BreakdownEntityDetails documentType) {
		this.documentType = documentType;
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
	 * @return the lastName
	 */
	public BreakdownEntityDetails getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(BreakdownEntityDetails lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the firstName
	 */
	public BreakdownEntityDetails getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(BreakdownEntityDetails firstName) {
		this.firstName = firstName;
	}
	
	
}
