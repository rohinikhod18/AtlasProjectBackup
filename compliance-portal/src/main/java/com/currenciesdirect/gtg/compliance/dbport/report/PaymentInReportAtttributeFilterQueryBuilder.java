package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentInReportFilter;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.PaymentInQueueFilterQueryBuilder;

public class PaymentInReportAtttributeFilterQueryBuilder extends PaymentInQueueFilterQueryBuilder {

	private PaymentInReportFilter filter;

	public PaymentInReportAtttributeFilterQueryBuilder(PaymentInReportFilter filter, String query,
			Boolean addWhereCluase) {
		super(filter, query, addWhereCluase);
		this.filter = filter;
	}

	@Override
	public void addFilter() {

		String keyInReport = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with
		 * regularExpression and return type If type is not equal to null then
		 * search the record for that keyword
		 */
		if (keyInReport != null && !KeywordConstants.COLON.equals(keyInReport)) {
			String keyword = keyInReport.trim();
			String type = parseKeyword(keyword);
			if (type != null && !KeywordConstants.CLIENT_NAME.equals(type)) {
				searchKeyword(type, keyword);
				
			}
		}

		String[] buyCurrenciesInReport = filter.getSellCurrency();
		if (buyCurrenciesInReport != null) {
			addInCriteria("vtransactioncurrency", buyCurrenciesInReport, CriteriaBuilder.AND);
		}
		
		String[] countryOfFundInReport = filter.getCountryofFund();
		if (countryOfFundInReport != null) {
			addInCriteria("c.DisplayName", countryOfFundInReport, CriteriaBuilder.AND);
		}
		
		String[] paymentMethodInReport = filter.getPaymentMethod();
		if (paymentMethodInReport != null) {
			addInCriteria("vPaymentMethod", paymentMethodInReport, CriteriaBuilder.AND);
		}
	}

	/**
	 * The method searchKeyword Business: 1.Get the type of the keyword and
	 * search value of that keyword Implementation: 1.Check type of keyword and
	 * apply criteria on column according to type 2.Search record according to
	 * value of that keyword
	 */
	@Override
	public void searchKeyword(String type, String keyword) {

		String[] keyValue = keyword.split(KeywordConstants.COLON);
		if (keyValue.length >= 2) {
			switch (type) {

			case KeywordConstants.AMOUNT:
				addEqualCriteria("vtransactionamount", keyValue[1], CriteriaBuilder.AND);
				break;
			case KeywordConstants.PAYMENT_METHOD:
				addEqualCriteria("vpaymentmethod", keyValue[1], CriteriaBuilder.AND);
				break;
			default:
				break;
			}
		} else {
			if (keyValue[0] != null && KeywordConstants.CONTRACT_NO.equals(type)) {
	      
				addContainsCriteria("Attributes", "contract_number", keyValue[0], 0, true, CriteriaBuilder.AND);
			}
		}
	}

}
