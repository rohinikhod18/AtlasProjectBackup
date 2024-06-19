package com.currenciesdirect.gtg.compliance.compliancesrv.core.transactionmonitoringmq;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.MQBulkReprocessResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.commons.util.HttpClientPool;

public class TMBulkReprocessMonitorThread extends Thread {
	
	/** The log. */
	private Logger log = LoggerFactory.getLogger(TMBulkReprocessMonitorThread.class);

	private List<TransactionMonitoringMQRequest> bulkProcessList;
	
	
	public TMBulkReprocessMonitorThread(List<TransactionMonitoringMQRequest> bulkProcessList) {
		this.bulkProcessList = bulkProcessList;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		try {
			log.warn("{}  TMBulkReprocessMonitorThread started", this.getName());
			if (null != bulkProcessList && !bulkProcessList.isEmpty()) {
				HttpClientPool clientPool = HttpClientPool.getInstance();
				MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
				String url = null;
				
				ArrayList<Object> list = new ArrayList<>();
				headers.put("user",list);
				 
				String baseUrl = System.getProperty("baseComplianceServiceUrl");

				 url = baseUrl + "/compliance-service/services-internal/intuition/tmmqBulkReprocess";

					for (int reprocess = 0; reprocess < bulkProcessList.size(); reprocess++) {

						clientPool.sendRequest(url, "POST", bulkProcessList.get(reprocess), MQBulkReprocessResponse.class,
								headers, MediaType.APPLICATION_JSON_TYPE);
					}
					 
			}
		} catch (Exception e) {
			String logData = this.getName();
			logData = logData.concat(" Error while sending request to reprocess ; ").concat(e.toString());
			log.error(logData);
		}
	}

}
