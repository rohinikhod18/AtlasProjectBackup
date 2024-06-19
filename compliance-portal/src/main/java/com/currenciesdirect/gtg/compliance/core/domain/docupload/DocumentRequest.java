/**
 * 
 */
package com.currenciesdirect.gtg.compliance.core.domain.docupload;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Shashank D.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentRequest implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * AuroraAccountID.
	 */
	@XmlElement(required = true, name = "crmAccountID")
	private String crmAccountID;

	/**
	 * AuroraContactID.
	 */
	@XmlElement(required = true, name = "crmContactID")
	private String crmContactID;

	/**
	 * OrganizationCode.
	 */
	@XmlElement(required = true, name = "orgCode")
	private String orgCode;

	/**
	 * Source.
	 */
	@XmlElement(required = true, name = "source")
	private String source;

	/**
	 * withDetails.
	 */
	@XmlElement(required = true, name = "withDetails")
	private Boolean withDetails;

	/**
	 * @return the withDetails
	 */
	public Boolean getWithDetails() {
		return withDetails;
	}

	/**
	 * @param withDetails
	 *            the withDetails to set
	 */
	public void setWithDetails(Boolean withDetails) {
		this.withDetails = withDetails;
	}

	/**
	 * @return the crmAccountID
	 */
	public String getCrmAccountID() {
		return crmAccountID;
	}

	/**
	 * @param crmAccountID
	 *            the crmAccountID to set
	 */
	public void setCrmAccountID(String crmAccountID) {
		this.crmAccountID = crmAccountID;
	}

	/**
	 * @return the crmContactID
	 */
	public String getCrmContactID() {
		return crmContactID;
	}

	/**
	 * @param crmContactID
	 *            the crmContactID to set
	 */
	public void setCrmContactID(String crmContactID) {
		this.crmContactID = crmContactID;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode
	 *            the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
