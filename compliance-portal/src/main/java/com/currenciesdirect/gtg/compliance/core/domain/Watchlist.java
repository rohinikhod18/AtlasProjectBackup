package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class Watchlist.
 */
public class Watchlist {

	/** The watchlist data. */
	private List<WatchListData> watchlistData;

	/**
	 * Gets the watchlist data.
	 *
	 * @return the watchlist data
	 */
	public List<WatchListData> getWatchlistData() {
		return watchlistData;
	}

	/**
	 * Sets the watchlist data.
	 *
	 * @param watchlistData
	 *            the new watchlist data
	 */
	public void setWatchlistData(List<WatchListData> watchlistData) {
		this.watchlistData = watchlistData;
	}
}
