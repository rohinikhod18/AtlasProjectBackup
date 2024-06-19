/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.gbgroupport;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Identification;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.id3global.www.ID3gWS._2013._04.GlobalAddress;
import com.id3global.www.ID3gWS._2013._04.GlobalAddresses;
import com.id3global.www.ID3gWS._2013._04.GlobalContactDetails;
import com.id3global.www.ID3gWS._2013._04.GlobalGender;
import com.id3global.www.ID3gWS._2013._04.GlobalIdentityCard;
import com.id3global.www.ID3gWS._2013._04.GlobalIdentityDocuments;
import com.id3global.www.ID3gWS._2013._04.GlobalInputData;
import com.id3global.www.ID3gWS._2013._04.GlobalInternationalPassport;
import com.id3global.www.ID3gWS._2013._04.GlobalLandTelephone;
import com.id3global.www.ID3gWS._2013._04.GlobalMobileTelephone;
import com.id3global.www.ID3gWS._2013._04.GlobalPersonal;
import com.id3global.www.ID3gWS._2013._04.GlobalPersonalDetails;
import com.id3global.www.ID3gWS._2013._04.GlobalSpain;
import com.id3global.www.ID3gWS._2013._04.GlobalSpainTaxIDNumber;
import com.id3global.www.ID3gWS._2013._04.GlobalUKData;
import com.id3global.www.ID3gWS._2013._04.GlobalUKDrivingLicence;
import com.id3global.www.ID3gWS._2013._04.GlobalUS;
import com.id3global.www.ID3gWS._2013._04.GlobalUSSocialSecurity;
/**
 * The Class GBGroupTransformer.
 *
 * @author manish
 */
public class GBGroupTransformer {
	
	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory
			.getLogger(GBGroupTransformer.class);
	
	/** The replace regex. */
	private static String replaceRegex = "(Appt|Apartment|Building|Build|Bldg|Bldg.|Apartment No.)";
	
	/** The pattern to split. */
	private static String patternToSplit = "(,)|(\r+\n+)|(;)|(\\.)";
	
	/** The g B group transformer. */
	private static GBGroupTransformer gBGroupTransformer = null;
	 
	/**
	 * Instantiates a new GB group transformer.
	 */
	private GBGroupTransformer() {
		
	}
	
	/**
	 * Gets the single instance of GBGroupTransformer.
	 *
	 * @return single instance of GBGroupTransformer
	 */
	public static GBGroupTransformer getInstance() {
		if(gBGroupTransformer == null) {
			gBGroupTransformer = new GBGroupTransformer();
		}
		return gBGroupTransformer;
	}
	
	
	/**
	 * Transform request object.
	 *
	 * @param request the request
	 * @return the global input data
	 * @throws KYCException the KYC exception
	 */
	public GlobalInputData transformRequestObject(KYCContactRequest request) throws KYCException {
		GlobalInputData  data = new GlobalInputData();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			
	    	GlobalPersonalDetails personalDetails = new GlobalPersonalDetails();
	    	if(request.getPersonalDetails().getGender()!=null && !request.getPersonalDetails().getGender().isEmpty())
	    	{
	    		GlobalGender gender;
	    		if("MALE".equalsIgnoreCase(request.getPersonalDetails().getGender().trim())){
	    			 gender = new GlobalGender("Male");
	    		}else if("FEMALE".equalsIgnoreCase(request.getPersonalDetails().getGender().trim())){
	    			 gender = new GlobalGender("Female");
	    		}else {
	    			 gender = new GlobalGender(request.getPersonalDetails().getGender());
	    		}
	      	
	      	personalDetails.setGender(gender);
	    	}else{
	    		GlobalGender gender = new GlobalGender("Unspecified");
		      	personalDetails.setGender(gender);
	    	}
	    	data = transformAsCountry(request, data, formatter, personalDetails);
		} 
		catch(KYCException e) {
			throw e;
		} catch(Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST,e);
		}
		return data;
	}

	/**
	 * Transform as country.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 * @throws KYCException the KYC exception
	 */
	private GlobalInputData transformAsCountry(KYCContactRequest request, GlobalInputData data,
			SimpleDateFormat formatter, GlobalPersonalDetails personalDetails) throws ParseException, KYCException {
		switch(request.getAddress().getCountry().toUpperCase()) {
		
		case Constants.COUNTRY_UNITEDKINGDOM:
			transformUK(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_DENMARK:
			transformDenmark(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_SPAIN:
			transFormSpain(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_GERMANY:
			transformGermany(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_NORWAY:
			transFormNorway(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_SWITZERLAND:
			transformSwitzerland(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_SWEDEN:
			transFormSweden(request, data, formatter, personalDetails);
			break;
		case Constants.COUNTRY_NETHERLANDS :
			transformNetharlands(request, data, formatter, personalDetails);
		    break;
		//Add for AT-3662 - Automate US EIDV 
		case Constants.COUNTRY_USA :
			transformUSA(request, data, formatter, personalDetails);
		    break;
		default :
			throw new KYCException(KYCErrors.COUNTRY_IS_NOT_SUPPORTED);
		}
		return data;
	}

	/**
	 * Transform germany.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transformGermany(KYCContactRequest requestGermany, GlobalInputData dataGermany, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsGermany) throws ParseException {
		
		GlobalPersonal personalGermany ;
		GlobalAddress currentAddressGermany = new GlobalAddress();
		GlobalAddresses  addresseGermany = new GlobalAddresses();
		GlobalIdentityDocuments identityDocumentsGermany = new GlobalIdentityDocuments();
		GlobalInternationalPassport internationalPassportGermany = new GlobalInternationalPassport();
		GlobalLandTelephone globalLandTelephoneGermany = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneGermany = new GlobalMobileTelephone();
		GlobalContactDetails contactDetailsGermany = new GlobalContactDetails();
		personalDetailsGermany.setTitle(requestGermany.getPersonalDetails().getTitle());
		personalDetailsGermany.setForename(requestGermany.getPersonalDetails().getForeName());
		personalDetailsGermany.setMiddleName(requestGermany.getPersonalDetails().getMiddleName());
		personalDetailsGermany.setSurname(requestGermany.getPersonalDetails().getSurName());
   
		personalGermany= getPersonalDetails(requestGermany,personalDetailsGermany,formatter);
		dataGermany.setPersonal(personalGermany);
		
		currentAddressGermany.setCountry(requestGermany.getAddress().getCountry());
		currentAddressGermany.setStateDistrict(requestGermany.getAddress().getState());
		currentAddressGermany.setStreet(requestGermany.getAddress().getStreet());
		currentAddressGermany.setRegion(requestGermany.getAddress().getRegion());
		currentAddressGermany.setZipPostcode(requestGermany.getAddress().getPostCode());
		currentAddressGermany.setBuilding(requestGermany.getAddress().getBuildingNumber());
		
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressGermany,requestGermany);
		}
		
		addresseGermany.setCurrentAddress(currentAddressGermany);
		
		if(requestGermany.getIdentification()!=null) {
		for (Identification identity : requestGermany.getIdentification()) {
			if(Constants.KYC_PASSPORT.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
				fillPassportDetails(formatter, identityDocumentsGermany, internationalPassportGermany, identity);
			}
		}
		}
		
		if(requestGermany.getPhone()!=null) {
			fillPhoneDetails(requestGermany, contactDetailsGermany, globalLandTelephoneGermany, globalMobileTelephoneGermany);
		}
		dataGermany.setAddresses(addresseGermany);
    	dataGermany.setIdentityDocuments(identityDocumentsGermany);
    	dataGermany.setContactDetails(contactDetailsGermany);
		return dataGermany;
	}

	/**
	 * Trans form norway.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transFormNorway(KYCContactRequest requestNorway, GlobalInputData dataNorway, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsNorway) throws ParseException {

		GlobalPersonal personalNorway;
		GlobalAddress currentAddressNorway = new GlobalAddress();
		GlobalAddresses  addresseNorway = new GlobalAddresses();
		GlobalIdentityDocuments identityDocumentsNorway = new GlobalIdentityDocuments();
		GlobalInternationalPassport internationalPassportNorway = new GlobalInternationalPassport();
		GlobalLandTelephone globalLandTelephoneNorway = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneNorway = new GlobalMobileTelephone();
		GlobalContactDetails contactDetailsNorway = new GlobalContactDetails();
		
		personalNorway= getPersonalDetails(requestNorway,personalDetailsNorway,formatter);
		dataNorway.setPersonal(personalNorway);
		
		currentAddressNorway.setCountry(requestNorway.getAddress().getCountry());
		currentAddressNorway.setStateDistrict(requestNorway.getAddress().getState());
		currentAddressNorway.setStreet(requestNorway.getAddress().getStreet());
		currentAddressNorway.setRegion(requestNorway.getAddress().getRegion());
		currentAddressNorway.setCity(requestNorway.getAddress().getCity()); // added later.. temp
		currentAddressNorway.setZipPostcode(requestNorway.getAddress().getPostCode());
		currentAddressNorway.setStateDistrict(requestNorway.getAddress().getState());
		currentAddressNorway.setBuilding(requestNorway.getAddress().getBuildingNumber());
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressNorway,requestNorway);
		}
		addresseNorway.setCurrentAddress(currentAddressNorway);
		
		if(requestNorway.getIdentification()!=null) {
		for (Identification identity : requestNorway.getIdentification()) {
			if(Constants.KYC_PASSPORT.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
				fillPassportDetails(formatter, identityDocumentsNorway, internationalPassportNorway, identity);
		    	
			}
		}
		}
		if(requestNorway.getPhone()!=null) {
			fillPhoneDetails(requestNorway, contactDetailsNorway, globalLandTelephoneNorway, globalMobileTelephoneNorway);
		}
		dataNorway.setAddresses(addresseNorway);
    	dataNorway.setIdentityDocuments(identityDocumentsNorway);
    	dataNorway.setContactDetails(contactDetailsNorway);
		return dataNorway;
	}

	/**
	 * Transform switzerland.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transformSwitzerland(KYCContactRequest requestSwitzerland, GlobalInputData dataSwitzerland, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsSwitzerland) throws ParseException {
		GlobalPersonal personalSwitzerland;
		GlobalAddress currentAddressSwitzerland = new GlobalAddress();
		GlobalAddresses  addresseSwitzerland = new GlobalAddresses();
		GlobalIdentityDocuments identityDocumentsSwitzerland = new GlobalIdentityDocuments();
		GlobalInternationalPassport internationalPassportSwitzerland = new GlobalInternationalPassport();
		GlobalLandTelephone globalLandTelephoneSwitzerland = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneSwitzerland = new GlobalMobileTelephone();
		GlobalContactDetails contactDetailsSwitzerland = new GlobalContactDetails();

		personalSwitzerland= getPersonalDetails(requestSwitzerland,personalDetailsSwitzerland,formatter);
		dataSwitzerland.setPersonal(personalSwitzerland);
		
		currentAddressSwitzerland.setCountry(requestSwitzerland.getAddress().getCountry());
		currentAddressSwitzerland.setStateDistrict(requestSwitzerland.getAddress().getState());
		currentAddressSwitzerland.setStreet(requestSwitzerland.getAddress().getStreet());
		currentAddressSwitzerland.setRegion(requestSwitzerland.getAddress().getRegion());
		currentAddressSwitzerland.setCity(requestSwitzerland.getAddress().getCity()); // temp added
		currentAddressSwitzerland.setZipPostcode(requestSwitzerland.getAddress().getPostCode());
		currentAddressSwitzerland.setStateDistrict(requestSwitzerland.getAddress().getState());
		currentAddressSwitzerland.setBuilding(requestSwitzerland.getAddress().getBuildingNumber());
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressSwitzerland,requestSwitzerland);
		}
		addresseSwitzerland.setCurrentAddress(currentAddressSwitzerland);
		
		if(requestSwitzerland.getIdentification()!=null) {
		for (Identification identity : requestSwitzerland.getIdentification()) {
			if(Constants.KYC_PASSPORT.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
				fillPassportDetails(formatter, identityDocumentsSwitzerland, internationalPassportSwitzerland, identity);
		    	
			}
		}
		}
		if(requestSwitzerland.getPhone()!=null) {
			fillPhoneDetails(requestSwitzerland, contactDetailsSwitzerland, globalLandTelephoneSwitzerland, globalMobileTelephoneSwitzerland);
		}
		dataSwitzerland.setAddresses(addresseSwitzerland);
    	dataSwitzerland.setIdentityDocuments(identityDocumentsSwitzerland);
    	dataSwitzerland.setContactDetails(contactDetailsSwitzerland);
		return dataSwitzerland;
	}

	/**
	 * Trans form sweden.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transFormSweden(KYCContactRequest requestSweden, GlobalInputData dataSweden, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsSweden) throws ParseException {

		GlobalPersonal personalSweden;
		GlobalAddress currentAddressSweden = new GlobalAddress();
		GlobalAddresses  addresseSweden = new GlobalAddresses();
		GlobalIdentityDocuments identityDocumentsSweden = new GlobalIdentityDocuments();
		GlobalInternationalPassport internationalPassportSweden = new GlobalInternationalPassport();
		GlobalLandTelephone globalLandTelephoneSweden = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneSweden = new GlobalMobileTelephone();
		GlobalContactDetails contactDetailsSweden = new GlobalContactDetails();
	
		personalSweden= getPersonalDetails(requestSweden,personalDetailsSweden,formatter);
		dataSweden.setPersonal(personalSweden);
		
		currentAddressSweden.setCountry(requestSweden.getAddress().getCountry());
		currentAddressSweden.setStateDistrict(requestSweden.getAddress().getState());
		currentAddressSweden.setStreet(requestSweden.getAddress().getStreet());
		currentAddressSweden.setRegion(requestSweden.getAddress().getRegion());
		currentAddressSweden.setCity(requestSweden.getAddress().getCity()); //temp added 
		currentAddressSweden.setZipPostcode(requestSweden.getAddress().getPostCode());
		currentAddressSweden.setBuilding(requestSweden.getAddress().getBuildingNumber());
		currentAddressSweden.setStateDistrict(requestSweden.getAddress().getState());
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressSweden,requestSweden);
		}
		addresseSweden.setCurrentAddress(currentAddressSweden);
		if (requestSweden.getIdentification() != null) {
			for (Identification identity : requestSweden.getIdentification()) {
				switch (identity.getType().toUpperCase().replaceAll("\\s+", "")) {
				case Constants.KYC_PASSPORT:
					fillPassportDetails(formatter, identityDocumentsSweden, internationalPassportSweden, identity);
					break;
				case Constants.KYC_NATIONAL_ID_NUMBER:
					GlobalIdentityCard globalIdentityCard = new GlobalIdentityCard();
					globalIdentityCard.setNumber(identity.getNumber());
					globalIdentityCard.setCountry(identity.getCountryOfIssue());
					identityDocumentsSweden.setIdentityCard(globalIdentityCard);
					break;
				default:
					break;

				}
			}
		}
		
		
		
		if(requestSweden.getPhone()!=null) {
			fillPhoneDetails(requestSweden, contactDetailsSweden, globalLandTelephoneSweden, globalMobileTelephoneSweden);
		}
		dataSweden.setAddresses(addresseSweden);
    	dataSweden.setIdentityDocuments(identityDocumentsSweden);
    	dataSweden.setContactDetails(contactDetailsSweden);
		return dataSweden;
	}

	/**
	 * Transform netharlands.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @throws ParseException the parse exception
	 */
	private void transformNetharlands(KYCContactRequest requestNetharlands, GlobalInputData dataNetharlands, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsNetharlands) throws ParseException {
		GlobalPersonal personalNetharlands;
		GlobalAddress currentAddressNetharlands = new GlobalAddress();
		GlobalAddresses addresseNetharlands = new GlobalAddresses();

		personalNetharlands = getPersonalDetails(requestNetharlands, personalDetailsNetharlands, formatter);

		dataNetharlands.setPersonal(personalNetharlands);
		currentAddressNetharlands.setCountry(requestNetharlands.getAddress().getCountry());

		currentAddressNetharlands.setStreet(requestNetharlands.getAddress().getStreet());
		currentAddressNetharlands.setStateDistrict(requestNetharlands.getAddress().getState());
		currentAddressNetharlands.setCity(requestNetharlands.getAddress().getCity());
		currentAddressNetharlands.setZipPostcode(requestNetharlands.getAddress().getPostCode());
		currentAddressNetharlands.setBuilding(requestNetharlands.getAddress().getBuildingNumber());
		currentAddressNetharlands.setStateDistrict(requestNetharlands.getAddress().getState());
		currentAddressNetharlands.setSubBuilding(requestNetharlands.getAddress().getSubBuildingOrFlat());
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressNetharlands,requestNetharlands);
		}
		addresseNetharlands.setCurrentAddress(currentAddressNetharlands);
		dataNetharlands.setAddresses(addresseNetharlands);
	}

	/**
	 * Trans form spain.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transFormSpain(KYCContactRequest requestSpain, GlobalInputData dataSpain, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsSpain) throws ParseException {

		GlobalPersonal personalSpain;
		GlobalAddress currentAddressSpain = new GlobalAddress();
		GlobalAddresses  addresseSpain = new GlobalAddresses();
		GlobalIdentityDocuments identityDocumentsSpain = new GlobalIdentityDocuments();
		GlobalInternationalPassport internationalPassportSpain = new GlobalInternationalPassport();
		GlobalLandTelephone globalLandTelephoneSpain = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneSpain = new GlobalMobileTelephone();
		GlobalContactDetails contactDetailsSpain = new GlobalContactDetails();
		
		personalSpain= getPersonalDetails(requestSpain,personalDetailsSpain,formatter);
		dataSpain.setPersonal(personalSpain);
		
		currentAddressSpain.setCountry(requestSpain.getAddress().getCountry());
		currentAddressSpain.setStateDistrict(requestSpain.getAddress().getState());
		currentAddressSpain.setStreet(requestSpain.getAddress().getStreet());
		currentAddressSpain.setRegion(requestSpain.getAddress().getRegion());
		currentAddressSpain.setCity(requestSpain.getAddress().getCity()); // added later.. temp
		currentAddressSpain.setZipPostcode(requestSpain.getAddress().getPostCode());
		currentAddressSpain.setStateDistrict(requestSpain.getAddress().getState());
		currentAddressSpain.setBuilding(requestSpain.getAddress().getBuildingNumber());
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressSpain,requestSpain);
		}
		addresseSpain.setCurrentAddress(currentAddressSpain);
		if(requestSpain.getIdentification()!=null) {
		for (Identification identity : requestSpain.getIdentification()) {
			switch(identity.getType().toUpperCase().replaceAll("\\s+","")) {
			case Constants.KYC_PASSPORT :
				fillPassportDetails(formatter, identityDocumentsSpain, internationalPassportSpain, identity);
				break;
			case Constants.KYC_TAX_ID_NUMBER :	
				setGlobalSpain(identityDocumentsSpain, identity);
		    	break;
		    default:
		    	break;
		    	
			}
		}
		}
		
		if(requestSpain.getPhone()!=null) {
			fillPhoneDetails(requestSpain, contactDetailsSpain, globalLandTelephoneSpain, globalMobileTelephoneSpain);
		}
		dataSpain.setAddresses(addresseSpain);
    	dataSpain.setIdentityDocuments(identityDocumentsSpain);
    	dataSpain.setContactDetails(contactDetailsSpain);
		return dataSpain;
	}

	/**
	 * Sets the global spain.
	 *
	 * @param identityDocuments the identity documents
	 * @param identity the identity
	 */
	private void setGlobalSpain(GlobalIdentityDocuments identityDocuments, Identification identity) {
		GlobalSpain globalSpain = new GlobalSpain();
		GlobalSpainTaxIDNumber globalSpainTaxIDNumber = new GlobalSpainTaxIDNumber();
		globalSpainTaxIDNumber.setNumber(identity.getNumber());
		globalSpain.setTaxIDNumber(globalSpainTaxIDNumber);
		identityDocuments.setSpain(globalSpain);
	}

	/**
	 * Transform denmark.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transformDenmark(KYCContactRequest requestDenmark, GlobalInputData dataDenmark, SimpleDateFormat formatter,
			GlobalPersonalDetails personalDetailsDenmark) throws ParseException {

		GlobalPersonal personalDenmark;
		GlobalAddress currentAddressDenmark = new GlobalAddress();
		GlobalAddresses  addresseDenmark = new GlobalAddresses();
		GlobalIdentityDocuments identityDocumentsDenmark = new GlobalIdentityDocuments();
		GlobalInternationalPassport internationalPassportDenmark = new GlobalInternationalPassport();
		GlobalLandTelephone globalLandTelephoneDenmark = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneDenmark = new GlobalMobileTelephone();
		GlobalContactDetails contactDetailsDenmark = new GlobalContactDetails();
		
		personalDenmark= getPersonalDetails(requestDenmark,personalDetailsDenmark,formatter);
		dataDenmark.setPersonal(personalDenmark);
		
		currentAddressDenmark.setCountry(requestDenmark.getAddress().getCountry());
		currentAddressDenmark.setStateDistrict(requestDenmark.getAddress().getState());
		currentAddressDenmark.setStreet(requestDenmark.getAddress().getStreet());
		currentAddressDenmark.setRegion(requestDenmark.getAddress().getRegion());
		currentAddressDenmark.setZipPostcode(requestDenmark.getAddress().getPostCode());
		currentAddressDenmark.setBuilding(requestDenmark.getAddress().getBuildingNumber());
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			setFreeFormatAddressLine(currentAddressDenmark,requestDenmark);
		}
		addresseDenmark.setCurrentAddress(currentAddressDenmark);
		
		if(requestDenmark.getIdentification()!=null) {
		for (Identification identity : requestDenmark.getIdentification()) {
			switch(identity.getType().toUpperCase().replaceAll("\\s+","")) {
			case Constants.KYC_PASSPORT :
				fillPassportDetails(formatter, identityDocumentsDenmark, internationalPassportDenmark, identity);
				break;
			case Constants.KYC_IDENTITY_CARD :	
				GlobalIdentityCard globalIdentityCard = new GlobalIdentityCard();
				globalIdentityCard.setNumber(identity.getNumber());
				globalIdentityCard.setCountry(identity.getCountryOfIssue());
				identityDocumentsDenmark.setIdentityCard(globalIdentityCard);
		    	break;
		    default:
		    	break;
		    	
			}
		}
		}
		if(requestDenmark.getPhone()!=null) {
			fillPhoneDetails(requestDenmark, contactDetailsDenmark, globalLandTelephoneDenmark, globalMobileTelephoneDenmark);
		}
		dataDenmark.setAddresses(addresseDenmark);
    	dataDenmark.setIdentityDocuments(identityDocumentsDenmark);
    	dataDenmark.setContactDetails(contactDetailsDenmark);
		return dataDenmark;
	}

	/**
	 * Transform UK.
	 *
	 * @param request the request
	 * @param data the data
	 * @param formatter the formatter
	 * @param personalDetails the personal details
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transformUK(KYCContactRequest requestUK, GlobalInputData dataUK, SimpleDateFormat formatter,GlobalPersonalDetails personalDetailsUK 
			)
			throws ParseException {
		GlobalPersonal personalUK;
		GlobalAddresses  addresseUK = new GlobalAddresses();
		GlobalAddress currentAddressUK = new GlobalAddress();
		GlobalContactDetails contactDetailsUK = new GlobalContactDetails();
		GlobalLandTelephone globalLandTelephoneUK = new GlobalLandTelephone();
		GlobalMobileTelephone globalMobileTelephoneUK = new GlobalMobileTelephone();
    	GlobalIdentityDocuments identityDocumentsUK = new GlobalIdentityDocuments();
    	GlobalInternationalPassport internationalPassportUK = new GlobalInternationalPassport();
		GlobalUKData uk = new GlobalUKData();
		
		personalUK= getPersonalDetails(requestUK,personalDetailsUK,formatter);
		dataUK.setPersonal(personalUK);
	
		currentAddressUK.setCountry(requestUK.getAddress().getCountry());
		currentAddressUK.setStateDistrict(requestUK.getAddress().getState());
		currentAddressUK.setStreet(requestUK.getAddress().getStreet());
		currentAddressUK.setRegion(requestUK.getAddress().getRegion());
		currentAddressUK.setCity(requestUK.getAddress().getCity()); //temp added
		currentAddressUK.setZipPostcode(requestUK.getAddress().getPostCode());
		currentAddressUK.setBuilding(requestUK.getAddress().getBuildingNumber());
		
		Boolean isFreeFormatEnabled = getFreeFormatFlag();
		
		if (Boolean.TRUE.equals(isFreeFormatEnabled)){
			/*Set Free Format Fields for UK*/
			setFreeFormatAddressLine(currentAddressUK,requestUK);
		}
		
		addresseUK.setCurrentAddress(currentAddressUK);
		
		if(requestUK.getIdentification()!=null) {
		for (Identification identity : requestUK.getIdentification()) {
			switch(identity.getType().toUpperCase().replaceAll("\\s+","")) {
			case Constants.KYC_PASSPORT :
				fillPassportDetails(formatter, identityDocumentsUK, internationalPassportUK, identity);
				break;
			case Constants.KYC_DRIVING_LICENSE :
		    	GlobalUKDrivingLicence drivingLicence = new GlobalUKDrivingLicence();
		    	drivingLicence.setNumber(identity.getNumber());
		    	uk.setDrivingLicence(drivingLicence);
		    	identityDocumentsUK.setUK(uk);
		    	break;
		    default:
		    	break;
		    	
			}
		}
		}

		if(requestUK.getPhone()!=null) {
		fillPhoneDetails(requestUK, contactDetailsUK, globalLandTelephoneUK, globalMobileTelephoneUK);
		}
		dataUK.setAddresses(addresseUK);
    	dataUK.setIdentityDocuments(identityDocumentsUK);
    	dataUK.setContactDetails(contactDetailsUK);
		return dataUK;
	}
	
	/**
	 * Transform USA.
	 *
	 * @param requestUSA the request USA
	 * @param dataUSA the data USA
	 * @param formatter the formatter
	 * @param personalDetailsUSA the personal details USA
	 * @return the global input data
	 * @throws ParseException the parse exception
	 */
	private GlobalInputData transformUSA(KYCContactRequest requestUSA, GlobalInputData dataUSA, SimpleDateFormat formatter,GlobalPersonalDetails personalDetailsUSA 
			)
			throws ParseException {
		GlobalPersonal personalUSA;
		GlobalAddresses  addresseUSA = new GlobalAddresses();
		GlobalAddress currentAddressUSA = new GlobalAddress();
		GlobalContactDetails contactDetailsUSA = new GlobalContactDetails();
		GlobalLandTelephone globalLandTelephoneUSA = new GlobalLandTelephone();
    	GlobalIdentityDocuments identityDocumentsUSA = new GlobalIdentityDocuments();
    	GlobalUS globalUS = new GlobalUS();
		
		personalUSA= getPersonalDetails(requestUSA,personalDetailsUSA,formatter);
		dataUSA.setPersonal(personalUSA);
	
		currentAddressUSA.setCountry(requestUSA.getAddress().getCountry());
		currentAddressUSA.setStateDistrict(requestUSA.getAddress().getState());
		currentAddressUSA.setStreet(requestUSA.getAddress().getStreet());
		currentAddressUSA.setRegion(requestUSA.getAddress().getRegion());
		currentAddressUSA.setCity(requestUSA.getAddress().getCity()); //temp added
		currentAddressUSA.setZipPostcode(requestUSA.getAddress().getPostCode());
		currentAddressUSA.setBuilding(requestUSA.getAddress().getBuildingNumber());
		currentAddressUSA.setPremise(requestUSA.getAddress().getBuildingName());//AT-3718
		currentAddressUSA.setSubBuilding(requestUSA.getAddress().getSubBuildingOrFlat());
		
		addresseUSA.setCurrentAddress(currentAddressUSA);
		
		if(requestUSA.getIdentification()!=null) {
			for (Identification identity : requestUSA.getIdentification()) {
				if(Constants.SSN_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					GlobalUSSocialSecurity globalUSSocialSecurity = new GlobalUSSocialSecurity();
					globalUSSocialSecurity.setNumber(identity.getNumber());
					globalUS.setSocialSecurity(globalUSSocialSecurity);
					identityDocumentsUSA.setUS(globalUS);
			    	break;
				}
			}
		}

		if(null != requestUSA.getPhone()) {
			fillPhoneDetailsForUSA(requestUSA, contactDetailsUSA, globalLandTelephoneUSA);
		}
		dataUSA.setAddresses(addresseUSA);
		dataUSA.setIdentityDocuments(identityDocumentsUSA);
		dataUSA.setContactDetails(contactDetailsUSA);
		return dataUSA;
	}

	/**
	 * Sets the free format address line.
	 *
	 * @param currentAddress the current address
	 * @param request the request
	 */
	private void setFreeFormatAddressLine(GlobalAddress currentAddress, KYCContactRequest request) {
		
		Address address = request.getAddress();
		String addressLine1 = address.getAddressLine1().replaceAll(replaceRegex, "");
		String[] parseStreet = addressLine1.split(patternToSplit);
		List<String> trimParseStreet = Arrays.asList(parseStreet);  
		List<String> resultList =  new ArrayList<>();
		
		for (String line: trimParseStreet)
		{ 
			String temp =  line.trim();
			if(!"".equals(temp))
				resultList.add(temp);
		}
		List<String> finalresultList =  new ArrayList<>();
		if(resultList.size()>5){
			StringBuilder s= new StringBuilder();
			for(int count=4;count<resultList.size(); count++){
				s.append(' ').append(resultList.get(count));
			}
			
			for(int count=0;count<4; count++){
				finalresultList.add(resultList.get(count));
			}
			
			finalresultList.add(s.toString());
		}else {
			finalresultList.addAll(resultList);
		}
		setFreeFormatAddressLineFields(currentAddress,finalresultList);
		currentAddress.setAddressLine6(address.getCity());
		if(null != address.getState() &&  !address.getState().isEmpty()){
			currentAddress.setAddressLine7(address.getState());
		}
		currentAddress.setAddressLine8(address.getPostCode());
		
	}
	
	/**
	 * Fill phone details.
	 *
	 * @param request the request
	 * @param contactDetails the contact details
	 * @param globalLandTelephone the global land telephone
	 * @param globalMobileTelephone the global mobile telephone
	 */
	private void fillPhoneDetails(KYCContactRequest request, GlobalContactDetails contactDetails,
			GlobalLandTelephone globalLandTelephone, GlobalMobileTelephone globalMobileTelephone) {
		for (Phone phone : request.getPhone()) {
			if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
				globalLandTelephone.setNumber(phone.getNumber());
				contactDetails.setLandTelephone(globalLandTelephone);
			}else if(Constants.KYC_PHONE_TYPE_MOBILE.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))){
				globalMobileTelephone.setNumber(phone.getNumber());
				contactDetails.setMobileTelephone(globalMobileTelephone);
   
			}
		}
	}
	
	/**
	 * Fill phone details.
	 *
	 * @param request the request
	 * @param contactDetails the contact details
	 * @param globalLandTelephone the global land telephone
	 */
	private void fillPhoneDetailsForUSA(KYCContactRequest request, GlobalContactDetails contactDetails,
			GlobalLandTelephone globalLandTelephone) {
		for (Phone phone : request.getPhone()) {
			if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
				phone.setNumber(phone.getNumber().replaceAll("\\D", ""));
				if (phone.getNumber().length() >= 10 && null != phone.getNumber()) {
					phone.setNumber(phone.getNumber().substring(phone.getNumber().length()-10));//Add for AT-3837
					
					String formattedLandTelephone = phone.getNumber().substring(0, 3) + '-'
							+ phone.getNumber().substring(3, 6) + '-' + phone.getNumber().substring(6, 10);
					globalLandTelephone.setNumber(formattedLandTelephone);
				} else {
					globalLandTelephone.setNumber("");
				}
				contactDetails.setLandTelephone(globalLandTelephone);
			}

		}
	}

	/**
	 * Fill passport details.
	 *
	 * @param formatter the formatter
	 * @param identityDocuments the identity documents
	 * @param internationalPassport the international passport
	 * @param identity the identity
	 * @throws ParseException the parse exception
	 */
	private void fillPassportDetails(SimpleDateFormat formatter, GlobalIdentityDocuments identityDocuments,
			GlobalInternationalPassport internationalPassport, Identification identity) throws ParseException {
		Date date;
		Calendar c1;
		internationalPassport.setNumber(identity.getNumber());	
		if(identity.getExiprydate()!=null && !(identity.getExiprydate().isEmpty())) {
			 date = formatter.parse(identity.getExiprydate());
			 c1 = Calendar.getInstance();
			 c1.setTime(date);
			internationalPassport.setExpiryDay(c1.get(Calendar.DAY_OF_MONTH));
			internationalPassport.setExpiryMonth(c1.get(Calendar.MONTH)+1);
			internationalPassport.setExpiryYear(c1.get(Calendar.YEAR));
			
		 	}
		internationalPassport.setCountryOfOrigin(identity.getCountryOfIssue());
		identityDocuments.setInternationalPassport(internationalPassport);
	}
	
	/**
	 * Gets the personal details.
	 *
	 * @param request the request
	 * @param personalDetails the personal details
	 * @param formatter the formatter
	 * @return the personal details
	 * @throws ParseException the parse exception
	 */
	private GlobalPersonal getPersonalDetails(KYCContactRequest request, GlobalPersonalDetails personalDetails,
			SimpleDateFormat formatter) throws ParseException {
		GlobalPersonal personal = new GlobalPersonal();
		Date date;
		Calendar c1;
		
		personalDetails.setTitle(request.getPersonalDetails().getTitle());
		personalDetails.setForename(request.getPersonalDetails().getForeName());
		personalDetails.setMiddleName(request.getPersonalDetails().getMiddleName());
		personalDetails.setSurname(request.getPersonalDetails().getSurName());
		if (!(request.getPersonalDetails().getDob().isEmpty())) {
			date = formatter.parse(request.getPersonalDetails().getDob());
			c1 = Calendar.getInstance();
			c1.setTime(date);
			personalDetails.setDOBDay(c1.get(Calendar.DAY_OF_MONTH));
			personalDetails.setDOBMonth(c1.get(Calendar.MONTH) + 1);
			personalDetails.setDOBYear(c1.get(Calendar.YEAR));
			personal.setPersonalDetails(personalDetails);
		}
		return personal;
	}
  
	/**
	 * Gets the flag for free format address fields.
	 *
	 * @return flag for free format address fields
	 */
	public static Boolean getFreeFormatFlag(){
		Boolean isFreeFormatEnabled = Boolean.FALSE;
		try{
			isFreeFormatEnabled = Boolean.parseBoolean(System.getProperty("kyc.freeformat"));	
			return isFreeFormatEnabled;
		}catch(Exception e){
			LOG.error("Error while reading property 'kyc.freeformat' ",e);
			return isFreeFormatEnabled;
		}
	}

     /**
      * Sets the free format address line fields.
      *
      * @param addr the addr
      * @param resultList the result list
      */
	@SuppressWarnings("squid:S3011")
     private void setFreeFormatAddressLineFields(Object addr, List<String> resultList){
		
		try {
			int count=1;
			for(String s:resultList){
				Field newField = addr.getClass().getDeclaredField("addressLine"+count);
				newField.setAccessible(true);
				newField.set(addr, s);
				count++;
				if(count > 5){
					break;
				}
			}
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
			LOG.error(" Error in GBGroupTransformer : setFreeFormatAddressLines() {1} ", e);
		}
	}

	
}

