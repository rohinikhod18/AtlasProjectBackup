package com.currenciesdirect.gtg.compliance.compliancesrv.core.card.update;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.compliancesrv.core.reg.RegistrationEnumValues;

// TODO: Auto-generated Javadoc
/**
 * The Class CardStatusEnumValues.
 */
public class CardStatusEnumValues {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(CardStatusEnumValues.class);
	
	/** The Constant ERROR. */
	private static final String ERROR = "Error";
	
	/** The enum values. */
	private static CardStatusEnumValues enumValues = null;
	
	/** The block status. */
	private List<String> blockStatus = new ArrayList<>();
	
	/** The freeze status. */
	private List<String> freezeStatus = new ArrayList<>();
	
	/** The status. */
	private List<String> status = new ArrayList<>();
	
	/**
	 * Instantiates a new card status enum values.
	 */
	private CardStatusEnumValues() {
		blockStatus.add("SHORT_TERM_PARTIAL_BLOCK");
		blockStatus.add("SHORT_TERM_FULL_BLOCK");
		blockStatus.add("LONG_TERM_PARTIAL_BLOCK");
		blockStatus.add("LONG_TERM_FULL_BLOCK");
		blockStatus.add("TEMPORARY_BLOCK_COMPLIANCE");
		blockStatus.add("PERMANENT_COMPLIANCE_BLOCK");
		
		freezeStatus.add("FREEZE_CARD");
		
		status.add("ALL_GOOD");
		status.add("REFER_TO_CARD_ISSUER");
		status.add("NOT_ACTIVATED");
		status.add("CAPTURE_CARD");
		status.add("DO_NOT_HONOUR");
		status.add("INVALID_CARD");
		status.add("TRANSACTION_NOT_PERMITTED");
		status.add("RESTRICTED_CARD");
		status.add("SECURITY_VIOLATION");
		status.add("CONTACT_ISSUER");
		status.add("ALLOWABLE_ON_OF_PIN_TRIES_EXCEEDED");
		status.add("REFUND_GIVEN_TO_CUSTOMER");
		status.add("CARD_VOIDED");
		status.add("SUSPECTED_FRAUD_COMPLIANCE");
		status.add("LOST_CARD");
		status.add("STOLEN_CARD");
		status.add("CLOSED_ACCOUNT");
		status.add("EXPIRED_CARD");
		status.add("SUSPECTED_FRAUD");
		status.add("CARD_DESTROYED");
		status.add("DAMAGED_CARD");
	}
	
	/**
	 * Gets the single instance of CardStatusEnumValues.
	 *
	 * @return single instance of CardStatusEnumValues
	 */
	public static CardStatusEnumValues getInstance() {
		if(enumValues==null){
			enumValues = new CardStatusEnumValues();
		}
		return enumValues;
	}
	
	/**
	 * Check block status.
	 *
	 * @param cardStatus the card status
	 * @return true, if successful
	 */
	public boolean checkBlockStatus(String cardStatus){
		try{
			for (String status : blockStatus) {
				if(cardStatus.equalsIgnoreCase(status)){
					return true;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return false;
	}
	
	/**
	 * Check freeze status.
	 *
	 * @param cardStatus the card status
	 * @return true, if successful
	 */
	public boolean checkFreezeStatus(String cardStatus){
		try{
			for (String status : freezeStatus) {
				if(cardStatus.equalsIgnoreCase(status)){
					return true;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return false;
	}
	
	/**
	 * Check status.
	 *
	 * @param cardStatus the card status
	 * @return true, if successful
	 */
	public boolean checkStatus(String cardStatus){
		try{
			for (String status : status) {
				if(cardStatus.equalsIgnoreCase(status)){
					return true;
				}
			}
		}catch(Exception e){
			LOGGER.trace(ERROR,e);
		}
		return false;
	}
}
