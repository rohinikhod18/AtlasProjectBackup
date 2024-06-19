package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportFilter;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.enums.CustomerTypeEnum;

/**
 * @author laxmib
 *
 */
public class WorkEfficiencyReportFilterQueryBuilder extends AbstractFilterQueryBuilder {
 
	/** The filter */
	private WorkEfficiencyReportFilter filter ;
	
	/**
	 * @param filter
	 * @param query
	 */
	public WorkEfficiencyReportFilterQueryBuilder(WorkEfficiencyReportFilter filter,String query) {
		super(query, false);
		this.filter = filter;
	}

	@Override
	public void addFilter() {
		
		String[] types = filter.getCustType();
		if(null != types){
			String[] dbTypes = new String[types.length];
			int cnt=0;
			for(String t:types){
				dbTypes[cnt]= CustomerTypeEnum.getDbStatusFromUiStatus(t).toString();
				cnt++;
			}
			addInCriteria(WorkEfficiencyReportDBColumns.CFX_TYPE.getName(), dbTypes, CriteriaBuilder.AND);
			addInCriteria(WorkEfficiencyReportDBColumns.PFX_TYPE.getName(), dbTypes, CriteriaBuilder.OR);
			addInCriteria(WorkEfficiencyReportDBColumns.PAYIN_TYPE.getName(), dbTypes, CriteriaBuilder.OR);
			addInCriteriaWithBracket(WorkEfficiencyReportDBColumns.PAYOUT_TYPE.getName(), dbTypes, CriteriaBuilder.OR);
		}
		String[] queueType = filter.getQueueType();
		handleQueueType(queueType);
		String[] user = filter.getUser();
		if (user != null) {
			addInCriteria(WorkEfficiencyReportDBColumns.USER.getName(), user, CriteriaBuilder.AND);
		}

	}

	private void handleQueueType(String[] queueType) {
		if (queueType != null) {
			String[] filterList = new String[queueType.length];
			int cnt=0;
			for(String s:queueType){
				switch(s){
				case "Contact":
					filterList[cnt]="3";
					break;
				case "Payment In":
					filterList[cnt]="2";
					break;
				case "Payment Out":
					filterList[cnt]="1";
					break;
				case "Account":
					filterList[cnt]="4";
					break;
				default:
					break;
				}
				cnt++;
			}
			addInCriteria(WorkEfficiencyReportDBColumns.QUEUETYPE.getName(), filterList, CriteriaBuilder.AND);
		}
	}
}
