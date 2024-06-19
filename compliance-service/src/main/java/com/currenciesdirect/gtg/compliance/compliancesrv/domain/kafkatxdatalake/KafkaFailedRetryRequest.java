package com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author prashant.verma
 */
public class KafkaFailedRetryRequest implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The audit request ids. */
	@ApiModelProperty(value = "The audit request ids", required = false)
	@JsonProperty(value="auditIds")
	private String auditIds;
	
	/** The audit request from date */
	@ApiModelProperty(value = "The audit request from date", required = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonProperty(value="fromDate")
	private String fromDate;
	
	/** The audit request to date. */
	@ApiModelProperty(value = "The audit request to date", required = false)
	@JsonFormat(pattern = "dd/MM/yyyy")
	@JsonProperty(value="toDate")
	private String toDate;

	public String getAuditIds() {
		return auditIds;
	}

	public void setAuditIds(String auditIds) {
		this.auditIds = auditIds;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	@Override
	public String toString() {
		return "KafkaFailedRetryRequestBody [auditIds=" + auditIds + ", fromDate=" + fromDate + ", toDate=" + toDate + "]";
	}
	
	
}