package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;


import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade_account_number",
	"payment_fundsout_id",
	"amount",
	"buying_amount_base_value"
})
public class FundsOutUpdateRequest extends FundsOutBaseRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("trade")
	private UpdateTrade trade;
	@JsonProperty("beneficiary")
	private UpdateBeneficiary beneficiary;
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
	
	/**
	 * 
	 * @return The trade
	 */
	@JsonProperty("trade")
	public UpdateTrade getTrade() {
		return trade;
	}

	/**
	 * 
	 * @param trade
	 *            The trade
	 */
	@JsonProperty("trade")
	public void setTrade(UpdateTrade trade) {
		this.trade = trade;
	}

	/**
	 * 
	 * @return The beneficiary
	 */
	@JsonProperty("beneficiary")
	public UpdateBeneficiary getBeneficiary() {
		return beneficiary;
	}

	/**
	 * 
	 * @param beneficiary
	 *            The beneficiary
	 */
	@JsonProperty("beneficiary")
	public void setBeneficiary(UpdateBeneficiary beneficiary) {
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

	


	@Override
	@JsonIgnore
	public Integer getPaymentFundsoutId() {
		return getBeneficiary().getPaymentFundsoutId();
	}

	/**
	 * @return the accId
	 */
	
	public String getTradeAccountNumber()
	{
		return getTrade().getTradeAccountNumber();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((beneficiary == null) ? 0 : beneficiary.hashCode());
		result = prime * result + ((deviceInfo == null) ? 0 : deviceInfo.hashCode());
		result = prime * result + ((trade == null) ? 0 : trade.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FundsOutUpdateRequest other = (FundsOutUpdateRequest) obj;
		if (beneficiary == null) {
			if (other.beneficiary != null)
				return false;
		} else if (!beneficiary.equals(other.beneficiary)) {
			return false;
		}
		if (deviceInfo == null) {
			if (other.deviceInfo != null)
				return false;
		} else if (!deviceInfo.equals(other.deviceInfo)) {
			return false;
		}
		if (trade == null) {
			if (other.trade != null)
				return false;
		} else if (!trade.equals(other.trade)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FundsOutUpdateRequest [trade=" + trade + ", beneficiary=" + beneficiary + ", deviceInfo=" + deviceInfo
				+ "]";
	}

}

