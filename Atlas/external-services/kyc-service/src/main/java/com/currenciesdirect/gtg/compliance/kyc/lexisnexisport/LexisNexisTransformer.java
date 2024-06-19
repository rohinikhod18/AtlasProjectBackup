/**
 * 
 */
package com.currenciesdirect.gtg.compliance.kyc.lexisnexisport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Identification;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.seisint.webservices.WsIdentity.AustraliaPassport;
import com.seisint.webservices.WsIdentity.AustraliaVerification;
import com.seisint.webservices.WsIdentity.AustriaVerification;
import com.seisint.webservices.WsIdentity.BrazilVerification;
import com.seisint.webservices.WsIdentity.CanadaVerification;
import com.seisint.webservices.WsIdentity.ChinaVerification;
import com.seisint.webservices.WsIdentity.CountryCodeEnum;
import com.seisint.webservices.WsIdentity.HongKongVerification;
import com.seisint.webservices.WsIdentity.InstantIDIntl2Request;
import com.seisint.webservices.WsIdentity.InstantIDIntlSearchBy;
import com.seisint.webservices.WsIdentity.JapanVerification;
import com.seisint.webservices.WsIdentity.LuxembourgVerification;
import com.seisint.webservices.WsIdentity.MexicoVerification;
import com.seisint.webservices.WsIdentity.NewZealandVerification;
import com.seisint.webservices.WsIdentity.SouthAfricaVerification;
import com.seisint.webservices.WsIdentity.User;

/**
 * @author manish
 *
 */
public class LexisNexisTransformer {

	private static LexisNexisTransformer lexisNexisTransformer = null;

	private LexisNexisTransformer() {

	}

	public static LexisNexisTransformer getInstance() {
		if (lexisNexisTransformer == null) {
			lexisNexisTransformer = new LexisNexisTransformer();
		}
		return lexisNexisTransformer;
	}

	/**
	 * Transform request object.
	 *
	 * @param request the request
	 * @return the instant ID intl 2 request
	 * @throws KYCException the KYC exception
	 */
	public InstantIDIntl2Request transformRequestObject(KYCContactRequest request) throws KYCException {
		InstantIDIntl2Request idIntl2Request = new InstantIDIntl2Request();
		InstantIDIntlSearchBy idIntlSearchBy = new InstantIDIntlSearchBy();
		User user=new User();
		String country = null;
		Date date = null;
		Calendar c1 = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Boolean foundSupportedCoutry = Boolean.FALSE;
		try {
			country = request.getAddress().getCountry().toUpperCase();
			date = formatter.parse(request.getPersonalDetails().getDob());
			c1 = Calendar.getInstance();
			c1.setTime(date);
			user.setQueryId(request.getContactSFId());
			if(isGLBAndDLPurposeEnable()){
				user.setGLBPurpose(getGLBPurposeValue());
				user.setDLPurpose(getDLPurposeValue());
			}
			
			idIntl2Request.setUser(user);
			
			foundSupportedCoutry = createNorthCountryWiseRequests(request, idIntl2Request, idIntlSearchBy, country, c1);
			if(Boolean.FALSE.equals(foundSupportedCoutry))
				createSouthCountryWiseRequests(request, idIntl2Request, idIntlSearchBy, country, c1);

		} catch (KYCException e) {
			throw e;
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return idIntl2Request;
	}

	private boolean createNorthCountryWiseRequests(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, String country, Calendar c1) throws KYCException {
		Boolean countryFound = Boolean.FALSE;
		switch (country) {
		case Constants.COUNTRY_JAPAN:
			createRequestForJapan(request, idIntl2Request, idIntlSearchBy, c1);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_AUSTRIA:
			createRequestForAustria(request, idIntl2Request, idIntlSearchBy, c1);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_LUXEMBOURG:
			createRequestForLuxembourg(request, idIntl2Request, idIntlSearchBy, c1);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_CANADA:
			createRequestForCanada(request, idIntl2Request, idIntlSearchBy, c1);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_CHINA:
			createRequestForChina(request, idIntl2Request, idIntlSearchBy, c1);
			countryFound = Boolean.TRUE;
			break;
		default:
			break;
		}
		return countryFound;
	}

	private void createRequestForChina(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, Calendar c1) throws KYCException {
		idIntlSearchBy.setCountry(CountryCodeEnum.CN);
		ChinaVerification chinaVerification = transformChina(request, c1);
		idIntlSearchBy.setChinaVerification(chinaVerification);
		idIntl2Request.setSearchBy(idIntlSearchBy);
	}

	private void createRequestForCanada(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, Calendar c1) throws KYCException {
		idIntlSearchBy.setCountry(CountryCodeEnum.CA);
		CanadaVerification canadaVerification = transFormCanada(request, c1);
		idIntlSearchBy.setCanadaVerification(canadaVerification);
		idIntl2Request.setSearchBy(idIntlSearchBy);
	}

	private void createRequestForLuxembourg(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, Calendar c1) throws KYCException {
		idIntlSearchBy.setCountry(CountryCodeEnum.LU);
		LuxembourgVerification luxembourgVerification = transformLuxemourg(request, c1);
		idIntlSearchBy.setLuxembourgVerification(luxembourgVerification);
		idIntl2Request.setSearchBy(idIntlSearchBy);
	}

	private void createRequestForAustria(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, Calendar c1) throws KYCException {
		idIntlSearchBy.setCountry(CountryCodeEnum.AT);
		AustriaVerification austriaVerification = transformAustria(request, c1);
		idIntlSearchBy.setAustriaVerification(austriaVerification);
		idIntl2Request.setSearchBy(idIntlSearchBy);
	}

	private void createRequestForJapan(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, Calendar c1) throws KYCException {
		idIntlSearchBy.setCountry(CountryCodeEnum.JP);
		JapanVerification japanVerification = transformJapan(request, c1);
		idIntlSearchBy.setJapanVerification(japanVerification);
		idIntl2Request.setSearchBy(idIntlSearchBy);
	}
	
	private void createSouthCountryWiseRequests(KYCContactRequest request, InstantIDIntl2Request idIntl2Request,
			InstantIDIntlSearchBy idIntlSearchBy, String country, Calendar c1) throws KYCException {
		switch (country) {
		
		case Constants.COUNTRY_AUSTRALIA:
			idIntlSearchBy.setCountry(CountryCodeEnum.AU);
			AustraliaVerification australiaVerification = transformAustralia(request, c1);
			idIntlSearchBy.setAustraliaVerification(australiaVerification);
			idIntl2Request.setSearchBy(idIntlSearchBy);
			break;
		case Constants.COUNTRY_NEWZEALAND:
			idIntlSearchBy.setCountry(CountryCodeEnum.NZ);
			NewZealandVerification newZealandVerification = transformNewZealand(request, c1);
			idIntlSearchBy.setNewZealandVerification(newZealandVerification);
			idIntl2Request.setSearchBy(idIntlSearchBy);
			break;
		
		case Constants.COUNTRY_MEXICO:
			idIntlSearchBy.setCountry(CountryCodeEnum.MX);
			MexicoVerification mexicoVerification = transformMexico(request, c1);
			idIntlSearchBy.setMexicoVerification(mexicoVerification);
			idIntl2Request.setSearchBy(idIntlSearchBy);
			break;
		
		case Constants.COUNTRY_SOUTHAFRICA:
			idIntlSearchBy.setCountry(CountryCodeEnum.ZA);
			SouthAfricaVerification southAfricaVerification = transformSouthAfrica(request, c1);
			idIntlSearchBy.setSouthAfricaVerification(southAfricaVerification);
			idIntl2Request.setSearchBy(idIntlSearchBy);
			break;
		
		case Constants.COUNTRY_BRAZIL:
			idIntlSearchBy.setCountry(CountryCodeEnum.BR);
			BrazilVerification brazilVerification = transformBrazil(request, c1);
			idIntlSearchBy.setBrazilVerification(brazilVerification);
			idIntl2Request.setSearchBy(idIntlSearchBy);
			break;
		
		case Constants.COUNTRY_HONGKONG:
			idIntlSearchBy.setCountry(CountryCodeEnum.HK);
			HongKongVerification hongKongVerification = transFormHonKong(request, c1);
			idIntlSearchBy.setHongKongVerification(hongKongVerification);
			idIntl2Request.setSearchBy(idIntlSearchBy);
			break;
		
		default:
			throw new KYCException(KYCErrors.COUNTRY_IS_NOT_SUPPORTED);
		}
	}

	private boolean isGLBAndDLPurposeEnable(){
		String property = System.getProperty("lexisnexis.enable.GLB/DL");
		return (property != null && "true".equalsIgnoreCase(property.trim()));
	}
	
	private String getGLBPurposeValue(){
		String property = System.getProperty("lexisnexis.GLBPurpose");
		if(property != null && !property.trim().isEmpty()){
			return property.trim();
		}
		return "0";
	}
	
	
	private String getDLPurposeValue(){
		String property = System.getProperty("lexisnexis.DLPurpose");
		if(property != null && !property.trim().isEmpty()){
			return property.trim();
		}
		return "0";
	}

	private CanadaVerification transFormCanada(KYCContactRequest request, Calendar c1) throws KYCException {
		CanadaVerification canadaVerification = new CanadaVerification();
		try {
			canadaVerification.setFirstName(request.getPersonalDetails().getForeName());
			canadaVerification.setLastName(request.getPersonalDetails().getSurName());

			canadaVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			canadaVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			canadaVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			canadaVerification.setStreetName(request.getAddress().getStreet());
			canadaVerification.setStreetType(request.getAddress().getStreetType());
			canadaVerification.setPostalCode(request.getAddress().getPostCode());
			canadaVerification.setUnitNumber(request.getAddress().getUnitNumber());
			canadaVerification.setCivicNumber(request.getAddress().getCivicNumber());
			canadaVerification.setMunicipality(request.getAddress().getCity());
			canadaVerification.setProvince(request.getAddress().getState());

			fillIdentityPhoneDetailsForCanada(request, canadaVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return canadaVerification;
	}

	private void fillIdentityPhoneDetailsForCanada(KYCContactRequest request, CanadaVerification canadaVerification) {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if(Constants.KYC_SOCIAL_INSURANCE_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					canadaVerification.setSocialInsuranceNumber(identity.getNumber());
				}
			}
		}

		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					canadaVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private HongKongVerification transFormHonKong(KYCContactRequest request, Calendar c1) throws KYCException {
		HongKongVerification hongKongVerification = new HongKongVerification();
		try {
			hongKongVerification.setFirstName(request.getPersonalDetails().getForeName());
			hongKongVerification.setLastName(request.getPersonalDetails().getSurName());

			hongKongVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			hongKongVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			hongKongVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			hongKongVerification.setStreetName(request.getAddress().getStreet());
			hongKongVerification.setCity(request.getAddress().getCity());
			hongKongVerification.setBuildingName(request.getAddress().getBuildingName());
			hongKongVerification.setBuildingNumber(request.getAddress().getBuildingNumber());
			hongKongVerification.setUnitNumber(request.getAddress().getUnitNumber());
			hongKongVerification.setDistrict(request.getAddress().getDistrict());
			
			fillIndentityPhoneDetailsForHongkong(request, hongKongVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return hongKongVerification;

	}

	private void fillIndentityPhoneDetailsForHongkong(KYCContactRequest request,
			HongKongVerification hongKongVerification) {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if(Constants.KYC_HK_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					hongKongVerification.setHongKongIDNumber(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					hongKongVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private LuxembourgVerification transformLuxemourg(KYCContactRequest request, Calendar c1) throws KYCException {
		LuxembourgVerification luxembourgVerification = new LuxembourgVerification();
		try {
			luxembourgVerification.setFirstName(request.getPersonalDetails().getForeName());
			luxembourgVerification.setLastName(request.getPersonalDetails().getSurName());
			luxembourgVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			luxembourgVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			luxembourgVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			luxembourgVerification.setHouseNumber(String.valueOf(request.getAddress().getBuildingNumber()));
			luxembourgVerification.setStreetName(request.getAddress().getStreet());
			luxembourgVerification.setCity(request.getAddress().getCity());
			luxembourgVerification.setPostalCode(request.getAddress().getPostCode());
			
			fillPhoneDetailsForLuxembourg(request, luxembourgVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return luxembourgVerification;
	}

	private void fillPhoneDetailsForLuxembourg(KYCContactRequest request,
			LuxembourgVerification luxembourgVerification) {
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					luxembourgVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private BrazilVerification transformBrazil(KYCContactRequest request, Calendar c1) throws KYCException {
		BrazilVerification brazilVerification = new BrazilVerification();
		try {
			brazilVerification.setFirstName(request.getPersonalDetails().getForeName());
			brazilVerification.setLastName(request.getPersonalDetails().getSurName());
			brazilVerification.setGender(request.getPersonalDetails().getGender());
			brazilVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			brazilVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH)));
			brazilVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH) + 1));
			brazilVerification.setAddress1(request.getAddress().getAddress1());
			brazilVerification.setUnitNumber(String.valueOf(request.getAddress().getUnitNumber()));
			brazilVerification.setCity(request.getAddress().getCity());
			brazilVerification.setState(request.getAddress().getState());
			brazilVerification.setPostalCode(request.getAddress().getPostCode());
			fillIdentityPhoneDetailsForBrazil(request, brazilVerification);
			if(brazilVerification.getNationalIDNumber()==null || brazilVerification.getNationalIDNumber().isEmpty()){
				brazilVerification.setNationalIDNumber(" ");
			}
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return brazilVerification;
	}

	private void fillIdentityPhoneDetailsForBrazil(KYCContactRequest request, BrazilVerification brazilVerification) {
		brazilVerification.setPostalCode(request.getAddress().getPostCode());
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if(Constants.KYC_NATIONAL_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					brazilVerification.setNationalIDNumber(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					brazilVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private JapanVerification transformJapan(KYCContactRequest request, Calendar c1) throws KYCException {
		JapanVerification japanVerification = new JapanVerification();
		try {
			japanVerification.setFirstName(request.getPersonalDetails().getForeName());
			japanVerification.setSurname(request.getPersonalDetails().getSurName());
			japanVerification.setGender(request.getPersonalDetails().getGender());
			japanVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			japanVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			japanVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			// fill remaining fields after getting test data - Area Numbers and
			// prefecture muncipality?
			
			japanVerification.setAreaNumbers(request.getAddress().getAreaNumber());
			japanVerification.setMunicipality(request.getAddress().getCity());
			japanVerification.setPrefecture(request.getAddress().getState());
			japanVerification.setAza(request.getAddress().getAza());
			japanVerification.setBuildingName(request.getAddress().getBuildingName());
			japanVerification.setPostalCode(request.getAddress().getPostCode());

			fillPhoneDetailsForJapan(request, japanVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return japanVerification;
	}

	private void fillPhoneDetailsForJapan(KYCContactRequest request, JapanVerification japanVerification) {
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					japanVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private SouthAfricaVerification transformSouthAfrica(KYCContactRequest request, Calendar c1) throws KYCException {
		SouthAfricaVerification southAfricaVerification = new SouthAfricaVerification();
		try {
			southAfricaVerification.setFirstName(request.getPersonalDetails().getForeName());
			southAfricaVerification.setLastName(request.getPersonalDetails().getSurName());
			southAfricaVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			southAfricaVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			southAfricaVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			southAfricaVerification.setAddress1(request.getAddress().getAddress1());
			southAfricaVerification.setCity(request.getAddress().getCity());
			southAfricaVerification.setSuburb(request.getAddress().getRegion());
			southAfricaVerification.setProvince(request.getAddress().getState());

			southAfricaVerification.setPostalCode(request.getAddress().getPostCode());

			fillIndentityPhoneDetailsForSA(request, southAfricaVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return southAfricaVerification;

	}

	private void fillIndentityPhoneDetailsForSA(KYCContactRequest request,
			SouthAfricaVerification southAfricaVerification) {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if(Constants.KYC_NATIONAL_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					southAfricaVerification.setNationalIDNumber(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					southAfricaVerification.setTelephone(phone.getNumber());
				} else if(Constants.KYC_PHONE_TYPE_MOBILE.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					southAfricaVerification.setCellNumber(phone.getNumber());
				}
			}
		}
	}

	private NewZealandVerification transformNewZealand(KYCContactRequest request, Calendar c1) throws KYCException {
		NewZealandVerification newZealandVerification = new NewZealandVerification();
		try {
			newZealandVerification.setFirstName(request.getPersonalDetails().getForeName());
			newZealandVerification.setLastName(request.getPersonalDetails().getSurName());
			newZealandVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			newZealandVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			newZealandVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			if(request.getAddress().getBuildingNumber() != null){
				newZealandVerification.setHouseNumber(String.valueOf(request.getAddress().getBuildingNumber()));
			}
			newZealandVerification.setStreetName(request.getAddress().getStreet());
			newZealandVerification.setStreetType(request.getAddress().getStreetType());
			newZealandVerification.setUnitNumber(request.getAddress().getUnitNumber());
			newZealandVerification.setSuburb(request.getAddress().getRegion());
			newZealandVerification.setCity(request.getAddress().getCity());
			
			fillPhoneIdentityDetailsForNZ(request, newZealandVerification);
			newZealandVerification.setPostalCode(request.getAddress().getPostCode());
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return newZealandVerification;
	}

	private void fillPhoneIdentityDetailsForNZ(KYCContactRequest request, NewZealandVerification newZealandVerification) {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if(Constants.KYC_DRIVING_LICENSE.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					newZealandVerification.setDriverLicenceNumber(identity.getNumber());
					newZealandVerification.setDriverLicenceVersionNumber(identity.getDlVersionNumber());
				}
			}
		}
		
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					newZealandVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private MexicoVerification transformMexico(KYCContactRequest request, Calendar c1) throws KYCException {
		MexicoVerification mexicoVerification = new MexicoVerification();
		try {
			mexicoVerification.setFirstName(request.getPersonalDetails().getForeName());
			mexicoVerification.setFirstSurname(request.getPersonalDetails().getSurName());
			mexicoVerification.setSecondSurname(request.getPersonalDetails().getSecondSurname());
			// Check whether we should pass MALE or M
			mexicoVerification.setGender(request.getPersonalDetails().getGender());
			mexicoVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			mexicoVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			mexicoVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			mexicoVerification.setAddress1(request.getAddress().getAddress1());
			mexicoVerification.setCity(request.getAddress().getCity());
			mexicoVerification.setState(request.getAddress().getState());
			mexicoVerification.setPostalCode(request.getAddress().getPostCode());
			
			fillIdentityPhoneDetailsForMexico(request, mexicoVerification);
			mexicoVerification.setStateOfBirth(request.getPersonalDetails().getStateOfBirth());
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return mexicoVerification;
	}

	private void fillIdentityPhoneDetailsForMexico(KYCContactRequest request, MexicoVerification mexicoVerification) {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if(Constants.KYC_CURP_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+",""))) {
					mexicoVerification.setCURPIDNumber(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					mexicoVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private AustriaVerification transformAustria(KYCContactRequest request, Calendar c1) throws KYCException {
		AustriaVerification austriaVerification = new AustriaVerification();
		try {
			austriaVerification.setFirstName(request.getPersonalDetails().getForeName());
			austriaVerification.setLastName(request.getPersonalDetails().getSurName());
			austriaVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			austriaVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			austriaVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			austriaVerification.setHouseNumber(request.getAddress().getBuildingNumber());
			austriaVerification.setStreetName(request.getAddress().getStreet());
			austriaVerification.setPostalCode(request.getAddress().getPostCode());
			austriaVerification.setCity(request.getAddress().getCity());
			
			fillPhoneDetailsForAustria(request, austriaVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return austriaVerification;
	}

	private void fillPhoneDetailsForAustria(KYCContactRequest request, AustriaVerification austriaVerification) {
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					austriaVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private ChinaVerification transformChina(KYCContactRequest request, Calendar c1) throws KYCException {
		ChinaVerification chinaVerification = new ChinaVerification();
		try {
			chinaVerification.setSurname(request.getPersonalDetails().getSurName());
			chinaVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			chinaVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			chinaVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			chinaVerification.setStreetName(request.getAddress().getStreet());
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return chinaVerification;
	}

	private AustraliaVerification transformAustralia(KYCContactRequest request, Calendar c1) throws KYCException {
		AustraliaVerification australiaVerification = new AustraliaVerification();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try {
			australiaVerification.setFirstName(request.getPersonalDetails().getForeName());
			australiaVerification.setLastName(request.getPersonalDetails().getSurName());
			australiaVerification.setYearOfBirth(String.valueOf(c1.get(Calendar.YEAR)));
			australiaVerification.setMonthOfBirth(String.valueOf(c1.get(Calendar.MONTH) + 1));
			australiaVerification.setDayOfBirth(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));

			// not confirmed
			if(request.getAddress().getStreetNumber() == null || 
					request.getAddress().getStreetNumber().trim().isEmpty()){
				australiaVerification.setStreetNumber(request.getAddress().getBuildingNumber());
			} else australiaVerification.setStreetNumber(request.getAddress().getStreetNumber());
			
			australiaVerification.setStreetName(request.getAddress().getStreet());
			australiaVerification.setStreetType(request.getAddress().getStreetType());
			// Not in request - Region is equivalent t suburb
			australiaVerification.setSuburb(request.getAddress().getRegion());

			australiaVerification.setPostalCode(request.getAddress().getPostCode());
			
			australiaVerification.setState(request.getAddress().getState());
			australiaVerification.setUnitNumber(request.getAddress().getUnitNumber());
			fillIdentityDetailsForAustralia(request, australiaVerification, formatter);
			fillPhoneDetailsForAustralia(request, australiaVerification);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return australiaVerification;
	}

	private void fillPhoneDetailsForAustralia(KYCContactRequest request, AustraliaVerification australiaVerification) {
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if(Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+",""))) {
					australiaVerification.setTelephone(phone.getNumber());
				}
			}
		}
	}

	private void fillIdentityDetailsForAustralia(KYCContactRequest request, AustraliaVerification australiaVerification,
			SimpleDateFormat formatter) throws ParseException {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				switch (identity.getType().toUpperCase().replaceAll("\\s+", "")) {
				case Constants.KYC_RTA_CARD_NUMBER:
					australiaVerification.setRTACardNumber(identity.getNumber());
					break;
				case Constants.KYC_PASSPORT:
					fillPassportDetailsForAustralia(request, australiaVerification, formatter, identity);
					break;
				case Constants.KYC_DRIVING_LICENSE:
					australiaVerification.setDriverLicenceNumber(identity.getNumber());
					australiaVerification.setDriverLicenceState(identity.getStateOfIssue());
					
					break;
				case Constants.KYC_MEDICARE:
					australiaVerification.setMedicareNumber(identity.getNumber());
					/**Medicare ref no is set to resolve AT-356 by Vishal J*/
					australiaVerification.setMedicareReference(identity.getMedicareRefNumber());
					break;
				default:
					break;
				}
			}
		}
	}

	private void fillPassportDetailsForAustralia(KYCContactRequest request, AustraliaVerification australiaVerification,
			SimpleDateFormat formatter, Identification identity) throws ParseException {
		Calendar c1;
		Date date;
		AustraliaPassport australiaPassport = new AustraliaPassport();
		australiaPassport.setPassportNumber(identity.getNumber());
		if (!(identity.getExiprydate().isEmpty())) {
			date = formatter.parse(identity.getExiprydate());
			c1 = Calendar.getInstance();
			c1.setTime(date);
			australiaPassport.setPassportDayOfExpiry(String.valueOf(c1.get(Calendar.DAY_OF_MONTH)));
			australiaPassport.setPassportMonthOfExpiry(String.valueOf(c1.get(Calendar.MONTH) + 1));
			australiaPassport.setPassportYearOfExpiry(String.valueOf(c1.get(Calendar.YEAR)));
		}
		australiaPassport.setFamilyNameAtBirth(identity.getFamilyNameAtBirth());
		australiaPassport.setFamilyNameAtCitizenship(identity.getFamilyNameAtCitizenship());
		australiaPassport.setPlaceOfBirth(request.getPersonalDetails().getMunicipalityOfBirth());
		australiaPassport.setPassportCountry(identity.getCountryOfIssue());
		australiaVerification.setPassport(australiaPassport);
	}
}
