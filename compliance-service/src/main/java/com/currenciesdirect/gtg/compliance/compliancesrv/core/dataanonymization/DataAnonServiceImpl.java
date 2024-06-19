package com.currenciesdirect.gtg.compliance.compliancesrv.core.dataanonymization;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonAccount;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonContact;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonRequestFromES;
import com.currenciesdirect.gtg.compliance.commons.domain.dataanonymization.DataAnonResponseForES;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistSTPData;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

@Component("dataAnonServiceImpl")
public class DataAnonServiceImpl implements IDataAnonService{
	
	private static final Logger LOG = LoggerFactory.getLogger(DataAnonServiceImpl.class);
	
	/** The data anon DB service impl. */
	@Autowired
	@Qualifier("dataAnonDBServiceImpl")
	private IDataAnonDBService iDataAnonDBServiceImpl;
	
	/**
	 * Process data anonymization.
	 *
	 * @param message the message
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> processDataAnonymization(Message<MessageContext> message){
		DataAnonRequestFromES request = message.getPayload().getGatewayMessageExchange().getRequest(DataAnonRequestFromES.class);
		DataAnonResponseForES response = new DataAnonResponseForES();
		DataAnonResponseForES actualResponse = new DataAnonResponseForES();
		boolean isAnonymizationComplete = false;
		try {
			Account oldAccount = (Account) request.getAdditionalAttribute(Constants.FIELD_OLD_ACCOUNT);
			createContactAttributeAnonymizeData(request);
			createBlacklistProviderResponse(request);
			isAnonymizationComplete = iDataAnonDBServiceImpl.processDataAnonymization(request,oldAccount);
			if(isAnonymizationComplete) {
				response.setCode(DataAnonConstants.RESPONSECODE2);
				response.setStatus(DataAnonConstants.RECEIVED_AND_PROCESSED);
				actualResponse.setDecision(BaseResponse.DECISION.SUCCESS);
				actualResponse.setResponseCode(ComplianceReasonCode.PASS.getReasonCode());
				actualResponse.setResponseDescription("Data Anonymization successfully completed");
			}
			else {
				response.setCode(DataAnonConstants.RESPONSECODE1);
				response.setStatus(DataAnonConstants.RECEIVED);
				actualResponse.setDecision(BaseResponse.DECISION.FAIL);
				actualResponse.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
				actualResponse.setResponseDescription("Data Anonymization not completed");
			}
			response.setSystem(DataAnonConstants.SYSTEM);	
		}catch(Exception e) {
			LOG.error("Error in DataAnonServiceImpl for processDataAnonymization : {1}",e);
			response.setCode(DataAnonConstants.RESPONSECODE1);
			response.setStatus(DataAnonConstants.RECEIVED);
			response.setSystem(DataAnonConstants.SYSTEM);
			actualResponse.setDecision(BaseResponse.DECISION.FAIL);
			actualResponse.setResponseCode(ComplianceReasonCode.MISSINGINFO.getReasonCode());
			actualResponse.setResponseDescription("Error in processDataAnonymization()");
		}
		response.addAttribute(Constants.BASE_RESPONSE, actualResponse);
		message.getPayload().getGatewayMessageExchange().setResponse(response);
		return message;
	}
	
	/**
	 * Creates the contact attribute anonymize data.
	 *
	 * @param oldAccount the old account
	 * @param request the request
	 */
	private void createContactAttributeAnonymizeData(DataAnonRequestFromES request) {
		String contactAttribute = (String) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS_ATTRIBUTE);
		DataAnonAccount account = request.getAccount();
		DataAnonContact contact = request.getAccount().getContact().get(0);
		
		try {
			Contact anonContactAttribute = JsonConverterUtil.convertToObject(Contact.class, contactAttribute);
			anonContactAttribute.setTitle(contact.getAnonymizationDetails().getTitle());
			anonContactAttribute.setFirstName(contact.getAnonymizationDetails().getName());
			anonContactAttribute.setMiddleName(contact.getAnonymizationDetails().getName());
			anonContactAttribute.setLastName(contact.getAnonymizationDetails().getName());
			anonContactAttribute.setDob(contact.getAnonymizationDetails().getName());
			anonContactAttribute.setState(account.getAnonymizationDetails().getAddress().getState());//stateProvinceCounty
			anonContactAttribute.setPrefecture(account.getAnonymizationDetails().getAddress().getState());//prefecture
			anonContactAttribute.setCity(account.getAnonymizationDetails().getAddress().getCity());//townCityMuni
			anonContactAttribute.setAza(account.getAnonymizationDetails().getAddress().getCity());
			anonContactAttribute.setSubCity(account.getAnonymizationDetails().getAddress().getCity());
			anonContactAttribute.setAddress1(account.getAnonymizationDetails().getAddress().getAddressLine());
			anonContactAttribute.setStreet(account.getAnonymizationDetails().getAddress().getStreet());
			anonContactAttribute.setBuildingName(account.getAnonymizationDetails().getAddress().getStreet());
			anonContactAttribute.setPostCode(account.getAnonymizationDetails().getAddress().getPostalCode());
			anonContactAttribute.setCountry(account.getAnonymizationDetails().getAddress().getCountry());
			anonContactAttribute.setPhoneHome(contact.getAnonymizationDetails().getPhone());
			anonContactAttribute.setPhoneMobile(contact.getAnonymizationDetails().getPhone());
			anonContactAttribute.setPhoneWork(contact.getAnonymizationDetails().getPhone());
			anonContactAttribute.setPhoneWorkExtn(contact.getAnonymizationDetails().getPhone());
			anonContactAttribute.setIpAddress(account.getAnonymizationDetails().getIp());
			anonContactAttribute.setEmail(contact.getAnonymizationDetails().getEmail());
			
			request.addAttribute(DataAnonConstants.CONTACT_ATTRIBUTE_ANONDATA, JsonConverterUtil.convertToJsonWithNull(anonContactAttribute));
		}catch(Exception e) {
			LOG.error("Error in DataAnonServiceImpl for createContactAttributeAnonymizeData : {1}",e);
		}
	}

	/**
	 * Save data anon request.
	 *
	 * @param request the request
	 * @throws ComplianceException 
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> saveDataAnonRequest(Message<MessageContext> message) throws ComplianceException {
		UserProfile userProfile = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		DataAnonRequestFromES request = message.getPayload().getGatewayMessageExchange().getRequest(DataAnonRequestFromES.class);
		DataAnonResponseForES response = message.getPayload().getGatewayMessageExchange().getResponse(DataAnonResponseForES.class);
		try {
			iDataAnonDBServiceImpl.saveDataAnonRequest(request,userProfile);
		}catch(Exception e) {
			LOG.error("Error in DataAnonServiceImpl for saveDataAnonRequest : {1}",e);
			response.setCode(DataAnonConstants.RESPONSECODE1);
			response.setStatus(DataAnonConstants.RECEIVED);
			response.setSystem(DataAnonConstants.SYSTEM);
		}
		return message;
	}
	
	/**
	 * Creates the blacklist provider response.
	 *
	 * @param request the request
	 */
	private void createBlacklistProviderResponse(DataAnonRequestFromES request) {
		creatingRegProviderResponse(request);
		creatingPayInProviderResponse(request);
		creatingPayOutProviderResponse(request);
	}

	/**
	 * Creating reg provider response.
	 *
	 * @param request the request
	 * @param regProviderResponse the reg provider response
	 * @param data the data
	 */
	private void creatingRegProviderResponse(DataAnonRequestFromES request) {
		BlacklistContactResponse providerResponse = new BlacklistContactResponse();
		List<BlacklistSTPData> listData = new ArrayList<>();
		setResponse(listData,"NAME","CONTACT NAME",request.getAccount().getAnonymizationDetails().getName());
		setResponse(listData,"EMAIL","EMAIL",request.getAccount().getContact().get(0).getAnonymizationDetails().getEmail());
		setResponse(listData,"DOMAIN","DOMAIN","");
		setResponse(listData,"PHONE_NUMBER","PHONE_NUMBER",request.getAccount().getContact().get(0).getAnonymizationDetails().getPhone());
		setResponse(listData,"IPADDRESS","IPADDRESS",request.getAccount().getAnonymizationDetails().getIp());
		providerResponse.setStatus("");
		providerResponse.setData(listData);
		String jsonProviderResponse = JsonConverterUtil.convertToJsonWithNull(providerResponse);
		request.addAttribute("blacklistProviderResponse", jsonProviderResponse);
	}
	
	/**
	 * Creating pay out provider response.
	 *
	 * @param request the request
	 * @param providerResponse the provider response
	 * @param data the data
	 */
	private void creatingPayOutProviderResponse(DataAnonRequestFromES request) {
		BlacklistContactResponse providerResponse = new BlacklistContactResponse();
		List<BlacklistSTPData> listData = new ArrayList<>();
		setResponse(listData,"ACC_NUMBER","ACC_NUMBER",request.getAccount().getAnonymizationDetails().getBankAccount());
		setResponse(listData,"NAME","CONTACT NAME",request.getAccount().getAnonymizationDetails().getName());
		setResponse(listData,"NAME","BANK NAME",request.getAccount().getAnonymizationDetails().getName());
		providerResponse.setStatus("");
		providerResponse.setData(listData);
		String jsonProviderResponse = JsonConverterUtil.convertToJsonWithNull(providerResponse);
		request.addAttribute("blacklistPayOutProviderResponse", jsonProviderResponse);
	}

	/**
	 * Creating pay in provider response.
	 *
	 * @param request the request
	 * @param providerResponse the provider response
	 * @param data the data
	 */
	private void creatingPayInProviderResponse(DataAnonRequestFromES request) {
		BlacklistContactResponse providerResponse = new BlacklistContactResponse();
		List<BlacklistSTPData> listData = new ArrayList<>();
		setResponse(listData,"NAME","CC NAME",request.getAccount().getAnonymizationDetails().getName());
		providerResponse.setStatus("");
		providerResponse.setData(listData);
		String jsonProviderResponse = JsonConverterUtil.convertToJsonWithNull(providerResponse);
		request.addAttribute("blacklistPayInProviderResponse", jsonProviderResponse);
	}
	
	/**
	 * Sets the response.
	 *
	 * @param listData the list data
	 * @param type the type
	 * @param requestType the request type
	 * @param value the value
	 */
	private void setResponse(List<BlacklistSTPData> listData,String type,String requestType,String value) {
		BlacklistSTPData data = new BlacklistSTPData();
		data.setType(type);
		data.setRequestType(requestType);
		data.setValue(value);
		data.setFound(Boolean.FALSE);
		data.setMatch(0);
		data.setMatchedData("");
		listData.add(data);
	}

}
