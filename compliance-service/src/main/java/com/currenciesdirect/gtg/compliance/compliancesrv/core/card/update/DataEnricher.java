package com.currenciesdirect.gtg.compliance.compliancesrv.core.card.update;

import com.currenciesdirect.gtg.compliance.commons.domain.base.BaseResponse;
import com.currenciesdirect.gtg.compliance.commons.util.ObjectCloner;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.InternalProcessingCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.AbstractDataEnricher;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.CardDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.OrderCardStatusUpdateRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.ordercardsrv.response.OrderCardResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Card;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.ComplianceAccount;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.response.RegistrationResponse;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.CopyObjectUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
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
	
	/** The Constant REQUEST_IS_NOT_VALID. */
	private static final String REQUEST_IS_NOT_VALID = "Request is not valid";

	@Autowired
	protected CardDBServiceImpl cardDBServiceImpl;

	/**
	 * Process.
	 *
	 * @param message the message
	 * @param correlationID the correlation ID
	 * @return the message
	 * @throws ComplianceException the compliance exception
	 */
	@org.springframework.integration.annotation.ServiceActivator
	public Message<MessageContext> process(Message<MessageContext> message, @Header UUID correlationID)  {
		LOGGER.debug("Gateway message headers=[{}], payload=[{}]", message.getHeaders(),
				message.getPayload());

		MessageExchange messageExchange = message.getPayload().getGatewayMessageExchange();
		OrderCardResponse orderCardResponse = messageExchange.getResponse(OrderCardResponse.class);
		OrderCardStatusUpdateRequest orderCardStatusUpdateRequest = messageExchange.getRequest(OrderCardStatusUpdateRequest.class);
		
		try {
			RegistrationServiceRequest registrationServiceRequest = new RegistrationServiceRequest();
			RegistrationResponse registrationResponse = new RegistrationResponse();
			
			Card oldRequest=getCardDetails(orderCardStatusUpdateRequest.getCardId());
			Card intuitionCardStatusUpdateRequest = setCardInformation(orderCardStatusUpdateRequest, oldRequest);
			Card newRequest=(Card) ObjectCloner.deepCopy(intuitionCardStatusUpdateRequest);
	        
	        if(oldRequest != null) {
	            CopyObjectUtil.copyNullFields(newRequest, oldRequest, Card.class);
	            updateCard(message, messageExchange, orderCardResponse, newRequest,
						registrationServiceRequest, registrationResponse);
	            messageExchange.getRequest().addAttribute("isUpdateCard", Boolean.TRUE);
	        }else {
	        	updateCard(message, messageExchange, orderCardResponse, intuitionCardStatusUpdateRequest,
						registrationServiceRequest, registrationResponse);
	        	messageExchange.getRequest().addAttribute("isUpdateCard", Boolean.FALSE);
	        	if(messageExchange.getResponse().getErrorCode()==null) {
	        		return message;
	        	}
	        	else{        		
	        		LOGGER.error(REQUEST_IS_NOT_VALID);
	    			orderCardResponse.setDecision(BaseResponse.DECISION.FAIL);
	    			orderCardResponse.setResponseCode(InternalProcessingCode.INV_REQUEST.toString());
	    			orderCardResponse.setResponseDescription(InternalProcessingCode.INV_REQUEST.toString());
	    			messageExchange.setResponse(orderCardResponse);
	    			return message;
	        	}	
	        }	
			
		}catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			orderCardResponse.setDecision(BaseResponse.DECISION.FAIL);
			orderCardResponse.setResponseCode(InternalProcessingCode.INV_REQUEST.toString());
			orderCardResponse.setResponseDescription(InternalProcessingCode.INV_REQUEST.toString());
			messageExchange.setResponse(orderCardResponse);
			return message;
		}
		return message;
	}
	
	/**
	 * Sets the card information.
	 *
	 * @param orderCardStatusUpdateRequest the order card status update request
	 * @param oldRequest the old request
	 * @return the card
	 */
	private Card setCardInformation(OrderCardStatusUpdateRequest orderCardStatusUpdateRequest, Card oldRequest) {
		Card card = new Card();
		CardStatusEnumValues enumValues = CardStatusEnumValues.getInstance();
		card.setCardID(orderCardStatusUpdateRequest.getCardId());
		card.setTradeAccountNumber(orderCardStatusUpdateRequest.getTradeAccountNumber());
		card.setEventType(orderCardStatusUpdateRequest.getEventType());
		card.setIsCardActive(orderCardStatusUpdateRequest.getIsCardActive());
		card.setContactId(Integer.parseInt(orderCardStatusUpdateRequest.getContactId()));
		
		if(orderCardStatusUpdateRequest.getEventType()!=null) {
			setEventDate(orderCardStatusUpdateRequest, card);
		}
		
		
		//if cardActive field is true at a first time then set DateCardActivated OR in update case check cardActive value is false or null
		if((oldRequest!=null && (oldRequest.getIsCardActive() == null || Boolean.FALSE.equals(oldRequest.getIsCardActive())) 
				&& Boolean.TRUE.equals(orderCardStatusUpdateRequest.getIsCardActive())) ||
				(oldRequest == null && Boolean.TRUE.equals(orderCardStatusUpdateRequest.getIsCardActive()))) {
				card.setDateCardActivated(orderCardStatusUpdateRequest.getEventDate());
		}	
		
		if(Boolean.TRUE.equals(enumValues.checkFreezeStatus(orderCardStatusUpdateRequest.getEventStatus()))) {
			card.setDateCardFrozen(orderCardStatusUpdateRequest.getEventDate());
			card.setFreezeReason(orderCardStatusUpdateRequest.getEventReason());	
		}
		
		if(Boolean.TRUE.equals(enumValues.checkBlockStatus(orderCardStatusUpdateRequest.getEventStatus()))) {
			card.setDateCardBlocked(orderCardStatusUpdateRequest.getEventDate());
			card.setReasonForBlock(orderCardStatusUpdateRequest.getEventReason());
		}	
		
		if(oldRequest!=null && oldRequest.getCardStatusFlag() != null) {
			if(Boolean.TRUE.equals(enumValues.checkFreezeStatus(oldRequest.getCardStatusFlag())) 
					&& orderCardStatusUpdateRequest.getEventStatus().equalsIgnoreCase("ALL_GOOD")) {
				card.setDateCardUnfrozen(orderCardStatusUpdateRequest.getEventDate());
			}
			
			if(Boolean.TRUE.equals(enumValues.checkBlockStatus(oldRequest.getCardStatusFlag())) 
					&& orderCardStatusUpdateRequest.getEventStatus().equalsIgnoreCase("ALL_GOOD")) {
				card.setDateCardUnblocked(orderCardStatusUpdateRequest.getEventDate());
			}
		}
		
		return card;
	}

	/**
	 * @param orderCardStatusUpdateRequest
	 * @param card
	 */
	private void setEventDate(OrderCardStatusUpdateRequest orderCardStatusUpdateRequest, Card card) {
		if(orderCardStatusUpdateRequest.getEventType().equalsIgnoreCase("ViewPIN")) {
			card.setDateLastCardPinView(orderCardStatusUpdateRequest.getEventDate());
		}
		else if(orderCardStatusUpdateRequest.getEventType().equalsIgnoreCase("ViewPAN")){
			card.setDateLastCardPanView(orderCardStatusUpdateRequest.getEventDate());
		}
		else if(orderCardStatusUpdateRequest.getEventType().equalsIgnoreCase("CreateCard")) {
			card.setDateCardDispatched(orderCardStatusUpdateRequest.getEventDate());
		}
		
		if(orderCardStatusUpdateRequest.getEventType().equalsIgnoreCase("CreateCard") || 
				orderCardStatusUpdateRequest.getEventType().equalsIgnoreCase("StatusUpdate")) {
			card.setCardStatusFlag(orderCardStatusUpdateRequest.getEventStatus());
		}
	}
	  
	/**
	 * Update card.
	 *
	 * @param message the message
	 * @param messageExchange the message exchange
	 * @param orderCardResponse the order card response
	 * @param orderCardStatusUpdateRequest the order card status update request
	 * @param registrationServiceRequest the registration service request
	 * @param registrationResponse the registration response
	 */
	private void updateCard(Message<MessageContext> message, MessageExchange messageExchange,
			OrderCardResponse orderCardResponse, Card intuitionCardStatusUpdateRequest,
			RegistrationServiceRequest registrationServiceRequest, RegistrationResponse registrationResponse) {
		Card card=new Card();
		HashMap<String, Integer> pair=new HashMap<>();
		try {
			getUserTableId(message);
			Map<String, String> accountInfo = getAccountId (intuitionCardStatusUpdateRequest.getTradeAccountNumber(), intuitionCardStatusUpdateRequest.getContactId());
				Account account = new Account();
			account.setTradeAccountNumber(intuitionCardStatusUpdateRequest.getTradeAccountNumber());
			
			if(accountInfo.isEmpty()) {
				registrationServiceRequest.setAccount(account);
				pair.put(intuitionCardStatusUpdateRequest.getTradeAccountNumber(), intuitionCardStatusUpdateRequest.getContactId());			
				registrationServiceRequest.addAttribute("CONTACT_ID_WRONG", pair);
				account.setId(null);
				throw new Exception();
			}else {
				account.setId(Integer.parseInt(accountInfo.get("id")));
			}			
			account.setVersion(1);

			card = setCardIntuitionInformation(intuitionCardStatusUpdateRequest);
			card.setContactId(Integer.parseInt(accountInfo.get("contactId")));
			
			registrationServiceRequest.setOrgCode(accountInfo.get("orgCode"));
			registrationServiceRequest.setIsCardApply("U");
			registrationServiceRequest.setAccount(account);
			registrationServiceRequest.setCard(card);

			ComplianceAccount complianceAccount = new ComplianceAccount();
			registrationResponse.setAccount(complianceAccount);
			message.getPayload().setOrgCode(accountInfo.get("orgCode"));
			messageExchange.setRequest(registrationServiceRequest);
			messageExchange.setResponse(registrationResponse);
			messageExchange.getRequest().addAttribute("card", card);
		} catch (Exception e) {
			LOGGER.error(REQUEST_IS_NOT_VALID, e);
			orderCardResponse.setDecision(BaseResponse.DECISION.FAIL);
			orderCardResponse.setResponseCode(InternalProcessingCode.INV_REQUEST.toString());
			orderCardResponse.setResponseDescription(InternalProcessingCode.INV_REQUEST.toString());
			orderCardResponse.setErrorCode(InternalProcessingCode.INV_REQUEST.toString());
			messageExchange.setRequest(registrationServiceRequest);
			messageExchange.setResponse(orderCardResponse);
			message.getPayload().setFailed(true);
			
		}
	}

	/**	
	 * Sets the card intuition information.
	 *
	 * @param orderCardStatusUpdateRequest the order card status update request
	 * @return the card
	 */
	private Card setCardIntuitionInformation(Card orderCardStatusUpdateRequest) {
		Card card = new Card();
		card.setCardID(orderCardStatusUpdateRequest.getCardID());
		card.setCardStatusFlag(orderCardStatusUpdateRequest.getCardStatusFlag());
		card.setDateCardDispatched(orderCardStatusUpdateRequest.getDateCardDispatched());
		card.setDateCardActivated(orderCardStatusUpdateRequest.getDateCardActivated());
		card.setDateCardFrozen(orderCardStatusUpdateRequest.getDateCardFrozen());
		card.setFreezeReason(orderCardStatusUpdateRequest.getFreezeReason());
		card.setDateCardUnfrozen(orderCardStatusUpdateRequest.getDateCardUnfrozen());
		card.setDateCardBlocked(orderCardStatusUpdateRequest.getDateCardBlocked());
		card.setReasonForBlock(orderCardStatusUpdateRequest.getReasonForBlock());
		card.setDateCardUnblocked(orderCardStatusUpdateRequest.getDateCardUnblocked());
		card.setDateLastCardPinView(orderCardStatusUpdateRequest.getDateLastCardPinView());
		card.setDateLastCardPanView(orderCardStatusUpdateRequest.getDateLastCardPanView());
		card.setTradeAccountNumber(orderCardStatusUpdateRequest.getTradeAccountNumber());
		card.setEventType(orderCardStatusUpdateRequest.getEventType());
		card.setIsCardActive(orderCardStatusUpdateRequest.getIsCardActive());
		
		return card;
	}
	
	 /**
 	 * Gets the account id.
 	 *
 	 * @param tradeAccountNumber the trade account number
 	 * @return the account id
 	 */
	public Map<String, String> getAccountId(String tradeAccountNumber, Integer tradeContactId) {
		Map<String, String> map = null;
        try {
        	map = newRegistrationDBServiceImpl.getAccountIdByTradeAccountNumber(tradeAccountNumber, tradeContactId);
        	if(map.isEmpty()) throw new ComplianceException(InternalProcessingCode.INV_REQUEST);
        } catch (ComplianceException e) {
        	LOGGER.error("Error in getAccountId method:", e);
        }
        return map;
	}
	 
	/**
	 * Gets the card details.
	 *
	 * @param cardRequest the card request
	 * @return the card details
	 */
	// Added for AT-4812
    public Card getCardDetails(String cardId) {
        Card oldRequest = null;
        try {
            oldRequest = cardDBServiceImpl.getCardDetailsFromDb(cardId);
            return oldRequest;
        } catch (ComplianceException e) {
        	LOGGER.error("Error getCardDetails method:", e);
        }
        return oldRequest;
    }
  
}
