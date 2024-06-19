package com.currenciesdirect.gtg.compliance.transformer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Company;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.RiskProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsSummary;
import com.currenciesdirect.gtg.compliance.core.domain.AccountWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.BaseDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CountryRiskLevelEnum;
import com.currenciesdirect.gtg.compliance.core.domain.EuPoiCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.Intuition;
import com.currenciesdirect.gtg.compliance.core.domain.IntuitionPaymentResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCompany;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationContact;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCorperateCompliance;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationRiskProfile;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.VelocityCheck;
import com.currenciesdirect.gtg.compliance.core.domain.WatchListData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.CountryCheckSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.CustomCheckSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutPaymentReference;
import com.currenciesdirect.gtg.compliance.core.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class BaseDetailsTransformer.
 */
public class BaseDetailsTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(BaseDetailsTransformer.class);

	/**
	 * Gets the contact details.
	 *
	 * @param baseDBDto
	 *            the base DB dto
	 * @return Contact
	 */
	protected ContactWrapper getContactDetails(BaseDBDto baseDBDto) {
		RegistrationContact regContact = JsonConverterUtil.convertToObject(RegistrationContact.class,
				baseDBDto.getContactAttib());
		ContactWrapper contact = new ContactWrapper();
		if (regContact != null) {
			contact.setAddress(getCompleteAddress(regContact));
			contact.setCountryOfResidence(regContact.getCountry());
			contact.setEmail(regContact.getEmail());
			contact.setMobile(regContact.getPhoneMobile());
			contact.setNationality(regContact.getNationality());
			contact.setOccupation(regContact.getOccupation());
			contact.setPhone(regContact.getPhoneHome());
			contact.setDob(DateTimeFormatter.dateFormat(regContact.getDob()));
			updateWorkPhone(regContact, contact);

			// changes done to show full name on UI
			if (regContact.getMiddleName() != null && !regContact.getMiddleName().trim().isEmpty()) {
				String fullName = new StringBuilder().append(regContact.getFirstName())
						.append(" ").append(regContact.getMiddleName()).append(" ").append(regContact.getLastName()).toString();
				contact.setName(fullName);
			} else {
				contact.setName(baseDBDto.getContactName());
			}
			contact.setIsUsClient(regContact.getCountry() != null && "USA".equals(regContact.getCountry()));
		}
		if (baseDBDto.getRegIn() != null) {
			contact.setRegIn(DateTimeFormatter.dateTimeFormatter(baseDBDto.getRegIn()));
		}
		if (baseDBDto.getRegComp() != null) {
			contact.setRegComplete(DateTimeFormatter.dateTimeFormatter(baseDBDto.getRegComp()));
		} else {
			contact.setRegComplete(Constants.DASH_UI);
		}
		contact.setAccountId(baseDBDto.getAccountId());
		contact.setId(baseDBDto.getContactId());
		contact.setTradeContactID(Integer.valueOf(baseDBDto.getTradeContactId()));
		contact.setCrmAccountId(baseDBDto.getCrmAccountId());
		contact.setCrmContactId(baseDBDto.getCrmContactId());
		contact.setComplianceStatus(baseDBDto.getConComplianceStatus());
		contact.setNationalityFullName(baseDBDto.getNationalityFullName());

		return contact;
	}

  private void updateWorkPhone(RegistrationContact regContact, ContactWrapper contact) {
    if ((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn()))
    		&& (!StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
    	contact.setPhoneWork(regContact.getPhoneWork() + ";" + regContact.getPhoneWorkExtn());

    if ((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn()))
    		&& (StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
    	contact.setPhoneWork(regContact.getPhoneWorkExtn());

    if ((StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn()))
    		&& (!StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
    	contact.setPhoneWork(regContact.getPhoneWork());
  }

	/**
	 * Gets the account details.
	 *
	 * @param baseDBDto
	 *            the base DB dto
	 * @return Account
	 */
	protected AccountWrapper getAccountDetails(BaseDBDto baseDBDto) {
		RegistrationAccount regAccount = JsonConverterUtil.convertToObject(RegistrationAccount.class,
				baseDBDto.getAccAttrib());
		AccountWrapper account = new AccountWrapper();
		if (regAccount != null) {
			account.setClientType(regAccount.getCustType());
			account.setCurrencyPair(regAccount.getSellingCurrency() + "-" + regAccount.getBuyingCurrency());
			account.setEstimTransValue(regAccount.getValueOfTransaction());
			account.setPurposeOfTran(regAccount.getPurposeOfTran());
			account.setServiceRequired(regAccount.getServiceRequired());
			account.setSource(regAccount.getSource());
			account.setSourceOfFund(regAccount.getSourceOfFund());
			account.setRefferalText(regAccount.getReferralText());
			account.setAffiliateName(regAccount.getAffiliateName());
			account.setName(regAccount.getAccountName());

			if (("CFX").equals(regAccount.getCustType())) {
				account.setCompany(getCompany(regAccount.getCompany()));
				account.setCorperateCompliance(getCorporateCompliance(regAccount.getCorperateCompliance()));
				account.setRiskProfile(getRiskProfile(regAccount.getRiskProfile()));
				account.setWebsite(regAccount.getWebsite());
				if (null != regAccount.getTurnover() && !regAccount.getTurnover().isEmpty()) {
					account.setAnnualFXRequirement(StringUtils.getNumberFormat(regAccount.getTurnover()));
				}
			}
		}
		account.setComplianceStatus(baseDBDto.getAccComplianceStatus());
		account.setId(baseDBDto.getAccountId());
		account.setOrgCode(baseDBDto.getOrgCode());
		account.setTradeAccountNumber(baseDBDto.getTradeAccountNum());
		return account;
	}

	/**
	 * Delete duplicate watclist.
	 *
	 * @param watchList
	 *            the watch list
	 * @return watchList
	 */
	protected Watchlist deleteDuplicateWatclist(Watchlist watchList) {
		TreeMap<String, WatchListData> reasonMap = new TreeMap<>();
		ArrayList<WatchListData> watchListDataList = new ArrayList<>();

		for (WatchListData watch : watchList.getWatchlistData()) {
			reasonMap.put(watch.getName(), watch);
		}
		watchListDataList.addAll(reasonMap.values());
		watchList.setWatchlistData(watchListDataList);
		return watchList;
	}

	/**
	 * Gets the fraugster.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the fraugster
	 */
	protected Fraugster getFraugster(BaseDBDto detailsDBDto) {
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDtos().get("FRAUGSTERCONTACT");
		Fraugster fraugster = new Fraugster();
		Integer passCount = 0;
		Integer failCount = 0;
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			fraugster.setFraugsterTotalRecords(0);
			return fraugster;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);

		if (eventDBDto == null) {
			fraugster.setFraugsterTotalRecords(0);
			return fraugster;
		}
		if (eventDBDto.getUpdatedOn() != null) {
			fraugster.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		}
		fraugster.setUpdatedBy(eventDBDto.getUpdatedBy());

		FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
				eventDBDto.getSummary());

		if (fraugsterSummary != null) {
			if (!Constants.NOT_REQUIRED.equalsIgnoreCase(eventDBDto.getStatus())) {
				boolean status = fraugsterSummary.getStatus() != null
						&& Constants.PASS.equals(fraugsterSummary.getStatus());
				if (status) {
					passCount = eventDBDtos.size();
				} else {
					failCount = eventDBDtos.size();
				}
				fraugster.setPassCount(passCount);
				
				fraugster.setFailCount(failCount);
				
				fraugster.setStatus(status);
				
				fraugster.setStatusValue(eventDBDto.getStatus());
				
				fraugster.setIsRequired(Boolean.TRUE);
			} else {
				fraugster.setIsRequired(Boolean.FALSE);
				fraugster.setStatusValue(Constants.NOT_REQUIRED);
			}
			fraugster.setFraugsterId(fraugsterSummary.getFrgTransId());
			fraugster.setScore(fraugsterSummary.getScore());

		}
		fraugster.setId(eventDBDto.getId());
		fraugster.setFraugsterTotalRecords(eventDBDtos.size());
		return fraugster;
	}

	/**
	 * Gets the blacklist.
	 *
	 * @param eventDBDto
	 *            the event DB dto
	 * @return the blacklist
	 */
	/*
	 * 1) We are checking whether blacklist is required to perform or not first,
	 * if it is 'NOT REQUIRED' then we will show that status on UI
	 */
	protected Blacklist getBlacklist(EventDBDto eventDBDto) {
		Boolean blacklistSummaryPhone = Boolean.TRUE;
		Blacklist blacklist = new Blacklist();
		BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class,
				eventDBDto.getSummary());
		if (blacklistSummary == null) {
			return blacklist;
		}

		// If blacklist status is other than 'NOT REQUIRED' then get details
		if (!Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getStatus())) {
			blacklistSummary.setEmail(blacklistSummary.getEmail());
			blacklistSummary.setIp(blacklistSummary.getIp());
			blacklistSummary.setPhone(blacklistSummary.getPhone() == null ? blacklistSummaryPhone : blacklistSummary.getPhone());
			blacklistSummary.setAccountNumber(blacklistSummary.getAccountNumber());
			blacklistSummary.setName(blacklistSummary.getName());
			blacklistSummary.setBankName(blacklistSummary.getBankName());

			if (Constants.SERVICE_FAILURE.equalsIgnoreCase(blacklistSummary.getStatus())) {
				setServiceFailureResponseForBlacklist(blacklist);
				blacklist.setStatusValue(blacklistSummary.getStatus());
			} else {
				updateBlackListForNotRequired(eventDBDto, blacklist, blacklistSummary);
			}
		} else {
			// passing status value ('NOT_REQUIRED') to UI
			blacklist.setIsRequired(Boolean.FALSE);
			blacklist.setStatusValue(Constants.NOT_REQUIRED_UI);
		}

		setBlacklistedMatchedData(blacklistSummary, blacklist);

		return blacklist;

	}

  private void updateBlackListForNotRequired(EventDBDto eventDBDto, Blacklist blacklist,
      BlacklistSummary blacklistSummary) {
    blacklist.setEmail(blacklistSummary.getEmail());
    blacklist.setIp(blacklistSummary.getIp());
    blacklist.setPhone(!blacklistSummary.getPhone());
    blacklist.setStatus(Constants.PASS.equals(blacklistSummary.getStatus()));
    blacklist.setId(eventDBDto.getId());
    blacklist.setEntityType(eventDBDto.getEitityType());
    if (Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getAccountNumber()))
    	blacklist.setAccountNumber(Constants.NOT_REQUIRED_UI);
    else
    	blacklist.setAccountNumber(blacklistSummary.getAccountNumber());
    if (Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getName()))
    	blacklist.setName(Constants.NOT_REQUIRED_UI);
    else
    	blacklist.setName(blacklistSummary.getName());
    if (Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getBankName()))
    	blacklist.setBankName(Constants.NOT_REQUIRED_UI);
    else
    	blacklist.setBankName(blacklistSummary.getBankName());

    blacklist.setIsRequired(Boolean.TRUE);
    setBeneficiaryBlacklistPassAndFailCount(blacklist);
    blacklist.setStatusValue(blacklistSummary.getStatus());
  }

	/**
	 * Sets the service failure response for blacklist.
	 *
	 * @param blacklist
	 * the new service failure response for blacklist
	 * changes made for blacklist repeat check.
	 * Name, bank name and account number is set to Not Available if service status is failure.
	 */
	private void setServiceFailureResponseForBlacklist(Blacklist blacklist) {
		blacklist.setName(Constants.NOT_AVAILABLE);
		blacklist.setBankName(Constants.NOT_AVAILABLE);
		blacklist.setEmail(Constants.BLACKLISTED_TRUE);
		blacklist.setDomain(Constants.BLACKLISTED_TRUE);
		blacklist.setIp(Constants.BLACKLISTED_TRUE);
		blacklist.setWebSite(Constants.BLACKLISTED_TRUE);
		blacklist.setAccountNumber(Constants.NOT_AVAILABLE);
		blacklist.setPhone(Boolean.FALSE);
		blacklist.setStatus(Boolean.FALSE);
		blacklist.setIsRequired(Boolean.TRUE);
	}

	/**
	 * Sets the beneficiary blacklist pass and fail count.
	 *
	 * @param blacklist
	 *            the new beneficiary blacklist pass and fail count
	 */
	protected void setBeneficiaryBlacklistPassAndFailCount(Blacklist blacklist) {
		Integer passCount = 0;
		Integer failCount = 0;

		/**
		 * Changes done to get count since name field is made as string Vishal J
		 */
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getName())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getName())) {
			failCount++;
		}
		/**
		 * Changes done to get count since acc_no field is made as string Vishal
		 * J
		 */
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getAccountNumber())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getAccountNumber())) {
			failCount++;
		}

		if (Constants.FALSE.equalsIgnoreCase(blacklist.getBankName())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getBankName())) {
			failCount++;
		}
		blacklist.setPassCount(passCount);
		blacklist.setFailCount(failCount);
	}

	/**
	 * Gets the contact sanction.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the contact sanction
	 */
	protected Sanction getContactSanction(BaseDBDto detailsDBDto) {
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.SANCTIONCONTACT);
		Sanction sanction = new Sanction();
		int passCount = 0;
		int failCount = 0;
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			sanction.setSanctionTotalRecords(0);
			sanction.setEntityId(detailsDBDto.getContactId().toString());
			sanction.setEntityType(Constants.CONTACT);
			return sanction;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);
		sanction = getSanction(eventDBDto);
		if (Boolean.TRUE.equals(sanction.getStatus())) {
			passCount = eventDBDtos.size();
		} else {
			failCount = eventDBDtos.size();
		}
		sanction.setFailCount(failCount);
		sanction.setPassCount(passCount);
		sanction.setSanctionTotalRecords(eventDBDtos.size());
		return sanction;

	}

	/**
	 * Gets the sanction.
	 *
	 * @param eventDBDto
	 *            the event DB dto
	 * @return the sanction
	 */
	/*
	 * 1) We are checking whether blacklist is required to perform or not first,
	 * if it is 'NOT REQUIRED' then we will show that status on UI
	 * 
	 * changes done by Vishal J
	 */
	protected Sanction getSanction(EventDBDto eventDBDto) {
		Sanction sanction = new Sanction();
		SanctionSummary summary = JsonConverterUtil.convertToObject(SanctionSummary.class, eventDBDto.getSummary());
		sanction.setEventServiceLogId(eventDBDto.getId());
		sanction.setEntityType(eventDBDto.getEitityType());

		if (summary != null) {
			// If sanction status is other than 'NOT REQUIRED' then get details
			if (!Constants.NOT_REQUIRED.equalsIgnoreCase(summary.getStatus())) {
				// constant added by Vishal J
				boolean status = eventDBDto.getStatus() != null
						&& Constants.PASS.equalsIgnoreCase(eventDBDto.getStatus());
				sanction.setStatus(status);
				sanction.setStatusValue(eventDBDto.getStatus());
				sanction.setIsRequired(Boolean.TRUE);
			} else {
				// passing status value ('NOT_REQUIRED') to UI
				sanction.setIsRequired(Boolean.FALSE);
				sanction.setStatusValue(Constants.NOT_REQUIRED_UI);
				sanction.setStatus(Boolean.FALSE);
			}
			// this method is extracted for code re-usability
			setSanctionResponse(eventDBDto, sanction, summary);

		}
		return sanction;
	}

	/**
	 * Sets the sanction response.
	 *
	 * @param eventDBDto
	 *            the event DB dto
	 * @param sanction
	 *            the sanction
	 * @param summary
	 *            the summary
	 */
	// method extracted to reduce cyclomatic complexity and for code re
	// usability
	private void setSanctionResponse(EventDBDto eventDBDto, Sanction sanction, SanctionSummary summary) {
		sanction.setWorldCheck(summary.getWorldCheck());
		sanction.setSanctionId(summary.getSanctionId());
		sanction.setOfacList(summary.getOfacList());
		sanction.setEventServiceLogId(eventDBDto.getId());
		sanction.setEntityType(eventDBDto.getEitityType());
		sanction.setEntityId(eventDBDto.getEntityId());
		sanction.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		sanction.setUpdatedBy(eventDBDto.getUpdatedBy());
	}

	/**
	 * Gets the payment out custom check.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the payment out custom check
	 */
	/*
	 * 1) We are checking whether custom check is required to perform or not
	 * first, if it is 'NOT REQUIRED' then we will show that status on UI
	 * Changes done by Vishal J
	 */
	protected PaymentOutCustomCheck getPaymentOutCustomCheck(BaseDBDto detailsDBDto) {
		// constant added by Vishal J
		List<EventDBDto> customCheckEventDBDtos = detailsDBDto.getEventDBDtos()
				.get(Constants.VELOCITY_CHECK_BENEFICIARY);
		PaymentOutCustomCheck customCheck = new PaymentOutCustomCheck();
		VelocityCheck velocityCheck = new VelocityCheck();
		WhitelistCheck whitelistCheck = new WhitelistCheck();
		EuPoiCheck euPoiCheck = new EuPoiCheck();
		customCheck.setVelocityCheck(velocityCheck);
		customCheck.setWhiteListCheck(whitelistCheck);
		customCheck.setEuPoiCheck(euPoiCheck);
		if (customCheckEventDBDtos == null || customCheckEventDBDtos.isEmpty()) {
			customCheck.setTotalRecords(0);
			return customCheck;
		}
		EventDBDto eventDBDto = customCheckEventDBDtos.get(0);
		Integer passCount = 0;
		Integer failCount = 0;
		if (Constants.PASS.equalsIgnoreCase(eventDBDto.getStatus())) {
			passCount = customCheckEventDBDtos.size();
		} else {
			failCount = customCheckEventDBDtos.size();
		}
		customCheck.setPassCount(passCount);
		customCheck.setFailCount(failCount);

		if (eventDBDto.getUpdatedOn() != null) {
			customCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		}

		if (eventDBDto.getSummary() != null) {
			CustomCheckSummary customCheckSummary = JsonConverterUtil.convertToObject(CustomCheckSummary.class,
					eventDBDto.getSummary());

			// If Custom check status is other than 'NOT REQUIRED' then get
			// details
			if (!Constants.NOT_REQUIRED.equalsIgnoreCase(customCheckSummary.getOverallStatus())) {
				if (customCheckSummary.getVelocityCheck() != null) {
					setBeneCheckStatus(velocityCheck, customCheckSummary);
					setNoOfTransactionsCheckStatus(velocityCheck, customCheckSummary);
					setVelocityAmountCheckStatus(velocityCheck, customCheckSummary);
					velocityCheck.setStatus(customCheckSummary.getVelocityCheck().getStatus());
				}
				if (customCheckSummary.getWhiteListCheck() != null) {
					String amountrangeStatus = customCheckSummary.getWhiteListCheck().getAmoutRange();
					setAmountRangeStatus(whitelistCheck, customCheckSummary, amountrangeStatus);
					whitelistCheck.setCurrency(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getWhiteListCheck().getCurrency()));
					checkAndSetReasonofTransfer(whitelistCheck, customCheckSummary);

					whitelistCheck.setThirdParty(customCheckSummary.getWhiteListCheck().getThirdParty());
				}
			
				setEuPoiCheckStatus(euPoiCheck, customCheckSummary);
				customCheck.setEuPoiCheck(euPoiCheck);
				customCheck.setFraudPredictStatus(customCheckSummary.getFraudPredictStatus());//Add for AT-3161 only for PFX
				customCheck.setIsRequired(Boolean.TRUE);
				customCheck.setStatus(eventDBDto.getStatus());
			} else {
				// passing status value ('NOT_REQUIRED') to UI
				customCheck.setIsRequired(Boolean.FALSE);
				velocityCheck.setBeneCheck(Constants.NOT_REQUIRED_UI);
				velocityCheck.setNoOffundsoutTxn(Constants.NOT_REQUIRED_UI);
				velocityCheck.setPermittedAmoutcheck(Constants.NOT_REQUIRED_UI);
				whitelistCheck.setAmoutRange(Constants.NOT_REQUIRED_UI);
				whitelistCheck.setCurrency(Constants.NOT_REQUIRED_UI);
				whitelistCheck.setReasonOfTransfer(Constants.NOT_REQUIRED_UI);
				customCheck.setFraudPredictStatus(Constants.NOT_REQUIRED_UI);//Add for AT-3161 only for PFX
				customCheckSummary.setOverallStatus(Constants.NOT_REQUIRED_UI);
				customCheck.setStatus(Constants.NOT_REQUIRED_UI);
				euPoiCheck.setStatus(Constants.NOT_REQUIRED_UI);//Add for AT-3349
				customCheck.setEuPoiCheck(euPoiCheck);
			}
		}
		customCheck.setTotalRecords(customCheckEventDBDtos.size());
		customCheck.setEntityType(eventDBDto.getEitityType());
		customCheck.setId(eventDBDto.getId());
		return customCheck;
	}

	private void setEuPoiCheckStatus(EuPoiCheck euPoiCheck, CustomCheckSummary customCheckSummary) {
		if (customCheckSummary.getEuPoiCheck()!= null) {
			euPoiCheck.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getEuPoiCheck().getStatus()));// AT-3349
		} 
		else
		{
			euPoiCheck.setStatus("- - -");
		}
	}

	/**
	 * Check and set reasonof transfer.
	 *
	 * @param whitelistCheck
	 *            the whitelist check
	 * @param customCheckSummary
	 *            the custom check summary
	 */
	private void checkAndSetReasonofTransfer(WhitelistCheck whitelistCheck, CustomCheckSummary customCheckSummary) {
		if (Constants.NOT_REQUIRED.equalsIgnoreCase(customCheckSummary.getWhiteListCheck().getReasonOfTransfer())) {
			whitelistCheck.setReasonOfTransfer(Constants.NOT_REQUIRED_UI);
		} else {
			whitelistCheck.setReasonOfTransfer(customCheckSummary.getWhiteListCheck().getReasonOfTransfer());
		}
	}

	/**
	 * Sets the no of transactions check status.
	 *
	 * @param velocityCheck
	 *            the velocity check
	 * @param customCheckSummary
	 *            the custom check summary
	 */
	private void setNoOfTransactionsCheckStatus(VelocityCheck velocityCheck, CustomCheckSummary customCheckSummary) {
		if (Constants.FAIL.equals(customCheckSummary.getVelocityCheck().getNoOffundsoutTxn())) {
			velocityCheck.setNoOffundsoutTxn(customCheckSummary.getVelocityCheck().getNoOffundsoutTxn() + " "
					+ customCheckSummary.getVelocityCheck().getNoOfFundsOutTxnDetails());
		} else {
			velocityCheck.setNoOffundsoutTxn(customCheckSummary.getVelocityCheck().getNoOffundsoutTxn());
		}
	}

	/**
	 * Sets the bene check status.
	 *
	 * @param velocityCheck
	 *            the velocity check
	 * @param customCheckSummary
	 *            the custom check summary
	 */
	private void setBeneCheckStatus(VelocityCheck velocityCheck, CustomCheckSummary customCheckSummary) {
		String beneCheckStatus = customCheckSummary.getVelocityCheck().getBeneCheck();
		if (Constants.FAIL.equalsIgnoreCase(beneCheckStatus)) {
			String[] tradeAccountNumber;
			velocityCheck.setBeneCheck(customCheckSummary.getVelocityCheck().getBeneCheck() + "(With account :");
			tradeAccountNumber = (customCheckSummary.getVelocityCheck().getMatchedAccNumber()).split(",");
			velocityCheck.setBeneTradeAccountid(Arrays.asList(tradeAccountNumber));

		} else {
			velocityCheck.setBeneCheck(customCheckSummary.getVelocityCheck().getBeneCheck());
		}
	}

	/**
	 * Sets the velocity amount check status.
	 *
	 * @param velocityCheck
	 *            the velocity check
	 * @param customCheckSummary
	 *            the custom check summary
	 */
	private void setVelocityAmountCheckStatus(VelocityCheck velocityCheck, CustomCheckSummary customCheckSummary) {
		String permittedAmountCheckStatus = customCheckSummary.getVelocityCheck().getPermittedAmoutcheck();
		if (Constants.FAIL.equalsIgnoreCase(permittedAmountCheckStatus)
				&& customCheckSummary.getVelocityCheck().getMaxAmount() != null) {
			velocityCheck.setPermittedAmoutcheck(customCheckSummary.getVelocityCheck().getPermittedAmoutcheck()
					+ "(Max:"
					+ StringUtils.getNumberFormat(customCheckSummary.getVelocityCheck().getMaxAmount().toString())
					+ ")");
		} else {
			velocityCheck.setPermittedAmoutcheck(customCheckSummary.getVelocityCheck().getPermittedAmoutcheck());
		}
	}

	/**
	 * Sets the amount range status.
	 *
	 * @param whitelistCheck
	 *            the whitelist check
	 * @param customCheckSummary
	 *            the custom check summary
	 * @param amountrangeStatus
	 *            the amountrange status
	 */
	protected void setAmountRangeStatus(WhitelistCheck whitelistCheck, CustomCheckSummary customCheckSummary,
			String amountrangeStatus) {
		if (Constants.FAIL.equalsIgnoreCase(amountrangeStatus)) {
			if (null != customCheckSummary.getWhiteListCheck().getMaxAmount()) {
				String amountRange = StringUtils
						.getNumberFormat(customCheckSummary.getWhiteListCheck().getMaxAmount().toString());
				whitelistCheck.setAmoutRange(
						customCheckSummary.getWhiteListCheck().getAmoutRange() + " (Max:" + amountRange + ")");
			} else {
				whitelistCheck.setAmoutRange(customCheckSummary.getWhiteListCheck().getAmoutRange() + " (Max:----)");
			}
		} else {
			whitelistCheck.setAmoutRange(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getWhiteListCheck().getAmoutRange()));
		}
	}

	/**
	 * Gets the company.
	 *
	 * @param comanyDetails
	 *            the comany details
	 * @return Company
	 */
	private Company getCompany(RegistrationCompany comanyDetails) {
		Company company = new Company();

		company.setBillingAddress(comanyDetails.getBillingAddress());
		company.setCcj(comanyDetails.getCcj());
		company.setCompanyPhone(comanyDetails.getCompanyPhone());
		company.setCorporateType(comanyDetails.getCorporateType());
		company.setCountryOfEstablishment(comanyDetails.getCountryOfEstablishment());
		company.setCountryRegion(comanyDetails.getCountryRegion());
		company.setEstNoTransactionsPcm(comanyDetails.getEstNoTransactionsPcm());
		company.setEtailer(comanyDetails.getEtailer());
		company.setIndustry(comanyDetails.getIndustry());
		company.setOption(comanyDetails.getOption());
		company.setRegistrationNo(comanyDetails.getRegistrationNo());
		company.setShippingAddress(comanyDetails.getShippingAddress());
		company.setTypeOfFinancialAccount(comanyDetails.getTypeOfFinancialAccount());
		company.setVatNo(comanyDetails.getVatNo());

		try {
			company.setIncorporationDate(DateTimeFormatter.dateFormat(comanyDetails.getIncorporationDate()));
			company.setOngoingDueDiligenceDate(
					DateTimeFormatter.dateFormat(comanyDetails.getOngoingDueDiligenceDate()));
			company.settAndcSignedDate(DateTimeFormatter.dateFormat(comanyDetails.gettAndcSignedDate()));
		} catch (Exception ex) {
			LOG.debug("exception in company data:", ex);
		}
		return company;
	}

	/**
	 * Gets the risk profile.
	 *
	 * @param riskProfileDetails
	 *            the risk profile details
	 * @return the risk profile
	 */
	private RiskProfile getRiskProfile(RegistrationRiskProfile riskProfileDetails) {
		RiskProfile riskProfile = new RiskProfile();

		riskProfile.setCountryRiskIndicator(riskProfileDetails.getCountryRiskIndicator());
		riskProfile.setDelinquencyFailureScore(riskProfileDetails.getDelinquencyFailureScore());
		riskProfile.setFailureScore(riskProfileDetails.getFailureScore());
		riskProfile.setRiskDirection(riskProfileDetails.getRiskDirection());
		riskProfile.setRiskTrend(riskProfileDetails.getRiskTrend());
		riskProfile.setUpdatedRisk(riskProfileDetails.getUpdatedRisk());
		// newly added Dnb DATA fir riskprofile added by neelesh pant
		riskProfile.setContinent(riskProfileDetails.getContinent());
		riskProfile.setCountry(riskProfileDetails.getCountry());
		riskProfile.setStateCountry(riskProfileDetails.getStateCountry());
		riskProfile.setDunsNumber(riskProfileDetails.getDunsNumber());
		riskProfile.setTradingStyles(riskProfileDetails.getTradingStyles());
		riskProfile.setUs1987PrimarySic4Digit(riskProfileDetails.getUs1987PrimarySic4Digit());
		riskProfile.setFinancialFiguresMonth(riskProfileDetails.getFinancialFiguresMonth());
		riskProfile.setFinancialFiguresYear(riskProfileDetails.getFinancialFiguresYear());
		riskProfile.setFinancialYearEndDate(riskProfileDetails.getFinancialYearEndDate());
		riskProfile.setAnnualSales(riskProfileDetails.getAnnualSales());
		riskProfile.setModelledAnnualSales(riskProfileDetails.getModelledAnnualSales());
		riskProfile.setNetWorthAmount(riskProfileDetails.getNetWorthAmount());
		riskProfile.setModelledNetWorth(riskProfileDetails.getModelledNetWorth());
		riskProfile.setLocationType(riskProfileDetails.getLocationType());
		riskProfile.setImportExportIndicator(riskProfileDetails.getImportExportIndicator());
		riskProfile.setDomesticUltimateRecord(riskProfileDetails.getDomesticUltimateRecord());
		riskProfile.setGlobalUltimateRecord(riskProfileDetails.getGlobalUltimateRecord());
		riskProfile.setGroupStructureNumberOfLevels(riskProfileDetails.getGroupStructureNumberOfLevels());
		riskProfile.setHeadquarterDetails(riskProfileDetails.getHeadquarterDetails());
		riskProfile.setImmediateParentDetails(riskProfileDetails.getImmediateParentDetails());
		riskProfile.setDomesticUltimateParentDetails(riskProfileDetails.getDomesticUltimateParentDetails());
		riskProfile.setGlobalUltimateParentDetails(riskProfileDetails.getGlobalUltimateParentDetails());
		riskProfile.setCreditLimit(riskProfileDetails.getCreditLimit());
		riskProfile.setRiskRating(riskProfileDetails.getRiskRating());
		riskProfile.setProfitLoss(riskProfileDetails.getProfitLoss());
		// newly added Dnb data ends
		return riskProfile;
	}

	/**
	 * Gets the corporate compliance.
	 *
	 * @param corpComplianceDetails
	 *            the corp compliance details
	 * @return the corporate compliance
	 */
	private CorperateCompliance getCorporateCompliance(RegistrationCorperateCompliance corpComplianceDetails) {
		CorperateCompliance corporateCompliance = new CorperateCompliance();

		corporateCompliance.setFixedAssets(corpComplianceDetails.getFixedAssets());
		corporateCompliance.setForeignOwnedCompany(corpComplianceDetails.getForeignOwnedCompany());
		corporateCompliance.setFormerName(corpComplianceDetails.getFormerName());
		corporateCompliance.setLegalForm(corpComplianceDetails.getLegalForm());
		corporateCompliance.setNetWorth(corpComplianceDetails.getNetWorth());
		corporateCompliance.setRegistrationNumber(corpComplianceDetails.getRegistrationNumber());
		corporateCompliance.setSic(corpComplianceDetails.getSic());
		corporateCompliance.setSicDesc(corpComplianceDetails.getSicDesc());
		corporateCompliance.setTotalLiabilitiesAndEquities(corpComplianceDetails.getTotalLiabilitiesAndEquities());
		corporateCompliance.setTotalShareHolders(corpComplianceDetails.getTotalShareHolders());
		// newly added DNb DATA for Corporate compliance added by neelesh pant
		corporateCompliance.setGlobalUltimateDuns(corpComplianceDetails.getGlobalUltimateDuns());
		corporateCompliance.setGlobalUltimateName(corpComplianceDetails.getGlobalUltimateName());
		corporateCompliance.setGlobalUltimateCountry(corpComplianceDetails.getGlobalUltimateCountry());
		corporateCompliance.setRegistrationDate(corpComplianceDetails.getRegistrationDate());
		corporateCompliance.setMatchName(corpComplianceDetails.getMatchName());
		corporateCompliance.setIsoCountryCode2Digit(corpComplianceDetails.getIsoCountryCode2Digit());
		corporateCompliance.setIsoCountryCode3Digit(corpComplianceDetails.getIsoCountryCode2Digit());
		corporateCompliance.setStatementDate(corpComplianceDetails.getStatementDate());
		corporateCompliance.setGrossIncome(corpComplianceDetails.getGrossIncome());
		corporateCompliance.setNetIncome(corpComplianceDetails.getNetIncome());
		corporateCompliance.setTotalCurrentAssets(corpComplianceDetails.getTotalCurrentAssets());
		corporateCompliance.setTotalAssets(corpComplianceDetails.getTotalAssets());
		corporateCompliance.setTotalLongTermLiabilities(corpComplianceDetails.getTotalLongTermLiabilities());
		corporateCompliance.setTotalCurrentLiabilities(corpComplianceDetails.getTotalCurrentLiabilities());
		corporateCompliance.setTotalMatchedShareholders(corpComplianceDetails.getTotalMatchedShareholders());
		corporateCompliance.setFinancialStrength(corpComplianceDetails.getFinancialStrength());
		// newly added DnB data ends

		return corporateCompliance;
	}

	/**
	 * Gets the country check summary.
	 *
	 * @param eventDBDto
	 *            the event DB dto
	 * @return the country check summary
	 */
	protected CountryCheck getCountryCheckSummary(EventDBDto eventDBDto) {
		CountryCheck countryCheck = new CountryCheck();
		CountryCheckSummary countryCheckSummary = JsonConverterUtil.convertToObject(CountryCheckSummary.class,
				eventDBDto.getSummary());
		if (countryCheckSummary == null)
			return countryCheck;

		countryCheck.setCountry(countryCheckSummary.getCountry());
		if (countryCheckSummary.getRiskLevel() != null) {
			countryCheck.setRiskLevel(
					"(" + CountryRiskLevelEnum.valueOf(countryCheckSummary.getRiskLevel()).getRiskLevel() + ")");
		}

		return countryCheck;
	}

	/**
	 * Sets the blacklisted matched data.
	 *
	 * @param blacklistSummary
	 *            the blacklist summary
	 * @param blacklist
	 *            the blacklist
	 */
	protected void setBlacklistedMatchedData(BlacklistSummary blacklistSummary, Blacklist blacklist) {

		if (null != blacklistSummary) {
			blacklist.setAccNumberMatchedData(blacklistSummary.getAccNumberMatchedData());
			blacklist.setDomainMatchedData(blacklistSummary.getDomainMatchedData());
			blacklist.setEmailMatchedData(blacklistSummary.getEmailMatchedData());
			blacklist.setIpMatchedData(blacklistSummary.getIpMatchedData());
			blacklist.setNameMatchedData(blacklistSummary.getNameMatchedData());
			blacklist.setBankNameMatchedData(blacklistSummary.getBankNameMatchedData());
			blacklist.setPhoneMatchedData(blacklistSummary.getPhoneMatchedData());
			blacklist.setWebsiteMatchedData(blacklistSummary.getWebsiteMatchedData());
		}

	}

	/**
	 * this function gives complete address as per input fields.
	 *
	 * @author abhijeetg
	 * @param regContact
	 *            the reg contact
	 * @return the complete address
	 */
	protected String getCompleteAddress(RegistrationContact regContact) {
		List<String> list = new ArrayList<>();
		if (!isNullOrEmpty(regContact.getAddress1())) {
			list.add(regContact.getAddress1());
		}
		if (!isNullOrEmpty(regContact.getUnitNumber())) {
			list.add(regContact.getUnitNumber());
		}
		if (!isNullOrEmpty(regContact.getStreetNumber())) {
			list.add(regContact.getStreetNumber());
		}
		if (!isNullOrEmpty(regContact.getAreaNumber())) {
			list.add(regContact.getAreaNumber());
		}
		if (!isNullOrEmpty(regContact.getCivicNumber())) {
			list.add(regContact.getCivicNumber());
		}
		if (!isNullOrEmpty(regContact.getDistrict())) {
			list.add(regContact.getDistrict());
		}
		if (!isNullOrEmpty(regContact.getState())) {
			list.add(regContact.getState());
		}
		if (!isNullOrEmpty(regContact.getCity())) {
			list.add(regContact.getCity());
		}
		if (!isNullOrEmpty(regContact.getPostCode())) {
			list.add(regContact.getPostCode());
		}
		if (!isNullOrEmpty(regContact.getCountry())) {
			list.add(regContact.getCountry());
		}
		return String.join(", ", list);
	}

	/**
	 * Checks if is null or empty.
	 *
	 * @param str
	 *            the str
	 * @return true, if is null or empty
	 */
	protected static boolean isNullOrEmpty(String str) {
		boolean result = true;
		if (str != null && !str.trim().isEmpty())
			return false;

		return result;
	}
	
	protected IntuitionPaymentResponse getIntuition(BaseDBDto detailsDBDto) {
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.TRANSACTION_MONITORINGACCOUNT);
		IntuitionPaymentResponse intuition = new IntuitionPaymentResponse();
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			intuition.setIntuitionTotalRecords(0);
			return intuition;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);
		if (eventDBDto == null) {
			intuition.setIntuitionTotalRecords(0);
			return intuition;
		}
		intuition.setIntuitionTotalRecords(1);

		TransactionMonitoringPaymentsSummary intuitionSummary = JsonConverterUtil
				.convertToObject(TransactionMonitoringPaymentsSummary.class, eventDBDto.getSummary());
		
		String currentAction = eventDBDto.getIntuitionCurrentAction();
		
		checkForNotPerformed(eventDBDtos, intuition, intuitionSummary, currentAction);
		return intuition;
	}
	
	private void checkForNotPerformed(List<EventDBDto> eventDBDtos, IntuitionPaymentResponse intuition,
			TransactionMonitoringPaymentsSummary intuitionSummary, String currentAction) {
			EventDBDto eventDBDto = eventDBDtos.get(0);
			Integer passCount = 0;
			Integer failCount = 0;

			intuition.setCorrelationId(Constants.DASH_SINGLE_DETAILS);
			intuition.setRuleRiskLevel(Constants.DASH_SINGLE_DETAILS);
			intuition.setClientRiskLevel(Constants.DASH_SINGLE_DETAILS);
			intuition.setUpdatedBy("system");

			if (intuitionSummary != null) {
				if (!Constants.NOT_PERFORMED.equalsIgnoreCase(intuitionSummary.getStatus())
						&& !Constants.NOT_REQUIRED.equalsIgnoreCase(intuitionSummary.getStatus())) {
					boolean status = intuitionSummary.getStatus() != null
							&& (Constants.CLEAR.equalsIgnoreCase(intuitionSummary.getStatus())
									|| Constants.CLEAN.equals(intuitionSummary.getStatus()));
					boolean action = intuitionSummary.getAction() != null
							&& (Constants.CLEAR.equalsIgnoreCase(intuitionSummary.getAction())
									|| Constants.CLEAN.equals(intuitionSummary.getAction()));
					
					if (status || action) {
						passCount = eventDBDtos.size();
					} else {
						failCount = eventDBDtos.size();
					}

					setIntuitionPaymentsResponse(intuition, intuitionSummary);

				} else if (Constants.NOT_REQUIRED.equalsIgnoreCase(intuitionSummary.getStatus())) {
					intuition.setIsRequired(Boolean.FALSE);
					intuition.setStatus(Constants.NOT_REQUIRED);
					intuition.setDecision(Constants.NOT_REQUIRED);//Add for AT-5028
				} else if (Constants.NOT_PERFORMED.equalsIgnoreCase(intuitionSummary.getStatus())) {
					intuition.setIsRequired(Boolean.FALSE);
					intuition.setStatus(Constants.NOT_PERFORMED);
					intuition.setDecision(Constants.NOT_PERFORMED);//Add for AT-5028
				}
			}else {
				intuition.setIsRequired(Boolean.FALSE);
				intuition.setStatus(Constants.NOT_PERFORMED);
				intuition.setDecision(Constants.NOT_PERFORMED);//Add for AT-5028
			}

			if (eventDBDto.getUpdatedOn() != null) {
				intuition.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}
			intuition.setPassCount(passCount);
			intuition.setFailCount(failCount);
			intuition.setId(eventDBDto.getId());
			intuition.setIntuitionTotalRecords(eventDBDtos.size());
			intuition.setCurrentAction(currentAction); //AT-4962

		  }

	private void setIntuitionPaymentsResponse(IntuitionPaymentResponse intuition,
			TransactionMonitoringPaymentsSummary intuitionSummary) {
		if (null != intuitionSummary.getCorrelationId()) {
			intuition.setCorrelationId(intuitionSummary.getCorrelationId());
		}

		if (null != intuitionSummary.getRuleScore()) {
			intuition.setRuleScore(intuitionSummary.getRuleScore());
		}

		if (null != intuitionSummary.getRuleRiskLevel()) {
			intuition.setRuleRiskLevel(intuitionSummary.getRuleRiskLevel());
		}

		if (null != intuitionSummary.getClientRiskScore()) {
			intuition.setClientRuleScore(intuitionSummary.getClientRiskScore());
		}

		if (null != intuitionSummary.getClientRiskLevel()) {
			intuition.setClientRiskLevel(intuitionSummary.getClientRiskLevel());
		}

		intuition.setStatus(intuitionSummary.getStatus());
		//Add for AT-5028
		if (intuitionSummary.getAction() == null && (intuitionSummary.getStatus().equalsIgnoreCase(Constants.CLEAN)
				|| intuitionSummary.getStatus().equalsIgnoreCase(Constants.CLEAR))) {
			intuition.setDecision(intuitionSummary.getStatus());
		} else if (intuitionSummary.getAction() == null && intuitionSummary.getStatus().equalsIgnoreCase(Constants.HOLD)) {
			intuition.setDecision(intuitionSummary.getStatus());
		} else if (intuitionSummary.getAction() != null
				&& (intuitionSummary.getAction().equalsIgnoreCase(Constants.CLEAR)
						|| intuitionSummary.getAction().equalsIgnoreCase(Constants.CLEAN))
				&& intuitionSummary.getStatus().equalsIgnoreCase(Constants.HOLD)) {
			intuition.setDecision(intuitionSummary.getAction());
		} else if (intuitionSummary.getAction() != null && (intuitionSummary.getAction().equalsIgnoreCase(Constants.REJECT)
				|| intuitionSummary.getAction().equalsIgnoreCase(Constants.SEIZE))
				&& intuitionSummary.getStatus().equalsIgnoreCase(Constants.HOLD)) {
			intuition.setDecision(intuitionSummary.getAction());
		}	
		
		intuition.setUpdatedBy(intuitionSummary.getUserId());
		if(intuitionSummary.getUserId() == null) {
			intuition.setUpdatedBy("system");
		}
		intuition.setIsRequired(Boolean.TRUE);
	}
}
