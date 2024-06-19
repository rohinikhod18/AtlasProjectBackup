package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.ReprocessFailedDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.enums.ReprocessStatusEnum;
import com.currenciesdirect.gtg.compliance.commons.enums.TransactionTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.BulkRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.BulkRegRecheckResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;

/**
 * The Class RecheckDBServiceImpl.
 */
public class RecheckDBServiceImpl extends AbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(RecheckDBServiceImpl.class);

	/**
	 * Update failed funds out status to done.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateFailedFundsOutStatusToDone(Message<MessageContext> message)
			throws ComplianceException {
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		FundsOutRequest fundsOutRequest = (FundsOutRequest) exchange.getRequest();

		MessageExchange fundsOutBulkExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.FUNDSOUT_BULK_RECHECK_SERVICE);
		BulkRecheckResponse serviceResponse = (BulkRecheckResponse) fundsOutBulkExchange.getResponse();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.UPDATE_FAILED_RECHECK.getQuery());
			if ("PASS".equals(serviceResponse.getStatus())) {
				preparedStatement.setInt(1, ReprocessStatusEnum.DONE.getReprocessStatusAsInteger());
			} else {
				preparedStatement.setInt(1, ReprocessStatusEnum.FAILED.getReprocessStatusAsInteger());
			}
			preparedStatement.setInt(2, fundsOutRequest.getFundsOutId());
			preparedStatement.setInt(3, TransactionTypeEnum.getTransactionTypeAsInteger("PAYMENTOUT"));
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error("Error while updating bulk recheck fundsout status:", e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return message;

	}

	/**
	 * Update failed funds out status to in progress.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateFailedRecheckStatusToInProgress(Message<MessageContext> message)
			throws ComplianceException {
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		ReprocessFailedDetails reprocessRequest = (ReprocessFailedDetails) exchange.getRequest();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.UPDATE_FAILED_RECHECK.getQuery());
			preparedStatement.setInt(1, ReprocessStatusEnum.INPROGRESS.getReprocessStatusAsInteger());
			preparedStatement.setInt(2, reprocessRequest.getTransId());
			preparedStatement.setInt(3,
					TransactionTypeEnum.getTransactionTypeAsInteger(reprocessRequest.getTransType()));
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error("Error while updating bulk recheck status:", e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return message;

	}

	/**
	 * Update failed funds in status to done.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateFailedFundsInStatusToDone(Message<MessageContext> message)
			throws ComplianceException {
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		FundsInCreateRequest fundsInRequest = (FundsInCreateRequest) exchange.getRequest();

		MessageExchange fundsInBulkExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.FUNDSIN_BULK_RECHECK_SERVICE);
		BulkRecheckResponse serviceResponse = (BulkRecheckResponse) fundsInBulkExchange.getResponse();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.UPDATE_FAILED_RECHECK.getQuery());
			if ("PASS".equals(serviceResponse.getStatus())) {
				preparedStatement.setInt(1, ReprocessStatusEnum.DONE.getReprocessStatusAsInteger());
			} else {
				preparedStatement.setInt(1, ReprocessStatusEnum.FAILED.getReprocessStatusAsInteger());
			}
			preparedStatement.setInt(2, fundsInRequest.getFundsInId());
			preparedStatement.setInt(3, TransactionTypeEnum.getTransactionTypeAsInteger("PAYMENTIN"));
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error("Error while updating bulk recheck fundsin status:", e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return message;

	}

	/**
	 * Update failed registration status to done.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateFailedRegistrationStatusToDone(Message<MessageContext> message)
			throws ComplianceException {
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest regRequest = (RegistrationServiceRequest) exchange.getRequest();
		Integer accountId = (Integer) regRequest.getAdditionalAttribute("accountId");
		Integer contactId = (Integer) regRequest.getAdditionalAttribute("contactId");

		MessageExchange regBulkExchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.REGISTRATION_BULK_RECHECK_SERVICE);
		BulkRegRecheckResponse serviceResponse = (BulkRegRecheckResponse) regBulkExchange.getResponse();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.UPDATE_FAILED_RECHECK.getQuery());
			if ("PASS".equals(serviceResponse.getStatus())) {
				preparedStatement.setInt(1, ReprocessStatusEnum.DONE.getReprocessStatusAsInteger());
			} else {
				preparedStatement.setInt(1, ReprocessStatusEnum.FAILED.getReprocessStatusAsInteger());
			}
			if (null != accountId) {
				preparedStatement.setInt(2, accountId);
				preparedStatement.setInt(3, TransactionTypeEnum.ACCOUNT.getTransactionTypeAsInteger());
			} else {
				preparedStatement.setInt(2, contactId);
				preparedStatement.setInt(3, TransactionTypeEnum.CONTACT.getTransactionTypeAsInteger());
			}
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			message.getPayload().setFailed(true);
			LOG.error("Error while updating bulk recheck registration status:", e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return message;

	}

}
