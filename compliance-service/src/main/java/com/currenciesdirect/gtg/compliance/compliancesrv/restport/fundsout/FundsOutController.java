package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fundsout;

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
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutDeleteRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutDeleteResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Controller
@EnableSwagger2
@EnableWebMvc
@Api(value = "/")
public class FundsOutController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(FundsOutController.class);
	
	@ApiOperation(notes = "Called at the time of a new Funds-Out request sent by Titan or Aurora", 
			value = "Call this api at the time of fundout", 
			nickname = "fundsOut")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", 
					value = "Add bearer token", 
					defaultValue = "Bearer <<AbCdEf123456>>", 
					required = true, 
					dataType = "string", 
					paramType = "header") 
			})
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = { @ApiResponse(response = FundsOutResponse.class, code = 200, message = "A successful response is an instance of the FundsOutResponse class.") })
	@PostMapping(value = "/fundsout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsOut(final HttpServletRequest httpRequest,
			@ApiParam(value = "FundsOut details request JSON data", required = true) final @RequestBody FundsOutRequest fundsOutDomainRequest){
		
		String jsonFundsOutDomainRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsOutDomainRequest);
		LOGGER.warn("\n ------- FundsOut Request Start -------- \n  {}",jsonFundsOutDomainRequest );
		LOGGER.warn(" \n ----------- FundsOut Request End ---------");
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.FUNDS_OUT, fundsOutDomainRequest.getOrgCode(),
				fundsOutDomainRequest);
		
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());	

		DeferredResult<ResponseEntity> deferredResult;
			deferredResult = super.executeWorkflow(messageContext, userToken);
					
		LOGGER.warn(" \n -----------  FundsOut Request End ---------");	
		return deferredResult;
	}
	
	
	@ApiOperation(notes = "Called when we get update Funds-Out request from Titan or Aurora", 
			value = "Call this api at the time of update OPI", 
			nickname = "fundsOut")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", 
					value = "Add bearer token", 
					defaultValue = "Bearer <<AbCdEf123456>>", 
					required = true, 
					dataType = "string", 
					paramType = "header") 
			})
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = { @ApiResponse(response = FundsOutUpdateResponse.class, code = 200, message = "A successful response is an instance of the BlacklistResendResponse class.") })
	@PostMapping(value = "/updatefundsOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updatefundsOut(final HttpServletRequest httpRequest,
			@ApiParam(value = "delete fundsout request JSON data", required = true) final @RequestBody FundsOutUpdateRequest fundsOutUpdateRequest){
		String jsonFundsOutUpdateRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsOutUpdateRequest);
		LOGGER.warn("\n ------- Update FundsOut Request Start -------- \n  {}",jsonFundsOutUpdateRequest);
		LOGGER.warn(" \n ----------- Update FundsOut Request End ---------");
		
		
		
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.UPDATE_OPI, fundsOutUpdateRequest.getOrgCode(),
				fundsOutUpdateRequest);
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) httpRequest.getUserPrincipal();
		DeferredResult<ResponseEntity> deferredResult;
		if(null != principal){
			deferredResult= super.executeWorkflow(messageContext,
					principal.getKeycloakSecurityContext().getToken());
		}else{
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
			deferredResult.setResult(responseEntity);
		}

		return deferredResult;
	}

	@ApiOperation(notes = "Mark a Funds-Out as reversed when we get a delete Funds-Out request from Titan or Aurora", 
			value = "Call this api to delete an onward payment instruction", 
			nickname = "deletefundsOut")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", 
					value = "Add bearer token", 
					defaultValue = "Bearer <<AbCdEf123456>>", 
					required = true, 
					dataType = "string", 
					paramType = "header") 
			})
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = { @ApiResponse(response = FundsOutDeleteResponse.class, code = 200, message = "A successful response is an instance of the FundsOutDeleteResponse class.") })
	@PostMapping(value = "/deletefundsOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> deletefundsOut(final HttpServletRequest httpRequest,
			@ApiParam(value = "delete fundsout request JSON data", required = true) final @RequestBody FundsOutDeleteRequest fundsOutDeleteRequest){
		String jsonFundsOutDeleteRequest =  JsonConverterUtil.convertToJsonWithoutNull(fundsOutDeleteRequest);
		LOGGER.warn("\n ------- Delete FundsOut Request Start -------- \n  {}",jsonFundsOutDeleteRequest);
		LOGGER.warn(		" \n ----------- Delete FundsOut Request End ---------");
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.DELETE_OPI, fundsOutDeleteRequest.getOrgCode(),
				fundsOutDeleteRequest);
		
		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) httpRequest.getUserPrincipal();
		DeferredResult<ResponseEntity> deferredResult;
		if(null != principal){
			deferredResult= super.executeWorkflow(messageContext,
					principal.getKeycloakSecurityContext().getToken());
		}else{
			deferredResult = new DeferredResult<>();
			BaseResponse baseResponse = new BaseResponse();
			ResponseEntity<BaseResponse> responseEntity = new ResponseEntity<>(baseResponse, HttpStatus.UNAUTHORIZED);
			deferredResult.setResult(responseEntity);
		}

		return deferredResult;
	}
	
	@ApiOperation(notes = "when we have got service failure for checks and we want to perform bulk repeat check for failed Funds-Out from Compliance portal", 
			value = "Call this api to perform repeat check", 
			nickname = "repeatCheckFailedFundsOut")
	@ApiResponses(value = { @ApiResponse(response = FundsOutResponse.class, code = 200, message = "A successful response is an instance of the FundsOutResponse class.") })
	@PostMapping(value = "/repeatCheckFailedFundsOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> repeatCheckFailedFundsOut(final HttpServletRequest httpRequest,
			@ApiParam(value = "payment id to repeat check failed services", required = true) 
			final @RequestBody ReprocessFailedDetails fundsOutServiceFailedRequest){
		String jsonFundsOutServiceFailedRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsOutServiceFailedRequest);
		LOGGER.warn("\n ------- Repeat Check for fundsout failed Services Request Start -------- \n  {}",jsonFundsOutServiceFailedRequest);
		LOGGER.warn(" \n ----------- Repeat Check for fundsout failed Services Request End ---------");
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.RECHECK_FAILURES, null,
				fundsOutServiceFailedRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class,  httpRequest.getHeader("user"));
		AccessToken userToken =  new AccessToken();
		userToken.setPreferredUsername(user.getName());
		
		return super.executeWorkflow(messageContext,userToken);
	}
}
