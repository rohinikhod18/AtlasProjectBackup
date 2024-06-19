package com.currenciesdirect.gtg.compliance.transformer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.onfido.OnfidoSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.AccountWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.GlobalCheck;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.Intuition;
import com.currenciesdirect.gtg.compliance.core.domain.IpCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.Onfido;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationContact;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.IpSummary;
import com.currenciesdirect.gtg.compliance.core.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;




/**
 * The Class RegistrationDetailsTransformer.
 */
@Component("registrationDetailsTransformer")
public class RegistrationDetailsTransformer extends AbstractRegistrationTransformer implements ITransform<RegistrationDetailsDto, RegistrationDetailsDBDto> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.currenciesdirect.gtg.compliance.core.ITransform#transform(java.lang.
	 * Object)
	 */
	@Override
	public RegistrationDetailsDto transform(RegistrationDetailsDBDto detailsDBDto) {
		RegistrationDetailsDto regDetailsDto = new RegistrationDetailsDto();
		regDetailsDto.setAccount(getAccount(detailsDBDto));
		regDetailsDto.setCurrentContact(getContact(detailsDBDto));
		regDetailsDto.setInternalRule(getInternalRule(detailsDBDto));
		regDetailsDto.setKyc(getKyc(detailsDBDto));
		regDetailsDto.setSanction(getSanction(detailsDBDto));
		regDetailsDto.setFraugster(getFraugster(detailsDBDto));
		regDetailsDto.setIntuition(getIntuition(detailsDBDto)); //AT-4114
		regDetailsDto.setOnfido(getOnfido(detailsDBDto));
		regDetailsDto.setOtherContacts(detailsDBDto.getOtherContacts());
		regDetailsDto.setPaginationDetails(detailsDBDto.getPaginationDetails());
		detailsDBDto.getActivityLogs().setTotalRecords(detailsDBDto.getActivityLogs().getActivityLogData().size());
		if(detailsDBDto.getActivityLogs().getActivityLogData().size() <= 10){
			regDetailsDto.setActivityLogs(detailsDBDto.getActivityLogs());
		} else {
			detailsDBDto.getActivityLogs().setActivityLogData(detailsDBDto.getActivityLogs().getActivityLogData().subList(0, 10));
			regDetailsDto.setActivityLogs(detailsDBDto.getActivityLogs());
		}
		Watchlist watchlist = deleteDuplicateWatclist(detailsDBDto.getWatachList());
		regDetailsDto.setWatchlist(watchlist);
		regDetailsDto.setStatusReason(detailsDBDto.getContactStatusReason());
		if(detailsDBDto.getLockedBy() != null && !detailsDBDto.getLockedBy().isEmpty()){
			regDetailsDto.setLocked(Boolean.TRUE);
			regDetailsDto.setLockedBy(detailsDBDto.getLockedBy());
			regDetailsDto.setUserResourceId(detailsDBDto.getUserResourceId());
		}
		regDetailsDto.setDocumentList(detailsDBDto.getDocumentList());
		regDetailsDto.setAlertComplianceLog(detailsDBDto.getAlertComplianceLog());
		regDetailsDto.setIsOnQueue(detailsDBDto.getIsOnQueue());
		regDetailsDto.setDataAnonStatus(detailsDBDto.getDataAnonStatus());
		regDetailsDto.setPoiExists(detailsDBDto.getPoiExists());//AT-3391
		
		//Added for AT-4190
		if(detailsDBDto.getIntuitionRiskLevel() != null) {
			regDetailsDto.setIntuitionRiskLevel(detailsDBDto.getIntuitionRiskLevel());
		}
		regDetailsDto.setAccountVersion(detailsDBDto.getAccountVersion());
		return regDetailsDto;
	}

	/**
	 * Gets the account.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the account
	 */
	private AccountWrapper getAccount(RegistrationDetailsDBDto detailsDBDto) {
		AccountWrapper account = new AccountWrapper();
		RegistrationAccount regAccount = JsonConverterUtil.convertToObject(RegistrationAccount.class,
				detailsDBDto.getAccountAttrib());
		if (regAccount != null) {
			account.setClientType(regAccount.getCustType());
			account.setCurrencyPair(regAccount.getSellingCurrency() + "  -  " + regAccount.getBuyingCurrency());
			account.setEstimTransValue(regAccount.getValueOfTransaction());
			account.setPurposeOfTran(regAccount.getPurposeOfTran());
			account.setServiceRequired(regAccount.getServiceRequired());
			account.setSource(regAccount.getSource());
			account.setSourceOfFund(regAccount.getSourceOfFund());
			account.setRefferalText(regAccount.getReferralText());
			account.setAffiliateName(regAccount.getAffiliateName());
			account.setRegMode(regAccount.getRegistrationMode());
			account.setConversionPrediction(regAccount.getConversionPrediction());
		}
		account.setAccountTMFlag(detailsDBDto.getAccountTMFlag());
		account.setComplianceStatus(detailsDBDto.getAccComplianceStatus());
		account.setId(detailsDBDto.getAccountId());
		account.setOrgCode(detailsDBDto.getOrgnization());
		account.setTradeAccountNumber(detailsDBDto.getTradeAccountNum());
		if(null != detailsDBDto.getRegCompAccount()){
			account.setRegCompleteAccount(detailsDBDto.getRegCompAccount().toString());
		}else {
			account.setRegCompleteAccount(Constants.DASH_UI);
		}
		if(null != detailsDBDto.getRegistrationInDate()){
			account.setRegistrationInDate(detailsDBDto.getRegistrationInDate().toString());
		}
		if(null != detailsDBDto.getComplianceExpiry()){
			account.setComplianceExpiry(detailsDBDto.getComplianceExpiry().toString());
		}else {
			account.setComplianceExpiry(null);
		}
		if(null != detailsDBDto.getLegalEntity() && !detailsDBDto.getLegalEntity().isEmpty())
			account.setLegalEntity(detailsDBDto.getLegalEntity());
		else account.setLegalEntity(Constants.DASH_UI);
		
		return account;

	}

	/**
	 * Gets the contact.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the contact
	 */
	private ContactWrapper getContact(RegistrationDetailsDBDto detailsDBDto) {
		RegistrationContact regContact = JsonConverterUtil.convertToObject(RegistrationContact.class,
				detailsDBDto.getContactAttrib());
		ContactWrapper contact = new ContactWrapper();
		if (regContact != null) {
			contact.setAddress(getCompleteAddress(regContact));
			contact.setCountryOfResidence(detailsDBDto.getCountryOfResidenceFullName()+" ("+regContact.getCountry()+")");
			contact.setEmail(regContact.getEmail());
			contact.setMobile(regContact.getPhoneMobile());
			contact.setNationality(regContact.getNationality());
			contact.setOccupation(regContact.getOccupation());
			contact.setPhone(regContact.getPhoneHome());
			contact.setIsUsClient(regContact.getCountry() != null && Constants.USA.equals(regContact.getCountry()));
			contact.setIpAddress(regContact.getIpAddress());
			contact.setIsCountrySupported(checkIsKycSupportedCountry(regContact.getCountry(),detailsDBDto.getKycSupportedCountryList(),
					detailsDBDto.getOrgnization(),detailsDBDto.getLegalEntity()));
			contact.setPrimaryContact(regContact.getPrimaryContact());
			//added By Neelesh Pant
			contact.setDob(DateTimeFormatter.getDateInRFC3339(regContact.getDob()));
			updateWorkPhone(regContact, contact);
	
			//changes done to show full name on UI
			if(regContact.getMiddleName() != null && !regContact.getMiddleName().trim().isEmpty()){
				String fullName = new StringBuilder().append(regContact.getFirstName()).append(" ").append(regContact.getMiddleName()).append(" ").append(regContact.getLastName()).toString();
				contact.setName(fullName);
			} else {
				contact.setName(detailsDBDto.getContactName());
			}
				
			
		}
		if (detailsDBDto.getRegIn() != null) {
			contact.setRegIn(DateTimeFormatter.dateTimeFormatter(detailsDBDto.getRegIn()));
		}
		if (detailsDBDto.getRegComp() != null) {
			contact.setRegComplete(DateTimeFormatter.dateTimeFormatter(detailsDBDto.getRegComp()));
		}
		contact.setAccountId(detailsDBDto.getAccountId());
		contact.setId(detailsDBDto.getContactId());
		contact.setTradeContactID(Integer.valueOf(detailsDBDto.getTradeContactId()));
		contact.setCrmAccountId(detailsDBDto.getCrmAccountId());
		contact.setCrmContactId(detailsDBDto.getCrmContactId());
		contact.setComplianceStatus(detailsDBDto.getConComplianceStatus());
		return contact;

	}

  private void updateWorkPhone(RegistrationContact regContact, ContactWrapper contact) {
	 if((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn())) && (!StringUtils.isNullOrEmpty(regContact.getPhoneWork()))) {
	    	contact.setPhoneWork(regContact.getPhoneWork()+";"+regContact.getPhoneWorkExtn());
	 }
    	
     if((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn())) && (StringUtils.isNullOrEmpty(regContact.getPhoneWork()))) {
    	contact.setPhoneWork(regContact.getPhoneWorkExtn());
     }
    
     if((StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn()))&& (!StringUtils.isNullOrEmpty(regContact.getPhoneWork()))) {
    	contact.setPhoneWork(regContact.getPhoneWork());
     }
  }

	/**
	 * Gets the internal rule.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the internal rule
	 */
	private InternalRule getInternalRule(RegistrationDetailsDBDto detailsDBDto) {
		InternalRule internalRule = new InternalRule();
		internalRule.setCustomCheck(getCustomCheck(detailsDBDto));
		internalRule.setBlacklist(getBlacklist(detailsDBDto));
		return internalRule;

	}
	
	private CustomCheck getCustomCheck(RegistrationDetailsDBDto detailsDBDto){
		CustomCheck customCheck = new CustomCheck();
		customCheck.setIpCheck(getIp(detailsDBDto));
		customCheck.setGlobalCheck(getGlobalCheck(detailsDBDto));
		customCheck.setCountryCheck(getCountryCheck(detailsDBDto));
		
		customCheck.setCustomCheckTotalRecords(1);
		
		if((null != detailsDBDto.getCustomCheckStatus() || !detailsDBDto.getCustomCheckStatus().isEmpty() )
				&& (detailsDBDto.getCustomCheckStatus().equalsIgnoreCase(Constants.PASS) || 
				detailsDBDto.getCustomCheckStatus().equalsIgnoreCase(Constants.NOT_REQUIRED_UI))){
			customCheck.setPassCount(customCheck.getCustomCheckTotalRecords());	
		}
		else
			customCheck.setFailCount(customCheck.getCustomCheckTotalRecords());
		return customCheck;
	}


	/**
	 * Gets the kyc.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the kyc
	 */
	private Kyc getKyc(RegistrationDetailsDBDto detailsDBDto) {
		List<EventDBDto> kycEventDBDtos = detailsDBDto.getEventDBDto().get(Constants.KYC);
		Integer passCount = 0;
		Integer failCount = 0;
		Kyc kyc = new Kyc();
		if (kycEventDBDtos == null || kycEventDBDtos.isEmpty()) {
			kyc.setKycTotalRecords(0);
			return kyc;
		}
		EventDBDto kycEventDBDto = kycEventDBDtos.get(0);
		KYCSummary kycSummary = JsonConverterUtil.convertToObject(KYCSummary.class, kycEventDBDto.getSummary());
		if (kycSummary == null) {
			kyc.setFailCount(kycEventDBDtos.size());
			return kyc;
		}
		
		//condition added by Vishal J to check whether we should do KYC check for pfx contact
		/*
		 * 1) We are checking status of response from KYC service if it is 'NOT_REQUIRED' 
		 *    then we are showing that on UI with other important fields also.
		 */
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(kycSummary.getStatus()))
		{
			boolean status = Constants.PASS.equals(kycSummary.getStatus());
			kyc.setStatus(status);
			kyc.setEidCheck(kycSummary.getEidCheck());			
			kyc.setIsRequired(Boolean.TRUE);
			/**if (null != kycSummary.getEidCheck()  && kycSummary.getEidCheck()) {
				passCount++;
			} else {
				failCount++;
			}*/
			if (status) {
				passCount= kycEventDBDtos.size();
			} else {
				failCount = kycEventDBDtos.size();
			}
			kyc.setPassCount(passCount);
			kyc.setFailCount(failCount);
			kyc.setKycTotalRecords(kycEventDBDtos.size());
		}
		else
		{
			//sending status value to UI
			kyc.setIsRequired(Boolean.FALSE);
			kyc.setStatusValue(Constants.NOT_REQUIRED_UI);	
			kyc.setKycTotalRecords(kycEventDBDtos.size());
		}
		
		//method is extracted for code re-usability
		setKycResponse(kyc, kycEventDBDto, kycSummary);		
		return kyc;

	}

	//this method is created for code re-usability from getKyc()
	private void setKycResponse(Kyc kyc, EventDBDto kycEventDBDto, KYCSummary kycSummary) {
		kyc.setCheckedOn(DateTimeFormatter.dateTimeFormatter(kycSummary.getCheckedOn()));
		kyc.setDob(kycSummary.getDob());
		kyc.setId(kycEventDBDto.getId());
		kyc.setReferenceId(kycSummary.getReferenceId());
		kyc.setVerifiactionResult(kycSummary.getVerifiactionResult());
		kyc.setPrevStatusValue(kycSummary.getStatus());
	}

	/**
	 * Gets the sanction.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the sanction
	 */
	private Sanction getSanction(RegistrationDetailsDBDto detailsDBDto) {
		Sanction sanction = new Sanction();
		Integer passCount = 0;
		Integer failCount = 0;
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDto().get(Constants.SANCTION);
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			sanction.setSanctionTotalRecords(0);
			return sanction;
		}

		EventDBDto eventDBDto = eventDBDtos.get(0);
		SanctionSummary summary = JsonConverterUtil.convertToObject(SanctionSummary.class, eventDBDto.getSummary());
		sanction.setEventServiceLogId(eventDBDto.getId());
		
		//condition added by Vishal J to check whether we should do sanction check for pfx contact
		/*
		 * 1) We are checking status of response from sanction service if it is 'NOT_REQUIRED' 
		 *    then we are showing that on UI with other important fields also.
		 */
		if (summary != null && !Constants.NOT_REQUIRED.equalsIgnoreCase(summary.getStatus())) {			
			boolean status = summary.getStatus() != null && Constants.PASS.equals(summary.getStatus());
			if (status) {
				passCount = eventDBDtos.size();
			} else {
				failCount = eventDBDtos.size();
			}			
			sanction.setStatus(status);
			sanction.setIsRequired(Boolean.TRUE);
			sanction.setFailCount(failCount);
			sanction.setPassCount(passCount);
			sanction.setSanctionTotalRecords(eventDBDtos.size());
		}
		else
		{	
			//sending status value (NOT_REQUIRED) to UI
			sanction.setIsRequired(Boolean.FALSE);
			sanction.setStatusValue(Constants.NOT_REQUIRED_UI);	
			sanction.setSanctionTotalRecords(eventDBDtos.size());
		}
		
		//method is extracted for code re-usability
		setSanctionResponse(sanction, eventDBDto, summary);		
		return sanction;

	}

	//this method is created for code re-usability from getSanction()
	private void setSanctionResponse(Sanction sanction, EventDBDto eventDBDto, SanctionSummary summary) {
		if(summary !=null ){
			sanction.setWorldCheck(summary.getWorldCheck());
			sanction.setSanctionId(summary.getSanctionId());
			sanction.setOfacList(summary.getOfacList());
			sanction.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			sanction.setUpdatedBy(eventDBDto.getUpdatedBy());
			sanction.setPrevStatusValue(summary.getStatus());
		}
	}

	/**
	 * Gets the fraugster.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the fraugster
	 */
	private Fraugster getFraugster(RegistrationDetailsDBDto detailsDBDto) {
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDto().get(Constants.FRAUGSTER);
		Fraugster fraugster = new Fraugster();
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			fraugster.setFraugsterTotalRecords(0);
			return fraugster;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);
		if (eventDBDto == null) {
			fraugster.setFraugsterTotalRecords(0);
			return fraugster;
		}
		
		FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
				eventDBDto.getSummary());
		checkForNotRequired(eventDBDtos, fraugster,  eventDBDto, fraugsterSummary);
		return fraugster;
	}
	
  private void checkForNotRequired(List<EventDBDto> eventDBDtos, Fraugster fraugster,  EventDBDto eventDBDto, FraugsterSummary fraugsterSummary) {
    //condition added by Vishal J to check whether we should do fraugster check for pfx contact
		/*
		 * 1) We are checking status of response from fraugster service if it is 'NOT_REQUIRED' 
		 *    then we are showing that on UI with other important fields also.
		 */
    Integer passCount = 0;
    Integer failCount = 0;
		if(fraugsterSummary != null && !Constants.NOT_REQUIRED.equalsIgnoreCase(fraugsterSummary.getStatus()))
		{
			if (eventDBDto.getUpdatedOn() != null) {
				fraugster.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}
			fraugster.setUpdatedBy(eventDBDto.getUpdatedBy());
	
			boolean status = fraugsterSummary.getStatus() != null && Constants.PASS.equals(fraugsterSummary.getStatus());
			if (status) {
				passCount = eventDBDtos.size();
			} else {
				failCount = eventDBDtos.size();
			}
			fraugster.setFraugsterId(fraugsterSummary.getFrgTransId());
			fraugster.setScore(fraugsterSummary.getScore());
			fraugster.setPassCount(passCount);
			fraugster.setFailCount(failCount);
			fraugster.setId(eventDBDto.getId());
			fraugster.setIsRequired(Boolean.TRUE);
			fraugster.setFraugsterTotalRecords(eventDBDtos.size());
			fraugster.setStatus(status);
			fraugster.setPrevStatusValue(fraugsterSummary.getStatus());
		}
		else
		{
			//sending status value to UI (NOT REQUIRED)
			fraugster.setIsRequired(Boolean.FALSE);
			fraugster.setStatusValue(Constants.NOT_REQUIRED_UI);
			if (eventDBDto.getUpdatedOn() != null) {
				fraugster.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}
			if (fraugsterSummary != null) {
				fraugster.setFraugsterId(fraugsterSummary.getFrgTransId());
				fraugster.setScore(fraugsterSummary.getScore());
				fraugster.setPassCount(passCount);
				fraugster.setFailCount(failCount);
				fraugster.setPrevStatusValue(fraugsterSummary.getStatus());
			}
			fraugster.setId(eventDBDto.getId());
			fraugster.setUpdatedBy(eventDBDto.getUpdatedBy());
			fraugster.setFraugsterTotalRecords(eventDBDtos.size());
		}
  }

	
	/**
	 * purpose of method:
	 * Get the contact details from EventServiceLog table and using most recent blacklisted data
	 *		to show on UI
	 * Implementation:
	 * 1) In this method we created list for blacklisted event if the list is empty or null 
	 *      then return blacklist with fail count(No. of columns)
	 * 2) If the list is present then fetch data from blacklist summary and set that data into UI perspective object Blacklist
	 */
	private Blacklist getBlacklist(RegistrationDetailsDBDto detailsDBDto) {
		//constant added by Vishal J
		List<EventDBDto> blacklistEventDBDtos = detailsDBDto.getEventDBDto().get(Constants.BLACKLIST);		
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {
			return null;
		}
		
		BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class,
				blacklistEventDBDtos.get(0).getSummary());
		if (blacklistSummary == null) {
			return blacklist;
		}
		
		
		setIntoBlacklistSummary(blacklistSummary);
		//added method as setBlacklistFromBlacklistSummary
		setBlacklistFromBlacklistSummary(blacklistEventDBDtos, blacklist, blacklistSummary);
		
		//added method to set blacklist matched data
		setBlacklistedMatchedData(blacklistSummary,blacklist);
		
		if(!blacklistSummary.getStatus().equalsIgnoreCase(Constants.NOT_REQUIRED))
			setBlacklistPassAndFailCount(blacklist);
		return blacklist;
	}

	/**
	 * Fetch Data from BlacklistSummary and set Data into Blacklist
	 */
	private void setBlacklistFromBlacklistSummary(List<EventDBDto> blacklistEventDBDtos, Blacklist blacklist,
			BlacklistSummary blacklistSummary) {
		
		//condition added by Vishal J to show 'NOT REQUIRED' status on UI
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getStatus()))
		{
			if(Constants.SERVICE_FAILURE.equalsIgnoreCase(blacklistSummary.getStatus())){
				setServiceFailureResponseForBlacklist(blacklist);
			}
			else {
				updateBlacklist(blacklistEventDBDtos, blacklist, blacklistSummary);
				}
			}
		else
		{
			//if status is 'NOT REQUIRED' then we will show that on UI for blacklist check
			blacklist.setStatusValue(Constants.NOT_REQUIRED_UI);
			blacklist.setIsRequired(Boolean.FALSE);
		}
		//end of - condition added
		
	}

  private void updateBlacklist(List<EventDBDto> blacklistEventDBDtos, Blacklist blacklist,
      BlacklistSummary blacklistSummary) {
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getName()))
    	blacklist.setName(Constants.NOT_REQUIRED_UI);
    else blacklist.setName(blacklistSummary.getName());
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getEmail()))
    	blacklist.setEmail(Constants.NOT_REQUIRED_UI);
    else blacklist.setEmail(blacklistSummary.getEmail());
    //Set Domain for blacklist check for AT-112 -Saylee
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getDomain()))
    	blacklist.setDomain(Constants.NOT_REQUIRED_UI);
    else blacklist.setDomain(blacklistSummary.getDomain());
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getIp()))
    	blacklist.setIp(Constants.NOT_REQUIRED_UI);
    else blacklist.setIp(blacklistSummary.getIp());
    blacklist.setPhone(!blacklistSummary.getPhone());
    blacklist.setStatus(Constants.PASS.equals(blacklistSummary.getStatus()));
    blacklist.setId(blacklistEventDBDtos.get(0).getId());
    blacklist.setIsRequired(Boolean.TRUE);
    blacklist.setPrevStatusValue(blacklistSummary.getStatus());
  }

	private void setServiceFailureResponseForBlacklist(Blacklist blacklist) {
		blacklist.setName(Constants.BLACKLISTED_TRUE);
		blacklist.setEmail(Constants.BLACKLISTED_TRUE);
		blacklist.setDomain(Constants.BLACKLISTED_TRUE);
		blacklist.setIp(Constants.BLACKLISTED_TRUE);
		blacklist.setPhone(Boolean.FALSE);
		blacklist.setStatus(Boolean.FALSE);
		blacklist.setIsRequired(Boolean.TRUE);
		blacklist.setPrevStatusValue(Constants.SERVICE_FAILURE);
	}

	/**
	 * set Data into BlacklistSummary
	 */
	private void setIntoBlacklistSummary(BlacklistSummary blacklistSummary) {
		Boolean blacklistSummaryPhone = Boolean.TRUE;
		blacklistSummary.setName(blacklistSummary.getName());
		blacklistSummary.setEmail(blacklistSummary.getEmail());
		blacklistSummary.setDomain(blacklistSummary.getDomain());
		blacklistSummary.setIp(blacklistSummary.getIp());
		blacklistSummary.setPhone(blacklistSummary.getPhone() == null ? blacklistSummaryPhone : blacklistSummary.getPhone());
	}


	/**
	 * Gets the ip.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the ip
	 */
	private IpCheck getIp(RegistrationDetailsDBDto detailsDBDto) {
		List<EventDBDto> ipEventDBDtos = detailsDBDto.getEventDBDto().get(Constants.IP);
		IpCheck ip = new IpCheck();
		if (ipEventDBDtos == null || ipEventDBDtos.isEmpty()) {
			ip.setIpCheckTotalRecords(0);
			return ip;
		}
		EventDBDto eventDBDto = ipEventDBDtos.get(0);
		IpSummary ipSummary = JsonConverterUtil.convertToObject(IpSummary.class, eventDBDto.getSummary());
		
		
		if (eventDBDto.getUpdatedOn() != null) {
			ip.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		}
		if (ipSummary == null) {
			return ip;
		}
		
		/*condition added by Vishal J to check the status of IP check
		* If status is 'NOT_REQUIRED' then we wil show it on UI
		*/
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(ipSummary.getStatus()))
		{
			ip.setIpAddress(ipSummary.getIpAddress());
			ip.setIpRule(ipSummary.getIpRule());
			ip.setId(eventDBDto.getId());
			ip.setStatus(eventDBDto.getStatus());			
			ip.setIsRequired(Boolean.TRUE);	
			if(ip.getStatus().equalsIgnoreCase(Constants.PASS) || ip.getStatus().equalsIgnoreCase(Constants.FAIL)){
				ip.setIpCity(Constants.IP_CITY_FIELD+StringUtils.isNullOrTrimEmptySetNA(ipSummary.getIpCity())+Constants.COMMA);
				ip.setIpCountry(Constants.IP_COUNTRY_FIELD+StringUtils.isNullOrTrimEmptySetNA(ipSummary.getIpCountry())+Constants.COMMA);
				ip.setGeoDifference(Constants.IP_DISTANCE_FIELD+StringUtils.isNullOrTrimEmptySetNA(DecimalFormatter.convertToOneDigit
				(ipSummary.getGeoDifference()))+
				StringUtils.isNullOrTrimEmptySetSpace(ipSummary.getUnit())+Constants.CLOSING_BRACKET);
			}
			
		}
		else
		{
			//sending status value (NOT_REQUIRED) on UI
			ip.setIsRequired(Boolean.FALSE);
			ip.setStatusValue(Constants.NOT_REQUIRED_UI);
		}
		ip.setIpCheckTotalRecords(ipEventDBDtos.size());
		return ip;
	}
	
	private GlobalCheck getGlobalCheck(RegistrationDetailsDBDto detailsDBDto){
		List<EventDBDto> gbEventDBDtos = detailsDBDto.getEventDBDto().get(Constants.GLOBALCHECK);
		 GlobalCheck globalCheck = new GlobalCheck();

		if (gbEventDBDtos == null || gbEventDBDtos.isEmpty()) {
			globalCheck.setGlobalCheckTotalRecords(0);
			return globalCheck;
		}
		EventDBDto eventDBDto = gbEventDBDtos.get(0);
			
			/*condition added by Vishal J to check the status of Global check
			* If status is 'NOT_REQUIRED' then we will show it on UI
			*/
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(eventDBDto.getStatus()))
		{
			globalCheck.setEntityType(eventDBDto.getEitityType());
			globalCheck.setId(eventDBDto.getId());
			globalCheck.setStatus(eventDBDto.getStatus());
				
			globalCheck.setIsRequired(Boolean.TRUE);
		}
		else
		{
				//sending status value (NOT_REQUIRED) on UI
			globalCheck.setIsRequired(Boolean.FALSE);
			globalCheck.setStatusValue(Constants.NOT_REQUIRED_UI);				
		}
		globalCheck.setGlobalCheckTotalRecords(gbEventDBDtos.size());
		return globalCheck;
	}
	
	private CountryCheck getCountryCheck(RegistrationDetailsDBDto detailsDBDto) {
		List<EventDBDto> ccEventDBDtos = detailsDBDto.getEventDBDto().get(Constants.COUNTRYCHECK);
		CountryCheck countryCheck = new CountryCheck();
		if (ccEventDBDtos == null || ccEventDBDtos.isEmpty()) {
			countryCheck.setCountryCheckTotalRecords(0);
			return countryCheck;
		}
		EventDBDto eventDBDto = ccEventDBDtos.get(0);

			/*condition added by Vishal J to check the status of Country check
			* If status is 'NOT_REQUIRED' then we will show it on UI
			*/
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(eventDBDto.getStatus()))
		{
			countryCheck.setEntityType(eventDBDto.getEitityType());
			countryCheck.setId(eventDBDto.getId());
			countryCheck.setStatus(eventDBDto.getStatus());
				
			countryCheck.setIsRequired(true);
		}
		else
		{
				//sending status value (NOT_REQUIRED) on UI
			countryCheck.setIsRequired(false);
			countryCheck.setStatusValue(Constants.NOT_REQUIRED_UI);
		}	
		countryCheck.setCountryCheckTotalRecords(ccEventDBDtos.size());
		return countryCheck;
	}
	
	/**
	 * Gets the onfido.
	 *
	 * @param detailsDBDto the details DB dto
	 * @return the onfido
	 */
	private Onfido getOnfido(RegistrationDetailsDBDto detailsDBDto) {
		Onfido onfido = new Onfido();
		Integer passCount = 0;
		Integer failCount = 0;
		
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDto().get(Constants.ONFIDO);
		
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			onfido.setOnfidoTotalRecords(0);
			return onfido;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);
		OnfidoSummary summary = JsonConverterUtil.convertToObject(OnfidoSummary.class,eventDBDto.getSummary());
		boolean status = summary.getStatus() != null && Constants.PASS.equals(summary.getStatus());
		if (status) {
			passCount = eventDBDtos.size();
		} else {
			failCount = eventDBDtos.size();
		}
		onfido.setIsRequired(Boolean.TRUE);
		if(Constants.PASS.equals(summary.getStatus())) {
			onfido.setStatus(Boolean.TRUE);
			onfido.setStatusValue(Constants.PASS);
		}
		else {
			onfido.setStatus(Boolean.FALSE);
			onfido.setStatusValue(Constants.FAIL);
		}
		onfido.setReviewed(Constants.DASH_DETAILS_PAGE);
		if(null != summary.getReviewed()) {
			onfido.setReviewed(summary.getReviewed());
		}
		onfido.setOnfidoTotalRecords(eventDBDtos.size());
		onfido.setEntityId(eventDBDto.getEntityId());
		onfido.setEventServiceLogId(eventDBDto.getId());
		onfido.setOnfidoId(summary.getOnfidoId());
		onfido.setPassCount(passCount);
		onfido.setFailCount(failCount);
		onfido.setUpdatedBy(eventDBDto.getUpdatedBy());
		onfido.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		onfido.setOnfidoReport(JsonConverterUtil.convertToJsonWithoutNull(summary.getOnfidoReport()));
		
		return onfido;
	}
	
	// AT-4114
	private Intuition getIntuition(RegistrationDetailsDBDto detailsDBDto) {
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDto().get(Constants.TRANSACTION_MONITORING);
		Intuition intuition = new Intuition();
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			intuition.setIntuitionTotalRecords(0);
			return intuition;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);
		if (eventDBDto == null) {
			intuition.setIntuitionTotalRecords(0);
			return intuition;
		}

		TransactionMonitoringAccountSummary intuitionSummary = JsonConverterUtil
				.convertToObject(TransactionMonitoringAccountSummary.class, eventDBDto.getSummary());
		checkForNotPerformed(eventDBDtos, intuition, eventDBDto, intuitionSummary);
		return intuition;
	}
	
	// AT-4114
	 private void checkForNotPerformed(List<EventDBDto> eventDBDtos, Intuition intuition,  EventDBDto eventDBDto, TransactionMonitoringAccountSummary intuitionSummary) {
		   
		    Integer passCount = 0;
		    Integer failCount = 0;
		    
		    intuition.setCorrelationId(Constants.DASH_SINGLE_DETAILS);
			intuition.setProfileScore(Constants.DASH_SINGLE_DETAILS);
			intuition.setRuleScore(Constants.DASH_SINGLE_DETAILS);
			intuition.setRiskLevel(Constants.DASH_SINGLE_DETAILS);
		    
			if (intuitionSummary != null && !Constants.NOT_PERFORMED.equalsIgnoreCase(intuitionSummary.getStatus()) && !Constants.NOT_REQUIRED.equalsIgnoreCase(intuitionSummary.getStatus())) {
				boolean status = intuitionSummary.getStatus() != null && Constants.PASS.equals(intuitionSummary.getStatus());
				if (status) {
					passCount = eventDBDtos.size();
				} else {
					failCount = eventDBDtos.size();
				}
				
			    setIntuitionSummaryDetails(intuition, intuitionSummary);
				
				intuition.setIsRequired(Boolean.TRUE);
				intuition.setStatus(status);
			}
			else if(intuitionSummary != null && Constants.NOT_REQUIRED.equalsIgnoreCase(intuitionSummary.getStatus())){
				intuition.setIsRequired(Boolean.FALSE);
				intuition.setStatusValue(Constants.NOT_REQUIRED);	
			}
			else {
				intuition.setIsRequired(Boolean.FALSE);
				intuition.setStatusValue(Constants.NOT_PERFORMED); 
			}
			
		    if (eventDBDto.getUpdatedOn() != null) {
				intuition.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}
		    intuition.setPassCount(passCount);
			intuition.setFailCount(failCount);
		    intuition.setId(eventDBDto.getId());
			intuition.setUpdatedBy(eventDBDto.getUpdatedBy());
			intuition.setIntuitionTotalRecords(eventDBDtos.size());
		    
		  }

	/**
	 * @param intuition
	 * @param intuitionSummary
	 */
	private void setIntuitionSummaryDetails(Intuition intuition, TransactionMonitoringAccountSummary intuitionSummary) {
		if(null != intuitionSummary.getCorrelationId()) {
			intuition.setCorrelationId(intuitionSummary.getCorrelationId());
		}
		
		if(null != intuitionSummary.getProfileScore()) {
			intuition.setProfileScore(intuitionSummary.getProfileScore());
		}
		
		if(null != intuitionSummary.getRuleScore()) {
			intuition.setRuleScore(intuitionSummary.getRuleScore());
		}
		
		if(null != intuitionSummary.getRiskLevel()) {
			intuition.setRiskLevel(intuitionSummary.getRiskLevel());
		}
	}
}