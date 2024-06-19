package com.currenciesdirect.gtg.compliance.dbport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.core.IDBService;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryRequest;
import com.currenciesdirect.gtg.compliance.core.domain.AccountHistoryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.Company;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.ContactHistoryResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.CorperateCompliance;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoRequest;
import com.currenciesdirect.gtg.compliance.core.domain.DeviceInfoResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.IDomain;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.LockResourceResponse;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogRequest;
import com.currenciesdirect.gtg.compliance.core.domain.ProviderResponseLogResponse;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationAccount;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCompany;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationContact;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationCorperateCompliance;
import com.currenciesdirect.gtg.compliance.core.domain.RegistrationRiskProfile;
import com.currenciesdirect.gtg.compliance.commons.domain.profile.request.RiskProfile;
import com.currenciesdirect.gtg.compliance.dbport.enums.UserLockResourceTypeEnum;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalErrors;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.ObjectUtils;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

public abstract class AbstractDBServiceLevel2 extends AbstractDBServiceLevel1 implements IDBService {
	
	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDBServiceLevel2.class);

	@Override
	public ProviderResponseLogResponse getProviderResponse(ProviderResponseLogRequest logRequest)
			throws CompliancePortalException {
		ProviderResponseLogResponse logResponse = new ProviderResponseLogResponse();
		String responseJson = null;
		try {
			responseJson = getProviderReponsebyEventServiceLogId(logRequest.getEventServiceLogId(),
					logRequest.getServiceType());
			logResponse.setResponseJson(responseJson);
		} catch (CompliancePortalException e) {
			throw e;
		} catch (Exception e) {
			throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
		}
		return logResponse;
	}
	//This method is added to resolve sonar issue
	
		private String serviceTypeResponse(String response)
		{
			String responseJson="";
			try {
				JSONObject jsonObject = new JSONObject(response);
				responseJson = jsonObject.get("providerResponse").toString();
				} 
			catch (Exception e) {
				LOGGER.debug("Error", e);
				responseJson=response;
			}
			return responseJson;
		}
		
		
		/**
		 * Gets the provider reponseby event service log id.
		 *
		 * @param evenServiceLogID
		 *            the even service log ID
		 * @param serviceType
		 *            the service type
		 * @return the provider reponseby event service log id
		 * @throws CompliancePortalException
		 *             the compliance portal exception
		 */
		@SuppressWarnings("squid:S5361")
		protected String getProviderReponsebyEventServiceLogId(Integer evenServiceLogID, String serviceType)
				throws CompliancePortalException {
			String responseJson = "";
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_PROVIDER_RESPONSE.getQuery());
				preparedStatement.setInt(1, evenServiceLogID);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {

					responseJson = rs.getString("ProviderResponse");
					responseJson = responseJson.replaceAll("\n", "");
					responseJson = responseJson.replaceAll("\\\\", "");
					responseJson = responseJson.replaceAll("\"\\{", "\\{");
					responseJson = responseJson.replaceAll("\\}\"", "\\}");

					if ("SANCTION".equalsIgnoreCase(serviceType) || "KYC".equalsIgnoreCase(serviceType)) {
						responseJson=serviceTypeResponse(responseJson);
					}
				}
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return responseJson;
		}
		
		@Override
		public DeviceInfoResponse getDeviceInfo(DeviceInfoRequest deviceInfoRequest)
				throws CompliancePortalException {
			DeviceInfoResponse deviceInfoResponse = new DeviceInfoResponse();
			List<IDomain> deviceInfoResponseList = null;
			try {
				deviceInfoResponseList = getDeviceInfoByAccountId(deviceInfoRequest.getAccountId());
				deviceInfoResponse.setDeviceInfoList(deviceInfoResponseList);
			} catch (CompliancePortalException e) {
				throw e;
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_GETTING_DEVICE_INFO_DATA, e);
			}
			return deviceInfoResponse;
		}
		
		
		protected List<IDomain> getDeviceInfoByAccountId(Integer accountId)	throws CompliancePortalException {
			List<IDomain> deviceInfoResponseList = new ArrayList<>();
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_DEVICE_INFO.getQuery());
				preparedStatement.setInt(1, accountId);
				
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					DeviceInfoResponse deviceInfoResponse = new DeviceInfoResponse();
					deviceInfoResponse.setUserAgent(rs.getString("UserAgent"));
					deviceInfoResponse.setEventType(rs.getString("Type"));
					deviceInfoResponse.setCreatedOn(rs.getString("CreatedOn"));
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
		public AccountHistoryResponse getAccountHistory(AccountHistoryRequest accountHistoryRequest)throws CompliancePortalException {
			AccountHistoryResponse accountHistoryResponse = null;
			List<ContactHistoryResponse> contactHistoryResponse=null;
			DeviceInfoResponse deviceInfoResponse=null;
			try {
				accountHistoryResponse = getAccountHistoryByAccountId(accountHistoryRequest.getAccountId());
				contactHistoryResponse = getContactHistoryByContactId(accountHistoryRequest.getAccountId());
				deviceInfoResponse = getDeviceInfoForSignup(accountHistoryRequest.getAccountId());
				accountHistoryResponse.setContactHistory(contactHistoryResponse);
				accountHistoryResponse.setDeviceInfo(deviceInfoResponse);
			} catch (CompliancePortalException e) {
				throw e;
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			}
			return accountHistoryResponse;
		}
		private AccountHistoryResponse getAccountHistoryByAccountId(Integer accountId)throws CompliancePortalException {
			AccountHistoryResponse accountHistoryResponse = new AccountHistoryResponse();
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_ACCOUNT_HISTORY.getQuery());
				preparedStatement.setInt(1, accountId);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					RegistrationAccount regCfxAccount = JsonConverterUtil.convertToObject(RegistrationAccount.class, rs.getString(Constants.ATTRIBUTES));
					
					if (regCfxAccount != null) {
						accountHistoryResponse.setClientType(regCfxAccount.getCustType());
						accountHistoryResponse.setCurrencyPair(regCfxAccount.getSellingCurrency() + "  -  " + regCfxAccount.getBuyingCurrency());
						accountHistoryResponse.setEstimTransValue(regCfxAccount.getValueOfTransaction());
						accountHistoryResponse.setPurposeOfTrans(regCfxAccount.getPurposeOfTran());
						accountHistoryResponse.setServiceRequired(regCfxAccount.getServiceRequired());
						accountHistoryResponse.setSource(regCfxAccount.getSource());
						accountHistoryResponse.setSourceOfFund(regCfxAccount.getSourceOfFund());
						accountHistoryResponse.setRefferalText(regCfxAccount.getReferralText());
						accountHistoryResponse.setAffiliateName(regCfxAccount.getAffiliateName());
						accountHistoryResponse.setRegMode(regCfxAccount.getRegistrationMode());
						getCompanyHistory(accountHistoryResponse, regCfxAccount);
						getRiskProfileHistory(accountHistoryResponse, regCfxAccount);
						getCorperateComplianceHistory(accountHistoryResponse, regCfxAccount);
						accountHistoryResponse.setName(regCfxAccount.getAccountName());
						accountHistoryResponse.setWebSite(regCfxAccount.getWebsite());
						accountHistoryResponse.setSourceLookup(regCfxAccount.getSubSource());
						accountHistoryResponse.setAvgTransactionValue(String.valueOf(regCfxAccount.getAverageTransactionValue()));
						accountHistoryResponse.setCountriesOfOperation(regCfxAccount.getCountriesOfOperation());
						accountHistoryResponse.setAnnualFXRequirement(regCfxAccount.getTurnover());
						accountHistoryResponse.setTradeAccountNum(regCfxAccount.getTradeAccountNumber());
						accountHistoryResponse.setTradeAccountId(regCfxAccount.getTradeAccountID().toString());
						accountHistoryResponse.setAccSFID(regCfxAccount.getAccSFID());
						accountHistoryResponse.setAddCampaign(regCfxAccount.getAdCampaign());
						accountHistoryResponse.setAffiliateNumber(regCfxAccount.getAffiliateNumber());
						accountHistoryResponse.setBranch(regCfxAccount.getBranch());
						accountHistoryResponse.setBuyingCurrency(regCfxAccount.getBuyingCurrency());
						accountHistoryResponse.setChannel(regCfxAccount.getChannel());
						accountHistoryResponse.setExtendedReferral(regCfxAccount.getExtendedReferral());
						accountHistoryResponse.setCountryOfInterest(regCfxAccount.getCountryOfInterest());
						accountHistoryResponse.setKeywords(regCfxAccount.getKeywords());
						accountHistoryResponse.setReferralId(regCfxAccount.getReferral());
						accountHistoryResponse.setSearchEngine(regCfxAccount.getSearchEngine());
						accountHistoryResponse.setSellingCurrency(regCfxAccount.getSellingCurrency());
						accountHistoryResponse.setSubSource(regCfxAccount.getSubSource());
						accountHistoryResponse.setTradeName(regCfxAccount.getTradingName());
						accountHistoryResponse.setTurnover(regCfxAccount.getTurnover());
						accountHistoryResponse.setComplianceDoneOn(DateTimeFormatter.removeTimeFromDate(rs.getTimestamp("compliancedoneon")));
						accountHistoryResponse.setDateOfReg(DateTimeFormatter.removeTimeFromDate(rs.getTimestamp("createdon")));
						setLegalEntity(accountHistoryResponse, regCfxAccount);
						//PFX 
						accountHistoryResponse.setEstimTransValue(regCfxAccount.getValueOfTransaction());
						accountHistoryResponse.setPurposeOfTrans(regCfxAccount.getPurposeOfTran());
						accountHistoryResponse.setSourceOfFund(regCfxAccount.getSourceOfFund());
					}
				}
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			accountHistoryResponse.setIsAllFieldsEmpty(ObjectUtils.isAllFieldsEmpty(accountHistoryResponse));
			return accountHistoryResponse;
		}
		/**
		 * @param accountHistoryResponse
		 * @param regCfxAccount
		 */
		private void setLegalEntity(AccountHistoryResponse accountHistoryResponse, RegistrationAccount regCfxAccount) {
			if(regCfxAccount.getLegalEntity() != null && !regCfxAccount.getLegalEntity().isEmpty())
				accountHistoryResponse.setLegalEntity(regCfxAccount.getLegalEntity());
			else accountHistoryResponse.setLegalEntity(Constants.DASH_UI);
		}
		/**
		 * @param accountHistoryResponse
		 * @param regCfxAccount
		 */
		private void getCorperateComplianceHistory(AccountHistoryResponse accountHistoryResponse,
				RegistrationAccount regCfxAccount) {
			if (null != regCfxAccount.getRiskProfile()) {
				accountHistoryResponse.setCorporateCompliance(getCorporateCompliance(regCfxAccount.getCorperateCompliance()));
			}
		}
		
		/**
		 * @param accountHistoryResponse
		 * @param regCfxAccount
		 */
		private void getRiskProfileHistory(AccountHistoryResponse accountHistoryResponse,
				RegistrationAccount regCfxAccount) {
			if (null != regCfxAccount.getRiskProfile()) {
				accountHistoryResponse.setRiskProfile(getRiskProfile(regCfxAccount.getRiskProfile()));
			}
		}
		/**
		 * @param accountHistoryResponse
		 * @param regCfxAccount
		 */
		private void getCompanyHistory(AccountHistoryResponse accountHistoryResponse, RegistrationAccount regCfxAccount) {
			if (null != regCfxAccount.getCompany()) {
				accountHistoryResponse.setCompany(getCompany(regCfxAccount.getCompany()));
			}
		}
		
		

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
		
		
		private List<ContactHistoryResponse> getContactHistoryByContactId(Integer accountId)throws CompliancePortalException {
			List<ContactHistoryResponse> contactHistoryList=new ArrayList<>();
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			try {
				String time = SearchCriteriaUtil.getHistoryInfoTime();
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_CONTACT_HISTORY.getQuery());
				preparedStatement.setString(1, time);
				preparedStatement.setInt(2, accountId);
				
				
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					ContactHistoryResponse contactHistoryResponse = new ContactHistoryResponse();
					RegistrationContact regContact = JsonConverterUtil.convertToObject(RegistrationContact.class, rs.getString(Constants.ATTRIBUTES));
				    
					if (regContact != null) {
						contactHistoryResponse.setAddress(regContact.getAddress1());
						contactHistoryResponse.setCountryOfResidence(regContact.getCountry());
						contactHistoryResponse.setEmail(regContact.getEmail());
						contactHistoryResponse.setMobile(regContact.getPhoneMobile());
						contactHistoryResponse.setNationality(regContact.getNationality());
						contactHistoryResponse.setOccupation(regContact.getOccupation());
						contactHistoryResponse.setPhone(regContact.getPhoneHome());
						contactHistoryResponse.setIsUsClient(regContact.getCountry() != null && "USA".equals(regContact.getCountry()));
						contactHistoryResponse.setIpAddress(regContact.getIpAddress());
						contactHistoryResponse.setDateofbirth(DateTimeFormatter.dateFormat(regContact.getDob()));
						setWorkphone(contactHistoryResponse, regContact);
						//changes done to show full name on UI
						setName(contactHistoryResponse, regContact);
						//CFX Contact Extra Fields 
						contactHistoryResponse.setJobTitle(regContact.getJobTitle());
						contactHistoryResponse.setPositionOfSignificance(regContact.getPositionOfSignificance());
						contactHistoryResponse.setAuthorisedSignatory(regContact.getAuthorisedSignatory());
						contactHistoryResponse.setAddressType(regContact.getAddressType());
						contactHistoryResponse.setCrmContactId(regContact.getContactSFID());
						contactHistoryResponse.setTradeContactId(regContact.getTradeContactID().toString());
						contactHistoryResponse.setAreaNumber(regContact.getAreaNumber());
						contactHistoryResponse.setAustraliaRTACardNumber(regContact.getAustraliaRTACardNumber());
						contactHistoryResponse.setAza(regContact.getAza());
						contactHistoryResponse.setBuildingNumber(regContact.getBuildingNumber());
						contactHistoryResponse.setCivicNumber(regContact.getCivicNumber());
						contactHistoryResponse.setCountryOfBirth(regContact.getCountryOfBirth());
						contactHistoryResponse.setDistrict(regContact.getDistrict());
						contactHistoryResponse.setPostCode(regContact.getPostCode());
						contactHistoryResponse.setPrefName(regContact.getPreferredName());
						contactHistoryResponse.setPrefecture(regContact.getPrefecture());
						contactHistoryResponse.setPrimaryPhoneNumber(regContact.getPrimaryPhone());
						contactHistoryResponse.setRegionSuburb(regContact.getRegion());
						contactHistoryResponse.setResidentialStatus(regContact.getResidentialStatus());
						contactHistoryResponse.setSecondEmail(regContact.getSecondEmail());
						contactHistoryResponse.setSecondSurname(regContact.getSecondSurname());
						contactHistoryResponse.setStateOfBirth(regContact.getStateOfBirth());
						contactHistoryResponse.setStateProvinceCounty(regContact.getState());
						contactHistoryResponse.setStreet(regContact.getStreet());
						contactHistoryResponse.setStreetNumber(regContact.getStreetNumber());
						contactHistoryResponse.setStreetType(regContact.getStreetType());
						contactHistoryResponse.setSubBuilding(regContact.getSubBuildingorFlat());
						contactHistoryResponse.setSubCity(regContact.getSubCity());
						contactHistoryResponse.setTitle(regContact.getTitle());
						contactHistoryResponse.setTownCityMuncipalty(regContact.getCity());
						contactHistoryResponse.setUnitNumber(regContact.getUnitNumber());
						contactHistoryResponse.setYearsInAddress(regContact.getYearsInAddress());
						contactHistoryResponse.setDlLicenseNumber(regContact.getDlLicenseNumber());
						contactHistoryResponse.setDlExpiryDate(regContact.getDlExpiryDate());
						contactHistoryResponse.setDlCardNumber(regContact.getDlCardNumber());
						contactHistoryResponse.setDlCountryCode(regContact.getDlCountryCode());
						contactHistoryResponse.setDlStateCode(regContact.getDlStateCode());
						contactHistoryResponse.setDlVersionNumber(regContact.getDlVersionNumber());
						contactHistoryResponse.setFirstName(regContact.getFirstName());
						contactHistoryResponse.setFloorNumber(regContact.getFloorNumber());
						contactHistoryResponse.setGender(regContact.getGender());
						contactHistoryResponse.setPhoneHome(regContact.getPhoneHome());
						contactHistoryResponse.setLastName(regContact.getLastName());
						contactHistoryResponse.setMedicareCardNumber(regContact.getMedicareCardNumber());
						contactHistoryResponse.setMedicareReferenceNumber(regContact.getMedicareReferenceNumber());
						contactHistoryResponse.setMiddleName(regContact.getMiddleName());
						contactHistoryResponse.setMothersSurname(regContact.getMothersSurname());
						contactHistoryResponse.setMunicipalityOfBirth(regContact.getMunicipalityOfBirth());
						contactHistoryResponse.setNationalIdType(regContact.getNationalIdType());
						contactHistoryResponse.setPassportFamilyNameAtBirth(regContact.getPassportFamilyNameAtBirth());
						contactHistoryResponse.setPassportPlaceOfBirth(regContact.getPassportPlaceOfBirth());
						contactHistoryResponse.setPassportCountryCode(regContact.getPassportCountryCode());
						contactHistoryResponse.setPassportExiprydate(regContact.getPassportExiprydate());
						contactHistoryResponse.setPassportFullName(regContact.getPassportFullName());
						contactHistoryResponse.setPassportMRZLine1(regContact.getPassportMRZLine1());
						contactHistoryResponse.setPassportMRZLine2(regContact.getPassportMRZLine2());
						contactHistoryResponse.setPassportNameAtCitizenship(regContact.getPassportNameAtCitizenship());
						contactHistoryResponse.setPassportNumber(regContact.getPassportNumber());
						contactHistoryResponse.setNationalIdNumber(regContact.getNationalIdNumber());
						contactHistoryResponse.setIsAllFieldsEmpty(ObjectUtils.isAllFieldsEmpty(contactHistoryResponse));
						contactHistoryList.add(contactHistoryResponse);
					}
				}
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			
			return contactHistoryList;
		}
		/**
		 * @param contactHistoryResponse
		 * @param regContact
		 */
		private void setWorkphone(ContactHistoryResponse contactHistoryResponse, RegistrationContact regContact) {
			if((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn())) && (!StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
				contactHistoryResponse.setWorkphone(regContact.getPhoneWork()+";"+regContact.getPhoneWorkExtn());
			if((!StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn())) && (StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
				contactHistoryResponse.setWorkphone(regContact.getPhoneWorkExtn());
			if((StringUtils.isNullOrEmpty(regContact.getPhoneWorkExtn()))&& (!StringUtils.isNullOrEmpty(regContact.getPhoneWork())))
				contactHistoryResponse.setWorkphone(regContact.getPhoneWork());
		}
		
		
		
		/**
		 * @param contactHistoryResponse
		 * @param regContact
		 */
		private void setName(ContactHistoryResponse contactHistoryResponse, RegistrationContact regContact) {
			if(regContact.getMiddleName() != null && !regContact.getMiddleName().trim().isEmpty()){
				String fullName = new StringBuilder().append(regContact.getFirstName()).append(" "+regContact.getMiddleName()).append(" "+regContact.getLastName()).toString();
				contactHistoryResponse.setName(fullName);
			} else{
				contactHistoryResponse.setName(regContact.getFirstName()+ " "+regContact.getLastName());
			}
		}
		
		
		private DeviceInfoResponse getDeviceInfoForSignup(Integer accountId)throws CompliancePortalException {
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			DeviceInfoResponse deviceInfo=new DeviceInfoResponse();
			try {
				connection = getConnection(Boolean.TRUE);
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_DEVICE_FOR_SIGNUP.getQuery());
				preparedStatement.setInt(1, accountId);
				rs = preparedStatement.executeQuery();
				while (rs.next()) {
					deviceInfo.setApplicationId(rs.getString("CDAppID"));
					deviceInfo.setApplicationVersion(rs.getString("CDAppVersion"));
					deviceInfo.setBrowserLanguage(rs.getString("BrowserLanguage"));
					deviceInfo.setBrowserOnline(rs.getBoolean("BrowserOnline"));
					deviceInfo.setBrowserVersion(rs.getString("BrowserVersion"));
					deviceInfo.setDeviceId(rs.getString("DeviceID"));
					deviceInfo.setDeviceManufacturer(rs.getString("DeviceManufacturer"));
					deviceInfo.setDeviceName(rs.getString("DeviceName"));
					deviceInfo.setDeviceType(rs.getString("DeviceType"));
					deviceInfo.setDeviceVersion(rs.getString("DeviceVersion"));
					if(null != rs.getTimestamp("OSTimestamp")){
						deviceInfo.setoSTimestamp(DateTimeFormatter.removeTimeFromDate(rs.getTimestamp("OSTimestamp")));
					}
					deviceInfo.setoSType(rs.getString("OSType"));
					deviceInfo.setUserAgent(rs.getString("UserAgent"));
				}
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			
			return deviceInfo;
		}
		
		
		@Override
		public LockResourceResponse insertLockResource(LockResourceDBDto updatelockResourceDBDto)
				throws CompliancePortalException {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			LockResourceResponse lockResourceResponse = null;
			try {
				connection = getConnection(Boolean.FALSE);

				lockResourceResponse = checkLockResource(updatelockResourceDBDto.getResourceId(),
						updatelockResourceDBDto.getResourceType(), connection);
				if (lockResourceResponse != null) {
					return lockResourceResponse;
				}
				lockResourceResponse = new LockResourceResponse();

				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.INSERT_USER_RESOURCE_LOCK_PFX.getQuery(),
						Statement.RETURN_GENERATED_KEYS);
				preparedStatement.setString(1, updatelockResourceDBDto.getUserId());
				preparedStatement.setInt(2, UserLockResourceTypeEnum.getDbStatusFromUiStatus(updatelockResourceDBDto.getResourceType()));
				preparedStatement.setInt(3, updatelockResourceDBDto.getResourceId());
				preparedStatement.setTimestamp(4, updatelockResourceDBDto.getLockReleasedOn());
				preparedStatement.setString(5, updatelockResourceDBDto.getCreatedBy());
				preparedStatement.setTimestamp(6, updatelockResourceDBDto.getCreatedOn());
				preparedStatement.setTimestamp(7, null);

				int updateCount = preparedStatement.executeUpdate();
				if (updateCount == 0) {
					lockResourceResponse.setStatus("Fail");
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				}
				rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					lockResourceResponse.setUserResourceId(rs.getInt(1));
					lockResourceResponse.setStatus("Pass");
					lockResourceResponse.setResourceId(updatelockResourceDBDto.getResourceId());
					lockResourceResponse.setName(updatelockResourceDBDto.getUserId());
				}

			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_INSERTING_DATA, e);
			} finally {
				closeResultset(rs);
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return lockResourceResponse;
		}

		private LockResourceResponse checkLockResource(Integer resourceId, String resourceType, Connection connection)
				throws CompliancePortalException {
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.GET_RECORDLOCK_STATUS.getQuery());
				preparedStatement.setInt(1, resourceId);
				preparedStatement.setInt(2, UserLockResourceTypeEnum.getDbStatusFromUiStatus(resourceType));
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) {
					LockResourceResponse lockResourceDBDto = new LockResourceResponse();
					lockResourceDBDto.setName(resultSet.getString("UserID"));
					lockResourceDBDto.setUserResourceId(resultSet.getInt("Id"));
					return lockResourceDBDto;
				}
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_FETCHING_DATA, e);
			} finally {
				closeResultset(resultSet);
				closePrepareStatement(preparedStatement);
			}
			return null;
		}
		
		@Override
		public LockResourceResponse updateLockResource(LockResourceDBDto updatelockResourceDBDto)
				throws CompliancePortalException {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			LockResourceResponse lockResourceResponse = new LockResourceResponse();
			try {
				connection = getConnection(Boolean.FALSE);
				preparedStatement = connection.prepareStatement(RegistrationQueryConstant.UPDATE_USER_RESOURCE_LOCK_PFX.getQuery());
				preparedStatement.setTimestamp(1, updatelockResourceDBDto.getLockReleasedOn());
				preparedStatement.setInt(2, updatelockResourceDBDto.getId());
				int updateCount = preparedStatement.executeUpdate();
				if (updateCount == 0) {
					lockResourceResponse.setStatus("Fail");
					throw new CompliancePortalException(CompliancePortalErrors.INVALID_REQUEST);
				} else {
					lockResourceResponse.setStatus("Pass");
					lockResourceResponse.setUserResourceId(updatelockResourceDBDto.getId());
					lockResourceResponse.setResourceId(updatelockResourceDBDto.getResourceId());
					lockResourceResponse.setName(updatelockResourceDBDto.getUserId());
				}

			} catch (CompliancePortalException exception) {
				throw exception;
			} catch (Exception e) {
				throw new CompliancePortalException(CompliancePortalErrors.ERROR_WHILE_UPDATING_DATA, e);
			} finally {
				closePrepareStatement(preparedStatement);
				closeConnection(connection);
			}
			return lockResourceResponse;
		}

		
}
