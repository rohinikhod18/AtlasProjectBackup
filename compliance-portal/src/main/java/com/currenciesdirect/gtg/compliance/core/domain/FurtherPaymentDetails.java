package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class PaymentDetails.
 */
public class FurtherPaymentDetails {

	private List<FurtherPaymentInDetails> furtherPaymentInDetails;
	
	private List<FurtherPaymentOutDetails> furtherPaymentOutDetails;

	private Integer payInDetailsTotalRecords;
	
	private Integer payOutDetailsTotalRecords;
	

	public Integer getPayInDetailsTotalRecords() {
		return payInDetailsTotalRecords;
	}

	public void setPayInDetailsTotalRecords(Integer payInDetailsTotalRecords) {
		this.payInDetailsTotalRecords = payInDetailsTotalRecords;
	}

	public Integer getPayOutDetailsTotalRecords() {
		return payOutDetailsTotalRecords;
	}

	public void setPayOutDetailsTotalRecords(Integer payOutDetailsTotalRecords) {
		this.payOutDetailsTotalRecords = payOutDetailsTotalRecords;
	}

	public List<FurtherPaymentInDetails> getFurtherPaymentInDetails() {
		return furtherPaymentInDetails;
	}

	public void setFurtherPaymentInDetails(List<FurtherPaymentInDetails> furtherPaymentInDetails) {
		this.furtherPaymentInDetails = furtherPaymentInDetails;
	}

	public List<FurtherPaymentOutDetails> getFurtherPaymentOutDetails() {
		return furtherPaymentOutDetails;
	}

	public void setFurtherPaymentOutDetails(List<FurtherPaymentOutDetails> furtherPaymentOutDetails) {
		this.furtherPaymentOutDetails = furtherPaymentOutDetails;
	}

	

}
