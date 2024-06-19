package com.currenciesdirect.gtg.compliance.core.domain.wallets;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wallet_number",
    "wallet_currency",
    "total_balance",
    "available_balance"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Wallet implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

    @JsonProperty("wallet_number")
    private String walletNumber;
    @JsonProperty("wallet_currency")
    private String walletCurrency;
    @JsonProperty("total_balance")
    private Double totalBalance;
    @JsonProperty("available_balance")
    private Double availableBalance;
    
    private String walletTotalBalance;
    
    private String walletAvailableBalance;
    
    public String getWalletTotalBalance() {
		return walletTotalBalance;
	}

	public void setWalletTotalBalance(String walletTotalBalance) {
		this.walletTotalBalance = walletTotalBalance;
	}

	public String getWalletAvailableBalance() {
		return walletAvailableBalance;
	}

	public void setWalletAvailableBalance(String walletAvailableBalance) {
		this.walletAvailableBalance = walletAvailableBalance;
	}

    @JsonProperty("wallet_number")
    public String getWalletNumber() {
        return walletNumber;
    }

    @JsonProperty("wallet_number")
    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    @JsonProperty("wallet_currency")
    public String getWalletCurrency() {
        return walletCurrency;
    }

    @JsonProperty("wallet_currency")
    public void setWalletCurrency(String walletCurrency) {
        this.walletCurrency = walletCurrency;
    }

    @JsonProperty("total_balance")
    public Double getTotalBalance() {
        return totalBalance;
    }

    @JsonProperty("total_balance")
    public void setTotalBalance(Double totalBalance) {
        this.totalBalance = totalBalance;
    }

    @JsonProperty("available_balance")
    public Double getAvailableBalance() {
        return availableBalance;
    }

    @JsonProperty("available_balance")
    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }

}
