package com.currenciesdirect.gtg.compliance.core.domain.wallets;


import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class WalletTransactionDetailsResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransactionDetailsResponse extends ServiceMessageResponse{

	  /** The Constant serialVersionUID. */
	  private static final long serialVersionUID = 1L;

	  /** The wallet. */
	  @JsonProperty("wallet")
	  private Wallet wallet;

	  /** The wallet transaction list. */
	  @JsonProperty("wallet_transaction_list")
	  private List<WalletTransaction> walletTransactionList;

	  /** The wallet search criteria. */
	  @JsonProperty("search_criteria")
	  private WalletSearchCriteria walletSearchCriteria;
	 
	  @JsonProperty(value = "response_code")
		private String responseCode;
	  
	  @JsonProperty(value = "response_description")
		private String responseDescription;
	  
	  /** The orgCode. */
	  @JsonProperty(value = "org_code")
	  private String orgCode;
	  
	  /** The accountNumber. */
	  @JsonProperty(value = "account_number")
	  private String accountNumber;
	    
	  
	/**
	 * @return the orgCode
	 */
	 public String getOrgCode() {
		return orgCode;
	}
	 
	 /**
	  * Sets the orgCode.
	  * @param orgCode 
	  */
	 public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	 /**
	  * @return the accountNumber
	  */
	public String getAccountNumber() {
		return accountNumber;
	}
	
	/**
	 * Sets the accountNumber.
	 * @param accountNumber 
	 */

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
	 /**
	  * @return the responseCode
	  */
	public String getResponseCode() {
			return responseCode;
		}
	
     /**
	  * Sets the responseCode.
	  * @param responseCode 
	  */
	public void setResponseCode(String responseCode) {
			this.responseCode = responseCode;
		}
	
	 /**
	  * @return the responseDescription
	  */
	public String getResponseDescription() {
			return responseDescription;
		}
	
	/**
	* Sets the responseDescription.
	* @param responseDescription 
    */
	public void setResponseDescription(String responseDescription) {
			this.responseDescription = responseDescription;
		}

		
	  /**
	   * Gets the wallet.
	   *
	   * @return the wallet
	   */
	  public Wallet getWallet() {
	    return wallet;
	  }

	  /**
	   * Sets the wallet.
	   *
	   * @param wallet the wallet to set
	   */
	  public void setWallet(Wallet wallet) {
	    this.wallet = wallet;
	  }

	  /**
	   * Gets the wallet transaction list.
	   *
	   * @return the walletTransactionList
	   */
	  public List<WalletTransaction> getWalletTransactionList() {
	    return walletTransactionList;
	  }

	  /**
	   * Sets the wallet transaction list.
	   *
	   * @param walletTransactionList the walletTransactionList to set
	   */
	  public void setWalletTransactionList(List<WalletTransaction> walletTransactionList) {
	    this.walletTransactionList = walletTransactionList;
	  }

	  
	  /**
	   * Gets the wallet search criteria.
	   *
	   * @return the wallet search criteria
	   */
	  public WalletSearchCriteria getWalletSearchCriteria() {
	    return walletSearchCriteria;
	  }

	  /**
	   * Sets the wallet search criteria.
	   *
	   * @param walletSearchCriteria the new wallet search criteria
	   */
	  public void setWalletSearchCriteria(WalletSearchCriteria walletSearchCriteria) {
	    this.walletSearchCriteria = walletSearchCriteria;
	  }  
	}
