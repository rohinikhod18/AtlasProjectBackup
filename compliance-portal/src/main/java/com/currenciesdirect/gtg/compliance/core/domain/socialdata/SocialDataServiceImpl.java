package com.currenciesdirect.gtg.compliance.core.domain.socialdata;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.report.ISocialDataService;
import com.currenciesdirect.gtg.compliance.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

@Component("socialDataServiceImpl")
public class SocialDataServiceImpl implements ISocialDataService{

	private Logger log = LoggerFactory.getLogger(SocialDataServiceImpl.class);
	
	public static final String SOCIALDATA_POST = "fraudring.api.post";
	
	public SocialDataResponse getSocialData(SocialDataRequest socialDataRequest) {
		SocialDataResponse socialDataResponse = new SocialDataResponse();
		HttpClientPool clientPool = HttpClientPool.getInstance();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		String socialDataurl = System.getProperty(SOCIALDATA_POST);
		String emailId = socialDataRequest.getEmailId();
		String url = socialDataurl+"/socialdata/v1/"+emailId;
		try {
			socialDataResponse = clientPool.sendRequest(url, "GET", 
					JsonConverterUtil.convertToJsonWithNull(socialDataRequest),
					SocialDataResponse.class,headers,MediaType.APPLICATION_JSON_TYPE);
		}
		catch(Exception e) {
			log.error("Exception in getSocialData in SocialDataServiceImpl {1}", e);
		}
		return socialDataResponse;
	}
}