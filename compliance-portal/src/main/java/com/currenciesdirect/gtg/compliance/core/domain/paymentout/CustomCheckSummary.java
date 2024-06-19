package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class CustomCheckSummary.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomCheckSummary {

	@JsonProperty("errorCode")
	private String errorCode;
	@JsonProperty("errorDescription")
	private String errorDescription;
	@JsonProperty("orgCode")
	private String orgCode;
	@JsonProperty("accId")
	private Integer accId;
	@JsonProperty("paymentTransId")
	private Integer paymentTransId;
	@JsonProperty("overallStatus")
	private String overallStatus;
	@JsonProperty("velocityCheck")
	private VelocityCheckSummary velocityCheck;
	@JsonProperty("whiteListCheck")
	private WhiteListCheckSummary whiteListCheck;
	@JsonProperty("fraudPredictStatus")
	private String fraudPredictStatus;//Add for AT-3161
	@JsonProperty("firstCreditCheck")
	private FirstCreditCheckSummary firstCreditCheck;//Added for AT-3346
	@JsonProperty("euPoiCheck")
	private EuPoiDocCheckSummary euPoiCheck;//Added for AT-3349
	@JsonProperty("cdincFirstCreditCheck")
	private CDINCFirstCreditCheckSummary cdincFirstCreditCheck;//Added for At-3738
	
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}
	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	/**
	 * @return the accId
	 */
	public Integer getAccId() {
		return accId;
	}
	/**
	 * @param accId the accId to set
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}
	/**
	 * @return the paymentTransId
	 */
	public Integer getPaymentTransId() {
		return paymentTransId;
	}
	/**
	 * @param paymentTransId the paymentTransId to set
	 */
	public void setPaymentTransId(Integer paymentTransId) {
		this.paymentTransId = paymentTransId;
	}
	/**
	 * @return the overallStatus
	 */
	public String getOverallStatus() {
		return overallStatus;
	}
	/**
	 * @param overallStatus the overallStatus to set
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}
	/**
	 * @return the velocityCheck
	 */
	public VelocityCheckSummary getVelocityCheck() {
		return velocityCheck;
	}
	/**
	 * @param velocityCheck the velocityCheck to set
	 */
	public void setVelocityCheck(VelocityCheckSummary velocityCheck) {
		this.velocityCheck = velocityCheck;
	}
	/**
	 * @return the whiteListCheck
	 */
	public WhiteListCheckSummary getWhiteListCheck() {
		return whiteListCheck;
	}
	/**
	 * @param whiteListCheck the whiteListCheck to set
	 */
	public void setWhiteListCheck(WhiteListCheckSummary whiteListCheck) {
		this.whiteListCheck = whiteListCheck;
	}
	
	/**
	 * Gets the fraud predict status.
	 *
	 * @return the fraud predict status
	 */
	public String getFraudPredictStatus() {
		return fraudPredictStatus;
	}
	
	/**
	 * Sets the fraud predict status.
	 *
	 * @param fraudPredictStatus the new fraud predict status
	 */
	public void setFraudPredictStatus(String fraudPredictStatus) {
		this.fraudPredictStatus = fraudPredictStatus;
	}
	/**
	 * @return the firstCreditCheck
	 */
	public FirstCreditCheckSummary getFirstCreditCheck() {
		return firstCreditCheck;
	}
	/**
	 * @param firstCreditCheck the firstCreditCheck to set
	 */
	public void setFirstCreditCheck(FirstCreditCheckSummary firstCreditCheck) {
		this.firstCreditCheck = firstCreditCheck;
	}
	/**
	 * @return the euPoiCheck
	 */
	public EuPoiDocCheckSummary getEuPoiCheck() {
		return euPoiCheck;
	}
	/**
	 * @param euPoiCheck the euPoiCheck to set
	 */
	public void setEuPoiCheck(EuPoiDocCheckSummary euPoiCheck) {
		this.euPoiCheck = euPoiCheck;
	}
	
	public CDINCFirstCreditCheckSummary getCdincFirstCreditCheck() {
		return cdincFirstCreditCheck;
	}
	
	public void setCdincFirstCreditCheck(CDINCFirstCreditCheckSummary cdincFirstCreditCheck) {
		this.cdincFirstCreditCheck = cdincFirstCreditCheck;
	}
	
}
