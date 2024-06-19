package com.currenciesdirect.gtg.compliance.commons.domain.sanction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * The Class SanctionResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SanctionResponse extends BaseResponse implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The org code. */
	private String orgCode;

	/** The correlation id. */
	private String correlationId;

	/** The contact responses. */
	private List<SanctionContactResponse> contactResponses = new ArrayList<>();

	/** The beneficiary responses. */
	private List<SanctionBeneficiaryResponse> beneficiaryResponses = new ArrayList<>();

	/** The bank responses. */
	private List<SanctionBankResponse> bankResponses = new ArrayList<>();

	/** The provider response. */
	private String providerResponse;

	/**
	 * Gets the org code.
	 *
	 * @return the org code
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * Gets the correlation id.
	 *
	 * @return the correlation id
	 */
	public String getCorrelationId() {
		return correlationId;
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
	 * Sets the correlation id.
	 *
	 * @param correlationId
	 *            the new correlation id
	 */
	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	/**
	 * Gets the contact responses.
	 *
	 * @return the contact responses
	 */
	public List<SanctionContactResponse> getContactResponses() {
		return contactResponses;
	}

	/**
	 * Gets the provider response.
	 *
	 * @return the provider response
	 */
	public String getProviderResponse() {
		return providerResponse;
	}

	/**
	 * Sets the contact responses.
	 *
	 * @param contactResponses
	 *            the new contact responses
	 */
	public void setContactResponses(List<SanctionContactResponse> contactResponses) {
		this.contactResponses = contactResponses;
	}

	/**
	 * Sets the provider response.
	 *
	 * @param providerResponse
	 *            the new provider response
	 */
	public void setProviderResponse(String providerResponse) {
		this.providerResponse = providerResponse;
	}

	/**
	 * Gets the beneficiary responses.
	 *
	 * @return the beneficiary responses
	 */
	public List<SanctionBeneficiaryResponse> getBeneficiaryResponses() {
		return beneficiaryResponses;
	}

	/**
	 * Gets the bank responses.
	 *
	 * @return the bank responses
	 */
	public List<SanctionBankResponse> getBankResponses() {
		return bankResponses;
	}

	/**
	 * Sets the beneficiary responses.
	 *
	 * @param beneficiaryResponses
	 *            the new beneficiary responses
	 */
	public void setBeneficiaryResponses(List<SanctionBeneficiaryResponse> beneficiaryResponses) {
		this.beneficiaryResponses = beneficiaryResponses;
	}

	/**
	 * Sets the bank responses.
	 *
	 * @param bankResponses
	 *            the new bank responses
	 */
	public void setBankResponses(List<SanctionBankResponse> bankResponses) {
		this.bankResponses = bankResponses;
	}

	/**
	 * Gets the sanction contact response by id.
	 *
	 * @param conatctId
	 *            the conatct id
	 * @return the sanction contact response by id
	 */
	public SanctionContactResponse getSanctionContactResponseById(Integer conatctId) {
		for (SanctionContactResponse sResponse : contactResponses) {
			if (sResponse.getContactId().equals(conatctId)) {
				return sResponse;
			}
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SanctionResponse [orgCode=" + orgCode + ", correlationId=" + correlationId + ", contactResponses="
				+ contactResponses + ", beneficiaryResponses=" + beneficiaryResponses + ", bankResponses="
				+ bankResponses + ", providerResponse=" + providerResponse + "]";
	}
	
}
