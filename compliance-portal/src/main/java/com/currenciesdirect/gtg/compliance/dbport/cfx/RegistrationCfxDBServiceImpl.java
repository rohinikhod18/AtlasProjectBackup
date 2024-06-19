/*
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport.cfx;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.commons.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.core.ICfxDBService;
import com.currenciesdirect.gtg.compliance.core.IRegistrationDBService;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceStatus;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCfxDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationCfxDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceRegistrationCFX;
import com.currenciesdirect.gtg.compliance.dbport.ClauseType;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.RegistrationQueryConstant;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.EntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class RegistrationCfxDBServiceImpl.
 */
@Component("regCfxDBServiceImpl")
public class RegistrationCfxDBServiceImpl extends AbstractDBServiceRegistrationCFX implements IRegistrationDBService, ICfxDBService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationCfxDBServiceImpl.class);
	
	private static final String CRM_CONTACT_ID = "CRMContactID";
	private static final String REASON = "Reason";

	@Autowired
	@Qualifier("registrationCfxDetailsTransformer")
	private ITransform<RegistrationCfxDetailsDto, RegistrationCfxDetailsDBDto> regDetailsTransformer;

	@Autowired
	@Qualifier("regActivityLogTransformer")
	private ITransform<ActivityLogs, RegUpdateDBDto> regActivityLogTransformer;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.cfx.core.IRegistrationCfxDBService#
	 * getRegistrationCfxDetails(java.lang.Integer)
	 * 
	 * 1. execute query to get registrationCfxDetails. 2. table used:
	 * dbo.AccountAttribute, dbo.ContactAttribute, dbo.EventServiceLog,
	 * dbo.ContactComplianceStatus, dbo.AccountComplianceStatus,
	 * dbo.ContactComplianceStatusEnum, dbo.AccountComplianceStatusEnum,
	 * dbo.UserResourceLock, dbo.Organization, dbo.Compliance_ServiceTypeEnum 3.
	 * get watch list details from dbo.Watchlist dbo.ContactWatchList 4. get
	 * ActivityLog details from dbo.ProfileActivityLog,
	 * dbo.ProfileActivityLogDetail and dbo.ActivityTypeEnum 5. get Status
	 * reason from dbo.StatusUpdateReason and dbo.ProfileStatusReason . 6.. take
	 * list of otherContacts including kyc, sanction, blacklist. 7. transform
	 * result set object to RegistrationCfxDetailsDto. 8. return
	 * RegistrationCfxDetailsDto object.
	 */
	@Override
	public IRegistrationDetails getRegistrationDetails(Integer contactId, RegistrationSearchCriteria searchCriteria)
			throws CompliancePortalException {
		List<RegistrationDetailsDBDto> regDetailDtoList = new ArrayList<>();
		RegistrationCfxDetailsDBDto cfxDetailsDBDto = new RegistrationCfxDetailsDBDto();

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer accountId = null;

		try {
			connection = getConnection(Boolean.TRUE);
			//AT-2248 - Modification for high query IOPS
			regDetailDtoList = getRegCFXAccountContactDetails(connection,cfxDetailsDBDto,contactId);
			accountId = regDetailDtoList.get(0).getAccountId();
			regDetailDtoList = getCFXDashboardEventDetails(connection,regDetailDtoList,accountId);
			cfxDetailsDBDto.setRegCfxDetailsDBDto(regDetailDtoList);
			cfxDetailsDBDto.setDocumentTypeList(getUploadDocumentList(connection));
			cfxDetailsDBDto.setWatachList(getAccountWatchlistWithSelected(accountId, connection,searchCriteria.getFilter().getUserProfile()));
			cfxDetailsDBDto.setActivityLogs(getActivityLogsByAccount(accountId, connection));
			cfxDetailsDBDto.setContactStatusReason(getContactStatusReasonsByAccountId(accountId, connection));
			if(null != accountId){
				cfxDetailsDBDto.setAlertComplianceLog(getAlertComplianceLog(accountId));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);

		}
		return regDetailsTransformer.transform(cfxDetailsDBDto);
	}

	private void checkAndSetContactId(Integer contactId, ResultSet rs, RegistrationDetailsDBDto detailDto)
			throws SQLException {
		if (contactId.equals(rs.getInt("ID"))) {
			detailDto.setCcCrmContactId(rs.getString(CRM_CONTACT_ID));
		}
	}

	private void checkAndSetLockedBy(ResultSet rs, String locedkBy, RegistrationDetailsDBDto detailDto)
			throws SQLException {
		if (null != locedkBy && !locedkBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			detailDto.setLockedBy(rs.getString("LockedBy"));
		}
	}

	@Override
	public LockResourceResponse insertLockResourceForMultiContact(LockResourceDBDto lockResourceDBDto)
			throws CompliancePortalException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		LockResourceResponse lockResourceResponse = null;
		try {
			connection = getConnection(Boolean.FALSE);

			lockResourceResponse = checkLockResourceByAccountId(lockResourceDBDto.getResourceId(),
					lockResourceDBDto.getResourceType(), connection);
			if (lockResourceResponse != null) {
				return lockResourceResponse;
			}
			lockResourceResponse = new LockResourceResponse();

			preparedStatement = connection.prepareStatement(
					RegistrationCfxDBQueryConstant.INSERT_USER_RESOURCE_LOCK_BY_ACCOUNT_ID.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, lockResourceDBDto.getUserId());
			preparedStatement.setInt(2, UserLockResourceTypeEnum.getDbStatusFromUiStatus(lockResourceDBDto.getResourceType()));
			preparedStatement.setInt(3, lockResourceDBDto.getResourceId());
			preparedStatement.setTimestamp(4, lockResourceDBDto.getLockReleasedOn());
			preparedStatement.setString(5, lockResourceDBDto.getCreatedBy());
			preparedStatement.setTimestamp(6, lockResourceDBDto.getCreatedOn());
			preparedStatement.setTimestamp(7, null);
			int updateCount = preparedStatement.executeUpdate();
			if (updateCount == 0) {
				lockResourceResponse.setStatus("Fail");
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
			rs = preparedStatement.getGeneratedKeys();
			if (rs.next()) {
				lockResourceResponse.setUserResourceId(rs.getInt(1));
				lockResourceResponse.setStatus("Pass");
				lockResourceResponse.setResourceId(lockResourceDBDto.getResourceId());
				lockResourceResponse.setName(lockResourceDBDto.getUserId());
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return lockResourceResponse;

	}

	@Override
	public LockResourceResponse updateLockResourceForMultiContact(LockResourceDBDto lockResourceDBDto)
			throws CompliancePortalException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		LockResourceResponse lockResourceResponse = new LockResourceResponse();
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(
					RegistrationCfxDBQueryConstant.UPDATE_USER_RESOURCE_LOCK_BY_ACCOUNT_ID.getQuery());
			preparedStatement.setTimestamp(1, lockResourceDBDto.getLockReleasedOn());
			preparedStatement.setInt(2, lockResourceDBDto.getResourceId());
			int updateCount = preparedStatement.executeUpdate();
			if (updateCount == 0) {
				lockResourceResponse.setStatus("Fail");
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			} else {
				lockResourceResponse.setStatus("Pass");
				lockResourceResponse.setUserResourceId(lockResourceDBDto.getId());
				lockResourceResponse.setResourceId(lockResourceDBDto.getResourceId());
				lockResourceResponse.setName(lockResourceDBDto.getUserId());
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return lockResourceResponse;

	}

	private LockResourceResponse checkLockResourceByAccountId(Integer resourceId, String resourceType,
			Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.GET_RECORDLOCK_STATUS_BY_ACCOUNT_ID.getQuery());
			preparedStatement.setInt(1, resourceId);
			preparedStatement.setInt(2,  UserLockResourceTypeEnum.getDbStatusFromUiStatus(resourceType));
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				LockResourceResponse lockResourceDBDto = new LockResourceResponse();
				lockResourceDBDto.setName(resultSet.getString("UserID"));
				lockResourceDBDto.setUserResourceId(resultSet.getInt("Id"));
				return lockResourceDBDto;
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return null;
	}

	private ActivityLogs getActivityLogsByAccount(Integer accountID, Connection connection)
			throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.GET_ACTIVITY_LOGS_OF_ACCOUNT_ID.getQuery());
			statement.setInt(1, accountID);
			rs = statement.executeQuery();
			while (rs.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setActivityType(ActivityType.getActivityLogDisplay(rs.getString("ActivityType")));
				activityLogData.setCreatedBy(rs.getString("User"));
				Timestamp createdOn = rs.getTimestamp("CreatedOn");
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setActivity(rs.getString("Activity"));
				activityLogData.setComment(rs.getString("Comment"));
				activityLogDataList.add(activityLogData);
				
			}

			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closePrepareStatement(statement);
			closeResultset(rs);
		}
		return activityLogs;
	}

	private StatusReason getContactStatusReasonsByAccountId(Integer accountId, Connection connection)
			throws CompliancePortalException {

		StatusReason contactStatusReason = new StatusReason();
		List<StatusReasonData> contactStatusReasonList = new ArrayList<>();
		contactStatusReason.setStatusReasonData(contactStatusReasonList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(
					RegistrationCfxDBQueryConstant.GET_PROFILE_STATUS_REASON_BY_ACCOUNT_ID.getQuery());
			statement.setInt(1, accountId);
			rs = statement.executeQuery();
			while (rs.next()) {
				StatusReasonData contactStatusReasonData = new StatusReasonData();
				contactStatusReasonData.setName(rs.getString(REASON));
				Integer contactIdDb = rs.getInt("ContactId");
				if (contactIdDb != -1) {
					contactStatusReasonData.setValue(Boolean.TRUE);
				} else {
					contactStatusReasonData.setValue(Boolean.FALSE);
				}
				contactStatusReasonList.add(contactStatusReasonData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return contactStatusReason;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IRegistrationDBService#updateContact(com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto)
	 */
	@Override
	public ActivityLogs updateContact(RegUpdateDBDto requestHandlerDto) throws CompliancePortalException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer tradeAccountId = null;
		Connection readOnlyConn = null;
		String registrationStatusReason =null;
		try {
			connection = getConnection(Boolean.FALSE);
			readOnlyConn = getConnection(Boolean.TRUE);
			
			preparedStatement = readOnlyConn
					.prepareStatement(RegistrationCfxDBQueryConstant.GET_ACCOUNT_CONTACT_IDS_BY_ACCCOUNID.getQuery());
			preparedStatement.setInt(1, requestHandlerDto.getAccountId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				tradeAccountId = resultSet.getInt("TradeAccountID");
			}
			beginTransaction(connection);
			deleteWatchlist(requestHandlerDto, connection);
			addWatchlist(requestHandlerDto, connection);
			/**added getWatchListStatus() to update watch list status for paymentout and paymentin */
			getWatchListStatus(requestHandlerDto,readOnlyConn);
			updateWatchlistStatus(requestHandlerDto,connection);
			deleteReasons(requestHandlerDto, connection);
			addReasons(requestHandlerDto, connection);
			addActivityLogs(requestHandlerDto, connection);

			String accountStatus = requestHandlerDto.getAccountStatus();
			/**Added ResponseStatus*/
			if (requestHandlerDto.getAddReasons() != null && !(requestHandlerDto.getAddReasons().isEmpty())) {
				registrationStatusReason=requestHandlerDto.getAddReasons().toString().replace("[", "").replace("]", "");
			}
			
			if (accountStatus != null && !accountStatus.isEmpty()) {
				ComplianceStatus complianceStatus = getAccountStatus(accountStatus);
				updateAccountStatus(accountStatus, requestHandlerDto, connection);

				updateContactStatus(accountStatus, connection, requestHandlerDto);
				RegistrationAccount account = getAccountAttributes(requestHandlerDto.getAccountId(), connection);
				if(account == null){
					throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
				}
				account.setAccountStatus(accountStatus);
				//Previously updated status from UI were updated in AccountAttribute table so that compliance service side can fetch the updated value.
			    //But Later on improvement compliance service started fetching value from Account table.So no need to update AccountAttribute from UI. : Laxmi B.
				//Only account status will be updated ,no contact status is updated for CFX as per requirement :  Laxmi B.
				
				if(accountStatus.equalsIgnoreCase(ComplianceStatus.ACTIVE.name()) || accountStatus.equalsIgnoreCase(ComplianceStatus.REJECTED.name()) ){
					updateWorkFlowTime(requestHandlerDto.getUserResourceId(),connection);	
				}
				/**
				 * Contact list has been sent in registration response creation as per JIRA AT-1576
				 * */
				saveIntoBroadcastQueue(getBroadCastDBObject(requestHandlerDto.getAccountId(),requestHandlerDto.getCreatedBy(),
	                                   getRegistrationResponse(complianceStatus,requestHandlerDto.getAccountSfId(),requestHandlerDto.getOrgCode(),tradeAccountId,registrationStatusReason,
								requestHandlerDto.getRegistrationInDate(),requestHandlerDto.getComplianceDoneOn()),
						requestHandlerDto.getOrgCode()), connection);
			}else if(Boolean.FALSE.equals(StringUtils.isNullOrEmpty(requestHandlerDto.getComment())) 
					&& Boolean.TRUE.equals(requestHandlerDto.getIsRequestFromQueue())){
				updateAccountIsOnQueueStatus(requestHandlerDto, connection);
			}
			commitTransaction(connection);

		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(readOnlyConn);
			closeConnection(connection);
		}
		return regActivityLogTransformer.transform(requestHandlerDto);

	}

	/**
	 * Update contact status.
	 *
	 * @param accountStatus the account status
	 * @param connection the connection
	 * @param requestHandlerDto the request handler dto
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void updateContactStatus(String accountStatus, Connection connection, RegUpdateDBDto requestHandlerDto) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationCfxDBQueryConstant.UPDATE_CONTACT_STATUS.getQuery());
			preparedStatement.setString(1, accountStatus);
			preparedStatement.setInt(2, requestHandlerDto.getAccountId());
			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * Update account is on queue status.
	 * updateAccountIsOnQueueStatus() updates isOnQueue column for CFX records
	 * @param requestHandlerDto the request handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 * @author abhijeetg
	 */
	private void updateAccountIsOnQueueStatus(RegUpdateDBDto requestHandlerDto, Connection connection) throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationCfxDBQueryConstant.UPDATE_ACCOUNT_IS_ON_QUEUE_STATUS.getQuery());
			preparedStatement.setString(1, requestHandlerDto.getCreatedBy());
			preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setBoolean(3, requestHandlerDto.getIsAccountOnQueue());
			preparedStatement.setInt(4, requestHandlerDto.getAccountId());
			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	private void updateAccountStatus(String status,RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		Boolean accountStatusActive=Boolean.FALSE;
		try {
			String complianceDoneOn =requestHandlerDto.getComplianceDoneOn();
			String complianceExpiry = requestHandlerDto.getComplianceExpiry();
			preparedStatement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.UPDATE_ACCOUNT_STATUS.getQuery());
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, status);
			accountStatusActive = status.equals(ComplianceStatus.ACTIVE.name());
			if(Boolean.TRUE.equals(accountStatusActive) 
					&& (null == complianceDoneOn || Constants.DASH_UI.equals(complianceDoneOn))){
				preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				Calendar c = Calendar.getInstance();
				c.setTime(new Timestamp(System.currentTimeMillis()));
				c.add(Calendar.YEAR, SearchCriteriaUtil.getComplianceExpiryYears());
				preparedStatement.setTimestamp(4, new Timestamp(c.getTimeInMillis()));
				requestHandlerDto.setComplianceDoneOn(new Timestamp(System.currentTimeMillis()).toString());
				requestHandlerDto.setComplianceExpiry(new Timestamp(c.getTimeInMillis()).toString());
			}else {
				Timestamp complianceDoneOnTimestamp =  DateTimeFormatter.getTimestamp(complianceDoneOn);
				Timestamp complianceExpiryTimestamp =  DateTimeFormatter.getTimestamp(complianceExpiry);
				if(null == complianceDoneOn || Constants.DASH_UI.equals(complianceDoneOn)) {
					preparedStatement.setNull(3, Types.TIMESTAMP);
					preparedStatement.setNull(4, Types.TIMESTAMP);
					requestHandlerDto.setComplianceDoneOn(null);
					requestHandlerDto.setComplianceExpiry(null);
				}else {
					preparedStatement.setTimestamp(3,complianceDoneOnTimestamp);
					preparedStatement.setTimestamp(4, complianceExpiryTimestamp);
					requestHandlerDto.setComplianceDoneOn(complianceDoneOnTimestamp.toString());
					requestHandlerDto.setComplianceExpiry(complianceExpiryTimestamp.toString());
				}
			}
			
			preparedStatement.setBoolean(5, requestHandlerDto.getIsAccountOnQueue());
			preparedStatement.setInt(6, requestHandlerDto.getAccountId());
		
			
			int count = preparedStatement.executeUpdate();
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}

	}
	
	private void addActivityLogs(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		List<ProfileActivityLogDto> activityLogs = requestHandlerDto.getActivityLog();
		if (activityLogs != null && !activityLogs.isEmpty()) {
			insertProfileActivityLogs(activityLogs, connection);
			insertProfileActivityLogDetail(activityLogs, connection);
		}
	}

	private void addReasons(RegUpdateDBDto requestHandlerDto, Connection connection) throws CompliancePortalException {
		List<String> addReasons = requestHandlerDto.getAddReasons();
		if (addReasons != null && !addReasons.isEmpty()) {
			insertContactReason(requestHandlerDto, connection);
		}
	}

	@SuppressWarnings("resource")
	private void insertContactReason(RegUpdateDBDto regReqHandlerDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.INSERT_REASON_ACCOUNT_ID.getQuery());
			for (String addReasonsStr : regReqHandlerDto.getAddReasons()) {
				preparedStatement.setString(1, regReqHandlerDto.getOrgCode());
				preparedStatement.setInt(2, regReqHandlerDto.getAccountId());
				preparedStatement.setString(3, addReasonsStr);
				preparedStatement.setString(4, regReqHandlerDto.getCreatedBy());
				preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setString(6, regReqHandlerDto.getCreatedBy());
				preparedStatement.setTimestamp(7, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setInt(8, regReqHandlerDto.getAccountId());
				int addRowCnt = preparedStatement.executeUpdate();
				if (addRowCnt == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	private void deleteReasons(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		Integer accountId = requestHandlerDto.getAccountId();

		List<String> delReasons = requestHandlerDto.getDeletedReasons();
		if (delReasons != null && !delReasons.isEmpty()) {
			String[] delReasonsArray = delReasons.toArray(new String[delReasons.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_STATUS_REASON_ID.getQuery());
			String query = builder.addCriteria("Module", new String[] { "CONTACT", "ALL" }, "AND", ClauseType.IN)
					.addCriteria(REASON, delReasonsArray, "AND", ClauseType.IN).build();
			query = "DELETE [ProfileStatusReason] WHERE ContactID IN (SELECT ID FROM Contact WHERE AccountId = "
					+ accountId + ") AND StatusUpdateReasonID IN (" + query + ")";
			deleteContactReason(query, connection);
		}
	}

	private void deleteContactReason(String query, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			int delRowCnt = preparedStatement.executeUpdate();
			if (delRowCnt == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_DELETING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	private void addWatchlist(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		List<String> addWatchlist = requestHandlerDto.getAddWatchlist();
		if (addWatchlist != null && !addWatchlist.isEmpty()) {
			insertWatchlist(requestHandlerDto, connection);
		}
	}

	@SuppressWarnings("resource")
	private void insertWatchlist(RegUpdateDBDto regReqHandlerDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.INSERT_WATCHLIST_BY_ACCOUNT_ID.getQuery());
			for (String addWatchlistStr : regReqHandlerDto.getAddWatchlist()) {
				preparedStatement.setString(1, addWatchlistStr);
				preparedStatement.setString(2, regReqHandlerDto.getCreatedBy());
				preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setInt(4, regReqHandlerDto.getAccountId());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	private void deleteWatchlist(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		Integer accountId = requestHandlerDto.getAccountId();

		List<String> delWatchlist = requestHandlerDto.getDeletedWatchlist();

		if (delWatchlist != null && !delWatchlist.isEmpty()) {
			String[] delwatchlistArray = delWatchlist.toArray(new String[delWatchlist.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_WATCHLIST_REASON.getQuery());
			String query = builder.addCriteria(REASON, delwatchlistArray, "AND", ClauseType.IN).build();
			query = "DELETE ContactWatchList WHERE Contact IN (SELECT ID FROM CONTACT WHERE AccountId=" + accountId
					+ ") AND Reason IN(" + query + ")";
			deleteWatchlist(query, connection);
		}
	}

	private BroadcastEventToDB getBroadCastDBObject(Integer accountId, String createdBy,
			String statusJson, String orgCode) {
		BroadcastEventToDB db = new BroadcastEventToDB();
		db.setAccountId(accountId);
		db.setCreatedBy(createdBy);
		db.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliverOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveryStatus(BroadCastStatusEnum.NEW.getBroadCastStatusAsString());
		db.setEntityType(BroadCastEntityTypeEnum.UPDATE.getBroadCastEntityTypeAsString());
		db.setOrgCode(orgCode);
		db.setStatusJson(statusJson);
		return db;
	}

	private String getRegistrationResponse(ComplianceStatus accountStatus, String accSfId, String orgCode, Integer tradeAccountId,
			String registrationStatusReason, String registrationInDate, String complianceDoneOn) {
		RegistrationResponse response = new RegistrationResponse();		
		response.setOsrID(UUID.randomUUID().toString());
		ComplianceAccount account = new ComplianceAccount();
		account.setAccountSFID(accSfId);

		account.setAcs(accountStatus);
		/** Added Response Code and Response Description for manual activation : issue AT-461 - Abhijit G */
		account.setResponseCode(Constants.REGISTRATION_RESPONE_CODE);
		account.setResponseDescription(Constants.REGISTRATION_RESPONE_DESCRIPTION);
		account.setTradeAccountID(tradeAccountId);
		account.setCustType(CustomerTypeEnum.CFX.getCustTypeAsString());
		response.setAccount(account);
		response.setOrgCode(orgCode);
		/**Added statusReason for save into BroadCastQueue*/
		account.setReasonForInactive(registrationStatusReason);
		account.setRegistrationInDate(DateTimeFormatter.removeMillisFromTimeStamp(registrationInDate));
		account.setRegisteredDate(DateTimeFormatter.removeMillisFromTimeStamp(complianceDoneOn));
		return JsonConverterUtil.convertToJsonWithNull(response);

	}

	private ComplianceStatus getAccountStatus(String accountStatus) {

		if (accountStatus.equals(ComplianceStatus.ACTIVE.name())) {
			return ComplianceStatus.ACTIVE;
		} else if (accountStatus.equals(ComplianceStatus.INACTIVE.name())) {
			return ComplianceStatus.INACTIVE;
		} else if (accountStatus.equals(ComplianceStatus.REJECTED.name())) {
			return ComplianceStatus.REJECTED;
		}
		return null;
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDataList);
		PreparedStatement statement = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.GET_CFX_PROFILE_ACTIVITY_LOGS_BY_ROWS.getQuery());
			statement.setInt(1, request.getEntityId());
			statement.setInt(2, request.getMinRecord());
			statement.setInt(3, request.getMaxRecord());
			rs = statement.executeQuery();
			while (rs.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setActivity(rs.getString("Activity"));
				activityLogData.setActivityType(ActivityType.getActivityLogDisplay(rs.getString("ActivityType")));
				activityLogData.setCreatedBy(rs.getString("User"));
				Timestamp createdOn = rs.getTimestamp("CreatedOn");
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setComment(rs.getString("Comment"));
				activityLogDataList.add(activityLogData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return activityLogs;

	}

	@Override
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationSearchCriteria searchCriteria)
			throws CompliancePortalException {
		return null;
	}

	@Override
	public ViewMoreResponse getMoreKycDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		return null;
	}

	@Override
	public ViewMoreResponse getMoreSanctionDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		return null;
	}

	@Override
	public ViewMoreResponse getMoreCustomCheckDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		return null;
	}

	@Override
	public ViewMoreResponse getMoreFraugsterDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		return null;
	}
	
	@Override
	public ViewMoreResponse getMoreOnfidoDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		return null;
	}
	
	/**
	 * AT-2248 - Event table high IOPS - (to optimize CFXdashboard details query)
	 * Gets the reg CFX account contact details.
	 *
	 * @param connection the connection
	 * @param cfxDetailsDBDto the cfx details DB dto
	 * @param contactId the contact id
	 * @return the reg CFX account contact details
	 */
	private List<RegistrationDetailsDBDto> getRegCFXAccountContactDetails(Connection connection,RegistrationCfxDetailsDBDto cfxDetailsDBDto,
			Integer contactId){
		List<RegistrationDetailsDBDto> regDetailDtoList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationCfxDBQueryConstant.GET_CFX_DASHBOARD_ACCOUNT_CONTACT_DETAILS.getQuery());
			preparedStatement.setInt(1, contactId);
			rs = preparedStatement.executeQuery();
			String locedkBy = null;
			while(rs.next()) {
				RegistrationDetailsDBDto detailDto = new RegistrationDetailsDBDto();
				detailDto.setAccComplianceStatus(rs.getString("AccountComplianceStatus"));
				detailDto.setConComplianceStatus(rs.getString("ContactComplianceStatus"));
				detailDto.setAccountAttrib(rs.getString("AccountAttrib"));
				detailDto.setContactAttrib(rs.getString("contactAttrib"));
				detailDto.setAccountId(rs.getInt("AccountId"));
				detailDto.setContactId(rs.getInt("ID"));
				detailDto.setTradeAccountId(rs.getString("TradeAccountId"));
				detailDto.setCrmAccountId(rs.getString("CRMAccountID"));
				detailDto.setCrmContactId(rs.getString(CRM_CONTACT_ID));
				detailDto.setContactName(rs.getString("Name"));
				detailDto.setOrgnization(rs.getString("Organization"));
				detailDto.setRegComp(rs.getTimestamp("RegComplete"));
				detailDto.setRegCompAccount(rs.getTimestamp("RegCompleteAcc"));
				detailDto.setRegistrationInDate(rs.getTimestamp("RegistrationInDate"));
				detailDto.setComplianceExpiry(rs.getTimestamp("ComplianceExpiry"));
				detailDto.setRegIn(rs.getTimestamp("RegIn"));
				detailDto.setTradeAccountNum(rs.getString("TradeAccountNumber"));
				detailDto.setTradeContactId(rs.getString("TradeContactID"));
				detailDto.setUserResourceId(rs.getInt("userResourceLockId"));
				detailDto.setCountryOfResidenceFullName(rs.getString("countryFullName"));
				detailDto.setAccountTMFlag(rs.getInt("accountTMFlag"));
				detailDto.setIntuitionRiskLevel(rs.getString("riskLevel")); // Added for AT-4190
				detailDto.setAccountVersion(rs.getInt("AccountVersion"));
				cfxDetailsDBDto.setIsOnQueue(rs.getBoolean("isOnQueue"));
				cfxDetailsDBDto.setDataAnonStatus(rs.getString("dataAnonStatus"));
				cfxDetailsDBDto.setPoiExists(rs.getString("poiExists"));
				locedkBy = rs.getString("LockedBy");
				checkAndSetLockedBy(rs, locedkBy, detailDto);
				detailDto.setLegalEntity(rs.getString("LegalEntity"));
				detailDto.setCurrentContactId(contactId);
				detailDto.setEventDBDto(new HashMap<String, List<EventDBDto>>());
				checkAndSetContactId(contactId, rs, detailDto);
				detailDto.setKycSupportedCountryList(getKycCountryList(connection));
				regDetailDtoList.add(detailDto);
			}
		}catch(Exception e) {
			LOGGER.error("Error in getRegCFXAccountContactDetails : {1}",e);
		}finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}
		return regDetailDtoList;
	}
	
	/**
	 * Added under AT - 2248
	 * Gets the CFX dashboard event details.
	 *
	 * @param connection the connection
	 * @param regCfxDetailsList the reg cfx details list
	 * @param accountId the account id
	 * @return the CFX dashboard event details
	 */
	private List<RegistrationDetailsDBDto> getCFXDashboardEventDetails(Connection connection,List<RegistrationDetailsDBDto> regCfxDetailsList,Integer accountId){
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationCfxDBQueryConstant.GET_CFX_DASHBOARD_EVENTS_DETAILS.getQuery());
			preparedStatement.setInt(1,accountId);
			rs = preparedStatement.executeQuery();
			Map<String, List<EventDBDto>> mm = new HashMap<>();
			while(rs.next()) {
				String serviceName = rs.getString("ServiceName");
				String entityType = EntityTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt("EntityType"));
				String entityId = rs.getString("Entityid");
				String key = entityId + serviceName + entityType;

				List<EventDBDto> eventDBDtoList = mm.get(key);
				if (eventDBDtoList == null) {
					eventDBDtoList = new ArrayList<>();
					mm.put(key, eventDBDtoList);
				}
				EventDBDto eventDBDto = new EventDBDto();
				eventDBDto.setId(rs.getInt("EventServiceLogId"));
				eventDBDto.setUpdatedBy(rs.getString("EventUpdatedBy"));
				eventDBDto.setUpdatedOn(rs.getTimestamp("EventUpdatedOn"));
				eventDBDto.setSummary(rs.getString("EventSummary"));
				eventDBDto.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus( rs.getInt("EventStatus")));
				eventDBDto.setEntityId(entityId);
				eventDBDto.setServiceType(serviceName);
				eventDBDtoList.add(eventDBDto);
			}
			for(RegistrationDetailsDBDto detailDto : regCfxDetailsList) {
				detailDto.setEventDBDto(mm);
			}

		}catch(Exception e) {
			LOGGER.error("Error in getCFXDashboardEventDetails : {1}",e);
		}finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}
		return regCfxDetailsList;
	}

	@Override
	public boolean setPoiExistsFlag(UserProfile user,Contact contact) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = getConnection(Boolean.TRUE);
			//update contact table to set POI Exists Flag
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_CONTACT_POI_EXISTS_FLAG.getQuery());	
			preparedStatement.setString(1, contact.getContactSFID());
		    preparedStatement.executeUpdate();
	} catch (Exception e) {
		transactionRolledBack(connection);
		throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
	} finally {
		closePrepareStatement(preparedStatement);
		closeConnection(connection);
	}
		return true;
	}

	@Override //AT-4114
	public ViewMoreResponse getMoreIntuitionDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		return null;
	}
}