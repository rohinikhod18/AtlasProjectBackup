package com.currenciesdirect.gtg.compliance.core.domain.blacklist;

import com.currenciesdirect.gtg.compliance.commons.domain.IRequest;

/**
 * The Class BlacklistUpdateRequest.
 *
 * @author Rajesh
 */
public class BlacklistUpdateRequest extends BaseBlackListRequest implements IRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** The data. */
	private BlacklistUpdateData[] updateData;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public BlacklistUpdateData[] getData() {
		return updateData;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData(BlacklistUpdateData[] data) {
		this.updateData = data;
	}

}
