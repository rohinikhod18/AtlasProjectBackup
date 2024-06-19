package com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request;


import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.util.DoubleSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author bnt
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
	"payment_fundsout_id",
	"amount",
})
public class BaseBeneficiary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
	
	@ApiModelProperty(value = "beneficiary amount details", required = true)
	@JsonProperty("amount")
	@JsonSerialize(using=DoubleSerializer.class)
	private Double amount;

	/**
	 * 
	 * @return The paymentFundsoutId
	 */
	@JsonProperty("payment_fundsout_id")
	public Integer getPaymentFundsoutId() {
		return paymentFundsoutId;
	}

	/**
	 * 
	 * @param paymentFundsoutId
	 *            The payment_fundsout_id
	 */
	@JsonProperty("payment_fundsout_id")
	public void setPaymentFundsoutId(Integer paymentFundsoutId) {
		this.paymentFundsoutId = paymentFundsoutId;
	}

	/**
	 * 
	 * @return The firstName
	 */
	

	/**
	 * 
	 * @return The amount
	 */
	@JsonProperty("amount")
	public Double getAmount() {
		return amount;
	}

	/**
	 * 
	 * @param amount
	 *            The amount
	 */
	@JsonProperty("amount")
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdateBeneficiary [paymentFundsoutId=" + paymentFundsoutId + ", amount=" + amount + "]";
	}

}