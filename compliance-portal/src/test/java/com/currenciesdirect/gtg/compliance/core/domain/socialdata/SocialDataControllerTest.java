package com.currenciesdirect.gtg.compliance.core.domain.socialdata;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.currenciesdirect.gtg.compliance.core.report.ISocialDataService;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;

public class SocialDataControllerTest {
	
	@InjectMocks
	private SocialDataController socialDataController;

	@Mock
	ISocialDataService iSocialDataServiceResponse;
	
	@Mock
	SocialDataRequest socialDataRequest;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private SocialDataRequest responceMockingForSocialDataRequest() {
		SocialDataRequest socialDataRequest = new SocialDataRequest();
		socialDataRequest.setEmailId("salvatore@mailinator.com");
		return socialDataRequest;
	}
	
	public SocialDataResponse responceMockingForGetSocialData() throws CompliancePortalException {
		SocialDataResponse socialDataResponse = null;
		SocialDataRequest socialDataRequest = responceMockingForSocialDataRequest();
		when(iSocialDataServiceResponse.getSocialData(socialDataRequest)).thenReturn(socialDataResponseMock());
		return socialDataResponse;
	}
	
	private SocialDataResponse socialDataResponseMock() {
		SocialDataResponse socialDataResponse = new SocialDataResponse();
		socialDataResponse.setFullName("George Salvatore");
		socialDataResponse.setLocation("Momintola");
		socialDataResponse.setGender("male");
		socialDataResponse.setTitle("Mr");
		socialDataResponse.setOrganization("TorFx");
		socialDataResponse.setLinkedin("--");
		socialDataResponse.setBio("--");
		socialDataResponse.setUpdated("--");
		return socialDataResponse;
	}

	@Test
	public void testGetSocialData() throws CompliancePortalException {
		SocialDataResponse actualSocialDataResponse;
		SocialDataResponse expectedSocialDataResponse;
		SocialDataRequest socialDataRequest = responceMockingForSocialDataRequest();
		expectedSocialDataResponse = responceMockingForGetSocialData();
		actualSocialDataResponse = socialDataController.getSocialData(socialDataRequest);
		assertSame(expectedSocialDataResponse, actualSocialDataResponse);
	}
}
