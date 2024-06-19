package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import com.currenciesdirect.gtg.compliance.core.domain.BaseUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FragusterWatchListEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FraugsterReasonsEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

/**
 * The Class AbstractDBServiceRegistrationCFX.
 */
public abstract class AbstractDBServiceRegistrationCFX extends AbstractDBServiceLevel2 {

	/**
	 * The Class AbstractDBServiceRegistrationCFX.
	 *
	 * @param accountId the account id
	 * @param connection the connection
	 * @param profile the profile
	 * @return the account watchlist with selected
	 * @throws CompliancePortalException the compliance portal exception
	 */

	protected Watchlist getAccountWatchlistWithSelected(Integer accountId, Connection connection, UserProfile profile)
			throws CompliancePortalException {

		Watchlist watchlist = new Watchlist();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlist.setWatchlistData(watchlistDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean category1 = profile.getPermissions().getCanManageWatchListCategory1();
		boolean category2 = profile.getPermissions().getCanManageWatchListCategory2();
		try {
			statement = getPrepareStatementForWatchlist(accountId, connection, category1, category2);
			rs = statement.executeQuery();
			while (rs.next()) {
				WatchListData watchlistData = new WatchListData();
				watchlistData.setName(rs.getString(Constants.WATCHLISTNAME));
				watchlistData.setContactId(rs.getInt(Constants.CONTACTID));
				Integer contactIdDb = rs.getInt(Constants.CONTACTID);
				if (contactIdDb != -1) {
					watchlistData.setValue(Boolean.TRUE);
				} else {
					watchlistData.setValue(Boolean.FALSE);
				}
				watchlistDataList.add(watchlistData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return watchlist;
	}

	/**
	 * Gets the prepare statement for watchlist.
	 *
	 * @param accountId the account id
	 * @param connection the connection
	 * @param category1 the category 1
	 * @param category2 the category 2
	 * @return the prepare statement for watchlist
	 * @throws SQLException the SQL exception
	 */
	@SuppressWarnings("squid:S2095")
	private PreparedStatement getPrepareStatementForWatchlist(Integer accountId, Connection connection,
			boolean category1, boolean category2) throws SQLException {
		PreparedStatement statement;
		// if only one of category is enabled, then add second param to
		// query
		// else no filter needed
		String accountWatchlistQuery;
		int result = 0;
		if (category1 == category2) {
			accountWatchlistQuery = RegistrationQueryConstant.GET_ACCOUNT_WATCHLIST_WITH_SELECTED.getQuery();
		} else {
			accountWatchlistQuery = RegistrationQueryConstant.GET_ACCOUNT_WATCHLIST_CATEGORY_WITH_SELECTED.getQuery();
			if (category1 && !category2) {
				result = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus();

			} else if (!category1 && category2) {
				result = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus();
			}
		}

		statement = connection.prepareStatement(accountWatchlistQuery);

		statement.setInt(1, accountId);
		if ((!category1 && category2) || (category1 && !category2))
			statement.setInt(2, result);
		return statement;
	}

	/**
	 * Save into broadcast queue.
	 *
	 * @param broadcastEventToDB the broadcast event to DB
	 * @param connection the connection
	 * @return the boolean
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected Boolean saveIntoBroadcastQueue(BroadcastEventToDB broadcastEventToDB, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.SAVE_INTO_BROADCAST_QUEUE.getQuery());
			preparedStatement.setString(1, broadcastEventToDB.getOrgCode());
			preparedStatement.setInt(2,
					BroadCastEntityTypeEnum.getBraodCastEntityTypeAsInteger(broadcastEventToDB.getEntityType()));
			preparedStatement.setInt(3, broadcastEventToDB.getAccountId());
			if (broadcastEventToDB.getContactId() != null) {
				preparedStatement.setInt(4, broadcastEventToDB.getContactId());
			} else {
				preparedStatement.setNull(4, Types.INTEGER);
			}
			if (broadcastEventToDB.getPaymentInId() != null) {
				preparedStatement.setInt(5, broadcastEventToDB.getPaymentInId());
			} else {
				preparedStatement.setNull(5, Types.INTEGER);
			}
			if (broadcastEventToDB.getPaymentOutId() != null) {
				preparedStatement.setInt(6, broadcastEventToDB.getPaymentOutId());
			} else {
				preparedStatement.setNull(6, Types.INTEGER);
			}
			preparedStatement.setString(7, broadcastEventToDB.getStatusJson());
			preparedStatement.setInt(8,
					BroadCastStatusEnum.getBraodCastStatusAsInteger(broadcastEventToDB.getDeliveryStatus()));
			preparedStatement.setTimestamp(9, broadcastEventToDB.getDeliverOn());
			preparedStatement.setString(10, broadcastEventToDB.getCreatedBy());
			preparedStatement.setTimestamp(11, broadcastEventToDB.getCreatedOn());
			int count = preparedStatement.executeUpdate();
			boolean result = true;
			if (count == 0) {
				result = false;
			}
			return result;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_SAVING_DATA_INTO_BROADCAST_QUEUE, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * Gets the account attributes.
	 *
	 * @param accountId the account id
	 * @param connection the connection
	 * @return the account attributes
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("squid:S2095")
	protected RegistrationAccount getAccountAttributes(Integer accountId, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_ACC_ATTRIBUTE.getQuery());
			preparedStatement.setInt(1, accountId);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				return JsonConverterUtil.convertToObject(RegistrationAccount.class, rs.getString(Constants.ATTRIBUTES));
			}
			rs.close();
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
		return null;
	}

	/**
	 * Insert Profile Activity Logs.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	@SuppressWarnings("resource")
	public void insertProfileActivityLogs(List<ProfileActivityLogDto> activityLogDtos, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(
					RegistrationQueryConstant.INSERT_PROFILE_ACTIVITY_LOG.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (ProfileActivityLogDto activityLog : activityLogDtos) {
				preparedStatement.setTimestamp(1, activityLog.getTimeStatmp());
				preparedStatement.setNString(2, activityLog.getActivityBy());
				preparedStatement.setNString(3, activityLog.getOrgCode());
				preparedStatement.setInt(4, activityLog.getAccountId());
				if (null == activityLog.getContactId()) {
					preparedStatement.setNull(5, Types.INTEGER);
				} else {
					preparedStatement.setInt(5, activityLog.getContactId());
				}
				preparedStatement.setNString(6, activityLog.getComment());
				preparedStatement.setNString(7, activityLog.getCreatedBy());
				preparedStatement.setTimestamp(8, activityLog.getCreatedOn());
				preparedStatement.setNString(9, activityLog.getUpdatedBy());
				preparedStatement.setTimestamp(10, activityLog.getUpdatedOn());
				// AT-894 WorkEfficiencyReport
				// Add ResouceType and ResourceID
				if (null == activityLog.getContactId()) {
					preparedStatement.setInt(11, UserLockResourceTypeEnum.ACCOUNT.getDatabaseStatus());
					preparedStatement.setInt(12, activityLog.getAccountId());
				} else {
					preparedStatement.setInt(11, UserLockResourceTypeEnum.CONTACT.getDatabaseStatus());
					preparedStatement.setInt(12, activityLog.getContactId());
				}

				int updateCount = preparedStatement.executeUpdate();
				if (updateCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					activityLog.setId(rs.getInt(1));
					activityLog.getActivityLogDetailDto().setActivityId(rs.getInt(1));
				}
			}

		} catch (CompliancePortalException e) {
			throw e;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * Insert Profile Activity Log Detail.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	public void insertProfileActivityLogDetail(List<ProfileActivityLogDto> activityLogDtos, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.INSERT_PROFILE_ACTIVITY_LOG_DETAIL.getQuery());
			for (ProfileActivityLogDto activityLog : activityLogDtos) {
				ActivityLogDetailDto activityLogDetailDto = activityLog.getActivityLogDetailDto();
				preparedStatement.setInt(1, activityLogDetailDto.getActivityId());
				preparedStatement.setString(2, activityLogDetailDto.getActivityType().getModule());
				preparedStatement.setString(3, activityLogDetailDto.getActivityType().getType());
				preparedStatement.setNString(4, activityLogDetailDto.getLog());
				preparedStatement.setNString(5, activityLogDetailDto.getCreatedBy());
				preparedStatement.setTimestamp(6, activityLogDetailDto.getCreatedOn());
				preparedStatement.setNString(7, activityLogDetailDto.getUpdatedBy());
				preparedStatement.setTimestamp(8, activityLogDetailDto.getUpdatedOn());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
			}

		} catch (CompliancePortalException e) {
			throw e;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}

	}

	/**
	 * Gets the upload document list.
	 *
	 * @param connection the connection
	 * @return the upload document list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<UploadDocumentTypeDBDto> getUploadDocumentList(Connection connection)
			throws CompliancePortalException {
		List<UploadDocumentTypeDBDto> documentType = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_DOCUMENT_LIST.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				UploadDocumentTypeDBDto uploadDocument = new UploadDocumentTypeDBDto();
				uploadDocument.setId(resultSet.getInt("Id"));
				uploadDocument.setDocumentName(resultSet.getString("Name"));
				documentType.add(uploadDocument);
			}
			return documentType;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * Delete watchlist.
	 *
	 * @param query the query
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void deleteWatchlist(String query, Connection connection) throws CompliancePortalException {
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

	/**
	 * Gets the kyc country list.
	 *
	 * @param connection the connection
	 * @return the kyc country list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getKycCountryList(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> kycCountryList = new ArrayList<>();

		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_KYC_SUPPORTED_COUNTRY_LIST.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				kycCountryList.add(resultSet.getString("Code"));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return kycCountryList;
	}

	/**
	 * Update work flow time.
	 *
	 * @param resourceId the resource id
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void updateWorkFlowTime(Integer resourceId, Connection connection) throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_WORKFLOW_TIME.getQuery());
			preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(2, resourceId);
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

	/**
	 * Update watchlist status.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void updateWatchlistStatus(BaseUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.UPDATE_WATCHLISTSTATUS_ACCOUNT.getQuery());
			preparedStatement.setString(1, requestHandlerDto.getCreatedBy());
			preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			if (requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus().equals(2)) {
				preparedStatement.setInt(3, ServiceStatusEnum.FAIL.getDatabaseStatus());
			} else if (requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus().equals(7)) {
				preparedStatement.setInt(3, ServiceStatusEnum.WATCH_LIST.getDatabaseStatus());
			} else {
				preparedStatement.setInt(3, ServiceStatusEnum.PASS.getDatabaseStatus());
			}

			if (requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus().equals(2)) {
				preparedStatement.setInt(4, ServiceStatusEnum.FAIL.getDatabaseStatus());
			} else if (requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus().equals(7)) {
				preparedStatement.setInt(4, ServiceStatusEnum.WATCH_LIST.getDatabaseStatus());
			} else {
				preparedStatement.setInt(4, ServiceStatusEnum.PASS.getDatabaseStatus());
			}
			preparedStatement.setInt(5, requestHandlerDto.getAccountId());
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

	/**
	 * Gets the watch list status.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param readOnlyConn the read only conn
	 * @return the watch list status
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void getWatchListStatus(BaseUpdateDBDto requestHandlerDto, Connection readOnlyConn)
			throws CompliancePortalException {

		List<WatchListDetails> watchListPaymentReasons = new ArrayList<>();
		List<String> toatalContactWatchListReason = new ArrayList<>();
		List<String> finalWatchList = new ArrayList<>();
		try {
			requestHandlerDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.PASS);
			requestHandlerDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.PASS);
			getPaymentWatchListReasons(readOnlyConn, watchListPaymentReasons);
			getTotalContactWatchListReasons(readOnlyConn, toatalContactWatchListReason,
					requestHandlerDto); //For AT-2986
			List<String> delWatchlist = requestHandlerDto.getDeletedWatchlist();
			List<String> addWatchlist = requestHandlerDto.getAddWatchlist();

			if (!toatalContactWatchListReason.isEmpty()) {
				finalWatchList.addAll(toatalContactWatchListReason);
			}

			removeDeletedReasonFromWatchList(toatalContactWatchListReason, delWatchlist, finalWatchList);
			addReasonToWatchList(finalWatchList, addWatchlist);
			getFinalWatchListStatus(requestHandlerDto, watchListPaymentReasons, finalWatchList);

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}

	}

	/**
	 * Gets the alert compliance log.
	 *
	 * @param accountId the account id
	 * @return the alert compliance log
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected String getAlertComplianceLog(Integer accountId) throws CompliancePortalException {
		String alertComplianceLog = Constants.DASH_DETAILS_PAGE;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		Connection connection = null;

		try {
			connection = getConnection(Boolean.TRUE);
			prepareStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_ALERT_COMPLIANCE_LOG.getQuery());
			prepareStatement.setInt(1, accountId);
			resultSet = prepareStatement.executeQuery();
			while (resultSet.next()) {
				alertComplianceLog = resultSet.getString("Comments");
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}

		return alertComplianceLog;
	}

	/**
	 * Sets the fraugster data.
	 *
	 * @param baseUpdateDBDto            the base update DB dto
	 * @param conn the conn
	 * @param readOnlyConn            the read only conn
	 * @param status the status
	 * @throws CompliancePortalException             the compliance portal exception
	 * 
	 *             This method is called to check whether a fraugster watchlist
	 *             is added or deleted or fraugster reason is added or deleted.
	 *             This method then return providerresponse on fraugster
	 *             eventservicelogId If fraugsterSummary or
	 *             fraugstertransactionId or Id is not null then data is
	 *             inserted in FraugsterSchedularData table
	 */

	protected void setFraugsterDataByAccountId(BaseUpdateDBDto baseUpdateDBDto, Connection conn,
			Connection readOnlyConn, String status) throws CompliancePortalException {

		String asyncStatus;
		try {

			Boolean isWatchListAdded = isWatchListAdded(baseUpdateDBDto);
			Boolean isWatchListRemoved = isWatchListRemoved(baseUpdateDBDto);

			if (Constants.ACTIVE.equalsIgnoreCase(status)) {
				if (Constants.CUST_TYPE_PFX.equalsIgnoreCase(baseUpdateDBDto.getCustType())) {

					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByContactId(readOnlyConn,
							baseUpdateDBDto.getContactId());
					updateFraugsterSchedularDataOnReasonAdded(baseUpdateDBDto, null, conn, summaryList);

				} else {
					/**
					 * Updated all contact for cfx record
					 */
					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByAccountId(readOnlyConn,
							baseUpdateDBDto.getAccountId());
					updateFraugsterSchedularDataOnWLAdded(baseUpdateDBDto, null, conn, summaryList);
				}

			} else {

				if (Boolean.TRUE.equals(isWatchListAdded)) {

					asyncStatus = FragusterWatchListEnum.FRAUGSTER_HIGH_RISK_OF_FRAUD.getWatchlistCode();
					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByAccountId(readOnlyConn,
							baseUpdateDBDto.getAccountId());
					updateFraugsterSchedularDataOnWLAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);

				} else if (Boolean.TRUE.equals(isWatchListRemoved)) {
					asyncStatus = FragusterWatchListEnum.APPROVED.getWatchlistCode();
					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByAccountId(readOnlyConn,
							baseUpdateDBDto.getAccountId());
					updateFraugsterSchedularDataOnWLAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);
				}

				if (Boolean.FALSE.equals(isWatchListAdded) && Boolean.FALSE.equals(isWatchListRemoved)) {
					setAsyncStatusForRegReason(baseUpdateDBDto, conn, readOnlyConn);
				}
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}
	}

	/**
	 * Sets the async status for reg reason.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @param conn the conn
	 * @param readOnlyConn the read only conn
	 * @return the string
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private String setAsyncStatusForRegReason(BaseUpdateDBDto baseUpdateDBDto, Connection conn, Connection readOnlyConn)
			throws CompliancePortalException {

		Boolean isReasonAdded = Boolean.FALSE;
		Boolean isReasonRemoved = Boolean.FALSE;
		String asyncStatus = FragusterWatchListEnum.APPROVED.getWatchlistCode();

		try {
			isReasonAdded = isReasonAddded(baseUpdateDBDto);
			isReasonRemoved = isReasonRemoved(baseUpdateDBDto);

			if (Boolean.TRUE.equals(isReasonAdded)) {
				for (String reason : baseUpdateDBDto.getAddReasons()) {
					asyncStatus = FraugsterReasonsEnum.getFraugsterReasonCode(reason);
				}
				if (Constants.CUST_TYPE_PFX.equalsIgnoreCase(baseUpdateDBDto.getCustType())) {

					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByContactId(readOnlyConn,
							baseUpdateDBDto.getContactId());
					updateFraugsterSchedularDataOnReasonAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);
				} else {
					/**
					 * Updated all contact for cfx record
					 */
					List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByAccountId(readOnlyConn,
							baseUpdateDBDto.getAccountId());
					updateFraugsterSchedularDataOnWLAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);
				}

			} else if (Boolean.TRUE.equals(isReasonRemoved)) {
				asyncStatus = FraugsterReasonsEnum.APPROVED.getReasonCode();
				List<FraugsterSummary> summaryList = getFraugsterEventServiceLogByContactId(readOnlyConn,
						baseUpdateDBDto.getContactId());
				updateFraugsterSchedularDataOnReasonAdded(baseUpdateDBDto, asyncStatus, conn, summaryList);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}

		return asyncStatus;
	}
}
