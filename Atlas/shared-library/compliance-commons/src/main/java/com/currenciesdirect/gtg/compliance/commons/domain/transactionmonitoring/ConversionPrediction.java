package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;
import java.math.BigDecimal;

import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * The Class ConversionPrediction.
 */
public class ConversionPrediction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The Constant serialVersionUID. */
	
	@JsonProperty(value = "AccountId")
	private String accountId;

	/** The e TV band. */
	@JsonProperty(value = "ETVBand")
	private String eTVBand = Constants.DASH_DETAILS_PAGE;

	/** The conversion flag. */
	@JsonProperty(value = "conversionFlag")
	private String conversionFlag;

	/** The conversion probability. */
	@JsonProperty(value = "conversionProbability")
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
