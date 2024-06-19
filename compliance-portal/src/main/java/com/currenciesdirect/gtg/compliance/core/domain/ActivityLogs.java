package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
/**
 * The Class ActivityLogs.
 */
public class ActivityLogs implements IDomain {

	/** The activity log data. */
	private List<ActivityLogDataWrapper> activityLogData;
	
	/** The user. */
	private UserProfile user;

	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;
	
	/** The total records. */
	private Integer totalRecords;
	
	/** The compliance done on. */
	private String complianceDoneOn;
	
	/** The registration in date. */
	private String registrationInDate;
	
	/** The compliance expiry. */
	private String complianceExpiry;
	
	private Boolean isWatchlistUpdated;

	/**
	 * Gets the activity log data.
	 *
	 * @return the activity log data
	 */
	public List<ActivityLogDataWrapper> getActivityLogData() {
		return activityLogData;
	}

	/**
	 * Sets the activity log data.
	 *
	 * @param activityLogData
	 *            the new activity log data
	 */
	public void setActivityLogData(List<ActivityLogDataWrapper> activityLogData) {
		this.activityLogData = activityLogData;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode
	 *            the new error code
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage
	 *            the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public UserProfile getUser() {
		return user;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails#setUser(com.currenciesdirect.gtg.compliance.core.domain.UserProfile)
	 */
	public void setUser(UserProfile user) {
		this.user = user;
	}

	/**
	 * Gets the total records.
	 *
	 * @return the total records
	 */
	public Integer getTotalRecords() {
		return totalRecords;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails#setTotalRecords(java.lang.Integer)
	 */
	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	/**
	 * @return the complianceDoneOn
	 */
	public String getComplianceDoneOn() {
		return complianceDoneOn;
	}

	/**
	 * @param complianceDoneOn the complianceDoneOn to set
	 */
	public void setComplianceDoneOn(String complianceDoneOn) {
		this.complianceDoneOn = complianceDoneOn;
	}

	/**
	 * Gets registration in date
	 * 
	 * @return the registrationInDate
	 */
	public String getRegistrationInDate() {
		return registrationInDate;
	}

	/**
	 *  Sets registration in date
	 * 
	 * @param registrationInDate
	 */
	public void setRegistrationInDate(String registrationInDate) {
		this.registrationInDate = registrationInDate;
	}
	
	/**
	 * Gets compliance expiry
	 * 
	 * @return the complianceExpiry
	 */
	public String getComplianceExpiry() {
		return complianceExpiry;
	}

	/**
	 * Sets compliance expiry
	 * 
	 * @param complianceExpiry
	 */
	public void setComplianceExpiry(String complianceExpiry) {
		this.complianceExpiry = complianceExpiry;
	}

	public Boolean getIsWatchlistUpdated() {
		return isWatchlistUpdated;
	}

	public void setIsWatchlistUpdated(Boolean isWatchlistUpdated) {
		this.isWatchlistUpdated = isWatchlistUpdated;
	}
	
}
