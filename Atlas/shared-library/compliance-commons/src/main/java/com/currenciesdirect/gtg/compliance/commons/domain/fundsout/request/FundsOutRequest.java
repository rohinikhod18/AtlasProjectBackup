package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsOutRequest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"cust_type",
	"trade",
	"beneficiary",
	"device_info",
	"age_of_payee"
})
public class FundsOutRequest extends FundsOutBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trade. */
	@ApiModelProperty(value = "trade details", required = true)
	@JsonProperty("trade")
	private Trade trade;
	
	/** The beneficiary. */
	@ApiModelProperty(value = "beneficiary details", required = true)
	@JsonProperty("beneficiary")
	private Beneficiary beneficiary;
	
	/** The device info. */
	@ApiModelProperty(value = "device details", required = true)
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
	/** The type. */
	@ApiModelProperty(value = "funds out type", required = true)
	@JsonProperty("type")
	protected String type;
	
	/** The acc id. */
	@JsonIgnore
	private Integer accId;
	
	/** The contact id. */
	@JsonIgnore
	private Integer contactId;
	
	/** The is contact eligible. */
	@JsonIgnore
	private Boolean isContactEligible = Boolean.TRUE ;
	
	/** The is beneficiary eligible. */
	@JsonIgnore
	private Boolean isBeneficiaryEligible = Boolean.TRUE;
	
	/** The is bank eligible. */
	@JsonIgnore
	private Boolean isBankEligible = Boolean.TRUE;
	
	/** The is beneficiary sanction performed. */
	@JsonIgnore
	private boolean isBeneficiarySanctionPerformed = false;
	
	/** The is bank sanction performed. */
	@JsonIgnore
	private boolean isBankSanctionPerformed = false;
	
	/** The age of payee. */
	@ApiModelProperty(value = "payee age", required = true)
	@JsonProperty("age_of_payee")
	private Integer ageOfPayee;
	
	/**
	 * Gets the age of payee.
	 *
	 * @return the age of payee
	 */
	public Integer getAgeOfPayee() {
		return ageOfPayee;
	}

	/**
	 * Sets the age of payee.
	 *
	 * @param ageOfPayee the new age of payee
	 */
	public void setAgeOfPayee(Integer ageOfPayee) {
		this.ageOfPayee = ageOfPayee;
	}

	/**
	 * Gets the trade.
	 *
	 * @return The trade
	 */
	@JsonProperty("trade")
	public Trade getTrade() {
		return trade;
	}

	/**
	 * Sets the trade.
	 *
	 * @param trade            The trade
	 */
	@JsonProperty("trade")
	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	/**
	 * Gets the beneficiary.
	 *
	 * @return The beneficiary
	 */
	@JsonProperty("beneficiary")
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	/**
	 * Sets the beneficiary.
	 *
	 * @param beneficiary            The beneficiary
	 */
	@JsonProperty("beneficiary")
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * Gets the device info.
	 *
	 * @return The deviceInfo
	 */
	@JsonProperty("device_info")
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * Sets the device info.
	 *
	 * @param deviceInfo            The device_info
	 */
	@JsonProperty("device_info")
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	/**
	 * Gets the checks if is contact eligible.
	 *
	 * @return the checks if is contact eligible
	 */
	public Boolean getIsContactEligible() {
		return isContactEligible;
	}

	/**
	 * Gets the checks if is beneficiary eligible.
	 *
	 * @return the checks if is beneficiary eligible
	 */
	public Boolean getIsBeneficiaryEligible() {
		return isBeneficiaryEligible;
	}

	/**
	 * Gets the checks if is bank eligible.
	 *
	 * @return the checks if is bank eligible
	 */
	public Boolean getIsBankEligible() {
		return isBankEligible;
	}

	/**
	 * Sets the checks if is contact eligible.
	 *
	 * @param isContactEligible the new checks if is contact eligible
	 */
	public void setIsContactEligible(Boolean isContactEligible) {
		this.isContactEligible = isContactEligible;
	}

	/**
	 * Sets the checks if is beneficiary eligible.
	 *
	 * @param isBeneficiaryEligible the new checks if is beneficiary eligible
	 */
	public void setIsBeneficiaryEligible(Boolean isBeneficiaryEligible) {
		this.isBeneficiaryEligible = isBeneficiaryEligible;
	}

	/**
	 * Sets the checks if is bank eligible.
	 *
	 * @param isBankEligible the new checks if is bank eligible
	 */
	public void setIsBankEligible(Boolean isBankEligible) {
		this.isBankEligible = isBankEligible;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type.
	 *
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest#getPaymentFundsoutId()
	 */
	@Override
	@JsonIgnore
	public Integer getPaymentFundsoutId() {
		return getBeneficiary().getPaymentFundsoutId();
	}

	/**
	 * Gets the acc id.
	 *
	 * @return the accId
	 */
	@JsonIgnore
	public Integer getAccId() {
		return accId;
	}

	/**
	 * Sets the acc id.
	 *
	 * @param accId the accId to set
	 */
	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	/**
	 * Gets the contact id.
	 *
	 * @return the contactId
	 */
	@JsonIgnore
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * Sets the contact id.
	 *
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutBaseRequest#getTradeAccountNumber()
	 */
	@JsonIgnore
	public String getTradeAccountNumber()
	{
		return getTrade().getTradeAccountNumber();
	}

	/**
	 * Checks if is beneficiary sanction performed.
	 *
	 * @return true, if is beneficiary sanction performed
	 */
	@JsonIgnore
	public boolean isBeneficiarySanctionPerformed() {
		return isBeneficiarySanctionPerformed;
	}

	/**
	 * Sets the beneficiary sanction performed.
	 *
	 * @param isBeneficiarySanctionPerformed the new beneficiary sanction performed
	 */
	@JsonIgnore
	public void setBeneficiarySanctionPerformed(boolean isBeneficiarySanctionPerformed) {
		this.isBeneficiarySanctionPerformed = isBeneficiarySanctionPerformed;
	}

	/**
	 * Checks if is bank sanction performed.
	 *
	 * @return true, if is bank sanction performed
	 */
	@JsonIgnore
	public boolean isBankSanctionPerformed() {
		return isBankSanctionPerformed;
	}

	/**
	 * Sets the bank sanction performed.
	 *
	 * @param isBankSanctionPerformed the new bank sanction performed
	 */
	@JsonIgnore
	public void setBankSanctionPerformed(boolean isBankSanctionPerformed) {
		this.isBankSanctionPerformed = isBankSanctionPerformed;
	}
	
}