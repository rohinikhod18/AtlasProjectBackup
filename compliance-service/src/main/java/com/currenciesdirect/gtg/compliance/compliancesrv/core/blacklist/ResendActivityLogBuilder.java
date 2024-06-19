
package com.currenciesdirect.gtg.compliance.compliancesrv.core.blacklist;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogData;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogs;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityTemplateEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsInBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.FundsOutBlacklistResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class ResendActivityLogBuilder.
 */
public class ResendActivityLogBuilder {

	public static final String UPDATED_STATUS = "{UPDATED_STATUS}";
	public static final String PRE_STATUS = "{PRE_STATUS}";
	public static final String BLACKLIST = "Blacklist";
	public static final String RECHECK_SERVICE_NAME = "{RECHECK_SERVICE_NAME}";
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
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
			ServiceInterfaceType serviceType = fResendMessage.getPayload().getGatewayMessageExchange()
					.getServiceInterface();
			ActivityLogs resendActivityLogs = null;
			String summary;
			BlacklistResendResponse fResendResponse = new BlacklistResendResponse();

			if (ServiceInterfaceType.PROFILE==serviceType){

				RegistrationServiceRequest request = fResendMessage.getPayload().getGatewayMessageExchange()
						.getRequest(RegistrationServiceRequest.class);
				String entityType = (String) request.getAdditionalAttribute("entityType");
				InternalServiceResponse internalServiceResponse = (InternalServiceResponse) fExchange.getResponse();
				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();
				EventServiceLog eventServiceLog;
				if (EntityEnum.ACCOUNT.name().equalsIgnoreCase(entityType)) {
					eventServiceLog = fExchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
							EntityEnum.ACCOUNT.name(), request.getAccount().getId());
					summary = eventServiceLog.getSummary();
				} else {
					eventServiceLog = fExchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
							EntityEnum.CONTACT.name(), contactResponses.get(0).getId());
					summary = eventServiceLog.getSummary();
				}

				List<ProfileActivityLogDto> activityLogDtos = getProfileActivityLog(request, userName, eventServiceLog);
				resendActivityLogs = getActivityLogData(resendActivityLogs, activityLogDtos);
				fResendResponse.addAttribute("profileActivities", activityLogDtos);
				setBlacklistSummaryData(summary
						, fResendResponse);
				fResendResponse.setActivityLogs(resendActivityLogs);
				fResendResponse.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
				fResendResponse.addAttribute("profileActivities", activityLogDtos);
			}

			if (ServiceInterfaceType.FUNDSIN == serviceType) {

				FundsInBlacklistResendRequest resendRequest = fResendMessage.getPayload().getGatewayMessageExchange()
						.getRequest(FundsInBlacklistResendRequest.class);
				FundsInCreateRequest fRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsInCreateRequest.class);
				InternalServiceResponse internalServiceResponse = (InternalServiceResponse) fExchange.getResponse();
				List<ContactResponse> contactResponses = internalServiceResponse.getContacts();
				EventServiceLog resendLog = fExchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.CONTACT.name(), contactResponses.get(0).getId());
				summary = resendLog.getSummary();
				setBlacklistSummaryData(summary, fResendResponse);
				List<PaymentInActivityLogDto> activityLogDtos = getPaymentInActivityLog(fRequest, userName, resendLog);
				resendActivityLogs = getPaymentInActivityLogData(activityLogDtos);
				fResendResponse.setActivityLogs(resendActivityLogs);
				fResendResponse.setEventServiceLogId(resendLog.getEventServiceLogId());
				fResendResponse.addAttribute("paymentInActivities", activityLogDtos);
			}

			if (ServiceInterfaceType.FUNDSOUT == serviceType) {

				FundsOutBlacklistResendRequest resendRequest = fResendMessage.getPayload().getGatewayMessageExchange()
						.getRequest(FundsOutBlacklistResendRequest.class);
				FundsOutRequest fRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
						FundsOutRequest.class);
				EventServiceLog resendLog = fExchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.BENEFICIARY.name(), fRequest.getBeneficiary().getBeneficiaryId());
				summary = resendLog.getSummary();
				setBlacklistSummaryData(summary, fResendResponse);
				List<PaymentOutActivityLogDto> activityLogDtos = getPaymentOutActivityLog(fRequest, userName,
						resendLog);
				resendActivityLogs = getPaymentOutActivityLogData(activityLogDtos);
				fResendResponse.setActivityLogs(resendActivityLogs);
				fResendResponse.setEventServiceLogId(resendLog.getEventServiceLogId());
				fResendResponse.addAttribute("paymentOutActivities", activityLogDtos);
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
			activityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_BLACKLIST_RECHECK);
			activityLogDetailDto.setLog(getPaymentInBlacklistLog(request,log));
			activityLogDetailDto.setCreatedBy(userName);
			activityLogDetailDto.setUpdatedBy(userName);
			activityLogDetailDto.setCreatedOn(timestamp);
			activityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(activityLogDto);
		return activityLogDtos;
	}
    
    	
	/**
	 * 
	 * Get payment in blacklist log
	 * @param request
	 * @param log
	 * @return 
	 */
  private String getPaymentInBlacklistLog(FundsInCreateRequest request, EventServiceLog log){
	  String prevFStatus = request.getAdditionalAttribute(Constants.BLACKLIST_STATUS, String.class);
			
	  return ActivityTemplateEnum.PAYMENT_RECHECK.getTemplate().replace(RECHECK_SERVICE_NAME, BLACKLIST)
				.replace(PRE_STATUS, prevFStatus).replace(UPDATED_STATUS, log.getStatus());
		}	
		
	/**
	 * Set blacklist summary data
	 * @param userName
	 * @param summary
	 * @param resendResponse
	 * @param eventServiceLog
	 */
	private void setBlacklistSummaryData(String summary, BlacklistResendResponse resendResponse) {
		if(summary != null) {
		     BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class, summary);
			 resendResponse.setSummary(blacklistSummary);
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
				activity.setActivityType(ActivityType.PAYMENT_IN_BLACKLIST_RECHECK.getModule() +" "+ActivityType.PAYMENT_IN_BLACKLIST_RECHECK.getType());
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
		fPOActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_BLACKLIST_REPEAT);
		fPOActivityLogDetailDto.setLog(getPaymentOutBlacklistLog(request, logs));
		fPOActivityLogDetailDto.setCreatedBy(userName);
		fPOActivityLogDetailDto.setUpdatedBy(userName);
		fPOActivityLogDetailDto.setCreatedOn(timestamp);
		fPOActivityLogDetailDto.setUpdatedOn(timestamp);
		activityLogDtos.add(fPOActivityLogDto);
		return activityLogDtos;
	}
	
	/**
	 * Gets the payment out blacklist log.
	 *
	 * @param request the request
	 * @param log the log
	 * @return the payment out blacklist log
	 */
	private String getPaymentOutBlacklistLog(FundsOutRequest request, EventServiceLog log) {
		String prevFStatus = request.getAdditionalAttribute(Constants.BLACKLIST_STATUS, String.class);
		return ActivityTemplateEnum.PAYMENT_RECHECK.getTemplate().replace(RECHECK_SERVICE_NAME, BLACKLIST)
				.replace(PRE_STATUS, prevFStatus).replace(UPDATED_STATUS, log.getStatus());

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
	 *PAYMENT_OUT_BLACKLIST_REPEAT
	 * @param activityLogDtos the activity log dtos
	 * @return the payment out activity logs response
	 */
	private ActivityLogs getPaymentOutActivityLogsResponse(List<PaymentOutActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentOutActivityLogDto profileActivityLogDto : activityLogDtos) {
			ActivityLogData activity = new ActivityLogData();
			activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PAYMENT_OUT_BLACKLIST_REPEAT.getModule() + " "
					+ ActivityType.PAYMENT_OUT_BLACKLIST_REPEAT.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private List<ProfileActivityLogDto> getProfileActivityLog(RegistrationServiceRequest request, String userName,
			EventServiceLog log) {
		List<ProfileActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		ProfileActivityLogDto activityLogDto = new ProfileActivityLogDto();
		activityLogDto.setAccountId(request.getAccount().getId());
		if(!request.getAccount().getContacts().isEmpty()) {
			activityLogDto.setContactId(request.getAccount().getContacts().get(0).getId());
		}
		activityLogDto.setOrgCode(request.getOrgCode());
		activityLogDto.setActivityBy(userName);
		activityLogDto.setCreatedBy(userName);
		activityLogDto.setUpdatedBy(userName);
		activityLogDto.setCreatedOn(timestamp);
		activityLogDto.setTimeStatmp(timestamp);
		activityLogDto.setUpdatedOn(timestamp);
		ActivityLogDetailDto activityLogDetailDto = new ActivityLogDetailDto();
		activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		activityLogDetailDto.setActivityType(ActivityType.PROFILE_BLACKLIST_RECHECK);
		activityLogDetailDto.setLog(getProfileBlacklistLog(request, log));
		activityLogDetailDto.setCreatedBy(userName);
		activityLogDetailDto.setUpdatedBy(userName);
		activityLogDetailDto.setCreatedOn(timestamp);
		activityLogDetailDto.setUpdatedOn(timestamp);
		activityLogDtos.add(activityLogDto);
		return activityLogDtos;
	}
	
	private ActivityLogs getActivityLogData(ActivityLogs activityLogs, List<ProfileActivityLogDto> activityLogDtos) {
		if(activityLogDtos != null && !activityLogDtos.isEmpty()){
			activityLogs = getActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}
	
	private ActivityLogs getActivityLogsResponse(List<ProfileActivityLogDto> activityLogDtos){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (ProfileActivityLogDto profileActivityLogDto : activityLogDtos) {
		    ActivityLogData activity = new ActivityLogData();
		    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PROFILE_BLACKLIST_RECHECK.getModule() +" "+ActivityType.PROFILE_BLACKLIST_RECHECK.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}

	private String getProfileBlacklistLog(RegistrationServiceRequest request, EventServiceLog log) {
		EntityDetails entityDetails = (EntityDetails) request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		String activityLog = null;

		if ("ACCOUNT".equalsIgnoreCase(entityDetails.getEntityType())) {
			activityLog = ActivityTemplateEnum.BLACKLIST_RECHECK.getTemplate().replace(RECHECK_SERVICE_NAME, BLACKLIST)
					.replace(PRE_STATUS, entityDetails.getPrevStatus()).replace(UPDATED_STATUS, log.getStatus())
					.replace("{ENTITY_NAME}", entityDetails.getAccountName());
		} else if ("CONTACT".equalsIgnoreCase(entityDetails.getEntityType())) {
			activityLog = ActivityTemplateEnum.BLACKLIST_RECHECK.getTemplate().replace(RECHECK_SERVICE_NAME, BLACKLIST)
					.replace(PRE_STATUS, entityDetails.getPrevStatus()).replace(UPDATED_STATUS, log.getStatus())
					.replace("{ENTITY_NAME}", entityDetails.getContactName());
		}

		return activityLog;
	}
	
}
