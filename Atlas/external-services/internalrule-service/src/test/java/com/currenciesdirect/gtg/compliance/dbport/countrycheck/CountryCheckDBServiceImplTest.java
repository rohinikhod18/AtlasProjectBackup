package com.currenciesdirect.gtg.compliance.dbport.countrycheck;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckException;

public class CountryCheckDBServiceImplTest {

	@InjectMocks
	CountryCheckDBServiceImpl countryCheckDBServiceImpl;

	@Mock
	private DataSource dataSource;

	@Mock
	private Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
		try {
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	public void setMockData() {
		try {
			when(rs.getString("RiskLevel")).thenReturn("L");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testGetCountryCheckResponse() {
		try {
			setMockData();
			CountryCheckContactResponse expectedResult = new CountryCheckContactResponse();
			expectedResult.setStatus("PASS");
			CountryCheckContactResponse actualResult = countryCheckDBServiceImpl
					.getCountryCheckResponse("United Kingdom");
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (CountryCheckException e) {
			System.out.println(e);
		}
	}
	@Test
	public void testForGetCountryCheckResponse() {
		try {
			CountryCheckContactResponse expectedResult = new CountryCheckContactResponse();
			expectedResult.setStatus(CountryCheckErrors.ERROR_COUNTRY_NOT_FOUND.getErrorDescription());
			CountryCheckContactResponse actualResult = countryCheckDBServiceImpl
					.getCountryCheckResponse("United Kingdom");
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (CountryCheckException e) {
			System.out.println(e);
		}
	}
}
