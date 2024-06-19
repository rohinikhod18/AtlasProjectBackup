/*
 * package com.currenciesdirect.gtg.compliance.kyc.restport;
 * 
 * import static org.junit.Assert.assertEquals; import static
 * org.mockito.Matchers.anyObject; import static org.mockito.Matchers.anyString;
 * import static org.mockito.Mockito.when; import java.util.ArrayList; import
 * java.util.List; import java.util.UUID; import javax.ws.rs.core.HttpHeaders;
 * import javax.ws.rs.core.Response; import org.junit.Before; import
 * org.junit.Test; import org.mockito.InjectMocks; import org.mockito.Mock;
 * import org.mockito.MockitoAnnotations; import
 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address; import
 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
 * import
 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
 * import
 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
 * import
 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
 * import
 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
 * import com.currenciesdirect.gtg.compliance.kyc.core.IKYCService; import
 * com.currenciesdirect.gtg.compliance.kyc.core.KYCCacheDataStructure; import
 * com.currenciesdirect.gtg.compliance.kyc.core.KYCConcreteDataBuilder; import
 * com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
 * import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors; import
 * com.currenciesdirect.gtg.compliance.kyc.exception.KYCException; import
 * com.currenciesdirect.gtg.compliance.kyc.util.Constants;
 * 
 * 
 * public class RestPortTest {
 * 
 * @InjectMocks RestPort restPort;
 * 
 * @Mock IKYCService kycService;
 * 
 * @Mock KYCProviderRequest kYCProviderRequest;
 * 
 * @Mock KYCConcreteDataBuilder concreteDataBuilder;
 * 
 * @Mock KYCCacheDataStructure kYCCacheDataStructure;
 * 
 * @Before public void setup() { MockitoAnnotations.initMocks(this); try {
 * when(concreteDataBuilder.getProviderForCountry(anyString())).thenReturn(
 * "GBGROUP");
 * when(concreteDataBuilder.getProviderInitConfigProperty(anyString())).
 * thenReturn(providerPropertyResponceMocking());
 * when(kYCCacheDataStructure.getProviderinitConfigMap(anyString())).thenReturn(
 * providerPropertyResponceMocking());
 * 
 * } catch (KYCException e) { System.out.println(e); } }
 * 
 * public void responceMocking() { try {
 * when(kycService.checkKYC(anyObject())).thenReturn(kycResponseMocking()); }
 * catch (KYCException e) { System.out.println(e); } } public void
 * responceMock() { try { KYCException e=new
 * KYCException(KYCErrors.KYC_GENERIC_EXCEPTION);
 * when(kycService.checkKYC(anyObject())).thenThrow(e); } catch (KYCException e)
 * { System.out.println(e); } }
 * 
 * private KYCProviderProperty providerPropertyResponceMocking() {
 * KYCProviderProperty kYCProviderProperty = new KYCProviderProperty();
 * kYCProviderProperty.setAlwaysPass(false); return kYCProviderProperty; }
 * 
 * public KYCProviderRequest kYCProviderRequestMocking() { KYCProviderRequest
 * kYCProviderRequest = new KYCProviderRequest();
 * kYCProviderRequest.setSourceApplication("Atlas");
 * kYCProviderRequest.setRequestType("individual");
 * kYCProviderRequest.setOrgCode("TorFXOz"); KYCContactRequest kYCContactRequest
 * = new KYCContactRequest(); List<KYCContactRequest> contact = new
 * ArrayList<>(); kYCContactRequest.setContactSFId("0030O000029F9O1QAK");
 * 
 * Address address = new Address(); address.setAreaNumber("British");
 * address.setPostCode("V2Z 2T7"); address.setAza("Langley");
 * address.setBuildingName("48 Ave"); address.setBuildingNumber("de8764");
 * address.setAddressLine1("2204 Glencair"); address.setCountry("Nor");
 * address.setCity("city"); address.setState("State/Province");
 * address.setStreet("gtd24"); kYCContactRequest.setAddress(address);
 * 
 * PersonalDetails personalDetails = new PersonalDetails();
 * personalDetails.setForeName("Chadsley");
 * personalDetails.setSurName("Atkins"); personalDetails.setDob("1996-09-19");
 * kYCContactRequest.setPersonalDetails(personalDetails);
 * 
 * contact.add(kYCContactRequest);
 * kYCProviderRequest.setAccountSFId("0010O00001v2ntLQAQ");
 * kYCProviderRequest.setContact(contact);
 * kYCProviderRequest.setCorrelationID(UUID.randomUUID());
 * 
 * return kYCProviderRequest;
 * 
 * }
 * 
 * private KYCProviderResponse kycResponseMocking() { KYCProviderResponse
 * kycProviderResponse = new KYCProviderResponse();
 * kycProviderResponse.setAccountId("0010O00001v2ntLQAQ");
 * kycProviderResponse.setStatus("Success");
 * kycProviderResponse.setOrgCode("TorFXOz"); KYCContactResponse
 * kYCContactResponse = new KYCContactResponse(); List<KYCContactResponse>
 * contactResponse = new ArrayList<>(); kYCContactResponse.setStatus("pass");
 * kYCContactResponse.setId(824169); contactResponse.add(kYCContactResponse);
 * kycProviderResponse.setContactResponse(contactResponse);
 * 
 * return kycProviderResponse; }
 * 
 * private KYCProviderResponse kycResponseMock() { KYCProviderResponse
 * kycProviderResponse = new KYCProviderResponse();
 * kycProviderResponse.setAccountId("0010O00001v2ntLQAQ");
 * kycProviderResponse.setStatus(Constants.SERVICE_FAILURE);
 * kycProviderResponse.setOrgCode("TorFXOz"); KYCContactResponse
 * kYCContactResponse = new KYCContactResponse(); List<KYCContactResponse>
 * contactResponse = new ArrayList<>();
 * kYCContactResponse.setStatus(Constants.SERVICE_FAILURE);
 * kYCContactResponse.setId(824169); contactResponse.add(kYCContactResponse);
 * kycProviderResponse.setContactResponse(contactResponse);
 * 
 * return kycProviderResponse; }
 * 
 * @Test public void testCheckKYC() { responceMocking(); long time =
 * System.currentTimeMillis(); Response
 * expectedResult=Response.status(200).entity(kycResponseMocking()).header(
 * "requestReceived", time).build(); HttpHeaders headers = null;
 * KYCProviderRequest request = kYCProviderRequestMocking(); Response
 * kycProviderResponse=restPort.checkKYC(headers, request);
 * assertEquals(expectedResult.getStatus(),kycProviderResponse.getStatus()); }
 * 
 * @Test public void testForCheckKYC() { responceMock(); long time =
 * System.currentTimeMillis(); Response
 * expectedResult=Response.status(200).entity(kycResponseMock()).header(
 * "requestReceived", time).build(); KYCProviderResponse
 * expectedResultProResponse=(KYCProviderResponse) expectedResult.getEntity();
 * HttpHeaders headers = null; KYCProviderRequest request =
 * kYCProviderRequestMocking(); Response actualResult=restPort.checkKYC(headers,
 * request); KYCProviderResponse kycProviderResponse=(KYCProviderResponse)
 * actualResult.getEntity();
 * assertEquals(expectedResultProResponse.getErrorCode(),kycProviderResponse.
 * getErrorCode()); } }
 */