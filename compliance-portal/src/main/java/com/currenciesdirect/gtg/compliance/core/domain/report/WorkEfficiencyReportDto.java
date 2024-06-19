package com.currenciesdirect.gtg.compliance.core.domain.report;

import java.util.List;

import com.currenciesdirect.gtg.compliance.core.domain.BaseReportDto;

/**
 * The Class WorkEfficiencyReportDto.
 * 
 * @author laxmib
 *
 */
public class WorkEfficiencyReportDto extends BaseReportDto{
	
	/** The work efficiency report data */
	private List<WorkEfficiencyReportData> workEfficiencyReportData;
	
	/** The user name list */
	private List<String> userNameList;
	
	/** The queue list */
	private List<String> queueList;

	/**
	 * @return the workEfficiencyReportData
	 */
	public List<WorkEfficiencyReportData> getWorkEfficiencyReportData() {
		return workEfficiencyReportData;
	}

	/**
	 * @param workEfficiencyReportData the workEfficiencyReportData to set
	 */
	public void setWorkEfficiencyReportData(List<WorkEfficiencyReportData> workEfficiencyReportData) {
		this.workEfficiencyReportData = workEfficiencyReportData;
	}

	/**
	 * @return the userNameList
	 */
	public List<String> getUserNameList() {
		return userNameList;
	}

	/**
	 * @param userNameList the userNameList to set
	 */
	public void setUserNameList(List<String> userNameList) {
		this.userNameList = userNameList;
	}

	/**
	 * @return the queueList
	 */
	public List<String> getQueueList() {
		return queueList;
	}

	/**
	 * @param list the queueList to set
	 */
	public void setQueueList(List<String> list) {
		this.queueList = list;
	}
	
	
}
