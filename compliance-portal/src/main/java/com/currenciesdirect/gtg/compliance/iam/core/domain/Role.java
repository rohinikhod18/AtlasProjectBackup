package com.currenciesdirect.gtg.compliance.iam.core.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * The Class Role.
 */
public class Role {

	/** The name. */
	private Set<String> names;
	
	/** The functions. */
	private List<Function> functions;
	
	
	/**
	 * Instantiates a new role.
	 */
	public Role(){
		functions = new ArrayList<>();
	}


	public Set<String> getNames() {
		return names;
	}

	public void setNames(Set<String> names) {
		this.names = names;
	}
	
	/**
	 * Clear.
	 */
	public void clear(){
		functions.clear();
	}
	
	/**
	 * Gets the function group.
	 *
	 * @param functionName the function name
	 * @return the function group
	 */
	public Function getFunctionByName(String functionName){
		Function function = new Function(functionName);
		int index = functions.indexOf(function);
		if( index == -1 ){
			functions.add(function);
			return function;
		}
		return functions.get(index);
	}
	
	
	/**
	 * Adds the function by name.
	 *
	 * @param functionName the function name
	 * @return true, if successful
	 */
	public boolean addFunctionByName(String functionName){
		 return functions.add(new Function(functionName));
	}

	/**
	 * Gets the functions.
	 *
	 * @return the functions
	 */
	public List<Function> getFunctions() {
		return functions;
	}

	/**
	 * Sets the functions.
	 *
	 * @param functions the new functions
	 */
	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}
	
	/**
	 * Checks for functions.
	 *
	 * @param functions the functions
	 * @param checkAll the check all
	 * @return true, if successful
	 */
	public boolean hasFunctions(String[] functions,boolean checkAll){
		if(checkAll){
			return hasAllFunctions(functions);
		} else {
			return hasAnyOneFunctions(functions);
		}
	}
	
	/**
	 * Checks for all functions.
	 *
	 * @param functions the functions
	 * @return true, if successful
	 */
	public boolean hasAllFunctions(String[] functions){
		for(String fun : functions){
			Function function = new Function(fun);
			int index = this.functions.indexOf(function);
			if( index == -1 || Boolean.FALSE.equals(this.functions.get(index).getHasAccess())){
				return false;
			} 
		}
		return true;
	}
	
	/**
	 * Checks for any one functions.
	 *
	 * @param functions the functions
	 * @return true, if successful
	 */
	public boolean hasAnyOneFunctions(String[] functions){
		for(String fun : functions){
			Function function = new Function(fun);
			int index = this.functions.indexOf(function);
			if( index != -1 && Boolean.TRUE.equals(this.functions.get(index).getHasAccess())){
				return true;
			} 
		}
		return false;
	}
 }
