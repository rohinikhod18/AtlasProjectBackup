package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import com.currenciesdirect.gtg.compliance.core.domain.BaseUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceStatus;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.GlobalCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Intuition;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.core.domain.IpCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.Onfido;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.UploadDocumentTypeDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.IpSummary;
import com.currenciesdirect.gtg.compliance.core.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.EntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FragusterWatchListEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.FraugsterReasonsEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.dbport.report.WorkEfficiencyReportQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;


/**
 * The Class AbstractDBServiceRegistration.
 */
@SuppressWarnings("squid:S2095")
public abstract class AbstractDBServiceRegistration extends AbstractDBServiceLevel2 {

	protected Watchlist getAccountWatchlistWithSelected(Integer contactId, Connection connection, UserProfile profile)
			throws CompliancePortalException {

		Watchlist watchlist = new Watchlist();
		List<WatchListData> watchlistDataList = new ArrayList<>();
		watchlist.setWatchlistData(watchlistDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		boolean category1 = profile.getPermissions().getCanManageWatchListCategory1();
		boolean category2 = profile.getPermissions().getCanManageWatchListCategory2();
		try {
			statement = getPrepareStatementForWatchlist(contactId, connection, category1, category2);
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
	 * @param accountId
	 * @param connection
	 * @param category1
	 * @param category2
	 * @return
	 * @throws SQLException
	 */
	private PreparedStatement getPrepareStatementForWatchlist(Integer contactId, Connection connection,
			boolean category1, boolean category2) throws SQLException {
		PreparedStatement statement;
		// if only one of category is enabled, then add second param to
		// query
		// else no filter needed
		String accountWatchlistQuery = "";
		int result = 0;
		if ((category1 == category2)) {
			accountWatchlistQuery = RegistrationQueryConstant.GET_CONTACT_WATCHLIST_WITH_SELECTED.getQuery();
		} else {
			accountWatchlistQuery = RegistrationQueryConstant.GET_CONTACT_WATCHLIST_CATEGORY_WITH_SELECTED.getQuery();
			if (category1 && !category2) {
				result = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus();

			} else if (!category1 && category2) {
				result = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus();
			}
		}

		statement = connection.prepareStatement(accountWatchlistQuery);

		statement.setInt(1, contactId);
		if ((!category1 && category2) || (category1 && !category2))
			statement.setInt(2, result);
		return statement;
	}

	/**
	 * Gets the other contacts.
	 *
	 * @param accountId
	 *            the account id
	 * @param contactId
	 *            the contact id
	 * @param connection
	 *            the connection
	 * @return the other contacts
	 * @throws CompliancePortalException
	 *             the compliance portal exception
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
	 * Update Account Status
	 * 
	 * @param requestHandlerDto
	 * @param accountStatus
	 * @param connection
	 * @return
	 * @throws CompliancePortalException
	 */
	protected Boolean updateAccountStatus(RegUpdateDBDto requestHandlerDto, String accountStatus, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		Boolean accountStatusActive = Boolean.FALSE;
		try {
			Integer accountId = requestHandlerDto.getAccountId();
			String complianceDoneOn = requestHandlerDto.getComplianceDoneOn();
			String complianceExpiry = requestHandlerDto.getComplianceExpiry();
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_ACCOUNT_STATUS.getQuery());
			preparedStatement.setString(1, accountStatus);
			preparedStatement.setString(2, accountStatus);
			accountStatusActive = accountStatus.equals(ComplianceStatus.ACTIVE.name());
			if (Boolean.TRUE.equals(accountStatusActive) && (null == complianceDoneOn || Constants.DASH_UI.equals(complianceDoneOn))) {
				preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				Calendar c = Calendar.getInstance();
				c.setTime(new Timestamp(System.currentTimeMillis()));
				c.add(Calendar.YEAR, SearchCriteriaUtil.getComplianceExpiryYears());
				preparedStatement.setTimestamp(4, new Timestamp(c.getTimeInMillis()));
				requestHandlerDto.setComplianceDoneOn(new Timestamp(System.currentTimeMillis()).toString());
				requestHandlerDto.setComplianceExpiry(new Timestamp(c.getTimeInMillis()).toString());
			} else {
				setComplianceFields(requestHandlerDto, preparedStatement, complianceDoneOn, complianceExpiry);
			}
			preparedStatement.setBoolean(5, requestHandlerDto.getIsAccountOnQueue());
			preparedStatement.setInt(6, accountId);

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
	 * Set Compliance Fields
	 * 
	 * @param requestHandlerDto
	 * @param preparedStatement
	 * @param complianceDoneOn
	 * @param complianceExpiry
	 * @throws ParseException
	 * @throws SQLException
	 */
	private void setComplianceFields(RegUpdateDBDto requestHandlerDto, PreparedStatement preparedStatement,
			String complianceDoneOn, String complianceExpiry) throws ParseException, SQLException {
		Timestamp complianceDoneOnTimestamp = DateTimeFormatter.getTimestamp(complianceDoneOn);
		Timestamp complianceExpiryTimestamp = DateTimeFormatter.getTimestamp(complianceExpiry);
		if (null == complianceDoneOn || Constants.DASH_UI.equals(complianceDoneOn)) {
			preparedStatement.setNull(3, Types.TIMESTAMP);
			preparedStatement.setNull(4, Types.TIMESTAMP);
			requestHandlerDto.setComplianceDoneOn(null);
			requestHandlerDto.setComplianceExpiry(null);
		} else {
			preparedStatement.setTimestamp(3, complianceDoneOnTimestamp);
			preparedStatement.setTimestamp(4, complianceExpiryTimestamp);
			requestHandlerDto.setComplianceDoneOn(complianceDoneOnTimestamp.toString());
			requestHandlerDto.setComplianceExpiry(complianceExpiryTimestamp.toString());
		}
	}

	/**
	 * Gets the more kyc details.
	 *
	 * @param resultSet the result set
	 * @return the more kyc details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<IDomain> getMoreKycDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Kyc kyc = new Kyc();
				KYCSummary kycSummary = JsonConverterUtil.convertToObject(KYCSummary.class,
						resultSet.getString(Constants.SUMMARY));
				kyc.setId(resultSet.getInt("id")); // ESL id
				kyc.setCheckedOn(DateTimeFormatter.dateTimeFormatter(kycSummary.getCheckedOn()));
				kyc.setDob(kycSummary.getDob());
				kyc.setEidCheck(kycSummary.getEidCheck());
				kyc.setVerifiactionResult(kycSummary.getVerifiactionResult());
				kyc.setReferenceId(kycSummary.getReferenceId());
				boolean status = Constants.PASS.equals(kycSummary.getStatus());
				/** Added changes to set status as Not_Required on UI -Saylee */
				if (Constants.NOT_REQUIRED.equalsIgnoreCase(kycSummary.getStatus())) {
					kyc.setIsRequired(Boolean.FALSE);
					kyc.setStatusValue(Constants.NOT_REQUIRED_UI);
				} else {
					kyc.setIsRequired(Boolean.TRUE);
					kyc.setStatusValue(kycSummary.getStatus());
					kyc.setStatus(status);
				}
				kyc.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				domainList.add((IDomain) kyc);
			}
			return domainList;

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}

	}

	/**
	 * Gets the more sanction details.
	 *
	 * @param resultSet the result set
	 * @return the more sanction details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<IDomain> getMoreSanctionDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Sanction sanc = new Sanction();
				SanctionSummary sancSummary = JsonConverterUtil.convertToObject(SanctionSummary.class,
						resultSet.getString(Constants.SUMMARY));
				sanc.setUpdatedBy(resultSet.getString(Constants.UPDATED_BY));
				sanc.setEventServiceLogId(resultSet.getInt("id")); // Set esl id
				sanc.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				sanc.setSanctionId(sancSummary.getSanctionId());
				sanc.setOfacList(sancSummary.getOfacList());
				sanc.setWorldCheck(sancSummary.getWorldCheck());
				boolean status = "PASS".equals(sancSummary.getStatus());
				/** Added changes to set status as Not_Required on UI -Saylee */
				if (Constants.NOT_REQUIRED.equalsIgnoreCase(sancSummary.getStatus())) {
					sanc.setIsRequired(Boolean.FALSE);
					sanc.setStatusValue(Constants.NOT_REQUIRED_UI);
				} else {
					sanc.setIsRequired(Boolean.TRUE);
					sanc.setStatusValue(sancSummary.getStatus());
					sanc.setStatus(status);
				}
				sanc.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				domainList.add((IDomain) sanc);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}

	}

	/**
	 * Gets the more fraugster details.
	 *
	 * @param resultSet the result set
	 * @return the more fraugster details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<IDomain> getMoreFraugsterDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Fraugster fraug = new Fraugster();
				FraugsterSummary fraugSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
						resultSet.getString(Constants.SUMMARY));
				boolean status = Constants.PASS.equals(fraugSummary.getStatus());
				fraug.setCreatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				fraug.setUpdatedBy(resultSet.getString(Constants.UPDATED_BY));
				fraug.setFraugsterId(fraugSummary.getFrgTransId());
				fraug.setScore(fraugSummary.getScore());
				fraug.setStatus(status);
				fraug.setId(resultSet.getInt("id")); // fix AT-373 - Umesh
				domainList.add((IDomain) fraug);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel1#getOrganization(java.sql.Connection)
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel1#getCurrency(java.sql.Connection)
	 */
	@Override
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
	 * Gets the more custom check details.
	 *
	 * @param resultSet the result set
	 * @return the more custom check details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<IDomain> getMoreCustomCheckDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			Map<Integer, CustomCheck> hm = new TreeMap<>();

			while (resultSet.next()) {
				CustomCheck cuschk = hm.get(resultSet.getInt(RegQueDBColumns.EVENTID.getName()));
				if (cuschk == null) {
					cuschk = new CustomCheck();
					getMoreCustomCheckServiceDetails(resultSet, cuschk);
					hm.put(resultSet.getInt(RegQueDBColumns.EVENTID.getName()), cuschk);
				} else {
					getMoreCustomCheckServiceDetails(resultSet, cuschk);
					hm.put(resultSet.getInt(RegQueDBColumns.EVENTID.getName()), cuschk);
				}
			}
			domainList.addAll(hm.values());
			Collections.reverse(domainList);
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	/**
	 * Gets the more custom check service details.
	 *
	 * @param resultSet the result set
	 * @param cuschk the cuschk
	 * @return the more custom check service details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected CustomCheck getMoreCustomCheckServiceDetails(ResultSet resultSet, CustomCheck cuschk)
			throws CompliancePortalException {

		try {
			if (("IP").equals(resultSet.getString(RegQueDBColumns.SERVICETYPE.getName()))
					&& cuschk.getIpCheck() == null) {
				IpCheck ipCheck = new IpCheck();
				ipCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				ipCheck.setStatus(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(resultSet.getInt(RegQueDBColumns.STATUS.getName())));
				IpSummary ipSummary = JsonConverterUtil.convertToObject(IpSummary.class,
						resultSet.getString(Constants.SUMMARY));
				if (!Constants.NOT_REQUIRED.equalsIgnoreCase(ipSummary.getStatus())) {
					ipCheck.setIpAddress(ipSummary.getIpAddress());
					ipCheck.setIpRule(ipSummary.getIpRule());
					ipCheck.setStatus(ipSummary.getStatus());
					ipCheck.setIsRequired(Boolean.TRUE);

					checkIPStatus(ipCheck, ipSummary);

				}
				cuschk.setIpCheck(ipCheck);

			} else if (("GLOBALCHECK").equals(resultSet.getString(RegQueDBColumns.SERVICETYPE.getName()))
					&& cuschk.getGlobalCheck() == null) {
				GlobalCheck globalCheck = new GlobalCheck();
				globalCheck.setStatus(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(resultSet.getInt(RegQueDBColumns.STATUS.getName())));
				cuschk.setGlobalCheck(globalCheck);

			} else if (("COUNTRYCHECK").equals(resultSet.getString(RegQueDBColumns.SERVICETYPE.getName()))
					&& cuschk.getCountryCheck() == null) {
				CountryCheck countryCheck = new CountryCheck();
				countryCheck.setStatus(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(resultSet.getInt(RegQueDBColumns.STATUS.getName())));
				cuschk.setCountryCheck(countryCheck);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		return cuschk;
	}

	/**
	 * Check IP status.
	 *
	 * @param ipCheck the ip check
	 * @param ipSummary the ip summary
	 */
	private void checkIPStatus(IpCheck ipCheck, IpSummary ipSummary) {
		if (ipCheck.getStatus().equalsIgnoreCase(Constants.PASS)
				|| ipCheck.getStatus().equalsIgnoreCase(Constants.FAIL)) {
			ipCheck.setIpCity(Constants.IP_CITY_FIELD + StringUtils.isNullOrTrimEmptySetNA(ipSummary.getIpCity())
					+ Constants.COMMA);
			ipCheck.setIpCountry(Constants.IP_COUNTRY_FIELD
					+ StringUtils.isNullOrTrimEmptySetNA(ipSummary.getIpCountry()) + Constants.COMMA);
			ipCheck.setGeoDifference(Constants.IP_DISTANCE_FIELD
					+ StringUtils
							.isNullOrTrimEmptySetNA(DecimalFormatter.convertToOneDigit(ipSummary.getGeoDifference()))
					+ StringUtils.isNullOrTrimEmptySetSpace(ipSummary.getUnit()) + Constants.CLOSING_BRACKET);
		}
	}

	/**
	 * Insert Profile Activity Logs
	 * 
	 * @param activityLogDtos
	 * @param connection
	 * @throws CompliancePortalException
	 */
	@Override
	@SuppressWarnings("resource")
	public void insertProfileActivityLogs(List<ProfileActivityLogDto> profileActivityLogDtos, Connection connection)
			throws CompliancePortalException {
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = connection.prepareStatement(
					RegistrationQueryConstant.INSERT_PROFILE_ACTIVITY_LOG.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (ProfileActivityLogDto profileActivityLog : profileActivityLogDtos) {
				prepareStatement.setTimestamp(1, profileActivityLog.getTimeStatmp());
				prepareStatement.setNString(2, profileActivityLog.getActivityBy());
				prepareStatement.setNString(3, profileActivityLog.getOrgCode());
				prepareStatement.setInt(4, profileActivityLog.getAccountId());
				if (null == profileActivityLog.getContactId()) {
					prepareStatement.setNull(5, Types.INTEGER);
				} else {
					prepareStatement.setInt(5, profileActivityLog.getContactId());
				}
				prepareStatement.setNString(6, profileActivityLog.getComment());
				prepareStatement.setNString(7, profileActivityLog.getCreatedBy());
				prepareStatement.setTimestamp(8, profileActivityLog.getCreatedOn());
				prepareStatement.setNString(9, profileActivityLog.getUpdatedBy());
				prepareStatement.setTimestamp(10, profileActivityLog.getUpdatedOn());
				// AT-894 WorkEfficiencyReport
				// Add ResouceType and ResourceID
				if (null == profileActivityLog.getContactId()) {
					prepareStatement.setInt(11, UserLockResourceTypeEnum.ACCOUNT.getDatabaseStatus());
					prepareStatement.setInt(12, profileActivityLog.getAccountId());
				} else {
					prepareStatement.setInt(11, UserLockResourceTypeEnum.CONTACT.getDatabaseStatus());
					prepareStatement.setInt(12, profileActivityLog.getContactId());
				}

				int updateCount = prepareStatement.executeUpdate();
				if (updateCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
				ResultSet rs = prepareStatement.getGeneratedKeys();
				if (rs.next()) {
					profileActivityLog.setId(rs.getInt(1));
					profileActivityLog.getActivityLogDetailDto().setActivityId(rs.getInt(1));
				}
			}

		} catch (CompliancePortalException e) {
			throw e;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(prepareStatement);
		}
	}

	/**
	 * Insert Profile Activity Log Detail
	 * 
	 * @param activityLogDtos
	 * @param connection
	 * @throws CompliancePortalException
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
	 * @param baseUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	protected void addProfileActivityLogs(BaseUpdateDBDto baseUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		List<ProfileActivityLogDto> activityLogs = baseUpdateDBDto.getActivityLog();
		if (activityLogs != null && !activityLogs.isEmpty()) {
			insertProfileActivityLogs(activityLogs, connection);
			insertProfileActivityLogDetail(activityLogs, connection);
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
	 * @param query
	 * @param connection
	 * @throws CompliancePortalException
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
					.prepareStatement(RegistrationQueryConstant.UPDATE_WATCHLISTSTATUS_CONTACT.getQuery());
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
			preparedStatement.setInt(5, requestHandlerDto.getContactId());
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
					requestHandlerDto);// for AT-2986
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
	 * Gets the account attributes.
	 *
	 * @param accountId the account id
	 * @param connection the connection
	 * @return the account attributes
	 * @throws CompliancePortalException the compliance portal exception
	 */
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
	 * Sets the fraugster data.
	 *
	 * @param baseUpdateDBDto
	 *            the base update DB dto
	 * @param readOnlyConn
	 *            the read only conn
	 * @throws CompliancePortalException
	 *             the compliance portal exception
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
	
	/**
	 * Gets the legal entity.
	 *
	 * @param connection the connection
	 * @return the legal entity
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
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
	
	/**
	 * Gets the more onfido details.
	 *
	 * @param resultSet the result set
	 * @return the more onfido details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected List<IDomain> getMoreOnfidoDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Onfido onfido = new Onfido();
				OnfidoSummary onfidoSummary = JsonConverterUtil.convertToObject(OnfidoSummary.class,
						resultSet.getString(Constants.SUMMARY));
				onfido.setUpdatedBy(resultSet.getString(Constants.UPDATED_BY));
				onfido.setEventServiceLogId(resultSet.getInt("id")); // Set esl id
				onfido.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				onfido.setOnfidoId(onfidoSummary.getOnfidoId());
				onfido.setReviewed(onfidoSummary.getReviewed());
				boolean status = "PASS".equals(onfidoSummary.getStatus());
				if (Constants.NOT_REQUIRED.equalsIgnoreCase(onfidoSummary.getStatus())) {
					onfido.setIsRequired(Boolean.FALSE);
					onfido.setStatusValue(Constants.NOT_REQUIRED_UI);
				} else {
					onfido.setIsRequired(Boolean.TRUE);
					onfido.setStatusValue(onfidoSummary.getStatus());
					onfido.setStatus(status);
				}
				onfido.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				domainList.add((IDomain) onfido);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
	}

	//AT-4114
	protected List<IDomain> getMoreIntuitionDetails(ResultSet resultSet) throws CompliancePortalException {
		List<IDomain> domainList = new ArrayList<>();
		try {
			while (resultSet.next()) {
				Intuition intuition = new Intuition();
				TransactionMonitoringAccountSummary intuitionSummary = JsonConverterUtil.convertToObject(TransactionMonitoringAccountSummary.class,
						resultSet.getString(Constants.SUMMARY));
				intuition.setUpdatedBy(resultSet.getString(Constants.UPDATED_BY));
				intuition.setId(resultSet.getInt("id")); 
				intuition.setCreatedOn(DateTimeFormatter.dateTimeFormatter(resultSet.getString(Constants.UPDATED_ON)));
				
				intuition.setCorrelationId(Constants.DASH_SINGLE_DETAILS);
				if(null != intuitionSummary.getCorrelationId()) {
					intuition.setCorrelationId(intuitionSummary.getCorrelationId());
				}
				intuition.setProfileScore(Constants.DASH_SINGLE_DETAILS);
				if(null != intuitionSummary.getProfileScore()) {
					intuition.setProfileScore(intuitionSummary.getProfileScore());
				}
				intuition.setRuleScore(Constants.DASH_SINGLE_DETAILS);
				if(null != intuitionSummary.getRuleScore()) {
					intuition.setRuleScore(intuitionSummary.getRuleScore());
				}
				
				intuition.setRiskLevel(Constants.DASH_SINGLE_DETAILS);
				if(null != intuitionSummary.getRiskLevel()) {
					intuition.setRiskLevel(intuitionSummary.getRiskLevel());
				}
				
				boolean status = "PASS".equals(intuitionSummary.getStatus());
			
				if (Constants.NOT_PERFORMED.equalsIgnoreCase(intuitionSummary.getStatus())) {
					intuition.setIsRequired(Boolean.FALSE);
					intuition.setStatusValue(Constants.NOT_PERFORMED);
				} else {
					intuition.setIsRequired(Boolean.TRUE);
					intuition.setStatusValue(intuitionSummary.getStatus());
					intuition.setStatus(status);
				}
				intuition.setEntityType(
						EntityTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt(Constants.ENTITY_TYPE)));
				domainList.add((IDomain) intuition);
			}
			return domainList;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}

	}
}
