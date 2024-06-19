package com.currenciesdirect.gtg.compliance.compliancesrv.restport.ordercardsrv;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.OrderCardStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.PostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.OrderCardRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.PostCardTransactionResponse;
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
@RequestMapping("card-services")
public class OrderCardController extends BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderCardController.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@ApiOperation(notes = "Called when NGOP sends a OrderCardRequest", value = "Called when NGOP sends a OrderCardRequest", nickname = "Order-Card")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = OrderCardResponse.class, code = 200, message = "A successful response is an instance of the OrderCardResponse class.") })
	@PutMapping(value = "/cardEligibilityCheck", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> newOrderCardService(final HttpServletRequest httpRequest,
			@ApiParam(value = "Order-Card request JSON data", required = true) final @RequestBody OrderCardRequest orderCardRequest) {

		String jsonOrderCardRequest = JsonConverterUtil.convertToJsonWithoutNull(orderCardRequest);
		LOGGER.warn("\n -------Order Card Request Start -------- \n  {}", jsonOrderCardRequest);
		LOGGER.warn(" \n -----------Order Card Request End ---------");

		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.CARD, OperationEnum.CHECK_CARD_ELIGIBILITY,
				null, orderCardRequest);

		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) httpRequest
				.getUserPrincipal();
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
	 * New order card service.
	 *
	 * @param httpRequest the http request
	 * @param orderCardStatusUpdateRequest the order card status update request
	 * @return the deferred result
	 * AT-4812
	 */
	@ApiOperation(notes = "Called when NGOP sends a OrderCardStatusUpdateRequest", value = "Called when NGOP sends a OrderCardStatusUpdateRequest", nickname = "Order-Card")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = OrderCardResponse.class, code = 200, message = "A successful response is an instance of the OrderCardResponse class.") })
	@PostMapping(value = "/cardStatusUpdate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> newOrderCardService(final HttpServletRequest httpRequest,
			@ApiParam(value = "OrderCardStatusUpdateRequest JSON data", required = true) final @RequestBody OrderCardStatusUpdateRequest orderCardStatusUpdateRequest) {

		String jsonOrderCardRequest = JsonConverterUtil.convertToJsonWithoutNull(orderCardStatusUpdateRequest);
		LOGGER.warn("\n -------Card Status Update Request Start -------- \n  {}", jsonOrderCardRequest);
		LOGGER.warn(" \n -----------Card Status Update Request End ---------");
		
		DeferredResult<ResponseEntity> deferredResult = new DeferredResult<>();

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.CARD, OperationEnum.CARD_STATUS_UPDATE,
				null, orderCardStatusUpdateRequest);

		KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal = (KeycloakPrincipal<RefreshableKeycloakSecurityContext>) httpRequest
				.getUserPrincipal();
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
	 * Intuition post transaction.
	 *
	 * @param httpRequest the http request
	 * @param request     the request
	 * @return the deferred result
	 */
	// AT-4896
	@ApiOperation(notes = "Called when IBM-MQ sends a PostCardTransactionRequest", value = "Called when IBM-MQ sends a PostCardTransactionRequest", nickname = "Post-Card-Transaction")
	@ApiResponses(value = {
			@ApiResponse(response = PostCardTransactionResponse.class, code = 200, message = "A successful response is an instance of the PostCardTransactionResponse class.") })
	@PostMapping(value = "/intuitionCardPostTransaction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> intuitionPostTransaction(final HttpServletRequest httpRequest,
			@ApiParam(value = "PostCardTransactionRequest JSON data", required = true) final @RequestBody PostCardTransactionRequest request) {

		String jsonOrderCardRequest = JsonConverterUtil.convertToJsonWithoutNull(request);
		LOGGER.warn("\n -------Post Card Transaction Request Start -------- \n  {}", jsonOrderCardRequest);
		LOGGER.warn(" \n -----------Post Card Transaction Request End ---------");
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.CARD, OperationEnum.POST_TRANSACTION, null, request);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername("cd.comp.system");

		return super.executeWorkflow(messageContext, userToken);
	}

}
