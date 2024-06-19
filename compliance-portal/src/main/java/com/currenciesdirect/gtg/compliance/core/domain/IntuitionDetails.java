package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

/**
 * The Class IntuitionDtails.
 * AT-4114
 * @author SayliS
 */
public class IntuitionDetails {
	
	/** The sanction. */
	private List<Intuition> intuition;
	
	/** The pass count. */
	private Integer passCount;
	
	/** The fail count. */
	private Integer failCount;
	
	/** The intuition total records. */
	private Integer intuitionTotalRecords;

	/**
	 * @return the intuition
	 */
	public List<Intuition> getIntuition() {
		return intuition;
	}

	/**
	 * @param intuition the intuition to set
	 */
	public void setIntuition(List<Intuition> intuition) {
		this.intuition = intuition;
	}

	/**
	 * @return the passCount
	 */
	public Integer getPassCount() {
		return passCount;
	}

	/**
	 * @param passCount the passCount to set
	 */
	public void setPassCount(Integer passCount) {
		this.passCount = passCount;
	}

	/**
	 * @return the failCount
	 */
	public Integer getFailCount() {
		return failCount;
	}

	/**
	 * @param failCount the failCount to set
	 */
	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}

	/**
	 * @return the intuitionTotalRecords
	 */
	public Integer getIntuitionTotalRecords() {
		return intuitionTotalRecords;
	}

	/**
	 * @param intuitionTotalRecords the intuitionTotalRecords to set
	 */
	public void setIntuitionTotalRecords(Integer intuitionTotalRecords) {
		this.intuitionTotalRecords = intuitionTotalRecords;
	}
	
	
}
