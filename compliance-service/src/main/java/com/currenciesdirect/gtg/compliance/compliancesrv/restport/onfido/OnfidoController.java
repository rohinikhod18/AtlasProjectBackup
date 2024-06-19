package com.currenciesdirect.gtg.compliance.compliancesrv.restport.onfido;

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

import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateResponse;
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

@Controller
public class OnfidoController extends BaseController {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OnfidoController.class);
	
	@ApiOperation(notes = "Manually update a Onfido check status.\n\n", value = "Call this api to manually update a Onfido check status", nickname = "sanctionUpdateRequest")
	@ApiResponses(value = {
			@ApiResponse(response = OnfidoUpdateResponse.class, code = 200, message = "A successful response is an instance of the OnfidoUpdateResponse class.") })
	@PostMapping(value = "/updateOnfido", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateOnfido(final HttpServletRequest httpRequest,
			@RequestBody final OnfidoUpdateRequest onfidoUpdateRequest) {
		
		LOGGER.debug("Sanction UpdateRequest request received {}", onfidoUpdateRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		LOGGER.debug("User Information : {}", httpRequest.getHeader("user"));

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.ONFIDO_STATUS_UPDATE, onfidoUpdateRequest.getOrgCode(),
				onfidoUpdateRequest);

		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}
	
}