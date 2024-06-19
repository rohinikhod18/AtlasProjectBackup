package com.currenciesdirect.gtg.compliance.commons.enums;

public enum EventServiceLogServiceTypeEnum {
	
	/** The kyc. */
	KYC(1,"KYC"),
	
	/** The internalruleservice. */
	INTERNALRULESERVICE(2,"INTERNALRULESERVICE"),
	
	/** The blacklist. */
	BLACKLIST(3,"BLACKLIST"),
	
	/** The ip. */
	IP(4,"IP"),
	
	/** The globalcheck. */
	GLOBALCHECK(5,"GLOBALCHECK"),
	
	/** The fraugster. */
	FRAUGSTER(6,"FRAUGSTER"),
	
	/** The sanction. */
	SANCTION(7,"SANCTION"),
	
	/** The countrycheck. */
	COUNTRYCHECK(8,"COUNTRYCHECK"),
	
	/** The velocitycheck. */
	VELOCITYCHECK(9,"VELOCITYCHECK"),
	
	/** The mq. */
	MQ(10,"MQ"),
	
	/** The cardfraudscore. */
	CARDFRAUDSCORE(11,"CARDFRAUDSCORE"),
	
	/** The data lake. */
	DATA_LAKE(12,"DATA_LAKE"),
	
	/** The onfido. */
	ONFIDO(13,"ONFIDO");
	
	/** The event service type as integer. */
	private Integer eventServiceTypeAsInteger;
	
	/** The event service type as string. */
	private String eventServiceTypeAsString;
	
	/**
	 * Instantiates a new event service type enum.
	 *
	 * @param eventServiceTypeAsInteger the event service type as integer
	 * @param eventServiceTypeAsString the event service type as string
	 */
	EventServiceLogServiceTypeEnum(Integer eventServiceTypeAsInteger,String eventServiceTypeAsString){
		this.eventServiceTypeAsInteger = eventServiceTypeAsInteger;
		this.eventServiceTypeAsString = eventServiceTypeAsString;
	}

	/**
	 * @return the eventServiceTypeAsInteger
	 */
	public Integer getEventServiceTypeAsInteger() {
		return eventServiceTypeAsInteger;
	}

	/**
	 * @return the eventServiceTypeAsString
	 */
	public String getEventServiceTypeAsString() {
		return eventServiceTypeAsString;
	}
	
	/**
	 * Gets the event service type as string.
	 *
	 * @param type the type
	 * @return the event service type as string
	 */
	public static String getEventServiceTypeAsString(Integer type) {
		for(EventServiceLogServiceTypeEnum typeEnum : EventServiceLogServiceTypeEnum.values()) {
			if(typeEnum.getEventServiceTypeAsInteger().equals(type))
				typeEnum.getEventServiceTypeAsString();
		}
		return null;
	}
	
	/**
	 * Gets the event service type as integer.
	 *
	 * @param type the type
	 * @return the event service type as integer
	 */
	public static Integer getEventServiceTypeAsInteger(String type) {
		for(EventServiceLogServiceTypeEnum typeEnum : EventServiceLogServiceTypeEnum.values()) {
			if(typeEnum.getEventServiceTypeAsString().equals(type))
				typeEnum.getEventServiceTypeAsInteger();
		}
		return null;
	}
		
}