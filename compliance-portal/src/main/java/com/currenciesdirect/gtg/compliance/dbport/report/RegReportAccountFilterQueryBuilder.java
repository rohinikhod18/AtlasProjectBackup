package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.BaseFilter;
import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.OnfidoStatusEnum;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.RegQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.ProfileComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class RegReportAccountFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private BaseFilter filter;

	/**
	 * Instantiates a new reg queue account filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where cluase
	 */
	public RegReportAccountFilterQueryBuilder(BaseFilter filter, String query, boolean addWhereCluase) {
		super(query, addWhereCluase);
		this.filter = filter;
	}


	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {
		
		addSerivesCriteria();
		String[] status=filter.getStatus();
		if(status != null) {
			String[] dbTypes = new String[status.length];
			int cnt=0;
			for(String t:status){
				dbTypes[cnt]= ProfileComplianceStatus.getDbStatusFromUiStatus(t).toString();
				cnt++;
			}
			addInCriteria(RegQueDBColumns.REGSTATUS.getName(), dbTypes, CriteriaBuilder.AND);
		}
		
		 
		String[] onfidoStatus= filter.getOnfidoStatus();
		addOnfidoCriteria(onfidoStatus);	
		
		
		
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
					addLikeCriteria(KeywordConstants.CONTACT_NAME, keyword, CriteriaBuilder.AND);
				}
			}
			if(null != type && (type.equalsIgnoreCase(KeywordConstants.CLIENT_ID) 
					|| type.equalsIgnoreCase(KeywordConstants.CLIENT_ID_WITH_SIX_DIGITS_ONLY))){
				filter.setCustType(null);
			}
		}
		
		String registeredFrom = filter.getDateFrom();
		String registeredTo = filter.getDateTo();
		
		/** create date criteria for PFX and CFX records : Abhijit G*/
		addDateTimeCriteria(RegQueDBColumns.ACCOUNT_UPDATEDON.getName(),registeredFrom,registeredTo,CriteriaBuilder.AND);
  		
  		addCurrenciesCriteria();

		addSourceCriteria();

		addTransactionValueCriteria();
		
	}


	private void addTransactionValueCriteria() {
		String[] transValues = filter.getTransValue();
		if (transValues != null) {
			addInCriteria(RegQueDBColumns.TRANSACTIONVALUE.getName(), transValues, CriteriaBuilder.AND);
		}
	}


	private void addSourceCriteria() {
		String[] sources = filter.getSource();
		if (sources != null) {
			addInCriteria(RegQueDBColumns.SOURCE.getName(), sources, CriteriaBuilder.AND);
		}
	}


	private void addCurrenciesCriteria() {
		String[] buyCurrencies = filter.getBuyCurrency();
		if (buyCurrencies != null) {
			addInCriteria(RegQueDBColumns.BUYCURRENCY.getName(), buyCurrencies, CriteriaBuilder.AND);
		}

		String[] sellCurrencies = filter.getSellCurrency();
		if (sellCurrencies != null) {
			addInCriteria(RegQueDBColumns.SELLCURRENCY.getName(), sellCurrencies, CriteriaBuilder.AND);
		}
	}


	private void addOnfidoCriteria(String[] onfidoStatus) {
		if(onfidoStatus != null) {
			String[] dbTypes = new String[onfidoStatus.length];
			int count=0;
			for(String s:onfidoStatus){
            dbTypes[count]= OnfidoStatusEnum.getStatusAsInteger(s).toString();
				count++;
			}
			addInCriteria(RegQueDBColumns.ONFIDOSTATUS.getName(), dbTypes, CriteriaBuilder.AND);
		}
	}
	
	private void addSerivesCriteria(){
		addBlacklistCriteria();
		addSanctionCriteria();
	}
	
	private void addBlacklistCriteria(){
		String[] blacklistStatus = filter.getBlacklistStatus();
		if (blacklistStatus != null && blacklistStatus.length < 2 ) {
			if(ServiceStatusEnum.PASS.getStatus().equals(blacklistStatus[0])) {
				addEqualCriteria(RegQueDBColumns.BLACKLISTSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(RegQueDBColumns.BLACKLISTSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(RegQueDBColumns.BLACKLISTSTATUS.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
			
		}
	}
	
	private void addSanctionCriteria(){
		String[] sanctionStatus = filter.getSanctionStatus();
		if (sanctionStatus != null && sanctionStatus.length < 2) {
			if(ServiceStatusEnum.PASS.getStatus().equals(sanctionStatus[0])) {
				addEqualCriteria(RegQueDBColumns.SANCTIONSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(RegQueDBColumns.SANCTIONSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(RegQueDBColumns.SANCTIONSTATUS.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
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
	 *
	 * @param type the type
	 * @param keyword the keyword
	 */	
	public void searchKeyword(String type, String keyword) {
		String[] keyValue = keyword.split(KeywordConstants.COLON);

		if (keyValue.length >= 2 && KeywordConstants.ACSF_ID.equalsIgnoreCase(type)) {
			addEqualCriteria(Constants.CRM_ACCOUNT_ID, keyValue[1], CriteriaBuilder.AND);
		} else if (keyValue[0] != null && KeywordConstants.CLIENT_ID.equals(type)) {
			addEqualCriteria(Constants.LEGACY_TRADE_ACCOUNT_NUMBER, keyword, CriteriaBuilder.AND);
			addPercentLikeCriteria(Constants.TRADE_ACCOUNT_NUMBER, keyword, CriteriaBuilder.OR);
		} else if (keyValue[0] != null && KeywordConstants.CLIENT_ID_WITH_SIX_DIGITS_ONLY.equals(type)) {
			addPercentLikeCriteria(Constants.TRADE_ACCOUNT_NUMBER, keyword, CriteriaBuilder.AND);
			}
	}


}
