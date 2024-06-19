package com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DataAnonContactDetails implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The title. */
	@ApiModelProperty(value = "The title", required = true)
	@JsonProperty("title")
	private String title;
	
	/** The name. */
	@ApiModelProperty(value = "The name", required = true)
	@JsonProperty("name")
	private String name;
	
	/** The dob. */
	@ApiModelProperty(value = "The dob", required = true)
	@JsonProperty("dob")
	private String dob;
	
	/** The email. */
	@ApiModelProperty(value = "The email", required = true)
	@JsonProperty("email")
	private String email;
	
	/** The phone. */
	@ApiModelProperty(value = "The phone", required = true)
	@JsonProperty("phone")
	private String phone;

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
