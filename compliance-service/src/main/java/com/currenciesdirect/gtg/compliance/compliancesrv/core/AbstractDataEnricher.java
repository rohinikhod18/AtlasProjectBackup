package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.msg.ServiceMessage;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.MessageContextHeaders;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.fundsin.FundsInReasonCode;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.AbstractDao;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.UserProfile;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;

/**
 * The Class AbstractDataEnricher.
 */
public abstract class AbstractDataEnricher extends AbstractDao {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDataEnricher.class);
	
	/** The new registration DB service impl. */
	@Autowired
	protected NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;
	
	/** The user id cache. */
	private ProvideCacheLoader providerCacheLoader = ProvideCacheLoader.getInstance();

	/**
	 * Business -- 
	 * Check Account status  on the basis of customer type 
	 * and as per previous status decided to perform further checks. 
	 * for customer type "PFX" check account and contact status and
	 * for customer type "CFX" check only account status.
	 * 
	 * Implementation---
	 * 1) If customer type is "PFX" check account and contact status.
	 * 		1.a) if both account and contact status are "INACTIVE"  then set "ATT_DO_PERFORM_OTHER_CHECKS" flag to false.
	 * 		1.b) if both account and contact status are "ACTIVE"  then set "ATT_DO_PERFORM_OTHER_CHECKS" flag to true.
	 * 2) If customer type is "CFX" check account status.
	 *   	2.a) if account is "INACTIVE" then set "ATT_DO_PERFORM_OTHER_CHECKS" flag to false.
	 * 		2.b) if account is  "ACTIVE"  then set "ATT_DO_PERFORM_OTHER_CHECKS" flag to true.
	 * 
	 * @param request
	 * @param account
	 * @param contact
	 */
	protected void checkAccountContactStatus(ServiceMessage request, Account account, Contact contact) {
		try {
			if (account.getCustType().equalsIgnoreCase(Constants.PFX)) {
				if (ContactComplianceStatus.INACTIVE.name().equals(account.getAccountStatus())
						&& ContactComplianceStatus.INACTIVE.name().equals(contact.getContactStatus())) {
					request.addAttribute(Constants.PERFORM_CHECKS, Boolean.FALSE);
					request.addAttribute(Constants.NOT_PERFORM_CHECKS_REASON,
							FundsInReasonCode.INACTIVE_CUSTOMER.getReasonDescription());
				} else {
					request.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
				}
			} else {
				if (ContactComplianceStatus.INACTIVE.name().equals(account.getAccountStatus())) {
					request.addAttribute(Constants.PERFORM_CHECKS, Boolean.FALSE);
					request.addAttribute(Constants.NOT_PERFORM_CHECKS_REASON,
							FundsInReasonCode.INACTIVE_CUSTOMER.getReasonDescription());
				} else {
					request.addAttribute(Constants.PERFORM_CHECKS, Boolean.TRUE);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error while checking account and contact status :: checkAccountContactStatus() :: ", e);
		}
	}
	
	/**
	 * Gets the user table id.
	 *
	 * @param message the message
	 * @return the user table id
	 * @throws ComplianceException the compliance exception
	 */
	public void getUserTableId(Message<MessageContext> message) throws ComplianceException{
		UserProfile token = (UserProfile) message.getHeaders().get(MessageContextHeaders.GATEWAY_USER);
		//added under AT-2248-unrelated to any jira
		Integer userID = providerCacheLoader.getUserIdFromCache(token.getPreferredUserName());
		if(null == userID)
			userID = 1;
		token.setUserID(userID);
	}
	
	/**
	 * AT-3346
	 * Gets the country edd number.
	 *
	 * @param countryCode the country code
	 * @return the country edd number
	 * @throws ComplianceException the compliance exception
	 */
	public Integer getCountryEddNumber(String countryCode) throws ComplianceException {
		Integer eddNumber = providerCacheLoader.getCountryEddNumber(countryCode);
		if(null == eddNumber)
			eddNumber = 2;
		return eddNumber;
	}
}