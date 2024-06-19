package com.currenciesdirect.gtg.compliance.iam.core.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class FunctionGroup.
 */
public class FunctionGroup {
	
	private String name;
	
	private List<Function> functions;
	
	/**
	 * Instantiates a new function group.
	 */
	public FunctionGroup(){
		functions = new ArrayList<>();
	}

	/**
	 * Instantiates a new function group.
	 *
	 * @param name the name
	 */
	public FunctionGroup(String name){
		this();
		this.name = name;
	}
	
	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
