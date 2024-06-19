package com.currenciesdirect.gtg.compliance.commons.domain.docupload;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class DocumentUploadStatusResponse.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentUploadStatusResponse  extends ServiceMessageResponse implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The file upload status. */
	private String fileUploadStatus;
	
	
	/**
	 * Gets the file upload status.
	 *
	 * @return the file upload status
	 */
	public String getFileUploadStatus() {
		return fileUploadStatus;
	}

	/**
	 * Sets the file upload status.
	 *
	 * @param fileUploadStatus the new file upload status
	 */
	public void setFileUploadStatus(String fileUploadStatus) {
		this.fileUploadStatus = fileUploadStatus;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	@Override
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	@Override
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the error description.
	 *
	 * @return the error description
	 */
	@Override
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * Sets the error description.
	 *
	 * @param errorDescription the new error description
	 */
	@Override
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DocumentUploadStatusResponse [fileUploadStatus=" + fileUploadStatus + ", errorCode=" + errorCode
				+ ", errorDescription=" + errorDescription + "]";
	}

}
