package com.currenciesdirect.gtg.compliance.core.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportDto;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportFilter;
import com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria;
import com.currenciesdirect.gtg.compliance.exception.CompliancePortalException;
import com.currenciesdirect.gtg.compliance.util.SearchCriteriaUtil;

/**
 * The Class WorkEfficiencyReportServiceImpl.
 */
@Component("workEfficiencyReportServiceImpl")
public class WorkEfficiencyReportServiceImpl implements IWorkEfficiencyReportService{

	/** The log. */
	private Logger log = LoggerFactory.getLogger(WorkEfficiencyReportServiceImpl.class);
	
	/** The i work efficiency report DB service. */
	@Autowired
	@Qualifier("workEfficiencyReportDBServiceImpl")
	private IWorkEfficiencyReportDBService iWorkEfficiencyReportDBService;
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IWorkEfficiencyReportService#getWorkEfficiencyReport()
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReport() throws CompliancePortalException{
		WorkEfficiencyReportSearchCriteria searchCriteria = new WorkEfficiencyReportSearchCriteria();
		WorkEfficiencyReportFilter workEfficiencyReportFilter = new WorkEfficiencyReportFilter();
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		try {
			String fromTo = null;
			String dateFrom = null;
			fromTo = getFromTo(fromTo);
			dateFrom = getDateFrom(dateFrom);
			workEfficiencyReportFilter.setFromTo(fromTo);
			workEfficiencyReportFilter.setDateFrom(dateFrom);
			
			Integer dateDifference=dateDifferenceInWorkEfficiencyReport(fromTo,dateFrom);
			
			searchCriteria.setFilter(workEfficiencyReportFilter);
			workEfficiencyReportDto = iWorkEfficiencyReportDBService.getWorkEfficiencyReport(searchCriteria);
			workEfficiencyReportDto.setDateDifference(dateDifference);
		    return workEfficiencyReportDto;
		}catch(CompliancePortalException e){
			log.error("{1}",e);
			throw e;
		}
		
		
	}
	
	/**
	 * Date difference in work efficiency report.
	 *
	 * @param to the to
	 * @param from the from
	 * @return the integer
	 */
	//This method is added to resolve sonar issue
	public Integer dateDifferenceInWorkEfficiencyReport(String to, String from) 
	{
		        Integer dateDifference = 0;
		        try {
	                      dateDifference = getDateDifference(to,from); 
                 }
		        catch (ParseException e) {
					log.error("Exception in getWorkEfficiencyReport() in WorkEfficiencyReportServiceImpl {1} ",  e);
				}
		        return dateDifference;	
     }
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.IWorkEfficiencyReportService#getWorkEfficiencyReportWithCriteria(com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria)
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteria(WorkEfficiencyReportSearchCriteria searchCriteria) throws CompliancePortalException{
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		try {
			String fromTo = getFromTo(searchCriteria.getFilter().getFromTo()); //currentDate
			String dateFrom = getDateFrom(searchCriteria.getFilter().getDateFrom()); //pastdate
			searchCriteria.getFilter().setFromTo(fromTo);
			searchCriteria.getFilter().setDateFrom(dateFrom);
			
			Integer dateDifference=dateDifferenceInWorkEfficiencyReport(fromTo,dateFrom);
			
			if(searchCriteria.getFilter().getQueueType() == null || searchCriteria.getFilter().getQueueType().length == 0  || Arrays.asList(searchCriteria.getFilter().getQueueType()).contains("All")){
				workEfficiencyReportDto = iWorkEfficiencyReportDBService.getWorkEfficiencyReport(searchCriteria);
			}else {
				workEfficiencyReportDto = iWorkEfficiencyReportDBService.getWorkEfficiencyReportWithCriteria(searchCriteria);
			}
			workEfficiencyReportDto.setDateDifference(dateDifference);
			
		return workEfficiencyReportDto;
		}catch(CompliancePortalException e){
			log.error("{1}",e);
			throw e;
		}
	}
	
	/**
	 * Gets the date from.
	 *
	 * @param dateFrom the date from
	 * @return the date from
	 */
	public String getDateFrom(String dateFrom){
		Integer pastDateSize = SearchCriteriaUtil.getPastDateSize();
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd 00:00:00"); 
		if (dateFrom != null ) {
			return dateFrom;
		}else {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, pastDateSize);
			Date pastDate  =cal.getTime();
			return dt.format(pastDate);
		}
	}
	
	/**
	 * Gets the from to.
	 *
	 * @param fromTo the from to
	 * @return the from to
	 */
	public String getFromTo(String fromTo){
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd 23:59:59"); 
		if (fromTo != null) {
			return fromTo;
		}else {
			Calendar cal = Calendar.getInstance();
			Date currentDate  =cal.getTime();
			return dt.format(currentDate);
		}
	}
	
	/**
	 * Gets the date difference.
	 *
	 * @param fromTo the from to
	 * @param dateFrom the date from
	 * @return the date difference
	 * @throws ParseException the parse exception
	 */
	public static Integer getDateDifference(String fromTo,String dateFrom) throws ParseException{
		  
		Date convertedDate1 ;
		Date convertedDate2 ;
		if (fromTo.contains("-")) {
			DateFormat formatter =new SimpleDateFormat("yyyy-MM-dd");
	        convertedDate1 = formatter.parse(fromTo);
	        convertedDate2 = formatter.parse(dateFrom);
		
		} else {
		     DateFormat formatter =new SimpleDateFormat("dd/MM/yyyy");
	         convertedDate1 = formatter.parse(fromTo);
	         convertedDate2 = formatter.parse(dateFrom);
		}

        return daysBetween(convertedDate2 , convertedDate1);
	}
	
	/**
	 * Days between.
	 *
	 * @param d1 the d 1
	 * @param d2 the d 2
	 * @return the int
	 */
	public static int daysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
    }
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportService#getWorkEfficiencyReportInExcelFormat()
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReportInExcelFormat() throws CompliancePortalException{
		WorkEfficiencyReportSearchCriteria searchCriteriaForExcel = new WorkEfficiencyReportSearchCriteria();
		WorkEfficiencyReportFilter workEfficiencyReportFilterForExcel = new WorkEfficiencyReportFilter();
		WorkEfficiencyReportDto workEfficiencyReportDtoForExcel = new WorkEfficiencyReportDto();
		try {
			String fromToForExcel = null;
			String dateFromForExcel = null;
			fromToForExcel = getFromTo(fromToForExcel);
			dateFromForExcel = getDateFrom(dateFromForExcel);
			workEfficiencyReportFilterForExcel.setFromTo(fromToForExcel);
			workEfficiencyReportFilterForExcel.setDateFrom(dateFromForExcel);
			
			Integer dateDifference=dateDifferenceInWorkEfficiencyReport(fromToForExcel,dateFromForExcel);
			
			searchCriteriaForExcel.setFilter(workEfficiencyReportFilterForExcel);
			workEfficiencyReportDtoForExcel = iWorkEfficiencyReportDBService.getWorkEfficiencyReportInExcelFormat(searchCriteriaForExcel);
			workEfficiencyReportDtoForExcel.setDateDifference(dateDifference);
		    return workEfficiencyReportDtoForExcel;
		}catch(CompliancePortalException e){
			log.error("{1}",e);
			throw e;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.currenciesdirect.gtg.compliance.core.report.IWorkEfficiencyReportService#getWorkEfficiencyReportWithCriteriaInExcelFormat(com.currenciesdirect.gtg.compliance.core.domain.report.WorkEfficiencyReportSearchCriteria)
	 */
	@Override
	public WorkEfficiencyReportDto getWorkEfficiencyReportWithCriteriaInExcelFormat(WorkEfficiencyReportSearchCriteria searchCriteria) throws CompliancePortalException{
		WorkEfficiencyReportDto workEfficiencyReportDto = new WorkEfficiencyReportDto();
		try {
			String fromTo = getFromTo(searchCriteria.getFilter().getFromTo()); //currentDate
			String dateFrom = getDateFrom(searchCriteria.getFilter().getDateFrom()); //pastdate
			searchCriteria.getFilter().setFromTo(fromTo);
			searchCriteria.getFilter().setDateFrom(dateFrom);
			
			Integer dateDifference=dateDifferenceInWorkEfficiencyReport(fromTo,dateFrom);
			
			if(searchCriteria.getFilter().getQueueType() == null || searchCriteria.getFilter().getQueueType().length == 0  || Arrays.asList(searchCriteria.getFilter().getQueueType()).contains("All")){
				workEfficiencyReportDto = iWorkEfficiencyReportDBService.getWorkEfficiencyReportInExcelFormat(searchCriteria);
			}else {
				workEfficiencyReportDto = iWorkEfficiencyReportDBService.getWorkEfficiencyReportWithCriteriaInExcelFormat(searchCriteria);
			}
			workEfficiencyReportDto.setDateDifference(dateDifference);
			
		return workEfficiencyReportDto;
		}catch(CompliancePortalException e){
			log.error("{1}",e);
			throw e;
		}
	}
	
}
