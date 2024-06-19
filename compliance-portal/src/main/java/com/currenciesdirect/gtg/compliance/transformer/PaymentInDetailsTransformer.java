package com.currenciesdirect.gtg.compliance.transformer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.BaseDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.CDINCFirstCreditCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EuPoiCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.FirstCreditCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.VelocityCheck;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.WhitelistCheck;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInInfo;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.CustomCheckSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutCustomCheck;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

@Component("paymentInDetailsTransformer")
public class PaymentInDetailsTransformer extends BaseDetailsTransformer
		implements ITransform<PaymentInDetailsDto, PaymentInDBDto> {

	
	/** The Constant DASH. */
	private static final String DASH = "- - -";
	
	@Override
	public PaymentInDetailsDto transform(PaymentInDBDto paymentInDBDto) {
		PaymentInDetailsDto paymentInDetailsDto = new PaymentInDetailsDto();

		if (paymentInDBDto.getLockedBy() != null && !paymentInDBDto.getLockedBy().isEmpty()) {
			paymentInDetailsDto.setLocked(Boolean.TRUE);
			paymentInDetailsDto.setLockedBy(paymentInDBDto.getLockedBy());
			paymentInDetailsDto.setUserResourceId(paymentInDBDto.getUserResourceId());
		}

		Watchlist watchlist = deleteDuplicateWatclist(paymentInDBDto.getWatchList());
		paymentInDetailsDto.setWatchlist(watchlist);
		paymentInDetailsDto.setContactWatchlist(watchlist);
		paymentInDetailsDto.setThirdPartyPayment(paymentInDBDto.getThirdPartyPayment());
		paymentInDetailsDto.setStatus(paymentInDBDto.getStatus());
		paymentInDetailsDto.setCurrentContact(getContactDetails(paymentInDBDto));
		paymentInDetailsDto.setAccount(getAccountDetails(paymentInDBDto));
		paymentInDetailsDto.setPaymentInInfo(getPaymentInInfo(paymentInDBDto));
		paymentInDetailsDto.setDocumentList(paymentInDBDto.getDocumentList());
		paymentInDetailsDto.setStatusReason(paymentInDBDto.getStatusReason());
		paymentInDetailsDto.setFurtherPaymentDetails(paymentInDBDto.getFurtherpaymentDetails());
		Integer totalRecords = paymentInDBDto.getActivityLogs().getActivityLogData().size();
		if (paymentInDBDto.getActivityLogs().getActivityLogData().size() <= 10) {
			paymentInDetailsDto.setActivityLogs(paymentInDBDto.getActivityLogs());
			paymentInDetailsDto.getActivityLogs().setTotalRecords(totalRecords);
		} else {
			paymentInDBDto.getActivityLogs()
					.setActivityLogData(paymentInDBDto.getActivityLogs().getActivityLogData().subList(0, 10));
			paymentInDetailsDto.setActivityLogs(paymentInDBDto.getActivityLogs());
			paymentInDetailsDto.getActivityLogs().setTotalRecords(totalRecords);
		}
		paymentInDetailsDto.setFraugster(getFraugster(paymentInDBDto));
		paymentInDetailsDto.setDebitorBlacklist(getBlacklist(paymentInDBDto));
		paymentInDetailsDto.setThirdPartySanction(getDebtorSanction(paymentInDBDto));
		paymentInDetailsDto.setCustomCheck(getCustomCheck(paymentInDBDto));
		paymentInDetailsDto.setPaginationDetails(paymentInDBDto.getPaginationDetails());
		paymentInDetailsDto.setPrimaryContactName(paymentInDBDto.getPrimaryContactName());
		paymentInDetailsDto.setAlertComplianceLog(paymentInDBDto.getAlertComplianceLog());
		paymentInDetailsDto.setIntuition(getIntuition(paymentInDBDto)); //AT-4306
		
		if(paymentInDetailsDto.getAccount().getClientType().equalsIgnoreCase(Constants.CUST_TYPE_PFX)){
			paymentInDetailsDto.setName(paymentInDetailsDto.getCurrentContact().getName());
		}
		else {
			paymentInDetailsDto.setName(paymentInDetailsDto.getAccount().getName());
		}
		//added for AT-898 for Sprint 1
		paymentInDetailsDto.setNoOfContactsForAccount(paymentInDBDto.getNoOfContactsForAccount());
		paymentInDetailsDto.setIsOnQueue(paymentInDBDto.getIsOnQueue());
		//AT-3450
		paymentInDetailsDto.setPoiExists(paymentInDBDto.getPoiExists());
		//AT-4451
		paymentInDetailsDto.setAccountVersion(paymentInDBDto.getAccountVersion());
		paymentInDetailsDto.setAccountTMFlag(paymentInDBDto.getAccountTMFlag());
		return paymentInDetailsDto;
	}

	private CustomCheck getCustomCheck(PaymentInDBDto paymentInDBDto) {
		CustomCheck customCheck = new CustomCheck();
		customCheck.setCountryCheck(getCountryCheck(paymentInDBDto));
		customCheck.setPaymentOutCustomCheck(getPaymentOutCustomCheck(paymentInDBDto));
		return customCheck;
	}

	/*
	 * 1) We are checking whether country check is required to perform or not
	 * first, if it is 'NOT REQUIRED' then we will show that status on UI
	 * Changes done by Vishal J
	 */
	private CountryCheck getCountryCheck(PaymentInDBDto paymentInDBDto) {
		// constant added by Vishal J
		List<EventDBDto> countryCheckEventDBDtos = paymentInDBDto.getEventDBDtos().get(Constants.COUNTRYCHECK_CONTACT);
		CountryCheck countryCheck = new CountryCheck();

		if (countryCheckEventDBDtos == null || countryCheckEventDBDtos.isEmpty()) {
			countryCheck.setCountryCheckTotalRecords(0);
			return countryCheck;
		}

		EventDBDto eventDBDto = countryCheckEventDBDtos.get(0);
		
		Integer passCount = 0;
		Integer failCount = 0;

		// If country check status is other than 'NOT REQUIRED' then get details
		if (!Constants.NOT_REQUIRED.equalsIgnoreCase(eventDBDto.getStatus())) {
			countryCheck = getCountryCheckSummary(eventDBDto);
			if (Constants.PASS.equalsIgnoreCase(eventDBDto.getStatus())) {
				passCount = countryCheckEventDBDtos.size();
			} else {
				failCount = countryCheckEventDBDtos.size();
			}
			countryCheck.setPassCount(passCount);
			countryCheck.setFailCount(failCount);

			if (eventDBDto.getUpdatedOn() != null) {
				countryCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}

			countryCheck.setEntityType(eventDBDto.getEitityType());
			countryCheck.setId(eventDBDto.getId());
			countryCheck.setStatus(eventDBDto.getStatus());
			countryCheck.setCountryCheckTotalRecords(countryCheckEventDBDtos.size());
			countryCheck.setIsRequired(true);
		} else {
			// passing status value ('NOT_REQUIRED') to UI
			countryCheck.setIsRequired(false);
			if (eventDBDto.getUpdatedOn() != null) {
				countryCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}
			countryCheck.setStatusValue(Constants.NOT_REQUIRED_UI);
		}

		return countryCheck;
	}

	/*
	 * 1) We are checking whether custom check is required to perform or not
	 * first, if it is 'NOT REQUIRED' then we will show that status on UI
	 * Changes done by Vishal J
	 */
	@Override
	protected PaymentOutCustomCheck getPaymentOutCustomCheck(BaseDBDto paymentInDBDto) {
		// constant added by Vishal J
		List<EventDBDto> customCheckEventDBDtos = paymentInDBDto.getEventDBDtos().get(Constants.VELOCITYCHECK_ACCOUNT);
		PaymentOutCustomCheck customCheck = new PaymentOutCustomCheck();
		VelocityCheck velocityCheck = new VelocityCheck();
		WhitelistCheck whitelistCheck = new WhitelistCheck();
		FirstCreditCheck firstCreditCheck = new FirstCreditCheck();
		EuPoiCheck euPoiCheck = new EuPoiCheck();
		CDINCFirstCreditCheck cdincFirstCreditCheck = new CDINCFirstCreditCheck();
		customCheck.setVelocityCheck(velocityCheck);
		customCheck.setWhiteListCheck(whitelistCheck);
		customCheck.setFirstCreditCheck(firstCreditCheck);
		customCheck.setEuPoiCheck(euPoiCheck);
		customCheck.setCdincFirstCreditCheck(cdincFirstCreditCheck);
		if (customCheckEventDBDtos == null || customCheckEventDBDtos.isEmpty()) {
			customCheck.setTotalRecords(0);
			return customCheck;
		}
		EventDBDto eventDBDto = customCheckEventDBDtos.get(0);
		Integer passCount = 0;
		Integer failCount = 0;
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(eventDBDto.getStatus())) {
			if (Constants.PASS.equalsIgnoreCase(eventDBDto.getStatus())) {
				passCount = customCheckEventDBDtos.size();
			} else {
				failCount = customCheckEventDBDtos.size();
			}
			customCheck.setPassCount(passCount);
			customCheck.setFailCount(failCount);
		}
		if (eventDBDto.getUpdatedOn() != null) {
			customCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
		}

		if (eventDBDto.getSummary() != null) {
			CustomCheckSummary customCheckSummary = JsonConverterUtil.convertToObject(CustomCheckSummary.class,
					eventDBDto.getSummary());
			if (customCheckSummary.getWhiteListCheck() != null) {
				String amountrangeStatus = customCheckSummary.getWhiteListCheck().getAmoutRange();
				setAmountRangeStatus(whitelistCheck, customCheckSummary, amountrangeStatus);
				whitelistCheck.setCurrency(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getWhiteListCheck().getCurrency()));
				whitelistCheck.setReasonOfTransfer(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getWhiteListCheck().getReasonOfTransfer()));
				whitelistCheck.setThirdParty(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getWhiteListCheck().getThirdParty()));
			}
			setFirstCreditCheckStatus(firstCreditCheck, customCheckSummary);
			
			setEuPoiChecksStatus(euPoiCheck, customCheckSummary);
			
			setCDINCFirstCreditCheckStatus(cdincFirstCreditCheck, customCheckSummary); //AT-3738
			customCheck.setIsRequired(Boolean.TRUE);
			customCheck.setStatus(eventDBDto.getStatus());
		}
		customCheck.setTotalRecords(customCheckEventDBDtos.size());
		customCheck.setEntityType(eventDBDto.getEitityType());
		customCheck.setId(eventDBDto.getId());
		customCheck.setStatus(eventDBDto.getStatus());
		return customCheck;
	}

	private void setEuPoiChecksStatus(EuPoiCheck euPoiCheck, CustomCheckSummary customCheckSummary) {
		if (customCheckSummary.getEuPoiCheck()!= null) {
			euPoiCheck.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getEuPoiCheck().getStatus()));// AT-3349
		} else {
			euPoiCheck.setStatus(DASH);
		}
	}

	private void setFirstCreditCheckStatus(FirstCreditCheck firstCreditCheck, CustomCheckSummary customCheckSummary) {
		if (customCheckSummary.getFirstCreditCheck()!= null) {
			firstCreditCheck.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getFirstCreditCheck().getStatus()));// AT-3346
		} else {
			firstCreditCheck.setStatus(DASH);
		}
	}
	
	//AT-3738
	private void setCDINCFirstCreditCheckStatus(CDINCFirstCreditCheck cdincFirstCreditCheck, CustomCheckSummary customCheckSummary) {
		if (customCheckSummary.getCdincFirstCreditCheck()!= null) {
			cdincFirstCreditCheck.setStatus(ServiceStatusEnum.getUiStatusFromDatabaseStatus(customCheckSummary.getCdincFirstCreditCheck().getStatus()));// AT-3346
		} else {
			cdincFirstCreditCheck.setStatus(DASH);
		}
	}

	private Blacklist getBlacklist(PaymentInDBDto paymentInDBDto) {

		List<EventDBDto> blacklistEventDBDtos = paymentInDBDto.getEventDBDtos().get(Constants.BLACKLISTCONTACT);
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {
			blacklist.setFailCount(3);
			return blacklist;
		}
		EventDBDto eventDBDto = blacklistEventDBDtos.get(0);
		blacklist = getBlacklist(eventDBDto);
		BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class,
				eventDBDto.getSummary());
		if (Constants.SERVICE_FAILURE.equalsIgnoreCase(blacklistSummary.getStatus())) 
			blacklist.setFailCount(3);	
		
		return blacklist;
	}

	/**
	 * @param paymentInDBDto
	 * 
	 * @return PaymentInInfo
	 */
	private PaymentInInfo getPaymentInInfo(PaymentInDBDto paymentInDBDto) {

		PaymentInInfo paymentInInfo = new PaymentInInfo();

		paymentInInfo.setSellCurrency(paymentInDBDto.getSellCurrency());
		paymentInInfo.setDateOfPayment(DateTimeFormatter.removeTimeFromDate(paymentInDBDto.getDateOfPayment()));
		paymentInInfo.setId(paymentInDBDto.getPaymentInId());
		paymentInInfo.setThirdPartyPayment(paymentInDBDto.getThirdPartyPayment());
		paymentInInfo.setCountryOfFund(paymentInDBDto.getCountry());
		paymentInInfo.setInitialStatus(paymentInDBDto.getInitialStatus()); //AT-3471
		if(null != paymentInDBDto.getIntuitionRiskLevel() && !paymentInDBDto.getIntuitionRiskLevel().isEmpty()){ //AT-4187
			paymentInInfo.setIntuitionRiskLevel(paymentInDBDto.getIntuitionRiskLevel());
		}else {
			paymentInInfo.setIntuitionRiskLevel(Constants.DASH_UI);
		}
		updateDeletedFlag(paymentInDBDto, paymentInInfo);
		
		updateCountry(paymentInDBDto, paymentInInfo);
		
		updateCountryOfFund(paymentInDBDto, paymentInInfo);
		
		paymentInInfo.setPaymentMethod(paymentInDBDto.getPaymentMethod());
		paymentInInfo.setTransactionNumber(paymentInDBDto.getContractNumber());
		paymentInInfo.setTradePaymentId(paymentInDBDto.getTradePaymentId());
		paymentInInfo.setStatus(paymentInDBDto.getPaymentInStatus());
		paymentInInfo.setAmount(StringUtils.getNumberFormat(DecimalFormatter.convertToFourDigit(paymentInDBDto.getAmount())));
		FundsInCreateRequest req = JsonConverterUtil.convertToObject(FundsInCreateRequest.class,
				paymentInDBDto.getPaymentInAttributes());
		
		updateDebitorName(paymentInInfo, req);
		
		updateDebitorAccNumber(paymentInInfo, req);
	
		Boolean isRgDataExists = getIsRgDataExistsValue(req);//AT-3830
		Boolean isFraudSightEligible = checkIsFsAllowed(req);
		
		if (Boolean.FALSE.equals(isFraudSightEligible) && Boolean.TRUE.equals(isRgDataExists)) {
		updateRiskScore(paymentInDBDto, paymentInInfo, req);
		}else {
			updateFsScore(paymentInInfo, req,paymentInDBDto);
		}
		
		updateFraudSightData(paymentInDBDto, paymentInInfo, req);
		
		String ccName = req.getTrade().getCcFirstName();
		/**
		 * Added changes for AT-396 If PaymentMethod is SWITCH/DEBIT then set
		 * NameOnCard as ccName 
		 * changes made by- Saylee
		 */
		if (Constants.SWITCH_DEBIT.equalsIgnoreCase(paymentInInfo.getPaymentMethod())) {
			paymentInInfo.setNameOnCard(ccName);
		} else {
			if (paymentInDBDto.getDebitorName() != null && !("").equals(paymentInDBDto.getDebitorName())) {
				paymentInInfo.setNameOnCard(paymentInDBDto.getDebitorName());
			} else {
				paymentInInfo.setNameOnCard(Constants.DASH_UI);
			}
		}
		
		setLegalEntityOnUI(paymentInDBDto, paymentInInfo);
		
		//AT-3697
		if (Constants.SWITCH_DEBIT.equalsIgnoreCase(paymentInInfo.getPaymentMethod())) {
			if (req.getFraudSight() != null) {
				if (req.getFraudSight().getThreedsVersion() != null
						&& !(req.getFraudSight().getThreedsVersion().isEmpty())) {
					paymentInInfo.setThreeDsTwoAuthorised(req.getFraudSight().getThreedsVersion());
				} else {
					paymentInInfo.setThreeDsTwoAuthorised(Constants.DASH_UI);
				}
			}
			else {
				paymentInInfo.setThreeDsTwoAuthorised(Constants.DASH_UI);
			}
		} else {
			paymentInInfo.setThreeDsTwoAuthorised(Constants.DASH_UI);
		}
		updateAvsResult(paymentInInfo, req);
		updateCvcResult(paymentInInfo, req);
		//AT-4078
		updateTransferReason(paymentInInfo, req);
		return paymentInInfo;
	}

	
	private Boolean checkIsFsAllowed(FundsInCreateRequest req) {
		Boolean isFraudSightEligible;
		if (null != req.getFraudSight() && null != req.getFraudSight().getFsMessage() &&  null != req.getFraudSight().getFsScore()) {
			isFraudSightEligible=Boolean.TRUE;
		}else {
			isFraudSightEligible=Boolean.FALSE;
		}
		return isFraudSightEligible;
	}

	private Boolean getIsRgDataExistsValue(FundsInCreateRequest req) {
		Boolean isRgDataExists;
		if (null != req.getRiskScore() && null != req.getRiskScore().getTRisk() && null != req.getRiskScore().getTScore()) {
			isRgDataExists=Boolean.TRUE;
		}else {
			isRgDataExists=Boolean.FALSE;
		}
		return isRgDataExists;
	}
	
	private void setLegalEntityOnUI(PaymentInDBDto paymentInDBDto, PaymentInInfo paymentInInfo) {
		if(isNullOrEmpty(paymentInDBDto.getLegalEntity())) {
			paymentInInfo.setLegalEntity(Constants.DASH_UI);
		} else {
			paymentInInfo.setLegalEntity(paymentInDBDto.getLegalEntity());
		}
	}

  private void updateDebitorName(PaymentInInfo paymentInInfo, FundsInCreateRequest req) {
	    if(null != req.getTrade()){
				if(!StringUtils.isNullOrTrimEmpty(req.getDebtorName())){
					paymentInInfo.setDebitorName(req.getDebtorName());
				}else paymentInInfo.setDebitorName(Constants.DASH_UI);
			}else paymentInInfo.setDebitorName(Constants.DASH_UI);
	  }

private void updateRiskScore(PaymentInDBDto paymentInDBDto, PaymentInInfo paymentInInfo, FundsInCreateRequest req) {
    
	if (req.getRiskScore() != null) {
		if (req.getTscore() == null) {
			paymentInInfo.settScore(Constants.DASH_UI);
		} else {
			paymentInInfo.settScore(req.getTscore().toString());
			/**
			 * condition added to set the color to red or green for the risk_score in
			 * paymentindetails page based on the Eventservicelogstatus
			 */
			EventDBDto eventDBDto = setRiskScorecolor(paymentInDBDto);
			if (eventDBDto != null && eventDBDto.getStatus() != null && !(eventDBDto.getStatus().isEmpty())) {
				paymentInInfo.settScoreStatus(eventDBDto.getStatus());
			}
		}
	} else
		paymentInInfo.settScore(Constants.DASH_UI);
  }

     //AT-3714
	private void updateFraudSightData(PaymentInDBDto paymentInDBDto, PaymentInInfo paymentInInfo,FundsInCreateRequest req) {
			if (req.getFraudSight() != null) {	
				updateFsMessage(paymentInInfo, req);
				updateFsReasonCodes(paymentInInfo, req);
			}else {
				paymentInInfo.setFsMessage(Constants.DASH_UI);
				paymentInInfo.setFsReasonCode(Constants.DASH_UI);
			}
	}

	private void updateFsReasonCodes(PaymentInInfo paymentInInfo, FundsInCreateRequest req) {
		if(req.getFraudSight().getFsReasonCodes()== null || req.getFraudSight().getFsReasonCodes().isEmpty()){
			paymentInInfo.setFsReasonCode(Constants.DASH_UI);
		}else {
			paymentInInfo.setFsReasonCode(req.getFraudSight().getFsReasonCodes());
		}
	}

	private void updateFsMessage(PaymentInInfo paymentInInfo, FundsInCreateRequest req) {
		if(req.getFraudSight().getFsMessage() == null || req.getFraudSight().getFsMessage().isEmpty()){
			paymentInInfo.setFsMessage(Constants.DASH_UI);
		}else {
			paymentInInfo.setFsMessage(req.getFraudSight().getFsMessage());
		}
	}

	private void updateFsScore(PaymentInInfo paymentInInfo, FundsInCreateRequest req,PaymentInDBDto paymentInDBDto) {
		if(req.getFraudSight()!=null) {
			if(req.getFraudSight().getFsScore() == null || req.getFraudSight().getFsScore().isEmpty()){
			paymentInInfo.settScore(Constants.DASH_UI);
			}else {
			paymentInInfo.settScore(req.getFraudSight().getFsScore());
			}
		}else {
			paymentInInfo.settScore(Constants.DASH_UI);
		}
		EventDBDto eventDBDto = setFraudSightScorecolor(paymentInDBDto);
		if (eventDBDto != null && eventDBDto.getStatus() != null && !(eventDBDto.getStatus().isEmpty())) {
			paymentInInfo.settScoreStatus(eventDBDto.getStatus());
		}
	}

	private void updateAvsResult(PaymentInInfo paymentInInfo,FundsInCreateRequest req) {
		if(req.getTrade().getAvsResult()!=null && !(req.getTrade().getAvsResult().isEmpty())) {
			paymentInInfo.setAvsResult(req.getTrade().getAvsResult());
		}else {
			paymentInInfo.setAvsResult(Constants.DASH_UI);
		}
	}
	
	private void updateCvcResult(PaymentInInfo paymentInInfo,FundsInCreateRequest req) {
		if(req.getTrade().getCvcResult()!=null && !(req.getTrade().getCvcResult().isEmpty())) {
			paymentInInfo.setCvcResult(req.getTrade().getCvcResult());
		}else {
			paymentInInfo.setCvcResult(Constants.DASH_UI);
		}
	}
	
	//AT-4078
	private void updateTransferReason(PaymentInInfo paymentInInfo,FundsInCreateRequest req) {
		if(req.getTrade().getTransferReason()!=null && !(req.getTrade().getTransferReason().isEmpty())) {
			paymentInInfo.setTransferReason(req.getTrade().getTransferReason());
		}else {
			paymentInInfo.setTransferReason(Constants.DASH_UI);
		}
	}
	
	
  private void updateDebitorAccNumber(PaymentInInfo paymentInInfo, FundsInCreateRequest req) {
    if(null != req.getTrade()){
			if(null != req.getDebtorAccountNumber() && !req.getDebtorAccountNumber().isEmpty()){
				paymentInInfo.setDebtorAccountNumber(req.getDebtorAccountNumber());
			}else paymentInInfo.setDebtorAccountNumber(Constants.DASH_UI);
		}else paymentInInfo.setDebtorAccountNumber(Constants.DASH_UI);
  }

  private void updateCountryOfFund(PaymentInDBDto paymentInDBDto, PaymentInInfo paymentInInfo) {
    if(null != paymentInDBDto.getCountryOfFundFullName() && !paymentInDBDto.getCountryOfFundFullName().isEmpty()){
			paymentInInfo.setCountryOfFundFullName(paymentInDBDto.getCountryOfFundFullName());
		}else {
			paymentInInfo.setCountryOfFundFullName(Constants.DASH_UI);
		}
  }

  private void updateCountry(PaymentInDBDto paymentInDBDto, PaymentInInfo paymentInInfo) {
    if(null != paymentInDBDto.getCountry() && !paymentInDBDto.getCountry().isEmpty() ) {
			paymentInInfo.setCountryOfFund(paymentInDBDto.getCountry());
		}else {
			paymentInInfo.setCountryOfFund(Constants.DASH_UI);
		}
  }

  private void updateDeletedFlag(PaymentInDBDto paymentInDBDto, PaymentInInfo paymentInInfo) {
    if(paymentInDBDto.getIsDeleted().equals("true")) {
			paymentInInfo.setIsDeleted(paymentInDBDto.getIsDeleted());
			paymentInInfo.setUpdatedOn(paymentInDBDto.getUpdatedOn());
		}else{
			paymentInInfo.setIsDeleted(Constants.DASH_UI);
			paymentInInfo.setUpdatedOn(Constants.DASH_UI);
		}
  }

protected Sanction getDebtorSanction(BaseDBDto detailsDBDto) {
	List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.SANCTIONDEBTOR);
	Sanction sanction = new Sanction();
	int passCount = 0;
	int failCount = 0;
	if (eventDBDtos == null || eventDBDtos.isEmpty()) {
		sanction.setSanctionTotalRecords(0);
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
protected EventDBDto setRiskScorecolor(PaymentInDBDto paymentInDBDto){
	EventDBDto eventDBDto=null;
	if((paymentInDBDto.getEventDBDtos()!=null) && (!paymentInDBDto.getEventDBDtos().isEmpty()) && (paymentInDBDto.getEventDBDtos().get(Constants.CARD_FRAUD_SCORE_CONTACT)!=null) && (!paymentInDBDto.getEventDBDtos().get(Constants.CARD_FRAUD_SCORE_CONTACT).isEmpty()))
	{
			List<EventDBDto> eventDBDtos = paymentInDBDto.getEventDBDtos().get(Constants.CARD_FRAUD_SCORE_CONTACT);
			 eventDBDto= eventDBDtos.get(0);
			
		}
	
	return eventDBDto;
}
//AT-3714
protected EventDBDto setFraudSightScorecolor(PaymentInDBDto paymentInDBDto){
	EventDBDto eventDBDto=null;
	if((paymentInDBDto.getEventDBDtos()!=null) && (!paymentInDBDto.getEventDBDtos().isEmpty()) && (paymentInDBDto.getEventDBDtos().get(Constants.FRAUD_SIGHT_SCORE_CONTACT)!=null) && (!paymentInDBDto.getEventDBDtos().get(Constants.FRAUD_SIGHT_SCORE_CONTACT).isEmpty()))
	{
			List<EventDBDto> eventDBDtos = paymentInDBDto.getEventDBDtos().get(Constants.FRAUD_SIGHT_SCORE_CONTACT);
			 eventDBDto= eventDBDtos.get(0);
			
		}
	
	return eventDBDto;
}
	
}
