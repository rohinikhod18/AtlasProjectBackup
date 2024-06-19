package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.dbport.report.WorkEfficiencyReportQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;

/**
 * The Class AbstractDBServiceAnonymisation.
 */
public class AbstractDBServiceAnonymisation extends AbstractDBServiceLevel2 {

	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel1#getLockedUserName(java.sql.Connection)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel1#addSort(java.lang.String, boolean, java.lang.String)
	 */
	@Override
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

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {
		return null;
	}
	
	/**
	 * Gets the other contacts.
	 *
	 * @param accountId the account id
	 * @param contactId the contact id
	 * @param connection the connection
	 * @return the other contacts
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<ContactWrapper> getOtherContacts(Integer accountId, Integer contactId, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_OTHER_CONTACT_DETAILS.getQuery());
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, contactId);
			List<ContactWrapper> otherContacts = new ArrayList<>();
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ContactWrapper contact = new ContactWrapper();
				contact.setId(rs.getInt("Id"));
				contact.setName(rs.getString("Name"));
				contact.setComplianceStatus(rs.getString("complianceStatus"));
				contact.setCustType(rs.getString("CustomerType"));
				contact.setPreviousBlacklistStatus(
						ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt("BlacklistStatus")));
				contact.setPreviousSanctionStatus(
						ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt("SanctionStatus")));
				otherContacts.add(contact);
			}
			return otherContacts;

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}

	}
	
	/**
	 * Gets the account watchlist with selected.
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
				boolean watchlistDataValue = false;
				if (contactIdDb != -1) {
					watchlistDataValue = true;
				}
				watchlistData.setValue(watchlistDataValue);
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
		String accountWatchlistQuery = "";
		int result = 0;
		if ((category1 == category2)) {
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
}
