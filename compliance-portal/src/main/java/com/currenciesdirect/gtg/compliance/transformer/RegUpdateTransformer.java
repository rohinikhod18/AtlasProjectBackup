package com.currenciesdirect.gtg.compliance.transformer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceStatus;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.WatchlistUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class RegUpdateTransformer.
 */
@Component("regUpdateTransformer")
public class RegUpdateTransformer implements ITransform<RegUpdateDBDto, RegistrationUpdateRequest> {

	public static final String PRE_WATCHLIST = "{PRE_WATCHLIST}";
	public static final String CURRENT_WATCHLIST = "{CURRENT_WATCHLIST}";

	private String getUserName(RegistrationUpdateRequest request) {
		if (request.getUser() != null) {
			return request.getUser().getName();
		}
		return null;
	}

	@Override
	public RegUpdateDBDto transform(RegistrationUpdateRequest request) {
		RegUpdateDBDto requestHandler = new RegUpdateDBDto();
		requestHandler.setAccountId(request.getAccountId());
		requestHandler.setContactId(request.getContactId());
		requestHandler.setContactSfId(request.getContactSfId());
		requestHandler.setAccountSfId(request.getAccountSfId());
		requestHandler.setOrgCode(request.getOrgCode());
		requestHandler.setComment(request.getComment());
		requestHandler.setComplianceLog(request.getComplianceLog());
		requestHandler.setFragusterEventServiceLogId(request.getFraugsterEventServiceLogId());
		requestHandler.setOverallPaymentInWatchlistStatus(Boolean.TRUE.equals(request.getOverallWatchlistStatus()) ? ServiceStatusEnum.PASS : ServiceStatusEnum.FAIL);
		requestHandler.setOverallPaymentOutWatchlistStatus(Boolean.TRUE.equals(request.getOverallWatchlistStatus()) ? ServiceStatusEnum.PASS : ServiceStatusEnum.FAIL);
		requestHandler.setCreatedBy(getUserName(request));
		requestHandler.setAddWatchlist(new ArrayList<String>());
		requestHandler.setDeletedWatchlist(new ArrayList<String>());
		requestHandler.setAddReasons(new ArrayList<String>());
		requestHandler.setDeletedReasons(new ArrayList<String>());
		requestHandler.setActivityLog(new ArrayList<ProfileActivityLogDto>());
		requestHandler.setCustType(request.getCustType());
		requestHandler.setUserResourceId(request.getUserResourceId());
		
		handleWatchlistUpdateRequest(request.getWatchlist(), requestHandler.getAddWatchlist(),
				requestHandler.getDeletedWatchlist());
		handleContactStatusReasonUpdateRequest(request.getContactStatusReasons(), requestHandler.getAddReasons(),
				requestHandler.getDeletedReasons());
		requestHandler.setContactStatus(
				handleContactStatusRequest(request.getPreContactStatus(), request.getUpdatedContactStatus()));
		requestHandler.setAccountStatus(handleAccountStatusRequest(request.getPreAccountStatus(),request.getUpdatedAccountStatus()));
		ProfileActivityLogDto watchlistActivity = getProfileWatchlistActivityLog(request, requestHandler);
		if (watchlistActivity != null) {
			requestHandler.getActivityLog().add(watchlistActivity);
		}
		ProfileActivityLogDto contactStatusActivity = getProfileContactStatusUpdateActivityLog(request, requestHandler);
		
		ProfileActivityLogDto accountStatusActivity = getProfileAccountStatusUpdateActivityLog(request, requestHandler);
		
		if (contactStatusActivity != null) {
			requestHandler.getActivityLog().add(contactStatusActivity);
		}
		
		if(accountStatusActivity !=null){
			requestHandler.getActivityLog().add(accountStatusActivity);
		}
		/**added by neelesh Pant
		 * added to create activity log even if status of account or Contact is not changed or Watchlist reason is not changed**/
		
		if((contactStatusActivity==null && accountStatusActivity==null && watchlistActivity==null) && request.getComment()!=null)
		{
			ProfileActivityLogDto addCommentActivity =getAddCommentActivityLog(request);
			if(addCommentActivity!=null)
				requestHandler.getActivityLog().add(addCommentActivity);	
		}
		
		if(request.getComplianceLog() !=null && !request.getComplianceLog().isEmpty()){
			ProfileActivityLogDto addCompliacneLogActivity =getAddComplianceLog(request);
			if(addCompliacneLogActivity != null){
				requestHandler.getActivityLog().add(addCompliacneLogActivity);
			}
		}
		
		requestHandler.setComplianceDoneOn(request.getComplianceDoneOn());	
		requestHandler.setRegistrationInDate(request.getRegistrationInDate());
		requestHandler.setComplianceExpiry(request.getComplianceExpiry());
		requestHandler.setIsContactOnQueue(getContactQueueStatus(request,requestHandler));
		requestHandler.setIsAccountOnQueue(getAccountQueueStatus(request,requestHandler));
		requestHandler.setIsRequestFromQueue(request.getIsOnQueue());
		
		return requestHandler;
	}


	private Boolean getAccountQueueStatus(RegistrationUpdateRequest request, RegUpdateDBDto requestHandler) {
		Boolean isOnQueue = Boolean.FALSE;
		if (request.getCustType().equalsIgnoreCase(Constants.CUST_TYPE_CFX)) {
			isOnQueue = getCFXIsOnQueueStatus(request);
			requestHandler.setIsContactOnQueue(Boolean.FALSE);
		}
		return isOnQueue;
	}

	private Boolean getContactQueueStatus(RegistrationUpdateRequest request, RegUpdateDBDto requestHandler) {
		Boolean isOnQueue = Boolean.FALSE;
		
		if (request.getCustType().equalsIgnoreCase(Constants.CUST_TYPE_PFX)) {
			isOnQueue = getPfxIsOnQueueStatus(request);
			requestHandler.setIsAccountOnQueue(Boolean.FALSE);
		}
		return isOnQueue;
	}

	private Boolean getCFXIsOnQueueStatus(RegistrationUpdateRequest request) {
		Boolean isOnQueue;
		if (Boolean.TRUE.equals(request.getIsOnQueue())) {

			if (request.getUpdatedAccountStatus().equalsIgnoreCase(ComplianceStatus.ACTIVE.name())
					|| request.getUpdatedAccountStatus().equalsIgnoreCase(ComplianceStatus.REJECTED.name())
					|| !StringUtils.isNullOrEmpty(request.getComment())) {
				isOnQueue = Boolean.FALSE;
			} else {
				isOnQueue = Boolean.TRUE;
			}
		} else {
			isOnQueue = Boolean.FALSE;
		}
		return isOnQueue;
	}

	private Boolean getPfxIsOnQueueStatus(RegistrationUpdateRequest request) {
		Boolean isOnQueue;
		if (Boolean.TRUE.equals(request.getIsOnQueue())) {

			if (request.getUpdatedContactStatus().equalsIgnoreCase(ComplianceStatus.ACTIVE.name())
					|| request.getUpdatedContactStatus().equalsIgnoreCase(ComplianceStatus.REJECTED.name())
					|| !StringUtils.isNullOrEmpty(request.getComment())) {
				isOnQueue = Boolean.FALSE;
			} else {
				isOnQueue = Boolean.TRUE;
			}
		} else {
			isOnQueue = Boolean.FALSE;
		}
		return isOnQueue;
	}

	/**
	 * Handle watchlist update request.
	 *
	 * @param request
	 *            the request
	 * @param addWatchlist
	 *            the add watchlist
	 * @param deleteWatchlist
	 *            the delete watchlist
	 */
	private void handleWatchlistUpdateRequest(WatchlistUpdateRequest[] request, List<String> addWatchlist,
			List<String> deleteWatchlist) {
        
		for (WatchlistUpdateRequest watchlist : request) {
			 boolean preVal = false; 
			 boolean updatedVal = false;
			if(watchlist.getPreValue() != null){
				  preVal = watchlist.getPreValue();
			 }
			 if(watchlist.getUpdatedValue() !=null){
				  updatedVal = watchlist.getUpdatedValue();
			 }
			
			if (watchlist.getName() == null || watchlist.getName().isEmpty()
					|| (preVal == updatedVal)) {
				continue;
			}

			if (preVal && !updatedVal) {
				deleteWatchlist.add(watchlist.getName());
			} else {
				addWatchlist.add(watchlist.getName());
			}
		}
	}

	/**
	 * Handle contact status reason update request.
	 *
	 * @param request
	 *            the request
	 * @param addReasons
	 *            the add reasons
	 * @param deleteReasons
	 *            the delete reasons
	 */
	private void handleContactStatusReasonUpdateRequest(StatusReasonUpdateRequest[] request, List<String> addReasons,
			List<String> deleteReasons) {

		for (StatusReasonUpdateRequest reason : request) {

			if (reason.getName() == null || reason.getName().isEmpty()
					|| reason.getPreValue().equals(reason.getUpdatedValue())) {
				continue;
			}

			if (Boolean.TRUE.equals(reason.getPreValue()) && Boolean.FALSE.equals(reason.getUpdatedValue())) {
				deleteReasons.add(reason.getName());
			} else {
				addReasons.add(reason.getName());
			}
		}
	}

	/**
	 * Handle contact status request.
	 *
	 * @param preStatus
	 *            the pre status
	 * @param updatedStatus
	 *            the updated status
	 * @return the string
	 */
	private String handleContactStatusRequest(String preStatus, String updatedStatus) {

		if (updatedStatus == null) {
			return null;
		}

		if (preStatus != null && preStatus.equals(updatedStatus)) {
			return null;
		}
		return updatedStatus;
	}
	
	private String handleAccountStatusRequest(String preStatus, String updatedAccountStatus) {

		if (updatedAccountStatus == null) {
			return null;
		}

		if (preStatus != null && preStatus.equals(updatedAccountStatus)) {
			return null;
		}
		return updatedAccountStatus;
	}

	/**
	 * Gets the profile watchlist activity log.
	 *
	 * @param request
	 *            the request
	 * @param requestHandler
	 *            the request handler
	 * @return the profile watchlist activity log
	 */
	private ProfileActivityLogDto getProfileWatchlistActivityLog(RegistrationUpdateRequest request,
			RegUpdateDBDto requestHandler) {
		String log = getLogFromWatchlist(request.getWatchlist(), requestHandler.getAddWatchlist(),
				requestHandler.getDeletedWatchlist());
		if (log != null) {
			return getProfileActivityLog(request, log, ActivityType.PROFILE_WATCHLIST);
		}
		return null;
	}

	/**
	 * Gets the profile contact status update activity log.
	 *
	 * @param request
	 *            the request
	 * @param requestHandler
	 *            the request handler
	 * @return the profile contact status update activity log
	 */
	private ProfileActivityLogDto getProfileContactStatusUpdateActivityLog(RegistrationUpdateRequest request,
			RegUpdateDBDto requestHandler) {
		String log = getActivityLogsForContactStaus(request.getPreContactStatus(), requestHandler.getContactStatus());
		if (log != null) {
			return getProfileActivityLog(request, log, ActivityType.PROFILE_CONTACT_STATUS_UPDATE);
		}
		return null;
	}
	
	private ProfileActivityLogDto getProfileAccountStatusUpdateActivityLog(RegistrationUpdateRequest request,
			RegUpdateDBDto requestHandler) {
		String log = getActivityLogsForAccountStaus(request.getPreAccountStatus(), requestHandler.getAccountStatus());
		if (log != null) {
			return getProfileActivityLog(request, log, ActivityType.PROFILE_ACCOUNT_STATUS_UPDATE);
		}
		return null;
	}
	
	/**
	 * Gets the profile activity log.
	 *
	 * @param request
	 *            the request
	 * @param log
	 *            the log
	 * @param activityType
	 *            the activity type
	 * @return the profile activity log
	 */
	private ProfileActivityLogDto getProfileActivityLog(RegistrationUpdateRequest request, String log,
			ActivityType activityType) {

		ProfileActivityLogDto activityLogDto = new ProfileActivityLogDto();
		activityLogDto.setAccountId(request.getAccountId());
		if(("PFX").equalsIgnoreCase(request.getCustType().toUpperCase().trim())){
			activityLogDto.setContactId(request.getContactId());
		}
		activityLogDto.setOrgCode(request.getOrgCode());
		activityLogDto.setComment(request.getComment());
		activityLogDto.setActivityBy(getUserName(request));
		activityLogDto.setCreatedBy(getUserName(request));
		activityLogDto.setUpdatedBy(getUserName(request));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		activityLogDto.setCreatedOn(timestamp);
		activityLogDto.setTimeStatmp(timestamp);
		activityLogDto.setUpdatedOn(timestamp);
		ActivityLogDetailDto activityLogDetailDto = new ActivityLogDetailDto();
		activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		activityLogDetailDto.setActivityType(activityType);
		activityLogDetailDto.setLog(log);
		activityLogDetailDto.setCreatedBy(getUserName(request));
		activityLogDetailDto.setUpdatedBy(getUserName(request));
		activityLogDetailDto.setCreatedOn(timestamp);
		activityLogDetailDto.setUpdatedOn(timestamp);
		return activityLogDto;
	}

	/**
	 * Gets the log from watchlist.
	 *
	 * @param request
	 *            the request
	 * @param addedWatchlists
	 *            the added watchlists
	 * @param deletedWatchlists
	 *            the deleted watchlists
	 * @return the log from watchlist
	 */
	private String getLogFromWatchlist(WatchlistUpdateRequest[] request, List<String> addedWatchlists,
			List<String> deletedWatchlists) {
		String preWatchlist = getPreviousWatchlist(request);
		if (addedWatchlists.isEmpty() && deletedWatchlists.isEmpty()) {
			return null;
		} else if (!addedWatchlists.isEmpty() && !deletedWatchlists.isEmpty()) {
			return getActivityLogsForAddAndDeleteWatchlist(preWatchlist, addedWatchlists, deletedWatchlists);
		} else if (!addedWatchlists.isEmpty()) {
			return getActivityLogsForAddedWatchlist(preWatchlist, addedWatchlists);
		} else {
			return getActivityLogsForDeletedWatchlist(preWatchlist, deletedWatchlists);
		}
	}

	/**
	 * Gets the activity logs for deleted watchlist.
	 *
	 * @param preWatchlist
	 *            the pre watchlist
	 * @param deletedWatchlistList
	 *            the deleted watchlist list
	 * @return the activity logs for deleted watchlist
	 */
	public static String getActivityLogsForDeletedWatchlist(String preWatchlist, List<String> deletedWatchlistList) {
		if(null == preWatchlist)
			return null;
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
		ArrayList<String> current = new ArrayList<>();
		for (String s : arrayList) {
			if (!deletedWatchlistList.contains(s)) {
				current.add(s);
			}
		}
		
		String prevWatch = preWatchlist.replace(",", ", ");
		if(current.isEmpty()){
			return ActivityTemplateEnum.ALL_WATHCLIST_DELETED.getTemplate().replace(PRE_WATCHLIST, prevWatch);
					
		}
		
		String currents = current.toString();
		currents = currents.substring(1, currents.length() - 1).replace(", ", ", ");
		return ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, prevWatch)
				.replace(CURRENT_WATCHLIST, currents);
	}

	/**
	 * Gets the activity logs for added watchlist.
	 *
	 * @param preWatchlist
	 *            the pre watchlist
	 * @param addedWatchlistList
	 *            the added watchlist list
	 * @return the activity logs for added watchlist
	 */
	private String getActivityLogsForAddedWatchlist(String preWatchlist, List<String> addedWatchlistList) {
		String addWatchlistFormat = null;
		ArrayList<String> current = new ArrayList<>();
		if (preWatchlist != null && !preWatchlist.isEmpty()) {
			ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
			current.addAll(arrayList);
			for (String s : addedWatchlistList) {
				current.add(s);
			}
		} else {
			current.addAll(addedWatchlistList);
		}

		String currents = current.toString();
		currents = currents.substring(1, currents.length() - 1).replace(", ", ", ");
		
		if (preWatchlist != null) {
			String prevWatch = preWatchlist.replace(",", ", ");
			addWatchlistFormat = ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, prevWatch);
		} else {
			addWatchlistFormat = ActivityTemplateEnum.ADD_WATCHLIST.getTemplate();
		}
		return addWatchlistFormat.replace(CURRENT_WATCHLIST, currents);
	}

	/**
	 * Gets the activity logs for add and delete watchlist.
	 *
	 * @param preWatchlist
	 *            the pre watchlist
	 * @param addedWatchlistList
	 *            the added watchlist list
	 * @param delWatchlistList
	 *            the del watchlist list
	 * @return the activity logs for add and delete watchlist
	 */
	private String getActivityLogsForAddAndDeleteWatchlist(String preWatchlist, List<String> addedWatchlistList,
			List<String> delWatchlistList) {
		if(null == preWatchlist)
			return null;
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
		ArrayList<String> current = new ArrayList<>();
		current.addAll(arrayList);
		for (String s : addedWatchlistList) {
			current.add(s);
		}
		for (String s : arrayList) {
			if (delWatchlistList.contains(s)) {
				current.remove(s);
			}
		}
		String currents = current.toString();
		String prevWatch = preWatchlist.replace(",", ", ");
		currents = currents.substring(1, currents.length() - 1);
		return ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, prevWatch)
				.replace(CURRENT_WATCHLIST, currents);
	}

	/**
	 * Gets the activity logs for contact staus.
	 *
	 * @param preStatus
	 *            the pre status
	 * @param updateStatus
	 *            the update status
	 * @return the activity logs for contact staus
	 */
	private String getActivityLogsForContactStaus(String preStatus, String updateStatus) {
		String updateContactFormat = null;
		if (updateStatus != null) {
			updateContactFormat = ActivityTemplateEnum.UPDATE_CONTACT_STATUS.getTemplate();
			updateContactFormat = updateContactFormat.replace("{PRE_STATUS}", preStatus).replace("{UPDATED_STATUS}",
					updateStatus);
		}
		return updateContactFormat;
	}
	
	private String getActivityLogsForAccountStaus(String preStatus, String updateStatus) {
		String updateContactFormat = null;
		if (updateStatus != null) {
			updateContactFormat = ActivityTemplateEnum.UPDATE_ACCOUNT_STATUS.getTemplate();
			updateContactFormat = updateContactFormat.replace("{PRE_STATUS}", preStatus).replace("{UPDATED_STATUS}",
					updateStatus);
		}
		return updateContactFormat;
	}

	/**
	 * Gets the previous watchlist.
	 *
	 * @param request
	 *            the request
	 * @return the previous watchlist
	 */
	private String getPreviousWatchlist(WatchlistUpdateRequest[] request) {
		StringBuilder preWatchlist = new StringBuilder();
		boolean isEle = false;
		for (WatchlistUpdateRequest watchlist : request) {

			if (watchlist.getName() == null || watchlist.getName().isEmpty()) {
				continue;
			}

			if (Boolean.TRUE.equals(watchlist.getPreValue())) {
				isEle = true;
				preWatchlist.append(watchlist.getName().concat(","));
			}
		}
		if (isEle) {
			preWatchlist.setLength(preWatchlist.length() - 1);
		}
		if (preWatchlist.length() != 0) {
			return preWatchlist.toString();
		}
		return null;

	}
	//added By Neelesh 
			private ProfileActivityLogDto getAddCommentActivityLog(RegistrationUpdateRequest request)
			{
				String log=getActivitiesLogsForAddComment(request.getComment());
				if(log!=null)
				{
					return getProfileActivityLog(request, log,ActivityType.PROFILE_ADDED_COMMENT);
				}
				return null;
			}

	//added by neelesh pant
		private String getActivitiesLogsForAddComment(String comment)
		{
			String updateContactFormat = null;
			if(comment!=null)
			 updateContactFormat=ActivityTemplateEnum.ADD_COMMENT.getTemplate();
			
			return updateContactFormat;
				
			    }
		
		private ProfileActivityLogDto getAddComplianceLog(RegistrationUpdateRequest request){
			String log=getActivitiesLogsForAddComplianceLog(request.getComplianceLog());
			if(log!=null)
			{
				ProfileActivityLogDto activityLog=getProfileActivityLog(request, log,ActivityType.PROFILE_ADDED_COMPLIANCE_LOG);
				activityLog.setComment(request.getComplianceLog());
				activityLog.setContactId(null);
				return activityLog;
			}
			return null;
		}
		
		private String getActivitiesLogsForAddComplianceLog(String complianceLog)
		{
			String updateContactFormat = null;
			if(complianceLog!=null)
			 updateContactFormat=ActivityTemplateEnum.COMPLIANCE_LOG.getTemplate();
			
			return updateContactFormat;
				
		 }
		
}