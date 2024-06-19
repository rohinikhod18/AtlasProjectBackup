package com.currenciesdirect.gtg.compliance.compliancesrv.restport.customcheck;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.FundsInCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.request.FundsOutCustomCheckResendRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Controller
@RequestMapping("/repeatcheck")
public class CustomCheckController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomCheckController.class);

	/**
	 * Custom check resend.
	 *
	 * @param httpRequest the http request
	 * @param kycResendRequest the kyc resend request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Perform repeat check blacklist for registrations from the Compliance portal UI.\n\n"
			+ " - IP distance check. Only for Australia and UK (countries of residence). "
			+ "If the Dealer is within the specified range then it will pass otherwise it will fail\n"
			+ " - Country Check (Normal/ High Risk/ Extreme High Risk/ Sanctioned)\n" 
			+ " - Global Check. Only for USA (country of residence)\n\n" 
			+ "The checks have been divided into 3 types with different provisions and rules:\n\n"
			+ " - List A: Permisson Provided\n"
			+ " - List B: Partial Permission with Documents\n"
			+ " - List C: No Permission\n\n" 
			+ "This request is asynchronous. To avoid blocking, we use callbacks-based programming model where instead of the actual result, we will return a DeferredResult to the servlet container.", 
			value = "Call this api to Repeat the Custom Check", 
			nickname = "customCheckResend")
	@ApiResponses(value = { @ApiResponse(response = CustomCheckResendResponse.class, code = 200, message = "A successful response is an instance of the CustomCheckResendResponse class.") })
	@PostMapping(value = "/customCheck", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> customCheckResend(final HttpServletRequest httpRequest,
			final @RequestBody FundsOutCustomCheckResendRequest request) {
		String jsonCustomCheckRequest = JsonConverterUtil.convertToJsonWithoutNull(request); 
		LOGGER.warn("\n ------- CustomCheckResendRequest Request Start -------- \n  {}",jsonCustomCheckRequest);
		LOGGER.warn("\n ----------- CustomCheckResendRequest Request End ---------");
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class,  httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.CUSTOMCHECK_RESEND, request.getOrgCode(),
				request);
		
		AccessToken userToken =  new AccessToken();
		userToken.setPreferredUsername(user.getName());
		return super.executeWorkflow(messageContext, userToken);
	}
	
	@ApiOperation(notes = "This checks the customer as follows\n\n" + 
			" - Currency Check. The currency should be on the Safe List. If not then Currency Check will fail.\n" + 
			" - Amount Range Check. Amount should be within the Amount Range.\n" + 
			" - Reason of transfer check. Checks whether reason matches that stated at the time of registration.\n\n", 
			value = "Update White list data after repeat check for FundsOut",
			nickname = "updateWhiteListData")
	@ApiResponses(value = { @ApiResponse(response = CustomCheckResponse.class, code = 200, message = "A successful response is an instance of the CustomCheckResponse class.") })
	@PostMapping(value = "/updateWhiteListData", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateWhiteListData(final HttpServletRequest httpRequest,
			@ApiParam(value = "payment id to update whitelist dta from", required = true) 
			final @RequestBody UdateWhiteListRequest updateWhiteListData){
		String jsonUpdateWhitelistData = JsonConverterUtil.convertToJsonWithoutNull(updateWhiteListData); 
		LOGGER.warn("\n ------- UdateWhiteListRequest for fundsout Request Start -------- \n  {}",jsonUpdateWhitelistData);
		LOGGER.warn(" \n ----------- UdateWhiteListRequest for fundsout Request End ---------");
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class,  httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.WHITELIST_UPDATE, updateWhiteListData.getOrgCode(),
				updateWhiteListData);
		
		AccessToken userToken =  new AccessToken();
		userToken.setPreferredUsername(user.getName());
		DeferredResult<ResponseEntity> deferredResult;

		deferredResult= super.executeWorkflow(messageContext,userToken);
		return deferredResult;
	}
	
	
	@ApiOperation(notes = "This checks the customer as follows\n\n" + 
			" - Currency Check. The currency should be on the Safe List. If not then Currency Check will fail.\n" + 
			" - Amount Range Check. Amount should be within the Amount Range.\n" + 
			" - Third Party Check. Debtor Name should match with Client Name.\n\n", 
			value = "Repeat check customCheck for FundsIn",
			nickname = "customFundsInCheckResend")
	@ApiResponses(value = { @ApiResponse(response = CustomCheckResendResponse.class, code = 200, message = "A successful response is an instance of the CustomCheckResendResponse class.") })
	@PostMapping(value = "/customCheckfundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> customFundsInCheckResend(final HttpServletRequest httpRequest,
			final @RequestBody FundsInCustomCheckResendRequest request) {
		LOGGER.warn("CustomCheckResendRequest request received {}", request);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class,  httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.CUSTOMCHECK_RESEND, request.getOrgCode(),
				request);
		
		AccessToken userToken =  new AccessToken();
		userToken.setPreferredUsername(user.getName());
		return super.executeWorkflow(messageContext, userToken);
	}
	
	@ApiOperation(notes = "Call this api to update White list data after repeat check for FundsIn.", 
			value = "Call this api to update White list data after repeat check for FundsIn", 
			nickname = "updateFindsInWhiteListData")
	@ApiResponses(value = { @ApiResponse(response = CustomCheckResponse.class, code = 200, message = "A successful response is an instance of the CustomCheckResponse class.") })
	@PostMapping(value = "/updateWhiteListDatafundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateFundsInWhiteListData(final HttpServletRequest httpRequest,
			@ApiParam(value = "payment id to update whitelist dta from", required = true) 
			final @RequestBody UdateWhiteListRequest updateWhiteListData){
		String jsonUpdateWhitelistData = JsonConverterUtil.convertToJsonWithoutNull(updateWhiteListData); 
		LOGGER.warn("\n ------- updateWhiteListDatafundsIn Request Start -------- \n  {}",jsonUpdateWhitelistData);
		LOGGER.warn(" \n ----------- updateWhiteListDatafundsIn Request End ---------");
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class,  httpRequest.getHeader("user"));
		AccessToken userToken =  new AccessToken();
		userToken.setPreferredUsername(user.getName());
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.WHITELIST_UPDATE, updateWhiteListData.getOrgCode(),
				updateWhiteListData);
		
		return  super.executeWorkflow(messageContext,userToken);


	}
}
