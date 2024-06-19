package com.currenciesdirect.gtg.compliance.compliancesrv.restport.sanction;

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

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsOutSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * The Class SanctionController.
 */
@Controller
@RequestMapping("/repeatcheck")
public class SanctionController extends BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionController.class);

	/** The Constant SANCTION_RESEND_REQUEST_RECEIVED. */
	private static final String SANCTION_RESEND_REQUEST_RECEIVED = "Sanction ResendRequest request received {}";

	/**
	 * Sanction resend.
	 *
	 * @param httpRequest           the http request
	 * @param sanctionResendRequest the sanction resend request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Sanction uses a name to check whether the person has been officially sanctioned. It can perform partial matches on names too.  \n"
			+ "\n" + "This is sent to ATLAS which makes calls to the following external 3rd party service providers:\n"
			+ " - OFAC List\n" + " - World Check\n" + "\n"
			+ "If a Sanction check fails the user will have to manually clear it for either of the providers using the Repeat Check flow.\n", value = "Call this api to repeat a Sanction check", nickname = "sanctionResend")
	@ApiResponses(value = {
			@ApiResponse(response = SanctionResendResponse.class, code = 200, message = "A successful response is an instance of the SanctionResendResponse class.") })
	@PostMapping(value = "/sanction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> sanctionResend(final HttpServletRequest httpRequest,
			final @RequestBody SanctionResendRequest sanctionResendRequest) {
		LOGGER.debug(SANCTION_RESEND_REQUEST_RECEIVED, sanctionResendRequest);

		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.SANCTION_RESEND, sanctionResendRequest.getOrgCode(),
				sanctionResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Update sanction.
	 *
	 * @param httpRequest           the http request
	 * @param sanctionUpdateRequest the sanction update request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Manually update a Sanction check status.\n\n", value = "Call this api to manually update a Sanction check status", nickname = "sanctionUpdateRequest")
	@ApiResponses(value = {
			@ApiResponse(response = SanctionUpdateResponse.class, code = 200, message = "A successful response is an instance of the SanctionUpdateResponse class.") })
	@PostMapping(value = "/updateSanction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateSanction(final HttpServletRequest httpRequest,
			@RequestBody final SanctionUpdateRequest sanctionUpdateRequest) {
		LOGGER.debug("Sanction UpdateRequest request received {}", sanctionUpdateRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		LOGGER.debug("User Information : {}", httpRequest.getHeader("user"));

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.SANCTION_UPDATE, sanctionUpdateRequest.getOrgCode(),
				sanctionUpdateRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds out sanction resend.
	 *
	 * @param sanctionResendRequest the sanction resend request
	 * @param httpRequest           the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Repeat a Funds-Out Sanction check.\n\n", value = "Call this api to repeat a Funds-Out Sanction check", nickname = "fundsOutSanctionResend")
	@ApiResponses(value = {
			@ApiResponse(response = SanctionResendResponse.class, code = 200, message = "A successful response is an instance of the SanctionResendResponse class.") })
	@PostMapping(value = "/fundsOutSanction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsOutSanctionResend(
			@RequestBody final FundsOutSanctionResendRequest sanctionResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(SANCTION_RESEND_REQUEST_RECEIVED, sanctionResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.SANCTION_RESEND, sanctionResendRequest.getOrgCode(),
				sanctionResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds in sanction resend.
	 *
	 * @param sanctionResendRequest the sanction resend request
	 * @param httpRequest           the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Repeat a Funds-In Sanction check.\n\n", value = "Call this api to repeat a Funds-In Sanction check", nickname = "fundsInSanctionResend")
	@ApiResponses(value = {
			@ApiResponse(response = SanctionResendResponse.class, code = 200, message = "A successful response is an instance of the SanctionResendResponse class.") })
	@PostMapping(value = "/fundsInSanction", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> fundsInSanctionResend(
			@RequestBody final FundsInSanctionResendRequest sanctionResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(SANCTION_RESEND_REQUEST_RECEIVED, sanctionResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSIN, OperationEnum.SANCTION_RESEND, sanctionResendRequest.getOrgCode(),
				sanctionResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Funds out sanction resend.
	 *
	 * @param sanctionResendRequest the sanction resend request
	 * @param httpRequest           the http request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Repeat a bulk Sanction check.\n\n", value = "Call this api to repeat a bulk Sanction check", nickname = "reprocessSanctionaFailed")
	@ApiResponses(value = {
			@ApiResponse(response = SanctionResendResponse.class, code = 200, message = "A successful response is an instance of the SanctionResendResponse class.") })
	@PostMapping(value = "/resendSanctionFailed", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> reprocessSanctionaFailed(
			@RequestBody final FundsOutSanctionResendRequest sanctionResendRequest,
			final HttpServletRequest httpRequest) {
		LOGGER.debug(SANCTION_RESEND_REQUEST_RECEIVED, sanctionResendRequest);

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.FUNDSOUT, OperationEnum.SANCTION_RESEND, sanctionResendRequest.getOrgCode(),
				sanctionResendRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername("cd.comp.system");

		return super.executeWorkflow(messageContext, userToken);
	}
}
