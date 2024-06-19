package com.currenciesdirect.gtg.compliance.core.domain.report;

import org.apache.poi.ss.usermodel.Workbook;

import com.currenciesdirect.gtg.compliance.core.domain.paymentout.PaymentOutQueueDto;
import com.currenciesdirect.gtg.compliance.core.report.IReportGenerator;
import com.currenciesdirect.gtg.compliance.util.PrepareExcelSheet;

/**
 * The Class PaymentOut Report Generator
 *
 */
public class PaymentOutReportGenerator implements IReportGenerator{

	
	private static final String[] titles = { "TransactionId", "ClientId","Date",
			"ContactName","Organization","Legal Entity","Maturity Date","Type","BuyCurrency",
			"Amount","Beneficiary","Country", "OverallStatus", "Watchlist",
			"Fraugster", "Sanction","Reference check","Blacklist","Custom Check","InitialStatus ","IntuitionStatus"};
	
	private static final String[] getDataMembers = {"getTransactionId","getClientId","getDate","getContactName",
			"getOrganisation","getLegalEntity","getMaturityDate","getType","getBuyCurrency","getAmount"
			,"getBeneficiary","getCountry","getOverallStatus",
			"getWatchlist","getFraugster","getSanction","getBlacklistPayRef","getBlacklist","getCustomCheck","getInitialStatus","getIntuitionStatus"};
	
	private PaymentOutQueueDto paymentOutQueue = null;
	
	/**
	 * @param paymentOutQueueDto
	 */
	public PaymentOutReportGenerator(PaymentOutQueueDto paymentOutQueueDto) {
     
		paymentOutQueue = paymentOutQueueDto;
	}

	/**
	 * @see com.currenciesdirect.gtg.compliance.core.IReportGenerator#generateReport(org.apache.poi.ss.usermodel.Workbook)
	 */
	@Override
	public void generateReport(Workbook workbook) {
		new PrepareExcelSheet().prepareExcel(workbook, titles, getDataMembers, paymentOutQueue.getPaymentOutQueue(), "PaymentOut Report");
	}

	@Override
	public String getReportName() {
		return "PaymentOut-Report";
	}
	
}
