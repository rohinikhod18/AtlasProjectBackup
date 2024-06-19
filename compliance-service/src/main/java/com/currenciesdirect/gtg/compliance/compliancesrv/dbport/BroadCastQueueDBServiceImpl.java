/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.IBroadCastQueueDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastQueueRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.MQProviderResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MQMessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class BroadCastQueueDBServiceImpl.
 *
 * @author manish
 */
@SuppressWarnings("squid:S2095")
public class BroadCastQueueDBServiceImpl extends AbstractDao implements IBroadCastQueueDBService {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BroadCastQueueDBServiceImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IBroadCastQueueDBService#isMessageForBroadcast()
	 */
	@Override
	public Boolean isMessageForBroadcast() throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_BROADCAST_MESSAGE_FROM_DB.getQuery());

			rs = statement.executeQuery();
			if (rs.next()) {
				return Boolean.TRUE;
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IBroadCastQueueDBService#loadMessageFromDB(org.springframework.messaging.
	 * Message)
	 */
	@Override
	public Message<MQMessageContext> loadMessageFromDB(Message<MQMessageContext> message) throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		BroadCastQueueRequest broadCastQueueRequest = new BroadCastQueueRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_BROADCAST_MESSAGE_FROM_DB.getQuery());

			rs = statement.executeQuery();
			if (rs.next()) {
				broadCastQueueRequest.setId(rs.getInt("ID"));
				broadCastQueueRequest.setOrganizationID(rs.getInt("OrganizationID"));
				broadCastQueueRequest
						.setEntityType(BroadCastEntityTypeEnum.getBraodCastEntityTypeAsString(rs.getInt("EntityType")));
				broadCastQueueRequest.setAccountID(rs.getInt("AccountID"));
				broadCastQueueRequest.setContactID(rs.getInt("ContactID"));
				broadCastQueueRequest.setPaymentInID(rs.getInt("PaymentInID"));
				broadCastQueueRequest.setPaymentOutID(rs.getInt("PaymentOutID"));
				broadCastQueueRequest
						.setStatusJson(JsonConverterUtil.convertToObject(Object.class, rs.getString("StatusJson")));
				broadCastQueueRequest.setTimestamp(rs.getTimestamp("CreatedOn"));
				message.getPayload().setRetryCount(rs.getInt("RetryCount"));
				message.getPayload().setRequest(broadCastQueueRequest);
			} else {
				message.getPayload().setNoMessageLeft(Boolean.TRUE);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IBroadCastQueueDBService#updateBroadCastStatusToDB(org.springframework.
	 * messaging.Message)
	 */
	@Override
	public Message<MQMessageContext> updateBroadCastStatusToDB(Message<MQMessageContext> message)
			throws ComplianceException {
		PreparedStatement statement = null;

		Connection connection = null;
		BroadCastQueueRequest request = null;
		MQProviderResponse response = null;
		Integer retryCount = null;
		try {
			retryCount = message.getPayload().getRetryCount();
			request = (BroadCastQueueRequest) message.getPayload().getRequest();
			response = message.getPayload().getMqProviderResponse();
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_BROADCAST_STATUS_TO_DB.getQuery());
			if (Constants.SUCCESS.equalsIgnoreCase(response.getStatus())) {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.DELIVERED.toString()));
			} /**
				 * if enterprise service is down status will be not_found, set
				 * retry count -1
				 **/
			else if (Constants.NOT_FOUND.equalsIgnoreCase(response.getStatus())) {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.FAILED.toString()));
				retryCount = -1;
			} else {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.FAILED.toString()));
				retryCount++;
			}

			statement.setInt(2, retryCount);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, request.getId());
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return message;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IBroadCastQueueDBService#saveIntoBroadcastQueue(org.springframework.
	 * messaging.Message)
	 */
	@Override
	public Message<MessageContext> saveIntoBroadcastQueue(Message<MessageContext> message) throws ComplianceException {
		PreparedStatement broadcastEventStmt = null;
		Connection connection = null;
		BroadcastEventToDB stpBroadcastEvent = null;
		try {
			MessageExchange exchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.SAVE_TO_BROADCAST_SERVICE);
			stpBroadcastEvent = (BroadcastEventToDB) exchange.getRequest();
			connection = getConnection(Boolean.FALSE);
			broadcastEventStmt = connection.prepareStatement(DBQueryConstant.SAVE_INTO_BROADCAST_QUEUE.getQuery());
			broadcastEventStmt.setString(1, stpBroadcastEvent.getOrgCode());
			if (stpBroadcastEvent.getEntityType() != null) { //handle NPE - For AT-4355
				broadcastEventStmt.setInt(2, BroadCastEntityTypeEnum
						.getBraodCastEntityTypeAsInteger(stpBroadcastEvent.getEntityType().toString()));
			} else {
				broadcastEventStmt.setNull(2, Types.INTEGER);
			}
			
			if (stpBroadcastEvent.getAccountId() != null) {
				broadcastEventStmt.setInt(3, stpBroadcastEvent.getAccountId());
			} else {
				broadcastEventStmt.setNull(3, Types.INTEGER);
			}

			if (stpBroadcastEvent.getContactId() != null) {
				broadcastEventStmt.setInt(4, stpBroadcastEvent.getContactId());
			} else {
				broadcastEventStmt.setNull(4, Types.INTEGER);
			}
			if (stpBroadcastEvent.getPaymentInId() != null) {
				broadcastEventStmt.setInt(5, stpBroadcastEvent.getPaymentInId());
			} else {
				broadcastEventStmt.setNull(5, Types.INTEGER);
			}
			if (stpBroadcastEvent.getPaymentOutId() != null) {
				broadcastEventStmt.setInt(6, stpBroadcastEvent.getPaymentOutId());
			} else {
				broadcastEventStmt.setNull(6, Types.INTEGER);
			}
			broadcastEventStmt.setString(7, JsonConverterUtil.convertToJsonWithNull(stpBroadcastEvent.getStatusJson()));
			broadcastEventStmt.setInt(8,
					BroadCastStatusEnum.getBraodCastStatusAsInteger(stpBroadcastEvent.getDeliveryStatus()));
			broadcastEventStmt.setTimestamp(9, stpBroadcastEvent.getDeliverOn());
			broadcastEventStmt.setInt(10, stpBroadcastEvent.getCreatedBy());
			broadcastEventStmt.setTimestamp(11, stpBroadcastEvent.getCreatedOn());
			broadcastEventStmt.executeUpdate();
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error("Error in BroadCastQueueDBServiceImpl class saveIntoBroadcastQueue method:", e);
		} finally {
			closePrepareStatement(broadcastEventStmt);
			closeConnection(connection);
		}
		return message;
	}

	/**
	 * Save into broadcast queue.
	 *
	 * @param event
	 *            the broadcast event to DB
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void saveIntoBroadcastQueue(BroadcastEventToDB event) throws ComplianceException {
		PreparedStatement preparedStmt = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStmt = connection.prepareStatement(DBQueryConstant.SAVE_INTO_BROADCAST_QUEUE.getQuery());
			preparedStmt.setString(1, event.getOrgCode());
			preparedStmt.setInt(2,
					BroadCastEntityTypeEnum.getBraodCastEntityTypeAsInteger(event.getEntityType().toString()));
			if (event.getAccountId() != null) {
				preparedStmt.setInt(3, event.getAccountId());
			} else {
				preparedStmt.setNull(3, Types.INTEGER);
			}

			if (event.getContactId() != null) {
				preparedStmt.setInt(4, event.getContactId());
			} else {
				preparedStmt.setNull(4, Types.INTEGER);
			}
			if (event.getPaymentInId() != null) {
				preparedStmt.setInt(5, event.getPaymentInId());
			} else {
				preparedStmt.setNull(5, Types.INTEGER);
			}
			if (event.getPaymentOutId() != null) {
				preparedStmt.setInt(6, event.getPaymentOutId());
			} else {
				preparedStmt.setNull(6, Types.INTEGER);
			}
			preparedStmt.setString(7, JsonConverterUtil.convertToJsonWithNull(event.getStatusJson()));
			preparedStmt.setInt(8, BroadCastStatusEnum.getBraodCastStatusAsInteger(event.getDeliveryStatus()));
			preparedStmt.setTimestamp(9, event.getDeliverOn());
			preparedStmt.setInt(10, event.getCreatedBy());
			preparedStmt.setTimestamp(11, event.getCreatedOn());
			preparedStmt.executeUpdate();
		} catch (Exception e) {
			LOG.error("Error in BroadCastQueueDBServiceImpl class saveIntoBroadcastQueue method:", e);
		} finally {
			closePrepareStatement(preparedStmt);
			closeConnection(connection);
		}
	}
	
	public ComplianceAccount getContactsDetails(String accountSfId)  throws ComplianceException{
		PreparedStatement preparedStmt = null;
		Connection connection = null;
		ResultSet rs = null;
		ComplianceAccount details = new ComplianceAccount();
		List<ComplianceContact> contactList = new ArrayList<>();
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStmt = connection.prepareStatement(DBQueryConstant.GET_CONTACT_DETAILS_FOR_STATUSBROADCAST_IN_MQ.getQuery());
			preparedStmt.setString(1, accountSfId);
			rs = preparedStmt.executeQuery();
			while(rs.next()) {
				ComplianceContact cc = new ComplianceContact();
				cc.setContactSFID(rs.getString("ContactSFID"));
				cc.setTradeContactID(rs.getInt("TradeContactID"));
				contactList.add(cc);
			}
			details.setContacts(contactList);
		}
		catch(Exception e) {
			LOG.error("Error in BroadCastQueueDBServiceImpl class getContactsDetails method:", e);
		}
		finally {
				closePrepareStatement(preparedStmt);
				closeConnection(connection);
				closeResultset(rs);
		}
		return details;
	}
	
}
