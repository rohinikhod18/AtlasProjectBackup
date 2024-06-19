package com.currenciesdirect.gtg.compliance.util;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.currenciesdirect.gtg.compliance.core.domain.Constants;

/**
 * The Class CofigurationLoader.
 */
@Component
public class CofigurationLoader {

	public static final String HTTP_LOCALHOST_8080 = "http://localhost:8080";

	private static Logger log = LoggerFactory.getLogger(CofigurationLoader.class);

	@Autowired
	private ServletContext servletContext;

	/**
	 * Inits the config.
	 */
	@PostConstruct
	public void initConfig() {
		String baseComplianceUrl = System.getProperty("baseComplianceServiceUrl");
		String baseEnterpriseUrl = System.getProperty("baseEnterpriseUrl");
		String baseTitanUrl = System.getProperty("baseTitanUrl");
		String pageSize = System.getProperty(Constants.PAGE_SIZE);
		String viewMoreRecords = System.getProperty(Constants.VIEW_MORE_RECORDS);
		String pastDateSize = System.getProperty(Constants.PAST_DATE_SIZE);
		String complianceExpiryYears = System.getProperty(Constants.COMPLIANCE_EXPIRY_YEARS);
		
		if (null == baseComplianceUrl) {
			log.warn("Compliance Service base Url not Configured, using localhost");
			servletContext.setAttribute("complianceServiceUrl", HTTP_LOCALHOST_8080);
		} else {
			servletContext.setAttribute("complianceServiceUrl", baseComplianceUrl.trim());
		}
		if (null == baseEnterpriseUrl) {
			log.warn("Enterprise base Url not Configured, using localhost");
			servletContext.setAttribute("enterpriseUrl", HTTP_LOCALHOST_8080);
		} else {
			servletContext.setAttribute("enterpriseUrl", baseEnterpriseUrl.trim());
		}
		if (null == baseTitanUrl) {
			log.warn("Titan base Url not Configured, using localhost");
			servletContext.setAttribute("titanUrl", HTTP_LOCALHOST_8080);
		} else {
			servletContext.setAttribute("titanUrl", baseTitanUrl.trim());
		}
		if (null == pageSize) {
			log.warn("Queue Page size not Configured and Default page size is 50");
			servletContext.setAttribute(Constants.PAGE_SIZE, 50);
		} else {
			servletContext.setAttribute(Constants.PAGE_SIZE, pageSize.trim());
		}
		if (null == viewMoreRecords) {
			log.warn("View More Records size not Configured and Default page size is 10");
			servletContext.setAttribute(Constants.VIEW_MORE_RECORDS, 10);
		} else {
			servletContext.setAttribute(Constants.VIEW_MORE_RECORDS, viewMoreRecords.trim());
		}
		if (null == pastDateSize) {
			log.warn("Past Date Size not Configured for WorkEfficiency Report so Default past date size is 7");
			servletContext.setAttribute(Constants.PAST_DATE_SIZE, 7);
		} else {
			servletContext.setAttribute(Constants.PAST_DATE_SIZE, pastDateSize.trim());
		}
		if (null == complianceExpiryYears) {
			log.warn("Compliance Expiry Period not Configured so Default complianceExpiryYears is 3");
			servletContext.setAttribute(Constants.COMPLIANCE_EXPIRY_YEARS, 3);
		} else {
			servletContext.setAttribute(Constants.COMPLIANCE_EXPIRY_YEARS, complianceExpiryYears.trim());
		}
	}

}
