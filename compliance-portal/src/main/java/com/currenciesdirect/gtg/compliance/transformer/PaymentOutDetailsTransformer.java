package com.currenciesdirect.gtg.compliance.transformer;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.commons.domain.internalruleservice.BlacklistPayRefSummary;
import com.currenciesdirect.gtg.compliance.core.ITransform;
import com.currenciesdirect.gtg.compliance.core.domain.Blacklist;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.CountryCheck;
import com.currenciesdirect.gtg.compliance.core.domain.CustomCheck;
import com.currenciesdirect.gtg.compliance.core.domain.EventDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.Sanction;
import com.currenciesdirect.gtg.compliance.core.domain.Watchlist;
import com.currenciesdirect.gtg.compliance.core.domain.internalrule.BlacklistSummary;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutBlacklist;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDBDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutDetailsDto;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutInfo;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutPaymentReference;
import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutSanction;
import com.currenciesdirect.gtg.compliance.util.DateTimeFormatter;
import com.currenciesdirect.gtg.compliance.util.DecimalFormatter;
import com.currenciesdirect.gtg.compliance.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.util.StringUtils;

/**
 * The Class PaymentOutDetailsTransform.
 */
@Component("paymentOutDetailsTransformer")
public class PaymentOutDetailsTransformer extends BaseDetailsTransformer implements ITransform<PaymentOutDetailsDto, PaymentOutDBDto> {

	@Override
	public PaymentOutDetailsDto transform(PaymentOutDBDto paymentOutDBDto) {
		PaymentOutDetailsDto paymentOutDetailDto = new PaymentOutDetailsDto();
		paymentOutDetailDto.setPaginationDetails(paymentOutDBDto.getPaginationDetails());
		paymentOutDetailDto.setCurrentRecord(3);
		paymentOutDetailDto.setTotalRecords(30);
		if (paymentOutDBDto.getLockedBy() != null && !paymentOutDBDto.getLockedBy().isEmpty()) {
			paymentOutDetailDto.setLocked(Boolean.TRUE);
			paymentOutDetailDto.setLockedBy(paymentOutDBDto.getLockedBy());
			paymentOutDetailDto.setUserResourceId(paymentOutDBDto.getUserResourceId());
		}
		Watchlist watchlist = deleteDuplicateWatclist(paymentOutDBDto.getWatchList());
		paymentOutDetailDto.setWatchlist(watchlist);
		paymentOutDetailDto.setContactWatchlist(watchlist);
		paymentOutDetailDto.setStatusReason(paymentOutDBDto.getStatusReason());
	
		paymentOutDetailDto.setCurrentContact(getContactDetails(paymentOutDBDto));
		paymentOutDetailDto.setAccount(getAccountDetails(paymentOutDBDto));
		
		paymentOutDetailDto.setPaymentOutInfo(getPaymentOutInfo(paymentOutDBDto));
		Integer totalRecords = paymentOutDBDto.getActivityLogs().getActivityLogData().size();
		if (paymentOutDBDto.getActivityLogs().getActivityLogData().size() <= 10) {
			paymentOutDetailDto.setActivityLogs(paymentOutDBDto.getActivityLogs());
			paymentOutDetailDto.getActivityLogs().setTotalRecords(totalRecords);
		} else {
			paymentOutDBDto.getActivityLogs()
					.setActivityLogData(paymentOutDBDto.getActivityLogs().getActivityLogData().subList(0, 10));
			paymentOutDetailDto.setActivityLogs(paymentOutDBDto.getActivityLogs());
			paymentOutDetailDto.getActivityLogs().setTotalRecords(totalRecords);
		}
		paymentOutDetailDto.setActivityLogs(paymentOutDBDto.getActivityLogs());
		paymentOutDetailDto.setBlacklist(getBlacklist(paymentOutDBDto));
		paymentOutDetailDto.setSanction(getSanction(paymentOutDBDto));
		paymentOutDetailDto.setFraugster(getFraugster(paymentOutDBDto));
		paymentOutDetailDto.setStatus(paymentOutDBDto.getStatus());
		paymentOutDetailDto.setFurtherPaymentDetails(paymentOutDBDto.getFurtherpaymentDetails());
		paymentOutDetailDto.setDocumentList(paymentOutDBDto.getDocumentList());
		paymentOutDetailDto.setAlertComplianceLog(paymentOutDBDto.getAlertComplianceLog());
		paymentOutDetailDto.setPaymentReference(getPaymentReference(paymentOutDBDto)); //AT-3658
		paymentOutDetailDto.setIntuition(getIntuition(paymentOutDBDto)); //AT-4306
		
		if(paymentOutDetailDto.getAccount().getClientType().equalsIgnoreCase(Constants.CUST_TYPE_PFX)){
			paymentOutDetailDto.setName(paymentOutDetailDto.getCurrentContact().getName());
		}
		else {
			paymentOutDetailDto.setName(paymentOutDetailDto.getAccount().getName());
		}
		paymentOutDetailDto.setCustomCheck(getCustomCheck(paymentOutDBDto));
		//added for AT-898 for Sprint 1
		paymentOutDetailDto.setNoOfContactsForAccount(paymentOutDBDto.getNoOfContactsForAccount());
		paymentOutDetailDto.setIsOnQueue(paymentOutDBDto.getIsOnQueue());
		paymentOutDetailDto.setCustomRuleFPFlag(paymentOutDBDto.getCustomRuleFPFlag());//Add for AT-3161
		paymentOutDetailDto.setPoiExists(paymentOutDBDto.getPoiExists()); //AT-3450
		paymentOutDetailDto.setAccountTMFlag(paymentOutDBDto.getAccountTMFlag()); //AT-4451
		paymentOutDetailDto.setAccountVersion(paymentOutDBDto.getAccountVersion()); //AT-4451
		return paymentOutDetailDto;
	}


	private PaymentOutInfo getPaymentOutInfo(PaymentOutDBDto detailsDBDto) {
		FundsOutRequest request = JsonConverterUtil.convertToObject(FundsOutRequest.class, detailsDBDto.getPaymentOutAttributes());
		PaymentOutInfo paymentOutInfo = new PaymentOutInfo();
		if(request != null && request.getBeneficiary() != null) {
			paymentOutInfo.setAmount(StringUtils.getNumberFormat(DecimalFormatter.convertToFourDigit(request.getBeneficiary().getAmount())));
			paymentOutInfo.setInitialStatus(detailsDBDto.getInitialStatus());//AT-3471
			/**Code added if last name of beneficiary is null - Vishal J*/
			if(null!= request.getBeneficiary().getLastName() && !request.getBeneficiary().getLastName().isEmpty())
				paymentOutInfo.setBeneficiaryName(detailsDBDto.getBeneficiaryName());
			else paymentOutInfo.setBeneficiaryName(request.getBeneficiary().getFirstName());
		}
		//added by neelesh pant
		if (request != null && request.getTrade() != null && request.getTrade().getMaturityDate() != null)
		{
	
           paymentOutInfo.setMaturityDate(DateTimeFormatter.dateFormat(request.getTrade().getMaturityDate()));
        }
		
		updateDeletedFlag(detailsDBDto, paymentOutInfo);
		
		getPaymentOutInfoData(detailsDBDto, paymentOutInfo);
		
		setLegalEntityOnUI(detailsDBDto, paymentOutInfo);
		
		if(null!=request)
			paymentOutInfo.setBeneAccountNumber(request.getBeneficiary().getAccountNumber());
		
		return paymentOutInfo;
	}
	
	private void setLegalEntityOnUI(PaymentOutDBDto paymentOutDBDto, PaymentOutInfo paymentOutInfo) {
		if(isNullOrEmpty(paymentOutDBDto.getLegalEntity())) {
			paymentOutInfo.setLegalEntity(Constants.DASH_UI);
		} else {
			paymentOutInfo.setLegalEntity(paymentOutDBDto.getLegalEntity());
		}
	}

	private void getPaymentOutInfoData(PaymentOutDBDto detailsDBDto, PaymentOutInfo paymentOutInfo) {
		paymentOutInfo.setBuyCurrency(detailsDBDto.getBuyCurrency());
		paymentOutInfo.setCountryOfBeneficiary(detailsDBDto.getBeneficiaryCountry());
		if(null != detailsDBDto.getBeneficiaryCountryFullName() && !detailsDBDto.getBeneficiaryCountryFullName().isEmpty()){
			paymentOutInfo.setCountryOfBeneficiaryFullName(detailsDBDto.getBeneficiaryCountryFullName());
		}else {
			paymentOutInfo.setCountryOfBeneficiaryFullName(Constants.DASH_UI);
		}
		paymentOutInfo.setDateOfPayment(DateTimeFormatter.removeTimeFromDate(detailsDBDto.getDateOfPayment()));
		paymentOutInfo.setId(detailsDBDto.getPaymentOutId());
		paymentOutInfo.setReasonForTransfer(detailsDBDto.getReasonOfTransfer());
		paymentOutInfo.setStatus(detailsDBDto.getPaymentOutStatus());
		paymentOutInfo.setTransactionNumber(detailsDBDto.getContractNumber());
		paymentOutInfo.setTradePaymentId(detailsDBDto.getTradePaymentId());
		paymentOutInfo.setTradeBeneficiaryId(detailsDBDto.getTradeBeneficiaryId());
		paymentOutInfo.setThirdPartyPayment(getPaymentOutThirdPartyPayment(detailsDBDto));
		if(null != detailsDBDto.getIntuitionRiskLevel() && !detailsDBDto.getIntuitionRiskLevel().isEmpty()){ //AT-4187
			paymentOutInfo.setIntuitionRiskLevel(detailsDBDto.getIntuitionRiskLevel());
		}else {
			paymentOutInfo.setIntuitionRiskLevel(Constants.DASH_UI);
		}
	}

	/**
	 * Gets the sanction.
	 *
	 * @param detailsDBDto
	 *            the details DB dto
	 * @return the sanction
	 */
	private PaymentOutSanction getSanction(PaymentOutDBDto detailsDBDto) {
		PaymentOutSanction sanction = new PaymentOutSanction();
		sanction.setBankSanction(getBankSanction(detailsDBDto));
		sanction.setBeneficiarySanction(getBeneficiarySanction(detailsDBDto));
		sanction.setContactSanction(getContactSanction(detailsDBDto));
		return sanction;

	}

	private Sanction getBeneficiarySanction(PaymentOutDBDto detailsDBDto) {
		//constant added by Vishal J
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.SANCTIONBENEFICIARY);
		Sanction sanction = new Sanction();
		int passCount = 0;
		int failCount = 0;
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			sanction.setEntityId(detailsDBDto.getTradeBeneficiaryId());
			sanction.setEntityType(Constants.BENEFECIARY);
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

	private Sanction getBankSanction(PaymentOutDBDto detailsDBDto) {
		//constant added by Vishal J
		List<EventDBDto> eventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.SANCTIONBANK);
		Sanction sanction = new Sanction();
		int passCount = 0;
		int failCount = 0;
		if (eventDBDtos == null || eventDBDtos.isEmpty()) {
			sanction.setEntityId(detailsDBDto.getBankid());
			sanction.setEntityType(Constants.BANK);
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

	private PaymentOutBlacklist getBlacklist(PaymentOutDBDto detailsDBDto) {
		PaymentOutBlacklist blacklist = new PaymentOutBlacklist();
		blacklist.setBankBlacklist(getBankBlacklist(detailsDBDto));
		blacklist.setBenficiaryBlacklist(getBeneficiaryBlacklist(detailsDBDto));
		blacklist.setContactBlacklist(getContactBlacklist(detailsDBDto));
		return blacklist;

	}

	private Blacklist getBankBlacklist(PaymentOutDBDto detailsDBDto) {
		List<EventDBDto> blacklistEventDBDtos = detailsDBDto.getEventDBDtos().get("BLACKLISTBANK");
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {

			return blacklist;
		}
		EventDBDto eventDBDto = blacklistEventDBDtos.get(0);
		return getBlacklist(eventDBDto);

	}

	private Blacklist getContactBlacklist(PaymentOutDBDto detailsDBDto) {
		List<EventDBDto> blacklistEventDBDtos = detailsDBDto.getEventDBDtos().get("BLACKLISTCONTACT");
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {
			return blacklist;
		}
		EventDBDto eventDBDto = blacklistEventDBDtos.get(0);
		blacklist = getBlacklist(eventDBDto);
		//condition added by Vishal J to avoid null pointer exception
		if(Boolean.TRUE.equals(blacklist.getIsRequired()))
		{
			setContactBlacklistPassAndFailCount(blacklist);
		}
		return blacklist;

	}

	private Blacklist getBeneficiaryBlacklist(PaymentOutDBDto detailsDBDto) {
		//constant added by Vishal J
		List<EventDBDto> blacklistEventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.BLACKLISTBENEFICIARY);
		Blacklist blacklist = new Blacklist();
		if (blacklistEventDBDtos == null || blacklistEventDBDtos.isEmpty()) {
			return blacklist;
		}
		EventDBDto eventDBDto = blacklistEventDBDtos.get(0);
		blacklist = getBlacklist(eventDBDto);
		BlacklistSummary blacklistSummary = JsonConverterUtil.convertToObject(BlacklistSummary.class,
				eventDBDto.getSummary());
		//condition added by Vishal J to avoid null pointer exception
		if(Boolean.TRUE.equals(blacklist.getIsRequired()))
		{
			setBeneficiaryBlacklistPassAndFailCount(blacklist);
		}
	
		if (Constants.SERVICE_FAILURE.equalsIgnoreCase(blacklistSummary.getStatus())) 
			blacklist.setFailCount(3);	
		
		return blacklist;

	}
	
	private CustomCheck getCustomCheck(PaymentOutDBDto detailsDBDto) {
		CustomCheck customCheck = new CustomCheck();
		customCheck.setCountryCheck(getCountryCheck(detailsDBDto));
		customCheck.setPaymentOutCustomCheck(getPaymentOutCustomCheck(detailsDBDto));
		return customCheck;
	}

	/*
	 * 1) We are checking whether country check is required to perform or not first, if it is 'NOT REQUIRED' then we will show that status on UI 
	 *	Changes done by Vishal J
	 */
	private CountryCheck getCountryCheck(PaymentOutDBDto detailsDBDto) {
		//constant added by Vishal J
		List<EventDBDto> countryCheckEventDBDtos = detailsDBDto.getEventDBDtos().get(Constants.COUNTRYCHECK_BENEFICIARY);
		CountryCheck countryCheck = new CountryCheck();

		if (countryCheckEventDBDtos == null || countryCheckEventDBDtos.isEmpty()) {
			countryCheck.setCountryCheckTotalRecords(0);
			return countryCheck;
		}

		EventDBDto eventDBDto = countryCheckEventDBDtos.get(0);
		
		Integer passCount = 0;
		Integer failCount = 0;
		
		//If country check status is other than 'NOT REQUIRED' then get details
		if(!Constants.NOT_REQUIRED.equalsIgnoreCase(eventDBDto.getStatus()))
		{
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
		}
		else
		{
			//passing status value ('NOT_REQUIRED') to UI
			countryCheck.setIsRequired(false);
			if (eventDBDto.getUpdatedOn() != null) {
				countryCheck.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
			}
			countryCheck.setStatusValue(Constants.NOT_REQUIRED_UI);
			countryCheck.setRiskLevel(Constants.NOT_REQUIRED_UI);
		}
		return countryCheck;
	}

	private void setContactBlacklistPassAndFailCount(Blacklist blacklist) {
		Integer passCount = 0;
		Integer failCount = 0;
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getEmail())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getEmail())){
			failCount++;
		}
		if (Constants.FALSE.equalsIgnoreCase(blacklist.getIp())) {
			passCount++;
		} else if (Constants.TRUE.equalsIgnoreCase(blacklist.getIp())){
			failCount++;
		}
		if (Boolean.TRUE.equals(blacklist.getPhone())) {
			passCount++;
		} else {
			failCount++;
		}
		if (Boolean.TRUE.equals(blacklist.getStatus())) {
			passCount++;
		} else {
			failCount++;
		}
		blacklist.setPassCount(passCount);
		blacklist.setFailCount(failCount);
	}
	
	private Boolean getPaymentOutThirdPartyPayment(PaymentOutDBDto detailsDBDto) {
		boolean isThirdParty = false;
		try {
		String paymentOutAttribute = detailsDBDto.getPaymentOutAttributes();
		JSONObject paymentOutAttributeObject = new JSONObject(paymentOutAttribute);
		JSONObject paymentOutAttributeObjectTrade =  paymentOutAttributeObject.getJSONObject("trade");
		
		Boolean thirdPartyPayment = paymentOutAttributeObjectTrade.getBoolean("third_party_payment");
		if(Boolean.TRUE.equals(thirdPartyPayment)) {
			isThirdParty = true;
		}
		return isThirdParty;
		}
		catch(Exception e) {
			return Boolean.FALSE;
		}
	}
	
	private void updateDeletedFlag(PaymentOutDBDto paymentoutDBDto, PaymentOutInfo paymentOutInfo) {
	    if(paymentoutDBDto.getIsDeleted().equals("true")) {
	    	paymentOutInfo.setIsDeleted(paymentoutDBDto.getIsDeleted());
	    	paymentOutInfo.setUpdatedOn(paymentoutDBDto.getUpdatedOn());
		}else{
			paymentOutInfo.setIsDeleted(Constants.DASH_UI);
			paymentOutInfo.setUpdatedOn(Constants.DASH_UI);
		} 
	 }
	
	//Added for AT-3658
		protected PaymentOutPaymentReference getPaymentReference(PaymentOutDBDto detailsDBDto) {
			List<EventDBDto> eventDBDtoPayRef = detailsDBDto.getEventDBDtos().get("BLACKLIST_PAY_REFBENEFICIARY");
			PaymentOutPaymentReference paymentReference = new PaymentOutPaymentReference();

			Integer passCount = 0;
			Integer failCount = 0;
			
			paymentReference.setIsRequired(Boolean.TRUE);
			
			if (eventDBDtoPayRef == null || eventDBDtoPayRef.isEmpty()) {
				paymentReference.setCheckedOn(Constants.DASH_UI);
				paymentReference.setPaymentReference(Constants.DASH_UI);
				paymentReference.setMatchedKeyword(Constants.DASH_UI);
				paymentReference.setOverallStatus(Constants.NOT_AVAILABLE_UI);
				paymentReference.setTotalRecords(0);
				return paymentReference;
			}
			EventDBDto eventDBDto = eventDBDtoPayRef.get(0);
			BlacklistPayRefSummary payRefSummary = JsonConverterUtil.convertToObject(BlacklistPayRefSummary.class,
					(eventDBDto).getSummary());
			
			if(payRefSummary != null)
			{
				if (eventDBDto.getUpdatedOn() != null) {
					paymentReference.setCheckedOn(DateTimeFormatter.dateTimeFormatter(eventDBDto.getUpdatedOn()));
				}
				paymentReference.setPaymentReference(payRefSummary.getPaymentReference());
				paymentReference.setMatchedKeyword(payRefSummary.getSanctionText());
				paymentReference.setClosenessScore(payRefSummary.getTokenSetRatio());
				paymentReference.setOverallStatus(eventDBDto.getStatus());
			} else {
				paymentReference.setCheckedOn(Constants.DASH_UI);
				paymentReference.setPaymentReference(Constants.DASH_UI);
				paymentReference.setMatchedKeyword(Constants.DASH_UI);
				paymentReference.setOverallStatus(Constants.NOT_AVAILABLE_UI);
			}
			
			if (Constants.PASS.equalsIgnoreCase(eventDBDto.getStatus())) {
				passCount = eventDBDtoPayRef.size();
			} else {
				failCount = eventDBDtoPayRef.size();
			}
			
			paymentReference.setPassCount(passCount);
			paymentReference.setFailCount(failCount);
			paymentReference.setTotalRecords(eventDBDtoPayRef.size());
			
			return paymentReference;
		}
}
