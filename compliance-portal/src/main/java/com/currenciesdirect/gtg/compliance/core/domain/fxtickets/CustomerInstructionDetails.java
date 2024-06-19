
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class CustomerInstructionDetails.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "customer_instruction_id",
    "organization_name",
    "account_number",
    "reference",
    "deal_type",
    "organization_code"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerInstructionDetails implements Serializable{


	 /** The Constant serialVersionUID. */
 	/* The Constant serialVersionUID. */
	 private static final long serialVersionUID = 1L;

	 /** The customer instruction id. */
 	/* The customer instruction id. */
	 @JsonIgnore
	 @JsonProperty("customer_instruction_id")
	 public Long customerInstructionId;

	 /** The organization name. */
 	/* The organization name. */
	 @JsonProperty("organization_name")
	 private String organizationName;

	 /** The account number. */
 	/* The account number. */
	 @JsonProperty("account_number")
	 private String accountNumber;

	 /** The reference. */
 	/* The reference. */
	 @JsonProperty("reference")
	 private String reference;

	 /** The deal type. */
 	/* The deal type. */
	 @JsonProperty("deal_type")
	 private String dealType;

	 /** The organization code. */
 	/* The organization code. */
	 @JsonProperty("organization_code")
	 private String organizationCode;
	 
	 /** The customer instruction number. */
 	/* The customer instruction number. */
	 @JsonProperty("customer_instruction_number")
	 private String customerInstructionNumber;
	 
	 /** The account name. */
 	/* The account name. */
	 @JsonProperty("account_name")
	 private String accountName;

	 /**
	 * Gets the customer instruction id.
	 *
	 * @return the customerInstructionId
	 */
	 public Long getCustomerInstructionId() {
	 return customerInstructionId;
	 }

	 /**
	 * Sets the customer instruction id.
	 *
	 * @param customerInstructionId the customerInstructionId to set
	 */
	 public void setCustomerInstructionId(Long customerInstructionId) {
	 this.customerInstructionId = customerInstructionId;
	 }

	 /**
	 * Gets the organization name.
	 *
	 * @return the organizationName
	 */
	 public String getOrganizationName() {
	 return organizationName;
	 }

	 /**
	 * Sets the organization name.
	 *
	 * @param organizationName the organizationName to set
	 */
	 public void setOrganizationName(String organizationName) {
	 this.organizationName = organizationName;
	 }

	 /**
	 * Gets the account number.
	 *
	 * @return the accountNumber
	 */
	 public String getAccountNumber() {
	 return accountNumber;
	 }

	 /**
	 * Sets the account number.
	 *
	 * @param accountNumber the accountNumber to set
	 */
	 public void setAccountNumber(String accountNumber) {
	 this.accountNumber = accountNumber;
	 }

	 /**
	 * Gets the reference.
	 *
	 * @return the reference
	 */
	 public String getReference() {
	 return reference;
	 }

	 /**
	 * Sets the reference.
	 *
	 * @param reference the reference to set
	 */
	 public void setReference(String reference) {
	 this.reference = reference;
	 }

	 /**
	 * Gets the deal type.
	 *
	 * @return the dealType
	 */
	 public String getDealType() {
	 return dealType;
	 }

	 /**
	 * Sets the deal type.
	 *
	 * @param dealType the dealType to set
	 */
	 public void setDealType(String dealType) {
	 this.dealType = dealType;
	 }

	 /**
	 * Gets the organization code.
	 *
	 * @return the organizationCode
	 */
	 public String getOrganizationCode() {
	 return organizationCode;
	 }

	 /**
	 * Sets the organization code.
	 *
	 * @param organizationCode the organizationCode to set
	 */
	 public void setOrganizationCode(String organizationCode) {
	 this.organizationCode = organizationCode;
	 }

	 /**
	 * Gets the customer instruction number.
	 *
	 * @return the customer instruction number
	 */
	 public String getCustomerInstructionNumber() {
	 return customerInstructionNumber;
	 }

	 /**
	 * Sets the customer instruction number.
	 *
	 * @param customerInstructionNumber the new customer instruction number
	 */
	 public void setCustomerInstructionNumber(String customerInstructionNumber) {
	 this.customerInstructionNumber = customerInstructionNumber;
	 }

	 /**
	 * Gets the account name.
	 *
	 * @return the account name
	 */
	 public String getAccountName() {
	 return accountName;
	 }

	 /**
	 * Sets the account name.
	 *
	 * @param accountName the new account name
	 */
	 public void setAccountName(String accountName) {
	 this.accountName = accountName;
	 }

	 /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	 @Override
	 public String toString() {
	 StringBuilder builder = new StringBuilder();
	 builder.append("CustomerInstructionDetails [customerInstructionId=");
	 builder.append(customerInstructionId);
	 builder.append(", organizationName=");
	 builder.append(organizationName);
	 builder.append(", accountNumber=");
	 builder.append(accountNumber);
	 builder.append(", reference=");
	 builder.append(reference);
	 builder.append(", dealType=");
	 builder.append(dealType);
	 builder.append(", organizationCode=");
	 builder.append(organizationCode);
	 builder.append(", customerInstructionNumber=");
	 builder.append(customerInstructionNumber);
	 builder.append(", accountName=");
	 builder.append(accountName);
	 builder.append(']');
	 return builder.toString();
	 }

}
