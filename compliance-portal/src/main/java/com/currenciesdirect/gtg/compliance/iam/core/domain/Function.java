package com.currenciesdirect.gtg.compliance.iam.core.domain;

/**
 * The Class Function.
 */
public class Function {

	private String name;
	
	private Boolean hasAccess;
	
	private Boolean hasOverrideAccess;

	/**
	 * Instantiates a new function.
	 */
	public Function(){
		super();
		this.hasAccess = Boolean.FALSE;
		this.hasOverrideAccess = Boolean.FALSE;
	}
	
	/**
	 * Instantiates a new function.
	 *
	 * @param name the name
	 */
	public Function(String name) {
		super();
		this.name = name;
		this.hasAccess = Boolean.FALSE;
		this.hasOverrideAccess = Boolean.FALSE;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getHasAccess() {
		return hasAccess;
	}

	public void setHasAccess(Boolean hasAccess) {
		this.hasAccess = hasAccess;
	}

	public Boolean getHasOverrideAccess() {
		return hasOverrideAccess;
	}

	public void setHasOverrideAccess(Boolean hasOverrideAccess) {
		this.hasOverrideAccess = hasOverrideAccess;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Function other = (Function) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name)) {
			return false;
		  }
		return true;
	}

	@Override
	public String toString() {
		return "Function [name=" + name + ", hasAccess=" + hasAccess + ", hasOverrideAccess=" + hasOverrideAccess + "]";
	}
	
}
