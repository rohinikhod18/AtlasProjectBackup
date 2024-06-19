/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionGetStatusRequest.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.core.domain;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionEntity;

/**
 * The Class SanctionGetStatusRequest.
 *
 * @author manish
 */
public class SanctionGetStatusRequest extends SanctionEntity  implements IDomain, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3126435330438567675L;


	/** The id. */
	private Integer id;
	
	
	/** The application id. */
	private String applicationId;

	/**
	 * Gets the application id.
	 *
	 * @return the application id
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * Sets the application id.
	 *
	 * @param applicationId the new application id
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SanctionGetStatusRequest [sanctionId=" + sanctionId + ", gender=" + gender + ", fullName=" + fullName
				+ ", country=" + country + ", dob=" + dob + ", id=" + id + ", applicationId=" + applicationId + "]";
	}


}
