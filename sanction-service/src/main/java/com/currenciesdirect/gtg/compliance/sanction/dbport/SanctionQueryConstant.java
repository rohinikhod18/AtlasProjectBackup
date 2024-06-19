/*******************************************************************************
 * 
 * Copyright 2017 Currencies Direct Ltd, United Kingdom
 * 
 * Compliance: SanctionQueryConstant.java
 ******************************************************************************/
package com.currenciesdirect.gtg.compliance.sanction.dbport;

/**
 * The Class SanctionQueryConstant.
 */
public class SanctionQueryConstant {

	/** The Constant INSERT_INTO_SANCTION_SERVICE_DETAILS. */
	public static final String INSERT_INTO_SANCTION_SERVICE_DETAILS = "INSERT INTO Sanction_Service_Details(Correlation_Id,Organization_Code,Source,Request_Type,Operation,Request,Created_On,Created_By,Updated_On,Updated_By) VALUES (?,?,?,?,?,?,?,?,?,?)";
	
	/** The Constant UPDATE_SANCTION_SERVICE_DETAILS. */
	public static final String UPDATE_SANCTION_SERVICE_DETAILS = "UPDATE Sanction_Service_Details SET Response=?,Updated_On=?,Updated_By=? WHERE Correlation_Id=? AND Organization_Code=?";
    
	/** The Constant GET_SANCTION_PROVIDER_INIT_CONFIG_PROPERTY. */
	public static final String GET_SANCTION_PROVIDER_INIT_CONFIG_PROPERTY = "select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='SANCTION')";

	/**
	 * Instantiates a new sanction query constant.
	 */
	private SanctionQueryConstant() {
		
	}
}
