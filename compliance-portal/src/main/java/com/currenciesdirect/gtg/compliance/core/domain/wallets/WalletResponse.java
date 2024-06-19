package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class WalletResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletResponse extends ServiceMessageResponse {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty(value = "response_code")
	private String responseCode;
	
	@JsonProperty(value = "response_description")
	private String responseDescription;
	
	@JsonProperty(value = "org_code")
	private String orgCode;
	
	@JsonProperty(value = "account_number")
	private String accountNumber;
	
	@JsonProperty(value = "wallet_list")
	private List<WalletDetails> walletList = new ArrayList<>();
	
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @return the walletList
	 */
	public List<WalletDetails> getWalletList() {
		return walletList;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @param accountNumber
	 *            the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @param walletList
	 *            the walletList to set
	 */
	public void setWalletList(List<WalletDetails> walletList) {
		this.walletList = walletList;
	}

	/**
	 * @return the responseCode
	 */
	public String getResponseCode() {
		return responseCode;
	}

	/**
	 * @return the responseDescription
	 */
	public String getResponseDescription() {
		return responseDescription;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @param responseDescription the responseDescription to set
	 */
	public void setResponseDescription(String responseDescription) {
		this.responseDescription = responseDescription;
	}
}
