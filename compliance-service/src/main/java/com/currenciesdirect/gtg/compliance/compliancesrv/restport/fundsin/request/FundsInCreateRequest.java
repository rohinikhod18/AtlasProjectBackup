package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsin.request;


import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
	

	@JsonProperty("type")
	protected String type;
	
	@JsonProperty("trade")
	private FundsInTrade trade;
	
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
	@JsonProperty("risk_score")
	private RiskScore riskScore;
	
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
	
}