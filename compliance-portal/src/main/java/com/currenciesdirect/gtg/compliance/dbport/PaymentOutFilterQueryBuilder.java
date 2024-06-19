package com.currenciesdirect.gtg.compliance.dbport;

import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutFilter;
import com.currenciesdirect.gtg.compliance.dbport.enums.AccountTMFlagEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

/**
 * The class PaymentOutFilterQueryBuilder
 */
public class PaymentOutFilterQueryBuilder extends AbstractFilterQueryBuilder {

	private PaymentOutFilter filter;

	/**
	 * Instantiates a new payment out filter query builder.
	 *
	 * @param filter        the filter
	 * @param query         the query
	 * @param isFilterApply the is filter apply
	 */
	public PaymentOutFilterQueryBuilder(PaymentOutFilter filter, String query) {
		super(query, false);
		this.filter = filter;
	}

	@Override
	public void addFilter() {
		
		String[] orgaizations = filter.getOrganization();
		if (orgaizations != null) {
			addInCriteria("org.Name", orgaizations, CriteriaBuilder.AND);
		}
		
		String[] legalEntity = filter.getLegalEntity();
		if (legalEntity != null) {
			addInCriteria("le.Code", legalEntity, CriteriaBuilder.AND);
		}

		addSerivesCriteria();

		String[] owner = filter.getOwner();

		if (owner != null) {
			addInCriteria(PayOutQueDBColumns.REGOWNER.getName(), owner, CriteriaBuilder.AND);
		}

		String registeredFrom = filter.getDateFrom();
		String registeredTo = filter.getDateTo();
		addDateTimeCriteria("TransactionDate", registeredFrom, registeredTo, CriteriaBuilder.AND);
		
	}

	private void addSerivesCriteria() {
		addBlacklistCriteria();
		addSanctionCriteria();
		addFraugsterCriteria();
		addCustomCheckCriteria();
		addIntuitionCheckCriteria();//Added for AT-4614
		addTransactionMonitoringRequest();//Added for AT-4656
	}
	
	//Added for AT-4656
		private void addTransactionMonitoringRequest() {
			String[] transactionmonitoringrequest = filter.getTransactionMonitoringRequest();
			if (Boolean.FALSE.equals(validateServiceStatus(transactionmonitoringrequest))) {
				if ("PASS".equals(transactionmonitoringrequest[0])) {
					//Added for AT-4850
					String[] passCriteriaQueue= {AccountTMFlagEnum.ACCOUNTTMFLAG1.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG2.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG4.getDatabaseStatus().toString()};
					addInCriteria(PayOutQueDBColumns.FILTER_BY_TRAANSACTIONMONITORING_REQUEST.getName(), passCriteriaQueue, CriteriaBuilder.AND);
				} else if("FAIL".equals(transactionmonitoringrequest[0])) {
					//Added for AT-4850
				String[] failCriteriaQueue= {AccountTMFlagEnum.ACCOUNTTMFLAG0.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG3.getDatabaseStatus().toString()};
				addInCriteria(PayOutQueDBColumns.FILTER_BY_TRAANSACTIONMONITORING_REQUEST.getName(), failCriteriaQueue, CriteriaBuilder.AND);
				}
			}
		}
	
	//Added for AT-4614
    private void addIntuitionCheckCriteria() {
        String[] intuitionCheckStatus = filter.getIntuitionStatus();
        if (Boolean.FALSE.equals(validateServiceStatus(intuitionCheckStatus))) {
            if ("PASS".equals(intuitionCheckStatus[0])) {
                addEqualCriteria(PayOutQueDBColumns.FILTER_BY_INTUITIONCHECK.getName(),
                        ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
            } else {
            	//Added for AT-4842
				String[] intuitionFailQueueStatus= {ServiceStatusEnum.PASS.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString()};
				addNotInCriteria(PayOutQueDBColumns.FILTER_BY_INTUITIONCHECK.getName(), intuitionFailQueueStatus, CriteriaBuilder.AND);
        
            }
        }
    }

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

	private void addBlacklistCriteria() {
		String[] blacklistStatus = filter.getBlacklistStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(blacklistStatus))) {
			if ("PASS".equals(blacklistStatus[0])) {
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

	private void addSanctionCriteria() {
		String[] sanctionStatus = filter.getSanctionStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(sanctionStatus))) {
			if ("PASS".equals(sanctionStatus[0])) {
				addEqualCriteria(PayOutQueDBColumns.FILTER_BY_SANCTIONSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				String[] blacklistPayRefQueueStatus= {ServiceStatusEnum.PASS.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_PERFORMED.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString()};
				addInCriteria(PayOutQueDBColumns.FILTER_BY_BLACKLISTPAYREFSTATUS.getName(), blacklistPayRefQueueStatus,  CriteriaBuilder.AND);
			} else {

				addNotEqualCriteria2(PayOutQueDBColumns.FILTER_BY_SANCTIONSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria3(PayOutQueDBColumns.FILTER_BY_BLACKLISTPAYREFSTATUS.getName(),
						ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);

			}
		}
	}

	private void addFraugsterCriteria() {
		String[] fraugsterStatus = filter.getFraugsterStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(fraugsterStatus))) {
			if ("PASS".equals(fraugsterStatus[0])) {
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
