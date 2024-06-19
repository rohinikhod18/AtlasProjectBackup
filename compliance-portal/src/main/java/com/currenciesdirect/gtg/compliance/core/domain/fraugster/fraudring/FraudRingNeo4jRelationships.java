package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRingNeo4jRelationships {

	@JsonProperty("id")
	private String id;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("startNode")
	private String startNode;
	
	@JsonProperty("endNode")
	private String endNode;
	 
	@JsonProperty("properties")
	private FraudRingNeo4jProperties properties;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the startNode
	 */
	public String getStartNode() {
		return startNode;
	}

	/**
	 * @return the endNode
	 */
	public String getEndNode() {
		return endNode;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @param startNode the startNode to set
	 */
	public void setStartNode(String startNode) {
		this.startNode = startNode;
	}

	/**
	 * @param endNode the endNode to set
	 */
	public void setEndNode(String endNode) {
		this.endNode = endNode;
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
