package com.currenciesdirect.gtg.compliance.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserPermission;

/**
 * The Class UserPermissionFilterBuilder.
 */
public class UserPermissionFilterBuilder {

	private static Logger log = LoggerFactory.getLogger(UserPermissionFilterBuilder.class);
	
	private UserPermissionFilterBuilder(){
		
	}
	
	/**
	 * Gets the filter from user permission.
	 *
	 * @param <T> the generic type
	 * @param userPermission the user permission
	 * @param clazz the clazz
	 * @return the filter from user permission
	 */
	public static <T extends BaseFilter> T getFilterFromUserPermission(UserPermission userPermission , Class<T> clazz){
		T filter = null;
		try {
			filter = clazz.newInstance();
			handleKyc(filter, userPermission);
			handleFraugster(filter, userPermission);
			handleBlacklist(filter, userPermission);
			handleSanction(filter, userPermission);
			handleCustomCheck(filter, userPermission);
			handleCustType(filter, userPermission);		
		} catch (Exception e) {
			log.error("Exception :" , e);
		}
		
		return filter;
	}
	
	
	/**
	 * Override user permission on filter.
	 *
	 * @param filter the filter
	 * @param userPermission the user permission
	 */
	public static void overrideUserPermissionOnFilter(BaseFilter filter, UserPermission userPermission) {
		handleKyc(filter, userPermission);
		handleFraugster(filter, userPermission);
		handleBlacklist(filter, userPermission);
		handleSanction(filter, userPermission);
		handleCustomCheck(filter, userPermission);
		handleCustType(filter, userPermission);
	}
	
	private static void handleKyc(BaseFilter filter,UserPermission userPermission){
		if(!userPermission.getCanManageEID()){
			filter.setKycStatus(new String[]{Constants.PASS});
		}
	}
	
	private static void handleFraugster(BaseFilter filter,UserPermission userPermission){
		if(!userPermission.getCanManageFraud()){
			filter.setFraugsterStatus(new String[]{Constants.PASS});
		}
	}
	
	private static void handleBlacklist(BaseFilter filter,UserPermission userPermission){
		if(!userPermission.getCanManageBlackList()){
			filter.setBlacklistStatus(new String[]{Constants.PASS});
		}
	}
	
	private static void handleSanction(BaseFilter filter,UserPermission userPermission){
		if(!userPermission.getCanManageSanction()){
			filter.setSanctionStatus(new String[]{Constants.PASS});
		}
	}
	
	private static void handleCustomCheck(BaseFilter filter,UserPermission userPermission){
		if(!userPermission.getCanManageCustom()){
			filter.setCustomCheckStatus(new String[]{Constants.PASS});
		}
	}
	
	private static void handleCustType(BaseFilter filter,UserPermission userPermission){
		if(!userPermission.getCanWorkOnCFX() && !userPermission.getCanWorkOnPFX()){
			filter.setExcludeCustType(new String[]{Constants.CUST_TYPE_PFX,Constants.CUST_TYPE_CFX});
			filter.setCustType(null);
		} else if(!userPermission.getCanWorkOnCFX()){
			filter.setExcludeCustType(new String[]{Constants.CUST_TYPE_CFX});
			filter.setCustType(null);
		} else if(!userPermission.getCanWorkOnPFX()){
			filter.setExcludeCustType(new String[]{Constants.CUST_TYPE_PFX});
			filter.setCustType(null);
		}
	}
}
