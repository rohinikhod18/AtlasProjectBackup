package com.currenciesdirect.gtg.compliance.core.domain.report;

import org.apache.poi.ss.usermodel.Workbook;

import com.currenciesdirect.gtg.compliance.core.domain.paymentin.PaymentInQueueDto;
import com.currenciesdirect.gtg.compliance.core.report.IReportGenerator;
import com.currenciesdirect.gtg.compliance.util.PrepareExcelSheet;

/**
 * The Class PaymentIn Report Generator
 *
 */
public class PaymentInReportGenerator implements IReportGenerator{

	private static final String[] title = { "TransactionId", "Date",
			"ClientId", "ContactName", "Type","Organization","Legal Entity","SellCurrency",
			"Amount","Method","Country", "OverallStatus", "Watchlist",
			"Fraugster", "Sanction","Blacklist","CustomCheck","FraudSightStatus","InitialStatus", "IntuitionCheck" };//AT-3714 
	
	private static final String[] getDataMembers = {"getTransactionId","getDate","getClientId","getContactName","getType","getOrganization","getLegalEntity","getSellCurrency","getAmount"
			,"getMethod","getCountryFullName","getOverallStatus","getWatchlist","getFraugster","getSanction","getBlacklist","getCustomCheck","getRiskStatus","getInitialStatus","getIntuitionStatus"};
	
	private PaymentInQueueDto paymentInQueue = null;

	/**
	 * @param paymentInQueueDto
	 */
	public PaymentInReportGenerator(PaymentInQueueDto paymentInQueueDto) {
     
		paymentInQueue = paymentInQueueDto;
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IReportGenerator#generateReport(org.apache.poi.ss.usermodel.Workbook)
	 */
	@Override
	public void generateReport(Workbook workbook){
	   new PrepareExcelSheet().prepareExcel(workbook, title, getDataMembers, paymentInQueue.getPaymentInQueue(), "PaymentIn Report");
	}

	@Override
	public String getReportName() {
		return "PaymentIn-Report";
	}
	
}
