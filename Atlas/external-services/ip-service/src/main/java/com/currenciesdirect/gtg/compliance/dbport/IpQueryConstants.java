/**
 * 
 */
package com.currenciesdirect.gtg.compliance.dbport;

/**
 * @author manish
 *
 */
public class IpQueryConstants {

	public static final String GET_IP_PROVIDER_INIT_CONFIG_PROPERTY = "select a.Attribute,b.Code FROM IP_ServiceProviderAttribute a LEFT JOIN IP_ServiceProvider b on a.ID = b.ID";
	
	public static final String GET_LOCATION_FROM_POSTCODE = "select * from IP_PostCodeLocation where Postcode= ?";
}
