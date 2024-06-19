package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FraudRingNeo4jResult {
	
	@JsonProperty("columns")
	private List<String> columns = new ArrayList<>();
	
	@JsonProperty("data")
	private List <FraudRingNeo4jData> data;

	/**
	 * @return the data
	 */
	public List<FraudRingNeo4jData> getData() {
		return data;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setData(List<FraudRingNeo4jData> data) {
		this.data = data;
	}

	/**
	 * @return the columns
	 */
	public List<String> getColumns() {
		return columns;
	}

	/**
	 * @param columns the columns to set
	 */
	public void setColumns(List<String> columns ) {
		this.columns = columns;
	}
}
