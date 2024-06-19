/*
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.gbgroupport;

import static org.junit.Assert.fail;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.axis.message.SOAPHeaderElement;
import org.junit.Test;

import com.seisint.webservices.WsIdentity.CanadaVerification;
import com.seisint.webservices.WsIdentity.CountryCodeEnum;
import com.seisint.webservices.WsIdentity.InstantIDIntl2Request;
import com.seisint.webservices.WsIdentity.InstantIDIntlSearchBy;

/**
 * The Class LexisNexisPortTest.
 */
public class LexisNexisPortTest {

	/**
	 * Test check KY cdetails.
	 */
	@Test
	public void testCheckKYCdetails() {
		try {
		/*IKYCProviderService lexisNexisPort = LexisNexisPort.getInstance();
		InstantIDIntl2Request idIntl2Request = populateCanadaRequest() ;
		URL endpointUrl = new URL(
				"https://wsonline.seisint.com/WsIdentity?ver_=1.77");
		Service service = new Service();
		WsIdentityServiceSoapStub wsIdentityServiceSoapStub = new WsIdentityServiceSoapStub(
				endpointUrl, service);
		wsIdentityServiceSoapStub.setHeader(getSecurityHeader("ex"));

		InstantIDIntl2ResponseEx result = wsIdentityServiceSoapStub
				.instantIDIntl2(idIntl2Request);
		// LOG.info();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(result);
		System.out.println("Result :" + json);*/
		}catch(Exception r) {
		fail("Not yet implemented");
		}
	}

	/**
	 * Populate canada request.
	 *
	 * @return the instant ID intl 2 request
	 */
	public static InstantIDIntl2Request populateCanadaRequest() {
		InstantIDIntl2Request idIntl2Request = new InstantIDIntl2Request();
		InstantIDIntlSearchBy idIntlSearchBy = new InstantIDIntlSearchBy();
		idIntlSearchBy.setCountry(CountryCodeEnum.CA);
		CanadaVerification canadaVerification = new CanadaVerification();
		canadaVerification.setFirstName("JOHN");
		canadaVerification.setLastName("SMITHSON");
		canadaVerification.setYearOfBirth("1966");
		canadaVerification.setMonthOfBirth("7");
		canadaVerification.setDayOfBirth("9");
		canadaVerification.setStreetName("LYNNVIEW");
		canadaVerification.setStreetType("RD");
		canadaVerification.setPostalCode("T9H 5M2");
		canadaVerification.setTelephone("7807476741");
		canadaVerification.setCivicNumber("325");
		canadaVerification.setMunicipality("CALGARY");
		canadaVerification.setProvince("AB");
		canadaVerification.setSocialInsuranceNumber("453456881");

		idIntlSearchBy.setCanadaVerification(canadaVerification);
		idIntl2Request.setSearchBy(idIntlSearchBy);
		return idIntl2Request;
	}
	
	/**
	 * Gets the security header.
	 *
	 * @param demo the demo
	 * @return the security header
	 */
	public static SOAPHeaderElement getSecurityHeader(String demo) {

		QName headerName = new QName(
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
				"Security");
		SOAPHeaderElement header = new SOAPHeaderElement(headerName);
		header.setActor(null);
		header.setPrefix("wsse");
		header.setMustUnderstand(true);
		try {
			SOAPElement utElem;
			utElem = header.addChildElement("UsernameToken");
			SOAPElement userNameElem = utElem.addChildElement("Username");
			userNameElem.setValue("CURDIRDEVXML");
			SOAPElement passwordElem = utElem.addChildElement("Password");
			passwordElem.setValue("B5967t33");
		} catch (Exception exception) {

		}
		return header;
	}
}
