package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.IRegistrationDBService;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceContact;
import com.currenciesdirect.gtg.compliance.core.domain.ComplianceStatus;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.IRegistrationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.OnfidoStatusEnum;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.SourceEnum;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.TransactionValuesEnum;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.EntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ProfileComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.dbport.report.RegistrationReportQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class RegistrationDBServiceImpl.
 */
@Component("regDBServiceImpl")
public class RegistrationDBServiceImpl extends AbstractDBServiceRegistration implements IRegistrationDBService {

	private static final String REASON = "Reason";
	private static final String WATCHLIST_CATEGORY_FILTER = "{WATCHLIST_CATEGORY_FILTER}";

	@Autowired
	@Qualifier("registrationDetailsTransformer")
	private ITransform<RegistrationDetailsDto, RegistrationDetailsDBDto> registrationDetailsTransformer;

	@Autowired
	@Qualifier("regActivityLogTransformer")
	private ITransform<ActivityLogs, RegUpdateDBDto> regActivityLogTransformer;


	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.core.IRegistrationQueueDBService#
	 * getRegistrationQueue(com.currenciesdirect.gtg.compliance.core.domain.
	 * SearchCriteria)
	 */
	@Override
	public RegistrationQueueDto getRegistrationQueueWithCriteria(RegistrationSearchCriteria searchCriteria)
			throws CompliancePortalException {
		int offSet = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
		int rowsToFetch = SearchCriteriaUtil.getPageSize();
		int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
		String regQueue = "RegistrationQueue";
		RegQueueContactFilterQueryBuilder contactFilter = new RegQueueContactFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationQueryConstant.REGISTRATION_QUEUE_CONTACT_FILTER.getQuery(),
				 true);
		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		String contactFilterQuery = contactFilter.getQuery();
		if (null != watchListCategoryFilter) {
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, watchListCategoryFilter);
		} else {
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, "");
		}
		 contactFilterQuery = buildContactFilterQuery(searchCriteria, contactFilter);
			
		String accountFilterQuery = buildAccountFilterQuery(searchCriteria);

		String regFilterQuery = buildAccountAndContactAttributeFilterQuery(searchCriteria, contactFilter,
				contactFilterQuery,watchListCategoryFilter, accountFilterQuery);
		
		//ADD for AT-3269
		String legalEntityJoin = "";
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			String[] legalEntity = searchCriteria.getFilter().getLegalEntity();
			String value = appendAllValues(legalEntity);
			legalEntityJoin = "JOIN LegalEntity le ON acc.LegalEntityID = le.ID AND le.code In ("+ value + ")";
		}
		
		String listQuery = RegistrationQueryConstant.GET_REGISTRATION_QUEUE.getQuery()
				.replace("{REGISTRATION_QUEUE_CONTACT_FILTER}", contactFilterQuery)
				.replace("{REGISTRATION_QUEUE_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{REGISTRATION_QUEUE_FILTER}", regFilterQuery)
				.replace("{JOIN_LEGAL_ENTITY}", legalEntityJoin);
		
		listQuery = executeSort(searchCriteria.getFilter().getSort(),listQuery);
		
		String countQuery = RegistrationQueryConstant.GET_REGISTRATION_QUEUE_COUNT.getQuery()
				.replace("{REGISTRATION_QUEUE_CONTACT_FILTER}", contactFilterQuery)
				.replace("{REGISTRATION_QUEUE_ACCOUNT_FILTER}", accountFilterQuery)
				.replace("{REGISTRATION_QUEUE_FILTER}", regFilterQuery)
				.replace("{JOIN_LEGAL_ENTITY}", legalEntityJoin);
		
		RegistrationQueueDto registrationQueueDto = new RegistrationQueueDto();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			registrationQueueDto= getRegisrationQueueData(connection, offSet, rowsToFetch, listQuery, countQuery);
			Integer pageSize = SearchCriteriaUtil.getPageSize();
			Integer totalRecords = registrationQueueDto.getTotalRecords();
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
				int tMaxRecord=minRowNum + registrationQueueDto.getRegistrationQueue().size() - 1;
				if(tMaxRecord<totalRecords)
					page.setMaxRecord(tMaxRecord);
				else
					page.setMaxRecord(totalRecords);
				page.setPageSize(pageSize);
				page.setTotalPages(totalPages);
				page.setTotalRecords(totalRecords);
				page.setCurrentPage(SearchCriteriaUtil.getCurrentPage(searchCriteria));
				registrationQueueDto.setPage(page);
				List<String> organization = null;
				organization = getOrganization(connection);
				registrationQueueDto.setOrganization(organization);
				registrationQueueDto.setSource(SourceEnum.getSourceValues());
				registrationQueueDto.setTransValue(TransactionValuesEnum.getTransactionValues());
				List<String> owner = null;
				owner= getLockedUserName(connection);
				registrationQueueDto.setOwner(owner);	
				searchCriteria.setPage(registrationQueueDto.getPage());
				registrationQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
				
				registrationQueueDto.setOnfidoStatus(OnfidoStatusEnum.getOnfidoStatusValues());
				
				List<String> currency = null;
				currency = getCurrency(connection);
				registrationQueueDto.setCurrency(currency);
				SavedSearch savedSearch = null;
				savedSearch = getSaveSearches(searchCriteria.getFilter().getUserProfile().getId(),regQueue,connection);
				registrationQueueDto.setSavedSearch(savedSearch);
				List<String> legalEntity = null;
				legalEntity = getLegalEntity(connection);
				registrationQueueDto.setLegalEntity(legalEntity);
			}
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return registrationQueueDto;
	}


	private String buildAccountAndContactAttributeFilterQuery(RegistrationSearchCriteria searchCriteria,
			RegQueueContactFilterQueryBuilder contactFilter, String contactFilterQuery,String watchListCategoryFilter, String accountFilterQuery) {
		String contactJoin = "";
		if (contactFilterQuery.contains(Constants.WHERE) || null != watchListCategoryFilter){
			contactJoin = RegistrationQueryConstant.CONTACT_FILTER_JOIN.getQuery();
		}
			
		String accountJoin = "";
		if(accountFilterQuery.contains(Constants.WHERE)){
			accountJoin = RegistrationQueryConstant.ACCOUNT_FILTER_JOIN.getQuery();
		}
		
		if(contactFilter.isFilterAppliedForAccountAndContact()){
			contactJoin = RegistrationQueryConstant.CONTACT_FILTER_LEFT_JOIN.getQuery();
			accountJoin = RegistrationQueryConstant.ACCOUNT_FILTER_LEFT_JOIN.getQuery();
		}
		String orgJoin = "";
		if(null != searchCriteria.getFilter().getOrganization() && 
				searchCriteria.getFilter().getOrganization().length>0){
			orgJoin = RegistrationQueryConstant.ORG_TABLE_JOIN.getQuery();
		}
		
		String legalEntityJoin = "";
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			legalEntityJoin = RegistrationQueryConstant.LEGEL_ENTITY_TABLE_JOIN.getQuery();
		}

		String deviceJoin = "";
		if(null!=searchCriteria.getFilter().getDeviceId()) {
			deviceJoin =  RegistrationQueryConstant.DEVICE_ID_JOIN.getQuery();
		}
		
		String onfidoJoin = "";
		if(null!=searchCriteria.getFilter().getOnfidoStatus() && null == searchCriteria.getFilter().getOrganization()) {
			onfidoJoin =  RegistrationReportQueryConstant.ONFIDO_JOIN.getQuery();  
		}
		
		FilterQueryBuilder regFilter = new RegQueueFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationQueryConstant.REGISTRATION_QUEUE_FILTER.getQuery(),
				searchCriteria.getIsFilterApply(), true);
		String regFilterQuery = regFilter.getQuery();
		String userJoin = "";
		if(regFilterQuery.contains(RegQueDBColumns.REGOWNER.getName()))
		{
			userJoin = RegistrationQueryConstant.USER_FILTER_JOIN.getQuery();
		}
		
		if(contactJoin.length()>1 && accountJoin.length()>1){
			regFilterQuery = addFilterToQuery(regFilterQuery);
				
		}else{
			contactJoin = contactJoin.replace("LEFT", "");
			accountJoin = accountJoin.replace("LEFT", "");
		}
		
		regFilterQuery = regFilterQuery.replace("{CONTACT_FILTER_JOIN}", contactJoin)
				.replace("{ACCOUNT_FILTER_JOIN}", accountJoin)
				.replace("{ORG_TABLE_JOIN}", orgJoin)
				.replace("{USER_FILTER_JOIN}", userJoin)
				.replace("{LEGAL_ENTITY_JOIN}", legalEntityJoin)
				.replace("{DEVICE_ID_JOIN}", deviceJoin)
				.replace("{ONFIDO_JOIN}", onfidoJoin);

		return regFilterQuery;
	}

	private String addFilterToQuery(String regFilter) {
		String regFilterQuery;
		
		if(regFilter.contains(Constants.WHERE)){
			regFilterQuery = regFilter+" AND "+ RegistrationQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}else{
			regFilterQuery = regFilter+" WHERE "+ RegistrationQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}
		return regFilterQuery;
	}


	private String buildAccountFilterQuery(RegistrationSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new RegQueueAccountFilterQueryBuilder(searchCriteria.getFilter(),
				RegistrationQueryConstant.REGISTRATION_QUEUE_ACCOUNT_FILTER.getQuery(),
				 true);
		return accountFilter.getQuery();
	}

	
	private String buildContactFilterQuery(RegistrationSearchCriteria searchCriteria,
			RegQueueContactFilterQueryBuilder contactFilter) {
		String countryJoin = "";
		String contactFilterQuery = contactFilter.getQuery();
		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if(null!= watchListCategoryFilter ){
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, watchListCategoryFilter)  ;
		}else{
			contactFilterQuery = contactFilterQuery.replace(WATCHLIST_CATEGORY_FILTER, "")  ;
		}
		
	    if(contactFilter.isCountryOfResidenceFilterApplied()){
			countryJoin = RegistrationQueryConstant.REGISTRATION_QUEUE_COUNTRY_OF_RESIDENCE_FILTER.getQuery();
		}
		
		contactFilterQuery = contactFilterQuery.replace("{REGISTRATION_QUEUE_COUNTRY_OF_RESIDENCE_FILTER}", countryJoin);
		return contactFilterQuery;
	}

	
	private String getWatchListCategoryFilter(RegistrationSearchCriteria regSearchCriteria) {
		String watchCategosyStatus = null;
		boolean hasAllPermission = false;
		if (regSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1()) {
			watchCategosyStatus = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		}
		if (regSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2()) {
			if (null != watchCategosyStatus)
				hasAllPermission = true;
			else
				watchCategosyStatus = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();

		}
		if (hasAllPermission)
			watchCategosyStatus = null;
		else {
			if (null != watchCategosyStatus)
				watchCategosyStatus = RegistrationQueryConstant.WATCHLIST_CATEGORY_FILTER.getQuery().replace("?", watchCategosyStatus);
		}

		return watchCategosyStatus;
	}

	private String executeSort(Sort sort, String listQuery) {
		String columnName = "a.updatedon";
		boolean isAsc = false;
		if(sort != null && sort.getIsAscend() != null){
			isAsc = sort.getIsAscend();
		}		
		return addSort(columnName, isAsc,listQuery);
	}

	private void setNewOrUpdatedData(RegistrationQueueData registrationQueueData)
	{
		registrationQueueData.setNewOrUpdated(Constants.N);
		if(registrationQueueData.getAccountVersion() != null && registrationQueueData.getAccountVersion() > 1)
		{
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
	
	private void setLockedData(String lockedBy,ResultSet listRs,RegistrationQueueData registrationQueueData) throws SQLException {
		if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			registrationQueueData
					.setUserResourceLockId(listRs.getInt(RegQueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
			registrationQueueData.setLocked(Boolean.TRUE);
			registrationQueueData.setLockedBy(lockedBy);
		}
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
	private RegistrationQueueDto getRegisrationQueueData(Connection connection, int offSet, int rowsToFetch,
			String query, String countQuery) throws CompliancePortalException {
		RegistrationQueueDto queueDto = new RegistrationQueueDto();
		List<RegistrationQueueData> registrationQueueDataList = new ArrayList<>();
		PreparedStatement listStatement = null;
		PreparedStatement countStatement = null;
		ResultSet listRs = null;
		ResultSet countRs = null;
		Integer totalRecords = 0;
		try {	
			listStatement = connection.prepareStatement(query);
			listStatement.setInt(1, offSet);
			listStatement.setInt(2, rowsToFetch);
			listRs = listStatement.executeQuery();
			while (listRs.next()) {
				RegistrationQueueData registrationQueueData = new RegistrationQueueData();
				registrationQueueData.setContactId(listRs.getInt(RegQueDBColumns.CONTACTID.getName()));
				registrationQueueData.setAccountId(listRs.getInt(RegQueDBColumns.ACCOUNTID.getName()));
				registrationQueueData.setContactName(listRs.getString(RegQueDBColumns.CONTACTNAME.getName()));
				registrationQueueData.setTradeAccountNum(listRs.getString(RegQueDBColumns.TRADEACCOUNTNUM.getName()));
				Timestamp registeredOn = listRs.getTimestamp(RegQueDBColumns.REGISTEREDON.getName());
				if (registeredOn != null) {
					registrationQueueData.setRegisteredOn(DateTimeFormatter.dateTimeFormatter(registeredOn));
				}

				registrationQueueData.setOrganisation( listRs.getString(RegQueDBColumns.ORGANIZATION.getName()));
				registrationQueueData.setLegalEntity(listRs.getString(RegQueDBColumns.LEGALENTITY.getName()));
				registrationQueueData.setEidCheck(ServiceStatusEnum.getStatusFromDatabaseStatus(listRs.getInt(RegQueDBColumns.KYCSTATUS.getName())));
				registrationQueueData.setFraugster(ServiceStatusEnum.getStatusFromDatabaseStatus(listRs.getInt(RegQueDBColumns.FRAUGSTERSTATUS.getName())));
				registrationQueueData.setSanction(ServiceStatusEnum.getStatusFromDatabaseStatus(listRs.getInt(RegQueDBColumns.SANCTIONSTATUS.getName())));
				registrationQueueData.setBlacklist(ServiceStatusEnum.getStatusFromDatabaseStatus(listRs.getInt(RegQueDBColumns.BLACKLISTSTATUS.getName())));
				registrationQueueData.setBuyCurrency(listRs.getString(RegQueDBColumns.BUYCURRENCY.getName()));
				registrationQueueData.setSellCurrency(listRs.getString(RegQueDBColumns.SELLCURRENCY.getName()));
				registrationQueueData.setTransactionValue(StringUtils.getNumberFormat(listRs.getString(RegQueDBColumns.TRANSACTIONVALUE.getName())));
				registrationQueueData.setSource(listRs.getString(RegQueDBColumns.SOURCE.getName()));
				registrationQueueData.setType(CustomerTypeEnum.getUiStatusFromDatabaseStatus(listRs.getInt(RegQueDBColumns.TYPE.getName())));
				registrationQueueData.setCountryOfResidence(listRs.getString(RegQueDBColumns.COUNTRY_OF_RESIDENCE.getName()));
				registrationQueueData.setAccountVersion(listRs.getInt(RegQueDBColumns.ACCOUNT_VERSION.getName()));
				registrationQueueData.setCustomCheck(ServiceStatusEnum.getStatusFromDatabaseStatus(listRs.getInt(RegQueDBColumns.CUSOMCHECKSTATUS.getName())));
				setNewOrUpdatedData(registrationQueueData);
				Timestamp registeredDate = listRs.getTimestamp(RegQueDBColumns.REGISTERED_DATE.getName());
				setRegisteredDate(registeredDate, registrationQueueData);
				
				String lockedBy = listRs.getString(RegQueDBColumns.LOCKED_BY.getName());
				setLockedData(lockedBy, listRs, registrationQueueData);
				String userResourceCreatedOn = listRs.getString(RegQueDBColumns.USER_RESOURCE_CREATEDON.getName());
				if (userResourceCreatedOn != null && !userResourceCreatedOn.isEmpty()) {
					registrationQueueData.setUserResourceCreatedOn(listRs.getString(RegQueDBColumns.USER_RESOURCE_CREATEDON.getName()));
				}
				String userResourceWorkflowTime = listRs.getString(RegQueDBColumns.USER_RESOURCE_WORKFLOWTIME.getName());
				if (userResourceWorkflowTime != null && !userResourceWorkflowTime.isEmpty()) {
					registrationQueueData.setUserResourceWorkflowTime(listRs.getString(RegQueDBColumns.USER_RESOURCE_WORKFLOWTIME.getName()));
				}
				String userResourceEntityType = listRs.getString(RegQueDBColumns.USER_RESOURCE_ENTITYTYPE.getName());
				if (userResourceEntityType != null && !userResourceEntityType.isEmpty()) {
					registrationQueueData.setUserResourceEntityType(listRs.getString(RegQueDBColumns.USER_RESOURCE_ENTITYTYPE.getName()));
				}
				registrationQueueDataList.add(registrationQueueData);

			}
			countStatement = connection.prepareStatement(countQuery);
			countRs = countStatement.executeQuery();
			if(countRs.next())
				totalRecords= countRs.getInt(RegQueDBColumns.TOTALROWS.getName());	
		queueDto.setRegistrationQueue(registrationQueueDataList);
		queueDto.setTotalRecords(totalRecords);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeResultset(countRs);
			closePrepareStatement(countStatement);
		}
		return queueDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.core.IRegistrationQueueDBService#
	 * getRegistrationDetails(java.lang.Integer)
	 */
	@Override
	public IRegistrationDetails getRegistrationDetails(Integer contactId, 
			RegistrationSearchCriteria registrationSearchCriteria) throws CompliancePortalException {
		RegistrationDetailsDBDto detailsDBDto = new RegistrationDetailsDBDto();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer accountid=null;
		
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_CONTACT_DETAILS_WITH_EVENT.getQuery());
			preparedStatement.setInt(1, contactId);
			rs = preparedStatement.executeQuery();
			Integer count = 0;
			detailsDBDto.setEventDBDto(new HashMap<String, List<EventDBDto>>());
			while (rs.next()) {
				if (count == 0) {
					detailsDBDto.setContactName(rs.getString("Name"));
					detailsDBDto.setCrmAccountId(rs.getString("CRMAccountID"));
					detailsDBDto.setCrmContactId(rs.getString("CRMContactID"));
					detailsDBDto.setTradeContactId(rs.getString("TradeContactID"));
					detailsDBDto.setTradeAccountNum(rs.getString("TradeAccountNumber"));
					detailsDBDto.setConComplianceStatus(rs.getString("ContactComplianceStatus"));
					detailsDBDto.setAccountAttrib(rs.getString("AccountAttrib"));
					detailsDBDto.setContactAttrib(rs.getString("contactAttrib"));
					detailsDBDto.setAccountId(rs.getInt("AccountId"));
					detailsDBDto.setContactId(rs.getInt("ID"));
					detailsDBDto.setAccComplianceStatus(rs.getString("AccountComplianceStatus"));
					detailsDBDto.setOrgnization(rs.getString("Organization"));
					detailsDBDto.setRegComp(rs.getTimestamp("RegComplete"));
					detailsDBDto.setRegCompAccount(rs.getTimestamp("RegCompleteAcc"));
					detailsDBDto.setRegistrationInDate(rs.getTimestamp("RegistrationInDate"));
					detailsDBDto.setComplianceExpiry(rs.getTimestamp("ComplianceExpiry"));
					detailsDBDto.setRegIn(rs.getTimestamp("RegIn"));
					detailsDBDto.setLegalEntity(rs.getString("LegalEntity"));
					detailsDBDto.setUserResourceId(rs.getInt("userResourceLockId"));
					detailsDBDto.setCountryOfResidenceFullName(rs.getString("countryFullName"));
					detailsDBDto.setIsOnQueue(rs.getBoolean("isOnQueue"));
					detailsDBDto.setDataAnonStatus(rs.getString("dataAnonStatus"));
					detailsDBDto.setCustomCheckStatus(ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt("CustomCheckStatus")));
					detailsDBDto.setPoiExists(rs.getString("poiExists"));
					detailsDBDto.setAccountTMFlag(rs.getInt("accountTMFlag"));
					detailsDBDto.setIntuitionRiskLevel(rs.getString("riskLevel"));
					detailsDBDto.setAccountVersion(rs.getInt("AccountVersion"));
					
					String lockedBy = rs.getString("LockedBy");
					
					checkAndSetLockedBy(detailsDBDto, rs, lockedBy);	
					
				}
				String serviceName = rs.getString("ServiceName");
				List<EventDBDto> eventListByservice = detailsDBDto.getEventDBDto().get(serviceName);
				if (eventListByservice == null) {
					detailsDBDto.getEventDBDto().put(serviceName, new ArrayList<EventDBDto>());
				}
				EventDBDto eventDBDto = new EventDBDto();
				eventDBDto.setId(rs.getInt("EventServiceLogId"));
				eventDBDto.setUpdatedBy(rs.getString("EventUpdatedBy"));
				eventDBDto.setUpdatedOn(rs.getTimestamp("EventUpdatedOn"));
				eventDBDto.setSummary(rs.getString("EventSummary"));
				eventDBDto.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt("EventStatus") ));
				detailsDBDto.getEventDBDto().get(serviceName).add(eventDBDto);
				count++;
			}

			accountid = detailsDBDto.getAccountId();
			detailsDBDto.setOtherContacts(
					getOtherContacts(detailsDBDto.getAccountId(), detailsDBDto.getContactId(), connection));
			detailsDBDto.setWatachList(getAccountWatchlistWithSelected(detailsDBDto.getContactId(), connection,registrationSearchCriteria.getFilter().getUserProfile()));
			detailsDBDto.setActivityLogs(getActivityLogsByContact(contactId,accountid, connection));
			detailsDBDto.setContactStatusReason(getContactStatusReasons(contactId, connection));
			detailsDBDto.setDocumentList(getUploadDocumentList(connection));
			detailsDBDto.setKycSupportedCountryList(getKycCountryList(connection));
			
			if(null != accountid){
				detailsDBDto.setAlertComplianceLog(getAlertComplianceLog(accountid));	
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);

		}
		return registrationDetailsTransformer.transform(detailsDBDto);
	}

	private void checkAndSetLockedBy(RegistrationDetailsDBDto detailsDBDto, ResultSet rs, String lockedBy) throws SQLException {
		if(null != lockedBy && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			detailsDBDto.setLockedBy(rs.getString("LockedBy"));
		}
	}

	private StatusReason getContactStatusReasons(Integer contactId, Connection connection)
			throws CompliancePortalException {

		StatusReason contactStatusReason = new StatusReason();
		List<StatusReasonData> contactStatusReasonList = new ArrayList<>();
		contactStatusReason.setStatusReasonData(contactStatusReasonList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(RegistrationQueryConstant.GET_CONTACT_STATUS_REASON.getQuery());
			statement.setInt(1, contactId);
			rs = statement.executeQuery();
			while (rs.next()) {
				StatusReasonData contactStatusReasonData = new StatusReasonData();
				contactStatusReasonData.setName(rs.getString(REASON));
				Integer contactIdDb = rs.getInt("ContactId");
				if (contactIdDb != -1) {
					contactStatusReasonData.setValue(Boolean.TRUE);
				} else {
					contactStatusReasonData.setValue(Boolean.FALSE);
				}
				contactStatusReasonList.add(contactStatusReasonData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return contactStatusReason;
	}

	/**
	 * Gets the activity logs by contact.
	 *
	 * @param contactId the contact id
	 * @param connection the connection
	 * @return the activity logs by contact
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private ActivityLogs getActivityLogsByContact(Integer contactId,Integer accountId, Connection connection)
			throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataList = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDataList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = connection.prepareStatement(RegistrationQueryConstant.GET_ACTIVITY_LOGS_OF_CONTACT.getQuery());
			statement.setInt(1, contactId);
			statement.setInt(2, accountId);
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
			
			closePrepareStatement(statement);
			closeResultset(rs);
		}
		return activityLogs;

	}

	@Override
	public ActivityLogs updateContact(RegUpdateDBDto requestHandlerDto) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Integer tradeAccountId = null;
		Integer tradeContactId = null;
		Connection readOnlyConn = null;
		String registrationStatusReason= null;
		try {
			
			connection = getConnection(Boolean.FALSE);
			readOnlyConn = getConnection(Boolean.TRUE);

			preparedStatement = readOnlyConn
					.prepareStatement(RegistrationQueryConstant.GET_ACCOUNT_CONTACT_IDS_BY_CONTACT_ID.getQuery());
			preparedStatement.setInt(1, requestHandlerDto.getContactId());
			resultSet = preparedStatement.executeQuery();
			if(resultSet.next()){
				tradeAccountId = resultSet.getInt("TradeAccountID");
				tradeContactId = resultSet.getInt("TradeContactId");
			}
			beginTransaction(connection);
			/**added getWatchListStatus() to update watch list status for paymentout and paymentin */
			getWatchListStatus(requestHandlerDto,readOnlyConn);
			deleteWatchlist(requestHandlerDto, connection);
			addWatchlist(requestHandlerDto, connection);
			updateWatchlistStatus(requestHandlerDto,connection);
			deleteReasons(requestHandlerDto, connection);
			addReasons(requestHandlerDto, connection);
			addActivityLogs(requestHandlerDto, connection);

			String contactStatus = requestHandlerDto.getContactStatus();
			/**Added ResponseStatus*/	
			if (requestHandlerDto.getAddReasons() != null && !(requestHandlerDto.getAddReasons().isEmpty())) {
				registrationStatusReason=requestHandlerDto.getAddReasons().toString().replace("[", "").replace("]", "");
			}
			
			if (contactStatus != null && !contactStatus.isEmpty()) {
				updateContactStatus(contactStatus, requestHandlerDto, requestHandlerDto.getCreatedBy() ,connection);
			
				ComplianceStatus accountStatus = getAccountStatus(requestHandlerDto.getContactId(),contactStatus,
						requestHandlerDto.getAccountId(), connection);
				String responseCode = null;
				String responseCodeDescription = null ;
				//compares old and new account status.If status are different then update it and add response code & description
				if(!getOldAccountStatus(requestHandlerDto.getAccountId(),accountStatus.name(),connection)){
					responseCode=Constants.REGISTRATION_RESPONE_CODE;
					responseCodeDescription= Constants.REGISTRATION_RESPONE_DESCRIPTION;
					updateAccountStatus(requestHandlerDto, accountStatus.name(), connection);
				}				
				if(contactStatus.equalsIgnoreCase(ComplianceStatus.ACTIVE.name()) || contactStatus.equalsIgnoreCase(ComplianceStatus.REJECTED.name()) ){
					updateWorkFlowTime(requestHandlerDto.getUserResourceId(),connection);	
				}
				RegistrationAccount account = getAccountAttributes(requestHandlerDto.getAccountId(), connection);
				if(account == null){
					throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR);
				}
			    account.setAccountStatus(accountStatus.name());
			    ComplianceAccount responseAccount = buildResponseAccount(accountStatus, requestHandlerDto.getAccountSfId(), 
			    		tradeAccountId, responseCode, responseCodeDescription, requestHandlerDto.getRegistrationInDate(),
						requestHandlerDto.getComplianceDoneOn());		
				ComplianceContact responseContact = buildResponseContact(ComplianceStatus.valueOf(contactStatus), 
						requestHandlerDto.getContactSfId(), tradeContactId);
				
				saveIntoBroadcastQueue(getBroadCastDBObject(requestHandlerDto.getAccountId(),
						requestHandlerDto.getContactId(), requestHandlerDto.getCreatedBy(),
						getRegistrationResponse(requestHandlerDto.getOrgCode(),responseAccount, 
								responseContact, registrationStatusReason),
						requestHandlerDto.getOrgCode()), connection);
			}else if(Boolean.FALSE.equals(StringUtils.isNullOrEmpty(requestHandlerDto.getComment())) 
					&& Boolean.TRUE.equals(requestHandlerDto.getIsRequestFromQueue())){
				updateContactIsOnQueueStatus(requestHandlerDto,connection);
			}
			commitTransaction(connection);

		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closePrepareStatement(preparedStatement);
			closeResultset(resultSet);
			closeConnection(readOnlyConn);
			closeConnection(connection);
		}
		return regActivityLogTransformer.transform(requestHandlerDto);
	}


	/**
	 * Update contact is on queue status.
	 * updateContactIsOnQueueStatus() updates IsOnQueue column of contact table
	 * If IsOnQueue is true then PFX record displays on Reg Queue
	 * else record displays on Reg report.
	 * @param requestHandlerDto the request handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 * @author abhijeetg
	 */
	private void updateContactIsOnQueueStatus(RegUpdateDBDto requestHandlerDto, Connection connection) throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_CONTACT_IS_ON_QUEUE_STATUS.getQuery());
			preparedStatement.setString(1, requestHandlerDto.getCreatedBy());
			preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setBoolean(3, requestHandlerDto.getIsContactOnQueue());
			preparedStatement.setInt(4, requestHandlerDto.getContactId());
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

	private void deleteWatchlist(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		Integer contactId = requestHandlerDto.getContactId();
		List<String> delWatchlist = requestHandlerDto.getDeletedWatchlist();

		if (delWatchlist != null && !delWatchlist.isEmpty()) {
			String[] delwatchlistArray = delWatchlist.toArray(new String[delWatchlist.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_WATCHLIST_REASON.getQuery());
			String query = builder.addCriteria(REASON, delwatchlistArray, "AND", ClauseType.IN).build();
			query = "DELETE ContactWatchList WHERE Contact  ="+ contactId +" AND Reason IN(" + query + ")";
			deleteWatchlist(query, connection);
		}
	}

	private void addWatchlist(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		List<String> addWatchlist = requestHandlerDto.getAddWatchlist();
		if (addWatchlist != null && !addWatchlist.isEmpty()) {
			insertWatchlist(requestHandlerDto, connection);
		}
	}

	private void deleteReasons(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		Integer contactId = requestHandlerDto.getContactId();
		List<String> delReasons = requestHandlerDto.getDeletedReasons();
		if (delReasons != null && !delReasons.isEmpty()) {
			String[] delReasonsArray = delReasons.toArray(new String[delReasons.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_STATUS_REASON_ID.getQuery());
			String query = builder.addCriteria("Module", new String[] { "CONTACT", "ALL" }, "AND", ClauseType.IN)
					.addCriteria(REASON, delReasonsArray, "AND", ClauseType.IN).build();
			query = "DELETE [ProfileStatusReason] WHERE ContactID=" + contactId + " AND StatusUpdateReasonID IN ("
					+ query + ")";
			deleteContactReason(query, connection);
		}
	}

	private void addReasons(RegUpdateDBDto requestHandlerDto, Connection connection) throws CompliancePortalException {
		List<String> addReasons = requestHandlerDto.getAddReasons();
		if (addReasons != null && !addReasons.isEmpty()) {
			insertContactReason(requestHandlerDto, connection);
		}
	}

	private void addActivityLogs(RegUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		List<ProfileActivityLogDto> activityLogs = requestHandlerDto.getActivityLog();
		if (activityLogs != null && !activityLogs.isEmpty()) {
			insertProfileActivityLogs(activityLogs, connection);
			insertProfileActivityLogDetail(activityLogs, connection);
		}
	}

	@SuppressWarnings("resource")
	private void insertWatchlist(RegUpdateDBDto regReqHandlerDto, Connection conn)
			throws CompliancePortalException {
		PreparedStatement stmt = null;
		try {
			stmt = conn.prepareStatement(RegistrationQueryConstant.INSERT_CONTACT_WATCHLIST.getQuery());
			for (String addWatchlistStr : regReqHandlerDto.getAddWatchlist()) {
				stmt.setString(1, addWatchlistStr);
				stmt.setInt(2, regReqHandlerDto.getContactId());
				stmt.setString(3, regReqHandlerDto.getCreatedBy());
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

	private void updateContactStatus(String status, RegUpdateDBDto requestHandlerDto, String updatedBy ,Connection connection)
			throws CompliancePortalException {

		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_CONTACT_STATUS.getQuery());
			preparedStatement.setString(1, status);
			preparedStatement.setString(2, updatedBy);
			preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			preparedStatement.setBoolean(4, requestHandlerDto.getIsContactOnQueue());
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

	@SuppressWarnings("resource")
	private void insertContactReason(RegUpdateDBDto regReqHandlerDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		try {
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.INSERT_CONTACT_REASON.getQuery());
			for (String addReasonsStr : regReqHandlerDto.getAddReasons()) {
				preparedStatement.setString(1, regReqHandlerDto.getOrgCode());
				preparedStatement.setInt(2, regReqHandlerDto.getAccountId());
				preparedStatement.setInt(3, regReqHandlerDto.getContactId());
				preparedStatement.setString(4, addReasonsStr);
				preparedStatement.setString(5, regReqHandlerDto.getCreatedBy());
				preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				preparedStatement.setString(7, regReqHandlerDto.getCreatedBy());
				preparedStatement.setTimestamp(8, new Timestamp(System.currentTimeMillis()));
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

	private void deleteContactReason(String query, Connection connection) throws CompliancePortalException {
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

	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest actvtyLogRequest) throws CompliancePortalException {
		ActivityLogs actvtyLog = new ActivityLogs();
		List<ActivityLogDataWrapper> actvtyLogDataList = new ArrayList<>();
		actvtyLog.setActivityLogData(actvtyLogDataList);
		PreparedStatement preparedStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_PROFILE_ACTIVITY_LOGS_BY_ROWS.getQuery());
			preparedStatement.setInt(1, actvtyLogRequest.getEntityId());
			preparedStatement.setInt(2, actvtyLogRequest.getAccountId());
			preparedStatement.setInt(3, actvtyLogRequest.getMinRecord());
			preparedStatement.setInt(4, actvtyLogRequest.getMaxRecord());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setActivity(rs.getString("Activity"));
				activityLogData.setActivityType(ActivityType.getActivityLogDisplay(rs.getString("ActivityType")));
				activityLogData.setCreatedBy(rs.getString("User"));
				Timestamp createdOn = rs.getTimestamp("CreatedOn");
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setComment(rs.getString("Comments"));
				actvtyLogDataList.add(activityLogData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return actvtyLog;
	}

	private ComplianceStatus getAccountStatus(Integer contactId, String contactStatus, Integer accountId,Connection connection) 
			throws CompliancePortalException {
		Boolean inactiveStatusFlag = Boolean.FALSE;
		List<ContactWrapper> contacts = getOtherContacts(accountId, contactId, connection);
		for (ContactWrapper contact : contacts) {
			if (ComplianceStatus.ACTIVE.name().equalsIgnoreCase(contact.getComplianceStatus())) {
				return ComplianceStatus.ACTIVE;
			}
			if(!ServiceStatusEnum.PASS.getUiStatus().equalsIgnoreCase(contact.getPreviousBlacklistStatus()) && 
					!ServiceStatusEnum.NOT_REQUIRED.getUiStatus().equalsIgnoreCase(contact.getPreviousBlacklistStatus()) ){
				return ComplianceStatus.INACTIVE;
			}
			if(!ComplianceStatus.ACTIVE.name().equalsIgnoreCase(contact.getComplianceStatus())){
				inactiveStatusFlag = checkInactiveStatus(contactStatus);
			}				
		}
		if(null == contacts || contacts.isEmpty()){
			inactiveStatusFlag = checkInactiveStatus(contactStatus);
		}
		if(Boolean.TRUE.equals(inactiveStatusFlag)){
			return ComplianceStatus.INACTIVE;
		}		
		return ComplianceStatus.ACTIVE;

	}

	private Boolean checkInactiveStatus(String contactStatus) {
		Boolean result = Boolean.FALSE;
		if(contactStatus.equals(ComplianceStatus.REJECTED.toString()) || 
				contactStatus.equals(ComplianceStatus.INACTIVE.toString())){
			result =  Boolean.TRUE;
		}
		return result;
	}

	private BroadcastEventToDB getBroadCastDBObject(Integer accountId, Integer contactId, String createdBy,
			String statusJson, String orgCode) {
		BroadcastEventToDB db = new BroadcastEventToDB();
		db.setAccountId(accountId);
		db.setContactId(contactId);
		db.setCreatedBy(createdBy);
		db.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliverOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveryStatus(BroadCastStatusEnum.NEW.getBroadCastStatusAsString());
		db.setEntityType(BroadCastEntityTypeEnum.UPDATE.getBroadCastEntityTypeAsString());
		db.setOrgCode(orgCode);
		db.setStatusJson(statusJson);
		return db;
	}

	private String getRegistrationResponse(String orgCode, ComplianceAccount account, ComplianceContact contact,
			String registrationStatusReason) {
		
		RegistrationResponse response = new RegistrationResponse();
		
		account.addContact(contact);
		response.setOsrID(UUID.randomUUID().toString());
		response.setAccount(account);
		response.setOrgCode(orgCode);
		/**Added statusReason for save into BroadCastQueue*/
		contact.setReasonForInactive(registrationStatusReason);
		return JsonConverterUtil.convertToJsonWithoutNull(response);

	}

	private ComplianceContact buildResponseContact(ComplianceStatus contactStatus, String conSfId,
			Integer tradeContactId) {
		ComplianceContact contact = new ComplianceContact();
		contact.setResponseCode(Constants.REGISTRATION_RESPONE_CODE);
		contact.setResponseDescription(Constants.REGISTRATION_RESPONE_DESCRIPTION);
		contact.setCcs(contactStatus);
		contact.setContactSFID(conSfId);
		contact.setTradeContactID(tradeContactId);
		return contact;
	}

	private ComplianceAccount buildResponseAccount(ComplianceStatus accountStatus, String accSfId,
			Integer tradeAccountId, String responseCode, String responseCodeDescription, String registrationInDate,
			String registeredDate) {
		ComplianceAccount account = new ComplianceAccount();
		/** Added Response Code and Response Description for manual activation : issue AT-461 - Abhijit G */
		if(null !=responseCode && null!= responseCodeDescription){
			account.setResponseCode(responseCode);
			account.setResponseDescription(responseCodeDescription);
		}		
		account.setAccountSFID(accSfId);
		account.setAcs(accountStatus);
		account.setTradeAccountID(tradeAccountId);
		account.setRegistrationInDate(DateTimeFormatter.removeMillisFromTimeStamp(registrationInDate));
		account.setRegisteredDate(DateTimeFormatter.removeMillisFromTimeStamp(registeredDate));
		return account;
	}


	@Override
	public ViewMoreResponse getMoreKycDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getEntityId());
			preparedStatement.setInt(4, viewMoreRequest.getMinViewRecord());
			preparedStatement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreKycDetails(resultSet);

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
	public ViewMoreResponse getMoreSanctionDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getEntityId());
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
	public ViewMoreResponse getMoreFraugsterDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getEntityId());
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
	public ViewMoreResponse getMoreCustomCheckDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS_FOR_CUSTOM_CHECK.getQuery());
			preparedStatement.setString(1, Constants.IP);
			preparedStatement.setString(2, Constants.GLOBALCHECK);
			preparedStatement.setString(3, Constants.COUNTRYCHECK);
			preparedStatement.setInt(4, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(5, viewMoreRequest.getEntityId());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreCustomCheckDetails(resultSet);
			if (domainList.size() >= viewMoreRequest.getMaxViewRecord()) {
				domainList = domainList.subList(viewMoreRequest.getMinViewRecord(), viewMoreRequest.getMaxViewRecord());
			} else {
				domainList = domainList.subList(viewMoreRequest.getMinViewRecord(), domainList.size());
			}
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
	
	private boolean getOldAccountStatus(Integer accountId,String status,Connection connection) 
			throws CompliancePortalException {
		
		PreparedStatement statement = null;
		ResultSet rs = null;		
		try {
			statement = connection.prepareStatement(RegistrationQueryConstant.GET_OLD_ACCOUNT_STATUS.getQuery());
			statement.setInt(1, accountId);
			rs = statement.executeQuery();
			String oldAccountStatus = null;
			while(rs.next()){
				oldAccountStatus = ProfileComplianceStatus.getUiStatusFromDatabaseStatus(rs.getInt("compliancestatus"));
			}
			if(status.equalsIgnoreCase(oldAccountStatus)){
				return true;
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return false;
	}
	
	@Override
	public ViewMoreResponse getMoreOnfidoDetails(RegistrationViewMoreRequest viewMoreRequest)
			throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			preparedStatement.setInt(3, viewMoreRequest.getEntityId());
			preparedStatement.setInt(4, viewMoreRequest.getMinViewRecord());
			preparedStatement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preparedStatement.executeQuery();
			domainList = getMoreOnfidoDetails(resultSet);
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

	@Override
	public boolean setPoiExistsFlag(UserProfile user,Contact contact) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = getConnection(Boolean.TRUE);
			//update contact table to set POI Exists Flag
			preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_CONTACT_POI_EXISTS_FLAG.getQuery());	
			preparedStatement.setString(1, contact.getContactSFID());
		    preparedStatement.executeUpdate();
	} catch (Exception e) {
		transactionRolledBack(connection);
		throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
	} finally {
		closePrepareStatement(preparedStatement);
		closeConnection(connection);
	}
		return true;
	}
	

	@Override
	public ViewMoreResponse getMoreIntuitionDetails(RegistrationViewMoreRequest viewMoreRequest) //AT-4114
			throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS.getQuery());
			preparedStatement.setString(1, viewMoreRequest.getServiceType());
			preparedStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus("ACCOUNT")); //AT-4170 (TM perform on account level only)
			preparedStatement.setInt(3, viewMoreRequest.getAccountId()); //AT-4170
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
