/*
 * 
 */
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
import com.currenciesdirect.gtg.compliance.compliancesrv.core.OperationEnum;
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
 * The Class KYCTransformer.
 */
public class KYCTransformer extends AbstractTransformer {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(KYCTransformer.class);

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
			Account account = registrationRequest.getAccount();
			String custType = account.getCustType();
			boolean notBlackListed = true;
			boolean blacklistedEUCustomer = false;
			/***
			 * "PERFORM_REMAINING_CHECKS" value is set in
			 * InternalRuleServiceTransformer
			 * 
			 * 1) If PERFORM_REMAINING_CHECKS is true means Contact is Not
			 * BlackListed and we can perform further checks like Sanction etc.
			 * 2) Else part is executed when PERFORM_REMAINING_CHECKS is false
			 * means Contact is found in BlackList and Further
			 * checks(Sanction,KYC) should not be performed but Insert entry
			 * NOT_REQUIRED for All Remaining Checks. 3) Corner Scenario : For
			 * PFX and CFX Sanction Resend Operation : Default Value is "True"
			 * since no initial condition like ( BlackListed or NonBlackListed
			 * )is applied " So notBlackListed variable is set to "TRUE"" .
			 */
			if (registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED) != null) {
				notBlackListed = (boolean) registrationRequest.getAdditionalAttribute(Constants.NOT_BLACKLISTED);
			}
			/*
			 * if(registrationRequest.getAdditionalAttribute(Constants.
			 * BLACKLISTED_EU_CUSTOMER_POI) != null) { blacklistedEUCustomer = (boolean)
			 * registrationRequest.getAdditionalAttribute(Constants.
			 * BLACKLISTED_EU_CUSTOMER_POI); }
			 *///AT-3398
			if (notBlackListed /* || blacklistedEUCustomer */) {
				if (custType != null) {
					custType = custType.trim();
				}
				if (Constants.CFX.equalsIgnoreCase(custType)) {
					transformCFXRequest(message);
				} else if (Constants.PFX.equalsIgnoreCase(custType)) {
					transformPFXRequest(message);
				}
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
			/**
			 * creates EventServiceLog entry for status "NOT_REQUIRED" , by
			 * Abhijit G
			 */
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
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.NEW_REGISTRATION==operation) {
				transformPFXSignupRequest(message);
			} else if (OperationEnum.ADD_CONTACT==operation) {
				transformPFXAddContactRequest(message);
			} else if (OperationEnum.UPDATE_ACCOUNT==operation) {
				transformPFXUpdateRequest(message);
			} else if (OperationEnum.KYC_RESEND==operation) {
				transformPFXResendRequest(message);
			}
		} catch (Exception e) {
			LOGGER.warn(ERROR_MSG, e);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,e);
		}
		return message;

	}

	/**
	 * Transform PFX signup request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXSignupRequest(Message<MessageContext> message)
			throws ComplianceException {
		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();

			kycProviderRequest.setCorrelationID(registrationRequest.getCorrelationID());
			kycProviderRequest.setAccountSFId(account.getAccSFID());
			kycProviderRequest.setOrgCode(registrationRequest.getOrgCode());
			kycProviderRequest.setSourceApplication(registrationRequest.getSourceApplication());
			//AT-3327
			kycProviderRequest.setLegalEntity(account.getCustLegalEntity());
			kycProviderResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			List<KYCContactResponse> contactResponses = new ArrayList<>();
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
			for (Contact contact : contacts) {
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
				ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
						kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));

			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);
			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.warn(ERROR_MSG, e);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,e);
		}
		return message;
	}

	/**
	 * Transform PFX add contact request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXAddContactRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
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
				ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
						kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));

			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);

			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception exception) {
			LOGGER.warn(ERROR_MSG, exception);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,exception);
		}
		return message;
	}

	/**
	 * Transform PFX update request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXUpdateRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();

			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();
			kycProviderRequest.setCorrelationID(registrationRequest.getCorrelationID());
			kycProviderRequest.setAccountSFId(account.getAccSFID());
			kycProviderRequest.setOrgCode(registrationRequest.getOrgCode());
			kycProviderRequest.setSourceApplication(registrationRequest.getSourceApplication());
			//AT-3327
			kycProviderRequest.setLegalEntity(account.getCustLegalEntity());
			kycProviderResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			List<KYCContactResponse> contactResponses = new ArrayList<>();
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
			for (Contact contact : contacts) {
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
				// verify this
				if (!contact.isKYCEligible()) {
					ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
							kycContactResponse, summary, ServiceStatus.NOT_REQUIRED));
				} else {
					ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
							kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));

				}
			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);

			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
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
		/**
		 * We are making reference ID here that should be showed on UI, changes
		 * done by Vishal J
		 */
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

		if (!StringUtils.isNullOrTrimEmpty(contact.getBuildingNumber())) {
			address.setAddress1(contact.getBuildingNumber() + " " + contact.getStreet());
		} else if (!StringUtils.isNullOrTrimEmpty(contact.getStreetNumber())) {
			address.setAddress1(contact.getStreetNumber() + " " + contact.getStreet());
		} else {
			address.setAddress1(contact.getStreet());
		}

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
		//AT-3719
		if (null != contact.getCountry() && contact.getCountry().equalsIgnoreCase(Constants.USA)) {
			address.setState(null != countryCache.getCountryStateCodeValue(contact.getState())
					? countryCache.getCountryStateCodeValue(contact.getState()) : contact.getState());
		}else {
			address.setState(contact.getState());
		}
		
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
	 * Transform PFX resend request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformPFXResendRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
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
				KYCContactRequest contactRequest = new KYCContactRequest();

				populateContactRequest(contactRequests, orgID, contact, contactRequest);

				KYCContactResponse kycContactResponse = new KYCContactResponse();
				KYCSummary summary = new KYCSummary();
				kycContactResponse.setId(contact.getId());
				summary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
				summary.setDob(contact.getDob());
				summary.setEidCheck(Boolean.FALSE);
				ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
						kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));

			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);
			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.warn(ERROR_MSG, e);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,e);
		}
		return message;
	}

	/**
	 * Transform CFX request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformCFXRequest(Message<MessageContext> message) throws ComplianceException {
		try {
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			OperationEnum operation = messageExchange.getOperation();
			if (OperationEnum.NEW_REGISTRATION==operation) {
				transformCFXSignupRequest(message);
			} else if (OperationEnum.ADD_CONTACT==operation) {
				transformCFXAddContactRequest(message);
			} else if (OperationEnum.UPDATE_ACCOUNT==operation) {
				transformCFXUpdateRequest(message);
			} else if (OperationEnum.KYC_RESEND==operation) {
				transformCFXResendRequest(message);
			}

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	/**
	 * Transform CFX signup request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCFXSignupRequest(Message<MessageContext> message) {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();
			kycProviderRequest.setCorrelationID(registrationRequest.getCorrelationID());
			kycProviderRequest.setAccountSFId(account.getAccSFID());
			kycProviderRequest.setOrgCode(registrationRequest.getOrgCode());
			kycProviderRequest.setSourceApplication(registrationRequest.getSourceApplication());
			//AT-3327
			kycProviderRequest.setLegalEntity(account.getCustLegalEntity());
			kycProviderResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			List<KYCContactResponse> contactResponses = new ArrayList<>();
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);
			for (Contact contact : contacts) {

				KYCContactResponse kycContactResponse = new KYCContactResponse();
				KYCSummary summary = new KYCSummary();
				kycContactResponse.setId(contact.getId());
				summary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
				summary.setDob(contact.getDob());
				summary.setEidCheck(Boolean.FALSE);
				ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
						kycContactResponse, summary, ServiceStatus.NOT_REQUIRED));

			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);
			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().appendMessageExchange(ccExchange);

		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	/**
	 * Transform CFX update request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCFXUpdateRequest(Message<MessageContext> message) {
		return transformCFXAddAndUpdateCommonRequest(message);
	}

	/**
	 * Transform CFX add contact request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCFXAddContactRequest(Message<MessageContext> message) {
		return transformCFXAddAndUpdateCommonRequest(message);
	}

	/**
	 * Transform CFX add and update common request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	private Message<MessageContext> transformCFXAddAndUpdateCommonRequest(Message<MessageContext> message) {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			Account account = registrationRequest.getAccount();
			List<Contact> contacts = account.getContacts();
			KYCProviderResponse kycProviderResponse = new KYCProviderResponse();
			kycProviderRequest.setCorrelationID(registrationRequest.getCorrelationID());
			kycProviderRequest.setAccountSFId(account.getAccSFID());
			kycProviderRequest.setOrgCode(registrationRequest.getOrgCode());
			kycProviderRequest.setSourceApplication(registrationRequest.getSourceApplication());
			//AT-3327
			kycProviderRequest.setLegalEntity(account.getCustLegalEntity());
			kycProviderResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

			List<KYCContactResponse> contactResponses = new ArrayList<>();
			MessageExchange ccExchange = new MessageExchange();
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.KYC_SERVICE);

			for (Contact contact : contacts) {

				KYCContactResponse kycContactResponse = new KYCContactResponse();
				KYCSummary summary = new KYCSummary();
				kycContactResponse.setId(contact.getId());
				summary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
				summary.setDob(contact.getDob());
				summary.setEidCheck(Boolean.FALSE);
				ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
						kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));
			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);
			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOGGER.error("{1}",e);
		}
		return message;
	}

	/**
	 * Transform CFX resend request.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private Message<MessageContext> transformCFXResendRequest(Message<MessageContext> message)
			throws ComplianceException {

		try {
			KYCProviderRequest kycProviderRequest = new KYCProviderRequest();
			List<KYCContactRequest> contactRequests = new ArrayList<>();
			MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());
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
				KYCContactRequest contactRequest = new KYCContactRequest();
				populateContactRequest(contactRequests, orgID, contact, contactRequest);

				KYCContactResponse kycContactResponse = new KYCContactResponse();
				KYCSummary summary = new KYCSummary();
				kycContactResponse.setId(contact.getId());
				summary.setCheckedOn(new Timestamp(System.currentTimeMillis()).toString());
				summary.setDob(contact.getDob());
				summary.setEidCheck(Boolean.FALSE);
				ccExchange.addEventServiceLog(setKYCDefaultResponseStatus(message, contactResponses, contact,
						kycContactResponse, summary, ServiceStatus.NOT_PERFORMED));
			}
			kycProviderRequest.setContact(contactRequests);
			kycProviderResponse.setContactResponse(contactResponses);
			ccExchange.setRequest(kycProviderRequest);
			ccExchange.setResponse(kycProviderResponse);
			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception exception) {
			LOGGER.warn(ERROR_MSG, exception);
			throw new ComplianceException(InternalProcessingCode.SERVICE_ERROR,exception);
		}
		return message;
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
			
			if(!isNullOrEmpty(contact.getSsn())) {
				transformSSNInfoNew(identifications, contact);//AT-3661
			}else {
				transformSSNInfo(identifications, contact);
			}

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
			// MedicareReferenceNumber????
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

			// Object of RegistrationServiceRequest is created to fetch request
			// and access to org code, changes done by Vishal J
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			String orgID = StringUtils.leftPadWithZero(3, registrationRequest.getOrgId());

			if (kycProviderResponse == null) {
				List<KYCContactResponse> contactResponseList = new ArrayList<>();

				KYCSummary kycSummary = new KYCSummary();
				kycProviderResponse = new KYCProviderResponse();
				kycProviderResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());

				for (KYCContactRequest contact : kycProviderRequest.getContact()) {
					/*
					 * we are showing reference ID = 'orgID-C-contactID' instead
					 * of 'Not Available' on UI for 'Service Failure' change
					 * done by Vishal J method called to set that ID
					 */
					String providerRef = createReferenceId(orgID, Constants.ENTITY_TYPE_CONTACT, contact.getId() + "");
					KYCContactResponse contactResponse = new KYCContactResponse();
					contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
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
					// end of change for Ref. ID
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(contactResponse));
				}
				kycProviderResponse.setContactResponse(contactResponseList);
				exchange.setResponse(kycProviderResponse);
				return message;
			}

			KYCProviderRequest kycRequest = (KYCProviderRequest) exchange.getRequest();
			List<KYCContactResponse> contactResponseList = kycProviderResponse.getContactResponse();

			if (contactResponseList != null) {
				for (KYCContactResponse kycContactResponse : contactResponseList) {

					setKYCSummaryDetails(exchange, kycRequest, kycContactResponse);
				}
			} else {
				contactResponseList = new ArrayList<>();
				for (KYCContactRequest c : kycRequest.getContact()) {
					/*
					 * change done by Vishal J (if no contacts are present in
					 * response, at that time also we are setting status as
					 * FAIL) to show reference id on UI method called to set
					 * that ID
					 */
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
					// change end
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.KYC_SERVICE,
							EntityEnum.CONTACT.name(), c.getId());
					eventServiceLog
							.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(kycProviderResponse));
					eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
					eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				}
			}
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			for (Contact contact : contacts) {
				if (!contact.isKYCEligible()) {
					/*
					 * change done by Vishal J reference ID is set if response
					 * status is 'NOT REQUIRED' method called to set that ID
					 */
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
					// change end
					kycSummary.setEidCheck(Boolean.FALSE);
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(defaultResponse));
					eventServiceLog.setStatus(ServiceStatus.NOT_REQUIRED.name());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(kycSummary));
					eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				}
			}
		} catch (Exception e) {
			LOGGER.error(Constants.ERROR, e);
			message.getPayload().setFailed(true);
		}
		return message;
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

		/*
		 * If response status is 'SERVICE_FAILURE' or 'NOT_REQUIRED' or
		 * 'NOT_PERFORMED' from service provider then we are showing reference
		 * ID on UI instead of showing it as 'NOT AVAILABLE' 1) Originally it
		 * was checking status as either it is 'Fail' or 'Not performed' but as
		 * per demand we are checking whether status is 'Not Required' 2) If
		 * status is one of the above we are showing that ID instead of 'Not
		 * Available' on UI 3) And if status is PASS or any other than above we
		 * are showing ProviderUniqueRefNo on UI changes done by Vishal J
		 */
		if (ServiceStatus.SERVICE_FAILURE.name().equals(kycContactResponse.getStatus())
				|| ServiceStatus.NOT_PERFORMED.name().equals(kycContactResponse.getStatus())
				|| ServiceStatus.NOT_REQUIRED.name().equals(kycContactResponse.getStatus())) {
			kycSummary.setEidCheck(Boolean.FALSE);
			kycSummary.setVerifiactionResult(Constants.NOT_AVAILABLE);
			// we are showing that ID instead of 'Not Available' on UI
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
	 * Creates EventServiceLog entry for NOT_REQUIRED status, if
	 * performRemainingCheck is false.
	 *
	 * @author abhijeetg
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
	
	/**
	 * AT-3661
	 * 
	 * Transform SSN info new.
	 *
	 * @param identifications the identifications
	 * @param contact the contact
	 */
	private void transformSSNInfoNew(List<Identification> identifications, Contact contact) {
			Identification identification = new Identification();
			identification.setType(Constants.SSN_NUMBER);
			identification.setNumber(contact.getSsn());
			identifications.add(identification);
	}
}
