package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request;


import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author bnt
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"source_application",
	"trade",
	"device_info",
	"risk_score"
})
public class FundsInCreateRequest extends FundsInBaseRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "Funds In type", required = true, example = " ", position = 20)
	@JsonProperty("type")
	protected String type;
	
	@ApiModelProperty(value = "Object containing details of the Funds-In trade", required = true, position = 25)
	@JsonProperty("trade")
	private FundsInTrade trade;
	
	@ApiModelProperty(value = "Object containing details of the requesting device, see below", required = true, position = 30)
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
	@ApiModelProperty(value = "Object containing the card risk score", required = true, position = 35)
	@JsonProperty("risk_score")
	private RiskScore riskScore;
	
	@ApiModelProperty(value = "Object containing the fraud sight score", required = true, position = 35)
	@JsonProperty("fraud_sight")
	private FraudData fraudSight;
	
	@JsonIgnore
	private boolean isSanctionEligible = true;
	
	@JsonIgnore
	private Integer accId;
	
	@JsonIgnore
	private Integer contactId;
	
	
	/**
	 * 
	 * @return The trade
	 */
	@JsonProperty("trade")
	public FundsInTrade getTrade() {
		return trade;
	}

	/**
	 * 
	 * @param trade
	 *            The trade
	 */
	@JsonProperty("trade")
	public void setTrade(FundsInTrade trade) {
		this.trade = trade;
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

	/**
	 * @return the riskScore
	 */
	public RiskScore getRiskScore() {
		return riskScore;
	}

	/**
	 * @param riskScore the riskScore to set
	 */
	public void setRiskScore(RiskScore riskScore) {
		this.riskScore = riskScore;
	}
	
	/**
	 * @return the fraudSight
	 */
	public FraudData getFraudSight() {
		return fraudSight;
	}

	/**
	 * @param fraudSight the fraudSight to set
	 */
	public void setFraudSight(FraudData fraudSight) {
		this.fraudSight = fraudSight;
	}

	@JsonIgnore
	public boolean isSanctionEligible() {
		return isSanctionEligible;
	}
	
	@JsonIgnore
	public void setSanctionEligible(boolean isSanctionEligible) {
		this.isSanctionEligible = isSanctionEligible;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getAccId() {
		return accId;
	}

	public Integer getContactId() {
		return contactId;
	}

	public void setAccId(Integer accId) {
		this.accId = accId;
	}

	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	
	@Override
	@JsonIgnore
	public Integer getPaymentFundsInId() {
		return getTrade().getPaymentFundsInId();
	}

	@Override
	@JsonIgnore
	public String getTradeAccountNumber() {
			return getTrade().getTradeAccountNumber();
	}
	
	public String getDebtorAccountNumber() {
		return getTrade().getDebtorAccountNumber();
	}
	
}