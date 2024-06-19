package com.currenciesdirect.gtg.compliance.httpport;

import javax.servlet.http.HttpServletRequest;
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

import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.core.IRecheckService;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckRequest;
import com.currenciesdirect.gtg.compliance.core.domain.BaseRepeatCheckResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class RecheckController.
 */
@Controller
public class RecheckController extends BaseController {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(RecheckController.class);

	/** The i recheck service. */
	@Autowired
	@Qualifier("recheckServiceImpl")
	private IRecheckService iRecheckService;

	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
	@PostMapping(value = "/recheckRegistration")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse repeatCheckRegistrationFailures(@RequestAttribute("user") UserProfile user,
			@RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse response = null;

		try {
			response = iRecheckService.repeatCheckRegistrationFailures(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);

		}
		return response;
	}

	/**
	 * Repeat check funds out failures.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the base repeat check response
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
	@PostMapping(value = "/recheckFundsOut")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse repeatCheckFundsOutFailures(HttpServletRequest httpRequest, @RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse baseRepeatCheckResponse = null;
		try {
			UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
			baseRepeatCheckResponse = iRecheckService.repeatCheckFundsOutFailures(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return baseRepeatCheckResponse;
	}

	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
	@PostMapping(value = "/recheckFundsIn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse repeatCheckFundsInFailures(HttpServletRequest httpRequest,
			@RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse baseRepeatCheckResponse = null;

		try {
			UserProfile user = JsonConverterUtil.convertToObject(UserProfile.class, httpRequest.getHeader("user"));
			baseRepeatCheckResponse = iRecheckService.repeatCheckFundsInFailures(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);

		}
		return baseRepeatCheckResponse;
	}

	/**
	 * Gets the registration service failure count.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the registration service failure count
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
	@PostMapping(value = "/registrationServiceFailureCount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse getRegistrationServiceFailureCount(@RequestAttribute("user") UserProfile user,
			@RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse response = null;

		try {
			response = iRecheckService.getRegistrationServiceFailureCount(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);

		}
		return response;
	}

	/**
	 * Gets the funds in service failure count.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the funds in service failure count
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
	@PostMapping(value = "/fundsInServiceFailureCount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse getFundsInServiceFailureCount(@RequestAttribute("user") UserProfile user,
			@RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse response = null;

		try {
			response = iRecheckService.getFundsInServiceFailureCount(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);

		}
		return response;
	}

	/**
	 * Gets the funds out service failure count.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the funds out service failure count
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
    @PostMapping(value = "/fundsOutServiceFailureCount")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse getFundsOutServiceFailureCount(@RequestAttribute("user") UserProfile user,
			@RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse response = null;

		try {
			response = iRecheckService.getFundsOutServiceFailureCount(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);

		}
		return response;
	}
	
	/**
	 * @param user
	 * @param request
	 * @return repeat check response
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
    @PostMapping(value = "/forceClearFundsOuts")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseBody
    public BaseRepeatCheckResponse forceClearFundsOuts(@RequestAttribute("user") UserProfile user,
            @RequestBody BaseRepeatCheckRequest request) {
        BaseRepeatCheckResponse response = null;

        try {
            response = iRecheckService.forceClearFundsOuts(user, request);
        } catch (CompliancePortalException e) {
            log.debug("", e);

        }
        return response;
    }

	@PostMapping(value = "/repeatCheckProgressBar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public Integer getRepeatCheckProgressBar(@RequestBody BaseRepeatCheckRequest request) {
		Integer response = null;
		try {
			response = iRecheckService.getRepeatCheckProgressBar(request);
		}
		catch(CompliancePortalException e) {
			log.debug("",e);
		}
		return response;
	}
	
	
	/**
	 * @param user
	 * @param request
	 * @return repeat check response
	 */
	@PostMapping(value = "/forceClearFundsIn")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseRepeatCheckResponse forceClearFundsIn(@RequestAttribute("user") UserProfile user,
			@RequestBody BaseRepeatCheckRequest request) {
		BaseRepeatCheckResponse response = null;

		try {
			response = iRecheckService.forceClearFundsIn(user, request);
		} catch (CompliancePortalException e) {
			log.debug("", e);

		}
		return response;
	}
	
	/**
	 * Delete reprocess failed.
	 *
	 * @param request the request
	 * @return the integer
	 */
	@PostMapping(value = "/deleteReprocessFailed")
	@ResponseBody
	public Integer deleteReprocessFailed(@RequestBody BaseRepeatCheckRequest request) {
		Integer result = 0;
		try {
			result = iRecheckService.deleteReprocessFailed(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return result;
	}
	
	/**
	 * Update TMMQ retry count.
	 *
	 * @return true, if successful
	 */
	//Add For AT-4185
	@PostMapping(value = "/updateTMMQRetryCount")
	@ResponseBody
	public boolean updateTMMQRetryCount() {
		boolean retryCount = false;
		try {
			retryCount = iRecheckService.updateTMMQRetryCount();
		}
		catch(Exception e) {
			log.debug("Exception in RecheckController in updateTMMQRetryCount() {1}", e);
		}
		return retryCount;
	}
	
	/**
	 * Show count reprocess failed.
	 *
	 * @param batchId the batch id
	 * @return the string
	 */ //AT-4355
	@GetMapping(value = "/showCountReprocessFailed")
	@ResponseBody
	public String showCountReprocessFailed(@RequestParam("batchId") int batchId) {
		String result = null;
		try {
			result = iRecheckService.showCountReprocessFailed(batchId);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return result;
	}

	/**
	 * Clear reprocess failed.
	 *
	 * @return the integer
	 */ //AT-4355
	@GetMapping(value = "/clearReprocessFailed")
	@ResponseBody
	public Integer clearReprocessFailed() {
		Integer result = 0;
		try {
			result = iRecheckService.clearReprocessFailed();
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return result;
	}
	
	/**
	 * Update post card TMMQ retry count.
	 *
	 * @return true, if successful
	 */
	// Add For AT-5023
	@PostMapping(value = "/updatePostCardTMMQRetryCount")
	@ResponseBody
	public boolean updatePostCardTMMQRetryCount() {
		boolean retryCount = false;
		try {
			retryCount = iRecheckService.updatePostCardTMMQRetryCount();
		} catch (Exception e) {
			log.debug("Exception in RecheckController in updatePCMQRetryCount() {1}", e);
		}
		return retryCount;
	}
}
