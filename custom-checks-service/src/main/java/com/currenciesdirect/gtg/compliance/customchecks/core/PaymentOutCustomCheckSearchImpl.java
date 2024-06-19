package com.currenciesdirect.gtg.compliance.customchecks.core;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.EuPoiCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.fundsout.request.Beneficiary;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.ApprovedCurrencyAmountRangePair;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.HighRiskCountry;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.customchecks.core.cache.CustomCheckConcreteDataBuilder;
import com.currenciesdirect.gtg.compliance.customchecks.dbport.CacheDBServiceImpl;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckFraudPredictRules;
import com.currenciesdirect.gtg.compliance.customchecks.domain.CustomCheckVelocityRules;
import com.currenciesdirect.gtg.compliance.customchecks.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customchecks.domain.fundsout.FundsOutRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.esport.Constants;
import com.currenciesdirect.gtg.compliance.customchecks.esport.ESIndexingProcessor;
import com.currenciesdirect.gtg.compliance.customchecks.esport.ESSearchProcessor;
import com.currenciesdirect.gtg.compliance.customchecks.esport.SearchResult;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class PaymentOutCustomCheckSearchImpl.
 */
public class PaymentOutCustomCheckSearchImpl extends AbstractCustomCheckService
		implements IPaymentOutCustomCheckService {

	/** The i payment out search service. */
	@SuppressWarnings("squid:S3077")
	private static volatile IPaymentOutCustomCheckService iPaymentOutSearchService;

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PaymentOutCustomCheckSearchImpl.class);

	/** The db service impl. */
	private ICacheDBService dbServiceImpl = CacheDBServiceImpl.getInstance();

	/**
	 * Gets the single instance of PaymentOutCustomCheckSearchImpl.
	 *
	 * @return single instance of PaymentOutCustomCheckSearchImpl
	 */
	public static IPaymentOutCustomCheckService getInstance() {
		if (iPaymentOutSearchService == null) {
			synchronized (PaymentOutCustomCheckSearchImpl.class) {
				if (iPaymentOutSearchService == null) {
					iPaymentOutSearchService = new PaymentOutCustomCheckSearchImpl();
				}
			}
		}
		return iPaymentOutSearchService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * IPaymentOutCustomCheckService#performVelocityAndWhilteListChecks(com.
	 * currenciesdirect.gtg.compliance.customchecks.domain.request.
	 * CustomChecksRequest)
	 */
	@Override
	public CustomCheckResponse performVelocityAndWhilteListChecks(CustomChecksRequest document)
			throws CustomChecksException {
		/*
		 * CustomCheckResponse response = new CustomCheckResponse();
		 * response.setAccId(document.getAccId());
		 * response.setOrgCode(document.getOrgCode());
		 * response.setPaymentTransId(document.getPaymentTransId());
		 * response.setOverallStatus(ResponseStatus.PASS.getStatus());
		 * document.getESDocument().setAccId(document.getAccId()); FundsOutRequest
		 * fRequest = (FundsOutRequest) document.getESDocument(); SimpleDateFormat sdf =
		 * new SimpleDateFormat("yyyy-MM-dd"); String createdOn = sdf.format(new
		 * Date()); ESSearchProcessor searchProcessor = new ESSearchProcessor(); String
		 * type = document.getESDocument().getType(); // changes for jira AT-173 //
		 * changes-sending beneficiary account number instead of benificiary //
		 * firstname and lastname // made by -Saylee SearchResult result =
		 * searchProcessor.searchFundsOutDocument(fRequest.getOrgCode(),
		 * document.getAccId(), fRequest.getBeneficiary().getAccountNumber(), createdOn,
		 * fRequest.getTrade().getTradeAccountNumber());
		 * 
		 * AccountWhiteList existingAccWhiteList = result.getAccWhiteList(); Beneficiary
		 * bene = fRequest.getBeneficiary(); VelocityCheckResponse vResponse =
		 * performVelocityCheck(fRequest, result, document.getESDocument().getType(),
		 * document.getAccId(), existingAccWhiteList);
		 * 
		 *//** Code added to resolve AT-1122 */
		/*
		*//**
			 * Bene Name+AccountNumber+Country should be whitelisted if this combination
			 * does not match, payment should be on HOLD
			 *
			 * Use code for Concantenated string & Displayname for pretty print on UI ex:
			 * John Doe, Account: 12345, Country: United Kingdom (UK)
			 **//*
				 * 
				 * String code; code = bene.getFullName() + bene.getAccountNumber() +
				 * bene.getCountry(); if (null != existingAccWhiteList) for (HighRiskCountry hrc
				 * : existingAccWhiteList.getApprovedHighRiskCountryList()) { if
				 * (hrc.getCountryCode().equalsIgnoreCase(code)) {
				 * response.setCountryWhitelisted(true); } }
				 * 
				 * AccountWhiteList updatedWhiteList = updateAccountWhiteListData(document,
				 * existingAccWhiteList); if (null == existingAccWhiteList) {
				 * updatedWhiteList.setCreatedOn(new
				 * Timestamp(System.currentTimeMillis()).toString()); }
				 * updatedWhiteList.setUpdatedOn(new
				 * Timestamp(System.currentTimeMillis()).toString());
				 * 
				 * response.setVelocityCheck(vResponse);
				 * response.setWhiteListCheck(performFundsOutWhiteListCheck());
				 * response.setAccountWhiteList(updatedWhiteList);
				 * 
				 * //Add for AT-3349 EuPoiCheckResponse euPoiCheckResponse = new
				 * EuPoiCheckResponse();
				 * 
				 * if(LegalEntityEnum.CDLEU.getLECode().equals(fRequest.getTrade().
				 * getCustLegalEntity()) ||
				 * LegalEntityEnum.FCGEU.getLECode().equals(fRequest.getTrade().
				 * getCustLegalEntity()) ||
				 * LegalEntityEnum.TOREU.getLECode().equals(fRequest.getTrade().
				 * getCustLegalEntity())) { euPoiCheckResponse = performEuPoiCheck(document);
				 * }else {
				 * euPoiCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus()); }
				 * response.setEuPoiCheck(euPoiCheckResponse);
				 * 
				 * //Add for AT-3161 if(fRequest.getTrade().getCustType().equals("PFX")){
				 * LinkedHashMap<String, String> map = (LinkedHashMap<String, String>)
				 * document.getfPScoreUpdateOn();//changes for AT-3243
				 * performFraudPredictCheck(response, map); updateOverallStatusPFX(response);
				 * }else { updateOverallStatus(response); }
				 * 
				 * new ESIndexingProcessor().upsertDocument(Constants.INDEX_WHITELIST,
				 * Constants.TYPE_ACCOUNT, document.getAccId().toString(),
				 * JsonConverterUtil.convertToJsonWithoutNull(updatedWhiteList));
				 * 
				 * if (DocumentType.FUNDS_OUT_DELETE.name().equalsIgnoreCase(type))
				 * fRequest.setIsDeleted(Boolean.TRUE);
				 * 
				 * new ESIndexingProcessor().insertDocument(Constants.INDEX_FUNDSOUT,
				 * Constants.TYPE_CONTRACT, document.getPaymentTransId().toString(),
				 * JsonConverterUtil.convertToJsonWithoutNull(fRequest));
				 */
		VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
		velocityCheckResponse.setStatus("PASS");
		velocityCheckResponse.setBeneCheck("PASS");
		velocityCheckResponse.setNoOffundsoutTxn("PASS");
		velocityCheckResponse.setPermittedAmoutcheck("PASS");
		
		WhiteListCheckResponse whiteListCheckResponse = new WhiteListCheckResponse();
		whiteListCheckResponse.setStatus("PASS");
		whiteListCheckResponse.setAmoutRange("PASS");
		whiteListCheckResponse.setReasonOfTransfer("PASS");
		whiteListCheckResponse.setThirdParty("PASS");
		whiteListCheckResponse.setCurrency("PASS");
				
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
		euPoiCheckResponse.setStatus("PASS");
		
		CustomCheckResponse response = new CustomCheckResponse();
		response.setWhiteListCheck(whiteListCheckResponse);
		response.setVelocityCheck(velocityCheckResponse);
		response.setEuPoiCheck(euPoiCheckResponse);
		
		return response;
	}

	/**
	 * Perform velocity check.
	 *
	 * @param fRequest             the f request
	 * @param result               the result
	 * @param type                 the type
	 * @param accId                the acc id
	 * @param existingAccWhiteList the existing acc white list
	 * @return the velocity check response
	 * @throws CustomChecksException the custom checks exception
	 */
	private VelocityCheckResponse performVelocityCheck(FundsOutRequest fRequest, SearchResult result, String type,
			Integer accId, AccountWhiteList existingAccWhiteList) throws CustomChecksException {

		VelocityCheckResponse vResponse = new VelocityCheckResponse();
		vResponse.setNoOffundsoutTxn(ResponseStatus.PASS.getStatus());
		vResponse.setStatus(ResponseStatus.FAIL.getStatus());
		/*
		 * AT-1089 Customer limit Override organization amount limit for custom rules
		 * checks
		 */

		CustomCheckVelocityRules rules = CustomCheckConcreteDataBuilder.getInstance()
				.getVelocityRule(fRequest.getOrgCode(), fRequest.getCustType(), "PAYMENT_OUT");
		CustomCheckVelocityRules accRules = dbServiceImpl.getAccountVelocityRules(accId);
		if (null != accRules && null != rules) {
			if (accRules.getAmountThreshold() > rules.getAmountThreshold())
				performpermittedAmountcheck(fRequest, accRules, vResponse);
			else
				performpermittedAmountcheck(fRequest, rules, vResponse);

			if (accRules.getCountThreshold() > rules.getCountThreshold())
				performnumberOfTransaction(fRequest, result, type, accRules, vResponse);
			else
				performnumberOfTransaction(fRequest, result, type, rules, vResponse);
		} else {
			performnumberOfTransaction(fRequest, result, type, rules, vResponse);
			performpermittedAmountcheck(fRequest, rules, vResponse);
		}

		performbeneCheck(result, vResponse, fRequest.getBeneficiary().getAccountNumber());

		// AT-1200 White list beneficiary for an account
		if (null != existingAccWhiteList && existingAccWhiteList.getApprovedOPIAccountNumber()
				.contains(fRequest.getBeneficiary().getAccountNumber())) {
			vResponse.setBeneCheck(ResponseStatus.PASS.getStatus());
		}

		if (vResponse.getBeneCheck().equals(ResponseStatus.PASS.getStatus())
				&& vResponse.getNoOffundsoutTxn().equals(ResponseStatus.PASS.getStatus())
				&& vResponse.getPermittedAmoutcheck().equals(ResponseStatus.PASS.getStatus()))
			vResponse.setStatus(ResponseStatus.PASS.getStatus());
		return vResponse;

	}

	/**
	 * Perform funds out white list check.
	 *
	 * @param fRequest     the f request
	 * @param accWhiteList the acc white list
	 * @return the white list check response
	 * @throws CustomChecksException the custom checks exception
	 */
	private WhiteListCheckResponse performFundsOutWhiteListCheck() throws CustomChecksException {
		WhiteListCheckResponse wResponse = new WhiteListCheckResponse();
		String status = ResponseStatus.NOT_REQUIRED.getStatus();
		try {
			wResponse.setStatus(status);
			wResponse.setAmoutRange(status);
			wResponse.setCurrency(status);
			wResponse.setReasonOfTransfer(status);
			wResponse.setThirdParty(status);

		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_SEARCH_REQUEST, e);
		}

		return wResponse;
	}

	/**
	 * Performpermitted amountcheck.
	 *
	 * @param fRequest  the f request
	 * @param rules     the rules
	 * @param vResponse the v response
	 * @return the velocity check response
	 */
	private VelocityCheckResponse performpermittedAmountcheck(FundsOutRequest fRequest, CustomCheckVelocityRules rules,
			VelocityCheckResponse vResponse) {

		if (null != rules && fRequest.getTrade().getBuyingAmountBaseValue() >= rules.getAmountThreshold()) {
			vResponse.setPermittedAmoutcheck(ResponseStatus.FAIL.getStatus());
			Long maxAmount = Math.round(rules.getAmountThreshold());
			vResponse.setMaxAmount(Double.parseDouble(maxAmount.toString()));
		} else {
			vResponse.setPermittedAmoutcheck(ResponseStatus.PASS.getStatus());
		}

		return vResponse;
	}

	/**
	 * Performnumber of transaction.
	 *
	 * @param fRequest  the f request
	 * @param result    the result
	 * @param type      the type
	 * @param rules     the rules
	 * @param vResponse the v response
	 * @return the velocity check response
	 */
	private VelocityCheckResponse performnumberOfTransaction(FundsOutRequest fRequest, SearchResult result, String type,
			CustomCheckVelocityRules rules, VelocityCheckResponse vResponse) {

		if (Constants.FUNDS_OUT_ADD.equalsIgnoreCase(type)) {
			getNumberofTransactionCount(fRequest, result, rules, vResponse);
		} else {
			if (Constants.FUNDS_OUT_REPEAT.equalsIgnoreCase(type)) {
				if (null != rules && result.getIntraDayCount() > rules.getCountThreshold()) {
					vResponse.setNoOffundsoutTxn(ResponseStatus.FAIL.getStatus());
					vResponse.setNoOfFundsOutTxnDetails("(Current Count : " + result.getIntraDayCount()
							+ ", Max allowed count:" + rules.getCountThreshold() + ")");
				} else {
					vResponse.setNoOffundsoutTxn(ResponseStatus.PASS.getStatus());
				}
			}
		}
		return vResponse;
	}

	/**
	 * Performbene check.
	 *
	 * @param result             the result
	 * @param vResponse          the v response
	 * @param beneAccountToCheck the bene account to check
	 * @return the velocity check response
	 * @throws CustomChecksException the custom checks exception
	 */
	private VelocityCheckResponse performbeneCheck(SearchResult result, VelocityCheckResponse vResponse,
			String beneAccountToCheck) throws CustomChecksException {

		if (result.getOtherAccountsWithBene() > 0) {
			vResponse.setBeneCheck(ResponseStatus.FAIL.getStatus());
			vResponse.setMatchedAccNumber(result.getMatchedAccNumber());
		} else {
			vResponse.setBeneCheck(ResponseStatus.PASS.getStatus());
		}
		if (dbServiceImpl.isBeneficiaryAccountWhitelisted(beneAccountToCheck)) {
			vResponse.setBeneCheck(ResponseStatus.PASS.getStatus());
			vResponse.setMatchedAccNumber("");
		}

		return vResponse;
	}

	/**
	 * Gets the perform reason of transfer check.
	 *
	 * @return the perform reason of transfer check
	 */
	public static Boolean getPerformReasonOfTransferCheck() {
		Boolean isPerformReasonOfTransferCheck = Boolean.FALSE;
		try {
			isPerformReasonOfTransferCheck = Boolean.parseBoolean(System.getProperty("perform.reasonOfTransfer.check"));
			return isPerformReasonOfTransferCheck;
		} catch (Exception e) {
			LOG.error("Error while reading property 'perform.reasonOfTransfer.check' ", e);
			return isPerformReasonOfTransferCheck;
		}
	}

	/**
	 * Creates the default funds out whitelist response.
	 *
	 * @return the white list check response
	 */
	@SuppressWarnings("unused")
	private WhiteListCheckResponse createDefaultFundsOutWhitelistResponse() {
		WhiteListCheckResponse wResponse = new WhiteListCheckResponse();
		wResponse.setAmoutRange(ResponseStatus.FAIL.getStatus());
		wResponse.setCurrency(ResponseStatus.FAIL.getStatus());
		wResponse.setReasonOfTransfer(ResponseStatus.FAIL.getStatus());
		wResponse.setThirdParty(ResponseStatus.NOT_REQUIRED.getStatus());
		wResponse.setStatus(ResponseStatus.FAIL.getStatus());
		return wResponse;
	}

	/**
	 * Sets the value of reason of transfer.
	 *
	 * @param fRequest     the f request
	 * @param accWhiteList the acc white list
	 * @param wResponse    the w response
	 */
	@SuppressWarnings("unused")
	private void setValueOfReasonOfTransfer(FundsOutRequest fRequest, AccountWhiteList accWhiteList,
			WhiteListCheckResponse wResponse) {
		for (String reason : accWhiteList.getApprovedReasonOfTransList()) {
			if (fRequest.getTrade().getPurposeOfTrade().equalsIgnoreCase(reason)) {
				wResponse.setReasonOfTransfer(ResponseStatus.PASS.getStatus());
				break;
			}
		}
	}

	/**
	 * Sets the amount range status funds out.
	 *
	 * @param fRequest    the f request
	 * @param wResponse   the w response
	 * @param buyCurrency the buy currency
	 * @param currency    the currency
	 * @param amtLimit    the amt limit
	 */
	@SuppressWarnings("unused")
	private void setAmountRangeStatusFundsOut(FundsOutRequest fRequest, WhiteListCheckResponse wResponse,
			String buyCurrency, ApprovedCurrencyAmountRangePair currency, Double amtLimit) {
		if (fRequest.getBeneficiary().getAmount() <= amtLimit) {
			wResponse.setAmoutRange(ResponseStatus.PASS.getStatus());
		} else {
			Double safeAmountLimit = getSafeCurrencyAmountLimit(buyCurrency, DocumentType.FUNDS_OUT_ADD);
			if (fRequest.getBeneficiary().getAmount() <= safeAmountLimit) {
				wResponse.setAmoutRange(ResponseStatus.PASS.getStatus());
				currency.setTxnAmountUpperLimit(fRequest.getBeneficiary().getAmount());
			} else if (isSafeCurrency(buyCurrency, DocumentType.FUNDS_OUT_ADD)) {
				wResponse.setMaxAmount(safeAmountLimit);
			} else {
				wResponse.setMaxAmount(amtLimit);
			}
		}
	}

	/**
	 * Process funds out currency amout checks.
	 *
	 * @param fRequest     the f request
	 * @param accWhiteList the acc white list
	 * @param wResponse    the w response
	 * @param buyCurrency  the buy currency
	 */
	@SuppressWarnings("unused")
	private void processFundsOutCurrencyAmoutChecks(FundsOutRequest fRequest, AccountWhiteList accWhiteList,
			WhiteListCheckResponse wResponse, String buyCurrency) {
		if (!wResponse.getCurrency().equals(ResponseStatus.PASS.getStatus())) {

			if (isSafeCurrency(buyCurrency, DocumentType.FUNDS_OUT_ADD)) {
				wResponse.setCurrency(ResponseStatus.PASS.getStatus());
			}
			Double safeAmountLimit = getSafeCurrencyAmountLimit(buyCurrency, DocumentType.FUNDS_OUT_ADD);
			if (fRequest.getBeneficiary().getAmount() <= safeAmountLimit) {
				wResponse.setAmoutRange(ResponseStatus.PASS.getStatus());
			} else {
				wResponse.setMaxAmount(safeAmountLimit);
			}
			if (wResponse.getCurrency().equals(ResponseStatus.PASS.getStatus())
					&& wResponse.getAmoutRange().equals(ResponseStatus.PASS.getStatus())) {
				ApprovedCurrencyAmountRangePair request = new ApprovedCurrencyAmountRangePair();
				request.setCode(buyCurrency);
				request.setTxnAmountUpperLimit(fRequest.getBeneficiary().getAmount());
				accWhiteList.getApprovedBuyCurrencyAmountRangeList().add(request);
			}
		}
	}

	/**
	 * Gets the numberof transaction count.
	 *
	 * @param fRequest  the f request
	 * @param result    the result
	 * @param rules     the rules
	 * @param vResponse the v response
	 * @return the numberof transaction count
	 */
	private void getNumberofTransactionCount(FundsOutRequest fRequest, SearchResult result,
			CustomCheckVelocityRules rules, VelocityCheckResponse vResponse) {
		if (fRequest.getTrade().getBuyCurrency().equalsIgnoreCase(Constants.VALUE_BUYING_CURRENCY)
				&& fRequest.getBeneficiary().getCountry().equalsIgnoreCase(Constants.VALUE_BENEFICIARY_COUNTRY)
				&& fRequest.getBeneficiary().getAmount().equals(Double.parseDouble(Constants.VALUE_BUYING_AMOUNT))) {
			vResponse.setNoOffundsoutTxn(ResponseStatus.PASS.getStatus());
		} else if (null != rules && result.getIntraDayCount() + 1 > rules.getCountThreshold()) {
			Integer currentCount = result.getIntraDayCount() + 1;
			vResponse.setNoOffundsoutTxn(ResponseStatus.FAIL.getStatus());
			vResponse.setNoOfFundsOutTxnDetails(
					"(Current Count : " + currentCount + ", Max allowed count:" + rules.getCountThreshold() + ")");
		} else {
			vResponse.setNoOffundsoutTxn(ResponseStatus.PASS.getStatus());
		}
	}

	/**
	 * Perform fraud predict check.
	 *
	 * @param response  the response
	 * @param updatedOn the updated on
	 * @param score     the score
	 * @throws CustomChecksException the custom checks exception
	 */
	// ADD for AT-3161
	private void performFraudPredictCheck(CustomCheckResponse response, LinkedHashMap<String, String> map)
			throws CustomChecksException {

		SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
		Timestamp date = new Timestamp(System.currentTimeMillis());
		String currentDate = myFormat.format(date);
		response.setFraudPredictStatus(ResponseStatus.PASS.getStatus());
		int days = 5;
		Double riskThreshold = null;

		CustomCheckFraudPredictRules rules = CustomCheckConcreteDataBuilder.getInstance()
				.getProviderInitConfigProperty("FRAUD_PREDICT_SIGNUP");
		riskThreshold = rules.getRegistrationThreesholdScore();
		boolean riskThresholdFlag = false;

		try {
			// changes for AT-3243
			for (Entry<String, String> e : map.entrySet()) {
				String updatedOn = e.getKey();
				String score = e.getValue();

				Date update = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(updatedOn);
				String updateDate = myFormat.format(update);
				java.util.Date dateOld = myFormat.parse(updateDate);
				java.util.Date dateNew = myFormat.parse(currentDate);
				int daysBetween = (int) ((dateNew.getTime() - dateOld.getTime()) / (1000 * 60 * 60 * 24));

				if (score == null) {
					response.setFraudPredictStatus(ResponseStatus.PASS.getStatus());
				} else if (daysBetween <= days && Double.parseDouble(score) == 1.0) {
					response.setFraudPredictStatus(ResponseStatus.PASS.getStatus());
					riskThresholdFlag = true;
				} else if (daysBetween <= days && Double.parseDouble(score) == 0.0) {
					response.setFraudPredictStatus(ResponseStatus.FAIL.getStatus());
					riskThresholdFlag = true;
				}
				if (riskThresholdFlag)
					break;
			}

		} catch (Exception e) {
			LOG.error("performFraudPredictCheck", e);
		}

		// Add flag ON/OFF condition in database fraud predict signup
		if (rules.getFraudPredictCustomCheckEnabled() != null
				&& Boolean.FALSE.equals(rules.getFraudPredictCustomCheckEnabled())) {
			response.setFraudPredictStatus(ResponseStatus.PASS.getStatus());
		}
	}

	/**
	 * AT-3349 Perform eu poi check.
	 *
	 * @param fRequest the f request
	 * @param document the document
	 * @return the eu poi check response
	 * @throws CustomChecksException the custom checks exception
	 */
	private EuPoiCheckResponse performEuPoiCheck(CustomChecksRequest document) {
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
		euPoiCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());

		try {
			if (document.getPoiExistsFlag().equals(4) || document.getPoiExistsFlag().equals(5)
					|| document.getPoiExistsFlag().equals(6)) {
				if (document.getZeroFundsClearAfterLEDate()) {
					euPoiCheckResponse.setStatus(ResponseStatus.FAIL.getStatus());
				} else {
					euPoiCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
				}
			} else {
				euPoiCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
			}

		} catch (Exception e) {
			LOG.error("Error in performEuPoiCheck() in PaymentOutCustomCheckSearchImpl ", e);
		}

		return euPoiCheckResponse;
	}

}
