package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.postcardtransactionmq.PostCardTransactionMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

@SuppressWarnings("squid:S2095")
@Component
public class PostCardTransactionMQDBServiceImpl extends AbstractDao {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(PostCardTransactionMQDBServiceImpl.class);

	/** The Constant RETRYCOUNT. */
	private static final String RETRYCOUNT = "transactionmonitoring.retrycount";

	public Boolean isMessageForBroadcast() throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_POST_CARD_TM_MQ_MESSAGE_FROM_DB.getQuery());
			statement.setInt(1, Integer.parseInt(System.getProperty(RETRYCOUNT)));

			rs = statement.executeQuery();
			if (rs.next()) {
				return Boolean.TRUE;
			}

		} catch (Exception e) {
			LOG.error("Error in PostCardTransactionMQDBServiceImpl isMessageForBroadcast method");
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return Boolean.FALSE;
	}

	public Message<MessageContext> loadMessageFromDB(Message<MessageContext> message) throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		PostCardTransactionMQRequest postCardMQRequest = new PostCardTransactionMQRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_POST_CARD_TM_MQ_MESSAGE_FROM_DB.getQuery());
			statement.setInt(1, Integer.parseInt(System.getProperty(RETRYCOUNT)));

			rs = statement.executeQuery();
			if (rs.next()) {
				postCardMQRequest.setId(rs.getInt("ID"));
				postCardMQRequest.setTransactionID(rs.getString("TransactionID"));
				postCardMQRequest.setCardRequestType(rs.getString("CardRequestType"));
				postCardMQRequest
						.setRequestJson(JsonConverterUtil.convertToObject(Object.class, rs.getString("RequestJson")));
				postCardMQRequest.setCreatedBy(rs.getInt("CreatedBy"));
				postCardMQRequest.setIsPresent(rs.getInt("isPresent"));
				message.getPayload().setRetryCount(rs.getInt("RetryCount"));
				message.getPayload().getMessageExchange(ServiceTypeEnum.GATEWAY_SERVICE).setRequest(postCardMQRequest);
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

	/**
	 * Update MQ status to DB.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> updateMQStatusToDB(Message<MessageContext> message) throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		PostCardTransactionMQRequest postCardMQRequest = (PostCardTransactionMQRequest) messageExchange.getRequest();
		MessageExchange messageExch = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		TransactionMonitoringPostCardTransactionResponse tmCardResponse = (TransactionMonitoringPostCardTransactionResponse) messageExch
				.getResponse();

		PreparedStatement statement = null;
		Connection connection = null;
		Integer retryCount = null;
		Boolean isFailedRequest = Boolean.FALSE;
		Boolean cardStatus = Boolean.FALSE;
		try {
			retryCount = message.getPayload().getRetryCount();
			
			if (tmCardResponse != null) {
				isFailedRequest = (Boolean) tmCardResponse.getAdditionalAttribute("isFailedRequest");
				cardStatus = Constants.PASS.equalsIgnoreCase(tmCardResponse.getStatus())
						|| Constants.FAIL.equalsIgnoreCase(tmCardResponse.getStatus()) || isFailedRequest;
			}

			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_POST_CARD_TM_MQ_STATUS.getQuery());
			if (Boolean.TRUE.equals(cardStatus)) {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.DELIVERED.toString()));
			} else {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.FAILED.toString()));
				retryCount++;
			}

			statement.setInt(2, retryCount);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, postCardMQRequest.getId());
			statement.executeUpdate();

			message.getPayload().setRetryCount(retryCount);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return message;
	}
	
	/**
	 * Removes the delivered record from TMMQ table.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> removeDeliveredRecordFromTMMQTable(Message<MessageContext> message) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			PostCardTransactionMQRequest postCardMQRequest = (PostCardTransactionMQRequest) messageExchange.getRequest();
			
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.DELETE_DELIVERED_RECORD_FROM_POST_CARD_TMMQ.getQuery());
			statement.setInt(1, postCardMQRequest.getId());
			Integer count =  statement.executeUpdate();
			if(count > 0) {
				LOG.warn("Deleted Record From PostCardTransactionMonitoringMQ Where ID : {}",postCardMQRequest.getTransactionID());
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
		return message;
	}

}