package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class TradeDetails.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeDetails implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The selling amount. */
	@JsonProperty("selling_amount")
	private BigDecimal sellingAmount;

	/** The buying amount. */
	@JsonProperty("buying_amount")
	private BigDecimal buyingAmount;

	/** The selling currency. */
	@JsonProperty("selling_currency")
	private String sellingCurrency;

	/** The buying currency. */
	@JsonProperty( "buying_currency")
	private String buyingCurrency;

	/** The system rate. */
	@JsonProperty("system_rate")
	private BigDecimal systemRate;

	/** The agreed rate. */
	@JsonProperty("agreed_rate")
	private BigDecimal agreedRate;

	/** The live rate. */
	@JsonProperty("live_rate")
	private BigDecimal liveRate;

	/** The treasury rate. */
	@JsonProperty("treasury_rate")
	private BigDecimal treasuryRate;

	/** The net profit. */
	@JsonProperty( "net_profit")
	private BigDecimal netProfit;

	/** The base currency. */
	@JsonProperty( "base_currency")
	private String baseCurrency;


	/** The base currency rate. */
	@JsonProperty("base_currency_rate")
	private BigDecimal baseCurrencyRate;

	/** The base selling amount. */
	@JsonProperty("base_selling_amount")
	private BigDecimal baseSellingAmount;

	/** The base buying amount. */
	@JsonProperty("base_buying_amount")
	private BigDecimal baseBuyingAmount;

	/** The treasury profit. */
	@JsonProperty("treasury_profit")
	private BigDecimal treasuryProfit;

	/** The final profit. */
	@JsonProperty( "final_profit")
	private BigDecimal finalProfit;

	/** The reason for adjustment id. */
	@JsonProperty("reason_for_adjustment")
	private String reasonForAdjustment;

	/** Note consider as reason. */
	@JsonProperty("notes")
	private String notes;

	/** The value date. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	@JsonProperty( "value_date")
	private Timestamp valueDate;

	/** The b 2 b. */
	@JsonProperty("b2b")
	private Boolean b2b;

	/** The profit adjustment. */
	@JsonProperty("profit_adjustment")
	private BigDecimal profitAdjustment;

	/** The b 2 b. */
	@JsonProperty("affiliate_commission_paid")
	private Boolean affiliateCommissionPaid;

	/**  Fx ticket created date is consider as booking date. */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
	@JsonProperty("booking_date")
	private Timestamp bookingDate;


	

	/**
	 * Gets the reason for adjustment.
	 *
	 * @return the reasonForAdjustment
	 */
	public String getReasonForAdjustment() {
		return reasonForAdjustment;
	}

	/**
	 * Sets the reason for adjustment.
	 *
	 * @param reasonForAdjustment the reasonForAdjustment to set
	 */
	public void setReasonForAdjustment(String reasonForAdjustment) {
		this.reasonForAdjustment = reasonForAdjustment;
	}
	
	/**
	 * Gets the selling amount.
	 *
	 * @return the sellingAmount
	 */
	public BigDecimal getSellingAmount() {
		return sellingAmount;
	}

	/**
	 * Sets the selling amount.
	 *
	 * @param sellingAmount the sellingAmount to set
	 */
	public void setSellingAmount(BigDecimal sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	/**
	 * Gets the buying amount.
	 *
	 * @return the buyingAmount
	 */
	public BigDecimal getBuyingAmount() {
		return buyingAmount;
	}

	/**
	 * Sets the buying amount.
	 *
	 * @param buyingAmount the buyingAmount to set
	 */
	public void setBuyingAmount(BigDecimal buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	/**
	 * Gets the selling currency.
	 *
	 * @return the sellingCurrency
	 */
	public String getSellingCurrency() {
		return sellingCurrency;
	}

	/**
	 * Sets the selling currency.
	 *
	 * @param sellingCurrency the sellingCurrency to set
	 */
	public void setSellingCurrency(String sellingCurrency) {
		this.sellingCurrency = sellingCurrency;
	}

	/**
	 * Gets the buying currency.
	 *
	 * @return the buyingCurrency
	 */
	public String getBuyingCurrency() {
		return buyingCurrency;
	}

	/**
	 * Sets the buying currency.
	 *
	 * @param buyingCurrency the buyingCurrency to set
	 */
	public void setBuyingCurrency(String buyingCurrency) {
		this.buyingCurrency = buyingCurrency;
	}

	/**
	 * Gets the system rate.
	 *
	 * @return the systemRate
	 */
	public BigDecimal getSystemRate() {
		return systemRate;
	}

	/**
	 * Sets the system rate.
	 *
	 * @param systemRate the systemRate to set
	 */
	public void setSystemRate(BigDecimal systemRate) {
		this.systemRate = systemRate;
	}

	/**
	 * Gets the agreed rate.
	 *
	 * @return the agreedRate
	 */
	public BigDecimal getAgreedRate() {
		return agreedRate;
	}

	/**
	 * Sets the agreed rate.
	 *
	 * @param agreedRate the agreedRate to set
	 */
	public void setAgreedRate(BigDecimal agreedRate) {
		this.agreedRate = agreedRate;
	}

	/**
	 * Gets the live rate.
	 *
	 * @return the liveRate
	 */
	public BigDecimal getLiveRate() {
		return liveRate;
	}

	/**
	 * Sets the live rate.
	 *
	 * @param liveRate the liveRate to set
	 */
	public void setLiveRate(BigDecimal liveRate) {
		this.liveRate = liveRate;
	}

	/**
	 * Gets the treasury rate.
	 *
	 * @return the treasuryRate
	 */
	public BigDecimal getTreasuryRate() {
		return treasuryRate;
	}

	/**
	 * Sets the treasury rate.
	 *
	 * @param treasuryRate the treasuryRate to set
	 */
	public void setTreasuryRate(BigDecimal treasuryRate) {
		this.treasuryRate = treasuryRate;
	}

	/**
	 * Gets the net profit.
	 *
	 * @return the netProfit
	 */
	public BigDecimal getNetProfit() {
		return netProfit;
	}

	/**
	 * Sets the net profit.
	 *
	 * @param netProfit the netProfit to set
	 */
	public void setNetProfit(BigDecimal netProfit) {
		this.netProfit = netProfit;
	}

	/**
	 * Gets the base currency.
	 *
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * Sets the base currency.
	 *
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	/**
	 * Gets the base currency rate.
	 *
	 * @return the baseCurrencyRate
	 */
	public BigDecimal getBaseCurrencyRate() {
		return baseCurrencyRate;
	}

	/**
	 * Sets the base currency rate.
	 *
	 * @param baseCurrencyRate the baseCurrencyRate to set
	 */
	public void setBaseCurrencyRate(BigDecimal baseCurrencyRate) {
		this.baseCurrencyRate = baseCurrencyRate;
	}

	/**
	 * Gets the base selling amount.
	 *
	 * @return the baseSellingAmount
	 */
	public BigDecimal getBaseSellingAmount() {
		return baseSellingAmount;
	}

	/**
	 * Sets the base selling amount.
	 *
	 * @param baseSellingAmount the baseSellingAmount to set
	 */
	public void setBaseSellingAmount(BigDecimal baseSellingAmount) {
		this.baseSellingAmount = baseSellingAmount;
	}

	/**
	 * Gets the base buying amount.
	 *
	 * @return the baseBuyingAmount
	 */
	public BigDecimal getBaseBuyingAmount() {
		return baseBuyingAmount;
	}

	/**
	 * Sets the base buying amount.
	 *
	 * @param baseBuyingAmount the baseBuyingAmount to set
	 */
	public void setBaseBuyingAmount(BigDecimal baseBuyingAmount) {
		this.baseBuyingAmount = baseBuyingAmount;
	}

	/**
	 * Gets the treasury profit.
	 *
	 * @return the treasuryProfit
	 */
	public BigDecimal getTreasuryProfit() {
		return treasuryProfit;
	}

	/**
	 * Sets the treasury profit.
	 *
	 * @param treasuryProfit the treasuryProfit to set
	 */
	public void setTreasuryProfit(BigDecimal treasuryProfit) {
		this.treasuryProfit = treasuryProfit;
	}

	/**
	 * Gets the final profit.
	 *
	 * @return the finalProfit
	 */
	public BigDecimal getFinalProfit() {
		return finalProfit;
	}

	/**
	 * Sets the final profit.
	 *
	 * @param finalProfit the finalProfit to set
	 */
	public void setFinalProfit(BigDecimal finalProfit) {
		this.finalProfit = finalProfit;
	}


	/**
	 * Gets the notes.
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the notes.
	 *
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * Gets the value date.
	 *
	 * @return the valueDate
	 */
	public Timestamp getValueDate() {
		return valueDate;
	}

	/**
	 * Sets the value date.
	 *
	 * @param valueDate the valueDate to set
	 */
	public void setValueDate(Timestamp valueDate) {
		this.valueDate = valueDate;
	}

	/**
	 * Gets the b 2 b.
	 *
	 * @return the b2b
	 */
	public Boolean getB2b() {
		return b2b;
	}

	/**
	 * Sets the b 2 b.
	 *
	 * @param b2b the b2b to set
	 */
	public void setB2b(Boolean b2b) {
		this.b2b = b2b;
	}

	/**
	 * Gets the profit adjustment.
	 *
	 * @return the profitAdjustment
	 */
	public BigDecimal getProfitAdjustment() {
		return profitAdjustment;
	}

	/**
	 * Sets the profit adjustment.
	 *
	 * @param profitAdjustment the profitAdjustment to set
	 */
	public void setProfitAdjustment(BigDecimal profitAdjustment) {
		this.profitAdjustment = profitAdjustment;
	}

	/**
	 * Gets the affiliate commission paid.
	 *
	 * @return the affiliateCommissionPaid
	 */
	public Boolean getAffiliateCommissionPaid() {
		return affiliateCommissionPaid;
	}

	/**
	 * Sets the affiliate commission paid.
	 *
	 * @param affiliateCommissionPaid the affiliateCommissionPaid to set
	 */
	public void setAffiliateCommissionPaid(Boolean affiliateCommissionPaid) {
		this.affiliateCommissionPaid = affiliateCommissionPaid;
	}

	/**
	 * Gets the booking date.
	 *
	 * @return the bookingDate
	 */
	public Timestamp getBookingDate() {
		return bookingDate;
	}

	/**
	 * Sets the booking date.
	 *
	 * @param bookingDate the bookingDate to set
	 */
	public void setBookingDate(Timestamp bookingDate) {
		this.bookingDate = bookingDate;
	}

}
