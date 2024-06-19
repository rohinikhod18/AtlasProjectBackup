package com.currenciesdirect.gtg.compliance.compliancesrv.transformer.reg;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import com.currenciesdirect.gtg.compliance.commons.domain.transactionmonitoring.TransactionMonitoringAccountMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.ComplianceException;
import com.currenciesdirect.gtg.compliance.compliancesrv.core.enterprise.EnterpriseServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.CardDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.dbport.NewRegistrationDBServiceImpl;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.enterprise.EnterpriseIPAddressDetails;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Account;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Card;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.Contact;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.reg.RegistrationServiceRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionAccountMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionCFXLegalEntityMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionCardMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionContactMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionIPDeviceMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.domain.transactionmonitoringmq.IntuitionSignupMQRequest;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageContext;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.MessageExchange;
import com.currenciesdirect.gtg.compliance.compliancesrv.msg.ServiceTypeEnum;
import com.currenciesdirect.gtg.compliance.compliancesrv.transformer.AbstractTransformer;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.Constants;
import com.currenciesdirect.gtg.compliance.compliancesrv.util.JsonConverterUtil;

public class TransactionMonitoringRegSyncTransformer extends AbstractTransformer {

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(TransactionMonitoringRegSyncTransformer.class);
	

	/** The new registration DB service impl. */
	@Autowired
	protected NewRegistrationDBServiceImpl newRegistrationDBServiceImpl;

	@Autowired
	protected EnterpriseServiceImpl enterpriseServiceImpl;

	@Autowired
	protected CardDBServiceImpl cardDBServiceImpl;

	/**
	 * Transform request.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformRequest(Message<MessageContext> message) {
		TransactionMonitoringAccountMQRequest tmRequest = (TransactionMonitoringAccountMQRequest) message.getPayload()
				.getGatewayMessageExchange().getRequest();
		String jsonCcExchangeRequest;
		try {
			MessageExchange ccExchange = createSignupMQExchange(tmRequest);

			jsonCcExchangeRequest = JsonConverterUtil.convertToJsonWithoutNull(ccExchange.getRequest());
			LOG.debug("::::::::::::::: TransactionMonitoringTransformer : {}", jsonCcExchangeRequest);

			message.getPayload().appendMessageExchange(ccExchange);
		} catch (Exception e) {
			LOG.error("Error in TransactionMonitoringTransformer class : transformRequest -", e);
			message.getPayload().setFailed(true);
		}

		return message;
	}

	/**
	 * Creates the signup MQ exchange.
	 *
	 * @param tmRequest the tm request
	 * @return the message exchange
	 * @throws ComplianceException the compliance exception
	 * @throws ParseException 
	 */
	private MessageExchange createSignupMQExchange(TransactionMonitoringAccountMQRequest tmRequest)
			throws ComplianceException, ParseException {

		MessageExchange ccExchange = new MessageExchange();

		IntuitionSignupMQRequest request = new IntuitionSignupMQRequest();
		List<IntuitionAccountMQRequest> accRequestsList = new ArrayList<>();
		List<IntuitionContactMQRequest> tmSingUpContactRequests = new ArrayList<>();
		EnterpriseIPAddressDetails enterpriseIpAdd = new EnterpriseIPAddressDetails();

		
		
		RegistrationServiceRequest regReq = newRegistrationDBServiceImpl
				.getAccountContacDetailsForIntuition(tmRequest.getTradeAccountNumber());
		Account account = regReq.getAccount();

		transformAccountRequest(request, account, accRequestsList);
		request.setAccountStatus(account.getAccountStatus());
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//AT-5432
		if(account.getRegistrationDateTime()== null || account.getRegistrationDateTime().isEmpty()) {
			request.setRegDateTime(myFormat.format(regReq.getAdditionalAttribute("CreatedOn")));
		}else {
			request.setRegDateTime(account.getRegistrationDateTime());
		}
		
		for (Contact contact : regReq.getAccount().getContacts()) {
			IntuitionContactMQRequest tmSingupContact;
			tmSingupContact = transformContactRequest(contact, account);
			tmSingupContact.setContactStatus(contact.getContactStatus());
			tmSingupContact.setBlacklist(contact.getPreviousBlacklistStatus());
			tmSingupContact.setSanctionResult(contact.getPreviousSanctionStatus());
			tmSingupContact.setEIDVStatus(contact.getPreviousKycStatus());

			getFraugsterContactStatus(contact, tmSingupContact);

			String onfidoStatus = newRegistrationDBServiceImpl.getOnfidoContactStatusFromDB(contact.getId());
			tmSingupContact.setOnfido(onfidoStatus);

			transformContactRequestSetWatchlist(contact.getId(), tmSingupContact);
			transformStatusUpdateReason(contact.getId(), tmSingupContact);
			
			if(Boolean.TRUE.equals(contact.getPrimaryContact())) {
				enterpriseIpAdd = enterpriseServiceImpl.getIPAddressDetails(contact.getIpAddress());
			}
			
			
			tmSingUpContactRequests.add(tmSingupContact);
			
			if(contact.getPrimaryContact() != null && contact.getPrimaryContact()) {
				request.addAttribute("PrimaryContactId", contact.getId());
			}
		}
		
		if(enterpriseIpAdd != null) {
			setIntuitionIPDeviceMQRequest(enterpriseIpAdd, request);
		}
		
		Card cardInfo = cardDBServiceImpl.getCardDetailsByAccountId(account.getId()); 
		 if(cardInfo != null) {
			 transformCardDetails(cardInfo, request);
		}	
		
		request.setAccount(accRequestsList);
		request.setContacts(tmSingUpContactRequests);
		
		String orgCode = (String) regReq.getAdditionalAttribute("OrganizationCode");
		request.addAttribute("orgCode", orgCode);

		ccExchange.setRequest(request);
		ccExchange.setServiceTypeEnum(ServiceTypeEnum.TRANSACTION_MONITORING_SERVICE);

		return ccExchange;
	}

	/**
	 * Transform account request.
	 *
	 * @param requestData the request data
	 * @param account     the account
	 * @throws ComplianceException
	 * @throws ParseException 
	 */
	private void transformAccountRequest(IntuitionSignupMQRequest requestData, Account account,
			List<IntuitionAccountMQRequest> accRequest) throws ComplianceException, ParseException {
		requestData.setTradeAccNumber(account.getTradeAccountNumber());
		requestData.setChannel(account.getChannel());
		requestData.setActName(account.getAccountName());
		requestData.setCountryInterest(account.getCountryOfInterest());
		requestData.setTradeName(account.getTradingName());
		requestData.setTrasactionPurpose(account.getPurposeOfTran());
		requestData.setServiceReq(account.getServiceRequired());
		requestData.setTxnValue(account.getValueOfTransaction());
		requestData.setSourceOfFund(account.getSourceOfFund());
		requestData.setSource(account.getSource());
		requestData.setSubSource(account.getSubSource());
		requestData.setReferral(account.getReferral());
		requestData.setReferralText(account.getReferralText());
		requestData.setExtendedReferral(account.getExtendedReferral());
		requestData.setAdCampaign(account.getAdCampaign());
		requestData.setKeywords(account.getKeywords());
		requestData.setSearchEngine(account.getSearchEngine());
		requestData.setAffiliateName(account.getAffiliateName());
		requestData.setRegMode(account.getRegistrationMode());
		requestData.setOrganizationLegalEntity(account.getCustLegalEntity());
		
		//AT-5243
		if(account.getCompany() != null)
			requestData.setEtailer(account.getCompany().getEtailer());
		
		requestData.setAccountStatus(account.getAccountStatus());
		//AT-5432
		if(requestData.getEtailer() != null && requestData.getEtailer().equalsIgnoreCase("true")) {
			requestData.setCustType("CFX-Etailer");
		}else {
			requestData.setCustType(account.getCustType());
		}
		requestData.setBuyingCurrency(account.getBuyingCurrency());
		requestData.setSellingCurrency(account.getSellingCurrency());

		if (account.getConversionPrediction() != null) {
			requestData.setConversionFlag(account.getConversionPrediction().getConversionFlag());
			requestData
					.setConversionProbability(account.getConversionPrediction().getConversionProbability().toString());
			requestData.setEtvBand(account.getConversionPrediction().geteTVBand());
		}
		
		transformComplianceLog(account.getId(), requestData);
		setIntuitionAccountMQRequest(account, accRequest);
		setIntuitionCFXLegalEntityMQRequest(account, requestData);

	}

	private void setIntuitionIPDeviceMQRequest(EnterpriseIPAddressDetails enterpriseIpAdd,
			IntuitionSignupMQRequest requestData) {
		List<IntuitionIPDeviceMQRequest> ipDeveiceReqList = new ArrayList<>();
		IntuitionIPDeviceMQRequest ipDeveiceRequest = new IntuitionIPDeviceMQRequest();

		ipDeveiceRequest.setContinent(enterpriseIpAdd.getContinent());
		ipDeveiceRequest.setLongitude(enterpriseIpAdd.getLongitude());
		ipDeveiceRequest.setLatitiude(enterpriseIpAdd.getLatitiude());
		ipDeveiceRequest.setRegion(enterpriseIpAdd.getRegion());
		ipDeveiceRequest.setCity(enterpriseIpAdd.getCity());
		ipDeveiceRequest.setTimezone(enterpriseIpAdd.getTimezone());
		ipDeveiceRequest.setOrganization(enterpriseIpAdd.getOrganization());
		ipDeveiceRequest.setCarrier(enterpriseIpAdd.getCarrier());
		ipDeveiceRequest.setConnectionType(enterpriseIpAdd.getConnectionType());
		ipDeveiceRequest.setLineSpeed(enterpriseIpAdd.getLineSpeed());
		ipDeveiceRequest.setIpRoutingType(enterpriseIpAdd.getIpRoutingType());
		ipDeveiceRequest.setCountryName(enterpriseIpAdd.getCountryName());
		ipDeveiceRequest.setCountryCode(enterpriseIpAdd.getCountryCode());
		ipDeveiceRequest.setStateName(enterpriseIpAdd.getStateName());
		ipDeveiceRequest.setStateCode(enterpriseIpAdd.getStateCode());
		ipDeveiceRequest.setPostalCode(enterpriseIpAdd.getPostalCode());
		ipDeveiceRequest.setAreaCode(enterpriseIpAdd.getAreaCode());
		ipDeveiceRequest.setAnonymizerStatus(enterpriseIpAdd.getAnonymizerStatus());
		ipDeveiceRequest.setIpAddress(enterpriseIpAdd.getIpAddress());

		ipDeveiceReqList.add(ipDeveiceRequest);
		requestData.setIpDevice(ipDeveiceReqList);

	}

	private void transformCardDetails(Card card, IntuitionSignupMQRequest requestData) throws ParseException {
		
		List<IntuitionCardMQRequest> cardDetailsList = new ArrayList<>();
		IntuitionCardMQRequest cardDetails = new IntuitionCardMQRequest();
		
		cardDetails.setCardID(card.getCardID());
		cardDetails.setCardStatusFlag(card.getCardStatusFlag());
		cardDetails.setDateCardDispatched(getFormattedDate(card.getDateCardDispatched()));
		cardDetails.setDateCardActivated(getFormattedDate(card.getDateCardActivated()));
		cardDetails.setDateCardFrozen(getFormattedDate(card.getDateCardFrozen()));
		cardDetails.setFreezeReason(card.getFreezeReason());
		cardDetails.setDateCardUnfrozen(getFormattedDate(card.getDateCardUnfrozen()));
		cardDetails.setDateCardBlocked(getFormattedDate(card.getDateCardBlocked()));
		cardDetails.setReasonForBlock(card.getReasonForBlock());
		cardDetails.setDateCardUnblocked(getFormattedDate(card.getDateCardUnblocked()));
		cardDetails.setDateLastCardPinView(getFormattedDate(card.getDateLastCardPinView()));
		cardDetails.setDateLastCardPanView(getFormattedDate(card.getDateLastCardPanView()));
		
		cardDetailsList.add(cardDetails);
		requestData.setCard(cardDetailsList);
	}

	private void setIntuitionAccountMQRequest(Account account, List<IntuitionAccountMQRequest> accRequests) {
		IntuitionAccountMQRequest accRequest = new IntuitionAccountMQRequest();
		accRequest.setAccountId(account.getId().toString());
		accRequest.setAccSfId(account.getAccSFID());
		accRequest.setBranch(account.getBranch());
		accRequest.setCardDeliveryToSecondaryAddress(account.getCardDeliveryToSecondaryAddress());
		accRequest.setAffiliateNumber(account.getAffiliateNumber());
		accRequest.setLegacyAccountAuroraTitanCustomerNo(account.getLegacyTradeAccountNumber());// Add for AT-5393
		// Added for AT-5529
		if (account.getCustType().equalsIgnoreCase("CFX") || account.getCustType().equalsIgnoreCase(Constants.CFXETAILER)) {
			accRequest.setBlacklist(account.getPreviousBlacklistStatus());
			accRequest.setSanctionResult(account.getPreviousSanctionStatus());
		}
		accRequests.add(accRequest);
	}

	private void setIntuitionCFXLegalEntityMQRequest(Account account, IntuitionSignupMQRequest requestData) throws ParseException {
		List<IntuitionCFXLegalEntityMQRequest> cfxLegalEntityRequests = new ArrayList<>();
		IntuitionCFXLegalEntityMQRequest request = new IntuitionCFXLegalEntityMQRequest();

		// AT-4773
		if (account.getTurnover() != null && !account.getTurnover().isEmpty() && !account.getTurnover().equals("")) {
			request.setTurnover(getIntegerTurnOver(account.getTurnover())); // updated for AT-5029
		} else {
			request.setTurnover(0);
		}
		
		request.setWebsite(account.getWebsite());

		if (account.getCompany() != null) {
			request.setCompanyBillingAddress(account.getCompany().getBillingAddress());
			request.setCompanyPhone(account.getCompany().getCompanyPhone());
			request.setShippingAddress(account.getCompany().getShippingAddress());
			request.setVatNo(account.getCompany().getVatNo());
			request.setCountryRegion(account.getCompany().getCountryRegion());
			// AT-5180
			if (account.getCompany().gettAndcSignedDate() != null
					&& !account.getCompany().gettAndcSignedDate().isEmpty()) {
				if (account.getCompany().gettAndcSignedDate().length() > 10) //Update for AT-5478
					request.setTcSignedDate(getFormattedDate(account.getCompany().gettAndcSignedDate()));
				else
					request.setTcSignedDate(account.getCompany().gettAndcSignedDate());
			}
			request.setOption(account.getCompany().getOption());
			request.setTypeOfFinancialAccount(account.getCompany().getTypeOfFinancialAccount());
			request.setEstNoTransactionsPcm(account.getCompany().getEstNoTransactionsPcm());
			request.setCountryOfEstablishment(account.getCompany().getCountryOfEstablishment());
			request.setCorporateType(account.getCompany().getCorporateType());
			request.setRegistrationNo(account.getCompany().getRegistrationNo());
			if (account.getCompany().getIncorporationDate() != null
					&& !account.getCompany().getIncorporationDate().isEmpty()) {
				request.setIncorporationDate(account.getCompany().getIncorporationDate());
			}else {
				request.setIncorporationDate("1900-01-01");
			}
			request.setIndustry(account.getCompany().getIndustry());
			//Add for AT-5393
			if (account.getCompany().getOngoingDueDiligenceDate() != null
					&& !account.getCompany().getOngoingDueDiligenceDate().isEmpty()) {
				if (account.getCompany().getOngoingDueDiligenceDate().length() > 10)
					request.setOngoingDueDiligenceDate(getFormattedDate(account.getCompany().getOngoingDueDiligenceDate()));
				else
					request.setOngoingDueDiligenceDate(account.getCompany().getOngoingDueDiligenceDate());
			}
		}

		if (account.getCorperateCompliance() != null) {
			request.setSic(account.getCorperateCompliance().getSic());
			request.setFormerName(account.getCorperateCompliance().getFormerName());
			request.setMatchName(account.getCorperateCompliance().getMatchName());
			request.setLegalForm(account.getCorperateCompliance().getLegalForm());
			request.setForeignOwnedCompany(account.getCorperateCompliance().getForeignOwnedCompany());
			request.setNetWorth(account.getCorperateCompliance().getNetWorth());
			request.setFixedAssets(account.getCorperateCompliance().getFixedAssets());
			request.setTotalLiabilitiesAndEquities(account.getCorperateCompliance().getTotalLiabilitiesAndEquities());
			request.setGrossIncome(account.getCorperateCompliance().getGrossIncome());
			request.setNetIncome(account.getCorperateCompliance().getNetIncome());
			request.setFinancialStrength(account.getCorperateCompliance().getFinancialStrength());
			request.setTotalShareHolders(account.getCorperateCompliance().getTotalShareHolders());
			request.setTotalMatchedShareholders(account.getCorperateCompliance().getTotalMatchedShareholders());
			request.setIsoCountryCode2Digit(account.getCorperateCompliance().getIsoCountryCode2Digit());
			if (account.getCorperateCompliance().getRegistrationDate() != null
					&& !account.getCorperateCompliance().getRegistrationDate().isEmpty()) {
				request.setRegistrationDate(account.getCorperateCompliance().getRegistrationDate()); // Add for AT-5469
			}
		}

		if (account.getRiskProfile() != null) {
			request.setCreditLimit(account.getRiskProfile().getCreditLimit());
			request.setAnnualSales(account.getRiskProfile().getAnnualSales());
			request.setModelledAnnualSales(account.getRiskProfile().getModelledAnnualSales());
			request.setNetWorthAmount(account.getRiskProfile().getNetWorthAmount());
			request.setModelledNetWorth(account.getRiskProfile().getModelledNetWorth());
			request.setCountryRiskIndicator(account.getRiskProfile().getCountryRiskIndicator());
			request.setRiskTrend(account.getRiskProfile().getRiskTrend());
			request.setRiskDirection(account.getRiskProfile().getRiskDirection());
			request.setUpdatedRisk(account.getRiskProfile().getUpdatedRisk());
			request.setDunsNumber(account.getRiskProfile().getDunsNumber());
			request.setGroupStructureNumberOfLevels(account.getRiskProfile().getGroupStructureNumberOfLevels());
			request.setHeadquarterDetails(account.getRiskProfile().getHeadquarterDetails());
			request.setImmediateParentDetails(account.getRiskProfile().getImmediateParentDetails());
			request.setDomesticUltimateParentDetails(account.getRiskProfile().getDomesticUltimateParentDetails());
			request.setGlobalUltimateParentDetails(account.getRiskProfile().getGlobalUltimateParentDetails());
			request.setRiskRating(account.getRiskProfile().getRiskRating());
		}

		cfxLegalEntityRequests.add(request);
		requestData.setCfxLegalEntity(cfxLegalEntityRequests);
	}

	/**
	 * Transform contact request.
	 *
	 * @param contact the contact
	 * @param account the account
	 * @return the intuition contact MQ request
	 */
	private IntuitionContactMQRequest transformContactRequest(Contact contact, Account account) {
		IntuitionContactMQRequest requestData = new IntuitionContactMQRequest();

		requestData.setTradeAccNumber(account.getTradeAccountNumber());
		requestData.setContactSfId(contact.getContactSFID());
		requestData.setTradeContactId(contact.getTradeContactID().toString());
		requestData.setTitle(contact.getTitle());
		requestData.setPrefName(contact.getPreferredName());
		requestData.setFirstName(contact.getFirstName());
		requestData.setMiddleName(contact.getMiddleName());
		requestData.setLastName(contact.getLastName());
		requestData.setSecondSurname(contact.getSecondSurname());
		requestData.setMothersSurname(contact.getMothersSurname());
		if(contact.getDob() != null && !contact.getDob().isEmpty()) {
			requestData.setDob(contact.getDob());
		}else {
			requestData.setDob("1900-01-01");
		}
		requestData.setAddressType(contact.getAddressType());
		requestData.setStreet(contact.getStreet());
		requestData.setTownCityMuncipalty(contact.getCity());
		requestData.setAddress1(contact.getAddress1());
		requestData.setPostCode(contact.getPostCode());
		requestData.setSubBuilding(contact.getSubBuildingorFlat());
		requestData.setUnitNumber(contact.getUnitNumber());
		requestData.setSubCity(contact.getSubCity());
		requestData.setStateProvinceCounty(contact.getState());
		requestData.setWorkPhone(contact.getPhoneWork());
		requestData.setHomePhone(contact.getPhoneHome());
		requestData.setMobilePhone(contact.getPhoneMobile());
		requestData.setEmail(contact.getEmail());
		requestData.setSecondEmail(contact.getSecondEmail());
		requestData.setIsPrimaryContact(contact.getPrimaryContact().toString());
		requestData.setPrimaryPhone(contact.getPrimaryPhone());
		requestData.setGender(contact.getGender());
		requestData.setCountryOfNationality(contact.getNationality());
		requestData.setOccupation(contact.getOccupation());
		requestData.setPositionOfSignificance(contact.getPositionOfSignificance());

		if (null != contact.getPassportCountryCode()) {
			requestData.setPassportCountryCode(contact.getPassportCountryCode());
		}

		requestData.setPassportBirthFamilyName(contact.getPassportFamilyNameAtBirth());
		requestData.setPassportNameAtCitizen(contact.getPassportNameAtCitizenship());
		requestData.setPassportNumber(contact.getPassportNumber());
		requestData.setPassportBirthPlace(contact.getPassportPlaceOfBirth());

		if (null != contact.getPassportExiprydate() && !contact.getPassportExiprydate().isEmpty()) {
			requestData.setPassportExipryDate(contact.getPassportExiprydate());
		}

		requestData.setPassportFullName(contact.getPassportFullName());
		requestData.setPassportMrzLine1(contact.getPassportMRZLine1());
		requestData.setPassportMrzLine2(contact.getPassportMRZLine2());
		requestData.setDrivingLicenseCardNumber(contact.getDlCardNumber());

		if (null != contact.getDlCountryCode()) {
			requestData.setDrivingLicenseCountry(contact.getDlCountryCode());
		}

		if (null != contact.getDlExpiryDate() && !contact.getDlExpiryDate().isEmpty()) {
			requestData.setDrivingExpiry(contact.getDlExpiryDate());
		}

		requestData.setDrivingLicense(contact.getDlLicenseNumber());
		requestData.setDrivingStateCode(contact.getDlStateCode());
		requestData.setDrivingVersionNumber(contact.getDlVersionNumber());
		requestData.setMedicareCardNumber(contact.getMedicareCardNumber());
		requestData.setMedicareRefNumber(contact.getMedicareReferenceNumber());
		requestData.setMunicipalityOfBirth(contact.getMunicipalityOfBirth());
		requestData.setIpAddress(contact.getIpAddress());
		requestData.setMiddleName(contact.getMiddleName());
		requestData.setCountryOfBirth(contact.getCountryOfBirth());
		requestData.setStateOfBirth(contact.getStateOfBirth());
		requestData.setPrefecture(contact.getPrefecture());
		requestData.setAza(contact.getAza());
		requestData.setResidentialStatus(contact.getResidentialStatus());
		requestData.setAddressType(contact.getAddressType());
		requestData.setBuildingName(contact.getBuildingName());
		requestData.setSubBuilding(contact.getSubBuildingorFlat());
		requestData.setHouseBuildingNumber(contact.getBuildingNumber());
		requestData.setStreetNumber(contact.getStreetNumber());
		requestData.setStreetType(contact.getStreetType());
		requestData.setFloorNumber(contact.getFloorNumber());
		requestData.setAuthorisedSignatory(contact.getAuthorisedSignatory());
		requestData.setJobTitle(contact.getJobTitle());
		requestData.setDesignation(contact.getDesignation());
		requestData.setYearsInAddress(
				(contact.getYearsInAddress() == null || contact.getYearsInAddress().trim().isEmpty()) ? null
						: Double.parseDouble(contact.getYearsInAddress()));
		requestData.setAustraliaRtaCardNumber(contact.getAustraliaRTACardNumber());
		requestData.setDistrict(contact.getDistrict());
		requestData.setAreaNumber(contact.getAreaNumber());
		requestData.setNationalIdType(contact.getNationalIdType());
		requestData.setNationalIdNumber(contact.getNationalIdNumber());
		requestData.setVersion(contact.getVersion().toString());
		requestData.setCivicNumber(contact.getCivicNumber());
		requestData.setCustomCheck(contact.getPreviousCountryGlobalCheckStatus());//Add for AT-5393

		return requestData;
	}

	/**
	 * Gets the fraugster contact status.
	 *
	 * @param contact         the contact
	 * @param tmSingupContact the tm singup contact
	 * @return the fraugster contact status
	 * @throws ComplianceException the compliance exception
	 */
	private void getFraugsterContactStatus(Contact contact, IntuitionContactMQRequest tmSingupContact)
			throws ComplianceException {
		try {
			List<String> fraudPredict = newRegistrationDBServiceImpl.getFraugsterContactStatusFromDB(contact.getId());

			if (fraudPredict != null && !fraudPredict.isEmpty()) {
				Date update = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fraudPredict.get(1));
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = myFormat.format(update);
				tmSingupContact.setFraudPredictScore(fraudPredict.get(0));
				tmSingupContact.setFraudPredictDate(formattedDate);
			}
		} catch (Exception e) {
			LOG.error("Error in getFraugsterContactStatus :  ", e);
		}
	}

	/**
	 * Transform contact request set watchlist.
	 *
	 * @param contactID       the contact ID
	 * @param tmSingupContact the tm singup contact
	 * @throws ComplianceException the compliance exception
	 */
	private void transformContactRequestSetWatchlist(Integer contactID, IntuitionContactMQRequest tmSingupContact)
			throws ComplianceException {

		List<String> watchList = newRegistrationDBServiceImpl.getContactWatchlist(contactID);
		tmSingupContact.setWatchlist(watchList.toString());

	}

	/**
	 * Transform status update reason.
	 *
	 * @param contactID       the contact ID
	 * @param tmSingupContact the tm singup contact
	 * @throws ComplianceException the compliance exception
	 */
	private void transformStatusUpdateReason(Integer contactID, IntuitionContactMQRequest tmSingupContact)
			throws ComplianceException {

		List<String> statusReason = newRegistrationDBServiceImpl.getContactStatusUpdateReason(contactID);
		tmSingupContact.setStatusUpdateReason(statusReason.toString());

	}

	/**
	 * Transform compliance log.
	 *
	 * @param accountID       the account ID
	 * @param tmSingupContact the tm singup contact
	 * @throws ComplianceException the compliance exception
	 */
	private void transformComplianceLog(Integer accountID, IntuitionSignupMQRequest tmSingupRequest)
			throws ComplianceException {

		String complianceLog = newRegistrationDBServiceImpl.getComplianceLogForIntuition(accountID);
		tmSingupRequest.setComplianceLog(complianceLog);

	}

	/**
	 * Transform response.
	 *
	 * @param message the message
	 * @return the message
	 */
	@Override
	public Message<MessageContext> transformResponse(Message<MessageContext> message) {

		return null;
	}

	/**
	 * Gets the formatted date.
	 *
	 * @param date the date
	 * @return the formatted date
	 * @throws ParseException the parse exception
	 */
	public static String getFormattedDate(String date) throws ParseException {
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date fDate = formatter.parse(date);
			return formatter.format(fDate);
		}
		return null;
	}
	
	/** Add For AT-5029
	 * Gets the integer turn over.
	 *
	 * @param turnOver the turn over
	 * @return the integer turn over
	 */
	public static Integer getIntegerTurnOver(String turnOver) {
		if(turnOver.contains("-")) {
			return null;
		 } else {
			if(turnOver.contains(".")) {
				return (int) Math.round(Double.parseDouble(turnOver));
			}else {
				return Integer.parseInt(turnOver);
			}
		 }
	}

}
