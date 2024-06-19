package com.currenciesdirect.gtg.compliance.compliancesrv.core;

import java.sql.Timestamp;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;

/**
 * The Interface IRegistrationDBService.
 */
public interface IRegistrationDBService {

	/**
	 * Gets the account id by crm account id.
	 *
	 * @param crmAccountId the crm account id
	 * @return the account id by crm account id
	 * @throws ComplianceException the compliance exception
	 */
	Integer getAccountIdByCrmAccountId(String crmAccountId) throws ComplianceException;

	/**
	 * Gets the contact from contact attribute.
	 *
	 * @param contactId the contact id
	 * @return the contact from contact attribute
	 * @throws ComplianceException the compliance exception
	 */
	Contact getContactFromContactAttribute(Integer contactId) throws ComplianceException;

	/**
	 * Gets the account from account attribute.
	 *
	 * @param accountId the account id
	 * @return the account from account attribute
	 * @throws ComplianceException the compliance exception
	 */
	Account getAccountFromAccountAttribute(Integer accountId) throws ComplianceException;

	/**
	 * Checks if is accountexist.
	 *
	 * @param accountID the account ID
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isAccountexist(String accountID) throws ComplianceException;

	/**
	 * Checks if is contactexist.
	 *
	 * @param contactID the contact ID
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isContactexist(String contactID) throws ComplianceException;

	/**
	 * Checks if is contact exist by DB id.
	 *
	 * @param contactId the contact id
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isContactExistByDBId(Integer contactId) throws ComplianceException;

	/**
	 * Checks if is account exist by DB id.
	 *
	 * @param accountId the account id
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isAccountExistByDBId(Integer accountId) throws ComplianceException;

	/**
	 * Check country enum.
	 *
	 * @param country the country
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean checkCountryEnum(String country) throws ComplianceException;

	/**
	 * Gets the account version.
	 *
	 * @param accountId the account id
	 * @return the account version
	 * @throws ComplianceException the compliance exception
	 */
	Integer getAccountVersion(String accountId) throws ComplianceException;

	/**
	 * Gets the contact version.
	 *
	 * @param contactId the contact id
	 * @return the contact version
	 * @throws ComplianceException the compliance exception
	 */
	Integer getContactVersion(String contactId) throws ComplianceException;

	/**
	 * Gets the registration request by contact id.
	 *
	 * @param entityId the entity id
	 * @param accountId the account id
	 * @return the registration request by contact id
	 * @throws ComplianceException the compliance exception
	 */
	RegistrationServiceRequest getRegistrationRequestByContactId(Integer entityId, Integer accountId)
			throws ComplianceException;

	/**
	 * Gets the organisation ID.
	 *
	 * @param orgCode the org code
	 * @return the organisation ID
	 * @throws ComplianceException the compliance exception
	 */
	Integer getOrganisationID(String orgCode) throws ComplianceException;

	/**
	 * Gets the registration date time.
	 *
	 * @param accId the acc id
	 * @return the registration date time
	 * @throws ComplianceException the compliance exception
	 */
	Timestamp getRegistrationDateTime(Integer accId) throws ComplianceException;

	/**
	 * Gets the account details.
	 *
	 * @param orgCode the org code
	 * @param buyCurrency the buy currency
	 * @param sellCurrency the sell currency
	 * @param countryName the country name
	 * @param accountSfId the account sf id
	 * @param contactSFIds the contact SF ids
	 * @return the account details
	 * @throws ComplianceException the compliance exception
	 */
	RegistrationServiceRequest getAccountDetails(String orgCode, String buyCurrency, String sellCurrency,
			String countryName, String accountSfId, List<String> contactSFIds) throws ComplianceException;

	/**
	 * Gets the first sanction summary.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @return the first sanction summary
	 * @throws ComplianceException the compliance exception
	 */
	SanctionSummary getFirstSanctionSummary(Integer entityId, String entityType) throws ComplianceException;

	/**
	 * Checks if is trade account id exist.
	 *
	 * @param accountID the account ID
	 * @return the boolean
	 * @throws ComplianceException the compliance exception
	 */
	Boolean isTradeAccountIdExist(Integer accountID) throws ComplianceException;

	/**
	 * Gets the registration request by account ID.
	 *
	 * @param entityId the entity id
	 * @param accountId the account id
	 * @param orgCode the org code
	 * @return the registration request by account ID
	 * @throws ComplianceException the compliance exception
	 */
	RegistrationServiceRequest getRegistrationRequestByAccountID(Integer entityId, Integer accountId, String orgCode)
			throws ComplianceException;

	/**
	 * Gets the first fraugster signup summary.
	 *
	 * @param entityId the entity id
	 * @param entityType the entity type
	 * @return the first fraugster signup summary
	 * @throws ComplianceException the compliance exception
	 */
	FraugsterSummary getFirstFraugsterSignupSummary(Integer entityId, String entityType) throws ComplianceException;

	/**
	 * Checks if is account present by trade account id and org id.
	 *
	 * @param tradeAccountId the trade account id
	 * @param orgCode the org code
	 * @return true, if is account present by trade account id and org id
	 * @throws ComplianceException the compliance exception
	 */
	boolean isAccountPresentByTradeAccountIdAndOrgId(int tradeAccountId, String orgCode) throws ComplianceException;

	/**
	 *  Method added to fetch User ID by SSOUserId from user table - Vishal J.
	 *
	 * @param ssoUserID the sso user ID
	 * @return the user ID from SSO user ID
	 * @throws ComplianceException the compliance exception
	 */
	Integer getUserIDFromSSOUserID(String ssoUserID) throws ComplianceException;

	/**
	 * Gets the existing account contact id.
	 *
	 * @param orgCode the org code
	 * @param accountSfId the account sf id
	 * @param tradeAccountId the trade account id
	 * @param tradeAccountNumber the trade account number
	 * @param tradeContactIds the trade contact ids
	 * @param contactSFIds the contact SF ids
	 * @param details the details
	 * @return the existing account contact id
	 * @throws ComplianceException the compliance exception
	 */
	Boolean getExistingAccountContactId(String orgCode, String accountSfId, Integer tradeAccountId,
			String tradeAccountNumber, List<Integer> tradeContactIds, List<String> contactSFIds,
			RegistrationServiceRequest details) throws ComplianceException;

	/**
	 * Gets the internal rule service contact status.
	 *
	 * @param contactid the contactid
	 * @param request the request
	 * @return the internal rule service contact status
	 * @throws ComplianceException the compliance exception
	 */
	RegistrationServiceRequest getInternalRuleServiceContactStatus(Integer contactid,
			RegistrationServiceRequest request) throws ComplianceException;
	
	/**
	 * Gets the old organisation ID.
	 *
	 * @param accountId the account id
	 * @return the old organisation ID
	 * @throws ComplianceException the compliance exception
	 */
	Integer getOldOrganisationID(Integer accountId) throws ComplianceException;

}
