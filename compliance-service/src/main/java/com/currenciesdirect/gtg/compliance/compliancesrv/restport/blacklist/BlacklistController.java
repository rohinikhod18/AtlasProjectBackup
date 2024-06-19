package com.currenciesdirect.gtg.compliance.compliancesrv.restport.blacklist;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.representations.AccessToken;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutPaymentReferenceResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.PaymentReferenceResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class BlacklistController.
 */
@Controller
@RequestMapping("/repeatcheck")
public class BlacklistController extends BaseController {

	/** The Constant BLACKLIST_RESEND_REQUEST_REQUEST_RECEIVED. */
	private static final String BLACKLIST_RESEND_REQUEST_REQUEST_RECEIVED = "\n --Blacklist ResendRequest request received-- \n {}";
	// added for  AT-3658
	/** The Constant PAYMENT_REFERENCE_RESEND_REQUEST_REQUEST_RECEIVED. */
	private static final String PAYMENT_REFERENCE_RESEND_REQUEST_REQUEST_RECEIVED = "\n --Payment Reference ResendRequest request received-- \n {}";
	
	/** The Constant LOGGER. */
	private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BlacklistController.class);

	@ApiOperation(notes = "Perform repeat check blacklist for registrations from Compliance portal UI.\n\n"
			+ "This is an internal check in ATLAS which checks the Customer Information on the basis of various parameters like:\n\n"
			+ " - Name: (it should be whole name - first name plus last name fully matched)\n"
			+ " - Phone number: (last 9 digits)\n" + " - IP\n" + " - Domain\n" + " - Email\n\n"
			+ "If there is a validation failure the relevant MatchedData field will contain the data which was matched to the "
			+ "blacklist. With this information being displayed on the UI.\n\n"
			+ "This request is asynchronous. To avoid blocking, we use callbacks-based programming model where instead of the actual result, we will return a DeferredResult to the servlet container.", value = "Call this api to Repeat Blacklist Check", nickname = "blacklistResend", response = BlacklistResendResponse.class)
	@ApiResponses(value = {
	@ApiResponse(response = BlacklistResendResponse.class, code = 200, message = "A successful response is an instance of the BlacklistResendResponse class.") })
	@PostMapping(value = "/blacklist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> blacklistResend(final HttpServletRequest httpRequest,
			final @RequestBody BlacklistResendRequest blacklistResendRequest) {
		LOGGER.warn(BLACKLIST_RESEND_REQUEST_REQUEST_RECEIVED, blacklistResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.BLACKLIST_RESEND, blacklistResendRequest.getOrgCode(),
				blacklistResendRequest);
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds in blacklist resend.
	 *
	 * @param blacklistResendRequest
	 *            the blacklist resend request
	 * @param httpRequest
	 *            the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Retry a blacklisted Funds In request.\n\n", value = "Call this api to retry a blacklisted Funds In request", nickname = "fundsInBlacklistResend", response = BlacklistResendResponse.class)
	// @ApiResponses(value = { @ApiResponse(response =
	// BlacklistResendResponse.class, code = 200, message = "A successful
	// response is an instance of the BlacklistResendResponse class.") })
	@PostMapping(value = "/blacklistCheckfundsIn", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsInBlacklistResend(
			@ApiParam(value = "Funds In Blacklist Resend Request JSON object", required = true) @RequestBody final FundsInBlacklistResendRequest blacklistResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(BLACKLIST_RESEND_REQUEST_REQUEST_RECEIVED, blacklistResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.BLACKLIST_RESEND, blacklistResendRequest.getOrgCode(),
				blacklistResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds in blacklist resend.
	 *
	 * @param blacklistResendRequest
	 *            the blacklist resend request
	 * @param httpRequest
	 *            the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Retry a blacklisted Funds Out request.\n\n", value = "Call this api to retry a blacklisted Funds Out request", nickname = "fundsOutBlacklistResend", response = BlacklistResendResponse.class)
	// @ApiResponses(value = { @ApiResponse(response =
	// BlacklistResendResponse.class, code = 200, message = "A successful
	// response is an instance of the BlacklistResendResponse class.") })
	@PostMapping(value = "/blacklistCheckfundsOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsOutBlacklistResend(
			@ApiParam(value = "Funds Out Blacklist Resend Request JSON data", required = true) @RequestBody final FundsOutBlacklistResendRequest blacklistResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(BLACKLIST_RESEND_REQUEST_REQUEST_RECEIVED, blacklistResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.BLACKLIST_RESEND, blacklistResendRequest.getOrgCode(),
				blacklistResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
	
	/**
	 * Funds in reference resend.
	 *
	 * @param referenceResendRequest
	 *            the reference resend request
	 * @param httpRequest
	 *            the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Retry a reference Funds Out request.\n\n", value = "Call this api to retry a reference Funds Out request", nickname = "fundsOuteferenceResend", response = PaymentReferenceResendResponse.class)
	// @ApiResponses(value = { @ApiResponse(response =
	// PaymentReferenceResendResponse.class, code = 200, message = "A successful
	// response is an instance of the PaymentReferenceResendResponse class.") })
	@PostMapping(value = "/referenceCheckfundsOut", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsOutReferenceResend(
			@ApiParam(value = "Funds Out Payment Reference Resend Request JSON data", required = true) @RequestBody final FundsOutPaymentReferenceResendRequest paymentReferenceResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(PAYMENT_REFERENCE_RESEND_REQUEST_REQUEST_RECEIVED, paymentReferenceResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.PAYMENT_REFERENCE_RESEND, paymentReferenceResendRequest.getOrgCode(),
				paymentReferenceResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
}
