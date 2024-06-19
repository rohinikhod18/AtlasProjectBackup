package com.currenciesdirect.gtg.compliance.core.report;

import com.currenciesdirect.gtg.compliance.core.IDBService;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public interface IPaymentOutReportDBService extends IDBService{

	/**
	 * Gets the payment out queue with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return PaymentoutQueueDto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentOutQueueDto getPaymentOutReportWithCriteria(PaymentOutReportSearchCriteria searchCriteria) throws CompliancePortalException;

	/**
	 * Gets the payment out queue with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return PaymentoutQueueDto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public PaymentOutQueueDto getPaymentOutQueueWholeData(PaymentOutReportSearchCriteria request) throws CompliancePortalException;

}
