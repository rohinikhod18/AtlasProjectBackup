package com.currenciesdirect.gtg.compliance.httpport.dataanonymisation;

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

import com.currenciesdirect.gtg.compliance.core.IAnonymisationService;
import com.currenciesdirect.gtg.compliance.core.IRegistrationService;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationFilter;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationResponse;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueFilter;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.httpport.BaseController;
import com.currenciesdirect.gtg.compliance.httpport.ViewControllerConstant;
import com.currenciesdirect.gtg.compliance.httpport.ViewNameEnum;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class DataAnonymisationController.
 * 
 * @author deepakk
 */
@Controller
public class DataAnonymisationController extends BaseController {
	
	private static final String DATA_ANONYMISATION_DTO = "dataAnonymisationDto";
	
	@Autowired
	@Qualifier("dataAnonymisationServiceImpl")
	private IAnonymisationService iAnonymisationService;
	
	@Autowired
	@Qualifier("registrationServiceImpl")
	private IRegistrationService iRegistrationService;
	
	
	private Logger log = LoggerFactory.getLogger(DataAnonymisationController.class);
	
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_APPROVE_DATA_ANON})
	@GetMapping(value = "/dataAnon")
	public ModelAndView getDataAnonymisationQueue(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		DataAnonymisationDto dataAnonymisationDto = null;
		String jsonUser = JsonConverterUtil.convertToJsonWithNull(user);
		log.debug(jsonUser);
		try {
			DataAnonymisationFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), DataAnonymisationFilter.class);
			filter.setUserProfile(user);
			DataAnonymisationSearchCriteria searchCriteria = new DataAnonymisationSearchCriteria();
			searchCriteria.setFilter(filter);
			modelAndView.setViewName(ViewNameEnum.DATA_ANONYMISATION_QUEUE.getViewName());
			dataAnonymisationDto = iAnonymisationService.getDataAnonymisationWithCriteria(searchCriteria);
			dataAnonymisationDto.setUser(user);
			modelAndView.addObject(DATA_ANONYMISATION_DTO, dataAnonymisationDto);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			dataAnonymisationDto = new DataAnonymisationDto();
			dataAnonymisationDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			dataAnonymisationDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}
	
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/dataAnonDetails")
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
	
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_QUQUE })
	@PostMapping(value = "/dataAnonQueue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public DataAnonymisationDto getDataAnonymisationWithCriteria(@RequestBody DataAnonymisationSearchCriteria request,
			@RequestAttribute("user") UserProfile user) {
		DataAnonymisationDto dataAnonymisationDto = null;
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			dataAnonymisationDto = iAnonymisationService.getDataAnonymisationWithCriteria(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			dataAnonymisationDto = new DataAnonymisationDto();
			dataAnonymisationDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			dataAnonymisationDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		dataAnonymisationDto.setUser(user);
		return dataAnonymisationDto;
	}
	
	private boolean isValidRequest(Integer contactId, String custType) {
		return (null != contactId && !isNullOrEmpty(custType));
	}
	
	private boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;

		return result;
	}
	
	@PostMapping(value="/getDataAnonymize")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean getDataAnonymize(@RequestAttribute("user") UserProfile user,
			@RequestBody DataAnonymisationDataRequest dataAnonymizeRequest) {
		boolean dataAnon = false;
		try {
			dataAnon = iAnonymisationService.getDataAnonymize(user, dataAnonymizeRequest);
		}
		catch(Exception e) {
			log.debug("Exception in getDataAnonymize in DataAnonymisationController {1}", e);
		}
		return dataAnon;
	}


	@PostMapping(value="/updateDataAnon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public DataAnonymisationResponse updateDataAnonymisation(@RequestAttribute("user") UserProfile user,
			@RequestBody DataAnonymisationDataRequest request) {
		
		DataAnonymisationResponse dataAnonymisationResponce = new DataAnonymisationResponse();
		try {
			dataAnonymisationResponce = iAnonymisationService.updateDataAnonymisation(user, request);
		}
		catch(Exception e) {
			log.error("Exception in updateDataAnonymisation in DataAnonymisationController {1}", e);
		}
		return dataAnonymisationResponce;
	}

	

	@PostMapping(value="/cancelDataAnon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean cancelDataAnonymisation(@RequestAttribute("user") UserProfile user,
			@RequestBody DataAnonymisationDataRequest request) {
		
		boolean dataAnon=false;
		try {
			dataAnon = iAnonymisationService.cancelDataAnonymisation(user, request);
		}
		catch(Exception e) {
			log.error("Exception in cancelDataAnonymisation in DataAnonymisationController {1}", e);
		}
		return dataAnon;
	}
	
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_APPROVE_DATA_ANON})
	@PostMapping(value = "/getDataAnonHistory")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public DataAnonymisationDto getDataAnonHistory(@RequestBody DataAnonymisationDataRequest request) {

		DataAnonymisationDto dataAnonymisationDto = null;
		try {
			dataAnonymisationDto = iAnonymisationService.getDataAnonHistory(request);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			dataAnonymisationDto = new DataAnonymisationDto();
			dataAnonymisationDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			dataAnonymisationDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return dataAnonymisationDto;
	}
}
