package com.currenciesdirect.gtg.compliance.dbport.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.SourceEnum;
import com.currenciesdirect.gtg.compliance.core.domain.TransactionValuesEnum;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.report.IPaymentOutReportDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel2;
import com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.PayOutQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class PaymentOutReportDBServiceImpl.
 */
@Component("payOutReportDBServiceImpl")
public class PaymentOutReportDBServiceImpl extends AbstractDBServiceLevel2 implements IPaymentOutReportDBService {

	private static final String WHERE = "WHERE";

	/**
	 * Purpose of Method- Business: Get paymentOutData from database
	 * Implementation: 1.Fetch record from created connection by using sql query
	 * 2.Set value for each column on UI
	 * 
	 * @param countQuery
	 */
	private PaymentOutQueueDto getPaymentOutData(Connection connection, Integer minRowNum, Integer maxRowNum,
			String query, String countQuery) throws CompliancePortalException {
		List<PaymentOutQueueData> paymentOutQueueDataList = new ArrayList<>();
		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		PreparedStatement statement = null;
		PreparedStatement countStatement = null;
		ResultSet rs = null;
		ResultSet countRs = null;
		Integer totalRows = 0;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, minRowNum);
			statement.setInt(2, maxRowNum);
			rs = statement.executeQuery();

			while (rs.next()) {
				setPaymentOutList(paymentOutQueueDataList, rs);
			}
			if (countQuery != null) {
				countStatement = connection.prepareStatement(countQuery);
				countRs = countStatement.executeQuery();
				if (countRs.next()) {
					totalRows = countRs.getInt(PayOutReportQueueDBColumns.TOTAL_ROWS.getName());
				}
				paymentOutQueueDto.setTotalRecords(totalRows);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeResultset(countRs);
			closePrepareStatement(countStatement);
		}
		paymentOutQueueDto.setPaymentOutQueue(paymentOutQueueDataList);
		return paymentOutQueueDto;

	}

	private void setPaymentOutList(List<PaymentOutQueueData> paymentOutQueueDataList, ResultSet rs)
			throws SQLException {
		PaymentOutQueueData paymentOutQueueData = new PaymentOutQueueData();
		paymentOutQueueData.setTransactionId(rs.getString(PayOutReportQueueDBColumns.TRANSACTIONID.getName()));
		paymentOutQueueData.setClientId(rs.getString(PayOutReportQueueDBColumns.TRADEACCOUNTNUM.getName()));
		paymentOutQueueData.setAccountId(rs.getInt(PayOutReportQueueDBColumns.ACCOUNTID.getName()));
		paymentOutQueueData.setContactId(rs.getInt(PayOutReportQueueDBColumns.CONTACTID.getName()));
		paymentOutQueueData.setAcsfId(rs.getString(PayOutReportQueueDBColumns.ACSFID.getName()));
		paymentOutQueueData.setReasonOfTransfer(rs.getString(PayOutReportQueueDBColumns.REASONOFTRANSFER.getName()));
		if (null != rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())) {
			paymentOutQueueData.setDate(
					DateTimeFormatter.dateTimeFormatter(rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())));
		} else {
			paymentOutQueueData.setDate(Constants.DASH_UI);
		}
		paymentOutQueueData.setContactName(rs.getString(PayOutReportQueueDBColumns.CLIENTNAME.getName()));
		paymentOutQueueData.setType(
				CustomerTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayOutReportQueueDBColumns.TYPE.getName())));
		paymentOutQueueData.setBuyCurrency(rs.getString(PayOutReportQueueDBColumns.BUYCURRENCY.getName()));
		/**
		 * Code changed to show amount upto 2 decimal places on queue (AT - 490)
		 */
		String amount = rs.getString(PayOutQueDBColumns.BENEFICIARYAMMOUNT.getName());
		if (amount != null && amount.contains(".")) {
			amount = amount.substring(0, amount.indexOf('.') + 3);
			paymentOutQueueData.setAmount(com.currenciesdirect.gtg.compliance.util.StringUtils.getNumberFormat(amount));
		} else {
			paymentOutQueueData.setAmount(com.currenciesdirect.gtg.compliance.util.StringUtils.getNumberFormat(amount));
		}
		paymentOutQueueData.setBeneficiary(rs.getString(PayOutReportQueueDBColumns.BENEFICIARY.getName()));

		paymentOutQueueData.setCountry(rs.getString(PayOutReportQueueDBColumns.COUNTRY.getName()));
		paymentOutQueueData.setIsoCountry(rs.getString(PayOutReportQueueDBColumns.ISO_COUNTRY.getName()));
		paymentOutQueueData.setOverallStatus(rs.getString(PayOutReportQueueDBColumns.OVERALLSTATUS.getName()));
		paymentOutQueueData.setWatchlist(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayOutReportQueueDBColumns.WATCHLISTSTATUS.getName())));
		paymentOutQueueData.setFraugster(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.FRAUGSTERSTATUS.getName())));
		paymentOutQueueData.setSanction(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.SANCTIONSTATUS.getName())));
		paymentOutQueueData.setBlacklist(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.BLACKLISTSTATUS.getName())));
		paymentOutQueueData.setIntuitionStatus(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.INTUITIONSTATUS.getName())));//AT-4607
		/**
		 * Added sanction column BlacklistPaymentReference status on PaymentOut Queue :AT-3854 :
		 * Abhijit Khatavkar
		 */
		paymentOutQueueData.setBlacklistPayRef(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.BLACKLISTPAYREFSTATUS.getName())));
		/**
		 * Added Customcheck status column on PaymentOut Queue : fixed issue
		 * AT-464 : Abhijit Gorde
		 */
		paymentOutQueueData.setCustomCheck(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayOutReportQueueDBColumns.CUSTOMCHECKSTATUS.getName())));
		// hidden values on UI
		Integer payOutId = rs.getInt(PayOutReportQueueDBColumns.PAYMENTOUTID.getName());
		if (payOutId != null) {
			paymentOutQueueData.setPaymentOutId(payOutId.toString());
		}
		paymentOutQueueData.setOrganisation(rs.getString(PayOutReportQueueDBColumns.ORGANIZATION.getName()));
		paymentOutQueueData.setLegalEntity(rs.getString(PayOutReportQueueDBColumns.LEGALENTITY.getName()));
		
		String valueDate = rs.getString(PayOutReportQueueDBColumns.VALUE_DATE.getName());
		if (valueDate != null) {
			paymentOutQueueData.setValueDate(DateTimeFormatter.getDateInRFC3339(valueDate));
		}
		String maturityDate = rs.getString(PayOutReportQueueDBColumns.MATURITY_DATE.getName());
		if (maturityDate != null) {
			paymentOutQueueData.setMaturityDate(DateTimeFormatter.getDateInRFC3339(maturityDate));
		}
		String lockedBy = rs.getString(PayOutReportQueueDBColumns.LOCKED_BY.getName());
		if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			paymentOutQueueData
					.setUserResourceLockId(rs.getInt(PayOutReportQueueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
			paymentOutQueueData.setLocked(Boolean.TRUE);
			paymentOutQueueData.setLockedBy(lockedBy);
		}
		paymentOutQueueDataList.add(paymentOutQueueData);
	}
	
	private String getPaymentOutReportQuery(String baseQuery, String contactFilterQuery, String accountFilterQuery, 
			String paymentFilterQuery, String paymentOutFilterQuery) {
		return (baseQuery.replace("{PAYMENT_OUT_QUEUE_CONTACT_FILTER}", contactFilterQuery)
				.replace("{PAYMENT_OUT_QUEUE_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{PAYMENT_OUT_QUEUE_PAYMENT_FILTER}", paymentFilterQuery)
				.replace("{PAYMENT_QUEUE_FILTER}", paymentOutFilterQuery));
	}

	@Override
	public PaymentOutQueueDto getPaymentOutReportWithCriteria(PaymentOutReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		Connection connection = null;
		try {
			int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
			int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
			int rowsToFetch = SearchCriteriaUtil.getPageSize();
			String payOutReport = "PaymentOutReport";
			PaymentOutReportContactFilterQueryBuilder contactFilter = new PaymentOutReportContactFilterQueryBuilder(
					searchCriteria.getFilter(),
					PaymentOutReportQueryConstant.PAYMENT_OUT_QUEUE_CONTACT_FILTER.getQuery(), true);

			String contactFilterQuery = buildContactFilterQuery(contactFilter, searchCriteria);

			String accountFilterQuery = buildAccountFilterQuery(searchCriteria);

			String paymentFilterQuery = buildPaymentAttributeFilterQuery(searchCriteria);

			String paymentOutFilterQuery = buildPaymentOutFilterQuery(searchCriteria, contactFilterQuery,
					accountFilterQuery, paymentFilterQuery, contactFilter);
			
			String listQuery = getPaymentOutReportQuery(PaymentOutReportQueryConstant.GET_PAYMENTOUT_REPORT.getQuery(),
					contactFilterQuery, accountFilterQuery, paymentFilterQuery, paymentOutFilterQuery);

			listQuery = executeSort(searchCriteria.getFilter().getSort(), listQuery);

			String countQuery = getPaymentOutReportQuery(PaymentOutReportQueryConstant.GET_PAYMENTOUT_REPORT_COUNT.getQuery(),
					contactFilterQuery, accountFilterQuery, paymentFilterQuery, paymentOutFilterQuery);

			if (searchCriteria.getIsLandingPage() != null && searchCriteria.getIsLandingPage()) {
				countQuery = null;
			}
			connection = getConnection(Boolean.TRUE);
			paymentOutQueueDto = getPaymentOutData(connection, offset, rowsToFetch, listQuery, countQuery);

			Integer pageSize = SearchCriteriaUtil.getPageSize();
			Integer totalRecords = paymentOutQueueDto.getTotalRecords();
			getPageAndOtherDetails(searchCriteria, paymentOutQueueDto, connection, minRowNum, pageSize, totalRecords);
			SavedSearch savedSearch = null;
			savedSearch = getSaveSearches(searchCriteria.getFilter().getUserProfile().getId(), payOutReport,
					connection);
			paymentOutQueueDto.setSavedSearch(savedSearch);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return paymentOutQueueDto;
	}

	private String executeSort(Sort sort, String query) {
		String columnName = "UpdatedOn";
		boolean isAsc = false;
		if (sort != null && sort.getIsAscend() != null) {
			isAsc = sort.getIsAscend();
		}

		if (sort != null && sort.getFieldName() != null && !sort.getFieldName().isEmpty()) {
			columnName = sort.getFieldName();
		}

		String queryColumnName = "payOut.UpdatedOn";
		if ("UpdatedOn".equalsIgnoreCase(columnName)) {
			queryColumnName = "payOut.UpdatedOn";
		} else if ("tradeaccountnumber".equalsIgnoreCase(columnName)) {
			queryColumnName = "acc.tradeaccountnumber";
		} else if ("vMaturityDate".equalsIgnoreCase(columnName)) {
			queryColumnName = "poa.vMaturityDate";
		} else if ("type".equalsIgnoreCase(columnName)) {
			queryColumnName = "acc.type";
		}

		return addSort(queryColumnName, isAsc, query);

	}

	private String setPaymentOutFilterQuery(FilterQueryBuilder paymentOutFilter) {
		String paymentOutFilterQuery = paymentOutFilter.getQuery();
		if (paymentOutFilterQuery.contains(WHERE)) {
			paymentOutFilterQuery = paymentOutFilterQuery + " AND "
					+ PaymentOutReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		} else {
			paymentOutFilterQuery = paymentOutFilterQuery + " WHERE "
					+ PaymentOutReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}
		return paymentOutFilterQuery;
	}

	private String buildPaymentOutFilterQuery(PaymentOutReportSearchCriteria searchCriteria, String contactFilterQuery,
			String accountFilterQuery, String paymentFilterQuery,
			PaymentOutReportContactFilterQueryBuilder contactFilter) {
		String contactJoin = "";
		if (contactFilterQuery.contains(WHERE) || contactFilterQuery.contains("category")) {
			contactJoin = PaymentOutReportQueryConstant.CONTACT_FILTER_JOIN.getQuery();
		}

		String accountJoin = "";
		if (accountFilterQuery.contains(WHERE)) {
			accountJoin = PaymentOutReportQueryConstant.ACCOUNT_FILTER_JOIN.getQuery();
		}

		String paymentJoin = "";
		if (paymentFilterQuery.contains(WHERE)) {
			paymentJoin = PaymentOutReportQueryConstant.PAYMENT_FILTER_JOIN.getQuery();
		}

		String orgJoin = "";
		if (null != searchCriteria.getFilter().getOrganization()
				&& searchCriteria.getFilter().getOrganization().length > 0) {
			orgJoin = PaymentOutReportQueryConstant.ORG_FILTER_JOIN.getQuery();
		}
		String legalEntityJoin = "";
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			legalEntityJoin = PaymentOutReportQueryConstant.LEGEL_ENTITY_TABLE_JOIN.getQuery();
		}

		String userJoin = "";
		if (null != searchCriteria.getFilter().getOwner() && searchCriteria.getFilter().getOwner().length > 0) {
			userJoin = PaymentOutReportQueryConstant.USER_FILTER_JOIN.getQuery();
		}

		FilterQueryBuilder paymentOutFilter = new PaymentOutReportFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutReportQueryConstant.PAYMENT_QUEUE_FILTER.getQuery());

		String paymentOutFilterQuery = paymentOutFilter.getQuery();

		if (contactFilter.isFilterAppliedForAccountAndContact() && accountJoin.length() > 1) {
			paymentOutFilterQuery=setPaymentOutFilterQuery(paymentOutFilter);	
		} else {
			contactJoin = contactJoin.replace("LEFT", "");
			accountJoin = accountJoin.replace("LEFT", "");
		}
		paymentOutFilterQuery = paymentOutFilterQuery.replace("{CONTACT_FILTER_JOIN}", contactJoin)
				.replace("{ACCOUNT_FILTER_JOIN}", accountJoin).replace("{PAYMENT_TABLE_JOIN}", paymentJoin)
				.replace("{ORG_JOIN}", orgJoin).replace("{USER_JOIN}", userJoin).replace("{LEGAL_ENTITY_JOIN}", legalEntityJoin);

		return paymentOutFilterQuery;
	}

	private String getWatchListCategoryFilter(PaymentOutReportSearchCriteria payOutReportSearchCriteria) {
		String watchlistCategory = null;
		boolean hasAllPermission = false;
		if (payOutReportSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1()) {
			watchlistCategory = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		}
		if (payOutReportSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2()) {
			if (null != watchlistCategory)
				hasAllPermission = true;
			else
				watchlistCategory = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();

		}
		if (hasAllPermission)
			watchlistCategory = null;
		else {
			if (null != watchlistCategory)
				watchlistCategory = PaymentOutReportQueryConstant.WATCHLIST_CATEGORY_FILTER.getQuery().replace("?",
						watchlistCategory);
		}

		return watchlistCategory;
	}

	private void getPageAndOtherDetails(PaymentOutReportSearchCriteria searchCriteria,
			PaymentOutQueueDto paymentOutQueueDto, Connection connection, int minRowNum, Integer pageSize,
			Integer totalRecords) throws CompliancePortalException {
		if (totalRecords != null) {
			Integer totalPages;
			totalPages = totalRecords / pageSize;
			if (totalRecords % pageSize != 0) {
				totalPages += 1;
			}
			Page page = new Page();
			if (totalRecords == 0) {
				page.setMinRecord(0);
			} else {
				page.setMinRecord(minRowNum);
			}
			page.setMaxRecord(minRowNum + paymentOutQueueDto.getPaymentOutQueue().size() - 1);
			page.setPageSize(pageSize);
			page.setTotalPages(totalPages);
			page.setTotalRecords(totalRecords);
			// added by neelesh pant
			page.setCurrentPage(SearchCriteriaUtil.getCurrentPage(searchCriteria));
			paymentOutQueueDto.setPage(page);
		}
		List<String> organization = getOrganization(connection);
		paymentOutQueueDto.setOrganization(organization);
		List<String> currency = getCurrency(connection);
		paymentOutQueueDto.setCurrency(currency);

		Watchlist watchList;
		boolean category1 = searchCriteria.getFilter().getUserProfile().getPermissions()
				.getCanManageWatchListCategory1();
		boolean category2 = searchCriteria.getFilter().getUserProfile().getPermissions()
				.getCanManageWatchListCategory2();
		watchList = getWatchListReasonId(connection, category1, category2);
		paymentOutQueueDto.setWatchList(watchList);
		List<String> owner = getLockedUserName(connection);
		paymentOutQueueDto.setOwner(owner);
		List<String> paymentStatus = getPaymentStatus(connection);
		paymentOutQueueDto.setPaymentStatus(paymentStatus);
		List<String> countryofBeneficiary = getAllCountries(connection);
		paymentOutQueueDto.setCountry(countryofBeneficiary);
		paymentOutQueueDto.setSource(SourceEnum.getSourceValues());
		paymentOutQueueDto.setTransValue(TransactionValuesEnum.getTransactionValues());
		List<String> legalEntity = getLegalEntity(connection);
		paymentOutQueueDto.setLegalEntity(legalEntity);
		searchCriteria.setPage(paymentOutQueueDto.getPage());
		paymentOutQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
	}

	private String buildPaymentAttributeFilterQuery(PaymentOutReportSearchCriteria searchCriteria) {
		FilterQueryBuilder paymentFilter = new PaymentOutPaymentFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutReportQueryConstant.PAYMENT_OUT_QUEUE_PAYMENT_FILTER.getQuery(), true);

		String countryJoin = "";
		if (null != searchCriteria.getFilter().getCountryOfBeneficiary()
				&& searchCriteria.getFilter().getCountryOfBeneficiary().length > 0) {
			countryJoin = PaymentOutReportQueryConstant.COUNTRY_FILTER_JOIN.getQuery();
		}
		return paymentFilter.getQuery().replace("{COUNTRY_JOIN}", countryJoin);
	}

	private String buildAccountFilterQuery(PaymentOutReportSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new PaymenOutReportAccountFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutReportQueryConstant.PAYMENT_OUT_QUEUE_ACCOUNT_FILTER.getQuery(), true);
		return accountFilter.getQuery();
	}

	private String buildContactFilterQuery(PaymentOutReportContactFilterQueryBuilder contactFilter,
			PaymentOutReportSearchCriteria searchCriteria) {

		String watchListFilter = "";
		String filter = StringUtils.arrayToCommaDelimitedString(searchCriteria.getFilter().getWatchListReason());
		String contactFilterQuery = contactFilter.getQuery();

		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if (null != watchListCategoryFilter) {
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", watchListCategoryFilter);
		} else {
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", "");
		}

		if (null != filter && filter.length() > 0) {
			if (contactFilterQuery.contains(WHERE)) {
				watchListFilter = "AND "
						+ PaymentOutReportQueryConstant.WATCHLIST_FILTER.getQuery().replace("?", filter);
			} else {
				watchListFilter = " WHERE "
						+ PaymentOutReportQueryConstant.WATCHLIST_FILTER.getQuery().replace("?", filter);
			}
		}
		contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_FILTER}", watchListFilter);

		String countryJoin = "";
		if (contactFilter.isCountryOfResidenceFilterApplied()) {
			countryJoin = PaymentOutReportQueryConstant.PAYMENTOUT_QUEUE_COUNTRY_OF_RESIDENCE_FILTER.getQuery();
		}
		return contactFilterQuery.replace("{PAYMENTOUT_QUEUE_COUNTRY_OF_RESIDENCE_FILTER}", countryJoin);
	}

	/**
	 * Method for Download ExcelSheet
	 */
	@Override
	public PaymentOutQueueDto getPaymentOutQueueWholeData(PaymentOutReportSearchCriteria searchCriteria)
			throws CompliancePortalException {

		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		Connection connection = null;
		try {
			PaymentOutReportContactFilterQueryBuilder contactFilter = new PaymentOutReportContactFilterQueryBuilder(
					searchCriteria.getFilter(),
					PaymentOutReportQueryConstant.PAYMENT_OUT_QUEUE_CONTACT_FILTER.getQuery(), true);
			String contactFilterQuery = buildContactFilterQuery(contactFilter, searchCriteria);

			String accountFilterQuery = buildAccountFilterQuery(searchCriteria);

			String paymentFilterQuery = buildPaymentAttributeFilterQuery(searchCriteria);

			String paymentOutFilterQuery = buildPaymentOutFilterQuery(searchCriteria, contactFilterQuery,
					accountFilterQuery, paymentFilterQuery, contactFilter);

			String listQuery = getPaymentOutReportQuery(PaymentOutReportQueryConstant.GET_PAYMENTOUT_REPORT_DATA.getQuery(),
					contactFilterQuery, accountFilterQuery, paymentFilterQuery, paymentOutFilterQuery);

			connection = getConnection(Boolean.TRUE);
			paymentOutQueueDto.setPaymentOutQueue(getPaymentOutQueueWholeData(connection, listQuery));

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return paymentOutQueueDto;

	}

	/**
	 * Method for PaymentOutQueueWholeData ExcelSheet
	 */
	private List<PaymentOutQueueData> getPaymentOutQueueWholeData(Connection connection, String query)
			throws CompliancePortalException {
		List<PaymentOutQueueData> paymentOutQueueDataList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				PaymentOutQueueData paymentOutQueueData = new PaymentOutQueueData();
				paymentOutQueueData.setTransactionId(rs.getString(PayOutQueDBColumns.TRANSACTIONID.getName()));
				paymentOutQueueData.setClientId(rs.getString(PayOutQueDBColumns.TRADEACCOUNTNUM.getName()));
				paymentOutQueueData.setAccountId(rs.getInt(PayOutQueDBColumns.ACCOUNTID.getName()));
				paymentOutQueueData.setContactId(rs.getInt(PayOutQueDBColumns.CONTACTID.getName()));
				paymentOutQueueData.setInitialStatus(rs.getString(PayOutQueDBColumns.INITIALSTATUS.getName()));//AT-3472
				if (null != rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())) {
					paymentOutQueueData.setDate(DateTimeFormatter
							.dateTimeFormatter(rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())));
				} else {
					paymentOutQueueData.setDate(Constants.DASH_UI);
				}
				paymentOutQueueData.setContactName(rs.getString(PayOutQueDBColumns.CLIENTNAME.getName()));
				paymentOutQueueData.setType(rs.getString(PayOutQueDBColumns.TYPE.getName()));
				paymentOutQueueData.setBuyCurrency(rs.getString(PayOutQueDBColumns.BUYCURRENCY.getName()));

				String paymeOutAttrib = rs.getString(PayOutQueDBColumns.ATTRIBUTE.getName());
				FundsOutRequest req = JsonConverterUtil.convertToObject(FundsOutRequest.class, paymeOutAttrib);// added
																												// to
																												// resolve
																												// AT-270
																												// Neelesh.p
				setAmountValue(paymentOutQueueData, req);

				paymentOutQueueData.setBeneficiary(rs.getString(PayOutQueDBColumns.BENEFICIARY.getName()));

				paymentOutQueueData.setCountry(rs.getString(PayOutQueDBColumns.COUNTRY.getName()));
				paymentOutQueueData.setOverallStatus(rs.getString(PayOutQueDBColumns.OVERALLSTATUS.getName()));
				paymentOutQueueData.setWatchlist(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.WATCHLISTSTATUS.getName())));
				setWatchListValueForNull(paymentOutQueueData);
				
				paymentOutQueueData.setFraugster(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.FRAUGSTERSTATUS.getName())));
				paymentOutQueueData.setSanction(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.SANCTIONSTATUS.getName())));
				paymentOutQueueData.setBlacklist(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.BLACKLISTSTATUS.getName())));
				paymentOutQueueData.setIntuitionStatus(ServiceStatusEnum
						.getUiStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.INTUITIONSTATUS.getName()))); //AT-4717
				
				/**
				 * Added Reference Check status in xslsheet 
				 * issue AT-3855 : Abhijit Khatavkar
				 */
				paymentOutQueueData.setBlacklistPayRef(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.BLACKLISTPAYREFSTATUS.getName())));
				
				/**
				 * Added Customcheck status column on PaymentOut Queue : fixed
				 * issue AT-464 : Abhijit Gorde
				 */
				paymentOutQueueData.setCustomCheck(ServiceStatusEnum.getUiStatusFromDatabaseStatus(
						rs.getInt(PayOutReportQueueDBColumns.CUSTOMCHECKSTATUS.getName())));
				// hidden values on UI
				Integer payOutId = rs.getInt(PayOutQueDBColumns.PAYMENTOUTID.getName());
				if (payOutId != null) {
					paymentOutQueueData.setPaymentOutId(payOutId.toString());
				}
				paymentOutQueueData.setOrganisation(rs.getString(PayOutQueDBColumns.ORGANIZATION.getName()));
				paymentOutQueueData.setLegalEntity(rs.getString(PayOutReportQueueDBColumns.LEGALENTITY.getName()));
				
				String valueDate = rs.getString(PayOutReportQueueDBColumns.VALUE_DATE.getName());
				if (valueDate != null) {
					paymentOutQueueData.setValueDate(DateTimeFormatter.getDateInRFC3339(valueDate));
				}
				String maturityDate = rs.getString(PayOutReportQueueDBColumns.MATURITY_DATE.getName());
				if (maturityDate != null) {
					paymentOutQueueData.setMaturityDate(DateTimeFormatter.getDateInRFC3339(maturityDate));
				}
				String lockedBy = rs.getString(PayOutQueDBColumns.LOCKED_BY.getName());
				if (lockedBy != null && !lockedBy.isEmpty()) {
					paymentOutQueueData
							.setUserResourceLockId(rs.getInt(PayOutQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
					paymentOutQueueData.setLocked(Boolean.TRUE);
					paymentOutQueueData.setLockedBy(lockedBy);
				}
				paymentOutQueueDataList.add(paymentOutQueueData);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return paymentOutQueueDataList;
	}

	private void setWatchListValueForNull(PaymentOutQueueData paymentOutQueueData) {
		if (paymentOutQueueData.getWatchlist() == null) {
			paymentOutQueueData.setWatchlist(Constants.DASH_UI);
		}
	}

	private void setAmountValue(PaymentOutQueueData paymentOutQueueData, FundsOutRequest req) {
		Double amount = req.getBeneficiary().getAmount();
		if (amount != null) {
			paymentOutQueueData.setAmount(amount.toString());
		}
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) throws CompliancePortalException {

		return null;
	}

}
