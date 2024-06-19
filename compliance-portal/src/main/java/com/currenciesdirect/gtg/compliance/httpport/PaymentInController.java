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
import com.currenciesdirect.gtg.compliance.core.IPaymentInService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateRequest;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class PaymentInController.
 */
@Controller
public class PaymentInController extends BaseController {

	@Autowired
	@Qualifier("paymentInServiceImpl")
	private IPaymentInService ipaymentInService;

	private Logger log = LoggerFactory.getLogger(PaymentInController.class);

	/**
	 * Gets the paymentIn queue .
	 *
	 * @param user
	 *            the user
	 * @return the paymentIn queue
	 */

	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_QUQUE })
	@GetMapping(value = "/paymentInQueue")
	public ModelAndView getPaymentInQueue(@RequestAttribute("user") UserProfile user) {

		ModelAndView modelAndView = new ModelAndView();
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		modelAndView.setViewName(ViewNameEnum.PAYMENT_IN_QUEUE.getViewName());
		try {
			PaymentInSearchCriteria searchCriteria = new PaymentInSearchCriteria();
			PaymentInFilter filter = UserPermissionFilterBuilder.getFilterFromUserPermission(user.getPermissions(),
					PaymentInFilter.class);
			filter.setUserProfile(user);
			searchCriteria.setFilter(filter);
			paymentInQueueDto = ipaymentInService.getPaymentInQueueWithCriteria(searchCriteria);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueue method paymentIncontroller class {1}", e);
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		modelAndView.addObject("paymentInQueueDto", paymentInQueueDto);
		return modelAndView;
	}
	
	/**
	 * Gets the payment in queue.
	 *
	 * @param user the user
	 * @param searchCriteria the search criteria
	 * @return the payment in queue
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_QUQUE })
	@PostMapping(value = "/paymentInQueue")
	public ModelAndView getPaymentInQueue(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {

		ModelAndView modelAndView = new ModelAndView();
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		modelAndView.setViewName(ViewNameEnum.PAYMENT_IN_QUEUE.getViewName());
		try {
			PaymentInSearchCriteria searchCriteriaobj = JsonConverterUtil.convertToObject(PaymentInSearchCriteria.class,
					searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			paymentInQueueDto = ipaymentInService.getPaymentInQueueWithCriteria(searchCriteriaobj);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueue method paymentIncontroller class {1}", e);
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		modelAndView.addObject("paymentInQueueDto", paymentInQueueDto);
		return modelAndView;
	}

	/**
	 * Gets the payment in queue with criteria.
	 *
	 * @param searchCriteria
	 *            the request
	 * @param user
	 *            the user
	 * @return the payment in queue with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_QUQUE })
	@PostMapping(value = "/payInQueue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public PaymentInQueueDto getPaymentInQueueWithCriteria(@RequestBody PaymentInSearchCriteria searchCriteria,
			@RequestAttribute("user") UserProfile user) {
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteria.getFilter(), user.getPermissions());
			searchCriteria.getFilter().setUserProfile(user);
			paymentInQueueDto = ipaymentInService.getPaymentInQueueWithCriteria(searchCriteria);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueueWithCriteria method paymentIncontroller class {1}",e);
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		return paymentInQueueDto;

	}

	/**
	 * Gets the payment in details.
	 *
	 * @param paymentInId            the payment in id
	 * @param custType            the cust type
	 * @param searchCriteria            the search criteria
	 * @param source the source
	 * @param user            the user
	 * @return the payment in details
	 * @throws CompliancePortalException             the compliance portal exception
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1, 
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM }, allRequired = false)
	@PostMapping(value = "/paymentInDetail")
	public ModelAndView getPaymentInDetails(@RequestParam(value = "paymentInId", required = false) Integer paymentInId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestParam(value = "source", required = false) String source,
			@RequestAttribute("user") UserProfile user) throws CompliancePortalException {

		ModelAndView modelAndView = new ModelAndView();

		if (("PFX").equals(custType)) {
			modelAndView.setViewName(ViewNameEnum.PAYMENT_IN_DETAILS.getViewName());
		} else {
			modelAndView.setViewName(ViewNameEnum.CFX_PAYMENT_IN_DETAILS.getViewName());
		}
		PaymentInSearchCriteria searchCriteriaobj = JsonConverterUtil.convertToObject(PaymentInSearchCriteria.class,
				searchCriteria);
		UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
				user.getPermissions());
		searchCriteriaobj.getFilter().setUserProfile(user);
		PaymentInDetailsDto paymentInDetailsDto;
		paymentInDetailsDto = ipaymentInService.getPaymentInDetails(paymentInId, custType, searchCriteriaobj);
		paymentInDetailsDto.setIsPagenationRequired(Boolean.TRUE);
		paymentInDetailsDto.setSearchCriteria(searchCriteria);
		paymentInDetailsDto.setUser(user);
		paymentInDetailsDto.setSource(source);
		modelAndView.addObject("paymentInDetails", paymentInDetailsDto);
		return modelAndView;
	}

	/**
	 * Update payment in.
	 *
	 * @param paymentInUpdateRequest
	 *            the payment in update request
	 * @param user
	 *            the user
	 * @return the activity logs
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1, 
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM }, allRequired = false)
	@PostMapping(value = "/paymentInUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ActivityLogs updatePaymentIn(@RequestBody PaymentInUpdateRequest paymentInUpdateRequest,
			@RequestAttribute("user") UserProfile user) {
		paymentInUpdateRequest.setUser(user);
		ActivityLogs activityLogs = null;
		try {
			paymentInUpdateRequest.setUser(user);
			activityLogs = ipaymentInService.updatePaymentIn(paymentInUpdateRequest);
		} catch (CompliancePortalException e) {
			log.error("Error in updatePaymentIn method of PaymentIncontroller class",e);
			activityLogs = new ActivityLogs();
			activityLogs.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			activityLogs.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		activityLogs.setUser(user);
		return activityLogs;
	}

	/**
	 * Gets the activites.
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return the activites
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1, 
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM }, allRequired = false)	
	@PostMapping(value = "/paymentInActivites")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ActivityLogs getActivites(@RequestBody ActivityLogRequest request,
			@RequestAttribute("user") UserProfile user) {

		ActivityLogs activityLogs = null;
		try {
			request.setUser(user);
			activityLogs = ipaymentInService.getActivityLogs(request);
		} catch (CompliancePortalException e) {
			log.error("Error in getActivites method of PaymentIncontroller class",e);
			activityLogs = new ActivityLogs();
			activityLogs.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			activityLogs.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return activityLogs;
	}

	/**
	 * View More Details.
	 *
	 * @param viewMoreRequest
	 *            the view more request
	 * @param user
	 *            the user
	 * @return viewMoreResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1, 
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM }, allRequired = false)
	@PostMapping(value = "/getPaymentInViewMoreDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ViewMoreResponse getPaymentInViewMoreDetails(@RequestBody PaymentInViewMoreRequest viewMoreRequest) {

		ViewMoreResponse viewMoreResponse = null;
		try {
			viewMoreResponse = ipaymentInService.viewMoreDetails(viewMoreRequest);
		} catch (CompliancePortalException e) {
			log.error("Error in getPaymentInViewMoreDetails method of PaymentIncontroller class",e);
			viewMoreResponse = new ViewMoreResponse();
			viewMoreResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			viewMoreResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return viewMoreResponse;
	}

	/**
	 * Gets the payment in dashboard details.
	 *
	 * @param paymentInId
	 *            the payment in id
	 * @param custType
	 *            the cust type
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the payment in dashboard details
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1, 
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM }, allRequired = false)
	@PostMapping(value = "/paymentInDashboardDetails")
	public ModelAndView getPaymentInDashboardDetails(
			@RequestParam(value = "paymentInId", required = false) Integer paymentInId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) throws CompliancePortalException {

		ModelAndView modelAndView = new ModelAndView();

		if (("PFX").equals(custType)) {
			modelAndView.setViewName(ViewNameEnum.PAYMENT_IN_DETAILS.getViewName());
		} else {
			modelAndView.setViewName(ViewNameEnum.CFX_PAYMENT_IN_DETAILS.getViewName());
		}
		
		PaymentInSearchCriteria searchCriteriaobj = JsonConverterUtil.convertToObject(PaymentInSearchCriteria.class,
				searchCriteria);
		if(searchCriteriaobj == null){
			   searchCriteriaobj = new PaymentInSearchCriteria();
			   searchCriteriaobj.setFilter(new PaymentInFilter());
		      }
		UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
				user.getPermissions());
		searchCriteriaobj.getFilter().setUserProfile(user);
		PaymentInDetailsDto paymentInDetailsDto;	
		paymentInDetailsDto = ipaymentInService.getPaymentInDetails(paymentInId, custType, searchCriteriaobj);
		paymentInDetailsDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteriaobj));
		paymentInDetailsDto.setIsPagenationRequired(Boolean.FALSE);
		paymentInDetailsDto.setUser(user);

		modelAndView.addObject("paymentInDetails", paymentInDetailsDto);

		return modelAndView;
	}
	
	@PostMapping(value="/setPoiExistsFlagForPaymentIn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean setPoiExistsFlagForPaymentIn(@RequestAttribute("user") UserProfile user,
			@RequestBody Contact contact) {
		boolean poiExists = false;
		try {
			poiExists = ipaymentInService.setPoiExistsFlagForPaymentIn(user, contact);
		}
		catch(Exception e) {
			log.debug("Exception in PaymentInController in setPoiExistsFlagForPaymentIn() {1}", e);
		}
		return poiExists;
	}
	
}