package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class Page.
 */
public class Page {

	private Integer minRecord;

	private Integer maxRecord;

	private Integer currentRecord;

	private Integer totalRecords;

	private Integer currentPage;

	private Integer totalPages;

	private Integer pageSize;

	public Integer getMinRecord() {
		return minRecord;
	}

	public void setMinRecord(Integer minRecord) {
		this.minRecord = minRecord;
	}

	public Integer getMaxRecord() {
		return maxRecord;
	}

	public void setMaxRecord(Integer maxRecord) {
		this.maxRecord = maxRecord;
	}

	public Integer getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(Integer totalRecords) {
		this.totalRecords = totalRecords;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Integer totalPages) {
		this.totalPages = totalPages;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getCurrentRecord() {
		return currentRecord;
	}

	public void setCurrentRecord(Integer currentRecord) {
		this.currentRecord = currentRecord;
	}
}
