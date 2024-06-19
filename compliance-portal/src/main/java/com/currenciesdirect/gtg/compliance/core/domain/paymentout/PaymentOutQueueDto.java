package com.currenciesdirect.gtg.compliance.core.domain.paymentout;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseQueueDto;

/**
 *  The Class PaymentOutQueueDto.
 *
 */
public class PaymentOutQueueDto extends BaseQueueDto {

	/** The payment out queue. */
	private List<PaymentOutQueueData> paymentOutQueue;
	
	/**
	 * @return the paymentOutQueue
	 */
	public List<PaymentOutQueueData> getPaymentOutQueue() {
		return paymentOutQueue;
	}

	/**
	 * @param paymentOutQueue the paymentOutQueue to set
	 */
	public void setPaymentOutQueue(List<PaymentOutQueueData> paymentOutQueue) {
		this.paymentOutQueue = paymentOutQueue;
	}

}
