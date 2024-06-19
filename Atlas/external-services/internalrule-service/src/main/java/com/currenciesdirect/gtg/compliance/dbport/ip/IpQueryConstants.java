/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport.ip;

/**
 * @author manish
 *
 */
public class IpQueryConstants {

	public static final String GET_IP_PROVIDER_INIT_CONFIG_PROPERTY = "select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='IP')";
	
	public static final String GET_LOCATION_FROM_POSTCODE = "select * from IP_PostCodeLocation where Postcode= ?";

private IpQueryConstants(){	}
}

