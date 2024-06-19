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
import org.springframework.web.bind.annotation.ResponseBody;

import com.currenciesdirect.gtg.compliance.core.IActivityLogService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class ActivityLogController.
 */
@Controller
public class ActivityLogController extends BaseController {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(ActivityLogController.class);

	/** The i activity log service. */
	@Autowired
	@Qualifier("activityLogServiceImpl")
	private IActivityLogService iActivityLogService;

	/**
	 * Gets the payment in activity logs.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the payment in activity logs
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
   @PostMapping(value = "/getPaymentInActivityLogs")
	public ActivityLogs getPaymentInActivityLogs(@RequestAttribute("user") UserProfile user,
			@RequestBody ActivityLogRequest request) {
		ActivityLogs activityLogs = null;
		try {
			request.setUser(user);
			activityLogs = iActivityLogService.getPaymentInActivityLogs(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return activityLogs;
	}

	/**
	 * Gets the payment out activity logs.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the payment out activity logs
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	@PostMapping(value = "/getPaymentOutActivityLogs")
	public ActivityLogs getPaymentOutActivityLogs(@RequestAttribute("user") UserProfile user,
			@RequestBody ActivityLogRequest request) {
		ActivityLogs activityLogs = null;
		try {
			request.setUser(user);
			activityLogs = iActivityLogService.getPaymentOutActivityLogs(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return activityLogs;
	}

	/**
	 * Gets the registration activity logs.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the registration activity logs
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	@PostMapping(value = "/getRegistrationActivityLogs")
	public ActivityLogs getRegistrationActivityLogs(@RequestAttribute("user") UserProfile user,
			@RequestBody ActivityLogRequest request) {
		ActivityLogs activityLogs = null;
		try {
			request.setUser(user);
			activityLogs = iActivityLogService.getRegistrationActivityLogs(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return activityLogs;
	}

	/**
	 * Gets the consolidated registration, payment in, payment out activity
	 * logs.
	 *
	 * @param user
	 *            the user
	 * @param request
	 *            the request
	 * @return the consolidated registration, payment in, payment out activity
	 *         logs
	 */
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
    @PostMapping(value = "/getAllActivityLogs")
	public ActivityLogs getAllActivityLogs(@RequestAttribute("user") UserProfile user,
			@RequestBody ActivityLogRequest request) {
		ActivityLogs activityLogs = null;
		try {
			request.setUser(user);
			activityLogs = iActivityLogService.getAllActivityLogs(request);
		} catch (CompliancePortalException e) {
			log.debug("", e);
		}
		return activityLogs;
	}
}
