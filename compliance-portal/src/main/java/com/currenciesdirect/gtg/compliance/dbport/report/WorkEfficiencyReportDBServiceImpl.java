package com.currenciesdirect.gtg.compliance.dbport.report;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.QueueType;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportData;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportDBService;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.WorkEfficiencySlaEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class WorkEfficiencyReportDBServiceImpl.
 */
@Component("workEfficiencyReportDBServiceImpl")
public class WorkEfficiencyReportDBServiceImpl extends AbstractDao implements IWorkEfficiencyReportDBService {

	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IWorkEfficiencyReportDBService#getWorkEfficiencyReport()
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReport(WorkEfficiencyReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		List<WorkEfficiencyReportData> workEfficiencyReportList = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
			int rowsToFetch = SearchCriteriaUtil.getPageSize();
			String fromTo = DateTimeFormatter.parseDate(searchCriteria.getFilter().getFromTo()); // currentDate
			String dateFrom = DateTimeFormatter.parseDate(searchCriteria.getFilter().getDateFrom()); // pastDate
			searchCriteria.getFilter().setQueueType(null);
			FilterQueryBuilder queryBuilder = new WorkEfficiencyReportFilterQueryBuilder(searchCriteria.getFilter(),
					WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_LOAD_INNER_QUERY.getQuery());
			String query = queryBuilder.getQuery();
			query = WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_LOAD.getQuery()
					.replace(Constants.INNER_QUERY, query);
			connection = getConnection(Boolean.TRUE);
			if (fromTo.compareTo(dateFrom) > 0 || fromTo.compareTo(dateFrom) == 0) {
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setInt(1, offset);
				prepareStatement.setInt(2, rowsToFetch);
				prepareStatement.setString(3, dateFrom);
				prepareStatement.setString(4, fromTo);
				resultSet = prepareStatement.executeQuery();
				// False means queueType is null which is set to "All"
				workEfficiencyReportList = getWorkEfficiencyData(resultSet);
				workEfficiencyReportDto.setWorkEfficiencyReportData(workEfficiencyReportList);

				Page page = getPaginationData(searchCriteria, workEfficiencyReportDto);
				workEfficiencyReportDto.setPage(page);
				List<String> userNameList = getUserNameList(connection);
				workEfficiencyReportDto.setUserNameList(userNameList);
				workEfficiencyReportDto.setQueueList(QueueType.getQueueTypeValues());

			} else {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_FROMTO_LESS_THAN_DATEFROM);
			}

		} catch (CompliancePortalException cp) {
			throw cp;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
		return workEfficiencyReportDto;
	}
	
	/**
	 * @param resultSet
	 * @param callFrom
	 * @return
	 * @throws SQLException
	 */
	public List<WorkEfficiencyReportData> getWorkEfficiencyData(ResultSet resultSet) throws SQLException {
		List<WorkEfficiencyReportData> workEfficiencyReportList = new ArrayList<>();
		
			while (resultSet.next()){
					WorkEfficiencyReportData workEfficiencyReportData = new WorkEfficiencyReportData();
					workEfficiencyReportData.setUserName(resultSet.getString("SSOUserID"));
					workEfficiencyReportData.setQueueType(UserLockResourceTypeEnum.getUiStatusFromDatabaseStatusWithUIFormat(resultSet.getInt("ResourceType")));
					workEfficiencyReportData.setAccountType(CustomerTypeEnum.getUiStatusFromDatabaseStatus(resultSet.getInt("AccType")));
					workEfficiencyReportData.setLockedRecords(resultSet.getInt("LockedRecords"));
					workEfficiencyReportData.setReleasedRecords(resultSet.getInt("ReleasedRecords"));
					Double minutes = (double) resultSet.getInt("seconds");
					DecimalFormat df = new DecimalFormat("#.##");
					//Double hours = Double.valueOf(df.format(minutes)); //Convert it into Hours
					workEfficiencyReportData.setSeconds(Double.valueOf(df.format(minutes/60)));
					
					workEfficiencyReportData.setSlaValue(SearchCriteriaUtil.getSlaPropertyValues(
							WorkEfficiencySlaEnum.getSlaPropertyFromSlaValue(workEfficiencyReportData.getAccountType()
							+"_"+workEfficiencyReportData.getQueueType())));
					
					if (workEfficiencyReportData.getSeconds() != null && workEfficiencyReportData.getSeconds() > 0 ){
						Double percentEfficiency =  (workEfficiencyReportData.getSlaValue()*100)/workEfficiencyReportData.getSeconds();
						workEfficiencyReportData.setPercentEfficiency(Double.valueOf(df.format(percentEfficiency)));
					}else {
						workEfficiencyReportData.setPercentEfficiency(0.00);
					}
					if (workEfficiencyReportData.getReleasedRecords().compareTo(0) > 0){
						Double timeEfficiency = workEfficiencyReportData.getSeconds()/workEfficiencyReportData.getReleasedRecords();
						timeEfficiency = Double.valueOf(df.format(timeEfficiency));
						workEfficiencyReportData.setTimeEfficiency(timeEfficiency);
					}else {
						workEfficiencyReportData.setTimeEfficiency(0.00);
					}
					workEfficiencyReportData.setTotalRows(resultSet.getInt("totalrows"));
			    workEfficiencyReportList.add(workEfficiencyReportData);
			}
		return workEfficiencyReportList;
	}

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IWorkEfficiencyReportDBService#getWorkEfficiencyReportWithCriteria(com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria)
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteria(WorkEfficiencyReportSearchCriteria searchCriteria) throws CompliancePortalException {
		
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		List<WorkEfficiencyReportData> workEfficiencyReportList = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {	
			     int offset = SearchCriteriaUtil.getPageOffsetRows(searchCriteria);
			     int rowsToFetch = SearchCriteriaUtil.getPageSize();
				 String fromTo = DateTimeFormatter.parseDate(searchCriteria.getFilter().getFromTo()); //pastdate
				 String dateFrom =DateTimeFormatter.parseDate(searchCriteria.getFilter().getDateFrom()); //currentDate
				 
				 FilterQueryBuilder queryBuilder = new WorkEfficiencyReportFilterQueryBuilder(searchCriteria.getFilter(),
						WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_LOAD_INNER_QUERY.getQuery());
				 String query = queryBuilder.getQuery();
			     query = WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_LOAD.getQuery().replace(Constants.INNER_QUERY, query);
				 connection = getConnection(Boolean.TRUE);
				 if(fromTo.compareTo(dateFrom) > 0 || fromTo.compareTo(dateFrom) == 0){
					prepareStatement = connection.prepareStatement(query);
					prepareStatement.setInt(1, offset);
					prepareStatement.setInt(2, rowsToFetch);
					prepareStatement.setString(3, dateFrom);
					prepareStatement.setString(4, fromTo);
					resultSet = prepareStatement.executeQuery();
					workEfficiencyReportList = getWorkEfficiencyData(resultSet);
					workEfficiencyReportDto.setWorkEfficiencyReportData(workEfficiencyReportList);
				
				  Page page = getPaginationData(searchCriteria,workEfficiencyReportDto);
				  workEfficiencyReportDto.setPage(page);
				  
				}else {
						throw new CompliancePortalException(CompliancePortalErrors.ERROR_FROMTO_LESS_THAN_DATEFROM);
			    }
		}
		catch (CompliancePortalException cp) {
			throw cp;
		}
		catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
	return workEfficiencyReportDto;
	}
	
	/**
	 * @param connection
	 * @param searchCriteria
	 * @param workEfficiencyReportDto
	 * @param countQuery
	 * @param callfrom
	 * @return
	 * @throws CompliancePortalException
	 */
	private Page getPaginationData(WorkEfficiencyReportSearchCriteria searchCriteria,
			WorkEfficiencyReportDto workEfficiencyReportDto) {
		Integer totalRows = 0;
		workEfficiencyReportDto.getWorkEfficiencyReportData();
		if(null != workEfficiencyReportDto.getWorkEfficiencyReportData() && 
				!workEfficiencyReportDto.getWorkEfficiencyReportData().isEmpty()){
			totalRows = workEfficiencyReportDto.getWorkEfficiencyReportData().get(0).getTotalRows();
		}
		Integer pageSize = SearchCriteriaUtil.getPageSize(); // maxRow
		int minRowNum = SearchCriteriaUtil.getMinRowNumber(searchCriteria);
		Integer totalRecords = totalRows;
		Integer totalPages = null;
		if (totalRecords != null) {
			totalPages = totalRecords / pageSize;
			if (totalRecords % pageSize != 0) {
				totalPages += 1;
			}
		}

		Page page = new Page();
		if (totalRecords == 0) {
			page.setMinRecord(0);
		} else {
			page.setMinRecord(minRowNum);
		}
		page.setMaxRecord(minRowNum + workEfficiencyReportDto.getWorkEfficiencyReportData().size() - 1);
		page.setTotalRecords(totalRecords);
		if (page.getTotalRecords() == 0) {
			page.setMinRecord(0);
		}
		page.setCurrentPage(1);
		page.setTotalPages(totalPages);
		page.setPageSize(pageSize);
		return page;
	}

	/**
	 * @param connection
	 * @param dateFrom
	 * @param fromTo
	 * @return
	 * @throws CompliancePortalException
	 */
	public List<String> getUserNameList(Connection connection) throws CompliancePortalException{
		
		List<String> userNameList = new ArrayList<>();
		PreparedStatement statement = null;
		ResultSet rs = null;
		
		try {
			statement = connection.prepareStatement(WorkEfficiencyReportQueryConstant.GET_USERNAME_FOR_WORK_EFFICIENCY_REPORT.getQuery());
			rs = statement.executeQuery();
			while (rs.next()) {
				userNameList.add(rs.getString("SSOUserID"));
			}
		}catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
				closeResultset(rs);
				closePrepareStatement(statement);
		}
		return userNameList;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportDBService#getWorkEfficiencyReportInExcelFormat(com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria)
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReportInExcelFormat(WorkEfficiencyReportSearchCriteria searchCriteria)
			throws CompliancePortalException {
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		List<WorkEfficiencyReportData> workEfficiencyReportList = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {
			String fromTo = DateTimeFormatter.parseDate(searchCriteria.getFilter().getFromTo()); // currentDate
			String dateFrom = DateTimeFormatter.parseDate(searchCriteria.getFilter().getDateFrom()); // pastDate
			searchCriteria.getFilter().setQueueType(null);
			FilterQueryBuilder queryBuilder = new WorkEfficiencyReportFilterQueryBuilder(searchCriteria.getFilter(),
					WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_DOWNLOAD_INNER_QUERY.getQuery());
			String query = queryBuilder.getQuery();
			query = WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_DOWNLOAD.getQuery()
					.replace(Constants.INNER_QUERY, query);
			connection = getConnection(Boolean.TRUE);
			if (fromTo.compareTo(dateFrom) > 0 || fromTo.compareTo(dateFrom) == 0) {
				prepareStatement = connection.prepareStatement(query);
				prepareStatement.setString(1, dateFrom);
				prepareStatement.setString(2, fromTo);
				resultSet = prepareStatement.executeQuery();
				// False means queueType is null which is set to "All"
				workEfficiencyReportList = getWorkEfficiencyData(resultSet);
				workEfficiencyReportDto.setWorkEfficiencyReportData(workEfficiencyReportList);

				Page page = getPaginationData(searchCriteria, workEfficiencyReportDto);
				workEfficiencyReportDto.setPage(page);
				List<String> userNameList = getUserNameList(connection);
				workEfficiencyReportDto.setUserNameList(userNameList);
				workEfficiencyReportDto.setQueueList(QueueType.getQueueTypeValues());

			} else {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_FROMTO_LESS_THAN_DATEFROM);
			}

		} catch (CompliancePortalException cp) {
			throw cp;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
		return workEfficiencyReportDto;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportDBService#getWorkEfficiencyReportWithCriteriaInExcelFormat(com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria)
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteriaInExcelFormat(WorkEfficiencyReportSearchCriteria searchCriteria) throws CompliancePortalException {
		
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		List<WorkEfficiencyReportData> workEfficiencyReportList = null;
		Connection connection = null;
		PreparedStatement prepareStatement = null;
		ResultSet resultSet = null;

		try {	
			     String fromTo = DateTimeFormatter.parseDate(searchCriteria.getFilter().getFromTo()); //pastdate
				 String dateFrom =DateTimeFormatter.parseDate(searchCriteria.getFilter().getDateFrom()); //currentDate
				 
				 FilterQueryBuilder queryBuilder = new WorkEfficiencyReportFilterQueryBuilder(searchCriteria.getFilter(),
						WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_DOWNLOAD_INNER_QUERY.getQuery());
				 String query = queryBuilder.getQuery();
			     query = WorkEfficiencyReportQueryConstant.GET_WORK_EFFICIENCY_REPORT_ON_DOWNLOAD.getQuery().replace(Constants.INNER_QUERY, query);
				 connection = getConnection(Boolean.TRUE);
				 if(fromTo.compareTo(dateFrom) > 0 || fromTo.compareTo(dateFrom) == 0){
					prepareStatement = connection.prepareStatement(query);
					prepareStatement.setString(1, dateFrom);
					prepareStatement.setString(2, fromTo);
					resultSet = prepareStatement.executeQuery();
					workEfficiencyReportList = getWorkEfficiencyData(resultSet);
					workEfficiencyReportDto.setWorkEfficiencyReportData(workEfficiencyReportList);
				
				  Page page = getPaginationData(searchCriteria,workEfficiencyReportDto);
				  workEfficiencyReportDto.setPage(page);
				  
				}else {
						throw new CompliancePortalException(CompliancePortalErrors.ERROR_FROMTO_LESS_THAN_DATEFROM);
			    }
		}
		catch (CompliancePortalException cp) {
			throw cp;
		}
		catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(prepareStatement);
			closeConnection(connection);
		}
	return workEfficiencyReportDto;
	}
	
}
