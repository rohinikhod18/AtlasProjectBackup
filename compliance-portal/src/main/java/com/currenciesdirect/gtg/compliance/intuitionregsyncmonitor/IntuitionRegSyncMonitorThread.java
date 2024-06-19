package com.currenciesdirect.gtg.compliance.intuitionregsyncmonitor;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountMQRequest;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;

public class IntuitionRegSyncMonitorThread extends Thread {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(IntuitionRegSyncMonitorThread.class);
	
	/** The user. */
	private UserProfile user;

	/** The trade account numbers. */
	private List<TransactionMonitoringAccountMQRequest> tradeAccountNumbers;

	/**
	 * Instantiates a new intuition reg sync monitor thread.
	 *
	 * @param user the user
	 * @param tradeAccountNumbers the trade account numbers
	 */
	public IntuitionRegSyncMonitorThread(UserProfile user, List<TransactionMonitoringAccountMQRequest> tradeAccountNumbers) {
		this.user = user;
		this.tradeAccountNumbers = tradeAccountNumbers;
	}

	/**
	 * Run.
	 */
	@Override
	public void run() {
		try {
			if (tradeAccountNumbers != null && !tradeAccountNumbers.isEmpty()) {
				HttpClientPool clientPool = HttpClientPool.getInstance();
				MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
				ArrayList<Object> list = new ArrayList<>();
				list.add(JsonConverterUtil.convertToJsonWithNull(user));
				headers.put("user", list);
				String baseUrl = System.getProperty("baseComplianceServiceUrl");
				String url = baseUrl + "/compliance-service/services-internal/intuition/syncRegWithIntuition";
				
				for (int i = 0; i < tradeAccountNumbers.size(); i++) {
					clientPool.sendRequest(url, "POST", tradeAccountNumbers.get(i), BaseResponse.class, headers,
							MediaType.APPLICATION_JSON_TYPE);
				}
			}
		} catch (Exception e) {
			String logData = this.getName();
			logData = logData.concat(" Error while sending request to reprocess ; ").concat(e.toString());
			log.error(logData);
		}
	}
}
