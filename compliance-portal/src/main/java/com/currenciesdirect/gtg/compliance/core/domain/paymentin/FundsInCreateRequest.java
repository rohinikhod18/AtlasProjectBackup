package com.currenciesdirect.gtg.compliance.core.domain.paymentin;


import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FraudData;
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

	

	@JsonProperty("type")
	protected String type;
	
	@JsonProperty("trade")
	private FundsInTrade trade;
	
	@JsonProperty("device_info")
	private DeviceInfo deviceInfo;
	
	@JsonProperty("risk_score")
	private RiskScore riskScore;
	
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
	
	public String getCcFirstName(){
		return this.getTrade().getCcFirstName();
	}
	
	public String getDebtorAccountNumber(){
		return this.getTrade().getDebtorAccountNumber();
	}
	
	public String getDebtorName(){
		return this.getTrade().getDebtorName();
	}
	

	
	/**
	 * Removes the spaces and make lowercase.
	 *
	 * @param requestValue the request value
	 * @return the string
	 */
	public String removeSpacesAndmakeLowercase(String requestValue){		 
		return requestValue.replace(" ", "").toLowerCase();
	}

	public Double getTscore() {
		return getRiskScore().getTScore();
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
}