package com.currenciesdirect.gtg.compliance.httpport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.core.domain.report.AdministrationDto;
import com.currenciesdirect.gtg.compliance.core.report.IAdministrationService;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class CommonController.
 */
@Controller
public class CommonController extends BaseController{
	
	private static final  Logger log = LoggerFactory.getLogger(CommonController.class);
	
	@Autowired
	@Qualifier("AdministrationServiceImpl")
	private IAdministrationService iAdministrationService ;
	
	@GetMapping(value = "/dashboard")
	public ModelAndView getDashBoard(){
		ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName(ViewNameEnum.DASHBOARD.getViewName());
			return modelAndView;
	}
	
	
	
	@GetMapping(value = "/administration")
	public ModelAndView getAdministration() {
		ModelAndView modelAndView = new ModelAndView();
		AdministrationDto administrationDto = null;
		try {
			administrationDto = iAdministrationService.getAdministration();
		} catch (Exception exception) {
			log.error("Exception:", exception);
			administrationDto = new AdministrationDto();
			administrationDto.setErrorCode(CompliancePortalErrors.FAILED.getErrorCode());
			administrationDto.setErrorDescription(CompliancePortalErrors.FAILED.getErrorDescription());
		}
		modelAndView.addObject("administrationDto", administrationDto);
		modelAndView.setViewName(ViewNameEnum.ADMINISTRATION.getViewName());
		return modelAndView;
	}
	
	
	
	/**
	 * Logout.
	 *
	 * @param request
	 *            the request
	 * @return the model and view
	 */
	@SuppressWarnings("squid:S3752")
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView logout(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("Logout");
		try {
			request.getSession().invalidate();
			request.logout();
		} catch (ServletException e) {
			log.error("request.logout() throws error", e);
		}
		return modelAndView;
	}

	/**
	 * Unauthorized.
	 *
	 * @param request
	 *            the request
	 * @return the model and view
	 */
	
	@GetMapping(value = "/unauthorized")
	public ModelAndView unauthorized(@RequestAttribute("user") UserProfile user) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("UNAUTHORZIED");
		modelAndView.addObject("userJson", JsonConverterUtil.convertToJsonWithNull(user));
		modelAndView.addObject("user", user);
		return modelAndView;
	}
}
