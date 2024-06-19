package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class BaseViewMoreRequest.
 */
public class BaseViewMoreRequest {

	/** The entity id. */
	private Integer entityId;
	
	/** The service type. */
	private String serviceType;
	
	/** The entity type. */
	private String entityType;
	
	/** The no of display records. */
	private Integer noOfDisplayRecords;
	
	/** The max view record. */
	private Integer maxViewRecord;
	
	/** The min view record. */
	private Integer minViewRecord;
	
	/** The organisation. */
	private String organisation;

	/** The type. */
	private String clientType;
	
	/** The account id. */
	private Integer accountId;
	
	/**
	 * @return the entityId
	 */
	public Integer getEntityId() {
		return entityId;
	}

	/**
	 * @param entityId the entityId to set
	 */
	public void setEntityId(Integer entityId) {
		this.entityId = entityId;
	}

	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}

	/**
	 * @return the entityType
	 */
	public String getEntityType() {
		return entityType;
	}

	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	/**
	 * @param entityType the entityType to set
	 */
	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	/**
	 * @return the noOfDisplayRecords
	 */
	public Integer getNoOfDisplayRecords() {
		return noOfDisplayRecords;
	}

	/**
	 * @param noOfDisplayRecords the noOfDisplayRecords to set
	 */
	public void setNoOfDisplayRecords(Integer noOfDisplayRecords) {
		this.noOfDisplayRecords = noOfDisplayRecords;
	}

	/**
	 * @return the maxViewRecord
	 */
	public Integer getMaxViewRecord() {
		return maxViewRecord;
	}

	/**
	 * @return the minViewRecord
	 */
	public Integer getMinViewRecord() {
		return minViewRecord;
	}

	/**
	 * @param maxViewRecord the maxViewRecord to set
	 */
	public void setMaxViewRecord(Integer maxViewRecord) {
		this.maxViewRecord = maxViewRecord;
	}

	/**
	 * @param minViewRecord the minViewRecord to set
	 */
	public void setMinViewRecord(Integer minViewRecord) {
		this.minViewRecord = minViewRecord;
	}

	/**
	 * @return the organisation
	 */
	public String getOrganisation() {
		return organisation;
	}

	/**
	 * @param organisation the organisation to set
	 */
	public void setOrganisation(String organisation) {
		this.organisation = organisation;
	}

	/**
	 * @return the clientType
	 */
	public String getClientType() {
		return clientType;
	}

	/**
	 * @param clientType the clientType to set
	 */
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	/**
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

}
