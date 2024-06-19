package com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class IntuitionSignupAccountProviderRequest.
 */
@Getter 
@Setter
public class IntuitionSignupAccountProviderRequest implements Serializable{

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
	
	//Add for AT-5393
	@JsonProperty(value = "Legacy_Account_Aurora_Titan_Customer_No")
	private String legacyAccountAuroraTitanCustomerNo;
	
	//Added for AT-5457
	@JsonProperty(value = "ConsumerDutyScope")
	private String consumerDutyScope;
	
	// Added for AT-5529
	/** The blacklist. */
	@JsonProperty(value = "blacklist")
	private String blacklist;
	
	// Added for AT-5529
	@JsonProperty(value = "sanctionResult")
	private String sanctionResult;
	
}
