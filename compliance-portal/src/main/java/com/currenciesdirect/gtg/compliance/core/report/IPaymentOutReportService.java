package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface IPaymentOutReportService {

	/**
	 * Gets the payment out queue.
	 *
	 * @param searchCriteria the search criteria
	 * @return the payment out queue
	 */
	PaymentOutQueueDto getPaymentOutReportWithCriteria(PaymentOutReportSearchCriteria request) throws CompliancePortalException;
	
	/**
	 * Gets the payment out queue whole data.
	 * 
	 * @param searchCriteria the search criteria
	 * @return the payment out queue
	 * @throws CompliancePortalException
	 * 			the compliance portal exception
	 */
	PaymentOutQueueDto getPaymentOutQueueWholeData(PaymentOutReportSearchCriteria searchCriteria) throws CompliancePortalException;
	
}
