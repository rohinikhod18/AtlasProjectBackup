package com.currenciesdirect.gtg.compliance.dbport.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.OnfidoStatusEnum;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.SourceEnum;
import com.currenciesdirect.gtg.compliance.core.domain.TransactionValuesEnum;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.report.IRegistrationReportDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel2;
import com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.RegQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * 
 * The class RegistrationReportDBServiceImpl
 *
 */
@Component("regReportDBServiceImpl")
public class RegistrationReportDBServiceImpl extends AbstractDBServiceLevel2 implements IRegistrationReportDBService {

	private static final String WHERE = "WHERE";
	private static final String AND = " AND ";
	private static final String WATCHLIST_CATEGORY_FILTER = "{WATCHLIST_CATEGORY_FILTER}";
	
	private String getRegistrationReportQuery(String baseQuery, String contactFilterQuery, 
			String accountFilterQuery, String regFilterQuery, String legalEntityJoin) {
		return (baseQuery.replace("{REGISTRATION_QUEUE_CONTACT_FILTER}", contactFilterQuery)
				.replace("{REGISTRATION_QUEUE_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{REGISTRATION_QUEUE_FILTER}", regFilterQuery)
				.replace("{JOIN_LEGAL_ENTITY}", legalEntityJoin));
	}


	private String setLegalEntityJoin(RegistrationReportSearchCriteria searchCriteria)
	{
		String legalEntityJoin="";
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			String[] legalEntity =searchCriteria.getFilter().getLegalEntity();
			String value = appendAllValues(legalEntity);
			 legalEntityJoin = "JOIN LegalEntity le ON acc.LegalEntityID = le.ID AND le.code In ("+ value + ")";
		}
		return legalEntityJoin;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.core.IRegistrationQueueDBService#
	 * getRegistrationQueue(com.currenciesdirect.gtg.compliance.core.domain.
	 * SearchCriteria)
	 */
	@Override
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
		int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
		int rowsToFetch = SearchCriteriaUtil.getPageSize();
		String regReport = "RegistrationReport";
		RegReportContactFilterQueryBuilder contactFilter = new RegReportContactFilterQueryBuilder(
				searchCriteria.getFilter(),
				RegistrationReportQueryConstant.REGISTRATION_REPORT_CONTACT_FILTER.getQuery(), true);

		String contactFilterQuery = contactFilter.getQuery();
		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if (null != watchListCategoryFilter) {
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, watchListCategoryFilter);
		} else {
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, "");
		}

		contactFilterQuery = buildContactFilterQuery(searchCriteria, contactFilterQuery);

		String accountFilterQuery = buildAccountFilterQuery(searchCriteria);

		String regFilterQuery = buildAccountAndContactAttributeFilterQuery(searchCriteria, contactFilter,
				contactFilterQuery, watchListCategoryFilter, accountFilterQuery);
		
		//ADD for AT-3269
		String legalEntityJoin =setLegalEntityJoin(searchCriteria);
		String listQuery = getRegistrationReportQuery(RegistrationReportQueryConstant.GET_REGISTRATION_REPORT.getQuery(), 
				contactFilterQuery, accountFilterQuery, regFilterQuery, legalEntityJoin);

		listQuery = executeSort(searchCriteria.getFilter().getSort(), listQuery);

		String countQuery = getRegistrationReportQuery(RegistrationReportQueryConstant.GET_REGISTRATION_REPORT_COUNT.getQuery(), 
				contactFilterQuery, accountFilterQuery, regFilterQuery, legalEntityJoin);

		if (Boolean.TRUE.equals(searchCriteria.getIsLandingPage())) {
			countQuery = null;
		}
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			List<RegistrationQueueData> regQueueData = getRegisrationQueueData(connection, offset, rowsToFetch,
					listQuery, countQuery);
			registrationQueueDto.setRegistrationQueue(regQueueData);

			Integer pageSize = SearchCriteriaUtil.getPageSize();
			Integer totalRecords = 0;
			if (null != regQueueData && !regQueueData.isEmpty()) {
				totalRecords = regQueueData.get(0).getTotalRecords();
			}
			if (totalRecords != null) {
				Integer totalPages = totalRecords / pageSize;
				if (totalRecords % pageSize != 0) {
					totalPages += 1;
				}
				Page page = new Page();
				if (totalRecords == 0) {
					page.setMinRecord(0);
				} else {
					page.setMinRecord(minRowNum);
				}

				int tMaxRecord = minRowNum + registrationQueueDto.getRegistrationQueue().size() - 1;
				if (tMaxRecord < totalRecords)
					page.setMaxRecord(tMaxRecord);
				else
					page.setMaxRecord(totalRecords);
				page.setPageSize(pageSize);
				page.setTotalPages(totalPages);
				page.setTotalRecords(totalRecords);
				// added by neelesh pant
				page.setCurrentPage(SearchCriteriaUtil.getCurrentPage(searchCriteria));
				registrationQueueDto.setPage(page);
			}
			List<String> organization = null;
			organization = getOrganization(connection);
			registrationQueueDto.setOrganization(organization);
			registrationQueueDto.setSource(SourceEnum.getSourceValues());
			registrationQueueDto.setTransValue(TransactionValuesEnum.getTransactionValues());
			
			registrationQueueDto.setOnfidoStatus(OnfidoStatusEnum.getOnfidoStatusValues());
			
			List<String> currency = null;
			currency = getCurrency(connection);
			registrationQueueDto.setCurrency(currency);
			Watchlist watchlist = null;
			boolean category1 = searchCriteria.getFilter().getUserProfile().getPermissions()
					.getCanManageWatchListCategory1();
			boolean category2 = searchCriteria.getFilter().getUserProfile().getPermissions()
					.getCanManageWatchListCategory2();
			watchlist = getWatchListReasonId(connection, category1, category2);
			registrationQueueDto.setWatchList(watchlist);
			List<String> owner = null;
			owner = getLockedUserName(connection);
			registrationQueueDto.setOwner(owner);
			SavedSearch savedSearch = null;
			savedSearch = getSaveSearches(searchCriteria.getFilter().getUserProfile().getId(),regReport,connection);
			registrationQueueDto.setSavedSearch(savedSearch);
			List<String> legalEntity = null;
			legalEntity = getLegalEntity(connection);
			registrationQueueDto.setLegalEntity(legalEntity);
			searchCriteria.setPage(registrationQueueDto.getPage());
			registrationQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return registrationQueueDto;
	}

	private String buildAccountAndContactAttributeFilterQuery(RegistrationReportSearchCriteria searchCriteria,
			RegReportContactFilterQueryBuilder contactFilter, String contactFilterQuery, String watchListCategoryFilter,
			String accountFilterQuery) {
		String contactJoin = "";
		contactJoin = contactFilterQuery(contactFilterQuery, watchListCategoryFilter, contactJoin);

		String accountJoin = "";
		accountJoin = accountFilterQuery(accountFilterQuery, accountJoin);

		if (contactFilter.isFilterAppliedForAccountAndContact()) {
			contactJoin = RegistrationReportQueryConstant.CONTACT_FILTER_LEFT_JOIN.getQuery();
			accountJoin = RegistrationReportQueryConstant.ACCOUNT_FILTER_LEFT_JOIN.getQuery();
		}

		String orgJoin = "";
		orgJoin = organizationJoin(searchCriteria, orgJoin);
		
		String deviceJoin = "";
		deviceJoin = deviceJoin(searchCriteria, deviceJoin);
		
		String onfidoJoin = "";
		onfidoJoin = onfidoJoin(searchCriteria, onfidoJoin);

		String legalEntityJoin = "";
		legalEntityJoin = legalEntityJoin(searchCriteria, legalEntityJoin);

		FilterQueryBuilder regFilter = new RegReportFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationReportQueryConstant.REGISTRATION_REPORT_FILTER.getQuery(),
				searchCriteria.getIsFilterApply(), Boolean.TRUE);
		String regFilterQuery = regFilter.getQuery();

		String userJoin = "";
		userJoin = userJoin(regFilterQuery, userJoin);

		/*
		 * For certain filter search has to be performed on both account and
		 * Contact tables like Name, compliance status, service statuses and IF
		 * both table inner joins are added to REGISTRATION_REPORT_FILTER, PFX
		 * only are CFX only results are obtained and IF LEFT joins are added it
		 * returns all the rows. So adding LEFT joins with WHERE Clause and NULL
		 * checks.
		 * 
		 * Also, if User filter is added, WHERE clause gets added automatically,
		 * else WHERE has to be added
		 * 
		 * Moreover, if either contact or account only filter is added, should
		 * NOT use LEFT JOINs.
		 * 
		 */
		if (contactJoin.length() > 1 && accountJoin.length() > 1) {
			if (regFilterQuery.contains(WHERE)) {
				regFilterQuery = regFilterQuery + AND
						+ RegistrationReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
			} else {
				regFilterQuery = regFilterQuery + " " + WHERE + " "
						+ RegistrationReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
			}

		} else {
			contactJoin = contactJoin.replace("LEFT", "");
			accountJoin = accountJoin.replace("LEFT", "");
		}
		String filter = StringUtils.arrayToCommaDelimitedString(searchCriteria.getFilter().getWatchListReason());
		regFilterQuery = watchlistReasonFilter(regFilterQuery, filter);
		regFilterQuery = regFilterQuery.replace("{CONTACT_FILTER_JOIN}", contactJoin)
				.replace("{ACCOUNT_FILTER_JOIN}", accountJoin).replace("{ORG_TABLE_JOIN}", orgJoin)
				.replace("{USER_FILTER_JOIN}", userJoin)
				.replace("{LEGAL_ENTITY_JOIN}", legalEntityJoin)
				.replace("{DEVICE_ID_JOIN}", deviceJoin)
				.replace("{ONFIDO_JOIN}", onfidoJoin);

		return regFilterQuery;
	}


	private String userJoin(String regFilterQuery, String userJoin) {
		if (regFilterQuery.contains(RegQueDBColumns.REGOWNER.getName())) {
			userJoin = RegistrationReportQueryConstant.USER_FILTER_JOIN.getQuery();
		}
		return userJoin;
	}


	private String legalEntityJoin(RegistrationReportSearchCriteria searchCriteria, String legalEntityJoin) {
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			legalEntityJoin = RegistrationReportQueryConstant.LEGEL_ENTITY_TABLE_JOIN.getQuery();
		}
		return legalEntityJoin;
	}


	private String onfidoJoin(RegistrationReportSearchCriteria searchCriteria, String onfidoJoin) {
		if(null!=searchCriteria.getFilter().getOnfidoStatus() && null == searchCriteria.getFilter().getOrganization()) {
			onfidoJoin =  RegistrationReportQueryConstant.ONFIDO_JOIN.getQuery();  
		}
		return onfidoJoin;
	}


	private String deviceJoin(RegistrationReportSearchCriteria searchCriteria, String deviceJoin) {
		if(null!=searchCriteria.getFilter().getDeviceId()) {
			deviceJoin =  RegistrationReportQueryConstant.DEVICE_ID_JOIN.getQuery();  
		}
		return deviceJoin;
	}


	private String organizationJoin(RegistrationReportSearchCriteria searchCriteria, String orgJoin) {
		if (null != searchCriteria.getFilter().getOrganization()
				&& searchCriteria.getFilter().getOrganization().length > 0) {
			orgJoin = RegistrationReportQueryConstant.ORG_TABLE_JOIN.getQuery();
		}
		return orgJoin;
	}


	private String contactFilterQuery(String contactFilterQuery, String watchListCategoryFilter, String contactJoin) {
		if (contactFilterQuery.contains(WHERE) || null != watchListCategoryFilter) {
			contactJoin = RegistrationReportQueryConstant.CONTACT_FILTER_JOIN.getQuery();
		}
		return contactJoin;
	}


	private String accountFilterQuery(String accountFilterQuery, String accountJoin) {
		if (accountFilterQuery.contains(WHERE)) {
			accountJoin = RegistrationReportQueryConstant.ACCOUNT_FILTER_JOIN.getQuery();
		}
		return accountJoin;
	}

	private String buildAccountFilterQuery(RegistrationReportSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new RegReportAccountFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationReportQueryConstant.REGISTRATION_REPORT_ACCOUNT_FILTER.getQuery(), true);
		return accountFilter.getQuery();
	}

	private String buildContactFilterQuery(RegistrationReportSearchCriteria searchCriteria, String contactFilterQuery) {
		String countryJoin = "";
		if (searchCriteria.getFilter().getKeyword() != null
				&& searchCriteria.getFilter().getKeyword().contains("cor:")) {
			countryJoin = RegistrationReportQueryConstant.REGISTRATION_REPORT_COUNTRY_OF_RESIDENCE_FILTER.getQuery();
		}

		return contactFilterQuery.replace("{REGISTRATION_REPORT_COUNTRY_OF_RESIDENCE_FILTER}",
				countryJoin);
	}

	private String getWatchListCategoryFilter(RegistrationReportSearchCriteria regReportSearchCriteria) {
		String watchlistStatus = null;
		boolean hasAllPermission = false;
		if (regReportSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1()) {
			watchlistStatus = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		}
		if (regReportSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2()) {
			if (null != watchlistStatus)
				hasAllPermission = true;
			else
				watchlistStatus = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();

		}
		if (hasAllPermission)
			watchlistStatus = null;
		else {
			if (null != watchlistStatus)
				watchlistStatus = RegistrationReportQueryConstant.WATCHLIST_CATEGORY_FILTER.getQuery().replace("?", watchlistStatus);
		}

		return watchlistStatus;
	}

	/**
	 * @param regFilterQuery
	 * @param filter
	 * @return
	 */
	private String watchlistReasonFilter(String regFilterQuery, String filter) {
		String regWatchlistReasonFilterQuery = regFilterQuery;
		if (null != filter && filter.length() > 0) {
			if (regWatchlistReasonFilterQuery.contains(WHERE)) {
				regWatchlistReasonFilterQuery = regWatchlistReasonFilterQuery + AND
						+ RegistrationReportQueryConstant.WATCHLIST_FILTER.getQuery().replace("?", filter);
			} else {
				regWatchlistReasonFilterQuery = regWatchlistReasonFilterQuery + " " + WHERE + " "
						+ RegistrationReportQueryConstant.WATCHLIST_FILTER.getQuery().replace("?", filter);
			}
		}
		return regWatchlistReasonFilterQuery;
	}

	private String executeSort(Sort sort, String listQuery) {
		String columnName = "a.updatedon";
		boolean isAsc = false;
		if (sort != null && sort.getIsAscend() != null) {
			isAsc = sort.getIsAscend();
		}
		return addSort(columnName, isAsc, listQuery);
	}

	/**
	 * Gets the regisration queue dta.
	 *
	 * @param connection
	 *            the connection
	 * @param minRowNum
	 *            the min row num
	 * @param maxRowNum
	 *            the max row num
	 * @return the regisration queue datagetRegisrationQueueDta
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	private List<RegistrationQueueData> getRegisrationQueueData(Connection connection, int minRowNum, int maxRowNum,
			String query, String countQuery) throws CompliancePortalException {
		List<RegistrationQueueData> registrationQueueDataList = new ArrayList<>();
		PreparedStatement statement = null;
		PreparedStatement countStatement = null;
		ResultSet rs = null;
		ResultSet countRs = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, minRowNum);
			statement.setInt(2, maxRowNum);
			rs = statement.executeQuery();
			while (rs.next()) {
				RegistrationQueueData registrationQueueData = new RegistrationQueueData();
				registrationQueueData.setContactId(rs.getInt(RegQueDBColumns.CONTACTID.getName()));
				registrationQueueData.setAccountId(rs.getInt(RegQueDBColumns.ACCOUNTID.getName()));
				registrationQueueData.setContactName(rs.getString(RegQueDBColumns.CONTACTNAME.getName()));
				registrationQueueData.setTradeAccountNum(rs.getString(RegQueDBColumns.TRADEACCOUNTNUM.getName()));
				Timestamp registeredOn = rs.getTimestamp(RegQueDBColumns.REGISTEREDON.getName());
				if (registeredOn != null) {
					registrationQueueData.setRegisteredOn(DateTimeFormatter.dateTimeFormatter(registeredOn));
				}
				registrationQueueData.setOrganisation(rs.getString(RegQueDBColumns.ORGANIZATION.getName()));
				registrationQueueData.setLegalEntity(rs.getString(RegQueDBColumns.LEGALENTITY.getName()));
				registrationQueueData.setEidCheck(
						ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.KYCSTATUS.getName())));
				registrationQueueData.setFraugster(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.FRAUGSTERSTATUS.getName())));
				registrationQueueData.setSanction(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.SANCTIONSTATUS.getName())));
				registrationQueueData.setBlacklist(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.BLACKLISTSTATUS.getName())));
				registrationQueueData.setCustomCheck(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.CUSOMCHECKSTATUS.getName())));
				registrationQueueData.setBuyCurrency(rs.getString(RegQueDBColumns.BUYCURRENCY.getName()));
				registrationQueueData.setSellCurrency(rs.getString(RegQueDBColumns.SELLCURRENCY.getName()));
				registrationQueueData.setTransactionValue(com.currenciesdirect.gtg.compliance.util.StringUtils
						.getNumberFormat(rs.getString(RegQueDBColumns.TRANSACTIONVALUE.getName())));
				registrationQueueData.setSource(rs.getString(RegQueDBColumns.SOURCE.getName()));
				registrationQueueData.setType(
						CustomerTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.TYPE.getName())));
				registrationQueueData
						.setCountryOfResidence(rs.getString(RegQueDBColumns.COUNTRY_OF_RESIDENCE.getName()));
				registrationQueueData.setAccountVersion(rs.getInt(RegQueDBColumns.ACCOUNT_VERSION.getName()));
				setNewOrUpdatedData(registrationQueueData);
				Timestamp registeredDate = rs.getTimestamp(RegQueDBColumns.REGISTERED_DATE.getName());
				setRegisteredDate(registeredDate, registrationQueueData);
				registrationQueueData.setComplianceStatus(rs.getString(RegQueDBColumns.STATUS.getName()));
				String lockedBy = rs.getString(RegQueDBColumns.LOCKED_BY.getName());
				setLockedData(lockedBy, rs, registrationQueueData);
				registrationQueueDataList.add(registrationQueueData);
			}
			if (null != countQuery) {
				countStatement = connection.prepareStatement(countQuery);
				countRs = countStatement.executeQuery();
				if (countRs.next() && !registrationQueueDataList.isEmpty())
					registrationQueueDataList.get(0)
							.setTotalRecords(countRs.getInt(RegQueDBColumns.TOTALROWS.getName()));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeResultset(countRs);
			closePrepareStatement(countStatement);
		}
		return registrationQueueDataList;
	}

	// newly created method
	private List<RegistrationQueueData> getRegisrationWholeQueueData(Connection connection, String query)
			throws CompliancePortalException {
		List<RegistrationQueueData> registrationQueueDataList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				RegistrationQueueData registrationQueueData = new RegistrationQueueData();
				registrationQueueData.setContactId(rs.getInt(RegQueDBColumns.CONTACTID.getName()));
				registrationQueueData.setAccountId(rs.getInt(RegQueDBColumns.ACCOUNTID.getName()));
				registrationQueueData.setContactName(rs.getString(RegQueDBColumns.CONTACTNAME.getName()));
				registrationQueueData.setTradeAccountNum(rs.getString(RegQueDBColumns.TRADEACCOUNTNUM.getName()));
				Timestamp registeredOn = rs.getTimestamp(RegQueDBColumns.REGISTEREDON.getName());
				if (registeredOn != null) {
					registrationQueueData.setRegisteredOn(DateTimeFormatter.dateTimeFormatter(registeredOn));
				}

				registrationQueueData.setOrganisation(rs.getString(RegQueDBColumns.ORGANIZATION.getName()));
				registrationQueueData.setLegalEntity(rs.getString(RegQueDBColumns.LEGALENTITY.getName()));
				registrationQueueData.setEidCheck(
						ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.KYCSTATUS.getName())));
				registrationQueueData.setFraugster(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.FRAUGSTERSTATUS.getName())));
				registrationQueueData.setSanction(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.SANCTIONSTATUS.getName())));
				registrationQueueData.setBlacklist(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.BLACKLISTSTATUS.getName())));
				registrationQueueData.setCustomCheck(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(RegQueDBColumns.CUSOMCHECKSTATUS.getName())));
				registrationQueueData.setBuyCurrency(rs.getString(RegQueDBColumns.BUYCURRENCY.getName()));
				registrationQueueData.setSellCurrency(rs.getString(RegQueDBColumns.SELLCURRENCY.getName()));
				registrationQueueData.setTransactionValue(rs.getString(RegQueDBColumns.TRANSACTIONVALUE.getName()));
				registrationQueueData.setSource(rs.getString(RegQueDBColumns.SOURCE.getName()));
				registrationQueueData.setType(rs.getString(RegQueDBColumns.TYPE.getName()));
				registrationQueueData
						.setCountryOfResidence(rs.getString(RegQueDBColumns.COUNTRY_OF_RESIDENCE.getName()));
				registrationQueueData.setAccountVersion(rs.getInt(RegQueDBColumns.ACCOUNT_VERSION.getName()));
				if (registrationQueueData.getAccountVersion() != null
						&& registrationQueueData.getAccountVersion() > 1) {
					registrationQueueData.setNewOrUpdated(Constants.U);
				} else {
					registrationQueueData.setNewOrUpdated(Constants.N);
				}
				Timestamp registeredDate = rs.getTimestamp(RegQueDBColumns.REGISTERED_DATE.getName());
				if (registeredDate != null) {
					registrationQueueData.setRegisteredDate(DateTimeFormatter.dateTimeFormatter(registeredDate));
				} else {
					registrationQueueData.setRegisteredDate(Constants.DASH_UI);
				}
				registrationQueueData.setComplianceStatus(rs.getString(RegQueDBColumns.STATUS.getName()));
				String lockedBy = rs.getString(RegQueDBColumns.LOCKED_BY.getName());
				if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
					registrationQueueData
							.setUserResourceLockId(rs.getInt(RegQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
					registrationQueueData.setLocked(Boolean.TRUE);
					registrationQueueData.setLockedBy(lockedBy);
				}

				registrationQueueDataList.add(registrationQueueData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);

		}
		return registrationQueueDataList;
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		return null;
	}

	/**
	 * Gets the regisration queue whole data.
	 *
	 * @param connection
	 *            the connection
	 * @param query
	 *            the query
	 * @return the regisration queue whole data
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	/*
	 * Method for RegistrationQueueWholeData ExcelSheet
	 */
	public List<RegistrationQueueData> getRegisrationQueueWholeData(Connection connection, String query)
			throws CompliancePortalException {
		List<RegistrationQueueData> registrationQueueDataList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				RegistrationQueueData registrationQueueData = new RegistrationQueueData();
				registrationQueueData.setContactId(rs.getInt(RegQueDBColumns.CONTACTID.getName()));
				registrationQueueData.setAccountId(rs.getInt(RegQueDBColumns.ACCOUNTID.getName()));
				registrationQueueData.setContactName(rs.getString(RegQueDBColumns.CONTACTNAME.getName()));
				registrationQueueData.setTradeAccountNum(rs.getString(RegQueDBColumns.TRADEACCOUNTNUM.getName()));
				Timestamp registeredOn = rs.getTimestamp(RegQueDBColumns.REGISTEREDON.getName());
				if (registeredOn != null) {
					registrationQueueData.setRegisteredOn(DateTimeFormatter.dateTimeFormatter(registeredOn));
				}
				registrationQueueData.setOrganisation(rs.getString(RegQueDBColumns.ORGANIZATION.getName()));
				registrationQueueData.setEidCheck(rs.getString(RegQueDBColumns.KYCSTATUS.getName()));
				registrationQueueData.setFraugster(rs.getString(RegQueDBColumns.FRAUGSTERSTATUS.getName()));
				registrationQueueData.setSanction(rs.getString(RegQueDBColumns.SANCTIONSTATUS.getName()));
				registrationQueueData.setBlacklist(rs.getString(RegQueDBColumns.BLACKLISTSTATUS.getName()));
				registrationQueueData.setBuyCurrency(rs.getString(RegQueDBColumns.BUYCURRENCY.getName()));
				registrationQueueData.setSellCurrency(rs.getString(RegQueDBColumns.SELLCURRENCY.getName()));
				registrationQueueData.setTransactionValue(rs.getString(RegQueDBColumns.TRANSACTIONVALUE.getName()));
				registrationQueueData.setSource(rs.getString(RegQueDBColumns.SOURCE.getName()));
				registrationQueueData.setType(rs.getString(RegQueDBColumns.TYPE.getName()));
				String lockedBy = rs.getString(RegQueDBColumns.LOCKED_BY.getName());
				if (lockedBy != null && !lockedBy.isEmpty()) {
					registrationQueueData
							.setUserResourceLockId(rs.getInt(RegQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
					registrationQueueData.setLocked(Boolean.TRUE);
					registrationQueueData.setLockedBy(lockedBy);
				}
				registrationQueueDataList.add(registrationQueueData);

			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return registrationQueueDataList;
	}

	/**
	 * Method for Download ExcelSheet
	 */
	@Override
	public RegistrationQueueDto getRegistrationQueueWholeData(RegistrationReportSearchCriteria searchCriteria)
			throws CompliancePortalException {

		RegReportContactFilterQueryBuilder contactFilter = new RegReportContactFilterQueryBuilder(
				searchCriteria.getFilter(),
				RegistrationReportQueryConstant.REGISTRATION_REPORT_CONTACT_FILTER.getQuery(), true);

		String contactFilterQuery = contactFilter.getQuery();
		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if (null != watchListCategoryFilter) {
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, watchListCategoryFilter);
		}else{
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, "");
		}
		contactFilterQuery = buildContactFilterQuery(searchCriteria, contactFilterQuery);

		FilterQueryBuilder accountFilter = new RegReportAccountFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationReportQueryConstant.REGISTRATION_REPORT_ACCOUNT_FILTER.getQuery(), true);
		String accountFilterQuery = accountFilter.getQuery();

		String contactJoin = "";
		contactJoin = contactFilterQuery(contactFilterQuery, watchListCategoryFilter, contactJoin);

		String accountJoin = "";
		accountJoin = accountFilterQuery(accountFilterQuery, accountJoin);
		if (contactFilter.isFilterAppliedForAccountAndContact()) {
			contactJoin = RegistrationReportQueryConstant.CONTACT_FILTER_LEFT_JOIN.getQuery();
			accountJoin = RegistrationReportQueryConstant.ACCOUNT_FILTER_LEFT_JOIN.getQuery();
		}
		String orgJoin = "";
		orgJoin = organizationJoin(searchCriteria, orgJoin);
		
		String deviceJoin = "";
		deviceJoin = deviceJoin(searchCriteria, deviceJoin);
		
		String onfidoJoin = "";
		onfidoJoin = onfidoJoin(searchCriteria, onfidoJoin);
		
		String legalEntityJoin = "";
		legalEntityJoin = legalEntityJoin(searchCriteria, legalEntityJoin);

		FilterQueryBuilder regFilter = new RegReportFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationReportQueryConstant.REGISTRATION_REPORT_FILTER.getQuery(),
				searchCriteria.getIsFilterApply(), Boolean.TRUE);
		String regFilterQuery = regFilter.getQuery();

		String userJoin = "";
		userJoin = userJoin(regFilterQuery, userJoin);
		/*
		 * For certain filter search has to be performed on both account and
		 * Contact tables like Name, compliance status, service statuses and IF
		 * both table inner joins are added to REGISTRATION_REPORT_FILTER, PFX
		 * only are CFX only results are obtained and IF LEFT joins are added it
		 * returns all the rows. So adding LEFT joins with WHERE Clause and NULL
		 * checks.
		 * 
		 * Also, if User filter is added, WHERE clause gets added automatically,
		 * else WHERE has to be added
		 * 
		 * Moreover, if either contact or account only filter is added, should
		 * NOT use LEFT JOINs.
		 * 
		 */
		if (contactJoin.length() > 1 && accountJoin.length() > 1) {
			if (regFilterQuery.contains(WHERE)) {
				regFilterQuery = regFilterQuery + AND
						+ RegistrationReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
			} else {
				regFilterQuery = regFilterQuery + " WHERE "
						+ RegistrationReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
			}

		} else {
			contactJoin = contactJoin.replace("LEFT", "");
			accountJoin = accountJoin.replace("LEFT", "");
		}

		regFilterQuery = regFilterQuery.replace("{CONTACT_FILTER_JOIN}", contactJoin)
				.replace("{ACCOUNT_FILTER_JOIN}", accountJoin).replace("{ORG_TABLE_JOIN}", orgJoin)
				.replace("{USER_FILTER_JOIN}", userJoin).replace("{LEGAL_ENTITY_JOIN}", legalEntityJoin)
				.replace("{DEVICE_ID_JOIN}", deviceJoin)
				.replace("{ONFIDO_JOIN}", onfidoJoin);
		
		searchCriteria.getFilter().getWatchListReason();
		String filter = StringUtils.arrayToCommaDelimitedString(searchCriteria.getFilter().getWatchListReason());
		regFilterQuery = watchlistReasonFilter(regFilterQuery, filter);
		//ADD for AT-3269
		String legalEntityJoin1 = "";
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			String[] legalEntity = searchCriteria.getFilter().getLegalEntity();
			String value = appendAllValues(legalEntity);
			legalEntityJoin1 = "JOIN LegalEntity le ON acc.LegalEntityID = le.ID AND le.code In ("+ value + ")";
		}
		String listQuery = getRegistrationReportQuery(RegistrationReportQueryConstant.GET_REGISTRATION_EXCEL_REPORT.getQuery(), 
				contactFilterQuery, accountFilterQuery, regFilterQuery, legalEntityJoin1);

		RegistrationQueueDto registrationQueueDto = null;
		registrationQueueDto = registrationQueueWholeData(searchCriteria, listQuery);
		return registrationQueueDto;
	}


	private RegistrationQueueDto registrationQueueWholeData(RegistrationReportSearchCriteria searchCriteria,
			String listQuery) throws CompliancePortalException {
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			List<RegistrationQueueData> regQueueData = getRegisrationWholeQueueData(connection, listQuery);
			registrationQueueDto.setRegistrationQueue(regQueueData);
			Integer pageSize = SearchCriteriaUtil.getPageSize();
			Integer totalRecords = 0;
			if (null != regQueueData && !regQueueData.isEmpty()) {
				totalRecords = regQueueData.get(0).getTotalRecords();
			}
			if (totalRecords != null) {
				Integer totalPages = totalRecords / pageSize;
				if (totalRecords % pageSize != 0) {
					totalPages += 1;
				}
				Page page = new Page();
				page.setPageSize(pageSize);
				page.setTotalPages(totalPages);
				page.setTotalRecords(totalRecords);
				registrationQueueDto.setPage(page);
				List<String> organization = null;
				organization = getOrganization(connection);
				registrationQueueDto.setOrganization(organization);
				registrationQueueDto.setSource(SourceEnum.getSourceValues());
				registrationQueueDto.setTransValue(TransactionValuesEnum.getTransactionValues());
				List<String> currency = null;
				currency = getCurrency(connection);
				registrationQueueDto.setCurrency(currency);
				Watchlist watchlist = null;
				boolean category1 = searchCriteria.getFilter().getUserProfile().getPermissions()
						.getCanManageWatchListCategory1();
				boolean category2 = searchCriteria.getFilter().getUserProfile().getPermissions()
						.getCanManageWatchListCategory2();
				watchlist = getWatchListReasonId(connection, category1, category2);
				registrationQueueDto.setWatchList(watchlist);
				List<String> owner = null;
				owner = getLockedUserName(connection);
				registrationQueueDto.setOwner(owner);
				searchCriteria.setPage(registrationQueueDto.getPage());
				registrationQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return registrationQueueDto;
	}
	
	/**
	 * Append all values.
	 *
	 * @param values the values
	 * @return the string
	 */
	//Add For AT-3269
	private String appendAllValues(String[] values) {
		StringBuilder s = new StringBuilder();
		for (String value : values) {
			if (value != null && !value.isEmpty()) {
				s.append("'").append(value).append("'").append(',');
			}

		}
		if (s.length() > 1) {
			s.setLength(s.length() - 1);
			return s.toString();
		}
		return null;

	}
	
	private void setNewOrUpdatedData(RegistrationQueueData registrationQueueData)
	{
		registrationQueueData.setNewOrUpdated(Constants.N);
		if (registrationQueueData.getAccountVersion() != null && registrationQueueData.getAccountVersion() > 1) {
			registrationQueueData.setNewOrUpdated(Constants.U);
		} 
	}
	
	
	private void setRegisteredDate(Timestamp registeredDate,RegistrationQueueData registrationQueueData)
	{
		registrationQueueData.setRegisteredDate(Constants.DASH_UI);
		if (registeredDate != null) {
			registrationQueueData.setRegisteredDate(DateTimeFormatter.dateTimeFormatter(registeredDate));
		} 
	}
	
	private void setLockedData(String lockedBy,ResultSet rs,RegistrationQueueData registrationQueueData) throws SQLException {
		if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			registrationQueueData
					.setUserResourceLockId(rs.getInt(RegQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
			registrationQueueData.setLocked(Boolean.TRUE);
			registrationQueueData.setLockedBy(lockedBy);
		}
	}
	
	
}
