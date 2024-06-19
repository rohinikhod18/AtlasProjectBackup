package com.currenciesdirect.gtg.compliance.compliancesrv.core;

/**
 * The Enum EntityEnum.
 */
public enum EntityEnum {
	
	/** The unknown. */
	UNKNOWN(0,"UNKNOWN"),
	
	/** The account. */
	ACCOUNT(1,"ACCOUNT"),
	
	/** The beneficiary. */
	BENEFICIARY(2, "BENEFICIARY"),
	
	/** The contact. */
	CONTACT(3,"CONTACT"),
	
	/** The bank. */
	BANK(4,"BANK"),
	
	DEBTOR(5,"DEBTOR");

	/** The entity type as integer. */
	private Integer entityTypeAsInteger;
	
	/** The entity type as string. */
	private String entityTypeAsString;

	/**
	 * Instantiates a new entity enum.
	 *
	 * @param entityTypeAsInteger the entity type as integer
	 * @param entityTypeAsString the entity type as string
	 */
	EntityEnum(Integer entityTypeAsInteger, String entityTypeAsString) {
		this.entityTypeAsInteger = entityTypeAsInteger;
		this.entityTypeAsString = entityTypeAsString;
	}

	/**
	 * Gets the entity type as integer.
	 *
	 * @return the entity type as integer
	 */
	public Integer getEntityTypeAsInteger() {
		return entityTypeAsInteger;
	}

	/**
	 * Gets the entity type as string.
	 *
	 * @return the entity type as string
	 */
	public String getEntityTypeAsString() {
		return entityTypeAsString;
	}

	/**
	 * Gets the entity type as string.
	 *
	 * @param type the type
	 * @return the entity type as string
	 */
	public static String getEntityTypeAsString(Integer type){
		for(EntityEnum entity :EntityEnum.values()){
			if(entity.getEntityTypeAsInteger().equals(type)){
				return entity.getEntityTypeAsString();
			}
		}
		return null;
	}
	
	/**
	 * Gets the entity type as integer.
	 *
	 * @param type the type
	 * @return the entity type as integer
	 */
	public static Integer getEntityTypeAsInteger(String type){
		for(EntityEnum entity :EntityEnum.values()){
			if(entity.getEntityTypeAsString().equals(type)){
				return entity.getEntityTypeAsInteger();
			}
		}
		return null;
	}

}
