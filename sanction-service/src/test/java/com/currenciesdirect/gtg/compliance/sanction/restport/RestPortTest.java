package com.currenciesdirect.gtg.compliance.sanction.restport;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBankResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionBeneficiaryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.sanction.core.IService;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;

public class RestPortTest {
	
	@InjectMocks
	RestPort restPort;

	@Mock
	IService iService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public void responceMocking() {
		try {
			when(iService.getSanctionDetails(anyObject())).thenReturn(sanctionResponseMocking());
		} catch (SanctionException e) {
			System.out.println(e);
		}
	}
	public void responceMock() {
		try {
			SanctionException sanctionException= new SanctionException(SanctionErrors.ERRROR_IN_FINSCAN_PORT);
			when(iService.getSanctionDetails(anyObject())).thenThrow(sanctionException);
		} catch (SanctionException e) {
			System.out.println(e);
		}
	}
	public SanctionRequest sanctionRequestMocking() {
		SanctionRequest sanctionRequest = new SanctionRequest();
		sanctionRequest.setCustomerType("PFX");
		sanctionRequest.setSourceApplication("Atlas");
		sanctionRequest.setCustomerNumber("0201000009948876");
		sanctionRequest.setOrgCode("Currencies Direct");
		
		SanctionContactRequest sanctionContactRequest = new SanctionContactRequest();
		List<SanctionContactRequest> contactrequests = new ArrayList<>();
		sanctionContactRequest.setContactId(6638905);
		sanctionContactRequest.setSanctionId("002-C-0000048357");
		sanctionContactRequest.setCountry("Canada");
		sanctionContactRequest.setDob("1983-12-24");
		sanctionContactRequest.setPreviousOfac("NOT_AVAILABLE");
		sanctionContactRequest.setPreviousWorldCheck("NOT_AVAILABLE");
		sanctionContactRequest.setFullName("Damon Salvatore");
		sanctionContactRequest.setCategory("Individual");
		sanctionContactRequest.setIsExisting(false);
		contactrequests.add(sanctionContactRequest);
		sanctionRequest.setContactrequests(contactrequests);
		
		SanctionBankRequest sanctionBankRequest = new SanctionBankRequest();
		List<SanctionBankRequest> bankRequests = new ArrayList<>();
		bankRequests.add(sanctionBankRequest);
		sanctionRequest.setBankRequests(bankRequests);
		
		SanctionBeneficiaryRequest sanctionBeneficiaryRequest = new SanctionBeneficiaryRequest();
		List<SanctionBeneficiaryRequest> beneficiaryRequests = new ArrayList<>();
		beneficiaryRequests.add(sanctionBeneficiaryRequest);
		sanctionRequest.setBeneficiaryRequests(beneficiaryRequests);
		
		return sanctionRequest;
	}

	public SanctionResponse sanctionResponseMocking() {
		SanctionResponse sanctionResponse = new SanctionResponse();
		SanctionContactResponse sanctionContactResponse = new SanctionContactResponse();
		sanctionContactResponse.setStatus("PASS");
		sanctionContactResponse.setStatusDescription("Lookup PASSED");
		sanctionContactResponse.setSanctionId("002-C-0000048357");
		sanctionContactResponse.setContactId(76467676);
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		contactResponses.add(sanctionContactResponse);
		sanctionResponse.setContactResponses(contactResponses);
		
		SanctionBankResponse sanctionBankResponce = new SanctionBankResponse();
		List<SanctionBankResponse> bankResponce = new ArrayList<>();
		bankResponce.add(sanctionBankResponce);
		sanctionResponse.setBankResponses(bankResponce);
		
		SanctionBeneficiaryResponse sanctionBeneficiaryResponce = new SanctionBeneficiaryResponse();
		List<SanctionBeneficiaryResponse> beneficiaryResponce = new ArrayList<>();
		beneficiaryResponce.add(sanctionBeneficiaryResponce);
		sanctionResponse.setBeneficiaryResponses(beneficiaryResponce);
		
		sanctionResponse.setProviderResponse("{\"status\":\"PASS\",\"message\":\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"version\":\"v4.8.1.2 - Released January 14, 2020\",\"responses\":{\"lstservicesLookupResponse\":[{\"status\":\"PASS\",\"message\":\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"complianceRecords\":{\"slcomplianceRecord\":[]},\"clientId\":\"002-C-0000048357\",\"clientKey\":426332402,\"version\":\"v4.8.1.2 - Released January 14, 2020\",\"isiReserved\":\"\",\"searchResults\":{\"slsearchResults\":[{\"searchName\":\"Organization Name Search\",\"clientId\":\"002-C-0000048357\",\"clientName\":\"Geoffrey kabanda\",\"returned\":0,\"notReturned\":0,\"sequenceNumber\":9405,\"searchDateTime\":1600137438393,\"searchMatches\":{\"slsearchListRecord\":[]}}]},\"uboResults\":null}]},\"isiReserved\":\"\"}");
		sanctionResponse.setResponseCode("000");
		sanctionResponse.setResponseDescription("pass");
		sanctionResponse.setCorrelationId("26b4c045");
		sanctionResponse.setOrgCode("Currencies Direct");
		return sanctionResponse;
	}

	/*@Test
	public void testCheckFundoutSanctions() {
		SanctionResponse expectedResponse=sanctionResponseMocking();
		Response expectedResult=Response.status(200).entity(expectedResponse).build();
		try {
			responceMocking();
			Response sanctionResponse = restPort.checkFundoutSanctions("26b4c045", sanctionRequestMocking());
			assertEquals(expectedResult.getStatus(),sanctionResponse.getStatus());
		} catch (SanctionException e) {
			System.out.println(e);
		}
	}*/
	/*@Test
	public void testForCheckFundoutSanctions() {
		try {
			responceMock();
			Response sanctionResponse = restPort.checkFundoutSanctions("26b4c045", sanctionRequestMocking());
			SanctionResponse expectedResponse=(SanctionResponse) sanctionResponse.getEntity();
	        assertEquals(SanctionErrors.ERRROR_IN_FINSCAN_PORT.getErrorCode(), expectedResponse.getErrorCode());
		} catch (SanctionException e) {
			System.out.println(e);
		}
	}*/
}
