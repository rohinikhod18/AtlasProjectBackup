package com.currenciesdirect.gtg.compliance.compliancesrv.core.fraugster;

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
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class FraugsterRepeatCheckAtivityLogBuilder.
 */
public class FraugsterRepeatCheckAtivityLogBuilder {
	
	private static final String ENTITY_NAME = "{ENTITY_NAME}";
	private static final String UPDATED_STATUS = "{UPDATED_STATUS}";
	private static final String PRE_STATUS = "{PRE_STATUS}";
	private static final String RECHECK_SERVICE_NAME = "{RECHECK_SERVICE_NAME}";
	private static final Logger LOGGER = LoggerFactory.getLogger(FraugsterRepeatCheckAtivityLogBuilder.class);
	
	/**
	 * Builds the activity logs for fraugster repeat check and adds into message
	 *
	 * @param message the message
	 * @return the message
	 */
	@ServiceActivator
	public Message<MessageContext> buildActivityLogs(Message<MessageContext> message){
		
		try {
		UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		String userName = userProfile.getPreferredUserName();
		
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		
		ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
		OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
		ActivityLogs activityLogs = null;
		String summary;
		FraugsterResendResponse resendResponse = new FraugsterResendResponse();
		if(ServiceInterfaceType.PROFILE ==  eventType && OperationEnum.FRAUGSTER_RESEND == operation){
			FraugsterResendRequest resendRequest = message.getPayload().getGatewayMessageExchange()
					.getRequest(FraugsterResendRequest.class);
			RegistrationServiceRequest request = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST, RegistrationServiceRequest.class);
			String entityType = (String) request.getAdditionalAttribute("entityType");
		
			EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
					EntityEnum.CONTACT.name(), resendRequest.getContactId());
			summary = eventServiceLog.getSummary();
			
			List<ProfileActivityLogDto> activityLogDtos = getProfileActivityLog(request, userName, eventServiceLog, entityType);
			activityLogs = getActivityLogData(activityLogs, activityLogDtos);
			resendResponse.addAttribute("profileActivities", activityLogDtos);
			setFraugsterSummaryData(userName, summary, resendResponse, eventServiceLog);
			resendResponse.setActivityLogs(activityLogs);
			resendResponse.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
		}
		
		if(ServiceInterfaceType.FUNDSOUT ==  eventType && OperationEnum.FRAUGSTER_RESEND == operation){
			FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsOutRequest.class);
			
			EventServiceLog eventServiceLog = getPaymentOutServiceLog(fundsOutRequest, exchange);
			setFraugsterSummaryFromESL(userName, resendResponse, eventServiceLog);
			List<PaymentOutActivityLogDto> activityLogDtos = getPaymentOutActivityLog(fundsOutRequest,userName,eventServiceLog);
			activityLogs = getPaymentOutActivityLogData(activityLogs, activityLogDtos);
			resendResponse.addAttribute("paymentOutActivities", activityLogDtos);
			resendResponse.setActivityLogs(activityLogs);
			setESLogId(resendResponse, eventServiceLog);
		}
		
		
		
		message.getPayload().getGatewayMessageExchange().setResponse(resendResponse);
		} catch(Exception ex){
			LOGGER.error("Exception : ",ex);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	

	private void setFraugsterSummaryFromESL(String userName, FraugsterResendResponse resendResponse,
			EventServiceLog eventServiceLog) {
		if(null != eventServiceLog) {
			FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class, eventServiceLog.getSummary());
			fraugsterSummary.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
			fraugsterSummary.setUpdatedBy(userName);
			resendResponse.setSummary(fraugsterSummary);
		}
	}

	private void setESLogId(FraugsterResendResponse resendResponse, EventServiceLog eventServiceLog) {
		if(null != eventServiceLog) 
			resendResponse.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
	}

	private ActivityLogs getPaymentOutActivityLogData(ActivityLogs activityLogs,
			List<PaymentOutActivityLogDto> activityLogDtos) {
		if(activityLogDtos != null && !activityLogDtos.isEmpty()){
			activityLogs = getPaymentOutActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}

	private void setFraugsterSummaryData(String userName, String summary, FraugsterResendResponse resendResponse,
			EventServiceLog eventServiceLog) {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		if(summary != null) {
			FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class, summary);
			fraugsterSummary.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
			fraugsterSummary.setUpdatedBy(userName);
			fraugsterSummary.setCreatedOn(DateTimeFormatter.dateTimeFormatter(timestamp));
			resendResponse.setSummary(fraugsterSummary);
		}
	}


	private ActivityLogs getActivityLogData(ActivityLogs activityLogs, List<ProfileActivityLogDto> activityLogDtos) {
		if(activityLogDtos != null && !activityLogDtos.isEmpty()){
			activityLogs = getActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}
	
	private List<ProfileActivityLogDto> getProfileActivityLog(RegistrationServiceRequest request, String userName,
			EventServiceLog log, String entityType) {
		
		List<ProfileActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		EntityDetails entityDetails = (EntityDetails)request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		
		Contact contact;
			ProfileActivityLogDto fraugsterActivityLogDto = new ProfileActivityLogDto();
			fraugsterActivityLogDto.setAccountId(request.getAccount().getId());
			ActivityLogDetailDto fraugsterActivityLogDetailDto = new ActivityLogDetailDto();
			fraugsterActivityLogDto.setActivityLogDetailDto(fraugsterActivityLogDetailDto);
			if(EntityEnum.ACCOUNT.name().equalsIgnoreCase(entityType)){
				fraugsterActivityLogDetailDto.setLog(ActivityTemplateEnum.RECHECK.getTemplate()
			    		.replace(RECHECK_SERVICE_NAME,"FraudPredict")
			    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
			    		.replace(UPDATED_STATUS, log.getStatus())
			    		.replace(ENTITY_NAME, entityDetails.getAccountName()));
			}else{
				contact = request.getAccount().getContacts().get(0);
				fraugsterActivityLogDto.setContactId(contact.getId());
				fraugsterActivityLogDetailDto.setLog(ActivityTemplateEnum.RECHECK.getTemplate()
			    		.replace(RECHECK_SERVICE_NAME,"FraudPredict")
			    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
			    		.replace(UPDATED_STATUS, log.getStatus())
						.replace(ENTITY_NAME,entityDetails.getContactName()));
			}
			fraugsterActivityLogDto.setOrgCode(request.getOrgCode());
			fraugsterActivityLogDto.setActivityBy(userName);
			fraugsterActivityLogDto.setCreatedBy(userName);
			fraugsterActivityLogDto.setUpdatedBy(userName);
			fraugsterActivityLogDto.setCreatedOn(timestamp);
			fraugsterActivityLogDto.setTimeStatmp(timestamp);
			fraugsterActivityLogDto.setUpdatedOn(timestamp);
			fraugsterActivityLogDetailDto.setActivityType(ActivityType.PROFILE_FRAUGSTER_RECHECK);
			fraugsterActivityLogDetailDto.setCreatedBy(userName);
			fraugsterActivityLogDetailDto.setUpdatedBy(userName);
			fraugsterActivityLogDetailDto.setCreatedOn(timestamp);
			fraugsterActivityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(fraugsterActivityLogDto);
		return activityLogDtos;
	}
	
	private ActivityLogs getActivityLogsResponse(List<ProfileActivityLogDto> activityLogDtos){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (ProfileActivityLogDto profileActivityLogDto : activityLogDtos) {
		    ActivityLogData activity = new ActivityLogData();
		    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PROFILE_FRAUGSTER_RECHECK.getModule() +" "+ActivityType.PROFILE_FRAUGSTER_RECHECK.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private  List<PaymentOutActivityLogDto> getPaymentOutActivityLog(FundsOutRequest request,String userName,EventServiceLog logs){
		
		List<PaymentOutActivityLogDto> activityLogDtos = new ArrayList<>();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			PaymentOutActivityLogDto fraugsterPOActivityLogDto = new PaymentOutActivityLogDto();
			fraugsterPOActivityLogDto.setPaymentOutId(request.getFundsOutId());
			fraugsterPOActivityLogDto.setOrgCode(request.getOrgCode());
			fraugsterPOActivityLogDto.setActivityBy(userName);
			fraugsterPOActivityLogDto.setCreatedBy(userName);
			fraugsterPOActivityLogDto.setUpdatedBy(userName);
			fraugsterPOActivityLogDto.setCreatedOn(timestamp);
			fraugsterPOActivityLogDto.setTimeStatmp(timestamp);
			fraugsterPOActivityLogDto.setUpdatedOn(timestamp);
			PaymentOutActivityLogDetailDto fraugsterPOActivityLogDetailDto = new PaymentOutActivityLogDetailDto();
			fraugsterPOActivityLogDto.setActivityLogDetailDto(fraugsterPOActivityLogDetailDto);
			fraugsterPOActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_FRAUGSTER_RECHECK);
			fraugsterPOActivityLogDetailDto.setLog(getPaymentOutFraugsterLog(request,logs));
			fraugsterPOActivityLogDetailDto.setCreatedBy(userName);
			fraugsterPOActivityLogDetailDto.setUpdatedBy(userName);
			fraugsterPOActivityLogDetailDto.setCreatedOn(timestamp);
			fraugsterPOActivityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(fraugsterPOActivityLogDto);
		return activityLogDtos;
	}
	
	
	private ActivityLogs getPaymentOutActivityLogsResponse(List<PaymentOutActivityLogDto> activityLogDtos){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentOutActivityLogDto profileActivityLogDto : activityLogDtos) {
		    ActivityLogData activity = new ActivityLogData();
		    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PAYMENT_OUT_FRAUGSTER_RECHECK.getModule() +" "+ActivityType.PAYMENT_OUT_FRAUGSTER_RECHECK.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private String getPaymentOutFraugsterLog(FundsOutRequest request,EventServiceLog log){
		EntityDetails entityDetails = (EntityDetails)request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		
		if(Boolean.TRUE.equals(request.getIsContactEligible())){
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,"FraudPredict Contact")
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
		    		.replace(ENTITY_NAME, entityDetails.getContactName());
		} 
		if(Boolean.TRUE.equals(request.getIsBeneficiaryEligible())){
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,"FraudPredict Beneficiary")
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
					.replace(ENTITY_NAME, entityDetails.getBeneficiaryName());
		}
		if(Boolean.TRUE.equals(request.getIsBankEligible())){
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,"FraudPredict Bank")
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
		    		.replace(ENTITY_NAME, entityDetails.getBankName());
		}
		
		return null;
		
	}
	
	private EventServiceLog getPaymentOutServiceLog(FundsOutRequest request, MessageExchange exchange){
		if (Boolean.TRUE.equals(request.getIsContactEligible())) {
			Contact contact = (Contact) request.getAdditionalAttribute("contact");
			return exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
		}
		if (Boolean.TRUE.equals(request.getIsBeneficiaryEligible())) {
			return exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE, EntityEnum.BENEFICIARY.name(), request.getBeneficiary().getBeneficiaryId());
		}
		if (Boolean.TRUE.equals(request.getIsBankEligible())) {
			return exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE, EntityEnum.BANK.name(), request.getBeneficiary().getBeneficiaryBankid());
		}
		return null;
	}
	

	
	
	
	
	
	
	
}