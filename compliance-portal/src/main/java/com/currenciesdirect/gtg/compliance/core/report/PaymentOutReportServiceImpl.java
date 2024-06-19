package com.currenciesdirect.gtg.compliance.core.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

@Component("paymentOutReportServiceImpl")
public class PaymentOutReportServiceImpl implements IPaymentOutReportService {

	
	@Autowired
	@Qualifier("payOutReportDBServiceImpl")
	private IPaymentOutReportDBService iPaymentOutReportDBService;

	private Logger log = LoggerFactory.getLogger(PaymentOutReportServiceImpl.class);
	
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}
	
	@Override
	public PaymentOutQueueDto getPaymentOutReportWithCriteria(PaymentOutReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		try {
			return iPaymentOutReportDBService.getPaymentOutReportWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}
	@Override
	public PaymentOutQueueDto getPaymentOutQueueWholeData(PaymentOutReportSearchCriteria request)
			throws CompliancePortalException {
		try {
			return iPaymentOutReportDBService.getPaymentOutQueueWholeData(request);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}
	
}
