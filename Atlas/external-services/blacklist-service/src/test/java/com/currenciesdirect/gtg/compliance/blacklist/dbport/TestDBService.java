package com.currenciesdirect.gtg.compliance.blacklist.dbport;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistIdAndData;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistModifierResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.BlacklistSTPResponse;
import com.currenciesdirect.gtg.compliance.blacklist.core.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.blacklist.exception.BlacklistException;


@RunWith(MockitoJUnitRunner.class)
public class TestDBService {

    @Mock
    DataSource mockDataSource;
    @Mock
    Connection mockConn;
    @Mock
    PreparedStatement mockPreparedStmnt;
    @Mock
    ResultSet mockResultSet;

    public TestDBService() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws SQLException {
        when(mockDataSource.getConnection()).thenReturn(mockConn);
        doNothing().when(mockConn).commit();
        when(mockConn.prepareStatement(anyString())).thenReturn(mockPreparedStmnt);
        doNothing().when(mockPreparedStmnt).setString(anyInt(), anyString());
        when(mockPreparedStmnt.executeBatch()).thenReturn(new int[]{1,1});
        when(mockPreparedStmnt.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(Boolean.TRUE, Boolean.FALSE);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSaveBlacklistWithNoExceptions() throws SQLException, BlacklistException {

        DBServiceImpl service =(DBServiceImpl) DBServiceImpl.getInstance();
        service.setDataSource(mockDataSource);
        BlacklistRequest request = new BlacklistRequest();
        BlacklistData data = new BlacklistData();
        data.setType("NAME");
        data.setValue("Vivian Dambrosio");
        request.setData(new BlacklistData[]{data});
        BlacklistModifierResponse response = service.saveIntoBlacklist(request);
        assertEquals(ResponseStatus.SUCCESS.getStatus(), response.getStatus());
       
    }
    
    @Test
    public void testSearchBlacklistWithNoExceptions() throws SQLException, BlacklistException {

        DBServiceImpl service =(DBServiceImpl) DBServiceImpl.getInstance();
        service.setDataSource(mockDataSource);
        BlacklistSTPRequest request = new BlacklistSTPRequest();
        BlacklistIdAndData idAndData = new BlacklistIdAndData();
        idAndData.setId("123");
        
        BlacklistData data = new BlacklistData();
        data.setType("NAME");
        data.setValue("Vivian Dambrosio");
        List<BlacklistData> types = new ArrayList<>();
        types.add(data);
        idAndData.setData(types);
        List<BlacklistIdAndData> idAndDataList = new ArrayList<>();
        idAndDataList.add(idAndData);
        request.setSearch(idAndDataList);
        BlacklistSTPResponse response = service.stpSearchIntoBlacklist(request);
        assertFalse((response.getSearch().size() <= 0));
       
    }


    @Test(expected = BlacklistException.class)
    public void testCreateWithPreparedStmntException()throws BlacklistException, SQLException {

         when(mockConn.prepareStatement(anyString())).thenThrow(new SQLException());


        	DBServiceImpl service =(DBServiceImpl) DBServiceImpl.getInstance();
            service.setDataSource(mockDataSource);
            BlacklistRequest request = new BlacklistRequest();
            BlacklistData data = new BlacklistData();
            data.setType("NAME");
            data.setValue("Vivian Dambrosio");
            request.setData(new BlacklistData[]{data});
			service.saveIntoBlacklist(request);
    }
}