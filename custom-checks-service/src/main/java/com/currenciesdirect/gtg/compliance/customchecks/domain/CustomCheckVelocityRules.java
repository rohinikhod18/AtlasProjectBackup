package com.currenciesdirect.gtg.compliance.customchecks.domain;

/**
 * The Class CustomCheckVelocityRules.
 */
public class CustomCheckVelocityRules {

	/** The count threshold. */
	private Integer countThreshold;
	
	/** The amount threshold. */
	private Double amountThreshold;

	/**
	 * Gets the count threshold.
	 *
	 * @return the count threshold
	 */
	public Integer getCountThreshold() {
		return countThreshold;
	}

	/**
	 * Sets the count threshold.
	 *
	 * @param countThreshold the new count threshold
	 */
	public void setCountThreshold(Integer countThreshold) {
		this.countThreshold = countThreshold;
	}

	/**
	 * Gets the amount threshold.
	 *
	 * @return the amount threshold
	 */
	public Double getAmountThreshold() {
		return amountThreshold;
	}

	/**
	 * Sets the amount threshold.
	 *
	 * @param amountThreshold the new amount threshold
	 */
	public void setAmountThreshold(Double amountThreshold) {
		this.amountThreshold = amountThreshold;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amountThreshold == null) ? 0 : amountThreshold.hashCode());
		result = prime * result + ((countThreshold == null) ? 0 : countThreshold.hashCode());
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
		CustomCheckVelocityRules other = (CustomCheckVelocityRules) obj;
		if (amountThreshold == null) {
			if (other.amountThreshold != null)
				return false;
		} else if (!amountThreshold.equals(other.amountThreshold)) {
			return false;
		  }
		if (countThreshold == null) {
			if (other.countThreshold != null)
				return false;
		} else if (!countThreshold.equals(other.countThreshold)) {
			return false;
		  }
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CustomCheckVelocityRules [countThreshold=" + countThreshold + ", amountThreshold=" + amountThreshold
				+ "]";
	}

}
