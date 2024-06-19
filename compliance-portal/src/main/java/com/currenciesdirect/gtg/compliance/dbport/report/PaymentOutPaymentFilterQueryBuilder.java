package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportFilter;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;

/**
 * The Class PaymentOutPaymentFilterQueryBuilder.
 */
public class PaymentOutPaymentFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private PaymentOutReportFilter filter;

	/**
	 * Instantiates a new payment out payment filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where cluase
	 */
	public PaymentOutPaymentFilterQueryBuilder(PaymentOutReportFilter filter, String query,
			boolean addWhereCluase) {
		super(query, addWhereCluase);
		this.filter = filter;
	}

	@Override
	protected void addFilter() {
		String key = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with regularExpression and return type
		 * If type is not eqaul to null then search the record for that keyword
		 */
		if(key != null && !key.trim().isEmpty() && ! KeywordConstants.COLON.equals(key)) {
			String keyword=key.trim();
			String type=parseKeyword(keyword);
				if (type != null && !KeywordConstants.CLIENT_NAME.equals(type)) {
					searchKeyword(type, keyword);
				}
		}
		
		String[] buyCurrencies = filter.getBuyCurrency();
		if (buyCurrencies != null) {
			addInCriteria("vBuyingCurrency", buyCurrencies, CriteriaBuilder.AND);
		}
		
		String valueDateFrom = filter.getValueDateFrom();
		String valueDateTo = filter.getValueDateTo();
		addDateTimeCriteria("vvalueDate",valueDateFrom,valueDateTo,CriteriaBuilder.AND);
		
		String[] countryOfBeneficiary = filter.getCountryOfBeneficiary();
		if (countryOfBeneficiary != null) {
			addInCriteria("c.DisplayName", countryOfBeneficiary, CriteriaBuilder.AND);
		}

	}
	
	/**
	 * The method searchKeyword
	 * Business:
	 * 1.Get the type of the keyword and search value of that keyword
	 * Implementation:
	 * 1.Check type of keyword and apply criteria on column according to type
	 * 2.Search record according to value of that keyword
	 *
	 * @param type the type
	 * @param keyword the keyword
	 */
	public void searchKeyword(String type, String keyword){
		
		String[] keyValue = keyword.split(KeywordConstants.COLON);
		if(keyValue.length>=2)
		{
			switch(type) {

			case KeywordConstants.AMOUNT:
				addEqualCriteria("BeneAmount", keyValue[1], CriteriaBuilder.AND);
				break;
			case KeywordConstants.BENEFICIARY:
				String []name= keyValue[1].split(" ");
				addEqualCriteria("vBeneficiaryFirstName", name[0], CriteriaBuilder.AND);
				addEqualCriteria("vBeneficiaryLastName", name[1], CriteriaBuilder.AND);
				break;
			case KeywordConstants.REASON_OF_TRANSFER:
				addEqualCriteria("vReasonOfTransfer", keyValue[1], CriteriaBuilder.AND);
				break;
			default:
				break;
			}
		}else {
				if (keyValue[0] != null && KeywordConstants.CONTRACT_NO.equals(type)) {
					addContainsCriteria("Attributes", "contract_number", keyValue[0], 0, true, CriteriaBuilder.AND);
				}
		}
	}
	
}
