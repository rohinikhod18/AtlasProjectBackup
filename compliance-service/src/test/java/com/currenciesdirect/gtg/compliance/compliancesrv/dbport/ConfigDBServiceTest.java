package com.currenciesdirect.gtg.compliance.compliancesrv.dbport;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;

public class ConfigDBServiceTest {
	  @Mock
		 DataSource dataSource;

		@Mock
		 Connection connection;

		@Mock
		PreparedStatement preparedStatement;

		@Mock
		ResultSet rs;
		@InjectMocks
		ConfigDBService configDBService;
		
		@Before
		public void setUp() {
			try {
				
				MockitoAnnotations.initMocks(this);	
				when(dataSource.getConnection()).thenReturn(connection);
				when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
				when(preparedStatement.executeQuery()).thenReturn(rs); 
				Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
				Mockito.when(preparedStatement.execute()).thenReturn(true);
				when(preparedStatement.executeUpdate()).thenReturn(1);
				when(preparedStatement.getGeneratedKeys()).thenReturn(rs);
				when(rs.getString("Attribute")).thenReturn("{\"requestType\":\"INTERNAL_RULE_SERVICE\",\"endPointUrl\":\"http://localhost:8080/internalrule-service/checkRegistration\"}");
				when(rs.getString("Code")).thenReturn("INTERNAL_RULE_SERVICE").thenReturn("USA");
				when(rs.getString("code")).thenReturn("USA");
				when(rs.getString("displayname")).thenReturn("USA");
				when(rs.getString("UserName")).thenReturn("cd_api_user");
				when(rs.getInt("ID")).thenReturn(18);
				
			} catch (SQLException  e) {
				System.out.println(e);
			}
		}

	@Test
	public void testGetProviderinitConfigProperties() {
		try {
			ConcurrentMap<String,ProviderProperty> actualResult =configDBService.getProviderinitConfigProperties();
			ConcurrentMap<String,ProviderProperty> expectedResult=new ConcurrentHashMap<>();
			ProviderProperty providerProperty= new ProviderProperty();
			providerProperty.setEndPointUrl("http://localhost:8080/internalrule-service/checkRegistration");
			//providerProperty.set
			expectedResult.put("INTERNAL_RULE_SERVICE", providerProperty);
			assertEquals(expectedResult.get("INTERNAL_RULE_SERVICE").getEndPointUrl(),actualResult.get("INTERNAL_RULE_SERVICE").getEndPointUrl());
		} catch (ComplianceException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetFullNameFromISOCountryCodes() {
		try {
			ConcurrentMap<String,String> actualResult =	configDBService.getFullNameFromISOCountryCodes();
			ConcurrentMap<String,String> expectedResult=new ConcurrentHashMap<>();
			expectedResult.put("USA", "USA");
			assertEquals(expectedResult.get("USA"),actualResult.get("USA"));
			
		} catch (ComplianceException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetUserIdAndName() {
		try {
			ConcurrentMap<String,Integer> actualResult =configDBService.getUserIdAndName();
		
			ConcurrentMap<String,Integer> expectedResult=new ConcurrentHashMap<>();
			expectedResult.put("cd_api_user", 18);
			assertEquals(expectedResult.get("cd_api_user"),actualResult.get("cd_api_user"));
		} catch (ComplianceException e) {
			System.out.println(e);
		}
		
	}

}
