package com.currenciesdirect.gtg.compliance.core.report;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

@Component("paymentInReportServiceImpl")
public class PaymentInReportServiceImpl implements IPaymentInReportService {

	@Autowired
	@Qualifier("payInReportDBServiceImpl")
	private IPaymentInReportDBService iPaymentInReportDBService;
	
	private Logger log = LoggerFactory.getLogger(PaymentInReportServiceImpl.class);
	
	@Override
	public PaymentInQueueDto getPaymentInReportWithCriteria(PaymentInReportSearchCriteria searchCriteria) 
			throws CompliancePortalException {
		try {
			return iPaymentInReportDBService.getPaymentInReportWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
		
	}
	
	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

	@Override
	public PaymentInQueueDto getPaymentInQueueWholeData(PaymentInReportSearchCriteria request)
			throws CompliancePortalException {
		try {
			return iPaymentInReportDBService.getPaymentInQueueWholeData(request);
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		}
	}

}
