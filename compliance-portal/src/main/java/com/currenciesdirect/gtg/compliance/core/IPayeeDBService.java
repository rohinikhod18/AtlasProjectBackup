package com.currenciesdirect.gtg.compliance.core;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeQueueDto;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

/**
 * The Interface IPayeeDBService.
 */
public interface IPayeeDBService {

	/**
	 * Gets the payee with criteria.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the payee with criteria
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public PayeeQueueDto getPayeeWithCriteria(BeneficiaryReportSearchCriteria searchCriteria)
			throws CompliancePortalException;

	/**
	 * Gets the payee clients details from DB.
	 *
	 * @param customCheckPayeeResponse
	 *            the custom check payee response
	 * @return the payee clients details from DB
	 * @throws CompliancePortalException
	 */
	public PayeeESResponse getPayeeClientsDetailsFromDB(PayeeESResponse customCheckPayeeResponse)
			throws CompliancePortalException;

	/**
	 * Gets the transaction list.
	 *
	 * @param payeePaymentsRequest
	 *            the payee payments request
	 * @return the transaction list
	 * @throws CompliancePortalException
	 */
	public List<PayeePaymentsResponse> getTransactionList(PayeePaymentsRequest payeePaymentsRequest)
			throws CompliancePortalException;

	/**
	 * Gets the checks if is beneficiary whitelisted.
	 *
	 * @param atlasBeneAccNumber
	 *            the atlas bene acc number
	 * @param isBeneficiaryWhitelisted
	 *            the is beneficiary whitelisted
	 * @return the checks if is beneficiary whitelisted
	 */
	public Boolean getIsBeneficiaryWhitelisted(String atlasBeneAccNumber, Boolean isBeneficiaryWhitelisted)
			throws CompliancePortalException;

}
