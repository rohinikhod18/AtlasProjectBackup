package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class WatchListDetails.
 */
public class WatchListDetails {

	private String name;
	
	private boolean stopPaymentOut;
	
	private boolean stopPaymentIn;
	
	private Integer watchlistCategory;

	public Integer getWatchlistCategory() {
		return watchlistCategory;
	}

	public void setWatchlistCategory(Integer watchlistCategory) {
		this.watchlistCategory = watchlistCategory;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStopPaymentOut() {
		return stopPaymentOut;
	}

	public void setStopPaymentOut(boolean stopPaymentOut) {
		this.stopPaymentOut = stopPaymentOut;
	}

	public boolean isStopPaymentIn() {
		return stopPaymentIn;
	}

	public void setStopPaymentIn(boolean stopPaymentIn) {
		this.stopPaymentIn = stopPaymentIn;
	}
}
