package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade",
	"beneficiary",
	"device_info"
})
public class FundsOutRequest extends FundsOutBaseRequest{

	@JsonProperty("trade")
	private Trade trade;
	@JsonProperty("beneficiary")
	private Beneficiary beneficiary;
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	@JsonProperty("type")
	protected String type;
	
	@JsonIgnore
	private Integer accId;
	@JsonIgnore
	private Integer contactId;
	@JsonIgnore
	private Boolean isContactEligible = Boolean.TRUE ;
	@JsonIgnore
	private Boolean isBeneficiaryEligible = Boolean.TRUE;
	@JsonIgnore
	private Boolean isBankEligible =  Boolean.TRUE;
	/**
	 * 
	 * @return The trade
	 */
	@JsonProperty("trade")
	public Trade getTrade() {
		return trade;
	}

	/**
	 * 
	 * @param trade
	 *            The trade
	 */
	@JsonProperty("trade")
	public void setTrade(Trade trade) {
		this.trade = trade;
	}

	/**
	 * 
	 * @return The beneficiary
	 */
	@JsonProperty("beneficiary")
	public Beneficiary getBeneficiary() {
		return beneficiary;
	}

	/**
	 * 
	 * @param beneficiary
	 *            The beneficiary
	 */
	@JsonProperty("beneficiary")
	public void setBeneficiary(Beneficiary beneficiary) {
		this.beneficiary = beneficiary;
	}

	/**
	 * 
	 * @return The deviceInfo
	 */
	@JsonProperty("device_info")
	public DeviceInfo getDeviceInfo() {
		return deviceInfo;
	}

	/**
	 * 
	 * @param deviceInfo
	 *            The device_info
	 */
	@JsonProperty("device_info")
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public Boolean getIsContactEligible() {
		return isContactEligible;
	}

	public Boolean getIsBeneficiaryEligible() {
		return isBeneficiaryEligible;
	}

	public Boolean getIsBankEligible() {
		return isBankEligible;
	}

	public void setIsContactEligible(Boolean isContactEligible) {
		this.isContactEligible = isContactEligible;
	}

	public void setIsBeneficiaryEligible(Boolean isBeneficiaryEligible) {
		this.isBeneficiaryEligible = isBeneficiaryEligible;
	}

	public void setIsBankEligible(Boolean isBankEligible) {
		this.isBankEligible = isBankEligible;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	@Override
	@JsonIgnore
	public Integer getPaymentFundsoutId() {
		return getBeneficiary().getPaymentFundsoutId();
	}

	/**
	 * @return the accId
	 */
	@JsonIgnore
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
	 * @return the contactId
	 */
	@JsonIgnore
	public Integer getContactId() {
		return contactId;
	}

	/**
	 * @param contactId the contactId to set
	 */
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	

}