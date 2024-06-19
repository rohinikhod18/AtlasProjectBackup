package com.currenciesdirect.gtg.compliance.compliancesrv.restport.reg;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate.StatusUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.AddContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.SignUpRequestWrapper;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.UpdateProfileRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact.DeleteContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ProfileResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.restport.BaseController;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.ParseSSNNumber;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class RegistrationController.
 */
@Controller
@EnableSwagger2
@EnableWebMvc  
@Api(value = "/")   
@RequestMapping("profile")
public class RegistrationController extends BaseController {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationController.class);

	/**
	 * New reg.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param signUpRequestWrapper
	 *            the sign up request wrapper
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Called when SalesForce sends a SignUP request", value = "Called when SalesForce sends a SignUP request", nickname = "Sign-Up")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = RegistrationResponse.class, code = 200, message = "A successful response is an instance of the RegistrationResponse class.") })
	@PostMapping(value = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> newReg(final HttpServletRequest httpRequest,
			@ApiParam(value = "Sign-Up request JSON data", required = true) final @RequestBody SignUpRequestWrapper signUpRequestWrapper) {
		
		ParseSSNNumber.getInstance().parseSsnNumber(signUpRequestWrapper.getSignupRequest());//AT-3661
		
		String jsonSignupRequest = JsonConverterUtil.convertToJsonWithoutNull(signUpRequestWrapper);
		LOGGER.warn("\n -------Signup Request Start -------- \n  {}", jsonSignupRequest);
		LOGGER.warn(" \n -----------Signup Request End ---------");
		RegistrationServiceRequest registrationServiceRequest = convertToRegistrationRequest(signUpRequestWrapper);
		setCitytoRegionforZAF(registrationServiceRequest.getAccount());
		
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.NEW_REGISTRATION,
				signUpRequestWrapper.getSignupRequest().getOrgCode(), registrationServiceRequest);
		
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());	

		DeferredResult<ResponseEntity> deferredResult;
			deferredResult = super.executeWorkflow(messageContext, userToken);
			
		return deferredResult;
	}

	// Changes made for AT-459.
	// For country_of_residence South Africa if region field does not come then
	// we set city in it.
	/**
	 * Sets the cityto regionfor ZAF.
	 *
	 * @param account
	 *            the new cityto regionfor ZAF
	 */
	// Changes done by Tejas I
	private void setCitytoRegionforZAF(Account account) {
		for (com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact contact : account.getContacts()) {
			if ((Constants.ZAF).equalsIgnoreCase(contact.getCountry())
					&& StringUtils.isNullOrEmpty(contact.getRegion())) {
				contact.setRegion(contact.getCity());
			}
		}

	}

	/**
	 * Adds the contact.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param addContactRequest
	 *            the add contact request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Called when SalesForce sends an Add Contact", value = "Called when SalesForce sends an Add Contact", nickname = "Add Contact")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = RegistrationResponse.class, code = 200, message = "A successful response is an instance of the RegistrationResponse class.") })
	@PostMapping(value = "/addContact", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> addContact(final HttpServletRequest httpRequest,
			@ApiParam(value = "Add Contact request JSON data", required = true) final @RequestBody AddContactRequest addContactRequest) {
		
		ParseSSNNumber.getInstance().parseSsnNumberForAddContact(addContactRequest);//AT-3661
		
		String jsonAddContactRequest = JsonConverterUtil.convertToJsonWithoutNull(addContactRequest);
		LOGGER.warn("\n -------addContact Request Start -------- \n  {}", jsonAddContactRequest);
		LOGGER.warn(" \n -----------addContact Request End ---------");
		// Since add Contact has same flow as New Registration
		// operation is used same as above
		RegistrationServiceRequest request = mapToRegistrationRequest(addContactRequest);
		setCitytoRegionforZAF(request.getAccount());
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.ADD_CONTACT, addContactRequest.getOrgCode(), request);

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

	/**
	 * Update reg.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param updateProfileReuqest
	 *            the update profile reuqest
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Called when SF sends an Update request for registrations ", value = "Call this api at the time of updating profile", nickname = "Update Profile")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = ProfileResponse.class, code = 200, message = "A successful response is an instance of the BlacklistResendResponse class.") })
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateReg(final HttpServletRequest httpRequest,
			@ApiParam(value = "Update Profile request JSON data", required = true) final @RequestBody UpdateProfileRequest updateProfileReuqest) {
		
		ParseSSNNumber.getInstance().parseSsnNumberForUpdate(updateProfileReuqest.getAccount());//AT-3661
		
		String jsonUpdateProfileRequest = JsonConverterUtil.convertToJsonWithoutNull(updateProfileReuqest);
		LOGGER.warn("\n -------Update Request Start -------- \n  {}", jsonUpdateProfileRequest);
		LOGGER.warn(" \n -----------Update Request End ---------");
		RegistrationServiceRequest request = convertToRegistrationRequestForUpdate(updateProfileReuqest);
		if (null != request.getAccount()) {
			setCitytoRegionforZAF(request.getAccount());
		}
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.UPDATE_ACCOUNT, updateProfileReuqest.getOrgCode(), request);

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

	/**
	 * Convert to registration request.
	 *
	 * @param request
	 *            the request
	 * @return the registration service request
	 */
	private RegistrationServiceRequest convertToRegistrationRequest(SignUpRequestWrapper request) {
		RegistrationServiceRequest registrationServiceRequest;
		String json = JsonConverterUtil.convertToJsonWithoutNull(request.getSignupRequest());
		registrationServiceRequest = JsonConverterUtil.convertToObject(RegistrationServiceRequest.class, json);
		registrationServiceRequest.getAccount().setConversionPrediction(request.getConversionPrediction());
		return registrationServiceRequest;
	}

	/**
	 * Convert to registration request for update.
	 *
	 * @param request
	 *            the request
	 * @return the registration service request
	 */
	private RegistrationServiceRequest convertToRegistrationRequestForUpdate(Object request) {
		String json = JsonConverterUtil.convertToJsonWithoutNull(request);
		return JsonConverterUtil.convertToObject(RegistrationServiceRequest.class, json);
	}

	/**
	 * Map to registration request.
	 *
	 * @param aReq
	 *            the a req
	 * @return the registration service request
	 */
	private RegistrationServiceRequest mapToRegistrationRequest(AddContactRequest aReq) {
		RegistrationServiceRequest rReq = new RegistrationServiceRequest();
		rReq.setOrgCode(aReq.getOrgCode());
		rReq.setSourceApplication(aReq.getSourceApplication());
		rReq.setIsBroadCastRequired(aReq.getIsBroadCastRequired());
		Account acc = new Account();
		acc.setAccSFID(aReq.getAccSFID());
		acc.setCustType(aReq.getCustType());
		List<com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact> list = new ArrayList<>();
		for (Contact c : aReq.getContacts()) {
			String cJson = JsonConverterUtil.convertToJsonWithoutNull(c);
			com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact regCon = JsonConverterUtil
					.convertToObject(com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact.class, cJson);
			list.add(regCon);
		}
		acc.setContacts(list);
		rReq.setAccount(acc);

		String deviceInfoJson = JsonConverterUtil.convertToJsonWithoutNull(aReq.getDeviceInfo());
		DeviceInfo deviceInfo = JsonConverterUtil.convertToObject(DeviceInfo.class, deviceInfoJson);

		rReq.setDeviceInfo(deviceInfo);
		return rReq;
	}

	/**
	 * Repeat check failed reg.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param regRecheckFailureDetailsRequest
	 *            the reg recheck failure details request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "This is called when we have have service failure for checks and we want to perform "
			+ "a bulk repeat check for failed registrations from the Compliance portal\n\n", value = "Recheck failure details request", nickname = "repeatCheckFailedReg", response = RegistrationResponse.class)
	@ApiResponses(value = {
			@ApiResponse(response = RegistrationResponse.class, code = 200, message = "A successful response is an instance of the RegistrationResponse class.") })
	@PostMapping(value = "/repeatCheckFailedReg", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> repeatCheckFailedReg(final HttpServletRequest httpRequest,
			@ApiParam(value = "Repeat Check Failed Registration request JSON data", required = true) final @RequestBody ReprocessFailedDetails regRecheckFailureDetailsRequest) {
		String jsonRegRecheckDetailsRequest = JsonConverterUtil
				.convertToJsonWithoutNull(regRecheckFailureDetailsRequest);
		LOGGER.warn("\n -------Repeat Check Failed Registration Request Start -------- \n  {}",
				jsonRegRecheckDetailsRequest);
		LOGGER.warn(" \n -----------Repeat Check Failed Registration Request End ---------");
		LOGGER.warn(" \n ----- scanActiveCustomerForBRC ------ {}",Boolean.parseBoolean(System.getProperty("scanActiveCustomerForBRC")));
		LOGGER.warn(" \n ----- persistActiveAccountStatusPostBRC ------ {}",Boolean.parseBoolean(System.getProperty("persistActiveAccountStatusPostBRC")));
		LOGGER.warn(" \n ----- persistInactiveAccountStatusPostBRC ------ {}",Boolean.parseBoolean(System.getProperty("persistInactiveAccountStatusPostBRC")));
		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.RECHECK_FAILURES, null, regRecheckFailureDetailsRequest);
		UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
		AccessToken userToken = new AccessToken();
		userToken.setPreferredUsername(user.getName());

		return super.executeWorkflow(messageContext, userToken);
	}

	/**
	 * Update profile status.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param statusUpdateRequest
	 *            the status update request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Perform a manual profile status update.\n\n", value = "Call this api to manually update a profile status", nickname = "updateProfileStatus")
	@ApiResponses(value = {
			@ApiResponse(response = StatusUpdateResponse.class, code = 200, message = "A successful response is an instance of the StatusUpdateResponse class.") })
	@PostMapping(value = "/updateProfileStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> updateProfileStatus(final HttpServletRequest httpRequest,
			@ApiParam(value = "Update profie status request json data", required = true) final @RequestBody StatusUpdateRequest statusUpdateRequest) {
		String jsonRequestBody;
		String jsonResponsebody;

		jsonRequestBody = JsonConverterUtil.convertToJsonWithoutNull(statusUpdateRequest);
		LOGGER.warn("\n-----------Update Profile Status Request Start--------\n{}", jsonRequestBody);
		LOGGER.warn("\n-----------Update Profile Status Request END----------");

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.STATUS_UPDATE, statusUpdateRequest.getOrgCode(),
				statusUpdateRequest);

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

			jsonResponsebody = JsonConverterUtil.convertToJsonWithoutNull(responseEntity.getBody());
			LOGGER.warn("\n-------------Update Profile Status Response--------\n{}", jsonResponsebody);
		}
		return deferredResult;
	}

	/**
	 * Delete contact.
	 *
	 * @param httpRequest
	 *            the http request
	 * @param deleteContactRequest
	 *            the delete contact request
	 * @return the deferred result
	 */
	@ApiOperation(notes = "Called when SalesForce sends a Delete Contact", value = "Called when SalesForce sends a Delete Contact", nickname = "deleteContact")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Add bearer token", defaultValue = "Bearer <<AbCdEf123456>>", required = true, dataType = "string", paramType = "header") })
	@RolesAllowed("compliance_api_access")
	@ApiResponses(value = {
			@ApiResponse(response = RegistrationResponse.class, code = 200, message = "A successful response is an instance of the RegistrationResponse class.") })
	@PostMapping(value = "/deleteContact", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public DeferredResult<ResponseEntity> deleteContact(final HttpServletRequest httpRequest,
			@ApiParam(value = "delete contact request JSON data", required = true) final @RequestBody DeleteContactRequest deleteContactRequest) {
		String jsonContactDeleteRequest = JsonConverterUtil.convertToJsonWithoutNull(deleteContactRequest);
		LOGGER.warn("\n ------- Delete Contact Request Start -------- \n  {}", jsonContactDeleteRequest);
		LOGGER.warn(" \n ----------- Delete Contact Request End ---------");

		MessageContext messageContext = buildMessageContext(httpRequest, ServiceTypeEnum.GATEWAY_SERVICE,
				ServiceInterfaceType.PROFILE, OperationEnum.DELETE_CONTACT, deleteContactRequest.getOrgCode(),
				deleteContactRequest);

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