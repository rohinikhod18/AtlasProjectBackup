package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class VelocityCheckResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "Number of Funds Outs Transaction checks", example = "3", required = true)
	@JsonProperty("noOffundsoutTxn")
	private String noOffundsoutTxn;
	@ApiModelProperty(value = "Max Number checks allowed", example = "2", required = true)
	@JsonProperty("permittedAmoutcheck")
	private String permittedAmoutcheck;
	@ApiModelProperty(value = "Beneficary Check", example = "PASS", required = true)
	@JsonProperty("beneCheck")
	private String beneCheck;
	@ApiModelProperty(value = "Matched Account Number", example = "true", required = true)
	@JsonProperty("matchedAccNumber")
	private String matchedAccNumber;
	@ApiModelProperty(value = "Maximum Ammount", example = "5500", required = true)
	@JsonProperty("maxAmount")
	private Double maxAmount;
	@ApiModelProperty(value = "Details of Number of Funds Outs Transaction checks", required = true)
	@JsonProperty("noOfFundsOutTxnDetails")
	private String noOfFundsOutTxnDetails;
	
	private String status;
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
	 * @return the noOfFundsOutTxnDetails
	 */
	public String getNoOfFundsOutTxnDetails() {
		return noOfFundsOutTxnDetails;
	}
	/**
	 * @param noOfFundsOutTxnDetails the noOfFundsOutTxnDetails to set
	 */
	public void setNoOfFundsOutTxnDetails(String noOfFundsOutTxnDetails) {
		this.noOfFundsOutTxnDetails = noOfFundsOutTxnDetails;
	}
	
	
}
