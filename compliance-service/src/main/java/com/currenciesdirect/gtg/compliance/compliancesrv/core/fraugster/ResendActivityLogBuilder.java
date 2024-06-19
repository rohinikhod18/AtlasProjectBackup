package com.currenciesdirect.gtg.compliance.compliancesrv.core.fraugster;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsInFraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FundsOutFruagsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class ResendActivityLogBuilder.
 */
public class ResendActivityLogBuilder {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ResendActivityLogBuilder.class);

	/**
	 * Builds the activity logs.
	 *
	 * @param fResendMessage the f resend message
	 * @return the message
	 */
	@ServiceActivator
	public Message<MessageContext> buildActivityLogs(Message<MessageContext> fResendMessage) {

		try {
			UserProfile userProfile = (UserProfile) fResendMessage.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			String userName = userProfile.getPreferredUserName();
			MessageExchange fExchange = fResendMessage.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
			ServiceInterfaceType serviceType = fResendMessage.getPayload().getGatewayMessageExchange()
					.getServiceInterface();
			ActivityLogs resendActivityLogs = null;
		    String summary;
			FraugsterResendResponse fResendResponse = new FraugsterResendResponse();
			if (ServiceInterfaceType.FUNDSOUT==serviceType) {
				
				FundsOutFruagsterResendRequest resendRequest = fResendMessage.getPayload().getGatewayMessageExchange()
						.getRequest(FundsOutFruagsterResendRequest.class);
				FundsOutRequest fRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsOutRequest.class);
				EventServiceLog resendLog = fExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
						EntityEnum.CONTACT.name(), fRequest.getContactId());
				List<PaymentOutActivityLogDto> activityLogDtos = getPaymentOutActivityLog(fRequest, userName,
						resendLog);
				resendActivityLogs = getPaymentOutActivityLogData(activityLogDtos);
				fResendResponse.setActivityLogs(resendActivityLogs);
				fResendResponse.setSummary(JsonConverterUtil.convertToObject(FraugsterSummary.class, resendLog.getSummary()));
				fResendResponse.setEventServiceLogId(resendLog.getEventServiceLogId());
				fResendResponse.addAttribute("paymentOutActivities", activityLogDtos);
			 }
		
	         if(ServiceInterfaceType.FUNDSIN==serviceType){
				
				FundsInFraugsterResendRequest resendRequest = fResendMessage.getPayload().getGatewayMessageExchange()
						.getRequest(FundsInFraugsterResendRequest.class);
				FundsInCreateRequest fRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsInCreateRequest.class);
				
				Contact contact = (Contact) resendRequest.getAdditionalAttribute(Constants.FIELD_CONTACT);
				EventServiceLog resendLog = fExchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
						EntityEnum.CONTACT.name(), contact.getId());
				summary = resendLog.getSummary();
				setFraugsterSummaryData(userName, summary, fResendResponse, resendLog);
				List<PaymentInActivityLogDto> activityLogDtos = getPaymentInActivityLog(fRequest, userName,
						resendLog);
				resendActivityLogs = getPaymentInActivityLogData(activityLogDtos);
				fResendResponse.setActivityLogs(resendActivityLogs);
				fResendResponse.setEventServiceLogId(resendLog.getEventServiceLogId());
				fResendResponse.addAttribute("paymentInActivities", activityLogDtos);
			}

			fResendMessage.getPayload().getGatewayMessageExchange().setResponse(fResendResponse);
		} catch (Exception ex) {
			LOGGER.error("Error building log : ", ex);
			fResendMessage.getPayload().setFailed(true);
		}

		return fResendMessage;
	}
	
	
	/**
	 * @param activityLogDtos
	 * @return the Activity Logs   getPaymentInActivityLogData
	 */
	private ActivityLogs getPaymentInActivityLogData(List<PaymentInActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = null;
		if (activityLogDtos != null && !activityLogDtos.isEmpty()) {
			activityLogs = getPaymentInActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}
	

    /**
     * Get the payment in activity log
	 * @param request
	 * @param userName
	 * @param log
	 * @return
	 */
    private  List<PaymentInActivityLogDto> getPaymentInActivityLog(FundsInCreateRequest request, String userName, EventServiceLog log){
		
		List<PaymentInActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			PaymentInActivityLogDto activityLogDto = new PaymentInActivityLogDto();
			activityLogDto.setPaymentInId(request.getFundsInId());
			activityLogDto.setOrgCode(request.getOrgCode());
			activityLogDto.setActivityBy(userName);
			activityLogDto.setCreatedBy(userName);
			activityLogDto.setUpdatedBy(userName);
			activityLogDto.setCreatedOn(timestamp);
			activityLogDto.setTimeStatmp(timestamp);
			activityLogDto.setUpdatedOn(timestamp);
			PaymentInActivityLogDetailDto activityLogDetailDto = new PaymentInActivityLogDetailDto();
			activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
			activityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_FRAUGSTER_RECHECK);
			activityLogDetailDto.setLog(getPaymentInFraugsterLog(request,log));
			activityLogDetailDto.setCreatedBy(userName);
			activityLogDetailDto.setUpdatedBy(userName);
			activityLogDetailDto.setCreatedOn(timestamp);
			activityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(activityLogDto);
		return activityLogDtos;
	}
    
    	
	/**
	 * 
	 * Get payment in fraugster log
	 * @param request
	 * @param log
	 * @return 
	 */
  private String getPaymentInFraugsterLog(FundsInCreateRequest request, EventServiceLog log){
	  String prevFStatus = request.getAdditionalAttribute(Constants.FRAUGSTER_STATUS, String.class);
			
	  return ActivityTemplateEnum.PAYMENT_RECHECK.getTemplate().replace("{RECHECK_SERVICE_NAME}", "FraudPredict")
				.replace("{PRE_STATUS}", prevFStatus).replace("{UPDATED_STATUS}", log.getStatus());
		}	
		
	/**
	 * Set fraugster summary data
	 * @param userName
	 * @param summary
	 * @param resendResponse
	 * @param eventServiceLog
	 */
	private void setFraugsterSummaryData(String userName, String summary, FraugsterResendResponse resendResponse,
			EventServiceLog eventServiceLog) {
		if(summary != null) {
		     FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class, summary);
			 fraugsterSummary.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
			 fraugsterSummary.setUpdatedBy(userName);
			 resendResponse.setSummary(fraugsterSummary);
			}
		}	
	
	/**
	 * Get payment in activity log response
	 * @param activityLogDtos
	 * @return activityLogs
	 */
	private ActivityLogs getPaymentInActivityLogsResponse(List<PaymentInActivityLogDto> activityLogDtos){
			ActivityLogs activityLogs = new ActivityLogs();
				List<ActivityLogData> activityData = new ArrayList<>();
			for (PaymentInActivityLogDto profileActivityLogDto : activityLogDtos) {
			    ActivityLogData activity = new ActivityLogData();
			    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
				activity.setActivityType(ActivityType.PAYMENT_IN_FRAUGSTER_RECHECK.getModule() +" "+ActivityType.PAYMENT_IN_FRAUGSTER_RECHECK.getType());
				activity.setCreatedBy(profileActivityLogDto.getActivityBy());
				activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
				activityData.add(activity);
			  }
				activityLogs.setActivityLogData(activityData);
				return activityLogs;
		 }
	
	
	/**
	 * Gets the payment out activity log.
	 *
	 * @param request the request
	 * @param userName the user name
	 * @param logs the logs
	 * @return the payment out activity log
	 */
	private List<PaymentOutActivityLogDto> getPaymentOutActivityLog(FundsOutRequest request, String userName,
			EventServiceLog logs) {

		List<PaymentOutActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		PaymentOutActivityLogDto fPOActivityLogDto = new PaymentOutActivityLogDto();
		fPOActivityLogDto.setPaymentOutId(request.getFundsOutId());
		fPOActivityLogDto.setOrgCode(request.getOrgCode());
		fPOActivityLogDto.setActivityBy(userName);
		fPOActivityLogDto.setCreatedBy(userName);
		fPOActivityLogDto.setUpdatedBy(userName);
		fPOActivityLogDto.setCreatedOn(timestamp);
		fPOActivityLogDto.setTimeStatmp(timestamp);
		fPOActivityLogDto.setUpdatedOn(timestamp);
		PaymentOutActivityLogDetailDto fPOActivityLogDetailDto = new PaymentOutActivityLogDetailDto();
		fPOActivityLogDto.setActivityLogDetailDto(fPOActivityLogDetailDto);
		fPOActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_FRAUGSTER_REPEAT);
		fPOActivityLogDetailDto.setLog(getPaymentOutFraugsterLog(request, logs));
		fPOActivityLogDetailDto.setCreatedBy(userName);
		fPOActivityLogDetailDto.setUpdatedBy(userName);
		fPOActivityLogDetailDto.setCreatedOn(timestamp);
		fPOActivityLogDetailDto.setUpdatedOn(timestamp);
		activityLogDtos.add(fPOActivityLogDto);
		return activityLogDtos;
	}

	/**
	 * Gets the payment out fraugster log.
	 *
	 * @param request the request
	 * @param log the log
	 * @return the payment out fraugster log
	 */
	private String getPaymentOutFraugsterLog(FundsOutRequest request, EventServiceLog log) {
		String prevFStatus = request.getAdditionalAttribute(Constants.FRAUGSTER_STATUS, String.class);
		return ActivityTemplateEnum.PAYMENT_RECHECK.getTemplate().replace("{RECHECK_SERVICE_NAME}", "FraudPredict")
				.replace("{PRE_STATUS}", prevFStatus).replace("{UPDATED_STATUS}", log.getStatus());

	}

	/**
	 * Gets the payment out activity log data.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @return the payment out activity log data
	 */
	private ActivityLogs getPaymentOutActivityLogData(List<PaymentOutActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = null;
		if (activityLogDtos != null && !activityLogDtos.isEmpty()) {
			activityLogs = getPaymentOutActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}

	/**
	 * Gets the payment out activity logs response.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @return the payment out activity logs response
	 */
	private ActivityLogs getPaymentOutActivityLogsResponse(List<PaymentOutActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentOutActivityLogDto profileActivityLogDto : activityLogDtos) {
			ActivityLogData activity = new ActivityLogData();
			activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PAYMENT_OUT_FRAUGSTER_REPEAT.getModule() + " "
					+ ActivityType.PAYMENT_OUT_FRAUGSTER_REPEAT.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
}
