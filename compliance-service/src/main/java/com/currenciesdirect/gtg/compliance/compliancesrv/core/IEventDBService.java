package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.util.List;
import java.util.Map;

import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;

/**
 * The Interface IEventDBService.
 */
public interface IEventDBService extends IDBService{

	/**
	 * Creates the event.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	Message<MessageContext> createEvent(Message<MessageContext> message) throws ComplianceException;
	
	/**
	 * Update event.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	Message<MessageContext> updateEvent(Message<MessageContext> message) throws ComplianceException;
	
	/**
	 * Creates the event serivce log.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	Message<MessageContext> createEventSerivceLog(Message<MessageContext> message) throws ComplianceException; 
	
	/**
	 * Update event serivce log.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	Message<MessageContext> updateEventSerivceLog(Message<MessageContext> message) throws ComplianceException; 
	
	/**
	 * Gets the event serivce log.
	 *
	 * @param eventIds the event ids
	 * @param serviceName the service name
	 * @param contactId the contact id
	 * @return the event serivce log
	 * @throws ComplianceException the compliance exception
	 */
	Map<String,EventServiceLog> getEventSerivceLog(List<Integer> eventIds, String serviceName,Integer contactId) throws ComplianceException;
	
	/**
	 * Checks if is event exist by DB id.
	 *
	 * @param eventId the event id
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isEventExistByDBId(Integer eventId) throws ComplianceException;
	
	/**
	 * Checks if is service performed.
	 *
	 * @param entityId the entity id
	 * @param serviceName the service name
	 * @param entityType the entity type
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails isKycServicePerformed(Integer entityId, String entityType)throws ComplianceException;
	
	/**
	 * Checks if is service performed.
	 *
	 * @param entityId the entity id
	 * @param serviceName the service name
	 * @param entityType the entity type
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails getPreviousSanctionDetails(Integer entityId, String entityType)throws ComplianceException;
	
	/**
	 * Gets the previous fraugster details.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @return the previous fraugster details
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails getPreviousFraugsterDetails(Integer entityId, String entityType)throws ComplianceException;
	
	/**
	 * Checks if is event service log id.
	 *
	 * @param eventServiceLogId the event service log id
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isEventServiceLogId(Integer eventServiceLogId)throws ComplianceException;
	
	/**
	 * Gets the event serivce log by id.
	 *
	 * @param eventServiceLogId the event service log id
	 * @return the event serivce log by id
	 * @throws ComplianceException the compliance exception
	 */
	Map<String,EventServiceLog> getEventSerivceLogById(Integer eventServiceLogId) throws ComplianceException;
	
	/**
	 * Gets the event serivce log by id and service.
	 *
	 * @param eventServiceLogId the event service log id
	 * @param serviceName the service name
	 * @param message the message
	 * @return the event serivce log by id and service
	 * @throws ComplianceException the compliance exception
	 */
	void getEventSerivceLogByIdAndService(Integer eventServiceLogId, String serviceName,Message<MessageContext> message)
			throws ComplianceException;
	
	/**
	 * Gets the fundsout event serivce log by id and service.
	 *
	 * @param eventServiceLogId the event service log id
	 * @param serviceName the service name
	 * @param message the message
	 * @return the fundsout event serivce log by id and service
	 * @throws ComplianceException the compliance exception
	 */
	void getFundsOutEventSerivceLogByIdAndService(Integer eventServiceLogId, String serviceName,Message<MessageContext> message)
					throws ComplianceException;
			
	/**
	 * Checks if is service performed for funds out custom check.
	 *
	 * @param paymentOutId the payment out id
	 * @param serviceName the service name
	 * @return the entity details
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails isServicePerformedForFundsOutCustomCheck(Integer paymentOutId ,String serviceName)throws ComplianceException;
	
	/**
	 * Checks if is service performed for funds in custom check.
	 *
	 * @param paymentInId the payment in id
	 * @param serviceName the service name
	 * @return the entity details
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails isServicePerformedForFundsInCustomCheck(Integer paymentInId ,String serviceName)throws ComplianceException;
	
	/**
	 * Gets the funds in event serivce log by id and service.
	 *
	 * @param eventServiceLogId the event service log id
	 * @param serviceName the service name
	 * @param message the message
	 * @return the funds in event serivce log by id and service
	 * @throws ComplianceException the compliance exception
	 */
	void getFundsInEventSerivceLogByIdAndService(Integer eventServiceLogId, String serviceName,Message<MessageContext> message)
			throws ComplianceException;
	
	/**
	 * Checks if is Sanction Service is performed.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	  @return the entity details
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails isSanctionPerformed(Integer entityId, String entityType)
			throws ComplianceException;

	/**
	 * Creates the update event serivce log.
	 *
	 * @param message the message
	 * @return the message
	 */
	Message<MessageContext> createUpdateEventSerivceLog(Message<MessageContext> message);
	
	/**
	 * Gets the previous blacklist details.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @return the previous blacklist details
	 * @throws ComplianceException the compliance exception
	 */
	EntityDetails getPreviousBlacklistDetails(Integer entityId, String entityType)throws ComplianceException;
	
	Message<MessageContext> createEventTMMQ(Message<MessageContext> message) throws ComplianceException;
}
