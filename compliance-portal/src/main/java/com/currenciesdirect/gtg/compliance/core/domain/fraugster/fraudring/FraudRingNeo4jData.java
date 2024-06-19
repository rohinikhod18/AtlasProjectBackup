package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRingNeo4jData {

	@JsonProperty("graph")
	private FraudRingNeo4jGraph graph;

	/**
	 * @return the graph
	 */
	public FraudRingNeo4jGraph getGraph() {
		return graph;
	}

	/**
	 * @param graph the graph to set
	 */
	public void setGraph(FraudRingNeo4jGraph graph) {
		this.graph = graph;
	}
}
