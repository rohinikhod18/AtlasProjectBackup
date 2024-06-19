package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentDBDto;


/**
 * The Class PaymentOutDBDto.
 */
public class PaymentOutDBDto extends PaymentDBDto  {
	
	private Integer paymentOutId;
	
	private String beneficiaryCountry;
	
	private String beneficiaryName;
	
	private String reasonOfTransfer;
	
	private String paymentOutAttributes;
	
	private String sellingAmount;
	
	private String buyingAmount;
	
	private String buyCurrency;
	
	private String paymentOutStatus;
	
	private String tradeBeneficiaryId;
	
	private String beneficiaryAmount;
	
	private String bankid;
	
	private String beneficiaryCountryFullName;
	
	private Boolean thirdPartyPayment; 
	
	private String poiExists;
	
    //added by neelesh pant
	
	private String valuedate;
	
	/**The noOfContactsForAccount */
	private Integer noOfContactsForAccount;
	
	/** The custom rule FP flag. */
	private Boolean customRuleFPFlag; //Add for AT-3161
	
	private String initialStatus;
	
	public Integer getPaymentOutId() {
		return paymentOutId;
	}

	public void setPaymentOutId(Integer paymentOutId) {
		this.paymentOutId = paymentOutId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getReasonOfTransfer() {
		return reasonOfTransfer;
	}

	public void setReasonOfTransfer(String reasonOfTransfer) {
		this.reasonOfTransfer = reasonOfTransfer;
	}

	public String getPaymentOutAttributes() {
		return paymentOutAttributes;
	}

	public void setPaymentOutAttributes(String paymentOutAttributes) {
		this.paymentOutAttributes = paymentOutAttributes;
	}

	public String getSellingAmount() {
		return sellingAmount;
	}

	public void setSellingAmount(String sellingAmount) {
		this.sellingAmount = sellingAmount;
	}

	public String getBuyingAmount() {
		return buyingAmount;
	}

	public void setBuyingAmount(String buyingAmount) {
		this.buyingAmount = buyingAmount;
	}

	public String getPaymentOutStatus() {
		return paymentOutStatus;
	}

	public void setPaymentOutStatus(String paymentOutStatus) {
		this.paymentOutStatus = paymentOutStatus;
	}

	public String getBeneficiaryCountry() {
		return beneficiaryCountry;
	}

	public void setBeneficiaryCountry(String beneficiaryCountry) {
		this.beneficiaryCountry = beneficiaryCountry;
	}

	public String getBuyCurrency() {
		return buyCurrency;
	}

	public void setBuyCurrency(String buyCurrency) {
		this.buyCurrency = buyCurrency;
	}

	public String getTradeBeneficiaryId() {
		return tradeBeneficiaryId;
	}

	public void setTradeBeneficiaryId(String tradeBeneficiaryId) {
		this.tradeBeneficiaryId = tradeBeneficiaryId;
	}

	public String getBeneficiaryAmount() {
		return beneficiaryAmount;
	}

	public void setBeneficiaryAmount(String beneficiaryAmount) {
		this.beneficiaryAmount = beneficiaryAmount;
	}

	public String getBeneficiaryCountryFullName() {
		return beneficiaryCountryFullName;
	}

	public void setBeneficiaryCountryFullName(String beneficiaryCountryFullName) {
		this.beneficiaryCountryFullName = beneficiaryCountryFullName;
	}
	
	/**
	 * @return the thirdPartyPayment
	 */
	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * @param thirdPartyPayment the thirdPartyPayment to set
	 */
	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * @return the valuedate
	 */
	public String getValuedate() {
		return valuedate;
	}

	/**
	 * @param valuedate the valuedate to set
	 */
	public void setValuedate(String valuedate) {
		this.valuedate = valuedate;
	}

	/**
	 * @return the bankid
	 */
	public String getBankid() {
		return bankid;
	}

	/**
	 * @param bankid the bankid to set
	 */
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}

	/**
	 * Gets the no of contacts for account.
	 *
	 * @return the no of contacts for account
	 */
	public Integer getNoOfContactsForAccount() {
		return noOfContactsForAccount;
	}

	/**
	 * Sets the no of contacts for account.
	 *
	 * @param noOfContactsForAccount the new no of contacts for account
	 */
	public void setNoOfContactsForAccount(Integer noOfContactsForAccount) {
		this.noOfContactsForAccount = noOfContactsForAccount;
	}

	/**
	 * Gets the custom rule FP flag.
	 *
	 * @return the custom rule FP flag
	 */
	public Boolean getCustomRuleFPFlag() {
		return customRuleFPFlag;
	}

	/**
	 * Sets the custom rule FP flag.
	 *
	 * @param customRuleFPFlag the new custom rule FP flag
	 */
	public void setCustomRuleFPFlag(Boolean customRuleFPFlag) {
		this.customRuleFPFlag = customRuleFPFlag;
	}

	public String getPoiExists() {
		return poiExists;
	}

	public void setPoiExists(String poiExists) {
		this.poiExists = poiExists;
	}

	public String getInitialStatus() {
		return initialStatus;
	}

	public void setInitialStatus(String initialStatus) {
		this.initialStatus = initialStatus;
	}

}
