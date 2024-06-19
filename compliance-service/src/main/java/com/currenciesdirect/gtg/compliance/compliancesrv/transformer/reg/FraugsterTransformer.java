/**
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.DeviceInfo;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterOnUpdateResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterProfileBaseRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterResendRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSignupResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
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
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

/**
 * The Class FraugsterTransformer.
 *
 * @author manish
 */
public class FraugsterTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(FraugsterTransformer.class);

	/** The country cache. */
	private ProvideCacheLoader countryCache = ProvideCacheLoader.getInstance();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformRequest(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) messageExchange.getRequest();
		String jsonCxExchangeRequest;
		try {
			MessageExchange ccExchange;
			if ((OperationEnum.NEW_REGISTRATION)==message.getPayload().getGatewayMessageExchange().getOperation()
					|| (OperationEnum.ADD_CONTACT)==message.getPayload().getGatewayMessageExchange().getOperation()) {

				ccExchange = createSignUpExchange(registrationRequest, token,
						messageExchange.getServiceTypeEnum().name());
			} else {

				ccExchange = createOnUpdateExchange(registrationRequest, token,
						messageExchange.getServiceTypeEnum().name());
			}
			jsonCxExchangeRequest = JsonConverterUtil.convertToJsonWithoutNull(ccExchange.getRequest());
			LOG.debug("::::::::::::::: FrausterTransformer : {}",jsonCxExchangeRequest);

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in Fraugster transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Creates the sign up exchange.
	 *
	 * @param regRequest
	 *            the reg request
	 * @param token
	 *            the token
	 * @param serviceType
	 *            the service type
	 * @return the message exchange
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private MessageExchange createSignUpExchange(RegistrationServiceRequest regRequest, UserProfile token,
			String serviceType) throws ComplianceException {
		Integer eventId = (Integer) regRequest.getAdditionalAttribute("eventId");
		MessageExchange ccExchange = new MessageExchange();
		FraugsterSignupRequest request = new FraugsterSignupRequest();
		FraugsterSignupResponse fSignUpResponse = new FraugsterSignupResponse();
		List<FraugsterSignupContactRequest> fSingUpRequests = new ArrayList<>();
		List<FraugsterSignupContactResponse> fSignUpResponses = new ArrayList<>();

		try {

			/**
			 * performRemainingCheck is added to create default EventServiceLog
			 * entries for NOT_REQUIRED status in , changes done by Abhijit G
			 **/
			/**
			 * Commented below code for AT-995: Allow to perform Fraugster even
			 * blacklist check fails. i.e performRemainingCheck =true;
			 */
			/**
			 * Code commented to remove SONAR critical issue.
			 */
			request.setOrgCode(regRequest.getOrgCode());
			request.setCorrelationID(regRequest.getCorrelationID());
			request.setSourceApplication(regRequest.getSourceApplication());
			request.setRequestType(serviceType);
			fSignUpResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			for (Contact contact : regRequest.getAccount().getContacts()) {
				FraugsterSignupContactRequest fSingupContact;
				fSingupContact = transformSignupRequest(contact, regRequest.getDeviceInfo(), regRequest.getAccount());
				fSingupContact.setAccountContactNum(regRequest.getAccount().getContacts().size());
				fSingUpRequests.add(fSingupContact);

				FraugsterSignupContactResponse response = new FraugsterSignupContactResponse();
				response.setId(contact.getId());
				response.setStatus(ServiceStatus.NOT_PERFORMED.name());
				fSignUpResponses.add(response);

				FraugsterSummary summary = new FraugsterSummary();
				summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
				// use this to update the FraugsterSchedularData table.
				summary.setCdTrasId(fSingupContact.getTransactionID());
				ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.FRAUGSTER_SERVICE,
						ServiceProviderEnum.FRAUGSTER_SIGNUP_SERVICE, contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), response, summary));
			}
			request.setContactRequests(fSingUpRequests);
			fSignUpResponse.setContactResponses(fSignUpResponses);
			ccExchange.setRequest(request);
			ccExchange.setResponse(fSignUpResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);

		} catch (Exception e) {
			LOG.error("{1}",e);
		}
		return ccExchange;
	}

	/**
	 * Creates the on update exchange.
	 *
	 * @param regRequest
	 *            the reg request
	 * @param token
	 *            the token
	 * @param serviceType
	 *            the service type
	 * @return the message exchange
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	@SuppressWarnings("unchecked")
	private MessageExchange createOnUpdateExchange(RegistrationServiceRequest regRequest, UserProfile token,
			String serviceType) throws ComplianceException {
		Integer eventId = (Integer) regRequest.getAdditionalAttribute("eventId");
		MessageExchange ccExchange = new MessageExchange();
		FraugsterOnUpdateRequest fOnUpdateRequest = new FraugsterOnUpdateRequest();
		FraugsterOnUpdateResponse fResponse = new FraugsterOnUpdateResponse();
		List<FraugsterOnUpdateContactRequest> fOnUpdateRequests = new ArrayList<>();
		List<FraugsterOnUpdateContactResponse> fOnUpdateResponses = new ArrayList<>();
		List<Contact> contacts;
		if(!regRequest.getAccount().getContacts().isEmpty()) {
			contacts = regRequest.getAccount().getContacts();
		} else {
			contacts = (List<Contact>) regRequest.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);
		}
		try {
			/**
			 * performRemainingCheck is added to create default EventServiceLog
			 * entries for NOT_REQUIRED status in , changes done by Abhijit G
			 **/
			/**
			 * Commented below code for AT-995: Allow to perform Fraugster even
			 * blacklist check fails. i.e performRemainingCheck =true;
			 */

			fOnUpdateRequest.setOrgCode(regRequest.getOrgCode());
			fOnUpdateRequest.setCorrelationID(regRequest.getCorrelationID());
			fOnUpdateRequest.setSourceApplication(regRequest.getSourceApplication());
			fOnUpdateRequest.setRequestType(serviceType);

			fResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());
			for (Contact contact : contacts) {
				FraugsterOnUpdateContactRequest fOnUpdateContact = null;
				if (contact.isFraugsterEligible()) {

					fOnUpdateContact = transformOnUpdateRequest(contact, regRequest.getAccount(),
							regRequest.getDeviceInfo());
					fOnUpdateContact.setAccountContactNum(regRequest.getAccount().getContacts().size());
					fOnUpdateRequests.add(fOnUpdateContact);

					FraugsterOnUpdateContactResponse response = new FraugsterOnUpdateContactResponse();
					response.setId(contact.getId());
					response.setStatus(ServiceStatus.NOT_PERFORMED.name());
					fOnUpdateResponses.add(response);
				}

				FraugsterOnUpdateContactResponse defaultResponse = new FraugsterOnUpdateContactResponse();
				defaultResponse.setStatus(ServiceStatus.NOT_PERFORMED.name());

				FraugsterSummary summary = new FraugsterSummary();
				summary.setStatus(ServiceStatus.NOT_PERFORMED.name());
				// use this to update the FraugsterSchedularData table.
				if (null != fOnUpdateContact)
					summary.setCdTrasId(fOnUpdateContact.getTransactionID());
				ccExchange.addEventServiceLog(createEventServiceLogEntry(eventId, ServiceTypeEnum.FRAUGSTER_SERVICE,
						ServiceProviderEnum.FRAUGSTER_ONUPDATE_SERVICE, contact.getId(), contact.getVersion(),
						EntityEnum.CONTACT.name(), token.getUserID(), defaultResponse, summary));
			}

			fOnUpdateRequest.setContactRequests(fOnUpdateRequests);
			fResponse.setContactResponses(fOnUpdateResponses);
			ccExchange.setRequest(fOnUpdateRequest);
			ccExchange.setResponse(fResponse);
			ccExchange.setServiceTypeEnum(ServiceTypeEnum.FRAUGSTER_SERVICE);
		} catch (Exception e) {
			LOG.error("{1}",e);
		}
		return ccExchange;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.core.ITransfomer#
	 * transformResponse(org.springframework.messaging.Message)
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		EventServiceLog eventServiceLog;

		try {
			if ((OperationEnum.NEW_REGISTRATION)==message.getPayload().getGatewayMessageExchange().getOperation()
					|| (OperationEnum.ADD_CONTACT)==message.getPayload().getGatewayMessageExchange().getOperation()) {
				transformSignUpResponse(exchange);
			} else if ((OperationEnum.UPDATE_ACCOUNT)==message.getPayload().getGatewayMessageExchange().getOperation()) {
				transformOnUpdateResponse(exchange);
			}
			RegistrationServiceRequest registrationRequest = (RegistrationServiceRequest) message.getPayload()
					.getGatewayMessageExchange().getRequest();
			List<Contact> contacts = registrationRequest.getAccount().getContacts();
			for (Contact contact : contacts) {
				if (!contact.isFraugsterEligible()) {
					eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					eventServiceLog.setProviderResponse("");
					eventServiceLog.setStatus(ServiceStatus.NOT_PERFORMED.name());
					eventServiceLog.setSummary("");
					eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				}
			}
		} catch (Exception e) {
			LOG.error("Error", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Left pad with zero.
	 *
	 * @param id
	 *            the id
	 * @return the string
	 */
	private String leftPadWithZero(Integer id) {
		String pad = "0000000000";
		return (pad + id).substring((pad + id).length() - 10);
	}

	/**
	 * Checks if is integer.
	 *
	 * @param s
	 *            the s
	 * @return true, if is integer
	 */
	private static boolean isInteger(String s) {
		Integer val = null;
		try {
			if (s != null && !"".equals(s.trim())) {
				val = Integer.valueOf(s);
			}
		} catch (NumberFormatException | NullPointerException e) {
			LOG.debug("Invalid Integer value", e);
		}
		// only got here if we didn't return false
		return null == val;
	}

	/**
	 * Transform device info and provider base request.
	 *
	 * @param request
	 *            the request
	 * @param contact
	 *            the contact
	 * @param deviceInfo
	 *            the device info
	 * @param account
	 *            the account
	 * @param eventType
	 *            the event type
	 */
	private void transformDeviceInfoAndProviderBaseRequest(FraugsterBaseRequest request, Contact contact,
			DeviceInfo deviceInfo,String eventType) {

		request.setEventType(eventType);		
		request.setCustID(contact.getContactSFID());
		// Append version to the transaction id
		request.setTransactionID(leftPadWithZero(contact.getId()) + contact.getVersion());
		if (deviceInfo != null) {
			request.setUserAgent(deviceInfo.getUserAgent());
			// Added ScreenResolution instead of BrowserScreenResolution and
			// DeviceResolution
			request.setBrowserScreenResolution(deviceInfo.getScreenResolution());
			request.setBrowserName(deviceInfo.getBrowserName());
			request.setBrowserMajorVersion(deviceInfo.getBrowserMajorVersion());
			request.setDeviceType(deviceInfo.getDeviceType());
			request.setDeviceName(deviceInfo.getDeviceName());
			request.setDeviceVersion(deviceInfo.getDeviceVersion());
			request.setDeviceID(deviceInfo.getDeviceId());
			request.setDeviceManufacturer(deviceInfo.getDeviceManufacturer());
			request.setOsName(deviceInfo.getOsName());
			request.setBrowserLanguage(deviceInfo.getBrowserLanguage());
			request.setBrowserOnline(deviceInfo.getBrowserOnline());
			request.setOsDateAndTime(deviceInfo.getOsDateAndTime());
		}
	}

	/**
	 * Transform signup request.
	 *
	 * @param contact
	 *            the contact
	 * @param deviceInfo
	 *            the device info
	 * @param account
	 *            the account
	 * @return the fraugster signup contact request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FraugsterSignupContactRequest transformSignupRequest(Contact contact, DeviceInfo deviceInfo,
			Account account) throws ComplianceException {
		FraugsterSignupContactRequest requestData = new FraugsterSignupContactRequest();

		try {
			transformDeviceInfoAndProviderBaseRequest(requestData, contact, deviceInfo, Constants.SIGN_UP);
			transformProfileBaseRequest(requestData, contact, account);
			requestData.setId(contact.getId());
			if (null != contact.getPostCodeLongitude()) {
				requestData.setPostCodeLongitude(contact.getPostCodeLongitude());
			}
			if (null != contact.getPostCodeLatitude()) {
				requestData.setPostCodeLatitude(contact.getPostCodeLatitude());
			}
			requestData.setRegistrationDateTime(account.getRegistrationDateTime());
			requestData.setSecondEmail(contact.getSecondEmail());

		} catch (Exception e) {
			LOG.error("{1}",e);
		}
		return requestData;
	}

	/**
	 * Transform profile base request.
	 *
	 * @param requestData
	 *            the request data
	 * @param contact
	 *            the contact
	 * @param account
	 *            the account
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void transformProfileBaseRequest(FraugsterProfileBaseRequest requestData, Contact contact, Account account)
			throws ComplianceException {

		requestData.setTitle(contact.getTitle());
		requestData.setPreferredName(contact.getPreferredName());
		requestData.setFirstName(contact.getFirstName());
		requestData.setLastName(contact.getLastName());
		requestData.setDob(contact.getDob());
		setAddressFields(requestData, contact);
		setPhoneDetails(requestData, contact);
		requestData.setNationality(contact.getNationality());
		requestData.setOccupation(contact.getOccupation());
		setPassportDetails(requestData, contact);
		requestData.setdLCardNumber(contact.getDlCardNumber());
		setDlDetails(requestData, contact);
		requestData.setMedicareCardNumber(contact.getMedicareCardNumber());
		requestData.setMedicareReferenceNumber(contact.getMedicareReferenceNumber());
		requestData.setMunicipalityOfBirth(contact.getCity()); // ?????
		requestData.setCustomerType(account.getCustType());
		requestData.setChannel(account.getChannel());
		requestData.setiPAddress(contact.getIpAddress());
		if (null != contact.getIpAddressLatitude())
			requestData.setiPLatitude(Float.parseFloat(contact.getIpAddressLatitude()));
		if (null != contact.getIpAddressLongitude())
			requestData.setiPLongitude(Float.parseFloat(contact.getIpAddressLongitude()));
		requestData.setCountriesOfOperation(account.getCountriesOfOperation());
		requestData.setSourceOfFund(account.getSourceOfFund());
		requestData.setSubSource(account.getSubSource());
		requestData.setReferral(account.getReferral());
		requestData.setReferralText(account.getReferralText());
		requestData.setKeywords(account.getKeywords());
		requestData.setSearchEngine(account.getSearchEngine());
		requestData.setWebsite(account.getWebsite());
		requestData.setMiddleName(contact.getMiddleName());
		requestData.setNationalId(contact.getNationalIdNumber());
		requestData.setCountryOfBirth(contact.getCountryOfBirth());
		requestData.setAza(contact.getAza());
		requestData.setAdCampaign(account.getAdCampaign());
		requestData.setResidentialStatus(contact.getResidentialStatus());
		requestData.setAffiliateName(account.getAffiliateName());
		requestData.setRegMode(account.getRegistrationMode());
		requestData.setBranch(account.getBranch());
		requestData.setTxnValue(account.getMaxTransactionAmount());
		requestData.setTxnValueRange(account.getValueOfTransaction());		
		requestData.setAddressType(contact.getAddressType());
		requestData.setTurnover(account.getTurnover());
		requestData.setRegionSuburb(contact.getRegion());
		requestData.setSource(account.getSource());
		
		if(null!=account.getCompany() && null!=account.getCompany().getBillingAddress()) {
			requestData.setBillingAddress(account.getCompany().getBillingAddress());
		}
		
		if(Constants.PFX.equals(account.getCustType())) {
			requestData.setOnQueue(contact.isOnQueue());
		} else {
			requestData.setOnQueue(account.isOnQueue());
		}
		requestData.setEidStatus("1");
		requestData.setSanctionStatus("1");
		requestData.setJobTitle(contact.getJobTitle());
	}

	/**
	 * Sets the dl details.
	 *
	 * @param requestData
	 *            the request data
	 * @param contact
	 *            the contact
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void setDlDetails(FraugsterProfileBaseRequest requestData, Contact contact) throws ComplianceException {
		if (null != contact.getDlCountryCode()) {
			requestData.setDlCountryCode(null != countryCache.getCountryFullName(contact.getDlCountryCode())
					? countryCache.getCountryFullName(contact.getDlCountryCode()) : "");
		}
		requestData.setDlExpiryDate(contact.getDlExpiryDate());
		requestData.setDlLicenseNumber(contact.getDlLicenseNumber());
		requestData.setDlStateCode(contact.getDlStateCode());
	}

	/**
	 * Sets the passport details.
	 *
	 * @param requestData
	 *            the request data
	 * @param contact
	 *            the contact
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void setPassportDetails(FraugsterProfileBaseRequest requestData, Contact contact)
			throws ComplianceException {
		if (null != contact.getPassportCountryCode()) {
			requestData.setPassportCountryCode(null != countryCache.getCountryFullName(contact.getPassportCountryCode())
					? countryCache.getCountryFullName(contact.getPassportCountryCode()) : "");
		}
		requestData.setPassportFamilyNameAtBirth(contact.getPassportFamilyNameAtBirth());
		requestData.setPassportNameAtCitizenship(contact.getPassportNameAtCitizenship());
		requestData.setPassportNumber(contact.getPassportNumber());
		requestData.setPassportPlaceOfBirth(contact.getPassportPlaceOfBirth());
	}

	/**
	 * Sets the phone details.
	 *
	 * @param requestData
	 *            the request data
	 * @param contact
	 *            the contact
	 */
	private void setPhoneDetails(FraugsterProfileBaseRequest requestData, Contact contact) {
		requestData.setPhoneWork(contact.getPhoneWork());
		requestData.setPhoneHome(contact.getPhoneHome());
		requestData.setPhoneMobile(contact.getPhoneMobile());
		requestData.setEmail(contact.getEmail());
		requestData.setPrimaryContact(contact.getPrimaryContact());
		requestData.setPrimaryPhone(contact.getPrimaryPhone());
	}

	/**
	 * Sets the address fields.
	 *
	 * @param requestData
	 *            the request data
	 * @param contact
	 *            the contact
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private void setAddressFields(FraugsterProfileBaseRequest requestData, Contact contact) throws ComplianceException {
		requestData.setAddressType(contact.getAddressType());
		requestData.setStreet(contact.getStreet());
		requestData.setCity(contact.getCity());
		if (null != contact.getCountry()) {
			requestData.setCountry(null != countryCache.getCountryFullName(contact.getCountry())
					? countryCache.getCountryFullName(contact.getCountry()) : "");
			requestData.setCountryOfResidenceCode(contact.getCountry());
		}
		requestData.setPostCode(contact.getPostCode());
		requestData.setSubBuildingorFlat(contact.getSubBuildingorFlat());
		if (!isInteger(contact.getBuildingNumber()))
			requestData.setBuildingNumber(Integer.valueOf(contact.getBuildingNumber()));
		if (!isInteger(contact.getUnitNumber()))
			requestData.setUnitNumber(Integer.valueOf(contact.getUnitNumber()));
		requestData.setSubCity(contact.getSubCity());
		requestData.setRegion(contact.getRegion());
		requestData.setState(contact.getState());
	}

	/**
	 * Transform on update request.
	 *
	 * @param contact
	 *            the contact
	 * @param account
	 *            the account
	 * @param deviceInfo
	 *            the device info
	 * @return the fraugster on update contact request
	 * @throws ComplianceException
	 *             the compliance exception
	 */
	private FraugsterOnUpdateContactRequest transformOnUpdateRequest(Contact contact, Account account,
			DeviceInfo deviceInfo) throws ComplianceException {
		FraugsterOnUpdateContactRequest requestData = new FraugsterOnUpdateContactRequest();
		try {
			requestData.setRegistrationDateTime(account.getRegistrationDateTime());

			transformDeviceInfoAndProviderBaseRequest(requestData, contact, deviceInfo,Constants.UPDATE);
			transformProfileBaseRequest(requestData, contact, account);
			requestData.setId(contact.getId());
			if (null != contact.getPostCodeLatitude()) {
				requestData.setPostCodeLatitude(contact.getPostCodeLatitude());
			}
			if (null != contact.getPostCodeLongitude()) {
				requestData.setPostCodeLongitude(contact.getPostCodeLongitude());
			}
			requestData.setUpdateMethod(contact.getUpdateMethod());
			requestData.setRecordUpdatedOn(contact.getRecordUpdatedOn());
			requestData.setAccountStatus(account.getAccountStatus());
			requestData.setContactStatus(contact.getContactStatus());
			requestData.setOnlineLoginStatus(contact.getOnlineLoginStatus());
			requestData.setSessionID(contact.getSessionID());
			/** input parameter start **/
			requestData.setLastPINIssuedDateTime(contact.getLastPINIssuedDateTime());
			requestData.setLastLoginFailDateTime(contact.getLastLoginFailDateTime());
			requestData.setLastPasswordResetOn(contact.getLastPasswordResetOn());
			requestData.setListOfDevicesUsedByCustomer(contact.getListofDevicesUsedByCustomer());
			requestData.setSecondContactAddedLaterOnline(contact.getSecondContactAddedLaterOnline());
			requestData.setLastIPAddresses(contact.getLastIPAddresses());
			requestData.setLast5LoginDateTime(contact.getLast5LoginDateTime());
			/** input parameter end **/
		} catch (Exception e) {
			LOG.error("{1}",e);
		}
		return requestData;
	}

	/**
	 * Transform sign up response.
	 *
	 * @param exchange
	 *            the exchange
	 */
	private void transformSignUpResponse(MessageExchange exchange) {
		FraugsterSignupResponse fResponse = (FraugsterSignupResponse) exchange.getResponse();
		FraugsterSignupRequest fraugsterProviderRequest = exchange.getRequest(FraugsterSignupRequest.class);
		try {
			if (fResponse == null  || fResponse.getContactResponses().get(0).getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())
					||fResponse.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
				
				fResponse = new FraugsterSignupResponse();
				List<FraugsterSignupContactResponse> conactResponseList = new ArrayList<>();

				for (FraugsterSignupContactRequest contact : fraugsterProviderRequest.getContactRequests()) {
					FraugsterSignupContactResponse conactResponse = new FraugsterSignupContactResponse();
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
							eventServiceLog.getSummary());
					conactResponse.setId(contact.getId());
					conactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					conactResponseList.add(conactResponse);
					fResponse.setContactResponses(null);
					fraugsterSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fraugsterSummary.setFrgTransId(Constants.NOT_AVAILABLE);
					fraugsterSummary.setScore(Constants.NOT_AVAILABLE);
					eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(conactResponse));
				}
				fResponse.setContactResponses(conactResponseList);
				exchange.setResponse(fResponse);
		  }

	else {
			List<FraugsterSignupContactResponse> identityResponse = fResponse.getContactResponses();	
			if (identityResponse != null && !fResponse.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name()) ) {
				for (FraugsterSignupContactResponse response : identityResponse) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), response.getId());
					readResponse(eventServiceLog, response);
				}
			}
		}
			
	}catch (Exception e) {
		LOG.error("{1}",e);
	}
	}

	/**
	 * Transform on update response.
	 *
	 * @param exchange
	 *            the exchange
	 */
	private void transformOnUpdateResponse(MessageExchange exchange) {
		FraugsterOnUpdateResponse fResponse = (FraugsterOnUpdateResponse) exchange.getResponse();
		FraugsterOnUpdateRequest fraugsterProviderRequest = exchange.getRequest(FraugsterOnUpdateRequest.class);
		try {

			if (null==fResponse || !fResponse.getContactResponses().isEmpty() && fResponse.getContactResponses().get(0).getStatus()
					.equals(ServiceStatus.SERVICE_FAILURE.name())) {

				fResponse = new FraugsterOnUpdateResponse();

				List<FraugsterOnUpdateContactResponse> conactResponseList = new ArrayList<>();

				for (FraugsterOnUpdateContactRequest contact : fraugsterProviderRequest.getContactRequests()) {
					FraugsterOnUpdateContactResponse contactResponse = new FraugsterOnUpdateContactResponse();
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
							eventServiceLog.getSummary());
					fResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fResponse.setContactResponses(null);
					contactResponse.setId(contact.getId());
					conactResponseList.add(contactResponse);
					contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fraugsterSummary.setFrgTransId(Constants.NOT_AVAILABLE);
					fraugsterSummary.setScore(Constants.NOT_AVAILABLE);
					fraugsterSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(contactResponse));
				}
				fResponse.setContactResponses(conactResponseList);
				exchange.setResponse(fResponse);

			}
			List<FraugsterOnUpdateContactResponse> identityResponse = fResponse.getContactResponses();

			if (identityResponse != null && !fResponse.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
				for (FraugsterOnUpdateContactResponse response : identityResponse) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), response.getId());
					readResponse(eventServiceLog, response);
				}
			}

		} catch (Exception e) {
			LOG.error("{1}",e);
		}
	}

	/**
	 * Transform request for fraugster repeat check.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> transformRequestForFraugsterRepeatCheck(Message<MessageContext> message) {
		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		FraugsterResendRequest resendRequest = messageExchange.getRequest(FraugsterResendRequest.class);
		RegistrationServiceRequest oldRequest = resendRequest.getAdditionalAttribute(Constants.OLD_REQUEST,
				RegistrationServiceRequest.class);
		oldRequest.addAttribute(Constants.FIELD_EVENTID, resendRequest.getAdditionalAttribute(Constants.FIELD_EVENTID));
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		MessageExchange ccExchange = null;
		try {
			ccExchange = createOnUpdateExchange(oldRequest, token, messageExchange.getServiceTypeEnum().name());
		} catch (Exception e) {
			LOG.error("Error in Fraugster transformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}
		message.getPayload().appendMessageExchange(ccExchange);
		return message;
	}

	/**
	 * Transform response for fraugster repeat check.
	 *
	 * @param message
	 *            the message
	 * @return the message
	 */
	public Message<MessageContext> transformResponseForFraugsterRepeatCheck(Message<MessageContext> message) {
		MessageExchange exchange = message.getPayload().getMessageExchange(ServiceTypeEnum.FRAUGSTER_SERVICE);
		FraugsterResendRequest request = message.getPayload().getGatewayMessageExchange()
				.getRequest(FraugsterResendRequest.class);
		RegistrationServiceRequest oldRequest = request.getAdditionalAttribute(Constants.OLD_REQUEST,
				RegistrationServiceRequest.class);
		EventServiceLog eventServiceLog;
		try {
			transformOnUpdateResponseForFraugsterRepeatCheck(exchange);
			List<Contact> contacts = oldRequest.getAccount().getContacts();
			for (Contact contact : contacts) {
				if (!contact.isFraugsterEligible()) {
					eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					eventServiceLog.setProviderResponse("");
					eventServiceLog.setStatus(ServiceStatus.NOT_PERFORMED.name());
					eventServiceLog.setSummary("");
					eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
				}
			}
		} catch (Exception e) {
			LOG.error("Error", e);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	private void transformOnUpdateResponseForFraugsterRepeatCheck(MessageExchange exchange) {
		FraugsterOnUpdateResponse fResponse = (FraugsterOnUpdateResponse) exchange.getResponse();
		FraugsterOnUpdateRequest fraugsterProviderRequest = exchange.getRequest(FraugsterOnUpdateRequest.class);
		try {
			if (fResponse == null  || fResponse.getContactResponses().get(0).getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
				fResponse = new FraugsterOnUpdateResponse();

				List<FraugsterOnUpdateContactResponse> conactResponseList = new ArrayList<>();

				for (FraugsterOnUpdateContactRequest contact : fraugsterProviderRequest.getContactRequests()) {
					FraugsterOnUpdateContactResponse contactResponse = new FraugsterOnUpdateContactResponse();
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), contact.getId());
					FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
							eventServiceLog.getSummary());
					fResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fResponse.setContactResponses(null);
					contactResponse.setId(contact.getId());
					conactResponseList.add(contactResponse);
					contactResponse.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fraugsterSummary.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					fraugsterSummary.setFrgTransId(Constants.NOT_AVAILABLE);
					fraugsterSummary.setScore(Constants.NOT_AVAILABLE);
					eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
					eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(fraugsterSummary));
					eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(contactResponse));
				}
				fResponse.setContactResponses(conactResponseList);
				exchange.setResponse(fResponse);
			}
			List<FraugsterOnUpdateContactResponse> identityResponse = fResponse.getContactResponses();

			if (identityResponse != null && !fResponse.getStatus().equals(ServiceStatus.SERVICE_FAILURE.name())) {
				for (FraugsterOnUpdateContactResponse response : identityResponse) {
					EventServiceLog eventServiceLog = exchange.getEventServiceLog(ServiceTypeEnum.FRAUGSTER_SERVICE,
							EntityEnum.CONTACT.name(), response.getId());
					readResponse(eventServiceLog, response);
				}
			}
		} catch (Exception e) {
			LOG.error("{1}",e);
		}
	}

}
