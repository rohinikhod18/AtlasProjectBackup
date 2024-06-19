package com.currenciesdirect.gtg.compliance.compliancesrv.externalservice.enterpriseport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.kyc.Address;
import com.currenciesdirect.gtg.compliance.commons.externalservice.commhubport.ICommHubServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class EmailTransformer.
 */
public class EmailTransformer {

	/** The i comm hub service impl. */
	@Autowired
	private ICommHubServiceImpl iCommHubServiceImpl;
										 
	/**
	 * Send email on address update.
	 *
	 * @param message the message
	 */
	public Message<MessageContext> sendEmailOnAddressUpdate(Message<MessageContext> message) {

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		RegistrationServiceRequest request = messageExchange.getRequest(RegistrationServiceRequest.class);
		List<Contact> oldContacts = (List<Contact>) request.getAdditionalAttribute(Constants.FIELD_OLD_CONTACTS);

		//isEligibleForEmail assign false for jira AT-4519
		final boolean isEligibleForEmail = false;// (boolean) request.getAdditionalAttribute("sendEmailOnAddressChange");
		Address oldAddress = null;
		Address newAddress = null;
		if (isEligibleForEmail) {
			for (Contact contact : request.getAccount().getContacts()) {
				Contact oldContact = getContactBySFId(contact.getContactSFID(), oldContacts);
				if (null != oldContact) {
					oldAddress = transformAddressDetails(oldContact);
				}
				newAddress = transformAddressDetails(contact);
			}

			IEmailClient emailClient = new EmailClientImpl();
			emailClient.sendEmail(oldAddress, newAddress,request,iCommHubServiceImpl);
		}
		return message;

	}

	/**
	 * Transform address details.
	 *
	 * @param contact the contact
	 * @return the address
	 */
	private Address transformAddressDetails(Contact contact) {

		Address address = new Address();

		address.setStreetNumber(contact.getStreetNumber());
		address.setAddress1(contact.getAddress1());
		address.setCity(contact.getCity());
		address.setState(contact.getState());
		address.setPostCode(contact.getPostCode());
		address.setCountry(contact.getCountry());
		address.setSubCity(contact.getSubCity());
		address.setBuildingName(contact.getBuildingName());

		return address;
	}

	/**
	 * Gets the contact by SF id.
	 *
	 * @param sfId     the sf id
	 * @param contacts the contacts
	 * @return the contact by SF id
	 */
	private Contact getContactBySFId(String sfId, List<Contact> contacts) {
		for (Contact c : contacts) {
			if (c.getContactSFID().equalsIgnoreCase(sfId))
				return c;
		}
		return null;
	}
}
