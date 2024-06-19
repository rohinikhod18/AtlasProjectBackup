package com.currenciesdirect.gtg.compliance.compliancesrv.core.onfidostatusupdate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessageResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.DBQueryConstant;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchangeWrapper;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

@SuppressWarnings({"squid:S3252","squid:S2095"})
public class OnfidoStatusUpdateActivityLogs extends AbstractDao{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(OnfidoStatusUpdateActivityLogs.class);
	
	/**
	 * Creates the onfido status activity.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> createOnfidoStatusActivity(Message<MessageContext> message) 
			throws ComplianceException {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OnfidoUpdateRequest request = messageExchange.getRequest(OnfidoUpdateRequest.class);
			if ("profile".equalsIgnoreCase(request.getResourceType())) {
				createProfileActivity(message);
			}
		}
		catch(Exception e) {
			LOGGER.error("Error in OnfidoStatusUpdateActivityLogs createOnfidoStatusActivity() {1}", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}
	
	/**
	 * Creates the profile activity.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	private Message<MessageContext> createProfileActivity(Message<MessageContext> message) throws ComplianceException {
		try {
			List<ProfileActivityLogDto> profileActivites = new ArrayList<>();
			for (MessageExchangeWrapper exchange : message.getPayload().getMessageCollection()) {
				ServiceMessageResponse response = exchange.getMessageExchange().getResponse();
				@SuppressWarnings("unchecked")
				List<ProfileActivityLogDto> activites = (List<ProfileActivityLogDto>) response
						.getAdditionalAttribute("profileActivities");
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
	 * Insert profile activity logs.
	 *
	 * @param profileActivityLogs the profile activity logs
	 * @throws ComplianceException the compliance exception
	 */
	@SuppressWarnings("resource")
	private void insertProfileActivityLogs(List<ProfileActivityLogDto> profileActivityLogs) throws ComplianceException {
		PreparedStatement profileActivityStmt = null;
		Connection profileConnection = null;
		ResultSet rs = null;
		try {
			profileConnection = getConnection(Boolean.FALSE);
			profileActivityStmt = profileConnection.prepareStatement(
					DBQueryConstant.INSERT_PROFILE_ACTIVITY_LOG.getQuery(), PreparedStatement.RETURN_GENERATED_KEYS);
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
	
}
