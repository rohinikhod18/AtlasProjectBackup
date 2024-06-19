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
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.core.report.IPaymentInReportDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDBServiceLevel2;
import com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.PayInQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.RegQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentMethodEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class PaymentInReportDBServiceImpl.
 */
@Component("payInReportDBServiceImpl")
public class PaymentInReportDBServiceImpl extends AbstractDBServiceLevel2 implements IPaymentInReportDBService {
	
	private PaymentInQueueDto getPaymentInData(Connection con, int offSet, int rowsToFetch,
			String query, String queryCount) throws CompliancePortalException {
		List<PaymentInQueueData> paymentInQueueDataList = new ArrayList<>();
		PaymentInQueueDto paymentInQueueDto =new PaymentInQueueDto();
		PreparedStatement stmt = null;
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		ResultSet countRs = null;
		Integer totalRecords = 0;
		try {
			stmt = con.prepareStatement(query);
			stmt.setInt(1, offSet);
			stmt.setInt(2, rowsToFetch);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				setpaymentInReportDataList(paymentInQueueDataList, rs);
			}
			if (queryCount != null) {
				countStmt = con.prepareStatement(queryCount);
				countRs = countStmt.executeQuery();
				if (countRs.next()) {
					totalRecords = countRs.getInt(RegQueDBColumns.TOTALROWS.getName());
				}
				paymentInQueueDto.setTotalRecords(totalRecords);

			}
			paymentInQueueDto.setPaymentInQueue(paymentInQueueDataList);
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(stmt);
			closeResultset(countRs);
			closePrepareStatement(countStmt);
		}
		return paymentInQueueDto;
	}

	private void setpaymentInReportDataList(List<PaymentInQueueData> paymentInQueueDataList, ResultSet rs) throws SQLException {
				PaymentInQueueData paymentInQueueData = new PaymentInQueueData();
				paymentInQueueData.setTransactionId(rs.getString(PayInReportQueueDBColumns.TRANSACTIONID.getName()));
				paymentInQueueData.setClientId(rs.getString(PayInReportQueueDBColumns.TRADEACCOUNTNUM.getName()));
				paymentInQueueData.setAccountId(rs.getInt(PayInReportQueueDBColumns.ACCOUNTID.getName()));
				paymentInQueueData.setContactId(rs.getInt(PayInReportQueueDBColumns.CONTACTID.getName()));

				if (null != rs.getTimestamp(PayInReportQueueDBColumns.DATE.getName())) {
					paymentInQueueData.setDate(DateTimeFormatter.dateTimeFormatter(rs.getTimestamp(PayInReportQueueDBColumns.DATE.getName())));
				}
				paymentInQueueData.setContactName(rs.getString(PayInReportQueueDBColumns.CLIENTNAME.getName()));
				paymentInQueueData.setType(CustomerTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInReportQueueDBColumns.TYPE.getName())));
				paymentInQueueData.setSellCurrency(rs.getString(PayInReportQueueDBColumns.SELLCURRENCY.getName()));
				/**Code changed to show amount upto 2 decimal places on queue*/
				String amount = rs.getString(PayInReportQueueDBColumns.AMOUNT.getName());
				if (amount != null && amount.contains(".")) {
					amount = amount.substring(0, amount.indexOf('.')+3);
					paymentInQueueData.setAmount(com.currenciesdirect.gtg.compliance.util.StringUtils.getNumberFormat(amount));
				} else {
					paymentInQueueData.setAmount(com.currenciesdirect.gtg.compliance.util.StringUtils.getNumberFormat(amount));
				}
				paymentInQueueData.setMethod(rs.getString(PayInReportQueueDBColumns.METHOD.getName()));
				paymentInQueueData.setCountry(rs.getString(PayInReportQueueDBColumns.COUNTRY.getName()));
				String countryFullName = rs.getString(PayInReportQueueDBColumns.COUNTRY_DISPLAY_NAME.getName());
				setCountryFullName(paymentInQueueData, countryFullName);
				paymentInQueueData.setOverallStatus(rs.getString(PayInReportQueueDBColumns.OVERALLSTATUS.getName()));
				paymentInQueueData.setWatchlist(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInReportQueueDBColumns.WATCHLISTSTATUS.getName())));
				paymentInQueueData.setFraugster(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.FRAUGSTERSTATUS.getName())));
				paymentInQueueData.setSanction(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.SANCTIONSTATUS.getName())));
				paymentInQueueData.setBlacklist(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.BLACKLISTSTATUS.getName())));
				paymentInQueueData.setCustomCheck(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.CUSTOMCHECKSTATUS.getName())));
				paymentInQueueData.setIntuitionStatus(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.INTUITIONSTATUS.getName()))); //AT-4607
				// hidden values on UI
				Integer payInId = rs.getInt(PayInReportQueueDBColumns.PAYMENTINID.getName());
				if (payInId != null) {
					paymentInQueueData.setPaymentInId(payInId.toString());
				}
				paymentInQueueData.setOrganization(rs.getString(PayInReportQueueDBColumns.ORGANIZATION.getName()));
				paymentInQueueData.setRiskStatus(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.RISKSTATUS.getName())));
				paymentInQueueData.setLegalEntity(rs.getString(PayInReportQueueDBColumns.LEGALENTITY.getName()));
				String lockedBy = rs.getString(PayInReportQueueDBColumns.LOCKED_BY.getName());
				if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
					paymentInQueueData
							.setUserResourceLockId(rs.getInt(PayInReportQueueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
					paymentInQueueData.setLocked(Boolean.TRUE);
					paymentInQueueData.setLockedBy(lockedBy);
				}
				paymentInQueueDataList.add(paymentInQueueData);
			}
	
	private String getPaymentInReportQuery(String baseQuery, String contactFilterQuery, String accountFilterQuery, 
			String payInAttributeFilterQuery, String payInFilterQuery) {
		return (baseQuery.replace("{PAYMENTIN_REPORT_CONTACT_FILTER}", contactFilterQuery)
				.replace("{PAYMENTIN_REPORT_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{PAYMENTIN_ATTRIBUTE_FILTER}", payInAttributeFilterQuery)
				.replace("{PAYMENTIN_REPORT_FILTER}", payInFilterQuery) );
	}

	@Override
	public PaymentInQueueDto getPaymentInReportWithCriteria(PaymentInReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
		int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
		int rowsToFetch = SearchCriteriaUtil.getPageSize();
		String payInReport = "PaymentInReport";
		PaymentInQueueDto paymentInQueueDto ;
		Connection connection = null;
		PaymentInReportContactFilterQueryBuilder contactFilter = new PaymentInReportContactFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentInReportQueryConstant.PAYMENTIN_REPORT_CONTACT_ATTRIBUTE_FILTER.getQuery(),
				 true);
		String contactFilterQuery = getPayInContactFilterQuery(contactFilter, searchCriteria);
		String accountFilterQuery = getPayInAccountFilterQuery(searchCriteria);
		String payInAttributeFilterQuery = getPayInAttributeFilterQuery(searchCriteria);
		FilterQueryBuilder paymentInFilter=getPayInReportFilterQuery(searchCriteria);
		String contact = getContactFilterJoin(contactFilterQuery);
		String account = getAccountFilterJoin(accountFilterQuery);
		String attribute = getAttributeFilterJoin(payInAttributeFilterQuery);
		String org = getOrganizationJoin(searchCriteria);
		String legalEntity = getLegalEntityJoin(searchCriteria);
		String user = getUserJoin(searchCriteria);
		String country = getCountryJoin(searchCriteria);
		String joinRG=getRGJoin(searchCriteria);
		String payInFilterQuery = paymentInFilter.getQuery();
		
		if(contactFilter.isFilterAppliedForAccountAndContact() && account.length() > 1 ){
			payInFilterQuery = getAccountContactJoin(payInFilterQuery);
		}else{
			contact = contact.replace("LEFT", "");
			account = account.replace("LEFT", "");
		}
		
		 payInAttributeFilterQuery =  payInAttributeFilterQuery.replace("{COUNTRY_JOIN}", country);
		
			 payInFilterQuery = payInFilterQuery .replace("{CONTACT_FILTER_JOIN}", contact)
						.replace("{ACCOUNT_FILTER_JOIN}", account)
						.replace("{ATTRIBUTE_FILTER_JOIN}", attribute)
						.replace("{ORG_TABLE_JOIN}", org)
						.replace("{LEGAL_ENTITY_TABLE_JOIN}",legalEntity)
						.replace("{RG_FILTER_JOIN}",joinRG)
						.replace("{USER_JOIN}",user);
		 
		String paymentInReportQuery = getPaymentInReportQuery(PaymentInReportQueryConstant.GET_PAYMENTIN_REPORT.getQuery(),
				contactFilterQuery, accountFilterQuery, payInAttributeFilterQuery, payInFilterQuery);
		
		paymentInReportQuery = executeSort(searchCriteria.getFilter().getSort(),paymentInReportQuery);
		
		String countQuery = getPaymentInReportQuery(PaymentInReportQueryConstant.PAYMENTIN_REPORT_COUNT.getQuery(),
				contactFilterQuery, accountFilterQuery, payInAttributeFilterQuery, payInFilterQuery);
		
		if(searchCriteria.getIsLandingPage() != null && searchCriteria.getIsLandingPage()){
			countQuery = null;
		}
		try {
			connection = getConnection(Boolean.TRUE);
			paymentInQueueDto  = getPaymentInData( connection, offset, rowsToFetch,paymentInReportQuery, countQuery);
			Integer totalRecords = 0;
			totalRecords = paymentInQueueDto.getTotalRecords();
			Integer pageSize = SearchCriteriaUtil.getPageSize();
				getPageAndOtherDetails(searchCriteria, minRowNum, paymentInQueueDto, connection, totalRecords,
						pageSize);
			SavedSearch savedSearch = null;
			savedSearch = getSaveSearches(searchCriteria.getFilter().getUserProfile().getId(),payInReport,connection);
			paymentInQueueDto.setSavedSearch(savedSearch);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return paymentInQueueDto;
	}
	
	private String executeSort(Sort sortPayIn,String query) {
		String colName = "TransactionDate";
		boolean isAsc = false;
		if(sortPayIn != null && sortPayIn.getIsAscend() != null){
			isAsc = sortPayIn.getIsAscend();
		}
		
		if(sortPayIn != null && sortPayIn.getFieldName() != null &&  !sortPayIn.getFieldName().isEmpty()){
			colName = sortPayIn.getFieldName();
		}
		
		String columnNameQuery = "payin.TransactionDate";
		if("TransactionDate".equalsIgnoreCase(colName)){
			columnNameQuery = "payin.TransactionDate";
		} else if ("tradeaccountnumber".equalsIgnoreCase(colName)){
			columnNameQuery = "acc.tradeaccountnumber";
		} else if ("type".equalsIgnoreCase(colName)){
			columnNameQuery = "acc.type";
		}
		
		return addSort(columnNameQuery, isAsc,query);
		
		
	}

	private void getPageAndOtherDetails(PaymentInReportSearchCriteria searchCriteria, int minRowNum,
			PaymentInQueueDto paymentInQueueDto, Connection connection, Integer totalRecords, Integer pageSize)
			throws CompliancePortalException {
		if(totalRecords != null){
			Integer totalPages;
			totalPages = totalRecords / pageSize;
			if (totalRecords % pageSize != 0) {
				totalPages += 1;
			}
			Page page = new Page();
			page.setMinRecord(minRowNum);
			page.setMaxRecord(minRowNum + paymentInQueueDto.getPaymentInQueue().size() - 1);
			page.setTotalPages(totalPages);
			page.setTotalRecords(totalRecords);
			if (page.getTotalRecords() == 0) {
				page.setMinRecord(0);
			}
			// added by neelesh pant
			page.setCurrentPage(SearchCriteriaUtil.getCurrentPage(searchCriteria));
			paymentInQueueDto.setPage(page);
		}
		List<String> organization;
		organization = getOrganization(connection);
		paymentInQueueDto.setOrganization(organization);
		List<String> currency;
		currency = getCurrency(connection);
		paymentInQueueDto.setCurrency(currency);
		List<String> countryofFund;
		countryofFund = getAllCountries(connection);
		paymentInQueueDto.setCountry(countryofFund);
		paymentInQueueDto.setSource(SourceEnum.getSourceValues());
		paymentInQueueDto.setTransValue(TransactionValuesEnum.getTransactionValues());
		paymentInQueueDto.setPaymentMethod(PaymentMethodEnum.getPaymentMethodValues());
		Watchlist watchList;
		boolean category1 = searchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1();
		boolean category2 = searchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2();
		watchList = getWatchListReasonId(connection , category1 , category2);
		paymentInQueueDto.setWatchList(watchList);
		List<String> owner;
		owner = getLockedUserName(connection);
		paymentInQueueDto.setOwner(owner);
		List<String> paymentStatus;
		paymentStatus = getPaymentStatus(connection);
		paymentInQueueDto.setPaymentStatus(paymentStatus);
		List<String> legalEntity = getLegalEntity(connection);
		paymentInQueueDto.setLegalEntity(legalEntity);
		searchCriteria.setPage(paymentInQueueDto.getPage());
		paymentInQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
	}

	private String getAccountContactJoin(String payinFilterQuery) {
		String payInFilterQueryResult;
		if(payinFilterQuery.contains(Constants.WHERE)){
			payInFilterQueryResult = payinFilterQuery+" AND "+ PaymentInReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}else{
			payInFilterQueryResult = payinFilterQuery+" WHERE "+ PaymentInReportQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}
		return payInFilterQueryResult;
	}

	private String getCountryJoin(PaymentInReportSearchCriteria searchCriteria) {
		String countryJoin = "";
		if(null != searchCriteria.getFilter().getCountryofFund() &&
				searchCriteria.getFilter().getCountryofFund().length>0 ){
			countryJoin = PaymentInReportQueryConstant.COUNTRY_FILTER_JOIN.getQuery();
		}
		return countryJoin;
	}
	//AT-1646 RG filter changes
	private String getRGJoin(PaymentInReportSearchCriteria searchCriteria) {
		String rgJoin = "";
		if (null != searchCriteria.getFilter().getRiskStatus()
				&& searchCriteria.getFilter().getRiskStatus().length > 0) {
			rgJoin = PaymentInReportQueryConstant.RG_FILTER_JOIN.getQuery();
		}
		return rgJoin;
	}

	private String getUserJoin(PaymentInReportSearchCriteria searchCriteria) {
		String userJoin = "";
		if(null != searchCriteria.getFilter().getOwner() &&
				searchCriteria.getFilter().getOwner().length>0){
			userJoin = PaymentInReportQueryConstant.USER_FILTER_JOIN.getQuery();
		}
		return userJoin;
	}

	private String getOrganizationJoin(PaymentInReportSearchCriteria searchCriteria) {
		String orgJoin = "";
		if(null != searchCriteria.getFilter().getOrganization() && 
				searchCriteria.getFilter().getOrganization().length>0){
			orgJoin = PaymentInReportQueryConstant.ORG_TABLE_JOIN.getQuery();
		}
		return orgJoin;
	}
	
	private String getLegalEntityJoin(PaymentInReportSearchCriteria searchCriteria) {
		String legalEntityJoin = "";
		if(null != searchCriteria.getFilter().getLegalEntity() && 
				searchCriteria.getFilter().getLegalEntity().length>0) {
			legalEntityJoin = PaymentInReportQueryConstant.LEGAL_ENTITY_TABLE_JOIN.getQuery();
		}
		return legalEntityJoin;
	}

	private String getAttributeFilterJoin(String payInAttributeFilterQuery) {
		String attributeJoin = "";
		if(payInAttributeFilterQuery.contains(Constants.WHERE)){
			attributeJoin = PaymentInReportQueryConstant.PAYMENTIN_REPORT_ATTRIBUTE_FILTER_JOIN.getQuery();
		}
		return attributeJoin;
	}

	private String getAccountFilterJoin(String accountFilterQuery) {
		String accountJoin = "";
		if(accountFilterQuery.contains(Constants.WHERE)){
			accountJoin = PaymentInReportQueryConstant.PAYMENTIN_REPORT_ACCOUNT_FILTER_JOIN.getQuery();
		}
		return accountJoin;
	}

	private String getContactFilterJoin(String contactFilterQuery) {
		String contactJoin = "";
		if(contactFilterQuery.contains(Constants.WHERE) || contactFilterQuery.contains("category")){
			contactJoin = PaymentInReportQueryConstant.PAYMENTIN_REPORT_CONTACT_FILTER_JOIN.getQuery();
		}
		return contactJoin;
	}

	private FilterQueryBuilder getPayInReportFilterQuery(PaymentInReportSearchCriteria searchCriteria) {
		return new PaymentInReportFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentInReportQueryConstant.PAYMENTIN_REPORT_FILTER.getQuery(),Boolean.TRUE);
	}
	
	private String getPayInAttributeFilterQuery(PaymentInReportSearchCriteria searchCriteria) {
		FilterQueryBuilder payInAttributeFilter = new PaymentInReportAtttributeFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentInReportQueryConstant.PAYMENTIN_REPORT_ATTRIBUTE_FILTER.getQuery(), Boolean.TRUE);
		return payInAttributeFilter.getQuery();
	}

	private String getPayInAccountFilterQuery(PaymentInReportSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new PaymentInReportAccountFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentInReportQueryConstant.PAYMENTIN_REPORT_ACCOUNT_ATTRIBUTE_FILTER.getQuery(),
				 true);
		return  accountFilter.getQuery();
		}

	private String getPayInContactFilterQuery(PaymentInReportContactFilterQueryBuilder contactFilter,
				PaymentInReportSearchCriteria searchCriteria) {
		String watchListFilter = "";
		String filter=StringUtils.arrayToCommaDelimitedString(searchCriteria.getFilter().getWatchListReason());
		String contactFilterQuery = contactFilter.getQuery();
		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if(null!= watchListCategoryFilter ){
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", watchListCategoryFilter)  ;
		}else{
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", "")  ;
		}
		if(null != filter && filter.length()>0){
			if(contactFilterQuery.contains(Constants.WHERE)){
				watchListFilter = "AND "+ PaymentInReportQueryConstant.WATCHLIST_FILTER.getQuery()
						.replace("?", filter);
			}else{
				watchListFilter = " WHERE " + PaymentInReportQueryConstant.WATCHLIST_FILTER.getQuery()
				.replace("?", filter);
			}
		}
		contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_FILTER}", watchListFilter);
		
		String countryJoin = "";
		if(contactFilter.isCountryOfResidenceFilterApplied()){
			countryJoin = PaymentInReportQueryConstant.PAYMENTIN_REPORT_COUNTRY_OF_RESIDENCE_FILTER.getQuery();
		}
		contactFilterQuery = contactFilterQuery.replace("{PAYMENTIN_REPORT_COUNTRY_OF_RESIDENCE_FILTER}", countryJoin);
		
		return contactFilterQuery;
	}
	
	private String getWatchListCategoryFilter(PaymentInReportSearchCriteria payInSearchCriteria) {
		String status = null;
		boolean hasPermissionAll = false;
		if (payInSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1()) {
			status = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		}
		if (payInSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2()) {
			if (null != status)
				hasPermissionAll = true;
			else
				status = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();

		}
		if (hasPermissionAll)
			status = null;
		else {
			if (null != status)
				status = PaymentInReportQueryConstant.WATCHLIST_CATEGORY_FILTER.getQuery().replace("?", status);
		}

		return status;
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest request) {
		return null;
	}

	@Override
	public PaymentInQueueDto getPaymentInQueueWholeData(PaymentInReportSearchCriteria searchCriteria)
			throws CompliancePortalException {

		Connection connection = null;
		PaymentInQueueDto paymentInQueueDto;
		try {
			PaymentInReportContactFilterQueryBuilder contactFilter = new PaymentInReportContactFilterQueryBuilder(searchCriteria.getFilter(),
					PaymentInReportQueryConstant.PAYMENTIN_REPORT_CONTACT_ATTRIBUTE_FILTER.getQuery(),
					 true);
			String contactFilterQuery = getPayInContactFilterQuery(contactFilter, searchCriteria);
			String accountFilterQuery = getPayInAccountFilterQuery(searchCriteria);
			String payInAttributeFilterQuery = getPayInAttributeFilterQuery(searchCriteria);
			FilterQueryBuilder paymentInFilter=getPayInReportFilterQuery(searchCriteria);
			String contactJoin = getContactFilterJoin(contactFilterQuery);
			String accountJoin = getAccountFilterJoin(accountFilterQuery);
			String attributeJoin = getAttributeFilterJoin(payInAttributeFilterQuery);
			String orgJoin = getOrganizationJoin(searchCriteria);
			String legalEntityJoin = getLegalEntityJoin(searchCriteria);
			String userJoin = getUserJoin(searchCriteria);
			String countryJoin = getCountryJoin(searchCriteria);
			String joinRG=getRGJoin(searchCriteria); //added For AT-3227
			 
			String payinFilterQuery = paymentInFilter.getQuery();

			if(contactFilter.isFilterAppliedForAccountAndContact() && accountJoin.length() > 1 ){
				payinFilterQuery = getAccountContactJoin(payinFilterQuery);
			} else {
				contactJoin = contactJoin.replace("LEFT", "");
				accountJoin = accountJoin.replace("LEFT", "");
			}

			payInAttributeFilterQuery = payInAttributeFilterQuery.replace("{COUNTRY_JOIN}", countryJoin);
			
					payinFilterQuery = payinFilterQuery.replace("{CONTACT_FILTER_JOIN}", contactJoin)
							.replace("{ACCOUNT_FILTER_JOIN}", accountJoin).replace("{ATTRIBUTE_FILTER_JOIN}", attributeJoin)
							.replace("{ORG_TABLE_JOIN}", orgJoin).replace("{LEGAL_ENTITY_TABLE_JOIN}",legalEntityJoin)
							.replace("{USER_JOIN}", userJoin).replace("{RG_FILTER_JOIN}",joinRG);
			
			String paymentInReportQuery = getPaymentInReportQuery(PaymentInReportQueryConstant.GET_PAYMENTIN_REPORT_DATA.getQuery(),
					contactFilterQuery, accountFilterQuery, payInAttributeFilterQuery, payinFilterQuery);

			paymentInReportQuery = executeSort(searchCriteria.getFilter().getSort(),paymentInReportQuery);
			
			paymentInQueueDto = new PaymentInQueueDto();

			connection = getConnection(Boolean.TRUE);
			paymentInQueueDto.setPaymentInQueue(getPaymentInQueueWholeData(connection, paymentInReportQuery));

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return paymentInQueueDto;
		
	}

	
	private List<PaymentInQueueData> getPaymentInQueueWholeData(Connection connection,String query) throws CompliancePortalException {
		List<PaymentInQueueData> paymentInQueueDataList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(query);
			rs = statement.executeQuery();
			while (rs.next()) {
				PaymentInQueueData paymentInQueueData = new PaymentInQueueData();
				paymentInQueueData.setTransactionId(rs.getString(PayInQueDBColumns.TRANSACTIONID.getName()));
				paymentInQueueData.setClientId(rs.getString(PayInQueDBColumns.TRADEACCOUNTNUM.getName()));
				paymentInQueueData.setAccountId(rs.getInt(PayInQueDBColumns.ACCOUNTID.getName()));
				paymentInQueueData.setContactId(rs.getInt(PayInQueDBColumns.CONTACTID.getName()));
			    paymentInQueueData.setInitialStatus(rs.getString(PayInQueDBColumns.INITIALSTATUS.getName()));//AT-3472
				if (null != rs.getTimestamp(PayInQueDBColumns.DATE.getName())) {
				paymentInQueueData.setDate(DateTimeFormatter.dateTimeFormatter(rs.getTimestamp(PayInReportQueueDBColumns.DATE.getName())));
				}
				
				paymentInQueueData.setContactName(rs.getString(PayInQueDBColumns.CLIENTNAME.getName()));
				paymentInQueueData.setType(rs.getString(PayInQueDBColumns.TYPE.getName()));
				paymentInQueueData.setSellCurrency(rs.getString(PayInQueDBColumns.SELLCURRENCY.getName()));

				Integer amount = rs.getInt(PayInQueDBColumns.AMOUNT.getName());
				if (amount != null) {
					paymentInQueueData.setAmount(amount.toString());
				}

				paymentInQueueData.setMethod(rs.getString(PayInQueDBColumns.METHOD.getName()));
				paymentInQueueData.setCountry(rs.getString(PayInQueDBColumns.COUNTRY.getName()));
				String countryFullName = rs.getString(PayInQueDBColumns.COUNTRY_DISPLAY_NAME.getName());
				setCountryFullName(paymentInQueueData, countryFullName);
				paymentInQueueData.setOverallStatus(rs.getString(PayInQueDBColumns.OVERALLSTATUS.getName()));
				paymentInQueueData.setWatchlist(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.WATCHLISTSTATUS.getName())));
				if(paymentInQueueData.getWatchlist() == null){
					paymentInQueueData.setWatchlist(Constants.DASH_UI);
				}
				paymentInQueueData.setFraugster(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.FRAUGSTERSTATUS.getName())));
				paymentInQueueData.setSanction(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.SANCTIONSTATUS.getName())));
				paymentInQueueData.setBlacklist(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.BLACKLISTSTATUS.getName())));
				paymentInQueueData.setCustomCheck(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.CUSTOMCHECKSTATUS.getName())));
				paymentInQueueData.setRiskStatus(ServiceStatusEnum
						.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.RISKSTATUS.getName())));
				paymentInQueueData.setIntuitionStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.INTUITIONSTATUS.getName()))); //AT-4717
				// hidden values on UI
				Integer paymentInId = rs.getInt(PayInQueDBColumns.PAYMENTINID.getName());
				if (paymentInId != null) {
					paymentInQueueData.setPaymentInId(paymentInId.toString());
				}
				paymentInQueueData.setOrganization(rs.getString(PayInQueDBColumns.ORGANIZATION.getName()));
				paymentInQueueData.setLegalEntity(rs.getString(PayInQueDBColumns.LEGALENTITY.getName())); //Added for AT-3011
				String lockBy = rs.getString(PayInQueDBColumns.LOCKED_BY.getName());
				if (lockBy != null && !lockBy.isEmpty() && !lockBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
					paymentInQueueData
							.setUserResourceLockId(rs.getInt(PayInQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
					paymentInQueueData.setLocked(Boolean.TRUE);
					paymentInQueueData.setLockedBy(lockBy);
				}
				paymentInQueueDataList.add(paymentInQueueData);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return paymentInQueueDataList;
	}

	private void setCountryFullName(PaymentInQueueData paymentInQueueData, String countryFullName) {
		if (null != countryFullName){
			paymentInQueueData.setCountryFullName(countryFullName);
		}else {
			paymentInQueueData.setCountryFullName(Constants.DASH_UI);
		}
	}
	
}
