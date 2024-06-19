package com.currenciesdirect.gtg.compliance.compliancesrv.restport.dataanonymization;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonResponseForES;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization.IDataAnonService;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class DataAnonController extends BaseController{
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(DataAnonController.class);
	
	@Autowired
	@Qualifier("dataAnonServiceImpl")
	private IDataAnonService iDataAnonService;
	
	/**
	 * Gets the customer anonymization data.
	 *
	 * @param dataAnonRequestFromES the data anon request from ES
	 * @return the customer anonymization data
	 */
	@ApiOperation(notes = "Enterprise will call this API to update data fields in Atlas\n\n", value = "Enterprise will call this API to update data fields in Atlas\\n\\n", nickname = "dataAnonRequestFromES")
	@ApiResponses(value = {
			@ApiResponse(response = DataAnonResponseForES.class, code = 200, message = "A successful response is an instance of the DataAnonResponseForES class.") })
	@PostMapping(value = "/getCustomerAnonymizationData", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> getCustomerAnonymizationData(final HttpServletRequest httpRequest,
			@RequestBody final DataAnonRequestFromES dataAnonRequestFromES) {
		
		String jsonRequest = JsonConverterUtil.convertToJsonWithNull(dataAnonRequestFromES);
		LOG.warn("\n -------Data Anon Request ES to Atlas Start -------- \n  {}", jsonRequest);
		LOG.warn(" \n -----------Data Anon Request ES to Atlas End ---------");
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.DATA_ANONYMIZE, dataAnonRequestFromES.getOrgCode(),
				dataAnonRequestFromES);
		
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

}
