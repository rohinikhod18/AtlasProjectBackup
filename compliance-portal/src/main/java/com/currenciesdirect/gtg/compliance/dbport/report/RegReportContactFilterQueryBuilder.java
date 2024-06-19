	package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportFilter;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.RegQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.ProfileComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

/**
 * The Class RegQueueFilterQueryBuilder.
 */
public class RegReportContactFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private RegistrationReportFilter filter;
	
	private boolean filterAppliedForAccountAndContact=false;
	
	/**
	 * Instantiates a new reg queue contact filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 * @param isFilterApply the is filter apply
	 * @param addWhereCluase the add where clause
	 */
	public RegReportContactFilterQueryBuilder(RegistrationReportFilter filter, String query, boolean addWhereCluase) {
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
			filterAppliedForAccountAndContact =true;
		}
		
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
					filterAppliedForAccountAndContact =true;
				}
			}
		}
		String registeredFrom = filter.getDateFrom();
		String registeredTo = filter.getDateTo();
		
		/** create date criteria for PFX and CFX records : Abhijit G*/
		addDateTimeCriteria(RegQueDBColumns.CONTACT_UPDATEDON.getName(),registeredFrom,registeredTo,CriteriaBuilder.AND);
		
	}
	
	private void addSerivesCriteria(){
		addBlacklistCriteria();
		addKycCriteria();
		addSanctionCriteria();
		addFraugsterCriteria();
		addCustomCheckCriteria();
	}
	
	/**
	 * Adds the custom check criteria.
	 */
	private void addCustomCheckCriteria() {
		String[] customCheckStatus = filter.getCustomCheckStatus();
		if (customCheckStatus != null && customCheckStatus.length < 2 ) {
			if(ServiceStatusEnum.PASS.getStatus().equals(customCheckStatus[0])) {
				addEqualCriteria(RegQueDBColumns.CUSOMCHECKSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(RegQueDBColumns.CUSOMCHECKSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(RegQueDBColumns.CUSOMCHECKSTATUS.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
		}
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
			filterAppliedForAccountAndContact =true;
		}
	}
	
	private void addKycCriteria(){
		String[] kycStatus = filter.getKycStatus();
		if (kycStatus != null) {
			if(ServiceStatusEnum.PASS.getStatus().equals(kycStatus[0]) && kycStatus.length < 2) {
				addEqualCriteria(RegQueDBColumns.KYCSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(RegQueDBColumns.KYCSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(RegQueDBColumns.KYCSTATUS.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
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
			filterAppliedForAccountAndContact =true;
		}
		
	}
	
	private void addFraugsterCriteria(){
		String[] fraugsterStatus = filter.getFraugsterStatus();
		if (fraugsterStatus != null && fraugsterStatus.length < 2) {
			if(ServiceStatusEnum.PASS.getStatus().equals(fraugsterStatus[0])) {
				addEqualCriteria(RegQueDBColumns.FRAUGSTERSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(RegQueDBColumns.FRAUGSTERSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(RegQueDBColumns.FRAUGSTERSTATUS.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
			filterAppliedForAccountAndContact =true;
		}
	}
	
	/**
	 * The method searchKeyword
	 * @param type
	 * @param keyword
	 * Business:
	 * 1.Get the type of the keyword and search value of that keyword
	 * Implementation:
	 * 1.Check type of keyword and apply criteria on column according to type
	 * 2.Search record according to value of that keyword
	 */	
	public void searchKeyword(String type, String keyword) {
		String[] keyValue = keyword.split(KeywordConstants.COLON);

		if (keyValue.length >= 2) {
			switch (type) {
			case KeywordConstants.COUNTRY_OF_RESIDENCE:
				addCountryOfResidenceCriteria(keyValue[1]);
				break;
			case KeywordConstants.ADDRESS:
				addContainsCriteria(Constants.ATTRIBUTES, "address1", "\""+keyValue[1]+"\"", Integer.MAX_VALUE, false, CriteriaBuilder.AND);
				break;
			case KeywordConstants.OCCUPATION:
				addContainsCriteria(Constants.ATTRIBUTES, "occupation", "\""+keyValue[1]+"\"", 0, true, CriteriaBuilder.AND);
				break;
			case KeywordConstants.TELEPHONE:  
				String searchValue = getSearchValue(keyValue);
				addContainsCriteria(Constants.ATTRIBUTES, "\"*phone\"", searchValue, 10, false, CriteriaBuilder.AND);
				break;
			default:
				break;
			}
		} else {
			if (keyValue[0] != null) {
				addEmailCriteria(type, keyword);
			}
		}

	}

	/**
	 * Adds the country of residence criteria.
	 *
	 * @param countryName the country name
	 */
	private void addCountryOfResidenceCriteria(String countryName) {
		if(countryName != null) {
			addEqualCriteria(Constants.COUNTRY_DISPLAY_NAME, countryName, CriteriaBuilder.AND);
		}
	}

	/**
	 * @param type
	 * @param keyword
	 */
	private void addEmailCriteria(String type, String keyword) {
		if (KeywordConstants.EMAIL.equals(type)) {
			addEqualCriteria("vemail", keyword, CriteriaBuilder.AND);
		}
	}


	/**
	 * @param keyValue
	 * @return
	 */
	private String getSearchValue(String[] keyValue) {
		// Search in all types of phone numbers,
		// Also search for matching last nine digits
		String searchValue;
		if(keyValue[1].length()>9)
			searchValue = "\"*" + keyValue[1].substring(0, 9) + "*\"";
		else
			searchValue = "\"*" + keyValue[1] + "*\"";
		return searchValue;
	}


	/**
	 * @return the filterAppliedForAccountAndContact
	 */
	public boolean isFilterAppliedForAccountAndContact() {
		return filterAppliedForAccountAndContact;
	}


	/**
	 * @param filterAppliedForAccountAndContact the filterAppliedForAccountAndContact to set
	 */
	public void setFilterAppliedForAccountAndContact(boolean filterAppliedForAccountAndContact) {
		this.filterAppliedForAccountAndContact = filterAppliedForAccountAndContact;
	}
}
