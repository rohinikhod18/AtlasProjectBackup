package com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OnfidoBreakdown implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The face comparison. */
	@ApiModelProperty(value = "The face comparison shows whether the face in the document matches the face in the live photo or live video", required = true)
	@JsonProperty(value = "face_comparison")
	private FaceComparison faceComparison;
	
	/** The image integrity. */
	@ApiModelProperty(value = "The image integrity shows whether the quality of the uploaded files and the content contained within them were sufficient to perform a face comparison", required = true)
	@JsonProperty(value = "image_integrity")
	private ImageIntegrity imageIntegrity;
	
	/** The visual authenticity. */
	@ApiModelProperty(value = "The visual authenticity shows whether the person in the live photo or live video is real (not a spoof) and live (only done for live videos)", required = true)
	@JsonProperty(value = "visual_authenticity")
	private VisualAuthenticity visualAuthenticity;
	
	/** The document data comparison. */
	@ApiModelProperty(value = "The document data comparison shows whether data on the document is consistent with data provided by an applicant", required = true)
	@JsonProperty(value = "data_comparison")
	private DocumentDataComparison documentDataComparison;
	
	/** The document data validation. */
	@ApiModelProperty(value = "The document data validation shows whether algorithmically-validatable elements are correct e.g. MRZ lines and document numbers", required = true)
	@JsonProperty(value = "data_validation")
	private DocumentDataValidation documentDataValidation;
	
	/** The document police record. */
	@ApiModelProperty(value = "The document police record shows whether the document has been identified as lost, stolen or otherwise compromised", required = true)
	@JsonProperty(value = "police_record")
	private DocumentPoliceRecord documentPoliceRecord;
	
	/** The document compromised document. */
	@ApiModelProperty(value = "The document compromised document shows whether the image of the document has been found in our internal database of compromised documents", required = true)
	@JsonProperty(value = "compromised_document")
	private DocumentCompromisedDocument documentCompromisedDocument;
}
