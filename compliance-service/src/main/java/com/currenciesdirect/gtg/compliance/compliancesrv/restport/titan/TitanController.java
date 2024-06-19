package com.currenciesdirect.gtg.compliance.compliancesrv.restport.titan;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.representations.AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.request.TitanGetPaymentStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

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
public class TitanController extends BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TitanController.class);

	@ApiOperation(notes = "Called when Titan sends a request", value = "Called when Titan sends a request", nickname = "Sign-Up")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = FundsInCreateResponse.class, code = 200, message = "A successful response is an instance of the FundsResponse class.") })
	@PostMapping(value = "/getPaymentStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> titanGetPaymentStatus(final HttpServletRequest httpRequest,
			@ApiParam(value = "Titan request JSON data", required = true) final @RequestBody TitanGetPaymentStatusRequest fundsRequest) {

		String jsonFundsRequest = JsonConverterUtil.convertToJsonWithoutNull(fundsRequest);
		LOGGER.warn("\n -------Titan Get Payment Status Request Start -------- \n  {}", jsonFundsRequest);
		LOGGER.warn(" \n -----------Titan Get Payment Status Request End ---------");

		fundsRequest.setTradePaymentId(generateTradePaymentId(fundsRequest.getTrade().getContractNumber()));

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.TITAN, OperationEnum.GET_PAYMENT_STATUS, null, fundsRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername("cd.comp.system");

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Generate trade payment id.
	 *
	 * @param tradeAccountNumber the trade account number
	 * @return the integer
	 */
	private Integer generateTradePaymentId(String tradeContractNumber) {
		//Added changes for AT-5000
		if (tradeContractNumber == null || tradeContractNumber.trim().length() == 0
				|| !tradeContractNumber.contains("-")) {
			return null;
		} else {
			Boolean isContainAlphabets = tradeContractNumber.matches(".*[A-Za-z].*");
			if (Boolean.TRUE.equals(isContainAlphabets)) {
				return null;
			} else {
				String[] result = tradeContractNumber.split("-");
				return Integer.parseInt(result[1]);
			}
		}

	}
}
