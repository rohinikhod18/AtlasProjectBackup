package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface IPaymentInReportService {

	/**
	 * Gets the payment in queue.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment in queue
	 */
	PaymentInQueueDto getPaymentInReportWithCriteria(PaymentInReportSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the payment in queue whole data.
	 * 
	 * @param searchCriteria the search criteria
	 * @return the payment in queue
	 * @throws CompliancePortalException
	 * 			the compliance portal exception
	 */
	PaymentInQueueDto getPaymentInQueueWholeData(PaymentInReportSearchCriteria searchCriteria) throws CompliancePortalException;
	
}
