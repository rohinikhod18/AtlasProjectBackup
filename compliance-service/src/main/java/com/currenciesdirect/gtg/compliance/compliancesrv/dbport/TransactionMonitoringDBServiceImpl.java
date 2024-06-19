package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentsRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionPaymentStatusUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IntuitionHistoricPaymentRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentBoardCastResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class TransactionMonitoringDBServiceImpl extends AbstractDao {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringDBServiceImpl.class);

	/** The funds in DB service impl. */
	@Autowired
	protected FundsInDBServiceImpl fundsInDBServiceImpl;

	/** The funds out DB service impl. */
	@Autowired
	protected FundsOutDBServiceImpl fundsOutDBServiceImpl;
	
	/** The activity DB service impl. */
	@Autowired
	ActivityDBServiceImpl activityDBServiceImpl;
	
	/**
	 * Update intuition payment status and risk level.
	 *
	 * @param message the message
	 * @return the message
	 */
	//AT-4749
	public Message<MessageContext> updateIntuitionPaymentStatusAndRiskLevel(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		IntuitionPaymentStatusUpdateRequest request = (IntuitionPaymentStatusUpdateRequest) messageExchange
				.getRequest();
		IntuitionPaymentStatusUpdateResponse response = (IntuitionPaymentStatusUpdateResponse) messageExchange
				.getResponse();
		Account account = (Account) request.getAdditionalAttribute("account");

		Connection connection = null;
		String intuitionStatus = "FAIL";

		try {
			connection = getConnection(Boolean.FALSE);

			//changes add for AT-4880
			if (request.getAction().equalsIgnoreCase("Clean") || request.getAction().equalsIgnoreCase("Clear"))
				intuitionStatus = "PASS";

			if (request.getTrxType().equalsIgnoreCase("FUNDSIN")) {
				FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) request
						.getAdditionalAttribute("fundsInRequest");
				String paymentStatus = (String) fundsInRequest.getAdditionalAttribute("status");

				fundsInDBServiceImpl.updatePaymentInIntuitionStatus(fundsInRequest.getFundsInId(), intuitionStatus,
						token, connection);
				fundsInDBServiceImpl.updateAccountIntuitionRiskLevel(account.getId(), request.getClientRiskLevel(),
						token, connection);

				if (response.getPaymentStatus() != null
						&& !response.getPaymentStatus().equalsIgnoreCase(paymentStatus)) {
					fundsInDBServiceImpl.updatePaymentInStatus(response.getPaymentStatus(),
							fundsInRequest.getFundsInId(), connection);
					
					String statusJson = getPaymentStatusBroadCastQueueResponse(response,
							fundsInRequest.getTrade().getContractNumber(), fundsInRequest.getPaymentFundsInId(),
							fundsInRequest.getOrgCode());
					
					updatePaymentStatusBroadCastQueue(connection, statusJson, fundsInRequest.getOrgCode(),
							fundsInRequest.getAccId(), fundsInRequest.getContactId(), fundsInRequest.getFundsInId(), null);
				}
				
				activityDBServiceImpl.insertIntuitionPaymentInStatusUpdateActivityLog(connection, fundsInRequest.getFundsInId());

			} else {
				FundsOutRequest fundsOutRequest = (FundsOutRequest) request.getAdditionalAttribute("fundsOutRequest");
				String paymentStatus = (String) fundsOutRequest.getAdditionalAttribute("status");

				fundsOutDBServiceImpl.updatePaymentOutIntuitionStatus(fundsOutRequest.getFundsOutId(), intuitionStatus,
						token, connection);
				fundsOutDBServiceImpl.updateAccountIntuitionRiskLevel(account.getId(), request.getClientRiskLevel(),
						token, connection);

				if (response.getPaymentStatus() != null
						&& !response.getPaymentStatus().equalsIgnoreCase(paymentStatus)) {
					fundsOutDBServiceImpl.updatePaymentOutStatus(response.getPaymentStatus(),
							fundsOutRequest.getFundsOutId(), connection);
					
					String statusJson = getPaymentStatusBroadCastQueueResponse(response,
							fundsOutRequest.getTrade().getContractNumber(), fundsOutRequest.getPaymentFundsoutId(),
							fundsOutRequest.getOrgCode());
					
					updatePaymentStatusBroadCastQueue(connection, statusJson, fundsOutRequest.getOrgCode(),
							fundsOutRequest.getAccId(), fundsOutRequest.getContactId(), null, fundsOutRequest.getFundsOutId());
				}
				
				activityDBServiceImpl.insertIntuitionPaymentOutStatusUpdateActivityLog(connection, fundsOutRequest.getFundsOutId());
			}

		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error("Error while updating updateIntuitionPaymentStatusAndRiskLevel() ", e);
		} finally {
			try {
				closeConnection(connection);
			} catch (ComplianceException e) {
				message.getPayload().setFailed(true);
				LOG.error("Error while updating updateIntuitionPaymentStatusAndRiskLevel()", e);
			}
		}
		return message;
	}
	
	/**
	 * Gets the payment status broad cast queue response.
	 *
	 * @param response the response
	 * @param contractNumber the contract number
	 * @param transactionId the transaction id
	 * @param orgCode the org code
	 * @return the payment status broad cast queue response
	 */
	private String getPaymentStatusBroadCastQueueResponse(IntuitionPaymentStatusUpdateResponse response,
			String contractNumber, Integer transactionId, String orgCode) {

		TransactionMonitoringPaymentBoardCastResponse boardCastResponse = new TransactionMonitoringPaymentBoardCastResponse();
		boardCastResponse.setOsrID(UUID.randomUUID().toString());
		boardCastResponse.setOrgCode(orgCode);
		boardCastResponse.setTradeContractNumber(contractNumber);
		boardCastResponse.setTradePaymentID(transactionId);
		boardCastResponse.setStatus(response.getPaymentStatus());
		boardCastResponse.setResponseCode(response.getResponseCode());
		boardCastResponse.setResponseDescription(response.getResponseDescription());
		return JsonConverterUtil.convertToJsonWithNull(boardCastResponse);

	}
	
	/**
	 * Update payment status broad cast queue.
	 *
	 * @param connection the connection
	 * @param statusJson the status json
	 * @param orgCode the org code
	 * @param accountId the account id
	 * @param entityType the entity type
	 * @param paymentInId the payment in id
	 * @param paymentOutId the payment out id
	 * @throws ComplianceException the compliance exception
	 */
	private void updatePaymentStatusBroadCastQueue(Connection connection, String statusJson, String orgCode, Integer accountId, Integer contactId, 
			Integer paymentInId, Integer paymentOutId) throws ComplianceException {
		PreparedStatement preparedStatement = null;
		Integer entityType;

		try {
			if(paymentInId != null && paymentInId != 0)
				entityType = 4; //For PaymentIn
			else
				entityType = 5; //For PaymentOut
			
			preparedStatement = connection.prepareStatement(DBQueryConstant.SAVE_INTO_BROADCAST_QUEUE.getQuery());
			preparedStatement.setString(1, orgCode);
			preparedStatement.setInt(2, entityType);
			preparedStatement.setInt(3, accountId);
			preparedStatement.setInt(4, contactId);
			if (paymentInId != null && paymentInId != 0) {
				preparedStatement.setInt(5, paymentInId);
			} else {
				preparedStatement.setNull(5, Types.INTEGER);
			}
			if (paymentOutId != null && paymentOutId != 0) {
				preparedStatement.setInt(6, paymentOutId);
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
		}
	}
	
	/**
	 * Update account TM flag and dormant flag.
	 *
	 * @param signUpMessage the sign up message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	//AT-5256
	public Message<MessageContext> updateAccountTMFlagAndDormantFlag(Message<MessageContext> signUpMessage) throws ComplianceException {
		PreparedStatement signUpStmt = null;
		Connection connection = null;
		String accTMFlag = "DormantFlag = 1,";
		Integer updatedTMFlag = 0;
		try {
			UserProfile token = (UserProfile) signUpMessage.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			RegistrationResponse signUpResponse = (RegistrationResponse) signUpMessage.getPayload()
					.getGatewayMessageExchange().getResponse();
			OperationEnum registrationOperation = signUpMessage.getPayload().getGatewayMessageExchange().getOperation();
			
			if (registrationOperation==OperationEnum.NEW_REGISTRATION && 
					signUpResponse.getAccount().getTransactionMonitoringStatus().equalsIgnoreCase("PASS")) {
					accTMFlag += "AccountTMFlag = 4,";
					updatedTMFlag = 4;
				}				

				connection = getConnection(Boolean.FALSE);
				signUpStmt = connection
						.prepareStatement(DBQueryConstant.UPDATE_ACC_TM_FLAG.getQuery().replace("#", accTMFlag));
				signUpStmt.setString(1, signUpResponse.getAccount().getIntuitionRiskLevel());
				signUpStmt.setInt(2, token.getUserID());	
				signUpStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				signUpStmt.setInt(4, signUpResponse.getAccount().getId());
				signUpStmt.executeUpdate();
				
				signUpResponse.addAttribute("UpdatedTMFlag", updatedTMFlag);
				
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(signUpStmt);
			closeConnection(connection);
		}
		return signUpMessage;
	}
	
	/**
	 * Update account TM flag for intuition historic payment.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	//AT-5264
	public Message<MessageContext> updateAccountTMFlagForIntuitionHistoricPayment(Message<MessageContext> message)
			throws ComplianceException {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			IntuitionHistoricPaymentsRequest intuitionPaymentRequest = (IntuitionHistoricPaymentsRequest) messageExchange
					.getRequest();
			
			if ((boolean) intuitionPaymentRequest.getAdditionalAttribute("IntuitionHistoricPaymentInLocalSave")) {
				for (IntuitionHistoricPaymentRequest request : intuitionPaymentRequest
						.getIntuitionHistoricPaymentInRequest()) {
					updateAccountTMFlagForHistoricPayments(request.getAccountId());
				}
			}
			
			//AT-5304
			if ((boolean) intuitionPaymentRequest.getAdditionalAttribute("IntuitionHistoricPaymentOutLocalSave")) {
				for (IntuitionHistoricPaymentRequest request : intuitionPaymentRequest
						.getIntuitionHistoricPaymentOutRequest()) {
					updateAccountTMFlagForHistoricPayments(request.getAccountId());
				}
			}
		} catch (Exception e) {
			LOG.error("Error in TransactionMonitoringDBServiceImpl updateAccountTMFlagForIntuitionHistoricPayment(): ",
					e);
			message.getPayload().setFailed(true);
		}

		return message;
	}
	

	/**
	 * Update account TM flag for historic payments.
	 *
	 * @param accountId the account id
	 * @throws ComplianceException the compliance exception
	 */
	//AT-5264
	private void updateAccountTMFlagForHistoricPayments(Integer accountId) throws ComplianceException {
		PreparedStatement statement = null;
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);
			statement = connection
					.prepareStatement(DBQueryConstant.UPDATE_ACCOUNT_TM_FLAG_FOR_HISTORIC_PAYMENTS.getQuery());
			statement.setInt(1, 1);
			statement.setInt(2, 1);
			statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			statement.setInt(4, accountId);
			statement.executeUpdate();

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(statement);
			closeConnection(connection);
		}

	}

}
