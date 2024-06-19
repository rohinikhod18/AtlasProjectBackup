package com.currenciesdirect.gtg.compliance.core.countrycheck;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.CountryCheckContactResponse;
import com.currenciesdirect.gtg.compliance.dbport.countrycheck.CountryCheckDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckErrors;
import com.currenciesdirect.gtg.compliance.exception.countrycheck.CountryCheckException;

public class CountryCheckSearchImplTest {

	@Mock
	private ICountryCheckDBService checkDBService = CountryCheckDBServiceImpl.getInstance();

	@InjectMocks
	private static ICountryCheckSearch countryCheckSearch = CountryCheckSearchImpl.getInstance();

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
	}

	public void setMock() {
		CountryCheckContactResponse response = new CountryCheckContactResponse();
		response.setStatus("PASS");
		try {
			when(checkDBService.getCountryCheckResponse(anyString())).thenReturn(response);
		} catch (CountryCheckException e) {
			System.out.println(e);
		}
	}

	public void setMockData() {
		try {
			CountryCheckException e = new CountryCheckException(CountryCheckErrors.ERROR_WHILE_SEARCHING_DATA);
			when(checkDBService.getCountryCheckResponse(anyString())).thenThrow(e);
		} catch (CountryCheckException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetCountryCheckResponse() {
		try {
			setMock();
			CountryCheckContactResponse expectedResult = new CountryCheckContactResponse();
			expectedResult.setStatus("PASS");
			CountryCheckContactResponse actualResult = countryCheckSearch.getCountryCheckResponse("United Kingdom");
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (CountryCheckException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetCountryCheckResponse() {
		try {
			setMockData();
			countryCheckSearch.getCountryCheckResponse("United Kingdom");
		} catch (CountryCheckException e) {
			assertEquals(CountryCheckErrors.ERROR_WHILE_SEARCHING_DATA.getErrorCode(), e.getMessage());
		}
	}
}
