package com.currenciesdirect.gtg.compliance.httpport;

import java.util.List;

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

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.core.ITransactionMonitoringService;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

@Controller
public class TransactionMonitoringController extends BaseController {

	/** The log. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringController.class);
	
	/** The i transaction monitoring service. */
	@Autowired
	@Qualifier("transactionMonitoringServiceImpl")
	private ITransactionMonitoringService iTransactionMonitoringService;
	
	/**
	 * Repeat check registration failures.
	 *
	 * @param user the user
	 * @param request the request
	 * @return the base repeat check response
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_DO_ADMINISTRATION })
	@PostMapping(value = "/syncRegWithIntuition")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public BaseResponse repeatCheckRegistrationFailures(@RequestAttribute("user") UserProfile user,
			@RequestBody List<String> request) {
		BaseResponse response = null;

		try {
			response = iTransactionMonitoringService.syncRegistrationRecordWithIntuition(user, request);
		} catch (CompliancePortalException e) {
			LOG.debug("", e);

		}
		return response;
	}
	
}
