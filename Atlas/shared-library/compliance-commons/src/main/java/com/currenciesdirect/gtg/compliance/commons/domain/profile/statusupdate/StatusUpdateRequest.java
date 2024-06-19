package com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoReport;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * The Class OnfidoRequest.
 */
public class StatusUpdateRequest extends ServiceMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** The org code. */
	@ApiModelProperty(value = "The CD organisation code where the call originated", required = true, example = "Currencies Direct, CD SA, E4F, FCG, TorFX or TorFXOz", position = 5)
	@JsonProperty(value="org_code")
	private String orgCode;
	
	/** The crm account id. */
	@ApiModelProperty(value = "The crm account id", required = true)
	@JsonProperty(value="crm_account_id")
	private String crmAccountId;
	
	/** The crm contact id. */
	@ApiModelProperty(value = "The crm contact id", required = true)
	@JsonProperty(value="crm_contact_id")
	private String crmContactId;
	
	/** The reports. */
	@ApiModelProperty(value = "A List of OnFido reports", required = true)
	@JsonProperty(value="reports")
	private List<OnfidoReport> reports = new ArrayList<>();
	
	
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
	 * Gets the reports.
	 *
	 * @return the reports
	 */
	public List<OnfidoReport> getReports() {
		return reports;
	}

	/**
	 * Sets the reports.
	 *
	 * @param reports the new reports
	 */
	public void setReports(List<OnfidoReport> reports) {
		this.reports = reports;
	}

	/**
	 * @return the orgCode
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * @param orgCode the orgCode to set
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

}
