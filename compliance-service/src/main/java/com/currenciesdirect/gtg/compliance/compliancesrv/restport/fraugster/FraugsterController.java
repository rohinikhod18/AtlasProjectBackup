package com.currenciesdirect.gtg.compliance.compliancesrv.restport.fraugster;

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

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class FraugsterController.
 */
@Controller
@RequestMapping("/repeatcheck")
public class FraugsterController extends BaseController {

	/** The Constant FRAUGSTER_RESEND_REQUEST_REQUEST_RECEIVED. */
	private static final String FRAUGSTER_RESEND_REQUEST_REQUEST_RECEIVED = "Fraugster ResendRequest request received {}";
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(FraugsterController.class);

	/**
	 * Fraugster resend.
	 *
	 * @param fraugsterResendRequest
	 *            the fraugster resend request
	 * @param httpRequest
	 *            the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Perform repeat check of Fraugster for Registrations from Compliance portal UI.\n"
			+ "This is an internal check which verifies the Device Information (i.e. IP Address, Browser version, Broweser Name, etc.).\n"
			+ "It will generate a Score and if this is less than the defined threshold value then this test will PASS.\n\n", value = "Call this api to perform repeat check of Fraugster for Registrations from Compliance portal UI", nickname = "fundsInFraugsterResend")
	@ApiResponses(value = {
			@ApiResponse(response = FraugsterResendResponse.class, code = 200, message = "A successful response is an instance of the FraugsterResendResponse class.") })
	@PostMapping(value = "/fraugster", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fraugsterResend(
			@RequestBody final FraugsterResendRequest fraugsterResendRequest, final HttpServletRequest httpRequest) {
		LOGGER.debug(FRAUGSTER_RESEND_REQUEST_REQUEST_RECEIVED, fraugsterResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.FRAUGSTER_RESEND, fraugsterResendRequest.getOrgCode(),
				fraugsterResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds in fraugster resend.
	 *
	 * @param fraugsterResendRequest
	 *            the fraugster resend request
	 * @param httpRequest
	 *            the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Perform repeat check of Fraugster for FundsIn from Compliance portal UI", value = "Call this api to perform repeat check of Fraugster for FundsIn from Compliance portal UI", nickname = "fundsInFraugsterResend")
	@ApiResponses(value = {
			@ApiResponse(response = FraugsterResendResponse.class, code = 200, message = "A successful response is an instance of the FraugsterResendResponse class.") })
	@PostMapping(value = "/fraugsterCheckfundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsInFraugsterResend(
			@RequestBody final FundsInFraugsterResendRequest fraugsterResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(FRAUGSTER_RESEND_REQUEST_REQUEST_RECEIVED, fraugsterResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.FRAUGSTER_RESEND, fraugsterResendRequest.getOrgCode(),
				fraugsterResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds out fraugster resend.
	 *
	 * @param fraugsterResendRequest
	 *            the fraugster resend request
	 * @param httpRequest
	 *            the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Perform repeat check of Fraugster for FundsOut from Compliance portal UI", value = "Call this api to perform repeat check of Fraugster for FundsOut from Compliance portal UI", nickname = "fundsOutFraugsterResend")
	@ApiResponses(value = {
			@ApiResponse(response = FraugsterResendResponse.class, code = 200, message = "A successful response is an instance of the FraugsterResendResponse class.") })
	@PostMapping(value = "/fraugsterCheckfundsOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsOutFraugsterResend(
			@RequestBody final FundsOutFruagsterResendRequest fraugsterResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(FRAUGSTER_RESEND_REQUEST_REQUEST_RECEIVED, fraugsterResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.FRAUGSTER_RESEND, fraugsterResendRequest.getOrgCode(),
				fraugsterResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
}
