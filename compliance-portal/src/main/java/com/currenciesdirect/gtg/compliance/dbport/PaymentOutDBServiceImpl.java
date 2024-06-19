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
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Contact;
import com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogDataWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ActivityLogs;
import com.currenciesdirect.gtg.compliance.core.domain.BroadcastEventToDB;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.PaginationDetails;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest;
import com.currenciesdirect.gtg.compliance.core.domain.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.Status;
import com.currenciesdirect.gtg.compliance.core.domain.StatusData;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.ViewMoreResponse;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDetailDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueData;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutRecheckFailureDetails;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.RecentPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.core.domain.registration.ProfileActivityLogDto;
import com.currenciesdirect.gtg.compliance.core.domain.savedsearch.SavedSearch;
import com.currenciesdirect.gtg.compliance.dbport.cfx.RegistrationCfxDBQueryConstant;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.BroadCastStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.EntityTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WatchListCategoryEnum;
import com.currenciesdirect.gtg.compliance.dbport.report.PayOutReportQueueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.report.PaymenOutReportAccountFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.report.PaymentOutReportContactFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.report.PaymentOutReportQueryConstant;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class PaymentOutDBServiceImpl.
 */
@Component("paymentOutDBServiceImpl")
public class PaymentOutDBServiceImpl extends AbstractDBServicePaymentOut implements IPaymentOutDBService {

	
	private static final String INNER_QUERY = "{INNER_QUERY}";

    /** The Constant WHERE. */
	private static final String WHERE = "WHERE";
	
	/** The Constant UPDATEDON. */
	private static final String UPDATEDON = "UpdatedOn";
	
	/** The itransform. */
	@Autowired
	@Qualifier("paymentOutDetailsTransformer")
	private ITransform<PaymentOutDetailsDto, PaymentOutDBDto> itransform;

	/** The payment out activity log transformer. */
	@Autowired
	@Qualifier("paymentOutActivityLogTransformer")
	private ITransform<ActivityLogs, PaymentOutUpdateDBDto> paymentOutActivityLogTransformer;
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getPaymentOutDetails(java.lang.Integer, com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria)
	 */
	@Override
	public PaymentOutDetailsDto getPaymentOutDetails(Integer paymentOutId, String custType, PaymentOutSearchCriteria paymentOutSearchCriteria) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PaymentOutDBDto paymentOutDBDto = new PaymentOutDBDto();
		Integer accountId=null;
		
		try {
			Boolean isFirst = Boolean.TRUE;
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(PaymentOutQueryConstant.GET_PAYMENT_OUT_DETAILS.getQuery());
			statement.setInt(1, paymentOutId);
			rs = statement.executeQuery();
			paymentOutDBDto.setEventDBDtos(new HashMap<String, List<EventDBDto>>());
			while(rs.next()) {
				if(Boolean.TRUE.equals(isFirst)){
				paymentOutDBDto.setAccAttrib(rs.getString("AccountAttrib"));
				paymentOutDBDto.setAccountId(rs.getInt(Constants.ACCOUNT_ID));
				paymentOutDBDto.setBeneficiaryName(rs.getString("vBeneficiaryFirstName")+" "+rs.getString("vBeneficiaryLastName"));
				paymentOutDBDto.setBuyingAmount(rs.getString("vBuyingAmount"));
				paymentOutDBDto.setContactAttib(rs.getString("ContactAttrib"));
				paymentOutDBDto.setContactId(rs.getInt("ContactId"));
				paymentOutDBDto.setContactName(rs.getString("ContactName"));
				paymentOutDBDto.setContractNumber(rs.getString("ContractNumber"));
				paymentOutDBDto.setCountry(rs.getString("Country"));
				paymentOutDBDto.setCrmAccountId(rs.getString("CRMAccountId"));
				paymentOutDBDto.setOrgCode(rs.getString("Organisation"));
				paymentOutDBDto.setPaymentOutAttributes(rs.getString("PaymentOutAttributes"));
				paymentOutDBDto.setPaymentOutId(rs.getInt("Id"));
				paymentOutDBDto.setReasonOfTransfer(rs.getString("vReasonoftransfer"));
				paymentOutDBDto.setRegIn(rs.getTimestamp("RegIn"));
				paymentOutDBDto.setSellingAmount(rs.getString("vSellingAmount"));
				paymentOutDBDto.setTradeAccountNum(rs.getString("TradeAccountNumber"));
				paymentOutDBDto.setTradeContactId(rs.getString("TradeContactId"));
				paymentOutDBDto.setUserResourceId(rs.getInt("UserResourceLockId"));
				paymentOutDBDto.setAccComplianceStatus(rs.getString("AccComplianceStatus"));
				paymentOutDBDto.setConComplianceStatus(rs.getString("ContactComplianceStatus"));
				paymentOutDBDto.setBeneficiaryCountry(rs.getString("BeneficiaryCounry"));
				paymentOutDBDto.setBeneficiaryCountryFullName(rs.getString("CountryOfBeneficiaryFullName"));
				paymentOutDBDto.setBuyCurrency(rs.getString("BuyCurrency"));
				paymentOutDBDto.setPaymentOutStatus(rs.getString("PaymentOutStaus"));
				paymentOutDBDto.setCrmContactId(rs.getString("CRMContactID"));
				paymentOutDBDto.setRegComp(rs.getTimestamp("RegComplete"));
				paymentOutDBDto.setTradePaymentId(rs.getString("TradePaymentId"));
				paymentOutDBDto.setTradeBeneficiaryId(rs.getString("vtradeBeneficiaryId"));
                paymentOutDBDto.setIsDeleted(String.valueOf(rs.getBoolean("Deleted")));
				paymentOutDBDto.setIsOnQueue(rs.getBoolean("isOnQueue"));
				paymentOutDBDto.setInitialStatus(rs.getString("InitialStatus"));//AT-3471
				paymentOutDBDto.setIntuitionRiskLevel(rs.getString("IntuitionRiskLevel")); // AT-4187
				paymentOutDBDto.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(rs.getTimestamp(UPDATEDON)));
				getNationalityFullName(connection,paymentOutDBDto.getContactId(),paymentOutDBDto);
				paymentOutDBDto.setPoiExists(rs.getString("poiExists")); //AT-3450
				//added by neelesh pant
				paymentOutDBDto.setBankid(rs.getString("vTradeBankID"));
				paymentOutDBDto.setBeneficiaryAmount(rs.getString("vBuyingAmount"));
				paymentOutDBDto.setLegalEntity(rs.getString("LegalEntity"));
				paymentOutDBDto.setAccountTMFlag(rs.getInt("AccountTMFlag"));
				paymentOutDBDto.setAccountVersion(rs.getInt("Version"));
				String lockedBy= rs.getString("LockedBy");
				setLockedBy(paymentOutDBDto, lockedBy);
				isFirst = Boolean.FALSE;
				}
				String serviceName = rs.getString(Constants.SERVICE_TYPE);
				String entityType = EntityTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt("EntityType"));
				if(entityType != null){
					entityType = entityType.toUpperCase();
				}
				List<EventDBDto> eventListByEntityTpeService = paymentOutDBDto.getEventDBDtos()
						.get(serviceName + entityType);
				if (eventListByEntityTpeService == null) {
					paymentOutDBDto.getEventDBDtos().put(serviceName+entityType, new ArrayList<EventDBDto>());
				}
				EventDBDto eventDBDto = new EventDBDto();
				
				eventDBDto.setEitityType(entityType);
				eventDBDto.setEntityId(rs.getString("EntityId"));
				eventDBDto.setId(rs.getInt("EventServiceLogId"));
				eventDBDto.setServiceType(rs.getString(Constants.SERVICE_TYPE));
				eventDBDto.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(rs.getInt("EventServiceStatus")));
				eventDBDto.setSummary(rs.getString("Summary"));
				eventDBDto.setUpdatedBy(rs.getString("EventServiceUpdatedBy"));
				eventDBDto.setUpdatedOn(rs.getTimestamp("EventServiceUpdatedOn"));
				eventDBDto.setIntuitionCurrentAction(rs.getString("intuitionCurrentAction")); //AT-4962
				paymentOutDBDto.getEventDBDtos().get(serviceName+entityType).add(eventDBDto);
			}
			
			accountId = paymentOutDBDto.getAccountId();
			paymentOutDBDto.setWatchList(getAccountWatchlistWithSelected(paymentOutDBDto.getAccountId(), paymentOutDBDto.getContactId(), custType, 
					connection, paymentOutSearchCriteria.getFilter().getUserProfile()));
			paymentOutDBDto.setStatus(getPaymentOutStatus(paymentOutId, connection));
			paymentOutDBDto.setStatusReason(getPaymentOutStatusReasons(paymentOutDBDto.getPaymentOutId(), connection));
			paymentOutDBDto.setFurtherpaymentDetails(getFurtherPaymentDetailsList(paymentOutDBDto.getAccountId(), connection));
			paymentOutDBDto.setActivityLogs(getActivityLogs(paymentOutId, connection));
			paymentOutDBDto.setDocumentList(getUploadDocumentList(connection));
			
			if(null !=accountId){
				paymentOutDBDto.setAlertComplianceLog(getAlertComplianceLog(accountId));
			}
			//code added for AT-898 for Sprint 1 
			paymentOutDBDto.setNoOfContactsForAccount(getNoOfContactsForAccount(paymentOutDBDto.getAccountId(), connection));
			
			//Add for AT-3161
			if(custType.equals("PFX")){
				paymentOutDBDto.setCustomRuleFPFlag(getCustomRuleFraudPredictFlag(connection));
			}
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return itransform.transform(paymentOutDBDto);
	}
	
	
	/**
	 * Gets the custom rule fraud predict flag.
	 *
	 * @param connection the connection
	 * @return the custom rule fraud predict flag
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private Boolean getCustomRuleFraudPredictFlag(Connection connection) throws CompliancePortalException {
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		Boolean flag = null;
		try {
			prepareStatement = connection.prepareStatement(PaymentOutQueryConstant.GET_FRAUD_PREDICT_FLAG_FOR_CUSTOM_RULE.getQuery());
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				flag = rs.getBoolean("alwaysPass");
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(prepareStatement);
		}
		return flag;
	}
	
	/**
	 * Gets the no of contacts for account.
	 *
	 * @param accountId the account id
	 * @param connection the connection
	 * @return the no of contacts for account
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private Integer getNoOfContactsForAccount(Integer accountId, Connection connection) throws CompliancePortalException {
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		Integer contacts = 0;
		try {
			prepareStatement = connection.prepareStatement(PaymentInQueryConstant.GET_NO_OF_CONTACTS_FOR_ACCOUNT.getQuery());
			prepareStatement.setInt(1, accountId);
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				contacts = rs.getInt(1);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(prepareStatement);
		}
		return contacts;
	}

	/**
	 * Sets the locked by.
	 *
	 * @param paymentOutDBDto the payment out DB dto
	 * @param lockedBy the locked by
	 */
	private void setLockedBy(PaymentOutDBDto paymentOutDBDto, String lockedBy) {
		if(null != lockedBy && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)){
			paymentOutDBDto.setLockedBy(lockedBy);
		}
	}

	/**
	 * Gets the nationality full name.
	 *
	 * @param connection the connection
	 * @param contactId the contact id
	 * @param paymentOutDBDto the payment out DB dto
	 * @return the nationality full name
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void getNationalityFullName(Connection connection, Integer contactId,PaymentOutDBDto paymentOutDBDto) throws CompliancePortalException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection
					.prepareStatement(PaymentOutQueryConstant.GET_PAYMENT_OUT_NATIONALITY_DISPLAY_NAME.getQuery());
			statement.setInt(1, contactId);
			rs = statement.executeQuery();
			while (rs.next()) {
				paymentOutDBDto.setNationalityFullName(rs.getString("NationalityFullName"));
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getMostRecentPaymentOutId(java.lang.String, java.lang.String)
	 */
	@Override
	public RecentPaymentOutDetails getMostRecentPaymentOutId(String tradeAccountNumber, String beneAccountNumber) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		RecentPaymentOutDetails paymentOutDBDto = new RecentPaymentOutDetails();
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(PaymentOutQueryConstant.GET_MOST_RECENT_PAYMENTOUT.getQuery());
			statement.setString(1, tradeAccountNumber);
			statement.setString(2, beneAccountNumber);
			rs = statement.executeQuery();
			while(rs.next()) {
				paymentOutDBDto.setPaymentId(rs.getInt("Id"));
				paymentOutDBDto.setCustType(Integer.toString(rs.getInt("Type")));
			}
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		return paymentOutDBDto;
	}
 

	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getPaymentOutDetailsWithCriteria(com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria)
	 */
	@Override
	public PaymentOutDetailsDto getPaymentOutDetailsWithCriteria(PaymentOutSearchCriteria searchCriteria)
			throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		PaymentOutDBDto paymentOutDBD = new PaymentOutDBDto();
		Integer currentRecord = searchCriteria.getPage().getCurrentRecord();
	
		try {
			
			FilterQueryBuilder queryBuilder = new PaymentOutFilterQueryBuilder(searchCriteria.getFilter(),
					PaymentOutQueryConstant.GET_PAYMENTOUT_QUEUE_INNER_QUERY.getQuery());
			
			String query = queryBuilder.getQuery();
			query = PaymentOutQueryConstant.GET_PAYMENTOUT_DETAILS_BY_PAYMENTOUTID.getQuery().replace(INNER_QUERY, query);
			Boolean isFirst = Boolean.TRUE;
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(query);
			statement.setInt(1, currentRecord);
			statement.setInt(2, currentRecord);
			rs = statement.executeQuery();
			paymentOutDBD.setEventDBDtos(new HashMap<String, List<EventDBDto>>());
			while(rs.next()) {
				if(Boolean.TRUE.equals(isFirst)){
				paymentOutDBD.setAccAttrib(rs.getString("AccountAttrib"));
				paymentOutDBD.setAccountId(rs.getInt(Constants.ACCOUNT_ID));
				paymentOutDBD.setBeneficiaryName(rs.getString("BeneficiaryFirstName")+" "+rs.getString("BeneficiaryLastName"));
				paymentOutDBD.setBuyingAmount(rs.getString("BuyingAmount"));
				paymentOutDBD.setContactAttib(rs.getString("ContactAttrib"));
				paymentOutDBD.setContactId(rs.getInt("ContactId"));
				paymentOutDBD.setContactName(rs.getString("ContactName"));
				paymentOutDBD.setContractNumber(rs.getString("ContractNumber"));
				paymentOutDBD.setCountry(rs.getString("Country"));
				paymentOutDBD.setCrmAccountId(rs.getString("CRMAccountId"));
				paymentOutDBD.setLockedBy(rs.getString("LockedBy"));
				paymentOutDBD.setOrgCode(rs.getString("Organisation"));
				paymentOutDBD.setPaymentOutAttributes(rs.getString("PaymentOutAttributes"));
				paymentOutDBD.setPaymentOutId(rs.getInt("Id"));
				paymentOutDBD.setReasonOfTransfer(rs.getString("ReasonOfTransfer"));
				paymentOutDBD.setRegIn(rs.getTimestamp("RegIn"));
				paymentOutDBD.setSellingAmount(rs.getString("SellingAmount"));
				paymentOutDBD.setTradeAccountNum(rs.getString("TradeAccountNumber"));
				paymentOutDBD.setTradeContactId(rs.getString("TradeContactId"));
				paymentOutDBD.setUserResourceId(rs.getInt("UserResourceLockId"));
				paymentOutDBD.setAccComplianceStatus(rs.getString("AccComplianceStatus"));
				paymentOutDBD.setConComplianceStatus(rs.getString("ContactComplianceStatus"));
				paymentOutDBD.setBeneficiaryCountry(rs.getString("BeneficiaryCounry"));
				paymentOutDBD.setBuyCurrency(rs.getString("BuyCurrency"));
				paymentOutDBD.setDateOfPayment(rs.getTimestamp("DateOfPayment").toString());
				paymentOutDBD.setPaymentOutStatus(rs.getString("PaymentOutStaus"));
				paymentOutDBD.setCrmContactId(rs.getString("CRMContactID"));
				paymentOutDBD.setRegComp(rs.getTimestamp("RegComplete"));
				paymentOutDBD.setTradeBeneficiaryId(rs.getString("tradeBeneficiaryId"));
				paymentOutDBD.setTradePaymentId(rs.getString("TradePaymentId"));
				paymentOutDBD.setBeneficiaryAmount(rs.getString("BeneAmount"));

				isFirst = Boolean.FALSE;
				}
				String serviceName = rs.getString(Constants.SERVICE_TYPE);
				String typeOfEntity = rs.getString("EntityType");
				if(typeOfEntity != null){
					typeOfEntity = typeOfEntity.toUpperCase();
				}
				List<EventDBDto> eventListByEntityTypeService = paymentOutDBD.getEventDBDtos().get(serviceName+typeOfEntity);
				if (eventListByEntityTypeService == null) {
					paymentOutDBD.getEventDBDtos().put(serviceName+typeOfEntity, new ArrayList<EventDBDto>());
				}
				EventDBDto eventDBD = new EventDBDto();
				
				eventDBD.setEitityType(typeOfEntity);
				eventDBD.setEntityId(rs.getString("EntityId"));
				eventDBD.setId(rs.getInt("EventServiceLogId"));
				eventDBD.setServiceType(rs.getString(Constants.SERVICE_TYPE));
				eventDBD.setStatus(rs.getString("EventServiceStatus"));
				eventDBD.setSummary(rs.getString("Summary"));
				eventDBD.setUpdatedBy(rs.getString("EventServiceUpdatedBy"));
				eventDBD.setUpdatedOn(rs.getTimestamp("EventServiceUpdatedOn"));
				paymentOutDBD.getEventDBDtos().get(serviceName+typeOfEntity).add(eventDBD);
				
			}
			
			paymentOutDBD.getAccountId();
			paymentOutDBD.setWatchList(getAccountWatchlist(paymentOutDBD.getAccountId(), connection));
			paymentOutDBD.setStatus(getPaymentOutStatus(paymentOutDBD.getPaymentOutId(), connection));
			paymentOutDBD.setStatusReason(getPaymentOutStatusReasons(paymentOutDBD.getPaymentOutId(), connection));
			paymentOutDBD.setFurtherpaymentDetails(getFurtherPaymentDetailsList(paymentOutDBD.getAccountId(), connection));
			paymentOutDBD.setActivityLogs(getActivityLogs(paymentOutDBD.getPaymentOutId(), connection));
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
			closeConnection(connection);

		}
		return itransform.transform(paymentOutDBD);
	}

	/**
	 * Gets the payment out status reasons.
	 *
	 * @param paymentOutId the payment out id
	 * @param connection the connection
	 * @return the payment out status reasons
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private StatusReason getPaymentOutStatusReasons(Integer paymentOutId, Connection connection)
			throws CompliancePortalException {
		StatusReason paymentOutStatusReason = new StatusReason();
		List<StatusReasonData> paymentOutStatusReasonList = new ArrayList<>();
		paymentOutStatusReason.setStatusReasonData(paymentOutStatusReasonList);
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = connection.prepareStatement(PaymentOutQueryConstant.GET_PAYMENTOUT_STATUS_REASON.getQuery());
			statement.setInt(1, paymentOutId);
			rs = statement.executeQuery();
			while (rs.next()) {
				StatusReasonData paymentOutStatusReasonData = new StatusReasonData();
				paymentOutStatusReasonData.setName(rs.getString("Reason"));
				Integer paymentOutIdDb = rs.getInt(Constants.PAYMENT_OUT_ID);
				if (paymentOutIdDb != -1) {
					paymentOutStatusReasonData.setValue(Boolean.TRUE);
				}else {
					paymentOutStatusReasonData.setValue(Boolean.FALSE);
				}
				paymentOutStatusReasonList.add(paymentOutStatusReasonData);
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(statement);
		}
		return paymentOutStatusReason;
	}

		
	/**
	 * Gets the payment out data.
	 *
	 * @param connection the connection
	 * @param minRowNum the min row num
	 * @param maxRowNum the max row num
	 * @param query the query
	 * @param countQuery the count query
	 * @return the payment out data
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private PaymentOutQueueDto getPaymentOutData(Connection connection, Integer minRowNumber, Integer maxRowNumber,
			String query, String queryCount) throws CompliancePortalException {
		List<PaymentOutQueueData> payOutQueueDataList = new ArrayList<>();
		PaymentOutQueueDto payOutQueueDto = new PaymentOutQueueDto();
		PreparedStatement preparedStatement = null;
		PreparedStatement	countStmt = null;
		ResultSet rs = null;
		ResultSet countRs = null;
		Integer totalRows = 0;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, minRowNumber);
			preparedStatement.setInt(2, maxRowNumber);
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				setPaymentOutList(payOutQueueDataList, rs);
			}
			countStmt = connection.prepareStatement(queryCount);
			countRs = countStmt.executeQuery();
			if(countRs.next())
				totalRows = countRs.getInt(PayOutReportQueueDBColumns.TOTAL_ROWS.getName());
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeResultset(countRs);
			closePrepareStatement(countStmt);
		}
		payOutQueueDto.setPaymentOutQueue(payOutQueueDataList);
		payOutQueueDto.setTotalRecords(totalRows);
		
		List<String> owner = null;
		owner= getLockedUserName(connection);
		payOutQueueDto.setOwner(owner);
		
		return payOutQueueDto;

	}

	/**
	 * Sets the payment out list.
	 *
	 * @param paymentOutQueueDataList the payment out queue data list
	 * @param rs the rs
	 * @throws SQLException the SQL exception
	 */
	private void setPaymentOutList(List<PaymentOutQueueData> paymentOutQueueDataList, ResultSet rs)
			throws SQLException {
		PaymentOutQueueData payOutQueueData = new PaymentOutQueueData();
		payOutQueueData.setTransactionId(rs.getString(PayOutReportQueueDBColumns.TRANSACTIONID.getName()));
		payOutQueueData.setClientId(rs.getString(PayOutReportQueueDBColumns.TRADEACCOUNTNUM.getName()));
		payOutQueueData.setAccountId(rs.getInt(PayOutReportQueueDBColumns.ACCOUNTID.getName()));
		payOutQueueData.setContactId(rs.getInt(PayOutReportQueueDBColumns.CONTACTID.getName()));
		payOutQueueData.setAcsfId(rs.getString(PayOutReportQueueDBColumns.ACSFID.getName()));
		payOutQueueData.setReasonOfTransfer(rs.getString(PayOutReportQueueDBColumns.REASONOFTRANSFER.getName()));
		
		if (null!= rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())) {
			payOutQueueData.setDate(DateTimeFormatter.dateTimeFormatter(rs.getTimestamp(PayOutReportQueueDBColumns.DATE.getName())));
		}
		else
			payOutQueueData.setDate(Constants.DASH_UI);
		payOutQueueData.setContactName(rs.getString(PayOutReportQueueDBColumns.CLIENTNAME.getName()));
		payOutQueueData.setType(
				CustomerTypeEnum.getUiStatusFromDatabaseStatus(rs.getInt(PayOutReportQueueDBColumns.TYPE.getName())));
		payOutQueueData.setBuyCurrency(rs.getString(PayOutReportQueueDBColumns.BUYCURRENCY.getName()));
		/**
		 * Code changed to show amount upto 2 decimal places on queue (AT - 490)
		 */
		String beneAmount = rs.getString(PayOutQueDBColumns.BENEFICIARYAMMOUNT.getName());
		if (beneAmount != null && beneAmount.contains(".")) {
			beneAmount = beneAmount.substring(0, beneAmount.indexOf('.') + 3);
			payOutQueueData.setAmount(StringUtils.getNumberFormat(beneAmount));
		} else{
			payOutQueueData.setAmount(StringUtils.getNumberFormat(beneAmount));
		}
		payOutQueueData.setBeneficiary(rs.getString(PayOutReportQueueDBColumns.BENEFICIARY.getName()));
		payOutQueueData.setCountry(rs.getString(PayOutReportQueueDBColumns.COUNTRY.getName()));
		payOutQueueData.setIsoCountry(rs.getString(PayOutReportQueueDBColumns.ISO_COUNTRY.getName()));
		payOutQueueData.setOverallStatus(rs.getString(PayOutReportQueueDBColumns.OVERALLSTATUS.getName()));
		payOutQueueData.setWatchlist(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayOutReportQueueDBColumns.WATCHLISTSTATUS.getName())));
		payOutQueueData.setFraugster(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.FRAUGSTERSTATUS.getName())));
		payOutQueueData.setSanction(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.SANCTIONSTATUS.getName())));
		payOutQueueData.setBlacklist(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.BLACKLISTSTATUS.getName())));
		payOutQueueData.setIntuitionStatus(
				ServiceStatusEnum.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.INTUITIONSTATUS.getName())));//AT-4607
		
		
		
		/**
		 * Added sanction column BlacklistPaymentReference status on PaymentOut Queue :AT-3854 :
		 * Abhijit Khatavkar
		 */
		payOutQueueData.setBlacklistPayRef(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayOutQueDBColumns.BLACKLISTPAYREFSTATUS.getName())));
		/**
		 * Added Customcheck status column on PaymentOut Queue : fixed issue
		 * AT-464 : Abhijit Gorde
		 */
		payOutQueueData.setCustomCheck(ServiceStatusEnum
				.getStatusFromDatabaseStatus(rs.getInt(PayOutReportQueueDBColumns.CUSTOMCHECKSTATUS.getName())));
		// hidden values on UI
		Integer paymentOutId = rs.getInt(PayOutReportQueueDBColumns.PAYMENTOUTID.getName());
		if (paymentOutId != null) {
			payOutQueueData.setPaymentOutId(paymentOutId.toString());
		}
		payOutQueueData.setOrganisation(rs.getString(PayOutReportQueueDBColumns.ORGANIZATION.getName()));
		payOutQueueData.setLegalEntity(rs.getString(PayOutReportQueueDBColumns.LEGALENTITY.getName()));
		
		String valueDate = rs.getString(PayOutReportQueueDBColumns.VALUE_DATE.getName());
		if (valueDate != null) {
			payOutQueueData.setValueDate(DateTimeFormatter.getDateInRFC3339(valueDate));
		}
		String maturityDate = rs.getString(PayOutReportQueueDBColumns.MATURITY_DATE.getName());
		if (maturityDate != null) {
			payOutQueueData.setMaturityDate(DateTimeFormatter.getDateInRFC3339(maturityDate));
		}
		String lockedBy = rs.getString(PayOutReportQueueDBColumns.LOCKED_BY.getName());
		if (lockedBy != null && !lockedBy.isEmpty() && !lockedBy.equalsIgnoreCase(Constants.DUMMY_USER)) {
			payOutQueueData
					.setUserResourceLockId(rs.getInt(PayOutReportQueueDBColumns.USER_RESOURCE_LOCK_ID.getName()));
			payOutQueueData.setLocked(Boolean.TRUE);
			payOutQueueData.setLockedBy(lockedBy);
		}
		paymentOutQueueDataList.add(payOutQueueData);
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getPaymentOutQueueWithCriteria(com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSearchCriteria)
	 */
	@Override
	public PaymentOutQueueDto getPaymentOutQueueWithCriteria(PaymentOutSearchCriteria  searchCriteria)
			throws CompliancePortalException {
		PaymentOutQueueDto paymentOutQueueDto = new PaymentOutQueueDto();
		Connection connection = null;
		try {
			int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
			int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
			int rowsToFetch = SearchCriteriaUtil.getPageSize();
			String paymentOutQueue = "PaymentOutQueue";
			PaymentOutContactFilterQueryBuilder contactFilter = new PaymentOutContactFilterQueryBuilder(searchCriteria.getFilter(),
					PaymentOutQueryConstant.PAYMENT_OUT_QUEUE_CONTACT_FILTER.getQuery(),true);
			String contactFilterQuery = buildContactFilterQuery(searchCriteria);

			String accountFilterQuery = buildAccountFilterQuery(searchCriteria);

			String paymentFilterQuery = buildPaymentAttributeFilterQuery(searchCriteria);

			String paymentOutFilterQuery = buildPaymentOutFilterQuery(searchCriteria, contactFilterQuery,
					accountFilterQuery, paymentFilterQuery, contactFilter);
			
			String listQuery = PaymentOutQueryConstant.GET_PAYMENTOUT_QUEUE.getQuery()
					.replace("{PAYMENT_OUT_QUEUE_CONTACT_FILTER}", contactFilterQuery)
					.replace("{PAYMENT_OUT_QUEUE_ACCOUNT_FILTER}", accountFilterQuery)
					.replace("{PAYMENT_OUT_QUEUE_PAYMENT_FILTER}", paymentFilterQuery)
					.replace("{PAYMENT_QUEUE_FILTER}", paymentOutFilterQuery);
			
			listQuery = executeSort(searchCriteria.getFilter().getSort(),listQuery);
			

			String countQuery = PaymentOutQueryConstant.GET_PAYMENTOUT_REPORT_COUNT.getQuery()
					.replace("{PAYMENT_OUT_QUEUE_CONTACT_FILTER}", contactFilterQuery)
					.replace("{PAYMENT_OUT_QUEUE_ACCOUNT_FILTER}", accountFilterQuery)
					.replace("{PAYMENT_OUT_QUEUE_PAYMENT_FILTER}", paymentFilterQuery)
					.replace("{PAYMENT_QUEUE_FILTER}", paymentOutFilterQuery);

			connection = getConnection(Boolean.TRUE);
			paymentOutQueueDto = getPaymentOutData(connection, offset, rowsToFetch, listQuery, countQuery);

			Integer pageSize = SearchCriteriaUtil.getPageSize();
			Integer totalRecords = paymentOutQueueDto.getTotalRecords();
			if (totalRecords != null) {
				setPageAndOtherDetails(searchCriteria, paymentOutQueueDto, connection, minRowNum, pageSize,
						totalRecords);
			}
			SavedSearch savedSearch = null;
			savedSearch = getSaveSearches(searchCriteria.getFilter().getUserProfile().getId(),paymentOutQueue,connection);
			paymentOutQueueDto.setSavedSearch(savedSearch);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return paymentOutQueueDto;
	}
	
	/**
	 * Execute sort.
	 *
	 * @param sort the sort
	 * @param query the query
	 * @return the string
	 */
	private String executeSort(Sort colSort,String query) {
		String nameColumn = UPDATEDON;
		boolean isAsc = false;
		if(colSort != null && colSort.getIsAscend() != null){
			isAsc = colSort.getIsAscend();
		}
		
		if(colSort != null && colSort.getFieldName() != null &&  !colSort.getFieldName().isEmpty()){
			nameColumn = colSort.getFieldName();
		}
		
		String columnQueryName = "payOut.UpdatedOn";
		if(UPDATEDON.equalsIgnoreCase(nameColumn)){
			columnQueryName = "payOut.UpdatedOn";
		} else if ("tradeaccountnumber".equalsIgnoreCase(nameColumn)){
			columnQueryName = "acc.tradeaccountnumber";
		} else if ("vMaturityDate".equalsIgnoreCase(nameColumn)){
			columnQueryName = "poa.vMaturityDate";
		} else if ("type".equalsIgnoreCase(nameColumn)){
			columnQueryName = "acc.type";
		}
		
		return addSort(columnQueryName, isAsc,query);
		
		
	}

	/**
	 * Builds the payment out filter query.
	 *
	 * @param searchCriteria the search criteria
	 * @param contactFilterQuery the contact filter query
	 * @param accountFilterQuery the account filter query
	 * @param paymentFilterQuery the payment filter query
	 * @return the string
	 */
	private String buildPaymentOutFilterQuery(PaymentOutSearchCriteria searchCriteria, String contactFilterQuery,
			String accountFilterQuery, String paymentFilterQuery, PaymentOutContactFilterQueryBuilder contactFilter) 
	{
		String contactJoin = "";
		if (contactFilterQuery.contains(WHERE) || contactFilterQuery.contains("category")) {
			contactJoin = PaymentOutQueryConstant.CONTACT_FILTER_JOIN.getQuery();
		}

		String accountJoin = "";
		if (accountFilterQuery.contains(WHERE)) {
			accountJoin = PaymentOutQueryConstant.ACCOUNT_FILTER_JOIN.getQuery();
		}

		String paymentJoin = "";
		if (paymentFilterQuery.contains(WHERE)) {
			paymentJoin = PaymentOutQueryConstant.PAYMENT_FILTER_JOIN.getQuery();
		}
		
		String legalEntityJoin = "";
		if (null != searchCriteria.getFilter().getLegalEntity()
				&& searchCriteria.getFilter().getLegalEntity().length > 0) {
			legalEntityJoin = PaymentOutQueryConstant.LEGEL_ENTITY_TABLE_JOIN.getQuery();
		}
		
		String orgJoin = "";
		if (null != searchCriteria.getFilter().getOrganization()
				&& searchCriteria.getFilter().getOrganization().length > 0) {
			orgJoin = PaymentOutQueryConstant.ORG_FILTER_JOIN.getQuery();
		}

		FilterQueryBuilder paymentOutFilter = new PaymentOutFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutQueryConstant.PAYMENT_QUEUE_FILTER.getQuery());
		String paymentOutFilterQuery =  paymentOutFilter.getQuery();
		
		String userJoin = "";
		if(paymentOutFilterQuery.contains(PayOutQueDBColumns.REGOWNER.getName()))
		{
			userJoin = PaymentOutQueryConstant.USER_FILTER_JOIN.getQuery();
		}
		if(contactFilter.isFilterAppliedForAccountAndContact() && accountJoin.length() > 1 ){
			paymentOutFilterQuery = addFilterToQuery(paymentOutFilterQuery);
				
		}else{
			contactJoin = contactJoin.replace("LEFT", "");
			accountJoin = accountJoin.replace("LEFT", "");
		}
		
		paymentOutFilterQuery =  paymentOutFilterQuery.replace("{CONTACT_FILTER_JOIN}", contactJoin)
				.replace("{ACCOUNT_FILTER_JOIN}", accountJoin).replace("{PAYMENT_TABLE_JOIN}", paymentJoin)
				.replace("{ORG_JOIN}", orgJoin).replace("{USER_JOIN}", userJoin).replace("{LEGAL_ENTITY_JOIN}", legalEntityJoin);
		
		return paymentOutFilterQuery;
	}


	private String addFilterToQuery(String paymentOutFilter) {
		String paymentOutFilterQuery;
		
		if(paymentOutFilter.contains(WHERE)){
			paymentOutFilterQuery = paymentOutFilter+" AND "+ PaymentOutQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}else{
			paymentOutFilterQuery = paymentOutFilter+" WHERE "+ PaymentOutQueryConstant.REPORT_FILTER_NULL_CHECK.getQuery();
		}
		return paymentOutFilterQuery;
	}

	private String getWatchListCategoryFilter(PaymentOutSearchCriteria payOutSearchCriteria) {
		String watchlistCategoryResult = null;
		boolean hasAllPermission = false;
		if (payOutSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory1()) {
			watchlistCategoryResult = WatchListCategoryEnum.CATEGORY_1.getCategoryDbStatus().toString();
		}
		if (payOutSearchCriteria.getFilter().getUserProfile().getPermissions().getCanManageWatchListCategory2()) {
			if (null != watchlistCategoryResult)
				hasAllPermission = true;
			else
				watchlistCategoryResult = WatchListCategoryEnum.CATEGORY_2.getCategoryDbStatus().toString();

		}
		if (hasAllPermission)
			watchlistCategoryResult = null;
		else{
			if (null != watchlistCategoryResult)
				watchlistCategoryResult = PaymentOutQueryConstant.WATCHLIST_CATEGORY_FILTER.getQuery().replace("?", watchlistCategoryResult);
		}

		return watchlistCategoryResult;
	}


	/**
	 * Sets the page and other details.
	 *
	 * @param searchCriteria the search criteria
	 * @param paymentOutQueueDto the payment out queue dto
	 * @param connection the connection
	 * @param minRowNum the min row num
	 * @param pageSize the page size
	 * @param totalRecords the total records
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void setPageAndOtherDetails(PaymentOutSearchCriteria searchCriteria,
			PaymentOutQueueDto paymentOutQueueDto, Connection connection, int minRowNum, Integer pageSize,
			Integer totalRecords) throws CompliancePortalException {
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
		//added by neelesh pant
		page.setCurrentPage(SearchCriteriaUtil.getCurrentPage(searchCriteria));
		page.setMaxRecord(minRowNum + paymentOutQueueDto.getPaymentOutQueue().size() - 1);
		page.setPageSize(pageSize);
		page.setTotalPages(totalPages);
		page.setTotalRecords(totalRecords);
		paymentOutQueueDto.setPage(page);
		List<String> organization = getOrganization(connection);
		paymentOutQueueDto.setOrganization(organization);
		List<String> currency = getCurrency(connection);
		paymentOutQueueDto.setCurrency(currency);

		List<String> countryofBeneficiary = getAllCountries(connection);
		paymentOutQueueDto.setCountry(countryofBeneficiary);
		List<String> legalEntity = getLegalEntity(connection);
		paymentOutQueueDto.setLegalEntity(legalEntity);
		searchCriteria.setPage(paymentOutQueueDto.getPage());
		paymentOutQueueDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
	}

	/**
	 * Builds the payment attribute filter query.
	 *
	 * @param searchCriteria the search criteria
	 * @return the string
	 */
	private String buildPaymentAttributeFilterQuery(PaymentOutSearchCriteria searchCriteria) {
		FilterQueryBuilder paymentFilter = new PaymentOutPaymentFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutQueryConstant.PAYMENT_OUT_QUEUE_PAYMENT_FILTER.getQuery(), true);

		String countryJoin = "";
		if (null != searchCriteria.getFilter().getCountryOfBeneficiary()
				&& searchCriteria.getFilter().getCountryOfBeneficiary().length > 0) {
			countryJoin = PaymentOutQueryConstant.COUNTRY_FILTER_JOIN.getQuery();
		}
		return paymentFilter.getQuery().replace("{COUNTRY_JOIN}", countryJoin);
	}

	/**
	 * Builds the account filter query.
	 *
	 * @param searchCriteria the search criteria
	 * @return the string
	 */
	private String buildAccountFilterQuery(PaymentOutSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new PaymenOutReportAccountFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutQueryConstant.PAYMENT_OUT_QUEUE_ACCOUNT_FILTER.getQuery(),
				 true);
		return accountFilter.getQuery();
		}

	/**
	 * Builds the contact filter query.
	 *
	 * @param searchCriteria the search criteria
	 * @return the string
	 */
	private String buildContactFilterQuery(PaymentOutSearchCriteria searchCriteria) {
		PaymentOutReportContactFilterQueryBuilder contactFilter = new PaymentOutReportContactFilterQueryBuilder(searchCriteria.getFilter(),
				PaymentOutQueryConstant.PAYMENT_OUT_QUEUE_CONTACT_FILTER.getQuery(),
				 true);
		String countryJoin = "";
		String contactFilterQuery = contactFilter.getQuery();
		String watchListCategoryFilter = getWatchListCategoryFilter(searchCriteria);
		if(null!= watchListCategoryFilter ){
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", watchListCategoryFilter)  ;
		}else{
			contactFilterQuery = contactFilterQuery.replace("{WATCHLIST_CATEGORY_FILTER}", "")  ;
		}
		if(contactFilter.isCountryOfResidenceFilterApplied()){
			countryJoin = PaymentOutQueryConstant.PAYMENTOUT_QUEUE_COUNTRY_OF_RESIDENCE_FILTER.getQuery();
		}
		return contactFilterQuery.replace("{PAYMENTOUT_QUEUE_COUNTRY_OF_RESIDENCE_FILTER}", countryJoin);
	}

	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IDBService#getActivityLogs(com.currenciesdirect.gtg.compliance.core.domain.ActivityLogRequest)
	 */
	@Override
	public ActivityLogs getActivityLogs(ActivityLogRequest payOutActivityLogrequest) throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityLogDataWrapperList = new ArrayList<>();
		activityLogs.setActivityLogData(activityLogDataWrapperList);
		PreparedStatement prepareStatement = null;
		Connection connection = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			prepareStatement = connection.prepareStatement(PaymentOutQueryConstant.GET_PAYMENTOUT_ACTIVITY_LOGS_BY_ROWS.getQuery());
			prepareStatement.setInt(1, payOutActivityLogrequest.getEntityId());
			prepareStatement.setInt(2, payOutActivityLogrequest.getMinRecord()-1);
			prepareStatement.setInt(3, payOutActivityLogrequest.getRowToFetch());
			rs = prepareStatement.executeQuery();
			while (rs.next()) {
				ActivityLogDataWrapper activityLogData = new ActivityLogDataWrapper();
				activityLogData.setActivity(rs.getString("Activity"));
				activityLogData.setActivityType(ActivityType.getActivityLogDisplay(rs.getString("ActivityType")));
				activityLogData.setCreatedBy(rs.getString("User"));
				Timestamp createdOn = rs.getTimestamp("CreatedOn");
				activityLogData.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogData.setComment(rs.getString("Comment"));
				activityLogDataWrapperList.add(activityLogData);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
				closeResultset(rs);
				closePrepareStatement(prepareStatement);
				closeConnection(connection);
		}
		return activityLogs;
	}
	
	/**
	 * Gets the payment out status.
	 *
	 * @param paymentOutId the payment out id
	 * @param connection the connection
	 * @return the payment out status
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private Status getPaymentOutStatus(Integer paymentOutId, Connection connection)
			throws CompliancePortalException {
		PreparedStatement prepareStmt = null;
		ResultSet rs = null;
		Status status = new Status();
		List<StatusData> statusDatas = new ArrayList<>();
		status.setStatuses(statusDatas);
		try {
			prepareStmt = connection.prepareStatement(PaymentOutQueryConstant.GET_PAYMENTOUT_STATUS_WITH_OHTER_STATUS.getQuery());
			prepareStmt.setInt(1, paymentOutId);
			rs = prepareStmt.executeQuery();
			while (rs.next()) {
				StatusData dataStatus = new StatusData();
				dataStatus.setStatus(rs.getString("Status"));
				if (rs.getInt("Id") == paymentOutId) {
					dataStatus.setIsSelected(Boolean.TRUE);
				} else {
					dataStatus.setIsSelected(Boolean.FALSE);
				}

				statusDatas.add(dataStatus);
			}
			return status;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(prepareStmt);
		}

	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#updatePaymentOut(com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutUpdateDBDto)
	 */
	@Override
	public ActivityLogs updatePaymentOut(PaymentOutUpdateDBDto paymentOutUpdateDBDto) throws CompliancePortalException {
		Connection connection = null;
		Connection readOnlyConn = null;
		
		try {
			connection = getConnection(Boolean.FALSE);
			readOnlyConn = getConnection(Boolean.TRUE);
			
			beginTransaction(connection);
			paymentOutUpdateDBDto.setActivityLog(new ArrayList<ProfileActivityLogDto>());
			getWatchListStatus(paymentOutUpdateDBDto, connection);
			addWatchlist(paymentOutUpdateDBDto, connection);
			addWatchlistLog(paymentOutUpdateDBDto);
			deleteWatchlist(paymentOutUpdateDBDto, connection);
			updateWatchlistStatus(paymentOutUpdateDBDto,connection);
			addProfileActivityLogs(paymentOutUpdateDBDto,connection);   // call is given only to add logs of watchlist

			deleteReasons(paymentOutUpdateDBDto, connection);
			addReasons(paymentOutUpdateDBDto, connection);
			addActivityLogs(paymentOutUpdateDBDto, connection);
			String paymentOutStatus = paymentOutUpdateDBDto.getPaymentOutStatus();
			String paymentOutStatusReason=getResponseStatus(paymentOutUpdateDBDto);
			
			if (paymentOutStatus != null && !paymentOutStatus.isEmpty()) {
				updatePaymentOutStatus(paymentOutStatus, paymentOutUpdateDBDto, paymentOutUpdateDBDto.getCreatedBy(),connection);
				if(Constants.CLEAR.equalsIgnoreCase(paymentOutStatus) || Constants.SEIZE.equalsIgnoreCase(paymentOutStatus) 
						|| Constants.REJECT.equalsIgnoreCase(paymentOutStatus)){
					updateWorkFlowTime(paymentOutUpdateDBDto.getUserResourceId(), connection);
				}
				saveIntoBroadcastQueue(getBroadCastDBObject(paymentOutUpdateDBDto.getAccountId(),
						paymentOutUpdateDBDto.getPaymentOutId(),paymentOutUpdateDBDto.getCreatedBy(),
						getPaymentOutResponse(getPaymentComplianceStatus(paymentOutStatus),paymentOutUpdateDBDto.getTradeContractnumber(),
								paymentOutUpdateDBDto.getTradePaymentid(),paymentOutUpdateDBDto.getOrgCode(),paymentOutStatusReason),
								paymentOutUpdateDBDto.getOrgCode(),paymentOutUpdateDBDto.getContactId()),connection);
				
			}else if(Boolean.FALSE.equals(StringUtils.isNullOrEmpty(paymentOutUpdateDBDto.getComment())) 
					&& Boolean.TRUE.equals(paymentOutUpdateDBDto.getIsRequestFromQueue())){
				updatePaymentOutIsOnQueueStatus(paymentOutUpdateDBDto, connection);
			} 
			commitTransaction(connection);

		} catch (Exception e) {
			transactionRolledBack(connection);
			throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
		} finally {
			closeConnection(connection);
			closeConnection(readOnlyConn);
		}
		return paymentOutActivityLogTransformer.transform(paymentOutUpdateDBDto);
	}
	
	/**
	 * Update payment out is on queue status.
	 *
	 * @author abhijeetg
	 * @param paymentOutUpdateDBDto the payment out update DB dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void updatePaymentOutIsOnQueueStatus(PaymentOutUpdateDBDto paymentOutUpdateDBDto, Connection connection) throws CompliancePortalException {

		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(PaymentOutQueryConstant.UPDATE_PAYMENT_OUT_IS_ON_QUEUE_STATUS.getQuery());
			stmt.setString(1, paymentOutUpdateDBDto.getCreatedBy());
			stmt.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			stmt.setBoolean(3, paymentOutUpdateDBDto.getIsPaymentOnQueue());
			stmt.setInt(4, paymentOutUpdateDBDto.getPaymentOutId());
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


	/**
	 * Adds the watchlist.
	 *
	 * @param paymentOutUpdateDBDto the payment out update DB dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void addWatchlist(PaymentOutUpdateDBDto paymentOutUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		List<String> addWatchlist = paymentOutUpdateDBDto.getAddWatchlist();
		if (addWatchlist != null && !addWatchlist.isEmpty()) {
			//changes for AT-2986
			if(paymentOutUpdateDBDto.getCustType().equals("PFX")){
				insertWatchlistOnPFX(paymentOutUpdateDBDto, connection);
			}else{
				insertWatchlist(paymentOutUpdateDBDto, connection);
			}
		}
	}

	@SuppressWarnings("resource")
	private void insertWatchlistOnPFX(PaymentOutUpdateDBDto paymentOutUpdateDBDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement stmt = null;
		try {
			stmt = connection.prepareStatement(RegistrationQueryConstant.INSERT_CONTACT_WATCHLIST.getQuery());
			for (String addWatchlistStr : paymentOutUpdateDBDto.getAddWatchlist()) {
				stmt.setString(1, addWatchlistStr);
				stmt.setInt(2, paymentOutUpdateDBDto.getContactId());
				stmt.setString(3, paymentOutUpdateDBDto.getCreatedBy());
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
	
	/**
	 * Insert watchlist.
	 *
	 * @param paymentOutUpdateDBDto the payment out update DB dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	private void insertWatchlist(PaymentOutUpdateDBDto paymentOutUpdateDBDto, Connection con)
			throws CompliancePortalException {
		PreparedStatement prepareStmt = null;
		try {
			prepareStmt = con.prepareStatement(RegistrationCfxDBQueryConstant.INSERT_WATCHLIST_BY_ACCOUNT_ID.getQuery());
			for (String addWatchlistStr : paymentOutUpdateDBDto.getAddWatchlist()) {
				prepareStmt.setString(1, addWatchlistStr);
				prepareStmt.setString(2, paymentOutUpdateDBDto.getCreatedBy());
				prepareStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
				prepareStmt.setInt(4, paymentOutUpdateDBDto.getAccountId());
				prepareStmt.addBatch();
			}
			int[] count = prepareStmt.executeBatch();
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
			closePrepareStatement(prepareStmt);
		}
	}
	
	
	/**
	 * Gets the payment out response.
	 *
	 * @param paymentOutComplianceStatus the payment out compliance status
	 * @param tradeContractnumber the trade contractnumber
	 * @param tradePaymentid the trade paymentid
	 * @param orgCode the org code
	 * @param responseDescription the response description
	 * @return the payment out response
	 */
	private String getPaymentOutResponse(PaymentComplianceStatus paymentOutComplianceStatus, String tradeContractnumber,
			String tradePaymentid, String orgCode, String responseDescription) {

		PaymentOutResponse paymentOutResponse = new PaymentOutResponse();
		paymentOutResponse.setOrgCode(orgCode);
		paymentOutResponse.setOsrID(UUID.randomUUID().toString());
		paymentOutResponse.setTradeContractNumber(tradeContractnumber);
		paymentOutResponse.setTradePaymentID(Integer.valueOf(tradePaymentid));
		paymentOutResponse.setStatus(paymentOutComplianceStatus);
		paymentOutResponse.setResponseCode(paymentOutComplianceStatus.getCode());
		paymentOutResponse.setResponseDescription(responseDescription);
		return JsonConverterUtil.convertToJsonWithNull(paymentOutResponse);
	}

	/**
	 * Gets the broad cast DB object.
	 *
	 * @param accountId the account id
	 * @param paymentOutId the payment out id
	 * @param createdBy the created by
	 * @param statusJson the status json
	 * @param orgCode the org code
	 * @param contactId the contact id
	 * @return the broad cast DB object
	 */
	private BroadcastEventToDB getBroadCastDBObject(Integer accountId, Integer paymentOutId, String createdBy,
			String statusJson, String orgCode,Integer contactId) {
		BroadcastEventToDB db = new BroadcastEventToDB();
		db.setAccountId(accountId);
		db.setContactId(contactId);
		db.setPaymentOutId(paymentOutId);
		db.setCreatedBy(createdBy);
		db.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveredOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliverOn(new Timestamp(System.currentTimeMillis()));
		db.setDeliveryStatus(BroadCastStatusEnum.NEW.getBroadCastStatusAsString());
		db.setEntityType(BroadCastEntityTypeEnum.PAYMENTOUT.getBroadCastEntityTypeAsString());
		db.setOrgCode(orgCode);
		db.setStatusJson(statusJson);
		return db;
	}

	/**
	 * Update payment out status.
	 *
	 * @param status the status
	 * @param paymentOutUpdateDBDto the payment out update DB dto
	 * @param user the user
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void updatePaymentOutStatus(String status, PaymentOutUpdateDBDto payOutUpdateDBDto, String user, Connection connection)
			throws CompliancePortalException {

		PreparedStatement prepareStmt = null;
		try {
			prepareStmt = connection.prepareStatement(PaymentOutQueryConstant.UPDATE_PAYMENT_OUT_STATUS.getQuery());
			prepareStmt.setString(1, status);
			prepareStmt.setString(2, user);
			prepareStmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			prepareStmt.setBoolean(4, payOutUpdateDBDto.getIsPaymentOnQueue());	
			prepareStmt.setInt(5, payOutUpdateDBDto.getPaymentOutId());
			int count = prepareStmt.executeUpdate();
			if (count == 0) {
				throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(prepareStmt);
		}

	}
	
	/**
	 * Force clear payment out.
	 *
	 * @param paymentOutFailedList the payment out failed list
	 * @param activityLogs the activity logs
	 * @param user the user
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	public void forceClearPaymentOut(PaymentOutRecheckFailureDetails entry,
	            PaymentOutActivityLogDto activityLog, UserProfile user )
      throws CompliancePortalException {
    Connection connection = null;
    try {
      connection = getConnection(Boolean.FALSE);
      
      PaymentOutUpdateDBDto payOutUpdateDBDto = new PaymentOutUpdateDBDto();
        payOutUpdateDBDto.setPaymentOutId(entry.getPaymentOutId());
        payOutUpdateDBDto.setIsPaymentOnQueue(Boolean.FALSE);
        updatePaymentOutStatus(PaymentComplianceStatus.CLEAR.name(), payOutUpdateDBDto, user.getName(), connection);
        
        saveIntoBroadcastQueue(getBroadCastDBObject(entry.getAccountId(), entry.getPaymentOutId(),user.getName(),
            getPaymentOutResponse(PaymentComplianceStatus.CLEAR,entry.getTradeContractNumber(),
                entry.getTradePaymentId().toString(),entry.getOrgCode(),"Bulk Cleared Payment"),
                entry.getOrgCode(),entry.getContactId()),connection);
      
      if (null != activityLog ) {
        List<PaymentOutActivityLogDto> activityLogs = new ArrayList<>();
        activityLogs.add(activityLog);
        insertPaymentOutActivityLogs(activityLogs, connection);
        insertPaymentOutActivityLogDetail(activityLogs, connection);
      }
    } catch (Exception e) {
      transactionRolledBack(connection);
      throw new CompliancePortalException(CompliancePortalErrors.DATABASE_GENERIC_ERROR, e);
    } finally {
      closeConnection(connection);
    }

  }
	
	/**
	 * Adds the activity logs.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void addActivityLogs(PaymentOutUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		List<PaymentOutActivityLogDto> activityLogs = requestHandlerDto.getActivityLogs();
		if (activityLogs != null && !activityLogs.isEmpty()) {
			insertPaymentOutActivityLogs(activityLogs, connection);
			insertPaymentOutActivityLogDetail(activityLogs, connection);
		}
	}
	
	/**
	 * Insert payment out activity logs.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentOutActivityLogs(List<PaymentOutActivityLogDto> activityLogDtos, Connection connection)
			throws CompliancePortalException {
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(PaymentOutQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG.getQuery(),
					Statement.RETURN_GENERATED_KEYS);
			for (PaymentOutActivityLogDto payOutActivityLog : activityLogDtos) {
				statement.setInt(1, payOutActivityLog.getPaymentOutId());
				statement.setNString(2, payOutActivityLog.getActivityBy());
				statement.setTimestamp(3, payOutActivityLog.getTimeStatmp());
				statement.setNString(4, payOutActivityLog.getComment());
				statement.setNString(5, payOutActivityLog.getCreatedBy());
				statement.setTimestamp(6, payOutActivityLog.getCreatedOn());
				statement.setInt(7, payOutActivityLog.getPaymentOutId());
				int updateCount = statement.executeUpdate();
				if (updateCount == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
				ResultSet rs = statement.getGeneratedKeys();
				if (rs.next()) {
					payOutActivityLog.setId(rs.getInt(1));
					payOutActivityLog.getActivityLogDetailDto().setActivityId(rs.getInt(1));
				}
			}

		} catch (CompliancePortalException e) {
			throw e;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(statement);
		}
	}

	/**
	 * Insert payment out activity log detail.
	 *
	 * @param activityLogDtos the activity log dtos
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentOutActivityLogDetail(List<PaymentOutActivityLogDto> activityLogDtos, Connection connection)
			throws CompliancePortalException {
		PreparedStatement stmt = null;
		try {
			stmt = connection
					.prepareStatement(PaymentOutQueryConstant.INSERT_PAYMENT_OUT_ACTIVITY_LOG_DETAIL.getQuery());
			for (PaymentOutActivityLogDto activityLog : activityLogDtos) {
				PaymentOutActivityLogDetailDto payOutActivityLogDetailDto = activityLog.getActivityLogDetailDto();
				stmt.setInt(1, payOutActivityLogDetailDto.getActivityId());
				stmt.setString(2, payOutActivityLogDetailDto.getActivityType().getModule());
				stmt.setString(3, payOutActivityLogDetailDto.getActivityType().getType());
				stmt.setNString(4, payOutActivityLogDetailDto.getLog());
				stmt.setNString(5, payOutActivityLogDetailDto.getCreatedBy());
				stmt.setTimestamp(6, payOutActivityLogDetailDto.getCreatedOn());
				stmt.addBatch();
			}
			int[] count = stmt.executeBatch();
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
			closePrepareStatement(stmt);
		}
	}
	
	
	
	
	/**
	 * Delete reasons.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void deleteReasons(PaymentOutUpdateDBDto requestHandlerDto, Connection connection)
			throws CompliancePortalException {
		Integer paymentOutId = requestHandlerDto.getPaymentOutId();
		List<String> delReasons = requestHandlerDto.getDeletedReasons();
		if (delReasons != null && !delReasons.isEmpty()) {
			String[] delReasonsArray = delReasons.toArray(new String[delReasons.size()]);
			CriteriaBuilder builder = new CriteriaBuilder(RegistrationQueryConstant.GET_STATUS_REASON_ID.getQuery());
			String query = builder.addCriteria("Module", new String[]{"PAYMENT_OUT","ALL"}, "AND",ClauseType.IN)
					.addCriteria("Reason", delReasonsArray, "AND", ClauseType.IN).build();
			query = "DELETE [PaymentOutStatusReason] WHERE PaymentOutId=" + paymentOutId + " AND StatusUpdateReasonID IN ("
					+ query + ")";
			deletePaymentReasonOrWatchlist(query, connection);
		}
	}	
	
	/**
	 * Adds the reasons.
	 *
	 * @param requestHandlerDto the request handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private void addReasons(PaymentOutUpdateDBDto requestHandlerDto, Connection connection) throws CompliancePortalException {
		List<String> addReasons = requestHandlerDto.getAddReasons();
		if (addReasons != null && !addReasons.isEmpty()) {
			insertPaymentOutReason(requestHandlerDto, connection);
		}
	}
	
	/**
	 * Insert payment out reason.
	 *
	 * @param regReqHandlerDto the reg req handler dto
	 * @param connection the connection
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	private void insertPaymentOutReason(PaymentOutUpdateDBDto payOutUpdateDBDDto, Connection connection)
			throws CompliancePortalException {
		PreparedStatement prepareStmt = null;
		try {
			prepareStmt = connection.prepareStatement(PaymentOutQueryConstant.INSERT_PAYMENT_OUT_REASON.getQuery());
			for (String reasons : payOutUpdateDBDDto.getAddReasons()) {
				prepareStmt.setInt(1, payOutUpdateDBDDto.getPaymentOutId());
				prepareStmt.setString(2, reasons);
				prepareStmt.setString(3, payOutUpdateDBDDto.getCreatedBy());
				prepareStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
				prepareStmt.setString(5, payOutUpdateDBDDto.getCreatedBy());
				prepareStmt.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
				int addRowCnt = prepareStmt.executeUpdate();
				if (addRowCnt == 0) {
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
			}

		} catch (CompliancePortalException exception) {
			throw exception;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		} finally {
			closePrepareStatement(prepareStmt);
		}
	}
	
	/**
	 * Gets the activity logs.
	 *
	 * @param paymentOutId the payment out id
	 * @param connection the connection
	 * @return the activity logs
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private ActivityLogs getActivityLogs(Integer paymentOutId, Connection connection)
			throws CompliancePortalException {
		ActivityLogs activityLogs = new ActivityLogs();
		List<ActivityLogDataWrapper> activityDataLogList = new ArrayList<>();
		activityLogs.setActivityLogData(activityDataLogList);
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			stmt = connection.prepareStatement(PaymentOutQueryConstant.GET_ACTIVITY_LOGS_OF_PAYMENT_OUT.getQuery());
			stmt.setInt(1, paymentOutId);
			rs = stmt.executeQuery();
			while (rs.next()) {
				ActivityLogDataWrapper activityLogDataForPayOut = new ActivityLogDataWrapper();
				activityLogDataForPayOut.setActivity(rs.getString("Activity"));
				activityLogDataForPayOut.setActivityType(ActivityType.getActivityLogDisplay(rs.getString("ActivityType")));
				activityLogDataForPayOut.setCreatedBy(rs.getString("User"));
				Timestamp createdOn = rs.getTimestamp("CreatedOn");
				activityLogDataForPayOut.setCreatedOn(createdOn != null ? DateTimeFormatter.dateTimeFormatter(createdOn) : null);
				activityLogDataForPayOut.setComment(rs.getString("Comment"));
				activityDataLogList.add(activityLogDataForPayOut);
			}

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(stmt);
		}
		return activityLogs;

	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getMoreKycDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getMoreKycDetails(PaymentOutViewMoreRequest payOutViewMoreRequest)
			throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement prepareStmt = null;
		ResultSet rs = null;
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		try {
			connection = getConnection(Boolean.TRUE);
			prepareStmt = connection
					.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_REGISTRATION_DETAILS.getQuery());
			prepareStmt.setString(1, payOutViewMoreRequest.getServiceType());
			prepareStmt.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(payOutViewMoreRequest.getEntityType()));
			prepareStmt.setInt(3, payOutViewMoreRequest.getEntityId());
			prepareStmt.setInt(4, payOutViewMoreRequest.getMinViewRecord());
			prepareStmt.setInt(5, payOutViewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			rs = prepareStmt.executeQuery();
			domainList = getMoreKycDetails(rs);

			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(prepareStmt);
			closeConnection(connection);
		}

	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getMoreSanctionDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getMoreSanctionDetails(PaymentOutViewMoreRequest payOutViewMoreRequest) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preStmt = connection
					.prepareStatement(PaymentOutQueryConstant.GET_VIEWMORE_PAYMENTOUT_DETAILS.getQuery());
			preStmt.setString(1, payOutViewMoreRequest.getServiceType());
			preStmt.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(payOutViewMoreRequest.getEntityType()));
			preStmt.setInt(3, payOutViewMoreRequest.getPaymentOutId());
			preStmt.setInt(4, payOutViewMoreRequest.getMinViewRecord());
			preStmt.setInt(5, payOutViewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = preStmt.executeQuery();
			domainList = getMoreSanctionDetails(resultSet);
			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preStmt);
			closeConnection(connection);
		}

	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getMoreFraugsterDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getMoreFraugsterDetails(PaymentOutViewMoreRequest viewMoreRequest) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			statement = connection
					.prepareStatement(PaymentOutQueryConstant.GET_VIEWMORE_PAYMENTOUT_DETAILS.getQuery());
			statement.setString(1, viewMoreRequest.getServiceType());
			statement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequest.getEntityType()));
			statement.setInt(3, viewMoreRequest.getPaymentOutId());
			statement.setInt(4, viewMoreRequest.getMinViewRecord());
			statement.setInt(5, viewMoreRequest.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = statement.executeQuery();
			domainList = getMoreFraugsterDetails(resultSet);
			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getMoreCustomCheckDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getMoreCustomCheckDetails(PaymentOutViewMoreRequest viewMoreRequestForCustomCheck) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			prepareStatement = connection.prepareStatement(PaymentOutQueryConstant.GET_VIEWMORE_PAYMENTOUT_DETAILS.getQuery());
			prepareStatement.setString(1, viewMoreRequestForCustomCheck.getServiceType());
			prepareStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(viewMoreRequestForCustomCheck.getEntityType()));
			prepareStatement.setInt(3, viewMoreRequestForCustomCheck.getPaymentOutId());
			prepareStatement.setInt(4, viewMoreRequestForCustomCheck.getMinViewRecord());
			prepareStatement.setInt(5, viewMoreRequestForCustomCheck.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			rs = prepareStatement.executeQuery();
			domainList = getMoreCustomCheckDetailsForPayment(rs);
			viewMoreResponse.setServices(domainList);
			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getFurtherPaymentOutDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getFurtherPaymentOutDetails(PaymentOutViewMoreRequest payOutrequest) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<IDomain> domainList = new ArrayList<>();
		try{
			connection = getConnection(Boolean.TRUE);
			statement = connection.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_FURTHER_PAYMENT_OUT_DETAILS.getQuery());
			statement.setInt(1, payOutrequest.getAccountId());
			statement.setInt(2, payOutrequest.getMinViewRecord());
			statement.setInt(3, payOutrequest.getMaxViewRecord());
			resultSet = statement.executeQuery();
			domainList = getFurtherPaymentOutDetails(resultSet);
			viewMoreResponse.setServices(domainList);
			
		}catch(Exception e){
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA,e);
		}finally {
			closeResultset(resultSet);
			closePrepareStatement(statement);
			closeConnection(connection);
		}
		
		return viewMoreResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getViewMoreFurtherPaymentInDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getViewMoreFurtherPaymentInDetails(PaymentOutViewMoreRequest payOutViewMoreRequest) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement prepareStmt = null;
		ResultSet rs = null;
		List<IDomain> domainList = new ArrayList<>();
		try{
			connection = getConnection(Boolean.TRUE);
			prepareStmt = connection.prepareStatement(RegistrationQueryConstant.GET_VIEWMORE_FURTHER_PAYMENT_IN_DETAILS.getQuery());
			prepareStmt.setInt(1, payOutViewMoreRequest.getAccountId());
			prepareStmt.setInt(2, payOutViewMoreRequest.getMinViewRecord());
			prepareStmt.setInt(3, payOutViewMoreRequest.getMaxViewRecord());
			rs = prepareStmt.executeQuery();
			domainList = getViewMoreFurtherPaymentInDetails(rs);
			viewMoreResponse.setServices(domainList);
		}catch(Exception e){
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA,e);
		}finally {
			closeResultset(rs);
			closePrepareStatement(prepareStmt);
			closeConnection(connection);
		}
		
		return viewMoreResponse;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IPaymentOutDBService#getMoreCountryCheckDetails(com.currenciesdirect.gtg.compliance.core.domain.PaymentOutViewMoreRequest)
	 */
	@Override
	public ViewMoreResponse getMoreCountryCheckDetails(PaymentOutViewMoreRequest requestForViewMore) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			prepareStatement = connection
					.prepareStatement(PaymentOutQueryConstant.GET_VIEWMORE_PAYMENTOUT_DETAILS.getQuery());
			prepareStatement.setString(1, requestForViewMore.getServiceType());
			prepareStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(requestForViewMore.getEntityType()));
			prepareStatement.setInt(3, requestForViewMore.getPaymentOutId());
			prepareStatement.setInt(4, requestForViewMore.getMinViewRecord());
			prepareStatement.setInt(5, requestForViewMore.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = prepareStatement.executeQuery();
			domainList = getMoreCountryCheckDetails(resultSet);
			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
	}
	
	/**
	 * Gets the pagination details.
	 *
	 * @param paymentOutSearchCriteria the payment out search criteria
	 * @param connection the connection
	 * @return the pagination details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected PaginationDetails getPaginationDetails(PaymentOutSearchCriteria paymentOutSearchCriteria,
			Connection connection) throws CompliancePortalException {
		PaginationDetails paginationDetails = new PaginationDetails();
		try {
			if(paymentOutSearchCriteria.getFilter()!=null && !paymentOutSearchCriteria.getIsRequestFromReportPage()){
				
				FilterQueryBuilder queryBuilder = new PaymentOutFilterQueryBuilder(paymentOutSearchCriteria.getFilter(),
						PaymentOutQueryConstant.GET_PAYMENTOUT_QUEUE_INNER_QUERY_SELECT.getQuery());
				String query = queryBuilder.getQuery();
				query = PaymentOutQueryConstant.GET_PAYMENTOUT_QUEUE_FOR_PAGINATION_FILTER.getQuery().replace(INNER_QUERY, query);
				paginationDetails=getPaginationDetailQuery(connection,paymentOutSearchCriteria,query);
				
			}else if(Boolean.TRUE.equals(paymentOutSearchCriteria.getIsRequestFromReportPage())){
				
				FilterQueryBuilder queryBuilder = new PaymentOutFilterQueryBuilder(paymentOutSearchCriteria.getFilter(),
						PaymentOutReportQueryConstant.GET_PAYMENTOUT_REPORT_INNER_QUERY_SELECT.getQuery());
				String query = queryBuilder.getQuery();
				query = PaymentOutQueryConstant.GET_PAYMENTOUT_QUEUE_FOR_PAGINATION_FILTER.getQuery().replace(INNER_QUERY, query);
				paginationDetails=getPaginationDetailQuery(connection,paymentOutSearchCriteria,query);
				
			} else {
				paginationDetails=getPaginationDetailQuery(connection,paymentOutSearchCriteria,RegistrationQueryConstant.GET_PAGINATION_DETAILS.getQuery());
			}
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		return paginationDetails;
	}
	
	/**
	 * Gets the pagination detail query.
	 *
	 * @param connection the connection
	 * @param paymentOutSearchCriteria the payment out search criteria
	 * @param query the query
	 * @return the pagination detail query
	 * @throws CompliancePortalException the compliance portal exception
	 */
	protected PaginationDetails getPaginationDetailQuery(Connection connection, PaymentOutSearchCriteria paymentOutSearchCriteria,String query) 
			throws CompliancePortalException {
		
		PreparedStatement statement = null;
		ResultSet rs = null;
		PaginationDetails paginationDetails = new PaginationDetails();
		
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, 1);
			statement.setInt(2, paymentOutSearchCriteria.getPage().getCurrentRecord() - 1);
			statement.setInt(3, paymentOutSearchCriteria.getPage().getCurrentRecord() + 1);
			statement.setInt(4, paymentOutSearchCriteria.getPage().getTotalRecords());
			
			rs = statement.executeQuery();

			while (rs.next()) {
				if (paymentOutSearchCriteria.getPage().getCurrentRecord() - 1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setPrevRecord(getPaginationData(rs.getString(Constants.ACCOUNT_ID),rs.getString("type"),rs.getString(Constants.PAYMENT_OUT_ID)));
					
				} 
				if (paymentOutSearchCriteria.getPage().getCurrentRecord() + 1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setNextRecord(getPaginationData(rs.getString(Constants.ACCOUNT_ID),rs.getString("type"),rs.getString(Constants.PAYMENT_OUT_ID)));
				
				} 
				if (paymentOutSearchCriteria.getPage().getTotalRecords() == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setTotalRecords(getPaginationData(rs.getString(Constants.ACCOUNT_ID),rs.getString("type"),rs.getString(Constants.PAYMENT_OUT_ID)));
				} 
				if(1 == rs.getInt(Constants.ROWNUM)) {
					paginationDetails.setFirstRecord(getPaginationData(rs.getString(Constants.ACCOUNT_ID),rs.getString("type"),rs.getString(Constants.PAYMENT_OUT_ID)));
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


	@Override
	public boolean setPoiExistsFlagForPaymentOut(UserProfile user, Contact contact) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = getConnection(Boolean.TRUE);
			//update contact table to set POI Exists Flag
			preparedStatement = connection.prepareStatement(PaymentOutQueryConstant.UPDATE_CONTACT_POI_EXISTS_FLAG.getQuery());	
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
	
	//Added for AT-3658
	@Override
	public ViewMoreResponse getMorePaymentReferenceCheckDetails(PaymentOutViewMoreRequest requestForViewMore) throws CompliancePortalException {
		ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;
		try {
			connection = getConnection(Boolean.TRUE);
			prepareStatement = connection
					.prepareStatement(PaymentOutQueryConstant.GET_VIEWMORE_PAYMENTOUT_DETAILS.getQuery());
			prepareStatement.setString(1, requestForViewMore.getServiceType());
			prepareStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(requestForViewMore.getEntityType()));
			prepareStatement.setInt(3, requestForViewMore.getPaymentOutId());
			prepareStatement.setInt(4, requestForViewMore.getMinViewRecord());
			prepareStatement.setInt(5, requestForViewMore.getMaxViewRecord());
			List<IDomain> domainList = new ArrayList<>();
			resultSet = prepareStatement.executeQuery();
			domainList = getMorePaymentReferenceCheckDetails(resultSet);
			viewMoreResponse.setServices(domainList);

			return viewMoreResponse;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
	}
	
	//Added for AT-4306
		@Override
		public ViewMoreResponse getMoreIntuitionCheckDetails(PaymentOutViewMoreRequest requestForViewMore) throws CompliancePortalException {
			ViewMoreResponse viewMoreResponse = new ViewMoreResponse();
			Connection connection = null;
			PreparedStatement prepareStatement = null;
			ResultSet resultSet = null;
			try {
				connection = getConnection(Boolean.TRUE);
				prepareStatement = connection
						.prepareStatement(PaymentOutQueryConstant.GET_VIEWMORE_PAYMENTOUT_DETAILS.getQuery());
				prepareStatement.setString(1, requestForViewMore.getServiceType());
				prepareStatement.setInt(2, EntityTypeEnum.getDatabaseStatusFromUiStatus(requestForViewMore.getEntityType()));
				prepareStatement.setInt(3, requestForViewMore.getPaymentOutId());
				prepareStatement.setInt(4, requestForViewMore.getMinViewRecord());
				prepareStatement.setInt(5, requestForViewMore.getMaxViewRecord());
				List<IDomain> domainList = new ArrayList<>();
				resultSet = prepareStatement.executeQuery();
				domainList = getMoreIntuitionCheckDetails(resultSet);
				viewMoreResponse.setServices(domainList);

				return viewMoreResponse;
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(prepareStatement);
				closeConnection(connection);
			}
		}

}
