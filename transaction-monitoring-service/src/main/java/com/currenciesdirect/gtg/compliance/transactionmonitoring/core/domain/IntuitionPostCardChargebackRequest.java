package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter 
@Setter
public class IntuitionPostCardChargebackRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The update status. */
	@JsonProperty(value = "Update_Status")
	public String updateStatus;
	
	/** The update date. */
	@JsonProperty(value = "Date_Update")
	public String updateDate;
	
	/** The update amount. */
	@JsonProperty(value = "Refund_or_Disp_Amount")
	public Double updateAmount;
	
	/** The chargeback reason. */
	@JsonProperty(value = "Chargeback_Reason")
	public String chargebackReason;
	
}
