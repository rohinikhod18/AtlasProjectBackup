package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;

/**
 * The Class SanctionRequest.
 */
public class SanctionRequest extends ServiceMessage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;

	/** The customer type. */
	private String customerType;

	/** The contactrequests. */
	private List<SanctionContactRequest> contactrequests;

	/** The beneficiary requests. */
	private List<SanctionBeneficiaryRequest> beneficiaryRequests;

	/** The bank requests. */
	private List<SanctionBankRequest> bankRequests;

	/** The source application. */
	private String sourceApplication;
	
	/** The customer number. */
	private String customerNumber;

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Gets the source application.
	 *
	 * @return the source application
	 */
	public String getSourceApplication() {
		return sourceApplication;
	}

	/**
	 * Sets the source application.
	 *
	 * @param sourceApplication
	 *            the new source application
	 */
	public void setSourceApplication(String sourceApplication) {
		this.sourceApplication = sourceApplication;
	}

	/**
	 * Gets the contactrequests.
	 *
	 * @return the contactrequests
	 */
	public List<SanctionContactRequest> getContactrequests() {
		return contactrequests;
	}

	/**
	 * Gets the beneficiary requests.
	 *
	 * @return the beneficiary requests
	 */
	public List<SanctionBeneficiaryRequest> getBeneficiaryRequests() {
		return beneficiaryRequests;
	}

	/**
	 * Gets the bank requests.
	 *
	 * @return the bank requests
	 */
	public List<SanctionBankRequest> getBankRequests() {
		return bankRequests;
	}

	/**
	 * Sets the contactrequests.
	 *
	 * @param contactrequests
	 *            the new contactrequests
	 */
	public void setContactrequests(List<SanctionContactRequest> contactrequests) {
		this.contactrequests = contactrequests;
	}

	/**
	 * Sets the beneficiary requests.
	 *
	 * @param beneficiaryRequests
	 *            the new beneficiary requests
	 */
	public void setBeneficiaryRequests(List<SanctionBeneficiaryRequest> beneficiaryRequests) {
		this.beneficiaryRequests = beneficiaryRequests;
	}

	/**
	 * Sets the bank requests.
	 *
	 * @param bankRequests
	 *            the new bank requests
	 */
	public void setBankRequests(List<SanctionBankRequest> bankRequests) {
		this.bankRequests = bankRequests;
	}

	/**
	 * Gets the customer type.
	 *
	 * @return the customer type
	 */
	public String getCustomerType() {
		return customerType;
	}

	/**
	 * Sets the org code.
	 *
	 * @param orgCode
	 *            the new org code
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * Sets the customer type.
	 *
	 * @param customerType
	 *            the new customer type
	 */
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	/**
	 * @return the customerNumber
	 */
	public String getCustomerNumber() {
		return customerNumber;
	}

	/**
	 * @param customerNumber the customerNumber to set
	 */
	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

}
