package com.currenciesdirect.gtg.compliance.core.domain;

/**
 * The Class UploadDocumentTypeDBDto.
 */
public class UploadDocumentTypeDBDto {

	/** The id. */
	private Integer id;
	
	/** The category. */
	private String category; //PIA OR POA
	
	/** The document Name. */
	private String documentName; //Drivers License 

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}


}
