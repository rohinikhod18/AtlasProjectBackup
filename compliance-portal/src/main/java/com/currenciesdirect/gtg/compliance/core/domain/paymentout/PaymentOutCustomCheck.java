package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.core.domain.CDINCFirstCreditCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EuPoiCheck;
import com.currenciesdirect.gtg.compliance.core.domain.FirstCreditCheck;
import com.currenciesdirect.gtg.compliance.core.domain.VelocityCheck;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;

/**
 * The Class PaymentOutCustomCheck.
 */
public class PaymentOutCustomCheck implements IDomain{

	private Integer id;
	
	private String entityType;
	
	private String enitityId;
	
	private String checkedOn;
	
	private VelocityCheck velocityCheck;
	
	private WhitelistCheck whiteListCheck;
	
	private String status;
	
	private Integer totalRecords;
	
	private Integer passCount;
	
	private Integer failCount;
	
	//added by Vishal J to check whether custom check is required to perform or not
	private Boolean isRequired;
	
	/** The fraud predict status. */
	private String fraudPredictStatus;//Add for AT-3161

	/** The firstCredit Check status. */
	private FirstCreditCheck firstCreditCheck;//Added for AT-3346
	
	/** The EuPoiDoc Check status. */
	private EuPoiCheck euPoiCheck;//Added for AT-3349
	
	/** The CDINC firstcredit check status **/
	private CDINCFirstCreditCheck cdincFirstCreditCheck;//Added for AT-3738

	public String getCheckedOn() {
		return checkedOn;
	}

	public VelocityCheck getVelocityCheck() {
		return velocityCheck;
	}

	public WhitelistCheck getWhiteListCheck() {
		return whiteListCheck;
	}

	public void setCheckedOn(String checkedOn) {
		this.checkedOn = checkedOn;
	}

	public void setVelocityCheck(VelocityCheck velocityCheck) {
		this.velocityCheck = velocityCheck;
	}

	public void setWhiteListCheck(WhitelistCheck whiteListCheck) {
		this.whiteListCheck = whiteListCheck;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEntityType() {
		return entityType;
	}

	public String getEnitityId() {
		return enitityId;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public void setEnitityId(String enitityId) {
		this.enitityId = enitityId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getPassCount() {
		return passCount;
	}

	public Integer getFailCount() {
		return failCount;
	}

	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}
	
	/**
	 * Gets the checks if is required.
	 *
	 * @return the checks if is required
	 */
	public Boolean getIsRequired() {
		return isRequired;
	}

	/**
	 * Sets the checks if is required.
	 *
	 * @param isRequired the new checks if is required
	 */
	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
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
	public FirstCreditCheck getFirstCreditCheck() {
		return firstCreditCheck;
	}

	/**
	 * @param firstCreditCheck the firstCreditCheck to set
	 */
	public void setFirstCreditCheck(FirstCreditCheck firstCreditCheck) {
		this.firstCreditCheck = firstCreditCheck;
	}

	/**
	 * @return the euPoiCheck
	 */
	public EuPoiCheck getEuPoiCheck() {
		return euPoiCheck;
	}

	/**
	 * @param euPoiCheck the euPoiCheck to set
	 */
	public void setEuPoiCheck(EuPoiCheck euPoiCheck) {
		this.euPoiCheck = euPoiCheck;
	}

	public CDINCFirstCreditCheck getCdincFirstCreditCheck() {
		return cdincFirstCreditCheck;
	}

	public void setCdincFirstCreditCheck(CDINCFirstCreditCheck cdincFirstCreditCheck) {
		this.cdincFirstCreditCheck = cdincFirstCreditCheck;
	}

}
