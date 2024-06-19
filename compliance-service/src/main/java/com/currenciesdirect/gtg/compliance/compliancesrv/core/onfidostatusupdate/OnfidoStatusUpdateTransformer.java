package com.currenciesdirect.gtg.compliance.compliancesrv.core.onfidostatusupdate;

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
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class OnfidoStatusUpdateTransformer implements ITransfomer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OnfidoStatusUpdateTransformer.class);
	
	/** The Constant ENTITY_NAME. */
	public static final String ENTITY_NAME = "{ENTITY_NAME}";

	/** The Constant VALUE. */
	public static final String VALUE = "{VALUE}";

	/** The Constant PRE_STATUS. */
	public static final String PRE_STATUS = "{PRE_STATUS}";

	/** The Constant FIELD. */
	public static final String FIELD = "{FIELD}";

	/** The Constant ENTITY_TYPE. */
	public static final String ENTITY_TYPE = "{ENTITY_TYPE}";
	
	/** The Constant REVIEWED. */
	private static final String REVIEWED = "Reviewed";
	
	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		OnfidoUpdateRequest request = messageExchange.getRequest(OnfidoUpdateRequest.class);
		EntityDetails entityDetails = (EntityDetails) messageExchange.getRequest().getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		try {
			for (OnfidoUpdateData data : request.getOnfido()) {
				updateEventServiceLogFields(
						messageExchange.getEventServiceLog(ServiceTypeEnum.ONFIDO_SERVICE,
								EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId()),
						data, token, entityDetails);
			}
		} catch (Exception e) {
			LOGGER.error("Error in OnfidoStatusUpdateTransformer transformRequest {1}", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		OnfidoUpdateRequest request = messageExchange.getRequest(OnfidoUpdateRequest.class);
		OnfidoUpdateResponse response = messageExchange.getResponse(OnfidoUpdateResponse.class);
		
		EntityDetails entityDetails = (EntityDetails) messageExchange.getRequest().getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogData> activityData = new ArrayList<>();
		
		OnfidoUpdateData data = request.getOnfido().get(0);
		EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(ServiceTypeEnum.ONFIDO_SERVICE,
				EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId());
		
		try {
			ActivityLogData activity = new ActivityLogData();
			if((data.getEntityType()).equals(EntityEnum.CONTACT.name())) {
				activity.setActivity(
						ActivityTemplateEnum.UPDATE_ONFIDO.getTemplate()
						.replace(FIELD, data.getField())
						.replace(PRE_STATUS, entityDetails.getPrevStatus())
						.replace(VALUE, data.getValue())
						.replace(ENTITY_TYPE, data.getEntityType())
						.replace(ENTITY_NAME, entityDetails.getContactName()));
			}
			activity.setCreatedBy(token.getPreferredUserName());
			activity.setCreatedOn(new Timestamp(System.currentTimeMillis()).toString());
			activityData.add(activity);
			if ("profile".equalsIgnoreCase(request.getResourceType())) {
				activity.setActivityType(ActivityType.PROFILE_ONFIDO_UPDATE.getModule() + " "
						+ ActivityType.PROFILE_ONFIDO_UPDATE.getType());
				List<ProfileActivityLogDto> profileActivityLogDtos = new ArrayList<>();
				response.setStatus(eventServiceLog.getStatus());
				profileActivityLogDtos.add(getProfileActivityLog(activity, request, token, data.getEntityType()));
				response.addAttribute("profileActivities", profileActivityLogDtos);
			}
			activityLogs.setActivityLogData(activityData);
			response.setActivityLogs(activityLogs);
			response.setEventServiceLogId(data.getEventServiceLogId());
			response.setStatus(eventServiceLog.getStatus());
		}
		catch(Exception e) {
			LOGGER.error("Exception in OnfidoStatusUpdateTransformer transformResponse() {1}", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Update event service log fields.
	 *
	 * @param eventServiceLog the event service log
	 * @param data the data
	 * @param user the user
	 * @param entityDetails the entity details
	 */
	private void updateEventServiceLogFields(EventServiceLog eventServiceLog, OnfidoUpdateData data,
			UserProfile user,EntityDetails entityDetails) {
		eventServiceLog.setUpdatedBy(user.getUserID());
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		
		OnfidoSummary summary = JsonConverterUtil.convertToObject(OnfidoSummary.class,eventServiceLog.getSummary());
		if (summary == null) {
			return;
		}
		if (REVIEWED.equalsIgnoreCase(data.getField())) {
			entityDetails.setPrevStatus(summary.getReviewed());
			summary.setReviewed(data.getValue());
			summary.setStatus(getOnfidoStatus(data.getValue()));
		}
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setStatus(getOnfidoStatus(summary.getReviewed()));
	}
	
	/**
	 * Gets the onfido status.
	 *
	 * @param reviewed the reviewed
	 * @return the onfido status
	 */
	private String getOnfidoStatus(String reviewed) {
		if (Constants.ACCEPTED.equalsIgnoreCase(reviewed)) {
			return Constants.PASS;
		} else {
			return Constants.FAIL;
		}
	}
	
	/**
	 * Gets the profile activity log.
	 *
	 * @param data the data
	 * @param request the request
	 * @param user the user
	 * @param entityType the entity type
	 * @return the profile activity log
	 */
	private ProfileActivityLogDto getProfileActivityLog(ActivityLogData data, OnfidoUpdateRequest request,
			UserProfile user, String entityType) {
		ProfileActivityLogDto activityLogDto = new ProfileActivityLogDto();
		OnfidoUpdateData onfidoData = request.getOnfido().get(0);
		try {
			activityLogDto.setAccountId(request.getAccountId());
			if ((EntityEnum.CONTACT.name()).equalsIgnoreCase(entityType)) {
				activityLogDto.setContactId(onfidoData.getEntityId());
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
			activityLogDetailDto.setActivityType(ActivityType.PROFILE_ONFIDO_UPDATE);
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
	
}
