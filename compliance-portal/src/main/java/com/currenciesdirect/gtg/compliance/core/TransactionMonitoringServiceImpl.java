package com.currenciesdirect.gtg.compliance.core;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountMQRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.intuitionregsyncmonitor.IntuitionRegSyncMonitorThread;

/**
 * The Class TransactionMonitoringServiceImpl.
 */
@Component("transactionMonitoringServiceImpl")
public class TransactionMonitoringServiceImpl implements ITransactionMonitoringService {

	/** The Constant log. */
	private static final Logger log = LoggerFactory.getLogger(TransactionMonitoringServiceImpl.class);

	/**
	 * Sync registration record with intuition.
	 *
	 * @param user    the user
	 * @param request the request
	 * @return the base repeat check response
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	public BaseResponse syncRegistrationRecordWithIntuition(UserProfile user, List<String> request)
			throws CompliancePortalException {
		List<TransactionMonitoringAccountMQRequest> processRequest = new ArrayList<>();
		BaseResponse response = new BaseResponse();
		try {
			getRegSyncData(processRequest, request);
			IntuitionRegSyncMonitorThread intuitionRegSyncThread = new IntuitionRegSyncMonitorThread(user, processRequest);
			intuitionRegSyncThread.start();
		} catch (Exception e) {
			log.error("Error in syncRegistrationRecordWithIntuition ", e);
		}
		return response;
	}

	/**
	 * Gets the reg sync date.
	 *
	 * @param processRequest the process request
	 * @param request        the request
	 * @return the reg sync date
	 */
	private void getRegSyncData(List<TransactionMonitoringAccountMQRequest> processRequest, List<String> request) {
		for (int i = 0; i < request.size(); i++) {
			TransactionMonitoringAccountMQRequest tmAccountMQRequest = new TransactionMonitoringAccountMQRequest();
			tmAccountMQRequest.setTradeAccountNumber(request.get(i));
			processRequest.add(tmAccountMQRequest);
		}

	}

}
