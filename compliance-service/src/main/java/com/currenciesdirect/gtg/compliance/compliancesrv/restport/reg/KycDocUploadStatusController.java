package com.currenciesdirect.gtg.compliance.compliancesrv.restport.reg;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.docupload.DocumentUploadStatusResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.docupload.DocumentUploadStatusRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The Class KycDocUploadStatusController.
 * 
 * @author abhijitg
 */
@Controller
public class KycDocUploadStatusController extends BaseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(KycDocUploadStatusController.class);

	/**
	 * Document upload status.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param documentUploadStatusRequest
	 *            the document upload status request
	 * @return the deferred result
	 */
	//code commented for AT-5344
	/*@ApiOperation(notes = "Update Document upload status", 
			value = "Call this api to Update Document upload status", 
			nickname = "documentUploadStatus")
	@ApiResponses(value = { @ApiResponse(response = DocumentUploadStatusResponse.class, code = 200, message = "A successful response is an instance of the DocumentUploadStatusResponse class.") })
	@PostMapping(value = "/kycDocUploadStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> documentUploadStatus(final HttpServletRequest httpRequest,
			final @RequestBody DocumentUploadStatusRequest documentUploadStatusRequest,
			KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal) {
		LOGGER.warn("upload status request received {}", documentUploadStatusRequest);

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.DOCUMENT_UPLOAD, documentUploadStatusRequest.getOrgCode(),
				documentUploadStatusRequest);

		return super.executeWorkflow(messageContext, principal.getKeycloakSecurityContext().getToken());
	}*/
}
