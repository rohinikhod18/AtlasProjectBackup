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
import com.currenciesdirect.gtg.compliance.core.IPaymentOutService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutFilter;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RecentPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class PaymentOutController.
 */
@Controller
public class PaymentOutController extends BaseController {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(PaymentOutController.class);

	/** The i payment out service. */
	@Autowired
	@Qualifier("paymentOutServiceImpl")
	private IPaymentOutService iPaymentOutService;

	/**
	 * Gets the payment out details.
	 *
	 * @param paymentOutId            the payment out id
	 * @param custType            the cust type
	 * @param searchCriteria            the search criteria
	 * @param source the source
	 * @param user            the user
	 * @return the payment out details
	 * @throws CompliancePortalException             the compliance portal exception
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2 }, allRequired = false)
	@PostMapping(value = "/paymentOutDetail")
	public ModelAndView getPaymentOutDetails(
			@RequestParam(value = "paymentOutId", required = false) Integer paymentOutId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestParam(value = "source", required = false) String source,
			@RequestAttribute("user") UserProfile user) throws CompliancePortalException {
		ModelAndView modelAndView = new ModelAndView();
		PaymentOutDetailsDto paymentOutDetailDto;
		String jsonPaymentOutDetailDto;
  try {
		  	  PaymentOutSearchCriteria searchCriteriaobj = JsonConverterUtil.convertToObject(PaymentOutSearchCriteria.class, searchCriteria);
		      if(searchCriteriaobj == null){
			   searchCriteriaobj = new PaymentOutSearchCriteria();
			   searchCriteriaobj.setFilter(new PaymentOutFilter());
		      }
		     UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
				user.getPermissions());
		     searchCriteriaobj.getFilter().setUserProfile(user);
		
		     if ((CustomerTypeEnum.PFX.getUiStatus()).equals(custType)||("2").equals(custType)) {
	  		       modelAndView.setViewName(ViewNameEnum.PAYMENT_OUT_DETAILS.getViewName());
	          } else {
		           modelAndView.setViewName(ViewNameEnum.CFX_PAYMENT_OUT_DETAILS.getViewName());
	          }
		     if (isValidRequest(paymentOutId, custType)) {
			      paymentOutDetailDto = iPaymentOutService.getPaymentOutDetails(paymentOutId, custType, searchCriteriaobj);  //custType added for AT-2986
			      paymentOutDetailDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteriaobj));
			     
		      } else {
			    throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
		      }
		   }
	catch (CompliancePortalException e) {
			log.debug("" , e);
			paymentOutDetailDto = new PaymentOutDetailsDto();
			paymentOutDetailDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutDetailDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		paymentOutDetailDto.setIsPagenationRequired(Boolean.TRUE);
		paymentOutDetailDto.setUser(user);
		paymentOutDetailDto.setSource(source);
		jsonPaymentOutDetailDto = JsonConverterUtil.convertToJsonWithNull(paymentOutDetailDto);
		log.debug(jsonPaymentOutDetailDto);
		modelAndView.addObject("paymentOutDetails", paymentOutDetailDto);
		return modelAndView;
	}
	
 /**
  * This method returns most recent payment made for beneficiary.
  * 1.Takes trade account number and beneficiary account number as input.
  * 2.searches most recent payment made for beneficiary.
  * 3.Returns payment out details made for beneficiary
  * 
  * API added for AT-1277
  *
  * @param tradeAccountNumber the trade account number
  * @param beneAccountNumber the bene account number
  * @param searchCriteria the search criteria
  * @param source the source
  * @param user the user
  * @return the most recent payment out details
  * @throws CompliancePortalException the compliance portal exception
  */
@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
		ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION,
		ViewControllerConstant.CAN_MANAGE_CUSTOM, 
		ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1,
		ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2 }, allRequired = false)
             @PostMapping(value = "/mostRecentPaymentOutDetails")			
             public ModelAndView getMostRecentPaymentOutDetails(
					@RequestParam(value = "tradeId", required = false) String tradeAccountNumber,
					@RequestParam(value = "accountNumber", required = false) String beneAccountNumber,
					@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
					@RequestParam(value = "source", required = false) String source,
					@RequestAttribute("user") UserProfile user) throws CompliancePortalException {
	   
	            ModelAndView modelAndView;
		        RecentPaymentOutDetails paymentOutDetail;
		        paymentOutDetail = iPaymentOutService.getMostRecentPaymentOutId(tradeAccountNumber,beneAccountNumber);
		        modelAndView = getPaymentOutDetails(paymentOutDetail.getPaymentId(),paymentOutDetail.getCustType(),searchCriteria,source,user); 
		        return modelAndView;
	}	
   
   /**
    * Checks if is valid request.
    *
    * @param contactId the contact id
    * @param custType the cust type
    * @return true, if is valid request
    */
   private boolean isValidRequest(Integer contactId, String custType) {
	   return (null != contactId && isNullOrEmpty(custType));
   }
   
   /**
    * Checks if is null or empty.
    *
    * @param str the str
    * @return true, if is null or empty
    */
   private boolean isNullOrEmpty(String str) {
		return (str != null && !str.isEmpty());
	}
	
  	    
	/**
	 * Gets the payment out queue.
	 *
	 * @param user
	 *            the user
	 * @return the registration queue
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_QUQUE })
	@GetMapping(value = "/payOutQueue")
	public ModelAndView getPaymentOutQueue(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		PaymentOutQueueDto paymentOutQueueDto;
		modelAndView.setViewName(ViewNameEnum.PAYMENT_OUT_QUEUE.getViewName());
		try {
			PaymentOutSearchCriteria searchCriteria = new PaymentOutSearchCriteria();
			PaymentOutFilter filter = UserPermissionFilterBuilder
			.getFilterFromUserPermission(user.getPermissions(), PaymentOutFilter.class);
			filter.setUserProfile(user);
			searchCriteria.setFilter(filter);
			paymentOutQueueDto = iPaymentOutService.getPaymentOutQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			paymentOutQueueDto = new PaymentOutQueueDto();
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		paymentOutQueueDto.setUser(user);
		modelAndView.addObject("paymentOutQueueDto", paymentOutQueueDto);
		return modelAndView;
	}

	/**
	 * Gets the payment out queue with criteria .
	 *
	 * @param searchCriteria
	 *            the request
	 * @param user
	 *            the user
	 * @return the payment out queue with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_QUQUE })
	@PostMapping(value = "/paymentOutQueue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public PaymentOutQueueDto getPaymentOutQueueWithCriteria(@RequestBody PaymentOutSearchCriteria searchCriteria,
			@RequestAttribute("user") UserProfile user) {
		PaymentOutQueueDto paymentOutQueueDto = null;
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteria.getFilter(),
					user.getPermissions());
			searchCriteria.getFilter().setUserProfile(user);
			paymentOutQueueDto = iPaymentOutService.getPaymentOutQueueWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			log.debug("" , e);
			paymentOutQueueDto = new PaymentOutQueueDto();
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
			paymentOutQueueDto.setUser(user);
		}
		paymentOutQueueDto.setUser(user);
		return paymentOutQueueDto;
	}
	
	/**
	 * Gets the payment out queue with criteria.
	 *
	 * @param user the user
	 * @param searchCriteria the search criteria
	 * @return the payment out queue with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_QUQUE })
	@PostMapping(value = "/payOutQueue")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getPaymentOutQueueWithCriteria(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {
		ModelAndView modelAndView = new ModelAndView();
		PaymentOutQueueDto paymentOutQueueDto;
		modelAndView.setViewName(ViewNameEnum.PAYMENT_OUT_QUEUE.getViewName());
		try {
			PaymentOutSearchCriteria searchCriteriaobj = JsonConverterUtil.convertToObject(PaymentOutSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			
			paymentOutQueueDto = iPaymentOutService.getPaymentOutQueueWithCriteria(searchCriteriaobj);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			paymentOutQueueDto = new PaymentOutQueueDto();
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		paymentOutQueueDto.setUser(user);
		modelAndView.addObject("paymentOutQueueDto", paymentOutQueueDto);
		return modelAndView;
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
			ViewControllerConstant.CAN_MANAGE_CUSTOM,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2 }, allRequired = false)
	@PostMapping(value = "/paymentOutActivites")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ActivityLogs getActivites(@RequestBody ActivityLogRequest request,
			@RequestAttribute("user") UserProfile user) {

		ActivityLogs activityLogs = new ActivityLogs();
		try {
			request.setUser(user);
			activityLogs = iPaymentOutService.getActivityLogs(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
			activityLogs.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			activityLogs.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return activityLogs;
	}

	/**
	 * Update PaymentOut.
	 *
	 * @param paymentOutUpdateRequest
	 *            the payment out update request
	 * @param user
	 *            the user
	 * @return the activity logs
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_CUSTOM,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2 }, allRequired = false)
	@PostMapping(value = "/paymentOutUpdate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ActivityLogs updatePaymentOut(@RequestBody PaymentOutUpdateRequest paymentOutUpdateRequest,
			@RequestAttribute("user") UserProfile user) {
		paymentOutUpdateRequest.setUser(user);
		ActivityLogs activityLogs = new ActivityLogs();
		try {
			paymentOutUpdateRequest.setUser(user);
			activityLogs = iPaymentOutService.updatePaymentOut(paymentOutUpdateRequest);
		} catch (CompliancePortalException e) {
			log.debug("" , e);
			activityLogs.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			activityLogs.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		activityLogs.setUser(user);
		return activityLogs;
	}

	/**
	 * View More Details.
	 *
	 * @param viewMoreRequest            the view more request
	 * @return viewMoreResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_CUSTOM,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2 }, allRequired = false)
	@PostMapping(value = "/getPaymentOutViewMoreDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ViewMoreResponse getPaymentOutViewMoreDetails(@RequestBody PaymentOutViewMoreRequest viewMoreRequest) {

		ViewMoreResponse viewMoreResponse = null;
		try {
			viewMoreResponse = iPaymentOutService.viewMoreDetails(viewMoreRequest);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			viewMoreResponse = new ViewMoreResponse();
			viewMoreResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			viewMoreResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return viewMoreResponse;
	}

	/**
	 * Gets the Further PaymentOut Details.
	 *
	 * @param viewMoreRequest            the view more request
	 * @return viewMoreResponse
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION, 
			ViewControllerConstant.CAN_MANAGE_CUSTOM,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2 }, allRequired = false)
	@PostMapping(value = "/getFurtherPaymentOutDetails")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ViewMoreResponse getFurtherPaymentOutViewMoreDetails(
			@RequestBody PaymentOutViewMoreRequest viewMoreRequest) {

		ViewMoreResponse viewMoreResponse = null;
		try {
			viewMoreResponse = iPaymentOutService.getFurtherPaymentOutDetails(viewMoreRequest);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			viewMoreResponse = new ViewMoreResponse();
			viewMoreResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			viewMoreResponse.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return viewMoreResponse;
	}

	/**
	 * Gets the payment out dashboard details.
	 *
	 * @param paymentOutId
	 *            the payment out id
	 * @param custType
	 *            the cust type
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the payment out dashboard details
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BLACKLIST, 
			ViewControllerConstant.CAN_MANAGE_FRAUD, ViewControllerConstant.CAN_MANAGE_SANCTION,
			ViewControllerConstant.CAN_MANAGE_CUSTOM,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_1 ,
			ViewControllerConstant.CAN_MANAGE_WATCHLIST_CATEGORY_2}, allRequired = false)
	@PostMapping(value = "/paymentOutDashboardDetails")
	public ModelAndView getPaymentOutDashboardDetails(
			@RequestParam(value = "paymentOutId", required = false) Integer paymentOutId,
			@RequestParam(value = "custType", required = false) String custType,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) throws CompliancePortalException {

		ModelAndView modelAndView = new ModelAndView();

		PaymentOutSearchCriteria searchCriteriaobj = JsonConverterUtil.convertToObject(PaymentOutSearchCriteria.class, searchCriteria);
	      if(searchCriteriaobj == null){
		   searchCriteriaobj = new PaymentOutSearchCriteria();
		   searchCriteriaobj.setFilter(new PaymentOutFilter());
	      }
	   UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
			user.getPermissions());
	        searchCriteriaobj.getFilter().setUserProfile(user);

		if (("PFX").equals(custType)) {
			modelAndView.setViewName(ViewNameEnum.PAYMENT_OUT_DETAILS.getViewName());
		} else {
			modelAndView.setViewName(ViewNameEnum.CFX_PAYMENT_OUT_DETAILS.getViewName());
		}
		
		PaymentOutDetailsDto paymentOutDetailDto;
		paymentOutDetailDto = iPaymentOutService.getPaymentOutDetails(paymentOutId, custType, searchCriteriaobj);  //custType added for AT-2986
		paymentOutDetailDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteriaobj));
		paymentOutDetailDto.setIsPagenationRequired(Boolean.FALSE);
		paymentOutDetailDto.setUser(user);
		modelAndView.addObject("paymentOutDetails", paymentOutDetailDto);
		return modelAndView;
	}
	
	@PostMapping(value="/setPoiExistsFlagForPaymentOut")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public boolean setPoiExistsFlagForPaymentOut(@RequestAttribute("user") UserProfile user,
			@RequestBody Contact contact) {
		boolean poiExists = false;
		try {
			poiExists = iPaymentOutService.setPoiExistsFlagForPaymentOut(user, contact);
		}
		catch(Exception e) {
			log.debug("Exception in PaymentInController in setPoiExistsFlagForPaymentIn() {1}", e);
		}
		return poiExists;
	}

}
