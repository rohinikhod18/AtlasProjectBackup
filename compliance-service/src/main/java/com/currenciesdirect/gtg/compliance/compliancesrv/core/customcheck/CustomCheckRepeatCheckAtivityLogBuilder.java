package com.currenciesdirect.gtg.compliance.compliancesrv.core.customcheck;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogData;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.customchecks.response.CustomCheckResendResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class CustomCheckRepeatCheckAtivityLogBuilder.
 */
public class CustomCheckRepeatCheckAtivityLogBuilder {
	
	
	/**
	 * Builds the activity logs for sanction repeat check and adds into message
	 *
	 * @param message the message
	 * @return the message
	 */
	@ServiceActivator
	public Message<MessageContext> buildActivityLogs(Message<MessageContext> message){
		
		UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		String userName = userProfile.getPreferredUserName();
		
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE);
		
		ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
		OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
		ActivityLogs activityLogs = null;
		String summary;
		CustomCheckResendResponse resendResponse = new CustomCheckResendResponse();
		if(ServiceInterfaceType.FUNDSOUT ==  eventType && OperationEnum.CUSTOMCHECK_RESEND == operation){
			CustomChecksRequest serviceRequest = exchange.getRequest(CustomChecksRequest.class );
			FundsOutRequest fundsOutRequest = (FundsOutRequest)serviceRequest.getESDocument();
			EventServiceLog beneLog = exchange.getEventServiceLog(
				ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, EntityEnum.BENEFICIARY.name(), 
				fundsOutRequest.getBeneficiary().getBeneficiaryId());
			List<PaymentOutActivityLogDto> activityLogDtos = getPaymentOutActivityLog(fundsOutRequest, userName, beneLog);
			if(!activityLogDtos.isEmpty()){
				activityLogs = getCCPaymentOutActivityLogsResponse(activityLogDtos);
			}
			summary = beneLog.getSummary();

			if(summary != null) {
				CustomCheckResponse customCheckSummary = JsonConverterUtil.convertToObject(CustomCheckResponse.class, summary);
				resendResponse.setResponse(customCheckSummary);
				resendResponse.setActivityLogs(activityLogs);
			}
			resendResponse.addAttribute("paymentOutActivities", activityLogDtos);
			resendResponse.setActivityLogs(activityLogs);
			resendResponse.setEventServiceLogId(beneLog.getEventServiceLogId());
			resendResponse.setCheckedOn(beneLog.getCreatedOn().toString());
		} else if(ServiceInterfaceType.FUNDSIN ==  eventType && OperationEnum.CUSTOMCHECK_RESEND == operation){
			CustomChecksRequest serviceRequest = exchange.getRequest(CustomChecksRequest.class );
			FundsInCreateRequest fundsInRequest = (FundsInCreateRequest)serviceRequest.getESDocument();
			EventServiceLog debtorLog = exchange.getEventServiceLog(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE, 
						EntityEnum.ACCOUNT.name(), fundsInRequest.getAccId());
			List<PaymentInActivityLogDto> activityLogDtos = getPaymentInActivityLog(fundsInRequest,userName,debtorLog);
			if( !activityLogDtos.isEmpty()){
				activityLogs = getPaymentInActivityLogsResponse(activityLogDtos);
			}
			summary = debtorLog.getSummary(); 

			if(summary != null) {
				CustomCheckResponse customCheckSummary = JsonConverterUtil.convertToObject(CustomCheckResponse.class, summary);
				resendResponse.setResponse(customCheckSummary);
				resendResponse.setActivityLogs(activityLogs);
			}
			resendResponse.addAttribute("paymentInActivities", activityLogDtos);
			resendResponse.setActivityLogs(activityLogs);
			resendResponse.setEventServiceLogId(debtorLog.getEventServiceLogId());
			resendResponse.setCheckedOn(debtorLog.getCreatedOn().toString());
		}
		
		message.getPayload().getGatewayMessageExchange().setResponse(resendResponse);
		return message;
	}
	
	
	
	private  List<PaymentOutActivityLogDto> getPaymentOutActivityLog(FundsOutRequest request,String userName,EventServiceLog log){
		
		List<PaymentOutActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			PaymentOutActivityLogDto ccPayOutActivityLog = new PaymentOutActivityLogDto();
			ccPayOutActivityLog.setPaymentOutId(request.getFundsOutId());
			ccPayOutActivityLog.setOrgCode(request.getOrgCode());
			ccPayOutActivityLog.setActivityBy(userName);
			ccPayOutActivityLog.setCreatedBy(userName);
			ccPayOutActivityLog.setUpdatedBy(userName);
			ccPayOutActivityLog.setCreatedOn(timestamp);
			ccPayOutActivityLog.setTimeStatmp(timestamp);
			ccPayOutActivityLog.setUpdatedOn(timestamp);
			PaymentOutActivityLogDetailDto ccPaymentOutActivityLogDetail = new PaymentOutActivityLogDetailDto();
			ccPayOutActivityLog.setActivityLogDetailDto(ccPaymentOutActivityLogDetail);
			ccPaymentOutActivityLogDetail.setActivityType(ActivityType.PAYMENT_OUT_CUSTOM_CHECK_RECHECK);
			ccPaymentOutActivityLogDetail.setLog(getPaymentOutCustomCheckLog(request,log));
			ccPaymentOutActivityLogDetail.setCreatedBy(userName);
			ccPaymentOutActivityLogDetail.setUpdatedBy(userName);
			ccPaymentOutActivityLogDetail.setCreatedOn(timestamp);
			ccPaymentOutActivityLogDetail.setUpdatedOn(timestamp);
			activityLogDtos.add(ccPayOutActivityLog);
		return activityLogDtos;
	}
	
	
	private ActivityLogs getCCPaymentOutActivityLogsResponse(List<PaymentOutActivityLogDto> ccPayOutActivityLog){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentOutActivityLogDto profileActivityLogDto : ccPayOutActivityLog) {
		    ActivityLogData ccPOutActivity = new ActivityLogData();
		    ccPOutActivity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			ccPOutActivity.setActivityType(ActivityType.PAYMENT_OUT_CUSTOM_CHECK_RECHECK.getModule() +" "+ActivityType.PAYMENT_OUT_CUSTOM_CHECK_RECHECK.getType());
			ccPOutActivity.setCreatedBy(profileActivityLogDto.getActivityBy());
			ccPOutActivity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(ccPOutActivity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private String getPaymentOutCustomCheckLog(FundsOutRequest request, EventServiceLog log) {

		EntityDetails entityDetails = (EntityDetails) request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		if (null == entityDetails) {
			entityDetails = new EntityDetails();
		}
		getDefaultEntityDetails(entityDetails);
		return ActivityTemplateEnum.RECHECK.getTemplate().replace("{RECHECK_SERVICE_NAME}", "Custom check")
				.replace("{PRE_STATUS}", entityDetails.getPrevStatus())
				.replace("{UPDATED_STATUS}", log.getStatus().toLowerCase())
				.replace("{ENTITY_NAME}", entityDetails.getBeneficiaryName());

	}
	
	private  List<PaymentInActivityLogDto> getPaymentInActivityLog(FundsInCreateRequest request,String userName,
		EventServiceLog log){
		
		List<PaymentInActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			PaymentInActivityLogDto ccPaymentInActivityLog = new PaymentInActivityLogDto();
			ccPaymentInActivityLog.setPaymentInId(request.getFundsInId());
			ccPaymentInActivityLog.setOrgCode(request.getOrgCode());
			ccPaymentInActivityLog.setActivityBy(userName);
			ccPaymentInActivityLog.setCreatedBy(userName);
			ccPaymentInActivityLog.setUpdatedBy(userName);
			ccPaymentInActivityLog.setCreatedOn(timestamp);
			ccPaymentInActivityLog.setTimeStatmp(timestamp);
			ccPaymentInActivityLog.setUpdatedOn(timestamp);
			PaymentInActivityLogDetailDto ccPaymentInActivityLogDetailDto = new PaymentInActivityLogDetailDto();
			ccPaymentInActivityLog.setActivityLogDetailDto(ccPaymentInActivityLogDetailDto);
			ccPaymentInActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_CUSTOM_CHECK_RECHECK);
			ccPaymentInActivityLogDetailDto.setLog(getPaymentInCustomCheckLog(request,log));
			ccPaymentInActivityLogDetailDto.setCreatedBy(userName);
			ccPaymentInActivityLogDetailDto.setUpdatedBy(userName);
			ccPaymentInActivityLogDetailDto.setCreatedOn(timestamp);
			ccPaymentInActivityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(ccPaymentInActivityLog);
		return activityLogDtos;
	}
	
	
	private ActivityLogs getPaymentInActivityLogsResponse(List<PaymentInActivityLogDto> ccPaymentInActivityLog){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentInActivityLogDto profileActivityLogDto : ccPaymentInActivityLog) {
		    ActivityLogData ccPInActivity = new ActivityLogData();
		    ccPInActivity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			ccPInActivity.setActivityType(ActivityType.PAYMENT_IN_CUSTOM_CHECK_RECHECK.getModule() +" "+ActivityType.PAYMENT_IN_CUSTOM_CHECK_RECHECK.getType());
			ccPInActivity.setCreatedBy(profileActivityLogDto.getActivityBy());
			ccPInActivity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(ccPInActivity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private String getPaymentInCustomCheckLog(FundsInCreateRequest request, EventServiceLog log) {

		EntityDetails entityDetails = (EntityDetails) request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		if (null == entityDetails) {
			entityDetails = new EntityDetails();
		}
		getDefaultEntityDetails(entityDetails);

		return ActivityTemplateEnum.RECHECK.getTemplate().replace("{RECHECK_SERVICE_NAME}", "Custom check")
				.replace("{PRE_STATUS}", entityDetails.getPrevStatus())
				.replace("{UPDATED_STATUS}", log.getStatus().toLowerCase())
				.replace("{ENTITY_NAME}", entityDetails.getAccountName());
	}
	
	/**
	 * added to set default prev status to service failure when 
	 * there is no entry in Event service log table for respective service
	 */
	private void getDefaultEntityDetails(EntityDetails entityDetails) {

		if (null == entityDetails.getPrevStatus() || entityDetails.getPrevStatus().isEmpty()) {
			entityDetails.setPrevStatus(Constants.SERVICE_FAILURE);
		}
	}
	

}
