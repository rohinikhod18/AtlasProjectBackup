package com.currenciesdirect.gtg.compliance.core.domain.report;

import org.apache.poi.ss.usermodel.Workbook;

import com.currenciesdirect.gtg.compliance.core.domain.registration.RegistrationQueueDto;
import com.currenciesdirect.gtg.compliance.core.report.IReportGenerator;
import com.currenciesdirect.gtg.compliance.util.PrepareExcelSheet;

/**
 * The Class Registration Report Generator
 *
 */
public class RegistrationReportGenerator implements IReportGenerator  {

	
	private static final String[] titles = { "Client number", "Date",
				"Client name", "Type", "Country of Residence","Organization","Legal Entity",
				"N/U","Registration Date","Status","Transaction Value","EID", "Fraugster", "Sanction",
				"Blacklist","Custom Check"};
		
	private static final String[] getDataMembers = {"getTradeAccountNum","getRegisteredOn","getContactName","getType","getCountryOfResidence","getOrganisation"
				,"getLegalEntity","getNewOrUpdated","getRegisteredDate","getComplianceStatus","getTransactionValue","getEidCheck","getFraugster","getSanction","getBlacklist","getCustomCheck"};
		
	/** The registration queue. */
	private RegistrationQueueDto registrationQueue=null;
		
	public RegistrationReportGenerator(RegistrationQueueDto registrationQueueDto) {
			
			registrationQueue = registrationQueueDto;
		}



	@Override
	public void generateReport(Workbook workbook) {
		 new PrepareExcelSheet().prepareExcel(workbook, titles, getDataMembers, registrationQueue.getRegistrationQueue(), "Registration Report");
	}



	@Override
	public String getReportName() {
		return "Registration-Report";
	}

}


