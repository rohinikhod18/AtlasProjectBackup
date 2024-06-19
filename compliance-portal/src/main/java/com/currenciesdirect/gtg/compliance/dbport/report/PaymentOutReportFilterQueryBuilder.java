package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.report.PaymentOutReportFilter;
import com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.PayInQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.PayOutQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.enums.AccountTMFlagEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

/**
 * The Class PaymentOutReportFilterQueryBuilder.
 */
public class PaymentOutReportFilterQueryBuilder extends AbstractFilterQueryBuilder {

	/** The filter. */
	private PaymentOutReportFilter filter;

	/**
	 * Instantiates a new payment out report filter query builder.
	 *
	 * @param filter the filter
	 * @param query  the query
	 */
	public PaymentOutReportFilterQueryBuilder(PaymentOutReportFilter filter, String query) {
		super(query, true);
		this.filter = filter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.currenciesdirect.gtg.compliance.dbport.AbstractFilterQueryBuilder#
	 * addFilter()
	 */
	@Override
	public void addFilter() {
		
		String[] paymentStatus = filter.getPaymentStatus();
		if (paymentStatus != null) {
			String[] dbTypes = new String[paymentStatus.length];
			int cnt = 0;
			for (String t : paymentStatus) {
				Integer id = PaymentComplianceStatus.getIdByComplianceStatus(t);
				if (id == null) {
					continue;
				}
				dbTypes[cnt] = PaymentComplianceStatus.getIdByComplianceStatus(t).toString();
				cnt++;
			}
			addInCriteria("payOut.complianceStatus", dbTypes, CriteriaBuilder.AND);
		}
		
		String[] orgaizations = filter.getOrganization();
		if (orgaizations != null) {
			addInCriteria("org.Name", orgaizations, CriteriaBuilder.AND);
		}
		String[] legalEntity = filter.getLegalEntity();
		if (legalEntity != null) {
			addInCriteria("le.Code", legalEntity, CriteriaBuilder.AND);
		}

		addSerivesCriteria();
		String registeredFrom = filter.getDateFrom();
		String registeredTo = filter.getDateTo();
		addDateTimeCriteria("TransactionDate", registeredFrom, registeredTo, CriteriaBuilder.AND);

		String[] owner = filter.getOwner();
		if (owner != null) {
			addInCriteria("U.SsoUserId", owner, CriteriaBuilder.AND);
		}

	}

	/**
	 * Adds the serives criteria.
	 */
	private void addSerivesCriteria() {
		addBlacklistCriteria();
		addSanctionCriteria();
		addFraugsterCriteria();
		addCustomCheckCriteria();
		addIntuitionCheckCriteria();//Added for AT-4614
		addTransactionMonitoringRequest();//Added for AT-4656
	}

	/**
	 * Adds the transaction monitoring request.
	 */
	//Added for AT-4656
	private void addTransactionMonitoringRequest() {
		String[] transactionMonitoringRequest = filter.getTransactionMonitoringRequest();
		if (Boolean.FALSE.equals(validateServiceStatus(transactionMonitoringRequest))) {
			if ("PASS".equals(transactionMonitoringRequest[0])) {
		         //Added for AT-4850
				String[] passCriteriaReportPO= {AccountTMFlagEnum.ACCOUNTTMFLAG1.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG2.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG4.getDatabaseStatus().toString()};
			addInCriteria(PayOutQueDBColumns.FILTER_BY_TRAANSACTIONMONITORING_REQUEST.getName(), passCriteriaReportPO, CriteriaBuilder.AND);			
			} else if("FAIL".equals(transactionMonitoringRequest[0])) {
			//Added for AT-4850
				String[] failCriteriaReportPO= {AccountTMFlagEnum.ACCOUNTTMFLAG0.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG3.getDatabaseStatus().toString()};
				addInCriteria(PayOutQueDBColumns.FILTER_BY_TRAANSACTIONMONITORING_REQUEST.getName(), failCriteriaReportPO, CriteriaBuilder.AND);
			}
		}
		
	}
	
	
	
	/**
	 * Adds the intuition check criteria.
	 */
	//Added for AT-4614
	private void addIntuitionCheckCriteria(){
		String[] intuitionCheckStatus = filter.getIntuitionStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(intuitionCheckStatus))) {
			if ("PASS".equals(intuitionCheckStatus[0])) {
				addEqualCriteria(PayOutQueDBColumns.FILTER_BY_INTUITIONCHECK.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				//Added for AT-4842
				String[] intuitionFailReportStatus= {ServiceStatusEnum.PASS.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString()};
				addNotInCriteria(PayOutQueDBColumns.FILTER_BY_INTUITIONCHECK.getName(), intuitionFailReportStatus, CriteriaBuilder.AND);
			
			}
		}
	}
	
	/**
	 * Adds the custom check criteria.
	 */
	private void addCustomCheckCriteria() {
		String[] customCheckStatus = filter.getCustomCheckStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(customCheckStatus))) {
			if ("PASS".equals(customCheckStatus[0])) {
				addEqualCriteria(PayOutQueDBColumns.FILTER_BY_CUSTOMCHECKSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(PayOutQueDBColumns.FILTER_BY_CUSTOMCHECKSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(PayOutQueDBColumns.FILTER_BY_CUSTOMCHECKSTATUS.getName(),
						ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
		}
	}

	/**
	 * Adds the blacklist criteria.
	 */
	private void addBlacklistCriteria() {
		String[] blacklistStatus = filter.getBlacklistStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(blacklistStatus))) {
			if (ServiceStatusEnum.PASS.getStatus().equals(blacklistStatus[0])) {
				addEqualCriteria(PayOutQueDBColumns.FILTER_BY_BLACKLISTSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(PayOutQueDBColumns.FILTER_BY_BLACKLISTSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(PayOutQueDBColumns.FILTER_BY_BLACKLISTSTATUS.getName(),
						ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}

		}
	}

	/**
	 * Adds the sanction criteria.
	 */
	private void addSanctionCriteria() {
		String[] sanctionStatus = filter.getSanctionStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(sanctionStatus))) {
			if (ServiceStatusEnum.PASS.getStatus().equals(sanctionStatus[0])) {
				addEqualCriteria(PayOutQueDBColumns.FILTER_BY_SANCTIONSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				String[] blacklistPayRefReportStatus= {ServiceStatusEnum.PASS.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_PERFORMED.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString()};
				addInCriteria(PayOutQueDBColumns.FILTER_BY_BLACKLISTPAYREFSTATUS.getName(), blacklistPayRefReportStatus,  CriteriaBuilder.AND);
			} else {
				//Added for AT-4850
				addNotEqualCriteria4(PayOutQueDBColumns.FILTER_BY_SANCTIONSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria5(PayOutQueDBColumns.FILTER_BY_BLACKLISTPAYREFSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria6(PayOutQueDBColumns.FILTER_BY_SANCTIONSTATUS.getName(),ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.OR);
				addEqualCriteria1(PayOutQueDBColumns.FILTER_BY_BLACKLISTPAYREFSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addEqualCriteria2(PayOutQueDBColumns.FILTER_BY_SANCTIONSTATUS.getName(),  ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.OR);
				addNotEqualCriteria3(PayOutQueDBColumns.FILTER_BY_BLACKLISTPAYREFSTATUS.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				
			}
		}
	}

	/**
	 * Adds the fraugster criteria.
	 */
	private void addFraugsterCriteria() {
		String[] fraugsterStatus = filter.getFraugsterStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(fraugsterStatus))) {
			if (ServiceStatusEnum.PASS.getStatus().equals(fraugsterStatus[0])) {
				addEqualCriteria(PayOutQueDBColumns.FILTER_BY_FRAUGSTERSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(PayOutQueDBColumns.FILTER_BY_FRAUGSTERSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(PayOutQueDBColumns.FILTER_BY_FRAUGSTERSTATUS.getName(),
						ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
		}
	}

}
