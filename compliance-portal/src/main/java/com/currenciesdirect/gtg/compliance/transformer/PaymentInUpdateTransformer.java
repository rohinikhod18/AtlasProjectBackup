package com.currenciesdirect.gtg.compliance.transformer;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.util.StringUtils;


@Component("paymentInUpdateTransformer")
public class PaymentInUpdateTransformer extends BaseUpdateTransformer implements ITransform<PaymentInUpdateDBDto, PaymentInUpdateRequest> {

	@Override
	public PaymentInUpdateDBDto transform(PaymentInUpdateRequest request) {
		
		PaymentInUpdateDBDto paymentInUpdateDBDto = new PaymentInUpdateDBDto();
		paymentInUpdateDBDto.setAccountId(request.getAccountId());
		paymentInUpdateDBDto.setContactId(request.getContactId());
		paymentInUpdateDBDto.setAccountSfId(request.getAccountSfId());
		paymentInUpdateDBDto.setContactSfId(request.getContactSfId());
		paymentInUpdateDBDto.setPaymentInId(request.getPaymentinId());
		paymentInUpdateDBDto.setOrgCode(request.getOrgCode());
		paymentInUpdateDBDto.setComment(request.getComment());
		paymentInUpdateDBDto.setCreatedBy(getUserName(request));
		paymentInUpdateDBDto.setOverallPaymentInWatchlistStatus(Boolean.TRUE.equals(request.getOverallWatchlistStatus()) ? ServiceStatusEnum.PASS : ServiceStatusEnum.FAIL);
		paymentInUpdateDBDto.setAddWatchlist(new ArrayList<String>());
		paymentInUpdateDBDto.setDeletedWatchlist(new ArrayList<String>());
		paymentInUpdateDBDto.setPreviousReason(new ArrayList<String>());
		paymentInUpdateDBDto.setAddReasons(new ArrayList<String>());
		paymentInUpdateDBDto.setDeletedReasons(new ArrayList<String>());
		paymentInUpdateDBDto.setActivityLogs(new ArrayList<PaymentInActivityLogDto>());
		paymentInUpdateDBDto.setTradeContactid(request.getTradeContactId());
		paymentInUpdateDBDto.setTradeContractnumber(request.getTradeContractNumber());
		paymentInUpdateDBDto.setTradePaymentid(request.getTradePaymentId());
		paymentInUpdateDBDto.setWatchlist(request.getWatchlist());
		paymentInUpdateDBDto.setCustType(request.getCustType());
		paymentInUpdateDBDto.setIsPaymentOnQueue(getPaymentQueueStatus(request));
		paymentInUpdateDBDto.setIsRequestFromQueue(request.getIsOnQueue());
		paymentInUpdateDBDto.setFragusterEventServiceLogId(request.getFragusterEventServiceLogId());
		paymentInUpdateDBDto.setUserResourceId(request.getUserResourceId());
		
		handleWatchlistUpdateRequest(request.getWatchlist(), paymentInUpdateDBDto.getAddWatchlist(), //common
				paymentInUpdateDBDto.getDeletedWatchlist());
		handlePaymentStatusReasonUpdateRequest(request.getStatusReasons(), paymentInUpdateDBDto.getAddReasons(), //common
				paymentInUpdateDBDto.getDeletedReasons(),paymentInUpdateDBDto.getPreviousReason());
		paymentInUpdateDBDto.setPaymentInStatus(
				handlePaymentInStatusRequest(request.getPrePaymentInStatus(), request.getUpdatedPaymentInStatus())); //common
		PaymentInActivityLogDto contactStatusActivity = getPaymentInStatusUpdateActivityLog(request, paymentInUpdateDBDto);//this is different functionality.while all above are common functionality
		if (contactStatusActivity != null) {
			paymentInUpdateDBDto.getActivityLogs().add(contactStatusActivity);
		}
		if(contactStatusActivity==null)
		{
			PaymentInActivityLogDto addCommentActivity =getAddCommentActivityLog(request);
			if(addCommentActivity!=null) {
				paymentInUpdateDBDto.getActivityLogs().add(addCommentActivity);
			}
		}
		return paymentInUpdateDBDto;
	}

	private Boolean getPaymentQueueStatus(PaymentInUpdateRequest request) {
		
		Boolean isOnQueue;
		
		if(Boolean.TRUE.equals(request.getIsOnQueue())){
			if(request.getUpdatedPaymentInStatus().equalsIgnoreCase(PaymentComplianceStatus.SEIZE.name())
					|| request.getUpdatedPaymentInStatus().equalsIgnoreCase(PaymentComplianceStatus.CLEAR.name())
					|| request.getUpdatedPaymentInStatus().equalsIgnoreCase(PaymentComplianceStatus.REJECT.name())
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
	 * @param prePaymentInStatus
	 * @param updatedPaymentInStatus
	 * @return updatedPaymentInStatus
	 */
	private String handlePaymentInStatusRequest(String prePaymentInStatus, String updatedPaymentInStatus) {
	
		if (updatedPaymentInStatus == null) {
			return null;
		}

		if (prePaymentInStatus != null && prePaymentInStatus.equals(updatedPaymentInStatus)) {
			return null;
		}
		return updatedPaymentInStatus;
	}

	private PaymentInActivityLogDto getPaymentInStatusUpdateActivityLog(PaymentInUpdateRequest request,PaymentInUpdateDBDto paymentInUpdateDBDto) {
		String log = getActivityLogsForPaymentInStatus(request.getPrePaymentInStatus(), paymentInUpdateDBDto.getPaymentInStatus());
		if (log != null) {
			return getPaymentInActivityLog(request, log, ActivityType.PAYMENT_IN_STATUS_UPDATE);
		}
		return null;
	}

	private PaymentInActivityLogDto getPaymentInActivityLog(PaymentInUpdateRequest request, String log,ActivityType paymentInActivityUpdate) {
		
		PaymentInActivityLogDto activityLogDto = new PaymentInActivityLogDto();
		activityLogDto.setAccountId(request.getAccountId());
		activityLogDto.setPaymentInId(request.getPaymentinId());
		activityLogDto.setOrgCode(request.getOrgCode());
		activityLogDto.setComment(request.getComment());
		activityLogDto.setActivityBy(getUserName(request));
		activityLogDto.setCreatedBy(getUserName(request));
		activityLogDto.setUpdatedBy(getUserName(request));
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		activityLogDto.setCreatedOn(timestamp);
		activityLogDto.setTimeStatmp(timestamp);
		activityLogDto.setUpdatedOn(timestamp);
		PaymentInActivityLogDetailDto activityLogDetailDto = new PaymentInActivityLogDetailDto();
		activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		activityLogDetailDto.setActivityType(paymentInActivityUpdate);
		activityLogDetailDto.setLog(log);
		activityLogDetailDto.setCreatedBy(getUserName(request));
		activityLogDetailDto.setUpdatedBy(getUserName(request));
		activityLogDetailDto.setCreatedOn(timestamp);
		activityLogDetailDto.setUpdatedOn(timestamp);
		return activityLogDto;
	}

	private String getActivityLogsForPaymentInStatus(String prePaymentInStatus, String updatePaymentInStatus) {
		String updatePaymentOutFormat = null;
		if (updatePaymentInStatus != null) {
			updatePaymentOutFormat = ActivityTemplateEnum.PAYMENT_IN_STATUS.getTemplate();
			updatePaymentOutFormat = updatePaymentOutFormat.replace("{PRE_STATUS}", prePaymentInStatus).replace("{UPDATED_STATUS}",updatePaymentInStatus);
		}
		return updatePaymentOutFormat;
	}
	
	/**added by neelesh pant*/
	private PaymentInActivityLogDto getAddCommentActivityLog(PaymentInUpdateRequest request)
	{
		String log=getActivitiesLogsForAddComment(request.getComment());
		if(log!=null)
		{
			return getPaymentInActivityLog(request, log,ActivityType.PAYMENT_IN_ADD_COMMENT);
		}
		return null;
	}
	/**added by neelesh pant*/
	
	private String getActivitiesLogsForAddComment(String comment)
	{
		String updateContactFormat = null;
		if(comment!=null)
		 updateContactFormat=ActivityTemplateEnum.ADD_COMMENT.getTemplate();
		
		return updateContactFormat;
			
		    }
}
