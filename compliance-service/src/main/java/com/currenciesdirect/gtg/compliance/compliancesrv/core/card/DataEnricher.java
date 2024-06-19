/*
 * 
 */
package com.currenciesdirect.gtg.compliance.compliancesrv.core.card;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.OrderCardRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;

/**
 * The Class DataEnricher.
 */
public class DataEnricher extends AbstractDataEnricher {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataEnricher.class);

	/** The new reg DB service impl. */
	@Autowired
	protected NewRegistrationDBServiceImpl newRegDBServiceImpl;

	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID) {
		LOGGER.debug("Gateway message headers=[{}], payload=[{}]", message.getHeaders(), message.getPayload());

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		OrderCardResponse orderCardResponse = messageExchange.getResponse(OrderCardResponse.class);
		OrderCardRequest orderCardRequest = messageExchange.getRequest(OrderCardRequest.class);
		RegistrationServiceRequest registrationServiceRequest = new RegistrationServiceRequest();
		RegistrationResponse registrationResponse = new RegistrationResponse();
		HashMap<String, String> pair = new HashMap<>();
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		try {

			Account account = new Account();
			getUserTableId(message);
			Map<String, String> accountInfo = getOrgId(orderCardRequest.getTradeAccountNumber(), orderCardRequest.getTradeContactID());
			account.setTradeAccountNumber(orderCardRequest.getTradeAccountNumber());
			Boolean loadCriteria = Boolean.FALSE;
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			String registrationLoadCriteria = null;
			
			if (accountInfo.isEmpty()) {
				registrationServiceRequest.setAccount(account);
				pair.put(orderCardRequest.getTradeAccountNumber(), orderCardRequest.getTradeContactID());
				registrationServiceRequest.addAttribute("CONTACT_ID_WRONG", pair);
				account.setId(null);
				throw new Exception();
			}
			account.setCardDeliveryToSecondaryAddress(StringUtils
					.capitalize(BooleanUtils.toStringYesNo(orderCardRequest.getCardDeliveryToSecondaryAddress())));
			account.setId(Integer.parseInt(accountInfo.get("id")));
			account.setVersion(1);
			
			registrationServiceRequest.setOrgCode(accountInfo.get("orgCode"));
			registrationServiceRequest.setIsCardApply("A");
			registrationServiceRequest.setIpAddress(orderCardRequest.getRequestIPAddress());
			registrationServiceRequest.setTradeContractId(orderCardRequest.getTradeContactID());
			if(orderCardRequest.getPasswordChangeDate() != null) {
				registrationServiceRequest.setLastPasswordChangeDate(
						getFormattedDateForCardEligiblity(orderCardRequest.getPasswordChangeDate()));
			}
			else {
				registrationServiceRequest.setLastPasswordChangeDate(null);
			}
			
			if(orderCardRequest.getApplicationInstallDate() != null) {
				registrationServiceRequest.setAppInstallDate(
						getFormattedDateForCardEligiblity(orderCardRequest.getApplicationInstallDate()));
			}
			else {
				registrationServiceRequest.setAppInstallDate(null);
			}
			
			registrationServiceRequest.setDeviceId(orderCardRequest.getApplicationDeviceId());
			registrationServiceRequest.setAccount(account);

			ComplianceAccount complianceAccount = new ComplianceAccount();
			complianceAccount.setId(Integer.parseInt(accountInfo.get("id")));
			registrationResponse.setAccount(complianceAccount);

			// AT-5127
			if(registrationServiceRequest.getIsCardApply().equalsIgnoreCase("A") && Integer.parseInt(accountInfo.get("accountTMFlag")) == 0) {
				loadCriteria = Boolean.TRUE;
				registrationLoadCriteria = "cardApply-"+currentDate;
				//update accountTMFlag as 4
				updateAccountTMFlag(Integer.parseInt(accountInfo.get("id")), 4, token.getUserID());
			}
			
			message.getPayload().setOrgCode(accountInfo.get("orgCode"));
			messageExchange.setRequest(registrationServiceRequest);
			messageExchange.setResponse(registrationResponse);
			messageExchange.getRequest().addAttribute("loadCriteria", loadCriteria); //AT-5127
			messageExchange.getRequest().addAttribute("registrationLoadCriteria", registrationLoadCriteria); //AT-5127
			messageExchange.getRequest().addAttribute("orderCardEligibilityRequest", orderCardRequest);
		} catch (Exception e) {

			LOGGER.error("Request is not valid");
			orderCardResponse.setDecision(BaseResponse.DECISION.FAIL);
			orderCardResponse.setResponseCode(InternalProcessingCode.INV_REQUEST.toString());
			orderCardResponse.setResponseDescription(InternalProcessingCode.INV_REQUEST.toString());
			messageExchange.setResponse(orderCardResponse);
			messageExchange.setRequest(registrationServiceRequest);
			message.getPayload().setFailed(true);
		}
		return message;
	}

	/**
	 * Gets the org id.
	 *
	 * @param tradeAccountNumber the trade account number
	 * @param tradeContactId the trade contact id
	 * @return the org id
	 */
	public Map<String, String> getOrgId(String tradeAccountNumber, String tradeContactId) {
		Map<String, String> list = null;
		try {
			list = newRegistrationDBServiceImpl.getAccountIdByTradeAccountNumber(tradeAccountNumber,
					Integer.parseInt(tradeContactId));
			if (list.isEmpty())
				throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
		} catch (ComplianceException e) {
			LOGGER.error("Error in getAccountId method:", e);
		}
		return list;
	}

	/**
	 * Gets the formatted date for card eligiblity.
	 *
	 * @param dateToFormate the date to formate
	 * @return the formatted date for card eligiblity
	 */
	// AT-5071
	public String getFormattedDateForCardEligiblity(String dateToFormate) {
		String formattedDate = dateToFormate;

		boolean isDateFormatted = getFormattedDate(dateToFormate);
		if (isDateFormatted) {
			try {
				DateFormat originalFormat = new SimpleDateFormat("dd/mm/yyyy");
				DateFormat targetFormat = new SimpleDateFormat("yyyy-mm-dd");
				Date date = originalFormat.parse(dateToFormate);
				formattedDate = targetFormat.format(date);
			} catch (Exception e) {
				LOGGER.error("Exception in getFormattedDateForCardEligiblity() : ", e);
			}
		}

		return formattedDate;
	}

	/**
	 * Gets the formatted date.
	 *
	 * @param isDateFormatted the is date formatted
	 * @return the formatted date
	 */
	private boolean getFormattedDate(String isDateFormatted) {
		String regex = "[0-9]{1,2}\\/[0-9]{1,2}\\/[0-9]{4}";
		return isDateFormatted.matches(regex);
	}
	
	/**
	 * Update account TM flag.
	 *
	 * @param accountId the account id
	 * @param accountTMFlag the account TM flag
	 * @param userId the user id
	 * @throws ComplianceException the compliance exception
	 */
	//AT-5127
	private void updateAccountTMFlag(Integer accountId, Integer accountTMFlag, Integer userId)
			throws ComplianceException {
		try {
			newRegDBServiceImpl.updateIntoAccountTMFlag(accountId, accountTMFlag, userId);
		} catch (Exception e) {
			LOGGER.error("Error in updateAccountTMFlag method:", e);
		} 
	}

}
