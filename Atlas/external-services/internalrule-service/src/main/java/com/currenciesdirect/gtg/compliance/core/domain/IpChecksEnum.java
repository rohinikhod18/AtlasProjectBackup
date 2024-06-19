package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Enum IpChecksEnum.
 */
public enum IpChecksEnum {

	/** The ip distance check. */
	IP_DISTANCE_CHECK("IP_DISTANCE_CHECK","Pass : Ip distance less than 200","Failed : Ip distance more than 200");
	
	/** The name. */
	private String name;
	
	/** The pass description. */
	private String passDescription;
	
	/** The fail description. */
	private String failDescription;
	
	/**
	 * Instantiates a new ip checks enum.
	 *
	 * @param name the name
	 * @param passDescription the pass description
	 * @param failDescription the fail description
	 */
	IpChecksEnum(String name,String passDescription,String failDescription){
		this.name = name;
		this.passDescription = passDescription;
		this.failDescription = failDescription;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the pass description.
	 *
	 * @return the pass description
	 */
	public String getPassDescription() {
		return passDescription;
	}

	/**
	 * Gets the fail description.
	 *
	 * @return the fail description
	 */
	public String getFailDescription() {
		return failDescription;
	}
}
