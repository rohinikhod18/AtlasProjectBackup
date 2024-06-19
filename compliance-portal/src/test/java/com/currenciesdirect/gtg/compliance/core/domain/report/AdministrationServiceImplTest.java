package com.currenciesdirect.gtg.compliance.core.domain.report;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.report.IAdministrationDBSerivce;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class AdministrationServiceImplTest {

	@InjectMocks
	AdministrationServiceImpl administrationServiceImpl;

	@Mock
	IAdministrationDBSerivce iAdministrationService;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	public AdministrationDto getAdministrationDto() {
		List<String> countries = new ArrayList<>();
		countries.add("United Kingdom");
		countries.add("USA");
		countries.add("Spain");
		countries.add("United Arab Emirates");
		countries.add("Australia");
		AdministrationDto administrationDto = new AdministrationDto();
		administrationDto.setCountries(countries);
		return administrationDto;
	}

	public void setMockDataForGetAdministration() {
		try {
			when(iAdministrationService.getAdministration()).thenReturn(getAdministrationDto());
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	public void setMockForGetAdministration() {
		try {
			CompliancePortalException e = new CompliancePortalException(
					CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA);
			when(iAdministrationService.getAdministration()).thenThrow(e);
		} catch (CompliancePortalException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetAdministration() {
		setMockDataForGetAdministration();
		AdministrationDto expectedResult = getAdministrationDto();
		AdministrationDto actualResult = administrationServiceImpl.getAdministration();
		assertEquals(expectedResult.getCountries().get(0), actualResult.getCountries().get(0));

	}

	@Test
	public void testForGetAdministration() {
		setMockForGetAdministration();
		AdministrationDto actualResult = administrationServiceImpl.getAdministration();
		assertEquals(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA.getErrorCode(), actualResult.getErrorCode());
	}
}