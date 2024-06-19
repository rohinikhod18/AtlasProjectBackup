package com.currenciesdirect.gtg.compliance.core.blacklist.payrefport;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ProviderBlacklistPayrefRequest implements Serializable{
	
	@ApiModelProperty(value = "Payment reference", required = true)
	@JsonProperty("payment_reference")
	private String paymentReference;

	public String getPaymentReference() {
		return paymentReference;
	}

	public void setPaymentReference(String paymentReference) {
		this.paymentReference = paymentReference;
	}

}
