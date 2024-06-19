package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.core.IActivityLogDBService;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;

/**
 * The Class ActivityLogDBServiceImpl.
 */
@Component("activityLogDBServiceImpl")
public class ActivityLogDBServiceImpl extends AbstractDao implements IActivityLogDBService {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogDBService#
	 * getPaymentInActivityLogs(java.lang.Integer)
	 */
	@Override
	public ActivityLogs getPaymentInActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		ActivityLogs activityLogs = null;
		String query = ActivityLogQueryConstant.GET_PAYMENT_IN_ACTIVITY_LOGS_BY_ROWS.getQuery();
		String countQuery = ActivityLogQueryConstant.GET_PAYMENT_IN_ACTIVITY_LOGS_COUNT.getQuery();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			activityLogs = getPaymentActivityLogData(connection, query, request);
			activityLogs.setTotalRecords(getTotalCount(connection, countQuery, request));
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return activityLogs;
	}

	/**
	 * Gets the payment in activity log data.
	 *
	 * @param connection
	 *            the connection
	 * @param query
	 *            the query
	 * @param request
	 *            the request
	 * @return the payment in activity log data
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private ActivityLogs getPaymentActivityLogData(Connection connection, String query, ActivityLogRequest request)
			throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, request.getAccountId());
			preparedStatement.setInt(2, request.getMinRecord());
			preparedStatement.setInt(3, request.getMaxRecord());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setCreatedBy(resultSet.getString(Constants.USER));
				activityLogData.setActivity(resultSet.getString(Constants.ACTIVITY));
				activityLogData.setActivityType(
						ActivityType.getActivityLogDisplay(resultSet.getString(Constants.ACTIVITY_TYPE)));
				Timestamp createdOn = resultSet.getTimestamp(Constants.CREATED_ON);
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setContractNumber(resultSet.getString(Constants.CONTRACT_NUMBER));
				activityLogData.setComment(resultSet.getString(Constants.COMMENT));
				activityLogDataList.add(activityLogData);
			}
			activityLogs.setActivityLogData(activityLogDataList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return activityLogs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogDBService#
	 * getPaymentOutActivityLogs(com.currenciesdirect.gtg.compliance.core.domain
	 * .ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getPaymentOutActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		ActivityLogs activityLogs = null;
		String query = ActivityLogQueryConstant.GET_PAYMENT_OUT_ACTIVITY_LOGS_BY_ROWS.getQuery();
		String countQuery = ActivityLogQueryConstant.GET_PAYMENT_OUT_ACTIVITY_LOGS_COUNT.getQuery();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			activityLogs = getPaymentActivityLogData(connection, query, request);
			activityLogs.setTotalRecords(getTotalCount(connection, countQuery, request));
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return activityLogs;
	}

	/**
	 * Gets the total count.
	 *
	 * @param connection
	 *            the connection
	 * @param countQuery
	 *            the count query
	 * @param request
	 *            the request
	 * @return the total count
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private Integer getTotalCount(Connection connection, String countQuery, ActivityLogRequest request)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int totalcount = 0;
		try {
			preparedStatement = connection.prepareStatement(countQuery);
			preparedStatement.setInt(1, request.getAccountId());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				totalcount = resultSet.getInt("TotalCount");
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return totalcount;
	}

	/**
	 * Gets the payment out activity log data.
	 *
	 * @param connection
	 *            the connection
	 * @param query
	 *            the query
	 * @param request
	 *            the request
	 * @return the payment out activity log data
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogDBService#
	 * getRegistrationActivityLogs(com.currenciesdirect.gtg.compliance.core.
	 * domain.ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getRegistrationActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		ActivityLogs activityLogs = null;
		String query = ActivityLogQueryConstant.GET_REGISTRATION_ACTIVITY_LOGS.getQuery();
		String countQuery = ActivityLogQueryConstant.GET_REGISTRATION_ACTIVITY_LOGS_COUNT.getQuery();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			activityLogs = getRegistrationLogData(connection, query, request);
			activityLogs.setTotalRecords(getTotalCount(connection, countQuery, request));
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return activityLogs;
	}

	/**
	 * Gets the registration log data.
	 *
	 * @param connection
	 *            the connection
	 * @param query
	 *            the query
	 * @param request
	 *            the request
	 * @return the registration log data
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private ActivityLogs getRegistrationLogData(Connection connection, String query, ActivityLogRequest request)
			throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, request.getAccountId());
			preparedStatement.setInt(2, request.getMinRecord());
			preparedStatement.setInt(3, request.getMaxRecord());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setCreatedBy(resultSet.getString(Constants.USER));
				activityLogData.setActivity(resultSet.getString(Constants.ACTIVITY));
				activityLogData.setActivityType(
						ActivityType.getActivityLogDisplay(resultSet.getString(Constants.ACTIVITY_TYPE)));
				Timestamp createdOn = resultSet.getTimestamp(Constants.CREATED_ON);
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setContractNumber("--");
				activityLogData.setComment(resultSet.getString(Constants.COMMENT));
				activityLogDataList.add(activityLogData);
			}
			activityLogs.setActivityLogData(activityLogDataList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return activityLogs;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IActivityLogDBService#
	 * getAllActivityLogs(com.currenciesdirect.gtg.compliance.core.domain.
	 * ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getAllActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		ActivityLogs activityLogs = null;
		String query = ActivityLogQueryConstant.GET_ALL_ACTIVITY_LOGS.getQuery();

		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			activityLogs = getAllActivityLogData(connection, query, request);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return activityLogs;
	}

	/**
	 * Gets the consolidated registration, payment in, payment out activity log
	 * data.
	 *
	 * @param connection
	 *            the connection
	 * @param query
	 *            the query
	 * @param request
	 *            the request
	 * @return the consolidated registration, payment in, payment out activity
	 *         log data
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private ActivityLogs getAllActivityLogData(Connection connection, String query, ActivityLogRequest request)
			throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			final Integer accountId = request.getAccountId();
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, request.getMinRecord());
			preparedStatement.setInt(3, request.getMaxRecord());
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				activityLogs.setTotalRecords(resultSet.getInt("totalrecords"));
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setCreatedBy(resultSet.getString(Constants.USER));
				activityLogData.setActivity(resultSet.getString(Constants.ACTIVITY));
				activityLogData.setActivityType(
						ActivityType.getActivityLogDisplay(resultSet.getString(Constants.ACTIVITY_TYPE)));
				Timestamp createdOn = resultSet.getTimestamp(Constants.CREATED_ON);
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setContractNumber(resultSet.getString(Constants.CONTRACT_NUMBER));
				activityLogData.setComment(resultSet.getString(Constants.COMMENT));
				activityLogDataList.add(activityLogData);
			}
			activityLogs.setActivityLogData(activityLogDataList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return activityLogs;
	}
	
	
}
