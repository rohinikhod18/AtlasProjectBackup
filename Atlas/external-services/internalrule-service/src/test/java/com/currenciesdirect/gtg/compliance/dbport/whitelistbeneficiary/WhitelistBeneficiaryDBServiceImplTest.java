package com.currenciesdirect.gtg.compliance.dbport.whitelistbeneficiary;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

public class WhitelistBeneficiaryDBServiceImplTest {

	
	@InjectMocks
	WhitelistBeneficiaryDBServiceImpl whitelistBeneficiaryDBServiceImpl;
	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Mock
	AbstractDao abstractDao;
	
	@Before
	public void setUp() {
		try {
			int[] count = { 1 };
			MockitoAnnotations.initMocks(this);
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.executeBatch()).thenReturn(count);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	
	
	public WhitelistBeneficiaryRequest getWhitelistBeneficiaryRequest()
	{
		WhitelistBeneficiaryRequest whitelistBeneficiaryRequest =new WhitelistBeneficiaryRequest();
		whitelistBeneficiaryRequest.setAccountNumber("73152391200517");
		whitelistBeneficiaryRequest.setFirstName("Hmrc Vat");
		return whitelistBeneficiaryRequest;
	}
	public WhitelistBeneficiaryRequest setWhitelistBeneficiaryRequest()
	{
		WhitelistBeneficiaryRequest whitelistBeneficiaryRequest =new WhitelistBeneficiaryRequest();
		whitelistBeneficiaryRequest.setAccountNumber("73152391200517");
		return whitelistBeneficiaryRequest;
		
	}
	public void setMockForGetWhitelistBeneficiaryData()
	{
		try {
			when(rs.getString("FirstName")).thenReturn("Hmrc Vat");
			when(rs.getTimestamp("CreatedOn")).thenReturn(Timestamp.valueOf("2018-04-30 12:06:28"));
			when(rs.getString("AccountNumber")).thenReturn("73152391200517");
		
		} catch (SQLException e) {
			System.out.println(e);
		}		
	}
	public void setMockGetWhitelistBeneficiaryData()
	{
		try {
			when(rs.getString("FirstName")).thenReturn("Hmrc Vat");
			when(rs.getString("AccountNumber")).thenReturn("73152391200517");
		
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	public List<WhitelistBeneficiaryResponse> getWhitelistBeneficiaryResponse()
	{
		WhitelistBeneficiaryResponse whitelistBeneficiaryResponse= new WhitelistBeneficiaryResponse();
		whitelistBeneficiaryResponse.setFirstName("Hmrc Vat");
		whitelistBeneficiaryResponse.setCreatedOn("2018-04-30 12:06:28");
		whitelistBeneficiaryResponse.setAccountNumber("73152391200517");
		List<WhitelistBeneficiaryResponse> whitelistBeneficiaryResponseList=new ArrayList<>();
		whitelistBeneficiaryResponseList.add(whitelistBeneficiaryResponse);
		return whitelistBeneficiaryResponseList;
		
	}
	
public WhitelistBeneficiaryResponse setWhitelistBeneficiaryResponse()
{
	WhitelistBeneficiaryResponse whitelistBeneficiaryResponse= new WhitelistBeneficiaryResponse();
	whitelistBeneficiaryResponse.setStatus("Success");
	return whitelistBeneficiaryResponse;
}
	
	public void setMockForSearchWhiteListBeneficiaryDataToUpdate()
	{
		try {
			when(rs.getString("AccountNumber")).thenReturn("73152391200517");
			when(rs.getInt("Deleted")).thenReturn(1);
			when(rs.getInt("ID")).thenReturn(1);
			
		} catch (SQLException e) {
			System.out.println(e);
		}	
	}
	public void setMockSearchWhiteListBeneficiaryDataToUpdate()
	{
		try {
			when(rs.getInt("Deleted")).thenReturn(1);
			when(rs.getInt("ID")).thenReturn(1);
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}
	@Test
	public void testGetWhitelistBeneficiaryData() {
		try {
			setMockForGetWhitelistBeneficiaryData();
			List<WhitelistBeneficiaryResponse> expectedResult=getWhitelistBeneficiaryResponse();
			List<WhitelistBeneficiaryResponse> actualResult=whitelistBeneficiaryDBServiceImpl.getWhitelistBeneficiaryData(getWhitelistBeneficiaryRequest());
		assertEquals(expectedResult.get(0).getAccountNumber(),actualResult.get(0).getAccountNumber());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testForGetWhitelistBeneficiaryData() {
		try {
			setMockGetWhitelistBeneficiaryData();
            whitelistBeneficiaryDBServiceImpl.getWhitelistBeneficiaryData(getWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.FAILED.getErrorCode(),e.getMessage());
		}
	}
	@Test
	public void testDeleteFromWhiteListBeneficiaryData() {
		try {
			WhitelistBeneficiaryResponse expectedResult= setWhitelistBeneficiaryResponse();
			WhitelistBeneficiaryResponse actualResult=whitelistBeneficiaryDBServiceImpl.deleteFromWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
		assertEquals(expectedResult.getStatus(),actualResult.getStatus());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testSaveIntoWhitelistBeneficiary() {
		try {
			WhitelistBeneficiaryResponse expectedResult= setWhitelistBeneficiaryResponse();
			WhitelistBeneficiaryResponse actualResult=whitelistBeneficiaryDBServiceImpl.saveIntoWhitelistBeneficiary(getWhitelistBeneficiaryRequest());
			assertEquals(expectedResult.getStatus(),actualResult.getStatus());
			} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testSearchWhiteListBeneficiaryData() {
		try {
			setMockForGetWhitelistBeneficiaryData();
			List<WhitelistBeneficiaryResponse> expectedResult=getWhitelistBeneficiaryResponse();
			List<WhitelistBeneficiaryResponse> actualResult=whitelistBeneficiaryDBServiceImpl.searchWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
		assertEquals(expectedResult.get(0).getAccountNumber(),actualResult.get(0).getAccountNumber());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}
	
	@Test
	public void testForSearchWhiteListBeneficiaryData() {
		try {
			setMockGetWhitelistBeneficiaryData();
			whitelistBeneficiaryDBServiceImpl.searchWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.FAILED.getErrorCode(),e.getMessage());
		}
	}
	@Test
	public void testSearchWhiteListBeneficiaryDataToUpdate() {
		try {
			setMockForSearchWhiteListBeneficiaryDataToUpdate();
			Integer expectedResult=1; 
			Integer actualkResult = whitelistBeneficiaryDBServiceImpl.searchWhiteListBeneficiaryDataToUpdate(getWhitelistBeneficiaryRequest());
		assertEquals(expectedResult,actualkResult);
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForSearchWhiteListBeneficiaryDataToUpdate() {
		try {
			setMockForSearchWhiteListBeneficiaryDataToUpdate();
		 whitelistBeneficiaryDBServiceImpl.searchWhiteListBeneficiaryDataToUpdate(setWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.FAILED.getErrorCode(),e.getMessage());
		}
	}

	@Test
	public void testRemoveMillisFromTimeStamp() {
		String expectedResult="07-09-2020 17:50:41";
		String actualResult=whitelistBeneficiaryDBServiceImpl.removeMillisFromTimeStamp("07-09-2020 17:50:41");
	assertEquals(expectedResult,actualResult);
	}

}
