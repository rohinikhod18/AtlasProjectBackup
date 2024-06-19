package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanction;

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
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.FundsInSanctionResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResendResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
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
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class SanctionRepeatCheckAtivityLogBuilder.
 */
public class SanctionRepeatCheckAtivityLogBuilder {
	
	private static final String SANCTION = "Sanction";
  private static final String ENTITY_NAME = "{ENTITY_NAME}";
  private static final String UPDATED_STATUS = "{UPDATED_STATUS}";
  private static final String PRE_STATUS = "{PRE_STATUS}";
  private static final String RECHECK_SERVICE_NAME = "{RECHECK_SERVICE_NAME}";
  private static final Logger LOGGER = LoggerFactory.getLogger(SanctionRepeatCheckAtivityLogBuilder.class);
	/**
	 * Builds the activity logs for sanction repeat check and adds into message
	 *
	 * @param message the message
	 * @return the message
	 */
	@ServiceActivator
	public Message<MessageContext> buildActivityLogs(Message<MessageContext> message){
		
		try {
		UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		String userName = userProfile.getPreferredUserName();
		
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		
		SanctionResponse fResponse = (SanctionResponse) exchange.getResponse();
		List<SanctionContactResponse> contactList = fResponse.getContactResponses();
		
		ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
		OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
		ActivityLogs activityLogs = null;
		String summary;
		SanctionResendResponse resendResponse = new SanctionResendResponse();
		if(ServiceInterfaceType.PROFILE ==  eventType && OperationEnum.SANCTION_RESEND == operation){
			RegistrationServiceRequest request = message.getPayload().getGatewayMessageExchange().getRequest(RegistrationServiceRequest.class);
			String entityType = (String) request.getAdditionalAttribute("entityType");
			
			EventServiceLog eventServiceLog;
			if(EntityEnum.ACCOUNT.name().equalsIgnoreCase(entityType)){
				eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.ACCOUNT.name(), request.getAccount().getId());
				summary = eventServiceLog.getSummary();
			}else{
				eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(), contactList.get(0).getContactId());
				summary = eventServiceLog.getSummary();
			}
			
			List<ProfileActivityLogDto> activityLogDtos = getProfileActivityLog(request, userName, eventServiceLog, entityType);
			activityLogs = getActivityLogData(activityLogs, activityLogDtos);
			resendResponse.addAttribute("profileActivities", activityLogDtos);
			setSanctionSummaryData(userName, summary, resendResponse, eventServiceLog);
			resendResponse.setActivityLogs(activityLogs);
			resendResponse.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
		}
		
		if(ServiceInterfaceType.FUNDSOUT ==  eventType && OperationEnum.SANCTION_RESEND == operation){
			FundsOutRequest fundsOutRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsOutRequest.class);
			
			EventServiceLog eventServiceLog = getPaymentOutServiceLog(fundsOutRequest, exchange);
			setSanctionSummaryFromESL(userName, resendResponse, eventServiceLog);
			List<PaymentOutActivityLogDto> activityLogDtos = getPaymentOutActivityLog(fundsOutRequest,userName,eventServiceLog);
			activityLogs = getPaymentOutActivityLogData(activityLogs, activityLogDtos);
			resendResponse.addAttribute("paymentOutActivities", activityLogDtos);
			resendResponse.setActivityLogs(activityLogs);
			setESLogId(resendResponse, eventServiceLog);
		}
		
		if(ServiceInterfaceType.FUNDSIN ==  eventType && OperationEnum.SANCTION_RESEND == operation){
			FundsInCreateRequest fundsInCreateRequest = message.getPayload().getGatewayMessageExchange().getRequest(FundsInCreateRequest.class);
			EventServiceLog eventServiceLog = getPaymentInServiceLog(fundsInCreateRequest, exchange);
			summary = eventServiceLog.getSummary();
			setSanctionSummaryData(userName, summary, resendResponse, eventServiceLog);
			List<PaymentInActivityLogDto> activityLogDtos = getPaymentInActivityLog(fundsInCreateRequest, userName, eventServiceLog);
			activityLogs = getPaymentInActivityLogData(activityLogs, activityLogDtos);
			resendResponse.addAttribute("paymentInActivities", activityLogDtos);
			resendResponse.setActivityLogs(activityLogs);
			resendResponse.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
		}
		
		message.getPayload().getGatewayMessageExchange().setResponse(resendResponse);
		} catch(Exception ex){
			LOGGER.error("Exception : ",ex);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	private ActivityLogs getPaymentInActivityLogData(ActivityLogs activityLogs,
			List<PaymentInActivityLogDto> activityLogDtos) {
		if(activityLogDtos != null && !activityLogDtos.isEmpty()){
			activityLogs = getPaymentInActivityLogsResponse(activityLogDtos);
		}
		return activityLogs;
	}

	private void setSanctionSummaryFromESL(String userName, SanctionResendResponse resendResponse,
			EventServiceLog eventServiceLog) {
		if(null != eventServiceLog) {
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class, eventServiceLog.getSummary());
			sanctionSummary.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
			sanctionSummary.setUpdatedBy(userName);
			resendResponse.setSummary(sanctionSummary);
		}
	}

	private void setESLogId(SanctionResendResponse resendResponse, EventServiceLog eventServiceLog) {
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

	private void setSanctionSummaryData(String userName, String summary, SanctionResendResponse resendResponse,
			EventServiceLog eventServiceLog) {
		if(summary != null) {
			SanctionSummary sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class, summary);
			sanctionSummary.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
			sanctionSummary.setUpdatedBy(userName);
			resendResponse.setSummary(sanctionSummary);
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
			ProfileActivityLogDto sanctionActivityLogDto = new ProfileActivityLogDto();
			sanctionActivityLogDto.setAccountId(request.getAccount().getId());
			ActivityLogDetailDto sanctionActivityLogDetailDto = new ActivityLogDetailDto();
			sanctionActivityLogDto.setActivityLogDetailDto(sanctionActivityLogDetailDto);
			if(EntityEnum.ACCOUNT.name().equalsIgnoreCase(entityType)){
				sanctionActivityLogDetailDto.setLog(ActivityTemplateEnum.RECHECK.getTemplate()
			    		.replace(RECHECK_SERVICE_NAME,SANCTION)
			    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
			    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
			    		.replace(ENTITY_NAME, entityDetails.getAccountName()));
			}else{
				contact = request.getAccount().getContacts().get(0);
				sanctionActivityLogDto.setContactId(contact.getId());
				sanctionActivityLogDetailDto.setLog(ActivityTemplateEnum.RECHECK.getTemplate()
			    		.replace(RECHECK_SERVICE_NAME,SANCTION)
			    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
			    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
						.replace(ENTITY_NAME,entityDetails.getContactName()));
			}
			sanctionActivityLogDto.setOrgCode(request.getOrgCode());
			sanctionActivityLogDto.setActivityBy(userName);
			sanctionActivityLogDto.setCreatedBy(userName);
			sanctionActivityLogDto.setUpdatedBy(userName);
			sanctionActivityLogDto.setCreatedOn(timestamp);
			sanctionActivityLogDto.setTimeStatmp(timestamp);
			sanctionActivityLogDto.setUpdatedOn(timestamp);
			sanctionActivityLogDetailDto.setActivityType(ActivityType.PROFILE_SANCTION_RECHECK);
			sanctionActivityLogDetailDto.setCreatedBy(userName);
			sanctionActivityLogDetailDto.setUpdatedBy(userName);
			sanctionActivityLogDetailDto.setCreatedOn(timestamp);
			sanctionActivityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(sanctionActivityLogDto);
		return activityLogDtos;
	}
	
	private ActivityLogs getActivityLogsResponse(List<ProfileActivityLogDto> activityLogDtos){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (ProfileActivityLogDto profileActivityLogDto : activityLogDtos) {
		    ActivityLogData activity = new ActivityLogData();
		    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PROFILE_SANCTION_RECHECK.getModule() +" "+ActivityType.PROFILE_SANCTION_RECHECK.getType());
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
			PaymentOutActivityLogDto sanctionPOActivityLogDto = new PaymentOutActivityLogDto();
			sanctionPOActivityLogDto.setPaymentOutId(request.getFundsOutId());
			sanctionPOActivityLogDto.setOrgCode(request.getOrgCode());
			sanctionPOActivityLogDto.setActivityBy(userName);
			sanctionPOActivityLogDto.setCreatedBy(userName);
			sanctionPOActivityLogDto.setUpdatedBy(userName);
			sanctionPOActivityLogDto.setCreatedOn(timestamp);
			sanctionPOActivityLogDto.setTimeStatmp(timestamp);
			sanctionPOActivityLogDto.setUpdatedOn(timestamp);
			PaymentOutActivityLogDetailDto sanctionPOActivityLogDetailDto = new PaymentOutActivityLogDetailDto();
			sanctionPOActivityLogDto.setActivityLogDetailDto(sanctionPOActivityLogDetailDto);
			sanctionPOActivityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_SANCTION_RECHECK);
			sanctionPOActivityLogDetailDto.setLog(getPaymentOutSanctionLog(request,logs));
			sanctionPOActivityLogDetailDto.setCreatedBy(userName);
			sanctionPOActivityLogDetailDto.setUpdatedBy(userName);
			sanctionPOActivityLogDetailDto.setCreatedOn(timestamp);
			sanctionPOActivityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(sanctionPOActivityLogDto);
		return activityLogDtos;
	}
	
	
	private ActivityLogs getPaymentOutActivityLogsResponse(List<PaymentOutActivityLogDto> activityLogDtos){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentOutActivityLogDto profileActivityLogDto : activityLogDtos) {
		    ActivityLogData activity = new ActivityLogData();
		    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PAYMENT_OUT_SANCTION_RECHECK.getModule() +" "+ActivityType.PAYMENT_OUT_SANCTION_RECHECK.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private String getPaymentOutSanctionLog(FundsOutRequest request,EventServiceLog log){
		EntityDetails entityDetails = (EntityDetails)request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		
		if(Boolean.TRUE.equals(request.getIsContactEligible())){
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,"Sanction Contact")
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
		    		.replace(ENTITY_NAME, entityDetails.getContactName());
		} 
		if(Boolean.TRUE.equals(request.getIsBeneficiaryEligible())){
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,"Sanction Beneficiary")
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
					.replace(ENTITY_NAME, entityDetails.getBeneficiaryName());
		}
		if(Boolean.TRUE.equals(request.getIsBankEligible())){
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,"Sanction Bank")
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
		    		.replace(ENTITY_NAME, entityDetails.getBankName());
		}
		
		return null;
		
	}
	
	private EventServiceLog getPaymentOutServiceLog(FundsOutRequest request, MessageExchange exchange){
		if (Boolean.TRUE.equals(request.getIsContactEligible())) {
			Contact contact = (Contact) request.getAdditionalAttribute("contact");
			return exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.CONTACT.name(), contact.getId());
		}
		if (Boolean.TRUE.equals(request.getIsBeneficiaryEligible())) {
			return exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BENEFICIARY.name(), request.getBeneficiary().getBeneficiaryId());
		}
		if (Boolean.TRUE.equals(request.getIsBankEligible())) {
			return exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.BANK.name(), request.getBeneficiary().getBeneficiaryBankid());
		}
		return null;
	}
	
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
			activityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_SANCTION_RECHECK);
			activityLogDetailDto.setLog(getPaymentInSanctionLog(request,log));
			activityLogDetailDto.setCreatedBy(userName);
			activityLogDetailDto.setUpdatedBy(userName);
			activityLogDetailDto.setCreatedOn(timestamp);
			activityLogDetailDto.setUpdatedOn(timestamp);
			activityLogDtos.add(activityLogDto);
		return activityLogDtos;
	}
	
	
	private ActivityLogs getPaymentInActivityLogsResponse(List<PaymentInActivityLogDto> activityLogDtos){
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		for (PaymentInActivityLogDto profileActivityLogDto : activityLogDtos) {
		    ActivityLogData activity = new ActivityLogData();
		    activity.setActivity(profileActivityLogDto.getActivityLogDetailDto().getLog());
			activity.setActivityType(ActivityType.PAYMENT_IN_SANCTION_RECHECK.getModule() +" "+ActivityType.PAYMENT_IN_SANCTION_RECHECK.getType());
			activity.setCreatedBy(profileActivityLogDto.getActivityBy());
			activity.setCreatedOn(profileActivityLogDto.getCreatedOn().toString());
			activityData.add(activity);
		}
		activityLogs.setActivityLogData(activityData);
		return activityLogs;
	}
	
	private String getPaymentInSanctionLog(FundsInCreateRequest request, EventServiceLog log){
		
		EntityDetails entityDetails = (EntityDetails)request.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		
			return  ActivityTemplateEnum.RECHECK.getTemplate()
		    		.replace(RECHECK_SERVICE_NAME,SANCTION)
		    		.replace(PRE_STATUS, entityDetails.getPrevStatus())
		    		.replace(UPDATED_STATUS, log.getStatus().toLowerCase())
		    		.replace(ENTITY_NAME, entityDetails.getContactName());
	}
	
	private EventServiceLog getPaymentInServiceLog(FundsInCreateRequest request, MessageExchange exchange){
		FundsInSanctionResendRequest resendRequest = (FundsInSanctionResendRequest) request.getAdditionalAttribute("FUNDS_IN_REPEAT");
		return exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE, EntityEnum.DEBTOR.name(), resendRequest.getEntityId());
	}
	
}
