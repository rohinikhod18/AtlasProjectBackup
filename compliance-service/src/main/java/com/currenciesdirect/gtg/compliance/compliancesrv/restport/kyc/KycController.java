package com.currenciesdirect.gtg.compliance.compliancesrv.restport.kyc;

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

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KycResendRequest;
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
 * The Class KycController.
 */
@Controller
@RequestMapping("/repeatcheck")
public class KycController extends BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KycController.class);

	/**
	 * Kyc resend.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param kycResendRequest
	 *            the kyc resend request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "KYC checks below attributes:\n\n" + " - Address\n" + " - Name\n" + " - DOB\n"
			+ " - POI (Proof of Identity)\n" + "\n"
			+ "This is sent to ATLAS which makes calls to the following external 3rd party service providers (to check the KYC Details):\n"
			+ " - GB Group (UK/Germany/Spain/Sweden/Norway)\n"
			+ " - Lexus Nexis (Austria/Australia/Canada/South Africa/New Zealand)\n" + "\n"
			+ "Depending on the results from these two services, the system calculates a score and if it is below the defined threshold it will Pass.", value = "Call this api to retry a KYC Electronic ID check for Registrations", nickname = "kycResend")
	@ApiResponses(value = {
			@ApiResponse(response = KYCResendResponse.class, code = 200, message = "A successful response is an instance of the KYCResendResponse class.") })
	@PostMapping(value = "/kyc", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> kycResend(final HttpServletRequest httpRequest,
			final @RequestBody KycResendRequest kycResendRequest) {
		LOGGER.warn("kycResendRequest request received {}", kycResendRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.KYC_RESEND, kycResendRequest.getOrgCode(),
				kycResendRequest);
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

}