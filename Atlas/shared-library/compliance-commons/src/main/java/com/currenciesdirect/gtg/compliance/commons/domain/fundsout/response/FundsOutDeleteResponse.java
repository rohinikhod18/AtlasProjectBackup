package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class FundsOutDeleteResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"errorCode",
	"errorDescription",
	"responseCode",
	"responseDescription",
	"status",
	"orgCode",
	"payment_fundsout_id"
})
public class FundsOutDeleteResponse extends BaseResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The org code. */
	@ApiModelProperty(value = "The organisation code", example = "Currencies Direct",  required = true)
	@JsonProperty("orgCode")
	private String orgCode;
	
	/** The payment fundsout id. */
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
	
	/** The status. */
	@ApiModelProperty(value = "The status", example = "REVERSED", required = true)
	@JsonProperty("status")
	private String status;

	/**
	 * Gets the org code.
	 *
	 * @return The orgCode
	 */
	@JsonProperty("orgCode")
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode            The orgCode
	 */
	@JsonProperty("orgCode")
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	
	/**
	 * Gets the payment fundsout id.
	 *
	 * @return The paymentFundsoutId
	 */
	@JsonProperty("payment_fundsout_id")
	public Integer getPaymentFundsoutId() {
		return paymentFundsoutId;
	}

	/**
	 * Sets the payment fundsout id.
	 *
	 * @param paymentFundsoutId            The payment_fundsout_id
	 */
	@JsonProperty("payment_fundsout_id")
	public void setPaymentFundsoutId(Integer paymentFundsoutId) {
		this.paymentFundsoutId = paymentFundsoutId;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}
