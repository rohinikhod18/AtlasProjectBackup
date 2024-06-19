package com.currenciesdirect.gtg.compliance.compliancesrv.transformer;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

public abstract class AbstractTransformer implements ITransfomer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTransformer.class);
	
	/**
	 * Creates the event service log entry.
	 *
	 * @param eventId the event id
	 * @param servceType the servce type
	 * @param providerEnum the provider enum
	 * @param entityID the entity ID
	 * @param entityVersion the entity version
	 * @param entityType the entity type
	 * @param user the user
	 * @param providerResponse the provider response
	 * @param summary the summary
	 * @return the event service log
	 */
	@SuppressWarnings("squid:S00107")
	protected EventServiceLog createEventServiceLogEntry(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary) {

		return createEventServiceLogEntryWithStatus(eventId, servceType, providerEnum, entityID, entityVersion,
				entityType, user, providerResponse, summary, ServiceStatus.NOT_PERFORMED);
	}

	
	/**
	 * Creates the event service log entry with status.
	 *
	 * @param eventId the event id
	 * @param servceType the servce type
	 * @param providerEnum the provider enum
	 * @param entityID the entity ID
	 * @param entityVersion the entity version
	 * @param entityType the entity type
	 * @param user the user
	 * @param providerResponse the provider response
	 * @param summary the summary
	 * @param serviceStatus the service status
	 * @return the event service log
	 */
	@SuppressWarnings("squid:S00107")
	protected EventServiceLog createEventServiceLogEntryWithStatus(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary, ServiceStatus serviceStatus) {

		ServiceStatus newStatus;
		if(null == serviceStatus)
			newStatus = ServiceStatus.NOT_PERFORMED;
		else
			newStatus = serviceStatus;
		
		EventServiceLog eventServiceLog = new EventServiceLog();
		eventServiceLog.setServiceName(servceType.getShortName());
		eventServiceLog.setServiceProviderName(providerEnum.getProvidername());
		eventServiceLog.setEntityId(entityID);
		eventServiceLog.setEventId(eventId);
		eventServiceLog.setEntityVersion(entityVersion);
		eventServiceLog.setEntityType(entityType);
		eventServiceLog.setStatus(newStatus.name());
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setCratedBy(user);
		eventServiceLog.setUpdatedBy(user);
		eventServiceLog.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		return eventServiceLog;
	}
	
	/**
	 *  Creates Eventservice log entry for NOT_REQUIRED status. changes done by Abhijit G
	 * @param eventId
	 * @param eventServiceLogs
	 * @param servceType
	 * @param providerEnum
	 * @param entityID
	 * @param entityVersion
	 * @param entityType
	 * @param user
	 * @param providerResponse
	 * @param summary
	 */
	@SuppressWarnings("squid:S00107")
	protected EventServiceLog createEventServiceLogEntryNotRequired(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary) {

		return createEventServiceLogEntryWithStatus(eventId, servceType, providerEnum, entityID, entityVersion, entityType,
				user, providerResponse, summary, ServiceStatus.NOT_REQUIRED);
	}
	
	protected MessageExchange createMessageExchange(ServiceMessage request, ServiceMessageResponse response, ServiceTypeEnum serviceType) {
		MessageExchange ccExchange = new MessageExchange();
		ccExchange.setRequest(request);
		ccExchange.setResponse(response);
		ccExchange.setServiceTypeEnum(serviceType);
		return ccExchange;
	}
	
	protected void updateEventServiceLogEntry(EventServiceLog eventLog, Object summary, Object providerResponse, String status ) {
		if(null != summary)
			eventLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		if(null != providerResponse)
			eventLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventLog.setStatus(status);
		eventLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
	}
	
	
	/**
	 * Purpose: 
	 * Method created to show reference ID, sanction ID on the UI
	 * 			Instead of  creating that ID every time, this method can be called by passing appropriate parameters depending upon 
	 * 			business requirement.
	 * 
	 * Implementation:
	 * 1) We are passing organization ID, entity type (CONTACT, BANK, ACCOUNT), and entity ID (ID of that entity) as
	 *    parameters.
	 * 2) Then by manipulating them we will make a respective ID in the format "orgID-character-entityID" 
	 *    which can be shown on UI
	 * @param orgID
	 * @param entityType
	 * @param entityID
	 * @return refernce ID in the format "orgID-character-entityID" (Eg. 001-C-0000237894)
	 */
	protected String createReferenceId(String orgID, String entityType, String entityID) {
		StringBuilder referenceID = new StringBuilder();
	
		try{
			String orgId = StringUtils.leftPadWithZero(3, orgID);
			String ch = "";
			
			/*
			 * If entity is
			 * Contact then set 'C'
			 * Account then set 'A'
			 * Bank then set 'B'
			 * Beneficiary then set 'P'
			 * Debtor then set 'D'
			 */
			
			switch(entityType.toUpperCase().trim()){
			case Constants.ENTITY_TYPE_CONTACT:
				ch = "-C-";
				break;
			case Constants.ENTITY_TYPE_ACCOUNT:
				ch = "-A-";
				break;
			case Constants.ENTITY_TYPE_BENEFICIARY:
				ch = "-P-";
				break;
			case Constants.ENTITY_TYPE_BANK:
				ch = "-B-";
				break;
			case Constants.ENTITY_TYPE_DEBTOR:
				ch = "-D-";
				break;
			default:
				break;
			}
			
			String id = StringUtils.leftPadWithZero(10, entityID);
			referenceID.append(orgId).append(ch).append(id);
			return referenceID.toString();
		}		
		catch (Exception e) {
			LOGGER.error("{1}",e);
			throw e;
		}
	}
	
	/**
	 * Read response.
	 *
	 * @param eventServiceLog the event service log
	 * @param frgTransId the frg trans id
	 * @param score the score
	 * @param poc the poc
	 * @param status the status
	 * @param responseJson the response json
	 * @param errorCode the error code
	 * @param errorDesc the error desc
	 */
	protected void readResponse(EventServiceLog eventServiceLog, FraugsterBaseResponse baseResponse) {

		FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
				eventServiceLog.getSummary());
		try {
			if (baseResponse.getErrorCode() != null) {
				fraugsterSummary.setErrorCode(baseResponse.getErrorCode());
				fraugsterSummary.setErrorDescription(baseResponse.getErrorDescription());
			}
			fraugsterSummary.setFrgTransId(baseResponse.getFrgTransId());
			
			setScoreInFraugsterSummaryByProvider(baseResponse, fraugsterSummary);
			
			fraugsterSummary.setFraugsterApproved(baseResponse.getFraugsterApproved());
			fraugsterSummary.setStatus(baseResponse.getStatus());
			eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(baseResponse));
			eventServiceLog.setStatus(baseResponse.getStatus());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			
		} catch (Exception e) {
			LOGGER.error("Error in reading Fraugster response", e);
			fraugsterSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
			eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
			eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		}
	}


	/**
	 * Sets the score in fraugster summary by provider.
	 *
	 * @param baseResponse the base response
	 * @param fraugsterSummary the fraugster summary
	 */
	private void setScoreInFraugsterSummaryByProvider(FraugsterBaseResponse baseResponse,
			FraugsterSummary fraugsterSummary) {
		if("Fraugster".equalsIgnoreCase(System.getProperty("fraugster.service.provider"))) {
			if(null == baseResponse.getScore())
				fraugsterSummary.setScore(ServiceStatus.NOT_PERFORMED.name());
			else
			    fraugsterSummary.setScore(baseResponse.getScore());
		} else {
			if(null == baseResponse.getPercentageFromThreshold())
				fraugsterSummary.setScore(ServiceStatus.NOT_PERFORMED.name());
			else
			    fraugsterSummary.setScore(baseResponse.getPercentageFromThreshold());
		}
	}
	
	protected void createEventServiceLogForServiceFailureWithFraugsterID(EventServiceLog eventServiceLog, Object response, String fraugsterID ){
		FraugsterSummary fraugsterSummery = new FraugsterSummary();
		fraugsterSummery.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		fraugsterSummery.setCreatedOn(new Timestamp(System.currentTimeMillis()).toString());
		
		fraugsterSummery.setFraugsterId(fraugsterID);
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummery));
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(response));
	}
}
