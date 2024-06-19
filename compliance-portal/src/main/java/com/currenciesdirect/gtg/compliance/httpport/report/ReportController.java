package com.currenciesdirect.gtg.compliance.httpport.report;

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

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportGenerator;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportGenerator;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportGenerator;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportGenerator;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.report.dashboard.Dashboard;
import com.currenciesdirect.gtg.compliance.core.report.IDashboardService;
import com.currenciesdirect.gtg.compliance.core.report.IPaymentInReportService;
import com.currenciesdirect.gtg.compliance.core.report.IPaymentOutReportService;
import com.currenciesdirect.gtg.compliance.core.report.IRegistrationReportService;
import com.currenciesdirect.gtg.compliance.core.report.IReportGenerator;
import com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportService;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.httpport.BaseController;
import com.currenciesdirect.gtg.compliance.httpport.ViewControllerConstant;
import com.currenciesdirect.gtg.compliance.httpport.ViewNameEnum;
import com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.UserPermissionFilterBuilder;

/**
 * The Class ReportController.
 */
@Controller
public class ReportController extends BaseController {

	/** The Constant ERROR. */
	private static final String ERROR = "Exception ";

	/** The Constant EXCEL_VIEW. */
	private static final String EXCEL_VIEW = "excelView";

	/** The Constant REPORT_VIEW. */
	private static final String REPORT_VIEW = "iReportGenerator";

	/** The Constant REGISTRATION_QUEUE_DTO. */
	private static final String REGISTRATION_QUEUE_DTO = "registrationQueueDto";

	/** The log. */
	private Logger log = LoggerFactory.getLogger(ReportController.class);

	/** The i work efficiency report service. */
	@Autowired
	@Qualifier("workEfficiencyReportServiceImpl")
	private IWorkEfficiencyReportService iWorkEfficiencyReportService;

	/** The i registration report service. */
	@Autowired
	@Qualifier("registrationReportServiceImpl")
	private IRegistrationReportService iRegistrationReportService;

	/** The i payment in report service. */
	@Autowired
	@Qualifier("paymentInReportServiceImpl")
	private IPaymentInReportService iPaymentInReportService;

	/** The i dashboard compliance service. */
	@Autowired
	@Qualifier("dashboardComplianceServiceImpl")
	private IDashboardService iDashboardComplianceService;

	/** The i payment out report service. */
	@Autowired
	@Qualifier("paymentOutReportServiceImpl")
	private IPaymentOutReportService iPaymentOutReportService;

	/**
	 * Gets the work efficiency report.
	 *
	 * @param user
	 *            the user
	 * @return the work efficiency report
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_WORK_EFFICIANCY_REPORT })
	@GetMapping(value = "/workEfficiencyReport")
	public ModelAndView getWorkEfficiencyReport(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		WorkEfficiencyReportDto workEfficiencyReportDto;
		modelAndView.setViewName(ViewNameEnum.WORK_EFFICIENCY_REPORT.getViewName());
		try {
			workEfficiencyReportDto = iWorkEfficiencyReportService.getWorkEfficiencyReport();
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			workEfficiencyReportDto = new WorkEfficiencyReportDto();
			workEfficiencyReportDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			workEfficiencyReportDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		workEfficiencyReportDto.setUser(user);
		modelAndView.addObject("workEfficiencyReportDto", workEfficiencyReportDto);

		return modelAndView;
	}

	/**
	 * Gets the work efficiency report with criteria .
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return the work efficiency report with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_WORK_EFFICIANCY_REPORT })
	@PostMapping(value = "/workEfficiencyReport")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteria(
			@RequestBody WorkEfficiencyReportSearchCriteria request, @RequestAttribute("user") UserProfile user) {
		WorkEfficiencyReportDto workEfficiencyReportDto = null;
		try {
			workEfficiencyReportDto = iWorkEfficiencyReportService.getWorkEfficiencyReportWithCriteria(request);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			workEfficiencyReportDto = new WorkEfficiencyReportDto();
			workEfficiencyReportDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			workEfficiencyReportDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
			workEfficiencyReportDto.setUser(user);
		}
		workEfficiencyReportDto.setUser(user);
		return workEfficiencyReportDto;
	}

	/**
	 * Gets the registration report.
	 *
	 * @param user
	 *            the user
	 * @return the registration report
	 */

	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_REPORT })
	@GetMapping(value = "/regReport")
	public ModelAndView getRegistrationReport(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;
		try {
			RegistrationReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), RegistrationReportFilter.class);
			filter.setUserProfile(user);
			RegistrationReportSearchCriteria searchCriteria = new RegistrationReportSearchCriteria();
			searchCriteria.setIsFilterApply(Boolean.FALSE);
			searchCriteria.setFilter(filter);
			searchCriteria.setIsLandingPage(Boolean.TRUE);
			modelAndView.setViewName(ViewNameEnum.REG_REPORT.getViewName());
			registrationQueueDto = iRegistrationReportService.getRegistrationQueueWithCriteria(searchCriteria);
			registrationQueueDto.setUser(user);
			registrationQueueDto.setIsFromDetails(Boolean.FALSE);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {

			log.debug(ERROR, e);
		}
		return modelAndView;

	}

	/**
	 * Gets the registration queue with criteria.
	 *
	 * @param user
	 *            the user
	 * @param searchCriteria
	 *            the search criteria
	 * @return the registration queue with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_QUQUE })
	@PostMapping(value = "/regReport")
	public ModelAndView getRegistrationQueueWithCriteria(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;

		try {

			RegistrationReportSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(RegistrationReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			if ((searchCriteriaobj.getFilter().getExcludeCustType() == null
					|| searchCriteriaobj.getFilter().getExcludeCustType().length == 0)
					&& (searchCriteriaobj.getFilter().getCustType() != null
							&& searchCriteriaobj.getFilter().getCustType().length == 0)) {
				searchCriteriaobj.getFilter().setCustType(new String[] { Constants.CUST_TYPE_PFX });
			}
			searchCriteriaobj.getFilter().setUserProfile(user);
			searchCriteriaobj.setIsFilterApply(Boolean.TRUE);
			modelAndView.setViewName(ViewNameEnum.REG_REPORT.getViewName());
			registrationQueueDto = iRegistrationReportService.getRegistrationQueueWithCriteria(searchCriteriaobj);
			registrationQueueDto.setUser(user);
			registrationQueueDto.setIsFromDetails(Boolean.TRUE);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}

	/**
	 * Gets the registration report with criteria .
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return the registration report with criteria .
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_REPORT })
	@PostMapping(value = "/regReportCriteria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public RegistrationQueueDto getRegistrationQueueWithCriteria(@RequestBody RegistrationReportSearchCriteria request,
			@RequestAttribute("user") UserProfile user) {
		RegistrationQueueDto registrationQueueDto = null;
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			registrationQueueDto = iRegistrationReportService.getRegistrationQueueWithCriteria(request);
			registrationQueueDto.setIsFromDetails(Boolean.FALSE);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		registrationQueueDto.setUser(user);
		return registrationQueueDto;
	}

	/**
	 * Gets the paymentIn report .
	 *
	 * @param user
	 *            the user
	 * @return the paymentIn report with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_REPORT })
	@GetMapping(value = "/paymentInReport")
	public ModelAndView getPaymentInReport(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		PaymentInQueueDto paymentInQueueDto;
		modelAndView.setViewName(ViewNameEnum.PAYMENTIN_REPORT.getViewName());
		try {
			PaymentInReportSearchCriteria searchCriteria = new PaymentInReportSearchCriteria();
			PaymentInReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), PaymentInReportFilter.class);
			filter.setUserProfile(user);
			searchCriteria.setFilter(filter);
			searchCriteria.setIsLandingPage(Boolean.TRUE);
			paymentInQueueDto = iPaymentInReportService.getPaymentInReportWithCriteria(searchCriteria);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			paymentInQueueDto = new PaymentInQueueDto();
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		modelAndView.addObject("paymentInQueueDto", paymentInQueueDto);
		return modelAndView;
	}

	/**
	 * Gets the payment in queue.
	 *
	 * @param user
	 *            the user
	 * @param searchCriteria
	 *            the search criteria
	 * @return the payment in queue
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_QUQUE })
	@PostMapping(value = "/paymentInReport")
	public ModelAndView getPaymentInQueue(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {

		ModelAndView modelAndView = new ModelAndView();
		PaymentInQueueDto paymentInQueueDto = null;
		modelAndView.setViewName(ViewNameEnum.PAYMENTIN_REPORT.getViewName());
		try {
			PaymentInReportSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(PaymentInReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			if (Boolean.FALSE.equals(searchCriteriaobj.getIsFilterApply())) {
				searchCriteriaobj.setIsLandingPage(Boolean.TRUE);
			}
			paymentInQueueDto = iPaymentInReportService.getPaymentInReportWithCriteria(searchCriteriaobj);
			paymentInQueueDto.setIsFromDetails(Boolean.TRUE);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueue method paymentIncontroller class {1}", e);
			paymentInQueueDto = new PaymentInQueueDto();
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		modelAndView.addObject("paymentInQueueDto", paymentInQueueDto);
		return modelAndView;
	}

	/**
	 * Gets the paymentIn report with criteria .
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return the paymentIn report with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_REPORT })
	@PostMapping(value = "/paymentInReportCriteria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public PaymentInQueueDto getPaymentInReportWithCriteria(@RequestBody PaymentInReportSearchCriteria request,
			@RequestAttribute("user") UserProfile user) {
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			paymentInQueueDto = iPaymentInReportService.getPaymentInReportWithCriteria(request);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		return paymentInQueueDto;
	}

	/**
	 * Handle request to download an Excel document.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the payment in report in excel format
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTIN_REPORT })
	@PostMapping(value = "/paymentInReportDownLoad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/vnd.ms-excel")
	@ResponseBody
	public ModelAndView getPaymentInReportInExcelFormat(
			@RequestParam(value = "downloadCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) {
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		try {
			PaymentInReportSearchCriteria request = JsonConverterUtil
					.convertToObject(PaymentInReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			paymentInQueueDto = iPaymentInReportService.getPaymentInQueueWholeData(request);
			paymentInQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			paymentInQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentInQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		IReportGenerator iReportGenerator = new PaymentInReportGenerator(paymentInQueueDto);
		return new ModelAndView(EXCEL_VIEW, REPORT_VIEW, iReportGenerator);
	}

	/**
	 * Gets the registration report in excel format.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the registration report in excel format
	 */
	/*
	 **
	 * Handle request to download an Excel document
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_REGISTRTION_REPORT })
	@PostMapping(value = "/registrationReportDownLoad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/vnd.ms-excel")
	@ResponseBody
	public ModelAndView getRegistrationReportInExcelFormat(
			@RequestParam(value = "downloadCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) {
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		try {
			RegistrationReportSearchCriteria requestDownloadCriteria = JsonConverterUtil
					.convertToObject(RegistrationReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(requestDownloadCriteria.getFilter(),
					user.getPermissions());
			requestDownloadCriteria.getFilter().setUserProfile(user);
			registrationQueueDto = iRegistrationReportService.getRegistrationQueueWholeData(requestDownloadCriteria);
			registrationQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		IReportGenerator iReportGenerator = new RegistrationReportGenerator(registrationQueueDto);
		return new ModelAndView(EXCEL_VIEW, REPORT_VIEW, iReportGenerator);
	}

	/**
	 * Gets the compliance dash board.
	 *
	 * @param user
	 *            the user
	 * @return the compliance dash board
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_DASHBOARD })
	@GetMapping(value = "/")
	public ModelAndView getComplianceDashBoard(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		Dashboard dashboard = new Dashboard();
		modelAndView.setViewName(ViewNameEnum.DASHBOARD_COMPLIANCE.getViewName());
		try {
			dashboard = iDashboardComplianceService.getDashboard();
			dashboard.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getComplianceDashBoard method reportcontroller class {1}", e);
			dashboard.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			dashboard.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		modelAndView.addObject("dashboard", dashboard);
		return modelAndView;
	}

	/**
	 * Gets the PaymentOut report.
	 *
	 * @param user
	 *            the user
	 * @return the PaymentOut report
	 */

	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_REPORT })
	@GetMapping(value = "/paymentOutReport")
	public ModelAndView getPaymentOutReport(@RequestAttribute("user") UserProfile user) {

		PaymentOutQueueDto paymentOutQueueDto;

		try {
			PaymentOutReportSearchCriteria searchCriteria = new PaymentOutReportSearchCriteria();
			PaymentOutReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), PaymentOutReportFilter.class);
			filter.setUserProfile(user);
			searchCriteria.setFilter(filter);
			searchCriteria.setIsLandingPage(Boolean.TRUE);
			paymentOutQueueDto = iPaymentOutReportService.getPaymentOutReportWithCriteria(searchCriteria);
			paymentOutQueueDto.setIsFromDetails(Boolean.FALSE);
			paymentOutQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueue method paymentIncontroller class {1}", e);
			paymentOutQueueDto = new PaymentOutQueueDto();
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName(ViewNameEnum.PAYMENTOUT_REPORT.getViewName());
		modelAndView.addObject("paymentOutQueueDto", paymentOutQueueDto);
		return modelAndView;
	}

	/**
	 * Gets the payment out queue with criteria.
	 *
	 * @param user
	 *            the user
	 * @param searchCriteria
	 *            the search criteria
	 * @return the payment out queue with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_QUQUE })
	@PostMapping(value = "/paymentOutReport")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ModelAndView getPaymentOutQueueWithCriteria(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {
		ModelAndView modelAndView = new ModelAndView();
		PaymentOutQueueDto paymentOutQueueDto;
		modelAndView.setViewName(ViewNameEnum.PAYMENTOUT_REPORT.getViewName());
		try {
			PaymentOutReportSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(PaymentOutReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			if (Boolean.FALSE.equals(searchCriteriaobj.getIsFilterApply())) {
				searchCriteriaobj.setIsLandingPage(Boolean.TRUE);
			}
			paymentOutQueueDto = iPaymentOutReportService.getPaymentOutReportWithCriteria(searchCriteriaobj);
			paymentOutQueueDto.setIsFromDetails(Boolean.TRUE);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			paymentOutQueueDto = new PaymentOutQueueDto();
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		paymentOutQueueDto.setUser(user);
		modelAndView.addObject("paymentOutQueueDto", paymentOutQueueDto);
		return modelAndView;
	}

	/**
	 * Gets the paymentOut report with criteria .
	 *
	 * @param request
	 *            the request
	 * @param user
	 *            the user
	 * @return the paymentOut report with criteria
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_REPORT })
	@PostMapping(value = "/paymentOutReportCriteria")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public PaymentOutQueueDto getPaymentOutReportWithCriteria(@RequestBody PaymentOutReportSearchCriteria request,
			@RequestAttribute("user") UserProfile user) {
		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		try {
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			paymentOutQueueDto = iPaymentOutReportService.getPaymentOutReportWithCriteria(request);
			paymentOutQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueueWithCriteria method paymentIncontroller class {1}", e);
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		return paymentOutQueueDto;
	}

	/**
	 * Handle request to download an Excel document.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the payment out report in excel format
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_PAYMENTOUT_REPORT })
	@PostMapping(value = "/paymentOutReportDownLoad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/vnd.ms-excel")
	@ResponseBody
	public ModelAndView getPaymentOutReportInExcelFormat(
			@RequestParam(value = "downloadCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) {
		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		try {
			PaymentOutReportSearchCriteria request = JsonConverterUtil
					.convertToObject(PaymentOutReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(request.getFilter(), user.getPermissions());
			request.getFilter().setUserProfile(user);
			paymentOutQueueDto = iPaymentOutReportService.getPaymentOutQueueWholeData(request);
			paymentOutQueueDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug("error in getPaymentInQueueWithCriteria method paymentIncontroller class {1}", e);
			paymentOutQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			paymentOutQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		IReportGenerator iReportGenerator = new PaymentOutReportGenerator(paymentOutQueueDto);
		return new ModelAndView(EXCEL_VIEW, REPORT_VIEW, iReportGenerator);
	}

	/**
	 * Gets the registration report in excel format.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @param user
	 *            the user
	 * @return the registration report in excel format
	 */
	/*
	 **
	 * Handle request to download an Excel document
	 */
	@FunctionsAllowed(functions = { ViewControllerConstant.CAN_VIEW_WORK_EFFICIANCY_REPORT })
	@PostMapping(value = "/workEfficiencyReportDownLoad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces("application/vnd.ms-excel")
	@ResponseBody
	public ModelAndView getworkEfficiencyReportInExcelFormat(
			@RequestParam(value = "downloadCriteria", required = false) String searchCriteria,
			@RequestAttribute("user") UserProfile user) {
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		try {
			WorkEfficiencyReportSearchCriteria request = JsonConverterUtil
					.convertToObject(WorkEfficiencyReportSearchCriteria.class, searchCriteria);
			workEfficiencyReportDto = iWorkEfficiencyReportService
					.getWorkEfficiencyReportWithCriteriaInExcelFormat(request);
			workEfficiencyReportDto.setUser(user);
		} catch (CompliancePortalException e) {
			log.debug(ERROR, e);
			workEfficiencyReportDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			workEfficiencyReportDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}

		IReportGenerator iReportGenerator = new WorkEfficiencyReportGenerator(workEfficiencyReportDto);
		return new ModelAndView(EXCEL_VIEW, REPORT_VIEW, iReportGenerator);
	}

	/**
	 * Gets the holistic view report.
	 *
	 * @param user
	 *            the user
	 * @return the holistic view report
	 */
	// @FunctionsAllowed(functions = { CAN_VIEW_REGISTRTION_REPORT })
	@GetMapping(value = "/holisticViewReport")
	public ModelAndView getHolisticViewReport(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;
		try {
			RegistrationReportFilter filter = UserPermissionFilterBuilder
					.getFilterFromUserPermission(user.getPermissions(), RegistrationReportFilter.class);
			filter.setUserProfile(user);
			RegistrationReportSearchCriteria searchCriteria = new RegistrationReportSearchCriteria();
			searchCriteria.setIsFilterApply(Boolean.TRUE);
			searchCriteria.setFilter(filter);
			searchCriteria.setIsLandingPage(Boolean.FALSE);
			modelAndView.setViewName(ViewNameEnum.HOLISTIC_REPORT.getViewName());
			registrationQueueDto = iRegistrationReportService.getRegistrationQueueWithCriteria(searchCriteria);
			registrationQueueDto.setUser(user);
			registrationQueueDto.setIsFromDetails(Boolean.FALSE);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {

			log.debug(ERROR, e);
		}
		return modelAndView;

	}

	/**
	 * Gets the holistic view report.
	 *
	 * @param user
	 *            the user
	 * @param searchCriteria
	 *            the search criteria
	 * @return the holistic view report
	 */
	@PostMapping(value = "/holisticViewReport")
	public ModelAndView getHolisticViewReport(@RequestAttribute("user") UserProfile user,
			@RequestParam(value = "searchCriteria", required = false) String searchCriteria) {
		ModelAndView modelAndView = new ModelAndView();
		RegistrationQueueDto registrationQueueDto = null;

		try {

			RegistrationReportSearchCriteria searchCriteriaobj = JsonConverterUtil
					.convertToObject(RegistrationReportSearchCriteria.class, searchCriteria);
			UserPermissionFilterBuilder.overrideUserPermissionOnFilter(searchCriteriaobj.getFilter(),
					user.getPermissions());
			searchCriteriaobj.getFilter().setUserProfile(user);
			searchCriteriaobj.setIsFilterApply(Boolean.TRUE);
			modelAndView.setViewName(ViewNameEnum.HOLISTIC_REPORT.getViewName());
			registrationQueueDto = iRegistrationReportService.getRegistrationQueueWithCriteria(searchCriteriaobj);
			registrationQueueDto.setUser(user);
			registrationQueueDto.setIsFromDetails(Boolean.TRUE);
			modelAndView.addObject(REGISTRATION_QUEUE_DTO, registrationQueueDto);
		} catch (CompliancePortalException e) {
			log.debug(" {1}", e);
			registrationQueueDto = new RegistrationQueueDto();
			registrationQueueDto.setErrorCode(e.getCompliancePortalErrors().getErrorCode());
			registrationQueueDto.setErrorMessage(e.getCompliancePortalErrors().getErrorDescription());
		}
		return modelAndView;
	}
}
