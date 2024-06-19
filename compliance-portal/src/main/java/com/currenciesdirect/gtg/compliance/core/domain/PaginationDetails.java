package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class PaginationDetails.
 */
public class PaginationDetails {

	private PaginationData prevRecord;
	
	private PaginationData nextRecord;
	
	private PaginationData totalRecords;
	
	private PaginationData firstRecord;

	public PaginationData getPrevRecord() {
		return prevRecord;
	}

	public void setPrevRecord(PaginationData prevRecord) {
		this.prevRecord = prevRecord;
	}

	public PaginationData getNextRecord() {
		return nextRecord;
	}

	public void setNextRecord(PaginationData nextRecord) {
		this.nextRecord = nextRecord;
	}

	public PaginationData getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(PaginationData totalRecords) {
		this.totalRecords = totalRecords;
	}

	public PaginationData getFirstRecord() {
		return firstRecord;
	}

	public void setFirstRecord(PaginationData firstRecord) {
		this.firstRecord = firstRecord;
	}
}
