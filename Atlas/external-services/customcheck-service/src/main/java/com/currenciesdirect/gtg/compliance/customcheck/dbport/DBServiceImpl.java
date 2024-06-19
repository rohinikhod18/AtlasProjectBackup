
package com.currenciesdirect.gtg.compliance.customcheck.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.customcheck.core.IDBService;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckDeleteRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckInsertRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateRequest;
import com.currenciesdirect.gtg.compliance.customcheck.core.domain.CustomCheckUpdateResponse;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckErrors;
import com.currenciesdirect.gtg.compliance.customcheck.exception.CustomCheckException;

/**
 * The Class DBServiceImpl.
 * 
 * @author abhijitg
 */
public class DBServiceImpl extends AbstractDao implements IDBService {

	static final Logger LOG = LoggerFactory.getLogger(DBServiceImpl.class);

	private static volatile  IDBService iDBService = null;

	private DBServiceImpl() {
		
	}
	

	public static IDBService getInstance() {
		if (iDBService == null) {
			synchronized (DBServiceImpl.class) {
				if (iDBService == null) {
					iDBService = new DBServiceImpl();
				}
			}
		}
		return iDBService;
	}

	@Override
	public Boolean saveCustomCheckDetails(CustomCheckInsertRequest request) throws CustomCheckException {
		Connection conn = null;

		try {
			conn = getConnection();
			beginTransaction(conn);

			if (request.getOccupation() != null && request.getOccupation().getDataField() != null
					&& !request.getOccupation().getDataField().isEmpty()) {
				saveOccupation(request, conn);
			}
			if (request.getCountriesOfTrade() != null && request.getCountriesOfTrade().getDataField() != null
					&& !request.getCountriesOfTrade().getDataField().isEmpty()) {
				saveCountriesOfTrade(request, conn);
			}
			if (request.getPurposeOfTransaction() != null && request.getPurposeOfTransaction().getDataField() != null
					&& !request.getPurposeOfTransaction().getDataField().isEmpty()) {
				savePurposeOfTransaction(request, conn);
			}
			if (request.getSourceOfLead() != null && request.getSourceOfLead().getDataField() != null
					&& !request.getSourceOfLead().getDataField().isEmpty()) {
				saveSourceOfLead(request, conn);
			}
			if (request.getSourceOfFund() != null && request.getSourceOfFund().getDataField() != null
					&& !request.getSourceOfFund().getDataField().isEmpty()) {
				saveSourceOfFund(request, conn);
			}
			if (request.getValueOfTransaction() != null && request.getValueOfTransaction().getDataField() != null
					&& !request.getValueOfTransaction().getDataField().isEmpty()) {
				saveValueOfTransaction(request, conn);
			}
			commitTransaction(conn);
		} catch (CustomCheckException customCheckException) {
			throw customCheckException;
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_INSERTING_DATA, e);
			}
		}
		return true;
	}

	/*@Override
	public CustomCheckSearchResponse searchCustomCheckDetails(CustomCheckSearchRequest request)
			throws CustomCheckException {
		Integer totalScore = 0;
		Connection conn = null;
		CustomCheckSearchResponse customCheckSearchResponse = new CustomCheckSearchResponse();

		try {
			conn = getConnection();

			if (request.getOccupation() != null  
					&& !request.getOccupation().isEmpty()) {
				customCheckSearchResponse = searchOccupation(request, conn, customCheckSearchResponse);

			}
			if (request.getCountriesOfTrade() != null  
					&& !request.getCountriesOfTrade().isEmpty()) {
				customCheckSearchResponse = searchCountriesOfTrade(request, conn, customCheckSearchResponse);

			}
			if (request.getPurposeOfTransaction() != null  
					&& !request.getPurposeOfTransaction().isEmpty()) {
				customCheckSearchResponse = searchPurposeOfTransaction(request, conn, customCheckSearchResponse);

			}
			if (request.getSourceOfLead() != null  
					&& !request.getSourceOfLead().isEmpty()) {
				customCheckSearchResponse = searchSourceOfLead(request, conn, customCheckSearchResponse);

			}
			if (request.getSourceOfFund() != null  
					&& !request.getSourceOfFund().isEmpty()) {
				customCheckSearchResponse = searchSourceOfFund(request, conn, customCheckSearchResponse);

			}
			if (request.getValueOfTransaction() != null  
					&& !request.getValueOfTransaction().isEmpty()) {
				customCheckSearchResponse = searchValueOfTransaction(request, conn, customCheckSearchResponse);

			}

		} catch (CustomCheckException customCheckException) {
			throw customCheckException;
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_SEARCHING_DATA, e);
		} finally {
			try {
				closeConnection(conn);
				totalScore = getTotalScore(customCheckSearchResponse);
				customCheckSearchResponse.setTotal(totalScore);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_SEARCHING_DATA, e);
			}
		}
		return customCheckSearchResponse;
	}*/

	@Override
	public CustomCheckUpdateResponse updateCustomCheckDetails(CustomCheckUpdateRequest request) throws CustomCheckException {
		Connection conn = null;
		CustomCheckUpdateResponse response=new CustomCheckUpdateResponse();

		try {
			conn = getConnection();
			beginTransaction(conn);

			if (request.getOccupation() != null && request.getOccupation().getDataField() != null
					&& !request.getOccupation().getDataField().isEmpty()) {
				response=updateOccupation(request, conn,response);
			}
			if (request.getCountriesOfTrade() != null && request.getCountriesOfTrade().getDataField() != null
					&& !request.getCountriesOfTrade().getDataField().isEmpty()) {
				response=updateCountriesOfTrade(request, conn,response);
			}
			if (request.getPurposeOfTransaction() != null && request.getPurposeOfTransaction().getDataField() != null
					&& !request.getPurposeOfTransaction().getDataField().isEmpty()) {
				response=updatePurposeOfTransaction(request, conn,response);
			}
			if (request.getSourceOfLead() != null && request.getSourceOfLead().getDataField() != null
					&& !request.getSourceOfLead().getDataField().isEmpty()) {
				response=updateSourceOfLead(request, conn,response);
			}
			if (request.getSourceOfFund() != null && request.getSourceOfFund().getDataField() != null
					&& !request.getSourceOfFund().getDataField().isEmpty()) {
				response=updateSourceOfFund(request, conn,response);
			}
			if (request.getValueOfTransaction() != null && request.getValueOfTransaction().getDataField() != null
					&& !request.getValueOfTransaction().getDataField().isEmpty()) {
				response=updateValueOfTransaction(request, conn,response);
			}
			commitTransaction(conn);
		} catch (CustomCheckException customCheckException) {
			throw customCheckException;
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_UPDATING_DATA, e);
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return response;
	}

	@Override
	public Boolean deleteCustomCheckDetails(CustomCheckDeleteRequest request) throws CustomCheckException {
		Connection conn = null;

		try {
			conn = getConnection();
			beginTransaction(conn);
			if (request.getOccupation() != null && !request.getOccupation().isEmpty()) {
				deleteOccupation(request, conn);
			}
			if (request.getCountriesOfTrade() != null && !request.getCountriesOfTrade().isEmpty()) {
				deleteCountriesOfTrade(request, conn);
			}
			if (request.getPurposeOfTransaction() != null && !request.getPurposeOfTransaction().isEmpty()) {
				deletePurposeOfTransaction(request, conn);
			}
			if (request.getSourceOfLead() != null && !request.getSourceOfLead().isEmpty()) {
				deleteSourceOfLead(request, conn);
			}
			if (request.getSourceOfFund() != null && !request.getSourceOfFund().isEmpty()) {
				deleteSourceOfFund(request, conn);
			}
			if (request.getValueOfTransaction() != null && !request.getValueOfTransaction().isEmpty()) {
				deleteValueOfTransaction(request, conn);
			}
			commitTransaction(conn);
		} catch (CustomCheckException customCheckException) {
			throw customCheckException;
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_DELETING_DATA, e);
		} finally {
			try {
				closeConnection(conn);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Save occupation to DB.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean saveOccupation(CustomCheckInsertRequest request, Connection conn) throws CustomCheckException {
		LOG.info("DBServiceImpl start: saveOccupation()");
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		ResultSet rs = null;
		Integer generatedId = 0;

		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_OCCUPATION,
					Statement.RETURN_GENERATED_KEYS);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setString(2, request.getOccupation().getDataField());
			stmtMain.setInt(3, request.getOccupation().getScore());
			stmtMain.setInt(4, 1); // Version
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getCreatedBy());
			stmtMain.setTimestamp(7, request.getUpdatedOn());
			stmtMain.setString(8, request.getUpdatedBy());
			stmtMain.executeUpdate();
			rs = stmtMain.getGeneratedKeys();

			while (rs.next()) {
				generatedId = rs.getInt(1);
			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_OCCUPATION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getOccupation().getDataField());
			stmtHistory.setInt(3, request.getOccupation().getScore());
			stmtHistory.setInt(4, 1); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();

		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(rs);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Update occupation. 1) Version is updated in main table using inner query.
	 * 
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private CustomCheckUpdateResponse updateOccupation(CustomCheckUpdateRequest request, Connection conn,CustomCheckUpdateResponse response) throws CustomCheckException {
		LOG.info("DBServiceImpl start: updateOccupation()");
		Boolean isDeleted = false;
		Integer version = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet resultSet = null;
		Integer generatedId = null;
		Integer isUpdated=null;

		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.UPDATE_OCCUPATION);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setInt(2, request.getOccupation().getScore());
			//stmtMain.setString(3, request.getOccupation().getDataField());
			stmtMain.setTimestamp(3, request.getCreatedOn());
			stmtMain.setString(4, request.getCreatedBy());
			stmtMain.setTimestamp(5, request.getUpdatedOn());
			stmtMain.setString(6, request.getUpdatedBy());
			stmtMain.setString(7, request.getOccupation().getDataField());
			isUpdated = stmtMain.executeUpdate();
			if(isUpdated == 0)
			{
				response.setOccupationStatus("INVALID DATA");
				return response;
			}
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_OCUPATION);
			stmtSelect.setString(1, request.getOccupation().getDataField());
			resultSet = stmtSelect.executeQuery();

			while (resultSet.next()) {
				version = Integer.parseInt(resultSet.getString("Version"));
				generatedId=Integer.parseInt(resultSet.getString("Id"));
			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_OCCUPATION_HISTORY);

			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getOccupation().getDataField());
			stmtHistory.setInt(3, request.getOccupation().getScore());
			stmtHistory.setInt(4, version); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		response.setOccupationStatus("UPDATED");
		return response;
	}

	/**
	 * Delete occupation.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean deleteOccupation(CustomCheckDeleteRequest request, Connection conn) throws CustomCheckException {
		LOG.info("DBServiceImpl start: deleteOccupation()");
		Boolean isDeleted = true;
		Integer version = 0;
		Integer score = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		Integer serialNo=null;

		try {
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_OCUPATION);
			stmtSelect.setString(1, request.getOccupation());
			rs = stmtSelect.executeQuery();

			while (rs.next()) {
				version = Integer.parseInt(rs.getString("Version"));
				score = Integer.parseInt(rs.getString("Score"));
				serialNo=Integer.parseInt(rs.getString("Id"));
			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_OCCUPATION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getOccupation());
			stmtHistory.setInt(3, score);
			stmtHistory.setInt(4, version + 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, serialNo);
			stmtHistory.executeUpdate();

			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.DELETE_FROM_OCCUPATION);
			stmtMain.setString(1, request.getOccupation());
			stmtMain.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				if (rs != null) {
					closeResultset(rs);
				}
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	
	/**
	 * Save source of fund.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean saveSourceOfFund(CustomCheckInsertRequest request, Connection conn) throws CustomCheckException {
		LOG.info("DBServiceImpl start: saveSourceOfFund()");
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		ResultSet rs = null;
		Integer generatedId = null;
		try {

			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_FUND,
					Statement.RETURN_GENERATED_KEYS);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setString(2, request.getSourceOfFund().getDataField());
			stmtMain.setInt(3, request.getSourceOfFund().getScore());
			stmtMain.setInt(4, 1); // Version
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getCreatedBy());
			stmtMain.setTimestamp(7, request.getUpdatedOn());
			stmtMain.setString(8, request.getUpdatedBy());
			stmtMain.executeUpdate();
			rs = stmtMain.getGeneratedKeys();

			while (rs.next()) {
				generatedId = rs.getInt(1);
			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_FUND_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getSourceOfFund().getDataField());
			stmtHistory.setInt(3, request.getSourceOfFund().getScore());
			stmtHistory.setInt(4, 1); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();

		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);

		} finally {
			try {
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Update source of fund.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private CustomCheckUpdateResponse updateSourceOfFund(CustomCheckUpdateRequest request, Connection conn,CustomCheckUpdateResponse response) throws CustomCheckException {
		LOG.info("DBServiceImpl start: updateSourceOfFund()");
		Integer version = 0;
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet resultSet=null;
		Integer generatedId=null;
		Integer isUpdated=null;

		try {
			;
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.UPDATE_SOURCE_OF_FUND);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setInt(2, request.getSourceOfFund().getScore());
			//stmtMain.setString(3, request.getSourceOfFund().getDataField());
			stmtMain.setTimestamp(3, request.getCreatedOn());
			stmtMain.setString(4, request.getCreatedBy());
			stmtMain.setTimestamp(5, request.getUpdatedOn());
			stmtMain.setString(6, request.getUpdatedBy());
			stmtMain.setString(7, request.getSourceOfFund().getDataField());
			isUpdated = stmtMain.executeUpdate();
			if(isUpdated == 0)
			{
				response.setSourceOfFundStatus("Invalid Data");
				return response;
			}

			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_SOURCE_OF_FUND);
			stmtSelect.setString(1, request.getSourceOfFund().getDataField());
			resultSet = stmtSelect.executeQuery();

			while (resultSet.next()) {
				version = Integer.parseInt(resultSet.getString("Version"));
				generatedId=Integer.parseInt(resultSet.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_FUND_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getSourceOfFund().getDataField());
			stmtHistory.setInt(3, request.getSourceOfFund().getScore());
			stmtHistory.setInt(4, version );
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		response.setSourceOfFundStatus("UPDATED");
		return response;
	}

	/**
	 * Delete source of fund.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean deleteSourceOfFund(CustomCheckDeleteRequest request, Connection conn) throws CustomCheckException {
		LOG.info("DBServiceImpl start: deleteSourceOfFund()");
		Boolean isDeleted = true;
		Integer version = 0;
		Integer score = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		Integer serialNo=null;
		try {
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_SOURCE_OF_FUND);
			stmtSelect.setString(1, request.getSourceOfFund());
			rs = stmtSelect.executeQuery();

			while (rs.next()) {
				version = Integer.parseInt(rs.getString("Version"));
				score = Integer.parseInt(rs.getString("Score"));
				serialNo=Integer.parseInt(rs.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_FUND_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getSourceOfFund());
			stmtHistory.setInt(3, score);
			stmtHistory.setInt(4, version + 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, serialNo);
			
			stmtHistory.executeUpdate();
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.DELETE_FROM_SOURCE_OF_FUND);
			if (stmtMain != null) {
				stmtMain.setString(1, request.getSourceOfFund());
				stmtMain.executeUpdate();
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				if (rs != null) {
					closeResultset(rs);
				}
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	
	/**
	 * Save purpose of transaction.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean savePurposeOfTransaction(CustomCheckInsertRequest request, Connection conn)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: savePurposeOfTransaction()");
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		ResultSet rs = null;
		Integer generatedId = null;

		try {

			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_PURPOSE_OF_TRANSACTION,
					Statement.RETURN_GENERATED_KEYS);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setString(2, request.getPurposeOfTransaction().getDataField());
			stmtMain.setInt(3, request.getPurposeOfTransaction().getScore());
			stmtMain.setInt(4, 1); // Version
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getCreatedBy());
			stmtMain.setTimestamp(7, request.getUpdatedOn());
			stmtMain.setString(8, request.getUpdatedBy());
			stmtMain.executeUpdate();
			rs = stmtMain.getGeneratedKeys();

			while (rs.next()) {
				generatedId = rs.getInt(1);
			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_PURPOSE_OF_TRANSACTION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getPurposeOfTransaction().getDataField());
			stmtHistory.setInt(3, request.getPurposeOfTransaction().getScore());
			stmtHistory.setInt(4, 1); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();

			commitTransaction(conn);
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Update purpose of transaction.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private CustomCheckUpdateResponse updatePurposeOfTransaction(CustomCheckUpdateRequest request, Connection conn,CustomCheckUpdateResponse response)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: updatePurposeOfTransaction()");
		Boolean isDeleted = false;
		Integer version = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet resultSet = null;
		Integer generatedId = null;
		Integer isUpdated=null;

		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.UPDATE_PURPOSE_OF_TRANSACTION);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setInt(2, request.getPurposeOfTransaction().getScore());
			//stmtMain.setString(3, request.getPurposeOfTransaction().getDataField());
			stmtMain.setTimestamp(3, request.getCreatedOn());
			stmtMain.setString(4, request.getCreatedBy());
			stmtMain.setTimestamp(5, request.getUpdatedOn());
			stmtMain.setString(6, request.getUpdatedBy());
			stmtMain.setString(7, request.getPurposeOfTransaction().getDataField());
			isUpdated =stmtMain.executeUpdate();
			if(isUpdated == 0)
			{
				response.setPurposeOfTransactionStatus("Invalid Data");
				return response;
			}

			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_PURPOSE_OF_TRANSACTION);
			stmtSelect.setString(1, request.getPurposeOfTransaction().getDataField());
			resultSet = stmtSelect.executeQuery();

			while (resultSet.next()) {
				version = Integer.parseInt(resultSet.getString("Version"));
				generatedId=Integer.parseInt(resultSet.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_PURPOSE_OF_TRANSACTION_HISTORY);

			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getPurposeOfTransaction().getDataField());
			stmtHistory.setInt(3, request.getPurposeOfTransaction().getScore());
			stmtHistory.setInt(4, version ); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		response.setPurposeOfTransactionStatus("UPDATED");
		return response;
	}

	/**
	 * Delete purpose of transaction.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean deletePurposeOfTransaction(CustomCheckDeleteRequest request, Connection conn)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: deletePurposeOfTransaction()");
		Boolean isDeleted = true;
		Integer version = 0;
		Integer score = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		Integer serialNo=null;

		try {
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_PURPOSE_OF_TRANSACTION);
			stmtSelect.setString(1, request.getPurposeOfTransaction());
			rs = stmtSelect.executeQuery();

			while (rs.next()) {
				version = Integer.parseInt(rs.getString("Version"));
				score = Integer.parseInt(rs.getString("Score"));
				serialNo=Integer.parseInt(rs.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_PURPOSE_OF_TRANSACTION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getPurposeOfTransaction());
			stmtHistory.setInt(3, score);
			stmtHistory.setInt(4, version + 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, serialNo);
			stmtHistory.executeUpdate();

			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.DELETE_FROM_PURPOSE_OF_TRANSACTION);
			stmtMain.setString(1, request.getPurposeOfTransaction());
			stmtMain.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				if (rs != null) {
					closeResultset(rs);
				}
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	

	/**
	 * Save countries of trade.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean saveCountriesOfTrade(CustomCheckInsertRequest request, Connection conn)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: saveCountriesOfTrade()");
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		ResultSet rs = null;
		Integer generatedId = null;

		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_COUNTRIES_OF_TRADE,
					Statement.RETURN_GENERATED_KEYS);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setString(2, request.getCountriesOfTrade().getDataField());
			stmtMain.setInt(3, request.getCountriesOfTrade().getScore());
			stmtMain.setInt(4, 1); // Version
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getCreatedBy());
			stmtMain.setTimestamp(7, request.getUpdatedOn());
			stmtMain.setString(8, request.getUpdatedBy());
			stmtMain.executeUpdate();
			rs = stmtMain.getGeneratedKeys();

			while (rs.next()) {
				generatedId = rs.getInt(1);

			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_COUNTRIES_OF_TRADE_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getCountriesOfTrade().getDataField());
			stmtHistory.setInt(3, request.getCountriesOfTrade().getScore());
			stmtHistory.setInt(4, 1); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();
			commitTransaction(conn);
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Update countries of trade.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private CustomCheckUpdateResponse updateCountriesOfTrade(CustomCheckUpdateRequest request, Connection conn,CustomCheckUpdateResponse response)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: updateCountriesOfTrade()");
		Boolean isDeleted = false;
		Integer version = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet resultSet = null;
		Integer generatedId = null;
		Integer isUpdated=null;
		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.UPDATE_COUNTRIES_OF_TRADE);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setInt(2, request.getCountriesOfTrade().getScore());
			//stmtMain.setString(3, request.getCountriesOfTrade().getDataField());
			stmtMain.setTimestamp(3, request.getCreatedOn());
			stmtMain.setString(4, request.getCreatedBy());
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getUpdatedBy());
			stmtMain.setString(7, request.getCountriesOfTrade().getDataField());
			isUpdated = stmtMain.executeUpdate();
			if(isUpdated == 0)
			{
				response.setCountriesOfTradeStatus("Invalid Data");
				return response;
			}

			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_COUNTRIES_OF_TRADE);
			stmtSelect.setString(1, request.getCountriesOfTrade().getDataField());
			resultSet = stmtSelect.executeQuery();

			while (resultSet.next()) {
				version = Integer.parseInt(resultSet.getString("Version"));
				generatedId=Integer.parseInt(resultSet.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_COUNTRIES_OF_TRADE_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getCountriesOfTrade().getDataField());
			stmtHistory.setInt(3, request.getCountriesOfTrade().getScore());
			stmtHistory.setInt(4, version); // Version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();

		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		response.setCountriesOfTradeStatus("UPDATED");
		return response;
	}

	/**
	 * Delete countries of trade.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean deleteCountriesOfTrade(CustomCheckDeleteRequest request, Connection conn)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: deleteCountriesOfTrade()");
		Boolean isDeleted = true;
		Integer version = 0;
		Integer score = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		Integer serialNo=null;

		try {
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_COUNTRIES_OF_TRADE);
			stmtSelect.setString(1, request.getCountriesOfTrade());
			rs = stmtSelect.executeQuery();

			while (rs.next()) {
				version = Integer.parseInt(rs.getString("Version"));
				score = Integer.parseInt(rs.getString("Score"));
				serialNo=Integer.parseInt(rs.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_COUNTRIES_OF_TRADE_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getCountriesOfTrade());
			stmtHistory.setInt(3, score);
			stmtHistory.setInt(4, version + 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8,serialNo);
			stmtHistory.executeUpdate();
			
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.DELETE_FROM_COUNTRIES_OF_TRADE);
			stmtMain.setString(1, request.getCountriesOfTrade());
			stmtMain.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				if (rs != null) {
					closeResultset(rs);
				}
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	

	/**
	 * Save value of transaction.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean saveValueOfTransaction(CustomCheckInsertRequest request, Connection conn)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: saveValueOfTransaction()");
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		ResultSet rs = null;
		Integer generatedId = null;

		try {

			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_VALUE_OF_TRANSACTION,
					Statement.RETURN_GENERATED_KEYS);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setString(2, request.getValueOfTransaction().getDataField());
			stmtMain.setInt(3, request.getValueOfTransaction().getScore());
			stmtMain.setInt(4, 1); // Version
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getCreatedBy());
			stmtMain.setTimestamp(7, request.getUpdatedOn());
			stmtMain.setString(8, request.getUpdatedBy());
			stmtMain.executeUpdate();
			rs = stmtMain.getGeneratedKeys();

			while (rs.next()) {
				generatedId = rs.getInt(1);
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_VALUE_OF_TRANSACTION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getValueOfTransaction().getDataField());
			stmtHistory.setInt(3, request.getValueOfTransaction().getScore());
			stmtHistory.setInt(4, 1);// version
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();

		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Update value of transaction.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private CustomCheckUpdateResponse updateValueOfTransaction(CustomCheckUpdateRequest request, Connection conn,CustomCheckUpdateResponse response)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: updateValueOfTransaction()");
		Boolean isDeleted = false;
		Integer version = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet resultSet=null;
		Integer generatedId=null;
		Integer isUpdated=null;

		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.UPDATE_VALUE_OF_TRANSACTION );
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setInt(2, request.getValueOfTransaction().getScore());
		//	stmtMain.setString(3, request.getValueOfTransaction().getDataField());
			stmtMain.setTimestamp(3, request.getCreatedOn());
			stmtMain.setString(4, request.getCreatedBy());
			stmtMain.setTimestamp(5, request.getUpdatedOn());
			stmtMain.setString(6, request.getUpdatedBy());
			stmtMain.setString(7, request.getValueOfTransaction().getDataField());
			isUpdated =stmtMain.executeUpdate();
			if(isUpdated == 0)
			{
				response.setValueOfTransactionStatus("Invalid Data");
				return response;
			}
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_VALUE_OF_TRANSACTION);
			stmtSelect.setString(1, request.getValueOfTransaction().getDataField());
			resultSet= stmtSelect.executeQuery();
			while (resultSet.next()) {
				version = Integer.parseInt(resultSet.getString("Version"));
				generatedId=Integer.parseInt(resultSet.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_VALUE_OF_TRANSACTION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getValueOfTransaction().getDataField());
			stmtHistory.setInt(3, request.getValueOfTransaction().getScore());
			stmtHistory.setInt(4, version);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		response.setValueOfTransactionStatus("UPDATED");
		return response;
	}

	/**
	 * Delete value of transaction.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean deleteValueOfTransaction(CustomCheckDeleteRequest request, Connection conn)
			throws CustomCheckException {
		LOG.info("DBServiceImpl start: deleteValueOfTransaction()");
		Boolean isDeleted = true;
		Integer version = 0;
		Integer score = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		Integer serialNo=null;

		try {
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_VALUE_OF_TRANSACTION);
			stmtSelect.setString(1, request.getValueOfTransaction());
			rs = stmtSelect.executeQuery();

			while (rs.next()) {
				version = Integer.parseInt(rs.getString("Version"));
				score = Integer.parseInt(rs.getString("Score"));
				serialNo=Integer.parseInt(rs.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_VALUE_OF_TRANSACTION_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getValueOfTransaction());
			stmtHistory.setInt(3, score);
			stmtHistory.setInt(4, version + 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, serialNo);
			
			stmtHistory.executeUpdate();
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.DELETE_FROM_VALUE_OF_TRANSACTION);
			stmtMain.setString(1, request.getValueOfTransaction());
			stmtMain.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				if (rs != null) {
					closeResultset(rs);
				}
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	
	/**
	 * Save source of lead.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean saveSourceOfLead(CustomCheckInsertRequest request, Connection conn) throws CustomCheckException {
		LOG.info("DBServiceImpl start: saveSource()");
		Boolean isDeleted = false;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		ResultSet rs = null;
		Integer generatedId = null;
		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_LEAD,
					Statement.RETURN_GENERATED_KEYS);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setString(2, request.getSourceOfLead().getDataField());
			stmtMain.setInt(3, request.getSourceOfLead().getScore());
			stmtMain.setInt(4, 1); // Version
			stmtMain.setTimestamp(5, request.getCreatedOn());
			stmtMain.setString(6, request.getCreatedBy());
			stmtMain.setTimestamp(7, request.getUpdatedOn());
			stmtMain.setString(8, request.getUpdatedBy());
			stmtMain.executeUpdate();
			rs = stmtMain.getGeneratedKeys();

			while (rs.next()) {
				generatedId = rs.getInt(1);
			}

			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_LEAD_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getSourceOfLead().getDataField());
			stmtHistory.setInt(3, request.getSourceOfLead().getScore());
			stmtHistory.setInt(4, 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();

		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}

	/**
	 * Update source of lead.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private CustomCheckUpdateResponse updateSourceOfLead(CustomCheckUpdateRequest request, Connection conn,CustomCheckUpdateResponse response) throws CustomCheckException {
		LOG.info("DBServiceImpl start: updateSource()");
		Boolean isDeleted = false;
		Integer version = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet resultSet = null;
		Integer generatedId = null;
		Integer isUpdated=null;

		try {
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.UPDATE_SOURCE_OF_LEAD);
			stmtMain.setString(1, request.getOrganizationCode());
			stmtMain.setInt(2, request.getSourceOfLead().getScore());
		//	stmtMain.setString(3, request.getSourceOfLead().getDataField());
			stmtMain.setTimestamp(3, request.getCreatedOn());
			stmtMain.setString(4, request.getCreatedBy());
			stmtMain.setTimestamp(5, request.getUpdatedOn());
			stmtMain.setString(6, request.getUpdatedBy());
			stmtMain.setString(7, request.getSourceOfLead().getDataField());
			isUpdated =stmtMain.executeUpdate();
			if(isUpdated == 0)
			{
				response.setSourceOfLeadStatus("Invalid Data");
				return response;
			}
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_SOURCE_OF_LEAD);
			stmtSelect.setString(1, request.getSourceOfLead().getDataField());
			resultSet = stmtSelect.executeQuery();

			while (resultSet.next()) {
				version = Integer.parseInt(resultSet.getString("Version"));
				generatedId=Integer.parseInt(resultSet.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_LEAD_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getSourceOfLead().getDataField());
			stmtHistory.setInt(3, request.getSourceOfLead().getScore());
			stmtHistory.setInt(4, version);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, generatedId);
			stmtHistory.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				closeResultset(resultSet);
				closePrepareStatement(stmtMain);
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		response.setSourceOfLeadStatus("UPDATED");
		return response;
	}

	/**
	 * Delete source of lead.
	 *
	 * @param request
	 *            the request
	 * @param conn
	 *            the conn
	 * @return the boolean
	 * @throws CustomCheckException
	 *             the custom check exception
	 */
	private Boolean deleteSourceOfLead(CustomCheckDeleteRequest request, Connection conn) throws CustomCheckException {
		LOG.info("DBServiceImpl start: deleteSource()");
		Boolean isDeleted = true;
		Integer version = 0;
		Integer score = 0;
		PreparedStatement stmtMain = null;
		PreparedStatement stmtHistory = null;
		PreparedStatement stmtSelect = null;
		ResultSet rs = null;
		Integer serialNo=null;
		

		try {
			stmtSelect = conn.prepareStatement(CustomCheckQueryConstant.SELECT_VERSION_FROM_SOURCE_OF_LEAD);
			stmtSelect.setString(1, request.getSourceOfLead());
			rs = stmtSelect.executeQuery();

			while (rs.next()) {
				version = Integer.parseInt(rs.getString("Version"));
				score = Integer.parseInt(rs.getString("Score"));
				serialNo=Integer.parseInt(rs.getString("Id"));
			}
			stmtHistory = conn.prepareStatement(CustomCheckQueryConstant.INSERT_INTO_SOURCE_OF_LEAD_HISTORY);
			stmtHistory.setString(1, request.getOrganizationCode());
			stmtHistory.setString(2, request.getSourceOfLead());
			stmtHistory.setInt(3, score);
			stmtHistory.setInt(4, version + 1);
			stmtHistory.setTimestamp(5, request.getCreatedOn());
			stmtHistory.setString(6, request.getCreatedBy());
			stmtHistory.setBoolean(7, isDeleted);
			stmtHistory.setInt(8, serialNo);
			
			stmtHistory.executeUpdate();
			stmtMain = conn.prepareStatement(CustomCheckQueryConstant.DELETE_FROM_SOURCE_OF_LEAD);
			stmtMain.setString(1, request.getSourceOfLead());
			stmtMain.executeUpdate();
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			try {
				if (rs != null) {
					closeResultset(rs);
				}
				closePrepareStatement(stmtSelect);
				closePrepareStatement(stmtHistory);
				closePrepareStatement(stmtMain);
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.DATABASE_GENERIC_ERROR, e);
			}
		}
		return true;
	}


	@Override
	public ConcurrentHashMap<String, Integer> getAllFromOccupation() throws CustomCheckException {
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(CustomCheckQueryConstant.SELECT_FROM_OCUPATION);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String key = rs.getString("Occupation");
				Integer score = Integer.parseInt(rs.getString("Score"));
				hashMap.put(key, score);
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		} finally {
			try {
				if (stmt != null) {
					closePrepareStatement(stmt);
					closeConnection(conn);
				}
				if (rs != null) {
					closeResultset(rs);
				}
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
			}
		}
		return hashMap;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getAllFromSourceOfLead() throws CustomCheckException {
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(CustomCheckQueryConstant.SELECT_FROM_SOURCE_OF_LEAD);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String key = rs.getString("SourceOfLead");
				Integer score = Integer.parseInt(rs.getString("Score"));
				hashMap.put(key, score);
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		} finally {
			try {
				if (stmt != null) {
					closePrepareStatement(stmt);
					closeConnection(conn);
				}
				if (rs != null) {
					closeResultset(rs);
				}
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
			}
		}
		return hashMap;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getAllFromSourceOfFund() throws CustomCheckException {
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(CustomCheckQueryConstant.SELECT_FROM_SOURCE_OF_FUND);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String key = rs.getString("SourceOfFund");
				Integer score = Integer.parseInt(rs.getString("Score"));
				hashMap.put(key, score);
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		} finally {
			try {
				if (stmt != null) {
					closePrepareStatement(stmt);
					closeConnection(conn);
				}
				if (rs != null) {
					closeResultset(rs);
				}
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
			}
		}
		return hashMap;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getAllFromValueOfTransaction() throws CustomCheckException {
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(CustomCheckQueryConstant.SELECT_FROM_VALUE_OF_TRANSACTION);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String key = rs.getString("ValueOfTransaction");
				Integer score = Integer.parseInt(rs.getString("Score"));
				hashMap.put(key, score);
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		} finally {
			try {
				if (stmt != null) {
					closePrepareStatement(stmt);
					closeConnection(conn);
				}
				if (rs != null) {
					closeResultset(rs);
				}
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
			}
		}
		return hashMap;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getAllFromPurposeOfTransaction() throws CustomCheckException {
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(CustomCheckQueryConstant.SELECT_FROM_PURPOSE_OF_TRANSACTION);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String key = rs.getString("PurposeOfTransaction");
				Integer score = Integer.parseInt(rs.getString("Score"));
				hashMap.put(key, score);
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		} finally {
			try {
				if (stmt != null) {
					closePrepareStatement(stmt);
					closeConnection(conn);
				}
				if (rs != null) {
					closeResultset(rs);
				}
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
			}
		}
		return hashMap;
	}

	@Override
	public ConcurrentHashMap<String, Integer> getAllFromCountriesOfTrade() throws CustomCheckException {
		ConcurrentHashMap<String, Integer> hashMap = new ConcurrentHashMap<String, Integer>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(CustomCheckQueryConstant.SELECT_FROM_COUNTRIES_OF_TRADE);
			rs = stmt.executeQuery();

			while (rs.next()) {
				String key = rs.getString("CountriesOfTrade");
				Integer score = Integer.parseInt(rs.getString("Score"));
				hashMap.put(key, score);
			}
		} catch (Exception e) {
			throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
		} finally {
			try {
				if (stmt != null) {
					closePrepareStatement(stmt);
					closeConnection(conn);
				}
				if (rs != null) {
					closeResultset(rs);
				}
			} catch (Exception e) {
				throw new CustomCheckException(CustomCheckErrors.ERROR_WHILE_FETCHING_DATA_FROM_DB_TO_CACHE, e);
			}
		}
		return hashMap;
	}

}