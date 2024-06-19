package com.currenciesdirect.gtg.compliance.core.titan.payee;

import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Interface IBeneficiaryService.
 */
public interface IPayeeService {

	/**
	 * Gets the beneficiary list.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the beneficiary list
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public PayeeResponse getBeneficiaryList(BeneficiaryReportSearchCriteria searchCriteria)
			throws CompliancePortalException;

	/**
	 * Gets the beneficiary details.
	 *
	 * @param user
	 *            the user
	 * @param payeeRequest
	 *            the payee request
	 * @param searchCriteria
	 *            the search criteria
	 * @return the beneficiary details
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public PayeeResponse getBeneficiaryDetails(UserProfile user, PayeeDetailsRequest payeeRequest,
			BeneficiaryReportSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the beneficiary list with criteria.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @return the beneficiary list with criteria
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public PayeeQueueDto getBeneficiaryListWithCriteria(BeneficiaryReportSearchCriteria searchCriteria)
			throws CompliancePortalException;

	/**
	 * Gets the registration details from ES.
	 *
	 * @param user
	 *            the user
	 * @param payeeDetailsResponse
	 *            the payee details response
	 * @return the registration details from ES
	 */
	public PayeeESResponse getRegistrationDetailsFromES(UserProfile user, PayeeResponse payeeDetailsResponse)throws CompliancePortalException ;

	/**
	 * Gets the transaction list.
	 *
	 * @param user
	 *            the user
	 * @param payeePaymentsRequest
	 *            the payee payments request
	 * @return the transaction list
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	List<PayeePaymentsResponse> getTransactionList(UserProfile user, PayeePaymentsRequest payeePaymentsRequest)
			throws CompliancePortalException;

}
