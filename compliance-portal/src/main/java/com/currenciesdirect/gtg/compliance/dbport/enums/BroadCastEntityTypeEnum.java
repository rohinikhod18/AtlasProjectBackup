package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum BroadCastEntityTypeEnum.
 */
public enum BroadCastEntityTypeEnum {
	
	/** The signup. */
	SIGNUP(1,"SIGNUP"),
	
	/** The addcontact. */
	ADDCONTACT(2,"ADDCONTACT"),
	
	/** The update. */
	UPDATE(3,"UPDATE"),
	
	/** The paymentin. */
	PAYMENTIN(4, "PAYMENTIN"),
	
	/** The paymentout. */
	PAYMENTOUT(5, "PAYMENTOUT");

	/** The broad cast entity type as integer. */
	private Integer broadCastEntityTypeAsInteger;
	
	/** The broad cast entity type as string. */
	private String broadCastEntityTypeAsString;

	/**
	 * Instantiates a new broad cast entity type enum.
	 *
	 * @param broadCastEntityTypeAsInteger the broad cast entity type as integer
	 * @param broadCastEntityTypeAsString the broad cast entity type as string
	 */
	BroadCastEntityTypeEnum(Integer broadCastEntityTypeAsInteger, String broadCastEntityTypeAsString) {
		this.broadCastEntityTypeAsInteger = broadCastEntityTypeAsInteger;
		this.broadCastEntityTypeAsString = broadCastEntityTypeAsString;
	}

	/**
	 * Gets the broad cast entity type as integer.
	 *
	 * @return the broad cast entity type as integer
	 */
	public Integer getBroadCastEntityTypeAsInteger() {
		return broadCastEntityTypeAsInteger;
	}

	/**
	 * Gets the broad cast entity type as string.
	 *
	 * @return the broad cast entity type as string
	 */
	public String getBroadCastEntityTypeAsString() {
		return broadCastEntityTypeAsString;
	}

	/**
	 * Gets the braod cast entity type as string.
	 *
	 * @param type the type
	 * @return the braod cast entity type as string
	 */
	public static String getBraodCastEntityTypeAsString(Integer type) {
		for (BroadCastEntityTypeEnum statusEnum : BroadCastEntityTypeEnum.values()) {
			if (statusEnum.getBroadCastEntityTypeAsInteger().equals(type)) {
				return statusEnum.getBroadCastEntityTypeAsString();
			}
		}
		return null;
	}

	/**
	 * Gets the braod cast entity type as integer.
	 *
	 * @param type the type
	 * @return the braod cast entity type as integer
	 */
	public static Integer getBraodCastEntityTypeAsInteger(String type) {
		for (BroadCastEntityTypeEnum statusEnum : BroadCastEntityTypeEnum.values()) {
			if (statusEnum.getBroadCastEntityTypeAsString().equals(type)) {
				return statusEnum.getBroadCastEntityTypeAsInteger();
			}
		}
		return null;
	}

}
