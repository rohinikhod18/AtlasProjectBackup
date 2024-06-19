package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountMQRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentBoardCastResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransactionMonitoringMQDBService;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsout.FundsOutComplianceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionSignupMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;

/**
 * The Class TransactionMonitoringMQDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
@Component
public class TransactionMonitoringMQDBServiceImpl extends AbstractDao implements ITransactionMonitoringMQDBService {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(TransactionMonitoringMQDBServiceImpl.class);
	
	/** The Constant RETRYCOUNT. */
	private static final String RETRYCOUNT = "transactionmonitoring.retrycount";
	
	/** The Constant DORMANT_FLAG. */
	private static final String DORMANT_FLAG = "DormantFlag";
	
	/** The Constant PAYMENT_IN. */
	private static final String PAYMENT_IN = "payment_in";
	
	/** The Constant PAYMENT_IN_UPDATE. */
	private static final String PAYMENT_IN_UPDATE = "payment_in_update";
	
	/** The Constant PAYMENT_OUT. */
	private static final String PAYMENT_OUT = "payment_out";
	
	/** The Constant PAYMENT_OUT_UPDATE. */
	private static final String PAYMENT_OUT_UPDATE = "payment_out_update";
	
	/** The Constant OLD_PAYMENT_STATUS. */
	private static final String OLD_PAYMENT_STATUS = "oldPaymentStatus";
	
	/**
	 * Checks if is message for broadcast.
	 *
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	public Boolean isMessageForBroadcast() throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_TM_MQ_MESSAGE_FROM_DB.getQuery());
			statement.setInt(1, Integer.parseInt(System.getProperty(RETRYCOUNT)));

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

	/**
	 * Load message from DB.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	@Override
	public Message<MessageContext> loadMessageFromDB(Message<MessageContext> message) throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = new TransactionMonitoringMQRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_TM_MQ_MESSAGE_FROM_DB.getQuery());
			statement.setInt(1, Integer.parseInt(System.getProperty(RETRYCOUNT)));

			rs = statement.executeQuery();
			if (rs.next()) {
				transactionMonitoringMQRequest.setId(rs.getInt("ID"));
				transactionMonitoringMQRequest.setOrgCode(rs.getString("OrganizationCode"));
				transactionMonitoringMQRequest.setRequestType(rs.getString("RequestType"));
				transactionMonitoringMQRequest.setAccountID(rs.getInt("AccountID"));
				transactionMonitoringMQRequest.setContactID(rs.getInt("ContactID"));
				transactionMonitoringMQRequest.setPaymentInID(rs.getInt("PaymentInID"));
				transactionMonitoringMQRequest.setPaymentOutID(rs.getInt("PaymentOutID"));
				transactionMonitoringMQRequest
						.setRequestJson(JsonConverterUtil.convertToObject(Object.class, rs.getString("RequestJson")));
				transactionMonitoringMQRequest.setCreatedBy(rs.getInt("CreatedBy"));
				transactionMonitoringMQRequest.setTimestamp(rs.getTimestamp("CreatedOn"));
				transactionMonitoringMQRequest.setIsPresent(rs.getInt("isPresent"));
				transactionMonitoringMQRequest.addAttribute(DORMANT_FLAG, rs.getInt(DORMANT_FLAG));
				message.getPayload().setRetryCount(rs.getInt("RetryCount"));
				message.getPayload().getMessageExchange(ServiceTypeEnum.GATEWAY_SERVICE)
						.setRequest(transactionMonitoringMQRequest);
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
		PreparedStatement statement = null;
		
		Boolean accStatus = Boolean.FALSE;
		Boolean payInStauts = Boolean.FALSE;
		Boolean payOutStatus = Boolean.FALSE;

		Connection connection = null;
		Integer retryCount = null;
		try {
			retryCount = message.getPayload().getRetryCount();
			MessageExchange exchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
					.getRequest();
			TransactionMonitoringMQServiceResponse response = (TransactionMonitoringMQServiceResponse) exchange
					.getResponse();
			
			if(response.getTransactionMonitoringSignupResponse() != null) {
				 accStatus = Constants.PASS.equalsIgnoreCase(response.getTransactionMonitoringSignupResponse()
						.getTransactionMonitoringAccountSignupResponse().getStatus());
			} else if(response.getTransactionMonitoringPaymentInResponse() != null) {
				 payInStauts = Constants.PASS.equalsIgnoreCase(response.getTransactionMonitoringPaymentInResponse().getStatus()) || 
						 Constants.FAIL.equalsIgnoreCase(response.getTransactionMonitoringPaymentInResponse().getStatus());
			} else if(response.getTransactionMonitoringPaymentOutResponse() != null) {
				 payOutStatus = Constants.PASS.equalsIgnoreCase(response.getTransactionMonitoringPaymentOutResponse().getStatus()) ||
						 Constants.FAIL.equalsIgnoreCase(response.getTransactionMonitoringPaymentOutResponse().getStatus());
			}
			
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_TM_MQ_STATUS_TO_DB.getQuery());
			if (Boolean.TRUE.equals(accStatus) || Boolean.TRUE.equals(payInStauts) || Boolean.TRUE.equals(payOutStatus)) {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.DELIVERED.toString()));
			} else {
				statement.setInt(1,
						BroadCastStatusEnum.getBraodCastStatusAsInteger(BroadCastStatusEnum.FAILED.toString()));
				retryCount++;
			}

			statement.setInt(2, retryCount);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, transactionMonitoringMQRequest.getId());
			statement.executeUpdate();
			
			message.getPayload().setRetryCount(retryCount);
			
			if(Boolean.TRUE.equals(accStatus) && transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.SIGN_UP)) {
				updateAccTMFlag(transactionMonitoringMQRequest.getAccountID(), (Integer)transactionMonitoringMQRequest.getAdditionalAttribute(DORMANT_FLAG));
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return message;

	}
	
	
	/**
	 * Update acc TM flag.
	 *
	 * @param accID the acc ID
	 * @param dormantFlag 
	 * @throws ComplianceException the compliance exception
	 */
	private void updateAccTMFlag(Integer accID, Integer dormantFlag) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		String accountTMFlag = "1";
		try {
			if(dormantFlag == 1)
				accountTMFlag = "4";
			
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_ACC_TM_FLAG_MQ.getQuery().replace("#", accountTMFlag));
			statement.setInt(1, accID);
			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
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
			TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
					.getRequest();
			
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.DELETE_DELIVERED_RECORD_FROM_TMMQ.getQuery());
			statement.setInt(1, transactionMonitoringMQRequest.getAccountID());
			Integer count =  statement.executeUpdate();
			if(count > 0) {
				LOG.warn("Deleted Delivered Record From TransactionMonitoringMQ Where AccountID : {}",transactionMonitoringMQRequest.getAccountID());
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
		return message;
	}
	
	/**
	 * Update account intuition risk level.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> updateAccountIntuitionRiskLevel(Message<MessageContext> message) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		String clientRiskLevel = null;
		
		try {
			MessageExchange messageExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
			TransactionMonitoringMQServiceResponse transactionMonitoringMQResponse = (TransactionMonitoringMQServiceResponse) messageExchange
					.getResponse();
			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) exchange
					.getRequest();
			
			TransactionMonitoringMQServiceRequest mqRequest = (TransactionMonitoringMQServiceRequest) messageExchange
					.getRequest();

			if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.SIGN_UP)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.UPDATE)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.ADD_CONTACT)) {
				clientRiskLevel = updateIntuitionRiskLevelForSignup(clientRiskLevel, messageExchange,
						transactionMonitoringMQResponse);

			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN) 
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN_UPDATE)) {

				clientRiskLevel = updateIntuitionRiskLevelAndStatusForPaymentIn(messageExchange,
						transactionMonitoringMQResponse, transactionMonitoringMQRequest, mqRequest);
				
			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT) 
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT_UPDATE)) {

				clientRiskLevel = updateIntuitionRiskLevelAndStatusForPaymentOut(messageExchange,
						transactionMonitoringMQResponse, transactionMonitoringMQRequest, mqRequest);
				
			}

			if (clientRiskLevel != null) {
				connection = getConnection(Boolean.FALSE);
				statement = connection.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_INTUITION_RISK_LEVEL.getQuery());

				statement.setString(1, clientRiskLevel);
				statement.setInt(2, 1);
				statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				statement.setInt(4, transactionMonitoringMQRequest.getAccountID());

				statement.executeUpdate();
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
		return message;
	}
	
	
	/**
	 * @param messageExchange
	 * @param transactionMonitoringMQResponse
	 * @param transactionMonitoringMQRequest
	 * @param mqRequest
	 * @return
	 * @throws ComplianceException
	 */
	private String updateIntuitionRiskLevelAndStatusForPaymentOut(MessageExchange messageExchange,
			TransactionMonitoringMQServiceResponse transactionMonitoringMQResponse,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest,
			TransactionMonitoringMQServiceRequest mqRequest) throws ComplianceException {
		String clientRiskLevel;
		TransactionMonitoringPaymentsOutRequest request = mqRequest.getTransactionMonitoringPaymentsOutRequest();
		TransactionMonitoringPaymentOutResponse tmPayOutResponse = transactionMonitoringMQResponse
				.getTransactionMonitoringPaymentOutResponse();

		EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(
				ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
				transactionMonitoringMQRequest.getPaymentOutID());

		TransactionMonitoringPaymentsSummary summary = JsonConverterUtil
				.convertToObject(TransactionMonitoringPaymentsSummary.class, eventServiceLog.getSummary());

		clientRiskLevel = summary.getClientRiskLevel();
		
		if (clientRiskLevel != null && transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT)) {
			updatePaymentOutIntuitionClientRiskLevel(clientRiskLevel,transactionMonitoringMQRequest.getPaymentOutID());
		}
		
		if (FundsOutComplianceStatus.HOLD.name().equals(request.getAdditionalAttribute(OLD_PAYMENT_STATUS))) {
			updatePaymentOutStatus(tmPayOutResponse.getPaymentStatus(), transactionMonitoringMQRequest.getPaymentOutID());
		}
		
		updatePaymentIntuitionStatus(tmPayOutResponse.getStatus(), transactionMonitoringMQRequest.getPaymentOutID(), transactionMonitoringMQRequest.getRequestType());

		return clientRiskLevel;
	}

	/**
	 * @param messageExchange
	 * @param transactionMonitoringMQResponse
	 * @param transactionMonitoringMQRequest
	 * @param mqRequest
	 * @return
	 * @throws ComplianceException
	 */
	private String updateIntuitionRiskLevelAndStatusForPaymentIn(MessageExchange messageExchange,
			TransactionMonitoringMQServiceResponse transactionMonitoringMQResponse,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest,
			TransactionMonitoringMQServiceRequest mqRequest) throws ComplianceException {
		String clientRiskLevel;
		TransactionMonitoringPaymentsInRequest request = mqRequest.getTransactionMonitoringPaymentsInRequest();
		TransactionMonitoringPaymentInResponse tmPayInResponse = transactionMonitoringMQResponse
				.getTransactionMonitoringPaymentInResponse();

		EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(
				ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
				transactionMonitoringMQRequest.getPaymentInID());

		TransactionMonitoringPaymentsSummary summary = JsonConverterUtil
				.convertToObject(TransactionMonitoringPaymentsSummary.class, eventServiceLog.getSummary());

		clientRiskLevel = summary.getClientRiskLevel();
		
		if(clientRiskLevel != null && transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN)) {
			updatePaymentInIntuitionClientRiskLevel(clientRiskLevel, transactionMonitoringMQRequest.getPaymentInID());
		}
		
		if (FundsInComplianceStatus.HOLD.name().equals(request.getAdditionalAttribute(OLD_PAYMENT_STATUS))) {
			updatePaymentInStatus(tmPayInResponse.getPaymentStatus(), transactionMonitoringMQRequest.getPaymentInID());
		}
		updatePaymentIntuitionStatus(tmPayInResponse.getStatus(), transactionMonitoringMQRequest.getPaymentInID(),transactionMonitoringMQRequest.getRequestType());

		return clientRiskLevel;
	}

	/**
	 * @param clientRiskLevel
	 * @param messageExchange
	 * @param transactionMonitoringMQResponse
	 * @return
	 */
	private String updateIntuitionRiskLevelForSignup(String clientRiskLevel, MessageExchange messageExchange,
			TransactionMonitoringMQServiceResponse transactionMonitoringMQResponse) {
		if (transactionMonitoringMQResponse != null && !transactionMonitoringMQResponse
				.getTransactionMonitoringSignupResponse().getTransactionMonitoringAccountSignupResponse()
				.getStatus().equalsIgnoreCase(Constants.SERVICE_FAILURE)) {

			TransactionMonitoringAccountSignupResponse tmSingUpAccountResponse = transactionMonitoringMQResponse
					.getTransactionMonitoringSignupResponse().getTransactionMonitoringAccountSignupResponse();

			EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(
					ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND, EntityEnum.ACCOUNT.name(),
					tmSingUpAccountResponse.getAccountId());

			TransactionMonitoringAccountSummary accSummary = JsonConverterUtil
					.convertToObject(TransactionMonitoringAccountSummary.class, eventServiceLog.getSummary());
			clientRiskLevel = accSummary.getRiskLevel();

		}
		return clientRiskLevel;
	}

	/**
	 * Update payment in intuition client risk level.
	 *
	 * @param clientRiskLevel the client risk level
	 * @param paymentInID the payment in ID
	 * @throws ComplianceException the compliance exception
	 */
	private void updatePaymentInIntuitionClientRiskLevel(String clientRiskLevel, Integer paymentInID)
			throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_IN_INTUITION_CLIENT_RISK_LEVEL.getQuery());
			
			statement.setString(1, clientRiskLevel);
			statement.setInt(2, 1);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, paymentInID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
	}

	
	/**
	 * Update payment out intuition client risk level.
	 *
	 * @param clientRiskLevel the client risk level
	 * @param paymentOutID the payment out ID
	 * @throws ComplianceException the compliance exception
	 */
	private void updatePaymentOutIntuitionClientRiskLevel(String clientRiskLevel, Integer paymentOutID)
			throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_INTUITION_CLIENT_RISK_LEVEL.getQuery());
			
			statement.setString(1, clientRiskLevel);
			statement.setInt(2, 1);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, paymentOutID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
	}
	
	/**
	 * Update payment intuition status.
	 *
	 * @param status the status
	 * @param paymentID the payment ID
	 * @param paymentMethod the payment method
	 * @throws ComplianceException the compliance exception
	 */
	private void updatePaymentIntuitionStatus(String status, Integer paymentID, String paymentMethod)
			throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			if (paymentMethod.equalsIgnoreCase(PAYMENT_IN) || paymentMethod.equalsIgnoreCase(PAYMENT_IN_UPDATE)) {
				statement = connection
						.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_IN_INTUITION_CHECK_STATUS.getQuery());
			} else {
				statement = connection
						.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_INTUITION_CHECK_STATUS.getQuery());
			}

			statement.setInt(1, ServiceStatus.getStatusAsInteger(status));
			statement.setInt(2, 1);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, paymentID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
	}
	
	private void updatePaymentInStatus(String paymentStatus, Integer paymentInID) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_IN_STATUS.getQuery());
			
			statement.setInt(1, 1);
			statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			statement.setInt(3, FundsInComplianceStatus.getFundsInComplianceStatusAsInteger(paymentStatus));
			statement.setInt(4, paymentInID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
	}
	
	private void updatePaymentOutStatus(String paymentStatus, Integer paymentOutID) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection.prepareStatement(DBQueryConstant.UPDATE_PAYMENT_OUT_STATUS.getQuery());
			
			statement.setInt(1, 1);
			statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			statement.setInt(3, FundsOutComplianceStatus.getFundsInComplianceStatusAsInteger(paymentStatus));
			statement.setInt(4, paymentOutID);

			statement.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {

			closePrepareStatement(statement);
			closeConnection(connection);

		}
	}
	
	/**
	 * Update status in board cast queue.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> updateStatusInBoardCastQueue(Message<MessageContext> message)
			throws ComplianceException {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		MessageExchange exchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
		TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) messageExchange
				.getRequest();
		TransactionMonitoringMQServiceRequest mqRequest = (TransactionMonitoringMQServiceRequest) exchange.getRequest();
		TransactionMonitoringMQServiceResponse mqResponse = (TransactionMonitoringMQServiceResponse) exchange
				.getResponse();

		if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN)
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN_UPDATE)
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT)
				|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT_UPDATE)) {
			int entityType = 0;
			String statusJson = null;
			if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_IN_UPDATE)) {

				TransactionMonitoringPaymentsInRequest request = mqRequest.getTransactionMonitoringPaymentsInRequest();
				TransactionMonitoringPaymentInResponse responsePayIn = mqResponse
						.getTransactionMonitoringPaymentInResponse();
				entityType = 4;
				statusJson = getPaymentInResponse(responsePayIn, request, mqRequest.getOrgCode());

				if (!responsePayIn.getPaymentStatus()
						.equalsIgnoreCase(request.getAdditionalAttribute(OLD_PAYMENT_STATUS).toString())) {
					saveIntoStatusBoardCastQueue(transactionMonitoringMQRequest, entityType, statusJson);
				}
				
			} else if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT)
					|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(PAYMENT_OUT_UPDATE)) {
				TransactionMonitoringPaymentsOutRequest request = mqRequest
						.getTransactionMonitoringPaymentsOutRequest();
				TransactionMonitoringPaymentOutResponse responsePayOut = mqResponse
						.getTransactionMonitoringPaymentOutResponse();
				entityType = 5;
				statusJson = getPaymentOutResponse(responsePayOut, request, mqRequest.getOrgCode());
				
				if (!responsePayOut.getPaymentStatus()
						.equalsIgnoreCase(request.getAdditionalAttribute(OLD_PAYMENT_STATUS).toString())) {
					saveIntoStatusBoardCastQueue(transactionMonitoringMQRequest, entityType, statusJson);
				}
			}


		}

		return message;

	}

	/**
	 * @param transactionMonitoringMQRequest
	 * @param entityType
	 * @param statusJson
	 * @throws ComplianceException
	 */
	private void saveIntoStatusBoardCastQueue(TransactionMonitoringMQRequest transactionMonitoringMQRequest,
			int entityType, String statusJson) throws ComplianceException {
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.SAVE_INTO_BROADCAST_QUEUE.getQuery());
			preparedStatement.setString(1, transactionMonitoringMQRequest.getOrgCode());
			preparedStatement.setInt(2, entityType);
			preparedStatement.setInt(3, transactionMonitoringMQRequest.getAccountID());
			if (transactionMonitoringMQRequest.getContactID() != null) {
				preparedStatement.setInt(4, transactionMonitoringMQRequest.getContactID());
			} else {
				preparedStatement.setNull(4, Types.INTEGER);
			}
			if (transactionMonitoringMQRequest.getPaymentInID() != 0) {
				preparedStatement.setInt(5, transactionMonitoringMQRequest.getPaymentInID());
			} else {
				preparedStatement.setNull(5, Types.INTEGER);
			}
			if (transactionMonitoringMQRequest.getPaymentOutID() != 0) {
				preparedStatement.setInt(6, transactionMonitoringMQRequest.getPaymentOutID());
			} else {
				preparedStatement.setNull(6, Types.INTEGER);
			}
			preparedStatement.setString(7, statusJson);
			preparedStatement.setInt(8, 1);
			preparedStatement.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(10, 1);
			preparedStatement.setTimestamp(11, (new Timestamp(System.currentTimeMillis())));
			preparedStatement.executeUpdate();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}

	private String getPaymentInResponse(TransactionMonitoringPaymentInResponse responsePayIn,
			TransactionMonitoringPaymentsInRequest request, String orgCode) {

		TransactionMonitoringPaymentBoardCastResponse boardCastResponse = new TransactionMonitoringPaymentBoardCastResponse();
		boardCastResponse.setOsrID(UUID.randomUUID().toString());
		boardCastResponse.setOrgCode(orgCode);
		boardCastResponse.setTradeContractNumber(request.getContractNumber());
		boardCastResponse.setTradePaymentID(request.getTransactionId());
		boardCastResponse.setStatus(responsePayIn.getPaymentStatus());
		boardCastResponse.setResponseCode(responsePayIn.getResponseCode());
		boardCastResponse.setResponseDescription(responsePayIn.getResponseDescription());
		return JsonConverterUtil.convertToJsonWithNull(boardCastResponse);

	}

	private String getPaymentOutResponse(TransactionMonitoringPaymentOutResponse responsePayIn,
			TransactionMonitoringPaymentsOutRequest request, String orgCode) {

		TransactionMonitoringPaymentBoardCastResponse boardCastResponse = new TransactionMonitoringPaymentBoardCastResponse();
		boardCastResponse.setOsrID(UUID.randomUUID().toString());
		boardCastResponse.setOrgCode(orgCode);
		boardCastResponse.setTradeContractNumber(request.getContractNumber());
		boardCastResponse.setTradePaymentID(request.getTransactionId());
		boardCastResponse.setStatus(responsePayIn.getPaymentStatus());
		boardCastResponse.setResponseCode(responsePayIn.getResponseCode());
		boardCastResponse.setResponseDescription(responsePayIn.getResponseDescription());
		return JsonConverterUtil.convertToJsonWithNull(boardCastResponse);

	}
	
	
	public List<TransactionMonitoringMQRequest> loadAllMessageFromDB() throws ComplianceException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Connection connection = null;
		List<TransactionMonitoringMQRequest> transactionMonitoringMQRequestList = new ArrayList<>();

		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(DBQueryConstant.GET_ALL_TM_MQ_MESSAGE_FROM_DB.getQuery());
			statement.setInt(1, Integer.parseInt(System.getProperty(RETRYCOUNT)));

			rs = statement.executeQuery();
			while (rs.next()) {
				TransactionMonitoringMQRequest transactionMonitoringMQRequest = new TransactionMonitoringMQRequest();
				transactionMonitoringMQRequest.setId(rs.getInt("ID"));
				transactionMonitoringMQRequest.setOrgCode(rs.getString("OrganizationCode"));
				transactionMonitoringMQRequest.setRequestType(rs.getString("RequestType"));
				transactionMonitoringMQRequest.setAccountID(rs.getInt("AccountID"));
				transactionMonitoringMQRequest.setContactID(rs.getInt("ContactID"));
				transactionMonitoringMQRequest.setPaymentInID(rs.getInt("PaymentInID"));
				transactionMonitoringMQRequest.setPaymentOutID(rs.getInt("PaymentOutID"));
				transactionMonitoringMQRequest
						.setRequestJson(JsonConverterUtil.convertToObject(Object.class, rs.getString("RequestJson")));
				transactionMonitoringMQRequest.setCreatedBy(rs.getInt("CreatedBy"));
				transactionMonitoringMQRequest.setTimestamp(rs.getTimestamp("CreatedOn"));
				transactionMonitoringMQRequest.setIsPresent(rs.getInt("isPresent"));
				
				transactionMonitoringMQRequestList.add(transactionMonitoringMQRequest);
				
				
			} 
			
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return transactionMonitoringMQRequestList;
	}
	
	/**
	 * Check account is present or not.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	//AT-4773
	public Message<MessageContext> checkAccountIsPresentOrNot(Message<MessageContext> message)
			throws ComplianceException {
		TransactionMonitoringAccountMQRequest tmRequest = (TransactionMonitoringAccountMQRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		PreparedStatement statement = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection
					.prepareStatement(DBQueryConstant.GET_ACCOUNT_ID_USING_TRADE_ACCOUNT_NUMBER.getQuery());
			statement.setString(1, tmRequest.getTradeAccountNumber());
			rs = statement.executeQuery();
			if (!rs.next()) {
				message.getPayload().setFailed(true);
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
	 * Save reg sync to MQ.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	//AT-4473
	public Message<MessageContext> saveIntuitionRegSyncToMQ(Message<MessageContext> message)
			throws ComplianceException {
		MessageExchange messageExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);
		IntuitionSignupMQRequest request = (IntuitionSignupMQRequest) messageExchange.getRequest();
		PreparedStatement broadcastEventStmt = null;
		Connection connection = null;
		
		
		try {
			
			String orgCode = (String) request.getAdditionalAttribute("orgCode");
			Integer id = (Integer) request.getAdditionalAttribute("PrimaryContactId");
			
			String jsonAccountSignupRequest = JsonConverterUtil.convertToJsonWithoutNull(request);
			
			connection = getConnection(Boolean.FALSE);
			broadcastEventStmt = connection.prepareStatement(DBQueryConstant.SAVE_INTO_TRANSACTION_MONITORING_MQ.getQuery());
			
			broadcastEventStmt.setString(1, orgCode);
			broadcastEventStmt.setString(2, "signup");
			if (request.getAccount() != null && request.getAccount().get(0).getAccountId()!= null) {
				broadcastEventStmt.setInt(3, Integer.parseInt(request.getAccount().get(0).getAccountId()));
			} else {
				broadcastEventStmt.setNull(3, Types.INTEGER);
			}

			if (id != null && id != 0) {
				broadcastEventStmt.setInt(4, id);
			} else {
				broadcastEventStmt.setNull(4, Types.INTEGER);
			}
			
			broadcastEventStmt.setNull(5, Types.INTEGER);
			broadcastEventStmt.setNull(6, Types.INTEGER);
			broadcastEventStmt.setString(7, jsonAccountSignupRequest);
			broadcastEventStmt.setInt(8, 1);
			broadcastEventStmt.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			broadcastEventStmt.setInt(10, 1);
			broadcastEventStmt.setTimestamp(11, new Timestamp(System.currentTimeMillis()));
			broadcastEventStmt.setInt(12, 1);
			broadcastEventStmt.executeUpdate();
			
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(broadcastEventStmt);
			closeConnection(connection);
		}
		
		LOG.info("---------------- Registration Sync Intuition record {} successfully saved to TransactionMonitoringMQ table", request.getTradeAccNumber());
		
		return message;
	}
}
