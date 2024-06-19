package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRingNeo4jNodes {

	@JsonProperty("id")
	private String id; 
		
	@JsonProperty("labels")
	private List<String> labels = new ArrayList<>();
	
	@JsonProperty("properties")
	private FraudRingNeo4jProperties properties;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the labels
	 */
	public List<String> getLabels() {
		return labels;
	}

	/**
	 * @param labels the labels to set
	 */
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	/**
	 * @return the properties
	 */
	public FraudRingNeo4jProperties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(FraudRingNeo4jProperties properties) {
		this.properties = properties;
	}
}
