package com.currenciesdirect.gtg.compliance.transformer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class RegUpdateTransformer.
 */
@Component("paymentOutUpdateTransformer")
public class PaymentOutUpdateTransformer extends BaseUpdateTransformer implements ITransform<PaymentOutUpdateDBDto, PaymentOutUpdateRequest> {

	public static final String PRE_WATCHLIST = "{PRE_WATCHLIST}";
	public static final String CURRENT_WATCHLIST = "{CURRENT_WATCHLIST}";

	@Override
	public PaymentOutUpdateDBDto transform(PaymentOutUpdateRequest request) {
		PaymentOutUpdateDBDto requestHandler = new PaymentOutUpdateDBDto();
		requestHandler.setAccountId(request.getAccountId());
		requestHandler.setContactId(request.getContactId());
		requestHandler.setAccountSfId(request.getAccountSfId());
		requestHandler.setContactSfId(request.getContactSfId());
		requestHandler.setPaymentOutId(request.getPaymentOutId());
		requestHandler.setOrgCode(request.getOrgCode());
		requestHandler.setComment(request.getComment());
		requestHandler.setCreatedBy(getUserName(request));
		requestHandler.setOverallPaymentOutWatchlistStatus(Boolean.TRUE.equals(request.getOverallWatchlistStatus()) ? ServiceStatusEnum.PASS : ServiceStatusEnum.FAIL);
		requestHandler.setAddWatchlist(new ArrayList<String>());
		requestHandler.setDeletedWatchlist(new ArrayList<String>());
		requestHandler.setPreviousReason(new ArrayList<String>());
		requestHandler.setAddReasons(new ArrayList<String>());
		requestHandler.setDeletedReasons(new ArrayList<String>());
		requestHandler.setActivityLogs(new ArrayList<PaymentOutActivityLogDto>());
		requestHandler.setTradeBeneficiayId(request.getTradeBeneficiayId());
		requestHandler.setTradeContactid(request.getTradeContactId());
		requestHandler.setTradeContractnumber(request.getTradeContractNumber());
		requestHandler.setTradePaymentid(request.getTradePaymentId());
		requestHandler.setWatchlist(request.getWatchlist());
		requestHandler.setCustType(request.getCustType());
		requestHandler.setIsPaymentOnQueue(getPaymentQueueStatus(request));
		requestHandler.setIsRequestFromQueue(request.getIsOnQueue());
		requestHandler.setFragusterEventServiceLogId(request.getFragusterEventServiceLogId());
		requestHandler.setUserResourceId(request.getUserResourceId());
		
		handleWatchlistUpdateRequest(request.getWatchlist(), requestHandler.getAddWatchlist(), //common
				requestHandler.getDeletedWatchlist());
		handlePaymentStatusReasonUpdateRequest(request.getStatusReasons(), requestHandler.getAddReasons(), //common
				requestHandler.getDeletedReasons(),requestHandler.getPreviousReason());
		
		requestHandler.setPaymentOutStatus(
				handlePaymentOutStatusRequest(request.getPrePaymentOutStatus(), request.getUpdatedPaymentOutStatus())); //common
		PaymentOutActivityLogDto contactStatusActivity = getPaymentOutStatusUpdateActivityLog(request, requestHandler);//this is different functionality.while all above are common functionality
		if (contactStatusActivity != null) {
			requestHandler.getActivityLogs().add(contactStatusActivity);
		}
		if(contactStatusActivity == null){
			PaymentOutActivityLogDto addCommentActivity =getAddCommentActivityLog(request);
			if(addCommentActivity!=null)
				requestHandler.getActivityLogs().add(addCommentActivity);	
		                }
		return requestHandler;
	}

	private Boolean getPaymentQueueStatus(PaymentOutUpdateRequest request) {
		
		Boolean isOnQueue;
		
		if(Boolean.TRUE.equals(request.getIsOnQueue())){
			if(request.getUpdatedPaymentOutStatus().equalsIgnoreCase(PaymentComplianceStatus.SEIZE.name())
					|| request.getUpdatedPaymentOutStatus().equalsIgnoreCase(PaymentComplianceStatus.CLEAR.name())
					|| request.getUpdatedPaymentOutStatus().equalsIgnoreCase(PaymentComplianceStatus.REJECT.name())
					|| !StringUtils.isNullOrEmpty(request.getComment())){
				isOnQueue=Boolean.FALSE;
			}else{
				isOnQueue=Boolean.TRUE;
			}
		}else{
				isOnQueue=Boolean.FALSE;
		}
		return isOnQueue;
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
	private String handlePaymentOutStatusRequest(String preStatus, String updatedStatus) {

		if (updatedStatus == null) {
			return null;
		}

		if (preStatus != null && preStatus.equals(updatedStatus)) {
			return null;
		}
		return updatedStatus;
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
	private PaymentOutActivityLogDto getPaymentOutStatusUpdateActivityLog(PaymentOutUpdateRequest request,
			PaymentOutUpdateDBDto requestHandler) {
		String log = getActivityLogsForPaymentOutStaus(request.getPrePaymentOutStatus(), requestHandler.getPaymentOutStatus());
		if (log != null) {
			return getPaymentOutActivityLog(request, log, ActivityType.PAYMENT_OUT_STATUS_UPDATE);
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
	private PaymentOutActivityLogDto getPaymentOutActivityLog(PaymentOutUpdateRequest request, String log,
			ActivityType activityType) {

		PaymentOutActivityLogDto activityLogDto = new PaymentOutActivityLogDto();
		activityLogDto.setAccountId(request.getAccountId());
		activityLogDto.setPaymentOutId(request.getPaymentOutId());
		activityLogDto.setOrgCode(request.getOrgCode());
		activityLogDto.setComment(request.getComment());
		activityLogDto.setActivityBy(getUserName(request));
		activityLogDto.setCreatedBy(getUserName(request));
		activityLogDto.setUpdatedBy(getUserName(request));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		activityLogDto.setCreatedOn(timestamp);
		activityLogDto.setTimeStatmp(timestamp);
		activityLogDto.setUpdatedOn(timestamp);
		PaymentOutActivityLogDetailDto activityLogDetailDto = new PaymentOutActivityLogDetailDto();
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
	 * Gets the activity logs for deleted watchlist.
	 *
	 * @param preWatchlist
	 *            the pre watchlist
	 * @param deletedWatchlistList
	 *            the deleted watchlist list
	 * @return the activity logs for deleted watchlist
	 */
	public static String getActivityLogsForDeletedWatchlist(String preWatchlist, List<String> deletedWatchlistList) {
		ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(preWatchlist.split(",")));
		ArrayList<String> current = new ArrayList<>();
		for (String s : arrayList) {
			if (!deletedWatchlistList.contains(s)) {
				current.add(s);
			}
		}
		String currents = current.toString();
		currents = currents.substring(1, currents.length() - 1).replace(", ", ",");
		return ActivityTemplateEnum.WATHCLIST.getTemplate().replace(PRE_WATCHLIST, preWatchlist)
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
	private String getActivityLogsForPaymentOutStaus(String preStatus, String updateStatus) {
		String updatePaymentOutFormat = null;
		if (updateStatus != null) {
			updatePaymentOutFormat = ActivityTemplateEnum.PAYMENT_OUT_STATUS.getTemplate();
			updatePaymentOutFormat = updatePaymentOutFormat.replace("{PRE_STATUS}", preStatus).replace("{UPDATED_STATUS}",
					updateStatus);
		}
		return updatePaymentOutFormat;
	}
	
	//added By Neelesh 
		private PaymentOutActivityLogDto getAddCommentActivityLog(PaymentOutUpdateRequest request)
		{
			String log=getActivitiesLogsForAddComment(request.getComment());
			if(log!=null)
			{
				return getPaymentOutActivityLog(request, log,ActivityType.PAYMENT_OUT_ADD_COMMENT);
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

}
