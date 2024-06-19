package com.currenciesdirect.gtg.compliance.core.domain.wallets;


import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * The Class WalletTransactionRequest.
 */
public class WalletTransactionRequest{
	
	/** The organization code. */
	@JsonProperty(value = "org_code")
	private String orgCode;
		
	/** The source application. */
	@JsonProperty(value = "source_application")
	private String sourceApplication;
		
	  /** The osr id. */
  	@JsonProperty(value = "osr_id")
	  private String osrId;
	  
	  /** The account number. */
	  @JsonProperty(value = "account_number")
	  private String accountNumber;

	  /** The wallet currency code. */
	  @JsonProperty("wallet_currency")
	  private String walletCurrencyCode;

	  /** The wallet number. */
	  @JsonProperty(value = "wallet_number")
	  private String walletNumber;

	  /** The search criteria. */
	  @JsonProperty(value = "search_criteria")
	  private WalletSearchCriteria walletSearchCriteria;
	  
	  
	  /**
	   * Gets the organization code.
	   *
	   * @return the organizationCode
	   */
	  
	  public String getOrgCode() {
			return orgCode;
		}


		/**
		 * Sets the org code.
		 *
		 * @param orgCode the new org code
		 */
		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.core.domain.WalletRequest#setOrgCode(java.lang.String)
		 */
		public void setOrgCode(String orgCode) {
			this.orgCode = orgCode;
		}


		/**
		 * Gets the source application.
		 *
		 * @return the source application
		 */
		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.core.domain.WalletRequest#getSourceApplication()
		 */
		public String getSourceApplication() {
			return sourceApplication;
		}


		/**
		 * Sets the source application.
		 *
		 * @param sourceApplication the new source application
		 */
		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.core.domain.WalletRequest#setSourceApplication(java.lang.String)
		 */
		public void setSourceApplication(String sourceApplication) {
			this.sourceApplication = sourceApplication;
		}


		/**
		 * Sets the osr id.
		 *
		 * @param osrId the new osr id
		 */
		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.core.domain.WalletRequest#setOsrId(java.lang.String)
		 */
		public void setOsrId(String osrId) {
			this.osrId = osrId;
		}
	  
	
	  /**
  	 * Gets the osr id.
  	 *
  	 * @return the osr_id
  	 */
		public String getOsrId() {
			return osrId;
		}

	  

	  /**
	   * Gets the account number.
	   *
	   * @return the accountNumber
	   */
	  public String getAccountNumber() {
	    return accountNumber;
	  }

	  /**
	   * Sets the account number.
	   *
	   * @param accountNumber the accountNumber to set
	   */
	  public void setAccountNumber(String accountNumber) {
	    this.accountNumber = accountNumber;
	  }

	 
	  /**
	   * Gets the wallet currency code.
	   *
	   * @return the walletCurrencyCode
	   */
	  public String getWalletCurrencyCode() {
	    return walletCurrencyCode;
	  }

	  /**
	   * Sets the wallet currency code.
	   *
	   * @param walletCurrencyCode the walletCurrencyCode to set
	   */
	  public void setWalletCurrencyCode(String walletCurrencyCode) {
	    this.walletCurrencyCode = walletCurrencyCode;
	  }

	  /**
	   * Gets the wallet number.
	   *
	   * @return the walletNumber
	   */
	  public String getWalletNumber() {
	    return walletNumber;
	  }

	  /**
	   * Sets the wallet number.
	   *
	   * @param walletNumber the walletNumber to set
	   */
	  public void setWalletNumber(String walletNumber) {
	    this.walletNumber = walletNumber;
	  }

	  /**
  	 * Gets the wallet search criteria.
  	 *
  	 * @return walletSearchCriteria
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
