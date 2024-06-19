package com.currenciesdirect.gtg.compliance.dbport.countrycheck;

/**
 * The Enum DBQueryConstant.
 *
 * @author Rajesh
 */
public enum DBQueryConstant {

	GET_COUNTRY_STATUS("SELECT RiskLevel FROM Country WHERE DisplayName=?");

	private String query;

	DBQueryConstant(String query) {
		this.query = query;
	}

	public String getQuery() {
		return this.query;
	}

}

