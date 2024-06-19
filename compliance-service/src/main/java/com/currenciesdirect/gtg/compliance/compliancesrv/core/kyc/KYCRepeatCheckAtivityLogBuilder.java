package com.currenciesdirect.gtg.compliance.compliancesrv.core.kyc;

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
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class SanctionRepeatCheckAtivityLogBuilder.
 */
public class KYCRepeatCheckAtivityLogBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(KYCRepeatCheckAtivityLogBuilder.class);
	
	/**
	 * Builds the activity logs for sanction repeat check and adds into message
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	@ServiceActivator
	public Message<MessageContext> buildActivityLogs(Message<MessageContext> message) {
		
		try{
			RegistrationServiceRequest request = message.getPayload().getGatewayMessageExchange()
					.getRequest(RegistrationServiceRequest.class);
			UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			String userName = userProfile.getPreferredUserName();

			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			EventServiceLog log = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE, EntityEnum.CONTACT.name(),
					request.getAccount().getContacts().get(0).getId());
			List<ProfileActivityLogDto> activityLogDtos = getProfileActivityLog(request, userName, log);
			ActivityLogs activityLogs = null;
			if ( !activityLogDtos.isEmpty()) {
				activityLogs = getActivityLogsResponse(activityLogDtos);
			}

			KYCResendResponse resendResponse = new KYCResendResponse();
			String summary = log.getSummary();
			KYCSummary kycSummary = JsonConverterUtil.convertToObject(KYCSummary.class, summary);
			if (kycSummary != null) {
				kycSummary.setId(log.getEventServiceLogId());
				resendResponse.setSummary(kycSummary);
			}
			resendResponse.setActivityLogs(activityLogs);
			resendResponse.addAttribute("profileActivities", activityLogDtos);
			message.getPayload().getGatewayMessageExchange().setResponse(resendResponse);
			
		}catch(Exception e){
			LOG.error("Error in buildActivityLogs : ",e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	private List<ProfileActivityLogDto> getProfileActivityLog(RegistrationServiceRequest request, String userName,
			EventServiceLog log) {

		List<ProfileActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		EntityDetails entityDetails = (EntityDetails)request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		Contact contact = request.getAccount().getContacts().get(0);
		ProfileActivityLogDto activityLogDto = new ProfileActivityLogDto();
		activityLogDto.setAccountId(request.getAccount().getId());
		activityLogDto.setContactId(contact.getId());
		activityLogDto.setOrgCode(request.getOrgCode());
		activityLogDto.setActivityBy(userName);
		activityLogDto.setCreatedBy(userName);
		activityLogDto.setUpdatedBy(userName);
		activityLogDto.setCreatedOn(timestamp);
		activityLogDto.setTimeStatmp(timestamp);
		activityLogDto.setUpdatedOn(timestamp);
		ActivityLogDetailDto activityLogDetailDto = new ActivityLogDetailDto();
		activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
		activityLogDetailDto.setActivityType(ActivityType.PROFILE_KYC_RECHECK);
		activityLogDetailDto.setLog(ActivityTemplateEnum.RECHECK.getTemplate()
				.replace("{RECHECK_SERVICE_NAME}", "KYC")
				.replace("{PRE_STATUS}", (null == entityDetails.getPrevStatus()? "None":entityDetails.getPrevStatus()))
				.replace("{UPDATED_STATUS}", log.getStatus().toLowerCase())
				.replace("{ENTITY_NAME}", (null == entityDetails.getContactName()? contact.getFullName():entityDetails.getContactName())));
		activityLogDetailDto.setCreatedBy(userName);
		activityLogDetailDto.setUpdatedBy(userName);
		activityLogDetailDto.setCreatedOn(timestamp);
		activityLogDetailDto.setUpdatedOn(timestamp);
		activityLogDtos.add(activityLogDto);
		return activityLogDtos;
	}

	private ActivityLogs getActivityLogsResponse(List<ProfileActivityLogDto> activityLogDtos) {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (ProfileActivityLogDto profileActivityLogDto : activityLogDtos) {
			ActivityLogData activity = new ActivityLogData();
			activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(
					ActivityType.PROFILE_KYC_RECHECK.getModule() + " " + ActivityType.PROFILE_KYC_RECHECK.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}

}
