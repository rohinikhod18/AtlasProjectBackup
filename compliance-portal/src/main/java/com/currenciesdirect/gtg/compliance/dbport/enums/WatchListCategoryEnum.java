package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum WatchListCategory.
 */
public enum WatchListCategoryEnum {
	
	/** The category 1. */
	CATEGORY_1(1,"canManageWatchListCategory1","High"),
	/** The category 2. */
	CATEGORY_2(2,"canManageWatchListCategory2", "Low");
	/** The category db status. */
	private Integer categoryDbStatus;
	
	/** The ui status. */
	private String functionName;
	
	/** The desc. */
	private String desc;

	/**
	 * Instantiates a new watch list category.
	 *
	 * @param categoryDbStatus the category db status
	 * @param uiStatus the ui status
	 * @param status the status
	 */
	WatchListCategoryEnum (Integer categoryDbStatus,String uiStatus, String status){
		this.categoryDbStatus = categoryDbStatus;
		this.functionName = uiStatus;
		this.desc = status;
	}
	/**
	 * Gets the category db status.
	 *
	 * @return the categoryDbStatus
	 */
	public Integer getCategoryDbStatus() {
		return categoryDbStatus;
	}

	/**
	 * Gets the DB status from funcation name.
	 *
	 * @param functionName the function name
	 * @return the DB status from funcation name
	 */
	public Integer getDBStatusFromFuncationName(String functionName){
		for(WatchListCategoryEnum wEnum: WatchListCategoryEnum.values()){
			if(wEnum.getFunctionName().equalsIgnoreCase(functionName))
				return wEnum.getCategoryDbStatus();
		}
		return null;
	}


	/**
	 * Gets the ui status.
	 *
	 * @return the uiStatus
	 */
	public String getFunctionName() {
		return functionName;
	}


	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	
}

