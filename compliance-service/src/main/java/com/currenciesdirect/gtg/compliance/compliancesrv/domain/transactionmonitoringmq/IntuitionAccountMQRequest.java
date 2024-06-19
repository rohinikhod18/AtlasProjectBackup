package com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionAccountMQRequest.
 */
//AT-4744
@Getter 
@Setter
public class IntuitionAccountMQRequest implements Serializable{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The account id. */
	@JsonProperty(value = "AccountId")
	private String accountId;
	
	/** The acc sf id. */
	@JsonProperty(value = "acc_sf_id")
	private String accSfId;
	
	/** The branch. */
	@JsonProperty(value = "branch")
	private String branch;
	
	/** The affiliate number. */
	@JsonProperty(value = "affiliate_number")
	private String affiliateNumber;
	
	@JsonProperty(value = "cardDeliveryToSecondaryAddress")
	private String cardDeliveryToSecondaryAddress;

	@JsonProperty(value = "secondaryAddressPresent")
	private String secondaryAddressPresent;
	
	 //Add for AT-5318
	@JsonProperty(value = "ParentType")
	private String parentType; 
	
	@JsonProperty(value = "ParentID")
	private String parentID;  
	
	@JsonProperty(value = "MetInPerson")
	private String metInPerson;  
	
	@JsonProperty(value = "SelfieOnFile")
	private String selfieOnFile; 
	
	@JsonProperty(value = "VulnerabilityCharacteristic")
	private String vulnerabilityCharacteristic;  
	
	//AT-5376
	@JsonProperty(value = "NoOfChild")
	private Integer noOfChild; 

	// Add for AT-5393
	@JsonProperty(value = "Legacy_Account_Aurora_Titan_Customer_No")
	private String legacyAccountAuroraTitanCustomerNo;
	
	// AT-5529
	/** The blacklist. */
	@JsonProperty(value = "blacklist")
	private String blacklist;
	
	// AT-5529
	/** The sanction result. */
	@JsonProperty(value = "sanction_result")
	private String sanctionResult;
}
