package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DocumentVisualAuthenticityBreakdown implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "other", required = true)
	@JsonProperty(value = "other")
	private BreakdownEntityDetails other;
	
	@ApiModelProperty(value = "Was the original document present", required = true)
	@JsonProperty(value = "original_document_present")
	private BreakdownEntityDetails originalDocumentPrsent;
	
	@ApiModelProperty(value = "The fonts", required = true)
	@JsonProperty(value = "fonts")
	private BreakdownEntityDetails fonts;
	
	@ApiModelProperty(value = "The picture face integrity", required = true)
	@JsonProperty(value = "picture_face_integrity")
	private BreakdownEntityDetails pictureFaceIntegrity;
	
	@ApiModelProperty(value = "The security features", required = true)
	@JsonProperty(value = "security_features")
	private BreakdownEntityDetails securityFeautures;
	
	@ApiModelProperty(value = "The digital tampering", required = true)
	@JsonProperty(value = "digital_tampering")
	private BreakdownEntityDetails digitalTampering;
	
	@ApiModelProperty(value = "The template", required = true)
	@JsonProperty(value = "template")
	private BreakdownEntityDetails template;
	
	@ApiModelProperty(value = "The face detection", required = true)
	@JsonProperty(value = "face_detection")
	private BreakdownEntityDetails faceDetection;

	/**
	 * @return the other
	 */
	public BreakdownEntityDetails getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(BreakdownEntityDetails other) {
		this.other = other;
	}

	/**
	 * @return the originalDocumentPrsent
	 */
	public BreakdownEntityDetails getOriginalDocumentPrsent() {
		return originalDocumentPrsent;
	}

	/**
	 * @param originalDocumentPrsent the originalDocumentPrsent to set
	 */
	public void setOriginalDocumentPrsent(BreakdownEntityDetails originalDocumentPrsent) {
		this.originalDocumentPrsent = originalDocumentPrsent;
	}

	/**
	 * @return the fonts
	 */
	public BreakdownEntityDetails getFonts() {
		return fonts;
	}

	/**
	 * @param fonts the fonts to set
	 */
	public void setFonts(BreakdownEntityDetails fonts) {
		this.fonts = fonts;
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
	 * @return the securityFeautures
	 */
	public BreakdownEntityDetails getSecurityFeautures() {
		return securityFeautures;
	}

	/**
	 * @param securityFeautures the securityFeautures to set
	 */
	public void setSecurityFeautures(BreakdownEntityDetails securityFeautures) {
		this.securityFeautures = securityFeautures;
	}

	/**
	 * @return the digitalTampering
	 */
	public BreakdownEntityDetails getDigitalTampering() {
		return digitalTampering;
	}

	/**
	 * @param digitalTampering the digitalTampering to set
	 */
	public void setDigitalTampering(BreakdownEntityDetails digitalTampering) {
		this.digitalTampering = digitalTampering;
	}

	/**
	 * @return the template
	 */
	public BreakdownEntityDetails getTemplate() {
		return template;
	}

	/**
	 * @param template the template to set
	 */
	public void setTemplate(BreakdownEntityDetails template) {
		this.template = template;
	}

	/**
	 * @return the faceDetection
	 */
	public BreakdownEntityDetails getFaceDetection() {
		return faceDetection;
	}

	/**
	 * @param faceDetection the faceDetection to set
	 */
	public void setFaceDetection(BreakdownEntityDetails faceDetection) {
		this.faceDetection = faceDetection;
	}
}
