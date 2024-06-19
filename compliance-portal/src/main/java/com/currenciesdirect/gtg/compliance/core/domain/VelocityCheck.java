package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class VelocityCheck.
 */
public class VelocityCheck {

	private String noOffundsoutTxn;
	
	private String permittedAmoutcheck;

	private String beneCheck;
	
	private List<String> beneTradeAccountid;

	private String status;
	
	private String maxAmount;
	
	private String matchedAccNumber;
	
	private String beneficiaryAccountNumber;
	
	private String noOfFundsOutTxnDetails;
	
	
	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	
	public List<String> getBeneTradeAccountid() {
		return beneTradeAccountid;
	}

	public void setBeneTradeAccountid(List<String> beneTradeAccountid) {
		this.beneTradeAccountid = beneTradeAccountid;
	}
	
	public String getNoOffundsoutTxn() {
		return noOffundsoutTxn;
	}

	public String getPermittedAmoutcheck() {
		return permittedAmoutcheck;
	}

	public String getBeneCheck() {
		return beneCheck;
	}

	public String getStatus() {
		return status;
	}

	public void setNoOffundsoutTxn(String noOffundsoutTxn) {
		this.noOffundsoutTxn = noOffundsoutTxn;
	}

	public void setPermittedAmoutcheck(String permittedAmoutcheck) {
		this.permittedAmoutcheck = permittedAmoutcheck;
	}

	public void setBeneCheck(String beneCheck) {
		this.beneCheck = beneCheck;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(String maxAmount) {
		this.maxAmount = maxAmount;
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
