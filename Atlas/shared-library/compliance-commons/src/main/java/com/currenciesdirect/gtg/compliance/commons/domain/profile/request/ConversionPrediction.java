package com.currenciesdirect.gtg.compliance.commons.domain.profile.request;

import java.io.Serializable;
import java.math.BigDecimal;

import com.currenciesdirect.gtg.compliance.commons.domain.FieldDisplayName;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ConversionPrediction.
 */
public class ConversionPrediction implements Serializable {

	/** The Constant serialVersionUID. */
	
	@ApiModelProperty(value = "The SalesForce Id", example = "0010O00002LmkYzQAJ", required = true)
	@JsonProperty(value = "AccountId")
	@FieldDisplayName(displayName = "Account Id")
	private String accountId;

	/** The e TV band. */
	@ApiModelProperty(value = "The estimated transaction value band. Sales team uses this to judge how valuable the client's trade could be", example = "25k - 100k", required = true)
	@JsonProperty(value = "ETVBand")
	@FieldDisplayName(displayName = "ETV Band")
	private String eTVBand = Constants.DASH_DETAILS_PAGE;

	/** The conversion flag. */
	@ApiModelProperty(value = "The conversion flag (Low, Medium, High)", example = "Medium", required = true)
	@JsonProperty(value = "conversionFlag")
	@FieldDisplayName(displayName = "Conversion Flag")
	private String conversionFlag;

	/** The conversion probability. */
	@ApiModelProperty(value = "The conversion probability. Likelihood of becoming a customer as a decimal", example = "0.88081145", required = true)
	@JsonProperty(value = "conversionProbability")
	@FieldDisplayName(displayName = "Conversion Probability")
	private BigDecimal conversionProbability;

	/**
	 * Gets the account id.
	 *
	 * @return the account id
	 */
	public String getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId
	 *            the new account id
	 */
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the e TV band.
	 *
	 * @return the e TV band
	 */
	public String geteTVBand() {
		return eTVBand;
	}

	/**
	 * Sets the e TV band.
	 *
	 * @param eTVBand
	 *            the new e TV band
	 */
	public void seteTVBand(String eTVBand) {
		if(!StringUtils.isNullOrEmpty(eTVBand)) {
			this.eTVBand = eTVBand;
		}
	}

	/**
	 * Gets the conversion flag.
	 *
	 * @return the conversion flag
	 */
	public String getConversionFlag() {
		return conversionFlag;
	}

	/**
	 * Sets the conversion flag.
	 *
	 * @param conversionFlag
	 *            the new conversion flag
	 */
	public void setConversionFlag(String conversionFlag) {
		this.conversionFlag = conversionFlag;
	}

	/**
	 * Gets the conversion probability.
	 *
	 * @return the conversion probability
	 */
	public BigDecimal getConversionProbability() {
		return conversionProbability;
	}

	/**
	 * Sets the conversion probability.
	 *
	 * @param conversionProbability
	 *            the new conversion probability
	 */
	public void setConversionProbability(BigDecimal conversionProbability) {
		this.conversionProbability = conversionProbability;
	}

}
