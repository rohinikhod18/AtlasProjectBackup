package com.currenciesdirect.gtg.compliance.compliancesrv.transformer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.utils.FindbugsSuppressWarnings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayrefContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.ContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceRequestData;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.InternalServiceResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.IpSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.EntityEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

/**
 * The Class AbstractInternalRulesTransformer.
 */
public abstract class AbstractInternalRulesTransformer extends AbstractTransformer{
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractInternalRulesTransformer.class);
	
	/**
	 * Creates the contact request.
	 *
	 * @param sourceApplication the source application
	 * @param contact the contact
	 * @param countryFullName the country full name
	 * @return the internal service request data
	 */
	protected InternalServiceRequestData createContactRequest(String sourceApplication, Contact contact,
			String countryFullName, String orgLegalEntity) {
		InternalServiceRequestData data = new InternalServiceRequestData();
		data.setId(contact.getId());
		data.setCountry(countryFullName);
		data.setOrgLegalEntity(orgLegalEntity);
		data.setEmail(contact.getEmail());
		data.setIpAddress(contact.getIpAddress());
		data.setIsWildCardSearch(Boolean.FALSE);
		data.setName(contact.getFullName());
		data.addPhoneNo(contact.getPhoneMobile());
		data.addPhoneNo(contact.getPhoneHome());
		data.addPhoneNo(contact.getPhoneWork());
		data.setPostCode(contact.getPostCode());
		data.setSourceApplication(sourceApplication);
		data.setState(contact.getState());
		data.setEntityType("CONTACT");
		return data;
	}
	
	/**
	 * Creates the company CFX request.
	 *
	 * @param account the account
	 * @param sourceApplication the source application
	 * @return the internal service request data
	 */
	protected InternalServiceRequestData createCompanyCFXRequest(Account account,String sourceApplication){
		InternalServiceRequestData data = new InternalServiceRequestData();
		data.setId(account.getId());
		data.setCompanyName(account.getAccountName());
		data.setWebSite(account.getWebsite());
		data.setIsWildCardSearch(Boolean.FALSE);
		data.setSourceApplication(sourceApplication);
		data.setEntityType(EntityEnum.ACCOUNT.toString());
		return data;
	}
	
	/**
	 * Creates the default response.
	 *
	 * @param contacts the contacts
	 * @return the internal service response
	 */
	protected InternalServiceResponse createDefaultResponse(List<Contact> contacts){
		InternalServiceResponse response = new InternalServiceResponse();
		BlacklistContactResponse blacklistContactResponse = new BlacklistContactResponse();
		List<ContactResponse> responseList = new ArrayList<>();
		for(Contact contact: contacts){
			ContactResponse contactResponse =  new ContactResponse();
			contactResponse.setId(contact.getId());
			contactResponse.setContactStatus(ServiceStatus.NOT_PERFORMED.name());
			contactResponse.setBlacklist(blacklistContactResponse);
			responseList.add(contactResponse);
		}
		response.setContacts(responseList);
		return response;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer#createEventServiceLogEntry(java.lang.Integer, com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum, com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceProviderEnum, java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.Object, java.lang.Object)
	 */
	@Override
	@FindbugsSuppressWarnings("squid:S00107")
	protected EventServiceLog createEventServiceLogEntry(Integer eventId, ServiceTypeEnum servceType,
			ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, String entityType, Integer user,
			Object providerResponse, Object summary) {

		return createEventServiceLogEntryWithStatus(eventId, servceType, providerEnum, entityID, entityVersion,
				entityType, user, providerResponse, summary, ServiceStatus.NOT_PERFORMED);
	}
	
	
	/**
	 * Creates the event service log entry with status.
	 *
	 * @param eventId the event id
	 * @param eventServiceLogs the event service logs
	 * @param servceType the servce type
	 * @param providerEnum the provider enum
	 * @param entityID the entity ID
	 * @param entityVersion the entity version
	 * @param entityType the entity type
	 * @param user the user
	 * @param providerResponse the provider response
	 * @param summary the summary
	 * @param serviceStatus the service status
	 */
	@FindbugsSuppressWarnings("squid:S00107")
	protected void createEventServiceLogEntryWithStatus(Integer eventId, Map<String, EventServiceLog> eventServiceLogs, //NOSONAR 
			ServiceTypeEnum servceType, ServiceProviderEnum providerEnum, Integer entityID, Integer entityVersion, 
			String entityType, Integer user, Object providerResponse,Object summary, ServiceStatus serviceStatus){
		
		EventServiceLog eventServiceLog = new EventServiceLog();
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		eventServiceLog.setServiceName(servceType.getShortName());
		eventServiceLog.setServiceProviderName(providerEnum.getProvidername());
		eventServiceLog.setEntityId(entityID);
		eventServiceLog.setEventId(eventId);
		eventServiceLog.setEntityVersion(entityVersion); 
		eventServiceLog.setEntityType(entityType);
		eventServiceLog.setStatus(serviceStatus.name());
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(providerResponse));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(summary));
		eventServiceLog.setCratedBy(user);
		eventServiceLog.setUpdatedBy(user);
		eventServiceLog.setCreatedOn(timestamp);
		eventServiceLog.setUpdatedOn(timestamp);
		eventServiceLogs.put(servceType.getShortName()+entityType+entityID, eventServiceLog);
	}
	
      
	
	/**
	 * Creates the blacklist summary.
	 *
	 * @param contactResponse the contact response
	 * @return the blacklist summary
	 */
	protected BlacklistSummary createBlacklistSummary(ContactResponse contactResponse) {
		BlacklistSummary blacklistSummary = new BlacklistSummary();
		BlacklistContactResponse blacklistContactResponse = contactResponse.getBlacklist();
		List<String> matchedPhoneList=new ArrayList<>();
		
		if (blacklistContactResponse != null) {
			blacklistSummary.setStatus(blacklistContactResponse.getStatus());
		}
		if (blacklistContactResponse == null || blacklistContactResponse.getData() == null) {
			return blacklistSummary;
		}
		List<BlacklistSTPData> dataList = blacklistContactResponse.getData();
		for (BlacklistSTPData blacklistSTPData : dataList) {
			setBlacklistSummaryFromBlacklistData(blacklistSTPData, blacklistSummary,matchedPhoneList);
		}
		
		if(blacklistSummary.getStatus().equalsIgnoreCase(Constants.FAIL) &&  !matchedPhoneList.isEmpty()){
			blacklistSummary.setPhoneMatchedData(getBlacklistPhoneListInString(matchedPhoneList));	
		}
		
		/**
		 * After setting values into blacklist summary if some values are null then we are setting them to 
		 * 'NOT_REQUIRED'
		 * */
		setNotRequiredForEmptyFields(blacklistSummary);
		/**Change end*/
		return blacklistSummary;

	}
	
	/**
	 * Creates the blacklist pay ref summary.
	 *
	 * @param contactResponse the contact response
	 * @return the blacklist pay ref summary
	 */
	//Add for AT-3649
	protected BlacklistPayRefSummary createBlacklistPayRefSummary(ContactResponse contactResponse) {
		BlacklistPayRefSummary blacklistPayRefSummary = new BlacklistPayRefSummary();
		BlacklistPayrefContactResponse blacklistPayrefContactResponse = contactResponse.getBlacklistPayref();
		
		blacklistPayRefSummary.setStatus(blacklistPayrefContactResponse.getStatus());
		blacklistPayRefSummary.setPaymentReference(blacklistPayrefContactResponse.getPaymentReference());
		blacklistPayRefSummary.setRequestId(blacklistPayrefContactResponse.getRequestId());
		blacklistPayRefSummary.setSanctionText(blacklistPayrefContactResponse.getSanctionText());
		blacklistPayRefSummary.setTokenSetRatio(blacklistPayrefContactResponse.getTokenSetRatio());
		
		return blacklistPayRefSummary;

	}

	/**
	 * Sets the not required for empty fields.
	 *
	 * @param blacklistSummary the new not required for empty fields
	 */
	private void setNotRequiredForEmptyFields(BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrEmpty(blacklistSummary.getIp())) {
			blacklistSummary.setIp(ServiceStatus.NOT_REQUIRED.name());
		}
		if (StringUtils.isNullOrEmpty(blacklistSummary.getEmail())) {
			blacklistSummary.setEmail(ServiceStatus.NOT_REQUIRED.name());
		}
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getAccountNumber())) {
			blacklistSummary.setAccountNumber(ServiceStatus.NOT_REQUIRED.name());
		}
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getName())) {
			blacklistSummary.setName(ServiceStatus.NOT_REQUIRED.name());
		}
		
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getBankName())) {
			blacklistSummary.setBankName(ServiceStatus.NOT_REQUIRED.name());
		}
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getWebSite())) {
			blacklistSummary.setWebSite(ServiceStatus.NOT_REQUIRED.name());
		}
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getDomain())) {
			blacklistSummary.setDomain(ServiceStatus.NOT_REQUIRED.name());
		}
	}
	

	/**
	 * Sets the blacklist summary from blacklist data.
	 *
	 * @param blacklistSTPData the blacklist STP data
	 * @param blacklistSummary the blacklist summary
	 * @param matchedPhoneList the matched phone list
	 */
	private void setBlacklistSummaryFromBlacklistData(BlacklistSTPData blacklistSTPData,  
			BlacklistSummary blacklistSummary, List<String> matchedPhoneList) {

		setBlacklistSummaryFromBlacklistNameData(blacklistSTPData,blacklistSummary);
		switch (blacklistSTPData.getRequestType()) {
			case Constants.IPADDRESS:
				setIPAddress(blacklistSTPData, blacklistSummary);
				break;
	
			case Constants.EMAIL:
				setEmail(blacklistSTPData, blacklistSummary);
				break;
	
			case Constants.PHONE_NUMBER:
				setPhNumber(blacklistSTPData, blacklistSummary);
				addBlacklistMatchedDataIntoList(blacklistSTPData, matchedPhoneList);
				break;
	
			case Constants.ACC_NUMBER:
				setAccNumber(blacklistSTPData, blacklistSummary);
				break;
	
			case Constants.DOMAIN:
				setWebsite(blacklistSTPData, blacklistSummary);
				setDomain(blacklistSTPData, blacklistSummary);
				break;
	
			default:
				break;
		}

	}
	
	private void setBlacklistSummaryFromBlacklistNameData(BlacklistSTPData blacklistSTPData,  
			BlacklistSummary blacklistSummary) {

		switch (blacklistSTPData.getRequestType()) {
			case Constants.CONTACT_NAME:
			case Constants.CCNAME:
			case Constants.COMPANY_NAME:
				setName(blacklistSTPData, blacklistSummary);
				break;
	
			case Constants.BANK_NAME:
				setBankName(blacklistSTPData, blacklistSummary);
				break;
	
			default:
				break;
		}

	}
	

	private void setIPAddress(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrEmpty(blacklistSummary.getIp())) {
			blacklistSummary.setIp(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setIpMatchedData(blacklistSTPData.getMatchedData());			}
	}

	private void setEmail(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrEmpty(blacklistSummary.getEmail())) {
			blacklistSummary.setEmail(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setEmailMatchedData(blacklistSTPData.getMatchedData());	}
	}

	private void setPhNumber(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (blacklistSummary.getPhone()  == null || !blacklistSummary.getPhone()) {
			blacklistSummaryPhone(blacklistSTPData, blacklistSummary);					}
	}

	private void setAccNumber(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getAccountNumber())) {
			blacklistSummary.setAccountNumber(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setAccNumberMatchedData(blacklistSTPData.getMatchedData());			}
	}

	private void setName(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getName())) {
			blacklistSummary.setName(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setNameMatchedData(blacklistSTPData.getMatchedData());			}
	}
	
	private void setBankName(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getBankName())) {
			blacklistSummary.setBankName(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setBankNameMatchedData(blacklistSTPData.getMatchedData());			}
	}

	/**
	 * Blacklist summary phone.
	 *
	 * @param blacklistSTPData the blacklist STP data
	 * @param blacklistSummary the blacklist summary
	 */
	private void blacklistSummaryPhone(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		blacklistSummary.setPhone(blacklistSTPData.getFound());
		blacklistSummary.setPhoneMatchedData(blacklistSTPData.getMatchedData());
	}

	/**
	 * Adds the blacklist matched data into list.
	 *
	 * @param blacklistSTPData the blacklist STP data
	 * @param matchedPhoneList the matched phone list
	 */
	private void addBlacklistMatchedDataIntoList(BlacklistSTPData blacklistSTPData, List<String> matchedPhoneList) {
		if(Boolean.TRUE.equals(blacklistSTPData.getFound()) && null != blacklistSTPData.getMatchedData() 
				&& Boolean.FALSE.equals(blacklistSTPData.getMatchedData().isEmpty())){
			matchedPhoneList.add(blacklistSTPData.getMatchedData());
		}
	}
	
	/**
	 * Sets the website.
	 *
	 * @param blacklistSTPData the blacklist STP data
	 * @param blacklistSummary the blacklist summary
	 */
	//original method
	private void setWebsite(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getWebSite())) {
			blacklistSummary.setWebSite(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setWebsiteMatchedData(blacklistSTPData.getMatchedData());
		}

	}
	
	/**
	 * If domain is blacklisted then check should be performed and set into BlacklistSummary.
	 *
	 * @param blacklistSTPData the blacklist STP data
	 * @param blacklistSummary the blacklist summary
	 */
	//original method
	private void setDomain(BlacklistSTPData blacklistSTPData, BlacklistSummary blacklistSummary) {
		if (StringUtils.isNullOrTrimEmpty(blacklistSummary.getDomain())){
			blacklistSummary.setDomain(String.valueOf(blacklistSTPData.getFound()));
			blacklistSummary.setDomainMatchedData(blacklistSTPData.getMatchedData());
		}

	}

	/**
	 * Creates the ip summary.
	 *
	 * @param contactResponse the contact response
	 * @return the ip summary
	 */
	protected IpSummary createIpSummary(ContactResponse contactResponse) {
		IpContactResponse response = contactResponse.getIpCheck();
		IpSummary ip = new IpSummary();
		if (response != null) {
			ip.setIpAddress(response.getIpAddress());
			ip.setIpRule(response.getChecks());
			ip.setStatus(response.getStatus());
			ip.setIpCity(response.getIpCity());
			ip.setIpCountry(response.getIpCountry());
			ip.setGeoDifference(response.getGeoDifference());
			ip.setUnit(response.getUnit());
		}
		return ip;

	}
	
	
	/**
	 * Populate ip post code fields in contact.
	 *
	 * @param registrationRequest the registration request
	 * @param contactResponse the contact response
	 * @param contactid the contactid
	 */
	protected void populateIpPostCodeFieldsInContact(RegistrationServiceRequest registrationRequest,
			IpContactResponse contactResponse,Integer contactid){
		try{
		registrationRequest.getAccount().getContactByID(contactid).setIpAddressLatitude(contactResponse.getIpLatitude()!=null ? contactResponse.getIpLatitude():"0.00");
		registrationRequest.getAccount().getContactByID(contactid).setIpAddressLongitude(contactResponse.getIpLongitude()!=null? contactResponse.getIpLongitude():"0.00");
		/**
		 * To avoid null pointer exception check condition for PostCodeLatitude and PostCodeLongitude -Saylee
		 */
		if (contactResponse.getPostCodeLatitude() != null) {
				registrationRequest.getAccount().getContactByID(contactid)
						.setPostCodeLatitude(parseFloat(contactResponse.getPostCodeLatitude()));
			}else{
				registrationRequest.getAccount().getContactByID(contactid)
				.setPostCodeLatitude(parseFloat("0.00"));
			}
			if (contactResponse.getPostCodeLongitude() != null) { 
				registrationRequest.getAccount().getContactByID(contactid)
						.setPostCodeLongitude(parseFloat(contactResponse.getPostCodeLongitude()));
			}else{
				registrationRequest.getAccount().getContactByID(contactid)
				.setPostCodeLongitude(parseFloat("0.00"));
			}
		}catch(Exception e){
			LOGGER.error("{1}",e);
		}
	}
	
	/**
	 * Parses the float.
	 *
	 * @param input the input
	 * @return the float
	 */
	private static Float parseFloat(String input){
		Float result ;
		try{
			result = Float.parseFloat(input);
		}catch(Exception ex){
			LOGGER.debug("Invalid float value", ex);
			result = new Float(0.0);
		}
		return result;
	}
	
	/**
	 * Gets the blacklist phone list in string.
	 *
	 * @param list the list
	 * @return the blacklist phone list in string
	 */
	private String getBlacklistPhoneListInString(List<String> list) {
		
		if(null == list || list.isEmpty()){
			return "";
		}
		return String.join(", ", list);
	}
}
