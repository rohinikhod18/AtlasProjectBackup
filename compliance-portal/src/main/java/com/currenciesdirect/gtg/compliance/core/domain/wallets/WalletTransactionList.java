package com.currenciesdirect.gtg.compliance.core.domain.wallets;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonIgnoreProperties(ignoreUnknown = true)
public class WalletTransactionList {
	

	@JsonInclude(JsonInclude.Include.NON_NULL)
	@JsonPropertyOrder({
	    "customer_instruction",
	    "transaction_date",
	    "amount",
	    "current_total_balance",
	    "entry_type",
	    "reason",
	    "status"
	})

	    @JsonProperty("customer_instruction")
	    private Integer customerInstruction;
	    @JsonProperty("transaction_date")
	    private String transactionDate;
	    @JsonProperty("amount")
	    private Integer amount;
	    @JsonProperty("current_total_balance")
	    private Integer currentTotalBalance;
	    @JsonProperty("entry_type")
	    private String entryType;
	    @JsonProperty("reason")
	    private String reason;
	    @JsonProperty("status")
	    private String status;

	    @JsonProperty("customer_instruction")
	    public Integer getCustomerInstruction() {
	        return customerInstruction;
	    }

	    @JsonProperty("customer_instruction")
	    public void setCustomerInstruction(Integer customerInstruction) {
	        this.customerInstruction = customerInstruction;
	    }

	    @JsonProperty("transaction_date")
	    public String getTransactionDate() {
	        return transactionDate;
	    }

	    @JsonProperty("transaction_date")
	    public void setTransactionDate(String transactionDate) {
	        this.transactionDate = transactionDate;
	    }

	    @JsonProperty("amount")
	    public Integer getAmount() {
	        return amount;
	    }

	    @JsonProperty("amount")
	    public void setAmount(Integer amount) {
	        this.amount = amount;
	    }

	    @JsonProperty("current_total_balance")
	    public Integer getCurrentTotalBalance() {
	        return currentTotalBalance;
	    }

	    @JsonProperty("current_total_balance")
	    public void setCurrentTotalBalance(Integer currentTotalBalance) {
	        this.currentTotalBalance = currentTotalBalance;
	    }

	    @JsonProperty("entry_type")
	    public String getEntryType() {
	        return entryType;
	    }

	    @JsonProperty("entry_type")
	    public void setEntryType(String entryType) {
	        this.entryType = entryType;
	    }

	    @JsonProperty("reason")
	    public String getReason() {
	        return reason;
	    }

	    @JsonProperty("reason")
	    public void setReason(String reason) {
	        this.reason = reason;
	    }

	    @JsonProperty("status")
	    public String getStatus() {
	        return status;
	    }

	    @JsonProperty("status")
	    public void setStatus(String status) {
	        this.status = status;
	    }

	}
