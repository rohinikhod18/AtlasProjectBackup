/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.restport.kafkatxdatalake;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInTrade;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.DataLakeTxResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedRetryRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.KafkaRetryFailedTxExecutorService;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.KafkaRetryFailedTxService;
import com.currenciesdirect.gtg.compliance.compliancesrv.kafkatxdatalake.service.KafkaTxExecutorService;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author prashant.verma
 */

@Controller
@EnableSwagger2
@EnableWebMvc

public class KafkaDataLakeController extends BaseController {

	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaDataLakeController.class);

	@Autowired
	KafkaRetryFailedTxService kafkaRetryFailedTxService;
	
	@Autowired
	KafkaRetryFailedTxExecutorService kafkaRetryFailedTxExecutorService;
	
	@Autowired
	KafkaTxExecutorService kafkaTxExecutorService;
	
	/**
	 *
	 * @param httpRequest
	 *            the http request
	 * @param ids
	 *            comma separated message ids to trigger failed kafka request
	 * @param startDate
	 *            start date of failed requests to trigger failed kafka request
	 * @param endDate
	 *            end date of failed requests to trigger failed kafka request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Trigger failed kafka request based on message ids or startdate and end date.\n\n"
			+ "Date format for startdate and end date should be dd/MM/yyyy", 
			value = "Called manually to retry failed kafka requests and can be used as a bulk operation.", 
			nickname = "retryFailedKafkaRequests", 
			response = DataLakeTxResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@PostMapping(value = "/producedatalaketxmessage", produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> produceDatalakeTxMessage(final HttpServletRequest httpRequest,
			final @RequestParam String operation, final @RequestParam String  serviceType, final @RequestParam String  transactionId, final @RequestParam String  tradePaymentId) {

		LOGGER.info("\n ------- ProduceDatalakeTxMessage Request Start -------- \n");
		DeferredResult<ResponseEntity> deferredResult;		
		ServiceMessage messageRequest;
		if(operation!=null && serviceType!=null && tradePaymentId!=null) {
			OperationEnum operationEnum = OperationEnum.valueOf(operation);
			ServiceInterfaceType serviceTypeValue = ServiceInterfaceType.valueOf(serviceType);
			if(serviceTypeValue.equals(ServiceInterfaceType.FUNDSIN)) {
				FundsInCreateRequest fundInRequest = new FundsInCreateRequest();
				FundsInTrade trade = new FundsInTrade();
				trade.setPaymentFundsInId(Integer.valueOf(tradePaymentId));
				fundInRequest.setTrade(trade);
				messageRequest= fundInRequest;
			}else {
				FundsOutUpdateRequest fundOutRequest = new FundsOutUpdateRequest();
				fundOutRequest.setPaymentFundsoutId(Integer.valueOf(tradePaymentId));
				messageRequest= fundOutRequest;				
			}
			
			kafkaTxExecutorService.executeKafkaTx(operationEnum, serviceTypeValue, transactionId, messageRequest);
			
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
			deferredResult.setResult(responseEntity);
		}else {
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
			deferredResult.setResult(responseEntity);
		}
		LOGGER.info(" \n -----------  ProduceDatalakeTxMessage Request End ---------");
	
		return deferredResult;
	}
	
	/**
	 *
	 * @param httpRequest
	 *            the http request
	 * @param ids
	 *            comma separated message ids to trigger failed kafka request
	 * @param startDate
	 *            start date of failed requests to trigger failed kafka request
	 * @param endDate
	 *            end date of failed requests to trigger failed kafka request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Trigger failed kafka request based on message ids or startdate and end date.\n\n"
			+ "Date format for startdate and end date should be dd/MM/yyyy", 
			value = "Called manually to retry failed kafka requests and can be used as a bulk operation.", 
			nickname = "retryFailedKafkaRequests", 
			response = DataLakeTxResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@PostMapping(value = "/retrytxdatalaketx", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> retryFailedKafkaRequests(final HttpServletRequest httpRequest,
			@ApiParam(value = "KafkaFailedRetry request JSON data", required = true) final @RequestBody KafkaFailedRetryRequest kafkaFailedRetryRequest) {

		LOGGER.info("\n ------- RetryTxDataLake Request Start -------- \n");
		DeferredResult<ResponseEntity> deferredResult;
		DataLakeTxResponse dataLakeTxValidationResponse = kafkaRetryFailedTxService.validateRetryTxFailedRequest(kafkaFailedRetryRequest);
		
		if(dataLakeTxValidationResponse!=null && dataLakeTxValidationResponse.getStatus()) {
			
			kafkaRetryFailedTxExecutorService.executeKafkaFailedTx(kafkaFailedRetryRequest);
			
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.ACCEPTED);
			deferredResult.setResult(responseEntity);
		}else {
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
			deferredResult.setResult(responseEntity);
		}
		LOGGER.info(" \n -----------  RetryTxDataLake Request End ---------");
	
		return deferredResult;
	}

}
