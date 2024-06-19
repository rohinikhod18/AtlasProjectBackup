
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class AccountFxAttribute.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "account",
    "pricing_id",
    "pricing_ahid",
    "etailer_pricing_id",
    "etailer_pricing_ahid",
    "etailer_fee_id",
    "fee_plan_id",
    "price_plan_name",
    "fee_plan_name",
    "after_hour_plan_name",
    "etailer_fee_plan_name",
    "etailer_price_plan_name",
    "etailer_after_hour_plan_name"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountFxAttribute {

    /** The account. */
    @JsonProperty("account")
    private Integer account;
    
    /** The pricing id. */
    @JsonProperty("pricing_id")
    private Integer pricingId;
    
    /** The pricing ahid. */
    @JsonProperty("pricing_ahid")
    private Integer pricingAhid;
    
    /** The etailer pricing id. */
    @JsonProperty("etailer_pricing_id")
    private Object etailerPricingId;
    
    /** The etailer pricing ahid. */
    @JsonProperty("etailer_pricing_ahid")
    private Object etailerPricingAhid;
    
    /** The etailer fee id. */
    @JsonProperty("etailer_fee_id")
    private Object etailerFeeId;
    
    /** The fee plan id. */
    @JsonProperty("fee_plan_id")
    private Integer feePlanId;
    
    /** The price plan name. */
    @JsonProperty("price_plan_name")
    private Object pricePlanName;
    
    /** The fee plan name. */
    @JsonProperty("fee_plan_name")
    private Object feePlanName;
    
    /** The after hour plan name. */
    @JsonProperty("after_hour_plan_name")
    private Object afterHourPlanName;
    
    /** The etailer fee plan name. */
    @JsonProperty("etailer_fee_plan_name")
    private Object etailerFeePlanName;
    
    /** The etailer price plan name. */
    @JsonProperty("etailer_price_plan_name")
    private Object etailerPricePlanName;
    
    /** The etailer after hour plan name. */
    @JsonProperty("etailer_after_hour_plan_name")
    private Object etailerAfterHourPlanName;

    /**
     * Gets the account.
     *
     * @return the account
     */
    @JsonProperty("account")
    public Integer getAccount() {
        return account;
    }

    /**
     * Sets the account.
     *
     * @param account the new account
     */
    @JsonProperty("account")
    public void setAccount(Integer account) {
        this.account = account;
    }

    /**
     * Gets the pricing id.
     *
     * @return the pricing id
     */
    @JsonProperty("pricing_id")
    public Integer getPricingId() {
        return pricingId;
    }

    /**
     * Sets the pricing id.
     *
     * @param pricingId the new pricing id
     */
    @JsonProperty("pricing_id")
    public void setPricingId(Integer pricingId) {
        this.pricingId = pricingId;
    }

    /**
     * Gets the pricing ahid.
     *
     * @return the pricing ahid
     */
    @JsonProperty("pricing_ahid")
    public Integer getPricingAhid() {
        return pricingAhid;
    }

    /**
     * Sets the pricing ahid.
     *
     * @param pricingAhid the new pricing ahid
     */
    @JsonProperty("pricing_ahid")
    public void setPricingAhid(Integer pricingAhid) {
        this.pricingAhid = pricingAhid;
    }

    /**
     * Gets the etailer pricing id.
     *
     * @return the etailer pricing id
     */
    @JsonProperty("etailer_pricing_id")
    public Object getEtailerPricingId() {
        return etailerPricingId;
    }

    /**
     * Sets the etailer pricing id.
     *
     * @param etailerPricingId the new etailer pricing id
     */
    @JsonProperty("etailer_pricing_id")
    public void setEtailerPricingId(Object etailerPricingId) {
        this.etailerPricingId = etailerPricingId;
    }

    /**
     * Gets the etailer pricing ahid.
     *
     * @return the etailer pricing ahid
     */
    @JsonProperty("etailer_pricing_ahid")
    public Object getEtailerPricingAhid() {
        return etailerPricingAhid;
    }

    /**
     * Sets the etailer pricing ahid.
     *
     * @param etailerPricingAhid the new etailer pricing ahid
     */
    @JsonProperty("etailer_pricing_ahid")
    public void setEtailerPricingAhid(Object etailerPricingAhid) {
        this.etailerPricingAhid = etailerPricingAhid;
    }

    /**
     * Gets the etailer fee id.
     *
     * @return the etailer fee id
     */
    @JsonProperty("etailer_fee_id")
    public Object getEtailerFeeId() {
        return etailerFeeId;
    }

    /**
     * Sets the etailer fee id.
     *
     * @param etailerFeeId the new etailer fee id
     */
    @JsonProperty("etailer_fee_id")
    public void setEtailerFeeId(Object etailerFeeId) {
        this.etailerFeeId = etailerFeeId;
    }

    /**
     * Gets the fee plan id.
     *
     * @return the fee plan id
     */
    @JsonProperty("fee_plan_id")
    public Integer getFeePlanId() {
        return feePlanId;
    }

    /**
     * Sets the fee plan id.
     *
     * @param feePlanId the new fee plan id
     */
    @JsonProperty("fee_plan_id")
    public void setFeePlanId(Integer feePlanId) {
        this.feePlanId = feePlanId;
    }

    /**
     * Gets the price plan name.
     *
     * @return the price plan name
     */
    @JsonProperty("price_plan_name")
    public Object getPricePlanName() {
        return pricePlanName;
    }

    /**
     * Sets the price plan name.
     *
     * @param pricePlanName the new price plan name
     */
    @JsonProperty("price_plan_name")
    public void setPricePlanName(Object pricePlanName) {
        this.pricePlanName = pricePlanName;
    }

    /**
     * Gets the fee plan name.
     *
     * @return the fee plan name
     */
    @JsonProperty("fee_plan_name")
    public Object getFeePlanName() {
        return feePlanName;
    }

    /**
     * Sets the fee plan name.
     *
     * @param feePlanName the new fee plan name
     */
    @JsonProperty("fee_plan_name")
    public void setFeePlanName(Object feePlanName) {
        this.feePlanName = feePlanName;
    }

    /**
     * Gets the after hour plan name.
     *
     * @return the after hour plan name
     */
    @JsonProperty("after_hour_plan_name")
    public Object getAfterHourPlanName() {
        return afterHourPlanName;
    }

    /**
     * Sets the after hour plan name.
     *
     * @param afterHourPlanName the new after hour plan name
     */
    @JsonProperty("after_hour_plan_name")
    public void setAfterHourPlanName(Object afterHourPlanName) {
        this.afterHourPlanName = afterHourPlanName;
    }

    /**
     * Gets the etailer fee plan name.
     *
     * @return the etailer fee plan name
     */
    @JsonProperty("etailer_fee_plan_name")
    public Object getEtailerFeePlanName() {
        return etailerFeePlanName;
    }

    /**
     * Sets the etailer fee plan name.
     *
     * @param etailerFeePlanName the new etailer fee plan name
     */
    @JsonProperty("etailer_fee_plan_name")
    public void setEtailerFeePlanName(Object etailerFeePlanName) {
        this.etailerFeePlanName = etailerFeePlanName;
    }

    /**
     * Gets the etailer price plan name.
     *
     * @return the etailer price plan name
     */
    @JsonProperty("etailer_price_plan_name")
    public Object getEtailerPricePlanName() {
        return etailerPricePlanName;
    }

    /**
     * Sets the etailer price plan name.
     *
     * @param etailerPricePlanName the new etailer price plan name
     */
    @JsonProperty("etailer_price_plan_name")
    public void setEtailerPricePlanName(Object etailerPricePlanName) {
        this.etailerPricePlanName = etailerPricePlanName;
    }

    /**
     * Gets the etailer after hour plan name.
     *
     * @return the etailer after hour plan name
     */
    @JsonProperty("etailer_after_hour_plan_name")
    public Object getEtailerAfterHourPlanName() {
        return etailerAfterHourPlanName;
    }

    /**
     * Sets the etailer after hour plan name.
     *
     * @param etailerAfterHourPlanName the new etailer after hour plan name
     */
    @JsonProperty("etailer_after_hour_plan_name")
    public void setEtailerAfterHourPlanName(Object etailerAfterHourPlanName) {
        this.etailerAfterHourPlanName = etailerAfterHourPlanName;
    }

}
