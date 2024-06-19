/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsin;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.UdateWhiteListRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInDeleteResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class FundsInController.
 *
 * @author manish
 */
@Controller
@EnableSwagger2
@EnableWebMvc

public class FundsInController extends BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FundsInController.class);

	/**
	 * Funds in create.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param fundsInRestPortCreateRequest
	 *            the funds in rest port create request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Process a Funds-In request. The Funds IN request is validated and checked for the presence of mandatory fields.\n\n" +
			"Validation Steps:\n\n" + 
			" 1) When request is received, we get the country code and use it to get the complete name of country.\n\n" + 
			" 2) then customer type is found.\n\n" + 
			" 3) mandatory fields are validated whether null or not by passing fields to the isValidObject method.\n\n" + 
			" 4) paymentTime date is validated whether null or not and format of date if present is checked by passing fields to isDateInFormat method.\n\n" + 
			" 5) also format of other dates in request is checked in getOptionalDateFields method.\n\n" + 
			" 6) Payment Method is checked in getPaymentMethod method.\n\n" + 
			" 7) if payment method is SWITCH/DEBIT then check additional mandatory fields for debit card payment.\n\n", 
			value = "Called at the time of a new Funds-In request sent by Titan or Aurora", 
			nickname = "fundsInCreate", 
			response = FundsInCreateResponse.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@PostMapping(value = "/fundsin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsInCreate(final HttpServletRequest httpRequest,
			@ApiParam(value = "Funds In details JSON object", required = true) final @RequestBody FundsInCreateRequest fundsInRestPortCreateRequest) {
		String jsonFundsInRestPortCreateRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsInRestPortCreateRequest);
		LOGGER.warn("\n ------- FundsIn Request Start -------- \n  {}",jsonFundsInRestPortCreateRequest);
		FundsInCreateRequest fundsInDomainCreateRequest = convertToDomainRequest(fundsInRestPortCreateRequest);
		fundsInDomainCreateRequest.setType("FUNDS_IN_ADD");
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.FUNDS_IN, fundsInDomainCreateRequest.getOrgCode(),
				fundsInDomainCreateRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
				
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());	

		DeferredResult<ResponseEntity> deferredResult;
			deferredResult = super.executeWorkflow(messageContext, userToken);
					
		LOGGER.warn(" \n -----------  FundsIn Request End ---------");	
		return deferredResult;
				
	}

	/**
	 * Convert to domain request.
	 *
	 * @param fundsInRestPortCreateRequest
	 *            the funds in rest port create request
	 * @return the funds in create request
	 */
	private FundsInCreateRequest convertToDomainRequest(FundsInCreateRequest fundsInRestPortCreateRequest) {
		FundsInCreateRequest fundsInDomainCreateRequest;
		String fundsInRestPortCreateRequestJson = JsonConverterUtil
				.convertToJsonWithoutNull(fundsInRestPortCreateRequest);
		fundsInDomainCreateRequest = JsonConverterUtil.convertToObject(FundsInCreateRequest.class,
				fundsInRestPortCreateRequestJson);
		fundsInDomainCreateRequest.setCustType(fundsInRestPortCreateRequest.getTrade().getCustType());
		return fundsInDomainCreateRequest;
	}

	/**
	 * Update white list data.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param updateWhiteListData
	 *            the update white list data
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Called to update white list data when we clear any Funds-In", 
			value = "Call this api to update white list data", 
			nickname = "updateWhiteListDatafundsIn")
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = { @ApiResponse(response = CustomCheckResponse.class, code = 200, message = "A successful response is an instance of the CustomCheckResponse class.") })
	@PostMapping(value = "/updateWhiteListDatafundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateWhiteListData(final HttpServletRequest httpRequest,
			@ApiParam(value = "payment id to update whitelist dta from", required = true) final @RequestBody UdateWhiteListRequest updateWhiteListData) {
		String jsonUpdateWhiteListData = JsonConverterUtil.convertToJsonWithoutNull(updateWhiteListData);
		LOGGER.warn("\n ------- Update fundsIn whiteList data Request Start -------- \n  {}",jsonUpdateWhiteListData);
		LOGGER.warn(" \n -----------  Update fundsIn whiteList data Request End ---------");

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.WHITELIST_UPDATE, updateWhiteListData.getOrgCode(),
				updateWhiteListData);

		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) httpRequest
				.getUserPrincipal();
		DeferredResult<ResponseEntity> deferredResult;
		if (null != principal) {
			deferredResult = super.executeWorkflow(messageContext, principal.getKeycloakSecurityContext().getToken());
		} else {
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
			deferredResult.setResult(responseEntity);
		}

		return deferredResult;
	}

	/**
	 * Deletefunds in.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param fundsInDeleteRequest
	 *            the funds in delete request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Use to mark a Funds-In as reversed, when we get delete Funds-In request from Titan or Aurora", 
			value = "Call this api to delete an onward payment instruction", 
			nickname = "deletefundsIn")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = { @ApiResponse(response = FundsInDeleteResponse.class, code = 200, message = "A successful response is an instance of the FundsInDeleteResponse class.") })
	@PostMapping(value = "/deletefundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> deletefundsIn(final HttpServletRequest httpRequest,
			@ApiParam(value = "delete fundsin request JSON data", required = true) final @RequestBody FundsInDeleteRequest fundsInDeleteRequest) {
		String jsonFundsInDeleteRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsInDeleteRequest);
		LOGGER.warn("\n ------- Delete FundsIn Request Start -------- \n  {}",jsonFundsInDeleteRequest);
		LOGGER.warn(" \n ----------- Delete FundsIn Request End ---------");

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.DELETE_OPI, fundsInDeleteRequest.getOrgCode(),
				fundsInDeleteRequest);

		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) httpRequest
				.getUserPrincipal();
		DeferredResult<ResponseEntity> deferredResult;
		if (null != principal) {
			deferredResult = super.executeWorkflow(messageContext, principal.getKeycloakSecurityContext().getToken());
		} else {
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
			deferredResult.setResult(responseEntity);
		}

		return deferredResult;
	}
	
	@ApiOperation(notes = "This API is called when we have a service failure for checks and we want to perform bulk repeat check for failed FundsIn from Compliance portal.\n\n", 
			value = "Call this api to recheck a failed Funds-In", 
			nickname = "repeatCheckFailedFundsIn", response = FundsInCreateResponse.class)
	@ApiResponses(value = { @ApiResponse(response = FundsInCreateResponse.class, code = 200, message = "A successful response is an instance of the FundsInCreateResponse class.") })
	@PostMapping(value = "/repeatCheckFailedFundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> repeatCheckFailedFundsIn(final HttpServletRequest httpRequest,
			@ApiParam(value = "payment id to repeat check failed services", required = true) 
			final @RequestBody ReprocessFailedDetails fundsInServiceFailedRequest) {
		String jsonFundsInServiceFailedRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsInServiceFailedRequest);
		LOGGER.warn("\n ------- Repeat Check for fundsin failed Services Request Start -------- \n {}",jsonFundsInServiceFailedRequest);
		LOGGER.warn(" \n ----------- Repeat Check for fundsin failed Services Request Start ---------");
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.RECHECK_FAILURES, null,
				fundsInServiceFailedRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class,  httpRequest.getHeader("user"));
		AccessToken userToken =  new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

}
