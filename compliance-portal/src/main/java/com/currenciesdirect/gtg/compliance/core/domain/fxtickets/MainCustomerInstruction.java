
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class MainCustomerInstruction.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "org_code",
    "source_application",
    "external_source_ref_number",
    "account",
    "customer_source_of_fund_list",
    "customer_destination_of_fund_list",
    "existing_instruction_number",
    "instruction_details",
    "fx_details",
    "device_info",
    "forward_trade_details",
    "deposit_details"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainCustomerInstruction {

    /** The org code. */
    @JsonProperty("org_code")
    private String orgCode;
    
    /** The source application. */
    @JsonProperty("source_application")
    private String sourceApplication;
    
    /** The external source ref number. */
    @JsonProperty("external_source_ref_number")
    private String externalSourceRefNumber;
    
    /** The account. */
    @JsonProperty("account")
    private Account account;
    
    /** The customer source of fund list. */
    @JsonProperty("customer_source_of_fund_list")
    private Object customerSourceOfFundList;
    
    /** The customer destination of fund list. */
    @JsonProperty("customer_destination_of_fund_list")
    private Object customerDestinationOfFundList;
    
    /** The existing instruction number. */
    @JsonProperty("existing_instruction_number")
    private String existingInstructionNumber;
    
    /** The instruction details. */
    @JsonProperty("instruction_details")
    private InstructionDetails instructionDetails;
    
    /** The fx details. */
    @JsonProperty("fx_details")
    private FxDetails fxDetails;
    
    /** The device info. */
    @JsonProperty("device_info")
    private Object deviceInfo;
    
    /** The forward trade details. */
    @JsonProperty("forward_trade_details")
    private CustomerInstructionForward forwardTradeDetails;
    
    /** The deposit details. */
    @JsonProperty("deposit_details")
    private Object depositDetails;
    
    /** The deal type. */
    @JsonProperty("deal_type")
    private String dealType;

    /**
     * Gets the deal type.
     *
     * @return the deal type
     */
    public String getDealType() {
		return dealType;
	}

	/**
	 * Sets the deal type.
	 *
	 * @param dealType the new deal type
	 */
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	@JsonProperty("org_code")
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * Sets the org code.
     *
     * @param orgCode the new org code
     */
    @JsonProperty("org_code")
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * Gets the source application.
     *
     * @return the source application
     */
    @JsonProperty("source_application")
    public String getSourceApplication() {
        return sourceApplication;
    }

    /**
     * Sets the source application.
     *
     * @param sourceApplication the new source application
     */
    @JsonProperty("source_application")
    public void setSourceApplication(String sourceApplication) {
        this.sourceApplication = sourceApplication;
    }

    /**
     * Gets the external source ref number.
     *
     * @return the external source ref number
     */
    @JsonProperty("external_source_ref_number")
    public String getExternalSourceRefNumber() {
        return externalSourceRefNumber;
    }

    /**
     * Sets the external source ref number.
     *
     * @param externalSourceRefNumber the new external source ref number
     */
    @JsonProperty("external_source_ref_number")
    public void setExternalSourceRefNumber(String externalSourceRefNumber) {
        this.externalSourceRefNumber = externalSourceRefNumber;
    }

    /**
     * Gets the account.
     *
     * @return the account
     */
    @JsonProperty("account")
    public Account getAccount() {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param account the new account
     */
    @JsonProperty("account")
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Gets the customer source of fund list.
     *
     * @return the customer source of fund list
     */
    @JsonProperty("customer_source_of_fund_list")
    public Object getCustomerSourceOfFundList() {
        return customerSourceOfFundList;
    }

    /**
     * Sets the customer source of fund list.
     *
     * @param customerSourceOfFundList the new customer source of fund list
     */
    @JsonProperty("customer_source_of_fund_list")
    public void setCustomerSourceOfFundList(Object customerSourceOfFundList) {
        this.customerSourceOfFundList = customerSourceOfFundList;
    }

    /**
     * Gets the customer destination of fund list.
     *
     * @return the customer destination of fund list
     */
    @JsonProperty("customer_destination_of_fund_list")
    public Object getCustomerDestinationOfFundList() {
        return customerDestinationOfFundList;
    }

    /**
     * Sets the customer destination of fund list.
     *
     * @param customerDestinationOfFundList the new customer destination of fund list
     */
    @JsonProperty("customer_destination_of_fund_list")
    public void setCustomerDestinationOfFundList(Object customerDestinationOfFundList) {
        this.customerDestinationOfFundList = customerDestinationOfFundList;
    }

    /**
     * Gets the existing instruction number.
     *
     * @return the existing instruction number
     */
    @JsonProperty("existing_instruction_number")
    public String getExistingInstructionNumber() {
        return existingInstructionNumber;
    }

    /**
     * Sets the existing instruction number.
     *
     * @param existingInstructionNumber the new existing instruction number
     */
    @JsonProperty("existing_instruction_number")
    public void setExistingInstructionNumber(String existingInstructionNumber) {
        this.existingInstructionNumber = existingInstructionNumber;
    }

    /**
     * Gets the instruction details.
     *
     * @return the instruction details
     */
    @JsonProperty("instruction_details")
    public InstructionDetails getInstructionDetails() {
        return instructionDetails;
    }

    /**
     * Sets the instruction details.
     *
     * @param instructionDetails the new instruction details
     */
    @JsonProperty("instruction_details")
    public void setInstructionDetails(InstructionDetails instructionDetails) {
        this.instructionDetails = instructionDetails;
    }

    /**
     * Gets the fx details.
     *
     * @return the fx details
     */
    @JsonProperty("fx_details")
    public FxDetails getFxDetails() {
        return fxDetails;
    }

    /**
     * Sets the fx details.
     *
     * @param fxDetails the new fx details
     */
    @JsonProperty("fx_details")
    public void setFxDetails(FxDetails fxDetails) {
        this.fxDetails = fxDetails;
    }

    /**
     * Gets the device info.
     *
     * @return the device info
     */
    @JsonProperty("device_info")
    public Object getDeviceInfo() {
        return deviceInfo;
    }

    /**
     * Sets the device info.
     *
     * @param deviceInfo the new device info
     */
    @JsonProperty("device_info")
    public void setDeviceInfo(Object deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    /**
     * Gets the forward trade details.
     *
     * @return the forward trade details
     */
    @JsonProperty("forward_trade_details")
    public CustomerInstructionForward getForwardTradeDetails() {
        return forwardTradeDetails;
    }

    /**
     * Sets the forward trade details.
     *
     * @param forwardTradeDetails the new forward trade details
     */
    @JsonProperty("forward_trade_details")
    public void setForwardTradeDetails(CustomerInstructionForward forwardTradeDetails) {
        this.forwardTradeDetails = forwardTradeDetails;
    }

    /**
     * Gets the deposit details.
     *
     * @return the deposit details
     */
    @JsonProperty("deposit_details")
    public Object getDepositDetails() {
        return depositDetails;
    }

    /**
     * Sets the deposit details.
     *
     * @param depositDetails the new deposit details
     */
    @JsonProperty("deposit_details")
    public void setDepositDetails(Object depositDetails) {
        this.depositDetails = depositDetails;
    }

}
