package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class WalletDetails.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletDetails implements Serializable {
	 
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The wallet number. */
	@JsonProperty(value = "wallet_number")
	private String walletNumber;
	
	/** The wallet currency. */
	@JsonProperty(value = "wallet_currency")
	private String walletCurrency;
	
	/** The total balance. */
	@JsonProperty(value = "total_balance")
	private float totalBalance;
	
	/** The available balance. */
	@JsonProperty(value = "available_balance")
	private float availableBalance;
	
	private String walletAvailableBalance;
	
	private String walletTotalBalance;
	
	public String getWalletAvailableBalance() {
		return walletAvailableBalance;
	}

	public void setWalletAvailableBalance(String walletAvailableBalance) {
		this.walletAvailableBalance = walletAvailableBalance;
	}

	public String getWalletTotalBalance() {
		return walletTotalBalance;
	}

	public void setWalletTotalBalance(String walletTotalBalance) {
		this.walletTotalBalance = walletTotalBalance;
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
	 * Gets the wallet currency.
	 *
	 * @return the walletCurrency
	 */
	public String getWalletCurrency() {
		return walletCurrency;
	}
	
	/**
	 * Gets the total balance.
	 *
	 * @return the totalBalance
	 */
	public float getTotalBalance() {
		return totalBalance;
	}
	
	/**
	 * Gets the available balance.
	 *
	 * @return the availableBalance
	 */
	public float getAvailableBalance() {
		return availableBalance;
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
	 * Sets the wallet currency.
	 *
	 * @param walletCurrency the walletCurrency to set
	 */
	public void setWalletCurrency(String walletCurrency) {
		this.walletCurrency = walletCurrency;
	}
	
	/**
	 * Sets the total balance.
	 *
	 * @param totalBalance the totalBalance to set
	 */
	public void setTotalBalance(float totalBalance) {
		this.totalBalance = totalBalance;
	}
	
	/**
	 * Sets the available balance.
	 *
	 * @param availableBalance the availableBalance to set
	 */
	public void setAvailableBalance(float availableBalance) {
		this.availableBalance = availableBalance;
	}
	  
}

