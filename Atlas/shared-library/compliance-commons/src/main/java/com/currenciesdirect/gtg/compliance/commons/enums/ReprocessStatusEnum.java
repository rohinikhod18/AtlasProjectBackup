package com.currenciesdirect.gtg.compliance.commons.enums;


/**
 * The Enum ReprocessStatusEnum.
 */
public enum ReprocessStatusEnum {

	
	/** The failed. */
	FAILED(1,"FAILED"),
	
	/** The inprogress. */
	INPROGRESS(2,"INPROGRESS"),
	
	/** The done. */
	DONE(3,"DONE");

	/** The reprocess status as integer. */
	private Integer reprocessStatusAsInteger;
	
	/** The reprocess status as string. */
	private String reprocessStatusAsString;

	/**
	 * Instantiates a new reprocess status enum.
	 *
	 * @param reprocessStatusAsInteger the reprocess status as integer
	 * @param reprocessStatusAsString the reprocess status as string
	 */
	ReprocessStatusEnum(Integer reprocessStatusAsInteger, String reprocessStatusAsString) {
		this.reprocessStatusAsInteger = reprocessStatusAsInteger;
		this.reprocessStatusAsString = reprocessStatusAsString;
	}


	/**
	 * Gets the reprocess status as integer.
	 *
	 * @return the reprocess status as integer
	 */
	public Integer getReprocessStatusAsInteger() {
		return reprocessStatusAsInteger;
	}

	/**
	 * Gets the reprocess status as string.
	 *
	 * @return the reprocess status as string
	 */
	public String getReprocessStatusAsString() {
		return reprocessStatusAsString;
	}
	
	
	/**
	 * Gets the reprocess status as string.
	 *
	 * @param type the type
	 * @return the reprocess status as string
	 */
	public static String getReprocessStatusAsString(Integer type) {
		for (ReprocessStatusEnum status : ReprocessStatusEnum.values()) {
			if (status.getReprocessStatusAsInteger().equals(type)) {
				return status.getReprocessStatusAsString();
			}
		}
		return null;
	}

	/**
	 * Gets the reprocess status as integer.
	 *
	 * @param type the type
	 * @return the reprocess status as integer
	 */
	public static Integer getReprocessStatusAsInteger(String type) {
		for (ReprocessStatusEnum status : ReprocessStatusEnum.values()) {
			if (status.getReprocessStatusAsString().equals(type)) {
				return status.getReprocessStatusAsInteger();
			}
		}
		return null;
	}



}
