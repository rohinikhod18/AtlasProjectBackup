package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FundsOutRequest.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade",
	"beneficiary",
	"device_info"
})
public class FundsOutRequest extends FundsOutBaseRequest{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The trade. */
	@JsonProperty("trade")
	private Trade trade;
	
	/** The beneficiary. */
	@JsonProperty("beneficiary")
	private Beneficiary beneficiary;
	
	/** The device info. */
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
	/** The type. */
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
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request.FundsOutBaseRequest#getPaymentFundsoutId()
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
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request.FundsOutBaseRequest#getTradeAccountNumber()
	 */
	public String getTradeAccountNumber()
	{
		return getTrade().getTradeAccountNumber();
	}

}