package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class PaymentInQueueAccountFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private PaymentInFilter filter;
	

	/**
	 * Instantiates a new reg queue account filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where cluase
	 */
	public PaymentInQueueAccountFilterQueryBuilder(PaymentInFilter filter, String query,boolean addWhereCluase) {
		super(query, addWhereCluase);
		this.filter = filter;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {
		String key = filter.getKeyword();
		/**
		 * Validation for keyword not equal to null then match the Keyword with regularExpression and return type
		 * If type is not eqaul to null then search the record for that keyword
		 */
		if (key != null && !key.trim().isEmpty() && !KeywordConstants.COLON.equals(key)) {
			String keyword = key.trim();
			String type = parseKeyword(keyword);
			if (type != null) {
				if (!KeywordConstants.CLIENT_NAME.equals(type)) {
					searchKeyword(type, keyword);
				}
				// Checking for ClientName only
				else {
					addLikeCriteria("name", keyword, CriteriaBuilder.AND);
				}
			}
		}
		
		String[] types = filter.getCustType();
		if(null != types){
			String[] dbTypes = new String[types.length];
			int cnt=0;
			for(String t:types){
				dbTypes[cnt]= CustomerTypeEnum.getDbStatusFromUiStatus(t).toString();
				cnt++;
			}
			addInCriteria("Type", dbTypes, CriteriaBuilder.AND);
		}
		
		addSerivesCriteria();
		
	}
	
	private void addSerivesCriteria(){
		addWatchlistCriteria();
	}
	
	private void addWatchlistCriteria(){
		String[] watchlistStatus = filter.getWatchListStatus();
		if (watchlistStatus != null && watchlistStatus.length < 2 ) {
			if("PASS".equals(watchlistStatus[0])) {
				addEqualCriteria("PayInWatchlistStatus", ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria("PayInWatchlistStatus", ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
			
		}
	}
	
	/**
	 * The method searchKeyword
	 * Bussiness:
	 * 1.Get the type of the keyword and search value of that keyword
	 * Implementation:
	 * 1.Check type of keyword and apply criteria on column according to type
	 * 2.Search record according to value of that keyword
	 */	
	public void searchKeyword(String type, String keyword) {
		String[] keyValue = keyword.split(KeywordConstants.COLON);

		if (keyValue.length >= 2) {
			if(type.equals(KeywordConstants.ACSF_ID))
			{
				addEqualCriteria("crmAccountId", keyValue[1], CriteriaBuilder.AND);
			}
		} else if (null != keyValue[0] && KeywordConstants.CLIENT_ID.equals(type)) {
			addEqualCriteria(Constants.LEGACY_TRADE_ACCOUNT_NUMBER, keyword, CriteriaBuilder.AND);
			addPercentLikeCriteria(Constants.TRADE_ACCOUNT_NUMBER, keyword, CriteriaBuilder.OR);
		} else if (null != keyValue[0] && KeywordConstants.CLIENT_ID_WITH_SIX_DIGITS_ONLY.equals(type)) {
			addBeforePercentLikeCriteria(Constants.TRADE_ACCOUNT_NUMBER, keyword, CriteriaBuilder.AND);
		}
	}

}