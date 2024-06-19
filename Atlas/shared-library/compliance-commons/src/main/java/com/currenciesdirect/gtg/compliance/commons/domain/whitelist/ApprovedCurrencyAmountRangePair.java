/*
 * 
 */
package com.currenciesdirect.gtg.compliance.commons.domain.whitelist;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class ApprovedCurrencyAmountRangePair.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "code", "txnAmountUpperLimit" })
public class ApprovedCurrencyAmountRangePair implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The code. */
	@ApiModelProperty(value = "The code", required = true)
	@JsonProperty("code")
	private String code;

	/** The txn amount upper limit. */
	@ApiModelProperty(value = "The transaction amount upper limit", required = true)
	@JsonProperty("txnAmountUpperLimit")
	private Double txnAmountUpperLimit;

	/**
	 * Gets the code.
	 *
	 * @return The code
	 */
	@JsonProperty("code")
	public String getCode() {
		return code;
	}

	/**
	 * Sets the code.
	 *
	 * @param code
	 *            The code
	 */
	@JsonProperty("code")
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Gets the txn amount upper limit.
	 *
	 * @return The txnAmountUpperLimit
	 */
	@JsonProperty("txnAmountUpperLimit")
	public Double getTxnAmountUpperLimit() {
		return txnAmountUpperLimit;
	}

	/**
	 * Sets the txn amount upper limit.
	 *
	 * @param txnAmountUpperLimit
	 *            The txnAmountUpperLimit
	 */
	@JsonProperty("txnAmountUpperLimit")
	public void setTxnAmountUpperLimit(Double txnAmountUpperLimit) {
		this.txnAmountUpperLimit = txnAmountUpperLimit;
	}
	
	/**
	 * Removes the duplicates.
	 *
	 * @param amountRange the amount range
	 */
	public static  void removeDuplicates(List<ApprovedCurrencyAmountRangePair> amountRange){
		HashSet<ApprovedCurrencyAmountRangePair> set = new HashSet<>(amountRange);
		amountRange.clear();
		amountRange.addAll(set);
		set.clear();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		ApprovedCurrencyAmountRangePair other = (ApprovedCurrencyAmountRangePair) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code)) {
			return false;
		  }
		if(txnAmountUpperLimit > other.getTxnAmountUpperLimit()){
			other.setTxnAmountUpperLimit(txnAmountUpperLimit);
		}
		return true;
	}

}