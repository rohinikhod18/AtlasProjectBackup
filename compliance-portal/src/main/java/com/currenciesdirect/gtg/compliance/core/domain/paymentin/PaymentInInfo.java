package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInfo;

public class PaymentInInfo extends PaymentInfo {

	private String sellCurrency;
	
	private String countryOfFund;
	
	private String countryOfFundFullName;
	
	private Integer id;
	
	private String nameOnCard;
	
	private Boolean thirdPartyPayment;
	
	private String paymentMethod;
	
	private String debtorAccountNumber;
	
	private String tScore;
	
	private String tScoreStatus;
	
	private String isDeleted;
	
	private String updatedOn;
	
	private String debitorName;
	
	//AT-3714
	private String fraudSightFlag;
	
	private String fsScore;
	
	private String fsScoreStatus;
	
	private String fsMessage;
	
	private String fsReasonCode;
	//AT-3697
	private String threeDsTwoAuthorised;
	
	private String avsResult;
	
	private String cvcResult;
	
	//AT-4078
	private String transferReason; 
	
	/** The intuition Risk Level. */
	private String intuitionRiskLevel; // AT-4187
	
	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Boolean getThirdPartyPayment() {
		return thirdPartyPayment;
	}

	public void setThirdPartyPayment(Boolean thirdPartyPayment) {
		this.thirdPartyPayment = thirdPartyPayment;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}

	public String getSellCurrency() {
		return sellCurrency;
	}

	public void setSellCurrency(String sellCurrency) {
		this.sellCurrency = sellCurrency;
	}

	public String getCountryOfFund() {
		return countryOfFund;
	}

	public void setCountryOfFund(String countryOfFund) {
		this.countryOfFund = countryOfFund;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String gettScore() {
		return tScore;
	}

	public void settScore(String tScore) {
		this.tScore = tScore;
	}

	public String getDebtorAccountNumber() {
		return debtorAccountNumber;
	}

	public void setDebtorAccountNumber(String debtorAccountNumber) {
		this.debtorAccountNumber = debtorAccountNumber;
	}

	/**
	 * @return the tScoreStatus
	 */
	public String gettScoreStatus() {
		return tScoreStatus;
	}

	/**
	 * @param tScoreStatus the tScoreStatus to set
	 */
	public void settScoreStatus(String tScoreStatus) {
		this.tScoreStatus = tScoreStatus;
	}

	public String getCountryOfFundFullName() {
		return countryOfFundFullName;
	}

	public void setCountryOfFundFullName(String countryOfFundFullName) {
		this.countryOfFundFullName = countryOfFundFullName;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(String updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getDebitorName() {
		return debitorName;
	}

	public void setDebitorName(String debitorName) {
		this.debitorName = debitorName;
	}

	/**
	 * @return the fraudSightFlag
	 */
	public String getFraudSightFlag() {
		return fraudSightFlag;
	}

	/**
	 * @param fraudSightFlag the fraudSightFlag to set
	 */
	public void setFraudSightFlag(String fraudSightFlag) {
		this.fraudSightFlag = fraudSightFlag;
	}

	/**
	 * @return the fsScore
	 */
	public String getFsScore() {
		return fsScore;
	}

	/**
	 * @param fsScore the fsScore to set
	 */
	public void setFsScore(String fsScore) {
		this.fsScore = fsScore;
	}

	/**
	 * @return the fsScoreStatus
	 */
	public String getFsScoreStatus() {
		return fsScoreStatus;
	}

	/**
	 * @param fsScoreStatus the fsScoreStatus to set
	 */
	public void setFsScoreStatus(String fsScoreStatus) {
		this.fsScoreStatus = fsScoreStatus;
	}

	/**
	 * @return the fsMessage
	 */
	public String getFsMessage() {
		return fsMessage;
	}

	/**
	 * @param fsMessage the fsMessage to set
	 */
	public void setFsMessage(String fsMessage) {
		this.fsMessage = fsMessage;
	}

	/**
	 * @return the fsReasonCode
	 */
	public String getFsReasonCode() {
		return fsReasonCode;
	}

	/**
	 * @param fsReasonCode the fsReasonCode to set
	 */
	public void setFsReasonCode(String fsReasonCode) {
		this.fsReasonCode = fsReasonCode;
	}

	/**
	 * @return the threeDsTwoAuthorised
	 */
	public String getThreeDsTwoAuthorised() {
		return threeDsTwoAuthorised;
	}

	/**
	 * @param threeDsTwoAuthorised the threeDsTwoAuthorised to set
	 */
	public void setThreeDsTwoAuthorised(String threeDsTwoAuthorised) {
		this.threeDsTwoAuthorised = threeDsTwoAuthorised;
	}

	/**
	 * @return the avsResult
	 */
	public String getAvsResult() {
		return avsResult;
	}

	/**
	 * @param avsResult the avsResult to set
	 */
	public void setAvsResult(String avsResult) {
		this.avsResult = avsResult;
	}

	/**
	 * @return the cvcResult
	 */
	public String getCvcResult() {
		return cvcResult;
	}

	/**
	 * @param cvcResult the cvcResult to set
	 */
	public void setCvcResult(String cvcResult) {
		this.cvcResult = cvcResult;
	}

	/**
	 * @return the transferReason
	 */
	public String getTransferReason() {
		return transferReason;
	}

	/**
	 * @param transferReason the transferReason to set
	 */
	public void setTransferReason(String transferReason) {
		this.transferReason = transferReason;
	}

	/**
	 * @return the intuitionRiskLevel
	 */
	public String getIntuitionRiskLevel() {
		return intuitionRiskLevel;
	}

	/**
	 * @param intuitionRiskLevel the intuitionRiskLevel to set
	 */
	public void setIntuitionRiskLevel(String intuitionRiskLevel) {
		this.intuitionRiskLevel = intuitionRiskLevel;
	}

	
}
