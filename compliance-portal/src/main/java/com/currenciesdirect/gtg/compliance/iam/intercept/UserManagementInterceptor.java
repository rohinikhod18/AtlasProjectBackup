package com.currenciesdirect.gtg.compliance.iam.intercept;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.keycloak.adapters.saml.SamlPrincipal;
import org.keycloak.adapters.saml.SamlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.IIAMService;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * Implementation : 1. Intercept each request. Creates and populates user object
 * from keycloak principal and database 2. validates user have access for
 * request from functions 3. if it's have access then goes to respective
 * controller. 4.if user don't have access then sets status to
 * 401(UNAUTHORIZED) 5. if user unauthorized and not an ajax request then
 * redirects to Unauthorzied page.
 */
public class UserManagementInterceptor implements HandlerInterceptor {

	@Autowired
	@Qualifier("IAMServiceImpl")
	private IIAMService service;
	private static final Logger logger = LoggerFactory.getLogger(UserManagementInterceptor.class);

	private static final String X_REQUESTED_WITH_HDADER = "X-Requested-With";

	@Autowired
	private FunctionsMapper requestMapping;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		try {
		logger.warn("\n-------------- preHandle() Http Request : {} and Http Response : {}", request, response);
		UserProfile user = getUserWithPermission(request);
		request.setAttribute("user", user);
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.setMaxInactiveInterval(360);
		} else {
			logger.info("\n----------------- User session timed out -----------------"); //AT-5315
			request.getRequestDispatcher("/logout?GLO=true").forward(request, response);
			return false;
		}
		
		if(user == null || user.getId() == null){
			getUnauthorizedStatus(request,response);
			return false;
		}
		String url = request.getRequestURL().toString();
		String requestMappingUrl = url
				.substring(url.indexOf(request.getContextPath()) + request.getContextPath().length());
		com.currenciesdirect.gtg.compliance.iam.core.domain.FunctionsAllowed function = requestMapping.getAllowedFunctions(requestMappingUrl);
		
		if (function != null && !user.getRole().hasFunctions(function.functions(), function.allRequired())
				|| !request.isRequestedSessionIdValid()) {
			checkRequestSource(request, response, user, requestMappingUrl);
			return false;
		}
		} catch (Exception exception){
			logger.error("Exception in preHandle method:",exception );
			getUnauthorizedStatus(request,response);
			return false;
		}
		return true;
	}

	/**
	 * Check Request Source
	 * 
	 * @param request
	 * @param response
	 * @param user
	 * @param requestMappingUrl
	 */
	private void checkRequestSource(HttpServletRequest request, HttpServletResponse response, UserProfile user,
			String requestMappingUrl) {
		if(isRequestForDasboard(requestMappingUrl)){
			forwordToRegQueue(request,response,user);
		} else {
			getUnauthorizedStatus(request, response);
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null && request != null) {
			modelAndView.addObject("userJson", JsonConverterUtil.convertToJsonWithNull(request.getAttribute("user")));
			modelAndView.addObject("user", request.getAttribute("user"));
		}

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		logger.debug("After completion Request URL:: {}",request.getRequestURL());
	}

	private UserProfile getUserWithPermission(HttpServletRequest request) throws CompliancePortalException {
		//UserProfile user = getUserProfile(request);
		UserProfile user = getUserProfileDummy();
		if (user != null) {
			logger.warn("\n-------------- username : {}",user.getName());
			service.getUserFunctions(user);
			service.getUserPermissionsByRoleAndView(user);
		}
		return user;
	}
	
	private UserProfile getUserProfileDummy() {
		UserProfile user = new UserProfile();
		user.setBirthdate(null);
		user.setEmail(null);
		user.setFamilyName(null);
		user.setGender(null);
		user.setGivenName(null);
		user.setId(null);
		user.setLocale(null);
		user.setMiddleName(null);
		user.setName("cd.comp.system");
		user.setNickName(null);
		user.setPhoneNumber(null);
		user.setPreferredUserName("cd.comp.system");
		user.setWebsite(null);
		user.setZoneinfo(null);
		Role r = new Role();
		Set<String> name = new HashSet<>();
		name.add("manage-account");
		name.add("ATLAS_SENIOR_EXECUTIVE");
		name.add("manage-account-links");
		name.add("view-profile");
		name.add("ATLAS_DEPT_HEAD");
		//name.add("ATLAS_SENIOR_EXECUTIVE");
		name.add("ATLAS_DATA_ANON_INITIATOR");
		name.add("ATLAS_DATA_ANON_APPROVER");
		List<Function> fun = new ArrayList<>();
		Function f = new Function();
		f.setHasAccess(true);
		f.setHasOverrideAccess(false);
		f.setName("canWorkOnCFX");
		fun.add(f);
		Function f1 = new Function();
		f1.setHasAccess(true);
		f1.setHasOverrideAccess(false);
		f1.setName("canWorkOnPFX");
		fun.add(f1);
		Function f2 = new Function();
		f2.setHasAccess(true);
		f2.setHasOverrideAccess(false);
		f2.setName("canViewRegistrationQueue");
		fun.add(f2);
		Function f3 = new Function();
		f3.setHasAccess(true);
		f3.setHasOverrideAccess(false);
		f3.setName("canViewPaymentInQueue");
		fun.add(f3);
		Function f4 = new Function();
		f4.setHasAccess(true);
		f4.setHasOverrideAccess(false);
		f4.setName("canViewPaymentOutQueue");
		fun.add(f4);
		Function f5 = new Function();
		f5.setHasAccess(true);
		f5.setHasOverrideAccess(false);
		f5.setName("canViewRegistrationReport");
		fun.add(f5);
		Function f6 = new Function();
		f6.setHasAccess(true);
		f6.setHasOverrideAccess(false);
		f6.setName("canViewPaymentInReport");
		fun.add(f6);
		Function f7 = new Function();
		f7.setHasAccess(true);
		f7.setHasOverrideAccess(false);
		f7.setName("canViewPaymentOutReport");
		fun.add(f7);
		Function f8 = new Function();
		f8.setHasAccess(true);
		f8.setHasOverrideAccess(false);
		f8.setName("canViewDashboard");
		fun.add(f8);
		Function f9 = new Function();
		f9.setHasAccess(true);
		f9.setHasOverrideAccess(false);
		f9.setName("canManageFraud");
		fun.add(f9);
		Function f10 = new Function();
		f10.setHasAccess(true);
		f10.setHasOverrideAccess(false);
		f10.setName("canManageCustom");
		fun.add(f10);
		Function f11 = new Function();
		f11.setHasAccess(true);
		f11.setHasOverrideAccess(false);
		f11.setName("canManageEID");
		fun.add(f11);
		Function f12 = new Function();
		f12.setHasAccess(true);
		f12.setHasOverrideAccess(false);
		f12.setName("canManageSanction");
		fun.add(f12);
		Function f13 = new Function();
		f13.setHasAccess(true);
		f13.setHasOverrideAccess(false);
		f13.setName("canManageBlackList");
		fun.add(f13);
		Function f14 = new Function();
		f14.setHasAccess(true);
		f14.setHasOverrideAccess(false);
		f14.setName("canDoAdministration");
		fun.add(f14);
		Function f15 = new Function();
		f15.setHasAccess(true);
		f15.setHasOverrideAccess(false);
		f15.setName("canViewWorkEfficiancyReport");
		fun.add(f15);
		Function f16 = new Function();
		f16.setHasAccess(true);
		f16.setHasOverrideAccess(false);
		f16.setName("canManageWatchListCategory1");
		fun.add(f16);
		Function f17 = new Function();
		f17.setHasAccess(true);
		f17.setHasOverrideAccess(false);
		f17.setName("canManageWatchListCategory2");
		fun.add(f17);
		Function f18 = new Function();
		f18.setHasAccess(true);
		f18.setHasOverrideAccess(false);
		f18.setName("canInitiateDataAnon");
		fun.add(f18);
		Function f19 = new Function();
		f19.setHasAccess(true);
		f19.setHasOverrideAccess(false);
		f19.setName("canApproveDataAnon");
		fun.add(f19);
		Function f20 = new Function();
		f20.setHasAccess(true);
		f20.setHasOverrideAccess(false);
		f20.setName("isReadOnlyUser");
		fun.add(f20);
		Function f21 = new Function();
		f21.setHasAccess(true);
		f21.setHasOverrideAccess(false);
		f21.setName("canManageBeneficiary");
		fun.add(f21);
		Function f22 = new Function();
		f22.setHasAccess(true);
		f22.setHasOverrideAccess(false);
		f22.setName("canNotLockAccount");
		fun.add(f22);
		
		r.setNames(name);
		r.setFunctions(fun);
		user.setRole(r);
		
		UserPermission userPer = new UserPermission();
		userPer.setCanApproveDataAnon(false);
		userPer.setCanDoAdministration(true);
		userPer.setCanInitiateDataAnon(false);
		userPer.setCanManageBeneficiary(false);
		userPer.setCanManageBlackList(true);
		userPer.setCanManageCustom(true);
		userPer.setCanManageEID(true);
		userPer.setCanManageSanction(true);
		userPer.setCanManageSanction(true);
		userPer.setCanManageWatchListCategory1(false);
		userPer.setCanManageWatchListCategory2(false);
		userPer.setCanNotLockAccount(false);
		userPer.setCanUnlockRecords(true);
		userPer.setCanViewDashboard(true);
		userPer.setCanViewPaymentInDetails(true);
		userPer.setCanViewPaymentInQueue(true);
		userPer.setCanViewPaymentInReport(true);
		userPer.setCanViewPaymentOutDetails(true);
		userPer.setCanViewRegistrationDetails(true);
		userPer.setCanViewRegistrationQueue(true);
		userPer.setCanViewWorkEfficiacyReport(true);
		userPer.setCanWorkOnCFX(true);
		userPer.setCanWorkOnPFX(true);
		userPer.setIsReadOnlyUser(true);
		user.setPermissions(userPer);
		
		return user;
		
	}

	/**
	 * Gets the user profile.
	 *
	 * @param request
	 *            the request
	 * @return the user profile
	 */
	protected UserProfile getUserProfile(HttpServletRequest request) {
		UserProfile user = new UserProfile();
		if (request.getUserPrincipal() instanceof SamlPrincipal) {
			SamlPrincipal userPrincipal = (SamlPrincipal) request.getUserPrincipal();

			SamlSession samlSession = (SamlSession) request.getSession().getAttribute(SamlSession.class.getName());
			user.setPreferredUserName(userPrincipal.getFriendlyAttribute("givenName") + " "
					+ userPrincipal.getFriendlyAttribute("surname"));
			user.setName(userPrincipal.getName());
			Role role = new Role();
			role.setNames(samlSession.getRoles());
			user.setRole(role);
			return user;
		}
		return null;
	}

	private boolean isAjaxCall(HttpServletRequest request) {
		return "XMLHttpRequest".equals(request.getHeader(X_REQUESTED_WITH_HDADER));
	}
	
	private void getUnauthorizedStatus(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			if (!isAjaxCall(request)) {
				request.getRequestDispatcher("/unauthorized").forward(request, response);
			}
		} catch (Exception exception) {
			logger.error("Exception in getUnauthorizedStatus() method :",exception );
		}
	}
	
	private void forwordToRegQueue(HttpServletRequest request, HttpServletResponse response, 
			UserProfile user) {
		String forwardUrl ;
		try {
			 if(user.getPermissions().getCanViewRegistrationQueue()){
				forwardUrl =  "/reg";
			}else {
				forwardUrl = "/dashboard";
			}
			
			request.getRequestDispatcher(forwardUrl).forward(request, response);
		} catch (Exception exception) {
			logger.error("Exception :",exception );
		}
	}
	
	private boolean isRequestForDasboard(String url){
		return url != null && ("/").equalsIgnoreCase(url);
	}

}