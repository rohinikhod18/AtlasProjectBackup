package com.currenciesdirect.gtg.compliance.httpport;

import javax.servlet.http.HttpServletRequest;

import org.keycloak.adapters.saml.SamlPrincipal;
import org.keycloak.adapters.saml.SamlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.ModelAndView;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.IIAMService;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Role;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class BaseController.
 */
public class BaseController {
	
	@Autowired
	@Qualifier("IAMServiceImpl")
	private IIAMService service;
	
	/**
	 * Gets the user profile.
	 *
	 * @param request the request
	 * @return the user profile
	 */
	protected UserProfile getUserProfile(HttpServletRequest request){
		UserProfile user = new UserProfile();
		if (request.getUserPrincipal() instanceof SamlPrincipal) {
			SamlPrincipal userPrincipal = (SamlPrincipal) request.getUserPrincipal();
            
			SamlSession samlSession =(SamlSession) request.getSession().getAttribute(SamlSession.class.getName());
			user.setPreferredUserName(userPrincipal.getFriendlyAttribute("givenName") +" "+ userPrincipal.getFriendlyAttribute("surname"));
			user.setName(userPrincipal.getName());
			Role role = new Role();
			role.setNames(samlSession.getRoles());
			user.setRole(role);
			return user;
		}
		return null;
	}
	
	/**
	 * Adds the user object.
	 *
	 * @param model the model
	 * @param request the request
	 */
	protected void addUserObject(ModelAndView model,HttpServletRequest request){
		model.addObject("user", getUserProfile(request));
		model.addObject("userJson", JsonConverterUtil.convertToJsonWithoutNull(getUserProfile(request)));
	}
	
	protected void addUserObject(ModelAndView model,UserProfile user){
		model.addObject("user", user);
		model.addObject("userJson", JsonConverterUtil.convertToJsonWithoutNull(user));
	}
	
	protected UserProfile getUserWithPermission(HttpServletRequest request) throws CompliancePortalException {
		UserProfile user = getUserProfile(request);
		service.getUserFunctions(user);
		service.getUserPermissionsByRoleAndView(user);
		return user;
	}

}
