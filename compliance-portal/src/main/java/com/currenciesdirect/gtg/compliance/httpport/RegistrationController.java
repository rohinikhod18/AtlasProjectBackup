package com.currenciesdirect.gtg.compliance.httpport;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.IRegistrationService;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryRequest;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoRequest;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceRequest;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCfxDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueFilter;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class RegistrationController.
 */
/**
 * @author rajeshs
 *
 */
@Controller
public class RegistrationController extends BaseController {

	private static final String REGISTRATION_QUEUE_DTO = "registrationQueueDto";

  @Autowired
	@Qualifier("registrationServiceImpl")
	private IRegistrationService iRegistrationService;

	private Logger log = LoggerFactory.getLogger(RegistrationController.class);

	/**
	 * Gets the registration queue.
	 *
	 * @param user
	 *            the user
	 * @return the registration queue
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_QUQUE })
	@GetMapping(value = "/reg")
	public ModelAndView getRegistrationQueue(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;
		String jsonUser = JsonConverterUtil.convertToJsonWithNull(user);
		log.debug(jsonUser);
		try {
			RegistrationQueueFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), RegistrationQueueFilter.class);
			filter.setUserProfile(user);
			RegistrationSearchCriteria searchCriteria = new RegistrationSearchCriteria();
			searchCriteria.setFilter(filter);
			modelAndView.setViewName(ViewNameEnum.REGISTRATION_QUEUE.getViewName());
			registrationQueueDto = iRegistrationService.getRegistrationQueueWithCriteria(searchCriteria);
			registrationQueueDto.setUser(user);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}

	/**
	 * Gets the registration queue.
	 *
	 * @param user
	 *            the user
	 * @param searchCriteria
	 *            the search criteria
	 * @return the registration queue
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_QUQUE })
	@PostMapping(value = "/reg")
	public ModelAndView getRegistrationQueueWithCriteria(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;
		String jsonUser = JsonConverterUtil.convertToJsonWithNull(user);
		log.debug(jsonUser);
		try {

			RegistrationSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(RegistrationSearchCriteria.class, searchCriteria);
			if (searchCriteriaobj == null) {
				searchCriteriaobj = new RegistrationSearchCriteria();
				searchCriteriaobj.setFilter(new RegistrationQueueFilter());
			}
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			modelAndView.setViewName(ViewNameEnum.REGISTRATION_QUEUE.getViewName());
			registrationQueueDto = iRegistrationService.getRegistrationQueueWithCriteria(searchCriteriaobj);
			registrationQueueDto.setUser(user);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}

	/**
	 * Gets the registration queue.
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return the registration queue
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_QUQUE })
	@PostMapping(value = "/regQueue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public RegistrationQueueDto getRegistrationQueueWithCriteria(@RequestBody RegistrationSearchCriteria request,
			@RequestAttribute("user") UserProfile user) {
		RegistrationQueueDto registrationQueueDto = null;
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			registrationQueueDto = iRegistrationService.getRegistrationQueueWithCriteria(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		registrationQueueDto.setUser(user);
		return registrationQueueDto;
	}

	/**
	 * Gets the registration queue with criteria for dashboard.
	 *
	 * @param custType
	 *            the cust type
	 * @param user
	 *            the user
	 * @return the registration queue with criteria for dashboard
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/regQueueWithCriteria")
	public ModelAndView getRegistrationQueueWithCriteriaForDashboard(
			@RequestParam(value = "custType", required = false) String custType,
			@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;
		try {
			modelAndView.setViewName(ViewNameEnum.REGISTRATION_QUEUE.getViewName());
			RegistrationSearchCriteria searchCriteria = new RegistrationSearchCriteria();
			RegistrationQueueFilter queueFilter = new RegistrationQueueFilter();

			queueFilter.setCustType(new String[] { custType });
			searchCriteria.setFilter(queueFilter);
			searchCriteria.getFilter().setUserProfile(user);
			searchCriteria.setCustType(custType);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteria.getFilter(),
					user.getPermissions());
			registrationQueueDto = iRegistrationService.getRegistrationQueueWithCriteria(searchCriteria);
			registrationQueueDto.setUser(user);
			registrationQueueDto.setCustType(custType);
			registrationQueueDto.setIsDashboardSearchCriteria(Boolean.TRUE);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		return modelAndView;
	}

	/**
	 * Gets the registration details.
	 *
	 * @param contactId
	 *            the contact id
	 * @param custType
	 *            the cust type
	 * @param searchCriteria
	 *            the search criteria
	 * @param source
	 *            the source
	 * @param user
	 *            the user
	 * @return the registration details
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/registrationDetails")
	public ModelAndView getRegistrationDetails(@RequestParam(value = "contactId", required = false) Integer contactId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestParam(value = "source", required = false) String source,
			@RequestAttribute("user") UserProfile user) {

		ModelAndView modelAndView = new ModelAndView();
		IRegistrationDetails registrationDetailsDto;
		try {
			RegistrationSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(RegistrationSearchCriteria.class, searchCriteria);
			if (searchCriteriaobj == null) {
				searchCriteriaobj = new RegistrationSearchCriteria();
				searchCriteriaobj.setFilter(new RegistrationQueueFilter());
			}
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);

			if (("PFX").equals(custType)) {
				modelAndView.setViewName(ViewNameEnum.REGISTRATIN_DETAILS.getViewName());
			} else {
				modelAndView.setViewName(ViewNameEnum.CFX_REGISTRATION_DETAILS.getViewName());
			}
			if (isValidRequest(contactId, custType)) {
				registrationDetailsDto = iRegistrationService.getRegistrationDetails(contactId, custType,
						searchCriteriaobj);
				registrationDetailsDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteriaobj));
			} else {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
		} catch (CompliancePortalException e) {
			log.debug("", e);
			registrationDetailsDto = new RegistrationDetailsDto();
			registrationDetailsDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationDetailsDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		registrationDetailsDto.setIsPagenationRequired(Boolean.TRUE);
		registrationDetailsDto.setUser(user);
		registrationDetailsDto.setSource(source);
		modelAndView.addObject("regDetails", registrationDetailsDto);
		return modelAndView;
	}

	/**
	 * Update profile.
	 *
	 * @param profileUpdateRequest
	 *            the profile update request
	 * @param user
	 *            the user
	 * @return the string
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/profileUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ActivityLogs updateProfile(@RequestBody RegistrationUpdateRequest profileUpdateRequest,
			@RequestAttribute("user") UserProfile user) {
		profileUpdateRequest.setUser(user);
		ActivityLogs activityLogs = null;
		try {
			activityLogs = iRegistrationService.updateContact(profileUpdateRequest);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			activityLogs = new ActivityLogs();
			activityLogs.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			activityLogs.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		activityLogs.setUser(user);
		return activityLogs;
	}

	/**
	 * Lock Resource.
	 *
	 * @param lockResourceRequest
	 *            the lock resource request
	 * @param user
	 *            the user
	 * @return lockResourceResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/lockResource")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public LockResourceResponse lockResource(@RequestBody LockResourceRequest lockResourceRequest,
			@RequestAttribute("user") UserProfile user) {

		LockResourceResponse lockResourceResponse = null;
		try {
			lockResourceRequest.setUser(user);
			lockResourceResponse = iRegistrationService.lockResource(lockResourceRequest);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			lockResourceResponse = new LockResourceResponse();
			lockResourceResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			lockResourceResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return lockResourceResponse;
	}

	/**
	 * Lock resource list.
	 *
	 * @param lockResourceRequest
	 *            the lock resource request
	 * @param user
	 *            the user
	 * @return the lock resource response
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/lockResourceMultiContact")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public LockResourceResponse lockResourceList(@RequestBody LockResourceRequest lockResourceRequest,
			@RequestAttribute("user") UserProfile user) {

		LockResourceResponse lockResourceResponse = null;
		try {
			lockResourceRequest.setUser(user);
			lockResourceResponse = iRegistrationService.lockResourceMultiContacts(lockResourceRequest);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			lockResourceResponse = new LockResourceResponse();
			lockResourceResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			lockResourceResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return lockResourceResponse;
	}

	/**
	 * Gets the activites.
	 *
	 * @param activityLogRequest
	 *            the request
	 * @param user
	 *            the user
	 * @return the activites
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/regActivites")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ActivityLogs getActivites(@RequestBody ActivityLogRequest activityLogRequest,
			@RequestAttribute("user") UserProfile user) {

		ActivityLogs activityLogs = null;
		try {
			activityLogRequest.setUser(user);
			activityLogs = iRegistrationService.getActivityLogs(activityLogRequest);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			activityLogs = new ActivityLogs();
			activityLogs.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			activityLogs.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return activityLogs;
	}

	/**
	 * View More Details
	 * 
	 * @param viewMoreRequest
	 * @return viewMoreResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/viewMoreDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ViewMoreResponse viewMoreDetails(@RequestBody RegistrationViewMoreRequest viewMoreRequest) {

		ViewMoreResponse viewMoreResponse = null;
		try {
			viewMoreResponse = iRegistrationService.viewMoreDetails(viewMoreRequest);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			viewMoreResponse = new ViewMoreResponse();
			viewMoreResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			viewMoreResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return viewMoreResponse;
	}

	/**
	 * View More Details.
	 *
	 * @param providerResRequest
	 *            the request
	 * @return viewMoreResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/getProviderResponse")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ProviderResponseLogResponse getProviderResponse(@RequestBody ProviderResponseLogRequest providerResRequest) {

		ProviderResponseLogResponse providerResponseLogResponse = null;
		try {
			providerResponseLogResponse = iRegistrationService.getProviderResponse(providerResRequest);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			providerResponseLogResponse = new ProviderResponseLogResponse();
			providerResponseLogResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			providerResponseLogResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return providerResponseLogResponse;
	}

	/**
	 * Gets the registration details.
	 *
	 * @param contactId
	 *            the contact id
	 * @param custType
	 *            the cust type
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the registration details
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/registrationDashboardDetails")
	public ModelAndView getRegistrationDashboardDetails(
			@RequestParam(value = "contactId", required = false) Integer contactId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) {

		ModelAndView modelAndView = new ModelAndView();
		IRegistrationDetails registrationDetailsDto;

		try {
			
			RegistrationSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(RegistrationSearchCriteria.class, searchCriteria);
			if (searchCriteriaobj == null) {
				searchCriteriaobj = new RegistrationSearchCriteria();
				searchCriteriaobj.setFilter(new RegistrationQueueFilter());
			}
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			
			if (("PFX").equals(custType)) {
				modelAndView.setViewName(ViewNameEnum.REGISTRATIN_DETAILS.getViewName());
			} else {
				modelAndView.setViewName(ViewNameEnum.CFX_REGISTRATION_DETAILS.getViewName());
			}
			
			if (isValidRegDashboardRequest(contactId, custType)) {
				registrationDetailsDto = iRegistrationService.getRegistrationDetails(contactId, custType,searchCriteriaobj);
				registrationDetailsDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteriaobj));
			} else {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
		} catch (CompliancePortalException e) {
			log.debug("", e);
			registrationDetailsDto = new RegistrationDetailsDto();
			registrationDetailsDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationDetailsDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		if (registrationDetailsDto instanceof RegistrationCfxDetailsDto) {
			RegistrationCfxDetailsDto cfxDetailsDto = (RegistrationCfxDetailsDto) registrationDetailsDto;
			cfxDetailsDto.setIsPagenationRequired(Boolean.FALSE);
		} else {
			RegistrationDetailsDto cfxDetailsDto = (RegistrationDetailsDto) registrationDetailsDto;
			cfxDetailsDto.setIsPagenationRequired(Boolean.FALSE);
		}

		registrationDetailsDto.setUser(user);
		modelAndView.addObject("regDetails", registrationDetailsDto);
		return modelAndView;
	}

	private boolean isValidRequest(Integer contactId, String custType) {
		return (null != contactId && !isNullOrEmpty(custType));
	}

	private boolean isValidRegDashboardRequest(Integer contactId, String custType) {
		return (null != contactId && !isNullOrEmpty(custType));
	}

	private boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;

		return result;
	}

	/**
	 * View Device Info Details.
	 *
	 * @param deviceInfoRequest
	 *            the request
	 * @return viewMoreResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/getDeviceInfo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public DeviceInfoResponse getDeviceInfo(@RequestBody DeviceInfoRequest deviceInfoRequest) {

		DeviceInfoResponse deviceInfoResponse = null;
		try {
			deviceInfoResponse = iRegistrationService.getDeviceInfo(deviceInfoRequest);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			deviceInfoResponse = new DeviceInfoResponse();
			deviceInfoResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			deviceInfoResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return deviceInfoResponse;
	}

	/**
	 * View Account History Details.
	 *
	 * @param accountHistoryRequest
	 *            the request
	 * @return accountHistoryResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/getAccountHistory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public AccountHistoryResponse getAccountHistory(@RequestBody AccountHistoryRequest accountHistoryRequest) {

		AccountHistoryResponse accountHistoryResponse = null;
		try {
			accountHistoryResponse = iRegistrationService.getAccountHistory(accountHistoryRequest);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			accountHistoryResponse = new AccountHistoryResponse();
			accountHistoryResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			accountHistoryResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return accountHistoryResponse;
	}

	@PostMapping(value="/setPoiExistsFlag")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean setPoiExistsFlag(@RequestAttribute("user") UserProfile user,
			@RequestBody Contact contact) {
		boolean poiExists = false;
		try {
			poiExists = iRegistrationService.setPoiExistsFlag(user, contact);
		}
		catch(Exception e) {
			log.debug("Exception in RegistrationController in setPoiExistsFlag() {1}", e);
		}
		return poiExists;
	}
}