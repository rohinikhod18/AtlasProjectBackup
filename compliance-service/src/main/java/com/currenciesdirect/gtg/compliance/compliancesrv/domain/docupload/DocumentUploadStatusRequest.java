package com.currenciesdirect.gtg.compliance.compliancesrv.domain.docupload;

import java.io.Serializable;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class DocumentUploadStatusRequest.
 * @author abhijitg
 */
public class DocumentUploadStatusRequest extends ServiceMessage implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The crm account id. */
	@ApiModelProperty(value = "The crm account id", required = true)
	private String crmAccountId;
	
	/** The crm contact id. */
	@ApiModelProperty(value = "The crm contact id", required = true)
	private String crmContactId;
	
	/** The org code. */
	@ApiModelProperty(value = "The org code", required = true)
	private String orgCode;
	
	/** The source. */
	@ApiModelProperty(value = "The source", required = true)
	private String source;
	
	/** The doc upload status. */
	@ApiModelProperty(value = "The doc upload status", required = true)
	private String docUploadStatus;

	/**
	 * Gets the crm account id.
	 *
	 * @return the crm account id
	 */
	public String getCrmAccountId() {
		return crmAccountId;
	}

	/**
	 * Sets the crm account id.
	 *
	 * @param crmAccountId the new crm account id
	 */
	public void setCrmAccountId(String crmAccountId) {
		this.crmAccountId = crmAccountId;
	}

	/**
	 * Gets the crm contact id.
	 *
	 * @return the crm contact id
	 */
	public String getCrmContactId() {
		return crmContactId;
	}

	/**
	 * Sets the crm contact id.
	 *
	 * @param crmContactId the new crm contact id
	 */
	public void setCrmContactId(String crmContactId) {
		this.crmContactId = crmContactId;
	}

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Gets the source.
	 *
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * Sets the source.
	 *
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Gets the doc upload status.
	 *
	 * @return the doc upload status
	 */
	public String getDocUploadStatus() {
		return docUploadStatus;
	}

	/**
	 * Sets the doc upload status.
	 *
	 * @param docUploadStatus the new doc upload status
	 */
	public void setDocUploadStatus(String docUploadStatus) {
		this.docUploadStatus = docUploadStatus;
	}

	@Override
	public String toString() {
		return "DocumentUploadStatusRequest [crmAccountId=" + crmAccountId + ", crmContactId=" + crmContactId
				+ ", orgCode=" + orgCode + ", source=" + source + ", docUploadStatus=" + docUploadStatus + "]";
	}
	
}
