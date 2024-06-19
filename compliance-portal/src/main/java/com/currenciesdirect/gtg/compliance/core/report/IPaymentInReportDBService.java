package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.IDBService;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;


/**
 * The Interface IPaymentInReportDBService.
 */
public interface IPaymentInReportDBService extends IDBService {
	
	/**
	 * Gets the payment in queue with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return PaymentInQueueDto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentInQueueDto getPaymentInReportWithCriteria(PaymentInReportSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the payment in queue with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return PaymentInQueueDto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentInQueueDto getPaymentInQueueWholeData(PaymentInReportSearchCriteria request) throws CompliancePortalException;


}
