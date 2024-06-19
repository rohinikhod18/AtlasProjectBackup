package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRingNeo4jGraph {
	
	@JsonProperty("nodes")
	private List <FraudRingNeo4jNodes> nodes;
	
	@JsonProperty("relationships")
	private List <FraudRingNeo4jRelationships> relationships;
	
	/**
	 * @return the nodes
	 */
	public List<FraudRingNeo4jNodes> getNodes() {
		return nodes;
	}

	/**
	 * @return the relationships
	 */
	public List<FraudRingNeo4jRelationships> getRelationships() {
		return relationships;
	}

	/**
	 * @param nodes the nodes to set
	 */
	public void setNodes(List<FraudRingNeo4jNodes> nodes) {
		this.nodes = nodes;
	}

	/**
	 * @param relationships the relationships to set
	 */
	public void setRelationships(List<FraudRingNeo4jRelationships> relationships) {
		this.relationships = relationships;
	}
}
