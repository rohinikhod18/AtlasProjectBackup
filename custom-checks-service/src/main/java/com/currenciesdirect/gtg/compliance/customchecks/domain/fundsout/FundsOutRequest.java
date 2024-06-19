package com.currenciesdirect.gtg.compliance.customchecks.domain.fundsout;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Beneficiary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Trade;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.ESDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"cust_type",
	"trade",
	"beneficiary",
	"device_info"
})
public class FundsOutRequest extends ESDocument implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty("trade")
	private Trade fundsOutTrade;
	@JsonProperty("beneficiary")
	private Beneficiary fundsOutBeneficiary;
	@JsonProperty("device_info")
	private DeviceInfo fundsOutDeviceInfo;
	/**
	 * 
	 * @return The trade
	 */
	@JsonProperty("trade")
	public Trade getTrade() {
		return fundsOutTrade;
	}

	/**
	 * 
	 * @param trade
	 *            The trade
	 */
	@JsonProperty("trade")
	public void setTrade(Trade trade) {
		this.fundsOutTrade = trade;
	}

	/**
	 * 
	 * @return The beneficiary
	 */
	@JsonProperty("beneficiary")
	public Beneficiary getBeneficiary() {
		return fundsOutBeneficiary;
	}

	/**
	 * 
	 * @param beneficiary
	 *            The beneficiary
	 */
	@JsonProperty("beneficiary")
	public void setBeneficiary(Beneficiary beneficiary) {
		this.fundsOutBeneficiary = beneficiary;
	}

	/**
	 * 
	 * @return The deviceInfo
	 */
	@JsonProperty("device_info")
	public DeviceInfo getDeviceInfo() {
		return fundsOutDeviceInfo;
	}

	/**
	 * 
	 * @param deviceInfo
	 *            The device_info
	 */
	@JsonProperty("device_info")
	public void setDeviceInfo(DeviceInfo deviceInfo) {
		this.fundsOutDeviceInfo = deviceInfo;
	}

	@Override
	public String getCurrencyCode() {
		return getBeneficiary().getCurrencyCode().toUpperCase();
	}
	
	/**
	 * 
	 * @return The custType
	 */
	@JsonProperty("cust_type")
	public String getCustType() {
		return getTrade().getCustType();
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FundsOutRequest [trade=" + fundsOutTrade + ", beneficiary=" + fundsOutBeneficiary + ", deviceInfo=" + fundsOutDeviceInfo + "]";
	}

	@Override
	public Double getBaseAmount() {
		return getTrade().getBuyingAmountBaseValue();
	}


}