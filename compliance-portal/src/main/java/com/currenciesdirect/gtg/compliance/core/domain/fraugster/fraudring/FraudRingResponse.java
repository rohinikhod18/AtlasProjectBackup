package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRingResponse {
	
	@JsonProperty("results")
	private List <FraudRingNeo4jResult> results;
	
	@JsonProperty("errors")
	private List <FraudRingNeo4jError> errors;

	/**
	 * @return the results
	 */
	public List<FraudRingNeo4jResult> getResults() {
		return results;
	}

	/**
	 * @return the errors
	 */
	public List<FraudRingNeo4jError> getErrors() {
		return errors;
	}

	/**
	 * @param results the results to set
	 */
	public void setResults(List<FraudRingNeo4jResult> results) {
		this.results = results;
	}

	/**
	 * @param errors the errors to set
	 */
	public void setErrors(List<FraudRingNeo4jError> errors) {
		this.errors = errors;
	}

}
