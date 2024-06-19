package com.currenciesdirect.gtg.compliance.kyc.lexisnexisport;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Identification;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.commons.util.StringUtils;
import com.currenciesdirect.gtg.compliance.kyc.core.IKYCDBService;
import com.currenciesdirect.gtg.compliance.kyc.core.domain.KYCProviderProperty;
import com.currenciesdirect.gtg.compliance.kyc.dbport.KYCDBServiceImpl;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCErrors;
import com.currenciesdirect.gtg.compliance.kyc.exception.KYCException;
import com.currenciesdirect.gtg.compliance.kyc.util.Constants;
import com.verid.carbon.integration.AccountCategoryType;
import com.verid.carbon.integration.AccountOriginationType;
import com.verid.carbon.integration.AccountType;
import com.verid.carbon.integration.AccountVerificationType;
import com.verid.carbon.integration.AddressContextType;
import com.verid.carbon.integration.AddressType;
import com.verid.carbon.integration.BirthdateType;
import com.verid.carbon.integration.CredentialMethodType;
import com.verid.carbon.integration.CredentialType;
import com.verid.carbon.integration.IdentityVerificationSettingsType;
import com.verid.carbon.integration.ModeType;
import com.verid.carbon.integration.OnlineType;
import com.verid.carbon.integration.Passport;
import com.verid.carbon.integration.PersonType;
import com.verid.carbon.integration.PhoneNumberContextType;
import com.verid.carbon.integration.PhoneNumberType;
import com.verid.carbon.integration.TaskType;
import com.verid.carbon.integration.TransactionIdentityVerification;
import com.verid.carbon.integration.TransactionType;
import com.verid.carbon.integration.VenueType;

/**
 * The Class CarbonServiceTransformer.
 */
public class CarbonServiceTransformer {

	/** The carbon service transformer. */
	private static CarbonServiceTransformer carbonServiceTransformer = null;

	/** The ikyc dao. */
	private IKYCDBService ikycDao = KYCDBServiceImpl.getInstance();

	/**
	 * Instantiates a new carbon service transformer.
	 */
	private CarbonServiceTransformer() {

	}

	/**
	 * Gets the single instance of CarbonServiceTransformer.
	 *
	 * @return single instance of CarbonServiceTransformer
	 */
	public static CarbonServiceTransformer getInstance() {
		if (carbonServiceTransformer == null) {
			carbonServiceTransformer = new CarbonServiceTransformer();
		}
		return carbonServiceTransformer;
	}

	/**
	 * Transform request object.
	 *
	 * @param request
	 *            the request
	 * @param property
	 *            the property
	 * @return the transaction identity verification
	 * @throws KYCException
	 *             the KYC exception
	 */
	public TransactionIdentityVerification transformRequestObject(KYCContactRequest request,
			KYCProviderProperty property) throws KYCException {
		TransactionIdentityVerification transactionIdentityVerification = new TransactionIdentityVerification();
		Date date = null;
		Calendar c1 = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Boolean foundSupportedCoutry = Boolean.FALSE;
		try {
			String country = request.getAddress().getCountry().toUpperCase();
			String countryShortCode = ikycDao.getCountryShortCode(country);
			date = formatter.parse(request.getPersonalDetails().getDob());
			c1 = Calendar.getInstance();
			c1.setTime(date);
			setIdentityVerificationSettingsType(property, transactionIdentityVerification);

			setAdditionalDetailsForService(property, transactionIdentityVerification);

			foundSupportedCoutry = createNorthCountryWiseRequests(request, transactionIdentityVerification, c1, country,
					countryShortCode);

			if (Boolean.FALSE.equals(foundSupportedCoutry))
				createSouthCountryWiseRequests(request, transactionIdentityVerification, c1, country, countryShortCode);

		} catch (KYCException e) {
			throw e;
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
		return transactionIdentityVerification;
	}

	/**
	 * Sets the additional details for service.
	 *
	 * @param property
	 *            the property
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 */
	private void setAdditionalDetailsForService(KYCProviderProperty property,
			TransactionIdentityVerification transactionIdentityVerification) {

		CredentialType credentialType = new CredentialType();
		credentialType.getCredentialMethod().add(CredentialMethodType.PIN);
		credentialType.setUsername(property.getCredentialTypeUserName());
		credentialType.setDomain(property.getCredentialTypeDomain());
		credentialType.setIpAddress(property.getCredentialTypeIpAddress());

		OnlineType onlineType = new OnlineType();
		onlineType.setCredential(credentialType);

		VenueType venueType = new VenueType();
		venueType.setOnline(onlineType);

		AccountOriginationType accountOriginationType = new AccountOriginationType();
		accountOriginationType.setAccountCategory(AccountCategoryType.BROKERAGE);

		AccountType accountType = new AccountType();
		accountType.getCustomerId().add(property.getAccountTypeCustomerId());
		accountOriginationType.setAccount(accountType);

		AccountVerificationType accountVerificationType = new AccountVerificationType();
		accountVerificationType.setAccountOrigination(accountOriginationType);
		accountVerificationType.setVenue(venueType);

		TransactionType tt = new TransactionType();
		tt.setAccountVerification(accountVerificationType);

		transactionIdentityVerification.setTransaction(tt);
	}

	/**
	 * Sets the identity verification settings type.
	 *
	 * @param property
	 *            the property
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 */
	private void setIdentityVerificationSettingsType(KYCProviderProperty property,
			TransactionIdentityVerification transactionIdentityVerification) {

		IdentityVerificationSettingsType identityVerificationSettingsType = new IdentityVerificationSettingsType();
		identityVerificationSettingsType.setAccountName(property.getIdentityVerificationAccountName());
		identityVerificationSettingsType.setRuleset(property.getIdentityVerificationRuleset());
		identityVerificationSettingsType.setTask(TaskType.IAUTH);
		identityVerificationSettingsType.setMode(ModeType.valueOf(property.getModeType()));
		transactionIdentityVerification.setSettings(identityVerificationSettingsType);
	}

	/**
	 * Creates the north country wise requests.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param country
	 *            the country
	 * @param countryShortCode
	 *            the country short code
	 * @return true, if successful
	 * @throws KYCException
	 *             the KYC exception
	 */
	private boolean createNorthCountryWiseRequests(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String country,
			String countryShortCode) throws KYCException {
		Boolean countryFound = Boolean.FALSE;
		switch (country) {
		case Constants.COUNTRY_JAPAN:
			createRequestForJapan(request, transactionIdentityVerification, c1, countryShortCode);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_AUSTRIA:
		case Constants.COUNTRY_LUXEMBOURG:
			createRequestForAustriaLuxembourg(request, transactionIdentityVerification, c1, countryShortCode);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_CANADA:
			createRequestForCanada(request, transactionIdentityVerification, c1, countryShortCode);
			countryFound = Boolean.TRUE;
			break;
		case Constants.COUNTRY_CHINA:
			createRequestForChina(request, transactionIdentityVerification, c1, countryShortCode);
			countryFound = Boolean.TRUE;
			break;
		default:
			break;
		}
		return countryFound;
	}

	/**
	 * Creates the south country wise requests.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param country
	 *            the country
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createSouthCountryWiseRequests(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String country,
			String countryShortCode) throws KYCException {
		switch (country) {
		case Constants.COUNTRY_AUSTRALIA:
			createRequestForAustralia(request, transactionIdentityVerification, c1, countryShortCode);
			break;

		case Constants.COUNTRY_NEWZEALAND:
			createRequestForNewZealand(request, transactionIdentityVerification, c1, countryShortCode);
			break;

		case Constants.COUNTRY_MEXICO:
			createRequestForMexico(request, transactionIdentityVerification, c1, countryShortCode);
			break;

		case Constants.COUNTRY_SOUTHAFRICA:
			createRequestForSouthAfrica(request, transactionIdentityVerification, c1, countryShortCode);
			break;

		case Constants.COUNTRY_BRAZIL:
			createRequestForBrazil(request, transactionIdentityVerification, c1, countryShortCode);
			break;

		case Constants.COUNTRY_HONGKONG:
			createRequestForHonKong(request, transactionIdentityVerification, c1, countryShortCode);
			break;

		default:
			throw new KYCException(KYCErrors.COUNTRY_IS_NOT_SUPPORTED);
		}
	}

	/**
	 * Creates the request for australia.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForAustralia(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForAustraliaBrazilSouthafricaMexicoNewzealand(request, countryShortCode, address);

			fillIdentityDetailsForAustralia(request, person, formatter);

			addressList.add(address);
			person.getAddress().addAll(addressList);
			fillPhoneDetailsForAustraliaLuxembourgAustriaJapan(request, person);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
	}

	/**
	 * Fill identity details for australia.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 * @param formatter
	 *            the formatter
	 * @throws ParseException
	 *             the parse exception
	 */
	private void fillIdentityDetailsForAustralia(KYCContactRequest request, PersonType person,
			SimpleDateFormat formatter) throws ParseException {

		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				switch (identity.getType().toUpperCase().replaceAll("\\s+", "")) {
				case Constants.KYC_RTA_CARD_NUMBER:
					person.setNationalId(identity.getNumber());
					break;
				case Constants.KYC_PASSPORT:
					fillPassportDetailsForAustralia(person, formatter, identity);
					break;
				case Constants.KYC_DRIVING_LICENSE:
					person.setDriversLicenseNumber(identity.getNumber());
					person.setDriversLicenseState(identity.getStateOfIssue());

					break;
				case Constants.KYC_MEDICARE:
					person.setNationalId(identity.getNumber());
					break;
				default:
					break;
				}
			}
		}
	}

	/**
	 * Fill passport details for australia.
	 *
	 * @param person
	 *            the person
	 * @param formatter
	 *            the formatter
	 * @param identity
	 *            the identity
	 * @throws ParseException
	 *             the parse exception
	 */
	private void fillPassportDetailsForAustralia(PersonType person, SimpleDateFormat formatter, Identification identity)
			throws ParseException {
		Calendar c1;
		Date date;
		Passport passport = new Passport();
		com.verid.carbon.integration.Date carbonDate = new com.verid.carbon.integration.Date();
		passport.setNumber(identity.getNumber());
		if (!(identity.getExiprydate().isEmpty())) {
			date = formatter.parse(identity.getExiprydate());
			c1 = Calendar.getInstance();
			c1.setTime(date);
			carbonDate.setDay((short) c1.get(Calendar.DAY_OF_MONTH));
			carbonDate.setMonth((short) (c1.get(Calendar.MONTH) + 1));
			carbonDate.setYear((short) c1.get(Calendar.YEAR));
			passport.setExpirationDate(carbonDate);

		}
		passport.setCountry(identity.getCountryOfIssue());
		person.setPassport(passport);
	}

	/**
	 * Creates the request for hon kong.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForHonKong(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {

		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();

		try {
			setPersonalDetails(request, c1, person, birthdate);

			address.setAddressStreet1(request.getAddress().getAddress1());
			address.setAddressStreet2(request.getAddress().getBuildingName());

			if (!StringUtils.isNullOrTrimEmpty(request.getAddress().getCity())) {
				address.setAddressLocality(request.getAddress().getCity());
			}

			address.setAddressCountry(countryShortCode);
			address.setAddressContext(AddressContextType.PRIMARY);

			fillIndentityPhoneDetailsForHongkong(request, person);

			addressList.add(address);
			person.getAddress().addAll(addressList);
			transactionIdentityVerification.setPerson(person);

		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
	}

	/**
	 * Fill indentity phone details for hongkong.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillIndentityPhoneDetailsForHongkong(KYCContactRequest request, PersonType person) {
		PhoneNumberType phoneNumberType = new PhoneNumberType();
		List<PhoneNumberType> phoneNumberTypeList = new ArrayList<>();

		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if (Constants.KYC_HK_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+", ""))) {
					person.setNationalId(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.HOME);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				}
			}
		}
		phoneNumberTypeList.add(phoneNumberType);
		person.getPhoneNumber().addAll(phoneNumberTypeList);
	}

	/**
	 * Creates the request for brazil.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForBrazil(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();
		Passport passport = new Passport();
		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForAustraliaBrazilSouthafricaMexicoNewzealand(request, countryShortCode, address);

			passport.setGender(request.getPersonalDetails().getGender());
			person.setPassport(passport);

			fillIdentityPhoneDetailsForBrazil(request, person);

			if (person.getNationalId() == null || person.getNationalId().isEmpty()) {
				person.setNationalId(" ");
			}

			addressList.add(address);
			person.getAddress().addAll(addressList);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
	}

	/**
	 * Fill identity phone details for brazil.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillIdentityPhoneDetailsForBrazil(KYCContactRequest request, PersonType person) {

		PhoneNumberType phoneNumberType = new PhoneNumberType();
		List<PhoneNumberType> phoneNumberTypeList = new ArrayList<>();

		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if (Constants.KYC_NATIONAL_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+", ""))) {
					person.setNationalId(identity.getNumber());
				}
			}
		}

		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.HOME);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				}
			}
		}
		phoneNumberTypeList.add(phoneNumberType);
		person.getPhoneNumber().addAll(phoneNumberTypeList);
	}

	/**
	 * Creates the request for south africa.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForSouthAfrica(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();
		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForAustraliaBrazilSouthafricaMexicoNewzealand(request, countryShortCode, address);

			addressList.add(address);
			person.getAddress().addAll(addressList);

			fillIndentityPhoneDetailsForSA(request, person);

			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

	}

	/**
	 * Fill indentity phone details for SA.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillIndentityPhoneDetailsForSA(KYCContactRequest request, PersonType person) {
		PhoneNumberType phoneNumberType = new PhoneNumberType();
		List<PhoneNumberType> phoneNumberTypeList = new ArrayList<>();

		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if (Constants.KYC_NATIONAL_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+", ""))) {
					person.setNationalId(identity.getNumber());
				}
			}
		}

		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.HOME);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				} else if (Constants.KYC_PHONE_TYPE_MOBILE.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.MOBILE);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				}
			}
		}

		phoneNumberTypeList.add(phoneNumberType);
		person.getPhoneNumber().addAll(phoneNumberTypeList);
	}

	/**
	 * Creates the request for mexico.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForMexico(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();
		Passport passport = new Passport();
		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForAustraliaBrazilSouthafricaMexicoNewzealand(request, countryShortCode, address);

			passport.setGender(request.getPersonalDetails().getGender());
			addressList.add(address);
			person.getAddress().addAll(addressList);
			person.setPassport(passport);
			fillIdentityPhoneDetailsForMexico(request, person);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

	}

	/**
	 * Fill identity phone details for mexico.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillIdentityPhoneDetailsForMexico(KYCContactRequest request, PersonType person) {
		PhoneNumberType phoneNumberType = new PhoneNumberType();
		List<PhoneNumberType> phoneNumberTypeList = new ArrayList<>();

		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if (Constants.KYC_CURP_ID_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+", ""))) {
					person.setNationalId(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.HOME);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				}
			}
		}
		phoneNumberTypeList.add(phoneNumberType);
		person.getPhoneNumber().addAll(phoneNumberTypeList);
	}

	/**
	 * Creates the request for new zealand.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForNewZealand(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();

		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForAustraliaBrazilSouthafricaMexicoNewzealand(request, countryShortCode, address);

			addressList.add(address);
			person.getAddress().addAll(addressList);

			fillPhoneIdentityDetailsForNZ(request, person);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

	}

	/**
	 * Fill phone identity details for NZ.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillPhoneIdentityDetailsForNZ(KYCContactRequest request, PersonType person) {
		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if (Constants.KYC_DRIVING_LICENSE.equalsIgnoreCase(identity.getType().replaceAll("\\s+", ""))) {
					person.setDriversLicenseNumber(identity.getNumber());
				}
			}
		}
	}

	/**
	 * Creates the request for china.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForChina(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();

		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForChinaAustriaJapanLuxembourg(request, countryShortCode, address);

			addressList.add(address);
			person.getAddress().addAll(addressList);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

	}

	/**
	 * Creates the request for canada.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForCanada(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();
		try {
			setPersonalDetails(request, c1, person, birthdate);

			address.setAddressStreet1(request.getAddress().getAddress1());
			address.setAddressStreet2(request.getAddress().getStreetType());
			address.setAddressPostcode(request.getAddress().getPostCode());
			address.setAddressProvince(request.getAddress().getState());

			if (!StringUtils.isNullOrTrimEmpty(request.getAddress().getCity())) {
				address.setAddressLocality(request.getAddress().getCity());
			}

			address.setAddressCountry(countryShortCode);
			address.setAddressContext(AddressContextType.PRIMARY);

			fillIdentityPhoneDetailsForCanada(request, person);

			addressList.add(address);
			person.getAddress().addAll(addressList);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}
	}

	/**
	 * Fill identity phone details for canada.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillIdentityPhoneDetailsForCanada(KYCContactRequest request, PersonType person) {
		PhoneNumberType phoneNumberType = new PhoneNumberType();
		List<PhoneNumberType> phoneNumberTypeList = new ArrayList<>();

		if (request.getIdentification() != null) {
			for (Identification identity : request.getIdentification()) {
				if (Constants.KYC_SOCIAL_INSURANCE_NUMBER.equalsIgnoreCase(identity.getType().replaceAll("\\s+", ""))) {
					person.setNationalId(identity.getNumber());
				}
			}
		}
		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.HOME);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				}
			}
		}
		phoneNumberTypeList.add(phoneNumberType);
		person.getPhoneNumber().addAll(phoneNumberTypeList);
	}

	/**
	 * Creates the request for luxembourg.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForAustriaLuxembourg(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {

		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();

		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForChinaAustriaJapanLuxembourg(request, countryShortCode, address);

			fillPhoneDetailsForAustraliaLuxembourgAustriaJapan(request, person);

			addressList.add(address);
			person.getAddress().addAll(addressList);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

	}

	/**
	 * Creates the request for japan.
	 *
	 * @param request
	 *            the request
	 * @param transactionIdentityVerification
	 *            the transaction identity verification
	 * @param c1
	 *            the c 1
	 * @param countryShortCode
	 *            the country short code
	 * @throws KYCException
	 *             the KYC exception
	 */
	private void createRequestForJapan(KYCContactRequest request,
			TransactionIdentityVerification transactionIdentityVerification, Calendar c1, String countryShortCode)
			throws KYCException {
		PersonType person = new PersonType();
		BirthdateType birthdate = new BirthdateType();
		AddressType address = new AddressType();
		List<AddressType> addressList = new ArrayList<>();
		Passport passport = new Passport();

		try {
			setPersonalDetails(request, c1, person, birthdate);

			setAddressForChinaAustriaJapanLuxembourg(request, countryShortCode, address);

			passport.setGender(request.getPersonalDetails().getGender());
			person.setPassport(passport);

			fillPhoneDetailsForAustraliaLuxembourgAustriaJapan(request, person);

			addressList.add(address);
			person.getAddress().addAll(addressList);
			transactionIdentityVerification.setPerson(person);
		} catch (Exception e) {
			throw new KYCException(KYCErrors.INVALID_REQUEST, e);
		}

	}

	
	/**
	 * Sets the address for australia brazil southafrica mexico newzealand.
	 *
	 * @param request
	 *            the request
	 * @param countryShortCode
	 *            the country short code
	 * @param address
	 *            the address
	 */
	private void setAddressForAustraliaBrazilSouthafricaMexicoNewzealand(KYCContactRequest request,
			String countryShortCode, AddressType address) {
		address.setAddressStreet1(request.getAddress().getAddress1());

		if (!StringUtils.isNullOrTrimEmpty(request.getAddress().getCity())) {
			address.setAddressLocality(request.getAddress().getCity());
		}

		address.setAddressPostcode(request.getAddress().getPostCode());
		address.setAddressProvince(request.getAddress().getState());
		address.setAddressCountry(countryShortCode);
		address.setAddressContext(AddressContextType.PRIMARY);
	}

	/**
	 * Sets the address for china austria japan luxembourg.
	 *
	 * @param request
	 *            the request
	 * @param countryShortCode
	 *            the country short code
	 * @param address
	 *            the address
	 */
	private void setAddressForChinaAustriaJapanLuxembourg(KYCContactRequest request, String countryShortCode,
			AddressType address) {
		address.setAddressStreet1(request.getAddress().getAddress1());
		address.setAddressPostcode(request.getAddress().getPostCode());

		if (!StringUtils.isNullOrTrimEmpty(request.getAddress().getCity())) {
			address.setAddressLocality(request.getAddress().getCity());
		}

		address.setAddressCountry(countryShortCode);
		address.setAddressContext(AddressContextType.PRIMARY);
	}

	/**
	 * Sets the personal details.
	 *
	 * @param request
	 *            the request
	 * @param c1
	 *            the c 1
	 * @param person
	 *            the person
	 * @param birthdate
	 *            the birthdate
	 */
	private void setPersonalDetails(KYCContactRequest request, Calendar c1, PersonType person,
			BirthdateType birthdate) {

		person.setNameFirst(request.getPersonalDetails().getForeName());
		if (!StringUtils.isNullOrTrimEmpty(request.getPersonalDetails().getMiddleName())) {
			person.setNameMiddle(request.getPersonalDetails().getMiddleName());
		}
		person.setNameLast(request.getPersonalDetails().getSurName());

		birthdate.setYear(BigInteger.valueOf(c1.get(Calendar.YEAR)));
		birthdate.setMonth(c1.get(Calendar.MONTH) + 1);
		birthdate.setDay(c1.get(Calendar.DAY_OF_MONTH));
		person.setBirthdate(birthdate);
	}

	/**
	 * Fill phone details for australia luxembourg austria.
	 *
	 * @param request
	 *            the request
	 * @param person
	 *            the person
	 */
	private void fillPhoneDetailsForAustraliaLuxembourgAustriaJapan(KYCContactRequest request, PersonType person) {
		PhoneNumberType phoneNumberType = new PhoneNumberType();
		List<PhoneNumberType> phoneNumberTypeList = new ArrayList<>();

		if (request.getPhone() != null) {
			for (Phone phone : request.getPhone()) {
				if (Constants.KYC_PHONE_TYPE_HOME.equalsIgnoreCase(phone.getType().replaceAll("\\s+", ""))) {
					phoneNumberType.setPhoneNumberContext(PhoneNumberContextType.HOME);
					phoneNumberType.setPhoneNumber(phone.getNumber());
				}
			}
		}
		phoneNumberTypeList.add(phoneNumberType);
		person.getPhoneNumber().addAll(phoneNumberTypeList);
	}

}
