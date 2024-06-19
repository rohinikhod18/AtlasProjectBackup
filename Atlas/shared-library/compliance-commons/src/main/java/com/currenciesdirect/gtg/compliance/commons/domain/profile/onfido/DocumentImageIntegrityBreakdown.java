package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DocumentImageIntegrityBreakdown.
 */
public class DocumentImageIntegrityBreakdown implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The conclusive document quality. */
	@ApiModelProperty(value = "The conclusive document quality", required = true)
	@JsonProperty(value = "conclusive_document_quality")
	private BreakdownEntityDetails conclusiveDocumentQuality;
	
	/** The color picture. */
	@ApiModelProperty(value = "The color picture", required = true)
	@JsonProperty(value = "colour_picture")
	private BreakdownEntityDetails colorPicture;
	
	/** The supported documents. */
	@ApiModelProperty(value = "The supported documents", required = true)
	@JsonProperty(value = "supported_document")
	private BreakdownEntityDetails supportedDocuments;
	
	/** The picture face integrity. */
	@ApiModelProperty(value = "The picture face integrity", required = true)
	@JsonProperty(value = "picture_face_integrity")
	private BreakdownEntityDetails pictureFaceIntegrity;
	
	/** The image quality. */
	@ApiModelProperty(value = "The image quality", required = true)
	@JsonProperty(value = "image_quality")
	private BreakdownEntityDetails imageQuality;

	/**
	 * @return the conclusiveDocumentQuality
	 */
	public BreakdownEntityDetails getConclusiveDocumentQuality() {
		return conclusiveDocumentQuality;
	}

	/**
	 * @param conclusiveDocumentQuality the conclusiveDocumentQuality to set
	 */
	public void setConclusiveDocumentQuality(BreakdownEntityDetails conclusiveDocumentQuality) {
		this.conclusiveDocumentQuality = conclusiveDocumentQuality;
	}

	/**
	 * @return the colorPicture
	 */
	public BreakdownEntityDetails getColorPicture() {
		return colorPicture;
	}

	/**
	 * @param colorPicture the colorPicture to set
	 */
	public void setColorPicture(BreakdownEntityDetails colorPicture) {
		this.colorPicture = colorPicture;
	}

	/**
	 * @return the supportedDocuments
	 */
	public BreakdownEntityDetails getSupportedDocuments() {
		return supportedDocuments;
	}

	/**
	 * @param supportedDocuments the supportedDocuments to set
	 */
	public void setSupportedDocuments(BreakdownEntityDetails supportedDocuments) {
		this.supportedDocuments = supportedDocuments;
	}

	/**
	 * @return the pictureFaceIntegrity
	 */
	public BreakdownEntityDetails getPictureFaceIntegrity() {
		return pictureFaceIntegrity;
	}

	/**
	 * @param pictureFaceIntegrity the pictureFaceIntegrity to set
	 */
	public void setPictureFaceIntegrity(BreakdownEntityDetails pictureFaceIntegrity) {
		this.pictureFaceIntegrity = pictureFaceIntegrity;
	}

	/**
	 * @return the imageQuality
	 */
	public BreakdownEntityDetails getImageQuality() {
		return imageQuality;
	}

	/**
	 * @param imageQuality the imageQuality to set
	 */
	public void setImageQuality(BreakdownEntityDetails imageQuality) {
		this.imageQuality = imageQuality;
	}
	
	
}
