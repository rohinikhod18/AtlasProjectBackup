/**
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.fraugster;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FraugsterPaymentsInContactRequest.
 *
 * @author manish
 */
public class FraugsterPaymentsInContactRequest extends FraugsterPaymentsBaseRequest {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payment type. */
	private String paymentType;

	/** The name on card. */
	private String nameOnCard;

	/** The address on card. */
	private String addressOnCard;

	/** The a VS result. */
	private String aVSResult;

	/** The three D status. */
	private String threeDStatus;

	/** The debit card added date. */
	private String debitCardAddedDate;

	/** The a V trade value. */
	private String aVTradeValue;

	/** The a V trade frequency. */
	private String aVTradeFrequency;

	/** The third party payment. */
	private String thirdPartyPayment;

	/** The cheque clearance date. */
	private String chequeClearanceDate;

	/** The account identification. */
	private String accountIdentification;

	/** The date and time. */
	private String dateAndTime;

	/** The customer address or postcode. */
	private String customerAddressOrPostcode;

	/** The transaction reference. */
	private String transactionReference;

	/** The cust Type. */
	private String custType;
	
	/** The purposeOfTrade. */
	private String purposeOfTrade;
	
	/** The countryOfFund. */
	private String countryOfFund;
	
	/** The turnover. */
	  @ApiModelProperty(value = "Amount of turnover that client said they will probably do when they signed up, used for Fraud analysis", required = true, example = "10000000")
	private String turnover; 
	
	/** The sellingAmountGbpValue. */
	private String sellingAmountGbpValue;
	
	/** The contractNumber. */
	private String contractNumber;
	
	/** The riskScore. */
	private String riskScore;
	
	/**
	 * @return
	 */
	public String getRiskScore() {
		return riskScore;
	}

	/**
	 * @param riskScore
	 */
	public void setRiskScore(String riskScore) {
		this.riskScore = riskScore;
	}

	/**
	 * @return turnover
	 */
	public String getTurnover() {
		return turnover;
	}

	/**
	 * @param turnover
	 */
	public void setTurnover(String turnover) {
		this.turnover = turnover;
	}

	/**
	 * @return sellingAmountGbpValue
	 */
	public String getSellingAmountGbpValue() {
		return sellingAmountGbpValue;
	}

	/**
	 * @param sellingAmountGbpValue
	 */
	public void setSellingAmountGbpValue(String sellingAmountGbpValue) {
		this.sellingAmountGbpValue = sellingAmountGbpValue;
	}

	/**
	 * @return contractNumber
	 */
	public String getContractNumber() {
		return contractNumber;
	}

	/**
	 * @param contractNumber
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	
	/**
	 * @return purposeOfTrade
	 */
	public String getPurposeOfTrade() {
		return purposeOfTrade;
	}

	/**
	 * @param purposeOfTrade
	 */
	public void setPurposeOfTrade(String purposeOfTrade) {
		this.purposeOfTrade = purposeOfTrade;
	}

	/**
	 * @return countryOfFund
	 */
	public String getCountryOfFund() {
		return countryOfFund;
	}

	/**
	 * @param countryOfFund
	 */
	public void setCountryOfFund(String countryOfFund) {
		this.countryOfFund = countryOfFund;
	}

	/**
	 * @return custType
	 */
	public String getCustType() {
		return custType;
	}

	/**
	 * @param custType
	 */
	public void setCustType(String custType) {
		this.custType = custType;
	}

	/**
	 * Gets the payment type.
	 *
	 * @return the payment type
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * Sets the payment type.
	 *
	 * @param paymentType
	 *            the new payment type
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * Gets the name on card.
	 *
	 * @return the name on card
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * Sets the name on card.
	 *
	 * @param nameOnCard
	 *            the new name on card
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	/**
	 * Gets the address on card.
	 *
	 * @return the address on card
	 */
	public String getAddressOnCard() {
		return addressOnCard;
	}

	/**
	 * Sets the address on card.
	 *
	 * @param addressOnCard
	 *            the new address on card
	 */
	public void setAddressOnCard(String addressOnCard) {
		this.addressOnCard = addressOnCard;
	}

	/**
	 * Gets the a VS result.
	 *
	 * @return the a VS result
	 */
	public String getaVSResult() {
		return aVSResult;
	}

	/**
	 * Sets the a VS result.
	 *
	 * @param aVSResult
	 *            the new a VS result
	 */
	public void setaVSResult(String aVSResult) {
		this.aVSResult = aVSResult;
	}

	/**
	 * Gets the three D status.
	 *
	 * @return the three D status
	 */
	public String getThreeDStatus() {
		return threeDStatus;
	}

	/**
	 * Sets the three D status.
	 *
	 * @param threeDStatus
	 *            the new three D status
	 */
	public void setThreeDStatus(String threeDStatus) {
		this.threeDStatus = threeDStatus;
	}

	/**
	 * Gets the a V trade value.
	 *
	 * @return the a V trade value
	 */
	public String getaVTradeValue() {
		return aVTradeValue;
	}

	/**
	 * Sets the a V trade value.
	 *
	 * @param aVTradeValue
	 *            the new a V trade value
	 */
	public void setaVTradeValue(String aVTradeValue) {
		this.aVTradeValue = aVTradeValue;
	}

	/**
	 * Gets the a V trade frequency.
	 *
	 * @return the a V trade frequency
	 */
	public String getaVTradeFrequency() {
		return aVTradeFrequency;
	}

	/**
	 * Sets the a V trade frequency.
	 *
	 * @param aVTradeFrequency
	 *            the new a V trade frequency
	 */
	public void setaVTradeFrequency(String aVTradeFrequency) {
		this.aVTradeFrequency = aVTradeFrequency;
	}

	/**
	 * Gets the third party payment.
	 *
	 * @return the third party payment
	 */
	public String getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	/**
	 * Sets the third party payment.
	 *
	 * @param thirdPartyPayment
	 *            the new third party payment
	 */
	public void setThirdPartyPayment(String thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	/**
	 * Gets the account identification.
	 *
	 * @return the account identification
	 */
	public String getAccountIdentification() {
		return accountIdentification;
	}

	/**
	 * Sets the account identification.
	 *
	 * @param accountIdentification
	 *            the new account identification
	 */
	public void setAccountIdentification(String accountIdentification) {
		this.accountIdentification = accountIdentification;
	}

	/**
	 * Sets the customer address or postcode.
	 *
	 * @param customerAddressOrPostcode
	 *            the new customer address or postcode
	 */
	public void setCustomerAddressOrPostcode(String customerAddressOrPostcode) {
		this.customerAddressOrPostcode = customerAddressOrPostcode;
	}

	/**
	 * Gets the cheque clearance date.
	 *
	 * @return the cheque clearance date
	 */
	public String getChequeClearanceDate() {
		return chequeClearanceDate;
	}

	/**
	 * Sets the cheque clearance date.
	 *
	 * @param chequeClearanceDate
	 *            the new cheque clearance date
	 */
	public void setChequeClearanceDate(String chequeClearanceDate) {
		this.chequeClearanceDate = chequeClearanceDate;
	}

	/**
	 * Gets the debit card added date.
	 *
	 * @return the debit card added date
	 */
	public String getDebitCardAddedDate() {
		return debitCardAddedDate;
	}

	/**
	 * Sets the debit card added date.
	 *
	 * @param debitCardAddedDate
	 *            the new debit card added date
	 */
	public void setDebitCardAddedDate(String debitCardAddedDate) {
		this.debitCardAddedDate = debitCardAddedDate;
	}

	/**
	 * Gets the date and time.
	 *
	 * @return the date and time
	 */
	public String getDateAndTime() {
		return dateAndTime;
	}

	/**
	 * Sets the date and time.
	 *
	 * @param dateAndTime
	 *            the new date and time
	 */
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}

	/**
	 * Gets the customer address or postcode.
	 *
	 * @return the customer address or postcode
	 */
	public String getCustomerAddressOrPostcode() {
		return customerAddressOrPostcode;
	}

	/**
	 * Gets the transaction reference.
	 *
	 * @return the transaction reference
	 */
	public String getTransactionReference() {
		return transactionReference;
	}

	/**
	 * Sets the transaction reference.
	 *
	 * @param transactionReference
	 *            the new transaction reference
	 */
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}

}
