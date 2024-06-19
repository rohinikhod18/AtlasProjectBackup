package com.currenciesdirect.gtg.compliance.core.whitelistbeneficiary;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.WhitelistBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.dbport.whitelistbeneficiary.WhitelistBeneficiaryDBServiceImpl;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleErrors;
import com.currenciesdirect.gtg.compliance.exception.InternalRuleException;

public class WhitelistBeneficiaryModifierImplTest {

	@InjectMocks
	WhitelistBeneficiaryModifierImpl whitelistBeneficiaryModifierImpl;

	@Mock
	private IWhitelistBeneficiaryDBService iDBService = WhitelistBeneficiaryDBServiceImpl.getInstance();

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
	}

	public WhitelistBeneficiaryRequest getWhitelistBeneficiaryRequest() {
		WhitelistBeneficiaryRequest whitelistBeneficiaryRequest = new WhitelistBeneficiaryRequest();
		whitelistBeneficiaryRequest.setAccountNumber("73152391200517");
		whitelistBeneficiaryRequest.setFirstName("Hmrc Vat");
		return whitelistBeneficiaryRequest;

	}

	public WhitelistBeneficiaryResponse setWhitelistBeneficiaryResponse() {
		WhitelistBeneficiaryResponse whitelistBeneficiaryResponse = new WhitelistBeneficiaryResponse();
		whitelistBeneficiaryResponse.setStatus("Success");
		return whitelistBeneficiaryResponse;
	}

	public void setMockForSaveIntoWhitelistBeneficiaryData() {
		try {
			when(iDBService.saveIntoWhitelistBeneficiary(anyObject())).thenReturn(setWhitelistBeneficiaryResponse());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public void setMockSaveIntoWhitelistBeneficiaryData() {
		try {
			InternalRuleException e = new InternalRuleException(InternalRuleErrors.DATABASE_GENERIC_ERROR);
			when(iDBService.saveIntoWhitelistBeneficiary(anyObject())).thenThrow(e);
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public List<WhitelistBeneficiaryResponse> getWhitelistBeneficiaryResponse() {
		WhitelistBeneficiaryResponse whitelistBeneficiaryResponse = new WhitelistBeneficiaryResponse();
		whitelistBeneficiaryResponse.setFirstName("Hmrc Vat");
		whitelistBeneficiaryResponse.setCreatedOn("2018-04-30 12:06:28");
		whitelistBeneficiaryResponse.setAccountNumber("73152391200517");
		List<WhitelistBeneficiaryResponse> whitelistBeneficiaryResponseList = new ArrayList<>();
		whitelistBeneficiaryResponseList.add(whitelistBeneficiaryResponse);
		return whitelistBeneficiaryResponseList;

	}

	public void setMockForGetWhitelistBeneficiaryData() {
		try {
			when(iDBService.getWhitelistBeneficiaryData(anyObject())).thenReturn(getWhitelistBeneficiaryResponse());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public void setMockGetWhitelistBeneficiaryData() {
		try {
			InternalRuleException e = new InternalRuleException(InternalRuleErrors.FAILED);
			when(iDBService.getWhitelistBeneficiaryData(anyObject())).thenThrow(e);
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public void setMockForDeleteFromWhiteListBeneficiaryData() {
		try {
			when(iDBService.deleteFromWhiteListBeneficiaryData(anyObject()))
					.thenReturn(setWhitelistBeneficiaryResponse());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public void setMockDeleteFromWhiteListBeneficiaryData() {
		try {
			InternalRuleException e = new InternalRuleException(InternalRuleErrors.FAILED);
			when(iDBService.deleteFromWhiteListBeneficiaryData(anyObject())).thenThrow(e);
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public void setMockForSearchWhiteListBeneficiaryData() {
		try {
			when(iDBService.searchWhiteListBeneficiaryData(anyObject())).thenReturn(getWhitelistBeneficiaryResponse());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	public void setMockSearchWhiteListBeneficiaryData() {
		try {
			InternalRuleException e = new InternalRuleException(InternalRuleErrors.FAILED);
			when(iDBService.searchWhiteListBeneficiaryData(anyObject())).thenThrow(e);
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testGetWhitelistBeneficiaryData() {
		try {
			setMockForGetWhitelistBeneficiaryData();
			List<WhitelistBeneficiaryResponse> expectedResult = getWhitelistBeneficiaryResponse();
			List<WhitelistBeneficiaryResponse> actualResult = whitelistBeneficiaryModifierImpl
					.getWhitelistBeneficiaryData(getWhitelistBeneficiaryRequest());
			assertEquals(expectedResult.get(0).getAccountNumber(), actualResult.get(0).getAccountNumber());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForGetWhitelistBeneficiaryData() {
		try {
			setMockGetWhitelistBeneficiaryData();
			whitelistBeneficiaryModifierImpl.getWhitelistBeneficiaryData(getWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.FAILED.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testSaveIntoWhitelistBeneficiaryData() {
		setMockForSaveIntoWhitelistBeneficiaryData();
		try {
			WhitelistBeneficiaryResponse expectedResult = setWhitelistBeneficiaryResponse();
			WhitelistBeneficiaryResponse actualResult = whitelistBeneficiaryModifierImpl
					.saveIntoWhitelistBeneficiaryData(getWhitelistBeneficiaryRequest());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForSaveIntoWhitelistBeneficiaryData() {
		setMockSaveIntoWhitelistBeneficiaryData();
		try {
			whitelistBeneficiaryModifierImpl.saveIntoWhitelistBeneficiaryData(getWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.DATABASE_GENERIC_ERROR.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testDeleteFromWhiteListBeneficiaryData() {
		try {
			setMockForDeleteFromWhiteListBeneficiaryData();
			WhitelistBeneficiaryResponse expectedResult = setWhitelistBeneficiaryResponse();
			WhitelistBeneficiaryResponse actualResult = whitelistBeneficiaryModifierImpl
					.deleteFromWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
			assertEquals(expectedResult.getStatus(), actualResult.getStatus());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForDeleteFromWhiteListBeneficiaryData() {
		try {
			setMockDeleteFromWhiteListBeneficiaryData();
			whitelistBeneficiaryModifierImpl.deleteFromWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.FAILED.getErrorCode(), e.getMessage());
		}
	}

	@Test
	public void testSearchWhiteListBeneficiaryData() {
		try {
			setMockForSearchWhiteListBeneficiaryData();
			List<WhitelistBeneficiaryResponse> expectedResult = getWhitelistBeneficiaryResponse();
			List<WhitelistBeneficiaryResponse> actualResult = whitelistBeneficiaryModifierImpl
					.searchWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
			assertEquals(expectedResult.get(0).getAccountNumber(), actualResult.get(0).getAccountNumber());
		} catch (InternalRuleException e) {
			System.out.println(e);
		}
	}

	@Test
	public void testForSearchWhiteListBeneficiaryData() {
		try {
			setMockSearchWhiteListBeneficiaryData();
			whitelistBeneficiaryModifierImpl.searchWhiteListBeneficiaryData(getWhitelistBeneficiaryRequest());
		} catch (InternalRuleException e) {
			assertEquals(InternalRuleErrors.FAILED.getErrorCode(), e.getMessage());
		}
	}
}
