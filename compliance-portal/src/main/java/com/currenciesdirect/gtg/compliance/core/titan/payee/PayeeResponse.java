package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.core.domain.fxtickets.BaseSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportSearchCriteria;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The Class PayeeResponse.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PayeeResponse extends BaseResponse {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The payee list. */
	@JsonProperty("payee_list")
	private List<WrapperPayee> payeeList;

	/** The payee entity. */
	private WrapperPayee payee;

	/** The search criteria. */
	@JsonProperty(value = "search_criteria")
	private BeneficiaryReportSearchCriteria searchCriteria;

	/** The payee id. */
	@JsonProperty("payee_id")
	private Integer payeeId;

	/** The active status. */
	@JsonProperty("active_status")
	private Boolean activeStatus;

	/** The delete status. */
	@JsonProperty("delete_status")
	private Boolean deleteStatus;

	/** The deleted payee list. */
	@JsonProperty("delete_payee_list")
	private List<WrapperPayeeDelete> deletedPayeeList;

	/** The is beneficiary whitelisted. */
	private Boolean isBeneficiaryWhitelisted = Boolean.FALSE;

	/** The search criteria for UI. */
	private String searchCriteriaForUI;
	
	private Boolean isSystemNotAvailaible = Boolean.FALSE;

	/**
	 * Gets the payee list.
	 *
	 * @return the payeeList
	 */
	public List<WrapperPayee> getPayeeList() {
		return payeeList;
	}

	/**
	 * Sets the payee list.
	 *
	 * @param payeeList
	 *            the payeeList to set
	 */
	public void setPayeeList(List<WrapperPayee> payeeList) {
		this.payeeList = payeeList;
	}

	/**
	 * Gets the payee.
	 *
	 * @return the payee
	 */
	public WrapperPayee getPayee() {
		return payee;
	}

	/**
	 * Sets the payee.
	 *
	 * @param payee
	 *            the payee to set
	 */
	public void setPayee(WrapperPayee payee) {
		this.payee = payee;
	}

	/**
	 * Gets the payee id.
	 *
	 * @return the payeeId
	 */
	public Integer getPayeeId() {
		return payeeId;
	}

	/**
	 * Sets the payee id.
	 *
	 * @param payeeId
	 *            the payeeId to set
	 */
	public void setPayeeId(Integer payeeId) {
		this.payeeId = payeeId;
	}

	/**
	 * Gets the active status.
	 *
	 * @return the activeStatus
	 */
	public Boolean getActiveStatus() {
		return activeStatus;
	}

	/**
	 * Sets the active status.
	 *
	 * @param activeStatus
	 *            the activeStatus to set
	 */
	public void setActiveStatus(Boolean activeStatus) {
		this.activeStatus = activeStatus;
	}

	/**
	 * Gets the delete status.
	 *
	 * @return the deleteStatus
	 */
	public Boolean getDeleteStatus() {
		return deleteStatus;
	}

	/**
	 * Sets the delete status.
	 *
	 * @param deleteStatus
	 *            the deleteStatus to set
	 */
	public void setDeleteStatus(Boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	/**
	 * Gets the deleted payee list.
	 *
	 * @return the deletedPayeeList
	 */
	public List<WrapperPayeeDelete> getDeletedPayeeList() {
		return deletedPayeeList;
	}

	/**
	 * Sets the deleted payee list.
	 *
	 * @param deletedPayeeList
	 *            the deletedPayeeList to set
	 */
	public void setDeletedPayeeList(List<WrapperPayeeDelete> deletedPayeeList) {
		this.deletedPayeeList = deletedPayeeList;
	}

	/**
	 * Gets the search criteria.
	 *
	 * @return the searchCriteria
	 */
	public BaseSearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * Sets the search criteria.
	 *
	 * @param searchCriteria
	 *            the searchCriteria to set
	 */
	public void setSearchCriteria(BeneficiaryReportSearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * Gets the search criteria for UI.
	 *
	 * @return the search criteria for UI
	 */
	public String getSearchCriteriaForUI() {
		return searchCriteriaForUI;
	}

	/**
	 * Sets the search criteria for UI.
	 *
	 * @param searchCriteriaForUI
	 *            the new search criteria for UI
	 */
	public void setSearchCriteriaForUI(String searchCriteriaForUI) {
		this.searchCriteriaForUI = searchCriteriaForUI;
	}

	/**
	 * Gets the checks if is beneficiary whitelisted.
	 *
	 * @return the checks if is beneficiary whitelisted
	 */
	public Boolean getIsBeneficiaryWhitelisted() {
		return isBeneficiaryWhitelisted;
	}

	/**
	 * Sets the checks if is beneficiary whitelisted.
	 *
	 * @param isBeneficiaryWhitelisted
	 *            the new checks if is beneficiary whitelisted
	 */
	public void setIsBeneficiaryWhitelisted(Boolean isBeneficiaryWhitelisted) {
		this.isBeneficiaryWhitelisted = isBeneficiaryWhitelisted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayeeResponse [payeeList=");
		builder.append(payeeList);
		builder.append(", payee=");
		builder.append(payee);
		builder.append(", searchCriteria=");
		builder.append(searchCriteria);
		builder.append(", payeeId=");
		builder.append(payeeId);
		builder.append(", activeStatus=");
		builder.append(activeStatus);
		builder.append(", deleteStatus=");
		builder.append(deleteStatus);
		builder.append(", deletedPayeeList=");
		builder.append(deletedPayeeList);
		builder.append(']');
		return builder.toString();
	}

	/**
	 * @return the isSystemNotAvailaible
	 */
	public Boolean getIsSystemNotAvailaible() {
		return isSystemNotAvailaible;
	}

	/**
	 * @param isSystemNotAvailaible the isSystemNotAvailaible to set
	 */
	public void setIsSystemNotAvailaible(Boolean isSystemNotAvailaible) {
		this.isSystemNotAvailaible = isSystemNotAvailaible;
	}

}
