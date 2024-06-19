/*
 * package com.currenciesdirect.gtg.compliance.sanction.finscanport;
 * 
 * import static org.junit.Assert.*; import static
 * org.mockito.Matchers.anyBoolean; import static
 * org.mockito.Matchers.anyObject; import static org.mockito.Mockito.when;
 * import org.junit.Before; import org.junit.Test; import
 * org.mockito.InjectMocks; import org.mockito.Mock; import
 * org.mockito.MockitoAnnotations; import
 * com.currenciesdirect.gtg.compliance.commons.domain.config.ProviderProperty;
 * import com.currenciesdirect.gtg.compliance.sanction.core.domain.
 * SanctionGetStatusRequest; import
 * com.currenciesdirect.gtg.compliance.sanction.core.domain.
 * SanctionGetStatusResponse; import
 * com.currenciesdirect.gtg.compliance.sanction.exception.SanctionErrors; import
 * com.currenciesdirect.gtg.compliance.sanction.exception.SanctionException;
 * import com.innovativesystems.LSTServicesGetStatusRequest; import
 * com.innovativesystems.LSTServicesLookup; import
 * com.innovativesystems.LSTServicesLookupSoap;
 * 
 * public class FinscanPortTest {
 * 
 * @InjectMocks FinscanPort finscanPort;
 * 
 * @Mock FinscanTransformer finscanTransformer;
 * 
 * @Mock LSTServicesLookup lSTServicesLookup;
 * 
 * @Before public void setup() { MockitoAnnotations.initMocks(this); }
 * 
 * public void responceMocking() throws SanctionException {
 * SanctionGetStatusRequest statusRequest = sanctionGetStatusRequestMocking();
 * ProviderProperty providerProperty = providerPropertyResponceMocking();
 * when(finscanTransformer.transformGetContactStatusRequest(statusRequest,
 * providerProperty)).thenReturn(lSTServicesGetStatusRequestMocking());
 * when(lSTServicesLookup.getLSTServicesLookupSoap()).thenReturn(
 * serviceLookUpStubMocking());
 * when(finscanTransformer.transformContactStatusResponse(anyObject(),
 * anyObject(), anyBoolean())).thenReturn(sanctionResponseMocking()); }
 * 
 * private SanctionGetStatusResponse sanctionResponseMocking() {
 * SanctionGetStatusResponse sanctionGetStatusResponse = new
 * SanctionGetStatusResponse();
 * sanctionGetStatusResponse.setApplicationId("CLNTS");
 * sanctionGetStatusResponse.setOfacList("Not Available");
 * sanctionGetStatusResponse.setWorldCheck("Not Available");
 * sanctionGetStatusResponse.setStatus("Pass");
 * sanctionGetStatusResponse.setStatusDescription("Lookup PASSED"); return
 * sanctionGetStatusResponse; }
 * 
 * public LSTServicesLookupSoap serviceLookUpStubMocking() { return null; }
 * 
 * public LSTServicesGetStatusRequest lSTServicesGetStatusRequestMocking() {
 * LSTServicesGetStatusRequest lSTServicesGetStatusRequest = new
 * LSTServicesGetStatusRequest();
 * lSTServicesGetStatusRequest.setApplicationId("CLNTS");
 * lSTServicesGetStatusRequest.setClientId("002-C-0000048357");
 * lSTServicesGetStatusRequest.setOrganizationName("CurrenciesDirect");
 * lSTServicesGetStatusRequest.setUserName("webservices"); return
 * lSTServicesGetStatusRequest;
 * 
 * }
 * 
 * public SanctionGetStatusRequest sanctionGetStatusRequestMocking() {
 * SanctionGetStatusRequest statusRequest = new SanctionGetStatusRequest();
 * statusRequest.setCountry("GBR"); statusRequest.setDob("1989-08-02");
 * statusRequest.setGender("Male");
 * statusRequest.setFullName("Damon Salvatore"); statusRequest.setId(5482156);
 * statusRequest.setSanctionId("002-C-0000048357");
 * statusRequest.setApplicationId(""); return statusRequest; }
 * 
 * public ProviderProperty providerPropertyResponceMocking() { ProviderProperty
 * providerProperty = new ProviderProperty();
 * providerProperty.setAlwaysPass(false);
 * providerProperty.setConnectionTimeoutMillis(12000);
 * providerProperty.setEndPointUrl(
 * "https://hosted5.finscan.com/isi_test/wrapper/v4.8.1/LSTServicesLookup.asmx?wsdl"
 * ); providerProperty.setPassWord("webservices12");
 * providerProperty.setUserName("webservices"); return providerProperty; }
 * public ProviderProperty providerPropertyResponce() { ProviderProperty
 * providerProperty = new ProviderProperty();
 * providerProperty.setAlwaysPass(false);
 * providerProperty.setConnectionTimeoutMillis(12000);
 * providerProperty.setPassWord("webservices12");
 * providerProperty.setUserName("webservices"); return providerProperty; }
 * 
 * @Test public void testGetSanctionStatus() {
 * 
 * try { responceMocking(); SanctionGetStatusResponse expectedSanctionResponse =
 * sanctionResponseMocking(); SanctionGetStatusResponse
 * sanctionResponseMocking=finscanPort.getSanctionStatus(
 * sanctionGetStatusRequestMocking(),providerPropertyResponceMocking());
 * assertEquals(expectedSanctionResponse.getOfacList(),
 * sanctionResponseMocking.getOfacList()); } catch (SanctionException e) {
 * System.out.println(e); } }
 * 
 * @Test public void testForGetSanctionStatus() {
 * 
 * try { responceMocking();
 * finscanPort.getSanctionStatus(sanctionGetStatusRequestMocking(),
 * providerPropertyResponce() ); } catch (SanctionException e) {
 * assertEquals(SanctionErrors.ERRROR_IN_FINSCAN_PORT.getErrorDescription(),e.
 * getMessage()); } } }
 */