package com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class BulkRecheckResponse.
 */
public class BulkRegRecheckResponse extends ServiceMessageResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The status. */
	@JsonProperty(value = "status")
	/** The status. */
	private String status;

	/** The account ID. */
	@JsonProperty(value = "accountId")
	private Integer accountID;

	/**
	 * Gets the account ID.
	 *
	 * @return the account ID
	 */
	public Integer getAccountID() {
		return accountID;
	}

	/**
	 * Sets the account ID.
	 *
	 * @param accountID
	 *            the new account ID
	 */
	public void setAccountID(Integer accountID) {
		this.accountID = accountID;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
