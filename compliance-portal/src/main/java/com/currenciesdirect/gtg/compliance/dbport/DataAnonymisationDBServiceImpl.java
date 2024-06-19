package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.IAnonymisationDBService;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.Sort;
import com.currenciesdirect.gtg.compliance.core.domain.SourceEnum;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonAccount;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonContact;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDataRequest;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationDto;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymizationServiceRequest;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.iam.core.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class DataAnonymisationDBServiceImpl.
 */
@SuppressWarnings("squid:S2095")
@Component("dataAnonymisationDBServiceImpl")
public class DataAnonymisationDBServiceImpl extends AbstractDBServiceAnonymisation implements IAnonymisationDBService {
	
	/** The anonymisation details transformer. */
	@Autowired
	@Qualifier("dataAnonymisationDetailsTransformer")
	private ITransform<DataAnonymisationDetailsDto, DataAnonymisationDetailsDBDto> anonymisationDetailsTransformer;
	
	/**
	 * Gets the data anonymisation with criteria.
	 *
	 * @param searchCriteria the search criteria
	 * @return the data anonymisation with criteria
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	public DataAnonymisationDto getDataAnonymisationWithCriteria(DataAnonymisationSearchCriteria searchCriteria)
			throws CompliancePortalException {
		int offSet = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
		int rowsToFetch = SearchCriteriaUtil.getPageSize();
		int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
			
		String accountFilterQuery = buildAccountFilterQuery(searchCriteria);
		
		String listQuery = DataAnonymisationQueryConstant.DATA_ANON_MAIN_QUERY.getQuery()
				.replace("{DATA_ANON_QUERY}", accountFilterQuery);
		
		listQuery = executeSort(searchCriteria.getFilter().getSort(),listQuery);
		
		DataAnonAccountFilterQueryBuilder accountFilter = new DataAnonAccountFilterQueryBuilder(searchCriteria.getFilter(),
				DataAnonymisationQueryConstant.DATA_ANON_COUNT.getQuery(),
				 true);
		
		String countQuery = accountFilter.getQuery();
		
		DataAnonymisationDto dataAnonymisationDto = new DataAnonymisationDto();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			dataAnonymisationDto= getDataAnonymisationData(connection, offSet, rowsToFetch, listQuery, countQuery);
			Integer pageSize = SearchCriteriaUtil.getPageSize();
			Integer totalRecords = dataAnonymisationDto.getTotalRecords();
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
				int tMaxRecord=minRowNum + dataAnonymisationDto.getDataAnonymisation().size() - 1;
				if(tMaxRecord<totalRecords)
					page.setMaxRecord(tMaxRecord);
				else
					page.setMaxRecord(totalRecords);
				page.setPageSize(pageSize);
				page.setTotalPages(totalPages);
				page.setTotalRecords(totalRecords);
				page.setCurrentPage(SearchCriteriaUtil.getCurrentPage(searchCriteria));
				dataAnonymisationDto.setPage(page);
				dataAnonymisationDto.setSource(SourceEnum.getSourceValues());
				List<String> owner = null;
				owner= getLockedUserName(connection);
				dataAnonymisationDto.setOwner(owner);	
				searchCriteria.setPage(dataAnonymisationDto.getPage());
				dataAnonymisationDto.setSearchCriteria(JsonConverterUtil.convertToJsonWithNull(searchCriteria));
				
				
			}
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeConnection(connection);
		}
		return dataAnonymisationDto;
	}
	
	/**
	 * Builds the account filter query.
	 *
	 * @param searchCriteria the search criteria
	 * @return the string
	 */
	private String buildAccountFilterQuery(DataAnonymisationSearchCriteria searchCriteria) {
		FilterQueryBuilder accountFilter = new DataAnonAccountFilterQueryBuilder(searchCriteria.getFilter(),
				DataAnonymisationQueryConstant.DATA_ANON_QUERY.getQuery(),
				 true);
		return accountFilter.getQuery();
	}
	
	
	/**
	 * Execute sort.
	 *
	 * @param sort the sort
	 * @param listQuery the list query
	 * @return the string
	 */
	private String executeSort(Sort sort, String listQuery) {
		String columnName = "dataanon.InitialApprovalDate";
		boolean isAsc = false;
		if(sort != null && sort.getIsAscend() != null){
			isAsc = sort.getIsAscend();
		}		
		return addSort(columnName, isAsc,listQuery);
	}
	
	/**
	 * Gets the data anonymisation data.
	 *
	 * @param connection the connection
	 * @param offSet the off set
	 * @param rowsToFetch the rows to fetch
	 * @param query the query
	 * @param countQuery the count query
	 * @return the data anonymisation data
	 * @throws CompliancePortalException the compliance portal exception
	 */
	private DataAnonymisationDto getDataAnonymisationData(Connection connection, int offSet, int rowsToFetch,
			String query, String countQuery) throws CompliancePortalException {
		DataAnonymisationDto dataAnonDto = new DataAnonymisationDto();
		List<DataAnonymisationDataRequest> dataAnonymisationDataList = new ArrayList<>();
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
				DataAnonymisationDataRequest dataAnonymisationData = new DataAnonymisationDataRequest();
				dataAnonymisationData.setContactId(listRs.getInt(DataAnonDBColumns.CONTACTID.getName()));
				dataAnonymisationData.setAccountId(listRs.getInt(DataAnonDBColumns.ACCOUNTID.getName()));
				dataAnonymisationData.setContactName(listRs.getString(DataAnonDBColumns.CONTACTNAME.getName()));
				dataAnonymisationData.setTradeAccountNum(listRs.getString(DataAnonDBColumns.TRADEACCOUNTNUM.getName()));
				Timestamp requestDate = listRs.getTimestamp(DataAnonDBColumns.INITIAL_APPROVALDATE.getName());
				if (requestDate != null) {
					dataAnonymisationData.setInitialApprovalDate(DateTimeFormatter.dateTimeFormatter(requestDate));
				}
				
				Timestamp approvedDate = listRs.getTimestamp(DataAnonDBColumns.FINAL_APPROVALDATE.getName());
				if (approvedDate != null) {
					dataAnonymisationData.setFinalApprovalDate(DateTimeFormatter.dateTimeFormatter(approvedDate));
				}
				dataAnonymisationData.setFinalApprovalBy(listRs.getString(DataAnonDBColumns.OWNER.getName()));
				dataAnonymisationData.setInitialApprovalBy( listRs.getString(DataAnonDBColumns.REGOWNER.getName()));
				dataAnonymisationData.setDataAnonymizationStatus(listRs.getInt(DataAnonDBColumns.DATAANONSTATUS.getName()));
				dataAnonymisationData.setSource(listRs.getString(DataAnonDBColumns.SOURCE.getName()));
				dataAnonymisationData.setType(CustomerTypeEnum.getUiStatusFromDatabaseStatus(listRs.getInt(DataAnonDBColumns.TYPE.getName())));
				dataAnonymisationData.setComplianceStatus(listRs.getString(DataAnonDBColumns.STATUS.getName()));
				dataAnonymisationData.setId(listRs.getInt(DataAnonDBColumns.ID.getName()));
				dataAnonymisationDataList.add(dataAnonymisationData);

			}
			countStatement = connection.prepareStatement(countQuery);
			countRs = countStatement.executeQuery();
			if(countRs.next())
				totalRecords= countRs.getInt(DataAnonDBColumns.TOTALROWS.getName());	
			dataAnonDto.setDataAnonymisation(dataAnonymisationDataList);
			dataAnonDto.setTotalRecords(totalRecords);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(listStatement);
			closeResultset(countRs);
			closePrepareStatement(countStatement);
		}
		return dataAnonDto;
	}
	
	/**
	 * Gets the anonymisation details.
	 *
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the anonymisation details
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@Override
	public DataAnonymizationServiceRequest getAnonymisationDetails(DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		Integer accountID = dataAnonymizeRequest.getAccountId();
		DataAnonymizationServiceRequest dataanonreq = new DataAnonymizationServiceRequest();
		DataAnonAccount account= new DataAnonAccount();
		List<DataAnonContact> list = new ArrayList<>();
		int count = 1;
		
		try {
			
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.GET_CONTACT_DETAILS_WITH_EVENT.getQuery());
			preparedStatement.setInt(1, accountID);
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				if(count == 1){
					dataanonreq.setOrgCode(rs.getString("Organization"));
					account.setTradeAccountNumber(rs.getString("TradeAccountNumber"));
					account.setTradeAccountID(rs.getInt("TradeAccountID"));
					account.setAccountSFID( rs.getString("CRMAccountID"));
					dataanonreq.setAccount(account);
				}
				DataAnonContact contact = new DataAnonContact();
				contact.setTradeContactId(rs.getInt("TradeContactID"));
				contact.setContactSfId(rs.getString("CRMContactID"));
				list.add(contact);
				count++;
			}
			account.setContact(list);
			dataanonreq.setAccount(account);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);

		}
		return dataanonreq;
	}
	
	/**
	 * Gets the data anonymize.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anonymize
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	public boolean getDataAnonymize(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException 
	{
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet rs = null;
	try{
		connection = getConnection(Boolean.FALSE);
		preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.GET_COUNT_FROM_ACCOUNTID.getQuery());
		preparedStatement.setInt(1,dataAnonymizeRequest.getAccountId());
		rs = preparedStatement.executeQuery();
			while(rs.next()){
				int count= rs.getInt("AnonCount");
				if(count!=0) {
	             	preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.UPDATE_ON_REINITIATE_DATA_ANONYMISATION.getQuery());
	     			preparedStatement.setString(1,dataAnonymizeRequest.getEnterComment());
	     			preparedStatement.setInt(2,user.getId());
	     			preparedStatement.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
	     			preparedStatement.setInt(4,dataAnonymizeRequest.getAccountId());
	     			preparedStatement.executeUpdate();
	     			saveIntoDataAnonHistory(user,dataAnonymizeRequest);
	             }
				else {
					preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.INSERT_INTO_DATA_ANONYMIZE.getQuery());
					preparedStatement.setInt(1,dataAnonymizeRequest.getAccountId());
					preparedStatement.setInt(2,dataAnonymizeRequest.getContactId());
					preparedStatement.setString(3,dataAnonymizeRequest.getEnterComment());
					preparedStatement.setInt(4,user.getId());
					preparedStatement.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
					preparedStatement.setInt(6,user.getId());
					preparedStatement.setTimestamp(7,new Timestamp(System.currentTimeMillis()));
					preparedStatement.setTimestamp(8,new Timestamp(System.currentTimeMillis()));
					boolean result = preparedStatement.execute();
					if(!result)
					{
						saveIntoDataAnonHistory(user,dataAnonymizeRequest);
					}
					return true;
				}
		    }
	}
	catch(Exception e) {
		throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
	}
	finally {
		closePrepareStatement(preparedStatement);
		closeConnection(connection);
		closeResultset(rs);
	}
	return true;
	}
		
	/**
	 * Save into data anon history.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	public boolean saveIntoDataAnonHistory(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException 
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try{
			connection = getConnection(Boolean.FALSE);
			//update account table on initial anonymisation
			preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.UPDATE_ACCOUNT_DATA_ANON.getQuery());	
			preparedStatement.setInt(1,dataAnonymizeRequest.getAccountId());
			preparedStatement.executeUpdate();
			
			preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.INSERT_INTO_DATA_ANON_ACTIVITY_LOG_HISTORY.getQuery());
			preparedStatement.setInt(1, dataAnonymizeRequest.getAccountId());
			preparedStatement.setString(2, "INITIALIZATION");
			preparedStatement.setString(3, dataAnonymizeRequest.getEnterComment());
			preparedStatement.setInt(4, user.getId());
			preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
			preparedStatement.executeUpdate();
		}
			catch(Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
			}
			finally {
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
		return true;
		}
	
	/**
	 * Update data anonymisation.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anonymisation responce
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	public boolean updateDataAnonymisation(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException 
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		try{
			connection = getConnection(Boolean.FALSE);
			preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.UPDATE_ON_DATA_ANON_ACTIVITY_LOG.getQuery());
			preparedStatement.setString(1,dataAnonymizeRequest.getEnterComment());
			preparedStatement.setInt(2,user.getId());
			preparedStatement.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
			preparedStatement.setInt(4,dataAnonymizeRequest.getAccountId());
			boolean result = preparedStatement.execute();
			if(!result)
			{
				preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.UPDATE_FINAL_ACCOUNT_DATA_ANON.getQuery());	
				preparedStatement.setInt(1,dataAnonymizeRequest.getAccountId());
				preparedStatement.executeUpdate();
				
				preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.INSERT_INTO_DATA_ANON_ACTIVITY_LOG_HISTORY.getQuery());
				preparedStatement.setInt(1, dataAnonymizeRequest.getAccountId());
				preparedStatement.setString(2, "APPROVAL");
				preparedStatement.setString(3, dataAnonymizeRequest.getEnterComment());
				preparedStatement.setInt(4, user.getId());
				preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				preparedStatement.executeUpdate();
			}
			return true;
		}
		catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		}
		finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}
	
	
	/**
	 * Cancel data anonymisation.
	 *
	 * @param user the user
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return true, if successful
	 * @throws CompliancePortalException the compliance portal exception
	 */
	@SuppressWarnings("resource")
	public boolean cancelDataAnonymisation(UserProfile user, DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException 
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	
		try{
		connection = getConnection(Boolean.FALSE);
		
		preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.UPDATE_ON_DATA_ANONYMIZE_REJECT.getQuery());
		preparedStatement.setString(1,dataAnonymizeRequest.getEnterComment());
		preparedStatement.setInt(2,user.getId());
		preparedStatement.setTimestamp(3,new Timestamp(System.currentTimeMillis()));
		preparedStatement.setInt(4,dataAnonymizeRequest.getAccountId());
		boolean result = preparedStatement.execute();
		if(!result)
		{
			
				preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.UPDATE_CANCEL_ACCOUNT_DATA_ANON.getQuery());	
				preparedStatement.setInt(1,dataAnonymizeRequest.getAccountId());
				preparedStatement.executeUpdate();
				
				preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.INSERT_INTO_DATA_ANON_ACTIVITY_LOG_HISTORY.getQuery());
				preparedStatement.setInt(1, dataAnonymizeRequest.getAccountId());
				preparedStatement.setString(2, "REJECTION");
				preparedStatement.setString(3, dataAnonymizeRequest.getEnterComment());
				preparedStatement.setInt(4, user.getId());
				preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
				preparedStatement.executeUpdate();
		}
			return true;
		}
		
		
		catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
		}
		finally {
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
	}
	
	/**
	 * Gets the data anon history.
	 *
	 * @param dataAnonymizeRequest the data anonymize request
	 * @return the data anon history
	 * @throws CompliancePortalException the compliance portal exception
	 */
	public DataAnonymisationDto getDataAnonHistory( DataAnonymisationDataRequest dataAnonymizeRequest) throws CompliancePortalException 
	{
        DataAnonymisationDto dataAnonDto = new DataAnonymisationDto();
		List<DataAnonymisationDataRequest> dataAnonymisationDataList = new ArrayList<>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
	    ResultSet listRs = null;
		try{
		connection = getConnection(Boolean.FALSE);
		preparedStatement = connection.prepareStatement(DataAnonymisationQueryConstant.SHOW_DATAANON_HISTORY.getQuery());	
                preparedStatement.setInt(1, dataAnonymizeRequest.getId());
				listRs = preparedStatement.executeQuery();
				while (listRs.next()) {
                            DataAnonymisationDataRequest dataAnonymisationData = new DataAnonymisationDataRequest();             
                            dataAnonymisationData.setActivity(listRs.getString(DataAnonDBColumns.ACTIVITY.getName()));
                            dataAnonymisationData.setActivityBy(listRs.getString(DataAnonDBColumns.ACTIVITYBY.getName()));
                            dataAnonymisationData.setComment(listRs.getString(DataAnonDBColumns.COMMENT.getName()));
                            Timestamp requestDate = listRs.getTimestamp(DataAnonDBColumns.ACTIVITYDATE.getName());
                				
                            if (requestDate != null) {
                            	dataAnonymisationData.setActivityDate(listRs.getString(DataAnonDBColumns.ACTIVITYDATE.getName()));
                            }
                            dataAnonymisationDataList.add(dataAnonymisationData);
				}
				dataAnonDto.setDataAnonymisation(dataAnonymisationDataList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(listRs);
			closePrepareStatement(preparedStatement);
			
		}
		return dataAnonDto;
	}
	
}
