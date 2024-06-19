package com.currenciesdirect.gtg.compliance.dbport.blacklist;

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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistDataResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateData;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistUpdateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.ResponseStatus;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistErrors;
import com.currenciesdirect.gtg.compliance.exception.blacklist.BlacklistException;

public class DBServiceImplTest {

	@InjectMocks
	DBServiceImpl dBServiceImpl;

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

	private static final String NAME = "cd.comp.system";
	private static final String TYPE = "DOMAIN";
	private static final String VALUE = "@consultant.com";
	private static final String REQUESTTYPE = "PROFILE_NEW_REGISTRATION";
	private static final String CORRELATIONID = "83462864";
	private static final String ACCOUNTSFID = "0010O00001nN1lkQAC";

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
	public InternalServiceRequest getInternalServiceRequest() {
		BlacklistData blacklistData = new BlacklistData();
		blacklistData.setType(TYPE);
		blacklistData.setValue(VALUE);
		blacklistData.setRequestType(REQUESTTYPE);
		BlacklistData[] data = { blacklistData };
		BlacklistRequest blacklistRequest = new BlacklistRequest();
		blacklistRequest.setCorrelationId(CORRELATIONID);
		blacklistRequest.setCreatedBy(NAME);
		blacklistRequest.setCreatedOn(Timestamp.valueOf("2017-04-04 05:04:03"));
		blacklistRequest.setUpdatedBy(NAME);
		blacklistRequest.setUpdatedOn(Timestamp.valueOf("2017-06-04 05:04:04"));
		blacklistRequest.setData(data);
		InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
		internalServiceRequest.setAccountSFId(ACCOUNTSFID);
		internalServiceRequest.setIsAllCheckPerform(true);
		internalServiceRequest.setIsIpEligible(true);
		internalServiceRequest.setBlacklistRequest(blacklistRequest);
		return internalServiceRequest;
	}
	public InternalServiceRequest setInternalServiceRequest() {
		BlacklistData blacklistData = new BlacklistData();
		blacklistData.setType(TYPE);
		blacklistData.setRequestType(REQUESTTYPE);
		BlacklistData[] data = { blacklistData };
		BlacklistRequest blacklistRequest = new BlacklistRequest();
		blacklistRequest.setCorrelationId(CORRELATIONID);
		blacklistRequest.setCreatedBy(NAME);
		blacklistRequest.setCreatedOn(Timestamp.valueOf("2017-04-04 05:04:03"));
		blacklistRequest.setUpdatedBy(NAME);
		blacklistRequest.setUpdatedOn(Timestamp.valueOf("2017-06-04 05:04:04"));
		blacklistRequest.setData(data);
		InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
		internalServiceRequest.setAccountSFId(ACCOUNTSFID);
		internalServiceRequest.setIsAllCheckPerform(true);
		internalServiceRequest.setIsIpEligible(true);
		internalServiceRequest.setBlacklistRequest(blacklistRequest);
		return internalServiceRequest;
	}
	public InternalServiceRequest internalServiceRequest() {
		InternalServiceRequest internalServiceRequest = new InternalServiceRequest();
		internalServiceRequest.setAccountSFId(ACCOUNTSFID);
		internalServiceRequest.setIsAllCheckPerform(true);
		internalServiceRequest.setIsIpEligible(true);
		return internalServiceRequest;
	}
	public BlacklistSTPRequest getBlacklistSTPRequest() {
		BlacklistData blacklistData = new BlacklistData();
		blacklistData.setType(TYPE);
		blacklistData.setValue(VALUE);
		blacklistData.setRequestType(REQUESTTYPE);
		List<BlacklistData> data = new ArrayList<>();
		data.add(blacklistData);
		BlacklistSTPRequest blacklistSTPRequest = new BlacklistSTPRequest();
		blacklistSTPRequest.setCorrelationId(CORRELATIONID);
		blacklistSTPRequest.setSearch(data);
		return blacklistSTPRequest;
	}
	public BlacklistSTPRequest setBlacklistSTPRequest() {
		BlacklistData blacklistData = new BlacklistData();
		blacklistData.setType(TYPE);
		blacklistData.setRequestType(REQUESTTYPE);
		List<BlacklistData> data = new ArrayList<>();
		data.add(blacklistData);
		BlacklistSTPRequest blacklistSTPRequest = new BlacklistSTPRequest();
		blacklistSTPRequest.setCorrelationId(CORRELATIONID);
		blacklistSTPRequest.setSearch(data);
		return blacklistSTPRequest;
	}
	public void setMockForStpSearchIntoBlacklist() {
		try {
			when(rs.getString(1)).thenReturn("consultant.com");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	public BlacklistContactResponse getBlacklistContactResponse() {
		BlacklistContactResponse blacklistContactResponse = new BlacklistContactResponse();
		BlacklistSTPData blacklistData = new BlacklistSTPData();
		blacklistData.setType(TYPE);
		blacklistData.setValue(VALUE);
		blacklistData.setRequestType(REQUESTTYPE);
		blacklistData.setMatchedData("consultant.com");
		List<BlacklistSTPData> data = new ArrayList<>();
		data.add(blacklistData);
		blacklistContactResponse.setData(data);
		blacklistContactResponse.setStatus("FAIL");
		return blacklistContactResponse;
	}
	public void setMockForUiSearchIntoBlacklist() {
		try {
			when(rs.getTimestamp("created_on")).thenReturn(Timestamp.valueOf("2017-06-04 05:04:03"));
			when(rs.getString("value")).thenReturn(VALUE);
			when(rs.getString("Notes")).thenReturn(null);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	public InternalServiceResponse setInternalServiceResponse() {
		List<BlacklistDataResponse> blacklistDataList = new ArrayList<>();
		BlacklistDataResponse blacklistData = new BlacklistDataResponse();
		blacklistData.setCreatedOnDate("2017-06-04 05:04:03");
		blacklistData.setValue(VALUE);
		blacklistDataList.add(blacklistData);
		InternalServiceResponse internalServiceResponse = new InternalServiceResponse();
		internalServiceResponse.setBlacklistDataResponse(blacklistDataList);
		return internalServiceResponse;
	}
	public BlacklistUpdateRequest getBlacklistUpdateRequest() {
		BlacklistUpdateData blacklistUpdateData = new BlacklistUpdateData();
		blacklistUpdateData.setNewValue("Angela Maxwell");
		blacklistUpdateData.setOriginalValue(VALUE);
		blacklistUpdateData.setType("NAME");
		BlacklistUpdateData[] data = { blacklistUpdateData };
		BlacklistUpdateRequest blacklistUpdateRequest = new BlacklistUpdateRequest();
		blacklistUpdateRequest.setData(data);
		return blacklistUpdateRequest;
	}
	public void setMockForGetAllBlacklistTypes() {
		try {
			when(rs.getNString("Type")).thenReturn("NAME");
			when(rs.getInt("Relevance")).thenReturn(1);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testSaveIntoBlacklist() {
		try {
			InternalServiceResponse expectedResult = new InternalServiceResponse();
			expectedResult.setStatus(ResponseStatus.SUCCESS.getStatus());
			InternalServiceResponse actualResult = dBServiceImpl.saveIntoBlacklist(getInternalServiceRequest());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (BlacklistException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForSaveIntoBlacklist() {
		try {
			dBServiceImpl.saveIntoBlacklist(internalServiceRequest());
		} catch (BlacklistException e) {
			assertEquals(BlacklistErrors.ERROR_WHILE_INSERTING_DATA.getErrorCode(), e.getMessage());
		}
	}
	@Test
	public void testStpSearchIntoBlacklist() {
		try {
			setMockForStpSearchIntoBlacklist();
			BlacklistContactResponse expectedResult = getBlacklistContactResponse();
			BlacklistContactResponse actualResult = dBServiceImpl.stpSearchIntoBlacklist(getBlacklistSTPRequest());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (BlacklistException e) {
			System.out.println(e);
		}
	}
    /*
	@Test
	public void testForStpSearchIntoBlacklist() {
		try {
			setMockForStpSearchIntoBlacklist();
			dBServiceImpl.stpSearchIntoBlacklist(setBlacklistSTPRequest());
		} catch (BlacklistException e) {
			assertEquals(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
	*/
	@Test
	public void testUiSearchIntoBlacklist() {
		try {
			setMockForUiSearchIntoBlacklist();
			InternalServiceResponse expectedResult = setInternalServiceResponse();
			InternalServiceResponse actualResult = dBServiceImpl.uiSearchIntoBlacklist(getInternalServiceRequest());
			assertEquals(expectedResult.getBlacklistDataResponse().get(0).getCreatedOnDate(),
					actualResult.getBlacklistDataResponse().get(0).getCreatedOnDate());
		} catch (BlacklistException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForUiSearchIntoBlacklist() {
		try {
			dBServiceImpl.uiSearchIntoBlacklist(setInternalServiceRequest());
		} catch (BlacklistException e) {
			assertEquals(BlacklistErrors.ERROR_WHILE_SEARCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
	@Test
	public void testUpdateIntoBlacklist() {
		try {
			BlacklistModifierResponse expectedResult = new BlacklistModifierResponse();
			expectedResult.setStatus(ResponseStatus.SUCCESS.getStatus());
			BlacklistModifierResponse actualResult = dBServiceImpl.updateIntoBlacklist(getBlacklistUpdateRequest());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (BlacklistException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForUpdateIntoBlacklist() {
		try {
			BlacklistUpdateRequest blacklistUpdateRequest = new BlacklistUpdateRequest();
			dBServiceImpl.updateIntoBlacklist(blacklistUpdateRequest);
		} catch (BlacklistException e) {
			assertEquals(BlacklistErrors.ERROR_WHILE_UPDATING_DATA.getErrorCode(), e.getMessage());
		}
	}
	@Test
	public void testdeleteFromBacklist() {
		try {
			InternalServiceResponse expectedResult = new InternalServiceResponse();
			expectedResult.setStatus(ResponseStatus.SUCCESS.getStatus());
			InternalServiceResponse actualResult = dBServiceImpl.deleteFromBacklist(getInternalServiceRequest());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (BlacklistException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testFordeleteFromBacklist() {
		try {
			dBServiceImpl.deleteFromBacklist(internalServiceRequest());
		} catch (BlacklistException e) {
			assertEquals(BlacklistErrors.ERROR_WHILE_DELETING_DATA.getErrorCode(), e.getMessage());
		}
	}
	@Test
	public void testGetAllBlacklistTypes() {
		setMockForGetAllBlacklistTypes();
		try {
			Map<String, Integer> expectedResult = new ConcurrentHashMap<>();
			expectedResult.put("NAME", 1);
			Map<String, Integer> actualResult = dBServiceImpl.getAllBlacklistTypes();
			assertEquals(expectedResult, actualResult);
		} catch (BlacklistException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetAllBlacklistTypes() {
		try {
			dBServiceImpl.getAllBlacklistTypes();
		} catch (BlacklistException e) {
			assertEquals(BlacklistErrors.ERROR_WHILE_FETCHING_DATA_BLACKLIST_TYPE.getErrorCode(), e.getMessage());
		}
	}
}
