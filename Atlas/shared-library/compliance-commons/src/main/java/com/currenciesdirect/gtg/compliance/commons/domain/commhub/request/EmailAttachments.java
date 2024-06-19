package com.currenciesdirect.gtg.compliance.commons.domain.commhub.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailAttachments {

	/** The page orientation. */
	@JsonProperty("email_id")
	private String pageOrientation;

    /** The attachment type. */
	@JsonProperty("attachment_type")
    private String attachmentType;

    /** The mime type. */
	@JsonProperty("mime_type")
    private String mimeType;

    /** The attachment name. */
	@JsonProperty("attachment_name")
    private String attachmentName;

    /** The table data. */
	@JsonProperty("table_data")
    private EmailTableData[] tableData;

    /** The place holder. */
	@JsonProperty("place_holder")
    private String placeHolder;

    /** The page size. */
	@JsonProperty("page_size")
    private String pageSize;

	/**
	 * @return the pageOrientation
	 */
	public String getPageOrientation() {
		return pageOrientation;
	}

	/**
	 * @param pageOrientation the pageOrientation to set
	 */
	public void setPageOrientation(String pageOrientation) {
		this.pageOrientation = pageOrientation;
	}

	/**
	 * @return the attachmentType
	 */
	public String getAttachmentType() {
		return attachmentType;
	}

	/**
	 * @param attachmentType the attachmentType to set
	 */
	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the tableData
	 */
	public EmailTableData[] getTableData() {
		return tableData;
	}

	/**
	 * @param tableData the tableData to set
	 */
	public void setTableData(EmailTableData[] tableData) {
		this.tableData = tableData;
	}

	/**
	 * @return the placeHolder
	 */
	public String getPlaceHolder() {
		return placeHolder;
	}

	/**
	 * @param placeHolder the placeHolder to set
	 */
	public void setPlaceHolder(String placeHolder) {
		this.placeHolder = placeHolder;
	}

	/**
	 * @return the pageSize
	 */
	public String getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

    
 }
