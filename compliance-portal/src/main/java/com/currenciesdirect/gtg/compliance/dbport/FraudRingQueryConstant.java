package com.currenciesdirect.gtg.compliance.dbport;

public enum FraudRingQueryConstant {

	/** Client Details for showing on UI*/
	GET_CLIENT_DETAILS_FOR_FRAUD_RING( "   select  "  + 
			 " c.id AS ContactId,  "  + 
			 " JSON_VALUE(aa.[Attributes],'$.cust_type') AS [Type],  "  + 
			 " JSON_VALUE(ca.[Attributes],'$.email') AS Email,  "  + 
			 " JSON_VALUE(ca.[Attributes],'$.building_name') AS BuildingName,  "  + 
			 " JSON_VALUE(ca.[Attributes],'$.post_code') AS Postcode,  "  + 
			 " JSON_VALUE(ca.[Attributes],'$.mobile_phone') AS Phone,  "  + 
			 " JSON_VALUE(aa.[Attributes],'$.customer_legal_entity') AS CDH_Entity,  "  + 
			 " JSON_VALUE(aa.[Attributes],'$.reg_mode') AS Registration_Mode,  "  + 
			 " CASE  "  + 
			 " WHEN a.[Type] = 2 THEN c.CreatedOn  "  + 
			 " ELSE a.CreatedOn  "  + 
			 " END AS Registration_Date,  "  + 
			 " a.ComplianceDoneOn AS Registered_Date  "  + 
			 "   from  "  + 
			 " Comp.Account a  "  + 
			 "   JOIN Contact c ON  "  + 
			 " a.ID = c.AccountID  "  + 
			 "   JOIN Comp.AccountAttribute aa ON  "  + 
			 " a.id = aa.id  "  + 
			 "   JOIN Comp.ContactAttribute ca ON  "  + 
			 " c.ID = ca.ID  "  + 
			 "   WHERE  "  + 
			 " c.CRMContactID = ?");
	
	
	
	/** The query. */
	private String query;
	
	/**
	 * Instantiates a new fraudRing query constant.
	 *
	 * @param query the query
	 */
	FraudRingQueryConstant(String query) {
		this.query = query; 
	}
	
	/**
	 * Gets the query.
	 *
	 * @return the query
	 */
	public String getQuery() {
		return this.query;
	}
}
