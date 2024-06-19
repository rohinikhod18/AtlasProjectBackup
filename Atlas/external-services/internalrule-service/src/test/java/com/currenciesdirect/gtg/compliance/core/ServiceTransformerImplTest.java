package com.currenciesdirect.gtg.compliance.core;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.core.domain.blacklist.BlacklistSTPRequest;
import com.currenciesdirect.gtg.compliance.core.domain.globalcheck.StateRuleRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ip.IpServiceRequest;

public class ServiceTransformerImplTest {

	
	@InjectMocks
	ServiceTransformerImpl serviceTransformerImpl;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	private InternalServiceRequestData getInternalServiceRequestData()
	{
		InternalServiceRequestData internalServiceRequestData= new InternalServiceRequestData();
		internalServiceRequestData.setEmail("abc2133@gmail");
		internalServiceRequestData.setEntityType("CONTACT");
		internalServiceRequestData.setId(53962);
		internalServiceRequestData.setIpAddress("172.31.4.211");
		internalServiceRequestData.setIsEmailDomainCheckRequired(true);
		internalServiceRequestData.setIsWildCardSearch(false);
		internalServiceRequestData.setName("Micheal Norse");
		internalServiceRequestData.setOrgLegalEntity("CDLGB");;
		internalServiceRequestData.setPostCode("BD22 8EZ");
		List<String> phoneNoList= new ArrayList<>();
		phoneNoList.add("+44-7311123456");
		phoneNoList.add("");
		phoneNoList.add("");
		internalServiceRequestData.setPhoneNoList(phoneNoList);
		internalServiceRequestData.setCountry("USA");
		return internalServiceRequestData;
	}
	
	private BlacklistContactResponse setBlacklistContactResponse()
	{	
		BlacklistContactResponse blacklistContactResponse= new BlacklistContactResponse();
		List<BlacklistSTPData> data= new ArrayList<>();
		BlacklistSTPData blacklistSTPData = new BlacklistSTPData();
		blacklistSTPData.setValue("Micheal Norse");
		blacklistSTPData.setType("NAME");
		blacklistSTPData.setRequestType("CONTACT NAME");
		blacklistSTPData.setFound(false);
		blacklistSTPData.setMatch(0);
		blacklistSTPData.setMatchedData("No Match Found");
		BlacklistSTPData blacklistSTPData1 = new BlacklistSTPData();
		blacklistSTPData1.setValue("abc2133@gmail");
		blacklistSTPData1.setType("EMAIL");
		blacklistSTPData1.setRequestType("EMAIL");
		blacklistSTPData1.setFound(false);
		blacklistSTPData1.setMatch(0);
		blacklistSTPData1.setMatchedData("No Match Found");
		BlacklistSTPData blacklistSTPData2 = new BlacklistSTPData();
		
		blacklistSTPData2.setType("DOMAIN");
		blacklistSTPData2.setRequestType("DOMAIN");
		blacklistSTPData2.setFound(false);
		blacklistSTPData2.setMatch(0);
		blacklistSTPData2.setMatchedData("No Match Found");
		BlacklistSTPData blacklistSTPData3 = new BlacklistSTPData();
		blacklistSTPData3.setType("PHONE_NUMBER");
		blacklistSTPData3.setRequestType("PHONE_NUMBER");
		blacklistSTPData3.setFound(false);
		blacklistSTPData3.setMatch(0);
		blacklistSTPData3.setMatchedData("(No Match Found)");
		
		BlacklistSTPData blacklistSTPData4 = new BlacklistSTPData();
		blacklistSTPData4.setType("PHONE_NUMBER");
		blacklistSTPData4.setRequestType("PHONE_NUMBER");
		blacklistSTPData4.setFound(false);
		blacklistSTPData4.setMatch(0);
		blacklistSTPData4.setMatchedData("(No Match Found)");
		BlacklistSTPData blacklistSTPData5 = new BlacklistSTPData();
		blacklistSTPData5.setType("PHONE_NUMBER");
		blacklistSTPData5.setRequestType("PHONE_NUMBER");
		blacklistSTPData5.setFound(false);
		blacklistSTPData5.setMatch(0);
		blacklistSTPData5.setMatchedData("(No Match Found)");
		
		BlacklistSTPData blacklistSTPData6 = new BlacklistSTPData();
		blacklistSTPData6.setValue("172.31.4.211");
		blacklistSTPData6.setType("IPADDRESS");
		blacklistSTPData6.setRequestType("IPADDRESS");
		blacklistSTPData6.setFound(false);
		blacklistSTPData6.setMatch(0);
		blacklistSTPData6.setMatchedData("(No Match Found)");
		data.add(blacklistSTPData);
		data.add(blacklistSTPData1);
		data.add(blacklistSTPData2);
		data.add(blacklistSTPData3);
		data.add(blacklistSTPData4);
		data.add(blacklistSTPData5);
		data.add(blacklistSTPData6);
		blacklistContactResponse.setData(data);
		blacklistContactResponse.setStatus("Pass");
		return blacklistContactResponse;
		
	}
	
	private BlacklistContactResponse getBlacklistContactResponse()
	{	
		BlacklistContactResponse blacklistContactResponse= new BlacklistContactResponse();
		List<BlacklistSTPData> data= new ArrayList<>();
		BlacklistSTPData blacklistSTPData = new BlacklistSTPData();
		blacklistSTPData.setType("NAME");
		blacklistSTPData.setRequestType("CONTACT NAME");
		blacklistSTPData.setFound(false);
		blacklistSTPData.setMatch(0);
		blacklistSTPData.setMatchedData("No Match Found");
		BlacklistSTPData blacklistSTPData1 = new BlacklistSTPData();
		blacklistSTPData1.setType("EMAIL");
		blacklistSTPData1.setRequestType("EMAIL");
		blacklistSTPData1.setFound(false);
		blacklistSTPData1.setMatch(0);
		blacklistSTPData1.setMatchedData("No Match Found");
		BlacklistSTPData blacklistSTPData2 = new BlacklistSTPData();
		
		blacklistSTPData2.setType("DOMAIN");
		blacklistSTPData2.setRequestType("DOMAIN");
		blacklistSTPData2.setFound(false);
		blacklistSTPData2.setMatch(0);
		blacklistSTPData2.setMatchedData("No Match Found");
		BlacklistSTPData blacklistSTPData3 = new BlacklistSTPData();
		blacklistSTPData3.setType("PHONE_NUMBER");
		blacklistSTPData3.setRequestType("PHONE_NUMBER");
		blacklistSTPData3.setFound(false);
		blacklistSTPData3.setMatch(0);
		blacklistSTPData3.setMatchedData("(No Match Found)");
		
		BlacklistSTPData blacklistSTPData4 = new BlacklistSTPData();
		blacklistSTPData4.setType("PHONE_NUMBER");
		blacklistSTPData4.setRequestType("PHONE_NUMBER");
		blacklistSTPData4.setFound(false);
		blacklistSTPData4.setMatch(0);
		blacklistSTPData4.setMatchedData("(No Match Found)");
		BlacklistSTPData blacklistSTPData5 = new BlacklistSTPData();
		blacklistSTPData5.setType("PHONE_NUMBER");
		blacklistSTPData5.setRequestType("PHONE_NUMBER");
		blacklistSTPData5.setFound(false);
		blacklistSTPData5.setMatch(0);
		blacklistSTPData5.setMatchedData("(No Match Found)");
		
		BlacklistSTPData blacklistSTPData6 = new BlacklistSTPData();
		blacklistSTPData6.setType("IPADDRESS");
		blacklistSTPData6.setRequestType("IPADDRESS");
		blacklistSTPData6.setFound(false);
		blacklistSTPData6.setMatch(0);
		blacklistSTPData6.setMatchedData("(No Match Found)");
		data.add(blacklistSTPData);
		data.add(blacklistSTPData1);
		data.add(blacklistSTPData2);
		data.add(blacklistSTPData3);
		data.add(blacklistSTPData4);
		data.add(blacklistSTPData5);
		data.add(blacklistSTPData6);
		blacklistContactResponse.setData(data);
		blacklistContactResponse.setStatus("Pass");
		return blacklistContactResponse;
		
	}
 			private BlacklistSTPRequest getBlacklistSTPRequest()
			{
		         BlacklistSTPRequest blacklistSTPRequest= new BlacklistSTPRequest();
		         List<BlacklistData> data=new ArrayList<>();
		         BlacklistData blacklistData= new BlacklistData();
		         blacklistData.setType("NAME");
		         blacklistData.setValue("michealnorse");
		         BlacklistData blacklistData1 = new BlacklistData();
		         blacklistData1.setType("EMAIL");
		         blacklistData1.setValue("abc2133@gmail");
		         BlacklistData blacklistData2= new BlacklistData();
		         blacklistData2.setType("DOMAIN");
		         blacklistData2.setValue("gmail");
		         BlacklistData blacklistData3= new BlacklistData();
		         blacklistData3.setType("PHONE_NUMBER");
		         blacklistData3.setValue("447311123456");
		         BlacklistData blacklistData4= new BlacklistData();
		         blacklistData4.setType("PHONE_NUMBER");
		         blacklistData4.setValue("");
		         BlacklistData blacklistData5= new BlacklistData();
		         blacklistData5.setType("PHONE_NUMBER");
		         blacklistData5.setValue("");
		         BlacklistData blacklistData6= new BlacklistData();
		         blacklistData6.setType("IPADDRESS");
		         blacklistData6.setValue("172.31.4.211");
		         data.add(blacklistData);
		         data.add(blacklistData1);
		         data.add(blacklistData2);
		         data.add(blacklistData3);
		         data.add(blacklistData4);
		         data.add(blacklistData5);
		         data.add(blacklistData6);
		         blacklistSTPRequest.setSearch(data);
				return blacklistSTPRequest;
			}
	
	@Test
	public void testTransformBlacklistSTPResponse()
	{
		BlacklistContactResponse expectedResult=setBlacklistContactResponse();
 		BlacklistContactResponse actualResult=serviceTransformerImpl.transformBlacklistSTPResponse(getInternalServiceRequestData(), getBlacklistContactResponse());	
	    assertEquals(expectedResult.getData().get(0).getValue(),actualResult.getData().get(0).getValue());
	    assertEquals(expectedResult.getData().get(1).getValue(),actualResult.getData().get(1).getValue());
	    assertEquals(expectedResult.getData().get(2).getValue(),actualResult.getData().get(2).getValue());
	    assertEquals(expectedResult.getData().get(3).getValue(),actualResult.getData().get(3).getValue());
	    assertEquals(expectedResult.getData().get(4).getValue(),actualResult.getData().get(4).getValue());
	    assertEquals(expectedResult.getData().get(5).getValue(),actualResult.getData().get(5).getValue());
	    assertEquals(expectedResult.getData().get(6).getValue(),actualResult.getData().get(6).getValue());
	}
	
	
	
	@Test
	public void testTransformToBlacklistSTPRequest()
	{
		BlacklistSTPRequest expectedResult=getBlacklistSTPRequest();
		BlacklistSTPRequest actualResult=serviceTransformerImpl.transformToBlacklistSTPRequest(getInternalServiceRequestData());
		assertEquals(expectedResult.getSearch().get(0).getValue(), actualResult.getSearch().get(0).getValue());
		assertEquals(expectedResult.getSearch().get(1).getValue(), actualResult.getSearch().get(1).getValue());
		assertEquals(expectedResult.getSearch().get(2).getValue(), actualResult.getSearch().get(2).getValue());
		assertEquals(expectedResult.getSearch().get(3).getValue(), actualResult.getSearch().get(3).getValue());
		assertEquals(expectedResult.getSearch().get(4).getValue(), actualResult.getSearch().get(4).getValue());
		assertEquals(expectedResult.getSearch().get(5).getValue(), actualResult.getSearch().get(5).getValue());
		assertEquals(expectedResult.getSearch().get(6).getValue(), actualResult.getSearch().get(6).getValue());
		
	}
	
	
	@Test 
	public void testRemoveSpacesAndmakeLowercase()
	{
		String name=serviceTransformerImpl.removeSpacesAndmakeLowercase("Micheal Norse");
		assertEquals("michealnorse",name);
	}
	

	
	public StateRuleRequest getStateRuleRequest()
	{
		StateRuleRequest stateRuleRequest= new StateRuleRequest();
		stateRuleRequest.setIsoAlpha3CountryCode("USA");
		stateRuleRequest.setOrgCode("Currencies Direct");
		stateRuleRequest.setState("Alabama");
		stateRuleRequest.setOrgLegalEntity("CDLGB");
		return stateRuleRequest;

	}
	private IpServiceRequest getIpServiceRequest()
	{
		IpServiceRequest ipServiceRequest= new IpServiceRequest();
		ipServiceRequest.setCountry("USA");
		ipServiceRequest.setIpAddress("172.31.4.211");
		ipServiceRequest.setPostalCode("BD22 8EZ");
		return ipServiceRequest;
	}
	@Test    
	public void testTransformToRuleRequest()
	{
		StateRuleRequest expectedResult=getStateRuleRequest();
		StateRuleRequest actualResult=serviceTransformerImpl.transformToRuleRequest(getInternalServiceRequestData(), "Currencies Direct");
       assertEquals(expectedResult.getIsoAlpha3CountryCode(),actualResult.getIsoAlpha3CountryCode());
	}
	
	@Test
	public void testTransformTpIpRequest()
	{
		IpServiceRequest expectedResult=getIpServiceRequest();
		IpServiceRequest actualResult=serviceTransformerImpl.transformTpIpRequest(getInternalServiceRequestData());
      assertEquals(expectedResult.getCountry(),actualResult.getCountry());
	}
	
	@Test
	public void testGetDomainName() throws URISyntaxException
	{
		
		String actualResult=serviceTransformerImpl.getDomainName("http://www.eurotradepply.co.uk");
		assertEquals("eurotradepply.co.uk",actualResult);
	}
}
