package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class OnfidoProperties.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The score. */
	@ApiModelProperty(value = "The score is a number between 0 and 1 that expresses how similar the two faces are, where 1 is a perfect match", required = true)
	@JsonProperty(value = "score")
	private String score;

	/** The issuing country. */
	@ApiModelProperty(value = "The issuing country of the document, in 3-letter ISO code, specified when uploading it", required = true)
	@JsonProperty(value = "issuing_country")
	private String issuingCountry;
	
	/** The document type. */
	@ApiModelProperty(value = "The document type according to the set of supported documents (e.g. Passport)", required = true)
	@JsonProperty(value = "document_type")
	private String documentType;
	
	/** The first name. */
	@ApiModelProperty(value = "The first name on the document, including any initials and middle names", required = true)
	@JsonProperty(value = "first_name")
	private String firstName;
	
	/** The last name. */
	@ApiModelProperty(value = "The last name on the document", required = true)
	@JsonProperty(value = "last_name")
	private String lastName;
	
	/** The date of birth. */
	@ApiModelProperty(value = "The date of birth", required = true)
	@JsonProperty(value = "date_of_birth")
	private String dateOfBirth;
	
	/** The issuing date. */
	@ApiModelProperty(value = "The issuing date", required = true)
	@JsonProperty(value = "issuing_date")
	private String issuingDate;
	
	/** The expiry date. */
	@ApiModelProperty(value = "The expiry date", required = true)
	@JsonProperty(value = "date_of_expiry")
	private String expiryDate;
	
	/** The document numbers. */
	@ApiModelProperty(value = "The document numbers", required = true)
	@JsonProperty(value = "document_numbers")
	private List<DocumentNumber> documentNumbers = new ArrayList<>();
	
	/** The gender. */
	@ApiModelProperty(value = "The gender of the client", example = "Male", required = true)
	@JsonProperty(value = "gender")
	private String gender;
	
	/**
	 * @return the score
	 */
	public String getScore() {
		return score;
	}

	/**
	 * @param score the score to set
	 */
	public void setScore(String score) {
		this.score = score;
	}

	/**
	 * @return the issuingCountry
	 */
	public String getIssuingCountry() {
		return issuingCountry;
	}

	/**
	 * @param issuingCountry the issuingCountry to set
	 */
	public void setIssuingCountry(String issuingCountry) {
		this.issuingCountry = issuingCountry;
	}

	/**
	 * @return the documentType
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType the documentType to set
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/**
	 * @return the issuingDate
	 */
	public String getIssuingDate() {
		return issuingDate;
	}

	/**
	 * @param issuingDate the issuingDate to set
	 */
	public void setIssuingDate(String issuingDate) {
		this.issuingDate = issuingDate;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the documentNumbers
	 */
	public List<DocumentNumber> getDocumentNumbers() {
		return documentNumbers;
	}

	/**
	 * @param documentNumbers the documentNumbers to set
	 */
	public void setDocumentNumbers(List<DocumentNumber> documentNumbers) {
		this.documentNumbers = documentNumbers;
	}
	
}
