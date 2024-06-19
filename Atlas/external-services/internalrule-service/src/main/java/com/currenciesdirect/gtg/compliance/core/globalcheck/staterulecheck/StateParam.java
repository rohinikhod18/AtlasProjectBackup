package com.currenciesdirect.gtg.compliance.core.globalcheck.staterulecheck;

/**
 * The Class StateParam.
 */
public class StateParam {

	/** The iso alpha 3 country code. */
	private String isoAlpha3CountryCode;

	/** The state name. */
	private String stateName;

	/** The organization code. */
	private String organizationCode;
	
	/** The org legal entity. */
	private String orgLegalEntity;

	/** The result. */
	private String result;

	/**
	 * Instantiates a new state param.
	 *
	 * @param isoAlpha3CountryCode
	 *            the iso alpha 3 country code
	 * @param stateName
	 *            the state name
	 * @param organizationCode
	 *            the organization code
	 */
	public StateParam(String isoAlpha3CountryCode, String stateName, String organizationCode,
			String orgLegalEntity) {
		super();
		this.isoAlpha3CountryCode = isoAlpha3CountryCode;
		this.stateName = stateName;
		this.organizationCode = organizationCode;
		this.orgLegalEntity = orgLegalEntity;
	}

	/**
	 * Gets the iso alpha 3 country code.
	 *
	 * @return the iso alpha 3 country code
	 */
	public String getIsoAlpha3CountryCode() {
		return isoAlpha3CountryCode;
	}

	/**
	 * Sets the iso alpha 3 country code.
	 *
	 * @param isoAlpha3CountryCode
	 *            the new iso alpha 3 country code
	 */
	public void setIsoAlpha3CountryCode(String isoAlpha3CountryCode) {
		this.isoAlpha3CountryCode = isoAlpha3CountryCode;
	}

	/**
	 * Gets the organization code.
	 *
	 * @return the organization code
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * Sets the organization code.
	 *
	 * @param organizationCode
	 *            the new organization code
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * Gets the state name.
	 *
	 * @return the state name
	 */
	public String getStateName() {
		return stateName;
	}

	/**
	 * Sets the state name.
	 *
	 * @param stateName
	 *            the new state name
	 */
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	/**
	 * Gets the org legal entity.
	 *
	 * @return the org legal entity
	 */
	public String getOrgLegalEntity() {
		return orgLegalEntity;
	}

	/**
	 * Sets the org legal entity.
	 *
	 * @param orgLegalEntity
	 *            the new org legal entity
	 */
	public void setOrgLegalEntity(String orgLegalEntity) {
		this.orgLegalEntity = orgLegalEntity;
	}

	/**
	 * Gets the result.
	 *
	 * @return the result
	 */
	public String getResult() {
		return result;
	}

	/**
	 * Sets the result.
	 *
	 * @param result
	 *            the new result
	 */
	public void setResult(String result) {
		this.result = result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int res = 1;
		res = prime * res + ((stateName == null) ? 0 : stateName.hashCode());
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StateParam other = (StateParam) obj;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName)) {
			return false;
		}
		return true;
	}

}
