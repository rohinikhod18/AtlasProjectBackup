package com.currenciesdirect.gtg.compliance.blacklist.core.domain;

import java.sql.Timestamp;
import java.util.Arrays;

import com.currenciesdirect.gtg.compliance.blacklist.core.IRequest;

/**
 * The Class BlacklistUpdateRequest.
 *
 * @author Rajesh
 */
public class BlacklistUpdateRequest implements IRequest {

	/** The data. */
	private BlacklistUpdateData[] data;
	
	/** The created by. */
	private String createdBy;
	
	/** The created on. */
	private Timestamp createdOn;
	
	/** The updated by. */
	private String updatedBy;
	
	/** The updated on. */
	private Timestamp updatedOn;
	
	/** The correlation id. */
	private String correlationId;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public BlacklistUpdateData[] getData() {
		return data;
	}

	/**
	 * Sets the data.
	 *
	 * @param data the new data
	 */
	public void setData(BlacklistUpdateData[] data) {
		this.data = data;
	}

	/**
	 * Gets the created by.
	 *
	 * @return the created by
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * Sets the created by.
	 *
	 * @param createdBy the new created by
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the created on.
	 *
	 * @return the created on
	 */
	public Timestamp getCreatedOn() {
		return createdOn;
	}

	/**
	 * Sets the created on.
	 *
	 * @param createdOn the new created on
	 */
	public void setCreatedOn(Timestamp createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * Gets the updated by.
	 *
	 * @return the updated by
	 */
	public String getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the updated on.
	 *
	 * @return the updated on
	 */
	public Timestamp getUpdatedOn() {
		return updatedOn;
	}

	/**
	 * Sets the updated on.
	 *
	 * @param updatedOn the new updated on
	 */
	public void setUpdatedOn(Timestamp updatedOn) {
		this.updatedOn = updatedOn;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
	}

	/**
	 * Sets the correlation id.
	 *
	 * @param correlationId the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdOn == null) ? 0 : createdOn.hashCode());
		result = prime * result + Arrays.hashCode(data);
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedOn == null) ? 0 : updatedOn.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlacklistUpdateRequest other = (BlacklistUpdateRequest) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdOn == null) {
			if (other.createdOn != null)
				return false;
		} else if (!createdOn.equals(other.createdOn))
			return false;
		if (!Arrays.equals(data, other.data))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedOn == null) {
			if (other.updatedOn != null)
				return false;
		} else if (!updatedOn.equals(other.updatedOn))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BlacklistUpdateRequest [data=" + Arrays.toString(data)
				+ ", createdBy=" + createdBy + ", createdOn=" + createdOn
				+ ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn
				+ ", correlationId=" + correlationId + "]";
	}
	
	
}
