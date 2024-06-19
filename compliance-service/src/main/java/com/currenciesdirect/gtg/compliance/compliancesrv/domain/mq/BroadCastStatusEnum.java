/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq;

/**
 * The Enum BroadCastStatusEnum.
 *
 * @author manish
 */
public enum BroadCastStatusEnum {

	
	/** The new. */
	NEW(1,"NEW"),
	
	/** The delivered. */
	DELIVERED(2,"DELIVERED"),
	
	/** The failed. */
	FAILED(3,"FAILED");

	/** The broad cast status as integer. */
	private Integer broadCastStatusAsInteger;
	
	/** The broad cast status as string. */
	private String broadCastStatusAsString;

	/**
	 * Instantiates a new broad cast status enum.
 *
	 * @param broadCastStatusAsInteger the broad cast status as integer
	 * @param broadCastStatusAsString the broad cast status as string
 */
	BroadCastStatusEnum(Integer broadCastStatusAsInteger, String broadCastStatusAsString) {
		this.broadCastStatusAsInteger = broadCastStatusAsInteger;
		this.broadCastStatusAsString = broadCastStatusAsString;
	}

	/**
	 * Gets the broad cast status as integer.
	 *
	 * @return the broad cast status as integer
	 */
	public Integer getBroadCastStatusAsInteger() {
		return broadCastStatusAsInteger;
	}

	/**
	 * Gets the broad cast status as string.
	 *
	 * @return the broad cast status as string
	 */
	public String getBroadCastStatusAsString() {
		return broadCastStatusAsString;
	}

	/**
	 * Gets the braod cast status as string.
	 *
	 * @param type the type
	 * @return the braod cast status as string
	 */
	public static String getBraodCastStatusAsString(Integer type) {
		for (BroadCastStatusEnum status : BroadCastStatusEnum.values()) {
			if (status.getBroadCastStatusAsInteger().equals(type)) {
				return status.getBroadCastStatusAsString();
			}
		}
		return null;
	}

	/**
	 * Gets the braod cast status as integer.
	 *
	 * @param type the type
	 * @return the braod cast status as integer
	 */
	public static Integer getBraodCastStatusAsInteger(String type) {
		for (BroadCastStatusEnum status : BroadCastStatusEnum.values()) {
			if (status.getBroadCastStatusAsString().equals(type)) {
				return status.getBroadCastStatusAsInteger();
			}
		}
		return null;
	}

}
