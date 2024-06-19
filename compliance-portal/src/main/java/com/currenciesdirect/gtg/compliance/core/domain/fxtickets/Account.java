
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.sql.Timestamp;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class Account.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "legalEntityId",
    "account_id",
    "org_code",
    "legal_entity",
    "account_name",
    "account_number",
    "acc_sf_id",
    "cust_type",
    "reg_date_time",
    "active",
    "deleted",
    "created_by",
    "created_on",
    "updated_on",
    "org_name",
    "account_fx_attribute",
    "contact_list",
    "can_have_collection_account",
    "is_test_account",
    "acc_legal_entity_base_currency_id",
    "acc_legal_entity_base_currency_code"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {

    /** The legal entity id. */
    @JsonProperty("legalEntityId")
    private Integer legalEntityId;
    
    /** The account id. */
    @JsonProperty("account_id")
    private Integer accountId;
    
    /** The org code. */
    @JsonProperty("org_code")
    private String orgCode;
    
    /** The legal entity. */
    @JsonProperty("legal_entity")
    private String legalEntity;
    
    /** The account name. */
    @JsonProperty("account_name")
    private String accountName;
    
    /** The account number. */
    @JsonProperty("account_number")
    private String accountNumber;
    
    /** The acc sf id. */
    @JsonProperty("acc_sf_id")
    private String accSfId;
    
    /** The cust type. */
    @JsonProperty("cust_type")
    private String custType;
    
    /** The reg date time. */
    @JsonProperty("reg_date_time")
    private String regDateTime;
    
    /** The active. */
    @JsonProperty("active")
    private Boolean active;
    
    /** The deleted. */
    @JsonProperty("deleted")
    private Boolean deleted;
    
    /** The created by. */
    @JsonProperty("created_by")
    private Integer createdBy;
    
    /** The created on. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
    @JsonProperty("created_on")
    private Timestamp createdOn;
    
    /** The updated on. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
    @JsonProperty("updated_on")
    private Timestamp updatedOn;
    
    /** The org name. */
    @JsonProperty("org_name")
    private String orgName;
    
    /** The account fx attribute. */
    @JsonProperty("account_fx_attribute")
    private AccountFxAttribute accountFxAttribute;
    
    /** The contact list. */
    @JsonProperty("contact_list")
    private List<ContactList> contactList = null;
    
    /** The can have collection account. */
    @JsonProperty("can_have_collection_account")
    private Boolean canHaveCollectionAccount;
    
    /** The is test account. */
    @JsonProperty("is_test_account")
    private Boolean isTestAccount;
    
    /** The acc legal entity base currency id. */
    @JsonProperty("acc_legal_entity_base_currency_id")
    private Integer accLegalEntityBaseCurrencyId;
    
    /** The acc legal entity base currency code. */
    @JsonProperty("acc_legal_entity_base_currency_code")
    private String accLegalEntityBaseCurrencyCode;
    
    /** The updated by. */
    @JsonProperty("updated_by")
    private Integer updatedBy;

    /**
     * Gets the updated by.
     *
     * @return the updated by
     */
    public Integer getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * Sets the updated by.
	 *
	 * @param updatedBy the new updated by
	 */
	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

	/**
	 * Gets the legal entity id.
	 *
	 * @return the legal entity id
	 */
	@JsonProperty("legalEntityId")
    public Integer getLegalEntityId() {
        return legalEntityId;
    }

    /**
     * Sets the legal entity id.
     *
     * @param legalEntityId the new legal entity id
     */
    @JsonProperty("legalEntityId")
    public void setLegalEntityId(Integer legalEntityId) {
        this.legalEntityId = legalEntityId;
    }

    /**
     * Gets the account id.
     *
     * @return the account id
     */
    @JsonProperty("account_id")
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Sets the account id.
     *
     * @param accountId the new account id
     */
    @JsonProperty("account_id")
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * Gets the org code.
     *
     * @return the org code
     */
    @JsonProperty("org_code")
    public String getOrgCode() {
        return orgCode;
    }

    /**
     * Sets the org code.
     *
     * @param orgCode the new org code
     */
    @JsonProperty("org_code")
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /**
     * Gets the legal entity.
     *
     * @return the legal entity
     */
    @JsonProperty("legal_entity")
    public String getLegalEntity() {
        return legalEntity;
    }

    /**
     * Sets the legal entity.
     *
     * @param legalEntity the new legal entity
     */
    @JsonProperty("legal_entity")
    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    /**
     * Gets the account name.
     *
     * @return the account name
     */
    @JsonProperty("account_name")
    public String getAccountName() {
        return accountName;
    }

    /**
     * Sets the account name.
     *
     * @param accountName the new account name
     */
    @JsonProperty("account_name")
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * Gets the account number.
     *
     * @return the account number
     */
    @JsonProperty("account_number")
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Sets the account number.
     *
     * @param accountNumber the new account number
     */
    @JsonProperty("account_number")
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Gets the acc sf id.
     *
     * @return the acc sf id
     */
    @JsonProperty("acc_sf_id")
    public String getAccSfId() {
        return accSfId;
    }

    /**
     * Sets the acc sf id.
     *
     * @param accSfId the new acc sf id
     */
    @JsonProperty("acc_sf_id")
    public void setAccSfId(String accSfId) {
        this.accSfId = accSfId;
    }

    /**
     * Gets the cust type.
     *
     * @return the cust type
     */
    @JsonProperty("cust_type")
    public String getCustType() {
        return custType;
    }

    /**
     * Sets the cust type.
     *
     * @param custType the new cust type
     */
    @JsonProperty("cust_type")
    public void setCustType(String custType) {
        this.custType = custType;
    }

    /**
     * Gets the reg date time.
     *
     * @return the reg date time
     */
    @JsonProperty("reg_date_time")
    public String getRegDateTime() {
        return regDateTime;
    }

    /**
     * Sets the reg date time.
     *
     * @param regDateTime the new reg date time
     */
    @JsonProperty("reg_date_time")
    public void setRegDateTime(String regDateTime) {
        this.regDateTime = regDateTime;
    }

    /**
     * Gets the active.
     *
     * @return the active
     */
    @JsonProperty("active")
    public Boolean getActive() {
        return active;
    }

    /**
     * Sets the active.
     *
     * @param active the new active
     */
    @JsonProperty("active")
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Gets the deleted.
     *
     * @return the deleted
     */
    @JsonProperty("deleted")
    public Boolean getDeleted() {
        return deleted;
    }

    /**
     * Sets the deleted.
     *
     * @param deleted the new deleted
     */
    @JsonProperty("deleted")
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    /**
     * Gets the created by.
     *
     * @return the created by
     */
    @JsonProperty("created_by")
    public Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * Sets the created by.
     *
     * @param createdBy the new created by
     */
    @JsonProperty("created_by")
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Gets the created on.
     *
     * @return the created on
     */
    @JsonProperty("created_on")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    /**
     * Sets the created on.
     *
     * @param createdOn the new created on
     */
    @JsonProperty("created_on")
    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    /**
     * Gets the updated on.
     *
     * @return the updated on
     */
    @JsonProperty("updated_on")
    public Timestamp getUpdatedOn() {
        return updatedOn;
    }

    /**
     * Sets the updated on.
     *
     * @param updatedOn the new updated on
     */
    @JsonProperty("updated_on")
    public void setUpdatedOn(Timestamp updatedOn) {
        this.updatedOn = updatedOn;
    }

    /**
     * Gets the org name.
     *
     * @return the org name
     */
    @JsonProperty("org_name")
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets the org name.
     *
     * @param orgName the new org name
     */
    @JsonProperty("org_name")
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /**
     * Gets the account fx attribute.
     *
     * @return the account fx attribute
     */
    @JsonProperty("account_fx_attribute")
    public AccountFxAttribute getAccountFxAttribute() {
        return accountFxAttribute;
    }

    /**
     * Sets the account fx attribute.
     *
     * @param accountFxAttribute the new account fx attribute
     */
    @JsonProperty("account_fx_attribute")
    public void setAccountFxAttribute(AccountFxAttribute accountFxAttribute) {
        this.accountFxAttribute = accountFxAttribute;
    }

    /**
     * Gets the contact list.
     *
     * @return the contact list
     */
    @JsonProperty("contact_list")
    public List<ContactList> getContactList() {
        return contactList;
    }

    /**
     * Sets the contact list.
     *
     * @param contactList the new contact list
     */
    @JsonProperty("contact_list")
    public void setContactList(List<ContactList> contactList) {
        this.contactList = contactList;
    }

    /**
     * Gets the can have collection account.
     *
     * @return the can have collection account
     */
    @JsonProperty("can_have_collection_account")
    public Boolean getCanHaveCollectionAccount() {
        return canHaveCollectionAccount;
    }

    /**
     * Sets the can have collection account.
     *
     * @param canHaveCollectionAccount the new can have collection account
     */
    @JsonProperty("can_have_collection_account")
    public void setCanHaveCollectionAccount(Boolean canHaveCollectionAccount) {
        this.canHaveCollectionAccount = canHaveCollectionAccount;
    }

    /**
     * Gets the checks if is test account.
     *
     * @return the checks if is test account
     */
    @JsonProperty("is_test_account")
    public Boolean getIsTestAccount() {
        return isTestAccount;
    }

    /**
     * Sets the checks if is test account.
     *
     * @param isTestAccount the new checks if is test account
     */
    @JsonProperty("is_test_account")
    public void setIsTestAccount(Boolean isTestAccount) {
        this.isTestAccount = isTestAccount;
    }

    /**
     * Gets the acc legal entity base currency id.
     *
     * @return the acc legal entity base currency id
     */
    @JsonProperty("acc_legal_entity_base_currency_id")
    public Integer getAccLegalEntityBaseCurrencyId() {
        return accLegalEntityBaseCurrencyId;
    }

    /**
     * Sets the acc legal entity base currency id.
     *
     * @param accLegalEntityBaseCurrencyId the new acc legal entity base currency id
     */
    @JsonProperty("acc_legal_entity_base_currency_id")
    public void setAccLegalEntityBaseCurrencyId(Integer accLegalEntityBaseCurrencyId) {
        this.accLegalEntityBaseCurrencyId = accLegalEntityBaseCurrencyId;
    }

    /**
     * Gets the acc legal entity base currency code.
     *
     * @return the acc legal entity base currency code
     */
    @JsonProperty("acc_legal_entity_base_currency_code")
    public String getAccLegalEntityBaseCurrencyCode() {
        return accLegalEntityBaseCurrencyCode;
    }

    /**
     * Sets the acc legal entity base currency code.
     *
     * @param accLegalEntityBaseCurrencyCode the new acc legal entity base currency code
     */
    @JsonProperty("acc_legal_entity_base_currency_code")
    public void setAccLegalEntityBaseCurrencyCode(String accLegalEntityBaseCurrencyCode) {
        this.accLegalEntityBaseCurrencyCode = accLegalEntityBaseCurrencyCode;
    }

}
