package com.currenciesdirect.gtg.compliance.dbport.whitelistbeneficiary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.ResponseStatus;
import com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.IWhitelistBeneficiaryDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

/**
 * The Class WhitelistBeneficiaryDBServiceImpl.
 */
@SuppressWarnings({"squid:S2095", "squid:S3077"})
public class WhitelistBeneficiaryDBServiceImpl extends AbstractDao implements IWhitelistBeneficiaryDBService {

	/** The Constant COULD_NOT_CLOSE_CONNECTION. */
	private static final String COULD_NOT_CLOSE_CONNECTION = "Could not close connection";
	/** The i DB service. */
	private static volatile IWhitelistBeneficiaryDBService iDBService = null;
	
	/** The Constant ACCOUNT. */
	private static final String ACCOUNT_NUMBER = "AccountNumber";
	
	/** The Constant LOG. */

	/**
	 * Instantiates a new DB service impl.
	 */
	private WhitelistBeneficiaryDBServiceImpl() {
	}

	/**
	 * Gets the single instance of DBServiceImpl.
	 *
	 * @return single instance of DBServiceImpl
	 */
	public static IWhitelistBeneficiaryDBService getInstance() {
		if (iDBService == null) {
			synchronized (WhitelistBeneficiaryDBServiceImpl.class) {
				if (iDBService == null) {
					iDBService = new WhitelistBeneficiaryDBServiceImpl();
				}
			}
		}
		return iDBService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.
	 * IWhitelistBeneficiaryDBService#getWhitelistBeneficiaryData(com.
	 * currenciesdirect.gtg.compliance.commons.domain.internalruleservice.
	 * WhitelistBeneficiaryRequest)
	 */
	public List<WhitelistBeneficiaryResponse> getWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {
		LOG.debug("DBServiceImpl.getBlacklistDataByType: start");
		List<WhitelistBeneficiaryResponse> whitelistBeneficiaryList = new ArrayList<>();

		ResultSet rs = null;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection();
			preparedStatement = connection
					.prepareStatement(DBQueryConstantWhitelistBeneficiary.GET_WHITELIST_BENEFICIARY_DATA.getQuery());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				WhitelistBeneficiaryResponse whitelistResponse = new WhitelistBeneficiaryResponse();

				whitelistResponse.setFirstName(rs.getString("FirstName"));
				whitelistResponse.setCreatedOn((removeMillisFromTimeStamp(rs.getTimestamp("CreatedOn").toString())));
				whitelistResponse.setAccountNumber(rs.getString(ACCOUNT_NUMBER));
				whitelistResponse.setNotes(rs.getString("notes"));
				whitelistBeneficiaryList.add(whitelistResponse);
			}
		} catch (Exception exception) {
			throw new InternalRuleException(InternalRuleErrors.FAILED, exception);
		} finally {
			try {
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
				closeResultset(rs);
			} catch (Exception e) {
				LOG.error("Errror in getWhitelistBeneficiaryData", e);
			}
		}
		return whitelistBeneficiaryList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#
	 * deleteFromBacklist(com.currenciesdirect.gtg.compliance.core.domain.
	 * blacklist.BlacklistRequest)
	 */
	public WhitelistBeneficiaryResponse deleteFromWhiteListBeneficiaryData(
			WhitelistBeneficiaryRequest whiteListBeneficiaryRequest) throws InternalRuleException {
		LOG.debug("DBServiceImpl.deleteFromWhiteListBeneficiaryData: start");
		WhitelistBeneficiaryResponse response = new WhitelistBeneficiaryResponse();
		Connection connection = null;
		try {
			connection = getConnection();
			beginTransaction(connection);
			if (deleteFromWhiteListBeneficiary(whiteListBeneficiaryRequest, connection)) {
				LOG.debug("Data deleted successfully");
				response.setStatus(ResponseStatus.SUCCESS.getStatus());
				commitTransaction(connection);
			} else {
				throw new InternalRuleException(InternalRuleErrors.FAILED);
			}

		} catch (InternalRuleException internalRuleException) {
			throw internalRuleException;
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeConnection(connection);
			} catch (Exception e) {
				LOG.error(COULD_NOT_CLOSE_CONNECTION, e);
			}
		}
		return response;
	}

	/**
	 * Delete from blacklist.
	 *
	 * @param request
	 *            the request
	 * @param connection
	 *            the connection
	 * @return true, if successful
	 * @throws InternalRuleException
	 *             the internal rule exception
	 */
	private boolean deleteFromWhiteListBeneficiary(WhitelistBeneficiaryRequest request, Connection connection)
			throws InternalRuleException {
		Boolean status = Boolean.TRUE;
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(DBQueryConstantWhitelistBeneficiary.DELETE_WHITELIST_BENEFICIARY_DATA.getQuery());
			preparedStatement.setNString(1, request.getFirstName());
			preparedStatement.setNString(2, request.getAccountNumber());
			int count = preparedStatement.executeUpdate();
			status = getUpdateStatus(count);
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closePrepareStatement(preparedStatement);
			} catch (Exception e) {
				LOG.error("Could not close preparedStaement", e);
			}
		}
		return status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.blacklist.IDBService#
	 * saveIntoBlacklist(com.currenciesdirect.gtg.compliance.core.domain.
	 * blacklist.BlacklistRequest)
	 */
	public WhitelistBeneficiaryResponse saveIntoWhitelistBeneficiary(
			WhitelistBeneficiaryRequest whitelistBeneficiaryRequest) throws InternalRuleException {
		LOG.debug("DBServiceImpl.saveIntoWhitelistBeneficiary: start");
		WhitelistBeneficiaryResponse response = new WhitelistBeneficiaryResponse();
		try {

			if (saveIntoWhitelistBeneficiaryData(whitelistBeneficiaryRequest)) {
				response.setStatus(ResponseStatus.SUCCESS.getStatus());

			} else {
				throw new InternalRuleException(InternalRuleErrors.INVALID_REQUEST);
			}

		} catch (InternalRuleException e) {
			throw e;
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR);
		}
		return response;
	}

	/**
	 * Save into blacklist.
	 *
	 * @param request
	 *            the request
	 * @return true, if successful
	 * @throws InternalRuleException
	 *             the internal rule exception
	 * @throws SQLException
	 *             the SQL exception
	 */
	private boolean saveIntoWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException, SQLException {
		Boolean status = Boolean.TRUE;
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		try {
			connection = getConnection();
			preparedStatement = connection.prepareStatement(
					DBQueryConstantWhitelistBeneficiary.INSERT_INTO_WHITELIST_BENEFICIARY_DATA.getQuery());
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			request.setCreatedOn(timestamp);

			preparedStatement.setNString(1, request.getFirstName());
			preparedStatement.setNString(2, request.getAccountNumber());
			preparedStatement.setInt(3, 0);
			preparedStatement.setNString(4, request.getCreatedBy());
			preparedStatement.setTimestamp(5, request.getCreatedOn());
			preparedStatement.setNString(6, request.getNotes());

			int count = preparedStatement.executeUpdate();
			status = getUpdateStatus(count);
		} catch (Exception e) {
			try {
				LOG.debug("Duplicate entry found while inserting data...Trying to update delete status");
				status = updateDeleteFlagForWhitelistBeneficiaryData(request);
				LOG.debug("Error while ", e);
				if (!status.equals(Boolean.TRUE))
					throw new InternalRuleException(InternalRuleErrors.DUPLICATE_DATA_ADDITION);
			} catch (InternalRuleException ex) {
				throw new InternalRuleException(InternalRuleErrors.DUPLICATE_DATA_ADDITION, ex);
			} catch (Exception ex) {
				throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR, ex);
			}
		} finally {
			try {
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			} catch (Exception e) {
				LOG.error("Could not close preparedStaement", e);
			}
		}
		return status;
	}

	/**
	 * Update delete flag for whitelist beneficiary data.
	 *
	 * @param request
	 *            the request
	 * @return the boolean
	 * @throws SQLException
	 *             the SQL exception
	 * @throws InternalRuleException
	 *             the internal rule exception
	 */
	private Boolean updateDeleteFlagForWhitelistBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws SQLException, InternalRuleException {
		PreparedStatement updatePreparedStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		Boolean result= Boolean.FALSE;
		int count = 0;
		try {
			Integer id = searchWhiteListBeneficiaryDataToUpdate(request);
			connection = getConnection();
			if (null != id && id != 0) {
				updatePreparedStatement = connection.prepareStatement(
						DBQueryConstantWhitelistBeneficiary.UPDATE_DELETE_STATUS_FOR_WHITELIST_BENEFICIARY.getQuery());
				updatePreparedStatement.setInt(1, id);
			}
			if (null != updatePreparedStatement) {
				count = updatePreparedStatement.executeUpdate();
			}
			if (count == 0) {
				result= Boolean.FALSE;
				return result;
			}
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(updatePreparedStatement);
			closeConnection(connection);

		}
		result=Boolean.TRUE;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary.
	 * IWhitelistBeneficiaryDBService#searchWhiteListBeneficiaryData(com.
	 * currenciesdirect.gtg.compliance.commons.domain.internalruleservice.
	 * WhitelistBeneficiaryRequest)
	 */
	public List<WhitelistBeneficiaryResponse> searchWhiteListBeneficiaryData(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {
		List<WhitelistBeneficiaryResponse> whitelistBeneficiaryList = new ArrayList<>();

		Connection connection = null;
		PreparedStatement preparedStatementWildCard = null;
		ResultSet rs = null;
		try {
			connection = getConnection();
			preparedStatementWildCard = connection
					.prepareStatement(DBQueryConstantWhitelistBeneficiary.SEARCH_WHITELIST_BENEFICIARY_DATA.getQuery());
			String nameValue = replaceWildCard(request.getFirstName());
			String accountValue = replaceWildCard(request.getAccountNumber());
			preparedStatementWildCard.setNString(1, "%" + nameValue + "%");
			preparedStatementWildCard.setNString(2, "%" + accountValue + "%");
			rs = preparedStatementWildCard.executeQuery();
			while (rs.next()) {
				WhitelistBeneficiaryResponse whitelistResponse = new WhitelistBeneficiaryResponse();

				whitelistResponse.setFirstName(rs.getString("FirstName"));
				whitelistResponse.setCreatedOn((removeMillisFromTimeStamp(rs.getTimestamp("CreatedOn").toString())));
				whitelistResponse.setAccountNumber(rs.getString(ACCOUNT_NUMBER));
				whitelistResponse.setNotes(rs.getString("notes"));
				whitelistBeneficiaryList.add(whitelistResponse);
			}
			rs.close();

		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.FAILED);
		} finally {
			try {
				closeResultset(rs);
				if (preparedStatementWildCard != null) {
					closePrepareStatement(preparedStatementWildCard);
				}
				closeConnection(connection);
			} catch (InternalRuleException e) {
				LOG.error("Error in searchWhiteListBeneficiaryData {1}", e);

			}

		}
		return whitelistBeneficiaryList;
	}

	/**
	 * Search white list beneficiary data to update.
	 *
	 * @param request
	 *            the request
	 * @return the integer
	 * @throws InternalRuleException
	 *             the internal rule exception
	 */
	public Integer searchWhiteListBeneficiaryDataToUpdate(WhitelistBeneficiaryRequest request)
			throws InternalRuleException {

		Connection connection = null;
		PreparedStatement selectPreparedStatement = null;
		ResultSet rs = null;
		Integer id = 0;
		try {
			connection = getConnection();
			selectPreparedStatement = connection.prepareStatement(
					DBQueryConstantWhitelistBeneficiary.IS_WHITELIST_BENEFICIARY_DATA_PRESENT.getQuery());
			selectPreparedStatement.setString(1, request.getAccountNumber());
			rs = selectPreparedStatement.executeQuery();

			while (rs.next()) {
				if (rs.getString(ACCOUNT_NUMBER).equalsIgnoreCase(request.getAccountNumber())
						&& rs.getInt("Deleted") == 1) {
					id = rs.getInt("ID");
					return id;
				}
			}
		} catch (Exception e) {
			throw new InternalRuleException(InternalRuleErrors.FAILED);
		} finally {
			closeResultset(rs);
			closePrepareStatement(selectPreparedStatement);
			closeConnection(connection);

		}
		return id;
	}

	/**
	 * Removes the millis from time stamp.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public String removeMillisFromTimeStamp(String date) {
		if (null != date && date.contains(".")) {
			return date.substring(0, date.indexOf('.'));
		}
		return date;
	}

	/**
	 * Gets the update status.
	 *
	 * @param count
	 *            the count
	 * @return the update status
	 */
	private Boolean getUpdateStatus(int count) {
		Boolean status = Boolean.TRUE;
		if (count <= 0)
			status = Boolean.FALSE;
		return status;
	}

	/**
	 * Replace wild card.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	private String replaceWildCard(String value) {
		return value.replace('*', '%').intern();
	}
}
