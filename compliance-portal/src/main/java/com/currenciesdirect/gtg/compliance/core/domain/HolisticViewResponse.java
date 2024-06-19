package com.currenciesdirect.gtg.compliance.core.domain;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.docupload.DocumentDto;

/**
 * The Class HolisticViewRequest.
 */
public class HolisticViewResponse {
	
	private HolisticAccount holisticAccount;
	
	private List<HolisticContact> holisticContacts;
	
	private HolisticContact holisticContact;
	
	private PaymentSummary paymentSummary;
	
	private PaymentInSummary paymentInSummary;
	
	private PaymentOutSummary paymentOutSummary;
	
	private List<DocumentDto> documents;
	
	private ActivityLogs activityLogs;
	
	private String searchCriteria;
	
	private FurtherPaymentDetails furtherPaymentDetails;
	
	/** The error code. */
	private String errorCode;

	/** The error message. */
	private String errorMessage;
	
	/**
	 * @return the holisticAccount
	 */
	public HolisticAccount getHolisticAccount() {
		return holisticAccount;
	}

	/**
	 * @param holisticAccount the holisticAccount to set
	 */
	public void setHolisticAccount(HolisticAccount holisticAccount) {
		this.holisticAccount = holisticAccount;
	}

	
	/**
	 * @return the paymentSummary
	 */
	public PaymentSummary getPaymentSummary() {
		return paymentSummary;
	}

	/**
	 * @param paymentSummary the paymentSummary to set
	 */
	public void setPaymentSummary(PaymentSummary paymentSummary) {
		this.paymentSummary = paymentSummary;
	}

	/**
	 * @return the paymentInSummary
	 */
	public PaymentInSummary getPaymentInSummary() {
		return paymentInSummary;
	}

	/**
	 * @param paymentInSummary the paymentInSummary to set
	 */
	public void setPaymentInSummary(PaymentInSummary paymentInSummary) {
		this.paymentInSummary = paymentInSummary;
	}

	/**
	 * @return the paymentOutSummary
	 */
	public PaymentOutSummary getPaymentOutSummary() {
		return paymentOutSummary;
	}

	/**
	 * @param paymentOutSummary the paymentOutSummary to set
	 */
	public void setPaymentOutSummary(PaymentOutSummary paymentOutSummary) {
		this.paymentOutSummary = paymentOutSummary;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the holisticContacts
	 */
	public List<HolisticContact> getHolisticContacts() {
		return holisticContacts;
	}

	/**
	 * @param holisticContacts the holisticContacts to set
	 */
	public void setHolisticContacts(List<HolisticContact> holisticContacts) {
		this.holisticContacts = holisticContacts;
	}

	/**
	 * @param holisticContact the holisticContact to set
	 */
	public void setHolisticContact(HolisticContact holisticContact) {
		this.holisticContact = holisticContact;
	}

	/**
	 * @return the holisticContact
	 */
	public HolisticContact getHolisticContact() {
		return holisticContact;
	}

	/**
	 * @return the documents
	 */
	public List<DocumentDto> getDocuments() {
		return documents;
	}

	/**
	 * @param documents the documents to set
	 */
	public void setDocuments(List<DocumentDto> documents) {
		this.documents = documents;
	}

	/**
	 * @return the activityLogs
	 */
	public ActivityLogs getActivityLogs() {
		return activityLogs;
	}

	/**
	 * @param activityLogs the activityLogs to set
	 */
	public void setActivityLogs(ActivityLogs activityLogs) {
		this.activityLogs = activityLogs;
	}

	/**
	 * @return the searchCriteria
	 */
	public String getSearchCriteria() {
		return searchCriteria;
	}

	/**
	 * @param searchCriteria the searchCriteria to set
	 */
	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

	/**
	 * @return the furtherpaymentDetails
	 */
	public FurtherPaymentDetails getFurtherPaymentDetails() {
		return furtherPaymentDetails;
	}

	/**
	 * @param furtherpaymentDetails the furtherpaymentDetails to set
	 */
	public void setFurtherPaymentDetails(FurtherPaymentDetails furtherPaymentDetails) {
		this.furtherPaymentDetails = furtherPaymentDetails;
	}

}
