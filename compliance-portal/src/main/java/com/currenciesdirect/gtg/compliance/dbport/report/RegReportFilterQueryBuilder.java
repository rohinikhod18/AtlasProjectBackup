package com.currenciesdirect.gtg.compliance.dbport.report;

import java.util.Arrays;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;
import com.currenciesdirect.gtg.compliance.core.domain.report.RegistrationReportFilter;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.RegQueueFilterQueryBuilder;

/**
 * The class RegReportFilterQueryBuilder
 */
public class RegReportFilterQueryBuilder  extends RegQueueFilterQueryBuilder
{

	private RegistrationReportFilter filter;
	
	/**
	 * Instantiates a new reg report  filter query builder.
	 *
	 * @param filter the filter
	 * @param query the query
	 */
	public RegReportFilterQueryBuilder(RegistrationReportFilter filter, String query, Boolean isFilterApply, Boolean addWhereClause) 
	{
		super(filter, query, isFilterApply, addWhereClause);
		this.filter = filter;
	}
	
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.dbport.FilterQueryBuilder#addFiler()
	 */
	@Override
	protected void addFilter() {

		super.addFilter();
		
		String[] newOrUpdatedRecord = filter.getNewOrUpdatedRecord();
		if (newOrUpdatedRecord != null && Arrays.asList(newOrUpdatedRecord).contains(Constants.NEW)) {
			addEqualCriteria(Constants.A_DOT_VERSION, "1", CriteriaBuilder.AND);
		} else if (newOrUpdatedRecord != null && Arrays.asList(newOrUpdatedRecord).contains(Constants.UPDATED)) {
			addGreaterCriteria(Constants.A_DOT_VERSION, "1", CriteriaBuilder.AND);
		}
	}
	
		
	}
