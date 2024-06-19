package com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;

public class IntuitionPaymentStatusUpdateResponse extends BaseResponse implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String paymentStatus;

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

}
