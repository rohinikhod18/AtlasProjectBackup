package com.currenciesdirect.gtg.compliance.customchecks.domain.request;

import java.util.LinkedHashMap;
import java.util.Map;

import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class CustomChecksRequest.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "orgCode", "accId", "paymentTransId", "reasonsOfTransferOnAccount", "sellCurrencyOnAccount",
		"sellAmountOnAccount", "buyCurrencyOnAccount", "buyAmountOnAccount", "eSDocument", "watchlistSellCurrency", "fPScoreUpdateOn" })
public class CustomChecksRequest extends IRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The cc org code. */
	@JsonProperty("orgCode")
	private String ccOrgCode;

	/** The cc acc id. */
	@JsonProperty("accId")
	private Integer ccAccId;

	/** The cc payment trans id. */
	@JsonProperty("paymentTransId")
	private Integer ccPaymentTransId;

	/** The cc reasons of transfer on account. */
	@JsonProperty("reasonsOfTransferOnAccount")
	private String ccReasonsOfTransferOnAccount;

	/** The cc sell currency on account. */
	@JsonProperty("sellCurrencyOnAccount")
	private String ccSellCurrencyOnAccount;

	/** The cc sell amount on account. */
	@JsonProperty("sellAmountOnAccount")
	private Double ccSellAmountOnAccount;

	/** The cc buy currency on account. */
	@JsonProperty("buyCurrencyOnAccount")
	private String ccBuyCurrencyOnAccount;

	/** The cc buy amount on account. */
	@JsonProperty("buyAmountOnAccount")
	private Double ccBuyAmountOnAccount;

	/** The cc third party on account. */
	@JsonProperty("thirdPartyOnAccount")
	private String ccThirdPartyOnAccount;

	/** The e S document. */
	@JsonProperty("eSDocument")
	private ESDocument eSDocument;

	/** The watchlist sell currency. */
	@JsonProperty("watchlistSellCurrency")
	private String watchlistSellCurrency;
	
	/** The f P score update on. */
	@JsonProperty("fPScoreUpdateOn")
	private LinkedHashMap<String, String> fPScoreUpdateOn;//Add for AT-3243
	
	@JsonProperty("zeroFundsInClear")
	private boolean zeroFundsInClear;//AT-3346
	@JsonProperty("eddCountryFlag")
	private Integer eddCountryFlag;//AT-3346
	
	/** The zero funds clear after LE date. */
	@JsonProperty("zeroFundsClearAfterLEDate")
	private boolean zeroFundsClearAfterLEDate; //Add for AT-3349
	
	/** The poi exists flag. */
	@JsonProperty("poiExistsFlag")
	private Integer poiExistsFlag;//Add for AT-3349
	
	/** The zero funds in clear for CDINC. */
	@JsonProperty("zeroFundsInClearForCDINC")
	private boolean zeroFundsInClearForCDINC; //Add for AT-3738
	
	/**
	 * Gets the watchlist sell currency.
	 *
	 * @return the watchlist sell currency
	 */
	public String getWatchlistSellCurrency() {
		return watchlistSellCurrency;
	}

	/**
	 * Sets the watchlist sell currency.
	 *
	 * @param watchlistSellCurrency
	 *            the new watchlist sell currency
	 */
	public void setWatchlistSellCurrency(String watchlistSellCurrency) {
		this.watchlistSellCurrency = watchlistSellCurrency;
	}

	/**
	 * Gets the org code.
	 *
	 * @return The orgCode
	 */
	@JsonProperty("orgCode")
	public String getOrgCode() {
		return ccOrgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            The orgCode
	 */
	@JsonProperty("orgCode")
	public void setOrgCode(String orgCode) {
		this.ccOrgCode = orgCode;
	}

	/**
	 * Gets the acc id.
	 *
	 * @return The accId
	 */
	@JsonProperty("accId")
	public Integer getAccId() {
		return ccAccId;
	}

	/**
	 * Sets the acc id.
	 *
	 * @param accId
	 *            The accId
	 */
	@JsonProperty("accId")
	public void setAccId(Integer accId) {
		this.ccAccId = accId;
	}

	/**
	 * Gets the payment trans id.
	 *
	 * @return The paymentTransId
	 */
	@JsonProperty("paymentTransId")
	public Integer getPaymentTransId() {
		return ccPaymentTransId;
	}

	/**
	 * Sets the payment trans id.
	 *
	 * @param paymentTransId
	 *            The paymentTransId
	 */
	@JsonProperty("paymentTransId")
	public void setPaymentTransId(Integer paymentTransId) {
		this.ccPaymentTransId = paymentTransId;
	}

	/**
	 * Gets the reasons of transfer on account.
	 *
	 * @return The reasonsOfTransferOnAccount
	 */
	@JsonProperty("reasonsOfTransferOnAccount")
	public String getReasonsOfTransferOnAccount() {
		return ccReasonsOfTransferOnAccount;
	}

	/**
	 * Sets the reasons of transfer on account.
	 *
	 * @param reasonsOfTransferOnAccount
	 *            The reasonsOfTransferOnAccount
	 */
	@JsonProperty("reasonsOfTransferOnAccount")
	public void setReasonsOfTransferOnAccount(String reasonsOfTransferOnAccount) {
		this.ccReasonsOfTransferOnAccount = reasonsOfTransferOnAccount;
	}

	/**
	 * Gets the sell currency on account.
	 *
	 * @return The sellCurrencyOnAccount
	 */
	@JsonProperty("sellCurrencyOnAccount")
	public String getSellCurrencyOnAccount() {
		return ccSellCurrencyOnAccount;
	}

	/**
	 * Sets the sell currency on account.
	 *
	 * @param sellCurrencyOnAccount
	 *            The sellCurrencyOnAccount
	 */
	@JsonProperty("sellCurrencyOnAccount")
	public void setSellCurrencyOnAccount(String sellCurrencyOnAccount) {
		this.ccSellCurrencyOnAccount = sellCurrencyOnAccount;
	}

	/**
	 * Gets the sell amount on account.
	 *
	 * @return The sellAmountOnAccount
	 */
	@JsonProperty("sellAmountOnAccount")
	public Double getSellAmountOnAccount() {
		return ccSellAmountOnAccount;
	}

	/**
	 * Sets the sell amount on account.
	 *
	 * @param sellAmountOnAccount
	 *            The sellAmountOnAccount
	 */
	@JsonProperty("sellAmountOnAccount")
	public void setSellAmountOnAccount(Double sellAmountOnAccount) {
		this.ccSellAmountOnAccount = sellAmountOnAccount;
	}

	/**
	 * Gets the buy currency on account.
	 *
	 * @return The buyCurrencyOnAccount
	 */
	@JsonProperty("buyCurrencyOnAccount")
	public String getBuyCurrencyOnAccount() {
		return ccBuyCurrencyOnAccount;
	}

	/**
	 * Sets the buy currency on account.
	 *
	 * @param buyCurrencyOnAccount
	 *            The buyCurrencyOnAccount
	 */
	@JsonProperty("buyCurrencyOnAccount")
	public void setBuyCurrencyOnAccount(String buyCurrencyOnAccount) {
		this.ccBuyCurrencyOnAccount = buyCurrencyOnAccount;
	}

	/**
	 * Gets the buy amount on account.
	 *
	 * @return The buyAmountOnAccount
	 */
	@JsonProperty("buyAmountOnAccount")
	public Double getBuyAmountOnAccount() {
		return ccBuyAmountOnAccount;
	}

	/**
	 * Sets the buy amount on account.
	 *
	 * @param buyAmountOnAccount
	 *            The buyAmountOnAccount
	 */
	@JsonProperty("buyAmountOnAccount")
	public void setBuyAmountOnAccount(Double buyAmountOnAccount) {
		this.ccBuyAmountOnAccount = buyAmountOnAccount;
	}

	/**
	 * Gets the third party on account.
	 *
	 * @return the thirdPartyOnAccount
	 */
	public String getThirdPartyOnAccount() {
		return ccThirdPartyOnAccount;
	}

	/**
	 * Sets the third party on account.
	 *
	 * @param thirdPartyOnAccount
	 *            the thirdPartyOnAccount to set
	 */
	public void setThirdPartyOnAccount(String thirdPartyOnAccount) {
		this.ccThirdPartyOnAccount = thirdPartyOnAccount;
	}

	/**
	 * Gets the ES document.
	 *
	 * @return the eSDocument
	 */
	@JsonProperty("eSDocument")
	public ESDocument getESDocument() {
		return eSDocument;
	}

	/**
	 * Sets the ES document.
	 *
	 * @param eSDocument
	 *            the eSDocument to set
	 */
	@JsonProperty("eSDocument")
	public void setESDocument(ESDocument eSDocument) {
		this.eSDocument = eSDocument;
	}
	
	/**
	 * Gets the f P score update on.
	 *
	 * @return the f P score update on
	 */
	public Map<String, String> getfPScoreUpdateOn() {
		return fPScoreUpdateOn;
	}

	/**
	 * Setf P score update on.
	 *
	 * @param fPScoreUpdateOn the f P score update on
	 */
	public void setfPScoreUpdateOn(Map<String, String> fPScoreUpdateOn) {
		this.fPScoreUpdateOn = (LinkedHashMap<String, String>) fPScoreUpdateOn;
	}


	/**
	 * @return the zeroFundsInClear
	 */
	public boolean getZeroFundsInClear() {
		return zeroFundsInClear;
	}

	/**
	 * @param zeroFundsInClear the zeroFundsInClear to set
	 */
	public void setZeroFundsInClear(boolean zeroFundsInClear) {
		this.zeroFundsInClear = zeroFundsInClear;
	}

	/**
	 * @return the eddCountryFlag
	 */
	public Integer getEddCountryFlag() {
		return eddCountryFlag;
	}

	/**
	 * @param eddCountryFlag the eddCountryFlag to set
	 */
	public void setEddCountryFlag(Integer eddCountryFlag) {
		this.eddCountryFlag = eddCountryFlag;
	}

	/**
	 * Checks if is zero funds clear after LE date.
	 *
	 * @return true, if is zero funds clear after LE date
	 */
	public boolean getZeroFundsClearAfterLEDate() {
		return zeroFundsClearAfterLEDate;
	}

	/**
	 * Sets the zero funds clear after LE date.
	 *
	 * @param zeroFundsClearAfterLEDate the new zero funds clear after LE date
	 */
	public void setZeroFundsClearAfterLEDate(boolean zeroFundsClearAfterLEDate) {
		this.zeroFundsClearAfterLEDate = zeroFundsClearAfterLEDate;
	}

	/**
	 * Gets the poi exists flag.
	 *
	 * @return the poi exists flag
	 */
	public Integer getPoiExistsFlag() {
		return poiExistsFlag;
	}

	/**
	 * Sets the poi exists flag.
	 *
	 * @param poiExistsFlag the new poi exists flag
	 */
	public void setPoiExistsFlag(Integer poiExistsFlag) {
		this.poiExistsFlag = poiExistsFlag;
	}

	
	/**
	 * Checks if is zero funds in clear for CDINC.
	 *
	 * @return true, if is zero funds in clear for CDINC
	 */
	public boolean isZeroFundsInClearForCDINC() {
		return zeroFundsInClearForCDINC;
	}

	/**
	 * Sets the zero funds in clear for CDINC.
	 *
	 * @param zeroFundsInClearForCDINC the new zero funds in clear for CDINC
	 */
	public void setZeroFundsInClearForCDINC(boolean zeroFundsInClearForCDINC) {
		this.zeroFundsInClearForCDINC = zeroFundsInClearForCDINC;
	}

	/**
	 * Validate request.
	 *
	 * @return the field validator
	 */
	public FieldValidator validateRequest() {
		FieldValidator fv = new FieldValidator();
		fv.isValidObject(
				new Object[] { ccOrgCode, ccAccId, ccPaymentTransId, ccSellAmountOnAccount, ccBuyAmountOnAccount,
						eSDocument.getType() },
				new String[] { "orgCode", "accId", "paymentTransId", "sellAmountOnAccount", "buyAmountOnAccount",
						"type" });

		return fv;
	}

}
