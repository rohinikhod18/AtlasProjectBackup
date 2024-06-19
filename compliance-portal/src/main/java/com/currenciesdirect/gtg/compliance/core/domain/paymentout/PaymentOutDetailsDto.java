package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.core.domain.PaymentDetailsDto;

/**
 * The Class PaymentOutDetailDto.
 */
public class PaymentOutDetailsDto extends PaymentDetailsDto {

	private PaymentOutInfo paymentOutInfo;

	/** The blacklist. */
	private PaymentOutBlacklist blacklist;

	/** The sanction. */
	private PaymentOutSanction sanction;

	/** The noOfContactsForAccount*/
	private Integer noOfContactsForAccount;
	
	/** The custom rule FP flag. */
	private Boolean customRuleFPFlag;//Add for AT-3161
	
	//Added for AT-3658
	private PaymentOutPaymentReference paymentReference;

	/**
	 * Gets the blacklist.
	 *
	 * @return the blacklist
	 */
	public PaymentOutBlacklist getBlacklist() {
		return blacklist;
	}

	/**
	 * Sets the blacklist.
	 *
	 * @param blacklist
	 *            the new blacklist
	 */
	public void setBlacklist(PaymentOutBlacklist blacklist) {
		this.blacklist = blacklist;
	}

	/**
	 * Gets the sanction.
	 *
	 * @return the sanction
	 */
	public PaymentOutSanction getSanction() {
		return sanction;
	}

	/**
	 * Sets the sanction.
	 *
	 * @param sanction
	 *            the new sanction
	 */
	public void setSanction(PaymentOutSanction sanction) {
		this.sanction = sanction;
	}

	/**
	 * Gets the payment out info.
	 *
	 * @return the payment out info
	 */
	public PaymentOutInfo getPaymentOutInfo() {
		return paymentOutInfo;
	}

	/**
	 * Sets the payment out info.
	 *
	 * @param paymentOutInfo
	 *            the new payment out info
	 */
	public void setPaymentOutInfo(PaymentOutInfo paymentOutInfo) {
		this.paymentOutInfo = paymentOutInfo;
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

	public PaymentOutPaymentReference getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(PaymentOutPaymentReference paymentReference) {
		this.paymentReference = paymentReference;
	}

}
