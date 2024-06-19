package com.currenciesdirect.gtg.compliance.core.domain.report.dashboard;

/**
 * The Class TimelineSnapshot.
 * @author abhijeetg
 */
public class TimelineSnapshot {

	/** The oldest record. */
	private String oldestRecord;
	
	/** The avg record age. */
	private String avgRecordAge;

	/** The newest record. */
	private String newestRecord;
	
	/** The old pfx contact id. */
	private String oldPfxContactId;
	
	/** The old cfx contact id. */
	private String oldCfxContactId;
	
	/** The new pfx contact id. */
	private String newPfxContactId;
	
	/** The new cfx contact id. */
	private String newCfxContactId;
	
	private String oldCustomerType;
	
	private String newCustomerType;

	public String getOldCustomerType() {
		return oldCustomerType;
	}

	public void setOldCustomerType(String oldCustomerType) {
		this.oldCustomerType = oldCustomerType;
	}

	public String getNewCustomerType() {
		return newCustomerType;
	}

	public void setNewCustomerType(String newCustomerType) {
		this.newCustomerType = newCustomerType;
	}

	/**
	 * Gets the oldest record.
	 *
	 * @return the oldest record
	 */
	public String getOldestRecord() {
		return oldestRecord;
	}

	/**
	 * Sets the oldest record.
	 *
	 * @param oldestRecord the new oldest record
	 */
	public void setOldestRecord(String oldestRecord) {
		this.oldestRecord = oldestRecord;
	}

	/**
	 * Gets the avg record age.
	 *
	 * @return the avg record age
	 */
	public String getAvgRecordAge() {
		return avgRecordAge;
	}

	/**
	 * Sets the avg record age.
	 *
	 * @param avgRecordAge the new avg record age
	 */
	public void setAvgRecordAge(String avgRecordAge) {
		this.avgRecordAge = avgRecordAge;
	}

	/**
	 * Gets the newest record.
	 *
	 * @return the newest record
	 */
	public String getNewestRecord() {
		return newestRecord;
	}

	/**
	 * Sets the newest record.
	 *
	 * @param newestRecord the new newest record
	 */
	public void setNewestRecord(String newestRecord) {
		this.newestRecord = newestRecord;
	}

	/**
	 * Gets the old pfx contact id.
	 *
	 * @return the old pfx contact id
	 */
	public String getOldPfxContactId() {
		return oldPfxContactId;
	}

	/**
	 * Sets the old pfx contact id.
	 *
	 * @param oldPfxContactId the new old pfx contact id
	 */
	public void setOldPfxContactId(String oldPfxContactId) {
		this.oldPfxContactId = oldPfxContactId;
	}

	/**
	 * Gets the old cfx contact id.
	 *
	 * @return the old cfx contact id
	 */
	public String getOldCfxContactId() {
		return oldCfxContactId;
	}

	/**
	 * Sets the old cfx contact id.
	 *
	 * @param oldCfxContactId the new old cfx contact id
	 */
	public void setOldCfxContactId(String oldCfxContactId) {
		this.oldCfxContactId = oldCfxContactId;
	}

	/**
	 * Gets the new pfx contact id.
	 *
	 * @return the new pfx contact id
	 */
	public String getNewPfxContactId() {
		return newPfxContactId;
	}

	/**
	 * Sets the new pfx contact id.
	 *
	 * @param newPfxContactId the new new pfx contact id
	 */
	public void setNewPfxContactId(String newPfxContactId) {
		this.newPfxContactId = newPfxContactId;
	}

	/**
	 * Gets the new cfx contact id.
	 *
	 * @return the new cfx contact id
	 */
	public String getNewCfxContactId() {
		return newCfxContactId;
	}

	/**
	 * Sets the new cfx contact id.
	 *
	 * @param newCfxContactId the new new cfx contact id
	 */
	public void setNewCfxContactId(String newCfxContactId) {
		this.newCfxContactId = newCfxContactId;
	}
	
}
