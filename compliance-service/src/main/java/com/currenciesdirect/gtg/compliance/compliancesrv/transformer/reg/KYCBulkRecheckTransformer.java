package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Identification;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCProviderResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.PersonalDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Phone;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.NationalIdType;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class KYCBulkRecheckTransformer.
 */
public class KYCBulkRecheckTransformer extends AbstractTransformer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KYCBulkRecheckTransformer.class);

	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

	/** The Constant ERROR_MSG. */
	private static final String ERROR_MSG = "Error creating request for provider:";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {

		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			boolean notBlackListed = true;

			if (registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED) != null) {
				notBlackListed = (boolean) registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED);
			}
			if (notBlackListed) {
				transformPFXRequest(message);
			} else {
				createEventServiceLogEntryForNotRequired(message, registrationRequest);
			}

		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the event service log entry for not required.
	 *
	 * @param message
	 *            the message
	 * @param registrationRequest
	 *            the registration request
	 */
	private void createEventServiceLogEntryForNotRequired(Message<MessageContext> message,
			RegistrationServiceRequest registrationRequest) {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contact = new ArrayList<>();
			List<KYCContactResponse> contactResponses;
			Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
					.getAdditionalAttribute(Constants.FIELD_EVENTID);
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();
			UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());

			MessageExchange ccExchange = new MessageExchange();

			ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);

			contactResponses = createEventServiceLogForNotRequired(registrationRequest, eventId, ccExchange, token,
					orgID);
			kycProviderRequest.setContact(contact);
			kycProviderResponse.setContactResponse(contactResponses);
			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().removeMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
	}

	/**
	 * Transform PFX request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXRequest(Message<MessageContext> message) throws ComplianceException {

		try {

			transformPFXRecheckServiceFailureRequest(message);

		} catch (Exception e) {
			LOGGER.warn(ERROR_MSG, e);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,e);
		}
		return message;

	}

	/**
	 * Transform PFX recheck service failure request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXRecheckServiceFailureRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();
			kycProviderRequest.setCorrelationID(registrationRequest.getCorrelationID());
			kycProviderRequest.setAccountSFId(account.getAccSFID());
			kycProviderRequest.setOrgCode(registrationRequest.getOrgCode());
			kycProviderRequest.setLegalEntity(account.getCustLegalEntity());//AT-3327
			kycProviderRequest.setSourceApplication(registrationRequest.getSourceApplication());
			kycProviderResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			List<KYCContactResponse> contactResponses = new ArrayList<>();
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
			for (Contact contact : contacts) {
				if (null != contactId && contactId.equals(contact.getId())) {
					KYCContactRequest contactRequest = new KYCContactRequest();

					if (contact.isKYCEligible()) {
						populateContactRequest(contactRequests, orgID, contact, contactRequest);
					}

					KYCContactResponse kycContactResponse = new KYCContactResponse();
					KYCSummary summary = new KYCSummary();
					kycContactResponse.setId(contact.getId());
					summary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
					summary.setDob(contact.getDob());
					summary.setEidCheck(Boolean.FALSE);
					if (!contact.isKYCEligible()) {
						ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
								kycContactResponse, summary, ServiceStatus.NOT_REQUIRED));
					} else {
						ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
								kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));

					}
				}
			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);

			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().removeMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.warn(ERROR_MSG, e);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,e);
		}
		return message;
	}

	/**
	 * Populate contact request.
	 *
	 * @param contactRequests
	 *            the contact requests
	 * @param orgID
	 *            the org ID
	 * @param contact
	 *            the contact
	 * @param contactRequest
	 *            the contact request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void populateContactRequest(List<KYCContactRequest> contactRequests, String orgID, Contact contact,
			KYCContactRequest contactRequest) throws ComplianceException {
		Address address;
		PersonalDetails personalDetails;
		List<Phone> phones;
		List<Identification> identifications;
		address = new Address();
		personalDetails = new PersonalDetails();

		identifications = new ArrayList<>();
		phones = new ArrayList<>();
		contactRequest.setId(contact.getId());
		String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
		contactRequest.setContactSFId(providerRef);

		personalDetails.setTitle(contact.getTitle());
		personalDetails.setForeName(contact.getFirstName());
		personalDetails.setMiddleName(contact.getMiddleName());
		personalDetails.setSurName(contact.getLastName());
		personalDetails.setSecondSurname(contact.getSecondSurname());
		personalDetails.setDob(contact.getDob());
		personalDetails.setGender(contact.getGender());
		personalDetails.setMunicipalityOfBirth(contact.getMunicipalityOfBirth());
		personalDetails.setStateOfBirth(contact.getStateOfBirth());
		if (null != contact.getCountryOfBirth()) {
			personalDetails.setCountryOfBirth(null != countryCache.getCountryFullName(contact.getCountryOfBirth())
					? countryCache.getCountryFullName(contact.getCountryOfBirth()) : "");
		}
		contactRequest.setPersonalDetails(personalDetails);

		address.setAddress1(contact.getBuildingNumber() + " " + contact.getStreet());
		address.setBuildingName(contact.getBuildingName());
		address.setBuildingNumber(contact.getBuildingNumber());
		address.setCity(contact.getCity());
		address.setCivicNumber(contact.getCivicNumber());
		if (null != contact.getCountry()) {
			address.setCountry(null != countryCache.getCountryFullName(contact.getCountry())
					? countryCache.getCountryFullName(contact.getCountry()) : "");
		}
		address.setPostCode(contact.getPostCode());
		address.setRegion(contact.getRegion());
		address.setState(contact.getState());
		address.setStreet(contact.getStreet());
		address.setStreetType(contact.getStreetType());
		address.setStreetNumber(contact.getStreetNumber());
		address.setSubBuildingOrFlat(contact.getSubBuildingorFlat());
		address.setSubCity(contact.getSubCity());
		address.setUnitNumber(contact.getUnitNumber());
		address.setAza(contact.getAza());
		address.setPrefecture(contact.getPrefecture());
		address.setDistrict(contact.getDistrict());
		address.setAreaNumber(contact.getAreaNumber());

		setFreeFormatAddressLineFields(address, contact);
		contactRequest.setAddress(address);

		transformPhoneInfo(phones, contact);
		contactRequest.setPhone(phones);

		transformIdentificationInfo(identifications, contact);
		contactRequest.setIdentification(identifications);
		contactRequests.add(contactRequest);
	}

	/**
	 * Sets the free format address line fields.
	 *
	 * @param address
	 *            the address
	 * @param contact
	 *            the contact
	 */
	private void setFreeFormatAddressLineFields(Address address, Contact contact) {
		address.setAddressLine1(contact.getAddress1()); // Without parsing
														// street

	}

	/**
	 * Sets the KYC default response status.
	 *
	 * @param message
	 *            the message
	 * @param contactResponses
	 *            the contact responses
	 * @param contact
	 *            the contact
	 * @param kycContactResponse
	 *            the kyc contact response
	 * @param summary
	 *            the summary
	 * @param serviceStatus
	 *            the service status
	 * @return the event service log
	 */
	private EventServiceLog setKYCDefaultResponseStatus(Message<MessageContext> message,
			List<KYCContactResponse> contactResponses, Contact contact, KYCContactResponse kycContactResponse,
			KYCSummary summary, ServiceStatus serviceStatus) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		Integer eventId = (Integer) message.getPayload().getGatewayMessageExchange().getRequest()
				.getAdditionalAttribute(Constants.FIELD_EVENTID);
		kycContactResponse.setStatus(serviceStatus.name());
		summary.setStatus(serviceStatus.name());
		contactResponses.add(kycContactResponse);
		return createEventServiceLogEntryWithStatus(eventId, ServiceTypeEnum.KYC_SERVICE,
				ServiceProviderEnum.KYC_SERVICE, contact.getId(), contact.getVersion(), EntityEnum.CONTACT.name(),
				token.getUserID(), kycContactResponse, summary, serviceStatus);
	}

	/**
	 * Transform identification info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformIdentificationInfo(List<Identification> identifications, Contact contact) {
		try {
			transformPassportInfo(identifications, contact);

			transaformDLInfo(identifications, contact);

			transformMedicareInfo(identifications, contact);

			transformHKIdInfo(identifications, contact);

			transformSANationalIdInfo(identifications, contact);

			transformCurpIdInfo(identifications, contact);

			transformSocialInsuranceInfo(identifications, contact);

			transformSSNInfo(identifications, contact);

			transformNIFInfo(identifications, contact);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
	}

	/**
	 * Transform NIF info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformNIFInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getNationalIdNumber()) && contact.getCountry().equals("ESP")) {
			Identification identification = new Identification();
			identification.setType(Constants.TAX_ID_NUMBER);
			identification.setNumber(contact.getNationalIdNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform SSN info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformSSNInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getNationalIdType()) && !isNullOrEmpty(contact.getNationalIdNumber())
				&& contact.getNationalIdType().equals(NationalIdType.SSN.getName())) {
			Identification identification = new Identification();
			identification.setType(Constants.IDENTITY_CARD);
			identification.setNumber(contact.getNationalIdNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform social insurance info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformSocialInsuranceInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getNationalIdType()) && !isNullOrEmpty(contact.getNationalIdNumber())
				&& contact.getNationalIdType().equals(NationalIdType.SOCIAL_INSURANCE_NUMBER.getName())) {
			Identification identification = new Identification();
			identification.setType(Constants.SOCIAL_INSURANCE_NUMBER);
			identification.setNumber(contact.getNationalIdNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform curp id info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformCurpIdInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getNationalIdType()) && !isNullOrEmpty(contact.getNationalIdNumber())
				&& contact.getNationalIdType().equals(NationalIdType.CURP_ID_NUMBER.getName())) {
			Identification identification = new Identification();
			identification.setType(Constants.CURP_ID_NUMBER);
			identification.setNumber(contact.getNationalIdNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform SA national id info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformSANationalIdInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getNationalIdType()) && !isNullOrEmpty(contact.getNationalIdNumber())
				&& contact.getNationalIdType().equals(NationalIdType.SA_NATIONAL_ID_NUMBER.getName())) {
			Identification identification = new Identification();
			identification.setType(Constants.NATIONAL_ID_NUMBER);
			identification.setNumber(contact.getNationalIdNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform HK id info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformHKIdInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getNationalIdType()) && !isNullOrEmpty(contact.getNationalIdNumber())
				&& contact.getNationalIdType().equals(NationalIdType.HK_ID_NUMBER.getName())) {
			Identification identification = new Identification();
			identification.setType(Constants.HK_ID_NUMBER);
			identification.setNumber(contact.getNationalIdNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform medicare info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 */
	private void transformMedicareInfo(List<Identification> identifications, Contact contact) {
		if (!isNullOrEmpty(contact.getMedicareCardNumber())) {
			Identification identification = new Identification();
			identification.setType(Constants.MEDICARE);
			identification.setNumber(contact.getMedicareCardNumber());
			identification.setMedicareRefNumber(contact.getMedicareReferenceNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transaform DL info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void transaformDLInfo(List<Identification> identifications, Contact contact) throws ComplianceException {
		if (!isNullOrEmpty(contact.getDlLicenseNumber())) {
			Identification identification = new Identification();
			identification.setType(Constants.DRIVING_LICENSE);
			identification.setNumber(contact.getDlLicenseNumber());
			identification.setExiprydate(contact.getDlExpiryDate());
			if (null != contact.getDlCountryCode()) {
				identification.setCountryOfIssue(null != countryCache.getCountryFullName(contact.getDlCountryCode())
						? countryCache.getCountryFullName(contact.getDlCountryCode()) : "");
			}
			identification.setStateOfIssue(contact.getDlStateCode());
			identification.setDlVersionNumber(contact.getDlVersionNumber());
			identifications.add(identification);
		}
	}

	/**
	 * Transform passport info.
	 *
	 * @param identifications
	 *            the identifications
	 * @param contact
	 *            the contact
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void transformPassportInfo(List<Identification> identifications, Contact contact)
			throws ComplianceException {
		if (!isNullOrEmpty(contact.getPassportNumber())) {
			Identification identification = new Identification();
			identification.setType(Constants.PASSPORT);
			identification.setNumber(contact.getPassportNumber());
			identification.setPassportFullName(contact.getPassportFullName());
			identification.setPassportMRZLine1(contact.getPassportMRZLine1());
			identification.setPassportMRZLine2(contact.getPassportMRZLine2());
			identification.setExiprydate(contact.getPassportExiprydate());
			if (null != contact.getDlCountryCode()) {
				identification
						.setCountryOfIssue(null != countryCache.getCountryFullName(contact.getPassportCountryCode())
								? countryCache.getCountryFullName(contact.getPassportCountryCode()) : "");
			}
			identifications.add(identification);
		}
	}

	/**
	 * Transform phone info.
	 *
	 * @param phones
	 *            the phones
	 * @param contact
	 *            the contact
	 */
	private void transformPhoneInfo(List<Phone> phones, Contact contact) {
		try {
			if (contact.getPhoneMobile() != null && !contact.getPhoneMobile().isEmpty()) {
				Phone phone = new Phone();
				phone.setType(Constants.PHONE_TYPE_MOBILE);
				phone.setNumber(contact.getPhoneMobile());
				phones.add(phone);
			}

			if (contact.getPhoneHome() != null && !contact.getPhoneHome().isEmpty()) {
				Phone phone = new Phone();
				phone.setType(Constants.PHONE_TYPE_HOME);
				phone.setNumber(contact.getPhoneHome());
				phones.add(phone);
			}
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		try {
			message.getPayload().setServiceTobeInvoked(ServiceTypeEnum.KYC_SERVICE);
			MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.KYC_SERVICE);
			KYCProviderResponse kycProviderResponse = (KYCProviderResponse) exchange.getResponse();
			KYCProviderRequest kycProviderRequest = exchange.getRequest(KYCProviderRequest.class);

			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			Integer contactId = (Integer) registrationRequest.getAdditionalAttribute(Constants.CONTACT_ID);

			if (kycProviderResponse == null) {
				List<KYCContactResponse> contactResponseList = new ArrayList<>();

				KYCSummary kycSummary = new KYCSummary();
				kycProviderResponse = new KYCProviderResponse();
				kycProviderResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());

				kycResponseForServiceFailure(exchange, kycProviderResponse, kycProviderRequest, orgID, contactId,
						contactResponseList, kycSummary);
				kycProviderResponse.setContactResponse(contactResponseList);
				exchange.setResponse(kycProviderResponse);
				return message;
			}

			KYCProviderRequest kycRequest = (KYCProviderRequest) exchange.getRequest();
			List<KYCContactResponse> contactResponseList = kycProviderResponse.getContactResponse();

			if (contactResponseList != null) {
				for (KYCContactResponse kycContactResponse : contactResponseList) {
					if (null != contactId && contactId.equals(kycContactResponse.getId())) {
						setKYCSummaryDetails(exchange, kycRequest, kycContactResponse);
					}
				}
			} else {
				contactResponseList = new ArrayList<>();
				setKYCSummaryDetailsForServiceFailure(exchange, kycProviderResponse, orgID, contactId, kycRequest,
						contactResponseList);
			}
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			setKYCSummaryDetailsForNotRequired(exchange, orgID, contacts);
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	private void setKYCSummaryDetailsForNotRequired(MessageExchange exchange, String orgID, List<Contact> contacts) {
		for (Contact contact : contacts) {
			if (!contact.isKYCEligible()) {
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE,
						EntityEnum.CONTACT.name(), contact.getId());
				KYCSummary kycSummary = new KYCSummary();
				KYCContactResponse defaultResponse = new KYCContactResponse();
				defaultResponse.setId(contact.getId());
				defaultResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
				kycSummary.setStatus(ServiceStatus.NOT_REQUIRED.name());
				kycSummary.setCheckedOn(eventServiceLog.getCreatedOn().toString());
				kycSummary.setDob(DateTimeFormatter.getUKDateFormat(contact.getDob()));
				kycSummary.setReferenceId(providerRef);
				kycSummary.setVerifiactionResult(Constants.NOT_AVAILABLE);
				kycSummary.setEidCheck(Boolean.FALSE);
				eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(defaultResponse));
				eventServiceLog.setStatus(ServiceStatus.NOT_REQUIRED.name());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
				eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			}
		}
	}

	private void setKYCSummaryDetailsForServiceFailure(MessageExchange exchange,
			KYCProviderResponse kycProviderResponse, String orgID, Integer contactId, KYCProviderRequest kycRequest,
			List<KYCContactResponse> contactResponseList) {
		for (KYCContactRequest c : kycRequest.getContact()) {
			if (null != contactId && contactId.equals(c.getId())) {
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, c.getId() + "");
				KYCContactResponse kycContactResponse = new KYCContactResponse();
				kycContactResponse.setId(c.getId());
				kycContactResponse.setStatus(ServiceStatus.FAIL.name());
				contactResponseList.add(kycContactResponse);

				KYCSummary kycSummary = new KYCSummary();
				kycSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				kycSummary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
				kycSummary.setDob(DateTimeFormatter.getUKDateFormat(c.getPersonalDetails().getDob()));
				kycSummary.setEidCheck(Boolean.FALSE);
				kycSummary.setReferenceId(providerRef);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE,
						EntityEnum.CONTACT.name(), c.getId());
				eventServiceLog
						.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(kycProviderResponse));
				eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
				eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
			}
		}
	}

	private void kycResponseForServiceFailure(MessageExchange exchange, KYCProviderResponse kycProviderResponse,
			KYCProviderRequest kycProviderRequest, String orgID, Integer contactId,
			List<KYCContactResponse> contactResponseList, KYCSummary kycSummary) {
		for (KYCContactRequest contact : kycProviderRequest.getContact()) {

			if (null != contactId && contactId.equals(contact.getId())) {
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT,
						contact.getId() + "");
				KYCContactResponse contactResponse = new KYCContactResponse();
				contactResponse.setBandText(ServiceStatus.SERVICE_FAILURE.name());
				contactResponse.setId(contact.getId());
				contactResponseList.add(contactResponse);
				EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE,
						EntityEnum.CONTACT.name(), contact.getId());
				kycProviderResponse.setContactResponse(null);
				eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				kycSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
				kycSummary.setCheckedOn(eventServiceLog.getCreatedOn().toString());
				kycSummary.setDob(DateTimeFormatter.getUKDateFormat(contact.getPersonalDetails().getDob()));
				kycSummary.setEidCheck(Boolean.FALSE);
				kycSummary.setReferenceId(providerRef);
				eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
				eventServiceLog
						.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(contactResponse));
			}
		}
	}

	/**
	 * Sets the KYC summary details.
	 *
	 * @param exchange
	 *            the exchange
	 * @param kycRequest
	 *            the kyc request
	 * @param kycContactResponse
	 *            the kyc contact response
	 */
	private void setKYCSummaryDetails(MessageExchange exchange, KYCProviderRequest kycRequest,
			KYCContactResponse kycContactResponse) {
		KYCSummary kycSummary = new KYCSummary();

		EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE,
				EntityEnum.CONTACT.name(), kycContactResponse.getId());
		if (ServiceStatus.SERVICE_FAILURE.name().equals(kycContactResponse.getStatus())
				|| ServiceStatus.NOT_PERFORMED.name().equals(kycContactResponse.getStatus())
				|| ServiceStatus.NOT_REQUIRED.name().equals(kycContactResponse.getStatus())) {
			kycSummary.setEidCheck(Boolean.FALSE);
			kycSummary.setVerifiactionResult(Constants.NOT_AVAILABLE);
			kycSummary.setReferenceId(kycContactResponse.getContactSFId());
		} else {
			kycSummary.setEidCheck(Boolean.TRUE);
			kycSummary.setVerifiactionResult(kycContactResponse.getOverallScore());
			kycSummary.setReferenceId(kycContactResponse.getContactSFId());
		}

		kycSummary.setStatus(kycContactResponse.getStatus());
		eventServiceLog.setStatus(kycContactResponse.getStatus());

		kycSummary.setCheckedOn(eventServiceLog.getUpdatedOn().toString());
		kycSummary.setProviderName(kycContactResponse.getProviderName());
		kycSummary.setProviderMethod(kycContactResponse.getProviderMethod());

		KYCContactRequest req = kycRequest.getContactRequestByID(kycContactResponse.getId());
		if (req != null) {
			kycSummary.setDob(DateTimeFormatter.getUKDateFormat(req.getPersonalDetails().getDob()));
		}
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(kycContactResponse));
		String providerResponse = kycContactResponse.getProviderResponse();
		kycContactResponse.setProviderResponse(null);
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		kycContactResponse.setProviderResponse(providerResponse);
		if (kycContactResponse.getProviderName() != null)
			eventServiceLog.setServiceProviderName(kycContactResponse.getProviderName());
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str
	 *            the str
	 * @return true, if is null or empty
	 */
	private boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.isEmpty())
			return false;

		return result;
	}

	/**
	 * Creates the event service log for not required.
	 *
	 * @param request
	 *            the request
	 * @param eventId
	 *            the event id
	 * @param ccExchange
	 *            the cc exchange
	 * @param token
	 *            the token
	 * @param orgID
	 *            the org ID
	 * @return the list
	 */
	private List<KYCContactResponse> createEventServiceLogForNotRequired(RegistrationServiceRequest request,
			Integer eventId, MessageExchange ccExchange, UserProfile token, String orgID) {

		List<KYCContactResponse> contactResponsesList = new ArrayList<>();

		try {
			Account account = request.getAccount();

			for (Contact contact : account.getContacts()) {
				String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
				KYCContactResponse kycContactResponse = new KYCContactResponse();
				KYCSummary summary = new KYCSummary();
				kycContactResponse.setId(contact.getId());
				summary.setStatus(ServiceStatus.NOT_REQUIRED.name());
				summary.setReferenceId(providerRef);
				summary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
				summary.setDob(DateTimeFormatter.getUKDateFormat(contact.getDob()));
				summary.setEidCheck(Boolean.FALSE);
				kycContactResponse.setStatus(ServiceStatus.NOT_REQUIRED.name());
				ccExchange
						.addEventServiceLog(createEventServiceLogEntryNotRequired(eventId, ServiceTypeEnum.KYC_SERVICE,
								ServiceProviderEnum.KYC_SERVICE, contact.getId(), contact.getVersion(),
								EntityEnum.CONTACT.name(), token.getUserID(), kycContactResponse, summary));
				contactResponsesList.add(kycContactResponse);
			}
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return contactResponsesList;
	}
}
