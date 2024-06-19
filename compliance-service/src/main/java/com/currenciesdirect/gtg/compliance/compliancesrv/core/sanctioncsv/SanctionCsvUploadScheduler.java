package com.currenciesdirect.gtg.compliance.compliancesrv.core.sanctioncsv;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SanctionCsvUploadScheduler {
	
	/** The Constant LOG. */
	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(SanctionCsvUploadScheduler.class);
	
	@Autowired
	private SanctionSchedulerCronJob sanctionSchedulerCronJob;
	
	@Autowired
	private SanctionCsvFlowService sanctionCsvFlow;
	
	@Bean
	public String getCronJob() {
		return sanctionSchedulerCronJob.getCronJob();
	}
	
	//To prevent finscan csv flow from execution
	private static final Boolean ENABLE_FINSCAN_SCHEDULER = Boolean.FALSE;
	
	@Scheduled(cron = "#{getCronJob}")
	public void initiateScheduler() {
		if(ENABLE_FINSCAN_SCHEDULER) { 
		try {
			LOG.info("---Sanction CSV Flag value is {}---",ENABLE_FINSCAN_SCHEDULER);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
			LocalDateTime now = LocalDateTime.now();  
			
			LOG.info("-------------- Sanction CSV Scheduler Started {}--------------",dtf.format(now));
			sanctionCsvFlow.initiateFlow();
			
			now = LocalDateTime.now(); 
			LOG.info("-------------- Sanction CSV Scheduler Ended {}--------------",dtf.format(now));
		}catch(Exception e) {
			LOG.error("Error in SanctionCsvUploadScheduler", e);
		}
		}else {
		LOG.info("---Sanction CSV Flag value is {}---",ENABLE_FINSCAN_SCHEDULER);
		LOG.info("-------------- Skipping sanction CSV Execution --------------");
		}
	}
}
