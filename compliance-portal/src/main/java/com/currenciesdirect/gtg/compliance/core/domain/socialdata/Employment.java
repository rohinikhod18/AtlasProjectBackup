package com.currenciesdirect.gtg.compliance.core.domain.socialdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Employment {
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty("current")
	private boolean current;
	
	@JsonProperty("title")
	private String title;
	
	@JsonProperty("start")
	private Object start;
	
	@JsonProperty("end")
	private Object end;
	
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
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
	 * @return the current
	 */
	public boolean isCurrent() {
		return current;
	}

	/**
	 * @param current the current to set
	 */
	public void setCurrent(boolean current) {
		this.current = current;
	}

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
	 * @return the start
	 */
	public Object getStart() {
		return start;
	}

	/**
	 * @param start the start to set
	 */
	public void setStart(Object start) {
		this.start = start;
	}

	/**
	 * @return the end
	 */
	public Object getEnd() {
		return end;
	}

	/**
	 * @param end the end to set
	 */
	public void setEnd(Object end) {
		this.end = end;
	}
}
