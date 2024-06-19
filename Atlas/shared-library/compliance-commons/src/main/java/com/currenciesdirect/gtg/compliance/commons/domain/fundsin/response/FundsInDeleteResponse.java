package com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class FundsInDeleteResponse.
 */
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
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The org code. */
	@JsonProperty("orgCode")
	private String orgCode;
	
	/** The payment fundsin id. */
	@JsonProperty("payment_fundsin_id")
	private Integer paymentFundsinId;
	
	/** The status. */
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
	 * Gets the payment fundsin id.
	 *
	 * @return The paymentFundsinId
	 */
	@JsonProperty("payment_fundsin_id")
	public Integer getPaymentFundsinId() {
		return paymentFundsinId;
	}

	/**
	 * Sets the payment fundsin id.
	 *
	 * @param paymentFundsinId            The payment_fundsin_id
	 */
	@JsonProperty("payment_fundsin_id")
	public void setPaymentFundsinId(Integer paymentFundsinId) {
		this.paymentFundsinId = paymentFundsinId;
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

