package com.currenciesdirect.gtg.compliance.dbport.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearchRequest;
import com.currenciesdirect.gtg.compliance.core.report.ISavedSearchDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.dbport.SavedSearchQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;

@Component("savedSearchDBServiceImpl")
public class SavedSearchDBServiceImpl extends AbstractDao implements ISavedSearchDBService{

	public boolean savedSearch(UserProfile user,SavedSearchRequest savedSearchRequest) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String searchCriteria = JsonConverterUtil.convertToJsonWithoutNull(savedSearchRequest.getFilter());
		try{
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(SavedSearchQueryConstant.INSERT_INTO_SAVE_SEARCH.getQuery());
			preparedStatement.setString(1,savedSearchRequest.getPageType());
			preparedStatement.setString(2,savedSearchRequest.getSaveSearchName());
			preparedStatement.setString(3,searchCriteria);
			preparedStatement.setInt(4,user.getId());
			preparedStatement.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(6,user.getId());
			preparedStatement.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
			int updateCount = preparedStatement.executeUpdate();
			if (updateCount == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
			return true;
		} catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		}
		finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}
	
	public boolean deleteSavedSearch(UserProfile user,SavedSearchRequest savedSearchRequest) throws CompliancePortalException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(SavedSearchQueryConstant.DELETE_FROM_SAVE_SEARCH.getQuery());
			preparedStatement.setString(1,savedSearchRequest.getPageType());
			preparedStatement.setInt(2, user.getId());
			preparedStatement.setString(3, savedSearchRequest.getSaveSearchName());
			int updateCount = preparedStatement.executeUpdate();
			if (updateCount == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
			return true;
		} catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_DELETING_DATA, e);
		}
		finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}
	
	public boolean updateSavedSearch(UserProfile user,SavedSearchRequest savedSearchRequest) throws CompliancePortalException{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		String searchCriteria = JsonConverterUtil.convertToJsonWithoutNull(savedSearchRequest.getFilter());
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(SavedSearchQueryConstant.UPDATE_SAVE_SEARCH.getQuery());
			preparedStatement.setString(1, searchCriteria);
			preparedStatement.setString(2,savedSearchRequest.getPageType());
			preparedStatement.setInt(3, user.getId());
			preparedStatement.setString(4, savedSearchRequest.getSaveSearchName());
			int updateCount = preparedStatement.executeUpdate();
			if (updateCount == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}
			return true;
		} catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
		}
		finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}
	
	public int getSavedSearchCount(String pageType, int id) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		int count = 0;
		try {
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(SavedSearchQueryConstant.SAVE_SEARCH_COUNT.getQuery());
			preparedStatement.setString(1,pageType);
			preparedStatement.setInt(2, id);
			rs = preparedStatement.executeQuery();
			if(rs.next()) {
				count = rs.getInt(1);
			}
			return count;
		} catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}
}
