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

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateData;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringContactRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.EntityDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class AbstractDBServiceImpl.
 * 
 * @author abhijeetg
 */
@SuppressWarnings("squid:S2095")
public abstract class AbstractDBServiceImpl extends AbstractDao {
	
	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(AbstractDBServiceImpl.class);

	/** The Constant CONTACT_ID. */
	private static final String CONTACT_ID = "contactId";
	
	/** The Constant REASON. */
	private static final String REASON = "Reason";
	/**
	 * Insert into user resource lock. while performing STP we insert dummy
	 * records for Inactive, Hold customers for Registration and
	 * PaymentIn/PaymentOut respectively.
	 *
	 * @param connection the connection
	 * @param resourceType the resource type
	 * @param resourceId            the resource id
	 * @throws ComplianceException             the compliance exception
	 */
	protected void insertIntoUserResourceLock(Connection connection, String resourceType, Integer resourceId)
			throws ComplianceException {
		ResultSet rs = null;
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				DBQueryConstant.INSERT_USER_RESOURCE_LOCK.getQuery(), Statement.RETURN_GENERATED_KEYS);) {
			preparedStatement.setString(1, Constants.DUMMY_USER);
			preparedStatement.setInt(2, UserLockResourceTypeEnum.getDbStatusFromUiStatus(resourceType));
			preparedStatement.setInt(3, resourceId);
			preparedStatement.setTimestamp(4, null);
			preparedStatement.setString(5, Constants.DUMMY_USER);
			preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setTimestamp(7, null);
			preparedStatement.executeUpdate();
			rs = preparedStatement.getGeneratedKeys();

		} catch (SQLException se) {
			LOG.debug("Cannot insert duplicate key row in object 'UserResourceLock' : ", se);
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
		}
	}
	
	/**
	 * Insert into fraugster schedular data.
	 *
	 * @param eventServiceLog the event service log
	 * @param connection the connection
	 * @param query the query
	 * @throws ComplianceException the compliance exception
	 */
	protected void insertIntoFraugsterSchedularData(EventServiceLog eventServiceLog, Connection connection, String query)
			throws ComplianceException {
			
			FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
					eventServiceLog.getSummary());
			PreparedStatement statement = null;
			try {
				statement = connection.prepareStatement(query);
				statement.setString(1, fraugsterSummary.getCdTrasId());
				statement.setString(2, fraugsterSummary.getFrgTransId());
				statement.setBoolean(3, "1".equals(fraugsterSummary.getFraugsterApproved())); 
				statement.setString(4, null); 
				statement.setString(5, eventServiceLog.getStatus().equals(Constants.PASS) ? Constants.APPROVED : Constants.DECLINED); 
				statement.setTimestamp(6, null); 
				statement.setInt(7, eventServiceLog.getCratedBy()); 
				statement.executeUpdate();
			} catch (Exception e) {
				throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
			} finally {
				closePrepareStatement(statement);

			}
		}
	
	/**
	 * Business -- Update sanction status into contact or account table after
	 * updating values from UI.
	 * 
	 * Implementation--- 1) If operation is SANCTION_UPDATE or SANCTION_RESEND
	 * then call updateSanctionServiceStatus() 2) Get the EntityDetails and
	 * eventservicelog of that entityId and update sanction status of that
	 * entityId
	 * 
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Message<MessageContext> updateSanctionStatus(Message<MessageContext> message) throws ComplianceException {

		try {
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			if (operation == OperationEnum.SANCTION_RESEND) {
				MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.SANCTION_SERVICE);
				RegistrationServiceRequest request = message.getPayload().getGatewayMessageExchange()
						.getRequest(RegistrationServiceRequest.class);
				EntityDetails entityDetails = (EntityDetails) request
						.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.valueOf(entityDetails.getEntityType()).name(), entityDetails.getEntityId());

				/** Check Entity type and based on entity type find newstatus */
				updateSanctionServiceStatus(eventServiceLog, entityDetails);
			} else if (operation == OperationEnum.SANCTION_UPDATE) {
				MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
				SanctionUpdateRequest sanctionUpdateRequest = messageExchange.getRequest(SanctionUpdateRequest.class);
				EntityDetails entityDetails = (EntityDetails) sanctionUpdateRequest
						.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
				SanctionUpdateData data = sanctionUpdateRequest.getSanctions().get(0);
				EventServiceLog eventServiceLog = messageExchange.getEventServiceLog(ServiceTypeEnum.SANCTION_SERVICE,
						EntityEnum.valueOf(data.getEntityType()).name(), data.getEntityId());
				updateSanctionServiceStatus(eventServiceLog, entityDetails);
			}
		} catch (Exception e) {
			LOG.error("Error in AbstractRegistrationDBService updateSanctionStatus()", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	/**
	 * Update Sanction Status of respective Entity Type 1. If Entity type is
	 * Contact then update status into Contact Table 2. If Entity type is
	 * Account then update status into Account Table
	 *
	 * @param eventServiceLog
	 *            the event service log
	 * @param entityDetails
	 *            the entity details
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public void updateSanctionServiceStatus(EventServiceLog eventServiceLog, EntityDetails entityDetails)
			throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			if (null != entityDetails.getEntityType()
					&& (EntityEnum.ACCOUNT.name()).equalsIgnoreCase(entityDetails.getEntityType())) {
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_SANCTION_STATUS_FOR_ACCOUNT.getQuery());
			} else {
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_SANCTION_STATUS_FOR_CONTACT.getQuery());
			}
			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(eventServiceLog.getStatus()));
			preparedStatment.setInt(2, eventServiceLog.getEntityId());
			preparedStatment.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
	}

	/**
	 *  Method added to fetch User ID by SSOUserId from user table - Vishal J.
	 *
	 * @param ssoUserID the sso user ID
	 * @return the user ID from SSO user ID
	 * @throws ComplianceException the compliance exception
	 */
	/**
	 * Purpose: When we are doing repeat check on 'SANCTION' the 'updated by'
	 * entry in 'EventServiceLog' table in database. But it was inserted wrong
	 * so to update with proper UserID this method is written.
	 * 
	 * Implementation: 1)I added a query in DBQueryConstants which will fetch
	 * the ID of currently logged in user by his/her name. 2)Once that ID
	 * returned from this method, we are simply updating that into UserProfile
	 * token and inserting entry in EventServiceLog table.
	 * 
	 * Method added by Vishal J to fetch User ID by providing SSOUserID from
	 * User table - Vishal J
	 *
	 * @param ssoUserID
	 *            the sso user ID
	 * @return the user ID from SSO user ID
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Integer getUserIDFromSSOUserID(String ssoUserID) throws ComplianceException {
		Connection conn = null;
		PreparedStatement userStatment = null;
		ResultSet userRS = null;
		Integer userID = 1;
		try {
			conn = getConnection(Boolean.TRUE);
			userStatment = conn.prepareStatement(DBQueryConstant.GET_USER_ID_BY_SSOUSERID.getQuery());
			userStatment.setString(1, ssoUserID);
			userRS = userStatment.executeQuery();
			if (userRS.next()) {
				userID = userRS.getInt(1);
			}
			return userID;
		} catch (Exception e) {
			LOG.error(Constants.ERROR, e);
		} finally {
			closeResultset(userRS);
			closePrepareStatement(userStatment);
			closeConnection(conn);
		}
		return userID;
	}

	/**
	 * Business -- get list of accountSfId, tradeAccountId, tradeAccountNumber,
	 * contactSFIds and tradeContactId to validate duplicate id's.
	 * 
	 * Implementation--- 1) Execute GET_ACCOUNT_CONTACT_EXISTING_ID query to get
	 * Existing id's 2) if records are present then set additional attributes
	 * and return true 3) if no records present then return false
	 *
	 * @author abhijeetg
	 * @param orgCode
	 *            the org code
	 * @param accountSfId
	 *            the account sf id
	 * @param tradeAccountId
	 *            the trade account id
	 * @param tradeAccountNumber
	 *            the trade account number
	 * @param tradeContactIds
	 *            the trade contact ids
	 * @param contactSFIds
	 *            the contact SF ids
	 * @param details
	 *            the details
	 * @return the existing account contact id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public Boolean getExistingAccountContactId(String orgCode, String accountSfId, Integer tradeAccountId,
			String tradeAccountNumber, List<Integer> tradeContactIds, List<String> contactSFIds,
			RegistrationServiceRequest details) throws ComplianceException {

		Connection idExistsConn = null;
		PreparedStatement idExistsStatement = null;
		ResultSet idExistsRS = null;
		try {
			String contactIdsStr = "''";
			String tradecontactIdstr = "''";
			if (!contactSFIds.isEmpty()) {
				contactIdsStr = contactSFIds.toString();
				contactIdsStr = contactIdsStr.substring(1, contactIdsStr.length() - 1).replace(", ", ",");
			}
			if (!tradeContactIds.isEmpty()) {
				tradecontactIdstr = tradeContactIds.toString();
				tradecontactIdstr = tradecontactIdstr.substring(1, tradecontactIdstr.length() - 1).replace(", ", ",");
			}
			idExistsConn = getConnection(Boolean.TRUE);
			String query = DBQueryConstant.GET_ACCOUNT_CONTACT_EXISTING_ID.getQuery()
					.replace(Constants.TRADECONTACTIDS, tradecontactIdstr)
					.replace(Constants.CONTACTSFID, contactIdsStr);
			idExistsStatement = idExistsConn.prepareStatement(query);

			idExistsStatement.setString(1, accountSfId);
			if (null == tradeAccountId)
				idExistsStatement.setNull(2, Types.INTEGER);
			else
				idExistsStatement.setInt(2, tradeAccountId);
			if (null == tradeAccountNumber) {
				idExistsStatement.setNull(3, Types.VARCHAR);
			} else {
				idExistsStatement.setString(3, tradeAccountNumber);
			}
			idExistsStatement.setString(4, orgCode);
			idExistsStatement.setString(5, orgCode);

			idExistsRS = idExistsStatement.executeQuery();
			int count = 0;
			List<String> accountSfidList = new ArrayList<>();
			List<String> tradeAccountNumberList = new ArrayList<>();
			List<String> contactSfidList = new ArrayList<>();
			List<Integer> tradeAccountIdList = new ArrayList<>();
			List<Integer> tradeContactIdList = new ArrayList<>();

			while (idExistsRS.next()) {

				count = 1;
				accountSfidList.add(idExistsRS.getString("CRMAccountID"));
				tradeAccountNumberList.add(idExistsRS.getString("TradeAccountNumber"));
				contactSfidList.add(idExistsRS.getString("CRMContactID"));
				tradeAccountIdList.add(idExistsRS.getInt("TradeAccountID"));
				tradeContactIdList.add(idExistsRS.getInt("TradeContactID"));
			}

			details.addAttribute(Constants.ACCOUNT_SF_ID_LIST, accountSfidList);
			details.addAttribute(Constants.TRADE_ACCOUNT_NUMBER_LIST, tradeAccountNumberList);
			details.addAttribute(Constants.CONTACT_SF_ID_LIST, contactSfidList);
			details.addAttribute(Constants.TRADE_ACCOUNT_ID_LIST, tradeAccountIdList);
			details.addAttribute(Constants.TRADE_CONTACT_ID_LIST, tradeContactIdList);

			if (count == 0) {
				return Boolean.FALSE;
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(idExistsRS);
			closePrepareStatement(idExistsStatement);
			closeConnection(idExistsConn);
		}
		return Boolean.TRUE;
	}

	/**
	 * Gets the internal rule service contact status.
	 *
	 * @param accountid the accountid
	 * @param request the request
	 * @return the internal rule service contact status
	 * @throws ComplianceException the compliance exception
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getInternalRuleServiceContactStatus(java.lang.
	 * Integer, com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.
	 * RegistrationServiceRequest)
	 */
	public RegistrationServiceRequest getInternalRuleServiceContactStatus(Integer accountid,
			RegistrationServiceRequest request) throws ComplianceException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		StringBuilder listOfBlackListedContact = new StringBuilder();
		StringBuilder listOfSanctionContactFailed = new StringBuilder();
		StringBuilder listOfSanctionContactPending = new StringBuilder();
		RegistrationServiceRequest details = new RegistrationServiceRequest();
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection
					.prepareStatement(DBQueryConstant.GET_INTERNAL_RULE_SERVICE_CONTACT_STATUS.getQuery());
			statement.setInt(1, accountid);
			rs = statement.executeQuery();
			while (rs.next()) {
				if (ServiceStatus.FAIL.getServiceStatusAsInteger().equals(rs.getInt("BlacklistStatus"))) {
					listOfBlackListedContact.append(rs.getInt(CONTACT_ID)).append(',');
				}
				if (ServiceStatus.FAIL.getServiceStatusAsInteger().equals(rs.getInt("SanctionStatus"))) {
					listOfSanctionContactFailed.append(rs.getInt(CONTACT_ID)).append(',');
				}
				if (ServiceStatus.PENDING.getServiceStatusAsInteger().equals(rs.getInt("SanctionStatus"))) {
					listOfSanctionContactPending.append(rs.getInt(CONTACT_ID)).append(',');
				}

			}
			request.addAttribute("listOfBlackListedContact", listOfBlackListedContact);
			request.addAttribute("listOfSanctionContactFailed", listOfSanctionContactFailed);
			request.addAttribute("listOfSanctionContactPending", listOfSanctionContactPending);

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return details;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#isAccountExistByDBId(java.lang.Integer)
	 */

	/**
	 * Checks if is account exist by DB id.
	 *
	 * @param accountId the account id
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	public Boolean isAccountExistByDBId(Integer accountId) throws ComplianceException {
		Connection conn = null;
		PreparedStatement accByIdStmt = null;
		ResultSet accByIdRS = null;
		try {
			conn = getConnection(Boolean.TRUE);
			accByIdStmt = conn.prepareStatement(DBQueryConstant.CHECK_ACCOUNT_ID_EXIST_FOR_REPEAT_CHECK.getQuery());
			accByIdStmt.setInt(1, accountId);
			accByIdRS = accByIdStmt.executeQuery();
			if (accByIdRS.next()) {
				accByIdRS.close();
				return Boolean.TRUE;
			}

		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(accByIdRS);
			closePrepareStatement(accByIdStmt);
			closeConnection(conn);

		}
		return Boolean.FALSE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#isContactExistByDBId(java.lang.Integer)
	 */

	/**
	 * Checks if is contact exist by DB id.
	 *
	 * @param contactId the contact id
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	public Boolean isContactExistByDBId(Integer contactId) throws ComplianceException {
		Connection contactByIdConn = null;
		PreparedStatement contactByIdStatment = null;
		ResultSet contactByIdRS = null;
		try {
			contactByIdConn = getConnection(Boolean.TRUE);
			contactByIdStatment = contactByIdConn
					.prepareStatement(DBQueryConstant.CHECK_CONTACT_ID_EXIST_FOR_REPEAT_CHECK.getQuery());
			contactByIdStatment.setInt(1, contactId);
			contactByIdRS = contactByIdStatment.executeQuery();
			if (contactByIdRS.next()) {
				contactByIdRS.close();
				return Boolean.TRUE;
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(contactByIdRS);
			closePrepareStatement(contactByIdStatment);
			closeConnection(contactByIdConn);
		}
		return Boolean.FALSE;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.
	 * IRegistrationDBService#getFirstSanctionSummary(java.lang.Integer,
	 * java.lang.String)
	 */

	/**
	 * Gets the first sanction summary.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @return the first sanction summary
	 * @throws ComplianceException the compliance exception
	 */
	public SanctionSummary getFirstSanctionSummary(Integer entityId, String entityType) throws ComplianceException {
		Connection sanctionSummaryConn = null;
		PreparedStatement sanctionSummaryStatment = null;
		ResultSet sanctionSummaryRS = null;
		SanctionSummary sanctionSummary = null;
		try {
			sanctionSummaryConn = getConnection(Boolean.TRUE);
			sanctionSummaryStatment = sanctionSummaryConn
					.prepareStatement(DBQueryConstant.GET_SANCTION_FIRST_SUMMARY.getQuery());
			sanctionSummaryStatment.setInt(1, EntityEnum.getEntityTypeAsInteger(entityType));
			sanctionSummaryStatment.setString(2, "" + entityId);
			sanctionSummaryRS = sanctionSummaryStatment.executeQuery();
			if (sanctionSummaryRS.next()) {
				sanctionSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
						sanctionSummaryRS.getString("Summary"));
			}
		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(sanctionSummaryRS);
			closePrepareStatement(sanctionSummaryStatment);
			closeConnection(sanctionSummaryConn);
		}
		return sanctionSummary;
	}

	/**
	 * Checks if is account present by trade account id and org id.
	 *
	 * @param tradeAccountId
	 *            the trade account id
	 * @param orgCode
	 *            the org code
	 * @return true, if is account present by trade account id and org id
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	public boolean isAccountPresentByTradeAccountIdAndOrgId(int tradeAccountId, String orgCode)
			throws ComplianceException {
		Connection tradeAccountIdConn = null;
		PreparedStatement tradeAccountIdStatment = null;
		ResultSet tradeAccountIdRS = null;
		try {
			tradeAccountIdConn = getConnection(Boolean.TRUE);
			tradeAccountIdStatment = tradeAccountIdConn
					.prepareStatement(DBQueryConstant.GET_ACCOUNT_BY_TRADEACCOUNTID_AND_ORG.getQuery());
			tradeAccountIdStatment.setInt(1, tradeAccountId);
			tradeAccountIdStatment.setString(2, orgCode);
			tradeAccountIdRS = tradeAccountIdStatment.executeQuery();
			if (tradeAccountIdRS.next()) {
				return true;
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(tradeAccountIdRS);
			closePrepareStatement(tradeAccountIdStatment);
			closeConnection(tradeAccountIdConn);
		}
		return false;

	}
	
	/**
	 * Update blacklist status.
	 *
	 * @param message the message
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	public Message<MessageContext> updateBlacklistStatus(Message<MessageContext> message) throws ComplianceException {

		try {
			OperationEnum operation = message.getPayload().getGatewayMessageExchange().getOperation();
			if (operation == OperationEnum.BLACKLIST_RESEND) {
				MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.INTERNAL_RULE_SERVICE);
				RegistrationServiceRequest request = message.getPayload().getGatewayMessageExchange()
						.getRequest(RegistrationServiceRequest.class);
				EntityDetails entityDetails = (EntityDetails) request
						.getAdditionalAttribute(Constants.FIELD_ENTITY_DETAILS);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.BLACK_LIST_SERVICE,
						EntityEnum.valueOf(entityDetails.getEntityType()).name(), entityDetails.getEntityId());

				/** Check Entity type and based on entity type find newstatus */
				updateBlacklistServiceStatus(eventServiceLog, entityDetails);
			}
		} catch (Exception e) {
			LOG.error("Error in AbstractRegistrationDBService updateBlacklistStatus()", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}
	
	/**
	 * Update blacklist service status.
	 *
	 * @param eventServiceLog the event service log
	 * @param entityDetails the entity details
	 * @throws ComplianceException the compliance exception
	 */
	public void updateBlacklistServiceStatus(EventServiceLog eventServiceLog, EntityDetails entityDetails)
			throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		try {
			conn = getConnection(Boolean.FALSE);
			if (null != entityDetails.getEntityType()
					&& (EntityEnum.ACCOUNT.name()).equalsIgnoreCase(entityDetails.getEntityType())) {
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_BLACKLIST_STATUS_FOR_ACCOUNT.getQuery());
			} else {
				preparedStatment = conn.prepareStatement(DBQueryConstant.UPDATE_BLACKLIST_STATUS_FOR_CONTACT.getQuery());
			}
			preparedStatment.setInt(1, ServiceStatus.getStatusAsInteger(eventServiceLog.getStatus()));
			preparedStatment.setInt(2, eventServiceLog.getEntityId());
			preparedStatment.executeUpdate();
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
	}
	
	/**
	 * Gets the previous inactive reason.
	 *
	 * @param id the id
	 * @return the previous inactive reason
	 * @throws ComplianceException the compliance exception
	 */
	public String getPreviousInactiveReason(Integer id) throws ComplianceException{

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		String previousInactiveReason = "";
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection
					.prepareStatement(DBQueryConstant.GET_PREVIOUS_CONTACT_INACTIVE_REASON.getQuery());
			statement.setInt(1, id);
			rs = statement.executeQuery();
			if (rs.next()) {
				previousInactiveReason = rs.getString(REASON);
			}
		} catch (Exception e) {
			throw new ComplianceException(InternalProcessingCode.INTERNAL_SERVER_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return previousInactiveReason;
	}

	/**
	 * Gets the country display name based on three letter ISO Code
	 *
	 * @param countryCode
	 *            the country code
	 * @return the country display name
	 * @throws ComplianceException
	 */
	public String getCountryDisplayName(String countryCode) throws ComplianceException {
		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String displayName = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_COUNTRY_DISPLAY_NAME.getQuery());
			preparedStatement.setString(1, countryCode);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {

				displayName = resultSet.getString("DisplayName");

			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return displayName;
	}
	
	
	/**
	 * Gets the contact watchlist.
	 *
	 * @param contactID the contact ID
	 * @return the contact watchlist
	 * @throws ComplianceException the compliance exception
	 */
	public List<String> getContactWatchlist(Integer contactID) throws ComplianceException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<String> watchlist = new ArrayList<>();
		String data;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_CONTACT_WATCHLIST.getQuery());
			preparedStatement.setInt(1, contactID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				data = resultSet.getString(REASON);
				watchlist.add(data);
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return watchlist;

	}
	
	public List<String> getContactStatusUpdateReason(Integer contactID) throws ComplianceException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<String> reason = new ArrayList<>();
		String data;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_CONTACT_STATUS_UPDATE_REASON.getQuery());
			preparedStatement.setInt(1, contactID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				data = resultSet.getString(REASON);
				reason.add(data);
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return reason;

	}
	
	/**
	 * Gets the account contac details for intuition.
	 *
	 * @param tradeAccountNumber the trade account number
	 * @return the account contac details for intuition
	 * @throws ComplianceException the compliance exception
	 */
	public RegistrationServiceRequest getAccountContacDetailsForIntuition(String tradeAccountNumber)
			throws ComplianceException {
		Connection conn = null;
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		RegistrationServiceRequest regReqTM = new RegistrationServiceRequest();
		
		try {
			conn = getConnection(Boolean.FALSE);
			preparedStatment = conn.prepareStatement(DBQueryConstant.GET_ACCOUNT_CONTACT_DETAILS_FOR_INTUITION.getQuery());

			preparedStatment.setString(1, tradeAccountNumber);
			
			rs = preparedStatment.executeQuery();
			int count = 1;
			String json = null;
			Account account = null;
			List<Contact> contacts = new ArrayList<>();
			while (rs.next()) {
				json = rs.getString("accAtt");
				if (count == 1) {
					
					account = JsonConverterUtil.convertToObject(Account.class, json);
					account.setId(rs.getInt("accID"));
					account.setAccountStatus(rs.getString("accComplianceStatus"));
					account.setVersion(rs.getInt("accVersion"));
					account.setCustLegalEntity(rs.getString("LegalEntity"));
					account.setPreviousBlacklistStatus(ServiceStatus.getStatusAsString(rs.getInt("accBlacklistStatus")));
					account.setPreviousFraugsterStatus(ServiceStatus.getStatusAsString(rs.getInt("accFraugsterStatus")));
					account.setPreviousSanctionStatus(ServiceStatus.getStatusAsString(rs.getInt("accSanctionStatus")));
					regReqTM.addAttribute("CreatedOn", rs.getTimestamp("CreatedOn"));//Add for AT-4763
					regReqTM.addAttribute("AccountTMFlag", rs.getInt("AccountTMFlag"));
					regReqTM.addAttribute("IntuitionRiskLevel", rs.getString("IntuitionRiskLevel"));
					regReqTM.addAttribute("OrganizationCode", rs.getString("OrganizationCode"));
					account.setLegacyTradeAccountNumber(rs.getString("LegacyTradeAccountNumber")); //Add for AT-5393
				}

				json = rs.getString("conAtt");
				if (null != json) {
					Contact c = JsonConverterUtil.convertToObject(Contact.class, json);
					c.setId(rs.getInt("conID"));
					c.setContactStatus(rs.getString("conComplianceStatus"));
					c.setPreviousBlacklistStatus(ServiceStatus.getStatusAsString(rs.getInt("conBlacklistStatus")));
					c.setPreviousKycStatus(ServiceStatus.getStatusAsString(rs.getInt("conEIDStatus")));
					c.setPreviousFraugsterStatus(ServiceStatus.getStatusAsString(rs.getInt("conFraugsterStatus")));
					c.setPreviousSanctionStatus(ServiceStatus.getStatusAsString(rs.getInt("conSanctionStatus")));
					c.setPreviousCountryGlobalCheckStatus(ServiceStatus.getStatusAsString(rs.getInt("conCustomCheckStatus")));
					c.setVersion(rs.getInt("version"));
					contacts.add(c);
				}
				count++;
			}
			account.setContacts(contacts);
			regReqTM.setAccount(account);
			
			
		} catch (Exception e) {
			LOG.error("getAccountContacDetailsForIntuition():", e);
		} finally {
			closePrepareStatement(preparedStatment);
			closeConnection(conn);
		}
		return regReqTM;
	}
	
	/**
	 * Gets the fraugster contact status from DB.
	 *
	 * @param contactID the contact ID
	 * @param tmSingupContact the tm singup contact
	 * @return the fraugster contact status from DB
	 * @throws ComplianceException the compliance exception
	 */
	public List<String> getFraugsterContactStatusFromDB(Integer contactID) throws ComplianceException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<String> fraudPredict = new ArrayList<>();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(DBQueryConstant.GET_FRAUGSTER_CONTACT_STATUS_FOR_INTUITION.getQuery());
			preparedStatement.setInt(1, contactID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fraudPredict.add(resultSet.getString("fraugsterApproved"));
				fraudPredict.add(resultSet.getString("UpdatedOn"));
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		
		return fraudPredict;
	}
	
	/**
	 * Gets the compliance log for intuition.
	 *
	 * @param accountID the account ID
	 * @return the compliance log for intuition
	 * @throws ComplianceException the compliance exception
	 */
	public String getComplianceLogForIntuition(Integer accountID) throws ComplianceException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String compLog = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_COMPLIANCE_LOG_FOR_INTUITION.getQuery());
			preparedStatement.setInt(1, accountID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				compLog = resultSet.getString("Comments");
				LOG.warn(
						"\n------Compliance Log after getting from Database table in AbstractDBServiceImpl : {}------\n",
						compLog); //Log added for AT-5404
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return compLog;

	}
	
	/**
	 * Gets the onfido contact status from DB.
	 *
	 * @param contactID the contact ID
	 * @param tmSingupContact the tm singup contact
	 * @return the onfido contact status from DB
	 * @throws ComplianceException the compliance exception
	 */
	public String getOnfidoContactStatusFromDB(Integer contactID) throws ComplianceException {

		Connection connection = null;
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		String onfidoStatus = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DBQueryConstant.GET_ONFIDO_CONTACT_STATUS_FOR_INTUITION.getQuery());
			preparedStatement.setInt(1, contactID);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				onfidoStatus = resultSet.getString("ESL_OnfidoStatus");
			}

		} catch (Exception e) {
			LOG.error("", e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		
		return onfidoStatus;
	}
	
	
}
