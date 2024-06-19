package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class VelocityCheckSummary.
 */
public class VelocityCheckSummary {
	@JsonProperty("noOffundsoutTxn")
	private String noOffundsoutTxn;
	@JsonProperty("permittedAmoutcheck")
	private String permittedAmoutcheck;
	@JsonProperty("beneCheck")
	private String beneCheck;
	@JsonIgnore
	private String status;
	@JsonProperty("matchedAccNumber")
	private String matchedAccNumber;
	@JsonProperty("maxAmount")
	private Double maxAmount;
	@JsonProperty("noOfFundsOutTxnDetails")
	private String noOfFundsOutTxnDetails = "";
	
	/**
	 * @return the noOffundsoutTxn
	 */
	public String getNoOffundsoutTxn() {
		return noOffundsoutTxn;
	}
	/**
	 * @param noOffundsoutTxn the noOffundsoutTxn to set
	 */
	public void setNoOffundsoutTxn(String noOffundsoutTxn) {
		this.noOffundsoutTxn = noOffundsoutTxn;
	}
	/**
	 * @return the permittedAmoutcheck
	 */
	public String getPermittedAmoutcheck() {
		return permittedAmoutcheck;
	}
	/**
	 * @param permittedAmoutcheck the permittedAmoutcheck to set
	 */
	public void setPermittedAmoutcheck(String permittedAmoutcheck) {
		this.permittedAmoutcheck = permittedAmoutcheck;
	}
	/**
	 * @return the beneCheck
	 */
	public String getBeneCheck() {
		return beneCheck;
	}
	/**
	 * @param beneCheck the beneCheck to set
	 */
	public void setBeneCheck(String beneCheck) {
		this.beneCheck = beneCheck;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the matchedAccNumber
	 */
	public String getMatchedAccNumber() {
		return matchedAccNumber;
	}
	/**
	 * @param matchedAccNumber the matchedAccNumber to set
	 */
	public void setMatchedAccNumber(String matchedAccNumber) {
		this.matchedAccNumber = matchedAccNumber;
	}
	/**
	 * @return the maxAmount
	 */
	public Double getMaxAmount() {
		return maxAmount;
	}
	/**
	 * @param maxAmount the maxAmount to set
	 */
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	/**
	 * Gets the no of funds out txn details.
	 *
	 * @return the no of funds out txn details
	 */
	public String getNoOfFundsOutTxnDetails() {
		return noOfFundsOutTxnDetails;
	}
	
	/**
	 * Sets the no of funds out txn details.
	 *
	 * @param noOfFundsOutTxnDetails the new no of funds out txn details
	 */
	public void setNoOfFundsOutTxnDetails(String noOfFundsOutTxnDetails) {
		this.noOfFundsOutTxnDetails = noOfFundsOutTxnDetails;
	}	
	
}
