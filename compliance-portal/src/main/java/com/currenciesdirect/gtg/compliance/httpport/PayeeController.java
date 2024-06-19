package com.currenciesdirect.gtg.compliance.httpport;

import java.util.ArrayList;
import java.util.List;

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

import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeePaymentsResponse;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.BeneficiaryReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.titan.payee.IPayeeService;
import com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeDetailsRequest;
import com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeQueueDto;
import com.currenciesdirect.gtg.compliance.core.titan.payee.PayeeResponse;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class PayeeController.
 */
@Controller
public class PayeeController extends BaseController {

	/** The log. */
	private Logger log = LoggerFactory.getLogger(PayeeController.class);

	/** The i payee service. */
	@Autowired
	@Qualifier("payeeServiceImpl")
	private IPayeeService iPayeeService;

	/**
	 * Gets the registration details.
	 *
	 * @param user
	 *            the user
	 * @param payeeRequest
	 *            the payee request
	 * @return the registration details
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BENEFICIARY })
	@PostMapping(value = "/beneficiaryDetails")
	public ModelAndView getRegistrationDetails(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "payeeDetailsRequest", required = false) String payeeRequest) {

		ModelAndView modelAndView = new ModelAndView();
		PayeeResponse payeeDetailsResponse = new PayeeResponse();
		PayeeESResponse payeeESReponse = new PayeeESResponse();
		try {
			PayeeDetailsRequest payeeRequestObj = JsonConverterUtil.convertToObject(PayeeDetailsRequest.class,
					payeeRequest);
			BeneficiaryReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), BeneficiaryReportFilter.class);
			filter.setUserProfile(user);
			BeneficiaryReportSearchCriteria searchCriteria = new BeneficiaryReportSearchCriteria();
			searchCriteria.setFilter(filter);
			modelAndView.setViewName(ViewNameEnum.BENE_DETAILS.getViewName());
			payeeDetailsResponse = iPayeeService.getBeneficiaryDetails(user, payeeRequestObj, searchCriteria);
			if (null != payeeDetailsResponse.getPayee() && !payeeDetailsResponse.getIsBeneficiaryWhitelisted())
				payeeESReponse = iPayeeService.getRegistrationDetailsFromES(user, payeeDetailsResponse);
			String payeeESResponseJSON = JsonConverterUtil.convertToJsonWithNull(payeeESReponse);
			modelAndView.addObject("payeeESReponse", payeeESReponse);
			modelAndView.addObject("payeeDetailsResponse", payeeDetailsResponse);
			modelAndView.addObject("payeeESResponseJSON", payeeESResponseJSON);
		} catch (CompliancePortalException e) {

			log.debug("Error in getRegistrationDetails method of Titancontroller class {1}", e);
			payeeDetailsResponse = new PayeeResponse();
			payeeDetailsResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			payeeDetailsResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}

	/**
	 * Gets the beneficiaries report.
	 *
	 * @param user
	 *            the user
	 * @return the beneficiaries report
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	//@FunctionsAllowed(functions = { IS_READ_ONLY_USER })
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BENEFICIARY })
	@GetMapping(value = "/beneReport")
	public ModelAndView getBeneficiariesReport(@RequestAttribute("user") UserProfile user)
			throws CompliancePortalException {
		ModelAndView modelAndView = new ModelAndView();
		PayeeResponse payeeResponse = new PayeeResponse();
		PayeeQueueDto payeeQueueDto = new PayeeQueueDto();
		try {
			BeneficiaryReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), BeneficiaryReportFilter.class);

			filter.setUserProfile(user);

			BeneficiaryReportSearchCriteria searchCriteria = new BeneficiaryReportSearchCriteria();
			searchCriteria.setFilter(filter);
			payeeResponse = iPayeeService.getBeneficiaryList(searchCriteria);
			payeeQueueDto = iPayeeService.getBeneficiaryListWithCriteria(searchCriteria);

			modelAndView.setViewName(ViewNameEnum.BENE_REPORT.getViewName());
			modelAndView.addObject("payeeResponse", payeeResponse);
			modelAndView.addObject("payeeQueueDto", payeeQueueDto);
		} catch (CompliancePortalException e) {

			log.debug("Error in getBeneficiariesReport method of Titancontroller class {1}", e);
			payeeResponse = new PayeeResponse();
			payeeResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			payeeResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
			payeeQueueDto = new PayeeQueueDto();
			payeeQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			payeeQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;

	}

	/**
	 * Gets the beneficiaries report.
	 *
	 * @param user
	 *            the user
	 * @param searchCriteriaObj
	 *            the search criteria obj
	 * @return the beneficiaries report
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BENEFICIARY })
	@PostMapping(value = "/beneReportApply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public PayeeResponse getBeneficiariesReport(@RequestAttribute("user") UserProfile user,
			@RequestBody BeneficiaryReportSearchCriteria searchCriteriaObj) throws CompliancePortalException {
		PayeeResponse payeeResponse = new PayeeResponse();
		try {
			if (null != searchCriteriaObj && null != searchCriteriaObj.getFilter())
				searchCriteriaObj.getFilter().setUserProfile(user);
			payeeResponse = iPayeeService.getBeneficiaryList(searchCriteriaObj);
		} catch (CompliancePortalException e) {

			log.debug("Error in getBeneficiariesReport method of Titancontroller class {1}", e);
			payeeResponse = new PayeeResponse();
			payeeResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			payeeResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
		}
		return payeeResponse;

	}

	/**
	 * @param user
	 * @param searchCriteriaRedirect
	 * @return
	 * @throws CompliancePortalException
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BENEFICIARY })
	@PostMapping(value = "/beneReport")
	public ModelAndView getBeneficiariesReportOnRedirect(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "ququefilter", required = false) String searchCriteriaRedirect)
			throws CompliancePortalException {
		ModelAndView modelAndView = new ModelAndView();
		PayeeResponse payeeResponse = new PayeeResponse();
		PayeeQueueDto payeeQueueDto = new PayeeQueueDto();
		try {
			BeneficiaryReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), BeneficiaryReportFilter.class);

			filter.setUserProfile(user);
			BeneficiaryReportSearchCriteria searchCriteriaRedirectObj = JsonConverterUtil
					.convertToObject(BeneficiaryReportSearchCriteria.class, searchCriteriaRedirect);
			if (null != searchCriteriaRedirectObj && null != searchCriteriaRedirectObj.getFilter())
				searchCriteriaRedirectObj.getFilter().setUserProfile(user);
			payeeResponse = iPayeeService.getBeneficiaryList(searchCriteriaRedirectObj);
			payeeQueueDto = iPayeeService.getBeneficiaryListWithCriteria(searchCriteriaRedirectObj);

			modelAndView.setViewName(ViewNameEnum.BENE_REPORT.getViewName());
			modelAndView.addObject("payeeResponse", payeeResponse);
			modelAndView.addObject("payeeQueueDto", payeeQueueDto);
		} catch (CompliancePortalException e) {

			log.debug("Error in getBeneficiariesReportOn Redirect method of Titancontroller class {1}", e);
			payeeResponse = new PayeeResponse();
			payeeResponse.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			payeeResponse.setErrorDescription(e.getCompliancePortalErrors().getErrorDescription());
			payeeQueueDto = new PayeeQueueDto();
			payeeQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			payeeQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}

	/**
	 * Get the client of beneficiary trade account number
	 * 
	 * @param user
	 * @param accountid
	 * @return
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BENEFICIARY })
	@PostMapping(value = "/getBeneficiaryListOfClient")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public PayeeResponse getBeneClientTradeAccountNumber(@RequestAttribute("user") UserProfile user,
			@RequestBody String accountid) {

		PayeeResponse payeeResponse = new PayeeResponse();
		BeneficiaryReportSearchCriteria searchCriteria = new BeneficiaryReportSearchCriteria();
		BeneficiaryReportFilter filter = new BeneficiaryReportFilter();

		String tradeAccountNumber = accountid.replaceAll("\"", "");
		try {
			searchCriteria.setFilter(filter);
			if (null != searchCriteria.getFilter())
				searchCriteria.getFilter().setUserProfile(user);
			searchCriteria.getFilter().setKeyword(tradeAccountNumber);
			payeeResponse = iPayeeService.getBeneficiaryList(searchCriteria);

		} catch (CompliancePortalException e) {
			log.debug("Error in getBeneficiaryListOfClient method of Titancontroller class {1}", e);
		}
		return payeeResponse;
	}

	/**
	 * Gets the transaction list.
	 *
	 * @param user
	 *            the user
	 * @param atlasBeneAccNum
	 *            the atlas bene acc num
	 * @param tradeAccountNumber
	 *            the trade account number
	 * @return the transaction list
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_MANAGE_BENEFICIARY })
	@PostMapping(value = "/getTransactionList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public List<PayeePaymentsResponse> getTransactionList(@RequestAttribute("user") UserProfile user,
			@RequestBody PayeePaymentsRequest payeeRequest) throws CompliancePortalException {
		List<PayeePaymentsResponse> payeeResponse = new ArrayList<>();
		try {
			payeeResponse = iPayeeService.getTransactionList(user, payeeRequest);
		} catch (CompliancePortalException e) {
			log.debug("Error in getTransactionList method of Titancontroller class {1}", e);
		}
		return payeeResponse;
	}
}
