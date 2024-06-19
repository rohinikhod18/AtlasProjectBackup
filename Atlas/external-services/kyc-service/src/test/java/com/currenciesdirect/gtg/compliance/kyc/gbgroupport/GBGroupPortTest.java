package com.currenciesdirect.gtg.compliance.kyc.gbgroupport;
import static org.junit.Assert.fail;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

import org.apache.axis.message.SOAPHeaderElement;
import org.junit.Before;
import org.junit.Test;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.id3global.www.ID3gWS._2013._04.GlobalAddress;
import com.id3global.www.ID3gWS._2013._04.GlobalAddresses;
import com.id3global.www.ID3gWS._2013._04.GlobalGender;
import com.id3global.www.ID3gWS._2013._04.GlobalIdentityDocuments;
import com.id3global.www.ID3gWS._2013._04.GlobalInputData;
import com.id3global.www.ID3gWS._2013._04.GlobalInternationalPassport;
import com.id3global.www.ID3gWS._2013._04.GlobalPersonal;
import com.id3global.www.ID3gWS._2013._04.GlobalPersonalDetails;
import com.id3global.www.ID3gWS._2013._04.GlobalUKData;
import com.id3global.www.ID3gWS._2013._04.GlobalUKDrivingLicence;


/**
 * The Class GBGroupPortTest.
 */
public class GBGroupPortTest {
	
	/**
	 * Sets the up.
	 *
	 * @throws Exception the exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test check KY cdetails.
	 */
	@Test
	public void testCheckKYCdetails() {
		try{
		/*IKYCProviderService port = GBGroupPort.getInstance();
		GlobalInputData globalInputData = new GlobalInputData();
		globalInputData = populateID3GlobalRequestObject(null);
		URL endpointUrl = new URL("https://pilot.id3global.com/ID3gWS/ID3global.svc/Soap11_Auth");
		Service service = new Service();
		BasicHttpBinding_GlobalAuthenticateStub authenticateStub = new BasicHttpBinding_GlobalAuthenticateStub(
				endpointUrl, service);
		authenticateStub.setHeader(getSecurityHeader("ex"));
		GlobalProfileIDVersion profileIDVersion = new GlobalProfileIDVersion();
		profileIDVersion.setID("49693f46-adf1-4d23-99ed-b0c9ac9a41cb");
		UnsignedInt version = new UnsignedInt(1);
		profileIDVersion.setVersion(version);
		GlobalResultData result = authenticateStub.authenticateSP(profileIDVersion, null, globalInputData);*/
		} catch(Exception e) {
		fail("Not yet implemented");
		}
		
	}
	
	/**
	 * Populate ID 3 global request object.
	 *
	 * @param request the request
	 * @return the global input data
	 */
	public GlobalInputData populateID3GlobalRequestObject(KYCProviderRequest request){
		 GlobalInputData data = new GlobalInputData();
		
			try {
			GlobalPersonal personal = new GlobalPersonal();
			GlobalAddresses  addresse = new GlobalAddresses();
			GlobalAddress currentAddress = new GlobalAddress();
	    	GlobalPersonalDetails personalDetails = new GlobalPersonalDetails();
	    	personalDetails.setTitle("Mr");
	    	personalDetails.setForename("Gary");
	    	personalDetails.setMiddleName("L");
	    	personalDetails.setSurname("Milburn");
	    	GlobalGender gender = new GlobalGender("Male");
	    	personalDetails.setGender(gender);
	    	personalDetails.setDOBDay(6);
	    	personalDetails.setDOBMonth(10);
	    	personalDetails.setDOBYear(1973);
	    	personal.setPersonalDetails(personalDetails);
	    	data.setPersonal(personal);
	    	
	    	/*currentAddress.setAddressLine1("Brasenose Driftway");
	    	currentAddress.setAddressLine2("Oxfordshire");*/
	    
	    	currentAddress.setCountry("UK");
	    	currentAddress.setStreet("Brasenose Driftway");
	    	currentAddress.setRegion("Oxfordshire");
	    	currentAddress.setZipPostcode("OX42QX");
	    	currentAddress.setBuilding("36");
	    	addresse.setCurrentAddress(currentAddress);
	    	data.setAddresses(addresse);
	    	
	    	GlobalIdentityDocuments identityDocuments = new GlobalIdentityDocuments();
	    	GlobalInternationalPassport internationalPassport = new GlobalInternationalPassport();
	    	internationalPassport.setNumber("9876543213GBR7310065M080310806");
	    	internationalPassport.setExpiryDay(10);
	    	internationalPassport.setExpiryMonth(3);
	    	internationalPassport.setExpiryYear(2008);
	    	internationalPassport.setCountryOfOrigin("United Kingdom");
	    	identityDocuments.setInternationalPassport(internationalPassport);
	    	GlobalUKData UK = new GlobalUKData();
	    	GlobalUKDrivingLicence drivingLicence = new GlobalUKDrivingLicence();
	    	drivingLicence.setNumber("MILBU710063GL900");
	    	UK.setDrivingLicence(drivingLicence);
	    	identityDocuments.setUK(UK);
	    	data.setIdentityDocuments(identityDocuments);
			
			
			} catch(Exception e) {
				e.printStackTrace();
			}
	    	return data;
	    	
	    }
	
	/**
	 * Gets the security header.
	 *
	 * @param ex the ex
	 * @return the security header
	 */
	public SOAPHeaderElement getSecurityHeader(String ex) {

		QName headerName = new QName(
				"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
		SOAPHeaderElement header = new SOAPHeaderElement(headerName);
		header.setActor(null);
		header.setPrefix("wsse");
		header.setMustUnderstand(true);
		try {
			SOAPElement utElem;
			utElem = header.addChildElement("UsernameToken");
			SOAPElement userNameElem = utElem.addChildElement("Username");
			userNameElem.setValue("admin@currenciesdirect.com");
			SOAPElement passwordElem = utElem.addChildElement("Password");
			passwordElem.setValue("Stephen4kam2028!!");
		} catch (Exception exception) {

		}
		return header;
	}

}
