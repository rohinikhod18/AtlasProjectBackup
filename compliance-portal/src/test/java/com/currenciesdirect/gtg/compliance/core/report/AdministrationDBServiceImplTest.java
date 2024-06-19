package com.currenciesdirect.gtg.compliance.core.report;

import static org.junit.Assert.*;
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
import com.currenciesdirect.gtg.compliance.core.domain.report.AdministrationDto;
import com.currenciesdirect.gtg.compliance.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class AdministrationDBServiceImplTest {

	@InjectMocks
	AdministrationDBServiceImpl administrationDBServiceImpl;

	@Mock
	DataSource dataSource;

	@Mock
	Connection connection;

	@Mock
	PreparedStatement preparedStatement;

	@Mock
	ResultSet rs;

	@Mock
	AbstractDao abstractDao;

	@Before
	public void setUp() {
		try {
			MockitoAnnotations.initMocks(this);
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	public List<String> getCountries() {
		List<String> countries = new ArrayList<>();
		countries.add("United Kingdom");
		return countries;
	}

	public void setMockDataForGetAdministration() {
		try {
			when(rs.getString("Country")).thenReturn("United Kingdom");
		} catch (SQLException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetAdministration() {
		setMockDataForGetAdministration();
		try {

			AdministrationDto expectedResult = new AdministrationDto();
			expectedResult.setCountries(getCountries());
			AdministrationDto actualResult = administrationDBServiceImpl.getAdministration();
			assertEquals(expectedResult.getCountries().get(0), actualResult.getCountries().get(0));
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetAdministration() {
		try {
			administrationDBServiceImpl.getAdministration();
		} catch (CompliancePortalException e) {
			assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), e.getMessage());
		}
	}

}