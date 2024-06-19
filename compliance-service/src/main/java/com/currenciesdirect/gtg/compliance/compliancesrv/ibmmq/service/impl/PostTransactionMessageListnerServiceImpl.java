package com.currenciesdirect.gtg.compliance.compliancesrv.ibmmq.service.impl;

import java.util.ArrayList;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.enums.PostCardTransactionRequestTypeEnum;
import com.currenciesdirect.gtg.compliance.commons.util.HttpClientPool;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.PostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ibmmq.service.IPostTransactionMessageListnerService;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

@Component
public class PostTransactionMessageListnerServiceImpl implements IPostTransactionMessageListnerService {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PostTransactionMessageListnerServiceImpl.class);

	/**
	 * Prepare send post transaction request.
	 *
	 * @param textMsg the text msg
	 */
	//AT-4948 AT-4969
	@Override
	public void prepareSendPostTransactionRequest(String textMsg) {
		try {
			PostCardTransactionRequest request = JsonConverterUtil.convertToObject(PostCardTransactionRequest.class,
					textMsg);
			
			LOG.info("Post Card Transaction Request Received for Payment Life Cycle ID : {} and Request Type : {}",
					request.getPaymentLifeId(), request.getCardRequestType());

			if (PostCardTransactionRequestTypeEnum.CX_AUTH.getCardRequestTypeAsString()
					.equalsIgnoreCase(request.getCardRequestType())
					|| PostCardTransactionRequestTypeEnum.CX_REFUND.getCardRequestTypeAsString()
							.equalsIgnoreCase(request.getCardRequestType())
					|| PostCardTransactionRequestTypeEnum.CX_REVERSAL.getCardRequestTypeAsString()
							.equalsIgnoreCase(request.getCardRequestType())) {

				HttpClientPool clientPool = HttpClientPool.getInstance();
				MultivaluedHashMap<String, Object> headers = new MultivaluedHashMap<>();
				String url = null;

				ArrayList<Object> list = new ArrayList<>();
				headers.put("user", list);

				String baseUrl = System.getProperty("baseComplianceServiceUrl");

				url = baseUrl + "/compliance-service/services-internal/card-services/intuitionCardPostTransaction";

				clientPool.sendRequest(url, "POST", request, BaseResponse.class, headers,
						MediaType.APPLICATION_JSON_TYPE);
			}

		} catch (Exception e) {
			LOG.error("Exception in prepareSendPostTransactionRequest() of PostTransactionMessageListnerServiceImpl : ",
					e);
		}

	}

}
