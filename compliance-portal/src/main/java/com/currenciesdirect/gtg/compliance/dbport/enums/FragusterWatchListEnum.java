package com.currenciesdirect.gtg.compliance.dbport.enums;

/**
 * The Enum FragusterWatchListEnum.
 * @author Tejas I
 * Class responsibility :This class has watchlist of Fraguster and it returns true if found
 */
public enum FragusterWatchListEnum {

	/** The approved. */
	APPROVED("Approved","Approved"), 
	
	/** The Fraugster high risk of fraud. */
	FRAUGSTER_HIGH_RISK_OF_FRAUD("Fraugster high risk of fraud","Under Suspicion");
	
	/** The watchlist. */
	private String watchlist;	
	
	/** The watchlist code. */
	private String watchlistCode;	
	
 
	/**
	 * Instantiates a new fraguster watch list enum.
	 *
	 * @param watchlist the watchlist
	 * @param watchlistCode the watchlist code
	 */
	private FragusterWatchListEnum(String watchlist,String watchlistCode ) {
		this.watchlist = watchlist;
		this.watchlistCode=watchlistCode;
	}
	
	/**
	 * Gets the watchlist.
	 *
	 * @return the watchlist
	 */
	public String getWatchlist() {
		return watchlist;
	}
	
	
	/**
	 * Gets the watchlist code.
	 *
	 * @return the watchlist code
	 */
	public String getWatchlistCode() {
		return watchlistCode;
	}

	/**
	 * Gets the fraugster watchlist.
	 *
	 * @param watchlist the watchlist
	 * @return the fraugster watchlist
	 */
	public static Boolean getFraugsterWatchlist(String watchlist) {
		Boolean value;
		for(FragusterWatchListEnum fragusterWatchListEnum : FragusterWatchListEnum.values()){
			if (fragusterWatchListEnum.getWatchlist().equals(watchlist)) {
				value=Boolean.TRUE;
				return value;
			}
		}
		value=Boolean.FALSE;
		return value;
	}
}

