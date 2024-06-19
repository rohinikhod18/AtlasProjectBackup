package com.currenciesdirect.gtg.compliance.core.domain.paymentin;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseQueueDto;

public class PaymentInQueueDto extends BaseQueueDto {
	
	private List<PaymentInQueueData> paymentInQueue;

	/** The total pages. */
	private Integer totalPages;

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	
	public List<PaymentInQueueData> getPaymentInQueue() {
		return paymentInQueue;
	}

	public void setPaymentInQueue(List<PaymentInQueueData> paymentInQueue) {
		this.paymentInQueue = paymentInQueue;
	}
	
	

}
