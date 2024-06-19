package com.currenciesdirect.gtg.compliance.compliancesrv.externalservice.enterpriseport;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailHeader;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.EmailPayload;
import com.currenciesdirect.gtg.compliance.commons.domain.commhub.request.SendEmailRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;

/**
 * The Class EmailClientImpl.
 */
public class EmailClientImpl implements IEmailClient {

	/** The log. */
	private static Logger log = LoggerFactory.getLogger(EmailClientImpl.class);
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.compliancesrv.externalservice.
	 * enterpriseport.IEmailClient#sendEmail(com.currenciesdirect.gtg.compliance
	 * .commons.domain.kyc.Address,
	 * com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address,
	 * com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.
	 * RegistrationServiceRequest)
	 */
	@Override
	public void sendEmail(Address oldAddress, Address newAddress, RegistrationServiceRequest request,ICommHubServiceImpl iCommHubServiceImpl) {
		//AT-1999 
		SendEmailRequest sendEmailRequest = new SendEmailRequest(); 
		try {
			sendEmailRequest = prepareMessageForAddressUpdate(sendEmailRequest,oldAddress, newAddress,request);
			iCommHubServiceImpl.sendEmail(sendEmailRequest,true);
		} catch (Exception e) {
			log.error("Error in SendEmail()", e);
		}
	}
	
	/**
	 * Prepare message for address update.
	 *
	 * @param sendEmailRequest the send email request
	 * @return the send email request
	 */
	private SendEmailRequest prepareMessageForAddressUpdate(SendEmailRequest sendEmailRequest,Address oldAddress, Address newAddress,
			RegistrationServiceRequest request) {
		
		EmailHeader header = new EmailHeader();
		EmailPayload payload = new EmailPayload();
		Map<String, String> placeholders = new HashMap<>();
		String[] notificationType = new String[] {"EMAIL"};
		Account account = request.getAccount();
		
		createPlaceHolders(oldAddress, newAddress, placeholders, account);

		header.setCustNumber(account.getTradeAccountNumber());
		header.setCustomerType(account.getCustType());
		header.setEvent(Constants.ADDRESS_UPDATE_EVENT);
		header.setSourceSystem(Constants.SOURCE_ATLAS);
		header.setLocale(Constants.LOCALE_EN);
		header.setNotificationType(notificationType);
		header.setOrgCode(request.getOrgCode());
		header.setLegalEntity(account.getCustLegalEntity());
		header.setRetryTimeout("60000");
		
		payload.setEmailId(System.getProperty(Constants.ADDRESS_UPDATE + Constants.EAMIL_SENDTO));
		payload.setSubject("Address updated");
		payload.setPlaceHolder(placeholders);
		
		sendEmailRequest.setHeader(header);
		sendEmailRequest.setPayload(payload);
		
		return sendEmailRequest;
	}


	/**
	 * @param oldAddress
	 * @param newAddress
	 * @param placeholders
	 * @param account
	 */
	private void createPlaceHolders(Address oldAddress, Address newAddress, Map<String, String> placeholders,
			Account account) {
		
		placeholders.put("#customer_name", getPlaceholdersValue(account.getContacts().get(0).getFirstAndLastName()));
		placeholders.put("#customer_number", getPlaceholdersValue(account.getTradeAccountNumber()));
		
		placeholders.put("#old_building_name", getPlaceholdersValue(oldAddress.getBuildingName()));
		placeholders.put("#old_address1", getPlaceholdersValue(oldAddress.getAddress1()));
		placeholders.put("#old_city", getPlaceholdersValue(oldAddress.getCity()));
		placeholders.put("#old_sub_city", getPlaceholdersValue(oldAddress.getSubCity()));
		placeholders.put("#old_state", getPlaceholdersValue(oldAddress.getState()));
		placeholders.put("#old_postal_code", getPlaceholdersValue(oldAddress.getPostCode()));
		placeholders.put("#old_country", getPlaceholdersValue(oldAddress.getCountry()));

		placeholders.put("#new_building_name", getPlaceholdersValue(newAddress.getBuildingName()));
		placeholders.put("#new_address1", getPlaceholdersValue(newAddress.getAddress1()));
		placeholders.put("#new_city", getPlaceholdersValue(newAddress.getCity()));
		placeholders.put("#new_sub_city", getPlaceholdersValue(newAddress.getSubCity()));
		placeholders.put("#new_state", getPlaceholdersValue(newAddress.getState()));
		placeholders.put("#new_postal_code", getPlaceholdersValue(newAddress.getPostCode()));
		placeholders.put("#new_country", getPlaceholdersValue(newAddress.getCountry()));
	}
	
	/**
	 * Gets the placeholders value.
	 *
	 * @param placeholders the placeholders
	 * @return the placeholders value
	 */
	private String getPlaceholdersValue(String placeholders) {
		if(null != placeholders && !placeholders.isEmpty())
			return placeholders;
		return " ";
	}
}
