package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Company;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.RiskProfile;
import com.currenciesdirect.gtg.compliance.core.IHolisticViewDBService;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactHistoryResponse;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentInDetails;
import com.currenciesdirect.gtg.compliance.core.domain.FurtherPaymentOutDetails;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticAccount;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticContact;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest;
import com.currenciesdirect.gtg.compliance.core.domain.HolisticViewResponse;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentInSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentOutSummary;
import com.currenciesdirect.gtg.compliance.core.domain.PaymentSummary;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCompany;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationContact;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCorperateCompliance;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationRiskProfile;
import com.currenciesdirect.gtg.compliance.core.domain.docupload.Document;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.docuploadport.UploadDocumentPort;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.ObjectUtils;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

@Component("holisticDBServiceImpl")
public class HolisticViewDBServiceImpl  extends AbstractDao implements IHolisticViewDBService {
	
	private static final String CONTACTATTRIBUTE = "Contactattribute";
	private Logger logg = LoggerFactory.getLogger(HolisticViewDBServiceImpl.class);

	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IHolisticViewDBService#getHolisticAccountDetails(com.currenciesdirect.gtg.compliance.core.domain.HolisticViewRequest)
	 */
	@Override
	public HolisticViewResponse getHolisticAccountDetails(HolisticViewRequest holisticViewRequest)throws CompliancePortalException {
		HolisticViewResponse holisticViewResponse = new HolisticViewResponse();
		HolisticAccount holisticAccount = new HolisticAccount();
		HolisticContact contact = new HolisticContact();
		List<HolisticContact> contactList = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.HOLISTIC_VIEW.getQuery());
			preparedStatement.setInt(1, holisticViewRequest.getAccountId());
			preparedStatement.setInt(2, holisticViewRequest.getContactId());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				RegistrationAccount regCfxAccount = JsonConverterUtil.convertToObject(RegistrationAccount.class, rs.getString("Accountattribute"));
				RegistrationContact regContact = JsonConverterUtil.convertToObject(RegistrationContact.class, rs.getString(CONTACTATTRIBUTE));
				if (regCfxAccount != null) {
					getAccountData(holisticAccount, rs, regCfxAccount);
					getContactData(holisticViewResponse,rs,contact,regContact);
					getCompanyData(holisticViewResponse,holisticAccount, regCfxAccount);
					getRiskProfileData(holisticViewResponse,holisticAccount, regCfxAccount);
					getCorperateComplianceData(holisticViewResponse,holisticAccount, regCfxAccount);
				}
			}
			if("PFX".equals(holisticViewResponse.getHolisticAccount().getClientType())) {
				contactList = getOtherContacts(holisticViewRequest.getAccountId(),holisticViewRequest.getContactId(),connection);
			} else {
				contactList = getContactsForCFX(holisticViewRequest.getAccountId(),connection);
			}
			holisticViewResponse.setHolisticContacts(contactList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return holisticViewResponse;
	}

	/**
	 * @param contact
	 * @param regContact
	 */
	private void getContactData(HolisticViewResponse holisticViewResponse, ResultSet rs,HolisticContact contact, RegistrationContact regContact) {
		if (regContact != null) {
			try {
				getContact(contact,regContact,rs);
			} catch (SQLException e) {
				logg.debug("Exception in getContactData() method {1}", e);
			}
		}
		holisticViewResponse.setHolisticContact(contact);
	}

	/**
	 * @param contact
	 * @param regContact
	 * @throws SQLException 
	 */
	private void getContact(HolisticContact contact, RegistrationContact regContact, ResultSet rs) throws SQLException {
		contact.setAddress(getCompleteAddress(regContact));
		contact.setCountryOfResidence(regContact.getCountry());
		contact.setEmail(regContact.getEmail());
		contact.setMobile(regContact.getPhoneMobile());
		contact.setNationality(regContact.getNationality());
		contact.setOccupation(regContact.getOccupation());
		contact.setPhone(regContact.getPhoneHome());
		contact.setIsUsClient(regContact.getCountry() != null && "USA".equals(regContact.getCountry()));
		contact.setIpAddress(regContact.getIpAddress());
		contact.setDateofbirth(DateTimeFormatter.dateFormat(DateTimeFormatter.removeTimeFromDate(regContact.getDob())));
		contact.setAccountStatus(rs.getString("AccountStatus"));
		setWorkphone(contact, regContact);
		//changes done to show full name on UI
		setName(contact, regContact);
		//CFX Contact Extra Fields 
		contact.setJobTitle(regContact.getJobTitle());
		contact.setPositionOfSignificance(regContact.getPositionOfSignificance());
		contact.setAuthorisedSignatory(regContact.getAuthorisedSignatory());
		contact.setAddressType(regContact.getAddressType());
		contact.setCrmContactId(regContact.getContactSFID());
		contact.setTradeContactId(regContact.getTradeContactID().toString());
		contact.setAreaNumber(regContact.getAreaNumber());
		contact.setAustraliaRTACardNumber(regContact.getAustraliaRTACardNumber());
		contact.setAza(regContact.getAza());
		contact.setBuildingNumber(regContact.getBuildingNumber());
		contact.setCivicNumber(regContact.getCivicNumber());
		contact.setCountryOfBirth(regContact.getCountryOfBirth());
		contact.setDistrict(regContact.getDistrict());
		contact.setPostCode(regContact.getPostCode());
		contact.setPrefName(regContact.getPreferredName());
		contact.setPrefecture(regContact.getPrefecture());
		contact.setPrimaryPhoneNumber(regContact.getPrimaryPhone());
		contact.setRegionSuburb(regContact.getRegion());
		contact.setResidentialStatus(regContact.getResidentialStatus());
		contact.setSecondEmail(regContact.getSecondEmail());
		contact.setSecondSurname(regContact.getSecondSurname());
		contact.setStateOfBirth(regContact.getStateOfBirth());
		contact.setStateProvinceCounty(regContact.getState());
		contact.setStreet(regContact.getStreet());
		contact.setStreetNumber(regContact.getStreetNumber());
		contact.setStreetType(regContact.getStreetType());
		contact.setSubBuilding(regContact.getSubBuildingorFlat());
		contact.setSubCity(regContact.getSubCity());
		contact.setTitle(regContact.getTitle());
		contact.setTownCityMuncipalty(regContact.getCity());
		contact.setUnitNumber(regContact.getUnitNumber());
		contact.setYearsInAddress(regContact.getYearsInAddress());
		contact.setDlLicenseNumber(regContact.getDlLicenseNumber());
		contact.setDlExpiryDate(regContact.getDlExpiryDate());
		contact.setDlCardNumber(regContact.getDlCardNumber());
		contact.setDlCountryCode(regContact.getDlCountryCode());
		contact.setDlStateCode(regContact.getDlStateCode());
		contact.setDlVersionNumber(regContact.getDlVersionNumber());
		contact.setFirstName(regContact.getFirstName());
		contact.setFloorNumber(regContact.getFloorNumber());
		contact.setGender(regContact.getGender());
		contact.setPhoneHome(regContact.getPhoneHome());
		contact.setLastName(regContact.getLastName());
		contact.setMedicareCardNumber(regContact.getMedicareCardNumber());
		contact.setMedicareReferenceNumber(regContact.getMedicareReferenceNumber());
		contact.setMiddleName(regContact.getMiddleName());
		contact.setMothersSurname(regContact.getMothersSurname());
		contact.setMunicipalityOfBirth(regContact.getMunicipalityOfBirth());
		contact.setNationalIdType(regContact.getNationalIdType());
		contact.setPassportFamilyNameAtBirth(regContact.getPassportFamilyNameAtBirth());
		contact.setPassportPlaceOfBirth(regContact.getPassportPlaceOfBirth());
		contact.setPassportCountryCode(regContact.getPassportCountryCode());
		contact.setPassportExiprydate(regContact.getPassportExiprydate());
		contact.setPassportFullName(regContact.getPassportFullName());
		contact.setPassportMRZLine1(regContact.getPassportMRZLine1());
		contact.setPassportMRZLine2(regContact.getPassportMRZLine2());
		contact.setPassportNameAtCitizenship(regContact.getPassportNameAtCitizenship());
		contact.setPassportNumber(regContact.getPassportNumber());
		contact.setNationalIdNumber(regContact.getNationalIdNumber());
		contact.setIsAllFieldsEmpty(ObjectUtils.isAllFieldsEmpty(contact));
		contact.setIsPrimaryContact(regContact.getPrimaryContact());
		contact.setId(rs.getInt("ContactID"));
	}
	
	/**
	 * this function gives complete address as per input fields
	 * @author abhijeetg
	 * @param regContact
	 * @return
	 */
	private String getCompleteAddress(RegistrationContact regContact) {
		List<String> list=new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(regContact.getAddress1())){
			list.add(regContact.getAddress1());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getUnitNumber())){
			list.add(regContact.getUnitNumber());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getStreetNumber())){
			list.add(regContact.getStreetNumber());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getAreaNumber())){
			list.add(regContact.getAreaNumber());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getCivicNumber())){
			list.add(regContact.getCivicNumber());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getDistrict())){
			list.add(regContact.getDistrict());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getState())){
			list.add(regContact.getState());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getCity())){
			list.add(regContact.getCity());	
		}
		if(!StringUtils.isNullOrEmpty(regContact.getPostCode())){
			list.add(regContact.getPostCode());
		}
		if(!StringUtils.isNullOrEmpty(regContact.getCountry())){
			list.add(regContact.getCountry());
		}
		return String.join(", ", list);
	}

	/**
	 * @param account
	 * @param rs
	 * @param regCfxAccount
	 * @throws SQLException
	 */
	private void getAccountData(HolisticAccount account, ResultSet rs, RegistrationAccount regCfxAccount) throws SQLException {
		
		account.setClientType(regCfxAccount.getCustType());
		account.setCurrencyPair(regCfxAccount.getSellingCurrency() + "  -  " + regCfxAccount.getBuyingCurrency());
		account.setEstimTransValue(regCfxAccount.getValueOfTransaction());
		account.setPurposeOfTran(regCfxAccount.getPurposeOfTran());
		account.setServiceRequired(regCfxAccount.getServiceRequired());
		account.setSource(regCfxAccount.getSource());
		account.setSourceOfFund(regCfxAccount.getSourceOfFund());
		account.setRefferalText(regCfxAccount.getReferralText());
		account.setAffiliateName(regCfxAccount.getAffiliateName());
		account.setRegMode(regCfxAccount.getRegistrationMode());
		account.setName(regCfxAccount.getAccountName());
		account.setWebsite(regCfxAccount.getWebsite());
		account.setSourceLookup(regCfxAccount.getSubSource());
		if(null != regCfxAccount.getAverageTransactionValue()){
			account.setAvgTransactionValue(StringUtils.getNumberFormat(String.valueOf(regCfxAccount.getAverageTransactionValue())));
		}
		account.setCountriesOfOperation(regCfxAccount.getCountriesOfOperation());
		account.setAnnualFXRequirement(StringUtils.getNumberFormat(regCfxAccount.getTurnover()));
		account.setTradeAccountNumber(regCfxAccount.getTradeAccountNumber());
		account.setTradeAccountID(regCfxAccount.getTradeAccountID());
		account.setAccSFID(regCfxAccount.getAccSFID());
		account.setAddCampaign(regCfxAccount.getAdCampaign());
		account.setAffiliateNumber(regCfxAccount.getAffiliateNumber());
		account.setBranch(regCfxAccount.getBranch());
		account.setBuyingCurrency(regCfxAccount.getBuyingCurrency());
		account.setChannel(regCfxAccount.getChannel());
		account.setExtendedReferral(regCfxAccount.getExtendedReferral());
		account.setCountryOfInterest(regCfxAccount.getCountryOfInterest());
		account.setKeywords(regCfxAccount.getKeywords());
		account.setReferralId(regCfxAccount.getReferral());
		account.setSearchEngine(regCfxAccount.getSearchEngine());
		account.setSellingCurrency(regCfxAccount.getSellingCurrency());
		account.setSubSource(regCfxAccount.getSubSource());
		account.setTradingName(regCfxAccount.getTradingName());
		account.setTurnover(StringUtils.getNumberFormat(regCfxAccount.getTurnover()));
		account.setComplianceDoneOn(DateTimeFormatter.dateTimeFormatter(rs.getTimestamp("compliancedoneon")));
		account.setRegistrationInDate (DateTimeFormatter.dateTimeFormatter(rs.getTimestamp("RegistrationInDate")));
		account.setEstimTransValue(regCfxAccount.getValueOfTransaction());
		account.setPurposeOfTran(regCfxAccount.getPurposeOfTran());
		account.setSourceOfFund(regCfxAccount.getSourceOfFund());
		account.setLegalEntity(regCfxAccount.getLegalEntity());
		account.setOrgCode(rs.getString("OrgCode"));
		account.setId(rs.getInt("AccountID"));
	}
	
	/**
	 * @param accountHistoryResponse
	 * @param regCfxAccount
	 */
	private void getCompanyData(HolisticViewResponse holisticViewResponse,HolisticAccount account, RegistrationAccount regCfxAccount) {
		if (null != regCfxAccount.getCompany()) {
			account.setCompany(getCompany(regCfxAccount.getCompany()));
		}
		holisticViewResponse.setHolisticAccount(account);
	}
	
	/**
	 * @param comanyDetails
	 * @return
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
		company.setIncorporationDate(DateTimeFormatter.dateFormat(comanyDetails.getIncorporationDate()));
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
	 * @param accountHistoryResponse
	 * @param regCfxAccount
	 */
	private void getRiskProfileData(HolisticViewResponse holisticViewResponse,HolisticAccount account,
			RegistrationAccount regCfxAccount) {
		if (null != regCfxAccount.getRiskProfile()) {
			account.setRiskProfile(getRiskProfile(regCfxAccount.getRiskProfile()));
		}
		holisticViewResponse.setHolisticAccount(account);
	}
	
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
	 * @param accountHistoryResponse
	 * @param regCfxAccount
	 */
	private void getCorperateComplianceData(HolisticViewResponse holisticViewResponse,HolisticAccount account,
			RegistrationAccount regCfxAccount) {
		if (null != regCfxAccount.getRiskProfile()) {
			account.setCorperateCompliance(getCorporateCompliance(regCfxAccount.getCorperateCompliance()));
		}
		holisticViewResponse.setHolisticAccount(account);
	}
	
	/**
	 * @param corpComplianceDetails
	 * @return
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

		return corporateCompliance;
	}
	
	/**
	 * @param contact
	 * @param regContact
	 */
	private void setWorkphone(ContactHistoryResponse contact, RegistrationContact regContact) {
		if((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn())) && (!StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
			contact.setWorkphone(regContact.getPhoneWork()+";"+regContact.getPhoneWorkExtn());
		if((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn())) && (StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
			contact.setWorkphone(regContact.getPhoneWorkExtn());
		if((StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn()))&& (!StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
			contact.setWorkphone(regContact.getPhoneWork());
	}
	
	/**
	 * @param contact
	 * @param regContact
	 */
	private void setName(ContactHistoryResponse contact, RegistrationContact regContact) {
		if(regContact.getMiddleName() != null && !regContact.getMiddleName().trim().isEmpty()){
			String fullName = new StringBuilder().append(regContact.getFirstName()).append(" ").append(regContact.getMiddleName()).append(" ").append(regContact.getLastName()).toString();
			contact.setName(fullName);
		} else{
			contact.setName(regContact.getFirstName()+ " "+regContact.getLastName());
		}
	}

	@Override
	public HolisticViewResponse getHolisticPaymentDetails(HolisticViewRequest holisticViewRequest,
			HolisticViewResponse holisticViewResponse) throws CompliancePortalException { 
		
		PaymentSummary paymentSummary = new PaymentSummary();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.HOLISTIC_VIEW_PAYMENT_DETAILS.getQuery());
			preparedStatement.setInt(1, holisticViewRequest.getAccountId());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				paymentSummary.setFirstTradeDate(rs.getString("FirstTradeDate"));
				paymentSummary.setLastTradeDate(rs.getString("LastTradeDate"));
				paymentSummary.setNoOfTrades(rs.getInt("NumberOfTrades"));
			}
			getBaseCurrencyTotalSaleAmount(connection, holisticViewRequest.getAccountId(), paymentSummary);
			holisticViewResponse.setPaymentSummary(paymentSummary);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return holisticViewResponse;
	}

	private void getBaseCurrencyTotalSaleAmount(Connection connection, Integer accountId, PaymentSummary paymentSummary)
			throws CompliancePortalException {
		PreparedStatement newpreparedStatement = null;
		ResultSet resultset = null;
		try {
			newpreparedStatement = connection.prepareStatement(HolisticViewQueryConstant.HOLISTIC_VIEW_TOTAL_SALE_AMOUNT.getQuery());
			newpreparedStatement.setInt(1, accountId);
			resultset = newpreparedStatement.executeQuery();
			while (resultset.next()) {
				paymentSummary.setTotalSaleAmount(resultset.getDouble("TotalSaleAmount"));
			}
		} catch(Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultset);
			closePrepareStatement(newpreparedStatement);
		}		
	}

	@Override
	public HolisticViewResponse getHolisticPaymentInDetails(HolisticViewRequest holisticViewRequest,
			HolisticViewResponse holisticViewResponse) throws CompliancePortalException {
		
		HolisticViewResponse holisticViewPaymentInResponse = new HolisticViewResponse();
		PaymentInSummary paymentInSummary = new PaymentInSummary();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.HOLISTIC_VIEW_PAYMENTIN_DETAILS.getQuery());
			preparedStatement.setInt(1, holisticViewRequest.getPaymentInId());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				FundsInCreateRequest fundsInRequest = JsonConverterUtil.convertToObject(FundsInCreateRequest.class, rs.getString("Attributes"));
				paymentInSummary.setThirdPartyFlag(rs.getBoolean("vThirdPartyPayment"));
				paymentInSummary.setCountryOfFund(rs.getString("vCountryOfPayment"));
				paymentInSummary.setDebtorName(fundsInRequest.getTrade().getCcFirstName());
				paymentInSummary.setIsDeleted(rs.getBoolean("Deleted"));
				if(Boolean.TRUE.equals(paymentInSummary.getIsDeleted())) 
					paymentInSummary.setDeletedDate(rs.getDate("UpdatedOn").toString());
				paymentInSummary.setSellCurrency(rs.getString("vTransactionCurrency"));
				paymentInSummary.setDebtorAccountNumber(fundsInRequest.getTrade().getDebtorAccountNumber());
			}
			holisticViewPaymentInResponse.setPaymentInSummary(paymentInSummary);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return holisticViewPaymentInResponse;
	}

	@Override
	public HolisticViewResponse getHolisticPaymentOutDetails(HolisticViewRequest holisticViewRequest,
			HolisticViewResponse holisticViewResponse) throws CompliancePortalException {

		HolisticViewResponse holisticViewPaymentOutResponse = new HolisticViewResponse();
		PaymentOutSummary paymentOutSummary = new PaymentOutSummary();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.HOLISTIC_VIEW_PAYMENTOUT_DETAILS.getQuery());
			preparedStatement.setInt(1, holisticViewRequest.getPaymentOutId());
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				FundsOutRequest fundsOutRequest = JsonConverterUtil.convertToObject(FundsOutRequest.class, rs.getString("Attributes"));
				paymentOutSummary.setBankName(rs.getString("vBankName"));
				paymentOutSummary.setBeneficiaryName(rs.getString("vBeneficiaryFirstName") +" "+rs.getString("vBeneficiaryLastName"));
				paymentOutSummary.setCountryOfBeneficiary(rs.getString("vBeneficiaryCountry"));
				paymentOutSummary.setBeneficiaryAccountNumber(fundsOutRequest.getBeneficiary().getAccountNumber());
				paymentOutSummary.setBeneficiaryEmail(fundsOutRequest.getBeneficiary().getEmail());
				paymentOutSummary.setBeneficiaryType(fundsOutRequest.getBeneficiary().getBeneficiaryType());
				paymentOutSummary.setBuyCurrency(rs.getString("vBuyingCurrency"));
				paymentOutSummary.setIsDeleted(rs.getBoolean("Deleted"));
				paymentOutSummary.setPaymentReference(fundsOutRequest.getBeneficiary().getPaymentReference());
				paymentOutSummary.setPhone(fundsOutRequest.getBeneficiary().getPhone());
			}
			holisticViewPaymentOutResponse.setPaymentOutSummary(paymentOutSummary);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return holisticViewPaymentOutResponse;
	}
	
	/**
	 * Gets the other contacts.
	 *
	 * @param accountId
	 *            the account id
	 * @param contactId
	 *            the contact id
	 * @param connection
	 *            the connection
	 * @return the other contacts
	 * @throws CompliancePortalException
	 *             the compliance portal exception
	 */
	protected List<HolisticContact> getOtherContacts(Integer accountId, Integer contactId, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			List<HolisticContact> contactList = new ArrayList<>();
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.GET_OTHER_CONTACT_DETAILS.getQuery());
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, contactId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				RegistrationContact regContact = JsonConverterUtil.convertToObject(RegistrationContact.class, rs.getString(CONTACTATTRIBUTE));
				HolisticContact contact = new HolisticContact();
				contact.setId(rs.getInt("contactID"));
				contact.setJobTitle(regContact.getJobTitle());
				contact.setPositionOfSignificance(regContact.getPositionOfSignificance());
				contact.setAuthorisedSignatory(regContact.getAuthorisedSignatory());
				contact.setName(rs.getString("Name"));
				contact.setComplianceStatus(rs.getString("complianceStatus"));
				contactList.add(contact);
			}
			return contactList;

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}

	}
	
	
	/**
	 * @param accountId
	 * @return
	 * @throws CompliancePortalException
	 */
	protected List<IDomain> getDeviceInfoByAccountId(Integer accountId)	throws CompliancePortalException {
		List<IDomain> deviceInfoResponseList = new ArrayList<>();
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			connection = getConnection(Boolean.TRUE);
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.GET_DEVICE_INFO.getQuery());
			preparedStatement.setInt(1, accountId);
			
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				DeviceInfoResponse deviceInfoResponse = new DeviceInfoResponse();
				deviceInfoResponse.setDeviceType(rs.getString("DeviceType"));
				deviceInfoResponse.setDeviceName(rs.getString("DeviceName"));
				deviceInfoResponse.setDeviceVersion(rs.getString("DeviceVersion"));
				deviceInfoResponse.setDeviceId(rs.getString("DeviceID"));
				deviceInfoResponse.setDeviceManufacturer(rs.getString("DeviceManufacturer"));
				deviceInfoResponse.setoSType(rs.getString("OSType"));
				deviceInfoResponse.setBrowserName(rs.getString("BrowserName"));
				deviceInfoResponse.setBrowserVersion(rs.getString("BrowserVersion"));
				deviceInfoResponse.setBrowserLanguage(rs.getString("BrowserLanguage"));
				deviceInfoResponse.setBrowserOnline(rs.getBoolean("BrowserOnline"));
				deviceInfoResponse.setoSTimestamp(rs.getString("OSTimestamp"));
				deviceInfoResponse.setCdAppID(rs.getString("CDAppID"));
				deviceInfoResponse.setCdAppVersion(rs.getString("CDAppVersion"));
				deviceInfoResponseList.add(deviceInfoResponse);
			}
			
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
			closeConnection(connection);
		}
		return deviceInfoResponseList;
	}
	
	@Override
	public void addDocuments(HolisticViewResponse holisticViewResponse) throws CompliancePortalException {
		if(null != holisticViewResponse) {
			if ("PFX".equals(holisticViewResponse.getHolisticAccount().getCustType())) {
				addDocumentsForPfx(holisticViewResponse.getHolisticAccount().getAccSFID(), holisticViewResponse.getHolisticContact().getCrmContactId()
						, holisticViewResponse.getHolisticAccount().getOrgCode(), holisticViewResponse);
			} else {
				addDocumentsForCfx(holisticViewResponse.getHolisticAccount().getAccSFID(), holisticViewResponse.getHolisticContact().getCrmContactId()
						, holisticViewResponse.getHolisticAccount().getOrgCode(), holisticViewResponse);
			}
		}
	}
	
	private void addDocumentsForPfx(String accSFID, String conSFID, String orgCode, HolisticViewResponse holisticViewResponse) {
		try {
			if (null != accSFID && null != conSFID) {
				List<Document> documents = UploadDocumentPort.getAttachedDocument(accSFID,conSFID, orgCode, Constants.SOURCE_SALESFORCE);
				if (documents != null) {
					holisticViewResponse.setDocuments(UploadDocumentPort.updateDocumentDateFormat(documents));
				} else {
					holisticViewResponse.setDocuments(new ArrayList<>());
				}
			}
		} catch (Exception e) {
			logg.debug("Error : ", e);
			holisticViewResponse.setDocuments(new ArrayList<>());
		}
	}
	
	private void addDocumentsForCfx(String accSFID, String conSFID, String orgCode, HolisticViewResponse holisticViewResponse) {
		try {
				if (null != conSFID) {
				/**get list of document uploaded on account,
				 *  set source as salesforce for cfx to get list documents attached to respective CRMAccountid : Abhijit G*/
				List<Document> documents = UploadDocumentPort.getAttachedDocument(accSFID, conSFID, orgCode, Constants.SOURCE_SALESFORCE);
				if (documents != null) {
					holisticViewResponse.setDocuments(UploadDocumentPort.updateDocumentDateFormat(documents));
				} else {
					holisticViewResponse.setDocuments(new ArrayList<>());
				}
			}
				
		} catch (CompliancePortalException e) {
			logg.debug("Error : ", e);
			holisticViewResponse.setDocuments(new ArrayList<>());
		}
	}
	
	
	protected List<HolisticContact> getContactsForCFX(Integer accountId, Connection connection)
			throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			List<HolisticContact> contactList = new ArrayList<>();
			preparedStatement = connection.prepareStatement(HolisticViewQueryConstant.GET_CONTACT_DETAILS_FOR_CFX.getQuery());
			preparedStatement.setInt(1, accountId);
			rs = preparedStatement.executeQuery();
			while (rs.next()) {
				RegistrationContact regContact = JsonConverterUtil.convertToObject(RegistrationContact.class, rs.getString(CONTACTATTRIBUTE));
				HolisticContact contact = new HolisticContact();
				contact.setId(rs.getInt("contactID"));
				contact.setJobTitle(regContact.getJobTitle());
				contact.setPositionOfSignificance(regContact.getPositionOfSignificance());
				contact.setAuthorisedSignatory(regContact.getAuthorisedSignatory());
				contact.setName(rs.getString("Name"));
				contact.setComplianceStatus(rs.getString("complianceStatus"));
				contactList.add(contact);
			}
			return contactList;

		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(rs);
			closePrepareStatement(preparedStatement);
		}

	}
	
	@Override
	public void getFurtherPaymentDetailsList(HolisticViewResponse holisticViewResponse)
			throws CompliancePortalException {
		FurtherPaymentDetails paymentDetails = new FurtherPaymentDetails();
		Connection connection = null;
		try {
			connection = getConnection(Boolean.TRUE);
			setFurtherPaymentInDetailList(holisticViewResponse.getHolisticAccount().getId(), paymentDetails, connection);
			setFurtherPaymentOutDetailList(holisticViewResponse.getHolisticAccount().getId(), paymentDetails, connection);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		finally {
			closeConnection(connection);
		}
		
		holisticViewResponse.setFurtherPaymentDetails(paymentDetails);
	}
	
	protected List<FurtherPaymentInDetails> setFurtherPaymentInDetailList(Integer accountId,
			FurtherPaymentDetails paymentDetails, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FurtherPaymentInDetails> paymentInDetailsList = new ArrayList<>();
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_PAYMENTINFO_FOR_ACCOUNT_SELECT.getQuery());
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, 0);// Minrecord
			preparedStatement.setInt(3, 10);// MaxRecord
			resultSet = preparedStatement.executeQuery();
			Integer totalRecords = 0;
			while (resultSet.next()) {
				FurtherPaymentInDetails paymentInDetails = new FurtherPaymentInDetails();
				/**
				 * We are setting account name and number according to payment
				 * method 1) If payment method is 'SWITCH/DEBIT' then we will
				 * show Card holder name and card number on UI else account name
				 * and account number. 2)If account number is not received then
				 * we will show '-' for that.
				 */
				paymentInDetails.setPaymentId(resultSet.getInt("poid"));
				if ("SWITCH/DEBIT".equalsIgnoreCase(resultSet.getString(Constants.PAYMENT_METHOD_IN))) {
					paymentInDetails.setAccountName(resultSet.getString("Ccfirstname"));
				} else {
					setDebtorName(paymentInDetails, resultSet.getString("DebtorName"));
				}
				String debtorAccountNumber = resultSet.getString("DebtorAccountNumber");
				if (null != debtorAccountNumber && !debtorAccountNumber.isEmpty())
					paymentInDetails.setAccount(debtorAccountNumber);
				else
					paymentInDetails.setAccount("-");
				/** Method created to set risk guardian score on UI */
				setRiskGuardianScore(paymentInDetails, resultSet.getString("RiskScore"), resultSet.getString("TScore"));
				paymentInDetails.setAmount(StringUtils
						.getNumberFormat(DecimalFormatter.convertToFourDigit(resultSet.getString(Constants.AMOUNT))));
				paymentInDetails.setDateOfPayment(
						DateTimeFormatter.removeTimeFromDate(resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)));
				paymentInDetails.setMethod(resultSet.getString("PaymentMethodin"));
				paymentInDetails.setSellCurrency(resultSet.getString("SellCurrency"));
				paymentInDetails.setTradeContractNumber(resultSet.getString(Constants.TRADE_CONTRACT_NUMBER));
				//AT-1731 - SnehaZagade
				paymentInDetails.setBankName(resultSet.getString("BankName"));
				totalRecords = resultSet.getInt("TotalRows");
				paymentInDetailsList.add(paymentInDetails);
			}
			paymentDetails.setPayInDetailsTotalRecords(totalRecords);
			paymentDetails.setFurtherPaymentInDetails(paymentInDetailsList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return paymentInDetailsList;
	}
	
	protected List<FurtherPaymentOutDetails> setFurtherPaymentOutDetailList(Integer accountId,
			FurtherPaymentDetails paymentDetails, Connection connection) throws CompliancePortalException {
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<FurtherPaymentOutDetails> paymentOutDetailsList = new ArrayList<>();
		try {
			preparedStatement = connection
					.prepareStatement(RegistrationQueryConstant.GET_PAYMENTOUTINFO_FOR_ACCOUNT_SELECT.getQuery());
			preparedStatement.setInt(1, accountId);
			preparedStatement.setInt(2, 0);// Minrecord
			preparedStatement.setInt(3, 10);// MaxRecord
			resultSet = preparedStatement.executeQuery();
			Integer totalRecords = 0;
			while (resultSet.next()) {
				FurtherPaymentOutDetails paymentOutDetails = new FurtherPaymentOutDetails();
				// change done to fetch attributes json into POJO - Vishal J
				paymentOutDetails.setPaymentId(resultSet.getInt("poid"));
				if (null != resultSet.getString(Constants.BENEFICIARY_LAST_NAME)
						&& !resultSet.getString(Constants.BENEFICIARY_LAST_NAME).isEmpty()) {
					String beneficiaryName = new StringBuilder()
							.append(resultSet.getString(Constants.BENEFICIARY_FIRST_NAME)).append(" ")
							.append(resultSet.getString(Constants.BENEFICIARY_LAST_NAME)).toString();
					paymentOutDetails.setAccountName(beneficiaryName);

				} else {
					paymentOutDetails.setAccountName(resultSet.getString(Constants.BENEFICIARY_FIRST_NAME));
				}
				String accountNumber = resultSet.getString("AccountNumber");
				setAccountNumber(paymentOutDetails, accountNumber);
				paymentOutDetails.setAmount(StringUtils
						.getNumberFormat(DecimalFormatter.convertToFourDigit(resultSet.getString(Constants.AMOUNT))));
				paymentOutDetails.setBuyCurrency(resultSet.getString("BuyCurrency"));
				if (null != resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)) {
					paymentOutDetails.setDateOfPayment(
							DateTimeFormatter.removeTimeFromDate(resultSet.getTimestamp(Constants.DATE_OF_PAYMENT)));
				} else {
					paymentOutDetails.setDateOfPayment(Constants.DASH_DETAILS_PAGE);
				}
				// added by neelesh pant
				paymentOutDetails.setValuedate(DateTimeFormatter.dateFormat(resultSet.getString("MaturityDate")));
				totalRecords = resultSet.getInt("TotalRows");
				paymentOutDetails.setTradeContractNumber(resultSet.getString(Constants.TRADE_CONTRACT_NUMBER));
				if (null != resultSet.getString(Constants.PAYMENT_REFERENCE)
						&& !resultSet.getString(Constants.PAYMENT_REFERENCE).isEmpty()) {
					paymentOutDetails.setPaymentReference(resultSet.getString(Constants.PAYMENT_REFERENCE));
				}
				//AT-1731 - SnehaZagade
				paymentOutDetails.setBankName(resultSet.getString("BankName"));
				paymentOutDetailsList.add(paymentOutDetails);
			}
			paymentDetails.setPayOutDetailsTotalRecords(totalRecords);
			paymentDetails.setFurtherPaymentOutDetails(paymentOutDetailsList);
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		} finally {
			closeResultset(resultSet);
			closePrepareStatement(preparedStatement);
		}
		return paymentOutDetailsList;
	}

	private void setAccountNumber(FurtherPaymentOutDetails paymentOutDetails, String accountNumber) {
		if (null != accountNumber && !accountNumber.isEmpty()) {
			paymentOutDetails.setAccount(accountNumber);
		}
	}
	
	protected void setRiskGuardianScore(FurtherPaymentInDetails paymentInDetails, String riskScore, String tScore) {
		if (riskScore != null) {
			if (tScore == null || tScore.isEmpty())
				paymentInDetails.setRiskGuardianScore("-");
			else
				paymentInDetails.setRiskGuardianScore(tScore);
		} else {
			paymentInDetails.setRiskGuardianScore("-");
		}
	}
	
	protected void setDebtorName(FurtherPaymentInDetails paymentInDetails, String debtorName) {
		if (null != debtorName && !debtorName.isEmpty())
			paymentInDetails.setAccountName(debtorName);
		else
			paymentInDetails.setAccountName("-");
	}
}
