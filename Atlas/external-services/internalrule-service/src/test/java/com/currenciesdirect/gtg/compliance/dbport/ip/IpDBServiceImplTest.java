package com.currenciesdirect.gtg.compliance.dbport.ip;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.sql.DataSource;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpProviderProperty;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;
import com.currenciesdirect.gtg.compliance.exception.ip.IpErrors;

public class IpDBServiceImplTest {

	@InjectMocks
	IpDBServiceImpl ipDBServiceImpl;

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

	public static final String LEXISNEXIS = "LEXISNEXIS";

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

	public void setMockDataForGetIPProviderInitConfigProperty() {
		String attribute = "{\"endPointUrl\":\"https://enterprisesit.currenciesdirect.com/es-restfulinterface/rest/ipservice/ipAndPostcodeDistanceCheck\","
				+ "\"threeSholdScore\":200.0,\"socketTimeoutMillis\":6000,\"connectionTimeoutMillis\":6000}";
		try {
			when(rs.getString("Attribute")).thenReturn(attribute);
			when(rs.getString("Code")).thenReturn(LEXISNEXIS);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public Map<String, IpProviderProperty> getResult() {
		ObjectMapper mapper = new ObjectMapper();
		String attribute = "{\"endPointUrl\":\"https://enterprisesit.currenciesdirect.com/es-restfulinterface/rest/ipservice/ipAndPostcodeDistanceCheck\","
				+ "\"threeSholdScore\":200.0,\"socketTimeoutMillis\":6000,\"connectionTimeoutMillis\":6000}";
		Map<String, IpProviderProperty> result = new ConcurrentHashMap<>();
		IpProviderProperty property;
		try {
			property = mapper.readValue(attribute, IpProviderProperty.class);
			result.put(LEXISNEXIS, property);
		} catch (Exception e) {
			System.out.println(e);
		}

		return result;
	}

	@Test
	public void testGetIPProviderInitConfigProperty() {
		try {
			setMockDataForGetIPProviderInitConfigProperty();
			Map<String, IpProviderProperty> expectedResult = getResult();
			Map<String, IpProviderProperty> actualResult = ipDBServiceImpl.getIPProviderInitConfigProperty();
			assertEquals(expectedResult.get(LEXISNEXIS).getEndPointUrl(),
					actualResult.get(LEXISNEXIS).getEndPointUrl());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}

	}

	@Test
	public void testForGetIPProviderInitConfigProperty() {
		try {
			ipDBServiceImpl.getIPProviderInitConfigProperty();
		} catch (InternalRuleException e) {
			assertEquals(IpErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA.getErrorCode(), e.getMessage());
		}
	}

}
