package com.currenciesdirect.gtg.compliance.recheckmonitor;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.core.IRecheckDBService;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentRecheckFailureResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;

/**
 * The Class RecheckMonitorThread.
 */
public class RecheckMonitorThread extends Thread {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(RecheckMonitorThread.class);

	/** The user. */
	private UserProfile user;

	/** The reprocess failed details list. */
	private List<ReprocessFailedDetails> reprocessFailedDetailsList;

	/** The i recheck DB service. */
	private IRecheckDBService iRecheckDBService = null;

	/** The base repeat check response. */
	@SuppressWarnings("unused")
	private BaseRepeatCheckResponse baseRepeatCheckResponse = new BaseRepeatCheckResponse();

	/**
	 * Instantiates a new recheck monitor thread.
	 *
	 * @param user
	 *            the user
	 * @param reprocessFailedDetailsList
	 *            the reprocess failed details list
	 * @param iRecheckDBService
	 *            the i recheck DB service
	 * @param baseRepeatCheckResponse
	 *            the base repeat check response
	 */
	public RecheckMonitorThread(UserProfile user, List<ReprocessFailedDetails> reprocessFailedDetailsList,
			IRecheckDBService iRecheckDBService, BaseRepeatCheckResponse baseRepeatCheckResponse) {
		this.user = user;
		this.reprocessFailedDetailsList = reprocessFailedDetailsList;
		this.iRecheckDBService = iRecheckDBService;
		this.baseRepeatCheckResponse = baseRepeatCheckResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			log.warn("{}  RecheckMonitorThread started", this.getName());
			if (null != reprocessFailedDetailsList && !reprocessFailedDetailsList.isEmpty()) {
				ReprocessFailedDetails reprocessFailedDetails = reprocessFailedDetailsList.get(0);
				HttpClientPool clientPool = HttpClientPool.getInstance();
				MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
				ArrayList<Object> list = new ArrayList<>();
				list.add(JsonConverterUtil.convertToJsonWithNull(user));
				headers.put("user", list);
				String baseUrl = System.getProperty("baseComplianceServiceUrl");

				String url = getUrlBasedOnTransType(baseUrl, reprocessFailedDetails);

				clientPool.sendRequest(url, "POST", reprocessFailedDetails, PaymentRecheckFailureResponse.class,
						headers, MediaType.APPLICATION_JSON_TYPE);

				//sendFailedRecordsAsynchronously(clientPool, headers, url, reprocessFailedDetails);
				sendFailedRecordsAsynchronously(clientPool, headers, url); //AT-4355
			}
		} catch (Exception e) {
			String logData = this.getName();
			logData = logData.concat(" Error while sending request to reprocess ; ").concat(e.toString());
			log.error(logData);
		}
	}

	/**
	 * Send failed records asynchronously.
	 *
	 * @param clientPool
	 *            the client pool
	 * @param headers
	 *            the headers
	 * @param url
	 *            the url
	 * @param reprocessFailedDetails
	 *            the reprocess failed details
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private void sendFailedRecordsAsynchronously(HttpClientPool clientPool, MultivaluedHashMap<String, Object> headers,
			String url) throws CompliancePortalException {
		try {

			/* 
			boolean shutdown = true;
			while (shutdown) {
				if (iRecheckDBService.getReprocessStatusForFailed(reprocessFailedDetails))
					shutdown = false;
				else {
					synchronized (this) {
						wait(5000);
						clientPool.sendRequest(url, "POST", reprocessFailedDetails, PaymentRecheckFailureResponse.class,
								headers, MediaType.APPLICATION_JSON_TYPE);
					}
				}
			}*/ //commented this code for AT-4355

			for (int noOfFailedPayments = 1; noOfFailedPayments < reprocessFailedDetailsList
					.size(); noOfFailedPayments++) {
				clientPool.sendRequest(url, "POST", reprocessFailedDetailsList.get(noOfFailedPayments),
						PaymentRecheckFailureResponse.class, headers, MediaType.APPLICATION_JSON_TYPE);
			}

		} catch (Exception e) {
			String logData = this.getName();
			logData = logData.concat(" Error while processing failed records asynchronously ; ").concat(e.toString());
			log.error(logData);
		}
	}

	/**
	 * Gets the url based on trans type.
	 *
	 * @param baseUrl
	 *            the base url
	 * @param reprocessFailedDetails
	 *            the reprocess failed details
	 * @return the url based on trans type
	 */
	private String getUrlBasedOnTransType(String baseUrl, ReprocessFailedDetails reprocessFailedDetails) {
		String url = null;

		if (reprocessFailedDetails.getTransType().equalsIgnoreCase("PAYMENTOUT")) {
			url = baseUrl + "/compliance-service/services-internal/repeatCheckFailedFundsOut";
		}

		if (reprocessFailedDetails.getTransType().equalsIgnoreCase("PAYMENTIN")) {
			url = baseUrl + "/compliance-service/services-internal/repeatCheckFailedFundsIn";
		}

		if (reprocessFailedDetails.getTransType().equalsIgnoreCase("ACCOUNT")
				|| reprocessFailedDetails.getTransType().equalsIgnoreCase("CONTACT")) {
			url = baseUrl + "/compliance-service/services-internal/profile/repeatCheckFailedReg";
		}

		return url;
	}
}
