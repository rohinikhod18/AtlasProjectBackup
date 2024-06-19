package com.currenciesdirect.gtg.compliance.iam.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.Function;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.iam.dbport.IIAMDBService;

/**
 * The Class IAMServiceImpl.
 */
@Component("IAMServiceImpl")
public class IAMServiceImpl implements IIAMService {

	private Logger log = LoggerFactory.getLogger(IAMServiceImpl.class);
	
	@Autowired
	@Qualifier("IAMDBServiceImpl")
	private IIAMDBService service;
	
	
	@Override
	public List<Function> getUserFunctions(UserProfile user) throws CompliancePortalException {
		try {
			service.getUserRoleDetails(user);
			return user.getRole().getFunctions();
		} catch (CompliancePortalException e) {
			logError(e.getOrgException());
			throw e;
		} catch (Exception e) {
			logError(e);
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	private void logError(Throwable t) {
		log.error("Exception: ", t);
	}

	/*
	 * 1. user permission director used to construct user permissions from the builder
	 * 
	 */
	@Override
	public UserPermission getUserPermissionsByRoleAndView(UserProfile user) throws CompliancePortalException {
		
		UserPermissionDirector permissionDirector = new UserPermissionDirector();
		permissionDirector.constructUserPermissions(user);
		user.setPermissions(permissionDirector.getUserPermission());
		return user.getPermissions();
	}
}
