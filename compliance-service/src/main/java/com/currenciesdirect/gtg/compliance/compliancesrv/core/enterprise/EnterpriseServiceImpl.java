package com.currenciesdirect.gtg.compliance.compliancesrv.core.enterprise;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.enterprise.EnterpriseIPAddressDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

//AT-4745
@Component("enterpriseServiceImpl")
public class EnterpriseServiceImpl implements IEnterpriseService{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(EnterpriseServiceImpl.class);

	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "Content-Type";
	
	/** The Constant BASE_TITAN_URL. */
	private static final String BASE_ENTERPRISE_URL = "baseEnterpriseUrl";
	
	@Override
	public EnterpriseIPAddressDetails getIPAddressDetails(String iPAddress) throws ComplianceException {
		EnterpriseIPAddressDetails response = null;
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		String baseUrl = System.getProperty(BASE_ENTERPRISE_URL);
		String url = baseUrl+"/es-restfulinterface/rest/ipservice/getIPAddressDetails?IPAddress="+iPAddress;
		try {
			response = clientPool.sendRequest(url, "GET", 
					JsonConverterUtil.convertToJsonWithNull(response),
					EnterpriseIPAddressDetails.class,headers,MediaType.APPLICATION_JSON_TYPE);
		} catch (Exception e) {
			LOG.error("Error in getIPAddressDetails", e);
		}
		return response;
	}

}
