package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.mqbroadcast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.BroadCastEntityTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.BroadCastQueueDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.mq.BroadCastQueueRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceContact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MQMessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class DataEnricher {

	private static final Logger LOG = LoggerFactory.getLogger(DataEnricher.class);
	
	/**
	 * @param message
	 * @return message
	 * @throws ComplianceException
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MQMessageContext> process(Message<MQMessageContext> message) throws ComplianceException {
		BroadCastQueueDBServiceImpl broadCastQueueDBServiceImpl = new BroadCastQueueDBServiceImpl();
		BroadCastQueueRequest broadCastQueueRequest = (BroadCastQueueRequest) message.getPayload().getRequest();
		String statusJson = JsonConverterUtil.convertToJsonWithNull(broadCastQueueRequest.getStatusJson());
		RegistrationResponse regStatusJson = JsonConverterUtil.convertToObject(RegistrationResponse.class, statusJson);
		String entityType = broadCastQueueRequest.getEntityType();
		try {
			if( (BroadCastEntityTypeEnum.ADDCONTACT.toString().equals(entityType) 
					|| BroadCastEntityTypeEnum.SIGNUP.toString().equals(entityType) 
					|| BroadCastEntityTypeEnum.UPDATE.toString().equals(entityType)) 			
					&& Constants.CFX.equals(regStatusJson.getAccount().getCustType())) {
				addRegistrationResponseDates(regStatusJson,broadCastQueueRequest);
				ComplianceAccount allContacts = broadCastQueueDBServiceImpl.getContactsDetails(regStatusJson.getAccount().getAccountSFID());
				List<ComplianceContact> contactList;
				contactList = getContact(regStatusJson,allContacts);
				regStatusJson.getAccount().setContacts(contactList);
				broadCastQueueRequest.setStatusJson(regStatusJson);
				message.getPayload().setRequest(broadCastQueueRequest);
			}
			setSpecificFieldsValuesToNull(regStatusJson);
		}
		catch(Exception e) {
			LOG.error("Error DataEnricher process method:", e);
		}
		return message;
	}

	/**
	 * Gets all the contact response and add it in the request
	 * @param regStatusJson
	 * @param allContacts
	 * @return contact List
	 */
	private List<ComplianceContact> getContact(RegistrationResponse regStatusJson, ComplianceAccount allContacts) {
		List<ComplianceContact> contactList = new ArrayList<>();
		for(ComplianceContact contact : allContacts.getContacts()) {
			ComplianceContact contacts = new ComplianceContact();
			if(ComplianceReasonCode.BLACKLISTED.getReasonCode().equals(regStatusJson.getAccount().getResponseCode())) {
				if(regStatusJson.getAccount().getSpecificContact().getContactSFID().equals(contact.getContactSFID())) {
					contacts.setResponseCode(regStatusJson.getAccount().getResponseCode());
					contacts.setResponseDescription(regStatusJson.getAccount().getResponseDescription());
					contacts.setReasonForInactive(regStatusJson.getAccount().getReasonForInactive());
				}
				else {
					contacts.setResponseCode(regStatusJson.getAccount().getOtherContacts().getResponseCode());
					contacts.setResponseDescription(regStatusJson.getAccount().getOtherContacts().getResponseDescription());
				}
			}
			else {
				contacts.setResponseCode(regStatusJson.getAccount().getResponseCode());
				contacts.setResponseDescription(regStatusJson.getAccount().getResponseDescription());
			}
			contacts.setCcs(regStatusJson.getAccount().getAcs());
			contacts.setContactSFID(contact.getContactSFID());
			contacts.setTradeContactID(contact.getTradeContactID());
			contactList.add(contacts);
		}
		return contactList;
	}
	
	/**
	 * Remove the fields from request
	 * @param regStatusJson
	 */
	private void setSpecificFieldsValuesToNull(RegistrationResponse regStatusJson) {
		if(null != regStatusJson.getAccount()) {
			regStatusJson.getAccount().setSpecificContact(null);
			regStatusJson.getAccount().setOtherContacts(null);
			regStatusJson.getAccount().setCustType(null);
			regStatusJson.getAccount().setEventType(null);
		}
	}
	
	/**
	 * Set Registered and Registration in date
	 * @param regStatusJson
	 * @param broadCastQueueRequest
	 */
	@SuppressWarnings("unchecked")
	private void addRegistrationResponseDates(RegistrationResponse regStatusJson, BroadCastQueueRequest broadCastQueueRequest) {
		Map<String,Object> accounts;
		accounts = (Map<String, Object>) broadCastQueueRequest.getStatusJson();
		accounts = (Map<String, Object>) accounts.get("account");
		String regDate = (String) accounts.get("registeredDate");
		String regInDate = (String) accounts.get("registrationInDate");
		if(null != regDate)
			regStatusJson.getAccount().setRegisteredDate(DateTimeFormatter.convertStringToTimestamp(regDate));
		if(null != regInDate)
			regStatusJson.getAccount().setRegistrationInDate(DateTimeFormatter.convertStringToTimestamp(regInDate));
	}
}