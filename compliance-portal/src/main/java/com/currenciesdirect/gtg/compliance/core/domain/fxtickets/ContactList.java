
package com.currenciesdirect.gtg.compliance.core.domain.fxtickets;

import java.sql.Timestamp;

import com.currenciesdirect.gtg.compliance.commons.util.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * The Class ContactList.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "contact_id",
    "account_id",
    "is_primary_contact",
    "contact_sf_id",
    "legacy_contact_id",
    "contact_status",
    "is_authorised_signatory",
    "created_on",
    "updated_on",
    "is_active",
    "is_deleted",
    "contact_detail",
    "created_by"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactList {

    /** The contact id. */
    @JsonProperty("contact_id")
    private Integer contactId;
    
    /** The account id. */
    @JsonProperty("account_id")
    private Integer accountId;
    
    /** The is primary contact. */
    @JsonProperty("is_primary_contact")
    private Boolean isPrimaryContact;
    
    /** The contact sf id. */
    @JsonProperty("contact_sf_id")
    private String contactSfId;
    
    /** The legacy contact id. */
    @JsonProperty("legacy_contact_id")
    private Integer legacyContactId;
    
    /** The contact status. */
    @JsonProperty("contact_status")
    private String contactStatus;
    
    /** The is authorised signatory. */
    @JsonProperty("is_authorised_signatory")
    private Boolean isAuthorisedSignatory;
    
    /** The created on. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
    @JsonProperty("created_on")
    private Timestamp createdOn;
    
    /** The updated on. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateTimeFormatter.JSON_DATEFORMAT, timezone = DateTimeFormatter.JSON_TIMEZONE)
    @JsonProperty("updated_on")
    private Timestamp updatedOn;
    
    /** The is active. */
    @JsonProperty("is_active")
    private Boolean isActive;
    
    /** The is deleted. */
    @JsonProperty("is_deleted")
    private Boolean isDeleted;
    
    /** The contact detail. */
    @JsonProperty("contact_detail")
    private ContactDetail contactDetail;
    
    /** The created by. */
    @JsonProperty("created_by")
    private Integer createdBy;

    /**
     * Gets the contact id.
     *
     * @return the contact id
     */
    @JsonProperty("contact_id")
    public Integer getContactId() {
        return contactId;
    }

    /**
     * Sets the contact id.
     *
     * @param contactId the new contact id
     */
    @JsonProperty("contact_id")
    public void setContactId(Integer contactId) {
        this.contactId = contactId;
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
     * Gets the checks if is primary contact.
     *
     * @return the checks if is primary contact
     */
    @JsonProperty("is_primary_contact")
    public Boolean getIsPrimaryContact() {
        return isPrimaryContact;
    }

    /**
     * Sets the checks if is primary contact.
     *
     * @param isPrimaryContact the new checks if is primary contact
     */
    @JsonProperty("is_primary_contact")
    public void setIsPrimaryContact(Boolean isPrimaryContact) {
        this.isPrimaryContact = isPrimaryContact;
    }

    /**
     * Gets the contact sf id.
     *
     * @return the contact sf id
     */
    @JsonProperty("contact_sf_id")
    public String getContactSfId() {
        return contactSfId;
    }

    /**
     * Sets the contact sf id.
     *
     * @param contactSfId the new contact sf id
     */
    @JsonProperty("contact_sf_id")
    public void setContactSfId(String contactSfId) {
        this.contactSfId = contactSfId;
    }

    /**
     * Gets the legacy contact id.
     *
     * @return the legacy contact id
     */
    @JsonProperty("legacy_contact_id")
    public Integer getLegacyContactId() {
        return legacyContactId;
    }

    /**
     * Sets the legacy contact id.
     *
     * @param legacyContactId the new legacy contact id
     */
    @JsonProperty("legacy_contact_id")
    public void setLegacyContactId(Integer legacyContactId) {
        this.legacyContactId = legacyContactId;
    }

    /**
     * Gets the contact status.
     *
     * @return the contact status
     */
    @JsonProperty("contact_status")
    public String getContactStatus() {
        return contactStatus;
    }

    /**
     * Sets the contact status.
     *
     * @param contactStatus the new contact status
     */
    @JsonProperty("contact_status")
    public void setContactStatus(String contactStatus) {
        this.contactStatus = contactStatus;
    }

    /**
     * Gets the checks if is authorised signatory.
     *
     * @return the checks if is authorised signatory
     */
    @JsonProperty("is_authorised_signatory")
    public Boolean getIsAuthorisedSignatory() {
        return isAuthorisedSignatory;
    }

    /**
     * Sets the checks if is authorised signatory.
     *
     * @param isAuthorisedSignatory the new checks if is authorised signatory
     */
    @JsonProperty("is_authorised_signatory")
    public void setIsAuthorisedSignatory(Boolean isAuthorisedSignatory) {
        this.isAuthorisedSignatory = isAuthorisedSignatory;
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
     * Gets the checks if is active.
     *
     * @return the checks if is active
     */
    @JsonProperty("is_active")
    public Boolean getIsActive() {
        return isActive;
    }

    /**
     * Sets the checks if is active.
     *
     * @param isActive the new checks if is active
     */
    @JsonProperty("is_active")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Gets the checks if is deleted.
     *
     * @return the checks if is deleted
     */
    @JsonProperty("is_deleted")
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * Sets the checks if is deleted.
     *
     * @param isDeleted the new checks if is deleted
     */
    @JsonProperty("is_deleted")
    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * Gets the contact detail.
     *
     * @return the contact detail
     */
    @JsonProperty("contact_detail")
    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    /**
     * Sets the contact detail.
     *
     * @param contactDetail the new contact detail
     */
    @JsonProperty("contact_detail")
    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
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

}
