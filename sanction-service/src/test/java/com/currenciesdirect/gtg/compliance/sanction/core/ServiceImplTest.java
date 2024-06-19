package com.currenciesdirect.gtg.compliance.sanction.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionResponse;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
import com.currenciesdirect.gtg.compliance.sanction.util.Constants;

public class ServiceImplTest {
	
	@InjectMocks
	ServiceImpl ServiceImpl;

	@Mock
	ISanctionvalidator iSanctionvalidator;
	
	@Mock
	ISanctionService iSanctionService;
	
	@Mock
	SanctionConcreteDataBuilder concreteDataBuilder;
	
	@Mock
	ISanctionService iservicePort;
	
	@Mock
	ExecutorService sanctionExecutors;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	public void responceMocking() throws SanctionException, InterruptedException {
		SanctionRequest sanctionRequest = requestMocking();
		ProviderProperty providerProperty = providerPropertyResponceMocking();
		when(iSanctionvalidator.validateRequest(sanctionRequest)).thenReturn(fieldValidatorResponceMocking());
		when(concreteDataBuilder.getProviderInitConfigProperty(Constants.FINSCAN_PROVIDER)).thenReturn(providerPropertyResponceMocking());
		when(iservicePort.checkSanctionDetails(sanctionRequest, providerProperty)).thenReturn(sanctionsResponseMocking());
		when(sanctionExecutors.invokeAll(anyList())).thenReturn(taskMocking());
	}
	
	public List<IDomain> taskMocking() {
		SanctionResponse sanctionResponse = new SanctionResponse();
		List<IDomain> task= new ArrayList<>();
		sanctionResponse.setProviderResponse("{\\\"status\\\":\\\"PASS\\\",\\\"message\\\":\\\"Lookup PASSED\\\",\\\"returned\\\":0,\\\"notReturned\\\":0,\\\"resultsCount\\\":0,\\\"hitCount\\\":0,\\\"pendingCount\\\":0,\\\"safeCount\\\":0,\\\"version\\\":\\\"v4.8.1.2 - Released January 14, 2020\\\",\\\"responses\\\":{\\\"lstservicesLookupResponse\\\":[{\\\"status\\\":\\\"PASS\\\",\\\"message\\\":\\\"Lookup PASSED\\\",\\\"returned\\\":0,\\\"notReturned\\\":0,\\\"resultsCount\\\":0,\\\"hitCount\\\":0,\\\"pendingCount\\\":0,\\\"safeCount\\\":0,\\\"complianceRecords\\\":{\\\"slcomplianceRecord\\\":[]},\\\"clientId\\\":\\\"002-C-0000048357\\\",\\\"clientKey\\\":426332402,\\\"version\\\":\\\"v4.8.1.2 - Released January 14, 2020\\\",\\\"isiReserved\\\":\\\"\\\",\\\"searchResults\\\":{\\\"slsearchResults\\\":[{\\\"searchName\\\":\\\"Organization Name Search\\\",\\\"clientId\\\":\\\"002-C-0000048357\\\",\\\"clientName\\\":\\\"Geoffrey kabanda\\\",\\\"returned\\\":0,\\\"notReturned\\\":0,\\\"sequenceNumber\\\":9405,\\\"searchDateTime\\\":1600137438393,\\\"searchMatches\\\":{\\\"slsearchListRecord\\\":[]}}]},\\\"uboResults\\\":null}]},\\\"isiReserved\\\":\\\"\\\"}");
		SanctionContactResponse sanctionContactResponse = new SanctionContactResponse();
		sanctionContactResponse.setStatus("PASS");
		sanctionContactResponse.setStatusDescription("Lookup PASSED");
		sanctionContactResponse.setSanctionId("002-C-0000048357");
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		contactResponses.add(sanctionContactResponse);
		sanctionResponse.setContactResponses(contactResponses);
		task.add(0,sanctionResponse);
		return task;
	}

	public SanctionResponse sanctionsResponseMocking() {
		SanctionResponse sanctionResponse = new SanctionResponse();
		SanctionContactResponse sanctionContactResponse = new SanctionContactResponse();
		sanctionContactResponse.setStatus("PASS");
		sanctionContactResponse.setStatusDescription("Lookup PASSED");
		sanctionContactResponse.setSanctionId("002-C-0000048357");
		sanctionContactResponse.setContactId(76467676);
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		contactResponses.add(sanctionContactResponse);
		sanctionResponse.setContactResponses(contactResponses);
		
		sanctionResponse.setProviderResponse("{\"status\":\"PASS\",\"message\":\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"version\":\"v4.8.1.2 - Released January 14, 2020\",\"responses\":{\"lstservicesLookupResponse\":[{\"status\":\"PASS\",\"message\":\"Lookup PASSED\",\"returned\":0,\"notReturned\":0,\"resultsCount\":0,\"hitCount\":0,\"pendingCount\":0,\"safeCount\":0,\"complianceRecords\":{\"slcomplianceRecord\":[]},\"clientId\":\"002-C-0000048357\",\"clientKey\":426332402,\"version\":\"v4.8.1.2 - Released January 14, 2020\",\"isiReserved\":\"\",\"searchResults\":{\"slsearchResults\":[{\"searchName\":\"Organization Name Search\",\"clientId\":\"002-C-0000048357\",\"clientName\":\"Geoffrey kabanda\",\"returned\":0,\"notReturned\":0,\"sequenceNumber\":9405,\"searchDateTime\":1600137438393,\"searchMatches\":{\"slsearchListRecord\":[]}}]},\"uboResults\":null}]},\"isiReserved\":\"\"}");
		
		return sanctionResponse;
	}

	public ProviderProperty providerPropertyResponceMocking() {
		ProviderProperty providerProperty = new ProviderProperty();
		providerProperty.setAlwaysPass(false);
		providerProperty.setConnectionTimeoutMillis(12000);
		providerProperty.setEndPointUrl("https://hosted5.finscan.com/isi_test/wrapper/v4.8.1/LSTServicesLookup.asmx?wsdl");
		providerProperty.setPassWord("webservices12");
		providerProperty.setUserName("webservices");
		return providerProperty;
	}

	public FieldValidator fieldValidatorResponceMocking() {
		FieldValidator fieldValidator = new FieldValidator();
		String message = "";
		String errorField = "";
		fieldValidator.addError(message, errorField);
		return fieldValidator;
	}

	public SanctionRequest requestMocking() {
		SanctionRequest sanctionRequest = new SanctionRequest();
		sanctionRequest.setCustomerType("PFX");
		SanctionContactRequest sanctionContactRequest = new SanctionContactRequest();
		List<SanctionContactRequest> contactrequests = new ArrayList<>();
		sanctionContactRequest.setContactId(6638905);
		sanctionContactRequest.setPreviousOfac("Not Required");
		sanctionContactRequest.setFullName("Damon Salvatore");
		sanctionContactRequest.setPreviousWorldCheck("Not Required");
		contactrequests.add(sanctionContactRequest);
		sanctionRequest.setContactrequests(contactrequests);
		return sanctionRequest;
	}

	@Test
	public void testGetSanctionContactResponseById() {
		SanctionResponse sanctionResponse = sanctionsResponseMocking();
		SanctionContactResponse contactResponse = ServiceImpl.getSanctionContactResponseById(sanctionResponse,76467676);
		assertEquals(sanctionResponse.getContactResponses().get(0).getContactId(),contactResponse.getContactId());
	}

	@Test
	public void testGetSanctionContactResponseIndexById() {
		int id= 76467676;
		SanctionResponse sanctionResponse = sanctionsResponseMocking();
		int i = ServiceImpl.getSanctionContactResponseIndexById(sanctionResponse,id);
		assertEquals(0, i);
	}

	

}
