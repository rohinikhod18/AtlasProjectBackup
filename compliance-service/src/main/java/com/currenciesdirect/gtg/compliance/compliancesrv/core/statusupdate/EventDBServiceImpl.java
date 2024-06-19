package com.currenciesdirect.gtg.compliance.compliancesrv.core.statusupdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceInterfaceType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.DBQueryConstant;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLogSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

public class EventDBServiceImpl extends AbstractDBServiceImpl{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EventDBServiceImpl.class);
	
	private static final String PROFILE_STATUS_UPDATE = "PROFILE_STATUS_UPDATE";
	
	/**
	 * Creates the status update event.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> createStatusUpdateEvent(Message<MessageContext> message){
		Connection connection = null;
		try {
			String orgCode = message.getPayload().getOrgCode();
			ServiceInterfaceType eventType = message.getPayload().getGatewayMessageExchange().getServiceInterface();
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			String dbEventName = eventType + "_" + operation;
			Integer createdBy = token.getUserID();
			connection = getConnection(Boolean.FALSE);
			Integer eventId = null;
			beginTransaction(connection);
			if(PROFILE_STATUS_UPDATE.equalsIgnoreCase(dbEventName)) {
				eventId = handleProfileStatusUpdate(message, connection, orgCode, dbEventName, createdBy);
			}
			commitTransaction(connection);
			message.getPayload().getGatewayMessageExchange().getRequest().addAttribute(Constants.FIELD_EVENTID,eventId);
		}
		catch (Exception e) {
			LOGGER.error("error in createEvent: {1}", e);
			message.getPayload().setFailed(true);
		} finally {
			try {
				closeConnection(connection);
			} catch (ComplianceException e) {
				LOGGER.error("error in createEvent: {1}", e);
				message.getPayload().setFailed(true);
			}
		}
		return message;
	}
	
	/**
	 * Creates the status update event serivce log.
	 *
	 * @param message the message
	 * @return the message
	 */
	public Message<MessageContext> createStatusUpdateEventSerivceLog(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		try {
			StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
			EventServiceLog eventServiceLog = (EventServiceLog) statusUpdateRequest.getAdditionalAttribute(Constants.EVENT_SERVICE_LOG);
			saveIntoEventServiceLog(eventServiceLog);
			saveIntoEventServiceLogSummary(eventServiceLog);
		}catch(Exception e) {
			message.getPayload().setFailed(true);
			LOGGER.error("Error in EventDBServiceImpl createStatusUpdateEventSerivceLog mapping {1}", e);
		}
		return message;
	}
	
	/**
	 * Handle profile status update.
	 *
	 * @param message the message
	 * @param connection the connection
	 * @param orgCode the org code
	 * @param dbEventName the db event name
	 * @param createdBy the created by
	 * @return the integer
	 * @throws ComplianceException the compliance exception
	 */
	private Integer handleProfileStatusUpdate(Message<MessageContext> message, Connection connection, String orgCode,
			String dbEventName, Integer createdBy) throws ComplianceException {
		Integer eventId;
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		StatusUpdateRequest statusUpdateRequest = messageExchange.getRequest(StatusUpdateRequest.class);
		Account oldAccount = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		eventId = saveIntoEvent(connection, orgCode, dbEventName, oldAccount, null, null, createdBy);
		return eventId;
	}
	
	/**
	 * Save into event.
	 *
	 * @param connection the connection
	 * @param orgCode the org code
	 * @param eventType the event type
	 * @param account the account
	 * @param paymentInID the payment in ID
	 * @param paymentOutID the payment out ID
	 * @param createdBy the created by
	 * @return the integer
	 * @throws ComplianceException the compliance exception
	 */
	private Integer saveIntoEvent(Connection connection, String orgCode, String eventType, Account account,
			Integer paymentInID, Integer paymentOutID, Integer createdBy) throws ComplianceException {

		ResultSet rs = null;
		try (PreparedStatement statement = connection.prepareStatement(
				DBQueryConstant.INSERT_INTO_EVENT_REGISTRATION.getQuery(), Statement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, orgCode);
			statement.setString(2, eventType);
			if (null == account.getId())
				statement.setNull(3, java.sql.Types.INTEGER);
			else
				statement.setInt(3, account.getId());

			if (null == account.getVersion())
				statement.setNull(4, java.sql.Types.INTEGER);
			else
				statement.setInt(4, account.getVersion());

			if (null == paymentInID)
				statement.setNull(5, java.sql.Types.INTEGER);
			else
				statement.setInt(5, paymentInID);
			
			if (null == paymentOutID)
				statement.setNull(6, java.sql.Types.INTEGER);
			else
				statement.setInt(6, paymentOutID);
			statement.setInt(7, createdBy);
			statement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			statement.executeUpdate();
			rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
		}
		return null;
	}
	
	/**
	 * Save into event service log.
	 *
	 * @param eventServiceLog the event service log
	 */
	private void saveIntoEventServiceLog(EventServiceLog eventServiceLog) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		PreparedStatement eslStmt = null;
		try {
			eslStmt = connection.prepareStatement(DBQueryConstant.INSERT_INTO_EVENT_SERVICE_LOG.getQuery());
			eslStmt.setInt(1, eventServiceLog.getEventId());
			eslStmt.setInt(2, EntityEnum.getEntityTypeAsInteger(eventServiceLog.getEntityType()));
			if (eventServiceLog.getEntityId() != null)
				eslStmt.setInt(3, eventServiceLog.getEntityId());
			else
				eslStmt.setNull(3, Types.INTEGER);
			if (eventServiceLog.getEntityVersion() != null)
				eslStmt.setInt(4, eventServiceLog.getEntityVersion());
			else
				eslStmt.setNull(4, Types.INTEGER);
			eslStmt.setString(5, eventServiceLog.getServiceProviderName());
			eslStmt.setString(6, eventServiceLog.getServiceName());
			eslStmt.setString(7, "" + eventServiceLog.getProviderResponse());
			eslStmt.setInt(8, ServiceStatus.getStatusAsInteger(eventServiceLog.getStatus()));
			eslStmt.setString(9, "" + eventServiceLog.getSummary());
			eslStmt.setInt(10, eventServiceLog.getCratedBy());
			eslStmt.setTimestamp(11, eventServiceLog.getCreatedOn());
			eslStmt.setInt(12, eventServiceLog.getUpdatedBy());
			eslStmt.setTimestamp(13, eventServiceLog.getUpdatedOn());
			eslStmt.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(eslStmt);
			closeConnection(connection);
		}
	}
	
	/**
	 * AT-2248
	 * 
	 * This is for single update flow 
	 * 
	 * Save into event service log summary.
	 *
	 * @param eventServiceLogs the event service logs
	 * @throws ComplianceException the compliance exception
	 */
	private void saveIntoEventServiceLogSummary(EventServiceLog eventServiceLogs) throws ComplianceException {
		Connection connection = getConnection(Boolean.FALSE);
		List<EventServiceLogSummary> eventServiceLogSummaryList = new ArrayList<>(); 
		try {
			Integer eventId = eventServiceLogs.getEventId();
			eventServiceLogSummaryList = getEventServiceLogDataForCurrentOperation(eventId);
			insertOrUpdateEventServiceLogSummary(eventServiceLogSummaryList);
		} catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeConnection(connection);
		}
	}
	
	/**
	 * AT-2248
	 * Gets the event service log data for current operation.
	 *
	 * @param eventId the event id
	 * @return the event service log data for current operation
	 * @throws ComplianceException the compliance exception
	 * @throws SQLException the SQL exception
	 */
	private List<EventServiceLogSummary> getEventServiceLogDataForCurrentOperation(Integer eventId) throws ComplianceException, SQLException {
		Connection connection = getConnection(Boolean.FALSE);
		ResultSet resultSet = null;
		List<EventServiceLogSummary> eslSummaryList = new ArrayList<>();
		try(PreparedStatement preparedStatement = connection.prepareStatement(DBQueryConstant.GET_CURRENT_OPERATION_RECENT_DATA_FROM_EVENT_SERVICE_LOG.getQuery())) {
			preparedStatement.setInt(1,eventId);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				EventServiceLogSummary eslSummary = new EventServiceLogSummary();
				eslSummary.setEventServiceLogId(resultSet.getInt("EventServiceLogID"));
				eslSummary.setEntityType(resultSet.getInt("EntityType"));
				eslSummary.setServiceType(resultSet.getInt("ServiceType"));
				eslSummary.setStatus(resultSet.getInt("Status"));
				eslSummary.setEntityVersion(resultSet.getInt("EntityVersion"));
				eslSummary.setEntityId(resultSet.getInt("EntityID"));
				eslSummaryList.add(eslSummary);
			}
		}catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}finally {
			closeResultset(resultSet);
			closeConnection(connection);
		}
		return eslSummaryList;
	}
	
	/**
	 * AT-2248
	 * Insert or update event service log summary.
	 *
	 * @param eventServiceLogSummaryList the event service log summary list
	 * @throws ComplianceException the compliance exception
	 * @throws SQLException the SQL exception
	 */
	private void insertOrUpdateEventServiceLogSummary(List<EventServiceLogSummary> eventServiceLogSummaryList) throws ComplianceException, SQLException{
		Connection connection = getConnection(Boolean.FALSE);
		try(PreparedStatement preparedStatement = connection.prepareStatement(DBQueryConstant.INSERT_UPDATE_INTO_EVENT_SERVICE_LOG_SUMMARY.getQuery())) {
			beginTransaction(connection);
			for(EventServiceLogSummary eventServiceLogSummary : eventServiceLogSummaryList) {
				if(!ServiceStatus.NOT_PERFORMED.getServiceStatusAsInteger().equals(eventServiceLogSummary.getStatus())
						&& !ServiceStatus.SERVICE_FAILURE.getServiceStatusAsInteger().equals(eventServiceLogSummary.getStatus())
						&& !ServiceStatus.NOT_REQUIRED.getServiceStatusAsInteger().equals(eventServiceLogSummary.getStatus())) {
					preparedStatement.setInt(1,eventServiceLogSummary.getEntityId());
					preparedStatement.setInt(2,eventServiceLogSummary.getEntityType());
					preparedStatement.setInt(3,eventServiceLogSummary.getServiceType());
					preparedStatement.setInt(4,eventServiceLogSummary.getEventServiceLogId());
					preparedStatement.setInt(5,eventServiceLogSummary.getStatus());
					preparedStatement.addBatch();
				}
			}
			preparedStatement.executeBatch();
			commitTransaction(connection);
		}catch(Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}finally {
			closeConnection(connection);
		}
	}
}