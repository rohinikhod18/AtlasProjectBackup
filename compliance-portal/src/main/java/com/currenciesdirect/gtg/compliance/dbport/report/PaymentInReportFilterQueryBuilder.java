package com.currenciesdirect.gtg.compliance.dbport.report;

import com.currenciesdirect.gtg.compliance.core.domain.KeywordConstants;
import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInFilter;
import com.currenciesdirect.gtg.compliance.dbport.CriteriaBuilder;
import com.currenciesdirect.gtg.compliance.dbport.PayInQueDBColumns;
import com.currenciesdirect.gtg.compliance.dbport.PaymentInQueueFilterQueryBuilder;
import com.currenciesdirect.gtg.compliance.dbport.enums.AccountTMFlagEnum;
import com.currenciesdirect.gtg.compliance.dbport.enums.PaymentComplianceStatus;
import com.currenciesdirect.gtg.compliance.dbport.enums.ServiceStatusEnum;

public class PaymentInReportFilterQueryBuilder extends PaymentInQueueFilterQueryBuilder {

	private PaymentInFilter filter;
	
	public PaymentInReportFilterQueryBuilder(PaymentInFilter filter, String query,
			Boolean addWhereCluase) {
		super(filter, query, addWhereCluase);
		this.filter = filter;
	}
	
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
			addInCriteria("payin.complianceStatus", dbTypes, CriteriaBuilder.AND);
				}
		
		String[] orgaizations = filter.getOrganization();
		if (orgaizations != null) {
			addInCriteria("org.Name", orgaizations, CriteriaBuilder.AND);
		}
		
		String[] legalEntity = filter.getLegalEntity();
		if(legalEntity != null) {
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
		//AT-1646 filter changes //AT-3830
		String[] riskStatus = filter.getRiskStatus();
		if (riskStatus != null && riskStatus[0].equals("PASS")) {
			addEqualCriteria("payin.debitCardFraudCheckStatus", ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
		}
		else if(riskStatus != null && riskStatus[0].equals("FAIL"))
		{
			addEqualCriteria("payin.debitCardFraudCheckStatus", ServiceStatusEnum.FAIL.getDatabaseStatus().toString(), CriteriaBuilder.AND);
		}
			}
	
	@Override
	protected void addSerivesCriteria() {
		addBlacklistCriteria();
		addSanctionCriteria();
		addFraugsterCriteria();
		addCustomCheckCriteria();
		addIntuitionCheckCriteria();//Added for AT-4614
		addTransactionMonitoringRequest();//Added for AT-4655
	}
	
	//Added for AT-4655
	@Override
	protected void addTransactionMonitoringRequest() {
		String[] transactionmonitoringrequest = filter.getTransactionMonitoringRequest();
		if (Boolean.FALSE.equals(validateServiceStatus(transactionmonitoringrequest))) {
			if ("PASS".equals(transactionmonitoringrequest[0])) {
				//Added for AT-4843
				String passReportCriteria[]= {AccountTMFlagEnum.ACCOUNTTMFLAG1.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG2.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG4.getDatabaseStatus().toString()};
				addInCriteria(PayInQueDBColumns.FILTER_BY_TRAANSACTIONMONITORING_REQUEST.getName(), passReportCriteria, CriteriaBuilder.AND);
				
			} else if ("FAIL".equals(transactionmonitoringrequest[0])) {
				//Added for AT-4843
				String failReportCriteria[]= {AccountTMFlagEnum.ACCOUNTTMFLAG0.getDatabaseStatus().toString(),AccountTMFlagEnum.ACCOUNTTMFLAG3.getDatabaseStatus().toString()};
				addInCriteria(PayInQueDBColumns.FILTER_BY_TRAANSACTIONMONITORING_REQUEST.getName(),
						failReportCriteria, CriteriaBuilder.AND);
				
			}
		}
	}

	//Added for AT-4614    
    @Override
    protected void addIntuitionCheckCriteria() {
        String[] intuitionCheckStatus = filter.getIntuitionStatus();
        if (Boolean.FALSE.equals(validateServiceStatus(intuitionCheckStatus))) {
            if("PASS".equals(intuitionCheckStatus[0])) {
                addEqualCriteria(PayInQueDBColumns.FILTER_BY_INTUITIONCHECK.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
            } else {
                
              //Added for AT-4842
				String[] intuitionFailReportPayInStatus= {ServiceStatusEnum.PASS.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString()};
				addNotInCriteria(PayInQueDBColumns.FILTER_BY_INTUITIONCHECK.getName(), intuitionFailReportPayInStatus, CriteriaBuilder.AND);
            }
        }
    }
		
	@Override
	protected void addBlacklistCriteria() {
		String[] blacklistStatus = filter.getBlacklistStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(blacklistStatus))) {
			if ("PASS".equals(blacklistStatus[0])) {
				addEqualCriteria(PayInQueDBColumns.BLACKLISTSTATUSFILTER.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				}else {
				addNotEqualCriteria(PayInQueDBColumns.BLACKLISTSTATUSFILTER.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(PayInQueDBColumns.BLACKLISTSTATUSFILTER.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				}

			}
		}
		
	@Override
	protected void addSanctionCriteria() {
		String[] sanctionStatus = filter.getSanctionStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(sanctionStatus))) {
			if ("PASS".equals(sanctionStatus[0])) {
				addEqualCriteria(PayInQueDBColumns.SANCTIONSTATUSFILTER.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				//Added for AT-4850
				String[] sactionPayInReportFailStatus= {ServiceStatusEnum.PASS.getDatabaseStatus().toString(),ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString()};
				addNotInCriteria(PayInQueDBColumns.SANCTIONSTATUSFILTER.getName(), sactionPayInReportFailStatus, CriteriaBuilder.AND);
			}
		}
		}
		
	@Override
	protected void addFraugsterCriteria() {
		String[] fraugsterStatus = filter.getFraugsterStatus();
		if (Boolean.FALSE.equals(validateServiceStatus(fraugsterStatus))) {
			if ("PASS".equals(fraugsterStatus[0])) {
				addEqualCriteria(PayInQueDBColumns.FRAUGSTERSTATUSFILTER.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			} else {
				addNotEqualCriteria(PayInQueDBColumns.FRAUGSTERSTATUSFILTER.getName(), ServiceStatusEnum.PASS.getDatabaseStatus().toString(), CriteriaBuilder.AND);
				addNotEqualCriteria(PayInQueDBColumns.FRAUGSTERSTATUSFILTER.getName(), ServiceStatusEnum.NOT_REQUIRED.getDatabaseStatus().toString(), CriteriaBuilder.AND);
			}
		}
	}
	
	/**
	 * The method searchKeyword Bussiness: 1.Get the type of the keyword and
	 * search value of that keyword Implementation: 1.Check type of keyword and
	 * apply criteria on column according to type 2.Search record according to
	 * value of that keyword
	 */
	@Override
	public void searchKeyword(String type, String keyword) {
		String[] keyValue = keyword.split(KeywordConstants.COLON);
		if (keyValue.length >= 2) {
			switch (type) {
			case KeywordConstants.ACSF_ID: 
				addEqualCriteria(PayInReportQueueDBColumns.ACSFID_SEARCH_KEYWORD.getName(), keyValue[1],
						CriteriaBuilder.AND);
				break;

			case KeywordConstants.PAYMENT_METHOD: 
				addLikeCriteria(PayInReportQueueDBColumns.METHOD_SEARCH_KEYWORD.getName(), keyValue[1],
						CriteriaBuilder.AND);
				break;

			case KeywordConstants.AMOUNT:
				// condition changed from addLikeCriteria() to
				// addEqualCriteria() to resolve AT-422
				addEqualCriteria(PayInReportQueueDBColumns.AMOUNT_SEARCH_KEYWORD.getName(), keyValue[1],
						CriteriaBuilder.AND);
				break;
			default :
				break;
			}
		} else {
			if (keyValue[0] != null) {
				if (KeywordConstants.EMAIL.equals(type)) {
					addLikeCriteria(PayInReportQueueDBColumns.CONTACT_ATTRIBUTE_SEARCH_KEYWORD.getName(),
							"\"email\"" + KeywordConstants.COLON + "\"" + keyword + "\"", CriteriaBuilder.AND);
				} else if (KeywordConstants.CLIENT_ID.equals(type)) {
					addEqualCriteria(PayInReportQueueDBColumns.CLIENT_SEARCH_KEYWORD.getName(), keyword,
							CriteriaBuilder.AND);
				} else if(KeywordConstants.CONTRACT_NO.equals(type)){ 
					addEqualCriteria(PayInReportQueueDBColumns.TRANSACTIONS_SEARCH_KEYWORD.getName(), keyword,
							CriteriaBuilder.AND);
				}

			}
		}
	}
}
