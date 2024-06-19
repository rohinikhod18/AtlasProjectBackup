/**
 * 
 */
package com.currenciesdirect.gtg.compliance.fraugster.dbport;

/**
 * The Class FraugsterQueryConstants.
 *
 * @author manish
 */
public class FraugsterQueryConstants {
	
	/** The Constant GET_FRAUGSTER_PROVIDER_INIT_CONFIG_PROPERTY. */
	public static final String GET_FRAUGSTER_PROVIDER_INIT_CONFIG_PROPERTY = "select a.Attribute,b.Code FROM compliance_ServiceProviderAttribute a LEFT JOIN compliance_ServiceProvider b on a.ID = b.ID where b.internal=0 and b.servicetype=(SELECT ID FROM Compliance_ServiceTypeEnum WHERE Code='FRAUGSTER')";
	
	/**
	 * Instantiates a new fraugster query constants.
	 */
	private FraugsterQueryConstants ()
	{
		
	}
	
}
