package com.currenciesdirect.gtg.compliance.dbport.report;

/**
 * The Enum DashboardDBColumns.
 * @author abhijeetg
 */
public enum DashboardDBColumns {

	 LEGALENTITY("legalEntity"), RECORDS("records"), AGE("Age"), LAST_CONTACT_ID("lastContactId"),
	 
	 FIRST_CONTACT_ID("firstContactId"), OLD_CONTACT_AGE("oldContactAge"), NEW_CONTACT_AGE("newContactAge"), 
	 
	 LAST_PAYMENTIN_ID("lastPaymentInId"), FIRST_PAYMENTIN_ID("firstPaymentInId"), OLD_PAYMENTIN_AGE("oldPaymentInAge"),
	 
	 NEW_PAYMENTIN_AGE("newPaymentInAge"), LAST_PAYMENTOUT_ID("lastPaymentOutid"), FIRST_PAYMENTOUT_ID("firstPaymentOutId"),
	 
	 OLD_PAYMENTOUT_AGE("oldPaymentOutAge"), NEW_PAYMENTOUT_AGE("newPaymentOutAge"),
	 
	 COUNTRY_DISPLAY_NAME("displayname"), COUNTRY_SHORT_CODE("shortcode"), COUNTRY_WISE_TOTAL_RECORDS("CountryWiseTotal"),
	 
	 BUSINESS_WISE_TOTAL_RECORDS("BUWiseTotal"), CUSTOMER_TYPE("customerType"), RESOURCE_TYPE("ResourceType"), 
	 
	 TOTAL_HOURS("ToatalRecords"), TOTAL_RECORDS("ToatalRecords"), AVG_CLEARING_TIME("AvgClearingTime");
	
	private String name;

	private DashboardDBColumns(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
