package com.currenciesdirect.gtg.compliance.fraugster.dbport;

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
import org.powermock.core.classloader.annotations.PrepareForTest;
import com.currenciesdirect.gtg.compliance.fraugster.core.domain.FraugsterProviderProperty;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterErrors;
import com.currenciesdirect.gtg.compliance.fraugster.exception.FraugsterException;

@PrepareForTest(FraugsterDBServiceImpl.class)
public class FraugsterDBServiceImplTest {
	
	@InjectMocks
	FraugsterDBServiceImpl fraugsterDBServiceImpl;
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
	public void setup() {
		int[] count = { 1 };
		MockitoAnnotations.initMocks(this);
		try {
			when(dataSource.getConnection()).thenReturn(connection);
			when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
			when(preparedStatement.executeQuery()).thenReturn(rs);
			when(preparedStatement.executeBatch()).thenReturn(count);
			Mockito.when(rs.next()).thenReturn(true).thenReturn(false);
			Mockito.when(preparedStatement.execute()).thenReturn(true);
			Mockito.when(preparedStatement.executeUpdate()).thenReturn(1);			
		} catch (SQLException e) {	
			e.printStackTrace();
		}
	}
	public void setMock()
	{
		String attribute="{\"requestType\":\"FRAUGSTER_SIGNUP_SERVICE\",\"endPointUrl\":\"http://localhost:8080/fraugster-service/fraudDetection_signup\"}";
		try {
			when(rs.getString("Attribute")).thenReturn(attribute);
			when(rs.getString("Code")).thenReturn("FRAUGSTER_SIGNUP_SERVICE");
		} catch (SQLException e) {	
			e.printStackTrace();
		}
		
	}
	public ConcurrentMap<String, FraugsterProviderProperty> getMap()
	{
		ConcurrentMap<String, FraugsterProviderProperty> hm = new ConcurrentHashMap<>();
		FraugsterProviderProperty property = new FraugsterProviderProperty();
		property.setEndPointUrl("http://localhost:8080/fraugster-service/fraudDetection_signup");
		String providerName = "FRAUGSTER_SIGNUP_SERVICE";
		hm.put(providerName, property);
		return hm;
	}
	
	@Test
	public void testGetFraugsterProviderInitConfigProperty() {
		try {
			setMock();
			ConcurrentMap<String, FraugsterProviderProperty> expectedResult =getMap();
			ConcurrentMap<String, FraugsterProviderProperty> actualResult = fraugsterDBServiceImpl.getFraugsterProviderInitConfigProperty();
           assertEquals(expectedResult.get("FRAUGSTER_SIGNUP_SERVICE").getEndPointUrl(),actualResult.get("FRAUGSTER_SIGNUP_SERVICE").getEndPointUrl());
		} catch (FraugsterException e) {
			System.out.println(e);
		}		
	}

	@Test
	public void testForGetFraugsterProviderInitConfigProperty() {
		try {
			 fraugsterDBServiceImpl.getFraugsterProviderInitConfigProperty();
		} catch (FraugsterException e) {
			assertEquals(FraugsterErrors.ERROR_WHILE_LOADING_PROVIDER_INIT_DATA.getErrorCode(),e.getMessage());
		}
	}
}
