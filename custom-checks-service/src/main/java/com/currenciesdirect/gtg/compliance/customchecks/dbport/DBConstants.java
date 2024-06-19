package com.currenciesdirect.gtg.compliance.customchecks.dbport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Enum DBConstants.
 */
public enum DBConstants {
	
	/** The version. */
	VERSION("_version"),
	
	/** The history version. */
	HISTORY_VERSION("version"),
	
	/** The created. */
	CREATED("created"),
	
	/** The type. */
	TYPE("type"),
	
	/** The error. */
	ERROR("error"),
	
	/** The hits. */
	HITS("hits"),
	
	/** The total number of hits. */
	HITS_TOTAL("total"),
	
	
	/** The source. */
	SOURCE("_source"),
	
	/** The document already exist exception. */
	DOCUMENT_ALREADY_EXIST_EXCEPTION("document_already_exists_exception"),
	
	/** The document missing exception. */
	DOCUMENT_MISSING_EXCEPTION("document_missing_exception"),
	
	/** The fundsout index. */
	FUNDS_OUT_INDEX("fundsout"),
	
	/** The fundsout document type. */
	FUNDS_OUT_TYPE("contract"),
	
	/** The fundsout search url. */
	FUNDS_OUT_SEARCH_URL("fundsout/contract/_search?scroll=1m&size=50"),
	
	/** The fundsout update url. */
	FUNDS_OUT_UPDATE_URL("fundsout/contract/{DocumentId}/_update?_timestamp={TIMESTAMP}"),
	
	/** The fundsout create url. */
	FUNDS_OUT_CREATE_URL("fundsout/contract/{DocumentId}/_create?_timestamp={TIMESTAMP}"),
	
	/** The fundsout delete url. */
	FUNDS_OUT_DELETE_URL("fundsout/contract/{DocumentId}/_delete?_timestamp={TIMESTAMP}"),
	
	/** The method get. */
	GET("GET"),
	
	/** The method post. */
	POST("POST"),
	
	/** The method put. */
	PUT("PUT"),
	
	VELOCITY_RULES_REFRESH_TIMEOUT("VELOCITY_RULES_REFRESH_TIMEOUT"),
	
	GET_VELOCITY_RULES("SELECT a.code, b.EventType,b.CustType,b.CountThreshold,b.AmountThreshold FROM VelocityRules b LEFT JOIN Organization a on b.OrganizationID=a.ID"),
	
	GET_PROVIDER_INFO("select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0"),
	
	GET_ACCOUNT_VELOCITY_RULES("SELECT CountThreshold,AmountThreshold FROM Comp.AccountVelocity WHERE AccountId =?"),
	
	CHECK_BENEFICIARY_WHITELIST("SELECT id FROM WhiteListBeneficiary WHERE Deleted=0 AND AccountNumber=?"),
	
	//ADD for AT-3161
	GET_FRAUD_PREDICT_PROVIDER_INFO("select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a "
			+ " LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 "
			+ " and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='FRAUGSTER')");
	
	/** The value. */
	private String value;
	
	/**
	 * Instantiates a new DB constants.
	 *
	 * @param value the value
	 */
	DBConstants(String value){
		this.value = value;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue(){
		return this.value;
	}
	
	/**
	 * Replace with sf account id.
	 *
	 * @param id the id
	 * @return the string
	 */
	public String replaceWithDocumentId(Integer id){
		return this.getValue()
				.replace("{DocumentId}", id.toString())
				.replace("{TIMESTAMP}", parseDate());
	}
	
	private static String parseDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dateO = new Date(System.currentTimeMillis());
		return sdf.format(dateO);
	}
}
