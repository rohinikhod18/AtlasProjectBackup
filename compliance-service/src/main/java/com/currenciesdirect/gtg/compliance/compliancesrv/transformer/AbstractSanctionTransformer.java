package com.currenciesdirect.gtg.compliance.compliancesrv.transformer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionContactResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ProvideCacheLoader;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.SanctionCategoryEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.ServiceStatus;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Company;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.EventServiceLog;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.StringUtils;

public abstract class AbstractSanctionTransformer extends AbstractTransformer{
	
	
	protected List<SanctionContactRequest> createContactRequest(String orgID, List<Contact> contacts,
			ProvideCacheLoader cacheLoader, String category) throws ComplianceException {
		List<SanctionContactRequest> contactList = new ArrayList<>();
		for (Contact contact : contacts) {
			if (contact.isSanctionEligible()) {
				String countryName = cacheLoader
						.getCountryFullName(null != contact.getCountry() ? contact.getCountry() : "");
				SanctionContactRequest sanctionContact = new SanctionContactRequest();
				if (null != countryName) {
					sanctionContact.setCountry(countryName);
				}
				sanctionContact.setDob(contact.getDob());
				sanctionContact.setFullName(contact.getFullName());
				sanctionContact.setGender(contact.getGender());
				StringBuilder sanctionID = new StringBuilder();
				sanctionID.append(orgID).append("-C-").append(StringUtils.leftPadWithZero(10, contact.getId()));
				sanctionContact.setSanctionId(sanctionID.toString());
				sanctionContact.setContactId(contact.getId());
				sanctionContact.setIsExisting(contact.isSanctionPerformed());
				sanctionContact.setCategory(category);
				contactList.add(sanctionContact);
			}
		}
		return contactList;
	}
	
	protected SanctionContactRequest createCompanyCFXRequest(String orgID, Account account,
			ProvideCacheLoader cacheLoader,String sanctionCategory) throws ComplianceException {
		SanctionContactRequest sanctionContact = null;
		Company company = account.getCompany();
		if (company.isSanctionEligible()) {
			String countryName = cacheLoader.getCountryFullName(
					null != company.getCountryOfEstablishment() ? company.getCountryOfEstablishment() : "");
			sanctionContact = new SanctionContactRequest();
			if (null != countryName) {
				sanctionContact.setCountry(countryName);
			}
			sanctionContact.setFullName(account.getAccountName());
			StringBuilder sanctionID = new StringBuilder();
			sanctionID.append(orgID).append("-A-").append(StringUtils.leftPadWithZero(10, account.getId()));
			sanctionContact.setSanctionId(sanctionID.toString());
			sanctionContact.setContactId(account.getId());
			sanctionContact.setIsExisting(company.isSanctionPerformed());
			sanctionContact.setCategory(sanctionCategory);

		}
		return sanctionContact;
	}
	
	protected List<SanctionContactResponse> createContactResponse(List<Contact> contacts) {
		
		return createContactResponse(contacts,ServiceStatus.NOT_PERFORMED);
	}
	
	protected List<SanctionContactResponse> createContactResponse(List<Contact> contacts,ServiceStatus status) {
		List<SanctionContactResponse> contactResponses = new ArrayList<>();
		for (Contact contact : contacts) {
			SanctionContactResponse response = new SanctionContactResponse();
			/**Update Account set status to NOT_REQUIRED when contact.isSanctionEligible() is False : Laxmi B*/
			if(contact.isSanctionEligible()){
				response.setStatus(status.name());
			} else {
				response.setStatus(ServiceStatus.NOT_REQUIRED.name());
			}
			/**************************************************************/
			response.setContactId(contact.getId());
			contactResponses.add(response);
		}
		return contactResponses;
	}
	
	protected SanctionContactResponse createCFXResponse(Account account,ServiceStatus status) {
		
		SanctionContactResponse response = new SanctionContactResponse();
		response.setContactId(account.getId());
		response.setStatus(status.name());
		
	return response;
}
	
	protected SanctionSummary createSanctionSummary(String ofacList, String worldCheck, String sanctionID, String status) {
		SanctionSummary sanctionSummary = new SanctionSummary();
		sanctionSummary.setOfacList(ofacList);
		sanctionSummary.setWorldCheck(worldCheck);
		sanctionSummary.setSanctionId(sanctionID);
		sanctionSummary.setStatus(status);
		sanctionSummary.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
		return sanctionSummary;
	}

	protected void updateResponseInEventLog(EventServiceLog eventServiceLog, String providerResponse, String status, 
			SanctionSummary sanctionSummary) {
		eventServiceLog.setProviderResponse(providerResponse);
		eventServiceLog.setStatus(status);
		eventServiceLog.setUpdatedOn(new Timestamp(System.currentTimeMillis()));
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(sanctionSummary));
		eventServiceLog.setEventServiceLogId(eventServiceLog.getEventServiceLogId());
	}

	protected static String leftPadWithZero(Integer id){
		String pad = "0000000000";
		return (pad+id).substring((pad+id).length()-10);
	}
	
	protected void createEventServiceLogForServiceFailure(EventServiceLog eventServiceLog, Object response ){
		createEventServiceLogOnFailure(eventServiceLog, response);
	}

	protected void createEventServiceLogOnFailure(EventServiceLog eventServiceLog, Object response) {
		SanctionSummary sanctionSummery = new SanctionSummary();
		sanctionSummery.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		sanctionSummery.setWorldCheck(Constants.NOT_AVAILABLE);
		sanctionSummery.setOfacList(Constants.NOT_AVAILABLE);
		sanctionSummery.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(sanctionSummery));
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(response));
	}
	
	protected void createEventServiceLogForServiceFailureWithSanctionID(EventServiceLog eventServiceLog, Object response, String sanctionID ){
		SanctionSummary sanctionSummery = new SanctionSummary();
		sanctionSummery.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		sanctionSummery.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());
		sanctionSummery.setWorldCheck(Constants.NOT_AVAILABLE);
		sanctionSummery.setOfacList(Constants.NOT_AVAILABLE);
		sanctionSummery.setSanctionId(sanctionID);
		eventServiceLog.setStatus(ServiceStatus.SERVICE_FAILURE.name());
		eventServiceLog.setSummary(JsonConverterUtil.convertToJsonWithoutNull(sanctionSummery));
		eventServiceLog.setProviderResponse(JsonConverterUtil.convertToJsonWithoutNull(response));
	}
	
	/**
	 * create sanction contact response
	 * @author abhijeetg
	 * @param contact
	 * @param status
	 * @return
	 */
	protected SanctionContactResponse createContactResponse(Contact contact,ServiceStatus status,String orgID) {
		String sanctionID = createReferenceId(orgID,Constants.ENTITY_TYPE_CONTACT,contact.getId()+"");
			SanctionContactResponse response = new SanctionContactResponse();
			response.setContactId(contact.getId());
			response.setSanctionId(sanctionID);
			response.setStatus(status.name());
		return response;
	}
	
	/**
	 * Gets the account sanction category.
	 *
	 * @return the account sanction category
	 */
	protected String getAccountSanctionCategory(){
		return SanctionCategoryEnum.ORGANIZATION.getValue();
	}
	
	/**
	 * Gets the contact sanction category.
	 *
	 * @return the contact sanction category
	 */
	protected String getContactSanctionCategory(){
		return SanctionCategoryEnum.INDIVIDUAL.getValue();
	}
	
	/**
	 * Gets the bank sanction category.
	 *
	 * @return the bank sanction category
	 */
	protected String getBankSanctionCategory(){
		return SanctionCategoryEnum.ORGANIZATION.getValue();
	}
	
	/**
	 * Gets the beneficiary sanction category.
	 *
	 * @param beneficiaryType the beneficiary type
	 * @return the beneficiary sanction category
	 */
	protected String getBeneficiarySanctionCategory(String beneficiaryType){
		
		if(StringUtils.isNullOrTrimEmpty(beneficiaryType)  
				|| beneficiaryType.equalsIgnoreCase(SanctionCategoryEnum.INDIVIDUAL.getValue())){
			return SanctionCategoryEnum.INDIVIDUAL.getValue();
		}
		return SanctionCategoryEnum.ORGANIZATION.getValue();	
	}
	
	protected String getDebtorSanctionCategory(){
		return SanctionCategoryEnum.INDIVIDUAL.getValue();
	}
	
}
