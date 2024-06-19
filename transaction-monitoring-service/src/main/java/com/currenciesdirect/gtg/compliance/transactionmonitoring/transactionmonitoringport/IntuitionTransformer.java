/**
 * 
 */
package com.currenciesdirect.gtg.compliance.transactionmonitoring.transactionmonitoringport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.BooleanUtils;
import org.springframework.util.StringUtils;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.CardDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.IPAddressDetails;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringContactRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsInRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPaymentsOutRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringSignupRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsAtlasFlags;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsCalculations;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsDebitCard;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsDeviceAndIP;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionFundsTransactional;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionPostCardAccountRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionPostCardChargebackRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionPostCardIPDeviceRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionPostCardRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionPostCardTransactionRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupAccountProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupCFXLegalEntityProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupCardProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupContactProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupIPDeviceProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.core.domain.IntuitionSignupProviderRequest;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringErrors;
import com.currenciesdirect.gtg.compliance.transactionmonitoring.exception.TransactionMonitoringException;

/**
 * The Class IntuitionTransformer.
 */
public class IntuitionTransformer {

	/** The Constant LOG. */
	private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(IntuitionTransformer.class);

	/** The intuition transformer. */
	private static IntuitionTransformer intuitionTransformer = null;
	
	private static final String TIME_STRING = " 00:00:00";
	
	private static final String CHARACTER = "[\r\n]|[\t+]"; 
	
	private static final String CFXETAILER = "CFX-Etailer"; 
	

	/**
	 * Instantiates a new intuition transformer.
	 */
	private IntuitionTransformer() {

	}

	/**
	 * Gets the single instance of IntuitionTransformer.
	 *
	 * @return single instance of IntuitionTransformer
	 */
	public static IntuitionTransformer getInstance() {
		if (intuitionTransformer == null) {
			intuitionTransformer = new IntuitionTransformer();
		}
		return intuitionTransformer;
	}

	/**
	 * Transform new signup account request.
	 *
	 * @param request the request
	 * @return the intuition signup account provider request
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public IntuitionSignupProviderRequest transformNewSignupAccountRequest(
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException {

		LOG.debug("Intuition transformNewSignupRequest : STARTED ");
		
		TransactionMonitoringAccountRequest accRequest = request.getTransactionMonitoringAccountRequest();
		List<TransactionMonitoringContactRequest> contacts = request.getTransactionMonitoringContactRequests();
		
		IntuitionSignupProviderRequest providerSignupRequest = new IntuitionSignupProviderRequest();
		
		List<IntuitionSignupAccountProviderRequest> providerAccountRequest = new ArrayList<>();
		List<IntuitionSignupContactProviderRequest> providerContacts = new ArrayList<>();
		List<IntuitionSignupCFXLegalEntityProviderRequest> providerCfxRequest = new ArrayList<>();
		List<IntuitionSignupIPDeviceProviderRequest> providerIpRequest = new ArrayList<>();
		List<IntuitionSignupCardProviderRequest> providerCardRequest = new ArrayList<>();
		
		IntuitionSignupAccountProviderRequest providerAccount = new IntuitionSignupAccountProviderRequest();
		IntuitionSignupCFXLegalEntityProviderRequest providerCfx = new IntuitionSignupCFXLegalEntityProviderRequest();
		IntuitionSignupIPDeviceProviderRequest providerIp = new IntuitionSignupIPDeviceProviderRequest();
		IntuitionSignupCardProviderRequest providerCard = new IntuitionSignupCardProviderRequest();
		
		try {
			providerSignupRequest.setTradeAccNumber(accRequest.getTradeAccountNumber());
			if(accRequest.getRegistrationDateTime()== null || accRequest.getRegistrationDateTime().isEmpty()) {
				providerSignupRequest.setRegDateTime(accRequest.getCreatedOn()); //Add for AT-4763
			}else {
				providerSignupRequest.setRegDateTime(accRequest.getRegistrationDateTime());
			}
			providerSignupRequest.setChannel(accRequest.getChannel());
			providerSignupRequest.setActName(accRequest.getAccountName());
			providerSignupRequest.setCountryInterest(accRequest.getCountryOfInterest());
			providerSignupRequest.setTradeName(accRequest.getTradingName());
			providerSignupRequest.setTrasactionPurpose(accRequest.getPurposeOfTran());
			providerSignupRequest.setOpCountry(accRequest.getCountriesOfOperation());
			providerSignupRequest.setServiceReq(accRequest.getServiceRequired());
			providerSignupRequest.setTxnValue(accRequest.getValueOfTransaction());
			providerSignupRequest.setSourceOfFund(accRequest.getSourceOfFund());
			providerSignupRequest.setSource(accRequest.getSource());
			providerSignupRequest.setSubSource(accRequest.getSubSource());
			providerSignupRequest.setReferral(accRequest.getReferral());
			providerSignupRequest.setReferralText(accRequest.getReferralText());
			providerSignupRequest.setExtendedReferral(accRequest.getExtendedReferral());
			providerSignupRequest.setAdCampaign(accRequest.getAdCampaign());
			providerSignupRequest.setKeywords(accRequest.getKeywords());
			providerSignupRequest.setSearchEngine(accRequest.getSearchEngine());
			providerSignupRequest.setAffiliateName(accRequest.getAffiliateName());
			providerSignupRequest.setRegMode(accRequest.getRegistrationMode());
			providerSignupRequest.setOrganizationLegalEntity(accRequest.getCustLegalEntity());
		    if(providerSignupRequest.getEtailer() != null && providerSignupRequest.getEtailer().equalsIgnoreCase("true")) {
				providerSignupRequest.setCustType(CFXETAILER);
			}else {
				providerSignupRequest.setCustType(accRequest.getCustType());
			}
			providerSignupRequest.setBuyingCurrency(accRequest.getBuyingCurrency());
		    providerSignupRequest.setSellingCurrency(accRequest.getSellingCurrency());
			providerSignupRequest.setEtailer((accRequest.getCompany() != null) ? accRequest.getCompany().getEtailer() : null);//AT-5275
			
			//Logs added for AT-5404
			LOG.warn("\n------Compliance Log in IntuitionTransformer before formatting : {} \n",accRequest.getComplianceLog());
			providerSignupRequest.setComplianceLog((accRequest.getComplianceLog() != null)
					? accRequest.getComplianceLog().replaceAll("[\"]", "") : null); //Added for AT-5230
			LOG.warn("\n------Compliance Log in IntuitionTransformer after formatting : {} \n",providerSignupRequest.getComplianceLog());
			
			if(accRequest.getCustType()!= null && accRequest.getCustType().equalsIgnoreCase("PFX") && accRequest.getConversionPrediction() != null) {
				providerSignupRequest.setConversionFlag(accRequest.getConversionPrediction().getConversionFlag());
				providerSignupRequest.setConversionProbability((accRequest.getConversionPrediction().getConversionProbability() != null) ?
							accRequest.getConversionPrediction().getConversionProbability().toString() : null);
				providerSignupRequest.setEtvBand(accRequest.getConversionPrediction().geteTVBand());
			}

			providerSignupRequest.setIsCardApply(request.getIsCardApply());
			providerSignupRequest.setRegistrationLoadCriteria(request.getRegistrationLoadCriteria()); //AT-5127

			providerAccount.setAccountId(
					(accRequest.getTradeAccountID() != null) ? accRequest.getTradeAccountID().toString()
							: null);
			providerAccount.setAccSfId(accRequest.getAccSFID());
			providerAccount.setBranch(accRequest.getBranch());
			providerAccount.setAffiliateNumber(accRequest.getAffiliateNumber());
			providerAccount.setSecondaryAddressPresent(accRequest.getSecondaryAddressPresent());
			providerAccount.setCardDeliveryToSecondaryAddress(accRequest.getCardDeliveryToSecondaryAddress());
			//Add for AT-5318
			providerAccount.setParentType(accRequest.getParentType()); 
			providerAccount.setParentID(accRequest.getParentTitanAccNum());   
			providerAccount.setMetInPerson(accRequest.getMetInPerson()); 
			providerAccount.setSelfieOnFile(accRequest.getSelfieOnFile());  
			providerAccount.setVulnerabilityCharacteristic(accRequest.getVulnerabilityCharacteristic()); 
			//Add for AT-5376
			if(accRequest.getNumberOfChilds()!= null && !accRequest.getNumberOfChilds().isEmpty())	
				providerAccount.setNoOfChild(Integer.parseInt(accRequest.getNumberOfChilds()));	
			
			providerAccount.setLegacyAccountAuroraTitanCustomerNo(accRequest.getLegacyTradeAccountNumber()); //Add for AT-5393
			providerAccount.setConsumerDutyScope(accRequest.getConsumerDutyScope()); //Added for AT-5457
			
			if (accRequest.getCustType() != null && (accRequest.getCustType().equalsIgnoreCase("CFX")
					|| accRequest.getCustType().equalsIgnoreCase(CFXETAILER))) {
				providerAccount.setBlacklist(accRequest.getBlacklist());
				providerAccount.setSanctionResult(accRequest.getSanctionResult());
				providerCfx.setTurnover(accRequest.getTurnover());
				if(accRequest.getCompany() != null) {
					transformCompanyData(accRequest, providerCfx);
				}
				if(accRequest.getCorperateCompliance() != null) {
					transformCorperateComplianceData(providerCfx, accRequest);
				}
				if(accRequest.getRiskProfile() != null) {
					transformRiskProfileData(accRequest, providerCfx);
				}
				transformCFXRequest(accRequest, providerCfx);
			}
			
			providerSignupRequest.setAccountStatus(accRequest.getAccountStatus());
			
			for (TransactionMonitoringContactRequest contact : contacts) {
				IntuitionSignupContactProviderRequest contactRequest =  transformNewSignupContactRequest(contact, request);
				contactRequest.setBuyingCurrency(accRequest.getBuyingCurrency());
				contactRequest.setSellingCurrency(accRequest.getSellingCurrency());
				contactRequest.setCustType(accRequest.getCustType());			
				providerContacts.add(contactRequest);
			}
			
			if(accRequest.getiPAddressDetails() != null) {
				transformIpAddresDetailsData(accRequest, providerIp);
			}
			
			if(accRequest.getCardDetails() != null) {
				transformCardDetailsData(accRequest, providerCard);
			}
			
			providerCardRequest.add(providerCard);
			providerAccountRequest.add(providerAccount);
			providerCfxRequest.add(providerCfx);
			providerIpRequest.add(providerIp);
			

			providerSignupRequest.setCard(providerCardRequest);
			providerSignupRequest.setAccount(providerAccountRequest);
			providerSignupRequest.setCfxLegalEntity(providerCfxRequest);
			providerSignupRequest.setContacts(providerContacts);
			providerSignupRequest.setIpDevice(providerIpRequest);
			
			
		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Intuition : transform Signup Account Request : ENDED {}", providerSignupRequest);

		return providerSignupRequest;
	}
	
	private void transformCFXRequest(TransactionMonitoringAccountRequest accRequest, 
			IntuitionSignupCFXLegalEntityProviderRequest providerCfx) throws ParseException {
		
		if (accRequest.getCompany() != null) {
			/*if (accRequest.getCompany().getIncorporationDate() != null) {
				if (accRequest.getCompany().getIncorporationDate().length() > 10)
					accRequest.setIncorporationDate(
							setDateFormatForIntuition(accRequest.getCompany().getIncorporationDate()));
				else
					accRequest.setIncorporationDate(accRequest.getCompany().getIncorporationDate());
			}*/
			if (accRequest.getCompany().gettAndcSignedDate() != null) {
				providerCfx.setTcSignedDate(accRequest.getCompany().gettAndcSignedDate());
			}
		}
		
		if(accRequest.getCorperateCompliance() != null && accRequest.getCorperateCompliance().getRegistrationDate() != null) {
			providerCfx.setRegistrationDate(accRequest.getCorperateCompliance().getRegistrationDate());
		}
	}


	/**
	 * Transform company data.
	 *
	 * @param providerSignupAccountRequest the provider signup account request
	 * @param accRequest the acc request
	 * @throws ParseException 
	 */
	private void transformCompanyData(TransactionMonitoringAccountRequest accRequest,
			IntuitionSignupCFXLegalEntityProviderRequest providerCfx) throws ParseException {
		
		providerCfx.setCompanyBillingAddress(accRequest.getCompany().getBillingAddress().replaceAll("\\t+", " ").replaceAll("\"+", ""));
		providerCfx.setCompanyPhone(accRequest.getCompany().getCompanyPhone());
		providerCfx.setShippingAddress(accRequest.getCompany().getShippingAddress());
		providerCfx.setVatNo(accRequest.getCompany().getVatNo());
		providerCfx.setCountryRegion(accRequest.getCompany().getCountryRegion());
		providerCfx.setOption(accRequest.getCompany().getOption());
		providerCfx.setTypeOfFinancialAccount(accRequest.getCompany().getTypeOfFinancialAccount());
		providerCfx.setCountryOfEstablishment(accRequest.getCompany().getCountryOfEstablishment());
		providerCfx.setCorporateType(accRequest.getCompany().getCorporateType());
		providerCfx.setRegistrationNo(accRequest.getCompany().getRegistrationNo());
		providerCfx.setIndustry(accRequest.getCompany().getIndustry());
		providerCfx.setWebsite(accRequest.getWebsite());
		if (accRequest.getCompany().getIncorporationDate() != null) {
			providerCfx.setIncorporationDate(accRequest.getCompany().getIncorporationDate());
			if (accRequest.getCompany().getIncorporationDate().length() > 10)
				providerCfx.setIncorporationDate(
						setDateFormatForIntuition(accRequest.getCompany().getIncorporationDate()));
			else
				providerCfx.setIncorporationDate(accRequest.getCompany().getIncorporationDate());
		}else{
			providerCfx.setIncorporationDate("1900-01-01");//Add for AT-5020
		}
		providerCfx.setEstNoTransactionsPcm(accRequest.getCompany().getEstNoTransactionsPcm());
		providerCfx.setOngoingDueDiligenceDate(accRequest.getCompany().getOngoingDueDiligenceDate()); //Add for AT-5393
	}
	
	/**
	 * Transform new signup contact request.
	 *
	 * @param contact the contact
	 * @param request the request
	 * @return the intuition signup contact provider request
	 * @throws TransactionMonitoringException the transaction monitoring exception
	 */
	public IntuitionSignupContactProviderRequest transformNewSignupContactRequest( TransactionMonitoringContactRequest contact,
			TransactionMonitoringSignupRequest request) throws TransactionMonitoringException {
		
		
		LOG.debug("Intuition transformNewSignupContactRequest : STARTED ");
		IntuitionSignupContactProviderRequest providerSignupContactRequest = new IntuitionSignupContactProviderRequest();

		
		try {
			providerSignupContactRequest.setAddress1((contact.getAddress1() != null)
					? contact.getAddress1().replaceAll(CHARACTER, " ").replaceAll("\"+", "") //AT-5201
					: null);
			providerSignupContactRequest.setAddressType(contact.getAddressType());
			providerSignupContactRequest.setAreaNumber(contact.getAreaNumber());
			providerSignupContactRequest.setAustraliaRtaCardNumber(contact.getAustraliaRTACardNumber());
			providerSignupContactRequest.setAuthorisedSignatory(contact.getAuthorisedSignatory());
			providerSignupContactRequest.setAza(contact.getAza());
			providerSignupContactRequest.setBlacklist(contact.getBlacklist());
			providerSignupContactRequest.setBuildingName((contact.getBuildingName() != null)
					? contact.getBuildingName().replaceAll(CHARACTER, " ").replaceAll("\"+", "") //AT-5201
					: null);
			providerSignupContactRequest.setCivicNumber(contact.getCivicNumber());
			//providerSignupContactRequest.setComplianceLog(contact.getComplianceLog());
			providerSignupContactRequest.setContactSfId(contact.getContactSFID());
			providerSignupContactRequest.setCountryOfBirth(contact.getCountryOfBirth());
			providerSignupContactRequest.setCountryOfNationality(contact.getNationality());
			providerSignupContactRequest.setCountryOfResidence(contact.getCountry());
			providerSignupContactRequest.setDesignation((contact.getDesignation() != null)
					? contact.getDesignation().replaceAll("\\t+", " ").replaceAll("\"+", "")
					: null);
			providerSignupContactRequest.setDistrict(contact.getDistrict());
			
			// AT-5431
			if (contact.getDob() != null && !contact.getDob().isEmpty()) {
				if (contact.getDob().length() > 10) {
					String dateOfBirth = setDateFormatForIntuition(contact.getDob());
					providerSignupContactRequest.setDob(dateOfBirth);
				} else {
					providerSignupContactRequest.setDob(contact.getDob());
				}
			} else {
				providerSignupContactRequest.setDob("1900-01-01"); // AT-5053
			} 
		
			if(contact.getDlExpiryDate() != null && contact.getDlExpiryDate().length() > 10) {
				String updateDate = setDateFormatForIntuition(contact.getDlExpiryDate());
				providerSignupContactRequest.setDrivingExpiry(updateDate);
			} else if(contact.getDlExpiryDate() != null) {
				providerSignupContactRequest.setDrivingExpiry(contact.getDlExpiryDate());
			}
			
			providerSignupContactRequest.setDrivingLicense(contact.getDlLicenseNumber());
			providerSignupContactRequest.setDrivingLicenseCardNumber(contact.getDlCardNumber());
			providerSignupContactRequest.setDrivingLicenseCountry(contact.getDlCountryCode());
			providerSignupContactRequest.setDrivingStateCode(contact.getDlStateCode());
			providerSignupContactRequest.setDrivingVersionNumber(contact.getDlVersionNumber());
			providerSignupContactRequest.setEIDVStatus(contact.geteIDVStatus());
			providerSignupContactRequest.setEmail(contact.getEmail());
			providerSignupContactRequest.setFirstName((contact.getFirstName() != null)
					? contact.getFirstName().replaceAll("\\t+", " ").replaceAll("\"+", "")
					: null);
			providerSignupContactRequest.setFloorNumber(contact.getFloorNumber());
			
			
			if (contact.getFraudPredictDate() != null) {
				providerSignupContactRequest.setFraudPredictDate(contact.getFraudPredictDate());
			}
			
			providerSignupContactRequest.setFraudPredictScore(contact.getFraudPredictScore());
			providerSignupContactRequest.setGender(contact.getGender());
			providerSignupContactRequest.setHomePhone(contact.getPhoneHome());
			providerSignupContactRequest.setHouseBuildingNumber(contact.getHouseBuildingNumber());
			providerSignupContactRequest.setIpAddress(contact.getIpAddress());
			providerSignupContactRequest.setIsPrimaryContact((contact.getPrimaryContact() != null) ? contact.getPrimaryContact().toString() : null);
			providerSignupContactRequest.setJobTitle((contact.getJobTitle() != null)
					? contact.getJobTitle().replaceAll("\\t+", " ").replaceAll("\"+", "")
					: null);
			providerSignupContactRequest.setLastName((contact.getLastName() != null)
					? contact.getLastName().replaceAll("\\t+", " ").replaceAll("\"+", "")
					: null);
			providerSignupContactRequest.setMedicareCardNumber(contact.getMedicareCardNumber());
			providerSignupContactRequest.setMedicareRefNumber(contact.getMedicareReferenceNumber());
			providerSignupContactRequest.setMiddleName((contact.getMiddleName() != null)
					? contact.getMiddleName().replaceAll("\\t+", " ").replaceAll("\"+", "")
					: null);
			providerSignupContactRequest.setMobilePhone(contact.getPhoneMobile());
			providerSignupContactRequest.setMothersSurname(contact.getMothersSurname());
			providerSignupContactRequest.setMunicipalityOfBirth(contact.getMunicipalityOfBirth());
			providerSignupContactRequest.setNationalIdNumber(contact.getNationalIdNumber());
			providerSignupContactRequest.setNationalIdType(contact.getNationalIdType());
			providerSignupContactRequest.setOccupation((contact.getOccupation() != null)
					? contact.getOccupation().replaceAll("\\t+", " ").replaceAll("\"+", "")
					: null);
			providerSignupContactRequest.setPassportBirthFamilyName(contact.getPassportFamilyNameAtBirth());
			providerSignupContactRequest.setPassportBirthPlace(contact.getPassportPlaceOfBirth());
			providerSignupContactRequest.setPassportCountryCode(contact.getPassportCountryCode());
			
			if(contact.getPassportExiprydate() != null && contact.getPassportExiprydate().length() > 10) {
				String updateDate = setDateFormatForIntuition(contact.getPassportExiprydate());
				providerSignupContactRequest.setPassportExipryDate(updateDate);
			} else if(contact.getPassportExiprydate() != null) {
				providerSignupContactRequest.setPassportExipryDate(contact.getPassportExiprydate());
			}
			
			providerSignupContactRequest.setPassportFullName(contact.getPassportFullName());
			providerSignupContactRequest.setPassportMrzLine1(contact.getPassportMRZLine1());
			providerSignupContactRequest.setPassportMrzLine2(contact.getPassportMRZLine2());
			providerSignupContactRequest.setPassportNameAtCitizen(contact.getPassportNameAtCitizenship());
			providerSignupContactRequest.setPassportNumber(contact.getPassportNumber());
			providerSignupContactRequest.setPositionOfSignificance(contact.getPositionOfSignificance());
			providerSignupContactRequest.setPostCode(contact.getPostCode());
			providerSignupContactRequest.setPrefecture(contact.getPrefecture());
			providerSignupContactRequest.setPrefName(contact.getPreferredName());
			providerSignupContactRequest.setPrimaryPhone(contact.getPrimaryPhone());
			providerSignupContactRequest.setResidentialStatus(contact.getResidentialStatus());
			providerSignupContactRequest.setSanctionResult(contact.getSanctionResult());
			providerSignupContactRequest.setSecondEmail(contact.getSecondEmail());
			providerSignupContactRequest.setSecondSurname(contact.getSecondSurname());
			providerSignupContactRequest.setStateOfBirth(contact.getStateOfBirth());
			providerSignupContactRequest.setStateProvinceCounty(contact.getState());
			providerSignupContactRequest.setStatusUpdateReason((contact.getStatusUpdateReason() != null) ? contact.getStatusUpdateReason().toString() : null);
			providerSignupContactRequest.setStreet(contact.getStreet());
			providerSignupContactRequest.setStreetNumber(contact.getStreetNumber());
			providerSignupContactRequest.setStreetType(contact.getStreetType());
			providerSignupContactRequest.setSubBuilding(contact.getSubBuildingorFlat());
			providerSignupContactRequest.setSubCity(contact.getSubCity());
			providerSignupContactRequest.setTitle(contact.getTitle());
			providerSignupContactRequest.setTownCityMuncipalty(contact.getCity());
			providerSignupContactRequest.setTradeContactId((contact.getTradeContactID() != null) ? contact.getTradeContactID().toString() : null);
			providerSignupContactRequest.setUnitNumber(contact.getUnitNumber());
			providerSignupContactRequest.setUpdateStatus(contact.getUpdateStatus());
			providerSignupContactRequest.setVersion(contact.getVersion());
			providerSignupContactRequest.setWatchlist((contact.getWatchlist() != null) ? contact.getWatchlist().toString() : null);
			providerSignupContactRequest.setWorkPhone(contact.getPhoneWork());
			providerSignupContactRequest.setWorkPhoneExt(contact.getPhoneWorkExtn());
			if(contact.getYearsInAddress() != null && !contact.getYearsInAddress().isEmpty()) {
				providerSignupContactRequest.setYearsInAddress(Double.parseDouble(contact.getYearsInAddress()));
			}		
			
			if (request.getTransactionMonitoringAccountRequest() != null) {
				providerSignupContactRequest
						.setTradeAccNumber(request.getTransactionMonitoringAccountRequest().getTradeAccountNumber());
			} else {
				providerSignupContactRequest.setTradeAccNumber(contact.getTradeAccNumber());
			}
			
			providerSignupContactRequest.setContactStatus(contact.getContactStatus());
			providerSignupContactRequest.setOnfido(contact.getOnfido());

			providerSignupContactRequest.setLastPasswordChangeDate(contact.getLastPasswordChangeDate());
			providerSignupContactRequest.setAppInstallDate(contact.getAppInstallDate());
			providerSignupContactRequest.setDeviceId(contact.getDeviceId());
			providerSignupContactRequest.setCustomCheck(contact.getCustomCheck()); //Add for AT-5393;

		} catch (Exception e) {
			throw new TransactionMonitoringException(TransactionMonitoringErrors.ERROR_WHILE_REQUEST_TRANSFORMATION);
		}
		LOG.debug("Intuition : transform Signup Contact Request : ENDED {}", providerSignupContactRequest);

		
		return providerSignupContactRequest;
		
	}

	//Added for AT-4528
	/**
	 * Sets the date format for intuition.
	 *
	 * @param date the date
	 * @return the string
	 * @throws ParseException the parse exception
	 */
	private String setDateFormatForIntuition(String date) throws ParseException {
		Date update = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
		return myFormat.format(update);
	}
	
	/**
	 * Transform payment in request.
	 *
	 * @param request the request
	 * @return the intuition funds in provider request
	 */
	public IntuitionFundsProviderRequest transformPaymentInRequest(TransactionMonitoringPaymentsInRequest request) {
		IntuitionFundsProviderRequest intuitionFundsInProviderRequest = new IntuitionFundsProviderRequest();
		
		intuitionFundsInProviderRequest.setTradeIdentificationNumber(request.getContractNumber()+"_"+request.getFundsInId());
		intuitionFundsInProviderRequest.setTradeContractNumber(request.getContractNumber());
		intuitionFundsInProviderRequest.setTradePaymentID(request.getTransactionId().toString());
		intuitionFundsInProviderRequest.setTradeAccountNumber(request.getTradeAccountNumber());
		intuitionFundsInProviderRequest.setTimestamp(request.getTimeStamp());
		//AT-4815
		if(request.getEtailer() != null && request.getEtailer().equalsIgnoreCase("true")) { 
			intuitionFundsInProviderRequest.setCustType(CFXETAILER);
		}else {
			intuitionFundsInProviderRequest.setCustType(request.getCustType());
		}
		intuitionFundsInProviderRequest.setPurposeOfTrade(request.getPurposeOfTrade());
		intuitionFundsInProviderRequest.setSellingAmount(request.getSellingAmount());
		intuitionFundsInProviderRequest.setSellCurrency(request.getSellCurrency());
		intuitionFundsInProviderRequest.setTransactionCurrency(request.getTransactionCurrency());
		intuitionFundsInProviderRequest.setCustomerLegalEntity(request.getCustomerLegalEntity());
		intuitionFundsInProviderRequest.setPaymentMethod(request.getPaymentMethod());
		intuitionFundsInProviderRequest.setPhone(request.getPhone());
		intuitionFundsInProviderRequest.setCurrencyCode(request.getCurrencyCode());
		intuitionFundsInProviderRequest
				.setDebtorName((request.getDebtorName() == null || request.getDebtorName().trim().isEmpty()) ? "--"
						: request.getDebtorName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")); // Add for AT-4942
		intuitionFundsInProviderRequest.setDebtorAccountNumber(
				(request.getDebatorAccountNumber() == null || request.getDebatorAccountNumber().trim().isEmpty()) ? "--"
						: request.getDebatorAccountNumber()); // Add for AT-4942
		intuitionFundsInProviderRequest.setBillAddressLine(
				(request.getBillAddressLine() != null) ? request.getBillAddressLine().replaceAll(CHARACTER, " ").replaceAll("\"+", "") : null);
		intuitionFundsInProviderRequest.setCountryOfFund(
				(request.getCountryOfFund() == null || request.getCountryOfFund().trim().isEmpty()) ? "--"
						: request.getCountryOfFund()); // Add for AT-4942
		intuitionFundsInProviderRequest.setWatchlist(request.getWatchlist().toString());
		
		List<IntuitionFundsTransactional> intuitionFundsTransactionals = new ArrayList<>();
		IntuitionFundsTransactional fundsTransactional = new IntuitionFundsTransactional();
		fundsTransactional.setTradeAccountNumber(request.getTradeAccountNumber());
		fundsTransactional.setTradeContactId(request.getTradeContactId().toString());
		fundsTransactional.setTrxType("FUNDSIN");
		fundsTransactional.setPurposeOfTrade(request.getPurposeOfTrade());
		fundsTransactional.setContractNumber(request.getContractNumber());
		fundsTransactional.setSellingAmountBaseValue(request.getSellingAmountBaseValue());
		fundsTransactional.setThirdPartyPayment(String.valueOf(request.isThirdPartyPayment()));
		//AT-5337
		fundsTransactional.setInitiatingParentContact(request.getInitiatingParentContact());
		intuitionFundsTransactionals.add(fundsTransactional);
		intuitionFundsInProviderRequest.setTransactional(intuitionFundsTransactionals);
		
		
		List<IntuitionFundsDebitCard> intuitionFundsDebitCard = new ArrayList<>();
		IntuitionFundsDebitCard fundsDebitCard = new IntuitionFundsDebitCard();
		fundsDebitCard
				.setDebtorName((request.getDebtorName() == null || request.getDebtorName().trim().isEmpty()) ? "--"
						: request.getDebtorName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")); // Add for AT-4942
		fundsDebitCard.setDebtorAccountNumber(
				(request.getDebatorAccountNumber() == null || request.getDebatorAccountNumber().trim().isEmpty()) ? "--"
						: request.getDebatorAccountNumber()); // Add for AT-4942
		fundsDebitCard.setCcFirstName((request.getCcFirstName() != null)
				? request.getCcFirstName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
				: null);
		fundsDebitCard.setBillAdZip(request.getBillAdZip());
		fundsDebitCard.setCountryOfFund(
				(request.getCountryOfFund() == null || request.getCountryOfFund().trim().isEmpty()) ? "--"
						: request.getCountryOfFund()); // Add for AT-4942
		fundsDebitCard.setIsThreeds(request.getIsThreeds());
		fundsDebitCard.setAvsResult(request.getAvsResult());
		fundsDebitCard.setDebitCardAddedDate(request.getDebitCardAddedDate());
		fundsDebitCard.setMessage(request.getMessage());
		fundsDebitCard.setRGID(request.getRGID());
		fundsDebitCard.setTScore(request.gettScore());
		fundsDebitCard.setTRisk(String.valueOf(request.gettRisk()));
		fundsDebitCard.setDsOutcome(request.getThreeDsOutcome());
		fundsDebitCard.setCVVCVCResult(request.getCvcResult());
		fundsDebitCard.setFraudsightReason(request.getFraudsightReason());
		fundsDebitCard.setFraudsightRiskLevel(request.getFraudsightRiskLevel());
		intuitionFundsDebitCard.add(fundsDebitCard);
		intuitionFundsInProviderRequest.setDebitCard(intuitionFundsDebitCard);
		
		List<IntuitionFundsDeviceAndIP> intuitionFundsDeviceAndIP = new ArrayList<>();
		IntuitionFundsDeviceAndIP fundsDeviceAndIP = new IntuitionFundsDeviceAndIP();
		fundsDeviceAndIP.setBrwsrType(request.getBrowserType());
		fundsDeviceAndIP.setBrwsrVersion(request.getBrowserVersion());
		fundsDeviceAndIP.setBrwsrLang(request.getBrowserLanguage());
		fundsDeviceAndIP.setBrowserOnline(request.getBrowserOnline());
		fundsDeviceAndIP.setVersion(request.getVersion() != null ? request.getVersion().toString() : null);
		fundsDeviceAndIP.setStatusUpdateReason(request.getStatusUpdateReason());
		fundsDeviceAndIP.setDeviceName(request.getDeviceName());
		fundsDeviceAndIP.setDeviceVersion(request.getDeviceVersion());
		fundsDeviceAndIP.setDeviceId(request.getDeviceId());
		fundsDeviceAndIP.setDeviceManufacturer(request.getDeviceManufacturer());
		fundsDeviceAndIP.setDeviceType(request.getDeviceType());
		fundsDeviceAndIP.setOsType(request.getOsType());
		fundsDeviceAndIP.setScreenResolution(request.getScreenResolution());
		intuitionFundsDeviceAndIP.add(fundsDeviceAndIP);
		intuitionFundsInProviderRequest.setDeviceAndIP(intuitionFundsDeviceAndIP);
		
		List<IntuitionFundsAtlasFlags> intuitionFundsAtlasFlags = new ArrayList<>();
		IntuitionFundsAtlasFlags fundsAtlasFlags = new IntuitionFundsAtlasFlags();
		fundsAtlasFlags.setUpdateStatus(request.getUpdateStatus());
		fundsAtlasFlags.setBlacklist(request.getBlacklistStatus());
		fundsAtlasFlags.setSanctionsContact(request.getSanctionContactStatus());
		fundsAtlasFlags.setCountryCheck(request.getCountryCheckStatus());
		fundsAtlasFlags.setFraudPredict(request.getFraudPredictStatus());
		fundsAtlasFlags.setCustomCheck(request.getCustomCheckStatus());
		fundsAtlasFlags.setStpCodes(request.getAtlasSTPFlag());
		intuitionFundsAtlasFlags.add(fundsAtlasFlags);
		intuitionFundsInProviderRequest.setAtlasFlags(intuitionFundsAtlasFlags);
		
		List<IntuitionFundsCalculations> intuitionFundsCalculations = new ArrayList<>();
		IntuitionFundsCalculations fundsCalculations = new IntuitionFundsCalculations();
		fundsCalculations.setTurnover(request.getTurnover() != null ? Integer.parseInt(request.getTurnover()) : null);
		intuitionFundsCalculations.add(fundsCalculations);
		intuitionFundsInProviderRequest.setCalculations(intuitionFundsCalculations);
		
		//AT-4627
		intuitionFundsInProviderRequest.setFundsInState(request.getCreditorState());
		intuitionFundsInProviderRequest.setCollectionBankAccountName(request.getCreditorAccountName());
		intuitionFundsInProviderRequest.setCollectionBankAccountNumber(request.getCreditorBankAccountNumber());
		intuitionFundsInProviderRequest.setCollectionBankName(request.getCreditorBankName());
		intuitionFundsInProviderRequest.setComplianceLog(request.getComplianceLog()); //AT-5578		
		return intuitionFundsInProviderRequest;
	}
	
	/**
	 * Transform payment out request.
	 *
	 * @param request the request
	 * @return the intuition funds out provider request
	 */
	public IntuitionFundsProviderRequest transformPaymentOutRequest(TransactionMonitoringPaymentsOutRequest request) {
		
		IntuitionFundsProviderRequest intuitionFundsProviderRequest = new IntuitionFundsProviderRequest();
		
		intuitionFundsProviderRequest.setTradeIdentificationNumber(request.getContractNumber()+"_"+request.getFundsOutId());
		intuitionFundsProviderRequest.setTradeContractNumber(request.getContractNumber());
		intuitionFundsProviderRequest.setTradePaymentID(request.getTransactionId().toString());
		intuitionFundsProviderRequest.setTradeAccountNumber(request.getTradeAccountNumber());
		intuitionFundsProviderRequest.setTimestamp(request.getTimeStamp());
		intuitionFundsProviderRequest.setDealDate(request.getDealDate()+TIME_STRING);
		
		//AT-4815
		if(request.getEtailer() != null && request.getEtailer().equalsIgnoreCase("true")) { 
			intuitionFundsProviderRequest.setCustType(CFXETAILER);
		}else {
			intuitionFundsProviderRequest.setCustType(request.getCustType());
		}
		intuitionFundsProviderRequest.setPurposeOfTrade(request.getPurposeOfTrade());
		intuitionFundsProviderRequest.setBuyingAmount(request.getBuyingAmount());
		intuitionFundsProviderRequest.setBuyCurrency(request.getBuyCurrency());
		intuitionFundsProviderRequest.setSellingAmount(request.getSellingAmount());
		intuitionFundsProviderRequest.setSellCurrency(request.getSellCurrency());
		intuitionFundsProviderRequest.setTransactionCurrency(request.getTransactionCurrency());
		intuitionFundsProviderRequest.setCustomerLegalEntity(request.getCustomerLegalEntity());
		//AT-5435
		intuitionFundsProviderRequest.setCreditFrom(request.getCreditFrom());
		intuitionFundsProviderRequest.setPaymentMethod(request.getPaymentMethod());
		intuitionFundsProviderRequest.setAmount(request.getAmount());
		intuitionFundsProviderRequest.setFirstName((request.getFirstName() != null)
				? request.getFirstName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
				: null);
		intuitionFundsProviderRequest.setLastName((request.getLastName() != null)
				? request.getLastName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
				: null);
		intuitionFundsProviderRequest.setCountry(request.getCountry());
		intuitionFundsProviderRequest.setEmail(request.getEmail());
		intuitionFundsProviderRequest.setPhone(request.getPhone());
		intuitionFundsProviderRequest.setAccountNumber(request.getAccountNumber());
		intuitionFundsProviderRequest.setCurrencyCode(request.getCurrencyCode());
		intuitionFundsProviderRequest.setBeneficiaryId(request.getBeneficiaryId());
		intuitionFundsProviderRequest.setBeneficaryBankName((request.getBeneficiaryBankName() != null)
				? request.getBeneficiaryBankName().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
				: null);
		intuitionFundsProviderRequest.setBeneficaryBankAddress((request.getBeneficiaryBankAddress() != null)
				? request.getBeneficiaryBankAddress().replaceAll(CHARACTER, " ").replaceAll("\"+", "")
				: null);
		intuitionFundsProviderRequest.setPaymentReference(request.getPaymentReference());
		intuitionFundsProviderRequest.setPaymentRefMatchedKeyword(request.getPaymentRefMatchedKeyword());
		intuitionFundsProviderRequest.setMaturityDate(request.getMaturityDate());
		intuitionFundsProviderRequest.setCountryOfFund(request.getCountryOfFund());
		intuitionFundsProviderRequest.setWatchlist(request.getWatchlist().toString());
		
		List<IntuitionFundsTransactional> intuitionFundsTransactional = new ArrayList<>();
		IntuitionFundsTransactional fundsTransactional = new IntuitionFundsTransactional();
		fundsTransactional.setTradeAccountNumber(request.getTradeAccountNumber());
		fundsTransactional.setTradeContactId(request.getTradeContactId().toString());
		fundsTransactional.setTrxType("FUNDSOUT");
		fundsTransactional.setPurposeOfTrade(request.getPurposeOfTrade());
		fundsTransactional.setContractNumber(request.getContractNumber());
		fundsTransactional.setDealDate(request.getDealDate()+TIME_STRING);
		
		fundsTransactional.setThirdPartyPayment(String.valueOf(request.isThirdPartyPayment()));
		fundsTransactional.setBuyingAmountBaseValue(request.getBuyingAmountBaseValue());
		fundsTransactional.setValueDate(request.getValueDate());
		fundsTransactional.setOpiCreatedDate(request.getOpiCreatedDate());
		fundsTransactional.setMaturity_date(request.getMaturityDate()+TIME_STRING);
		//AT-5337
		fundsTransactional.setInitiatingParentContact(request.getInitiatingParentContact());
		intuitionFundsTransactional.add(fundsTransactional);
		intuitionFundsProviderRequest.setTransactional(intuitionFundsTransactional);
		
		List <IntuitionFundsDebitCard> intuitionFundsDebitCards = new ArrayList<>();
		IntuitionFundsDebitCard fundsDebitCard = new IntuitionFundsDebitCard();
		fundsDebitCard.setCountryOfFund(request.getCountryOfFund());
		fundsDebitCard.setIsThreeds(request.getIsThreeds());
		intuitionFundsDebitCards.add(fundsDebitCard);
		intuitionFundsProviderRequest.setDebitCard(intuitionFundsDebitCards);
		
		List<IntuitionFundsDeviceAndIP> intuitionFundsDeviceAndIPs = new ArrayList<>();
		IntuitionFundsDeviceAndIP fundsDeviceAndIP = new IntuitionFundsDeviceAndIP();
		fundsDeviceAndIP.setBrwsrType(request.getBrowserType());
		fundsDeviceAndIP.setBrwsrVersion(request.getBrowserVersion());
		fundsDeviceAndIP.setBrwsrLang(request.getBrowserLanguage());
		fundsDeviceAndIP.setBrowserOnline(request.getBrowserOnline());
		fundsDeviceAndIP.setVersion(request.getVersion() != null ? request.getVersion().toString() : null);
		fundsDeviceAndIP.setStatusUpdateReason(request.getStatusUpdateReason());
		fundsDeviceAndIP.setDeviceName(request.getDeviceName());
		fundsDeviceAndIP.setDeviceVersion(request.getDeviceVersion());
		fundsDeviceAndIP.setDeviceId(request.getDeviceId());
		fundsDeviceAndIP.setDeviceManufacturer(request.getDeviceManufacturer());
		fundsDeviceAndIP.setDeviceType(request.getDeviceType());
		fundsDeviceAndIP.setOsType(request.getOsType());
		fundsDeviceAndIP.setScreenResolution(request.getScreenResolution());
		intuitionFundsDeviceAndIPs.add(fundsDeviceAndIP);
		intuitionFundsProviderRequest.setDeviceAndIP(intuitionFundsDeviceAndIPs);
		
		List<IntuitionFundsAtlasFlags> intuitionFundsAtlasFlags = new ArrayList<>();
		IntuitionFundsAtlasFlags fundsAtlasFlags = new IntuitionFundsAtlasFlags();
		fundsAtlasFlags.setUpdateStatus(request.getUpdateStatus());
		fundsAtlasFlags.setBlacklist(request.getBlacklistStatus());
		fundsAtlasFlags.setCountryCheck(request.getCountryCheckStatus());
		fundsAtlasFlags.setSanctionsContact(request.getSanctionContactStatus());
		fundsAtlasFlags.setSanctionsBeneficiary(request.getSanctionBeneficiaryStatus());
		fundsAtlasFlags.setSanctionsBank(request.getSanctionBankStatus());
		fundsAtlasFlags.setSanctions3rdParty(request.getSanction3rdPartyStatus());
		fundsAtlasFlags.setPaymentReferenceCheck(request.getPaymentReferenceCheck());
		fundsAtlasFlags.setFraudPredict(request.getFraudPredictStatus());
		fundsAtlasFlags.setCustomCheck(request.getCustomCheckStatus());
		fundsAtlasFlags.setStpCodes(request.getAtlasSTPFlag());
		intuitionFundsAtlasFlags.add(fundsAtlasFlags);
		intuitionFundsProviderRequest.setAtlasFlags(intuitionFundsAtlasFlags);
		
		List<IntuitionFundsCalculations> intuitionFundsCalculations = new ArrayList<>();
		IntuitionFundsCalculations fundsCalculations = new IntuitionFundsCalculations();
		intuitionFundsCalculations.add(fundsCalculations);
		intuitionFundsProviderRequest.setCalculations(intuitionFundsCalculations);
		intuitionFundsProviderRequest.setComplianceLog(request.getComplianceLog());//AT-5578
		
		return intuitionFundsProviderRequest;
	}
	
	private void transformIpAddresDetailsData(TransactionMonitoringAccountRequest accRequest,
			IntuitionSignupIPDeviceProviderRequest providerIp) {
		IPAddressDetails ipDetails = accRequest.getiPAddressDetails();
		
		providerIp.setContinent(ipDetails.getContinent());
		providerIp.setLongitude(ipDetails.getLongitude());
		providerIp.setLatitiude(ipDetails.getLatitiude());
		providerIp.setRegion(ipDetails.getRegion());
		providerIp.setCity(ipDetails.getCity());
		providerIp.setTimezone(ipDetails.getTimezone());
		providerIp.setOrganization(ipDetails.getOrganization());
		providerIp.setCarrier(ipDetails.getCarrier());
		providerIp.setConnectionType(ipDetails.getConnectionType());
		providerIp.setLineSpeed(ipDetails.getLineSpeed());
		providerIp.setIpRoutingType(ipDetails.getIpRoutingType());
		providerIp.setCountryName(ipDetails.getCountryName());
		providerIp.setCountryCode(ipDetails.getCountryCode());
		providerIp.setStateName(ipDetails.getStateName());
		providerIp.setStateCode(ipDetails.getStateCode());
		providerIp.setPostalCode(ipDetails.getPostalCode());
		providerIp.setAreaCode(ipDetails.getAreaCode());
		providerIp.setAnonymizerStatus(ipDetails.getAnonymizerStatus());
		providerIp.setIpAddress(ipDetails.getIpAddress());
		providerIp.setContactIp(ipDetails.getContactIp());
	}
	
	/**
	 * Transform corperate compliance data.
	 *
	 * @param providerSignupAccountRequest the provider signup account request
	 * @param accRequest the acc request
	 */
	private void transformCorperateComplianceData(IntuitionSignupCFXLegalEntityProviderRequest providerCfx,
			TransactionMonitoringAccountRequest accRequest) {
		providerCfx.setSic(accRequest.getCorperateCompliance().getSic());
		providerCfx.setFormerName(accRequest.getCorperateCompliance().getFormerName());
		providerCfx.setMatchName(accRequest.getCorperateCompliance().getMatchName());
		providerCfx.setLegalForm(accRequest.getCorperateCompliance().getLegalForm());
		providerCfx
				.setForeignOwnedCompany(accRequest.getCorperateCompliance().getForeignOwnedCompany());
		providerCfx.setNetWorth(accRequest.getCorperateCompliance().getNetWorth());
		providerCfx.setFixedAssets(accRequest.getCorperateCompliance().getFixedAssets());
		providerCfx.setTotalLiabilitiesAndEquities(
				accRequest.getCorperateCompliance().getTotalLiabilitiesAndEquities());
		providerCfx.setGrossIncome(accRequest.getCorperateCompliance().getGrossIncome());
		providerCfx.setNetIncome(accRequest.getCorperateCompliance().getNetIncome());
		providerCfx
				.setFinancialStrength(accRequest.getCorperateCompliance().getFinancialStrength());
		providerCfx
				.setTotalShareHolders(accRequest.getCorperateCompliance().getTotalShareHolders());
		providerCfx
				.setTotalMatchedShareholders(accRequest.getCorperateCompliance().getTotalMatchedShareholders());
		providerCfx
				.setIsoCountryCode2Digit(accRequest.getCorperateCompliance().getIsoCountryCode2Digit());
	}
	
	/**
	 * Transform risk profile data.
	 *
	 * @param providerSignupAccountRequest the provider signup account request
	 * @param accRequest the acc request
	 */
	private void transformRiskProfileData(TransactionMonitoringAccountRequest accRequest, 
			IntuitionSignupCFXLegalEntityProviderRequest providerCfx) {
		
		providerCfx.setCreditLimit(accRequest.getRiskProfile().getCreditLimit());
		providerCfx.setAnnualSales(accRequest.getRiskProfile().getAnnualSales());
		providerCfx.setModelledAnnualSales(accRequest.getRiskProfile().getModelledAnnualSales());
		providerCfx.setNetWorthAmount(accRequest.getRiskProfile().getNetWorthAmount());
		providerCfx.setModelledNetWorth(accRequest.getRiskProfile().getModelledNetWorth());
		providerCfx.setCountryRiskIndicator(accRequest.getRiskProfile().getCountryRiskIndicator());
		providerCfx.setRiskTrend(accRequest.getRiskProfile().getRiskTrend());
		providerCfx.setRiskDirection(accRequest.getRiskProfile().getRiskDirection());
		providerCfx.setUpdatedRisk(accRequest.getRiskProfile().getUpdatedRisk());
		providerCfx.setDunsNumber(accRequest.getRiskProfile().getDunsNumber());
		providerCfx.setGroupStructureNumberOfLevels(accRequest.getRiskProfile().getGroupStructureNumberOfLevels());
		providerCfx.setHeadquarterDetails(accRequest.getRiskProfile().getHeadquarterDetails());
		providerCfx.setImmediateParentDetails(accRequest.getRiskProfile().getImmediateParentDetails());
		providerCfx.setDomesticUltimateParentDetails(accRequest.getRiskProfile().getDomesticUltimateParentDetails());
		providerCfx.setGlobalUltimateParentDetails(accRequest.getRiskProfile().getGlobalUltimateParentDetails());
		providerCfx.setRiskRating(accRequest.getRiskProfile().getRiskRating());
		
	}
	
	/**
	 *AT-4812
	 * Transform card details data.
	 *
	 * @param accRequest the acc request
	 * @param card the card
	 */
	private void transformCardDetailsData(TransactionMonitoringAccountRequest accRequest,
			IntuitionSignupCardProviderRequest card) {
		CardDetails cardDetails = accRequest.getCardDetails();
		
		card.setCardStatusFlag(cardDetails.getCardStatusFlag());
		card.setDateCardDispatched(cardDetails.getDateCardDispatched());
		card.setDateCardActivated(cardDetails.getDateCardActivated());
		card.setDateCardFrozen(cardDetails.getDateCardFrozen());
		card.setFreezeReason(cardDetails.getFreezeReason());
		card.setDateCardUnfrozen(cardDetails.getDateCardUnfrozen());
		card.setDateCardBlocked(cardDetails.getDateCardBlocked());
		card.setReasonForBlock(cardDetails.getReasonForBlock());
		card.setDateCardUnblocked(cardDetails.getDateCardUnblocked());
		card.setDateLastCardPanView(cardDetails.getDateLastCardPanView());
		card.setDateLastCardPinView(cardDetails.getDateLastCardPinView());
	}

	// AT-4896, AT-4972
	/**
	 * Transform post card transaction request.
	 *
	 * @param request the request
	 * @return the intuition post card transaction request
	 */
	public IntuitionPostCardRequest transformPostCardTransactionPostRequest(
			TransactionMonitoringPostCardTransactionRequest request) {
		
		
		IntuitionPostCardRequest intuitionRequest = new IntuitionPostCardRequest();
		
		List<IntuitionPostCardTransactionRequest> transactionList = new ArrayList<>();
		List<IntuitionPostCardAccountRequest> accountList = new ArrayList<>();
		List<IntuitionPostCardChargebackRequest> chargebackList = new ArrayList<>();
		List<IntuitionPostCardIPDeviceRequest> ipDeviceList = new ArrayList<>();
		
		IntuitionPostCardTransactionRequest transaction = new IntuitionPostCardTransactionRequest();
		IntuitionPostCardAccountRequest account = new IntuitionPostCardAccountRequest();
		IntuitionPostCardChargebackRequest chargeback = new IntuitionPostCardChargebackRequest();
		IntuitionPostCardIPDeviceRequest ipdevice = new IntuitionPostCardIPDeviceRequest();
		
		intuitionRequest.setTrxID(request.getTrxID());
		intuitionRequest.setGPSDatestamp(request.getgPSDatestamp());
		intuitionRequest.setTitanAccountNumber(request.getTitanAccountNumber());
		intuitionRequest.setCardToken(request.getCardToken());
		intuitionRequest.setBin(request.getBin());
		intuitionRequest.setProductID(request.getProductID());
		intuitionRequest.setAcqDate(request.getAcqDate());
		intuitionRequest.setRequestType(request.getRequestType());
		intuitionRequest.setTxnCountry(request.getTxnCountry());
		intuitionRequest.setMultiPartInd(request.getMultiPartInd());
		intuitionRequest.setMultiPartFinal(request.getMultiPartFinal());
		intuitionRequest.setMultiPartMatchingReference(request.getMultiPartMatchingReference());
		intuitionRequest.setBillCcy(request.getBillCcy());
		intuitionRequest.setBillAmount(request.getBillAmount());
		intuitionRequest.setAdditionalAmountCcy(request.getAdditionalAmountCcy());
		intuitionRequest.setAdditionalAmount(request.getAdditionalAmount());
		intuitionRequest.setMerchID(request.getMerchID());
		intuitionRequest.setMCCCode(request.getmCCCode());
		intuitionRequest.setAcquirerID(request.getAcquirerID());
		intuitionRequest.setTitanDecisionCode(request.getTitanDecisionCode());
		intuitionRequest.setTitanDecisionDescription(request.getTitanDecisionDescription());
		intuitionRequest.setCardStatusCode(request.getCardStatusCode());
		intuitionRequest.setGPSSTIPFlag(request.getgPSSTIPFlag());
		intuitionRequest.setAVSResult(request.getaVSResult());
		intuitionRequest.setSCAAuthenticationExemptionCode(request.getsCAAuthenticationExemptionCode());
		intuitionRequest.setSCAAuthenticationExemptionDescription(request.getsCAAuthenticationExemptionDescription());
		intuitionRequest.setAuthenticationMethod(request.getAuthenticationMethod());
		intuitionRequest.setAuthenticationMethodDesc(request.getAuthenticationMethodDesc());	
		intuitionRequest.setCVVResults(request.getcVVResults());
		intuitionRequest.setPOSTerminal(request.getpOSTerminal());
		intuitionRequest.setPOSDatestamp(request.getpOSDatestamp());
		intuitionRequest.setGPSPOSCapability(request.getgPSPOSCapability());
		intuitionRequest.setGPSPOSData(request.getgPSPOSData());
		
		transaction.setCardInputMode(request.getCardInputMode());
		transaction.setCardInputModeDesc(request.getCardInputModeDesc());
		transaction.setCardPresentIndicator(request.getCardPresentIndicator());
		transaction.setCardPresentDescription(request.getCardPresentDescription());
		transaction.setTxnCCY(request.getTxnCCY());
		transaction.setTxnAmount(request.getTxnAmount());
		transaction.setEffectiveAmount(request.getEffectiveAmount());
		transaction.setAdditionalAmountType(request.getAdditionalAmountType());
		transaction.setMerchantName(request.getMerchantName());
		transaction.setMerchantNameOther(request.getMerchantNameOther());
		transaction.setMCCCodeDescription(request.getmCCCodeDescription());
		transaction.setMerchantCountryCode(request.getMerchantCountryCode());
		transaction.setMerchantPostcode(request.getMerchantPostcode());
		transaction.setMerchantRegion(request.getMerchantRegion());
		transaction.setMerchantAddress(request.getMerchantAddress());
		transaction.setMerchantCity(request.getMerchantCity());
		transaction.setMerchantContact(request.getMerchantContact());
		transaction.setMerchantSecondContact(request.getMerchantSecondContact());
		transaction.setMerchantWebsite(request.getMerchantWebsite());
		transaction.setGPSDecisionCode(request.getgPSDecisionCode());
		transaction.setGPSDecisionCodeDescription(request.getgPSDecisionCodeDescription());
		//AT-5371
		transaction.setPtWallet(request.getPtWallet());
		transaction.setPtDeviceType(request.getPtDeviceType());
		transaction.setPtDeviceIP(request.getPtDeviceIP());
		transaction.setPtDeviceID(request.getPtDeviceID());
		
		
		account.setConsolidatedWalletBalances(request.getConsolidatedWalletBalances());
		
		chargeback.setChargebackReason(request.getChargebackReason());
		
		ipdevice.setContinent(request.getContinent());
		ipdevice.setLongitude(request.getLongitude());
		ipdevice.setLatitiude(request.getLatitiude());
		ipdevice.setRegion(request.getRegion());
		ipdevice.setCity(request.getCity());
		ipdevice.setTimezone(request.getTimezone());
		ipdevice.setOrganization(request.getOrganization());
		ipdevice.setCarrier(request.getCarrier());
		ipdevice.setConnectionType(request.getConnectionType());
		ipdevice.setLineSpeed(request.getLineSpeed());
		ipdevice.setIpRoutingType(request.getIpRoutingType());
		ipdevice.setCountryName(request.getCountryName());
		ipdevice.setCountryCode(request.getCountryCode());
		ipdevice.setStateName(request.getStateName());
		ipdevice.setStateCode(request.getStateCode());
		ipdevice.setPostalCode(request.getPostalCode());
		ipdevice.setAreaCode(request.getAreaCode());
		ipdevice.setAnonymizerStatus(request.getAnonymizerStatus());
		ipdevice.setIpAddress(request.getIpAddress());
		ipdevice.setDeviceID(request.getDeviceID());
		
		transactionList.add(transaction);
		accountList.add(account);
		chargebackList.add(chargeback);
		ipDeviceList.add(ipdevice);
		
		intuitionRequest.setTransaction(transactionList);
		intuitionRequest.setAccount(accountList);
		intuitionRequest.setChargeback(chargebackList);
		intuitionRequest.setIpDevice(ipDeviceList);
		
		return intuitionRequest;
	}
	
	/**
	 * Transform post card transaction patch request.
	 *
	 * @param request the request
	 * @return the intuition post card transaction request
	 */
	//AT-4968, AT-5175
	public IntuitionPostCardRequest transformPostCardTransactionPatchRequest(
			TransactionMonitoringPostCardTransactionRequest request) {
		IntuitionPostCardRequest intuitionRequest = new IntuitionPostCardRequest();
		List<IntuitionPostCardChargebackRequest> chargebackList = new ArrayList<>();
			IntuitionPostCardChargebackRequest chargeback = new IntuitionPostCardChargebackRequest();
			intuitionRequest.setTrxID(request.getTrxID());
			intuitionRequest.setGPSDatestamp(request.getgPSDatestamp());
			chargeback.setUpdateStatus(request.getRequestType());
			chargeback.setUpdateDate(request.getUpdateDate());
			chargeback.setUpdateAmount(request.getUpdateAmount());
			chargeback.setChargebackReason(request.getChargebackReason());
			chargebackList.add(chargeback);
			intuitionRequest.setChargeback(chargebackList);
		return intuitionRequest;
	}
}
