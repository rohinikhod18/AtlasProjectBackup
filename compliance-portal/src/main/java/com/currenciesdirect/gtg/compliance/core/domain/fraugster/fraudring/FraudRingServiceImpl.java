package com.currenciesdirect.gtg.compliance.core.domain.fraugster.fraudring;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.report.IFraudRingDBService;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

@Component("fraudRingGraphServiceImpl")
public class FraudRingServiceImpl implements IFraudRingService{
	
	@Autowired
	@Qualifier("fraudRingDBServiceImpl")
	private IFraudRingDBService iFraudRingDBServiceResponse;
	
	private Logger log = LoggerFactory.getLogger(FraudRingServiceImpl.class);
	
	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "Content-Type";
	
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	
	public static final String FRAUDRING_POST = "fraudring.api.post";
	
	@Override
	public FraudRingResponse getFraudRingGraphData(FraudRingRequest fraudRingGraphRequest) {
		FraudRingResponse fraudRingResponse = new FraudRingResponse();
		HttpClientPool clientPool = HttpClientPool.getInstance(); 
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		String neo4jUrl = System.getProperty(FRAUDRING_POST);
		String crmContactId = fraudRingGraphRequest.getCrmContactId();
		String url = neo4jUrl+"/fraudring/v1/get_ring/"+crmContactId;
		try {
			fraudRingResponse = clientPool.sendRequest(url, "GET",
					JsonConverterUtil.convertToJsonWithNull(fraudRingGraphRequest), FraudRingResponse.class, headers,
					MediaType.APPLICATION_JSON_TYPE);
		} catch (Exception e) {
			log.error("Exception in getFraudRingGraphData in FraudRingServiceImpl {1}", e);
		}
		return fraudRingResponse;
	}
	
	@Override
	public Boolean checkIsFraudRingPresent(FraudRingRequest fraudRingGraphRequest) {
		boolean isFraud = false;
		HttpClientPool clientPool = HttpClientPool.getInstance(); 
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		String neo4jUrl = System.getProperty(FRAUDRING_POST);
		String crmContactId = fraudRingGraphRequest.getCrmContactId();
		String url = neo4jUrl+"/fraudring/v1/check_is_fraud_ring_present/"+crmContactId;
		try {
			isFraud = clientPool.sendRequest(url, "GET",JsonConverterUtil.convertToJsonWithNull(fraudRingGraphRequest),
					Boolean.class, headers, MediaType.APPLICATION_JSON_TYPE);
		}
		catch(Exception e) {
			log.error("Exception in getFraudStatus in FraudRingServiceImpl {1}", e);
		}
		return isFraud;
	}
	
	@Override
	public FraudRingNode getNodeDetails(FraudRingRequest fraudRingGraphRequest) {
		FraudRingNode fraudRingNode = null;
		try {
			fraudRingNode = iFraudRingDBServiceResponse.getClientData(fraudRingGraphRequest);
		} catch(Exception e) {
			log.error("Exception in getNodeDetails in FraudRingServiceImpl {1}", e);
		}
		return fraudRingNode;
	}
}