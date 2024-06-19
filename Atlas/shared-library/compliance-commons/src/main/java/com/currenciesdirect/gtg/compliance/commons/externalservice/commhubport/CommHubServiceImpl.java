package com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.ITokenizer;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequestForTM;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.response.SendEmailResponse;
import com.currenciesdirect.gtg.compliance.commons.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;

@Component("commHubServiceImpl")
public class CommHubServiceImpl implements ICommHubServiceImpl{
	
	/** The log. */
	private static final Logger log = LoggerFactory.getLogger(CommHubServiceImpl.class);
	
	/** The Constant BASE_COMMHUB_URL. */
	private static final String BASE_COMMHUB_URL = "baseCommHubUrl";
	
	/** The Constant AUTHORIZATION. */
	public static final String AUTHORIZATION = "Authorization";
	
	/** The Constant CONTENT_TYPE. */
	public static final String CONTENT_TYPE = "Content-Type";
	
	public static final String COMMHUB = "commhub";
	
	/** The i tokenizer. */
	@Autowired
	private ITokenizer iTokenizer;

	/**
	 * Send email.
	 *
	 * @param sendEmailReq the send email req
	 * @return the send email response
	 */
	@Override
	public SendEmailResponse sendEmail(SendEmailRequest sendEmailReq,boolean createNewToken) {
		SendEmailResponse sendEmailResponse = new SendEmailResponse();
		
		HttpClientPool clientPool = new HttpClientPool();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_COMMHUB_URL);
		String token;
		try {
			String jsonRequest = JsonConverterUtil.convertToJsonWithNull(sendEmailReq);
			log.warn("-----Email Request Start--------- \n {} \n-----Email Request End------",jsonRequest);
			
			token = iTokenizer.getAuthToken(COMMHUB,createNewToken);
			headers.putSingle(AUTHORIZATION, token);
			
			sendEmailResponse = clientPool.sendRequest(baseUrl + "/commhub/notification/sendNotification",
					"POST", JsonConverterUtil.convertToJsonWithNull(sendEmailReq), SendEmailResponse.class, headers,
					MediaType.APPLICATION_JSON_TYPE);
			
			if(null != sendEmailResponse) {
				if(sendEmailResponse.getResponseCode().equals("000")){
					log.warn("Email sent successfully : {}",JsonConverterUtil.convertToJsonWithNull(sendEmailResponse));
				}
				else
					log.warn("Commhub Response : {}",JsonConverterUtil.convertToJsonWithNull(sendEmailResponse));
			}
			else {
				log.warn("Email sending failed");
			}
		}
		catch(Exception e) {
			log.error(" Exception in  CommHubServiceImpl in sending email {1}",e);
		}
		return sendEmailResponse;
	}
	
	/**
	 * Send email for TM alert.
	 *
	 * @param sendTMEmailRequest the send TM email request
	 * @param createNewToken the create new token
	 * @return the send email response
	 */
	//Add For AT-4185
	public SendEmailResponse sendEmailForTMAlert(SendEmailRequestForTM sendTMEmailRequest,boolean createNewToken) {
		SendEmailResponse sendEmailResponse = new SendEmailResponse();
		
		HttpClientPool clientPool = new HttpClientPool();
		MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
		headers.putSingle(CONTENT_TYPE, MediaType.APPLICATION_JSON_TYPE);
		String baseUrl = System.getProperty(BASE_COMMHUB_URL);
		String token;
		try {
			String jsonRequest = JsonConverterUtil.convertToJsonWithNull(sendTMEmailRequest);
			log.warn("-----Transaction Monitoring Email Request Start--------- \n {} \n-----Transaction Monitoring Email Request End------",jsonRequest);
			
			token = iTokenizer.getAuthToken(COMMHUB,createNewToken);
			headers.putSingle(AUTHORIZATION, token);
			
			sendEmailResponse = clientPool.sendRequest(baseUrl + "/commhub/notification/sendSimpleEmail",
					"POST", JsonConverterUtil.convertToJsonWithNull(sendTMEmailRequest), SendEmailResponse.class, headers,
					MediaType.APPLICATION_JSON_TYPE);
			
			if(null != sendEmailResponse) {
				if(sendEmailResponse.getResponseCode().equals("000")){
					log.warn("Transaction Monitoring Alert Email sent successfully : {}",JsonConverterUtil.convertToJsonWithNull(sendEmailResponse));
				}
				else
					log.warn("Commhub Response : {}",JsonConverterUtil.convertToJsonWithNull(sendEmailResponse));
			}
			else {
				log.warn("Transaction Monitoring AlertEmail sending failed");
			}
		}
		catch(Exception e) {
			log.error(" Exception in  CommHubServiceImpl in sendEmailForTMAlert {1}",e);
		}
		return sendEmailResponse;
	}
	
}
