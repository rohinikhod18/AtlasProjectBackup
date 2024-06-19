package com.currenciesdirect.gtg.compliance.customchecks.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.activity.PaymentMethodEnum;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CDINCFirstCreditCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.EuPoiCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.FirstCreditCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.VelocityCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.WhiteListCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.ApprovedCurrencyAmountRangePair;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.HighRiskCountry;
import com.currenciesdirect.gtg.compliance.commons.enums.LegalEntityEnum;
import com.currenciesdirect.gtg.compliance.commons.util.Constants;
import com.currenciesdirect.gtg.compliance.customchecks.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customchecks.domain.fundsin.FundsInCreateRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.esport.ESSearchProcessor;
import com.currenciesdirect.gtg.compliance.customchecks.esport.SearchResult;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;
import com.currenciesdirect.gtg.compliance.customchecks.util.StringUtils;

/**
 * The Class PaymentInCustomCheckSearchImpl.
 */
public class PaymentInCustomCheckSearchImpl extends AbstractCustomCheckService implements IPaymentInCustomCheckService {

	/** The i payment in search service. */
	@SuppressWarnings("squid:S3077")
	private static volatile IPaymentInCustomCheckService iPaymentInSearchService;

	/** The Constant FUNDSIN_ALLOWED_MARGIN. */
	private static final String FUNDSIN_ALLOWED_MARGIN = "fundsin.allowed.margin";

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(PaymentInCustomCheckSearchImpl.class);
	
	private static final String PFX = "PFX";

	/**
	 * Gets the single instance of PaymentInCustomCheckSearchImpl.
	 *
	 * @return single instance of PaymentInCustomCheckSearchImpl
	 */
	public static IPaymentInCustomCheckService getInstance() {
		if (iPaymentInSearchService == null) {
			synchronized (PaymentInCustomCheckSearchImpl.class) {
				if (iPaymentInSearchService == null) {
					iPaymentInSearchService = new PaymentInCustomCheckSearchImpl();
				}
			}
		}
		return iPaymentInSearchService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * IPaymentInCustomCheckService#performFundsInVelocityAndWhilteListChecks(
	 * com.currenciesdirect.gtg.compliance.customchecks.domain.request.
	 * CustomChecksRequest)
	 */
	@Override
	public CustomCheckResponse performFundsInVelocityAndWhilteListChecks(CustomChecksRequest document)
			throws CustomChecksException {
		CustomCheckResponse customChecksResponse = new CustomCheckResponse();
		//VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
		WhiteListCheckResponse whiteListCheckResponse =new WhiteListCheckResponse();
		FirstCreditCheckResponse firstCreditCheckResponse = new FirstCreditCheckResponse();
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
		CDINCFirstCreditCheckResponse cdincFirstCreditCheckResponse = new CDINCFirstCreditCheckResponse();
		/*
		document.getESDocument().setAccId(document.getAccId());
		FundsInCreateRequest fRequest = (FundsInCreateRequest) document.getESDocument();

		ESSearchProcessor searchProcessor = new ESSearchProcessor();
		SearchResult result = searchProcessor.searchWhiteListDocument(fRequest.getOrgCode(), document.getAccId());
		AccountWhiteList existingAccWhiteList = result.getAccWhiteList();
		AccountWhiteList updatedWhiteList = updateAccountWhiteListData(document, existingAccWhiteList);

		String status = ResponseStatus.NOT_REQUIRED.getStatus();

		VelocityCheckResponse velocityCheckResponse = new VelocityCheckResponse();
		velocityCheckResponse.setStatus(status);
		velocityCheckResponse.setBeneCheck(status);
		velocityCheckResponse.setNoOffundsoutTxn(status);
		velocityCheckResponse.setPermittedAmoutcheck(status);
		
		WhiteListCheckResponse whiteListCheckResponse = performFundsInWhiteListCheck(fRequest, updatedWhiteList);
		whiteListCheckResponse.setStatus(status);
		whiteListCheckResponse.setAmoutRange(status);
		whiteListCheckResponse.setReasonOfTransfer(status);
		
		FirstCreditCheckResponse firstCreditCheckResponse = new FirstCreditCheckResponse();
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
		
		if(LegalEntityEnum.CDLEU.getLECode().equals(fRequest.getFundsInTrade().getCustLegalEntity())
						|| LegalEntityEnum.FCGEU.getLECode().equals(fRequest.getFundsInTrade().getCustLegalEntity())
						|| LegalEntityEnum.TOREU.getLECode().equals(fRequest.getFundsInTrade().getCustLegalEntity())) {
			 firstCreditCheckResponse = performFundsInFirstCreditCustomCheck(fRequest,document);//AT-3346
			 euPoiCheckResponse = performFundsInEuPoiCheck(document);//AT-3349
		}
		else {
			euPoiCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());
			firstCreditCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());
		}
		
		CDINCFirstCreditCheckResponse cdincFirstCreditCheckResponse = new CDINCFirstCreditCheckResponse();
		
		if (LegalEntityEnum.CDINC.getLECode().equals(fRequest.getFundsInTrade().getCustLegalEntity())) {
			cdincFirstCreditCheckResponse = performCDINCFundsInFirstCreditCustomCheck(fRequest, document); //Add for AT-3738
		} else {
			cdincFirstCreditCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());
		}
		
		setAllCheckStatus(customChecksResponse, whiteListCheckResponse, firstCreditCheckResponse, euPoiCheckResponse, cdincFirstCreditCheckResponse);
		
		// if added for AT-1554 - Sneha z.
		if(null!=updatedWhiteList){
			for (HighRiskCountry hrc : updatedWhiteList.getApprovedHighRiskCountryListForFundsIn()) {
				if (hrc.getCountryCode().equalsIgnoreCase(fRequest.getFundsInTrade().getCountryOfFund())) {
					customChecksResponse.setCountryWhitelistedForFundsIn(true);
				}
			}
		}
*/
		whiteListCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
		firstCreditCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
		euPoiCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
		cdincFirstCreditCheckResponse.setStatus(cdincFirstCreditCheckResponse.getStatus());
		customChecksResponse.setOverallStatus(ResponseStatus.PASS.getStatus());
		
		return customChecksResponse;
	}

	private void setAllCheckStatus(CustomCheckResponse customChecksResponse,
			WhiteListCheckResponse whiteListCheckResponse, FirstCreditCheckResponse firstCreditCheckResponse,
			EuPoiCheckResponse euPoiCheckResponse, CDINCFirstCreditCheckResponse cdincFirstCreditCheckResponse) {
		if (ResponseStatus.PASS.getStatus().equals(whiteListCheckResponse.getCurrency())
				&& (ResponseStatus.PASS.getStatus().equals(whiteListCheckResponse.getThirdParty()) 
						|| ResponseStatus.NOT_REQUIRED.getStatus().equals(whiteListCheckResponse.getThirdParty()))
				&& (ResponseStatus.PASS.getStatus().equals(firstCreditCheckResponse.getStatus())
						|| ResponseStatus.NOT_REQUIRED.getStatus().equals(firstCreditCheckResponse.getStatus()))
				&& (ResponseStatus.PASS.getStatus().equals(euPoiCheckResponse.getStatus())
						|| ResponseStatus.NOT_REQUIRED.getStatus().equals(euPoiCheckResponse.getStatus()))
				&& (ResponseStatus.PASS.getStatus().equals(cdincFirstCreditCheckResponse.getStatus())
						|| ResponseStatus.NOT_REQUIRED.getStatus().equals(cdincFirstCreditCheckResponse.getStatus()))) {
			whiteListCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
			firstCreditCheckResponse.setStatus(firstCreditCheckResponse.getStatus());
			euPoiCheckResponse.setStatus(euPoiCheckResponse.getStatus());
			cdincFirstCreditCheckResponse.setStatus(cdincFirstCreditCheckResponse.getStatus());
			customChecksResponse.setOverallStatus(ResponseStatus.PASS.getStatus());
		} else {
			whiteListCheckResponse.setStatus(ResponseStatus.FAIL.getStatus());
			firstCreditCheckResponse.setStatus(firstCreditCheckResponse.getStatus());
			euPoiCheckResponse.setStatus(euPoiCheckResponse.getStatus());
			cdincFirstCreditCheckResponse.setStatus(cdincFirstCreditCheckResponse.getStatus());
			customChecksResponse.setOverallStatus(ResponseStatus.FAIL.getStatus());
		}
	}

	/**
	 * Perform funds in white list check.
	 *
	 * @param fRequest
	 *            the f request
	 * @param accWhiteList
	 *            the acc white list
	 * @return the white list check response
	 * @throws CustomChecksException
	 *             the custom checks exception
	 */
	private WhiteListCheckResponse performFundsInWhiteListCheck(FundsInCreateRequest fRequest,
			AccountWhiteList accWhiteList) throws CustomChecksException {
		WhiteListCheckResponse wResponse = createDefaultFundsInWhitelistResponse();
		try {
			if (null == accWhiteList) {
				return wResponse;
			}

			String txnCurrency = fRequest.getFundsInTrade().getTransactionCurrency();
			for (ApprovedCurrencyAmountRangePair currency : accWhiteList.getApprovedSellCurrencyAmountRangeList()) {
				if (txnCurrency.equalsIgnoreCase(currency.getCode())) {
					Double amtLimit = getMarginAmount(FUNDSIN_ALLOWED_MARGIN, currency.getTxnAmountUpperLimit());
					wResponse.setCurrency(ResponseStatus.PASS.getStatus());
					setAmountRangeStatusFundsIn(fRequest, wResponse, txnCurrency, currency, amtLimit);

					break;
				}
			}
			performFundsInThirdPartyCheck(fRequest, accWhiteList, wResponse);
			processFundsInCurrencyAmountChecks(fRequest, accWhiteList, wResponse, txnCurrency);
			if (wResponse.getAmoutRange().equals(ResponseStatus.PASS.getStatus())
					&& wResponse.getCurrency().equals(ResponseStatus.PASS.getStatus())
					&& (wResponse.getThirdParty().equals(ResponseStatus.PASS.getStatus())
							|| wResponse.getThirdParty().equals(ResponseStatus.NOT_REQUIRED.getStatus()))) {
				wResponse.setStatus(ResponseStatus.PASS.getStatus());
			}
		} catch (Exception e) {
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_SEARCH_REQUEST, e);
		}

		return wResponse;
	}

	/**
	 * Perform funds in third party check.
	 *
	 * @param fRequest
	 *            the f request
	 * @param accWhiteList
	 *            the acc white list
	 * @param wResponse
	 *            the w response
	 */
	/*
	 If ThirdParty flag is true
	 	Then Check
		1.CFX Etailer and debtorName is blank/NULL/Empty and debtorAccountNumber is in WhiteList Data. ELSE
		2.CFX/PFX and debtorAccountNumber is in WhiteList Data
		SO ThirdParty CHECK PASS
		
	*/	
	private void performFundsInThirdPartyCheck(FundsInCreateRequest fRequest, AccountWhiteList accWhiteList,
			WhiteListCheckResponse wResponse) {
		if (Boolean.TRUE.equals(fRequest.getFundsInTrade().getThirdPartyPayment())) {
			//AT-2451 - Reinstate Third Party Account whitelisting process
			if(!StringUtils.isNullOrEmpty(fRequest.getFundsInTrade().getDebtorAccountNumber())
					&& isDebtorAccountNumberWhitelisted(fRequest, accWhiteList)) {
				wResponse.setThirdParty(ResponseStatus.PASS.getStatus());
			}
			else {
				if(fRequest.getFundsInTrade().getCustType().equals(Constants.CFXETAILER) 
						&& StringUtils.isNullOrEmpty(fRequest.getFundsInTrade().getDebtorName())) {
					wResponse.setThirdParty(ResponseStatus.PASS.getStatus());
				} else {
					wResponse.setThirdParty(ResponseStatus.FAIL.getStatus());
				}
			}
		} else if (Boolean.FALSE.equals(fRequest.getFundsInTrade().getThirdPartyPayment())) {
			wResponse.setThirdParty(ResponseStatus.NOT_REQUIRED.getStatus());
		}
	}

	/**
	 * Creates the default funds in whitelist response.
	 *
	 * @return the white list check response
	 */
	private WhiteListCheckResponse createDefaultFundsInWhitelistResponse() {
		WhiteListCheckResponse fundsInWResponse = new WhiteListCheckResponse();
		fundsInWResponse.setAmoutRange(ResponseStatus.FAIL.getStatus());
		fundsInWResponse.setCurrency(ResponseStatus.FAIL.getStatus());
		fundsInWResponse.setReasonOfTransfer(ResponseStatus.NOT_REQUIRED.getStatus());
		fundsInWResponse.setThirdParty(ResponseStatus.NOT_REQUIRED.getStatus());
		fundsInWResponse.setStatus(ResponseStatus.FAIL.getStatus());
		return fundsInWResponse;
	}

	/**
	 * Sets the amount range status funds in.
	 *
	 * @param fRequest
	 *            the f request
	 * @param wResponse
	 *            the w response
	 * @param txnCurrency
	 *            the txn currency
	 * @param currency
	 *            the currency
	 * @param amtLimit
	 *            the amt limit
	 */
	private void setAmountRangeStatusFundsIn(FundsInCreateRequest fRequest, WhiteListCheckResponse wResponse,
			String txnCurrency, ApprovedCurrencyAmountRangePair currency, Double amtLimit) {
		if (fRequest.getFundsInTrade().getSellingAmount() <= amtLimit) {
			wResponse.setAmoutRange(ResponseStatus.PASS.getStatus());
		} else {
			Double safeAmountLimit = getSafeCurrencyAmountLimit(txnCurrency, DocumentType.FUNDS_IN_ADD);
			if (fRequest.getFundsInTrade().getSellingAmount() <= safeAmountLimit) {
				wResponse.setAmoutRange(ResponseStatus.PASS.getStatus());
				currency.setTxnAmountUpperLimit(fRequest.getFundsInTrade().getSellingAmount());
			} else if (isSafeCurrency(txnCurrency, DocumentType.FUNDS_IN_ADD)) {
				wResponse.setMaxAmount(safeAmountLimit);
			} else {
				wResponse.setMaxAmount(amtLimit);
			}
		}
	}

	/**
	 * Process funds in currency amount checks.
	 *
	 * @param fRequest
	 *            the f request
	 * @param accWhiteList
	 *            the acc white list
	 * @param wResponse
	 *            the w response
	 * @param txnCurrency
	 *            the txn currency
	 */
	private void processFundsInCurrencyAmountChecks(FundsInCreateRequest fRequest, AccountWhiteList accWhiteList,
			WhiteListCheckResponse wResponse, String txnCurrency) {
		if (!wResponse.getCurrency().equals(ResponseStatus.PASS.getStatus())) {

			if (isSafeCurrency(txnCurrency, DocumentType.FUNDS_IN_ADD)) {
				wResponse.setCurrency(ResponseStatus.PASS.getStatus());
			}
			Double safeAmountLimit = getSafeCurrencyAmountLimit(txnCurrency, DocumentType.FUNDS_IN_ADD);
			if (fRequest.getFundsInTrade().getSellingAmount() <= safeAmountLimit) {
				wResponse.setAmoutRange(ResponseStatus.PASS.getStatus());
			} else {
				wResponse.setMaxAmount(safeAmountLimit);
			}
			if (wResponse.getCurrency().equals(ResponseStatus.PASS.getStatus())
					&& wResponse.getAmoutRange().equals(ResponseStatus.PASS.getStatus())) {
				ApprovedCurrencyAmountRangePair request = new ApprovedCurrencyAmountRangePair();
				request.setCode(txnCurrency);
				request.setTxnAmountUpperLimit(fRequest.getFundsInTrade().getSellingAmount());
				accWhiteList.getApprovedSellCurrencyAmountRangeList().add(request);
			}
		}
	}
	
	/**
	 * @param fRequest
	 * @param accWhiteList
	 * @param wResponse
	 */
	private boolean isDebtorAccountNumberWhitelisted(FundsInCreateRequest fRequest, AccountWhiteList accWhiteList) {
		boolean status = false;
		if (accWhiteList.getApprovedThirdpartyAccountList().isEmpty())
			return status;
		for (String thridParty : accWhiteList.getApprovedThirdpartyAccountList()) {
			if (fRequest.getFundsInTrade().getDebtorAccountNumber().equalsIgnoreCase(thridParty)) {
				status = true;
				break;
			} else {
				status = false;
			}
		}
		return status;
	}

	/**
	 * AT-3346
	 * Perform funds in first credit custom check.
	 *
	 * @param fRequest the f request
	 * @return the first credit check response
	 */
	private FirstCreditCheckResponse performFundsInFirstCreditCustomCheck(FundsInCreateRequest fRequest,CustomChecksRequest document) {
		FirstCreditCheckResponse firstCreditCheckResponse = new FirstCreditCheckResponse();
		try {
			if(PFX.equals(fRequest.getFundsInTrade().getCustType()) && document.getZeroFundsInClear()) {
				//AT-5138
				if (!PaymentMethodEnum.FOA.getDatabasePaymentMethod()
						.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& !PaymentMethodEnum.SWITCHDEBIT.getDatabasePaymentMethod()
								.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& !PaymentMethodEnum.DIRECTDEBIT.getDatabasePaymentMethod()
								.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& !PaymentMethodEnum.WALLET.getDatabasePaymentMethod()
								.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& !PaymentMethodEnum.RETURNOFFUNDS.getDatabasePaymentMethod()
								.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& !PaymentMethodEnum.CHEQUEDRAFT.getDatabasePaymentMethod()
								.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& !PaymentMethodEnum.CHEQUE.getDatabasePaymentMethod()
								.equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& Boolean.FALSE.equals(fRequest.getFundsInTrade().getThirdPartyPayment())
						&& document.getEddCountryFlag().equals(1)) {
			    		firstCreditCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
			    }
				else
					firstCreditCheckResponse.setStatus(ResponseStatus.FAIL.getStatus());
			}
			else
				firstCreditCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());
		}catch(Exception e) {
			LOG.error("Error in performFundsInFirstCreditCustomCheck() in PaymentInCustomCHeckSearchImpl ",e);
		}
		return firstCreditCheckResponse;
    }
	
	/**
	 * AT-3349
	 * Perform funds in eu poi check.
	 *
	 * @param fRequest the f request
	 * @param document the document
	 */
	private EuPoiCheckResponse performFundsInEuPoiCheck(CustomChecksRequest document) {
		EuPoiCheckResponse euPoiCheckResponse = new EuPoiCheckResponse();
		euPoiCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());
		try {
				if((document.getPoiExistsFlag().equals(4) || document.getPoiExistsFlag().equals(5)
						|| document.getPoiExistsFlag().equals(6))
						&& document.getZeroFundsClearAfterLEDate()) {
					euPoiCheckResponse.setStatus(ResponseStatus.FAIL.getStatus());
				}
				else
					euPoiCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
	
		}catch(Exception e) {
			LOG.error("Error in performFundsInEuPoiCheck() in PaymentInCustomCHeckSearchImpl ",e);
		}
		return euPoiCheckResponse;
	}
	
	
	/**
	 * Perform CDINC funds in first credit custom check.
	 *
	 * @param fRequest the f request
	 * @param document the document
	 * @return the CDINC first credit check response
	 */
	private CDINCFirstCreditCheckResponse performCDINCFundsInFirstCreditCustomCheck(FundsInCreateRequest fRequest,CustomChecksRequest document) {
		CDINCFirstCreditCheckResponse cdincFirstCreditCheckResponse = new CDINCFirstCreditCheckResponse();
		try {
			if(PFX.equals(fRequest.getFundsInTrade().getCustType())) {
				if(PaymentMethodEnum.SWITCHDEBIT.getDatabasePaymentMethod().equalsIgnoreCase(fRequest.getFundsInTrade().getPaymentMethod())
						&& document.isZeroFundsInClearForCDINC()) {
					cdincFirstCreditCheckResponse.setStatus(ResponseStatus.FAIL.getStatus());
			    }
				else
					cdincFirstCreditCheckResponse.setStatus(ResponseStatus.PASS.getStatus());
			}
			else
				cdincFirstCreditCheckResponse.setStatus(ResponseStatus.NOT_REQUIRED.getStatus());
		}catch(Exception e) {
			LOG.error("Error in performCDINCFundsInFirstCreditCustomCheck() in PaymentInCustomCheckSearchImpl ",e);
		}
		return cdincFirstCreditCheckResponse;
    }
	
}