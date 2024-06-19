
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class InstructionDetails.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customer_instruction_type",
    "legacy_instruction_number",
    "instruction_created_date",
    "purpose_of_transaction",
    "selling_currency",
    "buying_currency",
    "online",
    "selling_amount",
    "buying_amount"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstructionDetails {

    /** The customer instruction type. */
    @JsonProperty("customer_instruction_type")
    private Object customerInstructionType;
    
    /** The legacy instruction number. */
    @JsonProperty("legacy_instruction_number")
    private Object legacyInstructionNumber;
    
    /** The instruction created date. */
    @JsonProperty("instruction_created_date")
    private String instructionCreatedDate;
    
    /** The purpose of transaction. */
    @JsonProperty("purpose_of_transaction")
    private Object purposeOfTransaction;
    
    /** The selling currency. */
    @JsonProperty("selling_currency")
    private String sellingCurrency;
    
    /** The buying currency. */
    @JsonProperty("buying_currency")
    private String buyingCurrency;
    
    /** The online. */
    @JsonProperty("online")
    private Boolean online;
    
    /** The selling amount. */
    @JsonProperty("selling_amount")
    private Integer sellingAmount;
    
    /** The buying amount. */
    @JsonProperty("buying_amount")
    private Integer buyingAmount;

    /**
     * Gets the customer instruction type.
     *
     * @return the customer instruction type
     */
    @JsonProperty("customer_instruction_type")
    public Object getCustomerInstructionType() {
        return customerInstructionType;
    }

    /**
     * Sets the customer instruction type.
     *
     * @param customerInstructionType the new customer instruction type
     */
    @JsonProperty("customer_instruction_type")
    public void setCustomerInstructionType(Object customerInstructionType) {
        this.customerInstructionType = customerInstructionType;
    }

    /**
     * Gets the legacy instruction number.
     *
     * @return the legacy instruction number
     */
    @JsonProperty("legacy_instruction_number")
    public Object getLegacyInstructionNumber() {
        return legacyInstructionNumber;
    }

    /**
     * Sets the legacy instruction number.
     *
     * @param legacyInstructionNumber the new legacy instruction number
     */
    @JsonProperty("legacy_instruction_number")
    public void setLegacyInstructionNumber(Object legacyInstructionNumber) {
        this.legacyInstructionNumber = legacyInstructionNumber;
    }

    /**
     * Gets the instruction created date.
     *
     * @return the instruction created date
     */
    @JsonProperty("instruction_created_date")
    public String getInstructionCreatedDate() {
        return instructionCreatedDate;
    }

    /**
     * Sets the instruction created date.
     *
     * @param instructionCreatedDate the new instruction created date
     */
    @JsonProperty("instruction_created_date")
    public void setInstructionCreatedDate(String instructionCreatedDate) {
        this.instructionCreatedDate = instructionCreatedDate;
    }

    /**
     * Gets the purpose of transaction.
     *
     * @return the purpose of transaction
     */
    @JsonProperty("purpose_of_transaction")
    public Object getPurposeOfTransaction() {
        return purposeOfTransaction;
    }

    /**
     * Sets the purpose of transaction.
     *
     * @param purposeOfTransaction the new purpose of transaction
     */
    @JsonProperty("purpose_of_transaction")
    public void setPurposeOfTransaction(Object purposeOfTransaction) {
        this.purposeOfTransaction = purposeOfTransaction;
    }

    /**
     * Gets the selling currency.
     *
     * @return the selling currency
     */
    @JsonProperty("selling_currency")
    public String getSellingCurrency() {
        return sellingCurrency;
    }

    /**
     * Sets the selling currency.
     *
     * @param sellingCurrency the new selling currency
     */
    @JsonProperty("selling_currency")
    public void setSellingCurrency(String sellingCurrency) {
        this.sellingCurrency = sellingCurrency;
    }

    /**
     * Gets the buying currency.
     *
     * @return the buying currency
     */
    @JsonProperty("buying_currency")
    public String getBuyingCurrency() {
        return buyingCurrency;
    }

    /**
     * Sets the buying currency.
     *
     * @param buyingCurrency the new buying currency
     */
    @JsonProperty("buying_currency")
    public void setBuyingCurrency(String buyingCurrency) {
        this.buyingCurrency = buyingCurrency;
    }

    /**
     * Gets the online.
     *
     * @return the online
     */
    @JsonProperty("online")
    public Boolean getOnline() {
        return online;
    }

    /**
     * Sets the online.
     *
     * @param online the new online
     */
    @JsonProperty("online")
    public void setOnline(Boolean online) {
        this.online = online;
    }

    /**
     * Gets the selling amount.
     *
     * @return the selling amount
     */
    @JsonProperty("selling_amount")
    public Integer getSellingAmount() {
        return sellingAmount;
    }

    /**
     * Sets the selling amount.
     *
     * @param sellingAmount the new selling amount
     */
    @JsonProperty("selling_amount")
    public void setSellingAmount(Integer sellingAmount) {
        this.sellingAmount = sellingAmount;
    }

    /**
     * Gets the buying amount.
     *
     * @return the buying amount
     */
    @JsonProperty("buying_amount")
    public Integer getBuyingAmount() {
        return buyingAmount;
    }

    /**
     * Sets the buying amount.
     *
     * @param buyingAmount the new buying amount
     */
    @JsonProperty("buying_amount")
    public void setBuyingAmount(Integer buyingAmount) {
        this.buyingAmount = buyingAmount;
    }

}
