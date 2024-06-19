package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class SanctionUpdateTransformer.
 */
public class SanctionUpdateTransformer implements ITransfomer {

	public static final String ENTITY_NAME = "{ENTITY_NAME}";

	public static final String VALUE = "{VALUE}";

	public static final String PRE_STATUS = "{PRE_STATUS}";

	public static final String FIELD = "{FIELD}";

	private static final Logger LOGGER = LoggerFactory.getLogger(SanctionUpdateTransformer.class);
	
	private static final String OFACLIST = "ofaclist";

	private static final String WORLDCHECK = "worldcheck";
	
	public static final String ENTITY_TYPE = "{ENTITY_TYPE}";

	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		SanctionUpdateRequest sanctionUpdateReq = messageExchange.getRequest(SanctionUpdateRequest.class);
		EntityDetails entityDetails = (EntityDetails) messageExchange.getRequest().getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		try {
			for (SanctionUpdateData data : sanctionUpdateReq.getSanctions()) {
				updateEventServiceLogFields(
						messageExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
								EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId()),
						data, token, entityDetails);
			}
		} catch (Exception e) {
			LOGGER.error("Error", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		SanctionUpdateRequest request = messageExchange.getRequest(SanctionUpdateRequest.class);
		SanctionUpdateResponse response = messageExchange.getResponse(SanctionUpdateResponse.class);
		
		EntityDetails entityDetails = (EntityDetails) messageExchange.getRequest().getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();

		SanctionUpdateData data = request.getSanctions().get(0);
		EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId());
		try {
			ActivityLogData activity = new ActivityLogData();
			
			 	if((data.getEntityType()).equals(EntityEnum.ACCOUNT.name())){
				activity.setActivity(
						ActivityTemplateEnum.UPDATE_SANCTION.getTemplate()
						.replace(FIELD, data.getField())
						.replace(PRE_STATUS, entityDetails.getPrevStatus())
						.replace(VALUE, data.getValue())
						.replace(ENTITY_TYPE, data.getEntityType())
						.replace(ENTITY_NAME, entityDetails.getAccountName()));
			}else if ((data.getEntityType()).equals(EntityEnum.CONTACT.name()) || (data.getEntityType()).equals(EntityEnum.DEBTOR.name())){
				activity.setActivity(
						ActivityTemplateEnum.UPDATE_SANCTION.getTemplate()
						.replace(FIELD, data.getField())
						.replace(PRE_STATUS, entityDetails.getPrevStatus())
						.replace(VALUE, data.getValue())
						.replace(ENTITY_TYPE, data.getEntityType())
						.replace(ENTITY_NAME, entityDetails.getContactName()));
			}else if ((data.getEntityType()).equals(EntityEnum.BANK.name())){
				activity.setActivity(
						ActivityTemplateEnum.UPDATE_SANCTION.getTemplate()
						.replace(FIELD, data.getField())
						.replace(PRE_STATUS, entityDetails.getPrevStatus())
						.replace(VALUE, data.getValue())
						.replace(ENTITY_TYPE, data.getEntityType())
						.replace(ENTITY_NAME, entityDetails.getBankName()));  
			}else if ((data.getEntityType()).equals(EntityEnum.BENEFICIARY.name())){
				activity.setActivity(
						ActivityTemplateEnum.UPDATE_SANCTION.getTemplate()
						.replace(FIELD, data.getField())
						.replace(PRE_STATUS, entityDetails.getPrevStatus())
						.replace(VALUE, data.getValue())
						.replace(ENTITY_TYPE, data.getEntityType())
						.replace(ENTITY_NAME, entityDetails.getBeneficiaryName())); 
			}
			activity.setCreatedBy(token.getPreferredUserName());
			activity.setCreatedOn(new Timestamp(System.currentTimeMillis()).toString());
			activityData.add(activity);
			if ("profile".equalsIgnoreCase(request.getResourceType())) {
				activity.setActivityType(ActivityType.PROFILE_SANCTION_UPDATE.getModule() + " "
						+ ActivityType.PROFILE_SANCTION_UPDATE.getType());
				List<ProfileActivityLogDto> profileActivityLogDtos = new ArrayList<>();
				response.setStatus(eventServiceLog.getStatus());
				profileActivityLogDtos.add(getProfileActivityLog(activity, request, token, data.getEntityType()));
				response.addAttribute("profileActivities", profileActivityLogDtos);
			} else if ("fundsout".equalsIgnoreCase(request.getResourceType())) {
				activity.setActivityType(ActivityType.PAYMENT_OUT_SANCTION_UPDATE.getModule() + " "
						+ ActivityType.PAYMENT_OUT_SANCTION_UPDATE.getType());
				List<PaymentOutActivityLogDto> paymentOutActivityLogDtos = new ArrayList<>();
				paymentOutActivityLogDtos.add(getPaymentOutActivityLog(activity, request, token));
				response.setStatus(eventServiceLog.getStatus());
				response.addAttribute("paymentOutActivities", paymentOutActivityLogDtos);
				/** update sanction status in paymentOut table  if operation is 
				 *  SANCTION_UPDATE then update payment out table for sanction status*/
				String status = determineSanctionStatus(messageExchange,response.getStatus());
				response.addAttribute(Constants.SANCTION_OVERALLSTATUS, status);
			} else if ("fundsin".equalsIgnoreCase(request.getResourceType())) {
				activity.setActivityType(ActivityType.PAYMENT_IN_SANCTION_UPDATE.getModule() + " "
						+ ActivityType.PAYMENT_IN_SANCTION_UPDATE.getType());
				List<PaymentInActivityLogDto> paymentOutActivityLogDtos = new ArrayList<>();
				paymentOutActivityLogDtos.add(getPaymentInActivityLog(activity, request, token));
				response.setStatus(eventServiceLog.getStatus());
				response.addAttribute("paymentInActivities", paymentOutActivityLogDtos);
			}
			activityLogs.setActivityLogData(activityData);
			response.setActivityLogs(activityLogs);
			response.setEventServiceLogId(data.getEventServiceLogId());
			response.setStatus(eventServiceLog.getStatus());
		} catch (Exception e) {
			LOGGER.error("Exception in SanctionUpdateTransformer transformResponse() ", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	private void updateEventServiceLogFields(EventServiceLog eventServiceLog, SanctionUpdateData data,
			UserProfile user,EntityDetails entityDetails) {
		eventServiceLog.setUpdatedBy(user.getUserID());
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		updateSummary(eventServiceLog, data,entityDetails);
	}

	private ProfileActivityLogDto getProfileActivityLog(ActivityLogData data, SanctionUpdateRequest request,
			UserProfile user, String entityType) {
		ProfileActivityLogDto activityLogDto = new ProfileActivityLogDto();
		SanctionUpdateData sanctionData = request.getSanctions().get(0);
		try {
			activityLogDto.setAccountId(request.getAccountId());
			if ((EntityEnum.CONTACT.name()).equalsIgnoreCase(entityType)) {
				activityLogDto.setContactId(sanctionData.getEntityId());
			}
			activityLogDto.setOrgCode(request.getOrgCode());
			activityLogDto.setActivityBy(user.getPreferredUserName());
			activityLogDto.setCreatedBy(user.getPreferredUserName());
			activityLogDto.setUpdatedBy(user.getPreferredUserName());
			activityLogDto.setCreatedOn(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDto.setTimeStatmp(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDto.setUpdatedOn(Timestamp.valueOf(data.getCreatedOn()));
			ActivityLogDetailDto activityLogDetailDto = new ActivityLogDetailDto();
			activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
			activityLogDetailDto.setActivityType(ActivityType.PROFILE_SANCTION_UPDATE);
			activityLogDetailDto.setLog(data.getActivity());
			activityLogDetailDto.setCreatedBy(user.getPreferredUserName());
			activityLogDetailDto.setUpdatedBy(user.getPreferredUserName());
			activityLogDetailDto.setCreatedOn(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDetailDto.setUpdatedOn(Timestamp.valueOf(data.getCreatedOn()));
		
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return activityLogDto;
	}

	private PaymentOutActivityLogDto getPaymentOutActivityLog(ActivityLogData data, SanctionUpdateRequest request,
			UserProfile user) {
		PaymentOutActivityLogDto activityLogDto = new PaymentOutActivityLogDto();
		try {
			activityLogDto.setAccountId(request.getAccountId());
			activityLogDto.setPaymentOutId(request.getResourceId());
			activityLogDto.setOrgCode(request.getOrgCode());
			activityLogDto.setActivityBy(user.getPreferredUserName());
			activityLogDto.setCreatedBy(user.getPreferredUserName());
			activityLogDto.setUpdatedBy(user.getPreferredUserName());
			activityLogDto.setCreatedOn(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDto.setTimeStatmp(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDto.setUpdatedOn(Timestamp.valueOf(data.getCreatedOn()));
			PaymentOutActivityLogDetailDto activityLogDetailDto = new PaymentOutActivityLogDetailDto();
			activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
			activityLogDetailDto.setActivityType(ActivityType.PAYMENT_OUT_SANCTION_UPDATE);
			activityLogDetailDto.setLog(data.getActivity());
			activityLogDetailDto.setCreatedBy(user.getPreferredUserName());
			activityLogDetailDto.setUpdatedBy(user.getPreferredUserName());
			activityLogDetailDto.setCreatedOn(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDetailDto.setUpdatedOn(Timestamp.valueOf(data.getCreatedOn()));
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return activityLogDto;
	}

	private PaymentInActivityLogDto getPaymentInActivityLog(ActivityLogData data, SanctionUpdateRequest request,
			UserProfile user) {
		PaymentInActivityLogDto activityLogDto = new PaymentInActivityLogDto();
		try {
			activityLogDto.setAccountId(request.getAccountId());
			activityLogDto.setPaymentInId(request.getResourceId());
			activityLogDto.setOrgCode(request.getOrgCode());
			activityLogDto.setActivityBy(user.getPreferredUserName());
			activityLogDto.setCreatedBy(user.getPreferredUserName());
			activityLogDto.setUpdatedBy(user.getPreferredUserName());
			activityLogDto.setCreatedOn(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDto.setTimeStatmp(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDto.setUpdatedOn(Timestamp.valueOf(data.getCreatedOn()));
			PaymentInActivityLogDetailDto activityLogDetailDto = new PaymentInActivityLogDetailDto();
			activityLogDto.setActivityLogDetailDto(activityLogDetailDto);
			activityLogDetailDto.setActivityType(ActivityType.PAYMENT_IN_SANCTION_UPDATE);
			activityLogDetailDto.setLog(data.getActivity());
			activityLogDetailDto.setCreatedBy(user.getPreferredUserName());
			activityLogDetailDto.setUpdatedBy(user.getPreferredUserName());
			activityLogDetailDto.setCreatedOn(Timestamp.valueOf(data.getCreatedOn()));
			activityLogDetailDto.setUpdatedOn(Timestamp.valueOf(data.getCreatedOn()));
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return activityLogDto;
	}

	void updateContactResponse(EventServiceLog eventServiceLog, SanctionUpdateData data) {
		SanctionContactResponse response = JsonConverterUtil.convertToObject(SanctionContactResponse.class,
				eventServiceLog.getProviderResponse());
		if (response == null) {
			response = new SanctionContactResponse();
		}

		if (OFACLIST.equalsIgnoreCase(data.getField())) {
			response.setOfacList(data.getValue());
			response.setStatus(getSanctonStatus(response.getOfacList(), response.getWorldCheck()));
		}

		if (WORLDCHECK.equalsIgnoreCase(data.getField())) {
			response.setWorldCheck(data.getValue());
			response.setStatus(getSanctonStatus(response.getOfacList(), response.getWorldCheck()));
		}
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(response));
		eventServiceLog.setStatus(getSanctonStatus(response.getOfacList(), response.getWorldCheck()));
	}

	void updateSummary(EventServiceLog eventServiceLog, SanctionUpdateData data, EntityDetails entityDetails) {
		SanctionSummary summary = JsonConverterUtil.convertToObject(SanctionSummary.class,
				eventServiceLog.getSummary());
		if (summary == null) {
			return;
		}		
		updateWCAndOfacValues(summary);
		
		if (OFACLIST.equalsIgnoreCase(data.getField())) {
			entityDetails.setPrevStatus(summary.getOfacList());
			summary.setOfacList(data.getValue());
			summary.setStatus(getSanctonStatus(summary.getOfacList(), summary.getWorldCheck()));
		}

		if (WORLDCHECK.equalsIgnoreCase(data.getField())) {
			entityDetails.setPrevStatus(summary.getWorldCheck());
			summary.setWorldCheck(data.getValue());
			summary.setStatus(getSanctonStatus(summary.getOfacList(), summary.getWorldCheck()));
		}

		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setStatus(getSanctonStatus(summary.getOfacList(), summary.getWorldCheck()));
	}

	private void updateWCAndOfacValues(SanctionSummary summary) {
		if(summary.getWorldCheck().equalsIgnoreCase(Constants.NO_MATCH_FOUND) || summary.getWorldCheck().equalsIgnoreCase(Constants.NO_MATCH))
		{
			summary.setWorldCheck(Constants.SAFE);
		} else if(summary.getWorldCheck().equalsIgnoreCase(Constants.MATCH_FOUND) || summary.getWorldCheck().equalsIgnoreCase(Constants.HIGH_RISK)) {			
			summary.setWorldCheck(Constants.CONFIRMED_HIT);
		}
		
		if(summary.getOfacList().equalsIgnoreCase(Constants.NO_MATCH_FOUND) || summary.getOfacList().equalsIgnoreCase(Constants.NO_MATCH))
		{
			summary.setOfacList(Constants.SAFE);
		} else if(summary.getOfacList().equalsIgnoreCase(Constants.MATCH_FOUND) || summary.getOfacList().equalsIgnoreCase(Constants.HIGH_RISK)){			
			summary.setOfacList(Constants.CONFIRMED_HIT);
		}
	}

	void updateBeneficiaryResponse(EventServiceLog eventServiceLog, SanctionUpdateData data) {
		SanctionBeneficiaryResponse response = JsonConverterUtil.convertToObject(SanctionBeneficiaryResponse.class,
				eventServiceLog.getProviderResponse());
		if (response == null) {
			return;
		}

		if (OFACLIST.equalsIgnoreCase(data.getField())) {
			response.setOfacList(data.getValue());
		}

		if (WORLDCHECK.equalsIgnoreCase(data.getField())) {
			response.setWorldCheck(data.getValue());
		}
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(response));
		eventServiceLog.setStatus(getSanctonStatus(response.getOfacList(), response.getWorldCheck()));
	}

	void updateBankResponse(EventServiceLog eventServiceLog, SanctionUpdateData data) {
		SanctionBankResponse response = JsonConverterUtil.convertToObject(SanctionBankResponse.class,
				eventServiceLog.getProviderResponse());
		if (response == null) {
			return;
		}

		if (OFACLIST.equalsIgnoreCase(data.getField())) {
			response.setOfacList(data.getValue());
		}

		if (WORLDCHECK.equalsIgnoreCase(data.getField())) {
			response.setWorldCheck(data.getValue());
		}
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(response));
		eventServiceLog.setStatus(getSanctonStatus(response.getOfacList(), response.getWorldCheck()));
	}

	private String getSanctonStatus(String ofacValue, String worldCheck) {
		if (Constants.SAFE.equalsIgnoreCase(ofacValue) && Constants.SAFE.equalsIgnoreCase(worldCheck)) {
			return Constants.PASS;
		} else {
			return Constants.FAIL;
		}

	}
	
	/** 
	 *  Business :
	 *  To update sanction status column value in payment out table we have compared
	 *  Contact ,Bank ,Beneficiary Status into one newStatus value and putting it into sanction status column to show it on UI
	 * 
	 * Implementation :
	 * 1)This method sets the sanction status to update sanction status column in payment out table.
	 * 2)Method used for Sanction Repeat Check and Update.
	 * 3)NewStatus is based on Contact ,Bank ,Beneficiary Status.
	 * 4)If Contact ,Bank ,Beneficiary Status is Pass then 'sanction status" is 'PASS'
	 * 5)Else 'sanction status" is 'FAIL'
	 * 
	 * @param messageExchange
	 * @param currentStatus
	 * @param operation
	 * @return
	 */
	private String determineSanctionStatus(MessageExchange messageExchange, String currentStatus) {
		String newStatus;
		EntityDetails entityDetails = (EntityDetails) messageExchange.getRequest().getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		SanctionUpdateRequest sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);

		if (entityDetails.getEntityType().equalsIgnoreCase(EntityEnum.CONTACT.name())) {
			newStatus = setSanctionStatus(currentStatus,
										sanctionUpdateRequest.getSanctions().get(0).getBankStatus(),
										sanctionUpdateRequest.getSanctions().get(0).getBeneficiaryStatus());
			
		} else if (entityDetails.getEntityType().equalsIgnoreCase(EntityEnum.BENEFICIARY.name())) {
			newStatus = setSanctionStatus(sanctionUpdateRequest.getSanctions().get(0).getContactStatus(), 
										  sanctionUpdateRequest.getSanctions().get(0).getBankStatus(),
										  currentStatus);
		} else {
			newStatus = setSanctionStatus(sanctionUpdateRequest.getSanctions().get(0).getContactStatus(),
										  currentStatus,
										  sanctionUpdateRequest.getSanctions().get(0).getBeneficiaryStatus());
		}
		return newStatus;
	}
	
	/** Check Contact ,Bank ,Beneficiary Status : If 'PASS' for all three status then SET 'PASS' else SET 'FAIL' */
	private String setSanctionStatus(String sanctionContactStatus, String sanctionBankStatus,
			String sanctionBeneficiaryStatus) {
		String newStatus = null;
		try {
			Boolean onlyContactSanctionPass = ServiceStatus.PASS.name().equalsIgnoreCase(sanctionContactStatus)
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBankStatus) 
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
			Boolean onlyBankSanctionPass = ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionContactStatus) 
					&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBankStatus) 
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
			Boolean onlyBeneSanctionPass = ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionContactStatus) 
					&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBankStatus)
					&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
			Boolean allSanctionPass = ServiceStatus.PASS.name().equalsIgnoreCase(sanctionContactStatus)
					&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBankStatus)
					&& ServiceStatus.PASS.name().equalsIgnoreCase(sanctionBeneficiaryStatus);
			
			newStatus = setNewSanctionStatus(sanctionContactStatus, sanctionBankStatus, sanctionBeneficiaryStatus,
					onlyContactSanctionPass, onlyBankSanctionPass, onlyBeneSanctionPass, allSanctionPass);
		} catch (Exception e) {
			LOGGER.error("Error while setting sanction status for repeat check :: setSanctionStatus() :: ", e);

		}

		return newStatus;
	}

	/**
	 * Sets the new sanction status.
	 *
	 * @param sanctionContactStatus the sanction contact status
	 * @param sanctionBankStatus the sanction bank status
	 * @param sanctionBeneficiaryStatus the sanction beneficiary status
	 * @param onlyContactSanctionPass the only contact sanction pass
	 * @param onlyBankSanctionPass the only bank sanction pass
	 * @param onlyBeneSanctionPass the only bene sanction pass
	 * @param allSanctionPass the all sanction pass
	 * @return the string
	 */
	private String setNewSanctionStatus(String sanctionContactStatus, String sanctionBankStatus,
			String sanctionBeneficiaryStatus, Boolean onlyContactSanctionPass, Boolean onlyBankSanctionPass,
			Boolean onlyBeneSanctionPass, Boolean allSanctionPass) {
		String newStatus;
		if (Boolean.TRUE.equals(allSanctionPass) 
				|| (Boolean.TRUE.equals(onlyContactSanctionPass) 
						|| Boolean.TRUE.equals(onlyBankSanctionPass) 
						|| Boolean.TRUE.equals(onlyBeneSanctionPass))) {
			
			newStatus = ServiceStatus.PASS.name();

		} else if (ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionContactStatus)
				&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBankStatus)
				&& ServiceStatus.NOT_REQUIRED.name().equalsIgnoreCase(sanctionBeneficiaryStatus)) {
			
			newStatus = ServiceStatus.NOT_REQUIRED.name();
			
		}else {
			newStatus = ServiceStatus.FAIL.name();
		}
		return newStatus;
	}
}
