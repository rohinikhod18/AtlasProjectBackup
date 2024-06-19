package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseQueueDto;

/**
 * The Class BeneficiaryQueueDto.
 */
public class PayeeQueueDto extends BaseQueueDto {

	/** The beneficiary queue. */
	private List<PayeeQueueData> payeeQueue;

	/**
	 * @return the beneficiaryQueue
	 */
	public List<PayeeQueueData> getPayeeQueue() {
		return payeeQueue;
	}

	/**
	 * @param beneficiaryQueue
	 *            the beneficiaryQueue to set
	 */
	public void setPayeeQueue(List<PayeeQueueData> payeeQueue) {
		this.payeeQueue = payeeQueue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((payeeQueue == null) ? 0 : payeeQueue.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		PayeeQueueDto other = (PayeeQueueDto) obj;
		if (payeeQueue == null) {
			if (other.payeeQueue != null)
				return false;
		} else if (!payeeQueue.equals(other.payeeQueue)) {
			return false;
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PayeeQueueDto [payeeQueue=" + payeeQueue + "]";
	}
}
