package com.currenciesdirect.gtg.compliance.dbport.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.core.domain.Page;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportData;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.core.report.WorkEfficiencyReportServiceImpl;
import com.currenciesdirect.gtg.compliance.dbport.PayOutQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.WorkEfficiencySlaEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

public class WorkEfficiencyReportDBServiceImplTest {
	@InjectMocks
	WorkEfficiencyReportDBServiceImpl workEfficiencyReportDBServiceImpl;
	
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet resultSet;
	
	
	private static final String Name ="cd.comp.system";
	@Before
	public void setUp() 
	{
	    MockitoAnnotations.initMocks(this);
		try {
			Mockito.when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(resultSet);
			when(preparedStatement.getResultSet()).thenReturn(resultSet);
			Mockito.when(resultSet.next()).thenReturn(true).thenReturn(false); 
			Mockito.when(preparedStatement.execute()).thenReturn(true); 
		
		} catch (SQLException e)
		{
			System.out.println(e);
		}
	}
	public Page getpage()
	{
		Page page= new Page();
		page.setCurrentPage(1);
		page.setMaxRecord(40);
		page.setMinRecord(1);
		page.setPageSize(40);
		page.setTotalPages(1);
		page.setTotalRecords(1);
		return page;
		
	}
	public List<WorkEfficiencyReportData> getWorkEfficiencyReportData()
	{
		 List<WorkEfficiencyReportData> data=new ArrayList<>();
		WorkEfficiencyReportData workEfficiencyReportData=new WorkEfficiencyReportData();
		workEfficiencyReportData.setAccountType("PFX");
		workEfficiencyReportData.setLockedRecords(1);
		workEfficiencyReportData.setPercentEfficiency(0.0);
		workEfficiencyReportData.setQueueType("Payment out");
		workEfficiencyReportData.setReleasedRecords(0);
		workEfficiencyReportData.setSeconds(0.0);
		workEfficiencyReportData.setSlaValue(3.0);
		workEfficiencyReportData.setTotalRows(1);
		workEfficiencyReportData.setTimeEfficiency(0.0);
		workEfficiencyReportData.setUserName(Name);
		data.add(workEfficiencyReportData);
		
		return data;
		
	}
	public WorkEfficiencyReportDto setWorkEfficiencyReportDto()
	{
		
		List<String> queueList= new ArrayList<>();
		queueList.add("Contact");
		List<String> userNameList= new ArrayList<>();
		userNameList.add("abhay");
		WorkEfficiencyReportDto workEfficiencyReportDto=new WorkEfficiencyReportDto();
		workEfficiencyReportDto.setDateDifference(7);
		workEfficiencyReportDto.setPage(getpage());
		workEfficiencyReportDto.setQueueList(queueList);
		workEfficiencyReportDto.setUserNameList(userNameList);
		workEfficiencyReportDto.setWorkEfficiencyReportData(getWorkEfficiencyReportData());
		
		return workEfficiencyReportDto;
		
	}
	public void setMockData()
	{
		
		try {
			when(resultSet.getString("SSOUserID")).thenReturn(Name);
			when(resultSet.getInt("ResourceType")).thenReturn(1);
			when(resultSet.getInt("AccType")).thenReturn(2);
			when(resultSet.getInt("LockedRecords")).thenReturn(1);
			when(resultSet.getInt("ReleasedRecords")).thenReturn(0);
			when(resultSet.getInt("seconds")).thenReturn(0);
			System.setProperty("sla.pfx.paymentOut", "3");
			
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void getWorkEfficiencyReportTest()
	{
		setMockData();
	WorkEfficiencyReportSearchCriteria searchCriteria = new WorkEfficiencyReportSearchCriteria();
	WorkEfficiencyReportFilter workEfficiencyReportFilter = new WorkEfficiencyReportFilter();
	WorkEfficiencyReportServiceImpl wr=new WorkEfficiencyReportServiceImpl();
	String fromTo = null;
	String dateFrom = null;
	fromTo = wr.getFromTo(fromTo);
	dateFrom = wr.getDateFrom(dateFrom);
	workEfficiencyReportFilter.setFromTo(fromTo);
	workEfficiencyReportFilter.setDateFrom(dateFrom);
	searchCriteria.setFilter(workEfficiencyReportFilter);
	try {
		WorkEfficiencyReportDto expectedResult=setWorkEfficiencyReportDto();
		WorkEfficiencyReportDto workEfficiencyReportDto= workEfficiencyReportDBServiceImpl.getWorkEfficiencyReport(searchCriteria);
		assertEquals(expectedResult.getWorkEfficiencyReportData().get(0).getSeconds(),workEfficiencyReportDto.getWorkEfficiencyReportData().get(0).getSeconds());
		
	} catch (CompliancePortalException e) {
		System.out.println(e);
	}
	}
	
	
	@Test
	public void getWorkEfficiencyReportWithCriteriaTest()
	{
		String[] name= {Name};
		setMockData();
		WorkEfficiencyReportSearchCriteria searchCriteria = new WorkEfficiencyReportSearchCriteria();
		WorkEfficiencyReportFilter workEfficiencyReportFilter = new WorkEfficiencyReportFilter();
		WorkEfficiencyReportServiceImpl wr=new WorkEfficiencyReportServiceImpl();
		workEfficiencyReportFilter.setUser(name);
		String fromTo = null;
		String dateFrom = null;
		fromTo = wr.getFromTo(fromTo);
		dateFrom = wr.getDateFrom(dateFrom);
		workEfficiencyReportFilter.setFromTo(fromTo);
		workEfficiencyReportFilter.setDateFrom(dateFrom);
		searchCriteria.setFilter(workEfficiencyReportFilter);
		try {
			WorkEfficiencyReportDto expectedResult=setWorkEfficiencyReportDto();
			WorkEfficiencyReportDto workEfficiencyReportDto= workEfficiencyReportDBServiceImpl.getWorkEfficiencyReportWithCriteria(searchCriteria);
			assertEquals(expectedResult.getWorkEfficiencyReportData().get(0).getSeconds(),workEfficiencyReportDto.getWorkEfficiencyReportData().get(0).getSeconds());
		
		
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
		
		
	}
	@Test
	public void testgetWorkEfficiencyReport()
	{
		setMockData();
	WorkEfficiencyReportSearchCriteria searchCriteria = new WorkEfficiencyReportSearchCriteria();
	try {
	 workEfficiencyReportDBServiceImpl.getWorkEfficiencyReport(searchCriteria);
	} catch (CompliancePortalException e) {
		 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
	}
	}
	
	
	@Test
	public void testgetWorkEfficiencyReportWithCriteria()
	{
		setMockData();
		WorkEfficiencyReportSearchCriteria searchCriteria = new WorkEfficiencyReportSearchCriteria();
		try {
			 workEfficiencyReportDBServiceImpl.getWorkEfficiencyReportWithCriteria(searchCriteria);
		} catch (CompliancePortalException e) {
			 assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(),e.getMessage());
		}
		
		
	}
}