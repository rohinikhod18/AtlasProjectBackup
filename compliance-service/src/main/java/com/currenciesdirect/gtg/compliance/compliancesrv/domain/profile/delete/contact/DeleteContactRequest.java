package com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"org_code",
	"contact_sf_id",
	"trade_contact_id",
	"acc_sf_id",
	"trade_acc_id",
})
public class DeleteContactRequest extends ServiceMessage{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the call originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty("org_code")
	private String orgCode;
	
	/** The contact sf id. */
	@ApiModelProperty(value = "Contact Salesforce ID", example = "0030O00002jdcXIQAY", required = true)
	@JsonProperty("contact_sf_id")
	private String contactSfId;
	
	/** The trade contact id. */
	@ApiModelProperty(value = "The trade contact ID", example = "751843", required = true)
	@JsonProperty("trade_contact_id")
	private Integer tradeContactId;
	
	/** The account sf id. */
	@ApiModelProperty(value = "The account salesforce id", required = true)
	@JsonProperty("acc_sf_id")
	private String accountSfId;
	
	/** The trade account id. */
	@ApiModelProperty(value = "The trade account id", required = true)
	@JsonProperty("trade_acc_id")
	private Integer tradeAccountId;
	
	/** The org id. */
	@ApiModelProperty(value = "The org id", required = true)
	private Integer orgId;

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
	 * @return the contactSfId
	 */
	public String getContactSfId() {
		return contactSfId;
	}

	/**
	 * @param contactSfId the contactSfId to set
	 */
	public void setContactSfId(String contactSfId) {
		this.contactSfId = contactSfId;
	}

	/**
	 * @return the tradeContactId
	 */
	public Integer getTradeContactId() {
		return tradeContactId;
	}

	/**
	 * @param tradeContactId the tradeContactId to set
	 */
	public void setTradeContactId(Integer tradeContactId) {
		this.tradeContactId = tradeContactId;
	}

	/**
	 * @return the accountSfId
	 */
	public String getAccountSfId() {
		return accountSfId;
	}

	/**
	 * @param accountSfId the accountSfId to set
	 */
	public void setAccountSfId(String accountSfId) {
		this.accountSfId = accountSfId;
	}

	/**
	 * @return the tradeAccountId
	 */
	public Integer getTradeAccountId() {
		return tradeAccountId;
	}

	/**
	 * @param tradeAccountId the tradeAccountId to set
	 */
	public void setTradeAccountId(Integer tradeAccountId) {
		this.tradeAccountId = tradeAccountId;
	}

	/**
	 * @return the orgId
	 */
	public Integer getOrgId() {
		return orgId;
	}

	/**
	 * @param orgId the orgId to set
	 */
	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
	}

}
