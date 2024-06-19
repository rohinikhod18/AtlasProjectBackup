package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.ActivityType;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentInActivityLogDto;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.IPaymentInDBService;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.SourceEnum;
import com.currenciesdirect.gtg.compliance.core.domain.Status;
import com.currenciesdirect.gtg.compliance.core.domain.StatusData;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.TransactionValuesEnum;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.dbport.cfx.RegistrationCfxDBQueryConstant;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.EntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentMethodEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.dbport.report.PayInReportQueueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.report.PaymentInReportQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class PaymentInDBServiceImpl.
 */
@Component("payInDBServiceImpl")
public class PaymentInDBServiceImpl extends AbstractDBServicePaymentIn implements IPaymentInDBService {

	@Autowired
	@Qualifier("paymentInDetailsTransformer")
	private ITransform<PaymentInDetailsDto, PaymentInDBDto> itransform;

	@Autowired
	@Qualifier("paymentInActivityLogTransformer")
	private ITransform<ActivityLogs, PaymentInUpdateDBDto> paymentInActivityLogTransformer;
	
	/** The Constant WHERE. */
	private static final String WHERE = "WHERE";

	private PaymentInQueueDto getPaymentInData(Connection connection, int offSet, int rowsToFetch, String query,
			String countQuery) throws CompliancePortalException {
		PaymentInQueueDto paymentInQueueDto = new PaymentInQueueDto();
		List<PaymentInQueueData> paymentInQueueDataList = new ArrayList<>();
		PreparedStatement statement = null;
		PreparedStatement countStatement = null;
		ResultSet rs = null;
		ResultSet countRs = null;
		Integer totalRecords = 0;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, offSet);
			statement.setInt(2, rowsToFetch);
			rs = statement.executeQuery();
			while (rs.next()) {
				setPaymentInDataQueueList(paymentInQueueDataList, rs);
			}
			countStatement = connection.prepareStatement(countQuery);
			countRs = countStatement.executeQuery();
			if (countRs.next()) {
				totalRecords = countRs.getInt(RegQueDBColumns.TOTALROWS.getName());
			}
			List<String> owner = null;
			owner = getLockedUserName(connection);
			paymentInQueueDto.setOwner(owner);
			paymentInQueueDto.setPaymentInQueue(paymentInQueueDataList);
			paymentInQueueDto.setTotalRecords(totalRecords);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeResultset(countRs);
			closePrepareStatement(countStatement);
		}
		return paymentInQueueDto;
	}

	private void setPaymentInDataQueueList(List<PaymentInQueueData> paymentInQueueDataList, ResultSet rs)
			throws SQLException {
		PaymentInQueueData paymentInQueueData = new PaymentInQueueData();
		paymentInQueueData.setTransactionId(rs.getString(PayInQueDBColumns.TRANSACTIONID.getName()));
		paymentInQueueData.setClientId(rs.getString(PayInQueDBColumns.TRADEACCOUNTNUM.getName()));
		paymentInQueueData.setAccountId(rs.getInt(PayInQueDBColumns.ACCOUNTID.getName()));
		paymentInQueueData.setContactId(rs.getInt(PayInQueDBColumns.CONTACTID.getName()));

		if (null != rs.getTimestamp(PayInQueDBColumns.DATE.getName())) {
			paymentInQueueData.setDate(
					DateTimeFormatter.dateTimeFormatter(rs.getTimestamp(PayInReportQueueDBColumns.DATE.getName())));
		}

		paymentInQueueData.setContactName(rs.getString(PayInQueDBColumns.CLIENTNAME.getName()));
		paymentInQueueData
				.setType(CustomerTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.TYPE.getName())));
		paymentInQueueData.setSellCurrency(rs.getString(PayInQueDBColumns.SELLCURRENCY.getName()));
		/** Code changed to show amount upto 2 decimal places on queue */
		String amount = rs.getString(PayInQueDBColumns.AMOUNT.getName());
		if (amount != null && amount.contains(".")) {
			amount = amount.substring(0, amount.indexOf('.') + 3);
			paymentInQueueData.setAmount(StringUtils.getNumberFormat(amount));
		} else {
			paymentInQueueData.setAmount(StringUtils.getNumberFormat(amount));
		}
		paymentInQueueData.setMethod(rs.getString(PayInQueDBColumns.METHOD.getName()));
		paymentInQueueData.setCountry(rs.getString(PayInQueDBColumns.COUNTRY.getName()));
		String countryFullName = rs.getString(PayInQueDBColumns.COUNTRY_DISPLAY_NAME.getName());
		if (null != countryFullName) {
			paymentInQueueData.setCountryFullName(countryFullName);
		} else {
			paymentInQueueData.setCountryFullName(Constants.DASH_UI);
		}
		paymentInQueueData.setOverallStatus(rs.getString(PayInQueDBColumns.OVERALLSTATUS.getName()));
		paymentInQueueData.setWatchlist(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.WATCHLISTSTATUS.getName())));
		paymentInQueueData.setFraugster(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.FRAUGSTERSTATUS.getName())));
		paymentInQueueData.setSanction(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.SANCTIONSTATUS.getName())));
		paymentInQueueData.setBlacklist(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.BLACKLISTSTATUS.getName())));
		paymentInQueueData.setCustomCheck(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.CUSTOMCHECKSTATUS.getName())));
		paymentInQueueData.setIntuitionStatus(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.INTUITIONSTATUS.getName()))); // AT-4607

		// hidden values on UI
		Integer payInId = rs.getInt(PayInQueDBColumns.PAYMENTINID.getName());
		if (payInId != null) {
			paymentInQueueData.setPaymentInId(payInId.toString());
		}
		paymentInQueueData.setOrganization(rs.getString(PayInQueDBColumns.ORGANIZATION.getName()));
		paymentInQueueData.setLegalEntity(rs.getString(PayInQueDBColumns.LEGALENTITY.getName()));
		paymentInQueueData.setRiskStatus(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayInQueDBColumns.RISKSTATUS.getName())));
		String lockedBy = rs.getString(PayInQueDBColumns.LOCKED_BY.getName());
		if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			paymentInQueueData.setUserResourceLockId(rs.getInt(PayInQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
			paymentInQueueData.setLocked(Boolean.TRUE);
			paymentInQueueData.setLockedBy(lockedBy);
		}
		paymentInQueueDataList.add(paymentInQueueData);
	}

	@Override
	public PaymentInQueueDto getPaymentInQueueWithCriteria(PaymentInSearchCriteria searchCriteria)
			throws CompliancePortalException {
		int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
		int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
		int rowsToFetch = SearchCriteriaUtil.getPageSize();
		String paymentInQueue = "PaymentInQueue";
		PaymentInQueueDto paymentInQueueDto;
		Connection connection = null;
		PaymentInQueueContactFilterQueryBuilder contactFilter = new PaymentInQueueContactFilterQueryBuilder(
				searchCriteria.getFilter(), PaymentInQueryConstant.PAYMENTIN_QUEUE_CONTACT_ATTRIBUTE_FILTER.getQuery(),
				true);

		String contactFilterQuery = getPayInContactFilterQuery(contactFilter, searchCriteria);

		String accountFilterQuery = getPayInAccountFilterQuery(searchCriteria);
		String payInAttributeFilterQuery = getPayInAttributeFilterQuery(searchCriteria);
		FilterQueryBuilder paymentInFilter = getPayInQueueFilterQuery(searchCriteria);

		String joinContact = getContactFilterJoin(contactFilterQuery);
		String joinAccount = getAccountFilterJoin(accountFilterQuery);
		String joinAttribute = getAttributeFilterJoin(payInAttributeFilterQuery);
		String joinOrg = getOrganizationJoin(searchCriteria);
		String joinLegalEntity = getLegalEntityJoin(searchCriteria);
		String joinUser = getUserJoin(searchCriteria);
		String joinCountry = getCountryJoin(searchCriteria);
		//line added for RG Filter (At-1646)
		String joinRG=getRGJoin(searchCriteria);
		String paymentInFilterQuery = paymentInFilter.getQuery();

		if (contactFilter.isFilterAppliedForAccountAndContact() && joinAccount.length() > 1) {
			paymentInFilterQuery = getAccountContactJoin(paymentInFilterQuery);
		} else {
			joinContact = joinContact.replace("LEFT", "");
			joinAccount = joinAccount.replace("LEFT", "");
		}

		payInAttributeFilterQuery = payInAttributeFilterQuery.replace("{COUNTRY_JOIN}", joinCountry);
		paymentInFilterQuery = paymentInFilterQuery.replace("{CONTACT_FILTER_JOIN}", joinContact)
				.replace("{ACCOUNT_FILTER_JOIN}", joinAccount).replace("{ATTRIBUTE_FILTER_JOIN}", joinAttribute)
				.replace("{ORG_TABLE_JOIN}", joinOrg).replace("{LEGAL_ENTITY_TABLE_JOIN}",joinLegalEntity)
				.replace("{RG_FILTER_JOIN}",joinRG).replace("{USER_JOIN}", joinUser);

		String paymentInReportQuery = PaymentInQueryConstant.GET_PAYMENTIN_QUEUE.getQuery()
				.replace("{PAYMENTIN_REPORT_CONTACT_FILTER}", contactFilterQuery)
				.replace("{PAYMENTIN_REPORT_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{PAYMENTIN_ATTRIBUTE_FILTER}", payInAttributeFilterQuery)
				.replace("{PAYMENTIN_REPORT_FILTER}", paymentInFilterQuery);

		paymentInReportQuery = executeSort(searchCriteria.getFilter().getSort(), paymentInReportQuery);

		String countQuery = PaymentInQueryConstant.PAYMENTIN_QUEUE_COUNT.getQuery()
				.replace("{PAYMENTIN_REPORT_CONTACT_FILTER}", contactFilterQuery)
				.replace("{PAYMENTIN_REPORT_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{PAYMENTIN_ATTRIBUTE_FILTER} ", payInAttributeFilterQuery)
				.replace("{PAYMENTIN_REPORT_FILTER} ", paymentInFilterQuery);

		try {
			connection = getConnection(Boolean.TRUE);
			paymentInQueueDto = getPaymentInData(connection, offset, rowsToFetch, paymentInReportQuery, countQuery);
			Integer totalRecords = paymentInQueueDto.getTotalRecords();
			Integer pageSize = SearchCriteriaUtil.getPageSize();
			if (totalRecords != null) {
				getPageAndOtherDetails(searchCriteria, minRowNum, paymentInQueueDto, connection, totalRecords,
						pageSize);
			}
			SavedSearch savedSearch = null;
			savedSearch = getSaveSearches(searchCriteria.getFilter().getUserProfile().getId(),paymentInQueue,connection);
			paymentInQueueDto.setSavedSearch(savedSearch);
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return paymentInQueueDto;
	}

	private String getWatchListCategoryFilter(PaymentInSearchCriteria searchCriteria) {
		String result = null;
		boolean hasAllPermission = false;
		if (searchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1()) {
			result = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		}
		if (searchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2()) {
			if (null != result)
				hasAllPermission = true;
			else
				result = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();

		}
		if (hasAllPermission)
			result = null;
		else {
			if (null != result)
				result = PaymentInQueryConstant.WATCHLIST_CATEGORY_FILTER.getQuery().replace("?", result);
		}

		return result;
	}

	private String executeSort(Sort sortDate, String query) {
		String clmnName = "TransactionDate";
		boolean isAscending = false;
		if (sortDate != null && sortDate.getIsAscend() != null) {
			isAscending = sortDate.getIsAscend();
		}

		if (sortDate != null && sortDate.getFieldName() != null && !sortDate.getFieldName().isEmpty()) {
			clmnName = sortDate.getFieldName();
		}

		String queryColumnName = "payin.TransactionDate";
		if ("TransactionDate".equalsIgnoreCase(clmnName)) {
			queryColumnName = "payin.TransactionDate";
		} else if ("tradeaccountnumber".equalsIgnoreCase(clmnName)) {
			queryColumnName = "acc.tradeaccountnumber";
		} else if ("type".equalsIgnoreCase(clmnName)) {
			queryColumnName = "acc.type";
		}

		return addSort(queryColumnName, isAscending, query);

	}

	private void getPageAndOtherDetails(PaymentInSearchCriteria searchCriteria, int minRowNum,
			PaymentInQueueDto paymentInQueueDto, Connection connection, Integer totalRecords, Integer pageSize)
			throws CompliancePortalException {
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
		List<String> legalEntity = getLegalEntity(connection);
		paymentInQueueDto.setLegalEntity(legalEntity);
		searchCriteria.setPage(page);
		paymentInQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
	}

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest actvityRequest) throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDataList);
		PreparedStatement prepareStmt = null;
		Connection connection = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			prepareStmt = connection
					.prepareStatement(PaymentInQueryConstant.GET_PAYMENTIN_ACTIVITY_LOGS_BY_ROWS.getQuery());
			prepareStmt.setInt(1, actvityRequest.getEntityId());
			prepareStmt.setInt(2, actvityRequest.getMinRecord() - 1);
			prepareStmt.setInt(3, actvityRequest.getRowToFetch());
			resultSet = prepareStmt.executeQuery();
			while (resultSet.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setActivity(resultSet.getString("Activity"));
				activityLogData
						.setActivityType(ActivityType.getActivityLogDisplay(resultSet.getString("ActivityType")));
				activityLogData.setCreatedBy(resultSet.getString("User"));
				Timestamp createdOn = resultSet.getTimestamp("CreatedOn");
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setComment(resultSet.getString("Comment"));
				activityLogDataList.add(activityLogData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {

			closeResultset(resultSet);
			closePrepareStatement(prepareStmt);
			closeConnection(connection);
		}
		return activityLogs;
	}

	@Override
	public PaymentInDetailsDto getPaymentInDetails(Integer paymentInId, String custType,
			PaymentInSearchCriteria searchCriteria) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PaymentInDBDto paymentInDBDto = new PaymentInDBDto();
		Integer accountId = null;

		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(PaymentInQueryConstant.GET_PAYMENT_IN_DETAILS.getQuery());
			statement.setInt(1, paymentInId);
			rs = statement.executeQuery();
			paymentInDBDto.setEventDBDtos(new HashMap<String, List<EventDBDto>>());
			while (rs.next()) {
				paymentInDBDto.setAccAttrib(rs.getString("AccountAttrib"));
				paymentInDBDto.setAccountId(rs.getInt("Accountid"));
				paymentInDBDto.setAccComplianceStatus(rs.getString("AccComplianceStatus"));
				paymentInDBDto.setConComplianceStatus(rs.getString("ContactComplianceStatus"));
				paymentInDBDto.setContactAttib(rs.getString("ContactAttrib"));
				paymentInDBDto.setContactId(rs.getInt("Contactid"));
				paymentInDBDto.setContactName(rs.getString("ContactName"));
				paymentInDBDto.setContractNumber(rs.getString("TradeContractNumber"));
				paymentInDBDto.setCountry(rs.getString("Country"));
				paymentInDBDto.setCountryOfFundFullName(rs.getString("CountryOfFundFullName"));
				paymentInDBDto.setCrmAccountId(rs.getString("Crmaccountid"));
				paymentInDBDto.setCrmContactId(rs.getString("Crmcontactid"));
				paymentInDBDto.setDateOfPayment(rs.getTimestamp("DateOfPayment").toString());
				paymentInDBDto.setOrgCode(rs.getString("Organisation"));
				paymentInDBDto.setPaymentInId(rs.getInt("Id"));
				paymentInDBDto.setPaymentMethod(rs.getString("vPaymentMethod"));
				paymentInDBDto.setSellCurrency(rs.getString("SellCurrency"));
				paymentInDBDto.setRegComp(rs.getTimestamp("RegComplete"));
				paymentInDBDto.setRegIn(rs.getTimestamp("RegIn"));
				paymentInDBDto.setTradeAccountNum(rs.getString("Tradeaccountnumber"));
				paymentInDBDto.setTradeContactId(rs.getString("TradeContactId"));
				paymentInDBDto.setTradePaymentId(rs.getString("Tradepaymentid"));
				paymentInDBDto.setUserResourceId(rs.getInt("UserResourceLockId"));
				paymentInDBDto.setPaymentInStatus(rs.getString("PaymentInStaus"));
				paymentInDBDto.setDebitorName(rs.getString("vDebitorName"));
				paymentInDBDto.setPaymentInAttributes(rs.getString("PaymentInAttributes"));
				paymentInDBDto.setAmount(rs.getString("Amount"));
				paymentInDBDto.setIsOnQueue(rs.getBoolean("isOnQueue"));
				paymentInDBDto.setIsDeleted(String.valueOf(rs.getBoolean("Deleted")));
				paymentInDBDto.setPoiExists(rs.getString("poiExists")); //AT-3450
				paymentInDBDto.setInitialStatus(rs.getString("InitialStatus"));//AT-3471
	            paymentInDBDto.setIntuitionRiskLevel(rs.getString("IntuitionRiskLevel")); // AT-4187
	            paymentInDBDto.setAccountTMFlag(rs.getInt("AccountTMFlag"));
	            paymentInDBDto.setAccountVersion(rs.getInt("AccountVersion"));
				
				paymentInDBDto
						.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(rs.getTimestamp("UpdatedOn")));
				String lockedBy = rs.getString("LockedBy");
				if (null != lockedBy && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
					paymentInDBDto.setLockedBy(lockedBy);
				}
				String serviceType = rs.getString("ServiceType");
				String entityType = EntityTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt("EntityType"));
				entityType = setEntityType(entityType);
				List<EventDBDto> eventListByEntityTpeService = paymentInDBDto.getEventDBDtos()
						.get(serviceType + entityType);
				if (eventListByEntityTpeService == null) {
					paymentInDBDto.getEventDBDtos().put(serviceType + entityType, new ArrayList<EventDBDto>());
				}
				paymentInDBDto.setThirdPartyPayment(rs.getBoolean("IsThirdParty"));
				paymentInDBDto.setLegalEntity(rs.getString("LegalEntity"));
				EventDBDto eventDBDto = new EventDBDto();

				eventDBDto.setEitityType(entityType);
				eventDBDto.setEntityId(rs.getString("EntityId"));
				eventDBDto.setId(rs.getInt("EventServiceLogId"));
				eventDBDto.setServiceType(rs.getString("ServiceType"));
				eventDBDto.setStatus(ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt("EventServiceStatus")));
				eventDBDto.setIntuitionCurrentAction(rs.getString("intuitionCurrentAction"));
				
				eventDBDto.setSummary(rs.getString("Summary"));
				eventDBDto.setUpdatedBy(rs.getString("EventServiceUpdatedBy"));
				eventDBDto.setUpdatedOn(rs.getTimestamp("EventServiceUpdatedOn"));
				paymentInDBDto.getEventDBDtos().get(serviceType + entityType).add(eventDBDto);
			}
			accountId = paymentInDBDto.getAccountId();
			paymentInDBDto.setWatchList(getAccountWatchlistWithSelected(paymentInDBDto.getAccountId(), paymentInDBDto.getContactId(),
					custType, connection,searchCriteria.getFilter().getUserProfile()));
			paymentInDBDto.setDocumentList(getUploadDocumentList(connection));
			paymentInDBDto.setStatus(getPaymentInStatus(paymentInDBDto.getPaymentInId(), connection));
			paymentInDBDto.setStatusReason(getPaymentInStatusReasons(paymentInDBDto.getPaymentInId(), connection));
			paymentInDBDto
					.setFurtherpaymentDetails(getFurtherPaymentDetailsList(paymentInDBDto.getAccountId(), connection));

			paymentInDBDto.setActivityLogs(getActivityLogs(paymentInDBDto.getPaymentInId(), connection));
            
            if (Constants.CUST_TYPE_CFX.equalsIgnoreCase(custType)) {

				paymentInDBDto
						.setPrimaryContactName(getPrimaryContactName(paymentInDBDto.getAccountId(), connection) == null
								? "" : getPrimaryContactName(paymentInDBDto.getAccountId(), connection));
			}

			if (null != accountId) {
				paymentInDBDto.setAlertComplianceLog(getAlertComplianceLog(accountId));
			}

			// added for AT-898 for Sprint 1
			paymentInDBDto
					.setNoOfContactsForAccount(getNoOfContactsForAccount(paymentInDBDto.getAccountId(), connection));
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}

		return itransform.transform(paymentInDBDto);
	}

	private String setEntityType(String entityType) {
		if (entityType != null) {
			return entityType.toUpperCase();
		}
		return entityType;
	}

	// added for AT-898 for Sprint 1
	private Integer getNoOfContactsForAccount(Integer accountId, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer noOfContacts = 0;
		try {
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.GET_NO_OF_CONTACTS_FOR_ACCOUNT.getQuery());
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				noOfContacts = resultSet.getInt(1);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return noOfContacts;

	}

	/**
	 * Gets the pagination details.
	 *
	 * @param searchCriteria
	 *            the search criteria
	 * @param connection
	 *            the connection
	 * @return the pagination details
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	public PaginationDetails getPaginationDetails(PaymentInSearchCriteria searchCriteria, Connection connection)
			throws CompliancePortalException {

		PaginationDetails paginationDetails = new PaginationDetails();
		try {
			if (searchCriteria.getFilter() != null && !searchCriteria.getIsRequestFromReportPage()) {

				FilterQueryBuilder queryBuilder = new PaymentInQueueFilterQueryBuilder(searchCriteria.getFilter(),
						PaymentInQueryConstant.GET_PAYMENTIN_QUEUE_INNER_QUERY.getQuery(), Boolean.TRUE);
				String query = queryBuilder.getQuery();
				query = PaymentInQueryConstant.GET_PAYMENTIN_QUEUE_FOR_PAGINATION_FILTER.getQuery()
						.replace("{INNER_QUERY}", query);
				paginationDetails = getPaginationDetailQuery(connection, searchCriteria, query);

			} else if (Boolean.TRUE.equals(searchCriteria.getIsRequestFromReportPage())) {

				FilterQueryBuilder queryBuilder = new PaymentInQueueFilterQueryBuilder(searchCriteria.getFilter(),
						PaymentInReportQueryConstant.GET_PAYMENTIN_REPORT_INNER_QUERY.getQuery(), Boolean.TRUE);
				String query = queryBuilder.getQuery();
				query = PaymentInQueryConstant.GET_PAYMENTIN_QUEUE_FOR_PAGINATION_FILTER.getQuery()
						.replace("{INNER_QUERY}", query);
				paginationDetails = getPaginationDetailQuery(connection, searchCriteria, query);

			} else {
				paginationDetails = getPaginationDetailQuery(connection, searchCriteria,
						PaymentInQueryConstant.GET_PAGINATION_DETAILS.getQuery());
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		return paginationDetails;
	}

	private PaginationDetails getPaginationDetailQuery(Connection connection, PaymentInSearchCriteria searchCriteria,
			String query) throws CompliancePortalException {

		PreparedStatement statement = null;
		ResultSet rs = null;
		PaginationDetails paginationDetails = new PaginationDetails();

		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, 1);
			statement.setInt(2, searchCriteria.getPage().getCurrentRecord() - 1);
			statement.setInt(3, searchCriteria.getPage().getCurrentRecord() + 1);
			statement.setInt(4, searchCriteria.getPage().getTotalRecords());

			rs = statement.executeQuery();

			while (rs.next()) {
				if (searchCriteria.getPage().getCurrentRecord() - 1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setPrevRecord(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString(Constants.DB_TYPE), rs.getString(Constants.DB_PAYMENT_IN_ID)));

				}
				if (searchCriteria.getPage().getCurrentRecord() + 1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setNextRecord(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString(Constants.DB_TYPE), rs.getString(Constants.DB_PAYMENT_IN_ID)));

				}
				if (searchCriteria.getPage().getTotalRecords() == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setTotalRecords(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString(Constants.DB_TYPE), rs.getString(Constants.DB_PAYMENT_IN_ID)));
				}
				if (1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setFirstRecord(getPaginationData(rs.getString(Constants.DB_ACCOUNT_ID),
							rs.getString(Constants.DB_TYPE), rs.getString(Constants.DB_PAYMENT_IN_ID)));
				}
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return paginationDetails;
	}

	/**
	 * @param paymentInId
	 * @param connection
	 * @return ActivityLogs
	 * @throws CompliancePortalException
	 */
	private ActivityLogs getActivityLogs(Integer paymentInId, Connection connection) throws CompliancePortalException {

		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(PaymentInQueryConstant.GET_ACTIVITY_LOGS_OF_PAYMENT_IN.getQuery());
			statement.setInt(1, paymentInId);
			rs = statement.executeQuery();
			while (rs.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setActivity(rs.getString("Activity"));
				activityLogData.setActivityType(ActivityType.getActivityLogDisplay(rs.getString("ActivityType")));
				activityLogData.setCreatedBy(rs.getString("User"));
				Timestamp createdOn = rs.getTimestamp("CreatedOn");
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setComment(rs.getString("Comment"));
				activityLogDataList.add(activityLogData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return activityLogs;
	}

	/**
	 * @param paymentInId
	 * @param connection
	 * @return StatusReason
	 * @throws CompliancePortalException
	 */
	private StatusReason getPaymentInStatusReasons(Integer paymentInId, Connection connection)
			throws CompliancePortalException {

		StatusReason paymentInStatusReason = new StatusReason();
		List<StatusReasonData> paymentInStatusReasonList = new ArrayList<>();
		paymentInStatusReason.setStatusReasonData(paymentInStatusReasonList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(PaymentInQueryConstant.GET_PAYMENTIN_STATUS_REASON.getQuery());
			statement.setInt(1, paymentInId);
			rs = statement.executeQuery();
			while (rs.next()) {
				StatusReasonData paymentInStatusReasonData = new StatusReasonData();
				paymentInStatusReasonData.setName(rs.getString("Reason"));
				Integer paymentInIdDb = rs.getInt("PaymentinId");
				if (paymentInIdDb != -1) {
					paymentInStatusReasonData.setValue(Boolean.TRUE);
				} else {
					paymentInStatusReasonData.setValue(Boolean.FALSE);
				}
				paymentInStatusReasonList.add(paymentInStatusReasonData);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return paymentInStatusReason;
	}

	/**
	 * @param paymentInId
	 * @param connection
	 * @return Status
	 * @throws CompliancePortalException
	 */
	private Status getPaymentInStatus(Integer paymentInId, Connection connection) throws CompliancePortalException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		Status status = new Status();
		List<StatusData> statusDatas = new ArrayList<>();
		status.setStatuses(statusDatas);

		try {
			statement = connection
					.prepareStatement(PaymentInQueryConstant.GET_PAYMENTIN_STATUS_WITH_OHTER_STATUS.getQuery());
			statement.setInt(1, paymentInId);
			rs = statement.executeQuery();
			while (rs.next()) {
				StatusData statusData = new StatusData();
				statusData.setStatus(rs.getString("Status"));
				if (rs.getInt("Id") == paymentInId) {
					statusData.setIsSelected(Boolean.TRUE);
				} else {
					statusData.setIsSelected(Boolean.FALSE);
				}

				statusDatas.add(statusData);
			}
			return status;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
	}

	@Override
	public ActivityLogs updatePaymentIn(PaymentInUpdateDBDto paymentInUpdateDBDto) throws CompliancePortalException {

		Connection connection = null;
		Connection readOnlyConn = null;
		try {
			connection = getConnection(Boolean.FALSE);
			readOnlyConn = getConnection(Boolean.TRUE);
			beginTransaction(connection);

			paymentInUpdateDBDto.setActivityLog(new ArrayList<ProfileActivityLogDto>());
			getWatchListStatus(paymentInUpdateDBDto, connection);
			addWatchlist(paymentInUpdateDBDto, connection);
			addWatchlistLog(paymentInUpdateDBDto);
			deleteWatchlist(paymentInUpdateDBDto, connection);
			updateWatchlistStatus(paymentInUpdateDBDto, connection);
			addProfileActivityLogs(paymentInUpdateDBDto, connection);
			deleteReasons(paymentInUpdateDBDto, connection);
			addReasons(paymentInUpdateDBDto, connection);
			addPaymentInActivityLogs(paymentInUpdateDBDto, connection);

			String paymentInStatus = paymentInUpdateDBDto.getPaymentInStatus();
			String paymentInStatusReason = getResponseStatus(paymentInUpdateDBDto);

			if (paymentInStatus != null && !paymentInStatus.isEmpty()) {
				updatePaymentInStatus(paymentInStatus, paymentInUpdateDBDto, paymentInUpdateDBDto.getCreatedBy(),
						connection);
				if (Constants.CLEAR.equalsIgnoreCase(paymentInStatus)
						|| Constants.SEIZE.equalsIgnoreCase(paymentInStatus)
						|| Constants.REJECT.equalsIgnoreCase(paymentInStatus)) {
					updateWorkFlowTime(paymentInUpdateDBDto.getUserResourceId(), connection);
				}
				saveIntoBroadcastQueue(getBroadCastDBObject(paymentInUpdateDBDto.getAccountId(),
						paymentInUpdateDBDto.getPaymentInId(), paymentInUpdateDBDto.getCreatedBy(),
						getPaymentInResponse(getPaymentComplianceStatus(paymentInStatus),
								paymentInUpdateDBDto.getTradeContractnumber(), paymentInUpdateDBDto.getTradePaymentid(),
								paymentInUpdateDBDto.getOrgCode(), paymentInStatusReason),
						paymentInUpdateDBDto.getOrgCode(), paymentInUpdateDBDto.getContactId()), connection);

			} else if (Boolean.FALSE.equals(StringUtils.isNullOrEmpty(paymentInUpdateDBDto.getComment()))
					&& Boolean.TRUE.equals(paymentInUpdateDBDto.getIsRequestFromQueue())) {
				updatePaymentInIsOnQueueStatus(paymentInUpdateDBDto, connection);
			}
			commitTransaction(connection);
		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeConnection(connection);
			closeConnection(readOnlyConn);
		}
		return paymentInActivityLogTransformer.transform(paymentInUpdateDBDto);
	}

	private void updatePaymentInIsOnQueueStatus(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.UPDATE_PAYMENT_IN_IS_ON_QUEUE_STATUS.getQuery());
			preparedStatement.setString(1, paymentInUpdateDBDto.getCreatedBy());
			preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setBoolean(3, paymentInUpdateDBDto.getIsPaymentOnQueue());
			preparedStatement.setInt(4, paymentInUpdateDBDto.getPaymentInId());
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

	private void addWatchlist(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		List<String> addWatchlist = paymentInUpdateDBDto.getAddWatchlist();
		if (addWatchlist != null && !addWatchlist.isEmpty()) {
			//changes for AT-2986
			if(paymentInUpdateDBDto.getCustType().equals("PFX")){
				insertWatchlistOnPFX(paymentInUpdateDBDto, connection);
			}else{
				insertWatchlist(paymentInUpdateDBDto, connection);
			}
		}
	}

	@SuppressWarnings("resource")
	private void insertWatchlistOnPFX(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(RegistrationQueryConstant.INSERT_CONTACT_WATCHLIST.getQuery());
			for (String addWatchlistStr : paymentInUpdateDBDto.getAddWatchlist()) {
				stmt.setString(1, addWatchlistStr);
				stmt.setInt(2, paymentInUpdateDBDto.getContactId());
				stmt.setString(3, paymentInUpdateDBDto.getCreatedBy());
				stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
			}
			int count = stmt.executeUpdate();
		
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(stmt);
		}
	}
	
	@SuppressWarnings("resource")
	private void insertWatchlist(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationCfxDBQueryConstant.INSERT_WATCHLIST_BY_ACCOUNT_ID.getQuery());
			for (String addWatchlistStr : paymentInUpdateDBDto.getAddWatchlist()) {
				preparedStatement.setString(1, addWatchlistStr);
				preparedStatement.setString(2, paymentInUpdateDBDto.getCreatedBy());
				preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setInt(4, paymentInUpdateDBDto.getAccountId());
				preparedStatement.addBatch();
			}
			int[] count = preparedStatement.executeBatch();
			for (int c : count) {
				if (c == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
			}
		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(preparedStatement);
		}
	}

	private BroadcastEventToDB getBroadCastDBObject(Integer accountId, Integer paymentInId, String createdBy,
			String statusJson, String orgCode, Integer contactId) {
		BroadcastEventToDB db = new BroadcastEventToDB();
		db.setAccountId(accountId);
		db.setContactId(contactId);
		db.setPaymentInId(paymentInId);
		db.setCreatedBy(createdBy);
		db.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliverOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveryStatus(BroadCastStatusEnum.NEW.getBroadCastStatusAsString());
		db.setEntityType(BroadCastEntityTypeEnum.PAYMENTIN.getBroadCastEntityTypeAsString());
		db.setOrgCode(orgCode);
		db.setStatusJson(statusJson);
		return db;
	}

	private String getPaymentInResponse(PaymentComplianceStatus paymentComplianceStatus, String tradeContractnumber,
			String tradePaymentid, String orgCode, String paymentInStatusReason) {

		PaymentInResponse paymentInResponse = new PaymentInResponse();
		paymentInResponse.setOsrID(UUID.randomUUID().toString());
		paymentInResponse.setOrgCode(orgCode);
		paymentInResponse.setTradeContractNumber(tradeContractnumber);
		paymentInResponse.setTradePaymentID(Integer.valueOf(tradePaymentid));
		paymentInResponse.setStatus(paymentComplianceStatus);
		paymentInResponse.setResponseCode(paymentComplianceStatus.getCode());
		paymentInResponse.setResponseDescription(paymentInStatusReason);
		return JsonConverterUtil.convertToJsonWithNull(paymentInResponse);

	}

	/**
	 * @param paymentInStatus
	 * @param paymentInUpdateDBDto
	 * @param user
	 * @param connection
	 * @throws CompliancePortalException
	 */
	private void updatePaymentInStatus(String paymentInStatus, PaymentInUpdateDBDto paymentInUpdateDBDto, String user,
			Connection connection) throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(PaymentInQueryConstant.UPDATE_PAYMENT_IN_STATUS.getQuery());
			preparedStatement.setString(1, paymentInStatus);
			preparedStatement.setString(2, user);
			preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setBoolean(4, paymentInUpdateDBDto.getIsPaymentOnQueue());
			preparedStatement.setInt(5, paymentInUpdateDBDto.getPaymentInId());
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
	 * @param paymentInUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	private void addPaymentInActivityLogs(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {

		List<PaymentInActivityLogDto> activityLogs = paymentInUpdateDBDto.getActivityLogs();
		if (activityLogs != null && !activityLogs.isEmpty()) {
			insertPaymentInActivityLogs(activityLogs, connection);
			insertPaymentInActivityLogDetail(activityLogs, connection);
		}

	}

	@SuppressWarnings("resource")
	private void insertPaymentInActivityLogDetail(List<PaymentInActivityLogDto> activityLogs, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG_DETAIL.getQuery());
			for (PaymentInActivityLogDto activityLog : activityLogs) {
				PaymentInActivityLogDetailDto activityLogDetailDto = activityLog.getActivityLogDetailDto();
				preparedStatement.setInt(1, activityLogDetailDto.getActivityId());
				preparedStatement.setString(2, activityLogDetailDto.getActivityType().getModule());
				preparedStatement.setString(3, activityLogDetailDto.getActivityType().getType());
				preparedStatement.setNString(4, activityLogDetailDto.getLog());
				preparedStatement.setNString(5, activityLogDetailDto.getCreatedBy());
				preparedStatement.setTimestamp(6, activityLogDetailDto.getCreatedOn());
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
	 * @param activityLogs
	 * @param connection
	 * @throws CompliancePortalException
	 */
	@SuppressWarnings("resource")
	private void insertPaymentInActivityLogs(List<PaymentInActivityLogDto> activityLogs, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			preparedStatement = connection.prepareStatement(
					PaymentInQueryConstant.INSERT_PAYMENT_IN_ACTIVITY_LOG.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (PaymentInActivityLogDto activityLog : activityLogs) {
				preparedStatement.setInt(1, activityLog.getPaymentInId());
				preparedStatement.setNString(2, activityLog.getActivityBy());
				preparedStatement.setTimestamp(3, activityLog.getTimeStatmp());
				preparedStatement.setNString(4, activityLog.getComment());
				preparedStatement.setNString(5, activityLog.getCreatedBy());
				preparedStatement.setTimestamp(6, activityLog.getCreatedOn());
				preparedStatement.setInt(7, activityLog.getPaymentInId());
				int updateCount = preparedStatement.executeUpdate();
				if (updateCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
				rs = preparedStatement.getGeneratedKeys();
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
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}

	}

	/**
	 * @param paymentInUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	private void addReasons(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {

		List<String> addReasons = paymentInUpdateDBDto.getAddReasons();
		if (addReasons != null && !addReasons.isEmpty()) {
			insertPaymentOutReason(paymentInUpdateDBDto, connection);
		}
	}

	/**
	 * @param paymentInUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	@SuppressWarnings("resource")
	private void insertPaymentOutReason(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(PaymentInQueryConstant.INSERT_PAYMENT_IN_REASON.getQuery());
			for (String addReasonsStr : paymentInUpdateDBDto.getAddReasons()) {
				preparedStatement.setInt(1, paymentInUpdateDBDto.getPaymentInId());
				preparedStatement.setString(2, addReasonsStr);
				preparedStatement.setString(3, paymentInUpdateDBDto.getCreatedBy());
				preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setString(5, paymentInUpdateDBDto.getCreatedBy());
				preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				int addRowCnt = preparedStatement.executeUpdate();
				if (addRowCnt == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
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
	 * @param paymentInUpdateDBDto
	 * @param connection
	 * @throws CompliancePortalException
	 */
	private void deleteReasons(PaymentInUpdateDBDto paymentInUpdateDBDto, Connection connection)
			throws CompliancePortalException {

		Integer paymentInId = paymentInUpdateDBDto.getPaymentInId();
		List<String> delReasons = paymentInUpdateDBDto.getDeletedReasons();
		if (delReasons != null && !delReasons.isEmpty()) {
			String[] delReasonsArray = delReasons.toArray(new String[delReasons.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_STATUS_REASON_ID.getQuery());
			String query = builder.addCriteria("Module", new String[] { "PAYMENT_IN", "ALL" }, "AND", ClauseType.IN)
					.addCriteria("Reason", delReasonsArray, "AND", ClauseType.IN).build();
			query = "DELETE [PaymentInStatusReason] WHERE PaymentInID=" + paymentInId + " AND StatusUpdateReasonID IN ("
					+ query + ")";
			deletePaymentReasonOrWatchlist(query, connection);
		}
	}

	private String getPrimaryContactName(Integer accountId, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String name = null;
		try {
			preparedStatement = connection.prepareStatement(PaymentInQueryConstant.GET_PRIMARY_CONTACT_NAME.getQuery());
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				name = resultSet.getString("name");
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return name;
	}

	@Override
	public ViewMoreResponse getMoreFraugsterDetails(PaymentInViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {

		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.GET_VIEWMORE_PAYMENTIN_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getPaymentInId());
			preparedStatement.setInt(4, viewMoreRequest.getMinViewRecord());
			preparedStatement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreFraugsterDetails(resultSet);
			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}

	@Override
	public ViewMoreResponse getMoreSanctionDetails(PaymentInViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {

		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.GET_VIEWMORE_PAYMENTIN_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getPaymentInId());
			preparedStatement.setInt(4, viewMoreRequest.getMinViewRecord());
			preparedStatement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreSanctionDetails(resultSet);
			viewMoreResponse.setServices(domainList);
			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}

	@Override
	public ViewMoreResponse getMoreCustomCheckDetails(PaymentInViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {

		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.GET_VIEWMORE_PAYMENTIN_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getPaymentInId());
			preparedStatement.setInt(4, viewMoreRequest.getMinViewRecord());
			preparedStatement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreCustomCheckDetailsForPayment(resultSet);
			viewMoreResponse.setServices(domainList);
			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}

	private String getAccountContactJoin(String payinFilterQuery) {
		String payInFilterQueryResult;
		if (payinFilterQuery.contains(WHERE)) {
			payInFilterQueryResult = payinFilterQuery + " AND "
					+ PaymentInQueryConstant.QUEUE_FILTER_NULL_CHECK.getQuery();
		} else {
			payInFilterQueryResult = payinFilterQuery + " WHERE "
					+ PaymentInQueryConstant.QUEUE_FILTER_NULL_CHECK.getQuery();
		}
		return payInFilterQueryResult;
	}

	private String getCountryJoin(PaymentInSearchCriteria searchCriteria) {
		String countryJoin = "";
		if (null != searchCriteria.getFilter().getCountryofFund()
				&& searchCriteria.getFilter().getCountryofFund().length > 0) {
			countryJoin = PaymentInQueryConstant.COUNTRY_FILTER_JOIN.getQuery();
		}
		return countryJoin;
	}
	
	//changes for AT-1646  RG Filter
	private String getRGJoin(PaymentInSearchCriteria searchCriteria) {
		String rgJoin = "";
		if (null != searchCriteria.getFilter().getRiskStatus()
				&& searchCriteria.getFilter().getRiskStatus().length > 0) {
			rgJoin = PaymentInQueryConstant.RG_FILTER_JOIN.getQuery();
		}
		return rgJoin;
	}
	private String getUserJoin(PaymentInSearchCriteria searchCriteria) {
		String userJoin = "";
		if (null != searchCriteria.getFilter().getOwner() && searchCriteria.getFilter().getOwner().length > 0) {
			userJoin = PaymentInQueryConstant.USER_FILTER_JOIN.getQuery();
		}
		return userJoin;
	}

	private String getOrganizationJoin(PaymentInSearchCriteria searchCriteria) {
		String orgJoin = "";
		if (null != searchCriteria.getFilter().getOrganization()
				&& searchCriteria.getFilter().getOrganization().length > 0) {
			orgJoin = PaymentInQueryConstant.ORG_TABLE_JOIN.getQuery();
		}
		return orgJoin;
	}
	
	private String getLegalEntityJoin(PaymentInSearchCriteria searchCriteria) {
		String legalEntityJoin = "";
		if(null != searchCriteria.getFilter().getLegalEntity() && 
				searchCriteria.getFilter().getLegalEntity().length>0) {
			legalEntityJoin = PaymentInQueryConstant.LEGAL_ENTITY_TABLE_JOIN.getQuery();
		}
		return legalEntityJoin;
	}

	private String getAttributeFilterJoin(String payInAttributeFilterQuery) {
		String attributeJoin = "";
		if (payInAttributeFilterQuery.contains(WHERE)) {
			attributeJoin = PaymentInQueryConstant.PAYMENTIN_QUEUE_ATTRIBUTE_FILTER_JOIN.getQuery();
		}
		return attributeJoin;
	}

	private String getAccountFilterJoin(String accountFilterQuery) {
		String accountJoin = "";
		if (accountFilterQuery.contains(WHERE)) {
			accountJoin = PaymentInQueryConstant.PAYMENTIN_QUEUE_ACCOUNT_FILTER_JOIN.getQuery();
		}
		return accountJoin;
	}

	private String getContactFilterJoin(String contactFilterQuery) {
		String contactJoin = "";
		if (contactFilterQuery.contains(WHERE) || contactFilterQuery.contains("category")) {
			contactJoin = PaymentInQueryConstant.PAYMENTIN_QUEUE_CONTACT_FILTER_JOIN.getQuery();
		}
		return contactJoin;
	}

	private FilterQueryBuilder getPayInQueueFilterQuery(PaymentInSearchCriteria searchCriteria) {
		return new PaymentInQueueFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentInQueryConstant.PAYMENTIN_QUEUE_FILTER.getQuery(), Boolean.FALSE);
	}

	private String getPayInAttributeFilterQuery(PaymentInSearchCriteria searchCriteria) {
		FilterQueryBuilder payInAttributeFilter = new PaymentInQueueAtttributeFilterQueryBuilder(
				searchCriteria.getFilter(), PaymentInQueryConstant.PAYMENTIN_QUEUE_ATTRIBUTE_FILTER.getQuery(), Boolean.TRUE);
		return payInAttributeFilter.getQuery();
	}

	private String getPayInAccountFilterQuery(PaymentInSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new PaymentInQueueAccountFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentInQueryConstant.PAYMENTIN_QUEUE_ACCOUNT_ATTRIBUTE_FILTER.getQuery(),true);
		return accountFilter.getQuery();
	}

	private String getPayInContactFilterQuery(PaymentInQueueContactFilterQueryBuilder contactFilter,
			PaymentInSearchCriteria searchCriteria) {
		String countryJoin = "";
		String contactFilterQuery = contactFilter.getQuery();

		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if (null != watchListCategoryFilter) {
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", watchListCategoryFilter);
		} else {
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", "");
		}

		if (contactFilter.isCountryOfResidenceFilterApplied()) {
			countryJoin = PaymentInQueryConstant.PAYMENTIN_QUEUE_COUNTRY_OF_RESIDENCE_FILTER.getQuery();
		}

		return contactFilterQuery.replace("{PAYMENTIN_QUEUE_COUNTRY_OF_RESIDENCE_FILTER}", countryJoin);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentInDBService#
	 * forceClearPaymentIn(com.currenciesdirect.gtg.compliance.core.domain.
	 * paymentin.PaymentInRecheckFailureDetails,
	 * com.currenciesdirect.gtg.compliance.commons.domain.activity.
	 * PaymentInActivityLogDto,
	 * com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile)
	 */
	@Override
	public void forceClearPaymentIn(PaymentInRecheckFailureDetails entry, PaymentInActivityLogDto activityLog,
			UserProfile user) throws CompliancePortalException {
		Connection connection = null;
		try {
			connection = getConnection(Boolean.FALSE);

			PaymentInUpdateDBDto payInUpdateDBDto = new PaymentInUpdateDBDto();
			payInUpdateDBDto.setPaymentInId(entry.getPaymentInId());
			payInUpdateDBDto.setIsPaymentOnQueue(Boolean.FALSE);
			updatePaymentInStatus(PaymentComplianceStatus.CLEAR.name(), payInUpdateDBDto, user.getName(), connection);

			saveIntoBroadcastQueue(getBroadCastDBObject(entry.getAccountId(), entry.getPaymentInId(), user.getName(),
					getPaymentInResponse(PaymentComplianceStatus.CLEAR, entry.getTradeContractNumber(),
							entry.getTradePaymentId().toString(), entry.getOrgCode(), "Bulk Cleared Payment"),
					entry.getOrgCode(), entry.getContactId()), connection);

			if (null != activityLog) {
				List<PaymentInActivityLogDto> activityLogs = new ArrayList<>();
				activityLogs.add(activityLog);
				insertPaymentInActivityLogs(activityLogs, connection);
				insertPaymentInActivityLogDetail(activityLogs, connection);
			}
		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeConnection(connection);
		}

	}

	@Override
	public boolean setPoiExistsFlagForPaymentIn(UserProfile user, Contact contact) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = getConnection(Boolean.TRUE);
			//update contact table to set POI Exists Flag
			preparedStatement = connection.prepareStatement(PaymentInQueryConstant.UPDATE_CONTACT_POI_EXISTS_FLAG.getQuery());	
			preparedStatement.setString(1, contact.getContactSFID());
		    preparedStatement.executeUpdate();
	} catch (Exception e) {
		transactionRolledBack(connection);
		throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
	} finally {
		closeConnection(connection);
		closePrepareStatement(preparedStatement);
	}
		return true;
	}
	
	//AT-4306
	@Override
	public ViewMoreResponse getMoreIntuitionDetails(PaymentInViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {

		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(PaymentInQueryConstant.GET_VIEWMORE_PAYMENTIN_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getPaymentInId());
			preparedStatement.setInt(4, viewMoreRequest.getMinViewRecord());
			preparedStatement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreIntuitionDetails(resultSet);
			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}

}
