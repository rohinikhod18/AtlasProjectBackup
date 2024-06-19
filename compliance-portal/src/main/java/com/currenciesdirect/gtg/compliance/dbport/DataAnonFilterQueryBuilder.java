package com.currenciesdirect.gtg.compliance.dbport;

import java.util.Arrays;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.dataanonymisation.DataAnonymisationFilter;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;

/**
 * The Class DataAnonFilterQueryBuilder.
 */
public class DataAnonFilterQueryBuilder extends AbstractFilterQueryBuilder {
	
	private DataAnonymisationFilter filter;
	
	// is 6 Digit search applied
		/** The is filter apply. */
		// if yes, do not apply type filter
		private Boolean isFilterApply;

		
		public DataAnonFilterQueryBuilder(DataAnonymisationFilter filter, String query, Boolean isFilterApply, boolean addWhereCluase) {
			super(query, addWhereCluase);
			this.filter = filter;
			this.isFilterApply=isFilterApply;
		}
		
		/**
		 * Gets the checks if is filter apply.
		 *
		 * @return the checks if is filter apply
		 */
		public Boolean getIsFilterApply() {
			return isFilterApply;
		}

		/**
		 * Sets the checks if is filter apply.
		 *
		 * @param isFilterApply the new checks if is filter apply
		 */
		public void setIsFilterApply(Boolean isFilterApply) {
			this.isFilterApply = isFilterApply;
		}
		
		/* (non-Javadoc)
		 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
		 */
		@Override
		protected void addFilter() 
		{
			String[] owner = filter.getOwner();
		
			if ( owner != null) {
				addInCriteria(RegQueDBColumns.REGOWNER.getName(), owner, CriteriaBuilder.AND);
				addISNullCriteria(RegQueDBColumns.ISLOCKRELEASEDON.getName(), CriteriaBuilder.AND);
			}
			
			String[] orgaizations = filter.getOrganization();
			if (orgaizations != null) {
				addInCriteria(RegQueDBColumns.ORG_NAME.getName(), orgaizations, CriteriaBuilder.AND);
			}

			String[] legalEntity = filter.getLegalEntity();
			if (legalEntity != null) {
				addInCriteria(RegQueDBColumns.LEGAL_ENTITY.getName(), legalEntity, CriteriaBuilder.AND);
			}
			
			
			String[] types = filter.getCustType();
			
			if(null != types){
				String[] dbTypes = new String[types.length];
				int cnt=0;
				for(String t:types){
					dbTypes[cnt]= CustomerTypeEnum.getDbStatusFromUiStatus(t).toString();
					cnt++;
				}
				addInCriteria(RegQueDBColumns.TYPE_FILTER.getName(), dbTypes, CriteriaBuilder.AND);
			}
			String[] newOrUpdatedRecord = filter.getNewOrUpdatedRecord();
			if(newOrUpdatedRecord != null && Arrays.asList(newOrUpdatedRecord).contains(Constants.NEW)) {
				addEqualCriteria(Constants.A_DOT_VERSION, "1", CriteriaBuilder.AND);
			} 
			else if(newOrUpdatedRecord != null && Arrays.asList(newOrUpdatedRecord).contains(Constants.UPDATED)) {
				addGreaterCriteria(Constants.A_DOT_VERSION, "1", CriteriaBuilder.AND);
			}
			
		}

}
