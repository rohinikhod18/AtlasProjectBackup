package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout.request;


import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.compliancesrv.util.DoubleSerializer;
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
public class UpdateBeneficiary implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "beneficiary funds out id", example = "3192830", required = true)
	@JsonProperty("payment_fundsout_id")
	private Integer paymentFundsoutId;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((paymentFundsoutId == null) ? 0 : paymentFundsoutId.hashCode());
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
		UpdateBeneficiary other = (UpdateBeneficiary) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount)) {
			return false;
		}
		if (paymentFundsoutId == null) {
			if (other.paymentFundsoutId != null)
				return false;
		} else if (!paymentFundsoutId.equals(other.paymentFundsoutId)) {
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UpdateBeneficiary [paymentFundsoutId=" + paymentFundsoutId + ", amount=" + amount + "]";
	}

}