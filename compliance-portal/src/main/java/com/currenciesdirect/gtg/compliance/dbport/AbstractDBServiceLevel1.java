/*
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.core.domain.BaseUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentInDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchData;
import com.currenciesdirect.gtg.compliance.dbport.enums.FragusterWatchListEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FraugsterReasonsEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.dbport.report.WorkEfficiencyReportQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class AbstractDBService.
 */
public abstract class AbstractDBServiceLevel1 extends AbstractDao {

	/** The Constant FRAUGSTER_APPROVED. */
	private static final String FRAUGSTER_APPROVED = "fraugsterApproved";
	
	/** The Constant FRG_TRANS_ID. */
	private static final String FRG_TRANS_ID = "frgTransId";
	
	private static final String EVENT_SERVICE_LOG = "eventServiceLogId";

	/**
	 * Gets the watchlist.
	 *
	 * @param contactId the contact id
	 * @param connection the connection
	 * @return the watchlist
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected Watchlist getWatchlist(Integer contactId, Connection connection) throws CompliancePortalException {

		Watchlist watchlist = new Watchlist();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlist.setWatchlistData(watchlistDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(RegistrationQueryConstant.GET_WATCHLIST_OF_CONTACT.getQuery());
			statement.setInt(1, contactId);
			rs = statement.executeQuery();
			while (rs.next()) {
				WatchListData watchlistData = new WatchListData();
				watchlistData.setName(rs.getString(Constants.WATCHLISTNAME));
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
	 * Gets the organization.
	 *
	 * @param connection the connection
	 * @return the organization
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getOrganization(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> organization = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_ORGANIZATION.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				organization.add(resultSet.getString(RegQueDBColumns.CODE.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return organization;
	}

	/**
	 * Gets the currency.
	 *
	 * @param connection the connection
	 * @return the currency
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getCurrency(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> currency = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_CURRENCY.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				currency.add(resultSet.getString(RegQueDBColumns.CURRENCY.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return currency;
	}

	/**
	 * Gets the all countries.
	 *
	 * @param connection the connection
	 * @return the all countries
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getAllCountries(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> countries = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_COUNTRY_LIST.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				countries.add(resultSet.getString("Country"));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return countries;
	}

	/**
	 * Insert Profile Activity Logs.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
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
	 * Gets the watch list reason id.
	 *
	 * @param connection the connection
	 * @param category1 the category 1
	 * @param category2 the category 2
	 * @return the watch list reason id
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected Watchlist getWatchListReasonId(Connection connection, boolean category1, boolean category2)
			throws CompliancePortalException {

		Watchlist watchlist = new Watchlist();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlist.setWatchlistData(watchlistDataList);
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String result = "0";
		try {
			if (category1 && category2) {
				result = "1,2";
			} else {
				result = getWatchlistCategory(category1, category2, result);
			}

			String temp = WorkEfficiencyReportQueryConstant.GET_WATCHLIST_CATEGORY.getQuery().replace("?", result);
			preparedStatement = connection.prepareStatement(temp);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				WatchListData watchlistData = new WatchListData();
				watchlistData.setName(resultSet.getString(RegQueDBColumns.REASON.getName()));
				watchlistData.setId(resultSet.getInt(RegQueDBColumns.WATCHLISTID.getName()));
				watchlistDataList.add(watchlistData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return watchlist;
	}

	/**
	 * Gets the watchlist category.
	 *
	 * @param category1 the category 1
	 * @param category2 the category 2
	 * @param result the result
	 * @return the watchlist category
	 */
	private String getWatchlistCategory(boolean category1, boolean category2, String result) {
		String watchlistCategory = result;
		if (category1 && !category2) {
			watchlistCategory = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		} else {
			if (!category1 && category2) {
				watchlistCategory = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();
			}
		}
		return watchlistCategory;
	}

	/**
	 * Gets the locked user name.
	 *
	 * @param connection the connection
	 * @return the locked user name
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getLockedUserName(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> owner = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(WorkEfficiencyReportQueryConstant.GET_OWNER.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				owner.add(resultSet.getString(RegQueDBColumns.OWNER.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return owner;
	}

	/**
	 * Gets the payment status.
	 *
	 * @param connection the connection
	 * @return the payment status
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getPaymentStatus(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> status = new ArrayList<>();
		try {
			preparedStatement = connection
					.prepareStatement(WorkEfficiencyReportQueryConstant.GET_PAYMENT_STATUS.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				status.add(resultSet.getString(RegQueDBColumns.STATUS.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return status;
	}

	/**
	 * Gets the final watch list status.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param watchListPaymentReasons the watch list payment reasons
	 * @param finalWatchList the final watch list
	 * @return the final watch list status
	 */
	protected void getFinalWatchListStatus(BaseUpdateDBDto requestHandlerDto,
			List<WatchListDetails> watchListPaymentReasons, List<String> finalWatchList) {

		if (null != finalWatchList && !finalWatchList.isEmpty()) {
			for (String reason : finalWatchList) {
				getFinalWatchListStatus(requestHandlerDto, watchListPaymentReasons, reason);
				if (requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus().equals(2) || requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus().equals(2)) {
					break;
				}
			}
		} else {
			requestHandlerDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.PASS);
			requestHandlerDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.PASS);
		}

	}
	
	protected void getFinalWatchListStatus(BaseUpdateDBDto requestHandlerDto,
			List<WatchListDetails> watchListPaymentReasons, String reason) {
		
		for (WatchListDetails paymentReason : watchListPaymentReasons) {
			setWatchlistStatus(requestHandlerDto, reason, paymentReason);
			if (requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus().equals(2) || requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus().equals(2)) {
				break;
			}
		}
	}

	/**
	 * Sets the watchlist status.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param reason the reason
	 * @param paymentReason the payment reason
	 */
	private void setWatchlistStatus(BaseUpdateDBDto requestHandlerDto, String reason, WatchListDetails paymentReason) {
	
		if(!(requestHandlerDto.getOverallPaymentInWatchlistStatus().getDatabaseStatus()).equals(7)){
			requestHandlerDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.PASS);
		}
		
		if (paymentReason.getName().equalsIgnoreCase(reason) && paymentReason.isStopPaymentIn() 
				&& paymentReason.getWatchlistCategory().equals(1)) {// check for category
			requestHandlerDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.FAIL);
		} else if(paymentReason.getName().equalsIgnoreCase(reason) && paymentReason.isStopPaymentIn() 
				&& paymentReason.getWatchlistCategory().equals(2)){
			requestHandlerDto.setOverallPaymentInWatchlistStatus(ServiceStatusEnum.WATCH_LIST);
		} 
		
		if(!(requestHandlerDto.getOverallPaymentOutWatchlistStatus().getDatabaseStatus()).equals(7)){
			requestHandlerDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.PASS);
		}
		
		if (paymentReason.getName().equalsIgnoreCase(reason) && paymentReason.isStopPaymentOut() 
				&& paymentReason.getWatchlistCategory().equals(1)) {// check for category
			requestHandlerDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.FAIL);
		} else if(paymentReason.getName().equalsIgnoreCase(reason) && paymentReason.isStopPaymentOut() 
				&& paymentReason.getWatchlistCategory().equals(2)){
			requestHandlerDto.setOverallPaymentOutWatchlistStatus(ServiceStatusEnum.WATCH_LIST);
		}
	}

	/**
	 * Adds the reason to watch list.
	 *
	 * @param finalWatchList the final watch list
	 * @param addWatchlist the add watchlist
	 */
	protected void addReasonToWatchList(List<String> finalWatchList, List<String> addWatchlist) {
		if (addWatchlist != null && !addWatchlist.isEmpty()) {

			for (String addReason : addWatchlist) {
				finalWatchList.add(addReason);
			}
		}
	}

	/**
	 * Removes the deleted reason from watch list.
	 *
	 * @param totalWatchList the total watch list
	 * @param delWatchlist the del watchlist
	 * @param finalWatchList the final watch list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void removeDeletedReasonFromWatchList(List<String> totalWatchList, List<String> delWatchlist,
			List<String> finalWatchList) throws CompliancePortalException {
		try {
			if (null != totalWatchList && !totalWatchList.isEmpty() && delWatchlist != null
					&& !delWatchlist.isEmpty()) {

				checkWatchlistToRemove(totalWatchList, delWatchlist, finalWatchList);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		}

	}

	/**
	 * Check watchlist to remove.
	 *
	 * @param totalWatchList the total watch list
	 * @param delWatchlist the del watchlist
	 * @param finalWatchList the final watch list
	 */
	private void checkWatchlistToRemove(List<String> totalWatchList, List<String> delWatchlist,
			List<String> finalWatchList) {
		for (String deleteReason : delWatchlist) {
			for (String reason : totalWatchList) {
				if (deleteReason.equalsIgnoreCase(reason)) {
					finalWatchList.remove(deleteReason);
				}
			}
		}
	}

	/**
	 * Gets the total contact watch list reasons.
	 *
	 * @param readOnlyConn the read only conn
	 * @param toatalContactWatchListReason the toatal contact watch list reason
	 * @param accountId the account id
	 * @return the total contact watch list reasons
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void getTotalContactWatchListReasons(Connection readOnlyConn, List<String> toatalContactWatchListReason,
			BaseUpdateDBDto requestHandlerDto) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			if(requestHandlerDto.getCustType().equals("PFX")) {
				preparedStatement = readOnlyConn
						.prepareStatement(RegistrationQueryConstant.GET_CONTACT_WATCHLIST_FOR_PFX.getQuery()); //AT-2986
				preparedStatement.setInt(1, requestHandlerDto.getContactId());
			} else {
				preparedStatement = readOnlyConn
						.prepareStatement(RegistrationQueryConstant.GET_CONTACT_WATCHLIST.getQuery());
				preparedStatement.setInt(1, requestHandlerDto.getAccountId());
			}
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				toatalContactWatchListReason.add(resultSet.getString("Reason"));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
	}

	/**
	 * Gets the payment watch list reasons.
	 *
	 * @param readOnlyConn the read only conn
	 * @param watchListPaymentReasons the watch list payment reasons
	 * @return the payment watch list reasons
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void getPaymentWatchListReasons(Connection readOnlyConn, List<WatchListDetails> watchListPaymentReasons)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			preparedStatement = readOnlyConn.prepareStatement(
					RegistrationQueryConstant.GET_WATCHLIST_REASON_LIST_FOR_PAYMENTOUT_AND_PAYMENTIN.getQuery());
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				WatchListDetails watchlist = new WatchListDetails();
				watchlist.setName(resultSet.getString("Reason"));
				watchlist.setStopPaymentIn(resultSet.getBoolean("StopPaymentIn"));
				watchlist.setStopPaymentOut(resultSet.getBoolean("StopPaymentOut"));
				watchlist.setWatchlistCategory(resultSet.getInt("Category"));
				
				watchListPaymentReasons.add(watchlist);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

	}

	/**
	 * Adds the sort.
	 *
	 * @param columnName the column name
	 * @param isAsc the is asc
	 * @param query the query
	 * @return the string
	 */
	protected String addSort(String columnName, boolean isAsc, String query) {
		String orderByCon = "{ORDER_TYPE}";
		if (columnName != null) {
			query = query.replace("{SORT_FIELD_NAME}", columnName);
			if (isAsc) {
				query = query.replace(orderByCon, "ASC");
			} else {
				query = query.replace(orderByCon, "DESC");
			}
		}
		return query;
	}

	/**
	 * Update fraugster schedular data on WL added.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @param asyncStatus the async status
	 * @param conn the conn
	 * @param summaryList the summary list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void updateFraugsterSchedularDataOnWLAdded(BaseUpdateDBDto baseUpdateDBDto, String asyncStatus,
			Connection conn, List<FraugsterSummary> summaryList) throws CompliancePortalException {

		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = conn
					.prepareStatement(RegistrationQueryConstant.UPDATE_FRAUGSTER_SCHEDULAR_DATA_FOR_WL.getQuery());
			for (FraugsterSummary summary : summaryList) {
				prepareStatement.setString(1, asyncStatus);
				prepareStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				prepareStatement.setString(3, baseUpdateDBDto.getCreatedBy());
				prepareStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				prepareStatement.setTimestamp(5, null);
				prepareStatement.setString(6, summary.getFrgTransId());
				prepareStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closePrepareStatement(prepareStatement);
		}
	}

	/**
	 * Update fraugster schedular data on reason added.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @param asyncStatus the async status
	 * @param conn the conn
	 * @param summaryList the summary list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected void updateFraugsterSchedularDataOnReasonAdded(BaseUpdateDBDto baseUpdateDBDto, String asyncStatus,
			Connection conn, List<FraugsterSummary> summaryList) throws CompliancePortalException {

		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = conn.prepareStatement(
					RegistrationQueryConstant.UPDATE_FRAUGSTER_SCHEDULAR_DATA_FOR_REASON.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (FraugsterSummary summary : summaryList) {
				prepareStatement.setString(1, asyncStatus);
				prepareStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
				prepareStatement.setString(3, baseUpdateDBDto.getCreatedBy());
				prepareStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				prepareStatement.setTimestamp(5, null);
				prepareStatement.setString(6, summary.getFrgTransId());
				prepareStatement.executeUpdate();
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closePrepareStatement(prepareStatement);
		}
	}

	/**
	 * Gets the fraugster event service log id.
	 *
	 * @param readOnlyConn            the read only conn
	 * @param accountId the account id
	 * @return the fraugster event service log id
	 * @throws CompliancePortalException             the compliance portal exception This method returns provider
	 *             response from fraugster eventservicelogid.
	 */
	protected List<FraugsterSummary> getFraugsterEventServiceLogByAccountId(Connection readOnlyConn, Integer accountId)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FraugsterSummary> fraugsterSummaryList = null;
		FraugsterSummary fraugsterSummary = null;
		try {
			fraugsterSummaryList = new ArrayList<>();
			preparedStatement = readOnlyConn.prepareStatement(
					RegistrationQueryConstant.GET_FRAUGSTER_PROVIDER_RESPONSE_BY_ACCOUNTID.getQuery());
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fraugsterSummary = new FraugsterSummary();
				fraugsterSummary.setId(resultSet.getString(EVENT_SERVICE_LOG));
				fraugsterSummary.setFrgTransId(resultSet.getString(FRG_TRANS_ID));
				fraugsterSummary.setUpdatedBy(resultSet.getString("updatedBy"));
				fraugsterSummary.setFraugsterApproved(resultSet.getString(FRAUGSTER_APPROVED));
				fraugsterSummaryList.add(fraugsterSummary);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeResultset(resultSet);
		}
		return fraugsterSummaryList;
	}

	/**
	 * Gets the fraugster event service log by contact id.
	 *
	 * @param readOnlyConn the read only conn
	 * @param contactId the contact id
	 * @return the fraugster event service log by contact id
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<FraugsterSummary> getFraugsterEventServiceLogByContactId(Connection readOnlyConn, Integer contactId)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FraugsterSummary> fraugsterSummaryList = null;
		FraugsterSummary fraugsterSummary = null;
		try {
			fraugsterSummaryList = new ArrayList<>();
			preparedStatement = readOnlyConn.prepareStatement(
					RegistrationQueryConstant.GET_FRAUGSTER_PROVIDER_RESPONSE_BY_CONTACTID.getQuery());
			preparedStatement.setInt(1, contactId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fraugsterSummary = new FraugsterSummary();
				fraugsterSummary.setId(resultSet.getString(EVENT_SERVICE_LOG));
				fraugsterSummary.setFrgTransId(resultSet.getString(FRG_TRANS_ID));
				fraugsterSummary.setUpdatedBy(resultSet.getString("updatedBy"));
				fraugsterSummary.setFraugsterApproved(resultSet.getString(FRAUGSTER_APPROVED));
				fraugsterSummary.setScore(resultSet.getString("score"));
				fraugsterSummaryList.add(fraugsterSummary);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeResultset(resultSet);
		}
		return fraugsterSummaryList;
	}

	/**
	 * Gets the fraugster event service log for payment.
	 *
	 * @param readOnlyConn the read only conn
	 * @param contactId the contact id
	 * @param eventType the event type
	 * @return the fraugster event service log for payment
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<FraugsterSummary> getFraugsterEventServiceLogForPayment(Connection readOnlyConn, Integer contactId,
			Integer eventType) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FraugsterSummary> fraugsterSummaryList = null;
		FraugsterSummary fraugsterSummary = null;
		try {
			fraugsterSummaryList = new ArrayList<>();
			preparedStatement = readOnlyConn
					.prepareStatement(RegistrationQueryConstant.GET_FRAUGSTER_PROVIDER_RESPONSE_FOR_PAYMENT.getQuery());
			preparedStatement.setInt(1, contactId);
			preparedStatement.setInt(2, eventType);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				fraugsterSummary = new FraugsterSummary();
				fraugsterSummary.setId(resultSet.getString(EVENT_SERVICE_LOG));
				fraugsterSummary.setFrgTransId(resultSet.getString(FRG_TRANS_ID));
				fraugsterSummary.setFraugsterApproved(resultSet.getString(FRAUGSTER_APPROVED));
				fraugsterSummaryList.add(fraugsterSummary);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeResultset(resultSet);
		}
		return fraugsterSummaryList;
	}

	/**
	 * Checks if is reason addded.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @return the boolean
	 */
	protected Boolean isReasonAddded(BaseUpdateDBDto baseUpdateDBDto) {
		Boolean isReasonAdded = Boolean.FALSE;
		for (String addReason : baseUpdateDBDto.getAddReasons()) {
			isReasonAdded = FraugsterReasonsEnum.getFraugsterReason(addReason);
		}
		return isReasonAdded;
	}

	/**
	 * Checks if is reason removed.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @return the boolean
	 */
	protected Boolean isReasonRemoved(BaseUpdateDBDto baseUpdateDBDto) {
		Boolean isReasonRemoved = Boolean.FALSE;
		for (String removeReason : baseUpdateDBDto.getDeletedReasons()) {
			isReasonRemoved = FraugsterReasonsEnum.getFraugsterReason(removeReason);
		}
		return isReasonRemoved;
	}

	/**
	 * Checks if is watch list added.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @return the boolean
	 */
	protected Boolean isWatchListAdded(BaseUpdateDBDto baseUpdateDBDto) {
		Boolean isWatchListAdded = Boolean.FALSE;

		for (String addWatchlist : baseUpdateDBDto.getAddWatchlist()) {
			isWatchListAdded = FragusterWatchListEnum.getFraugsterWatchlist(addWatchlist);
		}
		return isWatchListAdded;
	}

	/**
	 * Checks if is watch list removed.
	 *
	 * @param baseUpdateDBDto the base update DB dto
	 * @return the boolean
	 */
	protected Boolean isWatchListRemoved(BaseUpdateDBDto baseUpdateDBDto) {
		Boolean isWatchListRemoved = Boolean.FALSE;

		for (String removeWatchlist : baseUpdateDBDto.getDeletedWatchlist()) {
			isWatchListRemoved = FragusterWatchListEnum.getFraugsterWatchlist(removeWatchlist);
		}
		return isWatchListRemoved;
	}

	/**
	 * Sets the further payment in detail list.
	 *
	 * @param accountId the account id
	 * @param paymentDetails the payment details
	 * @param connection the connection
	 * @return the list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<FurtherPaymentInDetails> setFurtherPaymentInDetailList(Integer accountId,
			FurtherPaymentDetails paymentDetails, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FurtherPaymentInDetails> paymentInDetailsList = new ArrayList<>();
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_PAYMENTINFO_FOR_ACCOUNT_SELECT.getQuery());
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, 0);// Minrecord
			preparedStatement.setInt(3, 10);// MaxRecord
			resultSet = preparedStatement.executeQuery();
			Integer totalRecords = 0;
			while (resultSet.next()) {
				FurtherPaymentInDetails paymentInDetails = new FurtherPaymentInDetails();
				/**
				 * We are setting account name and number according to payment
				 * method 1) If payment method is 'SWITCH/DEBIT' then we will
				 * show Card holder name and card number on UI else account name
				 * and account number. 2)If account number is not received then
				 * we will show '-' for that.
				 */
				if ("SWITCH/DEBIT".equalsIgnoreCase(resultSet.getString(Constants.PAYMENT_METHOD_IN))) {
					paymentInDetails.setAccountName(resultSet.getString("Ccfirstname"));
				} else {
					setDebtorName(paymentInDetails, resultSet.getString("DebtorName"));
				}
				String debtorAccountNumber = resultSet.getString("DebtorAccountNumber");
				if (null != debtorAccountNumber && !debtorAccountNumber.isEmpty())
					paymentInDetails.setAccount(debtorAccountNumber);
				else
					paymentInDetails.setAccount("-");
				/** Method created to set risk guardian score on UI */
				setRiskGuardianScore(paymentInDetails, resultSet.getString("RiskScore"), resultSet.getString("TScore"));
				paymentInDetails.setAmount(StringUtils
						.getNumberFormat(DecimalFormatter.convertToFourDigit(resultSet.getString(Constants.AMOUNT))));
				paymentInDetails.setDateOfPayment(
						DateTimeFormatter.removeTimeFromDate(resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)));
				paymentInDetails.setMethod(resultSet.getString("PaymentMethodin"));
				paymentInDetails.setSellCurrency(resultSet.getString("SellCurrency"));
				paymentInDetails.setTradeContractNumber(resultSet.getString(Constants.TRADE_CONTRACT_NUMBER));
				//AT-1731 - SnehaZagade
				paymentInDetails.setBankName(resultSet.getString("BankName"));
				totalRecords = resultSet.getInt("TotalRows");
				paymentInDetailsList.add(paymentInDetails);
			}
			paymentDetails.setPayInDetailsTotalRecords(totalRecords);
			paymentDetails.setFurtherPaymentInDetails(paymentInDetailsList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return paymentInDetailsList;
	}

	/**
	 * Sets the debtor name.
	 *
	 * @param paymentInDetails the payment in details
	 * @param debtorName the debtor name
	 */
	protected void setDebtorName(FurtherPaymentInDetails paymentInDetails, String debtorName) {
		if (null != debtorName && !debtorName.isEmpty())
			paymentInDetails.setAccountName(debtorName);
		else
			paymentInDetails.setAccountName("-");
	}

	/**
	 * Purpose: If tScore field of risk score is empty or null then we need to
	 * show "-" on UI else we will show that score.
	 * 
	 * Implementation: 1)We are checking 'tScore' field of fundsInCreateRequest.
	 * If it is null then we are setting risk score as "-" otherwise actual
	 * score to it.
	 * 
	 * - code changed by Vishal J
	 *
	 * @param paymentInDetails the payment in details
	 * @param riskScore the risk score
	 * @param tScore the t score
	 */
	protected void setRiskGuardianScore(FurtherPaymentInDetails paymentInDetails, String riskScore, String tScore) {
		if (riskScore != null) {
			if (tScore == null || tScore.isEmpty())
				paymentInDetails.setRiskGuardianScore("-");
			else
				paymentInDetails.setRiskGuardianScore(tScore);
		} else {
			paymentInDetails.setRiskGuardianScore("-");
		}
	}

	/**
	 * Sets the further payment out detail list.
	 *
	 * @param accountId the account id
	 * @param paymentDetails the payment details
	 * @param connection the connection
	 * @return the list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<FurtherPaymentOutDetails> setFurtherPaymentOutDetailList(Integer accountId,
			FurtherPaymentDetails paymentDetails, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FurtherPaymentOutDetails> paymentOutDetailsList = new ArrayList<>();
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_PAYMENTOUTINFO_FOR_ACCOUNT_SELECT.getQuery());
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, 0);// Minrecord
			preparedStatement.setInt(3, 10);// MaxRecord
			resultSet = preparedStatement.executeQuery();
			Integer totalRecords = 0;
			while (resultSet.next()) {
				FurtherPaymentOutDetails paymentOutDetails = new FurtherPaymentOutDetails();
				// change done to fetch attributes json into POJO - Vishal J
				if (null != resultSet.getString(Constants.BENEFICIARY_LAST_NAME)
						&& !resultSet.getString(Constants.BENEFICIARY_LAST_NAME).isEmpty()) {
					String beneficiaryName = new StringBuilder()
							.append(resultSet.getString(Constants.BENEFICIARY_FIRST_NAME)).append(" ")
							.append(resultSet.getString(Constants.BENEFICIARY_LAST_NAME)).toString();
					paymentOutDetails.setAccountName(beneficiaryName);

				} else {
					paymentOutDetails.setAccountName(resultSet.getString(Constants.BENEFICIARY_FIRST_NAME));
				}
				String accountNumber = resultSet.getString("AccountNumber");
				if (null != accountNumber && !accountNumber.isEmpty()) {
					paymentOutDetails.setAccount(accountNumber);
				}
				paymentOutDetails.setAmount(StringUtils
						.getNumberFormat(DecimalFormatter.convertToFourDigit(resultSet.getString(Constants.AMOUNT))));
				paymentOutDetails.setBuyCurrency(resultSet.getString("BuyCurrency"));
				if (null != resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)) {
					paymentOutDetails.setDateOfPayment(
							DateTimeFormatter.removeTimeFromDate(resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)));
				} else {
					paymentOutDetails.setDateOfPayment(Constants.DASH_DETAILS_PAGE);
				}
				// added by neelesh pant
				paymentOutDetails.setValuedate(DateTimeFormatter.dateFormat(resultSet.getString("MaturityDate")));
				totalRecords = resultSet.getInt("TotalRows");
				paymentOutDetails.setTradeContractNumber(resultSet.getString(Constants.TRADE_CONTRACT_NUMBER));
				if (null != resultSet.getString(Constants.PAYMENT_REFERENCE)
						&& !resultSet.getString(Constants.PAYMENT_REFERENCE).isEmpty()) {
					paymentOutDetails.setPaymentReference(resultSet.getString(Constants.PAYMENT_REFERENCE));
				}
				//AT-1731 - SnehaZagade
				paymentOutDetails.setBankName(resultSet.getString("BankName"));
				paymentOutDetailsList.add(paymentOutDetails);
			}
			paymentDetails.setPayOutDetailsTotalRecords(totalRecords);
			paymentDetails.setFurtherPaymentOutDetails(paymentOutDetailsList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return paymentOutDetailsList;
	}

	/**
	 * Gets the further payment details list.
	 *
	 * @param accountId the account id
	 * @param connection the connection
	 * @return the further payment details list
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected FurtherPaymentDetails getFurtherPaymentDetailsList(Integer accountId, Connection connection)
			throws CompliancePortalException {
		FurtherPaymentDetails paymentDetails = new FurtherPaymentDetails();
		try {
			setFurtherPaymentInDetailList(accountId, paymentDetails, connection);
			setFurtherPaymentOutDetailList(accountId, paymentDetails, connection);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		return paymentDetails;
	}
	
	protected SavedSearch getSaveSearches(Integer userId,String pageType,Connection connection) throws CompliancePortalException {
		SavedSearch savedSearchResponse = new SavedSearch();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		List<SavedSearchData> saveSearch = new ArrayList<>();
		savedSearchResponse.setSavedSearchData(saveSearch);
		try {
			preparedStatement = connection.prepareStatement(SavedSearchQueryConstant.GET_SAVE_SEARCH.getQuery());
			preparedStatement.setString(1,pageType);
			preparedStatement.setInt(2,userId);
			preparedStatement.executeQuery();
			rs = preparedStatement.getResultSet();
			while(rs.next()) {
				SavedSearchData savedSearchData = new SavedSearchData();
				savedSearchData.setSaveSearchName(rs.getString("searchName"));
				savedSearchData.setSaveSearchFilter(rs.getString("searchCriteria"));
				saveSearch.add(savedSearchData);
			}
		}
		catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}
		return savedSearchResponse;
	}
	
	/**
	 * Gets the legal entity.
	 *
	 * @param connection the connection
	 * @return the legal entity
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<String> getLegalEntity(Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<String> legalEntity = new ArrayList<>();
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_LEGAL_ENTITY.getQuery());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				legalEntity.add(resultSet.getString(RegQueDBColumns.CODE.getName()));
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}

		return legalEntity;
	}
}
