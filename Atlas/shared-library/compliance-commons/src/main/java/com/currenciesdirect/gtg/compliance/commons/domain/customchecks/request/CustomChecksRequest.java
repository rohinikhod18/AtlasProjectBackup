package com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request;

import java.util.LinkedHashMap;
import java.util.Map;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class CustomChecksInsertRequest.
 * 
 * 	@author Rajesh
 */
@JsonPropertyOrder({ "orgCode", "accId", "paymentTransId", "reasonsOfTransferOnAccount", "sellCurrencyOnAccount",
	"sellAmountOnAccount", "buyCurrencyOnAccount", "buyAmountOnAccount", "eSDocument", "fPScoreUpdateOn" })
public class CustomChecksRequest extends ServiceMessage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("orgCode")
	private String orgCode;
	@JsonProperty("accId")
	private Integer accId;
	@JsonProperty("paymentTransId")
	private Integer paymentTransId;
	@JsonProperty("reasonsOfTransferOnAccount")
	private String reasonsOfTransferOnAccount;
	@JsonProperty("sellCurrencyOnAccount")
	private String sellCurrencyOnAccount;
	@JsonProperty("sellAmountOnAccount")
	private Double sellAmountOnAccount;
	@JsonProperty("buyCurrencyOnAccount")
	private String buyCurrencyOnAccount;
	@JsonProperty("buyAmountOnAccount")
	private Double buyAmountOnAccount;
	@JsonProperty("thirdPartyOnAccount")
	private String thirdPartyOnAccount;
	@JsonProperty("eSDocument")
	private ServiceMessage eSDocument;
	
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
	 * 
	 * @return The orgCode
	 */
	@JsonProperty("orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 
	 * @param orgCode
	 *            The orgCode
	 */
	@JsonProperty("orgCode")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 
	 * @return The accId
	 */
	@JsonProperty("accId")
	public Integer getAccId() {
		return accId;
	}

	/**
	 * 
	 * @param accId
	 *            The accId
	 */
	@JsonProperty("accId")
	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	/**
	 * 
	 * @return The paymentTransId
	 */
	@JsonProperty("paymentTransId")
	public Integer getPaymentTransId() {
		return paymentTransId;
	}

	/**
	 * 
	 * @param paymentTransId
	 *            The paymentTransId
	 */
	@JsonProperty("paymentTransId")
	public void setPaymentTransId(Integer paymentTransId) {
		this.paymentTransId = paymentTransId;
	}

	/**
	 * 
	 * @return The reasonsOfTransferOnAccount
	 */
	@JsonProperty("reasonsOfTransferOnAccount")
	public String getReasonsOfTransferOnAccount() {
		return reasonsOfTransferOnAccount;
	}

	/**
	 * 
	 * @param reasonsOfTransferOnAccount
	 *            The reasonsOfTransferOnAccount
	 */
	@JsonProperty("reasonsOfTransferOnAccount")
	public void setReasonsOfTransferOnAccount(String reasonsOfTransferOnAccount) {
		this.reasonsOfTransferOnAccount = reasonsOfTransferOnAccount;
	}

	/**
	 * 
	 * @return The sellCurrencyOnAccount
	 */
	@JsonProperty("sellCurrencyOnAccount")
	public String getSellCurrencyOnAccount() {
		return sellCurrencyOnAccount;
	}

	/**
	 * 
	 * @param sellCurrencyOnAccount
	 *            The sellCurrencyOnAccount
	 */
	@JsonProperty("sellCurrencyOnAccount")
	public void setSellCurrencyOnAccount(String sellCurrencyOnAccount) {
		this.sellCurrencyOnAccount = sellCurrencyOnAccount;
	}

	/**
	 * 
	 * @return The sellAmountOnAccount
	 */
	@JsonProperty("sellAmountOnAccount")
	public Double getSellAmountOnAccount() {
		return sellAmountOnAccount;
	}

	/**
	 * 
	 * @param sellAmountOnAccount
	 *            The sellAmountOnAccount
	 */
	@JsonProperty("sellAmountOnAccount")
	public void setSellAmountOnAccount(Double sellAmountOnAccount) {
		this.sellAmountOnAccount = sellAmountOnAccount;
	}

	/**
	 * 
	 * @return The buyCurrencyOnAccount
	 */
	@JsonProperty("buyCurrencyOnAccount")
	public String getBuyCurrencyOnAccount() {
		return buyCurrencyOnAccount;
	}

	/**
	 * 
	 * @param buyCurrencyOnAccount
	 *            The buyCurrencyOnAccount
	 */
	@JsonProperty("buyCurrencyOnAccount")
	public void setBuyCurrencyOnAccount(String buyCurrencyOnAccount) {
		this.buyCurrencyOnAccount = buyCurrencyOnAccount;
	}

	/**
	 * 
	 * @return The buyAmountOnAccount
	 */
	@JsonProperty("buyAmountOnAccount")
	public Double getBuyAmountOnAccount() {
		return buyAmountOnAccount;
	}

	/**
	 * 
	 * @param buyAmountOnAccount
	 *            The buyAmountOnAccount
	 */
	@JsonProperty("buyAmountOnAccount")
	public void setBuyAmountOnAccount(Double buyAmountOnAccount) {
		this.buyAmountOnAccount = buyAmountOnAccount;
	}

	
	/**
	 * @return the thirdPartyOnAccount
	 */
	public String getThirdPartyOnAccount() {
		return thirdPartyOnAccount;
	}

	/**
	 * @param thirdPartyOnAccount the thirdPartyOnAccount to set
	 */
	public void setThirdPartyOnAccount(String thirdPartyOnAccount) {
		this.thirdPartyOnAccount = thirdPartyOnAccount;
	}

	/**
	 * @return the eSDocument
	 */
	@JsonProperty("eSDocument")
	public ServiceMessage getESDocument() {
		return eSDocument;
	}

	/**
	 * @param eSDocument the eSDocument to set
	 */
	@JsonProperty("eSDocument")
	public void setESDocument(ServiceMessage eSDocument) {
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
	public boolean isZeroFundsClearAfterLEDate() {
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
	
}
