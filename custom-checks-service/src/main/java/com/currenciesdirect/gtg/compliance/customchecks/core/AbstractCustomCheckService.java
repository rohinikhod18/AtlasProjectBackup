package com.currenciesdirect.gtg.compliance.customchecks.core;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.currenciesdirect.gtg.compliance.commons.domain.customchecks.response.CustomCheckResponse;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.AccountWhiteList;
import com.currenciesdirect.gtg.compliance.commons.domain.whitelist.ApprovedCurrencyAmountRangePair;
import com.currenciesdirect.gtg.compliance.customchecks.domain.ResponseStatus;
import com.currenciesdirect.gtg.compliance.customchecks.domain.request.CustomChecksRequest;
import com.currenciesdirect.gtg.compliance.customchecks.util.StringUtils;

/**
 * The Class AbstractCustomCheckService.
 */
public abstract class AbstractCustomCheckService {

	/** The Constant FUNDSOUT_ALLOWED_MARGIN. */
	private static final String FUNDSOUT_ALLOWED_MARGIN = "fundsout.allowed.margin";

	/** The Constant LOG. */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCustomCheckService.class);

	/** The Constant PROPERTY_FUNDS_OUT_SAFE_CURRENCY. */
	private static final String PROPERTY_FUNDS_OUT_SAFE_CURRENCY = "atlas.safe.buy.currency";

	/** The Constant PROPERTY_FUNDS_IN_SAFE_CURRENCY. */
	private static final String PROPERTY_FUNDS_IN_SAFE_CURRENCY = "atlas.safe.sell.currency";

	/** The Constant COMMA. */
	private static final char COMMA = ',';

	/** The Constant COLON. */
	private static final String COLON = ":";

	/**
	 * Update account white list data.
	 *
	 * @param document
	 *            the document
	 * @param existingAccWhiteList
	 *            the existing acc white list
	 * @return the account white list
	 */
	protected AccountWhiteList updateAccountWhiteListData(CustomChecksRequest document,
			AccountWhiteList existingAccWhiteList) {
		AccountWhiteList newWhiteList;
		if (null == existingAccWhiteList) {
			newWhiteList = createNewWhiteListData(document);

		} else {
			Boolean found = Boolean.FALSE;
			if (null != existingAccWhiteList.getApprovedReasonOfTransList()) {
				existingAccWhiteList.getApprovedReasonOfTransList().removeAll(Collections.singleton(null));
			}

			if (null != existingAccWhiteList.getApprovedReasonOfTransList()
					&& !existingAccWhiteList.getApprovedReasonOfTransList().isEmpty()) {
				found = getApprovedReasonOfTrans(document, existingAccWhiteList, found);
			}

			if (Boolean.FALSE.equals(found)) {
				existingAccWhiteList.getApprovedReasonOfTransList().add(document.getReasonsOfTransferOnAccount());
			}
			if (null != document.getBuyCurrencyOnAccount()) {
				ApprovedCurrencyAmountRangePair buyPair = new ApprovedCurrencyAmountRangePair();
				buyPair.setCode(document.getBuyCurrencyOnAccount());
				buyPair.setTxnAmountUpperLimit(document.getBuyAmountOnAccount());
				updateCurrencyAmountPair(buyPair, existingAccWhiteList.getApprovedBuyCurrencyAmountRangeList());
			}
			if (null != document.getSellCurrencyOnAccount()) {
				ApprovedCurrencyAmountRangePair sellPair = new ApprovedCurrencyAmountRangePair();
				sellPair.setCode(document.getSellCurrencyOnAccount());
				sellPair.setTxnAmountUpperLimit(document.getSellAmountOnAccount());
				updateCurrencyAmountPair(sellPair, existingAccWhiteList.getApprovedSellCurrencyAmountRangeList());
			}

			newWhiteList = existingAccWhiteList;
		}

		return newWhiteList;
	}

	/**
	 * Creates the new white list data.
	 *
	 * @param document
	 *            the document
	 * @return the account white list
	 */
	private AccountWhiteList createNewWhiteListData(CustomChecksRequest document) {
		AccountWhiteList newWhiteList;
		newWhiteList = new AccountWhiteList();
		newWhiteList.setOrgCode(document.getOrgCode());
		newWhiteList.setAccountId(document.getAccId());

		if (null != document.getBuyCurrencyOnAccount()) {
			ApprovedCurrencyAmountRangePair buyPair = new ApprovedCurrencyAmountRangePair();
			buyPair.setCode(document.getBuyCurrencyOnAccount());
			buyPair.setTxnAmountUpperLimit(document.getBuyAmountOnAccount());
			List<ApprovedCurrencyAmountRangePair> approvedBuyCurrencyAmountRangeList = new ArrayList<>();
			approvedBuyCurrencyAmountRangeList.add(buyPair);
			newWhiteList.setApprovedBuyCurrencyAmountRangeList(approvedBuyCurrencyAmountRangeList);
		}

		if (null != document.getSellCurrencyOnAccount()) {
			ApprovedCurrencyAmountRangePair sellPair = new ApprovedCurrencyAmountRangePair();
			sellPair.setCode(document.getSellCurrencyOnAccount());
			sellPair.setTxnAmountUpperLimit(document.getSellAmountOnAccount());
			List<ApprovedCurrencyAmountRangePair> sellCurrencyAmountRangeList = new ArrayList<>();
			sellCurrencyAmountRangeList.add(sellPair);
			newWhiteList.setApprovedSellCurrencyAmountRangeList(sellCurrencyAmountRangeList);
		}

		List<String> reasons = new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(document.getReasonsOfTransferOnAccount()))
			reasons.add(document.getReasonsOfTransferOnAccount());
		newWhiteList.setApprovedReasonOfTransList(reasons);

		List<String> acc = new ArrayList<>();
		if(!StringUtils.isNullOrEmpty(document.getThirdPartyOnAccount()))
			acc.add(document.getThirdPartyOnAccount());
		newWhiteList.setApprovedThirdpartyAccountList(acc);

		return newWhiteList;
	}

	/**
	 * Gets the approved reason of trans.
	 *
	 * @param document
	 *            the document
	 * @param existingAccWhiteList
	 *            the existing acc white list
	 * @param found
	 *            the found
	 * @return the approved reason of trans
	 */
	private Boolean getApprovedReasonOfTrans(CustomChecksRequest document, AccountWhiteList existingAccWhiteList,
			Boolean found) {
		Boolean result = found;
		for (String reason : existingAccWhiteList.getApprovedReasonOfTransList()) {
			if (null != document.getReasonsOfTransferOnAccount()
					&& document.getReasonsOfTransferOnAccount().equalsIgnoreCase(reason)) {
				result = Boolean.TRUE;
				break;
			}
		}
		return result;
	}

	/**
	 * Update currency amount pair.
	 *
	 * @param requestPair
	 *            the request pair
	 * @param existingList
	 *            the existing list
	 */
	private void updateCurrencyAmountPair(ApprovedCurrencyAmountRangePair requestPair,
			List<ApprovedCurrencyAmountRangePair> existingList) {
		Boolean foundCurrency = Boolean.FALSE;
		Boolean foundAmount = Boolean.FALSE;
		ApprovedCurrencyAmountRangePair buyPair = null;
		for (ApprovedCurrencyAmountRangePair currency : existingList) {
			if (requestPair.getCode().equalsIgnoreCase(currency.getCode())) {
				foundCurrency = Boolean.TRUE;
				if (requestPair.getTxnAmountUpperLimit() <= currency.getTxnAmountUpperLimit())
					foundAmount = Boolean.TRUE;
				else
					buyPair = currency;
				break;
			}
		}
		if (Boolean.FALSE.equals(foundCurrency) || Boolean.FALSE.equals(foundAmount)) {
			if (Boolean.FALSE.equals(foundCurrency)) {
				buyPair = new ApprovedCurrencyAmountRangePair();
				buyPair.setCode(requestPair.getCode());
				buyPair.setTxnAmountUpperLimit(requestPair.getTxnAmountUpperLimit());
				existingList.add(buyPair);
			} else {
				buyPair.setTxnAmountUpperLimit(requestPair.getTxnAmountUpperLimit());
			}
		}
	}

	/**
	 * Gets the safe currency amount limit.
	 *
	 * @param currencyCode
	 *            the currency code
	 * @param docType
	 *            the doc type
	 * @return the safe currency amount limit
	 */
	protected Double getSafeCurrencyAmountLimit(String currencyCode, DocumentType docType) {
		String property = PROPERTY_FUNDS_OUT_SAFE_CURRENCY;
		String marginProperty = FUNDSOUT_ALLOWED_MARGIN;

		String safeList = System.getProperty(property);
		int startPOs = safeList.indexOf(currencyCode);
		if (startPOs >= 0) {
			int endPOS = safeList.indexOf(COMMA, startPOs);
			endPOS = (endPOS == -1) ? safeList.length() : endPOS;
			String[] curAmount = safeList.substring(startPOs, endPOS).split(COLON);
			Double maxAmount = Double.valueOf(curAmount[1]);
			return getMarginAmount(marginProperty, maxAmount);
		}
		return 0.0;
	}

	/**
	 * Gets the margin amount.
	 *
	 * @param type
	 *            the type
	 * @param baseAmt
	 *            the base amt
	 * @return the margin amount
	 */
	protected static Double getMarginAmount(String type, Double baseAmt) {
		Double result;
		Double percetage = 10.00;
		DecimalFormat limitedPrecisionFormat = new DecimalFormat("#.0000");
		try {
			percetage = Double.valueOf(System.getProperty(type));

		} catch (Exception ex) {
			LOGGER.debug("error parsing", ex);
		}
		result = baseAmt + (baseAmt * percetage / 100);
		return Double.valueOf(limitedPrecisionFormat.format(result));
	}

	/**
	 * Checks if is safe currency.
	 *
	 * @param currencyCode
	 *            the currency code
	 * @param docType
	 *            the doc type
	 * @return true, if is safe currency
	 */
	protected boolean isSafeCurrency(String currencyCode, DocumentType docType) {
		Boolean result = Boolean.FALSE;
		String property = PROPERTY_FUNDS_IN_SAFE_CURRENCY;
		String safeList = System.getProperty(property);
		int startPOs = safeList.indexOf(currencyCode);
		if (startPOs >= 0) {
			result = Boolean.TRUE;
		}
		return result;
	}

	/**
	 * Update overall status.
	 *
	 * @param response
	 *            the response
	 */
	protected void updateOverallStatus(CustomCheckResponse response) {
		/*
		 * NOT REQUIRED for whitelist check has been checked newly to resolve AT-1525 
		 * */
		if (null != response) {
			if (ResponseStatus.PASS.getStatus().equals(response.getVelocityCheck().getStatus())
					&& (ResponseStatus.PASS.getStatus().equals(response.getWhiteListCheck().getStatus()) 
							|| ResponseStatus.NOT_REQUIRED.getStatus().equals(response.getWhiteListCheck().getStatus()))
						&& (ResponseStatus.PASS.getStatus().equals(response.getEuPoiCheck().getStatus())
								|| ResponseStatus.NOT_REQUIRED.getStatus().equals(response.getEuPoiCheck().getStatus())))//Add for AT-3349
				response.setOverallStatus(ResponseStatus.PASS.getStatus());
			else
				response.setOverallStatus(ResponseStatus.FAIL.getStatus());
		}
	}
	
	/**
	 * Update overall status PFX.
	 *
	 * @param response the response
	 */
	//Add for AT-3161
	protected void updateOverallStatusPFX(CustomCheckResponse response) {
		/*
		 * NOT REQUIRED for whitelist check has been checked newly to resolve AT-1525 
		 * */
		if (null != response) {
			if (ResponseStatus.PASS.getStatus().equals(response.getVelocityCheck().getStatus())
					&& (ResponseStatus.PASS.getStatus().equals(response.getWhiteListCheck().getStatus()) 
							|| ResponseStatus.NOT_REQUIRED.getStatus().equals(response.getWhiteListCheck().getStatus()))
						&& ResponseStatus.PASS.getStatus().equals(response.getFraudPredictStatus())
							&& (ResponseStatus.PASS.getStatus().equals(response.getEuPoiCheck().getStatus())
									|| ResponseStatus.NOT_REQUIRED.getStatus().equals(response.getEuPoiCheck().getStatus())))//Add for AT-3349
				response.setOverallStatus(ResponseStatus.PASS.getStatus());
			else
				response.setOverallStatus(ResponseStatus.FAIL.getStatus());
		}
	}

}
