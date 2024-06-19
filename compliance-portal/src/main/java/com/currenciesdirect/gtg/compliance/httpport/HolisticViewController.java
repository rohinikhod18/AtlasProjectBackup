package com.currenciesdirect.gtg.compliance.httpport;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.IHolisticViewService;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueFilter;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class HolisticViewController.
 */
/**
 * @author laxmib
 *
 */
@Controller
public class HolisticViewController extends BaseController {
	
	/** The Constant HOLISTIC_ACCOUNT_RESPONSE. */
	private static final String HOLISTIC_ACCOUNT_RESPONSE = "holisticAccountResponse";

	/** The log. */
	private Logger log = LoggerFactory.getLogger(HolisticViewController.class);
	
	/**
	 * View HolisticView.
	 *
	 * @return holisticAccountResponse
	 */
	@Autowired
	@Qualifier("holisticViewServiceImpl")
	private IHolisticViewService iHolisticViewService;
	
	/**
	 * Gets the holistic view details.
	 *
	 * @param contactId the contact id
	 * @param custType the cust type
	 * @param searchCriteria the search criteria
	 * @param source the source
	 * @param accountId the account id
	 * @param user the user
	 * @return the holistic view details
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/getHolisticViewDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getHolisticViewDetails(@RequestParam(value = "contactId", required = false) Integer contactId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestParam(value = "source", required = false) String source,
			@RequestParam(value = "accountId", required = false) Integer accountId,
			@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		HolisticViewRequest holisticViewRequest = new HolisticViewRequest();
		HolisticViewResponse holisticAccountResponse = null;
		
			try {
				RegistrationSearchCriteria searchCriteriaobj = JsonConverterUtil
						.convertToObject(RegistrationSearchCriteria.class, searchCriteria);
				if(searchCriteriaobj == null){
					searchCriteriaobj = new RegistrationSearchCriteria();
					searchCriteriaobj.setFilter(new RegistrationQueueFilter());
				}
				UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
						user.getPermissions());
				searchCriteriaobj.getFilter().setUserProfile(user);
				
				holisticViewRequest.setAccountId(accountId);
				holisticViewRequest.setContactId(contactId);
				holisticViewRequest.setCustType(custType);
				holisticAccountResponse = iHolisticViewService.getHolisticViewDetails(holisticViewRequest);
				holisticAccountResponse.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteriaobj));
				modelAndView.addObject(HOLISTIC_ACCOUNT_RESPONSE, holisticAccountResponse);
				if("PFX".equals(custType))
					modelAndView.setViewName(ViewNameEnum.PFX_HOLISTIC_VIEW.getViewName());
				else 
					modelAndView.setViewName(ViewNameEnum.CFX_HOLISTIC_VIEW.getViewName());
			} catch (CompliancePortalException e) {
				log.debug("Exception in HolisticViewController class of getHolisticViewDetails() method " , e);
				holisticAccountResponse = new HolisticViewResponse();
				holisticAccountResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				holisticAccountResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
			}
		return modelAndView;
	}
	
	/**
	 * Gets the holistic view payment in details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @return the holistic view payment in details
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/getHolisticViewPaymentInDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getHolisticViewPaymentInDetails(@RequestBody HolisticViewRequest holisticViewRequest) {
		ModelAndView modelAndView = new ModelAndView();
		HolisticViewResponse holisticAccountResponse = null;
		
		try {				
				holisticAccountResponse = iHolisticViewService.getHolisticViewPaymentInDetails(holisticViewRequest);
				modelAndView.addObject(HOLISTIC_ACCOUNT_RESPONSE, holisticAccountResponse);
				modelAndView.setViewName(ViewNameEnum.HOLISTIC_FUNDS_IN_VIEW.getViewName());
		} catch (CompliancePortalException e) {
				log.debug("Exception in HolisticViewController class of getHolisticViewPaymentInDetails() method " , e);
				holisticAccountResponse = new HolisticViewResponse();
				holisticAccountResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				holisticAccountResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}
	
	/**
	 * Gets the holistic view payment out details.
	 *
	 * @param holisticViewRequest the holistic view request
	 * @return the holistic view payment out details
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_EID, 
			ViewControllerConstant.CAN_MANAGE_BLACKLIST, ViewControllerConstant.CAN_MANAGE_FRAUD,
			ViewControllerConstant.CAN_MANAGE_SANCTION }, allRequired = false)
	@PostMapping(value = "/getHolisticViewPaymentOutDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getHolisticViewPaymentOutDetails(@RequestBody HolisticViewRequest holisticViewRequest) {
		ModelAndView modelAndView = new ModelAndView();
		HolisticViewResponse holisticAccountResponse = null;
		
		try {				
				holisticAccountResponse = iHolisticViewService.getHolisticViewPaymentOutDetails(holisticViewRequest);
				modelAndView.addObject(HOLISTIC_ACCOUNT_RESPONSE, holisticAccountResponse);
				modelAndView.setViewName(ViewNameEnum.HOLISTIC_FUNDS_OUT_VIEW.getViewName());
		} catch (CompliancePortalException e) {
				log.debug("Exception in HolisticViewController class of getHolisticViewPaymentOutDetails() method " , e);
				holisticAccountResponse = new HolisticViewResponse();
				holisticAccountResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
				holisticAccountResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}

}