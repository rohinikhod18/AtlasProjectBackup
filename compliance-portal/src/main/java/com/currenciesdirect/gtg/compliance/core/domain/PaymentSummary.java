package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaymentSummary.
 */
public class PaymentSummary {
	
	/** The first trade date. */
	private String firstTradeDate;
	
	/** The last trade date. */
	private String lastTradeDate;
	
	/** The last trade date. */
	private Integer noOfTrades;
	
	/** The total sale amount. */
	private Double totalSaleAmount;

	/**
	 * @return the firstTradeDate
	 */
	public String getFirstTradeDate() {
		return firstTradeDate;
	}

	/**
	 * @param firstTradeDate the firstTradeDate to set
	 */
	public void setFirstTradeDate(String firstTradeDate) {
		this.firstTradeDate = firstTradeDate;
	}

	/**
	 * @return the lastTradeDate
	 */
	public String getLastTradeDate() {
		return lastTradeDate;
	}

	/**
	 * @param lastTradeDate the lastTradeDate to set
	 */
	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}

	/**
	 * @return the noOfTrades
	 */
	public Integer getNoOfTrades() {
		return noOfTrades;
	}

	/**
	 * @param noOfTrades the noOfTrades to set
	 */
	public void setNoOfTrades(Integer noOfTrades) {
		this.noOfTrades = noOfTrades;
	}

	/**
	 * @return the totalSaleAmount
	 */
	public Double getTotalSaleAmount() {
		return totalSaleAmount;
	}

	/**
	 * @param totalSaleAmount the totalSaleAmount to set
	 */
	public void setTotalSaleAmount(Double totalSaleAmount) {
		this.totalSaleAmount = totalSaleAmount;
	}
	
		
	
}
