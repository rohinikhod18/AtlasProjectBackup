package com.currenciesdirect.gtg.compliance.customchecks.esport;

import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;

public class SearchResult {

	private Long intraDayCount=0L;
	private Long otherAccountsWithBene=0L;
	private String matchedAccNumber;
	private AccountWhiteList accWhiteList;
	/**
	 * @return the intraDayCount
	 */
	public Integer getIntraDayCount() {
		return intraDayCount.intValue();
	}
	/**
	 * @param intraDayCount the intraDayCount to set
	 */
	public void setIntraDayCount(Long intraDayCount) {
		this.intraDayCount = intraDayCount;
	}
	/**
	 * @return the otherAccountsWithBene
	 */
	public Integer getOtherAccountsWithBene() {
		return otherAccountsWithBene.intValue();
	}
	/**
	 * @param otherAccountsWithBene the otherAccountsWithBene to set
	 */
	public void setOtherAccountsWithBene(Long otherAccountsWithBene) {
		this.otherAccountsWithBene = otherAccountsWithBene;
	}
	
	/**
	 * @return the matchedAccNumber
	 */
	public String getMatchedAccNumber() {
		return matchedAccNumber;
	}
	/**
	 * @param matchedAccNumber the matchedAccNumber to set
	 */
	public void setMatchedAccNumber(String matchedAccNumber) {
		this.matchedAccNumber = matchedAccNumber;
	}
	/**
	 * @return the accWhiteList
	 */
	public AccountWhiteList getAccWhiteList() {
		return accWhiteList;
	}
	/**
	 * @param accWhiteList the accWhiteList to set
	 */
	public void setAccWhiteList(AccountWhiteList accWhiteList) {
		this.accWhiteList = accWhiteList;
	}
	
}
