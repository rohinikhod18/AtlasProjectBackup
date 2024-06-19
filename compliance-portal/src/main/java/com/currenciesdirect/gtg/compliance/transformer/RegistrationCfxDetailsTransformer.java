package com.currenciesdirect.gtg.compliance.transformer;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.AccountWrapper;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.commons.domain.fraugster.FraugsterSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Company;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactWrapper;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Fraugster;
import com.currenciesdirect.gtg.compliance.core.domain.FraugsterDetails;
import com.currenciesdirect.gtg.compliance.core.domain.InternalRule;
import com.currenciesdirect.gtg.compliance.core.domain.Intuition;
import com.currenciesdirect.gtg.compliance.core.domain.IntuitionDetails;
import com.currenciesdirect.gtg.compliance.core.domain.Kyc;
import com.currenciesdirect.gtg.compliance.core.domain.KycDetails;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCfxDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCompany;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationContact;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCorperateCompliance;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationRiskProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.RiskProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountSummary;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringContactSummary;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.SanctionDetails;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReason;
import com.currenciesdirect.gtg.compliance.core.domain.StatusReasonData;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.core.domain.kyc.KYCSummary;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationCfxContactDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationCfxDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationDetailsDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.sanction.SanctionSummary;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class RegistrationCfxDetailsTransformer.
 */
@Component("registrationCfxDetailsTransformer")
public class RegistrationCfxDetailsTransformer extends AbstractRegistrationTransformer
		implements ITransform<RegistrationCfxDetailsDto, RegistrationCfxDetailsDBDto> {

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.ITransform#transform(java.lang.Object)
	 */
	@Override
	public RegistrationCfxDetailsDto transform(RegistrationCfxDetailsDBDto detailsDto) {
		RegistrationCfxDetailsDto regCfxDetailsDto = new RegistrationCfxDetailsDto();

		for (RegistrationDetailsDBDto dbDto : detailsDto.getRegCfxDetailsDBDto()) {
			regCfxDetailsDto.setInternalRule(getAccountInternalRule(dbDto));
			regCfxDetailsDto.setSanctionDetails(getCompanySanction(dbDto));
			regCfxDetailsDto.setIntuitionDetails(getAccountIntuition(dbDto)); //AT-4114
		}

		regCfxDetailsDto.setAccount(getAccount(detailsDto));
		regCfxDetailsDto.setCurrentContact(getCurrentContactDetails(detailsDto));
		regCfxDetailsDto.setContactDetails(getContactList(detailsDto));
		regCfxDetailsDto.setDocumentList(detailsDto.getDocumentTypeList());
		Watchlist watchlist = deleteDuplicateWatclist(detailsDto.getWatachList());
		regCfxDetailsDto.setWatchlist(watchlist);
		regCfxDetailsDto.setStatusReason(deleteDuplicateContactReason(detailsDto.getContactStatusReason()));
		detailsDto.getActivityLogs().setTotalRecords(detailsDto.getActivityLogs().getActivityLogData().size());

		if (detailsDto.getActivityLogs().getActivityLogData().size() <= 10) {
			regCfxDetailsDto.setActivityLogs(detailsDto.getActivityLogs());
		} else {
			detailsDto.getActivityLogs()
					.setActivityLogData(detailsDto.getActivityLogs().getActivityLogData().subList(0, 10));
			regCfxDetailsDto.setActivityLogs(detailsDto.getActivityLogs());
		}

		RegistrationDetailsDBDto dbDtoLockResource = detailsDto.getRegCfxDetailsDBDto().get(0);
		if (dbDtoLockResource.getLockedBy() != null && !dbDtoLockResource.getLockedBy().isEmpty()) {
			regCfxDetailsDto.setLocked(Boolean.TRUE);
			regCfxDetailsDto.setLockedBy(dbDtoLockResource.getLockedBy());
			regCfxDetailsDto.setUserResourceId(dbDtoLockResource.getUserResourceId());
		}
		regCfxDetailsDto.setPaginationDetails(detailsDto.getPaginationDetails());
		regCfxDetailsDto.setAlertComplianceLog(detailsDto.getAlertComplianceLog());
		regCfxDetailsDto.setIsOnQueue(detailsDto.getIsOnQueue());
		regCfxDetailsDto.setDataAnonStatus(detailsDto.getDataAnonStatus());
		regCfxDetailsDto.setPoiExists(detailsDto.getPoiExists());
		
		//Added for AT-4190
		if(detailsDto.getRegCfxDetailsDBDto().get(0).getIntuitionRiskLevel()!=null) {
			regCfxDetailsDto.setIntuitionRiskLevel(detailsDto.getRegCfxDetailsDBDto().get(0).getIntuitionRiskLevel());
		}
		regCfxDetailsDto.setAccountVersion(detailsDto.getRegCfxDetailsDBDto().get(0).getAccountVersion());
		return regCfxDetailsDto;
	}

	/**
	 * Gets the current contact details.
	 *
	 * @param dbDto the db dto
	 * @return the current contact details
	 */
	private ContactWrapper getCurrentContactDetails(RegistrationCfxDetailsDBDto dbDto) {
		ContactWrapper contact = new ContactWrapper();
		RegistrationDetailsDBDto detailsDto = dbDto.getRegCfxDetailsDBDto().get(0);

		for (RegistrationDetailsDBDto contactDto : dbDto.getRegCfxDetailsDBDto()) {

			RegistrationContact regCfxContact = JsonConverterUtil.convertToObject(RegistrationContact.class,
					contactDto.getContactAttrib());
			if (Boolean.TRUE.equals(regCfxContact.getPrimaryContact())) {
				contact.setCrmContactId(regCfxContact.getContactSFID());
			}
		}

		contact.setId(detailsDto.getCurrentContactId());
		contact.setCcCrmContactId(detailsDto.getCcCrmContactId());
		contact.setCrmAccountId(detailsDto.getCrmAccountId());
		contact.setRegComplete(DateTimeFormatter.dateTimeFormatter(detailsDto.getRegComp()));
		contact.setRegIn(DateTimeFormatter.dateTimeFormatter(detailsDto.getRegIn()));
		return contact;
	}

	/**
	 * Gets the account.
	 *
	 * @param dbDto the db dto
	 * @return the account
	 */
	private AccountWrapper getAccount(RegistrationCfxDetailsDBDto dbDto) {
		AccountWrapper account = new AccountWrapper();
		RegistrationDetailsDBDto detailsDto = dbDto.getRegCfxDetailsDBDto().get(0);
		RegistrationAccount regCfxAccount = JsonConverterUtil.convertToObject(RegistrationAccount.class,
				detailsDto.getAccountAttrib());

		if (regCfxAccount != null) {
			account.setClientType(regCfxAccount.getCustType());
			account.setCurrencyPair(regCfxAccount.getSellingCurrency() + " - " + regCfxAccount.getBuyingCurrency());
			account.setEstimTransValue(regCfxAccount.getValueOfTransaction());
			account.setPurposeOfTran(regCfxAccount.getPurposeOfTran());
			account.setServiceRequired(regCfxAccount.getServiceRequired());
			account.setSource(regCfxAccount.getSource());
			account.setSourceOfFund(regCfxAccount.getSourceOfFund());
			account.setRefferalText(regCfxAccount.getReferralText());
			account.setAffiliateName(regCfxAccount.getAffiliateName());
			account.setRegMode(regCfxAccount.getRegistrationMode());
			if (null != regCfxAccount.getCompany()) {
				account.setCompany(getCompany(regCfxAccount.getCompany()));
			}
			if (null != regCfxAccount.getRiskProfile()) {
				account.setRiskProfile(getRiskProfile(regCfxAccount.getRiskProfile()));
			}
			if (null != regCfxAccount.getRiskProfile()) {
				account.setCorperateCompliance(getCorporateCompliance(regCfxAccount.getCorperateCompliance()));
			}
			account.setName(regCfxAccount.getAccountName());
			account.setWebsite(regCfxAccount.getWebsite());
			account.setSourceLookup(regCfxAccount.getSubSource());
			account.setAvgTransactionValue(StringUtils.getNumberFormat(String.valueOf(regCfxAccount.getAverageTransactionValue())));
			account.setCountriesOfOperation(regCfxAccount.getCountriesOfOperation());
			account.setAnnualFXRequirement(StringUtils.getNumberFormat(regCfxAccount.getTurnover()));
		}
		account.setComplianceStatus(detailsDto.getAccComplianceStatus());
		account.setId(detailsDto.getAccountId());
		account.setOrgCode(detailsDto.getOrgnization());
		account.setTradeAccountNumber(detailsDto.getTradeAccountNum());
		account.setTradeAccountID(Integer.valueOf(detailsDto.getTradeAccountId()));
		account.setAccountTMFlag(detailsDto.getAccountTMFlag());
		if(detailsDto.getRegCompAccount() != null){
			account.setRegCompleteAccount(detailsDto.getRegCompAccount().toString());
		}else {
			account.setRegCompleteAccount(Constants.DASH_UI);
		}
		if (detailsDto.getLegalEntity() != null && !detailsDto.getLegalEntity().isEmpty())
			account.setLegalEntity(detailsDto.getLegalEntity());
		else
			account.setLegalEntity(Constants.DASH_UI);
		if(null != detailsDto.getRegistrationInDate()){
			account.setRegistrationInDate(detailsDto.getRegistrationInDate().toString());
		}
		if(null != detailsDto.getComplianceExpiry()){
			account.setComplianceExpiry(detailsDto.getComplianceExpiry().toString());
		}else {
			account.setComplianceExpiry(null);
		}
		return account;
	}

	/**
	 * Gets the company.
	 *
	 * @param comanyDetails the comany details
	 * @return the company
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
		company.setIncorporationDate(DateTimeFormatter.getDateInRFC3339(comanyDetails.getIncorporationDate()));
		company.setIndustry(comanyDetails.getIndustry());
		company.setOngoingDueDiligenceDate(DateTimeFormatter.dateFormat(comanyDetails.getOngoingDueDiligenceDate()));
		company.setOption(comanyDetails.getOption());
		company.setRegistrationNo(comanyDetails.getRegistrationNo());
		company.setShippingAddress(comanyDetails.getShippingAddress());
		company.settAndcSignedDate(DateTimeFormatter.dateFormat(comanyDetails.gettAndcSignedDate()));
		company.setTypeOfFinancialAccount(comanyDetails.getTypeOfFinancialAccount());
		company.setVatNo(comanyDetails.getVatNo());

		return company;
	}

	/**
	 * Gets the risk profile.
	 *
	 * @param riskProfileDetails the risk profile details
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
		//newly added DNB DAta
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
		//newly added DNB DAta ends
		
		

		return riskProfile;
	}

	/**
	 * Gets the corporate compliance.
	 *
	 * @param corpComplianceDetails the corp compliance details
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
		// newly added dnb data for corporate compliance added by neelesh pant
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
		//newly added DnB data ends

		return corporateCompliance;
	}

	/**
	 * Gets the account internal rule.
	 *
	 * @param dbDto the db dto
	 * @return the account internal rule
	 */
	private List<InternalRule> getAccountInternalRule(RegistrationDetailsDBDto dbDto) {
		List<InternalRule> internalRuleList = new ArrayList<>();
		InternalRule internalRule = new InternalRule();
		internalRule.setBlacklist(getAccountBlackList(dbDto));
		internalRuleList.add(internalRule);
		return internalRuleList;
	}

	/**
	 * Gets the company sanction.
	 *
	 * @param dbDto the db dto
	 * @return the company sanction
	 */
	private SanctionDetails getCompanySanction(RegistrationDetailsDBDto dbDto) {

		Integer passCount = 0;
		Integer failCount = 0;
		List<Sanction> sanctionList = new ArrayList<>();
		Integer entityId = dbDto.getAccountId();
		List<EventDBDto> sanctionEventDBDtos = dbDto.getEventDBDto().get(entityId + Constants.SANCTIONACCOUNT);
		Sanction sanction = new Sanction();
		SanctionDetails sanctionDetails = new SanctionDetails();

		if (sanctionEventDBDtos == null || sanctionEventDBDtos.isEmpty()) {
			sanctionDetails.setSanctionTotalRecords(0);
			return sanctionDetails;
		}

		EventDBDto eventDBDto = sanctionEventDBDtos.get(0);
		SanctionSummary summary = JsonConverterUtil.convertToObject(SanctionSummary.class, eventDBDto.getSummary());
		sanction.setEventServiceLogId(eventDBDto.getId());

		if (summary != null && !Constants.NOT_REQUIRED.equalsIgnoreCase(summary.getStatus())) {
			setSanctionValues(sanction, summary);
			boolean status = summary.getStatus() != null && Constants.PASS.equals(summary.getStatus());
			if (status) {
				passCount = sanctionEventDBDtos.size();
			} else {
				failCount = sanctionEventDBDtos.size();
			}
			sanction.setUpdatedBy(eventDBDto.getUpdatedBy());
			sanction.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			sanction.setStatus(status);
			sanction.setIsRequired(Boolean.TRUE);
			sanction.setPrevStatusValue(summary.getStatus());
		}
		/**Changes made for AT-363
		 *If Name and email is blacklisted then Sanction status set to Not_Required on UI - Saylee
		 */
		else{
			setSanctionValues(sanction, summary);
			sanction.setUpdatedBy(eventDBDto.getUpdatedBy());			
			sanction.setIsRequired(Boolean.FALSE);
			sanction.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			sanction.setStatusValue(Constants.NOT_REQUIRED_UI);
		}
		sanctionDetails.setFailCount(failCount);
		sanctionDetails.setPassCount(passCount);
		sanctionDetails.setSanctionTotalRecords(sanctionEventDBDtos.size());
		sanctionList.add(sanction);
		sanctionDetails.setSanction(sanctionList);
		return sanctionDetails;
	}

	/**
	 * Set the value of each coloum on UI
	 * changes made by- Saylee.
	 *
	 * @param sanction the sanction
	 * @param summary the summary
	 */
	private void setSanctionValues(Sanction sanction, SanctionSummary summary) {
		if(null != summary){
			sanction.setWorldCheck(summary.getWorldCheck());
			sanction.setSanctionId(summary.getSanctionId());
			sanction.setOfacList(summary.getOfacList());
		}
	}

	/**
	 * Gets the contact list.
	 *
	 * @param detailsBDDto the details BD dto
	 * @return the contact list
	 */
	private List<RegistrationCfxContactDetailsDto> getContactList(RegistrationCfxDetailsDBDto detailsBDDto) {
		List<RegistrationCfxContactDetailsDto> regDetailsList = new ArrayList<>();

		for (RegistrationDetailsDBDto dbDto : detailsBDDto.getRegCfxDetailsDBDto()) {

			RegistrationCfxContactDetailsDto regDetailsDto = new RegistrationCfxContactDetailsDto();
			regDetailsDto.setCurrentContact(getCurrentContact(dbDto));
			regDetailsDto.setKycDetails(getContactKyc(dbDto));
			regDetailsDto.setSanctionDetails(getContactSanction(dbDto));
			regDetailsDto.setFraugsterDetails(getContactFraugster(dbDto));
			regDetailsDto.setInternalRule(getContactInternalRule(dbDto));
			//regDetailsDto.setIntuitionDetails(getContactIntuition(dbDto)); //At-4114
			if (dbDto.getLockedBy() != null && !dbDto.getLockedBy().isEmpty()) {
				regDetailsDto.setLocked(Boolean.TRUE);
				regDetailsDto.setLockedBy(dbDto.getLockedBy());
				regDetailsDto.setUserResourceId(dbDto.getUserResourceId());
			}
			regDetailsList.add(regDetailsDto);
		}
		return regDetailsList;
	}

	/**
	 * Gets the current contact.
	 *
	 * @param dbDto the db dto
	 * @return the current contact
	 */
	private ContactWrapper getCurrentContact(RegistrationDetailsDBDto dbDto) {

		ContactWrapper contact = new ContactWrapper();
		RegistrationContact regCfxContact = JsonConverterUtil.convertToObject(RegistrationContact.class,
				dbDto.getContactAttrib());
		contact.setAccountId(dbDto.getAccountId());
		contact.setAddress(getCompleteAddress(regCfxContact));
		contact.setAddressType(regCfxContact.getAddressType());
		contact.setAuthorisedSignatory(regCfxContact.getAuthorisedSignatory());
		contact.setComplianceStatus(dbDto.getConComplianceStatus());
		contact.setCrmAccountId(dbDto.getCrmAccountId());
		contact.setCrmContactId(dbDto.getCrmContactId());
		contact.setDesignation(regCfxContact.getDesignation());
		contact.setEmail(regCfxContact.getEmail());
		contact.setId(dbDto.getContactId());
		contact.setIpAddress(regCfxContact.getIpAddress());
		contact.setCountryOfResidence(dbDto.getCountryOfResidenceFullName()+" ("+regCfxContact.getCountry()+")");
		contact.setIsUsClient(Boolean.FALSE);
		if (regCfxContact.getCountry() != null && (Constants.USA).equals(regCfxContact.getCountry()))
			contact.setIsUsClient(Boolean.TRUE);
		contact.setJobTitle(regCfxContact.getJobTitle());
		contact.setMobile(regCfxContact.getPhoneMobile());
		contact.setName(regCfxContact.getFullName());
		contact.setNationality(regCfxContact.getNationality());
		contact.setOccupation(regCfxContact.getOccupation());
		contact.setPhone(regCfxContact.getPhoneHome());
		contact.setPositionOfSignificance(regCfxContact.getPositionOfSignificance());
		contact.setRegComplete(DateTimeFormatter.dateTimeFormatter(dbDto.getRegComp()));
		contact.setRegIn(DateTimeFormatter.dateTimeFormatter(dbDto.getRegIn()));
		contact.setTradeContactID(Integer.valueOf(dbDto.getTradeContactId()));
		contact.setOrganization(dbDto.getOrgnization());
		//added By Neelesh Pant
		contact.setDob(DateTimeFormatter.getDateInRFC3339(regCfxContact.getDob()));
		if((!StringUtils.isNullOrEmpty(regCfxContact.getPhoneWorkExtn())) && (!StringUtils.isNullOrEmpty(regCfxContact.getPhoneWork()))) {
			contact.setPhoneWork(regCfxContact.getPhoneWork()+";"+regCfxContact.getPhoneWorkExtn());
		}
	    if((!StringUtils.isNullOrEmpty(regCfxContact.getPhoneWorkExtn())) && (StringUtils.isNullOrEmpty(regCfxContact.getPhoneWork()))) {
			contact.setPhoneWork(regCfxContact.getPhoneWorkExtn());
	    }
	    if((StringUtils.isNullOrEmpty(regCfxContact.getPhoneWorkExtn()))&& (!StringUtils.isNullOrEmpty(regCfxContact.getPhoneWork()))) {
			contact.setPhoneWork(regCfxContact.getPhoneWork());
	    }
		contact.setIsCountrySupported(
				checkIsKycSupportedCountry(regCfxContact.getCountry(), dbDto.getKycSupportedCountryList(), dbDto.getOrgnization(),dbDto.getLegalEntity()));
		contact.setPrimaryContact(Boolean.FALSE);
		if (Boolean.TRUE.equals(regCfxContact.getPrimaryContact()))
			contact.setPrimaryContact(Boolean.TRUE);
		return contact;
	}

	/**
	 * Purpose of method: -> To set KYC response which we got from KYC micro
	 * service for contact of CFX account
	 * 
	 * Implementation: 1) In this method, we are fetching response of KYC micro
	 * service for Contact of CFX and if it is empty then we return it
	 * immediately. 2) But if response details are present, then we first check
	 * status of that response. 2a. If status is 'NOT_REQUIRED' then we show end
	 * user that status on UI. 2b. If status is other than 'NOT_REQUIRED' then
	 * we show details for that contact on UI side.
	 *
	 * @param dbDto the db dto
	 * @return the contact kyc
	 */
	private KycDetails getContactKyc(RegistrationDetailsDBDto dbDto) {
		KycDetails kycDetails = new KycDetails();
		List<Kyc> kycList = new ArrayList<>();
		Integer entityId = dbDto.getContactId();
		// constant added by Vishal J
		List<EventDBDto> kycEventDBDtos = dbDto.getEventDBDto().get(entityId + Constants.KYCCONTACT);

		if (kycEventDBDtos == null || kycEventDBDtos.isEmpty()) {
			kycDetails.setKycTotalRecords(0);
			return kycDetails;
		}

		EventDBDto kycEventDBDto = kycEventDBDtos.get(0);
		KYCSummary kycSummary = JsonConverterUtil.convertToObject(KYCSummary.class, kycEventDBDto.getSummary());

		if (kycSummary == null) {
			kycDetails.setFailCount(kycEventDBDtos.size());
			return kycDetails;
		}
		// declared these members here for memory management
		Integer passCount = 0;
		Integer failCount = 0;
		Kyc kyc = new Kyc();
		// condition added by Vishal J to set requirement of KYC check on 25th
		// Jan
		/*
		 * If we get Response from KYC micro service as 'NOT_REQUIRED' for KYC
		 * check then we don't need to perform KYC check on contacts so we are
		 * simply showing that on UI for user understanding
		 */
		boolean status = false;
		if (!Constants.NOT_REQUIRED.equalsIgnoreCase(kycSummary.getStatus())) {
			status = Constants.PASS.equals(kycSummary.getStatus());
			if (status) {
				passCount = kycEventDBDtos.size();
			} else {
				failCount = kycEventDBDtos.size();
			}
			kycDetails.setPassCount(passCount);
			kycDetails.setFailCount(failCount);
			kyc.setIsRequired(Boolean.TRUE);
			
		} else {
			kyc.setIsRequired(Boolean.FALSE);
			kyc.setStatusValue(Constants.NOT_REQUIRED_UI);
		}

		setKycResponse(kycDetails, kycList, kycEventDBDtos, kycEventDBDto, kycSummary, kyc, status);
		// End of - condition added by Vishal J to set requirement of KYC check
		// on 25th Jan
		return kycDetails;
	}

	/**
	 * Sets the kyc response.
	 *
	 * @param kycDetails the kyc details
	 * @param kycList the kyc list
	 * @param kycEventDBDtos the kyc event DB dtos
	 * @param kycEventDBDto the kyc event DB dto
	 * @param kycSummary the kyc summary
	 * @param kyc the kyc
	 * @param status the status
	 */
	// to reduce the cyclomatic complexity we extract this method
	private void setKycResponse(KycDetails kycDetails, List<Kyc> kycList, List<EventDBDto> kycEventDBDtos,
			EventDBDto kycEventDBDto, KYCSummary kycSummary, Kyc kyc, boolean status) {
		kyc.setCheckedOn(DateTimeFormatter.dateTimeFormatter(kycSummary.getCheckedOn()));
		
		kyc.setDob(DateTimeFormatter.getDateInRFC3339(kycSummary.getDob()));
		kyc.setId(kycEventDBDto.getId());
		kyc.setReferenceId(kycSummary.getReferenceId());
		// If status of KYC check is 'Not Required' the there is no need to set
		// staus value (true or false)
		// because we are going to show that 'Not Required' itself on UI in that
		// condition
		if (!Constants.NOT_REQUIRED.equalsIgnoreCase(kyc.getStatusValue()))
			kyc.setStatus(status);
		kyc.setEidCheck(kycSummary.getEidCheck());
		kyc.setVerifiactionResult(kycSummary.getVerifiactionResult());
		kyc.setEntityType(Constants.CONTACT);
		kyc.setPrevStatusValue(kycSummary.getStatus());
		kycDetails.setKycTotalRecords(kycEventDBDtos.size());
		kycList.add(kyc);
		kycDetails.setKyc(kycList);
	}

	/**
	 * Purpose of method: -> To set Sanction response which we got from Sanction
	 * micro service for contact of CFX account
	 * 
	 * Implementation: 1) In this method, we are fetching response of Sanction
	 * micro service for Contact of CFX and if it is empty then we return it
	 * immediately. 2) But if response details are present, then we first check
	 * status of that response. 2a. If status is 'NOT_REQUIRED' then we show end
	 * user that status on UI. 2b. If status is other than 'NOT_REQUIRED' then
	 * we show details for that contact on UI side.
	 *
	 * @param dbDto the db dto
	 * @return the contact sanction
	 */
	private SanctionDetails getContactSanction(RegistrationDetailsDBDto dbDto) {
		Integer entityId = dbDto.getContactId();
		List<Sanction> sanctionList = new ArrayList<>();
		// constant added by Vishal J
		List<EventDBDto> sanctionEventDBDtos = dbDto.getEventDBDto().get(entityId + Constants.SANCTIONCONTACT);
		Sanction sanction = new Sanction();
		SanctionDetails sanctionDetails = new SanctionDetails();

		if (sanctionEventDBDtos == null || sanctionEventDBDtos.isEmpty()) {
			sanctionDetails.setSanctionTotalRecords(0);
			return sanctionDetails;
		}

		Integer passCount = 0;
		Integer failCount = 0;
		EventDBDto eventDBDto = sanctionEventDBDtos.get(0);
		SanctionSummary summary = JsonConverterUtil.convertToObject(SanctionSummary.class, eventDBDto.getSummary());
		sanction.setEventServiceLogId(eventDBDto.getId());

		// condition added by Vishal J to check the requirement of Sanction on
		// contact of cfx
		/*
		 * If we get Response from Sanction micro service as 'NOT_REQUIRED' for
		 * Sanction check then we don't need to perform Sanction check on
		 * contacts so we are simply showing that on UI for user understanding
		 */
		if (summary != null && !Constants.NOT_REQUIRED.equalsIgnoreCase(summary.getStatus())) {

			// constant added by Vishal J
			boolean status = summary.getStatus() != null && Constants.PASS.equals(summary.getStatus());
			if (status) {
				passCount = sanctionEventDBDtos.size();
			} else {
				failCount = sanctionEventDBDtos.size();
			}
			sanction.setStatus(status);
			sanctionDetails.setFailCount(failCount);
			sanctionDetails.setPassCount(passCount);
			sanction.setIsRequired(Boolean.TRUE);
			sanction.setPrevStatusValue(summary.getStatus());
		} else {
			sanction.setIsRequired(Boolean.FALSE);
			sanction.setStatusValue(Constants.NOT_REQUIRED_UI);
			sanction.setPrevStatusValue(Constants.NOT_REQUIRED);
		}
		// End of - condition added by Vishal J to check the requirement of
		// Sanction on contact of cfx
		setSanctionResponse(sanctionList, sanctionEventDBDtos, sanction, sanctionDetails, eventDBDto, summary);

		return sanctionDetails;
	}

	/**
	 * Sets the sanction response.
	 *
	 * @param sanctionList the sanction list
	 * @param sanctionEventDBDtos the sanction event DB dtos
	 * @param sanction the sanction
	 * @param sanctionDetails the sanction details
	 * @param eventDBDto the event DB dto
	 * @param summary the summary
	 */
	// this method is extracted to reduce cyclomatic complexity
	private void setSanctionResponse(List<Sanction> sanctionList, List<EventDBDto> sanctionEventDBDtos,
			Sanction sanction, SanctionDetails sanctionDetails, EventDBDto eventDBDto, SanctionSummary summary) {
		if (null != summary) {
			setSanctionValues(sanction, summary);
			sanction.setUpdatedBy(eventDBDto.getUpdatedBy());
			sanction.setUpdatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			sanctionDetails.setSanctionTotalRecords(sanctionEventDBDtos.size());
			sanction.setEntityType(Constants.CONTACT);
			sanctionList.add(sanction);
			sanctionDetails.setSanction(sanctionList);
		}
	}

	/**
	 * Gets the contact fraugster.
	 *
	 * @param dbDto the db dto
	 * @return the contact fraugster
	 */
	private FraugsterDetails getContactFraugster(RegistrationDetailsDBDto dbDto) {
		Integer entityId = dbDto.getContactId();
		List<EventDBDto> eventDBDtos = dbDto.getEventDBDto().get(entityId + Constants.FRAUGSTERCONTACT);
		List<Fraugster> fraugsterList = new ArrayList<>();
		Fraugster fraugster = new Fraugster();
		Integer passCount = 0;
		Integer failCount = 0;
		FraugsterDetails fraugsterDetails = new FraugsterDetails();

		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			fraugsterDetails.setFraugsterTotalRecords(0);
			fraugsterList.add(fraugster);
			fraugsterDetails.setFraugster(fraugsterList);
			return fraugsterDetails;
		}
		EventDBDto eventDBDto = eventDBDtos.get(0);
		if (eventDBDto == null) {
			fraugsterDetails.setFraugsterTotalRecords(0);
			fraugsterList.add(fraugster);
			fraugsterDetails.setFraugster(fraugsterList);
			return fraugsterDetails;
		}
		
		if (eventDBDto.getUpdatedOn() != null) {
			fraugster.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		}
		fraugster.setUpdatedBy(eventDBDto.getUpdatedBy());
	
		FraugsterSummary fraugsterSummary = JsonConverterUtil.convertToObject(FraugsterSummary.class,
					eventDBDto.getSummary());
		if (fraugsterSummary != null) {
			if(!Constants.NOT_REQUIRED.equalsIgnoreCase(fraugsterSummary.getStatus())) {
				boolean status = fraugsterSummary.getStatus() != null && Constants.PASS.equals(fraugsterSummary.getStatus());
				if (status) {
					passCount = eventDBDtos.size();
				} else {
					failCount = eventDBDtos.size();
				}
				fraugster.setStatus(status);
			}
			fraugster.setFraugsterId(fraugsterSummary.getFrgTransId());
			fraugster.setScore(fraugsterSummary.getScore());
			fraugster.setPrevStatusValue(fraugsterSummary.getStatus());
		}
		fraugster.setPassCount(passCount);
		fraugster.setFailCount(failCount);
		fraugster.setId(eventDBDto.getId());
		fraugsterList.add(fraugster);
		fraugsterDetails.setFraugsterTotalRecords(eventDBDtos.size());
		fraugsterDetails.setFraugster(fraugsterList);

		return fraugsterDetails;
	}

	/**
	 * Gets the contact internal rule.
	 *
	 * @param dbDto the db dto
	 * @return the contact internal rule
	 */
	private List<InternalRule> getContactInternalRule(RegistrationDetailsDBDto dbDto) {
		List<InternalRule> internalRulesList = new ArrayList<>();
		InternalRule internalRule = new InternalRule();
		internalRule.setBlacklist(getContactBlackList(dbDto));
		internalRulesList.add(internalRule);
		return internalRulesList;
	}

	/**
	 * Gets the contact black list.
	 *
	 * @param dbDto the db dto
	 * @return the contact black list
	 */
	private Blacklist getContactBlackList(RegistrationDetailsDBDto dbDto) {
		Integer entityId = dbDto.getContactId();
		List<EventDBDto> blacklistEventDBDtos = dbDto.getEventDBDto().get(entityId + Constants.BLACKLISTCONTACT);
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {
			blacklist.setFailCount(4);
			return blacklist;
		}

		BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class,
				blacklistEventDBDtos.get(0).getSummary());
		if (blacklistSummary == null) {
			return blacklist;
		}

		/*
		 * Since we are calling a common method to set blacklist response for
		 * 'Account' and 'Contact', for contact specific blacklist checks (IP,
		 * Email, phone) we are setting value of IP here and passing it to that
		 * common method named 'setBlackListResponse()' 1)getting value from
		 * blackListSummary object to check whether it is 'null', 2)If null then
		 * set IP check as false and setting them into object of blacklist
		 * Changes done by Vishal J on 6th feb 17
		 */
		blacklist.setIp(blacklistSummary.getIp());

		// condition and method added to resolve AT-259 by Vishal J 24th jan
		setBlackListResponse(blacklistEventDBDtos, blacklist, blacklistSummary, Constants.CONTACT);
		// condition end to resolve AT-259
		
		//added method to set blacklist matched data
		setBlacklistedMatchedData(blacklistSummary,blacklist);
		
		return blacklist;
	}

	/**
	 * Gets the account black list.
	 *
	 * @param dbDto the db dto
	 * @return the account black list
	 */
	private Blacklist getAccountBlackList(RegistrationDetailsDBDto dbDto) {
		Integer entityId = dbDto.getAccountId();
		List<EventDBDto> blacklistEventDBDtos = dbDto.getEventDBDto().get(entityId + Constants.BLACKLISTACCOUNT);
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {
			return null;
		}

		BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class,
				blacklistEventDBDtos.get(0).getSummary());
		if (blacklistSummary == null) {
			return blacklist;
		}

		setBlackListResponse(blacklistEventDBDtos, blacklist, blacklistSummary, Constants.ACCOUNT);
		
		//added method to set blacklist matched data
		setBlacklistedMatchedData(blacklistSummary,blacklist);
		
		return blacklist;
	}

	/**
	 * Sets the black list response.
	 *
	 * @param blacklistEventDBDtos the blacklist event DB dtos
	 * @param blacklist the blacklist
	 * @param blacklistSummary the blacklist summary
	 * @param entityType the entity type
	 */
	private void setBlackListResponse(List<EventDBDto> blacklistEventDBDtos, Blacklist blacklist,
			BlacklistSummary blacklistSummary, String entityType) {
		Boolean blacklistSummaryPhone = Boolean.TRUE;
		/*
		 * condition added to check whether blacklist check is required or not
		 * If required then do regular functionality otherwise just send that
		 * status value to UI side Vishal J
		 */
		if (!Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getStatus())) {
			blacklistSummary.setEmail(blacklistSummary.getEmail());
			blacklistSummary.setIp(blacklistSummary.getIp());
			blacklistSummary.setPhone(blacklistSummary.getPhone() == null ? blacklistSummaryPhone : blacklistSummary.getPhone());
			blacklistSummary.setWebSite(blacklistSummary.getWebSite());
			blacklistSummary.setName(blacklistSummary.getName());
			blacklist.setId(blacklistEventDBDtos.get(0).getId());
			blacklist.setEntityType(entityType);
			
			if(Constants.SERVICE_FAILURE.equalsIgnoreCase(blacklistSummary.getStatus())){
				setServiceFailureResponseForBlacklist(blacklist, entityType);
			} else {
				updateBlackListForNotRequired(blacklist, blacklistSummary, entityType);
			}
		} else {
			// sending status value to UI
			blacklist.setIsRequired(Boolean.FALSE);
			blacklist.setStatusValue(Constants.NOT_REQUIRED_UI);
		}
	}

  private void updateBlackListForNotRequired(Blacklist blacklist, BlacklistSummary blacklistSummary,
      String entityType) {
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getName()))
    	blacklist.setName(Constants.NOT_REQUIRED_UI);
    else blacklist.setName(blacklistSummary.getName());
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getWebSite()))
    	blacklist.setWebSite(Constants.NOT_REQUIRED_UI);
    else blacklist.setWebSite(blacklistSummary.getWebSite());
    /** below 3 fields (IP, email, phone) are added by Vishal J to set response
     for entity type 'CONTACT'*/
    if(Constants.NOT_REQUIRED.equalsIgnoreCase(blacklistSummary.getEmail()))
    	blacklist.setEmail(Constants.NOT_REQUIRED_UI);
    else blacklist.setEmail(blacklistSummary.getEmail());
    blacklist.setPhone(!blacklistSummary.getPhone());
    // field added ends here by Vishal J
    blacklist.setStatus(Constants.PASS.equals(blacklistSummary.getStatus()));
    blacklist.setIsRequired(Boolean.TRUE);
    blacklist.setPrevStatusValue(blacklistSummary.getStatus());
    // condition set to show for IP check as 'NOT_REQUIRED' for Account
    // blacklist check
    if (entityType.equalsIgnoreCase(Constants.ACCOUNT)) {
    	blacklist.setStatusValue(Constants.NOT_REQUIRED_UI);
    	blacklist.setIp(Constants.NOT_REQUIRED_UI);
    	setBlacklistAccountPassAndFailCount(blacklist);
    }
    // condition added to show PASS or FAIL count for 'CONTACT' by
    // Vishal J
    else if (entityType.equalsIgnoreCase(Constants.CONTACT)) {
    	blacklist.setStatusValue(Constants.NOT_REQUIRED_UI);
    	blacklist.setIp(Constants.NOT_REQUIRED_UI);
    	setBlacklistContactPassAndFailCount(blacklist);
    }
  }

	/**
	 * Sets the service failure response for blacklist.
	 *
	 * @param blacklist the new service failure response for blacklist
	 */
	private void setServiceFailureResponseForBlacklist(Blacklist blacklist, String entityType) {
		Integer failCount = 0;

		blacklist.setName(Constants.BLACKLISTED_TRUE);
		blacklist.setEmail(Constants.BLACKLISTED_TRUE);
		blacklist.setDomain(Constants.BLACKLISTED_TRUE);
		blacklist.setIp(Constants.BLACKLISTED_TRUE);
		blacklist.setWebSite(Constants.BLACKLISTED_TRUE);
		blacklist.setPhone(Boolean.FALSE);
		blacklist.setStatus(Boolean.FALSE);
		blacklist.setIsRequired(Boolean.TRUE);
		blacklist.setPrevStatusValue(Constants.SERVICE_FAILURE);

		if (entityType.equalsIgnoreCase(Constants.ACCOUNT)) {
			failCount = 3;
			blacklist.setFailCount(failCount);
		} else if (entityType.equalsIgnoreCase(Constants.CONTACT)) {
			failCount = 4;
			blacklist.setFailCount(failCount);
		}

	}
	

	/**
	 * Delete duplicate contact reason.
	 *
	 * @param statusReasonList the status reason list
	 * @return the status reason
	 */
	private StatusReason deleteDuplicateContactReason(StatusReason statusReasonList) {
		TreeMap<String, StatusReasonData> reasonMap = new TreeMap<>();
		ArrayList<StatusReasonData> statusReasonData = new ArrayList<>();

		for (StatusReasonData data : statusReasonList.getStatusReasonData()) {
			reasonMap.put(data.getName(), data);
		}
		statusReasonData.addAll(reasonMap.values());
		statusReasonList.setStatusReasonData(statusReasonData);
		return statusReasonList;
	}
	
	// AT-4114
	private IntuitionDetails getAccountIntuition(RegistrationDetailsDBDto dbDto) {

		Integer passCount = 0;
		Integer failCount = 0;
		List<Intuition> intuitionList = new ArrayList<>();
		Integer entityId = dbDto.getAccountId();
		List<EventDBDto> intuitionEventDBDtos = dbDto.getEventDBDto()
				.get(entityId + Constants.TRANSACTION_MONITORINGACCOUNT);
		Intuition intuition = new Intuition();
		IntuitionDetails intuitionDetails = new IntuitionDetails();

		if (intuitionEventDBDtos == null || intuitionEventDBDtos.isEmpty()) {
			intuitionDetails.setIntuitionTotalRecords(0);
			return intuitionDetails;
		}

		EventDBDto eventDBDto = intuitionEventDBDtos.get(0);
		TransactionMonitoringAccountSummary summary = JsonConverterUtil
				.convertToObject(TransactionMonitoringAccountSummary.class, eventDBDto.getSummary());
		intuition.setId(eventDBDto.getId());

		intuition.setCorrelationId(Constants.DASH_SINGLE_DETAILS);
		intuition.setProfileScore(Constants.DASH_SINGLE_DETAILS);
		intuition.setRuleScore(Constants.DASH_SINGLE_DETAILS);
		intuition.setRiskLevel(Constants.DASH_SINGLE_DETAILS);
		
		if (summary != null && !Constants.NOT_PERFORMED.equalsIgnoreCase(summary.getStatus()) && !Constants.NOT_REQUIRED.equalsIgnoreCase(summary.getStatus())) {
			boolean status = summary.getStatus() != null && Constants.PASS.equals(summary.getStatus());
			if (status) {
				passCount = intuitionEventDBDtos.size();
			} else {
				failCount = intuitionEventDBDtos.size();
			}
			
			setIntuitionSummaryDetails(intuition, summary);
			
			intuition.setStatus(status);
			intuition.setIsRequired(Boolean.TRUE);
		}
		else if(summary != null && Constants.NOT_REQUIRED.equalsIgnoreCase(summary.getStatus())){
			intuition.setIsRequired(Boolean.FALSE);
			intuition.setStatusValue(Constants.NOT_REQUIRED);
		}
		else {
			intuition.setIsRequired(Boolean.FALSE);
			intuition.setStatusValue(Constants.NOT_PERFORMED);
		}
		
		if(eventDBDto.getUpdatedOn()!= null)
			intuition.setCreatedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		
		intuition.setUpdatedBy(eventDBDto.getUpdatedBy());
		intuitionDetails.setFailCount(failCount);
		intuitionDetails.setPassCount(passCount);
		intuitionDetails.setIntuitionTotalRecords(intuitionEventDBDtos.size());
		intuitionList.add(intuition);
		intuitionDetails.setIntuition(intuitionList);
		return intuitionDetails;
	}

	/**
	 * @param intuition
	 * @param summary
	 */
	private void setIntuitionSummaryDetails(Intuition intuition, TransactionMonitoringAccountSummary summary) {
		if (null != summary.getCorrelationId()) 
			intuition.setCorrelationId(summary.getCorrelationId());
		
		if (null != summary.getProfileScore())
			intuition.setProfileScore(summary.getProfileScore());

		if (null != summary.getRuleScore()) 
			intuition.setRuleScore(summary.getRuleScore());

		if (null != summary.getRiskLevel()) 
			intuition.setRiskLevel(summary.getRiskLevel());
	}
	
	
}
