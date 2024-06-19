/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;
import com.currenciesdirect.kafka.transaction.CDTransaction;
import com.currenciesdirect.kafka.transaction.MessageData;
import com.currenciesdirect.kafka.transaction.MessageHeader;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedTxRequestAudit;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.kafkatxdatalake.KafkaFailedRetryRequest;

/**
 * The Class KafkaAuditDBServiceImpl.
 *
 * @author prashant.verma
 */
@Service
@SuppressWarnings("squid:S2095")
public class KafkaDataLakeTxDBServiceImpl extends AbstractDao {
	
	private static final Logger LOG = LoggerFactory.getLogger(KafkaDataLakeTxDBServiceImpl.class);

	/**
	 * Save failed kafka request in KafkaRequestFailedAudit.
	 *
	 * @param CDTransaction
	 *            the message
	 * @param String
	 *            the messageId
	 * @param String
	 *            the topic                      
	 * @return Long
	 * 			  the auditId
	 */
	public Long auditFailedKafkaRequest(CDTransaction cdTransaction, String messageId, String topic) throws ComplianceException {
		
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		Long auditId = null;
		Connection conn = null;
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.AUDIT_FAILED_KAFKA_INSERT_QUERY.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setString(1, messageId);
			preparedStatment.setString(2, cdTransaction.toString());
			preparedStatment.setString(3, topic);
			preparedStatment.setString(4, "PENDING");
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.execute();
			rs = preparedStatment.getGeneratedKeys();
			if (rs.next()) {
				auditId = rs.getLong(1);
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return auditId;
	}

	/**
	 * Save into payments in.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInRequest
	 *            the funds in request
	 * @param accountID
	 *            the account ID
	 * @param contactID
	 *            the contact ID
	 * @param userID
	 *            the user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	
	public List<KafkaFailedTxRequestAudit> getFailedTxRequests(KafkaFailedRetryRequest kafkaFailedRetryRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		KafkaFailedTxRequestAudit kafkaFailedTx = null;
		Connection conn = null;
		List<KafkaFailedTxRequestAudit> failedAuditTxList = new ArrayList<>();
		LOG.info("getFailedTxRequests() - kafkaFailedRetryRequest : "+kafkaFailedRetryRequest);
		try {
			String ids = kafkaFailedRetryRequest.getAuditIds()!=null ? String.join(",", kafkaFailedRetryRequest.getAuditIds()) : null;
			String startDate = kafkaFailedRetryRequest.getFromDate();
			String endDate = kafkaFailedRetryRequest.getToDate();
			
			conn = getConnection(Boolean.FALSE);
			
			String query = (DBQueryConstant.GET_FAILED_KAFKA_TX_QUERY.getQuery());
					if(ids != null) {
						query = query + " and AuditId in (?) ";
					}
					if(startDate!=null && endDate !=null) {
						query = query + " and (CreatedOn > ? and CreatedOn < ?)";
					}
			
			LOG.info("getFailedTxRequests() - query : "+query);

			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setString(1, "PENDING");
			if(ids != null && startDate!=null && endDate !=null) {
				preparedStatment.setString(2, ids);
				preparedStatment.setTimestamp(3, DateTimeFormatter.convertStringToTimestamp(startDate));
				preparedStatment.setTimestamp(4, DateTimeFormatter.convertStringToTimestamp(endDate));
			}
			else if(ids != null) {
				preparedStatment.setString(2, ids);
			}
			else if(startDate!=null && endDate !=null) {
				preparedStatment.setTimestamp(2, DateTimeFormatter.convertStringToTimestamp(startDate));
				preparedStatment.setTimestamp(3, DateTimeFormatter.convertStringToTimestamp(endDate));
			}
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				kafkaFailedTx = new KafkaFailedTxRequestAudit();
				kafkaFailedTx.setAuditId(rs.getLong("AuditId"));
				kafkaFailedTx.setMessageId(rs.getString("MessageId"));
				kafkaFailedTx.setMessage(rs.getString("MessageContent"));
				kafkaFailedTx.setTopic(rs.getString("Topic"));
				kafkaFailedTx.setStatus(rs.getString("Status"));
				kafkaFailedTx.setCreatedOn(rs.getTimestamp("CreatedOn"));
				kafkaFailedTx.setUpdatedOn(rs.getTimestamp("UpdatedOn"));
				kafkaFailedTx.setErrorMessage(rs.getString("ErrorMessage"));
				failedAuditTxList.add(kafkaFailedTx);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return failedAuditTxList;
	}
	
	public CDTransaction getFundsInTxDetails(Integer tradePaymentId) throws ComplianceException {
		String method = "KafkaDataLakeTxDBServiceImpl() - getFundsInTxDetails : ";
		LOG.info(method + "tradePaymentId : "+tradePaymentId);

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		CDTransaction cdTransaction = null;
		Connection conn = null;
		try {
			conn = getConnection(Boolean.FALSE);
			
			String query = (DBQueryConstant.GET_PAYMENT_IN_TX_DETAILS_QUERY.getQuery());
			LOG.info(method + "query : "+query);
			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setInt(1, tradePaymentId);
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				cdTransaction = new CDTransaction();
				
				MessageHeader header = new MessageHeader();
				header.setCdId(tradePaymentId.toString());
				header.setSource(Constants.HEADER_SOURCE_ATLAS);
				header.setTransactionDateTime((new Timestamp(System.currentTimeMillis())).toString());
				header.setTransactionName(Constants.TRANSACTION_NAME_PAYIN);
				cdTransaction.setMessageHeader(header);
				
				MessageData messageData = new MessageData();

				messageData.setSystemId(rs.getString(Constants.ID_KEY));
				messageData.setOrganizationID(rs.getString(Constants.ORG_ID_KEY)!=null?rs.getInt(Constants.ORG_ID_KEY):null);
				messageData.setOrganizationCode(rs.getString(Constants.ORG_CODE_KEY));
				messageData.setOrganizationName(rs.getString(Constants.ORG_NAME_KEY));
				messageData.setAccountID(rs.getString(Constants.ACCOUNT_ID_KEY)!=null?rs.getInt(Constants.ACCOUNT_ID_KEY):null);
				messageData.setAccountName(rs.getString(Constants.ACCOUNT_NAME_KEY));
				messageData.setTitanAccountId(rs.getString(Constants.TRADE_ACCOUNT_ID_KEY)!=null?rs.getInt(Constants.TRADE_ACCOUNT_ID_KEY):null);
				messageData.setCRMAccountID(rs.getString(Constants.CRM_ACCOUNT_ID_KEY));
				messageData.setTitanAccountNumber(rs.getString(Constants.TRADE_ACCOUNT_NUMBER_KEY));
				messageData.setContactID(rs.getString(Constants.CONTACT_ID_KEY)!=null?rs.getInt(Constants.CONTACT_ID_KEY):null);
				messageData.setContactName(rs.getString(Constants.CONTACT_NAME_KEY));
				messageData.setInstructionNumber(rs.getString(Constants.TRADE_CONTRACT_NUMBER_KEY));
				messageData.setTitanPaymentID(rs.getString(Constants.TRADE_PAYMENT_ID_KEY)!=null?rs.getInt(Constants.TRADE_PAYMENT_ID_KEY):null);
				messageData.setCreatedByUserId(rs.getString(Constants.CREATED_BY_KEY)!=null?rs.getInt(Constants.CREATED_BY_KEY):null);
				messageData.setCreatedBySSOUserId(rs.getString(Constants.CREATED_BY_SSOUSER_ID_KEY));
				messageData.setCreatedOn(rs.getString(Constants.CREATED_ON_KEY));
				messageData.setUpdatedByUserId(rs.getString(Constants.UPDATED_BY_KEY)!=null?rs.getInt(Constants.UPDATED_BY_KEY):null);
				messageData.setUpdatedBySSOUserId(rs.getString(Constants.UPDATED_BY_SSOUSER_ID_KEY));
				messageData.setUpdatedOn(rs.getString(Constants.UPDATED_ON_KEY));
				messageData.setTransactionDate(rs.getString(Constants.TRANSACTION_DATE_KEY));
				messageData.setDescription(rs.getString(Constants.DESCRIPTION_KEY));
				messageData.setComplianceStatusId(rs.getString(Constants.COMPLIANCE_STATUS_ID_KEY)!=null?rs.getInt(Constants.COMPLIANCE_STATUS_ID_KEY):null);
				messageData.setComplianceStatus(rs.getString(Constants.COMPLIANCE_STATUS_KEY));
				messageData.setComplianceDoneOn(rs.getString(Constants.COMPLIANCE_DONE_ON_KEY));
				messageData.setFraugsterStatusId(rs.getString(Constants.FRAUGSTER_STATUS_ID_KEY)!=null?rs.getInt(Constants.FRAUGSTER_STATUS_ID_KEY):null);
				messageData.setFraugsterStatus(rs.getString(Constants.FRAUGSTER_STATUS_KEY));
				messageData.setSanctionStatusId(rs.getString(Constants.SANCTION_STATUS_ID_KEY)!=null?rs.getInt(Constants.SANCTION_STATUS_ID_KEY):null);
				messageData.setSanctionStatus(rs.getString(Constants.SANCTION_STATUS_KEY));
				messageData.setBlacklistStatusId(rs.getString(Constants.BLACKLIST_STATUS_ID_KEY)!=null?rs.getInt(Constants.BLACKLIST_STATUS_ID_KEY):null);
				messageData.setBlacklistStatus(rs.getString(Constants.BLACKLIST_STATUS_KEY));
				messageData.setCustomCheckStatusId(rs.getString(Constants.CUSTOM_CHECK_STATUS_ID_KEY)!=null?rs.getInt(Constants.CUSTOM_CHECK_STATUS_ID_KEY):null);
				messageData.setCustomCheckStatus(rs.getString(Constants.CUSTOM_CHECK_STATUS_KEY));
				messageData.setIsOnQueue(rs.getString(Constants.IS_ON_QUEUE_KEY)!=null?rs.getInt(Constants.IS_ON_QUEUE_KEY):null);
				messageData.setSTPFlag(rs.getString(Constants.STP_FLAG_KEY)!=null?rs.getInt(Constants.STP_FLAG_KEY):null);
				messageData.setLegacyTradePaymentId(rs.getString(Constants.LEGACY_TRADE_PAYMENT_ID_KEY)!=null?rs.getInt(Constants.LEGACY_TRADE_PAYMENT_ID_KEY):null);
				messageData.setLegacyTradeContractNumber(rs.getString(Constants.LEGACY_TRADE_CONTRACT_NUMBER_KEY));
				messageData.setLegalEntityID(rs.getString(Constants.LEGAL_ENTITY_ID_KEY)!=null?rs.getInt(Constants.LEGAL_ENTITY_ID_KEY):null);
				messageData.setLegalEntityCode(rs.getString(Constants.LEGAL_ENTITY_CODE_KEY));
				messageData.setLegalEntityName(rs.getString(Constants.LEGAL_ENTITY_NAME_KEY));
				messageData.setInitialStatus(rs.getString(Constants.INITIAL_STATUS_KEY));
				messageData.setIntuitionClientRiskLevel(rs.getString(Constants.INTUITION_CLIENT_RISK_LEVEL_KEY));
				messageData.setIntuitionCheckStatusId(rs.getString(Constants.INTUITION_CHECK_STATUS_ID_KEY)!=null?rs.getInt(Constants.INTUITION_CHECK_STATUS_ID_KEY):null);
				messageData.setIntuitionCheckStatus(rs.getString(Constants.INTUITION_CHECK_STATUS_KEY));
				messageData.setDebitCardFraudCheckStatusId(rs.getString(Constants.DEBITCARD_FRAUD_CHECK_STATUS_ID_KEY)!=null?rs.getInt(Constants.DEBITCARD_FRAUD_CHECK_STATUS_ID_KEY):null);
				messageData.setDebitCardFraudCheckStatus(rs.getString(Constants.DEBITCARD_FRAUD_CHECK_STATUS_KEY));
				messageData.setPaymentClearedOn(rs.getString(Constants.PAYMENT_CLEARED_ON_KEY));
				messageData.setActivityID(rs.getString(Constants.ACTIVITY_ID_KEY)!=null?rs.getInt(Constants.ACTIVITY_ID_KEY):null);
				messageData.setActivityBy(rs.getString(Constants.ACTIVITY_BY_KEY)!=null?rs.getInt(Constants.ACTIVITY_BY_KEY):null);
				messageData.setActivityByUserId(rs.getString(Constants.ACTIVITY_BY_USER_ID_KEY));
				messageData.setActivityTimestamp(rs.getString(Constants.ACTIVITY_TIMESTAMP_KEY));
				messageData.setActivityComments(rs.getString(Constants.ACTIVITY_COMMENTS_KEY));
				messageData.setActivityCreatedBy(rs.getString(Constants.ACTIVITY_CREATED_BY_KEY)!=null?rs.getInt(Constants.ACTIVITY_CREATED_BY_KEY):null);
				messageData.setActivityCreatedByUserId(rs.getString(Constants.ACTIVITY_CREATED_BY_USER_ID_KEY));
				messageData.setActivityCreatedOn(rs.getString(Constants.ACTIVITY_CREATED_ON_KEY));
				messageData.setActivityType(rs.getString(Constants.ACTIVITY_TYPE_KEY)!=null?rs.getInt(Constants.ACTIVITY_TYPE_KEY):null);
				messageData.setActivityTypeModule(rs.getString(Constants.ACTIVITY_TYPE_MODULE_KEY));
				messageData.setActivityTypeValue(rs.getString(Constants.ACTIVITY_TYPE_VALUE_KEY));
				messageData.setActivityDetailsCreatedOn(rs.getString(Constants.ACTIVITY_DETAILS_CREATED_ON_KEY));
				messageData.setActivityLog(rs.getString(Constants.ACTIVITY_LOG_KEY));
				messageData.setDeleted((rs.getInt(Constants.DELETED_KEY)==1)?true:false);
				
				cdTransaction.setMessageData(messageData);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		LOG.info(method + "cdTransaction : "+cdTransaction);
		return cdTransaction;
	}
	
	public CDTransaction getFundsOutTxDetails(Integer tradePaymentId) throws ComplianceException {

		String method = "KafkaDataLakeTxDBServiceImpl() - getFundsOutTxDetails : ";
		LOG.info(method + "tradePaymentId : "+tradePaymentId);

		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		CDTransaction cdTransaction = null;
		Connection conn = null;
		try {
			conn = getConnection(Boolean.FALSE);
			
			String query = (DBQueryConstant.GET_PAYMENT_OUT_TX_DETAILS_QUERY.getQuery());
			LOG.info(method + "query : "+query);

			preparedStatment = conn.prepareStatement(query);
			preparedStatment.setInt(1, tradePaymentId);
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				cdTransaction = new CDTransaction();
				
				MessageHeader header = new MessageHeader();
				header.setCdId(tradePaymentId.toString());
				header.setSource(Constants.HEADER_SOURCE_ATLAS);
				header.setTransactionDateTime((new Timestamp(System.currentTimeMillis())).toString());
				header.setTransactionName(Constants.TRANSACTION_NAME_PAYOUT);
				cdTransaction.setMessageHeader(header);
				
				MessageData messageData = new MessageData();
				messageData.setSystemId(rs.getString(Constants.ID_KEY));
				messageData.setOrganizationID(rs.getString(Constants.ORG_ID_KEY)!=null?rs.getInt(Constants.ORG_ID_KEY):null);
				messageData.setOrganizationCode(rs.getString(Constants.ORG_CODE_KEY));
				messageData.setOrganizationName(rs.getString(Constants.ORG_NAME_KEY));
				messageData.setAccountID(rs.getString(Constants.ACCOUNT_ID_KEY)!=null?rs.getInt(Constants.ACCOUNT_ID_KEY):null);
				messageData.setAccountName(rs.getString(Constants.ACCOUNT_NAME_KEY));
				messageData.setTitanAccountId(rs.getString(Constants.TRADE_ACCOUNT_ID_KEY)!=null?rs.getInt(Constants.TRADE_ACCOUNT_ID_KEY):null);
				messageData.setCRMAccountID(rs.getString(Constants.CRM_ACCOUNT_ID_KEY));
				messageData.setTitanAccountNumber(rs.getString(Constants.TRADE_ACCOUNT_NUMBER_KEY));
				messageData.setContactID(rs.getString(Constants.CONTACT_ID_KEY)!=null?rs.getInt(Constants.CONTACT_ID_KEY):null);
				messageData.setContactName(rs.getString(Constants.CONTACT_NAME_KEY));
				messageData.setInstructionNumber(rs.getString(Constants.TRADE_CONTRACT_NUMBER_KEY));
				messageData.setTitanPaymentID(rs.getString(Constants.TRADE_PAYMENT_ID_KEY)!=null?rs.getInt(Constants.TRADE_PAYMENT_ID_KEY):null);
				messageData.setCreatedByUserId(rs.getString(Constants.CREATED_BY_KEY)!=null?rs.getInt(Constants.CREATED_BY_KEY):null);
				messageData.setCreatedBySSOUserId(rs.getString(Constants.CREATED_BY_SSOUSER_ID_KEY));
				messageData.setCreatedOn(rs.getString(Constants.CREATED_ON_KEY));
				messageData.setUpdatedByUserId(rs.getString(Constants.UPDATED_BY_KEY)!=null?rs.getInt(Constants.UPDATED_BY_KEY):null);
				messageData.setUpdatedBySSOUserId(rs.getString(Constants.UPDATED_BY_SSOUSER_ID_KEY));
				messageData.setUpdatedOn(rs.getString(Constants.UPDATED_ON_KEY));
				messageData.setTransactionDate(rs.getString(Constants.TRANSACTION_DATE_KEY));
				messageData.setDescription(rs.getString(Constants.DESCRIPTION_KEY));
				messageData.setComplianceStatusId(rs.getString(Constants.COMPLIANCE_STATUS_ID_KEY)!=null?rs.getInt(Constants.COMPLIANCE_STATUS_ID_KEY):null);
				messageData.setComplianceStatus(rs.getString(Constants.COMPLIANCE_STATUS_KEY));
				messageData.setComplianceDoneOn(rs.getString(Constants.COMPLIANCE_DONE_ON_KEY));
				messageData.setFraugsterStatusId(rs.getString(Constants.FRAUGSTER_STATUS_ID_KEY)!=null?rs.getInt(Constants.FRAUGSTER_STATUS_ID_KEY):null);
				messageData.setFraugsterStatus(rs.getString(Constants.FRAUGSTER_STATUS_KEY));
				messageData.setSanctionStatusId(rs.getString(Constants.SANCTION_STATUS_ID_KEY)!=null?rs.getInt(Constants.SANCTION_STATUS_ID_KEY):null);
				messageData.setSanctionStatus(rs.getString(Constants.SANCTION_STATUS_KEY));
				messageData.setBlacklistStatusId(rs.getString(Constants.BLACKLIST_STATUS_ID_KEY)!=null?rs.getInt(Constants.BLACKLIST_STATUS_ID_KEY):null);
				messageData.setBlacklistStatus(rs.getString(Constants.BLACKLIST_STATUS_KEY));
				messageData.setCustomCheckStatusId(rs.getString(Constants.CUSTOM_CHECK_STATUS_ID_KEY)!=null?rs.getInt(Constants.CUSTOM_CHECK_STATUS_ID_KEY):null);
				messageData.setCustomCheckStatus(rs.getString(Constants.CUSTOM_CHECK_STATUS_KEY));
				messageData.setIsOnQueue(rs.getString(Constants.IS_ON_QUEUE_KEY)!=null?rs.getInt(Constants.IS_ON_QUEUE_KEY):null);
				messageData.setSTPFlag(rs.getString(Constants.STP_FLAG_KEY)!=null?rs.getInt(Constants.STP_FLAG_KEY):null);
				messageData.setLegacyTradePaymentId(rs.getString(Constants.LEGACY_TRADE_PAYMENT_ID_KEY)!=null?rs.getInt(Constants.LEGACY_TRADE_PAYMENT_ID_KEY):null);
				messageData.setLegacyTradeContractNumber(rs.getString(Constants.LEGACY_TRADE_CONTRACT_NUMBER_KEY));
				messageData.setLegalEntityID(rs.getString(Constants.LEGAL_ENTITY_ID_KEY)!=null?rs.getInt(Constants.LEGAL_ENTITY_ID_KEY):null);
				messageData.setLegalEntityCode(rs.getString(Constants.LEGAL_ENTITY_CODE_KEY));
				messageData.setLegalEntityName(rs.getString(Constants.LEGAL_ENTITY_NAME_KEY));
				messageData.setInitialStatus(rs.getString(Constants.INITIAL_STATUS_KEY));
				messageData.setIntuitionClientRiskLevel(rs.getString(Constants.INTUITION_CLIENT_RISK_LEVEL_KEY));
				messageData.setIntuitionCheckStatusId(rs.getString(Constants.INTUITION_CHECK_STATUS_ID_KEY)!=null?rs.getInt(Constants.INTUITION_CHECK_STATUS_ID_KEY):null);
				messageData.setIntuitionCheckStatus(rs.getString(Constants.INTUITION_CHECK_STATUS_KEY));
				messageData.setPaymentReferenceStatusId(rs.getString(Constants.PAYMENT_REF_STATUS_ID_KEY)!=null?rs.getInt(Constants.PAYMENT_REF_STATUS_ID_KEY):null);
				messageData.setPaymentReferenceStatus(rs.getString(Constants.PAYMENT_REF_STATUS_KEY));
				messageData.setPaymentClearedOn(rs.getString(Constants.PAYMENT_CLEARED_ON_KEY));
				messageData.setActivityID(rs.getString(Constants.ACTIVITY_ID_KEY)!=null?rs.getInt(Constants.ACTIVITY_ID_KEY):null);
				messageData.setActivityBy(rs.getString(Constants.ACTIVITY_BY_KEY)!=null?rs.getInt(Constants.ACTIVITY_BY_KEY):null);
				messageData.setActivityByUserId(rs.getString(Constants.ACTIVITY_BY_USER_ID_KEY));
				messageData.setActivityTimestamp(rs.getString(Constants.ACTIVITY_TIMESTAMP_KEY));
				messageData.setActivityComments(rs.getString(Constants.ACTIVITY_COMMENTS_KEY));
				messageData.setActivityCreatedBy(rs.getString(Constants.ACTIVITY_CREATED_BY_KEY)!=null?rs.getInt(Constants.ACTIVITY_CREATED_BY_KEY):null);
				messageData.setActivityCreatedByUserId(rs.getString(Constants.ACTIVITY_CREATED_BY_USER_ID_KEY));
				messageData.setActivityCreatedOn(rs.getString(Constants.ACTIVITY_CREATED_ON_KEY));
				messageData.setActivityType(rs.getString(Constants.ACTIVITY_TYPE_KEY)!=null?rs.getInt(Constants.ACTIVITY_TYPE_KEY):null);
				messageData.setActivityTypeModule(rs.getString(Constants.ACTIVITY_TYPE_MODULE_KEY));
				messageData.setActivityTypeValue(rs.getString(Constants.ACTIVITY_TYPE_VALUE_KEY));
				messageData.setActivityDetailsCreatedOn(rs.getString(Constants.ACTIVITY_DETAILS_CREATED_ON_KEY));
				messageData.setActivityLog(rs.getString(Constants.ACTIVITY_LOG_KEY));
				messageData.setDeleted((rs.getInt(Constants.DELETED_KEY)==1)?true:false);
				
				cdTransaction.setMessageData(messageData);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}

		LOG.info(method + "cdTransaction : "+cdTransaction);
		return cdTransaction;
	}
	
	public Integer getTradePaymentIDFromFundsInID(Integer fundsInID)  throws ComplianceException {

		String method = "KafkaDataLakeTxDBServiceImpl - getTradePaymentIDFromFundsInID : ";
		String query = (DBQueryConstant.GET_TRADE_PAYMENT_ID_FROM_FUNDS_IN_ID.getQuery());
		LOG.info(method + "FundsIn ID : "+fundsInID+ "query : "+query);
		ResultSet rs = null;
		
		try(Connection conn = getConnection(Boolean.FALSE);
					PreparedStatement preparedStatment = conn.prepareStatement(query);) 
		{
			preparedStatment.setInt(1, fundsInID);
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				return rs.getString(Constants.TRADE_PAYMENT_ID_KEY)!=null?rs.getInt(Constants.TRADE_PAYMENT_ID_KEY):null;
			}
			return null;
		}catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}finally {
			closeResultset(rs);
		}
	}
	
	public Integer getTradePaymentIDFromFundsOutID(Integer fundsOutID)  throws ComplianceException {

		String method = "KafkaDataLakeTxDBServiceImpl - getTradePaymentIDFromFundsOutID : ";
		String query = (DBQueryConstant.GET_TRADE_PAYMENT_ID_FROM_FUNDS_OUT_ID.getQuery());
		LOG.info(method + "FundsOut ID : "+fundsOutID+ "query : "+query);
		ResultSet rs = null;
		
		try(Connection conn = getConnection(Boolean.FALSE);
				PreparedStatement preparedStatment = conn.prepareStatement(query); )
		{
			preparedStatment.setInt(1, fundsOutID);
			rs = preparedStatment.executeQuery();

			while (rs.next()) {
				return rs.getString(Constants.TRADE_PAYMENT_ID_KEY)!=null?rs.getInt(Constants.TRADE_PAYMENT_ID_KEY):null;
			}
			return null;

		}catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		}finally {
			closeResultset(rs);
		}
	}
}