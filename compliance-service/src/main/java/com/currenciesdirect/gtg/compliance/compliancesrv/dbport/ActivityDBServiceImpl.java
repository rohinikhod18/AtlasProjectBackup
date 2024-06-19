package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

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

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterPaymentsOutResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsin.request.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.statusupdate.StatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringMQServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentInResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentOutResponse;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OnfidoStatusEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsin.response.FundsInCreateResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.fundsout.response.FundsOutResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.profile.delete.contact.DeleteContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.TransactionMonitoringMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchangeWrapper;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class ActivityDBServiceImpl.
 */
/**
 * @author bnt
 *
 */
@SuppressWarnings("squid:S2095")
public class ActivityDBServiceImpl extends AbstractDao {

	/** The Constant IS_BLACKLIST_ELIGIBLE. */
	private static final String IS_BLACKLIST_ELIGIBLE = "isBlacklistEligible";
	
	/** The Constant IS_COUNTRY_CHECK_ELIGIBLE. */
	private static final String IS_COUNTRY_CHECK_ELIGIBLE = "isCountryCheckEligible";
	
	/** The Constant IS_FRAUGSTER_ELIGIBLE. */
	private static final String IS_FRAUGSTER_ELIGIBLE = "isFraugsterEligible";
	
	/** The Constant IS_SANCTION_ELIGIBLE. */
	private static final String IS_SANCTION_ELIGIBLE = "isSanctionEligible";
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(ActivityDBServiceImpl.class);

	/**
	 * Business -- 1)Performing sanction update from UI for PFX/CFX 2)Update
	 * Request can come from Registration/Profile, FundsOut ,FundsIn
	 * 
	 * Implementation--- 1)Insert Entry in ProfileActivityLog Table For
	 * Registration 2)PaymentOutActivityLog For FundsOut and
	 * PaymentInActivityLog for FundsIn.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createSanctionUpdateActivity(Message<MessageContext> message)
			throws ComplianceException {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			SanctionUpdateRequest request = messageExchange.getRequest(SanctionUpdateRequest.class);
			if ("profile".equalsIgnoreCase(request.getResourceType())) {
				createProfileActivity(message);
			} else if ("fundsout".equalsIgnoreCase(request.getResourceType())) {
				createPaymentOutActivity(message);
			} else if ("fundsin".equalsIgnoreCase(request.getResourceType())) {
				createPaymentInActivity(message);
			}
		} catch (Exception e) {
			LOGGER.error("Error in ActivityDBServiceImpl createSanctionUpdateActivity()", e);
			message.getPayload().setFailed(true);

		}
		return message;
	}

	/**
	 * Creates the profile activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createProfileActivity(Message<MessageContext> message) throws ComplianceException {
		try {
			List<ProfileActivityLogDto> profileActivites = new ArrayList<>();
			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				ServiceMessageResponse response = exchange.getMessageExchange().getResponse();
				@SuppressWarnings("unchecked")
				List<ProfileActivityLogDto> activites = (List<ProfileActivityLogDto>) response.getAdditionalAttribute("profileActivities");
				if (activites != null && !activites.isEmpty()) {
					profileActivites.addAll(activites);
				}
			}
			insertProfileActivityLogs(profileActivites);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the payment out activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createPaymentOutActivity(Message<MessageContext> message)
			throws ComplianceException {
		try {
			List<PaymentOutActivityLogDto> profileActivites = new ArrayList<>();
			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				ServiceMessageResponse response = exchange.getMessageExchange().getResponse();
				@SuppressWarnings("unchecked")
				List<PaymentOutActivityLogDto> activites = (List<PaymentOutActivityLogDto>) response
						.getAdditionalAttribute("paymentOutActivities");
				if (activites != null && !activites.isEmpty()) {
					profileActivites.addAll(activites);
				}
			}
			insertPaymentOutActivityLogs(profileActivites);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the payment in activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createPaymentInActivity(Message<MessageContext> message) throws ComplianceException {

		try {
			List<PaymentInActivityLogDto> profileActivites = new ArrayList<>();
			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				ServiceMessageResponse response = exchange.getMessageExchange().getResponse();
				@SuppressWarnings("unchecked")
				List<PaymentInActivityLogDto> activites = (List<PaymentInActivityLogDto>) response
						.getAdditionalAttribute("paymentInActivities");
				if (activites != null && !activites.isEmpty()) {
					profileActivites.addAll(activites);
				}
			}
			insertPaymentInActivityLogs(profileActivites);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Insert profile activity logs.
	 *
	 * @param profileActivityLogs
	 *            the profile activity logs
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertProfileActivityLogs(List<ProfileActivityLogDto> profileActivityLogs) throws ComplianceException {
		PreparedStatement profileActivityStmt = null;
		Connection profileConnection = null;
		ResultSet rs = null;
		try {
			profileConnection = getConnection(Boolean.FALSE);
			profileActivityStmt = profileConnection.prepareStatement(
					DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG.getQuery(), Statement.RETURN_GENERATED_KEYS);
			for (ProfileActivityLogDto oneLog : profileActivityLogs) {

				profileActivityStmt.setTimestamp(1, oneLog.getTimeStatmp());
				profileActivityStmt.setNString(2, oneLog.getActivityBy());
				profileActivityStmt.setNString(3, oneLog.getOrgCode());
				profileActivityStmt.setInt(4, oneLog.getAccountId());
				if (null == oneLog.getContactId()) {
					profileActivityStmt.setNull(5, java.sql.Types.INTEGER);
				} else {
					profileActivityStmt.setInt(5, oneLog.getContactId());
				}
				profileActivityStmt.setNString(6, oneLog.getComment());
				profileActivityStmt.setNString(7, oneLog.getCreatedBy());
				profileActivityStmt.setTimestamp(8, oneLog.getCreatedOn());
				profileActivityStmt.setNString(9, oneLog.getUpdatedBy());
				profileActivityStmt.setTimestamp(10, oneLog.getUpdatedOn());
				// AT-894 WorkEfficiencyReport
				// Add ResouceType and ResourceID
				if (null == oneLog.getContactId()) {
					profileActivityStmt.setInt(11, 4); // Account
					profileActivityStmt.setInt(12, oneLog.getAccountId());
				} else {
					profileActivityStmt.setInt(11, 3); // Contact
					profileActivityStmt.setInt(12, oneLog.getContactId());
				}
				int updateCount = profileActivityStmt.executeUpdate();
				if (updateCount == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
				rs = profileActivityStmt.getGeneratedKeys();
				if (rs.next()) {
					oneLog.setId(rs.getInt(1));
					oneLog.getActivityLogDetailDto().setActivityId(rs.getInt(1));
				}
			}
			insertProfileActivityLogDetail(profileActivityLogs, profileConnection);
		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(profileActivityStmt);
			closeConnection(profileConnection);
		}
	}

	/**
	 * Insert profile activity log detail.
	 *
	 * @param profileActivityLogs
	 *            the profile activity logs
	 * @param connection
	 *            the connection
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertProfileActivityLogDetail(List<ProfileActivityLogDto> profileActivityLogs, Connection connection)
			throws ComplianceException {
		PreparedStatement profileActivityDtlStmt = null;
		try {
			profileActivityDtlStmt = connection
					.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL.getQuery());
			for (ProfileActivityLogDto activityLog : profileActivityLogs) {
				ActivityLogDetailDto logDetails = activityLog.getActivityLogDetailDto();
				profileActivityDtlStmt.setInt(1, logDetails.getActivityId());
				profileActivityDtlStmt.setString(2, logDetails.getActivityType().getModule());
				profileActivityDtlStmt.setString(3, logDetails.getActivityType().getType());
				profileActivityDtlStmt.setNString(4, logDetails.getLog());
				profileActivityDtlStmt.setNString(5, logDetails.getCreatedBy());
				profileActivityDtlStmt.setTimestamp(6, logDetails.getCreatedOn());
				profileActivityDtlStmt.setNString(7, logDetails.getUpdatedBy());
				profileActivityDtlStmt.setTimestamp(8, logDetails.getUpdatedOn());
				profileActivityDtlStmt.addBatch();
			}
			int[] count = profileActivityDtlStmt.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
			}

		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closePrepareStatement(profileActivityDtlStmt);
		}
	}

	/**
	 * Insert payment out activity logs.
	 *
	 * @param poActivityLogs
	 *            the po activity logs
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentOutActivityLogs(List<PaymentOutActivityLogDto> poActivityLogs)
			throws ComplianceException {
		PreparedStatement poActivityStmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.FALSE);
			poActivityStmt = connection.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (PaymentOutActivityLogDto poActivity : poActivityLogs) {
				poActivityStmt.setInt(1, poActivity.getPaymentOutId());
				poActivityStmt.setNString(2, poActivity.getActivityBy());
				poActivityStmt.setTimestamp(3, poActivity.getTimeStatmp());
				poActivityStmt.setNString(4, poActivity.getComment());
				poActivityStmt.setNString(5, poActivity.getCreatedBy());
				poActivityStmt.setTimestamp(6, poActivity.getCreatedOn());
				poActivityStmt.setInt(7, poActivity.getPaymentOutId());
				int updateCount = poActivityStmt.executeUpdate();
				if (updateCount == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
				rs = poActivityStmt.getGeneratedKeys();
				if (rs.next()) {
					poActivity.setId(rs.getInt(1));
					poActivity.getActivityLogDetailDto().setActivityId(rs.getInt(1));
				}
			}
			insertPaymentOutActivityLogDetail(poActivityLogs, connection);
		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(poActivityStmt);
			closeConnection(connection);
		}
	}

	/**
	 * Insert payment out activity log detail.
	 *
	 * @param poActivityLogs
	 *            the po activity logs
	 * @param connection
	 *            the connection
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentOutActivityLogDetail(List<PaymentOutActivityLogDto> poActivityLogs, Connection connection)
			throws ComplianceException {
		PreparedStatement poActivityDtlStmt = null;
		try {
			poActivityDtlStmt = connection
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL.getQuery());
			for (PaymentOutActivityLogDto activityLog : poActivityLogs) {
				PaymentOutActivityLogDetailDto poActivityDetails = activityLog.getActivityLogDetailDto();
				poActivityDtlStmt.setInt(1, poActivityDetails.getActivityId());
				poActivityDtlStmt.setString(2, poActivityDetails.getActivityType().getModule());
				poActivityDtlStmt.setString(3, poActivityDetails.getActivityType().getType());
				poActivityDtlStmt.setNString(4, poActivityDetails.getLog());
				poActivityDtlStmt.setNString(5, poActivityDetails.getCreatedBy());
				poActivityDtlStmt.setTimestamp(6, poActivityDetails.getCreatedOn());
				poActivityDtlStmt.addBatch();
			}
			int[] count = poActivityDtlStmt.executeBatch();
			for (int poActivityCount : count) {
				if (poActivityCount == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
			}

		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closePrepareStatement(poActivityDtlStmt);
		}
	}

	/**
	 * Insert payment in activity logs.
	 *
	 * @param piActivityLogs
	 *            the pi activity logs
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentInActivityLogs(List<PaymentInActivityLogDto> piActivityLogs) throws ComplianceException {
		PreparedStatement piActivtyStmt = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.FALSE);
			piActivtyStmt = connection.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (PaymentInActivityLogDto activityLog : piActivityLogs) {
				piActivtyStmt.setInt(1, activityLog.getPaymentInId());
				piActivtyStmt.setNString(2, activityLog.getActivityBy());
				piActivtyStmt.setTimestamp(3, activityLog.getTimeStatmp());
				piActivtyStmt.setNString(4, activityLog.getComment());
				piActivtyStmt.setNString(5, activityLog.getCreatedBy());
				piActivtyStmt.setTimestamp(6, activityLog.getCreatedOn());
				piActivtyStmt.setInt(7, activityLog.getPaymentInId());
				int updateCount = piActivtyStmt.executeUpdate();
				if (updateCount == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
				rs = piActivtyStmt.getGeneratedKeys();
				if (rs.next()) {
					activityLog.setId(rs.getInt(1));
					activityLog.getActivityLogDetailDto().setActivityId(rs.getInt(1));
				}
			}
			insertPaymentInActivityLogDetail(piActivityLogs, connection);
		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(piActivtyStmt);
			closeConnection(connection);
		}
	}

	/**
	 * Insert payment in activity log detail.
	 *
	 * @param activityLogDtos
	 *            the activity log dtos
	 * @param connection
	 *            the connection
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentInActivityLogDetail(List<PaymentInActivityLogDto> activityLogDtos, Connection connection)
			throws ComplianceException {
		PreparedStatement piActivityDtlStmt = null;
		try {
			piActivityDtlStmt = connection
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL.getQuery());
			for (PaymentInActivityLogDto activityLog : activityLogDtos) {
				PaymentInActivityLogDetailDto piActivityDetail = activityLog.getActivityLogDetailDto();
				piActivityDtlStmt.setInt(1, piActivityDetail.getActivityId());
				piActivityDtlStmt.setString(2, piActivityDetail.getActivityType().getModule());
				piActivityDtlStmt.setString(3, piActivityDetail.getActivityType().getType());
				piActivityDtlStmt.setNString(4, piActivityDetail.getLog());
				piActivityDtlStmt.setNString(5, piActivityDetail.getCreatedBy());
				piActivityDtlStmt.setTimestamp(6, piActivityDetail.getCreatedOn());
				piActivityDtlStmt.addBatch();
			}
			int[] count = piActivityDtlStmt.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
			}

		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closePrepareStatement(piActivityDtlStmt);
		}
	}

	/**
	 * Creates the payment IN STP activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createSTPPaymentInActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fRequest = (FundsInCreateRequest) exchange.getRequest();
			FundsInCreateResponse fundsInCreateResponse = (FundsInCreateResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			conn = getConnection(Boolean.FALSE);
			insertInPaymetInActivityLogSTP(conn, fundsInCreateResponse, fRequest);

		} catch (Exception e) {
			LOGGER.error("Exception in createSTPPaymentInActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert in paymet in activity log STP.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 * @param fRequest
	 *            the f request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInPaymetInActivityLogSTP(Connection conn, FundsInCreateResponse fundsInCreateResponse,
			FundsInCreateRequest fRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setInt(1, fRequest.getFundsInId());
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			activityId = getActivityId(preparedStatment, activityId);

			insertInPaymetInActivityLogDetailsSTP(conn, fundsInCreateResponse, activityId);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in paymet in activity log details STP.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsInCreateResponse
	 *            the funds in create response
	 * @param activityId
	 *            the activity id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInPaymetInActivityLogDetailsSTP(Connection conn, FundsInCreateResponse fundsInCreateResponse,
			Integer activityId) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PAYMENT_IN);
			preparedStatment.setString(3, Constants.PAYMENT_IN_TYPE);
			preparedStatment.setNString(4, fundsInCreateResponse.getResponseDescription());
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Creates the payment OUT STP activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createSTPPaymentOutActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			FundsOutRequest fRequest = (FundsOutRequest) exchange.getRequest();
			FundsOutResponse fundsOutResponse = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
					.getResponse();
			conn = getConnection(Boolean.FALSE);
			insertInPaymetOutActivityLogSTP(conn, fundsOutResponse, fRequest);

		} catch (Exception e) {
			LOGGER.error("Exception in createSTPPaymentOutActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert in paymet out activity log STP.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutResponse
	 *            the funds out response
	 * @param fRequest
	 *            the f request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInPaymetOutActivityLogSTP(Connection conn, FundsOutResponse fundsOutResponse,
			FundsOutRequest fRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setInt(1, fRequest.getFundsOutId());
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			activityId = getActivityId(preparedStatment, activityId);

			insertInPaymetOutActivityLogDetailsSTP(conn, fundsOutResponse, activityId);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in paymet out activity log details STP.
	 *
	 * @param conn
	 *            the conn
	 * @param fundsOutResponse
	 *            the funds out response
	 * @param activityId
	 *            the activity id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInPaymetOutActivityLogDetailsSTP(Connection conn, FundsOutResponse fundsOutResponse,
			Integer activityId) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PAYMENT_OUT);
			preparedStatment.setString(3, Constants.PAYMENT_OUT_TYPE);
			preparedStatment.setNString(4, fundsOutResponse.getResponseDescription());
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Creates the profile STP activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createSTPProfileActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest fRequest = (RegistrationServiceRequest) exchange.getRequest();
			RegistrationResponse registrationResponse = (RegistrationResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			conn = getConnection(Boolean.FALSE);
			String custType = fRequest.getAccount().getCustType();

			if (CustomerTypeEnum.CFX.toString().equalsIgnoreCase(custType)) {
				insertInCFXProfileActivityLogSTP(conn, registrationResponse, fRequest);
			} else {
				insertInPFXProfileActivityLogSTP(conn, registrationResponse, fRequest);
			}

		} catch (Exception e) {
			LOGGER.error("Exception in createSTPProfileActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert in profile activity log STP PFX.
	 *
	 * @param conn
	 *            the conn
	 * @param registrationResponse
	 *            the registration response
	 * @param fRequest
	 *            the f request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertInPFXProfileActivityLogSTP(Connection conn, RegistrationResponse registrationResponse,
			RegistrationServiceRequest fRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		List<ComplianceContact> contactList = registrationResponse.getAccount().getContacts();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (ComplianceContact contact : contactList) {
				preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(2, Constants.DEFAULT_USER);
				preparedStatment.setInt(3, fRequest.getOrgId());
				preparedStatment.setInt(4, fRequest.getAccount().getId());
				preparedStatment.setInt(5, contact.getId());
				preparedStatment.setInt(6, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(8, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

				activityId = getActivityId(preparedStatment, activityId);
				addBlacklistActivity(fRequest,contact);//AT-3492
				insertInPFXProfileActivityLogDetailsSTP(conn, contact, activityId);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in profile activity log details STP PFX.
	 *
	 * @param conn
	 *            the conn
	 * @param contact
	 *            the contact
	 * @param activityId
	 *            the activity id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInPFXProfileActivityLogDetailsSTP(Connection conn, ComplianceContact contact, Integer activityId)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PROFILE_MODULE);
			preparedStatment.setString(3, Constants.PROFILE_SIGNUP_TYPE);
			preparedStatment.setNString(4, contact.getResponseDescription());
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(7, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in profile activity log STP CFX.
	 *
	 * @param conn
	 *            the conn
	 * @param registrationResponse
	 *            the registration response
	 * @param fRequest
	 *            the f request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertInCFXProfileActivityLogSTP(Connection conn, RegistrationResponse registrationResponse,
			RegistrationServiceRequest fRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		List<ComplianceContact> contactList = registrationResponse.getAccount().getContacts();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setInt(3, fRequest.getOrgId());
			preparedStatment.setInt(4, fRequest.getAccount().getId());
			preparedStatment.setInt(5, contactList.get(0).getId());
			preparedStatment.setInt(6, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(preparedStatment, activityId);
			insertInCFXProfileActivityLogDetailsSTP(conn, contactList, activityId);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in profile activity log details STP CFX.
	 *
	 * @param conn            the conn
	 * @param contactList the contact list
	 * @param activityId            the activity id
	 * @throws ComplianceException             the compliance exception
	 */
	private void insertInCFXProfileActivityLogDetailsSTP(Connection conn, List<ComplianceContact> contactList,
			Integer activityId) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PROFILE_MODULE);
			preparedStatment.setString(3, Constants.PROFILE_SIGNUP_TYPE);
			preparedStatment.setNString(4, contactList.get(0).getResponseDescription());
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(7, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Creates the profile update STP activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createSTPProfileUpdateActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest fRequest = (RegistrationServiceRequest) exchange.getRequest();
			RegistrationResponse registrationResponse = (RegistrationResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			conn = getConnection(Boolean.FALSE);

			Boolean isAccountModified = (Boolean) fRequest.getAdditionalAttribute("isAccountModified");
			Boolean isConactModified = (Boolean) fRequest.getAdditionalAttribute("isContactModified");
			Boolean isCompanyModified = (Boolean) fRequest.getAdditionalAttribute("isCompanyModified");
			Boolean isConversionPredictionModified = (Boolean) fRequest
					.getAdditionalAttribute("isConversionPredictionModified");
			Boolean isCorporateComplianceModified = (Boolean) fRequest
					.getAdditionalAttribute("isCorporateComplianceModified");

			List<String> activityLog = (List<String>) fRequest.getAdditionalAttribute("activityLog");

			if (Boolean.TRUE.equals(isAccountModified) || Boolean.TRUE.equals(isCompanyModified) 
					|| Boolean.TRUE.equals(isConversionPredictionModified)
					|| Boolean.TRUE.equals(isCorporateComplianceModified)) {

				insertInProfileUpdateAccountActivityLogSTP(conn, activityLog, fRequest);
			}

			if (Boolean.TRUE.equals(isConactModified)) {

				insertInProfileUpdateActivityLogSTP(conn, registrationResponse, activityLog, fRequest);
				LOGGER.warn("\n -------Activity details in activity db service impl -------- \n  {}", activityLog);
			}

		} catch (Exception e) {
			LOGGER.error("Exception in createSTPProfileUpdateActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert in profile update activity log STP.
	 *
	 * @param conn
	 *            the conn
	 * @param registrationResponse
	 *            the registration response
	 * @param activityLog
	 *            the activity log
	 * @param fRequest
	 *            the f request
	 * @param oldContacts
	 *            the old contacts
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInProfileUpdateActivityLogSTP(Connection conn, RegistrationResponse registrationResponse,
			List<String> activityLog, RegistrationServiceRequest fRequest)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		List<ComplianceContact> contacts = registrationResponse.getAccount().getContacts();

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setInt(3, fRequest.getOrgId());
			preparedStatment.setInt(4, fRequest.getAccount().getId());
			preparedStatment.setInt(5, contacts.get(0).getId());
			preparedStatment.setInt(6, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(preparedStatment, activityId);

			insertInProfileUpdateActivityLogDetailsSTP(conn, activityId, activityLog);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Gets the activity id.
	 *
	 * @param preparedStatment
	 *            the prepared statment
	 * @param activityId
	 *            the activity id
	 * @return the activity id
	 * @throws SQLException
	 *             the SQL exception
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Integer getActivityId(PreparedStatement preparedStatment, Integer activityId)
			throws SQLException, ComplianceException {
		ResultSet rs;
		Integer updateCount = preparedStatment.executeUpdate();
		if (updateCount == 0) {
			throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
		}
		rs = preparedStatment.getGeneratedKeys();
		if (rs.next()) {
			activityId = rs.getInt(1);
		}
		return activityId;
	}

	/**
	 * Insert in profile update activity log details STP.
	 *
	 * @param conn
	 *            the conn
	 * @param activityId
	 *            the activity id
	 * @param activityLog
	 *            the activity log
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertInProfileUpdateActivityLogDetailsSTP(Connection conn, Integer activityId,
			List<String> activityLog) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			for (String log : activityLog) {
				preparedStatment.setInt(1, activityId);
				preparedStatment.setString(2, Constants.PROFILE_MODULE);
				preparedStatment.setString(3, Constants.PROFILE_UPDATE_TYPE);
				preparedStatment.setNString(4, log);
				preparedStatment.setInt(5, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

				int count = preparedStatment.executeUpdate();

				if (count == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in profile update account activity log STP.
	 *
	 * @param conn
	 *            the conn
	 * @param activityLog
	 *            the activity log
	 * @param fRequest
	 *            the f request
	 * @param oldContacts
	 *            the old contacts
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertInProfileUpdateAccountActivityLogSTP(Connection conn, List<String> activityLog,
			RegistrationServiceRequest fRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setInt(3, fRequest.getOrgId());
			preparedStatment.setInt(4, fRequest.getAccount().getId());
			preparedStatment.setNull(5, java.sql.Types.INTEGER);
			preparedStatment.setInt(6, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(preparedStatment, activityId);

			insertInProfileUpdateAccountActivityLogDetailsSTP(conn, activityId, activityLog);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in profile update account activity log details STP.
	 *
	 * @param conn
	 *            the conn
	 * @param activityId
	 *            the activity id
	 * @param activityLog
	 *            the activity log
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertInProfileUpdateAccountActivityLogDetailsSTP(Connection conn, Integer activityId,
			List<String> activityLog) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {

			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			for (String log : activityLog) {
				preparedStatment.setInt(1, activityId);
				preparedStatment.setString(2, Constants.PROFILE_MODULE);
				preparedStatment.setString(3, Constants.PROFILE_UPDATE_TYPE);
				preparedStatment.setNString(4, log);
				preparedStatment.setInt(5, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

				int count = preparedStatment.executeUpdate();

				if (count == 0) {
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Creates the service failed recheck payment out activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createServiceFailedRecheckPaymentOutActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			FundsOutRequest fRequest = (FundsOutRequest) exchange.getRequest();
			conn = getConnection(Boolean.FALSE);
			insertServiceFailedRecheckPaymetOutActivityLog(conn, fRequest, userProfile, message);

		} catch (Exception e) {
			LOGGER.error("Exception in createServiceFailedRecheckPaymentOutActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert service failed recheck paymet out activity log.
	 *
	 * @param conn            the conn
	 * @param fRequest            the f request
	 * @param userProfile the user profile
	 * @param message the message
	 * @throws ComplianceException             the compliance exception
	 */
	private void insertServiceFailedRecheckPaymetOutActivityLog(Connection conn, FundsOutRequest fRequest,
			UserProfile userProfile, Message<MessageContext> message) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setInt(1, fRequest.getFundsOutId());
			preparedStatment.setInt(2, userProfile.getUserID());
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			activityId = getActivityId(preparedStatment, activityId);

			insertServiceFailedRecheckPaymetOutActivityLogDetails(conn, activityId, message, fRequest, userProfile);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert service failed recheck paymet out activity log details.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @param message the message
	 * @param fRequest the f request
	 * @param userProfile the user profile
	 * @throws ComplianceException the compliance exception
	 */
	private void insertServiceFailedRecheckPaymetOutActivityLogDetails(Connection conn, Integer activityId,
			Message<MessageContext> message, FundsOutRequest fRequest, UserProfile userProfile)
			throws ComplianceException {

		FundsOutResponse fundsOutResponse = (FundsOutResponse) message.getPayload().getGatewayMessageExchange()
				.getResponse();
		insertInPaymetOutActivityLogDetailsSTP(conn, fundsOutResponse, activityId);

		if ((boolean) fRequest.getAdditionalAttribute("isCustomCheckEligible")) {
			CustomCheckResponse cResponse = (CustomCheckResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT, "CUSTOM",
					cResponse.getOverallStatus(), userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)) {
			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
			String blacklistStatus = internalServiceResponse.getContacts().get(0).getBlacklist().getStatus();
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName(), blacklistStatus, userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();
			String countryCheckStatus = internalServiceResponse.getContacts().get(0).getCountryCheck().getStatus();
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT, "COUNTRY",
					countryCheckStatus, userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_SANCTION_ELIGIBLE)) {
			SanctionResponse sanctionResponse = (SanctionResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse();
			SanctionContactResponse scResponse = sanctionResponse.getContactResponses().get(0);
			SanctionBankResponse sBankResponse = sanctionResponse.getBankResponses().get(0);
			SanctionBeneficiaryResponse sBeneResponse = sanctionResponse.getBeneficiaryResponses().get(0);
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.SANCTION_SERVICE.getShortName(), scResponse.getStatus(), userProfile);
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_BENEFICIARY,
					ServiceTypeEnum.SANCTION_SERVICE.getShortName(), sBeneResponse.getStatus(), userProfile);
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_BANK,
					ServiceTypeEnum.SANCTION_SERVICE.getShortName(), sBankResponse.getStatus(), userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE)) {
			FraugsterPaymentsOutResponse fResponse = (FraugsterPaymentsOutResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();
			insertBulkRecheckActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.FRAUGSTER_SERVICE.getShortName(), fResponse.getStatus(), userProfile);
		}

	}
	
	private String getServiceFailureChangeString(String entityType,	String serviceType, String serviceStatus) {
		return("For " + entityType + ", After repeat " + serviceType 
				+ " check status changed from SERVICE_FAILURE to " + serviceStatus);
	}

	/**
	 * Insert bulk recheck activity log detail.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @param entityType the entity type
	 * @param serviceType the service type
	 * @param serviceStatus the service status
	 * @param userProfile the user profile
	 * @throws ComplianceException the compliance exception
	 */
	private void insertBulkRecheckActivityLogDetail(Connection conn, Integer activityId, String entityType,
			String serviceType, String serviceStatus, UserProfile userProfile) throws ComplianceException {
		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PAYMENT_OUT);
			preparedStatment.setString(3, Constants.PAYMENT_OUT_TYPE);
			preparedStatment.setNString(4, getServiceFailureChangeString(entityType, serviceType, serviceStatus));
			preparedStatment.setInt(5, userProfile.getUserID());
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Creates the service failed recheck registration activity.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> createServiceFailedRecheckRegistrationActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest fRequest = (RegistrationServiceRequest) exchange.getRequest();
			UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			RegistrationResponse registrationResponse = (RegistrationResponse) message.getPayload()
					.getGatewayMessageExchange().getResponse();
			conn = getConnection(Boolean.FALSE);
			Integer accountId = (Integer) fRequest.getAdditionalAttribute("accountId");
			if (null != accountId && 0 != accountId) {
				insertInServiceFailureRepeatCheckProfileActivityLogSTPForAccount(conn, registrationResponse, fRequest,
						message, userProfile);
			} else {
				insertInServiceFailureRepeatCheckProfileActivityLogSTPForContact(conn, registrationResponse, fRequest,
						message, userProfile);
			}

		} catch (Exception e) {
			LOGGER.error("Exception in createServiceFailedRecheckRegistrationActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert in service failure repeat check profile activity log STP for contact.
	 *
	 * @param conn the conn
	 * @param registrationResponse the registration response
	 * @param fRequest the f request
	 * @param message the message
	 * @param userProfile the user profile
	 * @throws ComplianceException the compliance exception
	 */
	private void insertInServiceFailureRepeatCheckProfileActivityLogSTPForContact(Connection conn,
			RegistrationResponse registrationResponse, RegistrationServiceRequest fRequest,
			Message<MessageContext> message, UserProfile userProfile) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		List<ComplianceContact> contactList = registrationResponse.getAccount().getContacts();
		try {

			for (ComplianceContact contact : contactList) {
				Integer contactId = (Integer) fRequest.getAdditionalAttribute("contactId");
				if (null != contactId && contactId.equals(contact.getId())) {
					preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
							Statement.RETURN_GENERATED_KEYS);
					preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
					preparedStatment.setInt(2, userProfile.getUserID());
					preparedStatment.setInt(3, fRequest.getOrgId());
					preparedStatment.setInt(4, fRequest.getAccount().getId());
					preparedStatment.setInt(5, contact.getId());
					preparedStatment.setInt(6, userProfile.getUserID());
					preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
					preparedStatment.setInt(8, userProfile.getUserID());
					preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

					activityId = getActivityId(preparedStatment, activityId);
					insertServiceFailedRecheckRegActivityLogDetailsForContact(conn, activityId, message, fRequest,
							userProfile, contactList);
				}
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert in service failure repeat check profile activity log STP for account.
	 *
	 * @param conn the conn
	 * @param registrationResponse the registration response
	 * @param fRequest the f request
	 * @param message the message
	 * @param userProfile the user profile
	 * @throws ComplianceException the compliance exception
	 */
	private void insertInServiceFailureRepeatCheckProfileActivityLogSTPForAccount(Connection conn,
			RegistrationResponse registrationResponse, RegistrationServiceRequest fRequest,
			Message<MessageContext> message, UserProfile userProfile) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		ComplianceAccount account = registrationResponse.getAccount();
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, userProfile.getUserID());
			preparedStatment.setInt(3, fRequest.getOrgId());
			preparedStatment.setInt(4, fRequest.getAccount().getId());
			preparedStatment.setString(5, null);
			preparedStatment.setInt(6, userProfile.getUserID());
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, userProfile.getUserID());
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
			activityId = getActivityId(preparedStatment, activityId);
			insertServiceFailedRecheckRegActivityLogDetailsForAccount(conn, activityId, message, fRequest, userProfile,
					account);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert service failed recheck reg activity log details for account.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @param message the message
	 * @param regServiceRequest the reg service request
	 * @param userProfile the user profile
	 * @param account the account
	 * @throws ComplianceException the compliance exception
	 */
	private void insertServiceFailedRecheckRegActivityLogDetailsForAccount(Connection conn, Integer activityId,
			Message<MessageContext> message, RegistrationServiceRequest regServiceRequest, UserProfile userProfile,
			ComplianceAccount account) throws ComplianceException {
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
		Integer accountId = (Integer) regServiceRequest.getAdditionalAttribute("accountId");

		insertInCFXProfileActivityLogDetailsSTPforRepeatcheck(conn, account, activityId, accountId);

		EventServiceLog log = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE, EntityEnum.ACCOUNT.name(),
				accountId);
		MessageExchange sanctionExchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
		EventServiceLog accountSanctionLog = sanctionExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
				EntityEnum.ACCOUNT.name(), accountId);
		MessageExchange blackListexchange = message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
		EventServiceLog accountBlackListlog = blackListexchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
				EntityEnum.ACCOUNT.name(), accountId);

		if ((boolean) regServiceRequest.getAdditionalAttribute("isEidEligible")) {
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId, Constants.ENTITY_TYPE_ACCOUNT,
					ServiceTypeEnum.KYC_SERVICE.getShortName(), log.getStatus(), userProfile);
		}
		if ((boolean) regServiceRequest.getAdditionalAttribute(IS_SANCTION_ELIGIBLE)) {

			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId, Constants.ENTITY_TYPE_ACCOUNT,
					ServiceTypeEnum.SANCTION_SERVICE.getShortName(), accountSanctionLog.getStatus(), userProfile);
		}

		if ((boolean) regServiceRequest.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)) {
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId, Constants.ENTITY_TYPE_ACCOUNT,
					ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName(), accountBlackListlog.getStatus(), userProfile);
		}
	}

	/**
	 * Insert service failed recheck reg activity log details for contact.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @param message the message
	 * @param regServiceRequest the reg service request
	 * @param userProfile the user profile
	 * @param contactList the contact list
	 * @throws ComplianceException the compliance exception
	 */
	private void insertServiceFailedRecheckRegActivityLogDetailsForContact(Connection conn, Integer activityId,
			Message<MessageContext> message, RegistrationServiceRequest regServiceRequest, UserProfile userProfile,
			List<ComplianceContact> contactList) throws ComplianceException {
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
		Integer contactId = (Integer) regServiceRequest.getAdditionalAttribute("contactId");
		ComplianceContact contactToBeRechecked = new ComplianceContact();

		for (ComplianceContact contact : contactList) {
			if (null != contactId && contactId.equals(contact.getId())) {
				insertInPFXProfileActivityLogDetailsSTP(conn, contact, activityId);
				contactToBeRechecked = contact;
				break;
			}
		}
		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();

		if (CustomerTypeEnum.PFX.name().equals(regServiceRequest.getAccount().getCustType())) {
			setServiceFailureRepeatCheckProfileActivityLogDetailsSTP(regServiceRequest, userProfile, contactToBeRechecked, internalServiceResponse, conn, activityId, exchange);
		}

		if ((boolean) regServiceRequest.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE)) {
			FraugsterOnUpdateResponse fraugsterOnUpdateResponse = (FraugsterOnUpdateResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.FRAUGSTER_SERVICE.getShortName(), fraugsterOnUpdateResponse.getStatus(),
					userProfile);
		}

		if ((boolean) regServiceRequest.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)) {
			String blacklistStatusOfContact = internalServiceResponse.getContacts().get(0).getBlacklist().getStatus();
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName(), blacklistStatusOfContact, userProfile);
		}
	}


	

	private void setServiceFailureRepeatCheckProfileActivityLogDetailsSTP(RegistrationServiceRequest regServiceRequest, UserProfile userProfile,ComplianceContact contactToBeRechecked,
			InternalServiceResponse internalServiceResponse ,Connection conn,Integer activityId,MessageExchange exchange ) throws ComplianceException
	{
		EventServiceLog log = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE, EntityEnum.CONTACT.name(),
				contactToBeRechecked.getId());
		if ((boolean) regServiceRequest.getAdditionalAttribute("isEidEligible")) {
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId,
					Constants.ENTITY_TYPE_CONTACT, ServiceTypeEnum.KYC_SERVICE.getShortName(), log.getStatus(),
					userProfile);
		}
		if ((boolean) regServiceRequest.getAdditionalAttribute(IS_SANCTION_ELIGIBLE)) {
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId,
					Constants.ENTITY_TYPE_CONTACT, ServiceTypeEnum.SANCTION_SERVICE.getShortName(),
					contactToBeRechecked.getSanctionStatus(), userProfile);
		}
		if ((boolean) regServiceRequest.getAdditionalAttribute("isGlobalCheckEligible")) {
			String globalCheckStatus = internalServiceResponse.getContacts().get(0).getGlobalCheck().getStatus();
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId,
					Constants.ENTITY_TYPE_CONTACT, ServiceTypeEnum.GLOBAL_CHECK_SERVICE.getShortName(),
					globalCheckStatus, userProfile);
		}
		if ((boolean) regServiceRequest.getAdditionalAttribute("isIpCheckEligible")) {
			String ipCheckStatus = internalServiceResponse.getContacts().get(0).getIpCheck().getStatus();
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId,
					Constants.ENTITY_TYPE_CONTACT, ServiceTypeEnum.IP_SERVICE.getShortName(), ipCheckStatus,
					userProfile);
		}

		if ((boolean) regServiceRequest.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			String countryCheckStatus = internalServiceResponse.getContacts().get(0).getCountryCheck().getStatus();
			insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(conn, activityId,
					Constants.ENTITY_TYPE_CONTACT, ServiceTypeEnum.HRC_SERVICE.getShortName(), countryCheckStatus,
					userProfile);
		}
	}
	
	
	/**
	 * Insert in CFX profile activity log details ST pfor repeatcheck.
	 *
	 * @param conn the conn
	 * @param account the account
	 * @param activityId the activity id
	 * @param contactId the contact id
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertInCFXProfileActivityLogDetailsSTPforRepeatcheck(Connection conn, ComplianceAccount account,
			Integer activityId, Integer contactId) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {

			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PROFILE_MODULE);
			preparedStatment.setString(3, Constants.PROFILE_SIGNUP_TYPE);
			preparedStatment.setNString(4, account.getResponseDescription());
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(7, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert service failure repeat check profile activity log details STP.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @param entityType the entity type
	 * @param serviceType the service type
	 * @param serviceStatus the service status
	 * @param userProfile the user profile
	 * @throws ComplianceException the compliance exception
	 */
	private void insertServiceFailureRepeatCheckProfileActivityLogDetailsSTP(Connection conn, Integer activityId,
			String entityType, String serviceType, String serviceStatus, UserProfile userProfile)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PROFILE_MODULE);
			preparedStatment.setString(3, Constants.PROFILE_SIGNUP_TYPE);
			preparedStatment.setNString(4, getServiceFailureChangeString(entityType, serviceType, serviceStatus));
			preparedStatment.setInt(5, userProfile.getUserID());
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(7, userProfile.getUserID());
			preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Creates the service failed recheck payment in activity.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> createServiceFailedRecheckPaymentInActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			FundsInCreateRequest fRequest = (FundsInCreateRequest) exchange.getRequest();
			UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			conn = getConnection(Boolean.FALSE);
			insertServiceFailedRecheckPaymetInActivityLog(conn, fRequest, userProfile, message);

		} catch (Exception e) {
			LOGGER.error("Exception in createServiceFailedRecheckPaymentInActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert service failed recheck paymet in activity log.
	 *
	 * @param conn
	 *            the conn
	 * @param fRequest
	 *            the f request
	 * @param userProfile
	 *            the user profile
	 * @param message
	 *            the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertServiceFailedRecheckPaymetInActivityLog(Connection conn, FundsInCreateRequest fRequest,
			UserProfile userProfile, Message<MessageContext> message) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setInt(1, fRequest.getFundsInId());
			preparedStatment.setInt(2, userProfile.getUserID());
			preparedStatment.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(4, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			activityId = getActivityId(preparedStatment, activityId);

			insertServiceFailedRecheckPaymetInActivityLogDetails(conn, activityId, message, fRequest, userProfile);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}

	/**
	 * Insert service failed recheck paymet in activity log details.
	 *
	 * @param conn
	 *            the conn
	 * @param activityId
	 *            the activity id
	 * @param message
	 *            the message
	 * @param fRequest
	 *            the f request
	 * @param userProfile
	 *            the user profile
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertServiceFailedRecheckPaymetInActivityLogDetails(Connection conn, Integer activityId,
			Message<MessageContext> message, FundsInCreateRequest fRequest, UserProfile userProfile)
			throws ComplianceException {

		FundsInCreateResponse fundsInCreateResponse = (FundsInCreateResponse) message.getPayload()
				.getGatewayMessageExchange().getResponse();
		insertInPaymetInActivityLogDetailsSTP(conn, fundsInCreateResponse, activityId);

		if ((boolean) fRequest.getAdditionalAttribute("isCustomCheckEligible")) {
			CustomCheckResponse cResponse = (CustomCheckResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.CUSTOM_CHECKS_SERVICE).getResponse();
			insertBulkRecheckForPaymentInActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT, "CUSTOM",
					cResponse.getOverallStatus(), userProfile);
		}

		InternalServiceResponse internalServiceResponse = (InternalServiceResponse) message.getPayload()
				.getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE).getResponse();

		if ((boolean) fRequest.getAdditionalAttribute(IS_BLACKLIST_ELIGIBLE)) {
			String blacklistStatus = internalServiceResponse.getContacts().get(0).getBlacklist().getStatus();
			insertBulkRecheckForPaymentInActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.BLACK_LIST_SERVICE.getShortName(), blacklistStatus, userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_COUNTRY_CHECK_ELIGIBLE)) {
			String countryCheckStatus = internalServiceResponse.getContacts().get(0).getCountryCheck().getStatus();
			insertBulkRecheckForPaymentInActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT, "COUNTRY",
					countryCheckStatus, userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_SANCTION_ELIGIBLE)) {
			SanctionResponse sanctionResponse = (SanctionResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE).getResponse();
			SanctionContactResponse scResponse = sanctionResponse.getContactResponses().get(0);
			insertBulkRecheckForPaymentInActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.SANCTION_SERVICE.getShortName(), scResponse.getStatus(), userProfile);
		}

		if ((boolean) fRequest.getAdditionalAttribute(IS_FRAUGSTER_ELIGIBLE)) {
			FraugsterPaymentsInResponse fResponse = (FraugsterPaymentsInResponse) message.getPayload()
					.getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE).getResponse();
			insertBulkRecheckForPaymentInActivityLogDetail(conn, activityId, Constants.ENTITY_TYPE_CONTACT,
					ServiceTypeEnum.FRAUGSTER_SERVICE.getShortName(), fResponse.getStatus(), userProfile);
		}

	}

	/**
	 * Insert bulk recheck for payment in activity log detail.
	 *
	 * @param conn
	 *            the conn
	 * @param activityId
	 *            the activity id
	 * @param entityType
	 *            the entity type
	 * @param serviceType
	 *            the service type
	 * @param serviceStatus
	 *            the service status
	 * @param userProfile
	 *            the user profile
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void insertBulkRecheckForPaymentInActivityLogDetail(Connection conn, Integer activityId, String entityType,
			String serviceType, String serviceStatus, UserProfile userProfile) throws ComplianceException {
		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PAYMENT_IN);
			preparedStatment.setString(3, Constants.PAYMENT_IN_TYPE);
			preparedStatment.setNString(4, getServiceFailureChangeString(entityType, serviceType, serviceStatus));
			preparedStatment.setInt(5, userProfile.getUserID());
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	
	/**
	 * @param message
	 * @return
	 * @throws ComplianceException
	 */
	public Message<MessageContext> createUpdateProfileActivityLog(Message<MessageContext> message)
			throws ComplianceException {
		MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
		StatusUpdateRequest statusUpdateRequest = exchange.getRequest(StatusUpdateRequest.class);
		Account oldAccount = (Account) statusUpdateRequest.getAdditionalAttribute(Constants.FIELD_ACC_ACCOUNT);
		String onfidoDocumentResultReason = (String) statusUpdateRequest.getAdditionalAttribute(Constants.ONFIDO_RESULT_REASON);
		String onfidoDocumentResult = (String) statusUpdateRequest.getAdditionalAttribute(Constants.ONFIDO_RESULT);
		String onfidoDocumentResultDescription = null;
		boolean detailsNotMatch = (boolean) statusUpdateRequest.getAdditionalAttribute("Details_Not_Match");
		PreparedStatement preparedStatment = null;
		Connection conn = null;
		Integer activityId = null;
		try {
			conn = getConnection(Boolean.TRUE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_WITH_ORG_ID.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setString(3, statusUpdateRequest.getOrgCode());
			preparedStatment.setInt(4, oldAccount.getId());
			preparedStatment.setInt(5, oldAccount.getContacts().get(0).getId());
			preparedStatment.setInt(6, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(preparedStatment, activityId);
			if(OnfidoStatusEnum.CLEAR.getOnfidoStatusAsString().equalsIgnoreCase(onfidoDocumentResultReason)
					&& !ServiceStatus.FAIL.name().equals(onfidoDocumentResult))
				onfidoDocumentResultDescription = "POI document verified as Cleared by provider, Contact status updated";
			else if(OnfidoStatusEnum.REJECT.getOnfidoStatusAsString().equalsIgnoreCase(onfidoDocumentResultReason)
					&& ServiceStatus.FAIL.name().equals(onfidoDocumentResult))
				onfidoDocumentResultDescription = "Contact status not modified, POI document considered by provider as Rejected";
			else if(OnfidoStatusEnum.CAUTION.getOnfidoStatusAsString().equalsIgnoreCase(onfidoDocumentResultReason)
					&& ServiceStatus.FAIL.name().equals(onfidoDocumentResult))
				onfidoDocumentResultDescription = "Contact status not modified, POI document considered by provider as Caution";
			else if(OnfidoStatusEnum.SUSPECT.getOnfidoStatusAsString().equalsIgnoreCase(onfidoDocumentResultReason)
					&& !ServiceStatus.FAIL.name().equals(onfidoDocumentResult))
				onfidoDocumentResultDescription = "Contact status not modified, POI document considered by provider as Suspected, 'Onfido Suspect' watchlist added";
			else
				onfidoDocumentResultDescription = "Contact status not modified, POI document verified as "+onfidoDocumentResultReason+", Other Services checks are failed";
			
			if(OnfidoStatusEnum.CONSIDER.getOnfidoStatusAsString().equalsIgnoreCase(onfidoDocumentResultReason))
				onfidoDocumentResultDescription = "Contact status not modified, POI document verified as "+onfidoDocumentResultReason+", Sub-result not found";
			
			if(detailsNotMatch) {
				onfidoDocumentResultDescription = "Contact status not modified, Contact details doest not match with POI document, POI document verified as "+onfidoDocumentResultReason;
			}

			createUpdateProfileActivityLogDetailsSTP(conn, activityId,onfidoDocumentResultDescription);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * @param conn
	 * @param activityId
	 * @throws ComplianceException
	 */
	private void createUpdateProfileActivityLogDetailsSTP(Connection conn, Integer activityId,String onfidoDocumentResultDescription)
			throws ComplianceException {

		PreparedStatement preparedStatment = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PROFILE_MODULE);
			preparedStatment.setString(3, Constants.PROFILE_UPDATE_TYPE);
			preparedStatment.setNString(4, onfidoDocumentResultDescription);
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(7, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * Creates the STP delete contacte activity.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("unchecked")
	public Message<MessageContext> createSTPDeleteContactActivity(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			DeleteContactRequest deleteContactRequest = (DeleteContactRequest)exchange.getRequest();
			RegistrationServiceRequest regRequest = (RegistrationServiceRequest) deleteContactRequest.getAdditionalAttribute("regRequest");
			conn = getConnection(Boolean.FALSE);
			
			if(!CustomerTypeEnum.PFX.name().equalsIgnoreCase(regRequest.getAccount().getCustType())) {
				List<String> activityLog = (List<String>) deleteContactRequest.getAdditionalAttribute("activityLog");
				insertInDeleteContactActivityLogSTP(conn, activityLog, deleteContactRequest);
			}
		} catch (Exception e) {
			LOGGER.error("Exception in createSTPDeleteContactActivity()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;

	}

	/**
	 * Insert in delete contact activity log STP.
	 *
	 * @param conn the conn
	 * @param activityLog the activity log
	 * @param deleteContactRequest the delete contact request
	 * @throws ComplianceException the compliance exception
	 */
	private void insertInDeleteContactActivityLogSTP(Connection conn, List<String> activityLog,
			DeleteContactRequest deleteContactRequest) throws ComplianceException {
		RegistrationServiceRequest regRequest = (RegistrationServiceRequest)deleteContactRequest.getAdditionalAttribute("regRequest");
		Account account = regRequest.getAccount();
		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setInt(3, deleteContactRequest.getOrgId());
			preparedStatment.setInt(4, account.getId());
			preparedStatment.setNull(5, java.sql.Types.INTEGER);
			preparedStatment.setInt(6, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(preparedStatment, activityId);

			insertInDeleteContactActivityLogDetailsSTP(conn, activityId, activityLog);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	

	/**
	 * Insert in delete contact activity log details STP.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @param activityLog the activity log
	 * @throws ComplianceException the compliance exception
	 */
	private void insertInDeleteContactActivityLogDetailsSTP(Connection conn, Integer activityId,
			List<String> activityLog) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {

			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			for (String log : activityLog) {
				preparedStatment.setInt(1, activityId);
				preparedStatment.setString(2, Constants.PROFILE_MODULE);
				preparedStatment.setString(3, Constants.PROFILE_DELETE_CONTACT_TYPE);
				preparedStatment.setNString(4, log);
				preparedStatment.setInt(5, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatment.setInt(7, Constants.DEFAULT_USER);
				preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

				int count = preparedStatment.executeUpdate();

				if (count == 0) {
					closePrepareStatement(preparedStatment);
					throw new ComplianceException(InternalProcessingCode.INV_REQUEST,
							new Exception(Constants.MISSIG_DATA));
				}
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * AT-3492
	 * Adds the blacklist activity.
	 *
	 * @param fRequest the f request
	 * @param contact the contact
	 */
	private void addBlacklistActivity(RegistrationServiceRequest fRequest,ComplianceContact contact) {
		boolean addBlackistactivity = false;
		if(null != fRequest.getAdditionalAttribute(Constants.ADD_BLACKLIST_ACTIVITY_LOG_FOR_EU))
			addBlackistactivity = (boolean) fRequest.getAdditionalAttribute(Constants.ADD_BLACKLIST_ACTIVITY_LOG_FOR_EU);
		if(addBlackistactivity && contact.getCrc()==ComplianceReasonCode.KYC_POI) {
			String contactResDescr = contact.getResponseDescription();
			contactResDescr = "Black Listed Customer information " + contactResDescr;
			contact.setResponseDescription(contactResDescr);
		}
	}
	
	/**
	 * Creates the STP transaction monitoring MQ activity log.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> createSTPTransactionMonitoringMQActivityLog(Message<MessageContext> message)
			throws ComplianceException {
		Connection conn = null;
		try {

			MessageExchange exchange = message.getPayload().getGatewayMessageExchange();
			TransactionMonitoringMQRequest transactionMonitoringMQRequest = (TransactionMonitoringMQRequest) exchange.getRequest();
			MessageExchange msgExchange = message.getPayload()
					.getMessageExchange(ServiceTypeEnum.TRANSACTION_MONITORING_MQ_RESEND);
			TransactionMonitoringMQServiceResponse response = (TransactionMonitoringMQServiceResponse) msgExchange.getResponse();;
			conn = getConnection(Boolean.FALSE);
			
			if (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.SIGN_UP) || 
			transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.UPDATE) ||
			transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.ADD_CONTACT)) {
				insertTMMQProfileActivityLogSTP(conn, response, transactionMonitoringMQRequest);
			} else if(transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in") 
						|| transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_in_update")) {
				insertTMMQPaymentInActivityLogs(response, transactionMonitoringMQRequest);
			} else if(transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out") 
						 || transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase("payment_out_update")) {
				insertTMMQPaymentOutActivityLogs(response, transactionMonitoringMQRequest);
			}

		} catch (Exception e) {
			LOGGER.error("Exception in createSTPTransactionMonitoringMQActivityLog()", e);
			message.getPayload().setFailed(true);
		} finally {
			closeConnection(conn);
		}

		return message;
	}
	
	/**
	 * Insert TMMQ profile activity log STP.
	 *
	 * @param conn the conn
	 * @param response the response
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertTMMQProfileActivityLogSTP(Connection conn, TransactionMonitoringMQServiceResponse response,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest) throws ComplianceException {

		PreparedStatement preparedStatment = null;
		Integer activityId = null;
		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_TM_MQ_PROFILE_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatment.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(2, Constants.DEFAULT_USER);
			preparedStatment.setString(3, transactionMonitoringMQRequest.getOrgCode());
			preparedStatment.setInt(4, transactionMonitoringMQRequest.getAccountID());
			if(transactionMonitoringMQRequest.getContactID() != null && (transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.SIGN_UP) ||
					transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.UPDATE) ||
					transactionMonitoringMQRequest.getRequestType().equalsIgnoreCase(Constants.ADD_CONTACT))) {
				preparedStatment.setInt(5, transactionMonitoringMQRequest.getContactID());
			}else {
				preparedStatment.setNull(5, Types.INTEGER);
			}
			preparedStatment.setInt(6, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(8, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(9, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(preparedStatment, activityId);
			insertTMMQProfileActivityLogDetailsSTP(conn, activityId);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * Insert TMMQ profile activity log details STP.
	 *
	 * @param conn the conn
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @param activityId the activity id
	 * @throws ComplianceException the compliance exception
	 */
	private void insertTMMQProfileActivityLogDetailsSTP(Connection conn, Integer activityId) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, Constants.PROFILE_MODULE);
			preparedStatment.setString(3, Constants.PROFILE_SIGNUP_TYPE);
			preparedStatment.setNString(4, "Resend Transaction Monitoring SignUp");
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatment.setInt(7, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(8, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * Insert TMMQ payment in activity logs.
	 *
	 * @param response the response
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertTMMQPaymentInActivityLogs(TransactionMonitoringMQServiceResponse response,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest) throws ComplianceException {
		TransactionMonitoringPaymentInResponse payInResponse = response.getTransactionMonitoringPaymentInResponse();
		PreparedStatement piActivtyStmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Integer activityId = null;
		try {
			connection = getConnection(Boolean.FALSE);
			piActivtyStmt = connection.prepareStatement(DBQueryConstant.INSERT_TM_MQ_PAYMENT_IN_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			
				piActivtyStmt.setInt(1, transactionMonitoringMQRequest.getPaymentInID());
				piActivtyStmt.setInt(2, transactionMonitoringMQRequest.getCreatedBy());
				piActivtyStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				piActivtyStmt.setInt(4, transactionMonitoringMQRequest.getCreatedBy());
				piActivtyStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				
				activityId = getActivityId(piActivtyStmt, activityId);
				
				insertTMMQPaymentInActivityLogDetailsSTP(connection, activityId, payInResponse);
		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(piActivtyStmt);
			closeConnection(connection);
		}
	}
	
	/**
	 * Insert TMMQ payment in activity log details STP.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @throws ComplianceException the compliance exception
	 */
	private void insertTMMQPaymentInActivityLogDetailsSTP(Connection conn, Integer activityId, TransactionMonitoringPaymentInResponse response) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			String reasonDiscription = "Resend Transaction Monitoring PaymentIn. ";
			if(response.getResponseDescription() != null && !response.getResponseDescription().isEmpty()) {
				reasonDiscription += response.getResponseDescription();
			}
			
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, "PAYMENT_IN");
			preparedStatment.setString(3, Constants.PAYMENT_IN_TYPE);
			preparedStatment.setNString(4, reasonDiscription);
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * Insert TMMQ payment out activity logs.
	 *
	 * @param response the response
	 * @param transactionMonitoringMQRequest the transaction monitoring MQ request
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertTMMQPaymentOutActivityLogs(TransactionMonitoringMQServiceResponse response,
			TransactionMonitoringMQRequest transactionMonitoringMQRequest) throws ComplianceException {
		TransactionMonitoringPaymentOutResponse payOutresponse = response.getTransactionMonitoringPaymentOutResponse();
		PreparedStatement poActivtyStmt = null;
		Connection connection = null;
		ResultSet rs = null;
		Integer activityId = null;
		try {
			connection = getConnection(Boolean.FALSE);
			poActivtyStmt = connection.prepareStatement(DBQueryConstant.INSERT_TM_MQ_PAYMENT_OUT_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			
			poActivtyStmt.setInt(1, transactionMonitoringMQRequest.getPaymentOutID());
			poActivtyStmt.setInt(2, transactionMonitoringMQRequest.getCreatedBy());
			poActivtyStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			poActivtyStmt.setInt(4, transactionMonitoringMQRequest.getCreatedBy());
			poActivtyStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				
				activityId = getActivityId(poActivtyStmt, activityId);
				
				insertTMMQPaymentOutActivityLogDetailsSTP(connection, activityId, payOutresponse);
		} catch (ComplianceException e) {
			throw e;
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(poActivtyStmt);
			closeConnection(connection);
		}
	}
	
	/**
	 * Insert TMMQ payment out activity log details STP.
	 *
	 * @param conn the conn
	 * @param activityId the activity id
	 * @throws ComplianceException the compliance exception
	 */
	private void insertTMMQPaymentOutActivityLogDetailsSTP(Connection conn, Integer activityId, TransactionMonitoringPaymentOutResponse response) throws ComplianceException {

		PreparedStatement preparedStatment = null;

		try {
			String reasonDiscription = "Resend Transaction Monitoring PaymentOut. ";
			if(response.getResponseDescription() != null && !response.getResponseDescription().isEmpty()) {
				reasonDiscription += response.getResponseDescription();
			}
			
			preparedStatment = conn.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, "PAYMENT_OUT");
			preparedStatment.setString(3, Constants.PAYMENT_OUT_TYPE);
			preparedStatment.setNString(4, reasonDiscription);
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * Insert intuition payment in status update activity log.
	 *
	 * @param connection the connection
	 * @param paymentInId the paymentInId
	 * @throws ComplianceException the compliance exception
	 */
	//AT-4749
	public void insertIntuitionPaymentInStatusUpdateActivityLog(Connection connection, Integer paymentInId)
			throws ComplianceException {
		PreparedStatement piActivtyStmt = null;
		ResultSet rs = null;
		Integer activityId = null;

		try {
			piActivtyStmt = connection.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);

			piActivtyStmt.setInt(1, paymentInId);
			piActivtyStmt.setInt(2, Constants.DEFAULT_USER);
			piActivtyStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			piActivtyStmt.setInt(4, Constants.DEFAULT_USER);
			piActivtyStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(piActivtyStmt, activityId);
			insertPaymentIntuitionStatusUpdateActivityLogDetails(connection, activityId);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(piActivtyStmt);
		}
	}

	/**
	 * Insert payment intuition status update activity log details.
	 *
	 * @param connection the connection
	 * @param activityId the activity id
	 * @throws ComplianceException the compliance exception
	 */
	private void insertPaymentIntuitionStatusUpdateActivityLogDetails(Connection connection, Integer activityId)
			throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {

			preparedStatment = connection
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, "PAYMENT_IN");
			preparedStatment.setString(3, Constants.PAYMENT_IN_TYPE);
			preparedStatment.setNString(4, "Intuition Status Update Payment In Call back");
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
	}
	
	/**
	 * Insert intuition payment out status update activity log.
	 *
	 * @param connection the connection
	 * @param paymentOutId the paymentOutId
	 * @throws ComplianceException the compliance exception
	 */
	//AT-4749
	public void insertIntuitionPaymentOutStatusUpdateActivityLog(Connection connection, Integer paymentOutId)
			throws ComplianceException {
		PreparedStatement piActivtyStmt = null;
		ResultSet rs = null;
		Integer activityId = null;

		try {
			piActivtyStmt = connection.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_STP.getQuery(),
					Statement.RETURN_GENERATED_KEYS);

			piActivtyStmt.setInt(1, paymentOutId);
			piActivtyStmt.setInt(2, Constants.DEFAULT_USER);
			piActivtyStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			piActivtyStmt.setInt(4, Constants.DEFAULT_USER);
			piActivtyStmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

			activityId = getActivityId(piActivtyStmt, activityId);
			insertPaymentOutIntuitionStatusUpdateActivityLogDetails(connection, activityId);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.GEN_DECLINE, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(piActivtyStmt);
		}
	}

	/**
	 * Insert payment out intuition status update activity log details.
	 *
	 * @param connection the connection
	 * @param activityId the activity id
	 * @throws ComplianceException the compliance exception
	 */
	private void insertPaymentOutIntuitionStatusUpdateActivityLogDetails(Connection connection, Integer activityId) throws ComplianceException {
		PreparedStatement preparedStatment = null;
		try {

			preparedStatment = connection
					.prepareStatement(DBQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL_STP.getQuery());
			preparedStatment.setInt(1, activityId);
			preparedStatment.setString(2, "PAYMENT_OUT");
			preparedStatment.setString(3, Constants.PAYMENT_OUT_TYPE);
			preparedStatment.setNString(4, "Intuition Status Update Payment Out Call back");
			preparedStatment.setInt(5, Constants.DEFAULT_USER);
			preparedStatment.setTimestamp(6, new Timestamp(System.currentTimeMillis()));

			int count = preparedStatment.executeUpdate();

			if (count == 0) {
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST, new Exception(Constants.MISSIG_DATA));
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
		}
		
	}
	
	
}
