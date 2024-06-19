package com.currenciesdirect.gtg.compliance.core.domain.wallets;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "response_code",
    "response_description",
    "org_code",
    "account_number",
    "wallet",
    "wallet_transaction_list",
    "search_criteria"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransactionResponse{
	@JsonProperty("response_code")
    private String responseCode;
    @JsonProperty("response_description")
    private String responseDescription;
    @JsonProperty("org_code")
    private String orgCode;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("wallet")
    private Wallet wallet;
    @JsonProperty("wallet_transaction_list")
    private List<WalletTransactionList> walletTransactionList = null;
    public String getResponseCode() {
        return responseCode;
    }

    @JsonProperty("response_code")
    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @JsonProperty("response_description")
    public String getResponseDescription() {
        return responseDescription;
    }

    @JsonProperty("response_description")
    public void setResponseDescription(String responseDescription) {
        this.responseDescription = responseDescription;
    }

    @JsonProperty("org_code")
    public String getOrgCode() {
        return orgCode;
    }

    @JsonProperty("org_code")
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @JsonProperty("account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    @JsonProperty("account_number")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @JsonProperty("wallet")
    public Wallet getWallet() {
        return wallet;
    }

    @JsonProperty("wallet")
    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    @JsonProperty("wallet_transaction_list")
    public List<WalletTransactionList> getWalletTransactionList() {
        return walletTransactionList;
    }

    @JsonProperty("wallet_transaction_list")
    public void setWalletTransactionList(List<WalletTransactionList> walletTransactionList) {
        this.walletTransactionList = walletTransactionList;
    }
}