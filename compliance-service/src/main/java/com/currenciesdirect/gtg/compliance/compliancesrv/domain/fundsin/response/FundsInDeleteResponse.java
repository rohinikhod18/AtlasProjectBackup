package com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"errorCode",
	"errorDescription",
	"responseCode",
	"responseDescription",
	"status",
	"orgCode",
	"payment_fundsin_id"
})
public class FundsInDeleteResponse extends BaseResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "Organization Code", required = true)
	@JsonProperty("orgCode")
	private String orgCode;
	@ApiModelProperty(value = "Funds In ID", required = true)
	@JsonProperty("payment_fundsin_id")
	private Integer paymentFundsinId;
	@ApiModelProperty(value = "Status", required = true)
	@JsonProperty("status")
	private String status;

	/**
	 * 
	 * @return The orgCode
	 */
	@JsonProperty("orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 
	 * @param orgCode
	 *            The orgCode
	 */
	@JsonProperty("orgCode")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * 
	 * @return The paymentFundsinId
	 */
	@JsonProperty("payment_fundsin_id")
	public Integer getPaymentFundsinId() {
		return paymentFundsinId;
	}

	/**
	 * 
	 * @param paymentFundsinId
	 *            The payment_fundsin_id
	 */
	@JsonProperty("payment_fundsin_id")
	public void setPaymentFundsinId(Integer paymentFundsinId) {
		this.paymentFundsinId = paymentFundsinId;
	}
	
	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}

