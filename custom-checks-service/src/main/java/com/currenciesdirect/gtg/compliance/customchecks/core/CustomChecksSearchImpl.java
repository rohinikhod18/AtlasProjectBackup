package com.currenciesdirect.gtg.compliance.customchecks.core;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.titan.response.PayeeESResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.ApprovedCurrencyAmountRangePair;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.HighRiskCountry;
import com.currenciesdirect.gtg.compliance.commons.util.JsonConverterUtil;
import com.currenciesdirect.gtg.compliance.commons.validator.FieldValidator;
import com.currenciesdirect.gtg.compliance.customchecks.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.SearchWhiteListRequest;
import com.currenciesdirect.gtg.compliance.customchecks.domain.response.SearchWhiteListResponse;
import com.currenciesdirect.gtg.compliance.customchecks.esport.Constants;
import com.currenciesdirect.gtg.compliance.customchecks.esport.ESIndexingProcessor;
import com.currenciesdirect.gtg.compliance.customchecks.esport.ESSearchProcessor;
import com.currenciesdirect.gtg.compliance.customchecks.esport.SearchResult;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksErrors;
import com.currenciesdirect.gtg.compliance.customchecks.exception.CustomChecksException;

/**
 * The Class CustomerDataSearchImpl.
 * 
 * @author Rajesh
 */
public class CustomChecksSearchImpl extends AbstractCustomCheckService implements ICustomChecksService {

	/** The Constant iPaymentInService. */
	private static IPaymentInCustomCheckService iPaymentInService = PaymentInCustomCheckSearchImpl.getInstance();

	/** The Constant iPaymentoutService. */
	private static IPaymentOutCustomCheckService iPaymentoutService = PaymentOutCustomCheckSearchImpl.getInstance();

	/** The Constant FUNDSIN_ALLOWED_MARGIN. */
	private static final String FUNDSIN_ALLOWED_MARGIN = "fundsin.allowed.margin";

	/** The Constant FUNDSOUT_ALLOWED_MARGIN. */
	private static final String FUNDSOUT_ALLOWED_MARGIN = "fundsout.allowed.margin";

	/** The Constant LOG. */
	private static final Logger LOG = LoggerFactory.getLogger(CustomChecksSearchImpl.class);

	/** The i customer search service. */
	@SuppressWarnings("squid:S3077")
	private static volatile ICustomChecksService iCustomerSearchService;

	/**
	 * Instantiates a new custom checks search impl.
	 */
	private CustomChecksSearchImpl() {
	}

	/**
	 * Gets the single instance of CustomChecksSearchImpl.
	 *
	 * @return single instance of CustomChecksSearchImpl
	 */
	public static ICustomChecksService getInstance() {
		if (iCustomerSearchService == null) {
			synchronized (CustomChecksSearchImpl.class) {
				if (iCustomerSearchService == null) {
					iCustomerSearchService = new CustomChecksSearchImpl();
				}
			}
		}
		return iCustomerSearchService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * ICustomChecksService#performCheck(com.currenciesdirect.gtg.compliance.
	 * customchecks.domain.request.CustomChecksRequest)
	 */
	@Override
	public CustomCheckResponse performCheck(CustomChecksRequest document) throws CustomChecksException {
		LOG.debug("CustomerDataSearchImpl.searchDocument() start");
		CustomCheckResponse response = null;
		try {
			FieldValidator fv = document.validateRequest();
			if (fv != null && !fv.isFailed()) {
				LOG.error("Invalid Request:{}", fv.getErrors());
				throw new CustomChecksException(CustomChecksErrors.ERROR_INVALID_REQUEST);
			}

			String type = document.getESDocument().getType();
			if (DocumentType.FUNDS_IN_ADD.name().equalsIgnoreCase(type)) {
				response = iPaymentInService.performFundsInVelocityAndWhilteListChecks(document);
			}

			if (DocumentType.FUNDS_OUT_ADD.name().equalsIgnoreCase(type)
					|| DocumentType.FUNDS_OUT_UPDATE.name().equalsIgnoreCase(type)
					|| DocumentType.FUNDS_OUT_DELETE.name().equalsIgnoreCase(type)) {
				response = iPaymentoutService.performVelocityAndWhilteListChecks(document);
			}
		} catch (CustomChecksException customerDataScanException) {
			LOG.error("Error:", customerDataScanException);
		} catch (Exception exception) {
			LOG.error("Error:", exception);
			throw new CustomChecksException(CustomChecksErrors.FAILED);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * ICustomChecksService#repeatCheck(com.currenciesdirect.gtg.compliance.
	 * customchecks.domain.request.CustomChecksRequest)
	 */
	@Override
	public CustomCheckResponse repeatCheck(CustomChecksRequest document) throws CustomChecksException {
		LOG.debug("CustomerDataSearchImpl.searchDocument() start");
		CustomCheckResponse response = null;
		try {
			String type = document.getESDocument().getType();
			if (DocumentType.FUNDS_IN_ADD.name().equalsIgnoreCase(type)) {
				response = iPaymentInService.performFundsInVelocityAndWhilteListChecks(document);
			} else {
				response = iPaymentoutService.performVelocityAndWhilteListChecks(document);
			}
		} catch (CustomChecksException customerDataScanException) {
			LOG.error("Error Occured: ", customerDataScanException);
		} catch (Exception exception) {
			LOG.error("Exception: ", exception);
			throw new CustomChecksException(CustomChecksErrors.FAILED);
		}
		return response;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * ICustomChecksService#updateWhiteListData(com.currenciesdirect.gtg.
	 * compliance.customchecks.domain.whitelist.AccountWhiteList)
	 */
	@Override
	public CustomCheckResponse updateWhiteListData(AccountWhiteList request) throws CustomChecksException {
		CustomCheckResponse response = new CustomCheckResponse();
		try {
			SearchResult result = new ESSearchProcessor().searchWhiteListDocument(request.getOrgCode(),
					request.getAccountId());

			AccountWhiteList existingWhiteList = createWhiteListObject(request, result);
			removeNullAndEmptyElementsFromRequest(request);

			updateWhiteListData(request, existingWhiteList);
			HighRiskCountry hrc = null;
			if (!request.getApprovedHighRiskCountryList().isEmpty()) {
				hrc = request.getApprovedHighRiskCountryList().get(0);
			}

			if (null != existingWhiteList && existingWhiteList.getApprovedHighRiskCountryList().contains(hrc)) {
				response.setCountryWhitelisted(true);
			}
			HighRiskCountry hrcForFundsIn = null;
			if (!request.getApprovedHighRiskCountryListForFundsIn().isEmpty()) {
				hrcForFundsIn = request.getApprovedHighRiskCountryListForFundsIn().get(0);
			}

			if (null != existingWhiteList && existingWhiteList.getApprovedHighRiskCountryListForFundsIn().contains(hrcForFundsIn)) {
				response.setCountryWhitelistedForFundsIn(true);
			}

			new ESIndexingProcessor().upsertDocument(Constants.INDEX_WHITELIST, Constants.TYPE_ACCOUNT,
					request.getAccountId().toString(), JsonConverterUtil.convertToJsonWithoutNull(existingWhiteList));

			response.setOrgCode(request.getOrgCode());
			response.setAccId(request.getAccountId());
			response.setOverallStatus(ResponseStatus.PASS.getStatus());
		} catch (Exception exception) {
			LOG.error("Exception: ", exception);
			throw new CustomChecksException(CustomChecksErrors.ERROR_WHILE_UPDATE_REQUEST);
		}
		return response;
	}

	/**
	 * Update white list data.
	 *
	 * @param request
	 *            the request
	 * @param existingWhiteList
	 *            the existing white list
	 */
	private void updateWhiteListData(AccountWhiteList request, AccountWhiteList existingWhiteList) {
		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedBuyCurrencyAmountRangeList())) && null != existingWhiteList) {
			existingWhiteList.getApprovedBuyCurrencyAmountRangeList()
					.addAll(request.getApprovedBuyCurrencyAmountRangeList());
		}

		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedReasonOfTransList())) && null != existingWhiteList) {
			existingWhiteList.getApprovedReasonOfTransList().addAll(request.getApprovedReasonOfTransList());
		}

		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedSellCurrencyAmountRangeList())) && null != existingWhiteList) {
			existingWhiteList.getApprovedSellCurrencyAmountRangeList()
					.addAll(request.getApprovedSellCurrencyAmountRangeList());
		}

		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedThirdpartyAccountList())) && null != existingWhiteList) {
			existingWhiteList.getApprovedThirdpartyAccountList().addAll(request.getApprovedThirdpartyAccountList());
		}

		updateWhiteListDataPart2(request, existingWhiteList);
	}

	/**
	 * Update white list data part 2.
	 *
	 * @param request
	 *            the request
	 * @param existingWhiteList
	 *            the existing white list
	 */
	private void updateWhiteListDataPart2(AccountWhiteList request, AccountWhiteList existingWhiteList) {
		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedOPIAccountNumber())) && null != existingWhiteList) {
			existingWhiteList.getApprovedOPIAccountNumber().addAll(request.getApprovedOPIAccountNumber());
		}

		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedHighRiskCountryList())) && null != existingWhiteList) {
			existingWhiteList.getApprovedHighRiskCountryList().addAll(request.getApprovedHighRiskCountryList());
			existingWhiteList.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());

		}

		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getDocumentationRequiredWatchlistSellCurrency())) && null != existingWhiteList) {
			existingWhiteList.getDocumentationRequiredWatchlistSellCurrency()
					.addAll(request.getDocumentationRequiredWatchlistSellCurrency());
		}

		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getUsClientListBBeneAccNumber())) && null != existingWhiteList) {
			existingWhiteList.getUsClientListBBeneAccNumber().addAll(request.getUsClientListBBeneAccNumber());
		}
		if (Boolean.FALSE.equals(isListNullOrEmpty(request.getApprovedHighRiskCountryListForFundsIn())) && null != existingWhiteList) {
			existingWhiteList.getApprovedHighRiskCountryListForFundsIn().addAll(request.getApprovedHighRiskCountryListForFundsIn());
			existingWhiteList.setUpdatedOn(new Timestamp(System.currentTimeMillis()).toString());

		}

		if (null != existingWhiteList)
			removeAccountWhiteListDuplicates(existingWhiteList);
	}

	/**
	 * Creates the white list object.
	 *
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 * @return the account white list
	 */
	private AccountWhiteList createWhiteListObject(AccountWhiteList request, SearchResult result) {
		AccountWhiteList existingWhiteList;
		if (null != result && null != result.getAccWhiteList()) {
			existingWhiteList = result.getAccWhiteList();
			removeNullAndEmptyElementsFromRequest(existingWhiteList);
		} else {
			existingWhiteList = new AccountWhiteList();
			existingWhiteList.setCreatedOn(new Timestamp(System.currentTimeMillis()).toString());
			existingWhiteList.setOrgCode(request.getOrgCode());
			existingWhiteList.setAccountId(request.getAccountId());
		}
		return existingWhiteList;
	}

	/**
	 * removeNullAndEmptyElementsFromRequest() method removes null and empty
	 * elements from request lists.
	 *
	 * @param request
	 *            the request
	 */
	private void removeNullAndEmptyElementsFromRequest(AccountWhiteList request) {
		if (null != request) {
			removeNullAndEmptyElementFromList(request.getApprovedBuyCurrencyAmountRangeList());
			removeNullAndEmptyElementFromList(request.getApprovedReasonOfTransList());
			removeNullAndEmptyElementFromList(request.getApprovedThirdpartyAccountList());
			removeNullAndEmptyElementFromList(request.getApprovedSellCurrencyAmountRangeList());
			removeNullAndEmptyElementFromList(request.getDocumentationRequiredWatchlistSellCurrency());
			removeNullAndEmptyElementFromList(request.getUsClientListBBeneAccNumber());
		}
	}

	/**
	 * Removes the account white list duplicates.
	 *
	 * @param accWhiteList
	 *            the acc white list
	 */
	private void removeAccountWhiteListDuplicates(AccountWhiteList accWhiteList) {
		ApprovedCurrencyAmountRangePair.removeDuplicates(accWhiteList.getApprovedBuyCurrencyAmountRangeList());
		ApprovedCurrencyAmountRangePair.removeDuplicates(accWhiteList.getApprovedSellCurrencyAmountRangeList());
		HashSet<String> set = new HashSet<>(accWhiteList.getApprovedReasonOfTransList());
		accWhiteList.getApprovedReasonOfTransList().clear();
		accWhiteList.getApprovedReasonOfTransList().addAll(set);
		set.clear();
		set.addAll(accWhiteList.getApprovedThirdpartyAccountList());
		accWhiteList.getApprovedThirdpartyAccountList().clear();
		accWhiteList.getApprovedThirdpartyAccountList().addAll(set);
		set.clear();
		set.addAll(accWhiteList.getDocumentationRequiredWatchlistSellCurrency());
		accWhiteList.getDocumentationRequiredWatchlistSellCurrency().clear();
		accWhiteList.getDocumentationRequiredWatchlistSellCurrency().addAll(set);
		set.clear();
		set.addAll(accWhiteList.getUsClientListBBeneAccNumber());
		accWhiteList.getUsClientListBBeneAccNumber().clear();
		accWhiteList.getUsClientListBBeneAccNumber().addAll(set);
		set.clear();

		HashSet<HighRiskCountry> hrc = new HashSet<>(accWhiteList.getApprovedHighRiskCountryList());
		accWhiteList.getApprovedHighRiskCountryList().clear();
		accWhiteList.getApprovedHighRiskCountryList().addAll(hrc);
		hrc.clear();
		HashSet<HighRiskCountry> hrcForFundsIn = new HashSet<>(accWhiteList.getApprovedHighRiskCountryListForFundsIn());
		accWhiteList.getApprovedHighRiskCountryListForFundsIn().clear();
		accWhiteList.getApprovedHighRiskCountryListForFundsIn().addAll(hrcForFundsIn);
		hrcForFundsIn.clear();
	}

	/**
	 * Removes the null and empty element from list.
	 *
	 * @author abhijeetg
	 * @param list
	 *            the list
	 */
	public static void removeNullAndEmptyElementFromList(List<?> list) {

		if (null != list && !list.isEmpty()) {
			list.removeAll(Arrays.asList(null, ""));
		}
	}

	/**
	 * Checks if is list null or empty.
	 *
	 * @param list
	 *            the list
	 * @return the boolean
	 */
	public static Boolean isListNullOrEmpty(List<?> list) {
		Boolean result = Boolean.TRUE;
		if (null != list && !list.isEmpty()) {
			result = Boolean.FALSE;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * ICustomChecksService#searchAccountWhilteList(com.currenciesdirect.gtg.
	 * compliance.customchecks.domain.request.SearchWhiteList)
	 */
	@Override
	public SearchWhiteListResponse searchAccountWhilteList(SearchWhiteListRequest request) {
		SearchWhiteListResponse response = new SearchWhiteListResponse();
		AccountWhiteList existingAccWhiteList = null;
		ESSearchProcessor searchProcessor = new ESSearchProcessor();
		SearchResult result = searchProcessor.searchWhiteListDocument(request.getOrgCode(), request.getAccId());
		if (null != result && null != result.getAccWhiteList()) {
			existingAccWhiteList = result.getAccWhiteList();
			updatemaxAmountWithMargin(existingAccWhiteList);
			response.setWhiteListData(existingAccWhiteList);
			response.setErrorCode(CustomChecksErrors.SUCCESS.getErrorCode());
			response.setErrorDescription(CustomChecksErrors.SUCCESS.getErrorDescription());
		} else {
			response.setErrorCode(CustomChecksErrors.ERROR_NOT_FOUND.getErrorCode());
			response.setErrorDescription(CustomChecksErrors.ERROR_NOT_FOUND.getErrorDescription());
		}
		return response;
	}

	/**
	 * Updatemax amount with margin.
	 *
	 * @param existingAccWhiteList
	 *            the existing acc white list
	 */
	private void updatemaxAmountWithMargin(AccountWhiteList existingAccWhiteList) {
		for (ApprovedCurrencyAmountRangePair pair : existingAccWhiteList.getApprovedBuyCurrencyAmountRangeList()) {
			Double amtLimit = getMarginAmount(FUNDSOUT_ALLOWED_MARGIN, pair.getTxnAmountUpperLimit());
			Double safeListMarginAmount = getSafeCurrencyAmountLimit(pair.getCode(), DocumentType.FUNDS_OUT_ADD);
			if (safeListMarginAmount != null && safeListMarginAmount >= amtLimit) {
				pair.setTxnAmountUpperLimit(safeListMarginAmount);
			} else {
				pair.setTxnAmountUpperLimit(amtLimit);
			}

		}
		for (ApprovedCurrencyAmountRangePair pair : existingAccWhiteList.getApprovedSellCurrencyAmountRangeList()) {
			Double amtLimit = getMarginAmount(FUNDSIN_ALLOWED_MARGIN, pair.getTxnAmountUpperLimit());
			Double safeListMarginAmount = getSafeCurrencyAmountLimit(pair.getCode(), DocumentType.FUNDS_IN_ADD);
			if (safeListMarginAmount != null && safeListMarginAmount >= amtLimit) {
				pair.setTxnAmountUpperLimit(safeListMarginAmount);
			} else {
				pair.setTxnAmountUpperLimit(amtLimit);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.customchecks.core.
	 * ICustomChecksService#getBeneficiaryDetails(java.lang.String)
	 */
	@Override
	public PayeeESResponse getBeneficiaryDetails(String beneBankAccNumber) throws CustomChecksException {

		PayeeESResponse customCheckPayeeResponse;
		ESSearchProcessor searchProcessor = new ESSearchProcessor();
		customCheckPayeeResponse = searchProcessor.searchBeneficiaryDetails(beneBankAccNumber);
		return customCheckPayeeResponse;
	}

}
