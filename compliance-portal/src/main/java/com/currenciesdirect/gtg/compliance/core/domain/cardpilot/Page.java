package com.currenciesdirect.gtg.compliance.core.domain.cardpilot;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class Page.
 * AT-4625
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Page implements Serializable{


	  /** The Constant serialVersionUID. */
	  private static final long serialVersionUID = 1L;

	  /** The min record. */
	  @JsonProperty(value = "min_record")
	  private Integer minRecord;

	  /** The max record. */
	  @JsonProperty(value = "max_record")
	  private Integer maxRecord;

	  /** The current record. */
	  @JsonProperty(value = "current_record")
	  private Integer currentRecord;

	  /** The total records. */
	  @JsonProperty(value = "total_records")
	  private Integer totalRecords;

	  /** The current page. */
	  @JsonProperty(value = "current_page")
	  private Integer currentPage;

	  /** The total pages. */
	  @JsonProperty(value = "total_pages")
	  private Integer totalPages;

	  /** The page size. */
	  @JsonProperty(value = "size")
	  private Integer pageSize;

	  /** The page offset. */
	  @JsonProperty(value = "offset")
	  private Integer pageOffset;

	  /**
	   * Gets the min record.
	   *
	   * @return the min record
	   */
	  public Integer getMinRecord() {
	    return minRecord;
	  }

	  /**
	   * Sets the min record.
	   *
	   * @param minRecord the new min record
	   */
	  public void setMinRecord(Integer minRecord) {
	    this.minRecord = minRecord;
	  }

	  /**
	   * Gets the max record.
	   *
	   * @return the max record
	   */
	  public Integer getMaxRecord() {
	    return maxRecord;
	  }

	  /**
	   * Sets the max record.
	   *
	   * @param maxRecord the new max record
	   */
	  public void setMaxRecord(Integer maxRecord) {
	    this.maxRecord = maxRecord;
	  }

	  /**
	   * Gets the current record.
	   *
	   * @return the current record
	   */
	  public Integer getCurrentRecord() {
	    return currentRecord;
	  }

	  /**
	   * Sets the current record.
	   *
	   * @param currentRecord the new current record
	   */
	  public void setCurrentRecord(Integer currentRecord) {
	    this.currentRecord = currentRecord;
	  }

	  /**
	   * Gets the total records.
	   *
	   * @return the total records
	   */
	  public Integer getTotalRecords() {
	    return totalRecords;
	  }

	  /**
	   * Sets the total records.
	   *
	   * @param totalRecords the new total records
	   */
	  public void setTotalRecords(Integer totalRecords) {
	    this.totalRecords = totalRecords;
	  }

	  /**
	   * Gets the current page.
	   *
	   * @return the current page
	   */
	  public Integer getCurrentPage() {
	    return currentPage;
	  }

	  /**
	   * Sets the current page.
	   *
	   * @param currentPage the new current page
	   */
	  public void setCurrentPage(Integer currentPage) {
	    this.currentPage = currentPage;
	  }

	  /**
	   * Gets the total pages.
	   *
	   * @return the total pages
	   */
	  public Integer getTotalPages() {
	    return totalPages;
	  }

	  /**
	   * Sets the total pages.
	   *
	   * @param totalPages the new total pages
	   */
	  public void setTotalPages(Integer totalPages) {
	    this.totalPages = totalPages;
	  }

	  /**
	   * Gets the page size.
	   *
	   * @return the page size
	   */
	  public Integer getPageSize() {
	    return pageSize;
	  }

	  /**
	   * Sets the page size.
	   *
	   * @param pageSize the new page size
	   */
	  public void setPageSize(Integer pageSize) {
	    this.pageSize = pageSize;
	  }

	  /**
	   * Gets the page offset.
	   *
	   * @return the page offset
	   */
	  public Integer getPageOffset() {
	    return pageOffset;
	  }

	  /**
	   * Sets the page offset.
	   *
	   * @param pageOffset the new page offset
	   */
	  public void setPageOffset(Integer pageOffset) {
	    this.pageOffset = pageOffset;
	  }

	  /* (non-Javadoc)
	   * @see java.lang.Object#toString()
	   */
	  @Override
	  public String toString() {
	    StringBuilder builder = new StringBuilder();
	    builder.append("Page [minRecord=");
	    builder.append(minRecord);
	    builder.append(", maxRecord=");
	    builder.append(maxRecord);
	    builder.append(", currentRecord=");
	    builder.append(currentRecord);
	    builder.append(", totalRecords=");
	    builder.append(totalRecords);
	    builder.append(", currentPage=");
	    builder.append(currentPage);
	    builder.append(", totalPages=");
	    builder.append(totalPages);
	    builder.append(", pageSize=");
	    builder.append(pageSize);
	    builder.append(", pageOffset=");
	    builder.append(pageOffset);
	    builder.append(']');
	    return builder.toString();
	  }
}
